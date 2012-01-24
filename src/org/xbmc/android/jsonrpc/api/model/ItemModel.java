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
import org.xbmc.android.jsonrpc.api.AbstractModel;

public final class ItemModel {
	/**
	 * Item.Details.Base
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class BaseDetails extends AbstractModel {
		public final static String API_TYPE = "Item.Details.Base";
		// field names
		public static final String LABEL = "label";
		// class members
		public final String label;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a BaseDetails object
		 */
		public BaseDetails(JSONObject obj) throws JSONException {
			mType = API_TYPE;
			label = obj.getString(LABEL);
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param label 
		 */
		public BaseDetails(String label) {
			this.label = label;
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(LABEL, label);
			return obj;
		}
		/**
		 * Extracts a list of {@link ItemModel.BaseDetails} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<ItemModel.BaseDetails> getItemModelBaseDetailsList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<ItemModel.BaseDetails> l = new ArrayList<ItemModel.BaseDetails>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new ItemModel.BaseDetails(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<ItemModel.BaseDetails>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeValue(label);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		* Construct via parcel
		*/
		protected BaseDetails(Parcel parcel) {
			label = parcel.readString();
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