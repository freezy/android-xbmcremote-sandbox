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

package org.xbmc.android.jsonrpc.notification;

import org.json.JSONException;
import org.json.JSONObject;
import org.xbmc.android.jsonrpc.api.model.AbstractModel;

import android.util.Log;

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
public abstract class FollowupRequest<T extends AbstractModel> {
	
	private static final String TAG = FollowupRequest.class.getSimpleName();
	
	private final JSONObject mRequest;
	private final Class<T> mClass;
	
	public FollowupRequest(JSONObject request, Class<T> c) {
		mRequest = request;
		mClass = c;
	}
	
	protected abstract <U extends AbstractModel> FollowupRequest<U> onResponse(T response);

	public JSONObject getRequest() {
		return mRequest;
	}
	
	public <U extends AbstractModel> FollowupRequest<U> respond(JSONObject response) {
		try {
			T obj = mClass.newInstance();
			obj.setData(response);
			return onResponse(obj);
		} catch (JSONException e) {
			Log.e(TAG, "Cannot parse JSON data: " + response + ": " + e.getMessage(), e);
		} catch (InstantiationException e) {
			Log.e(TAG, "Cannot instantiate class " + mClass.getSimpleName() + ": " + e.getMessage(), e);
		} catch (IllegalAccessException e) {
			Log.e(TAG, "Illegal access while instantiating class " + mClass.getSimpleName() + ": " + e.getMessage(), e);
		}
		return null;
	}
	
	public String getId() {
		try {
			return mRequest.getString("id");
		} catch (JSONException e) {
			Log.e(TAG, "Cannot get \"id\" field from request: " + mRequest, e);
		}
		return null;
	}
}