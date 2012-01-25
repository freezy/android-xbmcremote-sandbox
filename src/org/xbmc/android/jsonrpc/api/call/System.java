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
import android.util.Log;
import java.io.IOException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.node.ObjectNode;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.api.model.SystemModel;

public final class System {

	private final static String PREFIX = "System.";

	/**
	 * Retrieves the values of the given properties
	 * <p/>
	 * API Name: <code>System.GetProperties</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetProperties extends AbstractCall<SystemModel.PropertyValue> { 
		private static final String NAME = "GetProperties";
		/**
		 * Retrieves the values of the given properties
		 * @param properties One or more of: <tt>canshutdown</tt>, <tt>cansuspend</tt>, <tt>canhibernate</tt>, <tt>canreboot</tt>. See constants at {@link SystemModel.PropertyName}.
		 * @see SystemModel.PropertyName
		 */
		public GetProperties(String... properties) {
			super();
			addParameter("properties", properties);
		}
		@Override
		protected SystemModel.PropertyValue parseOne(ObjectNode node) {
			return new SystemModel.PropertyValue((ObjectNode)parseResult(node));
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
		/**
		 * Construct via parcel
		 */
		protected GetProperties(Parcel parcel) {
			try {
				mResponse = (ObjectNode)OM.readTree(parcel.readString());
			} catch (JsonProcessingException e) {
				Log.e(NAME, "Error reading JSON object from parcel: " + e.getMessage(), e);
			} catch (IOException e) {
				Log.e(NAME, "I/O exception reading JSON object from parcel: " + e.getMessage(), e);
			}
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetProperties> CREATOR = new Parcelable.Creator<GetProperties>() {
			@Override
			public GetProperties createFromParcel(Parcel parcel) {
				return new GetProperties(parcel);
			}
			@Override
			public GetProperties[] newArray(int n) {
				return new GetProperties[n];
			}
		};
}
	/**
	 * Puts the system running XBMC into hibernate mode
	 * <p/>
	 * API Name: <code>System.Hibernate</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Hibernate extends AbstractCall<String> { 
		private static final String NAME = "Hibernate";
		/**
		 * Puts the system running XBMC into hibernate mode
		 */
		public Hibernate() {
			super();
		}
		@Override
		protected String parseOne(ObjectNode node) {
			return node.get(RESULT).getTextValue();
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
		/**
		 * Construct via parcel
		 */
		protected Hibernate(Parcel parcel) {
			try {
				mResponse = (ObjectNode)OM.readTree(parcel.readString());
			} catch (JsonProcessingException e) {
				Log.e(NAME, "Error reading JSON object from parcel: " + e.getMessage(), e);
			} catch (IOException e) {
				Log.e(NAME, "I/O exception reading JSON object from parcel: " + e.getMessage(), e);
			}
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Hibernate> CREATOR = new Parcelable.Creator<Hibernate>() {
			@Override
			public Hibernate createFromParcel(Parcel parcel) {
				return new Hibernate(parcel);
			}
			@Override
			public Hibernate[] newArray(int n) {
				return new Hibernate[n];
			}
		};
}
	/**
	 * Reboots the system running XBMC
	 * <p/>
	 * API Name: <code>System.Reboot</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Reboot extends AbstractCall<String> { 
		private static final String NAME = "Reboot";
		/**
		 * Reboots the system running XBMC
		 */
		public Reboot() {
			super();
		}
		@Override
		protected String parseOne(ObjectNode node) {
			return node.get(RESULT).getTextValue();
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
		/**
		 * Construct via parcel
		 */
		protected Reboot(Parcel parcel) {
			try {
				mResponse = (ObjectNode)OM.readTree(parcel.readString());
			} catch (JsonProcessingException e) {
				Log.e(NAME, "Error reading JSON object from parcel: " + e.getMessage(), e);
			} catch (IOException e) {
				Log.e(NAME, "I/O exception reading JSON object from parcel: " + e.getMessage(), e);
			}
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Reboot> CREATOR = new Parcelable.Creator<Reboot>() {
			@Override
			public Reboot createFromParcel(Parcel parcel) {
				return new Reboot(parcel);
			}
			@Override
			public Reboot[] newArray(int n) {
				return new Reboot[n];
			}
		};
}
	/**
	 * Shuts the system running XBMC down
	 * <p/>
	 * API Name: <code>System.Shutdown</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Shutdown extends AbstractCall<String> { 
		private static final String NAME = "Shutdown";
		/**
		 * Shuts the system running XBMC down
		 */
		public Shutdown() {
			super();
		}
		@Override
		protected String parseOne(ObjectNode node) {
			return node.get(RESULT).getTextValue();
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
		/**
		 * Construct via parcel
		 */
		protected Shutdown(Parcel parcel) {
			try {
				mResponse = (ObjectNode)OM.readTree(parcel.readString());
			} catch (JsonProcessingException e) {
				Log.e(NAME, "Error reading JSON object from parcel: " + e.getMessage(), e);
			} catch (IOException e) {
				Log.e(NAME, "I/O exception reading JSON object from parcel: " + e.getMessage(), e);
			}
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Shutdown> CREATOR = new Parcelable.Creator<Shutdown>() {
			@Override
			public Shutdown createFromParcel(Parcel parcel) {
				return new Shutdown(parcel);
			}
			@Override
			public Shutdown[] newArray(int n) {
				return new Shutdown[n];
			}
		};
}
	/**
	 * Suspends the system running XBMC
	 * <p/>
	 * API Name: <code>System.Suspend</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Suspend extends AbstractCall<String> { 
		private static final String NAME = "Suspend";
		/**
		 * Suspends the system running XBMC
		 */
		public Suspend() {
			super();
		}
		@Override
		protected String parseOne(ObjectNode node) {
			return node.get(RESULT).getTextValue();
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
		/**
		 * Construct via parcel
		 */
		protected Suspend(Parcel parcel) {
			try {
				mResponse = (ObjectNode)OM.readTree(parcel.readString());
			} catch (JsonProcessingException e) {
				Log.e(NAME, "Error reading JSON object from parcel: " + e.getMessage(), e);
			} catch (IOException e) {
				Log.e(NAME, "I/O exception reading JSON object from parcel: " + e.getMessage(), e);
			}
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Suspend> CREATOR = new Parcelable.Creator<Suspend>() {
			@Override
			public Suspend createFromParcel(Parcel parcel) {
				return new Suspend(parcel);
			}
			@Override
			public Suspend[] newArray(int n) {
				return new Suspend[n];
			}
		};
}
}