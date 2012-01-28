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

package org.xbmc.android.jsonrpc.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.codehaus.jackson.JsonNode;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.api.AbstractModel;
import org.xbmc.android.jsonrpc.notification.PlayerObserver;
import org.xbmc.android.jsonrpc.service.ConnectionService;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;

/**
 * Provides simple access to XBMC's JSON-RPC API.
 * <p/>
 * It is used for two things:
 * <ol><li>Query JSON-API via persistent TCP socket or HTTP.</li>
 *     <li>Subscribe to notification events from XBMC.</li></ol>
 * 
 * The TCP connection is managed by {@link ConnectionService}. The manager uses
 * a {@link Messenger} to communicate with the service using a {@link Handler}
 * on both sides (see {@link IncomingHandler}).
 * <p/>
 * 
 * <h3>Serialization</h3>
 * Since we're dealing with a service, objects sent to and received by the 
 * service must be either native types or {@link Parcelable}. For this reason,
 * our entire JSON-RPC library implements {@link Parcelable}. That includes
 * all classes extending {@link AbstractCall} as well as {@link AbstractModel}.
 * <p/>
 * Once the service receives the API call object, it queries XBMC with the 
 * given JSON data and uses the Jackson parser to serialize the response
 * directly into a {@link JsonNode}. Since <tt>JsonNode</tt> is not parcelable,
 * the service directly converts it into our object model using the API call
 * object. The updated API call object is then sent back to 
 * <tt>ConnectionManager</tt>.
 * 
 * <h3>Synchronization</h3>
 * When syncing the local database we want to avoid the parcelization happening
 * when sending the response back from <tt>ConnectionService</tt> to
 * <tt>ConnectionManager</tt>. Therefore, <tt>call()</tt> can additionally 
 * provide a {@link JsonHandler}, which will synchronize the local DB and only
 * respond with a status code instead of the whole response (TBD).
 * 
 * <h3>Notifications</h3>
 * Every instance of {@link ConnectionManager} appears as a client on the 
 * service's side. Upon reception of a notification, the service announces all
 * connected clients. If {@link ConnectionManager} has any registered 
 * observers, they will be notified, otherwise the notification is dropped.
 * 
 * <h3>Follow-ups</h3>
 * When receiving a notification, sometimes more information is required. By
 * returning a  {@link FollowupCall} object, which is wrapper for a new {@link
 * AbstractCall} object, it is possible to immediately post another request
 * without having to pass through the manager.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class ConnectionManager {
	
	private static final String TAG = ConnectionManager.class.getSimpleName();
	
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
	 * List of observers listening to notifications.
	 */
	private final ArrayList<NotificationObserver> mObservers = new ArrayList<NotificationObserver>();
	/**
	 * List of currently processing API calls with handler. Key is the ID of the API call.
	 */
	private final HashMap<String, HandlerCallback> mHandlerCallbacks = new HashMap<String, HandlerCallback>();
	/**
	 * Since we can't return the de-serialized object from the service, put the
	 * response back into the received one and return the received one.
	 */
	private final HashMap<String, CallRequest<?>> mCallRequests = new HashMap<String, CallRequest<?>>();
	/**
	 * List of follow-ups
	 */
