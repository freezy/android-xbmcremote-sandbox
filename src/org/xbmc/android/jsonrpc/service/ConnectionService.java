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
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.io.ApiException;
import org.xbmc.android.jsonrpc.io.ConnectionManager;
import org.xbmc.android.jsonrpc.io.JsonHandler;

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
 * Service which keeps a steady TCP connection to XBMC's JSON-RPC API via
 * TCP socket (as opposed to HTTP messages).
 * <p>
 * It serves as listener for notification, but is also used for posting normal
 * API requests.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class ConnectionService extends IntentService {
	
	public final static String TAG = ConnectionService.class.getSimpleName();

	private static final int SOCKET_TIMEOUT = 5000;

	public static final String EXTRA_STATUS_RECEIVER = "org.xbmc.android.jsonprc.extra.STATUS_RECEIVER";
	public static final String EXTRA_APICALL = "org.xbmc.android.jsonprc.extra.APICALL";
	public static final String EXTRA_HANDLER = "org.xbmc.android.jsonprc.extra.HANDLER";
	public static final String EXTRA_ERROR_CODE = "org.xbmc.android.jsonprc.extra.ERROR_CODE";
	public static final String EXTRA_ERROR_MESSAGE = "org.xbmc.android.jsonprc.extra.ERROR_MESSAGE";

	public static final int MSG_REGISTER_CLIENT = 0x01;
	public static final int MSG_UNREGISTER_CLIENT = 0x02;
	public static final int MSG_RECEIVE_NOTIFICATION = 0x03;
	public static final int MSG_RECEIVE_APICALL = 0x04;	
	public static final int MSG_SEND_APICALL = 0x05;
	public static final int MSG_SEND_HANDLED_APICALL = 0x06;
	public static final int MSG_ERROR = 0x07;	

	public static final int ERROR_SOCKET_WRITE = 0x01;	
	public static final int ERROR_UNKNOWN_HOST = 0x02;	
	public static final int ERROR_IO_EXCEPTION = 0x03;	
	
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
	 * If we have to send data before we're connected, store data until connection 
	 */
	private final LinkedList<AbstractCall<?>> mPendingCalls = new LinkedList<AbstractCall<?>>();
	/**
	 * All calls the service is currently dealing with. Key is the ID of the call.
	 */
	private final HashMap<String, AbstractCall<?>> mCalls = new HashMap<String, AbstractCall<?>>();
	private final HashMap<String, JsonHandler> mHandlers = new HashMap<String, JsonHandler>();
	/**
	 * API call results are only returned to the client requested it, so here are the relations.
	 */
	private final HashMap<AbstractCall<?>, Messenger> mClientMap = new HashMap<AbstractCall<?>, Messenger>();
	
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
	 * Empty class constructor.
	 */
	public ConnectionService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		
		long s = System.currentTimeMillis();
		Log.d(TAG, "Starting TCP client...");
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
			notifyError(ERROR_UNKNOWN_HOST, "Unknown host: " + e.getMessage(), e);
			return;
		} catch (IOException e) {
			Log.e(TAG, "I/O error while opening: " + e.getMessage(), e);
			notifyError(ERROR_IO_EXCEPTION, "I/O error while opening: " + e.getMessage(), e);
			return;
		}
		
		try {
			Log.i(TAG, "Connected to TCP socket in " + (System.currentTimeMillis() - s) + "ms");
			
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
				Log.i(TAG, "READ: " + node.toString());
				notifyClients(node);
			}
			mOut.close();
			Log.i(TAG, "TCP socket closed.");

		} catch (IOException e) {
			Log.e(TAG, "I/O error while reading: " + e.getMessage(), e);
			notifyError(ERROR_IO_EXCEPTION, "I/O exception while reading: " + e.getMessage(), e);
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
		Log.i(TAG, "ConnectionService bound to new client.");
		return mMessenger.getBinder();
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		final boolean ret = super.onUnbind(intent);
		if (mClients.size() == 0) {
			Log.i(TAG, "No more clients, shutting down service.");
			stopSelf();
		}
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
		final HashMap<AbstractCall<?>, Messenger> map = mClientMap;
		final HashMap<String, AbstractCall<?>> calls = mCalls;
		final HashMap<String, JsonHandler> handlers = mHandlers;
		
		// check if notification or api call
		if (node.has("id")) {
			final String id = node.get("id").getValueAsText();
			if (calls.containsKey(id)) {
				final AbstractCall<?> call = calls.get(id);
				if (handlers.containsKey(id)) {
					try {
						handlers.get(id).applyResult(node, getContentResolver());
					} catch (ApiException e) {
						notifyError(e.getCode(), "Error synchronizing: " + e.getMessage(), e);
					}
				} else {
					
					call.setResponse(node);
					final Bundle b = new Bundle();
					b.putParcelable(EXTRA_APICALL, call);
					final Message msg = Message.obtain(null, MSG_RECEIVE_APICALL);
					msg.setData(b);
					if (map.containsKey(call)) {
						try {
							map.get(call).send(msg);
							Log.i(TAG, "Sent updated API call " + call.getName() + " to client.");
						} catch (RemoteException e) {
							Log.e(TAG, "Error sending API response to client: " + e.getMessage(), e);
							map.remove(call);
						} finally {
							// clean up
							map.remove(call);
							calls.remove(id);
						}
					} else {
						Log.w(TAG, "Cannot find client in caller-mapping, dropping response.");
					}
				}
			} else {
				Log.e(TAG, "Error: Cannot find API call with ID " + id + ".");
			}
		} else {
			Log.i(TAG, "Notifying " + clients.size() + " clients.");
			for (int i = clients.size() - 1; i >= 0; i--) {
				try {
					final Bundle b = new Bundle();
					// TODO add notification in here once it's parcelable 
					final Message msg = Message.obtain(null, MSG_RECEIVE_NOTIFICATION);
					msg.setData(b);
					clients.get(i).send(msg);
					
				} catch (RemoteException e) {
					Log.e(TAG, "Cannot send notification to client: " + e.getMessage(), e);
					// The client is dead. Remove it from the list; we are going
					// through the list from back to front so this is safe to do
					// inside the loop.
					clients.remove(i);
					stopSelf();
				}
			}
		}
	}
	
	/**
	 * Sends an error to all clients.
	 * @param code Error code, see ERROR_*
	 * @param message Error message
	 * @param e Exception
	 */
	private void notifyError(int code, String message, Exception e) {
		Log.i(TAG, "Sending error to " + mClients.size() + " clients.");
		for (int i = mClients.size() - 1; i >= 0; i--) {
			try {
				final Bundle b = new Bundle();
				b.putInt(EXTRA_ERROR_CODE, code);
				b.putString(EXTRA_ERROR_MESSAGE, message);
				Message msg = Message.obtain(null, MSG_ERROR);
				msg.setData(b);
				mClients.get(i).send(msg);
				Log.i(TAG, "Sent error to client " + i + ".");
				stopSelf();
				
			} catch (RemoteException e2) {
				Log.e(TAG, "Cannot send errors to client: " + e.getMessage(), e2);
				// The client is dead. Remove it from the list; we are going
				// through the list from back to front so this is safe to do
				// inside the loop.
				mClients.remove(i);
				stopSelf();
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
			notifyError(ERROR_SOCKET_WRITE, e.getMessage(), e);
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
				break;
			case MSG_UNREGISTER_CLIENT:
				mClients.remove(msg.replyTo);
				Log.d(TAG, "Unregistered client.");
				break;
			case MSG_SEND_APICALL: {
				Log.d(TAG, "Sending new API call..");
				final Bundle data = msg.getData();
				final AbstractCall<?> call = (AbstractCall<?>)data.getParcelable(EXTRA_APICALL);
				mCalls.put(call.getId(), call);
				mClientMap.put(call, msg.replyTo);
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
				mClientMap.put(call, msg.replyTo);
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
