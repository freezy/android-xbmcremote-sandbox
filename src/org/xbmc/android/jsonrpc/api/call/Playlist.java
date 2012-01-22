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
import org.xbmc.android.jsonrpc.api.model.ListModel;
import org.xbmc.android.jsonrpc.api.model.PlaylistModel;

public final class Playlist {

	private final static String PREFIX = "Playlist.";

	/**
	 * Add item(s) to playlist
	 * <p/>
	 * API Name: <code>Playlist.Add</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Add extends AbstractCall<String> { 
		private static final String NAME = "Add";
		/**
		 * Add item(s) to playlist
		 * @param playlistid 
		 * @param item 
		 * @throws JSONException
		 */
		public Add(Integer playlistid, PlaylistModel.Item item) throws JSONException {
			super();
			addParameter("playlistid", playlistid);
			addParameter("item", item);
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
	 * Clear playlist
	 * <p/>
	 * API Name: <code>Playlist.Clear</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Clear extends AbstractCall<String> { 
		private static final String NAME = "Clear";
		/**
		 * Clear playlist
		 * @param playlistid 
		 * @throws JSONException
		 */
		public Clear(Integer playlistid) throws JSONException {
			super();
			addParameter("playlistid", playlistid);
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
	 * Get all items from playlist
	 * <p/>
	 * API Name: <code>Playlist.GetItems</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetItems extends AbstractCall<ListModel.AllItem> { 
		private static final String NAME = "GetItems";
		public static final String RESULTS = "items";
		/**
		 * Get all items from playlist
		 * @param playlistid 
		 * @param properties One or more of: <tt>title</tt>, <tt>artist</tt>, <tt>albumartist</tt>, <tt>genre</tt>, <tt>year</tt>, <tt>rating</tt>, <tt>album</tt>, <tt>track</tt>, <tt>duration</tt>, <tt>comment</tt>, <tt>lyrics</tt>, <tt>musicbrainztrackid</tt>, <tt>musicbrainzartistid</tt>, <tt>musicbrainzalbumid</tt>, <tt>musicbrainzalbumartistid</tt>, <tt>playcount</tt>, <tt>fanart</tt>, <tt>director</tt>, <tt>trailer</tt>, <tt>tagline</tt>, <tt>plot</tt>, <tt>plotoutline</tt>, <tt>originaltitle</tt>, <tt>lastplayed</tt>, <tt>writer</tt>, <tt>studio</tt>, <tt>mpaa</tt>, <tt>cast</tt>, <tt>country</tt>, <tt>imdbnumber</tt>, <tt>premiered</tt>, <tt>productioncode</tt>, <tt>runtime</tt>, <tt>set</tt>, <tt>showlink</tt>, <tt>streamdetails</tt>, <tt>top250</tt>, <tt>votes</tt>, <tt>firstaired</tt>, <tt>season</tt>, <tt>episode</tt>, <tt>showtitle</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>resume</tt>, <tt>artistid</tt>, <tt>albumid</tt>, <tt>tvshowid</tt>, <tt>setid</tt>. See constants at {@link ListModel.AllFields}.
		 * @see ListModel.AllFields
		 * @throws JSONException
		 */
		public GetItems(Integer playlistid, String... properties) throws JSONException {
			super();
			addParameter("playlistid", playlistid);
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<ListModel.AllItem> parseMany(JSONObject obj) throws JSONException {
			final JSONArray items = parseResult(obj).getJSONArray(RESULTS);
			final ArrayList<ListModel.AllItem> ret = new ArrayList<ListModel.AllItem>(items.length());
			for (int i = 0; i < items.length(); i++) {
				final JSONObject item = items.getJSONObject(i);
				ret.add(new ListModel.AllItem(item));
			}
			return ret;
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return true;
		}
	}
	/**
	 * Returns all existing playlists
	 * <p/>
	 * API Name: <code>Playlist.GetPlaylists</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetPlaylists extends AbstractCall<GetPlaylists.GetPlaylistsResult> { 
		private static final String NAME = "GetPlaylists";
		/**
		 * Returns all existing playlists
		 * @throws JSONException
		 */
		public GetPlaylists() throws JSONException {
			super();
		}
		@Override
		protected GetPlaylists.GetPlaylistsResult parseOne(JSONObject obj) throws JSONException {
			return new GetPlaylists.GetPlaylistsResult(parseResult(obj));
		}
		/**
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class GetPlaylistsResult extends AbstractModel {
			// field names
			public static final String PLAYLISTID = "playlistid";
			public static final String TYPE = "type";
			// class members
			public final int playlistid;
			public final String type;
			/**
			 * Construct from JSON object.
			 * @param obj JSON object representing a GetPlaylistsResult object
			 */
			public GetPlaylistsResult(JSONObject obj) throws JSONException {
				playlistid = obj.getInt(PLAYLISTID);
				type = obj.getString(TYPE);
			}
			/**
			 * Construct object with native values for later serialization.
			 * @param playlistid 
			 * @param type 
			 */
			public GetPlaylistsResult(int playlistid, String type) {
				this.playlistid = playlistid;
				this.type = type;
			}
			@Override
			public JSONObject toJSONObject() throws JSONException {
				final JSONObject obj = new JSONObject();
				obj.put(PLAYLISTID, playlistid);
				obj.put(TYPE, type);
				return obj;
			}
			/**
			 * Extracts a list of {@link GetPlaylistsResult} objects from a JSON array.
			 * @param obj JSONObject containing the list of objects
			 * @param key Key pointing to the node where the list is stored
			 */
			static ArrayList<GetPlaylistsResult> getGetPlaylistsResultList(JSONObject obj, String key) throws JSONException {
				if (obj.has(key)) {
					final JSONArray a = obj.getJSONArray(key);
					final ArrayList<GetPlaylistsResult> l = new ArrayList<GetPlaylistsResult>(a.length());
					for (int i = 0; i < a.length(); i++) {
						l.add(new GetPlaylistsResult(a.getJSONObject(i)));
					}
					return l;
				}
				return new ArrayList<GetPlaylistsResult>(0);
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
	 * Retrieves the values of the given properties
	 * <p/>
	 * API Name: <code>Playlist.GetProperties</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetProperties extends AbstractCall<PlaylistModel.PropertyValue> { 
		private static final String NAME = "GetProperties";
		/**
		 * Retrieves the values of the given properties
		 * @param playlistid 
		 * @param properties One or more of: <tt>type</tt>, <tt>size</tt>. See constants at {@link PlaylistModel.PropertyName}.
		 * @see PlaylistModel.PropertyName
		 * @throws JSONException
		 */
		public GetProperties(Integer playlistid, String... properties) throws JSONException {
			super();
			addParameter("playlistid", playlistid);
			addParameter("properties", properties);
		}
		@Override
		protected PlaylistModel.PropertyValue parseOne(JSONObject obj) throws JSONException {
			return new PlaylistModel.PropertyValue(parseResult(obj));
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
	 * Insert item(s) into playlist. Does not work for picture playlists (aka slideshows).
	 * <p/>
	 * API Name: <code>Playlist.Insert</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Insert extends AbstractCall<String> { 
		private static final String NAME = "Insert";
		/**
		 * Insert item(s) into playlist. Does not work for picture playlists (aka slideshows).
		 * @param playlistid 
		 * @param position 
		 * @param item 
		 * @throws JSONException
		 */
		public Insert(Integer playlistid, Integer position, PlaylistModel.Item item) throws JSONException {
			super();
			addParameter("playlistid", playlistid);
			addParameter("position", position);
			addParameter("item", item);
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
	 * Remove item from playlist. Does not work for picture playlists (aka slideshows).
	 * <p/>
	 * API Name: <code>Playlist.Remove</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Remove extends AbstractCall<String> { 
		private static final String NAME = "Remove";
		/**
		 * Remove item from playlist. Does not work for picture playlists (aka slideshows).
		 * @param playlistid 
		 * @param position 
		 * @throws JSONException
		 */
		public Remove(Integer playlistid, Integer position) throws JSONException {
			super();
			addParameter("playlistid", playlistid);
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
	 * Swap items in the playlist. Does not work for picture playlists (aka slideshows).
	 * <p/>
	 * API Name: <code>Playlist.Swap</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Swap extends AbstractCall<String> { 
		private static final String NAME = "Swap";
		/**
		 * Swap items in the playlist. Does not work for picture playlists (aka slideshows).
		 * @param playlistid 
		 * @param position1 
		 * @param position2 
		 * @throws JSONException
		 */
		public Swap(Integer playlistid, Integer position1, Integer position2) throws JSONException {
			super();
			addParameter("playlistid", playlistid);
			addParameter("position1", position1);
			addParameter("position2", position2);
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