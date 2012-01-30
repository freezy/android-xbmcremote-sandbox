/*
 *      Copyright (C) 2005-2015 Team XBMC
 *      http://xbmc.org
 *
 *  This Program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2, or (at your option)
 *  any later version.
 *
 *  This Program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with XBMC Remote; see the file license.  If not, write to
 *  the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.
 *  http://www.gnu.org/copyleft/gpl.html
 *
 */

package org.xbmc.android.jsonrpc.service;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.io.ApiException;
import org.xbmc.android.jsonrpc.io.ConnectionManager;
import org.xbmc.android.jsonrpc.io.JsonHandler;
import org.xbmc.android.jsonrpc.notification.AbstractEvent;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

/**
 * Service which keeps a steady TCP connection to XBMC's JSON-RPC API via TCP
 * socket (as opposed to HTTP messages).
 * <p/>
 * It serves as listener for notification, but is also used for posting normal
 * API requests.
 * <p/>
 * Generally speaking, the service will shut down and terminate the TCP
 * connection as soon as there are no more connected clients. However, clients
 * may want to query several consecutive requests without having the service
 * stop and restart between every request. That is the reason why there is a
 * "cooldown" period, in which the service will just wait for new clients to
 * arrive before shutting down.
 * <p/>
 * About message exchange, see {@link ConnectionManager}.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public class ConnectionService extends IntentService {
	
	public final static String TAG = ConnectionService.class.getSimpleName();

	private static final int SOCKET_TIMEOUT = 5000;

	public static final String EXTRA_STATUS = "org.xbmc.android.jsonprc.extra.STATUS";
	public static final String EXTRA_APICALL = "org.xbmc.android.jsonprc.extra.APICALL";
	public static final String EXTRA_NOTIFICATION = "org.xbmc.android.jsonprc.extra.NOTIFICATION";
	public static final String EXTRA_HANDLER = "org.xbmc.android.jsonprc.extra.HANDLER";
	public static final String EXTRA_CALLID = "org.xbmc.android.jsonprc.extra.CALLID";

	public static final int MSG_REGISTER_CLIENT = 0x01;
	public static final int MSG_UNREGISTER_CLIENT = 0x02;
	public static final int MSG_CONNECTING = 0x03;
	public static final int MSG_CONNECTED = 0x04;
	public static final int MSG_RECEIVE_NOTIFICATION = 0x05;
	public static final int MSG_RECEIVE_APICALL = 0x06;	
	public static final int MSG_RECEIVE_HANDLED_APICALL = 0x07;	
	public static final int MSG_SEND_APICALL = 0x08;
	public static final int MSG_SEND_HANDLED_APICALL = 0x09;
	public static final int MSG_ERROR = 0x0a;	

	public static final int RESULT_SUCCESS = 0x01;
	
	/**
	 * Time in milliseconds we wait for new requests until we shut down the 
	 * service (and connection).
	 */
	private static final long COOLDOWN = 10000;
	
	/**
	 * Target we publish for clients to send messages to IncomingHandler.
	 */
	private final Messenger mMessenger = new Messenger(new IncomingHandler());

	/**
	 * Keeps track of all currently registered client. Normally, all clients
	 * are {@link ConnectionManager} instances.
	 */
	private final ArrayList<Messenger> mClients = new ArrayList<Messenger>();
	/**
	 * API call results are only returned to the client requested it, so here are the relations.
	 */
	private final HashMap<String, Messenger> mClientMap = new HashMap<String, Messenger>();
	/**
	 * If we have to send data before we're connected, store data until connection 
	 */
	private final LinkedList<AbstractCall<?>> mPendingCalls = new LinkedList<AbstractCall<?>>();
	/**
	 * All calls the service is currently dealing with. Key is the ID of the call.
	 */
	private final HashMap<String, AbstractCall<?>> mCalls = new HashMap<String, AbstractCall<?>>();
	/**
	 * The handler we'll update with a status code as soon as we're done.
	 */
	private final HashMap<String, JsonHandler> mHandlers = new HashMap<String, JsonHandler>();

	/**
	 * Reference to the socket, so we shut it down properly.
	 */
	private Socket mSocket = null;
	/**
	 * Output writer so we can also write stuff to the socket.
	 */
	private BufferedWriter mOut = null;
	
	/**
	 * Static reference to Jackson's object mapper.
	 */
	private final static ObjectMapper OM = new ObjectMapper();
	
	/**
	 * When no more clients are connected, wait {@link #COOLDOWN} milliseconds 
	 * and then shut down the service if no new clients connect.
	 */
	private Timer mCooldownTimer = null;
	
	   
	/**
	 * Empty class constructor.
	 */
	public ConnectionService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		long s = System.currentTimeMillis();
		Log.d(TAG, "Starting TCP client...");
		notifyStatus(MSG_CONNECTING);
		Socket socket = null;

		try {
			final InetSocketAddress sockaddr = new InetSocketAddress("192.168.0.100", 9090);
			socket = new Socket();
			mSocket = socket;       // update class reference
			socket.setSoTimeout(0); // no timeout for reading from connection.
			socket.connect(sockaddr, SOCKET_TIMEOUT);
			mOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (UnknownHostException e) {
			Log.e(TAG, "Unknown host: " + e.getMessage(), e);
			notifyError(new ApiException(ApiException.IO_UNKNOWN_HOST,  "Unknown host: " + e.getMessage(), e), null);
			return;
		} catch (SocketTimeoutException e) {
			Log.e(TAG, "Unknown host: " + e.getMessage(), e);
			notifyError(new ApiException(ApiException.IO_SOCKETTIMEOUT,  "Connection timeout: " + e.getMessage(), e), null);
			return;
		} catch (IOException e) {
			Log.e(TAG, "I/O error while opening: " + e.getMessage(), e);
			notifyError(new ApiException(ApiException.IO_EXCEPTION_WHILE_OPENING,  "I/O error while opening: " + e.getMessage(), e), null);
			return;
		}
		
		try {
			Log.i(TAG, "Connected to TCP socket in " + (System.currentTimeMillis() - s) + "ms");
			notifyStatus(MSG_CONNECTED);
			
			// check for saved post data to send while we weren't connected,
			// but do it in a separate thread so we can read already while sending.
			if (!mPendingCalls.isEmpty()) {
				new Thread("post-data-on-connection") {
					@Override
					public void run() {
						final LinkedList<AbstractCall<?>> calls = mPendingCalls;
						while (!calls.isEmpty()) {
							writeSocket(calls.poll());
						}
					};
				}.start();
			}
			
			final JsonFactory jf = OM.getJsonFactory();
			final JsonParser jp = jf.createJsonParser(socket.getInputStream());
			JsonNode node = null;
			while ((node = OM.readTree(jp)) != null) {
				Log.i(TAG, "READ: " + node.toString().substring(0, 80) + "...");
//				Log.i(TAG, "READ: " + node.toString());
				notifyClients(node);
			}
			mOut.close();
			Log.i(TAG, "TCP socket closed.");

		} catch (EOFException e) {
			Log.i(TAG, "Connection broken, quitting.");
		} catch (IOException e) {
			Log.e(TAG, "I/O error while reading: " + e.getMessage(), e);
			notifyError(new ApiException(ApiException.IO_EXCEPTION_WHILE_READING,  "I/O error while reading: " + e.getMessage(), e), null);
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
				if (mOut != null) {
					mOut.close();
				}
			} catch (IOException e) {
				// do nothing.
			}
		}
	}	

	@Override
	public IBinder onBind(Intent intent) {
		Log.i(TAG, "Connection service bound to new client.");
		return mMessenger.getBinder();
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		final boolean ret = super.onUnbind(intent);
		return ret;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			final Socket socket = mSocket;
			if (socket != null) {
				socket.shutdownInput();
				socket.close();
			}
		} catch (IOException e) {
			Log.e(TAG, "Error closing socket.", e);
		}
		Log.d(TAG, "Notification service destroyed.");
	}
	
	/**
	 * Starts cooldown. If there is no new client for {@link #COOLDOWN}
	 * milliseconds, the service will shutdown, otherwise it will continue
	 * to run until there is another cooldown.
	 */
	private void cooldown() {
		Log.i(TAG, "Starting service cooldown.");
		mCooldownTimer = new Timer();
		mCooldownTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (mClients.isEmpty()) {
					Log.i(TAG, "No new clients, shutting down service.");
					stopSelf();
				} else {
					Log.i(TAG, "Cooldown failed, got " + mClients.size() + " new client(s).");
				}
			}
		}, COOLDOWN);
	}
	

	/**
	 * Treats the received result.
	 * <p/>
	 * If an ID is found in the response, the API call object is retrieved, 
	 * updated and sent back to the client.
	 * <p/>
	 * Otherwise, the notification object is sent to all clients.
	 * 
	 * @param data JSON-serialized response
	 */
	private void notifyClients(JsonNode node) {
		final ArrayList<Messenger> clients = mClients;
		final HashMap<String, Messenger> map = mClientMap;
		final HashMap<String, AbstractCall<?>> calls = mCalls;
		final HashMap<String, JsonHandler> handlers = mHandlers;
		
		// check if notification or api call
		if (node.has("id")) {
			// it's api call.
			final String id = node.get("id").getValueAsText();
			if (calls.containsKey(id)) {
				final AbstractCall<?> call = calls.get(id);
				if (handlers.containsKey(id)) {
					// we got an provided handler, so apply it and send back status message.
					try {
						handlers.get(id).applyResult(node, getContentResolver());
						// get the right client to send back to
						if (map.containsKey(id)) {
							final Bundle b = new Bundle();
							b.putString(EXTRA_CALLID, call.getId());
							b.putInt(EXTRA_STATUS, RESULT_SUCCESS);
							final Message msg = Message.obtain(null, MSG_RECEIVE_HANDLED_APICALL);
							msg.setData(b);
							try {
								map.get(id).send(msg);
								Log.i(TAG, "API call handled successfully, posting status back to client.");
							} catch (RemoteException e) {
								Log.e(TAG, "Error posting status back to client: " + e.getMessage(), e);
								map.remove(id);
							} finally {
								// clean up
								map.remove(id);
								calls.remove(id);
							}
						} else {
							Log.w(TAG, "Cannot find client in caller-mapping for " + id + ", dropping response (handled call).");
						}
					} catch (ApiException e) {
						notifyError(e, id);
					}
				} else {
					// get the right client to send back to
					if (map.containsKey(id)) {
						call.setResponse(node);
						final Bundle b = new Bundle();
						b.putParcelable(EXTRA_APICALL, call);
						final Message msg = Message.obtain(null, MSG_RECEIVE_APICALL);
						msg.setData(b);
						try {
							map.get(id).send(msg);
							Log.i(TAG, "Sent updated API call " + call.getName() + " to client.");
						} catch (RemoteException e) {
							Log.e(TAG, "Error sending API response to client: " + e.getMessage(), e);
							map.remove(id);
						} finally {
							// clean up
							map.remove(id);
							calls.remove(id);
						}
					} else {
						Log.w(TAG, "Cannot find client in caller-mapping for " + id + ", dropping response (api call).");
					}
				}
			} else {
				Log.e(TAG, "Error: Cannot find API call with ID " + id + ".");
			}
		} else {
			// it's a notification.
			final AbstractEvent event = AbstractEvent.parse((ObjectNode) node);
			if (event != null) {
				Log.i(TAG, "Notifying " + clients.size() + " clients.");
				for (int i = clients.size() - 1; i >= 0; i--) {
					try {
						final Bundle b = new Bundle();
						b.putParcelable(EXTRA_NOTIFICATION, event);
						final Message msg = Message.obtain(null, MSG_RECEIVE_NOTIFICATION);
						msg.setData(b);
						clients.get(i).send(msg);

					} catch (RemoteException e) {
						Log.e(TAG, "Cannot send notification to client: " + e.getMessage(), e);
						/*
						 * The client is dead. Remove it from the list; we are
						 * going through the list from back to front so this is
						 * safe to do inside the loop.
						 */
						clients.remove(i);
						// stopSelf();
					}
				}
			} else {
				Log.i(TAG, "Ignoring unknown notification " + node.get("method").getTextValue() + ".");
			}
		}
	}
	
	/**
	 * Sends an error to all clients.
	 * @param code Error code, see ERROR_*
	 * @param message Error message
	 * @param e Exception
	 */
	private void notifyError(ApiException e, String id) {

		// if id is set and callback exists, only send error back to one client.
		if (id != null && mClientMap.containsKey(id)) {
			try {
				final Message msg = Message.obtain(null, MSG_ERROR);
				msg.setData(e.getBundle(getResources()));
				mClientMap.get(id).send(msg);
				Log.i(TAG, "Sent error to client with ID " + id + ".");
			} catch (RemoteException e2) {
				Log.e(TAG, "Cannot send errors to client " + id + ": "+ e.getMessage(), e2);
				mClientMap.remove(id);
			}
		} else {
			// otherwise, send error back to all clients and die.
			for (int i = mClients.size() - 1; i >= 0; i--) {
				final Message msg = Message.obtain(null, MSG_ERROR);
				msg.setData(e.getBundle(getResources()));
				try {
					mClients.get(i).send(msg);
					Log.i(TAG, "Sent error to client " + i + ".");
				} catch (RemoteException e2) {
					Log.e(TAG, "Cannot send errors to client: " + e2.getMessage(), e2);
					/*
					 * The client is dead. Remove it from the list; we are going
					 * through the list from back to front so this is safe to do
					 * inside the loop.
					 */
					mClients.remove(i);
				}
			}
			stopSelf();
		}
	}
	
	private void notifyStatus(int code) {
		final Message msg = Message.obtain(null, code);
		for (int i = mClients.size() - 1; i >= 0; i--) {
			try {
				mClients.get(i).send(msg);
			} catch (RemoteException e2) {
				Log.e(TAG, "Cannot send errors to client: " + e2.getMessage(), e2);
				/*
				 * The client is dead. Remove it from the list; we are going
				 * through the list from back to front so this is safe to do
				 * inside the loop.
				 */
				mClients.remove(i);
			}
		}
	}
	
	
	
	/**
	 * Serializes the API request and dumps it on the socket.
	 * @param call
	 */
	private void writeSocket(AbstractCall<?> call) {
		final String data = call.getRequest().toString();
		Log.d(TAG, "Sending data to server.");
		Log.d(TAG, "REQUEST: " + data);
		try {
			mOut.write(data + "\n");
			mOut.flush();
		} catch (IOException e) {
			Log.e(TAG, "Error writing to socket: " + e.getMessage(), e);
			notifyError(new ApiException(ApiException.IO_EXCEPTION_WHILE_WRITING,  "I/O error while writing: " + e.getMessage(), e),  call.getId());
		}
	}
	
	/**
	 * Handler of incoming messages from clients.
	 */
	private class IncomingHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_REGISTER_CLIENT:
				mClients.add(msg.replyTo);
				Log.d(TAG, "Registered new client.");
				if (mCooldownTimer != null) {
					Log.i(TAG, "Aborting cooldown timer.");
					mCooldownTimer.cancel();
					mCooldownTimer.purge();
				}
				break;
			case MSG_UNREGISTER_CLIENT:
				mClients.remove(msg.replyTo);
				Log.d(TAG, "Unregistered client.");
				if (mClients.size() == 0) {
					Log.i(TAG, "No more clients, cooling down service.");
					cooldown();
				}
				break;
			case MSG_SEND_APICALL: {
				Log.d(TAG, "Sending new API call..");
				final Bundle data = msg.getData();
				final AbstractCall<?> call = (AbstractCall<?>)data.getParcelable(EXTRA_APICALL);
				mCalls.put(call.getId(), call);
				mClientMap.put(call.getId(), msg.replyTo);
				if (mOut == null) {
					mPendingCalls.add(call);
				} else {
					writeSocket(call);
				}
			}
			break;
			case MSG_SEND_HANDLED_APICALL: {
				Log.d(TAG, "Sending new handled API call..");
				final Bundle data = msg.getData();
				final AbstractCall<?> call = (AbstractCall<?>)data.getParcelable(EXTRA_APICALL);
				final JsonHandler handler = (JsonHandler)data.getParcelable(EXTRA_HANDLER);
				mCalls.put(call.getId(), call);
				mHandlers.put(call.getId(), handler);
				mClientMap.put(call.getId(), msg.replyTo);
				if (mOut == null) {
					Log.d(TAG, "Quering for later.");
					mPendingCalls.add(call);
				} else {
					writeSocket(call);
				}
			}
			break;
			default:
				super.handleMessage(msg);
			}
		}
	}
}
