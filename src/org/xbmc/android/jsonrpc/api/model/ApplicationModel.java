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
import org.xbmc.android.jsonrpc.api.JsonSerializable;

public final class ApplicationModel {
	public interface PropertyName {
		public final String VOLUME = "volume";
		public final String MUTED = "muted";
		public final String NAME = "name";
		public final String VERSION = "version";
	}
	/**
	 * Application.Property.Value
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class PropertyValue extends AbstractModel {
		public final static String API_TYPE = "Application.Property.Value";
		// field names
		public static final String MUTED = "muted";
		public static final String NAME = "name";
		public static final String VERSION = "version";
		public static final String VOLUME = "volume";
		// class members
		public final Boolean muted;
		public final String name;
		public final Version version;
		public final Integer volume;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a PropertyValue object
		 */
		public PropertyValue(ObjectNode node) {
			mType = API_TYPE;
			muted = parseBoolean(node, MUTED);
			name = parseString(node, NAME);
			version = node.has(VERSION) ? new Version((ObjectNode)node.get(VERSION)) : null;
			volume = parseInt(node, VOLUME);
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param muted 
		 * @param name 
		 * @param version 
		 * @param volume 
		 */
		public PropertyValue(Boolean muted, String name, Version version, Integer volume) {
			this.muted = muted;
			this.name = name;
			this.version = version;
			this.volume = volume;
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(MUTED, muted);
			node.put(NAME, name);
			node.put(VERSION, version.toObjectNode());
			node.put(VOLUME, volume);
			return node;
		}
		/**
		 * Extracts a list of {@link ApplicationModel.PropertyValue} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<ApplicationModel.PropertyValue> getApplicationModelPropertyValueList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<ApplicationModel.PropertyValue> l = new ArrayList<ApplicationModel.PropertyValue>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new ApplicationModel.PropertyValue((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<ApplicationModel.PropertyValue>(0);
		}
		/**
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class Version implements JsonSerializable, Parcelable {
			// field names
			public static final String MAJOR = "major";
			public static final String MINOR = "minor";
			public static final String REVISION = "revision";
			public static final String TAG = "tag";
			// class members
			public final int major;
			public final int minor;
			public final String revision;
			/**
			 * One of: <tt>prealpha</tt>, <tt>alpha</tt>, <tt>beta</tt>, <tt>releasecandidate</tt>, <tt>stable</tt>.
			 */
			public final String tag;
			/**
			 * Construct from JSON object.
			 * @param obj JSON object representing a Version object
			 */
			public Version(ObjectNode node) {
				major = node.get(MAJOR).getIntValue();
				minor = node.get(MINOR).getIntValue();
				revision = parseString(node, REVISION);
				tag = node.get(TAG).getTextValue();
			}
			/**
			 * Construct object with native values for later serialization.
			 * @param major 
			 * @param minor 
			 * @param revision 
			 * @param tag 
			 */
			public Version(int major, int minor, String revision, String tag) {
				this.major = major;
				this.minor = minor;
				this.revision = revision;
				this.tag = tag;
			}
			@Override
			public ObjectNode toObjectNode() {
				final ObjectNode node = OM.createObjectNode();
				node.put(MAJOR, major);
				node.put(MINOR, minor);
				node.put(REVISION, revision);
				node.put(TAG, tag);
				return node;
			}
			/**
			 * Extracts a list of {@link Version} objects from a JSON array.
			 * @param obj ObjectNode containing the list of objects
			 * @param key Key pointing to the node where the list is stored
			 */
			static ArrayList<Version> getVersionList(ObjectNode node, String key) {
				if (node.has(key)) {
					final ArrayNode a = (ArrayNode)node.get(key);
					final ArrayList<Version> l = new ArrayList<Version>(a.size());
					for (int i = 0; i < a.size(); i++) {
						l.add(new Version((ObjectNode)a.get(i)));
					}
					return l;
				}
				return new ArrayList<Version>(0);
			}
			/**
			 * Flatten this object into a Parcel.
			 * @param parcel the Parcel in which the object should be written
			 * @param flags additional flags about how the object should be written
			 */
			@Override
			public void writeToParcel(Parcel parcel, int flags) {
				parcel.writeValue(major);
				parcel.writeValue(minor);
				parcel.writeValue(revision);
				parcel.writeValue(tag);
			}
			@Override
			public int describeContents() {
				return 0;
			}
			/**
			 * Construct via parcel
			 */
			protected Version(Parcel parcel) {
				major = parcel.readInt();
				minor = parcel.readInt();
				revision = parcel.readString();
				tag = parcel.readString();
			}
			/**
			 * Generates instances of this Parcelable class from a Parcel.
			 */
			public static final Parcelable.Creator<Version> CREATOR = new Parcelable.Creator<Version>() {
				@Override
				public Version createFromParcel(Parcel parcel) {
					return new Version(parcel);
				}
				@Override
				public Version[] newArray(int n) {
					return new Version[n];
				}
			};
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeValue(muted);
			parcel.writeValue(name);
			parcel.writeValue(version);
			parcel.writeValue(volume);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected PropertyValue(Parcel parcel) {
			muted = parcel.readInt() == 1;
			name = parcel.readString();
			version = parcel.<Version>readParcelable(Version.class.getClassLoader());
			volume = parcel.readInt();
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