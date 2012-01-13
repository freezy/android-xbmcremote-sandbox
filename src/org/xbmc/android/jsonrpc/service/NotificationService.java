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
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

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
 * 
 * @author freezy <freezy@xbmc.org>
 */
public class NotificationService extends IntentService {

	public final static String TAG = NotificationService.class.getSimpleName();
	
	public static final String EXTRA_STATUS_RECEIVER = "org.xbmc.android.jsonprc.extra.STATUS_RECEIVER";
	public static final String EXTRA_JSON_DATA = "org.xbmc.android.jsonprc.extra.JSON_DATA";
	
	private static final int SOCKET_TIMEOUT = 5000;

	public static final int STATUS_RUNNING = 0x1;
	public static final int STATUS_JSON_RESPONSE = 0x2;
	public static final int STATUS_ERROR = 0x3;
	public static final int STATUS_RESTARTING = 0x4;

	public static final int MSG_REGISTER_CLIENT = 1;
	public static final int MSG_UNREGISTER_CLIENT = 2;
	public static final int MSG_SET_JSON_DATA = 3;	
	
	/**
	 * Target we publish for clients to send messages to IncomingHandler.
	 */
	final Messenger mMessenger = new Messenger(new IncomingHandler());
	
	/**
	 * Keeps track of all current registered clients.
	 */
	ArrayList<Messenger> mClients = new ArrayList<Messenger>();
	   
	
	public NotificationService() {
		super(TAG);
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "Notification service started.");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		Log.i(TAG, "Starting TCP client...");
		Socket socket = null;
		BufferedReader in = null;

		try {
			
			final InetSocketAddress sockaddr = new InetSocketAddress("192.100.120.114", 9090);
			socket = new Socket();
			socket.setSoTimeout(0); // no timeout for reading from connection.
			socket.connect(sockaddr, SOCKET_TIMEOUT);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException e) {
			Log.e(TAG, "Unknown host: " + e.getMessage(), e);
			return;
		} catch (IOException e) {
			Log.e(TAG, "I/O error while opening: " + e.getMessage(), e);
			return;
		}
		
		// i counts curly brackets so we know when to parse the json object. { = 123, } = 125
		int i = 0;
		boolean started = false;
		StringBuffer sb = new StringBuffer();
		try {
			Log.i(TAG, "Listening on TCP socket..");
			int c;
			while ((c = in.read()) != -1) {
				if (c == 123) {
					i++;
					started = true;
				}
				if (c == 125) {
					i--;
				}
				if (started) { // don't append to string buffer unless "{" is received
					sb.append((char)c);
				}
				
				if (sb.length() > 0 && i == 0) {
					Log.i(TAG, "Sending " + sb.length() + " bytes of JSON data to clients.");
					notifyClients(sb.toString());
					
					// reset stringbuffer
					sb = new StringBuffer();
					started = false;
				}
			}
			in.close();
			Log.e(TAG, "TCP socket closed.");

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
			} catch (IOException e) {
				// do nothing.
			}
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return mMessenger.getBinder();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "Notification service destroyed.");
	}

	private void notifyClients(String data) {
		for (int i = mClients.size() - 1; i >= 0; i--) {
			try {
				Bundle b = new Bundle();
				b.putString(EXTRA_JSON_DATA, data);
				Message msg = Message.obtain(null, MSG_SET_JSON_DATA);
				msg.setData(b);
				mClients.get(i).send(msg);

			} catch (RemoteException e) {
				// The client is dead. Remove it from the list; we are going
				// through the list from back to front so this is safe to do
				// inside the loop.
				mClients.remove(i);
			}
		}
	}
	
	/**
	 * Handler of incoming messages from clients.
	 */
	private class IncomingHandler extends Handler { // 
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_REGISTER_CLIENT:
				mClients.add(msg.replyTo);
				break;
			case MSG_UNREGISTER_CLIENT:
				mClients.remove(msg.replyTo);
				break;
			default:
				super.handleMessage(msg);
			}
		}
	}

}
