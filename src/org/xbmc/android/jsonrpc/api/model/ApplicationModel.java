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

/**
 * Defines all types in the <code>Application.*</code> namespace.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public final class ApplicationModel {
	
	public static class PropertyValue extends AbstractModel {
		public final static String TYPE = "Application.Property.Value";
		public int volume;
		public final Boolean muted;
		public final String name;
		public final Version version;
		public PropertyValue(JSONObject obj) throws JSONException {
			mType = TYPE;
			volume = parseInt(obj, "volume");
			muted = parseBoolean(obj, "muted");
			name = parseString(obj, "name");
			version = obj.has("version") ? new Version(obj.getJSONObject("version")) : null;
		}
		public static class Version {
			public final int major;
			public final int minor;
			public final String revision;
			public final String tag;
			public Version(JSONObject obj) throws JSONException {
				this.major = obj.getInt("major");
				this.minor = obj.getInt("minor");
				this.revision = parseString(obj, "revision");
				this.tag = obj.getString("tag");
			}
			@Override
			public String toString() {
				return major + "." + minor + " " + tag + ", (" + revision + ")";
			}
		}
	}
	
	
	/*========================================================================* 
	 *  FIELDS 
	 *========================================================================*/

	public interface PropertyName {
		public final String VOLUME = "volume";
		public final String MUTED = "muted";
		public final String NAME = "name";
		public final String VERSION = "version";
	}
}
