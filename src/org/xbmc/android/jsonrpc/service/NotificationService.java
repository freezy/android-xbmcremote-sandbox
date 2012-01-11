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
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
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

	public static final int STATUS_RUNNING = 0x1;
	public static final int STATUS_JSON_RESPONSE = 0x2;
	public static final int STATUS_ERROR = 0x3;
	public static final int STATUS_RESTARTING = 0x4;
	
	public NotificationService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		final ResultReceiver receiver = intent.getParcelableExtra(EXTRA_STATUS_RECEIVER);
		if (receiver != null) {
			receiver.send(STATUS_RUNNING, Bundle.EMPTY);
		}
		
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
					if (receiver != null) {
						Log.i(TAG, "Sending " + sb.length() + " bytes of JSON data to receiver.");
						// send to receiver
						final Bundle bundle = new Bundle();
						bundle.putString(EXTRA_JSON_DATA, sb.toString());
						receiver.send(STATUS_JSON_RESPONSE, bundle);
					}
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

}
