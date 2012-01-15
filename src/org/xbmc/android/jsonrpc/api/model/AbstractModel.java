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

import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractModel {
	
	protected String mType;
	
	/**
	 * Tries to read an integer from JSON object.
	 * 
	 * @param obj JSON object
	 * @param key Key
	 * @return Integer value if found, -1 otherwise.
	 * @throws JSONException
	 */
	public static int parseInt(JSONObject obj, String key) throws JSONException {
		return obj.has(key) ? obj.getInt(key) : -1;
	}
	
	/**
	 * Tries to read an integer from JSON object.
	 * 
	 * @param obj JSON object
	 * @param key Key
	 * @return String value if found, null otherwise.
	 * @throws JSONException
	 */
	public static String parseString(JSONObject obj, String key) throws JSONException {
		return obj.has(key) ? obj.getString(key) : null;
	}
	
	/**
	 * Tries to read an boolean from JSON object.
	 * 
	 * @param obj JSON object
	 * @param key Key
	 * @return String value if found, null otherwise.
	 * @throws JSONException
	 */
	public static Boolean parseBoolean(JSONObject obj, String key) throws JSONException {
		return obj.has(key) ? obj.getBoolean(key) : null;
	}
}
