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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.xbmc.android.jsonrpc.NotificationManager;

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
 * A service which connects to JSON-RPC's TCP API and listens for data to
 * arrive.
 * <p>
 * Multiple clients can be connected to this service. When the last client
 * quits, the service closes the TCP socket and shuts itself down.
 * <p>
 * Client/service communication goes via a {@link Messenger} binder. The 
 * service processes the following messages:
 * <ul><li>{@link #MSG_REGISTER_CLIENT} registers a new client.</li>
 *     <li>{@link #MSG_UNREGISTER_CLIENT} removes a client.</li></ul>
 *     
 * Messages sent *to* the client are the following:
 * <ul><li>{@link #MSG_RECEIVE_JSON_DATA} sends a received JSON notification to the
 *          client.</li></ul>
 *          
 * This class should probably not be used directly, you more likely want to use
 * {@link NotificationManager}, which parses the server response and returns
 * easily treatable POJOs to the client.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public class NotificationService extends IntentService {

	public final static String TAG = NotificationService.class.getSimpleName();
	
	private static final int SOCKET_TIMEOUT = 5000;
	private static final int PRINT_MAX_CHARS = 5000;

	public static final String EXTRA_STATUS_RECEIVER = "org.xbmc.android.jsonprc.extra.STATUS_RECEIVER";
	public static final String EXTRA_JSON_DATA = "org.xbmc.android.jsonprc.extra.JSON_DATA";
	public static final String EXTRA_ERROR_CODE = "org.xbmc.android.jsonprc.extra.ERROR_CODE";
	public static final String EXTRA_ERROR_MESSAGE = "org.xbmc.android.jsonprc.extra.ERROR_MESSAGE";

	public static final int MSG_REGISTER_CLIENT = 1;
	public static final int MSG_UNREGISTER_CLIENT = 2;
	public static final int MSG_RECEIVE_JSON_DATA = 3;	
	public static final int MSG_SEND_JSON_DATA = 4;
	public static final int MSG_ERROR = 5;	
	
	/**
	 * Target we publish for clients to send messages to IncomingHandler.
	 */
	private final Messenger mMessenger = new Messenger(new IncomingHandler());

	/**
	 * Keeps track of all currently registered clients.
	 */
	private final ArrayList<Messenger> mClients = new ArrayList<Messenger>();
	/**
	 * If we have to send data before we're connected, store data until connection 
	 */
	private final LinkedList<String> mPostData = new LinkedList<String>();
	
	/**
	 * Reference to the socket, so we shut it down properly.
	 */
	private Socket mSocket = null;
	
	/**
	 * Output writer so we can also write stuff to the socket.
	 */
	private BufferedWriter mOut = null;
	
	   
	/**
	 * Empty class constructor.
	 */
	public NotificationService() {
		super(TAG);
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "Notification service created.");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		long s = System.currentTimeMillis();
		Log.d(TAG, "Starting TCP client...");
		Socket socket = null;
		BufferedReader in = null;

		try {
			final InetSocketAddress sockaddr = new InetSocketAddress("192.100.120.114", 9090);
			socket = new Socket();
			mSocket = socket;       // update class reference
			socket.setSoTimeout(0); // no timeout for reading from connection.
			socket.connect(sockaddr, SOCKET_TIMEOUT);
			//in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			mOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (UnknownHostException e) {
			Log.e(TAG, "Unknown host: " + e.getMessage(), e);
			notifyError(1, "Unknown host: " + e.getMessage());
			return;
		} catch (IOException e) {
			Log.e(TAG, "I/O error while opening: " + e.getMessage(), e);
			notifyError(2, "I/O error while opening: " + e.getMessage());
			return;
		}
		
		boolean started = false;
		StringBuffer sb = new StringBuffer();
		try {
			Log.i(TAG, "Connected to TCP socket in " + (System.currentTimeMillis() - s) + "ms");
			
			// check for saved post data to send while we weren't connected,
			// but do it in a separate thread so we can read already while sending.
			new Thread("post-data-on-connection") {
				@Override
				public void run() {
					final LinkedList<String> postData = mPostData;
					while (!postData.isEmpty()) {
						writeSocket(postData.poll());
					}
				};
			}.start();
			
			// i counts curly brackets so we know when to parse the json object. { = 0x7b, } = 0x7d
			int i = 0;
			// now listen to socket
			int c;
			Log.i(TAG, "Setting up Jackson..");
			final ObjectMapper mapper = new ObjectMapper();
			final JsonFactory jf = mapper.getJsonFactory();
			final JsonParser jp = jf.createJsonParser(socket.getInputStream());
			int n = 0;
			Log.i(TAG, "Reading using Jackson: ");
			JsonNode node = null;
			while ((node = mapper.readTree(jp)) != null) {
				Log.i(TAG, "READ: " + node.toString());
			}
/*			while ((c = in.read()) != -1) {
				if (c == 0x7b) {
					i++;
					if (!started) {
						s = System.currentTimeMillis();
					}
					started = true;
				}
				if (c == 0x7d) {
					i--;
				}
				if (started) { // don't append to string buffer unless "{" is received
					sb.append((char)c);
				}
				
				if (sb.length() > 0 && i == 0) {
					Log.d(TAG, "RESPONSE: " + (sb.length() > PRINT_MAX_CHARS ? sb.toString().substring(0, PRINT_MAX_CHARS) : sb.toString()));
					Log.i(TAG, "Read " + sb.length() + " bytes in " + (System.currentTimeMillis() - s) + "ms.");
					notifyClients(sb.toString());
					
					// reset string buffer
					sb = new StringBuffer();
					started = false;
				}
			}
			in.close();*/
			mOut.close();
			Log.i(TAG, "TCP socket closed.");

		} catch (IOException e) {
			Log.e(TAG, "I/O error while reading: " + e.getMessage(), e);
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
				if (in != null) {
					in.close();
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
		Log.i(TAG, "Bound to new client.");
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
	 * Sends the received JSON-data to all connected clients.
	 * @param data
	 */
	private void notifyClients(JsonNode node) {
		Log.i(TAG, "Notifying " + mClients.size() + " clients.");
		for (int i = mClients.size() - 1; i >= 0; i--) {
			try {
				final Bundle b = new Bundle();
				b.putSerializable(EXTRA_JSON_DATA, node);
				b.put(EXTRA_JSON_DATA, data);
				Message msg = Message.obtain(null, MSG_RECEIVE_JSON_DATA);
				msg.setData(b);
				mClients.get(i).send(msg);

			} catch (RemoteException e) {
				Log.e(TAG, "Cannot send data to client: " + e.getMessage(), e);
				// The client is dead. Remove it from the list; we are going
				// through the list from back to front so this is safe to do
				// inside the loop.
				mClients.remove(i);
				stopSelf();
			}
		}
	}
	
	private void notifyError(int code, String message) {
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
				
			} catch (RemoteException e) {
				Log.e(TAG, "Cannot send errors to client: " + e.getMessage(), e);
				// The client is dead. Remove it from the list; we are going
				// through the list from back to front so this is safe to do
				// inside the loop.
				mClients.remove(i);
				stopSelf();
			}
		}
	}
	
	private void writeSocket(String data) {
		Log.d(TAG, "Sending data to server.");
		Log.d(TAG, "REQUEST: " + data);
		try {
			mOut.write(data);
			mOut.flush();
		} catch (IOException e) {
			Log.e(TAG, "Error writing to socket: " + e.getMessage(), e);
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
			case MSG_SEND_JSON_DATA:
				final String data = (String)msg.obj;
				if (mOut != null) {
					writeSocket(data);
				} else {
					mPostData.add(data);
				}
			default:
				super.handleMessage(msg);
			}
		}
	}

}
