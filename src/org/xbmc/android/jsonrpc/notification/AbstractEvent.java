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

	public static int parseInt(ObjectNode node, String key) {
		return node.get(key).getValueAsInt(-1);
	}
	
	public static String parseString(ObjectNode node, String key) {
		return node.get(key).getTextValue();
	}
	
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(sender);
	}
	
}
