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

package org.xbmc.android.jsonrpc.api;

import java.util.ArrayList;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import android.os.Parcelable;

public abstract class AbstractModel implements JsonSerializable, Parcelable {
	
	/**
	 * Reference to Jackson's object mapper
	 */
	protected final static ObjectMapper OM = new ObjectMapper();
	
	protected String mType;
	
	/**
	 * Tries to read an integer from JSON object.
	 * 
	 * @param node JSON object
	 * @param key Key
	 * @return Integer value if found, -1 otherwise.
	 * @throws JSONException
	 */
	public static int parseInt(ObjectNode node, String key) {
		return node.has(key) ? node.get(key).getIntValue() : -1;
	}
	
	/**
	 * Tries to read an integer from JSON object.
	 * 
	 * @param node JSON object
	 * @param key Key
	 * @return String value if found, null otherwise.
	 * @throws JSONException
	 */
	public static String parseString(ObjectNode node, String key) {
		return node.has(key) ? node.get(key).getTextValue() : null;
	}
	
	/**
	 * Tries to read an boolean from JSON object.
	 * 
	 * @param node JSON object
	 * @param key Key
	 * @return String value if found, null otherwise.
	 * @throws JSONException
	 */
	public static Boolean parseBoolean(ObjectNode node, String key) {
		final boolean hasKey = node.has(key);
		if (hasKey) {
			return node.get(key).getBooleanValue();
		} else {
			return null;
		}
	}
	
	public static Double parseDouble(ObjectNode node, String key) {
		return node.has(key) ? node.get(key).getDoubleValue() : null;
	}

	public static ArrayList<String> getStringArray(ObjectNode node, String key) {
		if (node.has(key)) {
			final ArrayNode a = (ArrayNode)node.get(key);
			final ArrayList<String> l = new ArrayList<String>(a.size());
			for (int i = 0; i < a.size(); i++) {
				l.add(a.get(i).getTextValue());
			}
			return l;
		}
		return new ArrayList<String>(0);
	}
	
	public static ArrayList<Integer> getIntegerArray(ObjectNode node, String key) {
		if (node.has(key)) {
			final ArrayNode a = (ArrayNode)node.get(key);
			final ArrayList<Integer> l = new ArrayList<Integer>(a.size());
			for (int i = 0; i < a.size(); i++) {
				l.add(a.get(i).getIntValue());
			}
			return l;
		}
		return new ArrayList<Integer>(0);
	}
	
}
