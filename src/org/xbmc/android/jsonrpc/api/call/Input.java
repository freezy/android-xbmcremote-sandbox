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

public final class Input {

	private final static String PREFIX = "Input.";

	/**
	 * Goes back in GUI
	 * <p/>
	 * API Name: <code>Input.Back</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Back extends AbstractCall<String> { 
		private static final String NAME = "Back";
		/**
		 * Goes back in GUI
		 * @throws JSONException
		 */
		public Back() throws JSONException {
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
	 * Navigate down in GUI
	 * <p/>
	 * API Name: <code>Input.Down</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Down extends AbstractCall<String> { 
		private static final String NAME = "Down";
		/**
		 * Navigate down in GUI
		 * @throws JSONException
		 */
		public Down() throws JSONException {
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
	 * Goes to home window in GUI
	 * <p/>
	 * API Name: <code>Input.Home</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Home extends AbstractCall<String> { 
		private static final String NAME = "Home";
		/**
		 * Goes to home window in GUI
		 * @throws JSONException
		 */
		public Home() throws JSONException {
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
	 * Navigate left in GUI
	 * <p/>
	 * API Name: <code>Input.Left</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Left extends AbstractCall<String> { 
		private static final String NAME = "Left";
		/**
		 * Navigate left in GUI
		 * @throws JSONException
		 */
		public Left() throws JSONException {
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
	 * Navigate right in GUI
	 * <p/>
	 * API Name: <code>Input.Right</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Right extends AbstractCall<String> { 
		private static final String NAME = "Right";
		/**
		 * Navigate right in GUI
		 * @throws JSONException
		 */
		public Right() throws JSONException {
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
	 * Select current item in GUI
	 * <p/>
	 * API Name: <code>Input.Select</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Select extends AbstractCall<String> { 
		private static final String NAME = "Select";
		/**
		 * Select current item in GUI
		 * @throws JSONException
		 */
		public Select() throws JSONException {
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
	 * Navigate up in GUI
	 * <p/>
	 * API Name: <code>Input.Up</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Up extends AbstractCall<String> { 
		private static final String NAME = "Up";
		/**
		 * Navigate up in GUI
		 * @throws JSONException
		 */
		public Up() throws JSONException {
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