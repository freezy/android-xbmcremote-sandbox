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

import org.json.JSONException;
import org.json.JSONObject;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.api.model.SystemModel;

public final class System {

	private final static String PREFIX = "System.";

	/**
	 * Retrieves the values of the given properties
	 * <p/>
	 * API Name: <code>System.GetProperties</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetProperties extends AbstractCall<SystemModel.PropertyValue> { 
		private static final String NAME = "GetProperties";
		/**
		 * Retrieves the values of the given properties
		 * @param properties One or more of: <tt>canshutdown</tt>, <tt>cansuspend</tt>, <tt>canhibernate</tt>, <tt>canreboot</tt>. See constants at {@link SystemModel.PropertyName}.
		 * @see SystemModel.PropertyName
		 * @throws JSONException
		 */
		public GetProperties(String... properties) throws JSONException {
			super();
			addParameter("properties", properties);
		}
		@Override
		protected SystemModel.PropertyValue parseOne(JSONObject obj) throws JSONException {
			return new SystemModel.PropertyValue(parseResult(obj));
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
	 * Puts the system running XBMC into hibernate mode
	 * <p/>
	 * API Name: <code>System.Hibernate</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Hibernate extends AbstractCall<String> { 
		private static final String NAME = "Hibernate";
		/**
		 * Puts the system running XBMC into hibernate mode
		 * @throws JSONException
		 */
		public Hibernate() throws JSONException {
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
	 * Reboots the system running XBMC
	 * <p/>
	 * API Name: <code>System.Reboot</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Reboot extends AbstractCall<String> { 
		private static final String NAME = "Reboot";
		/**
		 * Reboots the system running XBMC
		 * @throws JSONException
		 */
		public Reboot() throws JSONException {
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
	 * Shuts the system running XBMC down
	 * <p/>
	 * API Name: <code>System.Shutdown</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Shutdown extends AbstractCall<String> { 
		private static final String NAME = "Shutdown";
		/**
		 * Shuts the system running XBMC down
		 * @throws JSONException
		 */
		public Shutdown() throws JSONException {
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
	 * Suspends the system running XBMC
	 * <p/>
	 * API Name: <code>System.Suspend</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Suspend extends AbstractCall<String> { 
		private static final String NAME = "Suspend";
		/**
		 * Suspends the system running XBMC
		 * @throws JSONException
		 */
		public Suspend() throws JSONException {
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