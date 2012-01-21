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

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * Super class of all API call implementations.
 * 
 * <p/>
 * Every sub class represents an API method of XBMC's JSON-RPC API. Basically 
 * it implements two things:
 * 	<ol><li>Creation of the JSON request object sent to XBMC's JSON-RPC API</li>
 * 	    <li>Parsing of the JSON response and serialization into our model</li></ol>
 * 
 * <h3>Type</h3>
 * Every sub class is typed with the class of our model that is returned in the
 * API method. The model aims to represent the types of the JSON-RPC API. All 
 * classes of the model extend {@link AbstractModel}.
 * 
 * <h3>Lists vs. single items</h3>
 * API methods either return a single item or a list of items. We both define
 * {@link #getResult()} for a single result and {@link #getResults()} for a 
 * bunch of results. Both work independently of what actual kind of result is
 * returned by XBMC.
 * <p/>
 * The difference is *what* those methods return. If the API returns a list,
 * {@link #getResult()} will return the first item. If the API returns a single
 * item, {@link #getResults()} returns a list containing only one item.
 * <p/>
 * The subclass has therefore to implement particulary two things:
 * 	<ol><li>{@link #returnsList()} returning <tt>true</tt> or <tt>false</tt>
 *      depending on if the API returns a list or not</li>
 * 	    <li>Depending on if a list is returned:
 * 	    	<ul><li>{@link #parseMany(JSONObject)} if that's the case, <b>or</b></li>
 * 	    	    <li>{@link #parseOne(JSONObject)} if a single item is returned.
 *  	    </li></ul>
 * 	    </li></ol>
 * The rest is taken care of in this abstract class.
 * <p/>
 *  
 * @author freezy <freezy@xbmc.org>
 */
public abstract class AbstractCall<T> {
	
	private static final String TAG = AbstractCall.class.getSimpleName();
	
	/**
	 * Name of the node containing parameters in the JSON-RPC request
	 */
	private static final String PARAMS = "params";
	
	/**
	 * Returns the name of the method.
	 * @return Full name of the method, e.g. "AudioLibrary.GetSongDetails".
	 */
	protected abstract String getName();

	/**
	 * Returns true if the API method returns a list of items, false if the API
	 * method returns a single item.
	 * <p/>
	 * Depending on this value, either {@link #parseOne(JSONObject)} or 
	 * {@link #parseMany(JSONObject)} must be overridden by the sub class.
	 * 
	 * @return True if API call returns a list, false if only one item
	 */
	protected abstract boolean returnsList();
	
	/**
	 * JSON request object sent to the API
	 * 
	 * <p/>
	 * <u>Example</u>:
	 * 	<code>{"jsonrpc": "2.0", "method": "Application.GetProperties", "id": "1", "params": { "properties": [ "version" ] } }</code>
	 */
	private final JSONObject mRequest = new JSONObject();
	
	/**
	 * The <tt>result</tt> node of the JSON response.
	 * 
	 * <p/>
	 * <u>Example</u>:
	 * 	<code> { "version": { "major": 11, "minor": 0, "revision": "20111210-f1ae0b6", "tag": "alpha" } </code>
	 */
	private JSONObject mResult = new JSONObject();
	
	/**
	 * The ID of the request.
	 */
	private final String mId;
	
	/**
	 * Creates the standard structure of the JSON request.
	 * 
	 * @throws JSONException
	 */
	protected AbstractCall() throws JSONException {
		final JSONObject request = mRequest;
		mId = String.valueOf(System.currentTimeMillis());
		request.put("jsonrpc", "2.0");
		request.put("id", mId);
		request.put("method", getName());
	}
	
	/**
	 * Returns the JSON request object sent to XBMC.
	 * @return Request object
	 */
	public JSONObject getRequest() {
		return mRequest;
	}
	
	/**
	 * Sets the result object once the data has arrived.
	 * @param result
	 */
	public void setResult(JSONObject result) {
		mResult = result;
	}

	/**
	 * Returns the result as a single item.
	 * <p>
	 * If the API method returned a list, this will return the first item, 
	 * otherwise the one item returned by the API method is returned.
	 * 
	 * @return Result of the API method as a single item
	 */
	public T getResult() {
		try {
			if (returnsList()) {
				return parseMany(mResult).get(0);
			}
			return parseOne(mResult);
		} catch (JSONException e) {
			Log.e(TAG, "Error parsing JSON: " + mResult, e);
		}
		return null;
	}
	
	/**
	 * Returns the result as a list of items.
	 * <p>
	 * If the API method returned a single result, this will return a list
	 * containing the single result only, otherwise the whole list is returned.
	 * 
	 * @return Result of the API method as list
	 */
	public ArrayList<T> getResults() {
		try {
			if (!returnsList()) {
				final ArrayList<T> results = new ArrayList<T>(1);
				results.add(parseOne(mResult));
				return results;
			}
			return parseMany(mResult);
		} catch (JSONException e) {
			Log.e(TAG, "Error parsing JSON: " + mResult, e);
		}
		return new ArrayList<T>(0);
	}
	
	/**
	 * Returns the generated ID of the request.
	 * @return Generated ID of the request
	 */
	public String getId() {
		return mId;
	}
	
	/**
	 * Parses the result if the API method returns a single item.
	 * <p/>
	 * Either this <b>or</b> {@link #parseMany(JSONObject)} must be overridden
	 * by every sub class.
	 * 
	 * @param obj The <tt>result</tt> node of the JSON response object.
	 * @return Result of the API call
	 * @throws JSONException
	 */
	protected T parseOne(JSONObject obj) throws JSONException {
		return null;
	}
	
	/**
	 * Parses the result if the API method returns a list of items.
	 * <p/>
	 * Either this <b>or</b> {@link #parseOne(JSONObject)} must be overridden
	 * by every sub class.
	 * 
	 * @param obj The <tt>result</tt> node of the JSON response object.
	 * @return Result of the API call
	 * @throws JSONException
	 */
	protected ArrayList<T> parseMany(JSONObject obj) throws JSONException {
		return null;
	}
	
	/**
	 * Adds a string parameter to the request object (only if not null).
	 * @param name Name of the parameter
	 * @param value Value of the parameter
	 * @throws JSONException
	 */
	protected void addParameter(String name, String value) throws JSONException {
		if (value != null) {
			getParameters().put(name, value);
		}
	}
	
	/**
	 * Adds an integer parameter to the request object (only if not null).
	 * @param name Name of the parameter
	 * @param value Value of the parameter
	 * @throws JSONException
	 */
	protected void addParameter(String name, Integer value) throws JSONException {
		if (value != null) {
			getParameters().put(name, value);
		}
	}
	
	/**
	 * Adds a boolean parameter to the request object (only if not null).
	 * @param name Name of the parameter
	 * @param value Value of the parameter
	 * @throws JSONException
	 */
	protected void addParameter(String name, Boolean value) throws JSONException {
		if (value != null) {
			getParameters().put(name, value);
		}
	}
	
	/**
	 * Adds an array of strings to the request object (only if not null and not empty).
	 * @param name Name of the parameter
	 * @param values String values
	 * @throws JSONException
	 */
	protected void addParameter(String name, String[] values) throws JSONException {
		// don't add if nothing to add
		if (values == null || values.length == 0) {
			return;
		}
		final JSONArray props = new JSONArray();
		for (int i = 0; i < values.length; i++) {
			props.put(values[i]);
		}
		getParameters().put(name, props);
	}
	
	/**
	 * Returns the parameters array. Use this to add any parameters.
	 * @param request
	 * @return
	 * @throws JSONException
	 */
	private JSONObject getParameters() throws JSONException {
		final JSONObject request = mRequest;
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
