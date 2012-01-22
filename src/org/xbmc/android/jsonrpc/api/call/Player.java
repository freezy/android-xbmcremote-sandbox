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

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
		 * @throws JSONException
		 */
		public GetActivePlayers() throws JSONException {
			super();
		}
		@Override
		protected GetActivePlayers.GetActivePlayersResult parseOne(JSONObject obj) throws JSONException {
			return new GetActivePlayers.GetActivePlayersResult(parseResult(obj));
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
			public GetActivePlayersResult(JSONObject obj) throws JSONException {
				playerid = obj.getInt(PLAYERID);
				type = obj.getString(TYPE);
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
			public JSONObject toJSONObject() throws JSONException {
				final JSONObject obj = new JSONObject();
				obj.put(PLAYERID, playerid);
				obj.put(TYPE, type);
				return obj;
			}
			/**
			 * Extracts a list of {@link GetActivePlayersResult} objects from a JSON array.
			 * @param obj JSONObject containing the list of objects
			 * @param key Key pointing to the node where the list is stored
			 */
			static ArrayList<GetActivePlayersResult> getGetActivePlayersResultList(JSONObject obj, String key) throws JSONException {
				if (obj.has(key)) {
					final JSONArray a = obj.getJSONArray(key);
					final ArrayList<GetActivePlayersResult> l = new ArrayList<GetActivePlayersResult>(a.length());
					for (int i = 0; i < a.length(); i++) {
						l.add(new GetActivePlayersResult(a.getJSONObject(i)));
					}
					return l;
				}
				return new ArrayList<GetActivePlayersResult>(0);
			}
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
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
		 * @throws JSONException
		 */
		public GetItem(Integer playerid, String... properties) throws JSONException {
			super();
			addParameter("playerid", playerid);
			addParameter("properties", properties);
		}
		@Override
		protected ListModel.AllItem parseOne(JSONObject obj) throws JSONException {
			return new ListModel.AllItem(parseResult(obj).getJSONObject(RESULTS));
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
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
		 * @throws JSONException
		 */
		public GetProperties(Integer playerid, String... properties) throws JSONException {
			super();
			addParameter("playerid", playerid);
			addParameter("properties", properties);
		}
		@Override
		protected PlayerModel.PropertyValue parseOne(JSONObject obj) throws JSONException {
			return new PlayerModel.PropertyValue(parseResult(obj));
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
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
		 * @throws JSONException
		 */
		public GoNext(Integer playerid) throws JSONException {
			super();
			addParameter("playerid", playerid);
		}
		@Override
		protected String parseOne(JSONObject obj) throws JSONException {
			return obj.getString(RESULT);
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
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
		 * @throws JSONException
		 */
		public GoPrevious(Integer playerid) throws JSONException {
			super();
			addParameter("playerid", playerid);
		}
		@Override
		protected String parseOne(JSONObject obj) throws JSONException {
			return obj.getString(RESULT);
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
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
		 * @throws JSONException
		 */
		public GoTo(Integer playerid, Integer position) throws JSONException {
			super();
			addParameter("playerid", playerid);
			addParameter("position", position);
		}
		@Override
		protected String parseOne(JSONObject obj) throws JSONException {
			return obj.getString(RESULT);
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
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
		 * @throws JSONException
		 */
		public MoveDown(Integer playerid) throws JSONException {
			super();
			addParameter("playerid", playerid);
		}
		@Override
		protected String parseOne(JSONObject obj) throws JSONException {
			return obj.getString(RESULT);
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
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
		 * @throws JSONException
		 */
		public MoveLeft(Integer playerid) throws JSONException {
			super();
			addParameter("playerid", playerid);
		}
		@Override
		protected String parseOne(JSONObject obj) throws JSONException {
			return obj.getString(RESULT);
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
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
		 * @throws JSONException
		 */
		public MoveRight(Integer playerid) throws JSONException {
			super();
			addParameter("playerid", playerid);
		}
		@Override
		protected String parseOne(JSONObject obj) throws JSONException {
			return obj.getString(RESULT);
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
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
		 * @throws JSONException
		 */
		public MoveUp(Integer playerid) throws JSONException {
			super();
			addParameter("playerid", playerid);
		}
		@Override
		protected String parseOne(JSONObject obj) throws JSONException {
			return obj.getString(RESULT);
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
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
		 * @throws JSONException
		 */
		public Open(PlaylistidPosition item) throws JSONException {
			super();
			addParameter("item", item);
		}
		/**
		 * Start playback of either the playlist with the given ID, a slideshow with the pictures from the given directory or a single file or an item from the database.
		 * @param item 
		 * @throws JSONException
		 */
		public Open(PlaylistModel.Item item) throws JSONException {
			super();
			addParameter("item", item);
		}
		/**
		 * Start playback of either the playlist with the given ID, a slideshow with the pictures from the given directory or a single file or an item from the database.
		 * @param item 
		 * @throws JSONException
		 */
		public Open(PathRandomRecursive item) throws JSONException {
			super();
			addParameter("item", item);
		}
		@Override
		protected String parseOne(JSONObject obj) throws JSONException {
			return obj.getString(RESULT);
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
			public JSONObject toJSONObject() throws JSONException {
				final JSONObject obj = new JSONObject();
				obj.put(PLAYLISTID, playlistid);
				obj.put(POSITION, position);
				return obj;
			}
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
			public JSONObject toJSONObject() throws JSONException {
				final JSONObject obj = new JSONObject();
				obj.put(PATH, path);
				obj.put(RANDOM, random);
				obj.put(RECURSIVE, recursive);
				return obj;
			}
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
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
		 * @throws JSONException
		 */
		public PlayPause(Integer playerid) throws JSONException {
			super();
			addParameter("playerid", playerid);
		}
		@Override
		protected PlayerModel.Speed parseOne(JSONObject obj) throws JSONException {
			return new PlayerModel.Speed(parseResult(obj));
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
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
		 * @throws JSONException
		 */
		public Repeat(Integer playerid, String state) throws JSONException {
			super();
			addParameter("playerid", playerid);
			addParameter("state", state);
		}
		@Override
		protected String parseOne(JSONObject obj) throws JSONException {
			return obj.getString(RESULT);
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
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
		 * @throws JSONException
		 */
		public Rotate(Integer playerid) throws JSONException {
			super();
			addParameter("playerid", playerid);
		}
		@Override
		protected String parseOne(JSONObject obj) throws JSONException {
			return obj.getString(RESULT);
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
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
		 * @throws JSONException
		 */
		public Seek(Integer playerid, Double value) throws JSONException {
			super();
			addParameter("playerid", playerid);
			addParameter("value", value);
		}
		/**
		 * Seek through the playing item
		 * @param playerid 
		 * @param value 
		 * @throws JSONException
		 */
		public Seek(Integer playerid, HoursMillisecondsMinutesSeconds value) throws JSONException {
			super();
			addParameter("playerid", playerid);
			addParameter("value", value);
		}
		/**
		 * Seek through the playing item
		 * @param playerid 
		 * @param value 
		 * @throws JSONException
		 */
		public Seek(Integer playerid, String value) throws JSONException {
			super();
			addParameter("playerid", playerid);
			addParameter("value", value);
		}
		@Override
		protected Seek.SeekResult parseOne(JSONObject obj) throws JSONException {
			return new Seek.SeekResult(parseResult(obj));
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
			public SeekResult(JSONObject obj) throws JSONException {
				percentage = parseDouble(obj, PERCENTAGE);
				time = obj.has(TIME) ? new GlobalModel.Time(obj.getJSONObject(TIME)) : null;
				totaltime = obj.has(TOTALTIME) ? new GlobalModel.Time(obj.getJSONObject(TOTALTIME)) : null;
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
			public JSONObject toJSONObject() throws JSONException {
				final JSONObject obj = new JSONObject();
				obj.put(PERCENTAGE, percentage);
				obj.put(TIME, time.toJSONObject());
				obj.put(TOTALTIME, totaltime.toJSONObject());
				return obj;
			}
			/**
			 * Extracts a list of {@link SeekResult} objects from a JSON array.
			 * @param obj JSONObject containing the list of objects
			 * @param key Key pointing to the node where the list is stored
			 */
			static ArrayList<SeekResult> getSeekResultList(JSONObject obj, String key) throws JSONException {
				if (obj.has(key)) {
					final JSONArray a = obj.getJSONArray(key);
					final ArrayList<SeekResult> l = new ArrayList<SeekResult>(a.length());
					for (int i = 0; i < a.length(); i++) {
						l.add(new SeekResult(a.getJSONObject(i)));
					}
					return l;
				}
				return new ArrayList<SeekResult>(0);
			}
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
			public JSONObject toJSONObject() throws JSONException {
				final JSONObject obj = new JSONObject();
				obj.put(HOURS, hours);
				obj.put(MILLISECONDS, milliseconds);
				obj.put(MINUTES, minutes);
				obj.put(SECONDS, seconds);
				return obj;
			}
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
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
		 * @throws JSONException
		 */
		public SetAudioStream(Integer playerid, String stream) throws JSONException {
			super();
			addParameter("playerid", playerid);
			addParameter("stream", stream);
		}
		/**
		 * Set the audio stream played by the player
		 * @param playerid 
		 * @param stream 
		 * @throws JSONException
		 */
		public SetAudioStream(Integer playerid, Integer stream) throws JSONException {
			super();
			addParameter("playerid", playerid);
			addParameter("stream", stream);
		}
		@Override
		protected String parseOne(JSONObject obj) throws JSONException {
			return obj.getString(RESULT);
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
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
		 * @throws JSONException
		 */
		public SetSpeed(Integer playerid, Integer speed) throws JSONException {
			super();
			addParameter("playerid", playerid);
			addParameter("speed", speed);
		}
		/**
		 * Set the speed of the current playback
		 * @param playerid 
		 * @param speed 
		 * @throws JSONException
		 */
		public SetSpeed(Integer playerid, String speed) throws JSONException {
			super();
			addParameter("playerid", playerid);
			addParameter("speed", speed);
		}
		@Override
		protected PlayerModel.Speed parseOne(JSONObject obj) throws JSONException {
			return new PlayerModel.Speed(parseResult(obj));
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
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
		 * @throws JSONException
		 */
		public SetSubtitle(Integer playerid, String subtitle) throws JSONException {
			super();
			addParameter("playerid", playerid);
			addParameter("subtitle", subtitle);
		}
		/**
		 * Set the subtitle displayed by the player
		 * @param playerid 
		 * @param subtitle 
		 * @throws JSONException
		 */
		public SetSubtitle(Integer playerid, Integer subtitle) throws JSONException {
			super();
			addParameter("playerid", playerid);
			addParameter("subtitle", subtitle);
		}
		@Override
		protected String parseOne(JSONObject obj) throws JSONException {
			return obj.getString(RESULT);
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
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
		 * @throws JSONException
		 */
		public Shuffle(Integer playerid) throws JSONException {
			super();
			addParameter("playerid", playerid);
		}
		@Override
		protected String parseOne(JSONObject obj) throws JSONException {
			return obj.getString(RESULT);
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
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
		 * @throws JSONException
		 */
		public Stop(Integer playerid) throws JSONException {
			super();
			addParameter("playerid", playerid);
		}
		@Override
		protected String parseOne(JSONObject obj) throws JSONException {
			return obj.getString(RESULT);
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
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
		 * @throws JSONException
		 */
		public UnShuffle(Integer playerid) throws JSONException {
			super();
			addParameter("playerid", playerid);
		}
		@Override
		protected String parseOne(JSONObject obj) throws JSONException {
			return obj.getString(RESULT);
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
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
		 * @throws JSONException
		 */
		public Zoom(Integer playerid, int value) throws JSONException {
			super();
			addParameter("playerid", playerid);
			addParameter("value", value);
		}
		@Override
		protected String parseOne(JSONObject obj) throws JSONException {
			return obj.getString(RESULT);
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
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
		 * @throws JSONException
		 */
		public ZoomIn(Integer playerid) throws JSONException {
			super();
			addParameter("playerid", playerid);
		}
		@Override
		protected String parseOne(JSONObject obj) throws JSONException {
			return obj.getString(RESULT);
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
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
		 * @throws JSONException
		 */
		public ZoomOut(Integer playerid) throws JSONException {
			super();
			addParameter("playerid", playerid);
		}
		@Override
		protected String parseOne(JSONObject obj) throws JSONException {
			return obj.getString(RESULT);
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
	}
}