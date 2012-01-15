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
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.xbmc.android.jsonrpc.api.model.AbstractModel;
import org.xbmc.android.jsonrpc.notification.FollowupCall;
import org.xbmc.android.jsonrpc.notification.PlayerEvent;
import org.xbmc.android.jsonrpc.notification.PlayerObserver;
import org.xbmc.android.jsonrpc.notification.SystemEvent;
import org.xbmc.android.jsonrpc.service.NotificationService;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

/**
 * Manages the Notification service.
 * <p>
 * Register your observers here.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public class NotificationManager {

	private static final String TAG = NotificationManager.class.getSimpleName();
	
	/**
	 * Keeps track of the observers.
	 */
	private final ArrayList<NotificationObserver> mNotificationObservers = new ArrayList<NotificationManager.NotificationObserver>();
	private final HashMap<String, FollowupCall<?>> mFollowups = new HashMap<String, FollowupCall<?>>();

	/**
	 * Reference to context
	 */
	private final Context mContext;
	
	/**
	 * True if bound to service, false otherwise.
	 */
	boolean mIsBound;
	
	/**
	 * The reference through which we receive messages from the service
	 */
	private final Messenger mMessenger = new Messenger(new IncomingHandler());
	
	/**
	 * The reference through which we send messages to the service
	 */
	private Messenger mService = null;
	
	/**
	 * Class constructor
	 * @param c Application context
	 */
	public NotificationManager(Context c) {
		mContext = c;
	}
	
	/**
	 * Converts a JSON object to a POJO (plain simple java object) of our 
	 * model.
	 * 
	 * @param event The JSON object
	 * @return
	 */
	private void parseAndNotify(JSONObject event) {
		
		try {
			
			// if "id" is supplied that means we're getting a response to a
			// follow-up request, not a notification.
			if (event.has("id")) {
				final String id = event.getString("id");
				final HashMap<String, FollowupCall<?>> followups = mFollowups;
				if (followups.containsKey(id)) {
					Log.i(TAG, "Received follow-up response.");
					handleFollowUp(followups.get(id).respond(event.getJSONObject("result")));
					followups.remove(id);
				} else {
					Log.w(TAG, "Got response with unknown id " + id + ".");
				}
				
			// parse notification
			} else {
				final String method = event.getString("method");
				final JSONObject params = event.getJSONObject("params");
				
				final ArrayList<NotificationObserver> observers = mNotificationObservers;
				if (method.equals(PlayerEvent.Play.METHOD)) {
					final PlayerEvent.Play notification = new PlayerEvent.Play(params);
					for (NotificationObserver observer : observers) {
						handleFollowUp(observer.getPlayerObserver().onPlay(notification));
					}
				} else if (method.equals(PlayerEvent.Pause.METHOD)) {
					final PlayerEvent.Pause notification = new PlayerEvent.Pause(params);
					for (NotificationObserver observer : observers) {
						handleFollowUp(observer.getPlayerObserver().onPause(notification));
					}
				} else if (method.equals(PlayerEvent.Stop.METHOD)) {
					final PlayerEvent.Stop notification = new PlayerEvent.Stop(params);
					for (NotificationObserver observer : observers) {
						handleFollowUp(observer.getPlayerObserver().onStop(notification));
					}
				} else if (method.equals(PlayerEvent.SpeedChanged.METHOD)) {
					final PlayerEvent.SpeedChanged notification = new PlayerEvent.SpeedChanged(params);
					for (NotificationObserver observer : observers) {
						handleFollowUp(observer.getPlayerObserver().onSpeedChanged(notification));
					}
				} else if (method.equals(PlayerEvent.Seek.METHOD)) {
					final PlayerEvent.Seek notification = new PlayerEvent.Seek(params);
					for (NotificationObserver observer : observers) {
						handleFollowUp(observer.getPlayerObserver().onSeek(notification));
					}
				} else if (method.equals(SystemEvent.Quit.METHOD)) {
					new SystemEvent.Quit(params);
				} else if (method.equals(SystemEvent.Restart.METHOD)) {
					new SystemEvent.Restart(params);
				} else if (method.equals(SystemEvent.Wake.METHOD)) {
					new SystemEvent.Wake(params);
				} else if (method.equals(SystemEvent.LowBattery.METHOD)) {
					new SystemEvent.LowBattery(params);
				} 
			}
		} catch (JSONException e) {
			Log.e(TAG, "Error parsing event, returning null (" + e.getMessage() + ")", e);
		}
	}
	
	/**
	 * If not null, save the follow-up request and dump it to the seocket.
	 * 
	 * @param followUp Received follow-up request or null 
	 */
	private <T extends AbstractModel> void handleFollowUp(FollowupCall<T> followUp) {
		if (followUp != null) {
			mFollowups.put(followUp.getId(), followUp);
			postData(followUp.getRequest().toString() + "\n");
		}
	}
	
	/**
	 * Adds a new observer.
	 * 
	 * @param observer New observer
	 * @return Class instance
	 */
	public NotificationManager registerObserver(NotificationObserver observer) {
		final ArrayList<NotificationObserver> observers = mNotificationObservers;

		// start service if no observer.
		if (observers.isEmpty()) {
			mContext.startService(new Intent(mContext, NotificationService.class));
			bindService();
		}
		observers.add(observer);
		return this;
	}
	
	/**
	 * Removes a previously added observer.
	 * 
	 * @param observer Observer to remove
	 * @return Class instance
	 */
	public NotificationManager unregisterObserver(NotificationObserver observer) {
		final ArrayList<NotificationObserver> observers = mNotificationObservers;
		observers.remove(observer);
		
		// stop service if no more observers.
		if (observers.isEmpty()) {
			unbindService();
		}
		return this;
	}
	
	/**
	 * Observer interface that handles arriving notifications.
	 * 
	 * @author freezy <freezy@xbmc.org>
	 */
	public static interface NotificationObserver {
		/**
		 * Handle an arriving notification in here.
		 * @param notification
		 */
		public PlayerObserver getPlayerObserver();
	}

	
	/**
	 * Binds the connection to the notification service.
	 */
	private void bindService() {
		mContext.bindService(new Intent(mContext, NotificationService.class), mConnection, Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}
	
	private void postData(String data) {
		if (mService != null) {
			try {
				final Message msg = Message.obtain(null, NotificationService.MSG_SEND_JSON_DATA, data);
				msg.replyTo = mMessenger;
				mService.send(msg);
			} catch (RemoteException e) {
				
			}
		}
	}
	
	/**
	 * Unbinds the connection from the notification service. This is done by
	 * notifying the service first and then terminating the connection.
	 */
	private void unbindService() {
		if (mIsBound) {
			// If we have received the service, and hence registered with it,
			// then now is the time to unregister.
			if (mService != null) {
				try {
					final Message msg = Message.obtain(null, NotificationService.MSG_UNREGISTER_CLIENT);
					msg.replyTo = mMessenger;
					mService.send(msg);
				} catch (RemoteException e) {
					Log.e(TAG, "Error unregistering client: " + e.getMessage(), e);
					// There is nothing special we need to do if the service has
					// crashed.
				}
			}
			// Detach our existing connection.
			mContext.unbindService(mConnection);
			mIsBound = false;
		}
	}
	
	/**
	 * Connection used to communicate with the service.
	 */
	private final ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			mService = new Messenger(service);
			Log.w(TAG, "Connected to notification service.");
			try {
				final Message msg = Message.obtain(null, NotificationService.MSG_REGISTER_CLIENT);
				msg.replyTo = mMessenger;
				mService.send(msg);
			} catch (RemoteException e) {
				Log.e(TAG, "Error registering client: " + e.getMessage(), e);
				// In this case the service has crashed before we could even do
				// anything with it
			}
		}

		public void onServiceDisconnected(ComponentName className) {
			// This is called when the connection with the service has been
			// unexpectedly disconnected - process crashed.
			mService = null;
			Log.w(TAG, "Service disconnected.");
		}
	};
	
	/**
	 * The handler from the receiving service.
	 * <p>
	 * In here we add the logic of what happens when we get messages from the 
	 * notification service.
	 * 
	 * @author freezy <freezy@xbmc.org>
	 */
	private class IncomingHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case NotificationService.MSG_RECEIVE_JSON_DATA:
					final String jsonResponse = msg.getData().getString(NotificationService.EXTRA_JSON_DATA);
					try {
						final JSONTokener tokener = new JSONTokener(jsonResponse);
						parseAndNotify((JSONObject) tokener.nextValue());
					} catch (JSONException e) {
						Log.e(TAG, "Exception parsing response: " + jsonResponse, e);
					}
					break;
				default:
					super.handleMessage(msg);
			}
		}
	}

}
