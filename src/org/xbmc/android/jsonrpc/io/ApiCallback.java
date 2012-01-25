package org.xbmc.android.jsonrpc.io;

import org.xbmc.android.jsonrpc.api.AbstractCall;

public interface ApiCallback<T> {
	
	public abstract void onResponse(AbstractCall<T> apiCall);
	public abstract void onError(int code, String message) ;
}
