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

/**
 * Base class for all API classes.
 * 
 * API classes don't execute any requests, they only create the request
 * objects that are compatible with the XBMC's JSON-RPC.
 * <p>
 * For all implemented commands, run 
 * <code>
 * 	curl -i -X POST -d '{"jsonrpc": "2.0", "method": "JSONRPC.Introspect", "params": { }, "id": 1}' http://localhost:8080/jsonrpc
 * </code>
 * 
 * @see http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3
 * @author freezy <freezy@xbmc.org>
 */
abstract class AbstractAPI {
	
	private static final String PARAMS = "params";
	
	/**
	 * JSON-RPC's "namespace", such as "Application.", "AudioLibrary.", "AudioPlayer.", etc etc.
	 * We keep one subclass per namespace retrieve the prefix automatically.
	 * 
	 * @return JSON-RPC's namespace prefix, suffixed with a dot (".").
	 */
	protected abstract String getPrefix();
	
	/**
	 * Creates the root nodes of the request object.
	 * 
	 * @param methodName Name of the method
	 * @return Object containing the basic attributes of the request object
	 * @throws JSONException Shit happens..
	 */
	protected JSONObject createRequest(String methodName) throws JSONException {
		final JSONObject request = new JSONObject();
		request.put("jsonrpc", "2.0");
		request.put("id", String.valueOf(System.currentTimeMillis()));
		request.put("method", getPrefix() + methodName);
		return request;
	}
	
	/**
	 * Returns the parameters array. Use this to add any parameters.
	 * @param request
	 * @return
	 * @throws JSONException
	 */
	protected JSONObject getParameters(JSONObject request) throws JSONException {
		if (request.has(PARAMS)) {
			return request.getJSONObject(PARAMS);
		} else {
			final JSONObject parameters = new JSONObject();
			request.put(PARAMS, parameters);
			return parameters;
		}
	}
	
	/**
	 * Helper method that converts an array of Strings into a JSON array.
	 * 
	 * @param items String items to convert
	 * @return
	 */
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
