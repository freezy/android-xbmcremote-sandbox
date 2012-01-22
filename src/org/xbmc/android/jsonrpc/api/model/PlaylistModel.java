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

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xbmc.android.jsonrpc.api.AbstractModel;

public final class PlaylistModel {
	/**
	 * Playlist.Item
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Item extends AbstractModel {
		public final static String API_TYPE = "Playlist.Item";
		// field names
		public static final String FILE = "file";
		public static final String DIRECTORY = "directory";
		public static final String MOVIEID = "movieid";
		public static final String EPISODEID = "episodeid";
		public static final String MUSICVIDEOID = "musicvideoid";
		public static final String ARTISTID = "artistid";
		public static final String ALBUMID = "albumid";
		public static final String SONGID = "songid";
		public static final String GENREID = "genreid";
		// class members
		/**
		 * Path to a file (not a directory) to be added to the playlist.
		 */
		public final String file;
		public final String directory;
		public final int movieid;
		public final int episodeid;
		public final int musicvideoid;
		public final int artistid;
		public final int albumid;
		public final int songid;
		/**
		 * Identification of a genre from the AudioLibrary.
		 */
		public final int genreid;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a Item object
		 */
		public Item(JSONObject obj) throws JSONException {
			mType = API_TYPE;
			file = obj.getString(FILE);
			directory = obj.getString(DIRECTORY);
			movieid = obj.getInt(MOVIEID);
			episodeid = obj.getInt(EPISODEID);
			musicvideoid = obj.getInt(MUSICVIDEOID);
			artistid = obj.getInt(ARTISTID);
			albumid = obj.getInt(ALBUMID);
			songid = obj.getInt(SONGID);
			genreid = obj.getInt(GENREID);
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param file Path to a file (not a directory) to be added to the playlist 
		 * @param directory 
		 * @param movieid 
		 * @param episodeid 
		 * @param musicvideoid 
		 * @param artistid 
		 * @param albumid 
		 * @param songid 
		 * @param genreid Identification of a genre from the AudioLibrary 
		 */
		public Item(String file, String directory, int movieid, int episodeid, int musicvideoid, int artistid, int albumid, int songid, int genreid) {
			this.file = file;
			this.directory = directory;
			this.movieid = movieid;
			this.episodeid = episodeid;
			this.musicvideoid = musicvideoid;
			this.artistid = artistid;
			this.albumid = albumid;
			this.songid = songid;
			this.genreid = genreid;
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(FILE, file);
			obj.put(DIRECTORY, directory);
			obj.put(MOVIEID, movieid);
			obj.put(EPISODEID, episodeid);
			obj.put(MUSICVIDEOID, musicvideoid);
			obj.put(ARTISTID, artistid);
			obj.put(ALBUMID, albumid);
			obj.put(SONGID, songid);
			obj.put(GENREID, genreid);
			return obj;
		}
		/**
		 * Default constructor.
		 * @param file Path to a file (not a directory) to be added to the playlist 
		 */
		public class FileItem extends Item {
			public FileItem(String file) {
				super(file, null, -1, -1, -1, -1, -1, -1, -1);
			}
		}
		/**
		 * Default constructor.
		 * @param directory 
		 */
		public class DirectoryItem extends Item {
			public DirectoryItem(String directory) {
				super(null, directory, -1, -1, -1, -1, -1, -1, -1);
			}
		}
		/**
		 * Default constructor.
		 * @param movieid 
		 */
		public class MovieidItem extends Item {
			public MovieidItem(int movieid) {
				super(null, null, movieid, -1, -1, -1, -1, -1, -1);
			}
		}
		/**
		 * Default constructor.
		 * @param episodeid 
		 */
		public class EpisodeidItem extends Item {
			public EpisodeidItem(int episodeid) {
				super(null, null, -1, episodeid, -1, -1, -1, -1, -1);
			}
		}
		/**
		 * Default constructor.
		 * @param musicvideoid 
		 */
		public class MusicvideoidItem extends Item {
			public MusicvideoidItem(int musicvideoid) {
				super(null, null, -1, -1, musicvideoid, -1, -1, -1, -1);
			}
		}
		/**
		 * Default constructor.
		 * @param artistid 
		 */
		public class ArtistidItem extends Item {
			public ArtistidItem(int artistid) {
				super(null, null, -1, -1, -1, artistid, -1, -1, -1);
			}
		}
		/**
		 * Default constructor.
		 * @param albumid 
		 */
		public class AlbumidItem extends Item {
			public AlbumidItem(int albumid) {
				super(null, null, -1, -1, -1, -1, albumid, -1, -1);
			}
		}
		/**
		 * Default constructor.
		 * @param songid 
		 */
		public class SongidItem extends Item {
			public SongidItem(int songid) {
				super(null, null, -1, -1, -1, -1, -1, songid, -1);
			}
		}
		/**
		 * Default constructor.
		 * @param genreid Identification of a genre from the AudioLibrary 
		 */
		public class GenreidItem extends Item {
			public GenreidItem(int genreid) {
				super(null, null, -1, -1, -1, -1, -1, -1, genreid);
			}
		}
		/**
		 * Extracts a list of {@link PlaylistModel.Item} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<PlaylistModel.Item> getPlaylistModelItemList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<PlaylistModel.Item> l = new ArrayList<PlaylistModel.Item>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new PlaylistModel.Item(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<PlaylistModel.Item>(0);
		}
	}
	public interface PropertyName {
		public final String TYPE = "type";
		public final String SIZE = "size";
	}
	/**
	 * Playlist.Property.Value
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class PropertyValue extends AbstractModel {
		public final static String API_TYPE = "Playlist.Property.Value";
		// field names
		public static final String SIZE = "size";
		public static final String TYPE = "type";
		// class members
		public final Integer size;
		public final String type;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a PropertyValue object
		 */
		public PropertyValue(JSONObject obj) throws JSONException {
			mType = API_TYPE;
			size = parseInt(obj, SIZE);
			type = parseString(obj, TYPE);
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param size 
		 * @param type 
		 */
		public PropertyValue(Integer size, String type) {
			this.size = size;
			this.type = type;
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(SIZE, size);
			obj.put(TYPE, type);
			return obj;
		}
		/**
		 * Extracts a list of {@link PlaylistModel.PropertyValue} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<PlaylistModel.PropertyValue> getPlaylistModelPropertyValueList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<PlaylistModel.PropertyValue> l = new ArrayList<PlaylistModel.PropertyValue>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new PlaylistModel.PropertyValue(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<PlaylistModel.PropertyValue>(0);
		}
	}
	public interface Type {
		public final String UNKNOWN = "unknown";
		public final String VIDEO = "video";
		public final String AUDIO = "audio";
		public final String PICTURE = "picture";
		public final String MIXED = "mixed";
	}
}