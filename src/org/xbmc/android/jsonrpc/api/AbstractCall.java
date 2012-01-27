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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import android.os.Parcel;
import android.os.Parcelable;
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
 * 	    	<ul><li>{@link #parseMany(JsonNode)} if that's the case, <b>or</b></li>
 * 	    	    <li>{@link #parseOne(JsonNode)} if a single item is returned.
 *  	    </li></ul>
 * 	    </li></ol>
 * The rest is taken care of in this abstract class.
 * <p/>
 *  
 * @author freezy <freezy@xbmc.org>
 */
public abstract class AbstractCall<T> implements Parcelable {
	
//	private static final String TAG = AbstractCall.class.getSimpleName();

	public static final String RESULT = "result";
	
	private final static Random RND = new Random(System.currentTimeMillis());
	protected final static ObjectMapper OM = new ObjectMapper();
	
	/**
	 * Name of the node containing parameters in the JSON-RPC request
	 */
	private static final String PARAMS = "params";
	
	/**
	 * Returns the name of the method.
	 * @return Full name of the method, e.g. "AudioLibrary.GetSongDetails".
	 */
	public abstract String getName();

	/**
	 * Returns true if the API method returns a list of items, false if the API
	 * method returns a single item.
	 * <p/>
	 * Depending on this value, either {@link #parseOne(JsonNode)} or 
	 * {@link #parseMany(JsonNode)} must be overridden by the sub class.
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
	public ObjectNode mRequest = OM.createObjectNode();
	
	/**
	 * The <tt>response</tt> node of the JSON response (the root node of the 
	 * response)
	 * <p/>
	 * <u>Example</u>:
	 * 	<code> { "version": { "major": 11, "minor": 0, "revision": "20111210-f1ae0b6", "tag": "alpha" } </code>
	 * @todo fix example
	 */
	protected T mResult = null;
	protected ArrayList<T> mResults = null;
	
	/**
	 * The ID of the request.
	 */
	private final String mId;
	
	/**
	 * Creates the standard structure of the JSON request.
	 * 
	 */
	protected AbstractCall() {
		final ObjectNode request = mRequest;
		
		mId = String.valueOf(RND.nextLong());
		request.put("jsonrpc", "2.0");
		request.put("id", mId);
		request.put("method", getName());
	}
	
	/**
	 * Returns the JSON request object sent to XBMC.
	 * @return Request object
	 */
	public ObjectNode getRequest() {
		return mRequest;
	}
	
	/**
	 * Sets the response object once the data has arrived.
	 * </p>
	 * This must be the root object of the response, containing the
	 * <tt>result</tt> object.
	 * @param response
	 */
	public void setResponse(JsonNode response) {
		if (returnsList()) {
			mResults = parseMany((ObjectNode)response.get(RESULT));
		} else {
			mResult = parseOne((ObjectNode)response.get(RESULT));
		}
	}
	
	@SuppressWarnings("unchecked")
	public void copyResponse(AbstractCall<?> call) {
		if (returnsList()) {
			mResults = (ArrayList<T>)call.getResults();
		} else {
			mResult = (T)call.getResult();
		}
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
		if (returnsList()) {
			return mResults.get(0);
		}
		return mResult;
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
		if (!returnsList()) {
			final ArrayList<T> results = new ArrayList<T>(1);
			results.add(mResult);
			return results;
		}
		return mResults;
	}
	
	/**
	 * Returns the generated ID of the request.
	 * @return Generated ID of the request
	 */
	public String getId() {
		return mId;
	}
	
	/**
	 * Gets the result object from a response.
	 * @param obj
	 * @return
	 */
	protected ObjectNode parseResult(ObjectNode obj) {
		return (ObjectNode)obj.get(RESULT);
	}
	
	protected ArrayNode parseResults(ObjectNode obj, String key) {
		return (ArrayNode)obj.get(key);
	}
	
	/**
	 * Parses the result if the API method returns a single item.
	 * <p/>
	 * Either this <b>or</b> {@link #parseMany(JsonNode)} must be overridden
	 * by every sub class.
	 * 
	 * @param obj The <tt>result</tt> node of the JSON response object.
	 * @return Result of the API call
	 */
	protected T parseOne(ObjectNode obj) {
		return null;
	}
	
	/**
	 * Parses the result if the API method returns a list of items.
	 * <p/>
	 * Either this <b>or</b> {@link #parseOne(JsonNode)} must be overridden
	 * by every sub class.
	 * 
	 * @param obj The <tt>result</tt> node of the JSON response object.
	 * @return Result of the API call
	 */
	protected ArrayList<T> parseMany(ObjectNode obj) {
		return null;
	}
	
	/**
	 * Adds a string parameter to the request object (only if not null).
	 * @param name Name of the parameter
	 * @param value Value of the parameter
	 */
	protected void addParameter(String name, String value) {
		if (value != null) {
			getParameters().put(name, value);
		}
	}
	
	/**
	 * Adds an integer parameter to the request object (only if not null).
	 * @param name Name of the parameter
	 * @param value Value of the parameter
	 */
	protected void addParameter(String name, Integer value) {
		if (value != null) {
			getParameters().put(name, value);
		}
	}
	
	/**
	 * Adds a boolean parameter to the request object (only if not null).
	 * @param name Name of the parameter
	 * @param value Value of the parameter
	 */
	protected void addParameter(String name, Boolean value) {
		if (value != null) {
			getParameters().put(name, value);
		}
	}
	
	protected void addParameter(String name, Double value) {
		if (value != null) {
			getParameters().put(name, value);
		}
	}
	
	protected void addParameter(String name, AbstractModel value) {
		if (value != null) {
			getParameters().put(name, value.toObjectNode());
		}
	}
	
	/**
	 * Adds an array of strings to the request object (only if not null and not empty).
	 * @param name Name of the parameter
	 * @param values String values
	 */
	protected void addParameter(String name, String[] values) {
		// don't add if nothing to add
		if (values == null || values.length == 0) {
			return;
		}
		final ArrayNode props = OM.createArrayNode();
		for (int i = 0; i < values.length; i++) {
			props.add(values[i]);
		}
		getParameters().put(name, props);
	}
	
	/**
	 * Returns the parameters array. Use this to add any parameters.
	 * @param request
	 * @return
	 */
	private ObjectNode getParameters() {
		final ObjectNode request = mRequest;
		if (request.has(PARAMS)) {
			return (ObjectNode)request.get(PARAMS);
		} else {
			final ObjectNode parameters = OM.createObjectNode();
			request.put(PARAMS, parameters);
			return parameters;
		}
	}
	
	/**
	 * Flatten this object into a Parcel.
	 * @param parcel the Parcel in which the object should be written
	 * @param flags additional flags about how the object should be written
	 */
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(mId);
		parcel.writeValue(mRequest.toString());
	}
	@Override
	public int describeContents() {
		return 0;
	}
	
	protected AbstractCall(Parcel parcel) {
		mId = parcel.readString();
		try {
			mRequest = (ObjectNode)OM.readTree(parcel.readString());
		} catch (JsonProcessingException e) {
			Log.e(getName(), "Error reading JSON object from parcel: " + e.getMessage(), e);
		} catch (IOException e) {
			Log.e(getName(), "I/O exception reading JSON object from parcel: " + e.getMessage(), e);
		}
	}
	
}
