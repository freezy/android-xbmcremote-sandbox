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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xbmc.android.jsonrpc.api.AbstractModel;
import org.xbmc.android.jsonrpc.api.JSONSerializable;

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
		public PropertyValue(JSONObject obj) throws JSONException {
			mType = API_TYPE;
			muted = parseBoolean(obj, MUTED);
			name = parseString(obj, NAME);
			version = obj.has(VERSION) ? new Version(obj.getJSONObject(VERSION)) : null;
			volume = parseInt(obj, VOLUME);
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
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(MUTED, muted);
			obj.put(NAME, name);
			obj.put(VERSION, version.toJSONObject());
			obj.put(VOLUME, volume);
			return obj;
		}
		/**
		 * Extracts a list of {@link ApplicationModel.PropertyValue} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<ApplicationModel.PropertyValue> getApplicationModelPropertyValueList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<ApplicationModel.PropertyValue> l = new ArrayList<ApplicationModel.PropertyValue>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new ApplicationModel.PropertyValue(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<ApplicationModel.PropertyValue>(0);
		}
		/**
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class Version implements JSONSerializable, Parcelable {
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
			public Version(JSONObject obj) throws JSONException {
				major = obj.getInt(MAJOR);
				minor = obj.getInt(MINOR);
				revision = parseString(obj, REVISION);
				tag = obj.getString(TAG);
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
			public JSONObject toJSONObject() throws JSONException {
				final JSONObject obj = new JSONObject();
				obj.put(MAJOR, major);
				obj.put(MINOR, minor);
				obj.put(REVISION, revision);
				obj.put(TAG, tag);
				return obj;
			}
			/**
			 * Extracts a list of {@link Version} objects from a JSON array.
			 * @param obj JSONObject containing the list of objects
			 * @param key Key pointing to the node where the list is stored
			 */
			static ArrayList<Version> getVersionList(JSONObject obj, String key) throws JSONException {
				if (obj.has(key)) {
					final JSONArray a = obj.getJSONArray(key);
					final ArrayList<Version> l = new ArrayList<Version>(a.length());
					for (int i = 0; i < a.length(); i++) {
						l.add(new Version(a.getJSONObject(i)));
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