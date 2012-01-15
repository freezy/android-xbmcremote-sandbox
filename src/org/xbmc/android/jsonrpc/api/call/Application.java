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
import org.xbmc.android.jsonrpc.api.model.ApplicationModel;
import org.xbmc.android.jsonrpc.api.model.ApplicationModel.PropertyValue;

/**
 * Implements JSON-RPC API methods for the "Application" namespace.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public final class Application {
	
	private final static String PREFIX = "Application.";
	
	/**
	 * Retrieves the values of the given properties
	 */
	public static class GetProperties extends AbstractCall<ApplicationModel.PropertyValue> {
		private static final String NAME = "GetProperties";
		/**
		 *  Gets the sources of the media windows.
		 *  <p/>
		 *  Curl example:
		 *    <code>curl -i -X POST -d '{"jsonrpc": "2.0", "method": "Application.GetProperties", "id": "1", "params": { "properties": [ "version" ] } }' http://localhost:8080/jsonrpc</code>
		 *    
		 * @param properties Which fields to return, see {@link ApplicationModel.PropertyName}.
		 * @throws JSONException
		 */
		public GetProperties(String... properties) throws JSONException {
			super();
			addParameter("properties", properties);
		}
		@Override
		protected PropertyValue parseOne(JSONObject obj) throws JSONException {
			return new PropertyValue(obj);
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
