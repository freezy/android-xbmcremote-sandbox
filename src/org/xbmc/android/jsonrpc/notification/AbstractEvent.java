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

import org.codehaus.jackson.node.ObjectNode;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Parent class for all notifications.
 *
 * @author freezy <freezy@xbmc.org>
 */
public abstract class AbstractEvent implements Parcelable {
	
	public final String sender;
	
	public AbstractEvent(ObjectNode node) {
		sender = node.get("sender").getTextValue();
	}
	protected AbstractEvent(Parcel parcel) {
		sender = parcel.readString();
	}
	
	public abstract int getId();
	
	/**
	 * Parses the notification type and returns an instance.
	 * @param node Original notification, as read from API.
	 * @return
	 */
	public static AbstractEvent parse(ObjectNode node) {
		final String method = node.get("method").getTextValue();
		final ObjectNode params = (ObjectNode)node.get("params");
		if (method.equals(PlayerEvent.Play.METHOD)) {
			return new PlayerEvent.Play(params);
		} else if (method.equals(PlayerEvent.Pause.METHOD)) {
			return new PlayerEvent.Pause(params);
		} else if (method.equals(PlayerEvent.Stop.METHOD)) {
			return new PlayerEvent.Stop(params);
		} else if (method.equals(PlayerEvent.SpeedChanged.METHOD)) {
			return new PlayerEvent.SpeedChanged(params);
		} else if (method.equals(PlayerEvent.Seek.METHOD)) {
			return new PlayerEvent.Seek(params);
		} else if (method.equals(SystemEvent.Quit.METHOD)) {
			return new SystemEvent.Quit(params);
		} else if (method.equals(SystemEvent.Restart.METHOD)) {
			return new SystemEvent.Restart(params);
		} else if (method.equals(SystemEvent.Wake.METHOD)) {
			return new SystemEvent.Wake(params);
		} else if (method.equals(SystemEvent.LowBattery.METHOD)) {
			return new SystemEvent.LowBattery(params);
		} else{
			return null;
		}
	}

	public static int parseInt(ObjectNode node, String key) {
		return node.has(key) ? node.get(key).getIntValue() : -1;
	}
	
	public static String parseString(ObjectNode node, String key) {
		return node.has(key) ? node.get(key).getTextValue() : null;
	}
	
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(sender);
	}
	
}
