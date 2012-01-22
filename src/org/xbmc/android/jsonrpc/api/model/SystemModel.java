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

public final class SystemModel {
	public interface PropertyName {
		public final String CANSHUTDOWN = "canshutdown";
		public final String CANSUSPEND = "cansuspend";
		public final String CANHIBERNATE = "canhibernate";
		public final String CANREBOOT = "canreboot";
	}
	/**
	 * System.Property.Value
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class PropertyValue extends AbstractModel {
		public final static String API_TYPE = "System.Property.Value";
		// field names
		public static final String CANHIBERNATE = "canhibernate";
		public static final String CANREBOOT = "canreboot";
		public static final String CANSHUTDOWN = "canshutdown";
		public static final String CANSUSPEND = "cansuspend";
		// class members
		public final Boolean canhibernate;
		public final Boolean canreboot;
		public final Boolean canshutdown;
		public final Boolean cansuspend;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a PropertyValue object
		 */
		public PropertyValue(JSONObject obj) throws JSONException {
			mType = API_TYPE;
			canhibernate = parseBoolean(obj, CANHIBERNATE);
			canreboot = parseBoolean(obj, CANREBOOT);
			canshutdown = parseBoolean(obj, CANSHUTDOWN);
			cansuspend = parseBoolean(obj, CANSUSPEND);
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param canhibernate 
		 * @param canreboot 
		 * @param canshutdown 
		 * @param cansuspend 
		 */
		public PropertyValue(Boolean canhibernate, Boolean canreboot, Boolean canshutdown, Boolean cansuspend) {
			this.canhibernate = canhibernate;
			this.canreboot = canreboot;
			this.canshutdown = canshutdown;
			this.cansuspend = cansuspend;
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(CANHIBERNATE, canhibernate);
			obj.put(CANREBOOT, canreboot);
			obj.put(CANSHUTDOWN, canshutdown);
			obj.put(CANSUSPEND, cansuspend);
			return obj;
		}
		/**
		 * Extracts a list of {@link SystemModel.PropertyValue} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<SystemModel.PropertyValue> getSystemModelPropertyValueList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<SystemModel.PropertyValue> l = new ArrayList<SystemModel.PropertyValue>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new SystemModel.PropertyValue(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<SystemModel.PropertyValue>(0);
		}
	}
}