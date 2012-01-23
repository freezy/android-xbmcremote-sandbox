package org.xbmc.android.jsonrpc.io;

import org.json.JSONObject;
import org.xbmc.android.jsonrpc.api.AbstractCall;

import android.util.Log;

public abstract class ApiCallback<T> {
	
	private final static String TAG = ApiCallback.class.getSimpleName();

	private AbstractCall<T> mApiCall = null;

	public void onResponse(AbstractCall<T> apiCall) throws ApiException {

	}

	public void onResponse(JSONObject response) throws ApiException {

	}
	
	public abstract void onError(int code, String message) ;

	public boolean doDeserialize() {
		return true;
	}

	public void setResponse(JSONObject response) {
		try {
			if (!doDeserialize()) {
				onResponse(response);
			} else {
				final AbstractCall<T> apiCall = mApiCall; 
				if (apiCall != null) {
					apiCall.setResponse(response);
					onResponse(apiCall);
				} else {
					Log.w(TAG, "Response type is set to de-serialize, but no API call object for deserializing found.");
				}
			}
		} catch (ApiException e) {
			Log.e(TAG, "Error returning response to caller: " + e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void setCall(AbstractCall<T> apiCall) {
		mApiCall = apiCall;
	}

	public AbstractCall<T> getCall() {
		return mApiCall;
	}
}
