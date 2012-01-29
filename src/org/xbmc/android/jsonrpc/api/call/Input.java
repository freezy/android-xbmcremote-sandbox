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

public final class Input {

	private final static String PREFIX = "Input.";

	/**
	 * Goes back in GUI
	 * <p/>
	 * API Name: <code>Input.Back</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Back extends AbstractCall<String> { 
		private static final String NAME = "Back";
		/**
		 * Goes back in GUI
		 */
		public Back() {
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
		protected Back(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Back> CREATOR = new Parcelable.Creator<Back>() {
			@Override
			public Back createFromParcel(Parcel parcel) {
				return new Back(parcel);
			}
			@Override
			public Back[] newArray(int n) {
				return new Back[n];
			}
		};
}
	/**
	 * Navigate down in GUI
	 * <p/>
	 * API Name: <code>Input.Down</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Down extends AbstractCall<String> { 
		private static final String NAME = "Down";
		/**
		 * Navigate down in GUI
		 */
		public Down() {
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
		protected Down(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Down> CREATOR = new Parcelable.Creator<Down>() {
			@Override
			public Down createFromParcel(Parcel parcel) {
				return new Down(parcel);
			}
			@Override
			public Down[] newArray(int n) {
				return new Down[n];
			}
		};
}
	/**
	 * Goes to home window in GUI
	 * <p/>
	 * API Name: <code>Input.Home</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Home extends AbstractCall<String> { 
		private static final String NAME = "Home";
		/**
		 * Goes to home window in GUI
		 */
		public Home() {
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
		protected Home(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Home> CREATOR = new Parcelable.Creator<Home>() {
			@Override
			public Home createFromParcel(Parcel parcel) {
				return new Home(parcel);
			}
			@Override
			public Home[] newArray(int n) {
				return new Home[n];
			}
		};
}
	/**
	 * Navigate left in GUI
	 * <p/>
	 * API Name: <code>Input.Left</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Left extends AbstractCall<String> { 
		private static final String NAME = "Left";
		/**
		 * Navigate left in GUI
		 */
		public Left() {
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
		protected Left(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Left> CREATOR = new Parcelable.Creator<Left>() {
			@Override
			public Left createFromParcel(Parcel parcel) {
				return new Left(parcel);
			}
			@Override
			public Left[] newArray(int n) {
				return new Left[n];
			}
		};
}
	/**
	 * Navigate right in GUI
	 * <p/>
	 * API Name: <code>Input.Right</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Right extends AbstractCall<String> { 
		private static final String NAME = "Right";
		/**
		 * Navigate right in GUI
		 */
		public Right() {
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
		protected Right(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Right> CREATOR = new Parcelable.Creator<Right>() {
			@Override
			public Right createFromParcel(Parcel parcel) {
				return new Right(parcel);
			}
			@Override
			public Right[] newArray(int n) {
				return new Right[n];
			}
		};
}
	/**
	 * Select current item in GUI
	 * <p/>
	 * API Name: <code>Input.Select</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Select extends AbstractCall<String> { 
		private static final String NAME = "Select";
		/**
		 * Select current item in GUI
		 */
		public Select() {
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
		protected Select(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Select> CREATOR = new Parcelable.Creator<Select>() {
			@Override
			public Select createFromParcel(Parcel parcel) {
				return new Select(parcel);
			}
			@Override
			public Select[] newArray(int n) {
				return new Select[n];
			}
		};
}
	/**
	 * Navigate up in GUI
	 * <p/>
	 * API Name: <code>Input.Up</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Up extends AbstractCall<String> { 
		private static final String NAME = "Up";
		/**
		 * Navigate up in GUI
		 */
		public Up() {
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
		protected Up(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Up> CREATOR = new Parcelable.Creator<Up>() {
			@Override
			public Up createFromParcel(Parcel parcel) {
				return new Up(parcel);
			}
			@Override
			public Up[] newArray(int n) {
				return new Up[n];
			}
		};
}
}