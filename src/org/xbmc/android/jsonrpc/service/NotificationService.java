package org.xbmc.android.jsonrpc.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * A service which connects to JSON-RPC's TCP API and listens for events to
 * arrive.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public class NotificationService extends IntentService {

	public final static String TAG = NotificationService.class.getSimpleName();

	public NotificationService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.i(TAG, "Starting TCP client...");
		Socket socket = null;
		BufferedReader in = null;

		try {
			socket = new Socket(InetAddress.getByName("192.168.0.100"), 9090);
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
		StringBuffer sb = new StringBuffer();
		try {
			Log.i(TAG, "Listening on TCP socket..");
			int c;
			while ((c = in.read()) != -1) {
				if (c == 123) {
					i++;
				}
				if (c == 125) {
					i--;
				}
				sb.append((char)c);
				
				if (sb.length() > 0 && i == 0) {
					Log.i(TAG, "Object: " + sb.toString());
					sb = new StringBuffer();
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

}
