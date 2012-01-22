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
import org.xbmc.android.jsonrpc.api.model.AudioModel;
import org.xbmc.android.jsonrpc.api.model.LibraryModel;

public final class AudioLibrary {

	private final static String PREFIX = "AudioLibrary.";

	/**
	 * Cleans the audio library from non-existent items
	 * <p/>
	 * API Name: <code>AudioLibrary.Clean</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Clean extends AbstractCall<String> { 
		private static final String NAME = "Clean";
		/**
		 * Cleans the audio library from non-existent items
		 * @throws JSONException
		 */
		public Clean() throws JSONException {
			super();
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
	 * Exports all items from the audio library
	 * <p/>
	 * API Name: <code>AudioLibrary.Export</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Export extends AbstractCall<String> { 
		private static final String NAME = "Export";
		/**
		 * Exports all items from the audio library
		 * @param options 
		 * @throws JSONException
		 */
		public Export(Path options) throws JSONException {
			super();
			addParameter("options", options);
		}
		/**
		 * Exports all items from the audio library
		 * @param options 
		 * @throws JSONException
		 */
		public Export(ImagesOverwrite options) throws JSONException {
			super();
			addParameter("options", options);
		}
		@Override
		protected String parseOne(JSONObject obj) throws JSONException {
			return obj.getString(RESULT);
		}
		/**
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class Path extends AbstractModel {
			// field names
			public static final String PATH = "path";
			// class members
			/**
			 * Path to the directory to where the data should be exported.
			 */
			public final String path;
			/**
			 * Construct object with native values for later serialization.
			 * @param path Path to the directory to where the data should be exported 
			 */
			public Path(String path) {
				this.path = path;
			}
			@Override
			public JSONObject toJSONObject() throws JSONException {
				final JSONObject obj = new JSONObject();
				obj.put(PATH, path);
				return obj;
			}
		}
		/**
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class ImagesOverwrite extends AbstractModel {
			// field names
			public static final String IMAGES = "images";
			public static final String OVERWRITE = "overwrite";
			// class members
			/**
			 * Whether to export thumbnails and fanart images.
			 */
			public final boolean images;
			/**
			 * Whether to overwrite existing exported files.
			 */
			public final boolean overwrite;
			/**
			 * Construct object with native values for later serialization.
			 * @param images Whether to export thumbnails and fanart images 
			 * @param overwrite Whether to overwrite existing exported files 
			 */
			public ImagesOverwrite(boolean images, boolean overwrite) {
				this.images = images;
				this.overwrite = overwrite;
			}
			@Override
			public JSONObject toJSONObject() throws JSONException {
				final JSONObject obj = new JSONObject();
				obj.put(IMAGES, images);
				obj.put(OVERWRITE, overwrite);
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
	 * Retrieve details about a specific album
	 * <p/>
	 * API Name: <code>AudioLibrary.GetAlbumDetails</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetAlbumDetails extends AbstractCall<AudioModel.AlbumDetails> { 
		private static final String NAME = "GetAlbumDetails";
		public static final String RESULTS = "albumdetails";
		/**
		 * Retrieve details about a specific album
		 * @param albumid 
		 * @param properties One or more of: <tt>title</tt>, <tt>description</tt>, <tt>artist</tt>, <tt>genre</tt>, <tt>theme</tt>, <tt>mood</tt>, <tt>style</tt>, <tt>type</tt>, <tt>albumlabel</tt>, <tt>rating</tt>, <tt>year</tt>, <tt>musicbrainzalbumid</tt>, <tt>musicbrainzalbumartistid</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>artistid</tt>. See constants at {@link AudioModel.AlbumFields}.
		 * @see AudioModel.AlbumFields
		 * @throws JSONException
		 */
		public GetAlbumDetails(Integer albumid, String... properties) throws JSONException {
			super();
			addParameter("albumid", albumid);
			addParameter("properties", properties);
		}
		@Override
		protected AudioModel.AlbumDetails parseOne(JSONObject obj) throws JSONException {
			return new AudioModel.AlbumDetails(parseResult(obj).getJSONObject(RESULTS));
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
	 * Retrieve all albums from specified artist or genre
	 * <p/>
	 * API Name: <code>AudioLibrary.GetAlbums</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetAlbums extends AbstractCall<AudioModel.AlbumDetails> { 
		private static final String NAME = "GetAlbums";
		public static final String RESULTS = "albums";
		/**
		 * Retrieve all albums from specified artist or genre
		 * @param artistid 
		 * @param genreid 
		 * @param properties One or more of: <tt>title</tt>, <tt>description</tt>, <tt>artist</tt>, <tt>genre</tt>, <tt>theme</tt>, <tt>mood</tt>, <tt>style</tt>, <tt>type</tt>, <tt>albumlabel</tt>, <tt>rating</tt>, <tt>year</tt>, <tt>musicbrainzalbumid</tt>, <tt>musicbrainzalbumartistid</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>artistid</tt>. See constants at {@link AudioModel.AlbumFields}.
		 * @see AudioModel.AlbumFields
		 * @throws JSONException
		 */
		public GetAlbums(Integer artistid, Integer genreid, String... properties) throws JSONException {
			super();
			addParameter("artistid", artistid);
			addParameter("genreid", genreid);
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<AudioModel.AlbumDetails> parseMany(JSONObject obj) throws JSONException {
			final JSONArray albums = parseResult(obj).getJSONArray(RESULTS);
			final ArrayList<AudioModel.AlbumDetails> ret = new ArrayList<AudioModel.AlbumDetails>(albums.length());
			for (int i = 0; i < albums.length(); i++) {
				final JSONObject item = albums.getJSONObject(i);
				ret.add(new AudioModel.AlbumDetails(item));
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
	 * Retrieve details about a specific artist
	 * <p/>
	 * API Name: <code>AudioLibrary.GetArtistDetails</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetArtistDetails extends AbstractCall<AudioModel.ArtistDetails> { 
		private static final String NAME = "GetArtistDetails";
		public static final String RESULTS = "artistdetails";
		/**
		 * Retrieve details about a specific artist
		 * @param artistid 
		 * @param properties One or more of: <tt>instrument</tt>, <tt>style</tt>, <tt>mood</tt>, <tt>born</tt>, <tt>formed</tt>, <tt>description</tt>, <tt>genre</tt>, <tt>died</tt>, <tt>disbanded</tt>, <tt>yearsactive</tt>, <tt>musicbrainzartistid</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>. See constants at {@link AudioModel.ArtistFields}.
		 * @see AudioModel.ArtistFields
		 * @throws JSONException
		 */
		public GetArtistDetails(Integer artistid, String... properties) throws JSONException {
			super();
			addParameter("artistid", artistid);
			addParameter("properties", properties);
		}
		@Override
		protected AudioModel.ArtistDetails parseOne(JSONObject obj) throws JSONException {
			return new AudioModel.ArtistDetails(parseResult(obj).getJSONObject(RESULTS));
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
	 * Retrieve all artists
	 * <p/>
	 * API Name: <code>AudioLibrary.GetArtists</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetArtists extends AbstractCall<AudioModel.ArtistDetails> { 
		private static final String NAME = "GetArtists";
		public static final String RESULTS = "artists";
		/**
		 * Retrieve all artists
		 * @param albumartistsonly Retrieve all artists
		 * @param genreid 
		 * @param properties One or more of: <tt>instrument</tt>, <tt>style</tt>, <tt>mood</tt>, <tt>born</tt>, <tt>formed</tt>, <tt>description</tt>, <tt>genre</tt>, <tt>died</tt>, <tt>disbanded</tt>, <tt>yearsactive</tt>, <tt>musicbrainzartistid</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>. See constants at {@link AudioModel.ArtistFields}.
		 * @see AudioModel.ArtistFields
		 * @throws JSONException
		 */
		public GetArtists(Boolean albumartistsonly, Integer genreid, String... properties) throws JSONException {
			super();
			addParameter("albumartistsonly", albumartistsonly);
			addParameter("genreid", genreid);
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<AudioModel.ArtistDetails> parseMany(JSONObject obj) throws JSONException {
			final JSONArray artists = parseResult(obj).getJSONArray(RESULTS);
			final ArrayList<AudioModel.ArtistDetails> ret = new ArrayList<AudioModel.ArtistDetails>(artists.length());
			for (int i = 0; i < artists.length(); i++) {
				final JSONObject item = artists.getJSONObject(i);
				ret.add(new AudioModel.ArtistDetails(item));
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
	 * Retrieve all genres
	 * <p/>
	 * API Name: <code>AudioLibrary.GetGenres</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetGenres extends AbstractCall<LibraryModel.GenreDetails> { 
		private static final String NAME = "GetGenres";
		public static final String RESULTS = "genres";
		/**
		 * Retrieve all genres
		 * @param properties One or more of: <tt>title</tt>, <tt>thumbnail</tt>. See constants at {@link LibraryModel.GenreFields}.
		 * @see LibraryModel.GenreFields
		 * @throws JSONException
		 */
		public GetGenres(String... properties) throws JSONException {
			super();
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<LibraryModel.GenreDetails> parseMany(JSONObject obj) throws JSONException {
			final JSONArray genres = parseResult(obj).getJSONArray(RESULTS);
			final ArrayList<LibraryModel.GenreDetails> ret = new ArrayList<LibraryModel.GenreDetails>(genres.length());
			for (int i = 0; i < genres.length(); i++) {
				final JSONObject item = genres.getJSONObject(i);
				ret.add(new LibraryModel.GenreDetails(item));
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
	 * Retrieve recently added albums
	 * <p/>
	 * API Name: <code>AudioLibrary.GetRecentlyAddedAlbums</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetRecentlyAddedAlbums extends AbstractCall<AudioModel.AlbumDetails> { 
		private static final String NAME = "GetRecentlyAddedAlbums";
		public static final String RESULTS = "albums";
		/**
		 * Retrieve recently added albums
		 * @param properties One or more of: <tt>title</tt>, <tt>description</tt>, <tt>artist</tt>, <tt>genre</tt>, <tt>theme</tt>, <tt>mood</tt>, <tt>style</tt>, <tt>type</tt>, <tt>albumlabel</tt>, <tt>rating</tt>, <tt>year</tt>, <tt>musicbrainzalbumid</tt>, <tt>musicbrainzalbumartistid</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>artistid</tt>. See constants at {@link AudioModel.AlbumFields}.
		 * @see AudioModel.AlbumFields
		 * @throws JSONException
		 */
		public GetRecentlyAddedAlbums(String... properties) throws JSONException {
			super();
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<AudioModel.AlbumDetails> parseMany(JSONObject obj) throws JSONException {
			final JSONArray albums = parseResult(obj).getJSONArray(RESULTS);
			final ArrayList<AudioModel.AlbumDetails> ret = new ArrayList<AudioModel.AlbumDetails>(albums.length());
			for (int i = 0; i < albums.length(); i++) {
				final JSONObject item = albums.getJSONObject(i);
				ret.add(new AudioModel.AlbumDetails(item));
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
	 * Retrieve recently added songs
	 * <p/>
	 * API Name: <code>AudioLibrary.GetRecentlyAddedSongs</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetRecentlyAddedSongs extends AbstractCall<AudioModel.SongDetails> { 
		private static final String NAME = "GetRecentlyAddedSongs";
		public static final String RESULTS = "songs";
		/**
		 * Retrieve recently added songs
		 * @param albumlimit Retrieve recently added songs
		 * @param properties One or more of: <tt>title</tt>, <tt>artist</tt>, <tt>albumartist</tt>, <tt>genre</tt>, <tt>year</tt>, <tt>rating</tt>, <tt>album</tt>, <tt>track</tt>, <tt>duration</tt>, <tt>comment</tt>, <tt>lyrics</tt>, <tt>musicbrainztrackid</tt>, <tt>musicbrainzartistid</tt>, <tt>musicbrainzalbumid</tt>, <tt>musicbrainzalbumartistid</tt>, <tt>playcount</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>artistid</tt>, <tt>albumid</tt>. See constants at {@link AudioModel.SongFields}.
		 * @see AudioModel.SongFields
		 * @throws JSONException
		 */
		public GetRecentlyAddedSongs(Integer albumlimit, String... properties) throws JSONException {
			super();
			addParameter("albumlimit", albumlimit);
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<AudioModel.SongDetails> parseMany(JSONObject obj) throws JSONException {
			final JSONArray songs = parseResult(obj).getJSONArray(RESULTS);
			final ArrayList<AudioModel.SongDetails> ret = new ArrayList<AudioModel.SongDetails>(songs.length());
			for (int i = 0; i < songs.length(); i++) {
				final JSONObject item = songs.getJSONObject(i);
				ret.add(new AudioModel.SongDetails(item));
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
	 * Retrieve details about a specific song
	 * <p/>
	 * API Name: <code>AudioLibrary.GetSongDetails</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetSongDetails extends AbstractCall<AudioModel.SongDetails> { 
		private static final String NAME = "GetSongDetails";
		public static final String RESULTS = "songdetails";
		/**
		 * Retrieve details about a specific song
		 * @param songid 
		 * @param properties One or more of: <tt>title</tt>, <tt>artist</tt>, <tt>albumartist</tt>, <tt>genre</tt>, <tt>year</tt>, <tt>rating</tt>, <tt>album</tt>, <tt>track</tt>, <tt>duration</tt>, <tt>comment</tt>, <tt>lyrics</tt>, <tt>musicbrainztrackid</tt>, <tt>musicbrainzartistid</tt>, <tt>musicbrainzalbumid</tt>, <tt>musicbrainzalbumartistid</tt>, <tt>playcount</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>artistid</tt>, <tt>albumid</tt>. See constants at {@link AudioModel.SongFields}.
		 * @see AudioModel.SongFields
		 * @throws JSONException
		 */
		public GetSongDetails(Integer songid, String... properties) throws JSONException {
			super();
			addParameter("songid", songid);
			addParameter("properties", properties);
		}
		@Override
		protected AudioModel.SongDetails parseOne(JSONObject obj) throws JSONException {
			return new AudioModel.SongDetails(parseResult(obj).getJSONObject(RESULTS));
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
	 * Retrieve all songs from specified album, artist or genre
	 * <p/>
	 * API Name: <code>AudioLibrary.GetSongs</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetSongs extends AbstractCall<AudioModel.SongDetails> { 
		private static final String NAME = "GetSongs";
		public static final String RESULTS = "songs";
		/**
		 * Retrieve all songs from specified album, artist or genre
		 * @param artistid 
		 * @param albumid 
		 * @param genreid 
		 * @param properties One or more of: <tt>title</tt>, <tt>artist</tt>, <tt>albumartist</tt>, <tt>genre</tt>, <tt>year</tt>, <tt>rating</tt>, <tt>album</tt>, <tt>track</tt>, <tt>duration</tt>, <tt>comment</tt>, <tt>lyrics</tt>, <tt>musicbrainztrackid</tt>, <tt>musicbrainzartistid</tt>, <tt>musicbrainzalbumid</tt>, <tt>musicbrainzalbumartistid</tt>, <tt>playcount</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>artistid</tt>, <tt>albumid</tt>. See constants at {@link AudioModel.SongFields}.
		 * @see AudioModel.SongFields
		 * @throws JSONException
		 */
		public GetSongs(Integer artistid, Integer albumid, Integer genreid, String... properties) throws JSONException {
			super();
			addParameter("artistid", artistid);
			addParameter("albumid", albumid);
			addParameter("genreid", genreid);
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<AudioModel.SongDetails> parseMany(JSONObject obj) throws JSONException {
			final JSONArray songs = parseResult(obj).getJSONArray(RESULTS);
			final ArrayList<AudioModel.SongDetails> ret = new ArrayList<AudioModel.SongDetails>(songs.length());
			for (int i = 0; i < songs.length(); i++) {
				final JSONObject item = songs.getJSONObject(i);
				ret.add(new AudioModel.SongDetails(item));
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
	 * Scans the audio sources for new library items
	 * <p/>
	 * API Name: <code>AudioLibrary.Scan</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Scan extends AbstractCall<String> { 
		private static final String NAME = "Scan";
		/**
		 * Scans the audio sources for new library items
		 * @throws JSONException
		 */
		public Scan() throws JSONException {
			super();
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