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
import org.xbmc.android.jsonrpc.api.UndefinedResult;

public final class XBMC {

	private final static String PREFIX = "XBMC.";

	/**
	 * Retrieve info booleans about XBMC and the system
	 * <p/>
	 * API Name: <code>XBMC.GetInfoBooleans</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetInfoBooleans extends AbstractCall<UndefinedResult> { 
		private static final String NAME = "GetInfoBooleans";
		/**
		 * Retrieve info booleans about XBMC and the system
		 * @param booleans 
		 * @throws JSONException
		 */
		public GetInfoBooleans(String... booleans) throws JSONException {
			super();
			addParameter("booleans", booleans);
		}
		@Override
		protected UndefinedResult parseOne(JSONObject obj) throws JSONException {
			return new UndefinedResult(obj);
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
	 * Retrieve info labels about XBMC and the system
	 * <p/>
	 * API Name: <code>XBMC.GetInfoLabels</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetInfoLabels extends AbstractCall<UndefinedResult> { 
		private static final String NAME = "GetInfoLabels";
		/**
		 * Retrieve info labels about XBMC and the system
		 * @param labels Retrieve info labels about XBMC and the system
		 * @throws JSONException
		 */
		public GetInfoLabels(String... labels) throws JSONException {
			super();
			addParameter("labels", labels);
		}
		@Override
		protected UndefinedResult parseOne(JSONObject obj) throws JSONException {
			return new UndefinedResult(obj);
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