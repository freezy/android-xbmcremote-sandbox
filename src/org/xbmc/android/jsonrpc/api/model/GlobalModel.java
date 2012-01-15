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
 * Defines all types in the <code>Global.*</code> namespace.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public final class GlobalModel {
	
	public static class Time extends AbstractModel {
		public final static String TYPE = "Global.Time";
		public final int hours;
		public final int minutes;
		public final int seconds;
		public final int milliseconds;
		public Time(JSONObject obj) throws JSONException {
			mType = TYPE;
			hours = obj.getInt("hours");
			minutes = obj.getInt("minutes");
			seconds = obj.getInt("seconds");
			milliseconds = obj.getInt("milliseconds");
		}
		@Override
		public String toString() {
			return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);
		}
	}

}
