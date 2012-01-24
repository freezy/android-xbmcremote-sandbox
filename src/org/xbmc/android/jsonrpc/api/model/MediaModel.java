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

package org.xbmc.android.jsonrpc.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class MediaModel {
	/**
	 * Media.Details.Base
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class BaseDetails extends ItemModel.BaseDetails {
		public final static String API_TYPE = "Media.Details.Base";
		// field names
		public static final String FANART = "fanart";
		public static final String THUMBNAIL = "thumbnail";
		// class members
		public final String fanart;
		public final String thumbnail;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a BaseDetails object
		 */
		public BaseDetails(JSONObject obj) throws JSONException {
			super(obj);
			mType = API_TYPE;
			fanart = parseString(obj, FANART);
			thumbnail = parseString(obj, THUMBNAIL);
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(FANART, fanart);
			obj.put(THUMBNAIL, thumbnail);
			return obj;
		}
		/**
		 * Extracts a list of {@link MediaModel.BaseDetails} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<MediaModel.BaseDetails> getMediaModelBaseDetailsList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<MediaModel.BaseDetails> l = new ArrayList<MediaModel.BaseDetails>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new MediaModel.BaseDetails(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<MediaModel.BaseDetails>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeValue(fanart);
			parcel.writeValue(thumbnail);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		* Construct via parcel
		*/
		protected BaseDetails(Parcel parcel) {
			super(parcel);
			fanart = parcel.readString();
			thumbnail = parcel.readString();
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<BaseDetails> CREATOR = new Parcelable.Creator<BaseDetails>() {
			@Override
			public BaseDetails createFromParcel(Parcel parcel) {
				return new BaseDetails(parcel);
			}
			@Override
			public BaseDetails[] newArray(int n) {
				return new BaseDetails[n];
			}
		};
	}
}