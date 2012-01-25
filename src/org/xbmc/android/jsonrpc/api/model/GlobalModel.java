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

public final class GlobalModel {
	/**
	 * Configuration
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Configuration extends AbstractModel {
		public final static String API_TYPE = "Configuration";
		// field names
		public static final String NOTIFICATIONS = "notifications";
		// class members
		public final ConfigurationModel.Notifications notifications;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a Configuration object
		 */
		public Configuration(ObjectNode node) {
			mType = API_TYPE;
			notifications = new ConfigurationModel.Notifications((ObjectNode)node.get(NOTIFICATIONS));
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param notifications 
		 */
		public Configuration(ConfigurationModel.Notifications notifications) {
			this.notifications = notifications;
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(NOTIFICATIONS, notifications.toObjectNode());
			return node;
		}
		/**
		 * Extracts a list of {@link GlobalModel.Configuration} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<GlobalModel.Configuration> getGlobalModelConfigurationList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<GlobalModel.Configuration> l = new ArrayList<GlobalModel.Configuration>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new GlobalModel.Configuration((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<GlobalModel.Configuration>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeValue(notifications);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected Configuration(Parcel parcel) {
			notifications = parcel.<ConfigurationModel.Notifications>readParcelable(ConfigurationModel.Notifications.class.getClassLoader());
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<Configuration> CREATOR = new Parcelable.Creator<Configuration>() {
			@Override
			public Configuration createFromParcel(Parcel parcel) {
				return new Configuration(parcel);
			}
			@Override
			public Configuration[] newArray(int n) {
				return new Configuration[n];
			}
		};
	}
	/**
	 * Global.Time
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Time extends AbstractModel {
		public final static String API_TYPE = "Global.Time";
		// field names
		public static final String HOURS = "hours";
		public static final String MILLISECONDS = "milliseconds";
		public static final String MINUTES = "minutes";
		public static final String SECONDS = "seconds";
		// class members
		public final int hours;
		public final int milliseconds;
		public final int minutes;
		public final int seconds;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a Time object
		 */
		public Time(ObjectNode node) {
			mType = API_TYPE;
			hours = node.get(HOURS).getIntValue();
			milliseconds = node.get(MILLISECONDS).getIntValue();
			minutes = node.get(MINUTES).getIntValue();
			seconds = node.get(SECONDS).getIntValue();
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param hours 
		 * @param milliseconds 
		 * @param minutes 
		 * @param seconds 
		 */
		public Time(int hours, int milliseconds, int minutes, int seconds) {
			this.hours = hours;
			this.milliseconds = milliseconds;
			this.minutes = minutes;
			this.seconds = seconds;
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(HOURS, hours);
			node.put(MILLISECONDS, milliseconds);
			node.put(MINUTES, minutes);
			node.put(SECONDS, seconds);
			return node;
		}
		/**
		 * Extracts a list of {@link GlobalModel.Time} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<GlobalModel.Time> getGlobalModelTimeList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<GlobalModel.Time> l = new ArrayList<GlobalModel.Time>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new GlobalModel.Time((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<GlobalModel.Time>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeValue(hours);
			parcel.writeValue(milliseconds);
			parcel.writeValue(minutes);
			parcel.writeValue(seconds);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected Time(Parcel parcel) {
			hours = parcel.readInt();
			milliseconds = parcel.readInt();
			minutes = parcel.readInt();
			seconds = parcel.readInt();
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<Time> CREATOR = new Parcelable.Creator<Time>() {
			@Override
			public Time createFromParcel(Parcel parcel) {
				return new Time(parcel);
			}
			@Override
			public Time[] newArray(int n) {
				return new Time[n];
			}
		};
		/**
		 * Returns time in milliseconds.
		 * @return Time in milliseconds
		 */
		public long getMilliseconds() {
			return hours * 3600000 + minutes * 60000 + seconds * 1000 + milliseconds;
		}
	}
}