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

public final class ConfigurationModel {
	/**
	 * Configuration.Notifications
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Notifications extends AbstractModel {
		public final static String API_TYPE = "Configuration.Notifications";
		// field names
		public static final String AUDIOLIBRARY = "audiolibrary";
		public static final String GUI = "gui";
		public static final String OTHER = "other";
		public static final String PLAYER = "player";
		public static final String SYSTEM = "system";
		public static final String VIDEOLIBRARY = "videolibrary";
		// class members
		public final boolean audiolibrary;
		public final boolean gui;
		public final boolean other;
		public final boolean player;
		public final boolean system;
		public final boolean videolibrary;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a Notifications object
		 */
		public Notifications(JSONObject obj) throws JSONException {
			mType = API_TYPE;
			audiolibrary = obj.getBoolean(AUDIOLIBRARY);
			gui = obj.getBoolean(GUI);
			other = obj.getBoolean(OTHER);
			player = obj.getBoolean(PLAYER);
			system = obj.getBoolean(SYSTEM);
			videolibrary = obj.getBoolean(VIDEOLIBRARY);
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param audiolibrary 
		 * @param gui 
		 * @param other 
		 * @param player 
		 * @param system 
		 * @param videolibrary 
		 */
		public Notifications(boolean audiolibrary, boolean gui, boolean other, boolean player, boolean system, boolean videolibrary) {
			this.audiolibrary = audiolibrary;
			this.gui = gui;
			this.other = other;
			this.player = player;
			this.system = system;
			this.videolibrary = videolibrary;
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(AUDIOLIBRARY, audiolibrary);
			obj.put(GUI, gui);
			obj.put(OTHER, other);
			obj.put(PLAYER, player);
			obj.put(SYSTEM, system);
			obj.put(VIDEOLIBRARY, videolibrary);
			return obj;
		}
		/**
		 * Extracts a list of {@link ConfigurationModel.Notifications} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<ConfigurationModel.Notifications> getConfigurationModelNotificationsList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<ConfigurationModel.Notifications> l = new ArrayList<ConfigurationModel.Notifications>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new ConfigurationModel.Notifications(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<ConfigurationModel.Notifications>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeValue(audiolibrary);
			parcel.writeValue(gui);
			parcel.writeValue(other);
			parcel.writeValue(player);
			parcel.writeValue(system);
			parcel.writeValue(videolibrary);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		* Construct via parcel
		*/
		protected Notifications(Parcel parcel) {
			audiolibrary = parcel.readInt() == 1;
			gui = parcel.readInt() == 1;
			other = parcel.readInt() == 1;
			player = parcel.readInt() == 1;
			system = parcel.readInt() == 1;
			videolibrary = parcel.readInt() == 1;
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Notifications> CREATOR = new Parcelable.Creator<Notifications>() {
			@Override
			public Notifications createFromParcel(Parcel parcel) {
				return new Notifications(parcel);
			}
			@Override
			public Notifications[] newArray(int n) {
				return new Notifications[n];
			}
		};
	}
}