//	private final HashMap<String, FollowupCall<?>> mFollowups = new HashMap<String, FollowupCall<?>>();
	/**
	 * When posting request data and the service isn't started yet, we need to 
	 * reschedule the post until the service is available. This list contains
	 * the requests that are to sent upon service startup. 
	 */
	private final LinkedList<AbstractCall<?>> mPendingCalls = new LinkedList<AbstractCall<?>>();
	private final HashMap<String, JsonHandler> mPendingHandlers = new HashMap<String, JsonHandler>();
	
	/**
	 * Class constructor.
	 * @param c Needed if the service needs to be started
	 */
	public ConnectionManager(Context c) {
		mContext = c;
	}
	
	/**
	 * Executes a JSON-RPC request.
	 * @param call Call to execute
	 * @param callback 
	 * @return
	 */
	public <T> ConnectionManager call(AbstractCall<T> call, ApiCallback<T> callback) {
		// start service if not yet started
		bindService();
		mCallRequests.put(call.getId(), new CallRequest<T>(call, callback));
		sendCall(call);
		return this;
	}
	
	public ConnectionManager call(AbstractCall<?> call, JsonHandler handler, HandlerCallback callback) {
		// start service if not yet started
		bindService();
		mHandlerCallbacks.put(call.getId(), callback);
		sendCall(call, handler);
		return this;
	}
	
	/**
	 * Adds a new notification observer.
	 * @param observer New observer
	 * @return Class instance
	 */
	public ConnectionManager registerObserver(NotificationObserver observer) {
		// start service if not yet started
		bindService();
		mObservers.add(observer);
		return this;
	}
	
	/**
	 * Removes a previously added observer.
	 * @param observer Observer to remove
	 * @return Class instance
	 */
	public ConnectionManager unregisterObserver(NotificationObserver observer) {
		final ArrayList<NotificationObserver> observers = mObservers;
		observers.remove(observer);
		// stop service if no more observers.
		if (observers.isEmpty()) {
			unbindService();
		}
		return this;
	}	
	
	/**
	 * Binds the connection to the notification service if not yet bound.
	 */
	private void bindService() {
		// start service if no observer and no api calls.
		if (!mIsBound) {
			Log.i(TAG, "Starting and binding service...");
			mContext.startService(new Intent(mContext, ConnectionService.class));
			mContext.bindService(new Intent(mContext, ConnectionService.class), mConnection, Context.BIND_AUTO_CREATE);
			mIsBound = true;
		}		
	}
	
	/**
	 * Posts a new request to the TCP 
	 * @param data
	 */
	private void sendCall(AbstractCall<?> apiCall) {
		if (mService != null) {
			try {
				final Message msg = Message.obtain(null, ConnectionService.MSG_SEND_APICALL);
				final Bundle data = new Bundle();
				data.putParcelable(ConnectionService.EXTRA_APICALL, apiCall);
				msg.setData(data);
				msg.replyTo = mMessenger;
				mService.send(msg);
				Log.i(TAG, "Posted API call service (with callback).");
			} catch (RemoteException e) {
				Log.e(TAG, "Error posting message to service: " + e.getMessage(), e);
			}
		} else {
			// service not yet started, saving data:
			Log.i(TAG, "Saving post data for later.");
			mPendingCalls.add(apiCall);
		}
	}
	
	private void sendCall(AbstractCall<?> apiCall, JsonHandler handler) {
		if (mService != null) {
			try {
				final Message msg = Message. obtain(null, ConnectionService.MSG_SEND_HANDLED_APICALL);
				final Bundle data = new Bundle();
				data.putParcelable(ConnectionService.EXTRA_APICALL, apiCall);
				data.putParcelable(ConnectionService.EXTRA_HANDLER, handler);
				msg.setData(data);
				msg.replyTo = mMessenger;
				mService.send(msg);
				Log.i(TAG, "Posted handled API call service.");
			} catch (RemoteException e) {
				Log.e(TAG, "Error posting message to service: " + e.getMessage(), e);
			}
		} else {
			// service not yet started, saving data:
			Log.i(TAG, "Saving post data for later.");
			mPendingCalls.add(apiCall);
			mPendingHandlers.put(apiCall.getId(), handler);
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
					final Message msg = Message.obtain(null, ConnectionService.MSG_UNREGISTER_CLIENT);
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
			Log.i(TAG, "Connected to service.");
			try {
				final Message msg = Message.obtain(null, ConnectionService.MSG_REGISTER_CLIENT);
				msg.replyTo = mMessenger;
				mService.send(msg);
				
			} catch (RemoteException e) {
				Log.e(TAG, "Error registering client: " + e.getMessage(), e);
				// In this case the service has crashed before we could even do
				// anything with it
			}
			// now check if there are lost requests:
			final LinkedList<AbstractCall<?>> calls = mPendingCalls;
			while (!calls.isEmpty()) {
				AbstractCall<?> call = calls.poll();
				if (mPendingHandlers.containsKey(call.getId())) {
					Log.d(TAG, "Posting pending handled call " + call.getName() + "...");
					final JsonHandler handler = mPendingHandlers.get(call.getId());
					sendCall(call, handler);
					mPendingHandlers.remove(call.getId());
				} else {
					Log.d(TAG, "Posting pending call " + call.getName() + " with callback...");
					sendCall(call);
				}
			}
		}

		public void onServiceDisconnected(ComponentName className) {
			// This is called when the connection with the service has been
			// unexpectedly disconnected - process crashed.
			mService = null;
			Log.i(TAG, "Service disconnected.");
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
			Log.i(TAG, "Got message: " + msg.what);
			final HashMap<String, CallRequest<?>> callrequests = mCallRequests;
			final HashMap<String, HandlerCallback> handlercallbacks = mHandlerCallbacks;
			switch (msg.what) {
				
				// fully updated API call object
				case ConnectionService.MSG_RECEIVE_APICALL: {
					final AbstractCall<?> returnedApiCall = msg.getData().getParcelable(ConnectionService.EXTRA_APICALL);
					if (returnedApiCall != null) {
						if (callrequests.containsKey(returnedApiCall.getId())) {
							final CallRequest<?> callrequest = callrequests.get(returnedApiCall.getId());
							callrequest.update(returnedApiCall);
							callrequest.respond();
							Log.d(TAG, "Callback for " + returnedApiCall.getName() + " sent back to caller.");
						} else {
							Log.w(TAG, "Unknown ID " + returnedApiCall.getId() + " for " + returnedApiCall.getName() + ", dropping.");
						}
					} else {
						Log.e(TAG, "Error retrieving API call object from bundle.");
					}
					break;
				}
					
				// status code after handled api call
				case ConnectionService.MSG_RECEIVE_HANDLED_APICALL: {
					final Bundle b = msg.getData();
					final String id = b.getString(ConnectionService.EXTRA_CALLID);
					if (handlercallbacks.containsKey(id)) {
						handlercallbacks.get(id).onFinish(b.getInt(ConnectionService.EXTRA_STATUS));
					} else {
						Log.w(TAG, "Unknown ID " + id + " for handled callback, not notifying caller.");
					}
					break;
				}
					
				// notification
				case ConnectionService.MSG_RECEIVE_NOTIFICATION: {
					// TODO handle notifications
					break;
				}
					
				// shit happened
				case ConnectionService.MSG_ERROR: {
					final Bundle b = msg.getData();
					final String message = b.getString(ConnectionService.EXTRA_ERROR_MESSAGE);
					final int code = b.getInt(ConnectionService.EXTRA_ERROR_CODE);
					final String id = b.getString(ConnectionService.EXTRA_CALLID);
					
					if (id != null && mHandlerCallbacks.containsKey(id)) {
						// if ID given and handler call back, announce to handler callback.
						Log.e(TAG, "Error, notifying one handler callback.");
						mHandlerCallbacks.get(id).onFinish(code);
					} else if (id != null && mCallRequests.containsKey(id)) {
						// if ID given and api call back, announce error.
						Log.e(TAG, "Error, notifying one API callback.");
						mCallRequests.get(id).error(code, message);
					} else {
						// otherwise, announce to all clients.
						Log.e(TAG, "Error, notifying everybody.");
						for (HandlerCallback handlerCallback : mHandlerCallbacks.values()) {
							handlerCallback.onFinish(code);
						}
						mHandlerCallbacks.clear();
						for (CallRequest<?> callreq : mCallRequests.values()) {
							callreq.error(code, message);
						}
						mCallRequests.clear();
					}
					break;
				}
				default:
					super.handleMessage(msg);
			}
		}
	}
	
	/**
	 * A call request bundles an API call and its callback of the same type.
	 *
	 * @author freezy <freezy@xbmc.org>
	 */
	private static class CallRequest<T> {
		private final AbstractCall<T> mCall;
		private final ApiCallback<T> mCallback;
		public CallRequest(AbstractCall<T> call, ApiCallback<T> callback) {
			this.mCall = call;
			this.mCallback = callback;
		}
		public void update(AbstractCall<?> call) {
			mCall.copyResponse(call);
		}
		public void respond() {
			mCallback.onResponse(mCall);
		}
		public void error(int code, String message) {
			mCallback.onError(code, message);
		}
	}
	
	/**
	 * Observer interface that handles arriving notifications.
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
	 * When providing a {@link JsonHandler} to an API call, this interface
	 * will inform the caller when processing has finished.
	 *
	 * @author freezy <freezy@xbmc.org>
	 */
	public static interface HandlerCallback {
		/**
		 * Processing has finished.
		 * @param code Either {@link ConnectionService#RESULT_SUCCESS} for success or <tt>ERROR_*</tt> when something went wrong.
		 */
		public void onFinish(int code);
	}

}
