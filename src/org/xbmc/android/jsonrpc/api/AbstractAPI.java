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

package org.xbmc.android.jsonrpc.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

abstract class AbstractAPI {
	
	private static final String PARAMS = "params";
	
	protected abstract String getPrefix();
	
	protected JSONObject createRequest(String methodName) throws JSONException {
		final JSONObject request = new JSONObject();
		request.put("jsonrpc", "2.0");
		request.put("id", System.currentTimeMillis());
		request.put("method", getPrefix() + methodName);
		return request;
	}
	
	protected JSONObject getParameters(JSONObject request) throws JSONException {
		if (request.has(PARAMS)) {
			return request.getJSONObject(PARAMS);
		} else {
			final JSONObject parameters = new JSONObject();
			request.put(PARAMS, parameters);
			return parameters;
		}
	}
	
	protected static JSONArray toJSONArray(String[] items) {
		final JSONArray array = new JSONArray();
		if (items != null && items.length > 0) {
			for (int i = 0; i < items.length; i++) {
				array.put(items[i]);
			}
		}
		return array;
	}
}
