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
import org.codehaus.jackson.node.ObjectNode;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.api.model.ApplicationModel;

public final class Application {

	private final static String PREFIX = "Application.";

	/**
	 * Retrieves the values of the given properties
	 * <p/>
	 * API Name: <code>Application.GetProperties</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetProperties extends AbstractCall<ApplicationModel.PropertyValue> { 
		private static final String NAME = "GetProperties";
		/**
		 * Retrieves the values of the given properties
		 * @param properties One or more of: <tt>volume</tt>, <tt>muted</tt>, <tt>name</tt>, <tt>version</tt>. See constants at {@link ApplicationModel.PropertyName}.
		 * @see ApplicationModel.PropertyName
		 */
		public GetProperties(String... properties) {
			super();
			addParameter("properties", properties);
		}
		@Override
		protected ApplicationModel.PropertyValue parseOne(ObjectNode node) {
			return new ApplicationModel.PropertyValue(node);
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
		protected GetProperties(Parcel parcel) {
			super(parcel);
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
	 * Quit application
	 * <p/>
	 * API Name: <code>Application.Quit</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Quit extends AbstractCall<String> { 
		private static final String NAME = "Quit";
		/**
		 * Quit application
		 */
		public Quit() {
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
		protected Quit(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Quit> CREATOR = new Parcelable.Creator<Quit>() {
			@Override
			public Quit createFromParcel(Parcel parcel) {
				return new Quit(parcel);
			}
			@Override
			public Quit[] newArray(int n) {
				return new Quit[n];
			}
		};
}
	/**
	 * Toggle mute/unmute
	 * <p/>
	 * API Name: <code>Application.SetMute</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class SetMute extends AbstractCall<Boolean> { 
		private static final String NAME = "SetMute";
		/**
		 * Toggle mute/unmute
		 * @param mute 
		 */
		public SetMute(Boolean mute) {
			super();
			addParameter("mute", mute);
		}
		/**
		 * Toggle mute/unmute
		 * @param mute 
		 */
		public SetMute(String mute) {
			super();
			addParameter("mute", mute);
		}
		@Override
		protected Boolean parseOne(ObjectNode node) {
			return node.getBooleanValue();
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
		protected SetMute(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<SetMute> CREATOR = new Parcelable.Creator<SetMute>() {
			@Override
			public SetMute createFromParcel(Parcel parcel) {
				return new SetMute(parcel);
			}
			@Override
			public SetMute[] newArray(int n) {
				return new SetMute[n];
			}
		};
}
	/**
	 * Set the current volume
	 * <p/>
	 * API Name: <code>Application.SetVolume</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class SetVolume extends AbstractCall<Integer> { 
		private static final String NAME = "SetVolume";
		/**
		 * Set the current volume
		 * @param volume 
		 */
		public SetVolume(int volume) {
			super();
			addParameter("volume", volume);
		}
		@Override
		protected Integer parseOne(ObjectNode node) {
			return node.getIntValue();
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
		protected SetVolume(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<SetVolume> CREATOR = new Parcelable.Creator<SetVolume>() {
			@Override
			public SetVolume createFromParcel(Parcel parcel) {
				return new SetVolume(parcel);
			}
			@Override
			public SetVolume[] newArray(int n) {
				return new SetVolume[n];
			}
		};
}
}