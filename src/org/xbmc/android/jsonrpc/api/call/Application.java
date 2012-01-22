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
import org.xbmc.android.jsonrpc.api.model.ApplicationModel;

public final class Application {

	private final static String PREFIX = "Application.";

	/**
	 * Retrieves the values of the given properties
	 * <p/>
	 * API Name: <code>Application.GetProperties</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetProperties extends AbstractCall<ApplicationModel.PropertyValue> { 
		private static final String NAME = "GetProperties";
		/**
		 * Retrieves the values of the given properties
		 * @param properties One or more of: <tt>volume</tt>, <tt>muted</tt>, <tt>name</tt>, <tt>version</tt>. See constants at {@link ApplicationModel.PropertyName}.
		 * @see ApplicationModel.PropertyName
		 * @throws JSONException
		 */
		public GetProperties(String... properties) throws JSONException {
			super();
			addParameter("properties", properties);
		}
		@Override
		protected ApplicationModel.PropertyValue parseOne(JSONObject obj) throws JSONException {
			return new ApplicationModel.PropertyValue(parseResult(obj));
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
	 * Quit application
	 * <p/>
	 * API Name: <code>Application.Quit</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Quit extends AbstractCall<String> { 
		private static final String NAME = "Quit";
		/**
		 * Quit application
		 * @throws JSONException
		 */
		public Quit() throws JSONException {
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
	 * Toggle mute/unmute
	 * <p/>
	 * API Name: <code>Application.SetMute</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class SetMute extends AbstractCall<Boolean> { 
		private static final String NAME = "SetMute";
		/**
		 * Toggle mute/unmute
		 * @param mute 
		 * @throws JSONException
		 */
		public SetMute(Boolean mute) throws JSONException {
			super();
			addParameter("mute", mute);
		}
		/**
		 * Toggle mute/unmute
		 * @param mute 
		 * @throws JSONException
		 */
		public SetMute(String mute) throws JSONException {
			super();
			addParameter("mute", mute);
		}
		@Override
		protected Boolean parseOne(JSONObject obj) throws JSONException {
			return obj.getBoolean(RESULT);
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
	 * Set the current volume
	 * <p/>
	 * API Name: <code>Application.SetVolume</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class SetVolume extends AbstractCall<Integer> { 
		private static final String NAME = "SetVolume";
		/**
		 * Set the current volume
		 * @param volume 
		 * @throws JSONException
		 */
		public SetVolume(int volume) throws JSONException {
			super();
			addParameter("volume", volume);
		}
		@Override
		protected Integer parseOne(JSONObject obj) throws JSONException {
			return obj.getInt(RESULT);
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