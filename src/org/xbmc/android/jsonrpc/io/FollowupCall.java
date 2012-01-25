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

import org.codehaus.jackson.node.ObjectNode;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.api.AbstractModel;

/**
 * A request posted immediately after receiving a notification, in order to 
 * query more information.
 * <p>
 * If a observer callback returns a class implementing this, it will trigger
 * a request on the JSON-RPC TCP API and deliver the result.
 * 
 * @author freezy <freezy@xbmc.org>
 * @param <T> Response format of the API call
 */
public abstract class FollowupCall<T extends AbstractModel> {
	
//	private static final String TAG = FollowupCall.class.getSimpleName();
	
	private final AbstractCall<T> mApiCall;
	
	public FollowupCall(AbstractCall<T> apiCall) {
		mApiCall = apiCall;
	}
	
	protected abstract <U extends AbstractModel> FollowupCall<U> onResponse(T response);
	
	public <U extends AbstractModel> FollowupCall<U> respond(ObjectNode response) {
		mApiCall.setResponse(response);
		return onResponse(mApiCall.getResult());
	}
	
	public ObjectNode getRequest() {
		return mApiCall.getRequest();
	}

	public AbstractCall<T> getApiCall() {
		return mApiCall;
	}
	
	public String getId() {
		return mApiCall.getId();
	}
}