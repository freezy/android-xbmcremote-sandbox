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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Creates request objects for the Application API (XBMC-querying stuff).
 * 
 * @author freezy <freezy@xbmc.org>
 */
public class ApplicationAPI extends AbstractAPI {
	
	private final static String PREFIX = "Application.";
	
	/**
	 * Gets XBMC-related properties.
	 * 
	 * Curl example:
	 * 		<code>curl -i -X POST -d '{"jsonrpc": "2.0", "method": "Application.GetProperties", "id": "1", "params": { "properties": [ "version" ] } }' http://localhost:8080/jsonrpc</code>
	 * 
	 * @param properties
	 * @return
	 * @throws JSONException
	 */
	public JSONObject getProperties(String... properties) throws JSONException {
		final JSONObject request = createRequest("GetProperties");
		final JSONArray props = new JSONArray();
		for (int i = 0; i < properties.length; i++) {
			props.put(properties[i]);
		}
		getParameters(request).put("properties", props);
		return request;
	}
	
	/**
	 * Application property names.
	 * 
	 * @see ApplicationAPI#getProperties(String[])
	 * @author freezy <freezy@xbmc.org>
	 */
	public interface PropertyName {
		final String VOLUME = "volume";
		final String MUTED = "muted";
		final String NAME = "name";
		final String VERSION = "version";
	}

	@Override
	protected String getPrefix() {
		return PREFIX;
	}
	
	/**
	 * Transfer object for version responses
	 * 
	 * @author freezy <freezy@xbmc.org>
	 */
	public static class Version {
		public final int major;
		public final int minor;
		public final String revision;
		public final String tag;
		public Version(JSONObject apiResponse) throws JSONException {
			this.major = apiResponse.getInt("major");
			this.minor = apiResponse.getInt("minor");
			this.revision = apiResponse.getString("revision");
			this.tag = apiResponse.getString("tag");
		}
		@Override
		public String toString() {
			return major + "." + minor + " " + tag + ", (" + revision + ")";
		}
	}
}
