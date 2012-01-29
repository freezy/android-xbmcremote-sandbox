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

package org.xbmc.android.jsonrpc.api.call;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.api.AbstractModel;
import org.xbmc.android.jsonrpc.api.UndefinedResult;

public final class JSONRPC {

	private final static String PREFIX = "JSONRPC.";

	/**
	 * Enumerates all actions and descriptions
	 * <p/>
	 * API Name: <code>JSONRPC.Introspect</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Introspect extends AbstractCall<UndefinedResult> { 
		private static final String NAME = "Introspect";
		/**
		 * Enumerates all actions and descriptions
		 * @param getdescriptions 
		 * @param getmetadata 
		 * @param filterbytransport 
		 * @param filter 
		 */
		public Introspect(Boolean getdescriptions, Boolean getmetadata, Boolean filterbytransport, GetreferencesIdType filter) {
			super();
			addParameter("getdescriptions", getdescriptions);
			addParameter("getmetadata", getmetadata);
			addParameter("filterbytransport", filterbytransport);
			addParameter("filter", filter);
		}
		@Override
		protected UndefinedResult parseOne(ObjectNode node) {
			return new UndefinedResult(node);
		}
		/**
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class GetreferencesIdType extends AbstractModel {
			// field names
			public static final String GETREFERENCES = "getreferences";
			public static final String ID = "id";
			public static final String TYPE = "type";
			// class members
			/**
			 * Whether or not to print the schema for referenced types.
			 */
			public final Boolean getreferences;
			/**
			 * Name of a namespace, method or type.
			 */
			public final String id;
			/**
			 * Type of the given name.
			 * One of: <tt>method</tt>, <tt>namespace</tt>, <tt>type</tt>, <tt>notification</tt>.
			 */
			public final String type;
			/**
			 * Construct object with native values for later serialization.
			 * @param getreferences Whether or not to print the schema for referenced types 
			 * @param id Name of a namespace, method or type 
			 * @param type Type of the given name 
			 */
			public GetreferencesIdType(Boolean getreferences, String id, String type) {
				this.getreferences = getreferences;
				this.id = id;
				this.type = type;
			}
			@Override
			public ObjectNode toObjectNode() {
				final ObjectNode node = OM.createObjectNode();
				node.put(GETREFERENCES, getreferences);
				node.put(ID, id);
				node.put(TYPE, type);
				return node;
			}
			/**
			 * Flatten this object into a Parcel.
			 * @param parcel the Parcel in which the object should be written
			 * @param flags additional flags about how the object should be written
			 */
			@Override
			public void writeToParcel(Parcel parcel, int flags) {
				parcel.writeValue(getreferences);
				parcel.writeValue(id);
				parcel.writeValue(type);
			}
			@Override
			public int describeContents() {
				return 0;
			}
			/**
			 * Construct via parcel
			 */
			protected GetreferencesIdType(Parcel parcel) {
				getreferences = parcel.readInt() == 1;
				id = parcel.readString();
				type = parcel.readString();
			}
			/**
			 * Generates instances of this Parcelable class from a Parcel.
			 */
			public static final Parcelable.Creator<GetreferencesIdType> CREATOR = new Parcelable.Creator<GetreferencesIdType>() {
				@Override
				public GetreferencesIdType createFromParcel(Parcel parcel) {
					return new GetreferencesIdType(parcel);
				}
				@Override
				public GetreferencesIdType[] newArray(int n) {
					return new GetreferencesIdType[n];
				}
			};
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
				parcel.writeParcelable(mResult, flags);
			}
		/**
		 * Construct via parcel
		 */
		protected Introspect(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Introspect> CREATOR = new Parcelable.Creator<Introspect>() {
			@Override
			public Introspect createFromParcel(Parcel parcel) {
				return new Introspect(parcel);
			}
			@Override
			public Introspect[] newArray(int n) {
				return new Introspect[n];
			}
		};
}
	/**
	 * Notify all other connected clients
	 * <p/>
	 * API Name: <code>JSONRPC.NotifyAll</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class NotifyAll extends AbstractCall<String> { 
		private static final String NAME = "NotifyAll";
		/**
		 * Notify all other connected clients
		 * @param sender 
		 * @param message 
		 * @param data 
		 */
		public NotifyAll(String sender, String message, String data) {
			super();
			addParameter("sender", sender);
			addParameter("message", message);
			addParameter("data", data);
		}
		@Override
		protected String parseOne(ObjectNode node) {
			return node.getTextValue();
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
				parcel.writeValue(mResult);
			}
		/**
		 * Construct via parcel
		 */
		protected NotifyAll(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<NotifyAll> CREATOR = new Parcelable.Creator<NotifyAll>() {
			@Override
			public NotifyAll createFromParcel(Parcel parcel) {
				return new NotifyAll(parcel);
			}
			@Override
			public NotifyAll[] newArray(int n) {
				return new NotifyAll[n];
			}
		};
}
	/**
	 * Retrieve the clients permissions
	 * <p/>
	 * API Name: <code>JSONRPC.Permission</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Permission extends AbstractCall<Permission.PermissionResult> { 
		private static final String NAME = "Permission";
		/**
		 * Retrieve the clients permissions
		 */
		public Permission() {
			super();
		}
		@Override
		protected Permission.PermissionResult parseOne(ObjectNode node) {
			return new Permission.PermissionResult(node);
		}
		/**
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class PermissionResult extends AbstractModel {
			// field names
			public static final String CONTROLNOTIFY = "controlnotify";
			public static final String CONTROLPLAYBACK = "controlplayback";
			public static final String CONTROLPOWER = "controlpower";
			public static final String NAVIGATE = "navigate";
			public static final String READDATA = "readdata";
			public static final String REMOVEDATA = "removedata";
			public static final String UPDATEDATA = "updatedata";
			public static final String WRITEFILE = "writefile";
			// class members
			public final boolean controlnotify;
			public final boolean controlplayback;
			public final boolean controlpower;
			public final boolean navigate;
			public final boolean readdata;
			public final boolean removedata;
			public final boolean updatedata;
			public final boolean writefile;
			/**
			 * Construct from JSON object.
			 * @param obj JSON object representing a PermissionResult object
			 */
			public PermissionResult(ObjectNode node) {
				controlnotify = node.get(CONTROLNOTIFY).getBooleanValue();
				controlplayback = node.get(CONTROLPLAYBACK).getBooleanValue();
				controlpower = node.get(CONTROLPOWER).getBooleanValue();
				navigate = node.get(NAVIGATE).getBooleanValue();
				readdata = node.get(READDATA).getBooleanValue();
				removedata = node.get(REMOVEDATA).getBooleanValue();
				updatedata = node.get(UPDATEDATA).getBooleanValue();
				writefile = node.get(WRITEFILE).getBooleanValue();
			}
			/**
			 * Construct object with native values for later serialization.
			 * @param controlnotify 
			 * @param controlplayback 
			 * @param controlpower 
			 * @param navigate 
			 * @param readdata 
			 * @param removedata 
			 * @param updatedata 
			 * @param writefile 
			 */
			public PermissionResult(boolean controlnotify, boolean controlplayback, boolean controlpower, boolean navigate, boolean readdata, boolean removedata, boolean updatedata, boolean writefile) {
				this.controlnotify = controlnotify;
				this.controlplayback = controlplayback;
				this.controlpower = controlpower;
				this.navigate = navigate;
				this.readdata = readdata;
				this.removedata = removedata;
				this.updatedata = updatedata;
				this.writefile = writefile;
			}
			@Override
			public ObjectNode toObjectNode() {
				final ObjectNode node = OM.createObjectNode();
				node.put(CONTROLNOTIFY, controlnotify);
				node.put(CONTROLPLAYBACK, controlplayback);
				node.put(CONTROLPOWER, controlpower);
				node.put(NAVIGATE, navigate);
				node.put(READDATA, readdata);
				node.put(REMOVEDATA, removedata);
				node.put(UPDATEDATA, updatedata);
				node.put(WRITEFILE, writefile);
				return node;
			}
			/**
			 * Extracts a list of {@link PermissionResult} objects from a JSON array.
			 * @param obj ObjectNode containing the list of objects
			 * @param key Key pointing to the node where the list is stored
			 */
			static ArrayList<PermissionResult> getPermissionResultList(ObjectNode node, String key) {
				if (node.has(key)) {
					final ArrayNode a = (ArrayNode)node.get(key);
					final ArrayList<PermissionResult> l = new ArrayList<PermissionResult>(a.size());
					for (int i = 0; i < a.size(); i++) {
						l.add(new PermissionResult((ObjectNode)a.get(i)));
					}
					return l;
				}
				return new ArrayList<PermissionResult>(0);
			}
			/**
			 * Flatten this object into a Parcel.
			 * @param parcel the Parcel in which the object should be written
			 * @param flags additional flags about how the object should be written
			 */
			@Override
			public void writeToParcel(Parcel parcel, int flags) {
				parcel.writeValue(controlnotify);
				parcel.writeValue(controlplayback);
				parcel.writeValue(controlpower);
				parcel.writeValue(navigate);
				parcel.writeValue(readdata);
				parcel.writeValue(removedata);
				parcel.writeValue(updatedata);
				parcel.writeValue(writefile);
			}
			@Override
			public int describeContents() {
				return 0;
			}
			/**
			 * Construct via parcel
			 */
			protected PermissionResult(Parcel parcel) {
				controlnotify = parcel.readInt() == 1;
				controlplayback = parcel.readInt() == 1;
				controlpower = parcel.readInt() == 1;
				navigate = parcel.readInt() == 1;
				readdata = parcel.readInt() == 1;
				removedata = parcel.readInt() == 1;
				updatedata = parcel.readInt() == 1;
				writefile = parcel.readInt() == 1;
			}
			/**
			 * Generates instances of this Parcelable class from a Parcel.
			 */
			public static final Parcelable.Creator<PermissionResult> CREATOR = new Parcelable.Creator<PermissionResult>() {
				@Override
				public PermissionResult createFromParcel(Parcel parcel) {
					return new PermissionResult(parcel);
				}
				@Override
				public PermissionResult[] newArray(int n) {
					return new PermissionResult[n];
				}
			};
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
				parcel.writeParcelable(mResult, flags);
			}
		/**
		 * Construct via parcel
		 */
		protected Permission(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Permission> CREATOR = new Parcelable.Creator<Permission>() {
			@Override
			public Permission createFromParcel(Parcel parcel) {
				return new Permission(parcel);
			}
			@Override
			public Permission[] newArray(int n) {
				return new Permission[n];
			}
		};
}
	/**
	 * Ping responder
	 * <p/>
	 * API Name: <code>JSONRPC.Ping</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Ping extends AbstractCall<String> { 
		private static final String NAME = "Ping";
		/**
		 * Ping responder
		 */
		public Ping() {
			super();
		}
		@Override
		protected String parseOne(ObjectNode node) {
			return node.getTextValue();
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
				parcel.writeValue(mResult);
			}
		/**
		 * Construct via parcel
		 */
		protected Ping(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Ping> CREATOR = new Parcelable.Creator<Ping>() {
			@Override
			public Ping createFromParcel(Parcel parcel) {
				return new Ping(parcel);
			}
			@Override
			public Ping[] newArray(int n) {
				return new Ping[n];
			}
		};
}
	/**
	 * Retrieve the jsonrpc protocol version
	 * <p/>
	 * API Name: <code>JSONRPC.Version</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Version extends AbstractCall<String> { 
		private static final String NAME = "Version";
		/**
		 * Retrieve the jsonrpc protocol version
		 */
		public Version() {
			super();
		}
		@Override
		protected String parseOne(ObjectNode node) {
			return node.getTextValue();
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
				parcel.writeValue(mResult);
			}
		/**
		 * Construct via parcel
		 */
		protected Version(Parcel parcel) {
			super(parcel);
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
}