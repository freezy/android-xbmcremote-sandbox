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

/**
 * Parses System.* events.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public class SystemEvent {
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 *  notifications: https://github.com/xbmc/xbmc/blob/master/xbmc/interfaces/json-rpc/notifications.json
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * XBMC will be closed.
	 */
	public static class Quit extends AbstractEvent {
		public final static String METHOD = "System.OnQuit";
		public Quit(JSONObject obj) throws JSONException {
			super(obj);
		}
		@Override
		public String toString() {
			return 	"QUIT";
		}
	}
	
	/**
	 * The system will be restarted.
	 */
	public static class Restart extends AbstractEvent {
		public final static String METHOD = "System.OnRestart";
		public Restart(JSONObject obj) throws JSONException {
			super(obj);
		}
		@Override
		public String toString() {
			return 	"RESTART";
		}
	}
	
	/**
	 * The system woke up from suspension.
	 */
	public static class Wake extends AbstractEvent {
		public final static String METHOD = "System.OnWake";
		public Wake(JSONObject obj) throws JSONException {
			super(obj);
		}
		@Override
		public String toString() {
			return 	"WAKEUP";
		}
	}
	
	/**
	 * The system woke up from suspension.
	 */
	public static class LowBattery extends AbstractEvent {
		public final static String METHOD = "System.OnLowBattery";
		public LowBattery(JSONObject obj) throws JSONException {
			super(obj);
		}
		@Override
		public String toString() {
			return 	"LOWBATTERY";
		}
	}
}
