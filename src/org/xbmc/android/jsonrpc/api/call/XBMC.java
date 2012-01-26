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
import org.xbmc.android.jsonrpc.api.UndefinedResult;

public final class XBMC {

	private final static String PREFIX = "XBMC.";

	/**
	 * Retrieve info booleans about XBMC and the system
	 * <p/>
	 * API Name: <code>XBMC.GetInfoBooleans</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetInfoBooleans extends AbstractCall<UndefinedResult> { 
		private static final String NAME = "GetInfoBooleans";
		/**
		 * Retrieve info booleans about XBMC and the system
		 * @param booleans 
		 */
		public GetInfoBooleans(String... booleans) {
			super();
			addParameter("booleans", booleans);
		}
		@Override
		protected UndefinedResult parseOne(ObjectNode node) {
			return new UndefinedResult(node);
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
		protected GetInfoBooleans(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetInfoBooleans> CREATOR = new Parcelable.Creator<GetInfoBooleans>() {
			@Override
			public GetInfoBooleans createFromParcel(Parcel parcel) {
				return new GetInfoBooleans(parcel);
			}
			@Override
			public GetInfoBooleans[] newArray(int n) {
				return new GetInfoBooleans[n];
			}
		};
}
	/**
	 * Retrieve info labels about XBMC and the system
	 * <p/>
	 * API Name: <code>XBMC.GetInfoLabels</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetInfoLabels extends AbstractCall<UndefinedResult> { 
		private static final String NAME = "GetInfoLabels";
		/**
		 * Retrieve info labels about XBMC and the system
		 * @param labels Retrieve info labels about XBMC and the system
		 */
		public GetInfoLabels(String... labels) {
			super();
			addParameter("labels", labels);
		}
		@Override
		protected UndefinedResult parseOne(ObjectNode node) {
			return new UndefinedResult(node);
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
		protected GetInfoLabels(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetInfoLabels> CREATOR = new Parcelable.Creator<GetInfoLabels>() {
			@Override
			public GetInfoLabels createFromParcel(Parcel parcel) {
				return new GetInfoLabels(parcel);
			}
			@Override
			public GetInfoLabels[] newArray(int n) {
				return new GetInfoLabels[n];
			}
		};
}
}