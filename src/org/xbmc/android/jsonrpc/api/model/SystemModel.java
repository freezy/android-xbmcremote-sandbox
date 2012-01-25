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

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.xbmc.android.jsonrpc.api.AbstractModel;

public final class SystemModel {
	public interface PropertyName {
		public final String CANSHUTDOWN = "canshutdown";
		public final String CANSUSPEND = "cansuspend";
		public final String CANHIBERNATE = "canhibernate";
		public final String CANREBOOT = "canreboot";
	}
	/**
	 * System.Property.Value
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class PropertyValue extends AbstractModel {
		public final static String API_TYPE = "System.Property.Value";
		// field names
		public static final String CANHIBERNATE = "canhibernate";
		public static final String CANREBOOT = "canreboot";
		public static final String CANSHUTDOWN = "canshutdown";
		public static final String CANSUSPEND = "cansuspend";
		// class members
		public final Boolean canhibernate;
		public final Boolean canreboot;
		public final Boolean canshutdown;
		public final Boolean cansuspend;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a PropertyValue object
		 */
		public PropertyValue(ObjectNode node) {
			mType = API_TYPE;
			canhibernate = parseBoolean(node, CANHIBERNATE);
			canreboot = parseBoolean(node, CANREBOOT);
			canshutdown = parseBoolean(node, CANSHUTDOWN);
			cansuspend = parseBoolean(node, CANSUSPEND);
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param canhibernate 
		 * @param canreboot 
		 * @param canshutdown 
		 * @param cansuspend 
		 */
		public PropertyValue(Boolean canhibernate, Boolean canreboot, Boolean canshutdown, Boolean cansuspend) {
			this.canhibernate = canhibernate;
			this.canreboot = canreboot;
			this.canshutdown = canshutdown;
			this.cansuspend = cansuspend;
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(CANHIBERNATE, canhibernate);
			node.put(CANREBOOT, canreboot);
			node.put(CANSHUTDOWN, canshutdown);
			node.put(CANSUSPEND, cansuspend);
			return node;
		}
		/**
		 * Extracts a list of {@link SystemModel.PropertyValue} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<SystemModel.PropertyValue> getSystemModelPropertyValueList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<SystemModel.PropertyValue> l = new ArrayList<SystemModel.PropertyValue>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new SystemModel.PropertyValue((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<SystemModel.PropertyValue>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeValue(canhibernate);
			parcel.writeValue(canreboot);
			parcel.writeValue(canshutdown);
			parcel.writeValue(cansuspend);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected PropertyValue(Parcel parcel) {
			canhibernate = parcel.readInt() == 1;
			canreboot = parcel.readInt() == 1;
			canshutdown = parcel.readInt() == 1;
			cansuspend = parcel.readInt() == 1;
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<PropertyValue> CREATOR = new Parcelable.Creator<PropertyValue>() {
			@Override
			public PropertyValue createFromParcel(Parcel parcel) {
				return new PropertyValue(parcel);
			}
			@Override
			public PropertyValue[] newArray(int n) {
				return new PropertyValue[n];
			}
		};
	}
}