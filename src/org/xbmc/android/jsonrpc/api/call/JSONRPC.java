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

package org.xbmc.android.jsonrpc.api.call;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.api.AbstractModel;
import org.xbmc.android.jsonrpc.api.UndefinedResult;

public final class JSONRPC {

	private final static String PREFIX = "JSONRPC.";

	/**
	 * Enumerates all actions and descriptions
	 * <p/>
	 * API Name: <code>JSONRPC.Introspect</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Introspect extends AbstractCall<UndefinedResult> { 
		private static final String NAME = "Introspect";
		/**
		 * Enumerates all actions and descriptions
		 * @param getdescriptions 
		 * @param getmetadata 
		 * @param filterbytransport 
		 * @param filter 
		 * @throws JSONException
		 */
		public Introspect(Boolean getdescriptions, Boolean getmetadata, Boolean filterbytransport, GetreferencesIdType filter) throws JSONException {
			super();
			addParameter("getdescriptions", getdescriptions);
			addParameter("getmetadata", getmetadata);
			addParameter("filterbytransport", filterbytransport);
			addParameter("filter", filter);
		}
		@Override
		protected UndefinedResult parseOne(JSONObject obj) throws JSONException {
			return new UndefinedResult(obj);
		}
		/**
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class GetreferencesIdType extends AbstractModel {
			// field names
			public static final String GETREFERENCES = "getreferences";
			public static final String ID = "id";
			public static final String TYPE = "type";
			// class members
			/**
			 * Whether or not to print the schema for referenced types.
			 */
			public final Boolean getreferences;
			/**
			 * Name of a namespace, method or type.
			 */
			public final String id;
			/**
			 * Type of the given name.
			 * One of: <tt>method</tt>, <tt>namespace</tt>, <tt>type</tt>, <tt>notification</tt>.
			 */
			public final String type;
			/**
			 * Construct object with native values for later serialization.
			 * @param getreferences Whether or not to print the schema for referenced types 
			 * @param id Name of a namespace, method or type 
			 * @param type Type of the given name 
			 */
			public GetreferencesIdType(Boolean getreferences, String id, String type) {
				this.getreferences = getreferences;
				this.id = id;
				this.type = type;
			}
			@Override
			public JSONObject toJSONObject() throws JSONException {
				final JSONObject obj = new JSONObject();
				obj.put(GETREFERENCES, getreferences);
				obj.put(ID, id);
				obj.put(TYPE, type);
				return obj;
			}
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
	}
	/**
	 * Notify all other connected clients
	 * <p/>
	 * API Name: <code>JSONRPC.NotifyAll</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class NotifyAll extends AbstractCall<String> { 
		private static final String NAME = "NotifyAll";
		/**
		 * Notify all other connected clients
		 * @param sender 
		 * @param message 
		 * @param data 
		 * @throws JSONException
		 */
		public NotifyAll(String sender, String message, String data) throws JSONException {
			super();
			addParameter("sender", sender);
			addParameter("message", message);
			addParameter("data", data);
		}
		@Override
		protected String parseOne(JSONObject obj) throws JSONException {
			return obj.getString(RESULT);
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
	}
	/**
	 * Retrieve the clients permissions
	 * <p/>
	 * API Name: <code>JSONRPC.Permission</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Permission extends AbstractCall<Permission.PermissionResult> { 
		private static final String NAME = "Permission";
		/**
		 * Retrieve the clients permissions
		 * @throws JSONException
		 */
		public Permission() throws JSONException {
			super();
		}
		@Override
		protected Permission.PermissionResult parseOne(JSONObject obj) throws JSONException {
			return new Permission.PermissionResult(parseResult(obj));
		}
		/**
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class PermissionResult extends AbstractModel {
			// field names
			public static final String CONTROLNOTIFY = "controlnotify";
			public static final String CONTROLPLAYBACK = "controlplayback";
			public static final String CONTROLPOWER = "controlpower";
			public static final String NAVIGATE = "navigate";
			public static final String READDATA = "readdata";
			public static final String REMOVEDATA = "removedata";
			public static final String UPDATEDATA = "updatedata";
			public static final String WRITEFILE = "writefile";
			// class members
			public final boolean controlnotify;
			public final boolean controlplayback;
			public final boolean controlpower;
			public final boolean navigate;
			public final boolean readdata;
			public final boolean removedata;
			public final boolean updatedata;
			public final boolean writefile;
			/**
			 * Construct from JSON object.
			 * @param obj JSON object representing a PermissionResult object
			 */
			public PermissionResult(JSONObject obj) throws JSONException {
				controlnotify = obj.getBoolean(CONTROLNOTIFY);
				controlplayback = obj.getBoolean(CONTROLPLAYBACK);
				controlpower = obj.getBoolean(CONTROLPOWER);
				navigate = obj.getBoolean(NAVIGATE);
				readdata = obj.getBoolean(READDATA);
				removedata = obj.getBoolean(REMOVEDATA);
				updatedata = obj.getBoolean(UPDATEDATA);
				writefile = obj.getBoolean(WRITEFILE);
			}
			/**
			 * Construct object with native values for later serialization.
			 * @param controlnotify 
			 * @param controlplayback 
			 * @param controlpower 
			 * @param navigate 
			 * @param readdata 
			 * @param removedata 
			 * @param updatedata 
			 * @param writefile 
			 */
			public PermissionResult(boolean controlnotify, boolean controlplayback, boolean controlpower, boolean navigate, boolean readdata, boolean removedata, boolean updatedata, boolean writefile) {
				this.controlnotify = controlnotify;
				this.controlplayback = controlplayback;
				this.controlpower = controlpower;
				this.navigate = navigate;
				this.readdata = readdata;
				this.removedata = removedata;
				this.updatedata = updatedata;
				this.writefile = writefile;
			}
			@Override
			public JSONObject toJSONObject() throws JSONException {
				final JSONObject obj = new JSONObject();
				obj.put(CONTROLNOTIFY, controlnotify);
				obj.put(CONTROLPLAYBACK, controlplayback);
				obj.put(CONTROLPOWER, controlpower);
				obj.put(NAVIGATE, navigate);
				obj.put(READDATA, readdata);
				obj.put(REMOVEDATA, removedata);
				obj.put(UPDATEDATA, updatedata);
				obj.put(WRITEFILE, writefile);
				return obj;
			}
			/**
			 * Extracts a list of {@link PermissionResult} objects from a JSON array.
			 * @param obj JSONObject containing the list of objects
			 * @param key Key pointing to the node where the list is stored
			 */
			static ArrayList<PermissionResult> getPermissionResultList(JSONObject obj, String key) throws JSONException {
				if (obj.has(key)) {
					final JSONArray a = obj.getJSONArray(key);
					final ArrayList<PermissionResult> l = new ArrayList<PermissionResult>(a.length());
					for (int i = 0; i < a.length(); i++) {
						l.add(new PermissionResult(a.getJSONObject(i)));
					}
					return l;
				}
				return new ArrayList<PermissionResult>(0);
			}
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
	}
	/**
	 * Ping responder
	 * <p/>
	 * API Name: <code>JSONRPC.Ping</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Ping extends AbstractCall<String> { 
		private static final String NAME = "Ping";
		/**
		 * Ping responder
		 * @throws JSONException
		 */
		public Ping() throws JSONException {
			super();
		}
		@Override
		protected String parseOne(JSONObject obj) throws JSONException {
			return obj.getString(RESULT);
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
	}
	/**
	 * Retrieve the jsonrpc protocol version
	 * <p/>
	 * API Name: <code>JSONRPC.Version</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Version extends AbstractCall<String> { 
		private static final String NAME = "Version";
		/**
		 * Retrieve the jsonrpc protocol version
		 * @throws JSONException
		 */
		public Version() throws JSONException {
			super();
		}
		@Override
		protected String parseOne(JSONObject obj) throws JSONException {
			return obj.getString(RESULT);
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
	}
}