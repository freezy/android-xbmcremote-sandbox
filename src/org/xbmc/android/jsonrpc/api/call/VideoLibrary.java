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
import org.xbmc.android.jsonrpc.api.model.LibraryModel;
import org.xbmc.android.jsonrpc.api.model.ListModel;
import org.xbmc.android.jsonrpc.api.model.VideoModel;

public final class VideoLibrary {

	private final static String PREFIX = "VideoLibrary.";

	/**
	 * Cleans the video library from non-existent items
	 * <p/>
	 * API Name: <code>VideoLibrary.Clean</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Clean extends AbstractCall<String> { 
		private static final String NAME = "Clean";
		/**
		 * Cleans the video library from non-existent items
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
	 * Exports all items from the video library
	 * <p/>
	 * API Name: <code>VideoLibrary.Export</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Export extends AbstractCall<String> { 
		private static final String NAME = "Export";
		/**
		 * Exports all items from the video library
		 * @param options 
		 * @throws JSONException
		 */
		public Export(Path options) throws JSONException {
			super();
			addParameter("options", options);
		}
		/**
		 * Exports all items from the video library
		 * @param options 
		 * @throws JSONException
		 */
		public Export(ActorthumbsImagesOverwrite options) throws JSONException {
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
		public static class ActorthumbsImagesOverwrite extends AbstractModel {
			// field names
			public static final String ACTORTHUMBS = "actorthumbs";
			public static final String IMAGES = "images";
			public static final String OVERWRITE = "overwrite";
			// class members
			/**
			 * Whether to export actor thumbnails.
			 */
			public final boolean actorthumbs;
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
			 * @param actorthumbs Whether to export actor thumbnails 
			 * @param images Whether to export thumbnails and fanart images 
			 * @param overwrite Whether to overwrite existing exported files 
			 */
			public ActorthumbsImagesOverwrite(boolean actorthumbs, boolean images, boolean overwrite) {
				this.actorthumbs = actorthumbs;
				this.images = images;
				this.overwrite = overwrite;
			}
			@Override
			public JSONObject toJSONObject() throws JSONException {
				final JSONObject obj = new JSONObject();
				obj.put(ACTORTHUMBS, actorthumbs);
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
	 * Retrieve details about a specific tv show episode
	 * <p/>
	 * API Name: <code>VideoLibrary.GetEpisodeDetails</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetEpisodeDetails extends AbstractCall<VideoModel.EpisodeDetails> { 
		private static final String NAME = "GetEpisodeDetails";
		public static final String RESULTS = "episodedetails";
		/**
		 * Retrieve details about a specific tv show episode
		 * @param episodeid 
		 * @param properties One or more of: <tt>title</tt>, <tt>plot</tt>, <tt>votes</tt>, <tt>rating</tt>, <tt>writer</tt>, <tt>firstaired</tt>, <tt>playcount</tt>, <tt>runtime</tt>, <tt>director</tt>, <tt>productioncode</tt>, <tt>season</tt>, <tt>episode</tt>, <tt>originaltitle</tt>, <tt>showtitle</tt>, <tt>cast</tt>, <tt>streamdetails</tt>, <tt>lastplayed</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>resume</tt>, <tt>tvshowid</tt>. See constants at {@link VideoModel.EpisodeFields}.
		 * @see VideoModel.EpisodeFields
		 * @throws JSONException
		 */
		public GetEpisodeDetails(Integer episodeid, String... properties) throws JSONException {
			super();
			addParameter("episodeid", episodeid);
			addParameter("properties", properties);
		}
		@Override
		protected VideoModel.EpisodeDetails parseOne(JSONObject obj) throws JSONException {
			return new VideoModel.EpisodeDetails(parseResult(obj).getJSONObject(RESULTS));
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
	 * Retrieve all tv show episodes
	 * <p/>
	 * API Name: <code>VideoLibrary.GetEpisodes</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetEpisodes extends AbstractCall<VideoModel.EpisodeDetails> { 
		private static final String NAME = "GetEpisodes";
		public static final String RESULTS = "episodes";
		/**
		 * Retrieve all tv show episodes
		 * @param tvshowid 
		 * @param season 
		 * @param properties One or more of: <tt>title</tt>, <tt>plot</tt>, <tt>votes</tt>, <tt>rating</tt>, <tt>writer</tt>, <tt>firstaired</tt>, <tt>playcount</tt>, <tt>runtime</tt>, <tt>director</tt>, <tt>productioncode</tt>, <tt>season</tt>, <tt>episode</tt>, <tt>originaltitle</tt>, <tt>showtitle</tt>, <tt>cast</tt>, <tt>streamdetails</tt>, <tt>lastplayed</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>resume</tt>, <tt>tvshowid</tt>. See constants at {@link VideoModel.EpisodeFields}.
		 * @see VideoModel.EpisodeFields
		 * @throws JSONException
		 */
		public GetEpisodes(Integer tvshowid, Integer season, String... properties) throws JSONException {
			super();
			addParameter("tvshowid", tvshowid);
			addParameter("season", season);
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<VideoModel.EpisodeDetails> parseMany(JSONObject obj) throws JSONException {
			final JSONArray episodes = parseResult(obj).getJSONArray(RESULTS);
			final ArrayList<VideoModel.EpisodeDetails> ret = new ArrayList<VideoModel.EpisodeDetails>(episodes.length());
			for (int i = 0; i < episodes.length(); i++) {
				final JSONObject item = episodes.getJSONObject(i);
				ret.add(new VideoModel.EpisodeDetails(item));
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
	 * API Name: <code>VideoLibrary.GetGenres</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetGenres extends AbstractCall<LibraryModel.GenreDetails> { 
		private static final String NAME = "GetGenres";
		public static final String RESULTS = "genres";
		/**
		 * Retrieve all genres
		 * @param type One of: <tt>movie</tt>, <tt>tvshow</tt>, <tt>musicvideo</tt>. See constants at {@link GlobalModel.}.
		 * @param properties One or more of: <tt>title</tt>, <tt>thumbnail</tt>. See constants at {@link LibraryModel.GenreFields}.
		 * @see GlobalModel.
		 * @see LibraryModel.GenreFields
		 * @throws JSONException
		 */
		public GetGenres(String type, String... properties) throws JSONException {
			super();
			addParameter("type", type);
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
	 * Retrieve details about a specific movie
	 * <p/>
	 * API Name: <code>VideoLibrary.GetMovieDetails</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetMovieDetails extends AbstractCall<VideoModel.MovieDetails> { 
		private static final String NAME = "GetMovieDetails";
		public static final String RESULTS = "moviedetails";
		/**
		 * Retrieve details about a specific movie
		 * @param movieid 
		 * @param properties One or more of: <tt>title</tt>, <tt>genre</tt>, <tt>year</tt>, <tt>rating</tt>, <tt>director</tt>, <tt>trailer</tt>, <tt>tagline</tt>, <tt>plot</tt>, <tt>plotoutline</tt>, <tt>originaltitle</tt>, <tt>lastplayed</tt>, <tt>playcount</tt>, <tt>writer</tt>, <tt>studio</tt>, <tt>mpaa</tt>, <tt>cast</tt>, <tt>country</tt>, <tt>imdbnumber</tt>, <tt>premiered</tt>, <tt>productioncode</tt>, <tt>runtime</tt>, <tt>set</tt>, <tt>showlink</tt>, <tt>streamdetails</tt>, <tt>top250</tt>, <tt>votes</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>sorttitle</tt>, <tt>resume</tt>, <tt>setid</tt>. See constants at {@link VideoModel.MovieFields}.
		 * @see VideoModel.MovieFields
		 * @throws JSONException
		 */
		public GetMovieDetails(Integer movieid, String... properties) throws JSONException {
			super();
			addParameter("movieid", movieid);
			addParameter("properties", properties);
		}
		@Override
		protected VideoModel.MovieDetails parseOne(JSONObject obj) throws JSONException {
			return new VideoModel.MovieDetails(parseResult(obj).getJSONObject(RESULTS));
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
	 * Retrieve details about a specific movie set
	 * <p/>
	 * API Name: <code>VideoLibrary.GetMovieSetDetails</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetMovieSetDetails extends AbstractCall<VideoModel.DetailsMovieSetExtended> { 
		private static final String NAME = "GetMovieSetDetails";
		public static final String RESULTS = "setdetails";
		/**
		 * Retrieve details about a specific movie set
		 * @param setid 
		 * @param properties One or more of: <tt>title</tt>, <tt>playcount</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>. See constants at {@link VideoModel.MovieSetFields}.
		 * @param movies 
		 * @see VideoModel.MovieSetFields
		 * @throws JSONException
		 */
		public GetMovieSetDetails(Integer setid, LimitsPropertiesSort movies, String... properties) throws JSONException {
			super();
			addParameter("setid", setid);
			addParameter("movies", movies);
			addParameter("properties", properties);
		}
		@Override
		protected VideoModel.DetailsMovieSetExtended parseOne(JSONObject obj) throws JSONException {
			return new VideoModel.DetailsMovieSetExtended(parseResult(obj).getJSONObject(RESULTS));
		}
		/**
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class LimitsPropertiesSort extends AbstractModel {
			// field names
			public static final String LIMITS = "limits";
			public static final String PROPERTIES = "properties";
			public static final String SORT = "sort";
			// class members
			public final ListModel.Limits limits;
			public final ArrayList<String> properties;
			public final ListModel.Sort sort;
			/**
			 * Construct object with native values for later serialization.
			 * @param limits 
			 * @param properties 
			 * @param sort 
			 */
			public LimitsPropertiesSort(ListModel.Limits limits, ArrayList<String> properties, ListModel.Sort sort) {
				this.limits = limits;
				this.properties = properties;
				this.sort = sort;
			}
			@Override
			public JSONObject toJSONObject() throws JSONException {
				final JSONObject obj = new JSONObject();
				obj.put(LIMITS, limits.toJSONObject());
				obj.put(PROPERTIES, properties);
				obj.put(SORT, sort.toJSONObject());
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
	 * Retrieve all movie sets
	 * <p/>
	 * API Name: <code>VideoLibrary.GetMovieSets</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetMovieSets extends AbstractCall<VideoModel.MovieSetDetails> { 
		private static final String NAME = "GetMovieSets";
		public static final String RESULTS = "sets";
		/**
		 * Retrieve all movie sets
		 * @param properties One or more of: <tt>title</tt>, <tt>playcount</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>. See constants at {@link VideoModel.MovieSetFields}.
		 * @see VideoModel.MovieSetFields
		 * @throws JSONException
		 */
		public GetMovieSets(String... properties) throws JSONException {
			super();
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<VideoModel.MovieSetDetails> parseMany(JSONObject obj) throws JSONException {
			final JSONArray sets = parseResult(obj).getJSONArray(RESULTS);
			final ArrayList<VideoModel.MovieSetDetails> ret = new ArrayList<VideoModel.MovieSetDetails>(sets.length());
			for (int i = 0; i < sets.length(); i++) {
				final JSONObject item = sets.getJSONObject(i);
				ret.add(new VideoModel.MovieSetDetails(item));
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
	 * Retrieve all movies
	 * <p/>
	 * API Name: <code>VideoLibrary.GetMovies</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetMovies extends AbstractCall<VideoModel.MovieDetails> { 
		private static final String NAME = "GetMovies";
		public static final String RESULTS = "movies";
		/**
		 * Retrieve all movies
		 * @param properties One or more of: <tt>title</tt>, <tt>genre</tt>, <tt>year</tt>, <tt>rating</tt>, <tt>director</tt>, <tt>trailer</tt>, <tt>tagline</tt>, <tt>plot</tt>, <tt>plotoutline</tt>, <tt>originaltitle</tt>, <tt>lastplayed</tt>, <tt>playcount</tt>, <tt>writer</tt>, <tt>studio</tt>, <tt>mpaa</tt>, <tt>cast</tt>, <tt>country</tt>, <tt>imdbnumber</tt>, <tt>premiered</tt>, <tt>productioncode</tt>, <tt>runtime</tt>, <tt>set</tt>, <tt>showlink</tt>, <tt>streamdetails</tt>, <tt>top250</tt>, <tt>votes</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>sorttitle</tt>, <tt>resume</tt>, <tt>setid</tt>. See constants at {@link VideoModel.MovieFields}.
		 * @see VideoModel.MovieFields
		 * @throws JSONException
		 */
		public GetMovies(String... properties) throws JSONException {
			super();
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<VideoModel.MovieDetails> parseMany(JSONObject obj) throws JSONException {
			final JSONArray movies = parseResult(obj).getJSONArray(RESULTS);
			final ArrayList<VideoModel.MovieDetails> ret = new ArrayList<VideoModel.MovieDetails>(movies.length());
			for (int i = 0; i < movies.length(); i++) {
				final JSONObject item = movies.getJSONObject(i);
				ret.add(new VideoModel.MovieDetails(item));
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
	 * Retrieve details about a specific music video
	 * <p/>
	 * API Name: <code>VideoLibrary.GetMusicVideoDetails</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetMusicVideoDetails extends AbstractCall<VideoModel.MusicVideoDetails> { 
		private static final String NAME = "GetMusicVideoDetails";
		public static final String RESULTS = "musicvideodetails";
		/**
		 * Retrieve details about a specific music video
		 * @param musicvideoid 
		 * @param properties One or more of: <tt>title</tt>, <tt>playcount</tt>, <tt>runtime</tt>, <tt>director</tt>, <tt>studio</tt>, <tt>year</tt>, <tt>plot</tt>, <tt>album</tt>, <tt>artist</tt>, <tt>genre</tt>, <tt>track</tt>, <tt>streamdetails</tt>, <tt>lastplayed</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>resume</tt>. See constants at {@link VideoModel.MusicVideoFields}.
		 * @see VideoModel.MusicVideoFields
		 * @throws JSONException
		 */
		public GetMusicVideoDetails(Integer musicvideoid, String... properties) throws JSONException {
			super();
			addParameter("musicvideoid", musicvideoid);
			addParameter("properties", properties);
		}
		@Override
		protected VideoModel.MusicVideoDetails parseOne(JSONObject obj) throws JSONException {
			return new VideoModel.MusicVideoDetails(parseResult(obj).getJSONObject(RESULTS));
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
	 * Retrieve all music videos
	 * <p/>
	 * API Name: <code>VideoLibrary.GetMusicVideos</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetMusicVideos extends AbstractCall<VideoModel.MusicVideoDetails> { 
		private static final String NAME = "GetMusicVideos";
		public static final String RESULTS = "musicvideos";
		/**
		 * Retrieve all music videos
		 * @param artistid 
		 * @param albumid 
		 * @param properties One or more of: <tt>title</tt>, <tt>playcount</tt>, <tt>runtime</tt>, <tt>director</tt>, <tt>studio</tt>, <tt>year</tt>, <tt>plot</tt>, <tt>album</tt>, <tt>artist</tt>, <tt>genre</tt>, <tt>track</tt>, <tt>streamdetails</tt>, <tt>lastplayed</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>resume</tt>. See constants at {@link VideoModel.MusicVideoFields}.
		 * @see VideoModel.MusicVideoFields
		 * @throws JSONException
		 */
		public GetMusicVideos(Integer artistid, Integer albumid, String... properties) throws JSONException {
			super();
			addParameter("artistid", artistid);
			addParameter("albumid", albumid);
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<VideoModel.MusicVideoDetails> parseMany(JSONObject obj) throws JSONException {
			final JSONArray musicvideos = parseResult(obj).getJSONArray(RESULTS);
			final ArrayList<VideoModel.MusicVideoDetails> ret = new ArrayList<VideoModel.MusicVideoDetails>(musicvideos.length());
			for (int i = 0; i < musicvideos.length(); i++) {
				final JSONObject item = musicvideos.getJSONObject(i);
				ret.add(new VideoModel.MusicVideoDetails(item));
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
	 * Retrieve all recently added tv episodes
	 * <p/>
	 * API Name: <code>VideoLibrary.GetRecentlyAddedEpisodes</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetRecentlyAddedEpisodes extends AbstractCall<VideoModel.EpisodeDetails> { 
		private static final String NAME = "GetRecentlyAddedEpisodes";
		public static final String RESULTS = "episodes";
		/**
		 * Retrieve all recently added tv episodes
		 * @param properties One or more of: <tt>title</tt>, <tt>plot</tt>, <tt>votes</tt>, <tt>rating</tt>, <tt>writer</tt>, <tt>firstaired</tt>, <tt>playcount</tt>, <tt>runtime</tt>, <tt>director</tt>, <tt>productioncode</tt>, <tt>season</tt>, <tt>episode</tt>, <tt>originaltitle</tt>, <tt>showtitle</tt>, <tt>cast</tt>, <tt>streamdetails</tt>, <tt>lastplayed</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>resume</tt>, <tt>tvshowid</tt>. See constants at {@link VideoModel.EpisodeFields}.
		 * @see VideoModel.EpisodeFields
		 * @throws JSONException
		 */
		public GetRecentlyAddedEpisodes(String... properties) throws JSONException {
			super();
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<VideoModel.EpisodeDetails> parseMany(JSONObject obj) throws JSONException {
			final JSONArray episodes = parseResult(obj).getJSONArray(RESULTS);
			final ArrayList<VideoModel.EpisodeDetails> ret = new ArrayList<VideoModel.EpisodeDetails>(episodes.length());
			for (int i = 0; i < episodes.length(); i++) {
				final JSONObject item = episodes.getJSONObject(i);
				ret.add(new VideoModel.EpisodeDetails(item));
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
	 * Retrieve all recently added movies
	 * <p/>
	 * API Name: <code>VideoLibrary.GetRecentlyAddedMovies</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetRecentlyAddedMovies extends AbstractCall<VideoModel.MovieDetails> { 
		private static final String NAME = "GetRecentlyAddedMovies";
		public static final String RESULTS = "movies";
		/**
		 * Retrieve all recently added movies
		 * @param properties One or more of: <tt>title</tt>, <tt>genre</tt>, <tt>year</tt>, <tt>rating</tt>, <tt>director</tt>, <tt>trailer</tt>, <tt>tagline</tt>, <tt>plot</tt>, <tt>plotoutline</tt>, <tt>originaltitle</tt>, <tt>lastplayed</tt>, <tt>playcount</tt>, <tt>writer</tt>, <tt>studio</tt>, <tt>mpaa</tt>, <tt>cast</tt>, <tt>country</tt>, <tt>imdbnumber</tt>, <tt>premiered</tt>, <tt>productioncode</tt>, <tt>runtime</tt>, <tt>set</tt>, <tt>showlink</tt>, <tt>streamdetails</tt>, <tt>top250</tt>, <tt>votes</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>sorttitle</tt>, <tt>resume</tt>, <tt>setid</tt>. See constants at {@link VideoModel.MovieFields}.
		 * @see VideoModel.MovieFields
		 * @throws JSONException
		 */
		public GetRecentlyAddedMovies(String... properties) throws JSONException {
			super();
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<VideoModel.MovieDetails> parseMany(JSONObject obj) throws JSONException {
			final JSONArray movies = parseResult(obj).getJSONArray(RESULTS);
			final ArrayList<VideoModel.MovieDetails> ret = new ArrayList<VideoModel.MovieDetails>(movies.length());
			for (int i = 0; i < movies.length(); i++) {
				final JSONObject item = movies.getJSONObject(i);
				ret.add(new VideoModel.MovieDetails(item));
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
	 * Retrieve all recently added music videos
	 * <p/>
	 * API Name: <code>VideoLibrary.GetRecentlyAddedMusicVideos</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetRecentlyAddedMusicVideos extends AbstractCall<VideoModel.MusicVideoDetails> { 
		private static final String NAME = "GetRecentlyAddedMusicVideos";
		public static final String RESULTS = "musicvideos";
		/**
		 * Retrieve all recently added music videos
		 * @param properties One or more of: <tt>title</tt>, <tt>playcount</tt>, <tt>runtime</tt>, <tt>director</tt>, <tt>studio</tt>, <tt>year</tt>, <tt>plot</tt>, <tt>album</tt>, <tt>artist</tt>, <tt>genre</tt>, <tt>track</tt>, <tt>streamdetails</tt>, <tt>lastplayed</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>resume</tt>. See constants at {@link VideoModel.MusicVideoFields}.
		 * @see VideoModel.MusicVideoFields
		 * @throws JSONException
		 */
		public GetRecentlyAddedMusicVideos(String... properties) throws JSONException {
			super();
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<VideoModel.MusicVideoDetails> parseMany(JSONObject obj) throws JSONException {
			final JSONArray musicvideos = parseResult(obj).getJSONArray(RESULTS);
			final ArrayList<VideoModel.MusicVideoDetails> ret = new ArrayList<VideoModel.MusicVideoDetails>(musicvideos.length());
			for (int i = 0; i < musicvideos.length(); i++) {
				final JSONObject item = musicvideos.getJSONObject(i);
				ret.add(new VideoModel.MusicVideoDetails(item));
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
	 * Retrieve all tv seasons
	 * <p/>
	 * API Name: <code>VideoLibrary.GetSeasons</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetSeasons extends AbstractCall<VideoModel.SeasonDetails> { 
		private static final String NAME = "GetSeasons";
		public static final String RESULTS = "seasons";
		/**
		 * Retrieve all tv seasons
		 * @param tvshowid 
		 * @param properties One or more of: <tt>season</tt>, <tt>showtitle</tt>, <tt>playcount</tt>, <tt>episode</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>tvshowid</tt>. See constants at {@link VideoModel.SeasonFields}.
		 * @see VideoModel.SeasonFields
		 * @throws JSONException
		 */
		public GetSeasons(Integer tvshowid, String... properties) throws JSONException {
			super();
			addParameter("tvshowid", tvshowid);
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<VideoModel.SeasonDetails> parseMany(JSONObject obj) throws JSONException {
			final JSONArray seasons = parseResult(obj).getJSONArray(RESULTS);
			final ArrayList<VideoModel.SeasonDetails> ret = new ArrayList<VideoModel.SeasonDetails>(seasons.length());
			for (int i = 0; i < seasons.length(); i++) {
				final JSONObject item = seasons.getJSONObject(i);
				ret.add(new VideoModel.SeasonDetails(item));
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
	 * Retrieve details about a specific tv show
	 * <p/>
	 * API Name: <code>VideoLibrary.GetTVShowDetails</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetTVShowDetails extends AbstractCall<VideoModel.TVShowDetails> { 
		private static final String NAME = "GetTVShowDetails";
		public static final String RESULTS = "tvshowdetails";
		/**
		 * Retrieve details about a specific tv show
		 * @param tvshowid 
		 * @param properties One or more of: <tt>title</tt>, <tt>genre</tt>, <tt>year</tt>, <tt>rating</tt>, <tt>plot</tt>, <tt>studio</tt>, <tt>mpaa</tt>, <tt>cast</tt>, <tt>playcount</tt>, <tt>episode</tt>, <tt>imdbnumber</tt>, <tt>premiered</tt>, <tt>votes</tt>, <tt>lastplayed</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>originaltitle</tt>, <tt>sorttitle</tt>, <tt>episodeguide</tt>. See constants at {@link VideoModel.TVShowFields}.
		 * @see VideoModel.TVShowFields
		 * @throws JSONException
		 */
		public GetTVShowDetails(Integer tvshowid, String... properties) throws JSONException {
			super();
			addParameter("tvshowid", tvshowid);
			addParameter("properties", properties);
		}
		@Override
		protected VideoModel.TVShowDetails parseOne(JSONObject obj) throws JSONException {
			return new VideoModel.TVShowDetails(parseResult(obj).getJSONObject(RESULTS));
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
	 * Retrieve all tv shows
	 * <p/>
	 * API Name: <code>VideoLibrary.GetTVShows</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetTVShows extends AbstractCall<VideoModel.TVShowDetails> { 
		private static final String NAME = "GetTVShows";
		public static final String RESULTS = "tvshows";
		/**
		 * Retrieve all tv shows
		 * @param properties One or more of: <tt>title</tt>, <tt>genre</tt>, <tt>year</tt>, <tt>rating</tt>, <tt>plot</tt>, <tt>studio</tt>, <tt>mpaa</tt>, <tt>cast</tt>, <tt>playcount</tt>, <tt>episode</tt>, <tt>imdbnumber</tt>, <tt>premiered</tt>, <tt>votes</tt>, <tt>lastplayed</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>originaltitle</tt>, <tt>sorttitle</tt>, <tt>episodeguide</tt>. See constants at {@link VideoModel.TVShowFields}.
		 * @see VideoModel.TVShowFields
		 * @throws JSONException
		 */
		public GetTVShows(String... properties) throws JSONException {
			super();
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<VideoModel.TVShowDetails> parseMany(JSONObject obj) throws JSONException {
			final JSONArray tvshows = parseResult(obj).getJSONArray(RESULTS);
			final ArrayList<VideoModel.TVShowDetails> ret = new ArrayList<VideoModel.TVShowDetails>(tvshows.length());
			for (int i = 0; i < tvshows.length(); i++) {
				final JSONObject item = tvshows.getJSONObject(i);
				ret.add(new VideoModel.TVShowDetails(item));
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
	 * Scans the video sources for new library items
	 * <p/>
	 * API Name: <code>VideoLibrary.Scan</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Scan extends AbstractCall<String> { 
		private static final String NAME = "Scan";
		/**
		 * Scans the video sources for new library items
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