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

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xbmc.android.jsonrpc.api.AbstractModel;

public final class GlobalModel {
	/**
	 * Configuration
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Configuration extends AbstractModel {
		public final static String API_TYPE = "Configuration";
		// field names
		public static final String NOTIFICATIONS = "notifications";
		// class members
		public final ConfigurationModel.Notifications notifications;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a Configuration object
		 */
		public Configuration(JSONObject obj) throws JSONException {
			mType = API_TYPE;
			notifications = new ConfigurationModel.Notifications(obj.getJSONObject(NOTIFICATIONS));
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param notifications 
		 */
		public Configuration(ConfigurationModel.Notifications notifications) {
			this.notifications = notifications;
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(NOTIFICATIONS, notifications.toJSONObject());
			return obj;
		}
		/**
		 * Extracts a list of {@link GlobalModel.Configuration} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<GlobalModel.Configuration> getGlobalModelConfigurationList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<GlobalModel.Configuration> l = new ArrayList<GlobalModel.Configuration>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new GlobalModel.Configuration(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<GlobalModel.Configuration>(0);
		}
	}
	/**
	 * Global.Time
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Time extends AbstractModel {
		public final static String API_TYPE = "Global.Time";
		// field names
		public static final String HOURS = "hours";
		public static final String MILLISECONDS = "milliseconds";
		public static final String MINUTES = "minutes";
		public static final String SECONDS = "seconds";
		// class members
		public final int hours;
		public final int milliseconds;
		public final int minutes;
		public final int seconds;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a Time object
		 */
		public Time(JSONObject obj) throws JSONException {
			mType = API_TYPE;
			hours = obj.getInt(HOURS);
			milliseconds = obj.getInt(MILLISECONDS);
			minutes = obj.getInt(MINUTES);
			seconds = obj.getInt(SECONDS);
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param hours 
		 * @param milliseconds 
		 * @param minutes 
		 * @param seconds 
		 */
		public Time(int hours, int milliseconds, int minutes, int seconds) {
			this.hours = hours;
			this.milliseconds = milliseconds;
			this.minutes = minutes;
			this.seconds = seconds;
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(HOURS, hours);
			obj.put(MILLISECONDS, milliseconds);
			obj.put(MINUTES, minutes);
			obj.put(SECONDS, seconds);
			return obj;
		}
		/**
		 * Extracts a list of {@link GlobalModel.Time} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<GlobalModel.Time> getGlobalModelTimeList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<GlobalModel.Time> l = new ArrayList<GlobalModel.Time>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new GlobalModel.Time(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<GlobalModel.Time>(0);
		}
	}
}