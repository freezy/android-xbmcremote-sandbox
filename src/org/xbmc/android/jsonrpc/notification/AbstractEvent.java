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

package org.xbmc.android.jsonrpc.notification;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractEvent {
	
	public final String sender;
	
	public AbstractEvent(JSONObject event) throws JSONException {
		sender = event.getString("sender");
	}

	public static int parseInt(JSONObject event, String key) throws JSONException {
		return event.has(key) ? event.getInt(key) : -1;
	}
	
	public static String parseString(JSONObject event, String key) throws JSONException {
		return event.has(key) ? event.getString(key) : null;
	}
	
}
