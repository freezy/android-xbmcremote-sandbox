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

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Some methods return undefined results, this is their response container.
 * <p/>
 * For v3, the methods returning undefined results are:
 * <ul><li><tt>JSONRPC.Introspect</tt></li>
 *     <li><tt>XBMC.GetInfoBooleans</tt></li>
 *     <li><tt>XBMC.GetInfoLabels</tt></li></ul>
 * 
 * It's up to the application to correctly parse those response types, if
 * necessary.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public class UndefinedResult implements Parcelable {
	
	private final static String TAG = UndefinedResult.class.getSimpleName();
	private final static ObjectMapper OM = new ObjectMapper();
	
	private final JsonNode mResponse;
	
	/**
	 * Class constructor.
	 * @param obj Root node of the response object.
	 */
	public UndefinedResult(JsonNode node) {
		mResponse = node;
	}
	
	/**
	 * Returns the root response object.
	 * 
	 * @return Root object of the response.
	 */
	public JsonNode getResponse() {
		return mResponse;
	}
	
	/**
	 * Returns the <tt>result</tt> node of the response object.
	 * @return The <tt>result</tt> node of the response object.
	 */
	public ObjectNode getResult() {
		return (ObjectNode)mResponse.get("result");
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeValue(mResponse.toString());
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	/**
	 * Class constructor via parcel
	 * @param obj Root node of the response object.
	 */
	private UndefinedResult(Parcel parcel) {
		ObjectNode response = null;
		try {
			response = (ObjectNode)OM.readTree(parcel.readString());
		} catch (JsonProcessingException e) {
			Log.e(TAG, "Error reading JSON object from parcel: " + e.getMessage(), e);
		} catch (IOException e) {
			Log.e(TAG, "I/O exception reading JSON object from parcel: " + e.getMessage(), e);
		} finally {
			mResponse = response;
		}
	}
	
	/**
	 * Generates instances of this Parcelable class from a Parcel.
	 */
	public static final Parcelable.Creator<UndefinedResult> CREATOR = new Parcelable.Creator<UndefinedResult>() {
		@Override
		public UndefinedResult createFromParcel(Parcel parcel) {
			return new UndefinedResult(parcel);
		}
		@Override
		public UndefinedResult[] newArray(int n) {
			return new UndefinedResult[n];
		}
	};
}
