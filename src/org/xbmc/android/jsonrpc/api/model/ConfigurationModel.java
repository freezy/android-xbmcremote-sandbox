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

public final class ConfigurationModel {
	/**
	 * Configuration.Notifications
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Notifications extends AbstractModel {
		public final static String API_TYPE = "Configuration.Notifications";
		// field names
		public static final String AUDIOLIBRARY = "audiolibrary";
		public static final String GUI = "gui";
		public static final String OTHER = "other";
		public static final String PLAYER = "player";
		public static final String SYSTEM = "system";
		public static final String VIDEOLIBRARY = "videolibrary";
		// class members
		public final boolean audiolibrary;
		public final boolean gui;
		public final boolean other;
		public final boolean player;
		public final boolean system;
		public final boolean videolibrary;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a Notifications object
		 */
		public Notifications(ObjectNode node) {
			mType = API_TYPE;
			audiolibrary = node.get(AUDIOLIBRARY).getBooleanValue();
			gui = node.get(GUI).getBooleanValue();
			other = node.get(OTHER).getBooleanValue();
			player = node.get(PLAYER).getBooleanValue();
			system = node.get(SYSTEM).getBooleanValue();
			videolibrary = node.get(VIDEOLIBRARY).getBooleanValue();
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param audiolibrary 
		 * @param gui 
		 * @param other 
		 * @param player 
		 * @param system 
		 * @param videolibrary 
		 */
		public Notifications(boolean audiolibrary, boolean gui, boolean other, boolean player, boolean system, boolean videolibrary) {
			this.audiolibrary = audiolibrary;
			this.gui = gui;
			this.other = other;
			this.player = player;
			this.system = system;
			this.videolibrary = videolibrary;
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(AUDIOLIBRARY, audiolibrary);
			node.put(GUI, gui);
			node.put(OTHER, other);
			node.put(PLAYER, player);
			node.put(SYSTEM, system);
			node.put(VIDEOLIBRARY, videolibrary);
			return node;
		}
		/**
		 * Extracts a list of {@link ConfigurationModel.Notifications} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<ConfigurationModel.Notifications> getConfigurationModelNotificationsList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<ConfigurationModel.Notifications> l = new ArrayList<ConfigurationModel.Notifications>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new ConfigurationModel.Notifications((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<ConfigurationModel.Notifications>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeValue(audiolibrary);
			parcel.writeValue(gui);
			parcel.writeValue(other);
			parcel.writeValue(player);
			parcel.writeValue(system);
			parcel.writeValue(videolibrary);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected Notifications(Parcel parcel) {
			audiolibrary = parcel.readInt() == 1;
			gui = parcel.readInt() == 1;
			other = parcel.readInt() == 1;
			player = parcel.readInt() == 1;
			system = parcel.readInt() == 1;
			videolibrary = parcel.readInt() == 1;
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<Notifications> CREATOR = new Parcelable.Creator<Notifications>() {
			@Override
			public Notifications createFromParcel(Parcel parcel) {
				return new Notifications(parcel);
			}
			@Override
			public Notifications[] newArray(int n) {
				return new Notifications[n];
			}
		};
	}
}