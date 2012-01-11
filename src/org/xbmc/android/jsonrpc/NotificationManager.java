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

package org.xbmc.android.jsonrpc;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.xbmc.android.jsonrpc.service.NotificationService;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

/**
 * Manages the Notification service.
 * 
 * Register your obervers here.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public class NotificationManager extends ResultReceiver {

	private static final String TAG = NotificationManager.class.getSimpleName();
	private static NotificationManager INSTANCE = null;
	
	private final static ArrayList<NotificationObserver> sNotificationObservers = new ArrayList<NotificationManager.NotificationObserver>(); 

	private final Context mContext;

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		Log.e(TAG, "Received notification: " + resultCode);
		switch (resultCode) {
			case NotificationService.STATUS_JSON_RESPONSE:
				final String jsonResponse = resultData.getString(NotificationService.EXTRA_JSON_DATA);
				try {
					final JSONTokener tokener = new JSONTokener(jsonResponse);
					final JSONObject notification = (JSONObject)tokener.nextValue();
					notifyHandlers(notification);
				} catch (JSONException e) {
					Log.e(TAG, "Exception parsing response: " + jsonResponse, e);
				}
				break;
		}
	}
	
	private void notifyHandlers(JSONObject notification) {
		final ArrayList<NotificationObserver> notificationHandlers = sNotificationObservers;
		for (NotificationObserver handler : notificationHandlers) {
			handler.handleNotification(notification);
		}
	}
	
	public NotificationManager registerHandler(NotificationObserver handler) {
		final ArrayList<NotificationObserver> notificationHandlers = sNotificationObservers;

		// start service if no handler.
		if (notificationHandlers.isEmpty()) {
			final Intent intent = new Intent(Intent.ACTION_SYNC, null, mContext, NotificationService.class);
			intent.putExtra(NotificationService.EXTRA_STATUS_RECEIVER, this);
			mContext.startService(intent);
		}
		notificationHandlers.add(handler);
		return this;
	}
	
	public NotificationManager unregisterHandler(NotificationObserver handler) {
		final ArrayList<NotificationObserver> notificationHandlers = sNotificationObservers;
		notificationHandlers.remove(handler);
		
		// stop service if no more handlers.
		if (notificationHandlers.isEmpty()) {
			mContext.stopService(new Intent(mContext, NotificationService.class));
		}
		return this;
	}
	
	public static interface NotificationObserver {
		public void handleNotification(JSONObject data);
	}
	
	public static NotificationManager getInstance(Context c) {
		if (INSTANCE == null) {
			INSTANCE = new NotificationManager(c);
		}
		return INSTANCE;
	}
	
	public NotificationManager() {
		super(null);
		throw new RuntimeException("Cannot call constructor directly, use getInstance().");
	}
	
	private NotificationManager(Context c) {
		super(null);
		mContext = c;
	}
}
