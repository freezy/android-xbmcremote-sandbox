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
import org.xbmc.android.jsonrpc.api.model.GlobalModel;
import org.xbmc.android.jsonrpc.api.model.ListModel;
import org.xbmc.android.jsonrpc.api.model.PlayerModel;
import org.xbmc.android.jsonrpc.api.model.PlaylistModel;

public final class Player {

	private final static String PREFIX = "Player.";

	/**
	 * Returns all active players
	 * <p/>
	 * API Name: <code>Player.GetActivePlayers</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetActivePlayers extends AbstractCall<GetActivePlayers.GetActivePlayersResult> { 
		private static final String NAME = "GetActivePlayers";
		/**
		 * Returns all active players
		 */
		public GetActivePlayers() {
			super();
		}
		@Override
		protected GetActivePlayers.GetActivePlayersResult parseOne(ObjectNode node) {
			return new GetActivePlayers.GetActivePlayersResult((ObjectNode)parseResult(node));
		}
		/**
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class GetActivePlayersResult extends AbstractModel {
			// field names
			public static final String PLAYERID = "playerid";
			public static final String TYPE = "type";
			// class members
			public final int playerid;
			public final String type;
			/**
			 * Construct from JSON object.
			 * @param obj JSON object representing a GetActivePlayersResult object
			 */
			public GetActivePlayersResult(ObjectNode node) {
				playerid = node.get(PLAYERID).getIntValue();
				type = node.get(TYPE).getTextValue();
			}
			/**
			 * Construct object with native values for later serialization.
			 * @param playerid 
			 * @param type 
			 */
			public GetActivePlayersResult(int playerid, String type) {
				this.playerid = playerid;
				this.type = type;
			}
			@Override
			public ObjectNode toObjectNode() {
				final ObjectNode node = OM.createObjectNode();
				node.put(PLAYERID, playerid);
				node.put(TYPE, type);
				return node;
			}
			/**
			 * Extracts a list of {@link GetActivePlayersResult} objects from a JSON array.
			 * @param obj ObjectNode containing the list of objects
			 * @param key Key pointing to the node where the list is stored
			 */
			static ArrayList<GetActivePlayersResult> getGetActivePlayersResultList(ObjectNode node, String key) {
				if (node.has(key)) {
					final ArrayNode a = (ArrayNode)node.get(key);
					final ArrayList<GetActivePlayersResult> l = new ArrayList<GetActivePlayersResult>(a.size());
					for (int i = 0; i < a.size(); i++) {
						l.add(new GetActivePlayersResult((ObjectNode)a.get(i)));
					}
					return l;
				}
				return new ArrayList<GetActivePlayersResult>(0);
			}
			/**
			 * Flatten this object into a Parcel.
			 * @param parcel the Parcel in which the object should be written
			 * @param flags additional flags about how the object should be written
			 */
			@Override
			public void writeToParcel(Parcel parcel, int flags) {
				parcel.writeValue(playerid);
				parcel.writeValue(type);
			}
			@Override
			public int describeContents() {
				return 0;
			}
			/**
			 * Construct via parcel
			 */
			protected GetActivePlayersResult(Parcel parcel) {
				playerid = parcel.readInt();
				type = parcel.readString();
			}
			/**
			 * Generates instances of this Parcelable class from a Parcel.
			 */
			public static final Parcelable.Creator<GetActivePlayersResult> CREATOR = new Parcelable.Creator<GetActivePlayersResult>() {
				@Override
				public GetActivePlayersResult createFromParcel(Parcel parcel) {
					return new GetActivePlayersResult(parcel);
				}
				@Override
				public GetActivePlayersResult[] newArray(int n) {
					return new GetActivePlayersResult[n];
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
		protected GetActivePlayers(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetActivePlayers> CREATOR = new Parcelable.Creator<GetActivePlayers>() {
			@Override
			public GetActivePlayers createFromParcel(Parcel parcel) {
				return new GetActivePlayers(parcel);
			}
			@Override
			public GetActivePlayers[] newArray(int n) {
				return new GetActivePlayers[n];
			}
		};
}
	/**
	 * Retrieves the currently played item
	 * <p/>
	 * API Name: <code>Player.GetItem</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetItem extends AbstractCall<ListModel.AllItem> { 
		private static final String NAME = "GetItem";
		public static final String RESULTS = "item";
		/**
		 * Retrieves the currently played item
		 * @param playerid 
		 * @param properties One or more of: <tt>title</tt>, <tt>artist</tt>, <tt>albumartist</tt>, <tt>genre</tt>, <tt>year</tt>, <tt>rating</tt>, <tt>album</tt>, <tt>track</tt>, <tt>duration</tt>, <tt>comment</tt>, <tt>lyrics</tt>, <tt>musicbrainztrackid</tt>, <tt>musicbrainzartistid</tt>, <tt>musicbrainzalbumid</tt>, <tt>musicbrainzalbumartistid</tt>, <tt>playcount</tt>, <tt>fanart</tt>, <tt>director</tt>, <tt>trailer</tt>, <tt>tagline</tt>, <tt>plot</tt>, <tt>plotoutline</tt>, <tt>originaltitle</tt>, <tt>lastplayed</tt>, <tt>writer</tt>, <tt>studio</tt>, <tt>mpaa</tt>, <tt>cast</tt>, <tt>country</tt>, <tt>imdbnumber</tt>, <tt>premiered</tt>, <tt>productioncode</tt>, <tt>runtime</tt>, <tt>set</tt>, <tt>showlink</tt>, <tt>streamdetails</tt>, <tt>top250</tt>, <tt>votes</tt>, <tt>firstaired</tt>, <tt>season</tt>, <tt>episode</tt>, <tt>showtitle</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>resume</tt>, <tt>artistid</tt>, <tt>albumid</tt>, <tt>tvshowid</tt>, <tt>setid</tt>. See constants at {@link ListModel.AllFields}.
		 * @see ListModel.AllFields
		 */
		public GetItem(Integer playerid, String... properties) {
			super();
			addParameter("playerid", playerid);
			addParameter("properties", properties);
		}
		@Override
		protected ListModel.AllItem parseOne(ObjectNode node) {
			return new ListModel.AllItem((ObjectNode)parseResult(node).get(RESULTS));
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
		protected GetItem(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetItem> CREATOR = new Parcelable.Creator<GetItem>() {
			@Override
			public GetItem createFromParcel(Parcel parcel) {
				return new GetItem(parcel);
			}
			@Override
			public GetItem[] newArray(int n) {
				return new GetItem[n];
			}
		};
}
	/**
	 * Retrieves the values of the given properties
	 * <p/>
	 * API Name: <code>Player.GetProperties</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetProperties extends AbstractCall<PlayerModel.PropertyValue> { 
		private static final String NAME = "GetProperties";
		/**
		 * Retrieves the values of the given properties
		 * @param playerid 
		 * @param properties One or more of: <tt>type</tt>, <tt>partymode</tt>, <tt>speed</tt>, <tt>time</tt>, <tt>percentage</tt>, <tt>totaltime</tt>, <tt>playlistid</tt>, <tt>position</tt>, <tt>repeat</tt>, <tt>shuffled</tt>, <tt>canseek</tt>, <tt>canchangespeed</tt>, <tt>canmove</tt>, <tt>canzoom</tt>, <tt>canrotate</tt>, <tt>canshuffle</tt>, <tt>canrepeat</tt>, <tt>currentaudiostream</tt>, <tt>audiostreams</tt>, <tt>subtitleenabled</tt>, <tt>currentsubtitle</tt>, <tt>subtitles</tt>. See constants at {@link PlayerModel.PropertyName}.
		 * @see PlayerModel.PropertyName
		 */
		public GetProperties(Integer playerid, String... properties) {
			super();
			addParameter("playerid", playerid);
			addParameter("properties", properties);
		}
		@Override
		protected PlayerModel.PropertyValue parseOne(ObjectNode node) {
			return new PlayerModel.PropertyValue((ObjectNode)parseResult(node));
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
	 * Go to next item on the playlist
	 * <p/>
	 * API Name: <code>Player.GoNext</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GoNext extends AbstractCall<String> { 
		private static final String NAME = "GoNext";
		/**
		 * Go to next item on the playlist
		 * @param playerid 
		 */
		public GoNext(Integer playerid) {
			super();
			addParameter("playerid", playerid);
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
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(mResult);
		}
		/**
		 * Construct via parcel
		 */
		protected GoNext(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GoNext> CREATOR = new Parcelable.Creator<GoNext>() {
			@Override
			public GoNext createFromParcel(Parcel parcel) {
				return new GoNext(parcel);
			}
			@Override
			public GoNext[] newArray(int n) {
				return new GoNext[n];
			}
		};
}
	/**
	 * Go to previous item on the playlist
	 * <p/>
	 * API Name: <code>Player.GoPrevious</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GoPrevious extends AbstractCall<String> { 
		private static final String NAME = "GoPrevious";
		/**
		 * Go to previous item on the playlist
		 * @param playerid 
		 */
		public GoPrevious(Integer playerid) {
			super();
			addParameter("playerid", playerid);
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
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(mResult);
		}
		/**
		 * Construct via parcel
		 */
		protected GoPrevious(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GoPrevious> CREATOR = new Parcelable.Creator<GoPrevious>() {
			@Override
			public GoPrevious createFromParcel(Parcel parcel) {
				return new GoPrevious(parcel);
			}
			@Override
			public GoPrevious[] newArray(int n) {
				return new GoPrevious[n];
			}
		};
}
	/**
	 * Go to item at the given position in the playlist
	 * <p/>
	 * API Name: <code>Player.GoTo</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GoTo extends AbstractCall<String> { 
		private static final String NAME = "GoTo";
		/**
		 * Go to item at the given position in the playlist
		 * @param playerid 
		 * @param position 
		 */
		public GoTo(Integer playerid, Integer position) {
			super();
			addParameter("playerid", playerid);
			addParameter("position", position);
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
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(mResult);
		}
		/**
		 * Construct via parcel
		 */
		protected GoTo(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GoTo> CREATOR = new Parcelable.Creator<GoTo>() {
			@Override
			public GoTo createFromParcel(Parcel parcel) {
				return new GoTo(parcel);
			}
			@Override
			public GoTo[] newArray(int n) {
				return new GoTo[n];
			}
		};
}
	/**
	 * If picture is zoomed move viewport down
	 * <p/>
	 * API Name: <code>Player.MoveDown</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class MoveDown extends AbstractCall<String> { 
		private static final String NAME = "MoveDown";
		/**
		 * If picture is zoomed move viewport down
		 * @param playerid 
		 */
		public MoveDown(Integer playerid) {
			super();
			addParameter("playerid", playerid);
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
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(mResult);
		}
		/**
		 * Construct via parcel
		 */
		protected MoveDown(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<MoveDown> CREATOR = new Parcelable.Creator<MoveDown>() {
			@Override
			public MoveDown createFromParcel(Parcel parcel) {
				return new MoveDown(parcel);
			}
			@Override
			public MoveDown[] newArray(int n) {
				return new MoveDown[n];
			}
		};
}
	/**
	 * If picture is zoomed move viewport left otherwise skip previous
	 * <p/>
	 * API Name: <code>Player.MoveLeft</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class MoveLeft extends AbstractCall<String> { 
		private static final String NAME = "MoveLeft";
		/**
		 * If picture is zoomed move viewport left otherwise skip previous
		 * @param playerid 
		 */
		public MoveLeft(Integer playerid) {
			super();
			addParameter("playerid", playerid);
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
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(mResult);
		}
		/**
		 * Construct via parcel
		 */
		protected MoveLeft(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<MoveLeft> CREATOR = new Parcelable.Creator<MoveLeft>() {
			@Override
			public MoveLeft createFromParcel(Parcel parcel) {
				return new MoveLeft(parcel);
			}
			@Override
			public MoveLeft[] newArray(int n) {
				return new MoveLeft[n];
			}
		};
}
	/**
	 * If picture is zoomed move viewport right otherwise skip next
	 * <p/>
	 * API Name: <code>Player.MoveRight</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class MoveRight extends AbstractCall<String> { 
		private static final String NAME = "MoveRight";
		/**
		 * If picture is zoomed move viewport right otherwise skip next
		 * @param playerid 
		 */
		public MoveRight(Integer playerid) {
			super();
			addParameter("playerid", playerid);
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
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(mResult);
		}
		/**
		 * Construct via parcel
		 */
		protected MoveRight(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<MoveRight> CREATOR = new Parcelable.Creator<MoveRight>() {
			@Override
			public MoveRight createFromParcel(Parcel parcel) {
				return new MoveRight(parcel);
			}
			@Override
			public MoveRight[] newArray(int n) {
				return new MoveRight[n];
			}
		};
}
	/**
	 * If picture is zoomed move viewport up
	 * <p/>
	 * API Name: <code>Player.MoveUp</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class MoveUp extends AbstractCall<String> { 
		private static final String NAME = "MoveUp";
		/**
		 * If picture is zoomed move viewport up
		 * @param playerid 
		 */
		public MoveUp(Integer playerid) {
			super();
			addParameter("playerid", playerid);
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
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(mResult);
		}
		/**
		 * Construct via parcel
		 */
		protected MoveUp(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<MoveUp> CREATOR = new Parcelable.Creator<MoveUp>() {
			@Override
			public MoveUp createFromParcel(Parcel parcel) {
				return new MoveUp(parcel);
			}
			@Override
			public MoveUp[] newArray(int n) {
				return new MoveUp[n];
			}
		};
}
	/**
	 * Start playback of either the playlist with the given ID, a slideshow with the pictures from the given directory or a single file or an item from the database.
	 * <p/>
	 * API Name: <code>Player.Open</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Open extends AbstractCall<String> { 
		private static final String NAME = "Open";
		/**
		 * Start playback of either the playlist with the given ID, a slideshow with the pictures from the given directory or a single file or an item from the database.
		 * @param item 
		 */
		public Open(PlaylistidPosition item) {
			super();
			addParameter("item", item);
		}
		/**
		 * Start playback of either the playlist with the given ID, a slideshow with the pictures from the given directory or a single file or an item from the database.
		 * @param item 
		 */
		public Open(PlaylistModel.Item item) {
			super();
			addParameter("item", item);
		}
		/**
		 * Start playback of either the playlist with the given ID, a slideshow with the pictures from the given directory or a single file or an item from the database.
		 * @param item 
		 */
		public Open(PathRandomRecursive item) {
			super();
			addParameter("item", item);
		}
		@Override
		protected String parseOne(ObjectNode node) {
			return node.get(RESULT).getTextValue();
		}
		/**
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class PlaylistidPosition extends AbstractModel {
			// field names
			public static final String PLAYLISTID = "playlistid";
			public static final String POSITION = "position";
			// class members
			public final int playlistid;
			public final Integer position;
			/**
			 * Construct object with native values for later serialization.
			 * @param playlistid 
			 * @param position 
			 */
			public PlaylistidPosition(int playlistid, Integer position) {
				this.playlistid = playlistid;
				this.position = position;
			}
			@Override
			public ObjectNode toObjectNode() {
				final ObjectNode node = OM.createObjectNode();
				node.put(PLAYLISTID, playlistid);
				node.put(POSITION, position);
				return node;
			}
			/**
			 * Flatten this object into a Parcel.
			 * @param parcel the Parcel in which the object should be written
			 * @param flags additional flags about how the object should be written
			 */
			@Override
			public void writeToParcel(Parcel parcel, int flags) {
				parcel.writeValue(playlistid);
				parcel.writeValue(position);
			}
			@Override
			public int describeContents() {
				return 0;
			}
			/**
			 * Construct via parcel
			 */
			protected PlaylistidPosition(Parcel parcel) {
				playlistid = parcel.readInt();
				position = parcel.readInt();
			}
			/**
			 * Generates instances of this Parcelable class from a Parcel.
			 */
			public static final Parcelable.Creator<PlaylistidPosition> CREATOR = new Parcelable.Creator<PlaylistidPosition>() {
				@Override
				public PlaylistidPosition createFromParcel(Parcel parcel) {
					return new PlaylistidPosition(parcel);
				}
				@Override
				public PlaylistidPosition[] newArray(int n) {
					return new PlaylistidPosition[n];
				}
			};
		}
		/**
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class PathRandomRecursive extends AbstractModel {
			// field names
			public static final String PATH = "path";
			public static final String RANDOM = "random";
			public static final String RECURSIVE = "recursive";
			// class members
			public final String path;
			public final Boolean random;
			public final Boolean recursive;
			/**
			 * Construct object with native values for later serialization.
			 * @param path 
			 * @param random 
			 * @param recursive 
			 */
			public PathRandomRecursive(String path, Boolean random, Boolean recursive) {
				this.path = path;
				this.random = random;
				this.recursive = recursive;
			}
			@Override
			public ObjectNode toObjectNode() {
				final ObjectNode node = OM.createObjectNode();
				node.put(PATH, path);
				node.put(RANDOM, random);
				node.put(RECURSIVE, recursive);
				return node;
			}
			/**
			 * Flatten this object into a Parcel.
			 * @param parcel the Parcel in which the object should be written
			 * @param flags additional flags about how the object should be written
			 */
			@Override
			public void writeToParcel(Parcel parcel, int flags) {
				parcel.writeValue(path);
				parcel.writeValue(random);
				parcel.writeValue(recursive);
			}
			@Override
			public int describeContents() {
				return 0;
			}
			/**
			 * Construct via parcel
			 */
			protected PathRandomRecursive(Parcel parcel) {
				path = parcel.readString();
				random = parcel.readInt() == 1;
				recursive = parcel.readInt() == 1;
			}
			/**
			 * Generates instances of this Parcelable class from a Parcel.
			 */
			public static final Parcelable.Creator<PathRandomRecursive> CREATOR = new Parcelable.Creator<PathRandomRecursive>() {
				@Override
				public PathRandomRecursive createFromParcel(Parcel parcel) {
					return new PathRandomRecursive(parcel);
				}
				@Override
				public PathRandomRecursive[] newArray(int n) {
					return new PathRandomRecursive[n];
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
			parcel.writeValue(mResult);
		}
		/**
		 * Construct via parcel
		 */
		protected Open(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Open> CREATOR = new Parcelable.Creator<Open>() {
			@Override
			public Open createFromParcel(Parcel parcel) {
				return new Open(parcel);
			}
			@Override
			public Open[] newArray(int n) {
				return new Open[n];
			}
		};
}
	/**
	 * Pauses or unpause playback and returns the new state
	 * <p/>
	 * API Name: <code>Player.PlayPause</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class PlayPause extends AbstractCall<PlayerModel.Speed> { 
		private static final String NAME = "PlayPause";
		/**
		 * Pauses or unpause playback and returns the new state
		 * @param playerid 
		 */
		public PlayPause(Integer playerid) {
			super();
			addParameter("playerid", playerid);
		}
		@Override
		protected PlayerModel.Speed parseOne(ObjectNode node) {
			return new PlayerModel.Speed((ObjectNode)parseResult(node));
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
		protected PlayPause(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<PlayPause> CREATOR = new Parcelable.Creator<PlayPause>() {
			@Override
			public PlayPause createFromParcel(Parcel parcel) {
				return new PlayPause(parcel);
			}
			@Override
			public PlayPause[] newArray(int n) {
				return new PlayPause[n];
			}
		};
}
	/**
	 * Set the repeat mode of the player
	 * <p/>
	 * API Name: <code>Player.Repeat</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Repeat extends AbstractCall<String> { 
		private static final String NAME = "Repeat";
		/**
		 * Set the repeat mode of the player
		 * @param playerid 
		 * @param state One of: <tt>off</tt>, <tt>one</tt>, <tt>all</tt>. See constants at {@link PlayerModel.Repeat}.
		 * @see PlayerModel.Repeat
		 */
		public Repeat(Integer playerid, String state) {
			super();
			addParameter("playerid", playerid);
			addParameter("state", state);
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
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(mResult);
		}
		/**
		 * Construct via parcel
		 */
		protected Repeat(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Repeat> CREATOR = new Parcelable.Creator<Repeat>() {
			@Override
			public Repeat createFromParcel(Parcel parcel) {
				return new Repeat(parcel);
			}
			@Override
			public Repeat[] newArray(int n) {
				return new Repeat[n];
			}
		};
}
	/**
	 * Rotates current picture
	 * <p/>
	 * API Name: <code>Player.Rotate</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Rotate extends AbstractCall<String> { 
		private static final String NAME = "Rotate";
		/**
		 * Rotates current picture
		 * @param playerid 
		 */
		public Rotate(Integer playerid) {
			super();
			addParameter("playerid", playerid);
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
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(mResult);
		}
		/**
		 * Construct via parcel
		 */
		protected Rotate(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Rotate> CREATOR = new Parcelable.Creator<Rotate>() {
			@Override
			public Rotate createFromParcel(Parcel parcel) {
				return new Rotate(parcel);
			}
			@Override
			public Rotate[] newArray(int n) {
				return new Rotate[n];
			}
		};
}
	/**
	 * Seek through the playing item
	 * <p/>
	 * API Name: <code>Player.Seek</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Seek extends AbstractCall<Seek.SeekResult> { 
		private static final String NAME = "Seek";
		/**
		 * Seek through the playing item
		 * @param playerid 
		 * @param value 
		 */
		public Seek(Integer playerid, Double value) {
			super();
			addParameter("playerid", playerid);
			addParameter("value", value);
		}
		/**
		 * Seek through the playing item
		 * @param playerid 
		 * @param value 
		 */
		public Seek(Integer playerid, HoursMillisecondsMinutesSeconds value) {
			super();
			addParameter("playerid", playerid);
			addParameter("value", value);
		}
		/**
		 * Seek through the playing item
		 * @param playerid 
		 * @param value 
		 */
		public Seek(Integer playerid, String value) {
			super();
			addParameter("playerid", playerid);
			addParameter("value", value);
		}
		@Override
		protected Seek.SeekResult parseOne(ObjectNode node) {
			return new Seek.SeekResult((ObjectNode)parseResult(node));
		}
		/**
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class SeekResult extends AbstractModel {
			// field names
			public static final String PERCENTAGE = "percentage";
			public static final String TIME = "time";
			public static final String TOTALTIME = "totaltime";
			// class members
			public final Double percentage;
			public final GlobalModel.Time time;
			public final GlobalModel.Time totaltime;
			/**
			 * Construct from JSON object.
			 * @param obj JSON object representing a SeekResult object
			 */
			public SeekResult(ObjectNode node) {
				percentage = parseDouble(node, PERCENTAGE);
				time = node.has(TIME) ? new GlobalModel.Time((ObjectNode)node.get(TIME)) : null;
				totaltime = node.has(TOTALTIME) ? new GlobalModel.Time((ObjectNode)node.get(TOTALTIME)) : null;
			}
			/**
			 * Construct object with native values for later serialization.
			 * @param percentage 
			 * @param time 
			 * @param totaltime 
			 */
			public SeekResult(Double percentage, GlobalModel.Time time, GlobalModel.Time totaltime) {
				this.percentage = percentage;
				this.time = time;
				this.totaltime = totaltime;
			}
			@Override
			public ObjectNode toObjectNode() {
				final ObjectNode node = OM.createObjectNode();
				node.put(PERCENTAGE, percentage);
				node.put(TIME, time.toObjectNode());
				node.put(TOTALTIME, totaltime.toObjectNode());
				return node;
			}
			/**
			 * Extracts a list of {@link SeekResult} objects from a JSON array.
			 * @param obj ObjectNode containing the list of objects
			 * @param key Key pointing to the node where the list is stored
			 */
			static ArrayList<SeekResult> getSeekResultList(ObjectNode node, String key) {
				if (node.has(key)) {
					final ArrayNode a = (ArrayNode)node.get(key);
					final ArrayList<SeekResult> l = new ArrayList<SeekResult>(a.size());
					for (int i = 0; i < a.size(); i++) {
						l.add(new SeekResult((ObjectNode)a.get(i)));
					}
					return l;
				}
				return new ArrayList<SeekResult>(0);
			}
			/**
			 * Flatten this object into a Parcel.
			 * @param parcel the Parcel in which the object should be written
			 * @param flags additional flags about how the object should be written
			 */
			@Override
			public void writeToParcel(Parcel parcel, int flags) {
				parcel.writeValue(percentage);
				parcel.writeValue(time);
				parcel.writeValue(totaltime);
			}
			@Override
			public int describeContents() {
				return 0;
			}
			/**
			 * Construct via parcel
			 */
			protected SeekResult(Parcel parcel) {
				percentage = parcel.readDouble();
				time = parcel.<GlobalModel.Time>readParcelable(GlobalModel.Time.class.getClassLoader());
				totaltime = parcel.<GlobalModel.Time>readParcelable(GlobalModel.Time.class.getClassLoader());
			}
			/**
			 * Generates instances of this Parcelable class from a Parcel.
			 */
			public static final Parcelable.Creator<SeekResult> CREATOR = new Parcelable.Creator<SeekResult>() {
				@Override
				public SeekResult createFromParcel(Parcel parcel) {
					return new SeekResult(parcel);
				}
				@Override
				public SeekResult[] newArray(int n) {
					return new SeekResult[n];
				}
			};
		}
		/**
			 * Time to seek to
		 * <p/>
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class HoursMillisecondsMinutesSeconds extends AbstractModel {
			// field names
			public static final String HOURS = "hours";
			public static final String MILLISECONDS = "milliseconds";
			public static final String MINUTES = "minutes";
			public static final String SECONDS = "seconds";
			// class members
			public final Integer hours;
			public final Integer milliseconds;
			public final Integer minutes;
			public final Integer seconds;
			/**
			 * Construct object with native values for later serialization.
			 * @param hours 
			 * @param milliseconds 
			 * @param minutes 
			 * @param seconds 
			 */
			public HoursMillisecondsMinutesSeconds(Integer hours, Integer milliseconds, Integer minutes, Integer seconds) {
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
			protected HoursMillisecondsMinutesSeconds(Parcel parcel) {
				hours = parcel.readInt();
				milliseconds = parcel.readInt();
				minutes = parcel.readInt();
				seconds = parcel.readInt();
			}
			/**
			 * Generates instances of this Parcelable class from a Parcel.
			 */
			public static final Parcelable.Creator<HoursMillisecondsMinutesSeconds> CREATOR = new Parcelable.Creator<HoursMillisecondsMinutesSeconds>() {
				@Override
				public HoursMillisecondsMinutesSeconds createFromParcel(Parcel parcel) {
					return new HoursMillisecondsMinutesSeconds(parcel);
				}
				@Override
				public HoursMillisecondsMinutesSeconds[] newArray(int n) {
					return new HoursMillisecondsMinutesSeconds[n];
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
		protected Seek(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Seek> CREATOR = new Parcelable.Creator<Seek>() {
			@Override
			public Seek createFromParcel(Parcel parcel) {
				return new Seek(parcel);
			}
			@Override
			public Seek[] newArray(int n) {
				return new Seek[n];
			}
		};
}
	/**
	 * Set the audio stream played by the player
	 * <p/>
	 * API Name: <code>Player.SetAudioStream</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class SetAudioStream extends AbstractCall<String> { 
		private static final String NAME = "SetAudioStream";
		/**
		 * Set the audio stream played by the player
		 * @param playerid 
		 * @param stream 
		 */
		public SetAudioStream(Integer playerid, String stream) {
			super();
			addParameter("playerid", playerid);
			addParameter("stream", stream);
		}
		/**
		 * Set the audio stream played by the player
		 * @param playerid 
		 * @param stream 
		 */
		public SetAudioStream(Integer playerid, Integer stream) {
			super();
			addParameter("playerid", playerid);
			addParameter("stream", stream);
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
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(mResult);
		}
		/**
		 * Construct via parcel
		 */
		protected SetAudioStream(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<SetAudioStream> CREATOR = new Parcelable.Creator<SetAudioStream>() {
			@Override
			public SetAudioStream createFromParcel(Parcel parcel) {
				return new SetAudioStream(parcel);
			}
			@Override
			public SetAudioStream[] newArray(int n) {
				return new SetAudioStream[n];
			}
		};
}
	/**
	 * Set the speed of the current playback
	 * <p/>
	 * API Name: <code>Player.SetSpeed</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class SetSpeed extends AbstractCall<PlayerModel.Speed> { 
		private static final String NAME = "SetSpeed";
		/**
		 * Set the speed of the current playback
		 * @param playerid 
		 * @param speed 
		 */
		public SetSpeed(Integer playerid, Integer speed) {
			super();
			addParameter("playerid", playerid);
			addParameter("speed", speed);
		}
		/**
		 * Set the speed of the current playback
		 * @param playerid 
		 * @param speed 
		 */
		public SetSpeed(Integer playerid, String speed) {
			super();
			addParameter("playerid", playerid);
			addParameter("speed", speed);
		}
		@Override
		protected PlayerModel.Speed parseOne(ObjectNode node) {
			return new PlayerModel.Speed((ObjectNode)parseResult(node));
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
		protected SetSpeed(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<SetSpeed> CREATOR = new Parcelable.Creator<SetSpeed>() {
			@Override
			public SetSpeed createFromParcel(Parcel parcel) {
				return new SetSpeed(parcel);
			}
			@Override
			public SetSpeed[] newArray(int n) {
				return new SetSpeed[n];
			}
		};
}
	/**
	 * Set the subtitle displayed by the player
	 * <p/>
	 * API Name: <code>Player.SetSubtitle</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class SetSubtitle extends AbstractCall<String> { 
		private static final String NAME = "SetSubtitle";
		/**
		 * Set the subtitle displayed by the player
		 * @param playerid 
		 * @param subtitle 
		 */
		public SetSubtitle(Integer playerid, String subtitle) {
			super();
			addParameter("playerid", playerid);
			addParameter("subtitle", subtitle);
		}
		/**
		 * Set the subtitle displayed by the player
		 * @param playerid 
		 * @param subtitle 
		 */
		public SetSubtitle(Integer playerid, Integer subtitle) {
			super();
			addParameter("playerid", playerid);
			addParameter("subtitle", subtitle);
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
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(mResult);
		}
		/**
		 * Construct via parcel
		 */
		protected SetSubtitle(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<SetSubtitle> CREATOR = new Parcelable.Creator<SetSubtitle>() {
			@Override
			public SetSubtitle createFromParcel(Parcel parcel) {
				return new SetSubtitle(parcel);
			}
			@Override
			public SetSubtitle[] newArray(int n) {
				return new SetSubtitle[n];
			}
		};
}
	/**
	 * Shuffle items in the player
	 * <p/>
	 * API Name: <code>Player.Shuffle</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Shuffle extends AbstractCall<String> { 
		private static final String NAME = "Shuffle";
		/**
		 * Shuffle items in the player
		 * @param playerid 
		 */
		public Shuffle(Integer playerid) {
			super();
			addParameter("playerid", playerid);
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
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(mResult);
		}
		/**
		 * Construct via parcel
		 */
		protected Shuffle(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Shuffle> CREATOR = new Parcelable.Creator<Shuffle>() {
			@Override
			public Shuffle createFromParcel(Parcel parcel) {
				return new Shuffle(parcel);
			}
			@Override
			public Shuffle[] newArray(int n) {
				return new Shuffle[n];
			}
		};
}
	/**
	 * Stops playback
	 * <p/>
	 * API Name: <code>Player.Stop</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Stop extends AbstractCall<String> { 
		private static final String NAME = "Stop";
		/**
		 * Stops playback
		 * @param playerid 
		 */
		public Stop(Integer playerid) {
			super();
			addParameter("playerid", playerid);
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
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(mResult);
		}
		/**
		 * Construct via parcel
		 */
		protected Stop(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Stop> CREATOR = new Parcelable.Creator<Stop>() {
			@Override
			public Stop createFromParcel(Parcel parcel) {
				return new Stop(parcel);
			}
			@Override
			public Stop[] newArray(int n) {
				return new Stop[n];
			}
		};
}
	/**
	 * Unshuffle items in the player
	 * <p/>
	 * API Name: <code>Player.UnShuffle</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class UnShuffle extends AbstractCall<String> { 
		private static final String NAME = "UnShuffle";
		/**
		 * Unshuffle items in the player
		 * @param playerid 
		 */
		public UnShuffle(Integer playerid) {
			super();
			addParameter("playerid", playerid);
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
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(mResult);
		}
		/**
		 * Construct via parcel
		 */
		protected UnShuffle(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<UnShuffle> CREATOR = new Parcelable.Creator<UnShuffle>() {
			@Override
			public UnShuffle createFromParcel(Parcel parcel) {
				return new UnShuffle(parcel);
			}
			@Override
			public UnShuffle[] newArray(int n) {
				return new UnShuffle[n];
			}
		};
}
	/**
	 * Zooms current picture
	 * <p/>
	 * API Name: <code>Player.Zoom</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Zoom extends AbstractCall<String> { 
		private static final String NAME = "Zoom";
		/**
		 * Zooms current picture
		 * @param playerid 
		 * @param value Zooms current picture
		 */
		public Zoom(Integer playerid, int value) {
			super();
			addParameter("playerid", playerid);
			addParameter("value", value);
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
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(mResult);
		}
		/**
		 * Construct via parcel
		 */
		protected Zoom(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Zoom> CREATOR = new Parcelable.Creator<Zoom>() {
			@Override
			public Zoom createFromParcel(Parcel parcel) {
				return new Zoom(parcel);
			}
			@Override
			public Zoom[] newArray(int n) {
				return new Zoom[n];
			}
		};
}
	/**
	 * Zoom in once
	 * <p/>
	 * API Name: <code>Player.ZoomIn</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class ZoomIn extends AbstractCall<String> { 
		private static final String NAME = "ZoomIn";
		/**
		 * Zoom in once
		 * @param playerid 
		 */
		public ZoomIn(Integer playerid) {
			super();
			addParameter("playerid", playerid);
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
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(mResult);
		}
		/**
		 * Construct via parcel
		 */
		protected ZoomIn(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<ZoomIn> CREATOR = new Parcelable.Creator<ZoomIn>() {
			@Override
			public ZoomIn createFromParcel(Parcel parcel) {
				return new ZoomIn(parcel);
			}
			@Override
			public ZoomIn[] newArray(int n) {
				return new ZoomIn[n];
			}
		};
}
	/**
	 * Zoom out once
	 * <p/>
	 * API Name: <code>Player.ZoomOut</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class ZoomOut extends AbstractCall<String> { 
		private static final String NAME = "ZoomOut";
		/**
		 * Zoom out once
		 * @param playerid 
		 */
		public ZoomOut(Integer playerid) {
			super();
			addParameter("playerid", playerid);
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
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(mResult);
		}
		/**
		 * Construct via parcel
		 */
		protected ZoomOut(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<ZoomOut> CREATOR = new Parcelable.Creator<ZoomOut>() {
			@Override
			public ZoomOut createFromParcel(Parcel parcel) {
				return new ZoomOut(parcel);
			}
			@Override
			public ZoomOut[] newArray(int n) {
				return new ZoomOut[n];
			}
		};
}
}