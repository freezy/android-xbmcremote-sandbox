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
public class TcpService extends IntentService {
	
	public final static String TAG = TcpService.class.getSimpleName();

	public TcpService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Socket echoSocket = null;
		BufferedReader in = null;

		try {
			echoSocket = new Socket(InetAddress.getByName("192.168.1.54"), 9090);
			//out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
		} catch (UnknownHostException e) {
			Log.e(TAG, "Unknown host: " + e.getMessage(), e);
			return;
		} catch (IOException e) {
			Log.e(TAG, "I/O error while opening: " + e.getMessage(), e);
			return;
		}

		try {
			int c;
			while ((c = in.read()) != -1) {
				Log.i(TAG, "Char = " + String.valueOf((char)c));
			}
			in.close();
			echoSocket.close();
			
		} catch (IOException e) {
			Log.e(TAG, "I/O error while reading: " + e.getMessage(), e);
		}
	}

}
