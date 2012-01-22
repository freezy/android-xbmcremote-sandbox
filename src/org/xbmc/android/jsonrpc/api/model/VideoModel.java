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
import org.xbmc.android.jsonrpc.api.JSONSerializable;

public final class VideoModel {
	/**
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Cast extends AbstractModel {
		public final static String API_TYPE = "Video.Cast";
		// field names
		public static final String NAME = "name";
		public static final String ROLE = "role";
		public static final String THUMBNAIL = "thumbnail";
		// class members
		public final String name;
		public final String role;
		public final String thumbnail;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a Cast object
		 */
		public Cast(JSONObject obj) throws JSONException {
			mType = API_TYPE;
			name = obj.getString(NAME);
			role = obj.getString(ROLE);
			thumbnail = parseString(obj, THUMBNAIL);
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param name 
		 * @param role 
		 * @param thumbnail 
		 */
		public Cast(String name, String role, String thumbnail) {
			this.name = name;
			this.role = role;
			this.thumbnail = thumbnail;
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(NAME, name);
			obj.put(ROLE, role);
			obj.put(THUMBNAIL, thumbnail);
			return obj;
		}
		/**
		 * Extracts a list of {@link VideoModel.Cast} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.Cast> getVideoModelCastList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<VideoModel.Cast> l = new ArrayList<VideoModel.Cast>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new VideoModel.Cast(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.Cast>(0);
		}
	}
	/**
	 * Video.Details.Base
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class BaseDetails extends MediaModel.BaseDetails {
		public final static String API_TYPE = "Video.Details.Base";
		// field names
		public static final String PLAYCOUNT = "playcount";
		// class members
		public final Integer playcount;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a BaseDetails object
		 */
		public BaseDetails(JSONObject obj) throws JSONException {
			super(obj);
			mType = API_TYPE;
			playcount = parseInt(obj, PLAYCOUNT);
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(PLAYCOUNT, playcount);
			return obj;
		}
		/**
		 * Extracts a list of {@link VideoModel.BaseDetails} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.BaseDetails> getVideoModelBaseDetailsList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<VideoModel.BaseDetails> l = new ArrayList<VideoModel.BaseDetails>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new VideoModel.BaseDetails(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.BaseDetails>(0);
		}
	}
	/**
	 * Video.Details.Episode
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class EpisodeDetails extends VideoModel.FileDetails {
		public final static String API_TYPE = "Video.Details.Episode";
		// field names
		public static final String CAST = "cast";
		public static final String EPISODE = "episode";
		public static final String EPISODEID = "episodeid";
		public static final String FIRSTAIRED = "firstaired";
		public static final String ORIGINALTITLE = "originaltitle";
		public static final String PRODUCTIONCODE = "productioncode";
		public static final String RATING = "rating";
		public static final String SEASON = "season";
		public static final String SHOWTITLE = "showtitle";
		public static final String TVSHOWID = "tvshowid";
		public static final String VOTES = "votes";
		public static final String WRITER = "writer";
		// class members
		public final ArrayList<VideoModel.Cast> cast;
		public final Integer episode;
		public final int episodeid;
		public final String firstaired;
		public final String originaltitle;
		public final String productioncode;
		public final Double rating;
		public final Integer season;
		public final String showtitle;
		public final Integer tvshowid;
		public final String votes;
		public final String writer;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a EpisodeDetails object
		 */
		public EpisodeDetails(JSONObject obj) throws JSONException {
			super(obj);
			mType = API_TYPE;
			cast = VideoModel.Cast.getVideoModelCastList(obj, CAST);
			episode = parseInt(obj, EPISODE);
			episodeid = obj.getInt(EPISODEID);
			firstaired = parseString(obj, FIRSTAIRED);
			originaltitle = parseString(obj, ORIGINALTITLE);
			productioncode = parseString(obj, PRODUCTIONCODE);
			rating = parseDouble(obj, RATING);
			season = parseInt(obj, SEASON);
			showtitle = parseString(obj, SHOWTITLE);
			tvshowid = parseInt(obj, TVSHOWID);
			votes = parseString(obj, VOTES);
			writer = parseString(obj, WRITER);
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			final JSONArray castArray = new JSONArray();
			for (VideoModel.Cast item : cast) {
				castArray.put(item.toJSONObject());
			}
			castArray.put(castArray);
			obj.put(CAST, castArray);
			obj.put(EPISODE, episode);
			obj.put(EPISODEID, episodeid);
			obj.put(FIRSTAIRED, firstaired);
			obj.put(ORIGINALTITLE, originaltitle);
			obj.put(PRODUCTIONCODE, productioncode);
			obj.put(RATING, rating);
			obj.put(SEASON, season);
			obj.put(SHOWTITLE, showtitle);
			obj.put(TVSHOWID, tvshowid);
			obj.put(VOTES, votes);
			obj.put(WRITER, writer);
			return obj;
		}
		/**
		 * Extracts a list of {@link VideoModel.EpisodeDetails} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.EpisodeDetails> getVideoModelEpisodeDetailsList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<VideoModel.EpisodeDetails> l = new ArrayList<VideoModel.EpisodeDetails>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new VideoModel.EpisodeDetails(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.EpisodeDetails>(0);
		}
	}
	/**
	 * Video.Details.File
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class FileDetails extends VideoModel.ItemDetails {
		public final static String API_TYPE = "Video.Details.File";
		// field names
		public static final String DIRECTOR = "director";
		public static final String RESUME = "resume";
		public static final String RUNTIME = "runtime";
		public static final String STREAMDETAILS = "streamdetails";
		// class members
		public final String director;
		public final VideoModel.Resume resume;
		public final String runtime;
		public final VideoModel.Streams streamdetails;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a FileDetails object
		 */
		public FileDetails(JSONObject obj) throws JSONException {
			super(obj);
			mType = API_TYPE;
			director = parseString(obj, DIRECTOR);
			resume = obj.has(RESUME) ? new VideoModel.Resume(obj.getJSONObject(RESUME)) : null;
			runtime = parseString(obj, RUNTIME);
			streamdetails = obj.has(STREAMDETAILS) ? new VideoModel.Streams(obj.getJSONObject(STREAMDETAILS)) : null;
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(DIRECTOR, director);
			obj.put(RESUME, resume.toJSONObject());
			obj.put(RUNTIME, runtime);
			obj.put(STREAMDETAILS, streamdetails.toJSONObject());
			return obj;
		}
		/**
		 * Extracts a list of {@link VideoModel.FileDetails} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.FileDetails> getVideoModelFileDetailsList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<VideoModel.FileDetails> l = new ArrayList<VideoModel.FileDetails>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new VideoModel.FileDetails(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.FileDetails>(0);
		}
	}
	/**
	 * Video.Details.Item
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class ItemDetails extends VideoModel.MediaDetails {
		public final static String API_TYPE = "Video.Details.Item";
		// field names
		public static final String FILE = "file";
		public static final String LASTPLAYED = "lastplayed";
		public static final String PLOT = "plot";
		// class members
		public final String file;
		public final String lastplayed;
		public final String plot;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a ItemDetails object
		 */
		public ItemDetails(JSONObject obj) throws JSONException {
			super(obj);
			mType = API_TYPE;
			file = parseString(obj, FILE);
			lastplayed = parseString(obj, LASTPLAYED);
			plot = parseString(obj, PLOT);
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(FILE, file);
			obj.put(LASTPLAYED, lastplayed);
			obj.put(PLOT, plot);
			return obj;
		}
		/**
		 * Extracts a list of {@link VideoModel.ItemDetails} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.ItemDetails> getVideoModelItemDetailsList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<VideoModel.ItemDetails> l = new ArrayList<VideoModel.ItemDetails>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new VideoModel.ItemDetails(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.ItemDetails>(0);
		}
	}
	/**
	 * Video.Details.Media
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class MediaDetails extends VideoModel.BaseDetails {
		public final static String API_TYPE = "Video.Details.Media";
		// field names
		public static final String TITLE = "title";
		// class members
		public final String title;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a MediaDetails object
		 */
		public MediaDetails(JSONObject obj) throws JSONException {
			super(obj);
			mType = API_TYPE;
			title = parseString(obj, TITLE);
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(TITLE, title);
			return obj;
		}
		/**
		 * Extracts a list of {@link VideoModel.MediaDetails} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.MediaDetails> getVideoModelMediaDetailsList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<VideoModel.MediaDetails> l = new ArrayList<VideoModel.MediaDetails>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new VideoModel.MediaDetails(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.MediaDetails>(0);
		}
	}
	/**
	 * Video.Details.Movie
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class MovieDetails extends VideoModel.FileDetails {
		public final static String API_TYPE = "Video.Details.Movie";
		// field names
		public static final String CAST = "cast";
		public static final String COUNTRY = "country";
		public static final String GENRE = "genre";
		public static final String IMDBNUMBER = "imdbnumber";
		public static final String MOVIEID = "movieid";
		public static final String MPAA = "mpaa";
		public static final String ORIGINALTITLE = "originaltitle";
		public static final String PLOTOUTLINE = "plotoutline";
		public static final String PREMIERED = "premiered";
		public static final String PRODUCTIONCODE = "productioncode";
		public static final String RATING = "rating";
		public static final String SET = "set";
		public static final String SETID = "setid";
		public static final String SHOWLINK = "showlink";
		public static final String SORTTITLE = "sorttitle";
		public static final String STUDIO = "studio";
		public static final String TAGLINE = "tagline";
		public static final String TOP250 = "top250";
		public static final String TRAILER = "trailer";
		public static final String VOTES = "votes";
		public static final String WRITER = "writer";
		public static final String YEAR = "year";
		// class members
		public final ArrayList<VideoModel.Cast> cast;
		public final String country;
		public final String genre;
		public final String imdbnumber;
		public final int movieid;
		public final String mpaa;
		public final String originaltitle;
		public final String plotoutline;
		public final String premiered;
		public final String productioncode;
		public final Double rating;
		public final ArrayList<String> set;
		public final ArrayList<Integer> setid;
		public final String showlink;
		public final String sorttitle;
		public final String studio;
		public final String tagline;
		public final Integer top250;
		public final String trailer;
		public final String votes;
		public final String writer;
		public final Integer year;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a MovieDetails object
		 */
		public MovieDetails(JSONObject obj) throws JSONException {
			super(obj);
			mType = API_TYPE;
			cast = VideoModel.Cast.getVideoModelCastList(obj, CAST);
			country = parseString(obj, COUNTRY);
			genre = parseString(obj, GENRE);
			imdbnumber = parseString(obj, IMDBNUMBER);
			movieid = obj.getInt(MOVIEID);
			mpaa = parseString(obj, MPAA);
			originaltitle = parseString(obj, ORIGINALTITLE);
			plotoutline = parseString(obj, PLOTOUTLINE);
			premiered = parseString(obj, PREMIERED);
			productioncode = parseString(obj, PRODUCTIONCODE);
			rating = parseDouble(obj, RATING);
			set = getStringArray(obj, SET);
			setid = getIntegerArray(obj, SETID);
			showlink = parseString(obj, SHOWLINK);
			sorttitle = parseString(obj, SORTTITLE);
			studio = parseString(obj, STUDIO);
			tagline = parseString(obj, TAGLINE);
			top250 = parseInt(obj, TOP250);
			trailer = parseString(obj, TRAILER);
			votes = parseString(obj, VOTES);
			writer = parseString(obj, WRITER);
			year = parseInt(obj, YEAR);
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			final JSONArray castArray = new JSONArray();
			for (VideoModel.Cast item : cast) {
				castArray.put(item.toJSONObject());
			}
			castArray.put(castArray);
			obj.put(CAST, castArray);
			obj.put(COUNTRY, country);
			obj.put(GENRE, genre);
			obj.put(IMDBNUMBER, imdbnumber);
			obj.put(MOVIEID, movieid);
			obj.put(MPAA, mpaa);
			obj.put(ORIGINALTITLE, originaltitle);
			obj.put(PLOTOUTLINE, plotoutline);
			obj.put(PREMIERED, premiered);
			obj.put(PRODUCTIONCODE, productioncode);
			obj.put(RATING, rating);
			obj.put(SET, set);
			obj.put(SETID, setid);
			obj.put(SHOWLINK, showlink);
			obj.put(SORTTITLE, sorttitle);
			obj.put(STUDIO, studio);
			obj.put(TAGLINE, tagline);
			obj.put(TOP250, top250);
			obj.put(TRAILER, trailer);
			obj.put(VOTES, votes);
			obj.put(WRITER, writer);
			obj.put(YEAR, year);
			return obj;
		}
		/**
		 * Extracts a list of {@link VideoModel.MovieDetails} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.MovieDetails> getVideoModelMovieDetailsList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<VideoModel.MovieDetails> l = new ArrayList<VideoModel.MovieDetails>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new VideoModel.MovieDetails(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.MovieDetails>(0);
		}
	}
	/**
	 * Video.Details.MovieSet
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class MovieSetDetails extends VideoModel.MediaDetails {
		public final static String API_TYPE = "Video.Details.MovieSet";
		// field names
		public static final String SETID = "setid";
		// class members
		public final int setid;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a MovieSetDetails object
		 */
		public MovieSetDetails(JSONObject obj) throws JSONException {
			super(obj);
			mType = API_TYPE;
			setid = obj.getInt(SETID);
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(SETID, setid);
			return obj;
		}
		/**
		 * Extracts a list of {@link VideoModel.MovieSetDetails} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.MovieSetDetails> getVideoModelMovieSetDetailsList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<VideoModel.MovieSetDetails> l = new ArrayList<VideoModel.MovieSetDetails>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new VideoModel.MovieSetDetails(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.MovieSetDetails>(0);
		}
	}
	/**
	 * Video.Details.MovieSet.Extended
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class DetailsMovieSetExtended extends VideoModel.MovieSetDetails {
		public final static String API_TYPE = "Video.Details.MovieSet.Extended";
		// field names
		public static final String MOVIES = "movies";
		// class members
		public final ArrayList<VideoModel.MovieDetails> movies;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a DetailsMovieSetExtended object
		 */
		public DetailsMovieSetExtended(JSONObject obj) throws JSONException {
			super(obj);
			mType = API_TYPE;
			movies = VideoModel.MovieDetails.getVideoModelMovieDetailsList(obj, MOVIES);
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			final JSONArray moviesArray = new JSONArray();
			for (VideoModel.MovieDetails item : movies) {
				moviesArray.put(item.toJSONObject());
			}
			moviesArray.put(moviesArray);
			obj.put(MOVIES, moviesArray);
			return obj;
		}
		/**
		 * Extracts a list of {@link VideoModel.DetailsMovieSetExtended} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.DetailsMovieSetExtended> getVideoModelDetailsMovieSetExtendedList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<VideoModel.DetailsMovieSetExtended> l = new ArrayList<VideoModel.DetailsMovieSetExtended>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new VideoModel.DetailsMovieSetExtended(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.DetailsMovieSetExtended>(0);
		}
	}
	/**
	 * Video.Details.MusicVideo
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class MusicVideoDetails extends VideoModel.FileDetails {
		public final static String API_TYPE = "Video.Details.MusicVideo";
		// field names
		public static final String ALBUM = "album";
		public static final String ARTIST = "artist";
		public static final String GENRE = "genre";
		public static final String MUSICVIDEOID = "musicvideoid";
		public static final String STUDIO = "studio";
		public static final String TRACK = "track";
		public static final String YEAR = "year";
		// class members
		public final String album;
		public final String artist;
		public final String genre;
		public final int musicvideoid;
		public final String studio;
		public final Integer track;
		public final Integer year;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a MusicVideoDetails object
		 */
		public MusicVideoDetails(JSONObject obj) throws JSONException {
			super(obj);
			mType = API_TYPE;
			album = parseString(obj, ALBUM);
			artist = parseString(obj, ARTIST);
			genre = parseString(obj, GENRE);
			musicvideoid = obj.getInt(MUSICVIDEOID);
			studio = parseString(obj, STUDIO);
			track = parseInt(obj, TRACK);
			year = parseInt(obj, YEAR);
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(ALBUM, album);
			obj.put(ARTIST, artist);
			obj.put(GENRE, genre);
			obj.put(MUSICVIDEOID, musicvideoid);
			obj.put(STUDIO, studio);
			obj.put(TRACK, track);
			obj.put(YEAR, year);
			return obj;
		}
		/**
		 * Extracts a list of {@link VideoModel.MusicVideoDetails} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.MusicVideoDetails> getVideoModelMusicVideoDetailsList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<VideoModel.MusicVideoDetails> l = new ArrayList<VideoModel.MusicVideoDetails>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new VideoModel.MusicVideoDetails(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.MusicVideoDetails>(0);
		}
	}
	/**
	 * Video.Details.Season
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class SeasonDetails extends VideoModel.BaseDetails {
		public final static String API_TYPE = "Video.Details.Season";
		// field names
		public static final String EPISODE = "episode";
		public static final String SEASON = "season";
		public static final String SHOWTITLE = "showtitle";
		public static final String TVSHOWID = "tvshowid";
		// class members
		public final Integer episode;
		public final int season;
		public final String showtitle;
		public final Integer tvshowid;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a SeasonDetails object
		 */
		public SeasonDetails(JSONObject obj) throws JSONException {
			super(obj);
			mType = API_TYPE;
			episode = parseInt(obj, EPISODE);
			season = obj.getInt(SEASON);
			showtitle = parseString(obj, SHOWTITLE);
			tvshowid = parseInt(obj, TVSHOWID);
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(EPISODE, episode);
			obj.put(SEASON, season);
			obj.put(SHOWTITLE, showtitle);
			obj.put(TVSHOWID, tvshowid);
			return obj;
		}
		/**
		 * Extracts a list of {@link VideoModel.SeasonDetails} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.SeasonDetails> getVideoModelSeasonDetailsList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<VideoModel.SeasonDetails> l = new ArrayList<VideoModel.SeasonDetails>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new VideoModel.SeasonDetails(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.SeasonDetails>(0);
		}
	}
	/**
	 * Video.Details.TVShow
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class TVShowDetails extends VideoModel.ItemDetails {
		public final static String API_TYPE = "Video.Details.TVShow";
		// field names
		public static final String CAST = "cast";
		public static final String EPISODE = "episode";
		public static final String EPISODEGUIDE = "episodeguide";
		public static final String GENRE = "genre";
		public static final String IMDBNUMBER = "imdbnumber";
		public static final String MPAA = "mpaa";
		public static final String ORIGINALTITLE = "originaltitle";
		public static final String PREMIERED = "premiered";
		public static final String RATING = "rating";
		public static final String SORTTITLE = "sorttitle";
		public static final String STUDIO = "studio";
		public static final String TVSHOWID = "tvshowid";
		public static final String VOTES = "votes";
		public static final String YEAR = "year";
		// class members
		public final ArrayList<VideoModel.Cast> cast;
		public final Integer episode;
		public final String episodeguide;
		public final String genre;
		public final String imdbnumber;
		public final String mpaa;
		public final String originaltitle;
		public final String premiered;
		public final Double rating;
		public final String sorttitle;
		public final String studio;
		public final int tvshowid;
		public final String votes;
		public final Integer year;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a TVShowDetails object
		 */
		public TVShowDetails(JSONObject obj) throws JSONException {
			super(obj);
			mType = API_TYPE;
			cast = VideoModel.Cast.getVideoModelCastList(obj, CAST);
			episode = parseInt(obj, EPISODE);
			episodeguide = parseString(obj, EPISODEGUIDE);
			genre = parseString(obj, GENRE);
			imdbnumber = parseString(obj, IMDBNUMBER);
			mpaa = parseString(obj, MPAA);
			originaltitle = parseString(obj, ORIGINALTITLE);
			premiered = parseString(obj, PREMIERED);
			rating = parseDouble(obj, RATING);
			sorttitle = parseString(obj, SORTTITLE);
			studio = parseString(obj, STUDIO);
			tvshowid = obj.getInt(TVSHOWID);
			votes = parseString(obj, VOTES);
			year = parseInt(obj, YEAR);
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			final JSONArray castArray = new JSONArray();
			for (VideoModel.Cast item : cast) {
				castArray.put(item.toJSONObject());
			}
			castArray.put(castArray);
			obj.put(CAST, castArray);
			obj.put(EPISODE, episode);
			obj.put(EPISODEGUIDE, episodeguide);
			obj.put(GENRE, genre);
			obj.put(IMDBNUMBER, imdbnumber);
			obj.put(MPAA, mpaa);
			obj.put(ORIGINALTITLE, originaltitle);
			obj.put(PREMIERED, premiered);
			obj.put(RATING, rating);
			obj.put(SORTTITLE, sorttitle);
			obj.put(STUDIO, studio);
			obj.put(TVSHOWID, tvshowid);
			obj.put(VOTES, votes);
			obj.put(YEAR, year);
			return obj;
		}
		/**
		 * Extracts a list of {@link VideoModel.TVShowDetails} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.TVShowDetails> getVideoModelTVShowDetailsList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<VideoModel.TVShowDetails> l = new ArrayList<VideoModel.TVShowDetails>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new VideoModel.TVShowDetails(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.TVShowDetails>(0);
		}
	}
	public interface EpisodeFields {
		public final String TITLE = "title";
		public final String PLOT = "plot";
		public final String VOTES = "votes";
		public final String RATING = "rating";
		public final String WRITER = "writer";
		public final String FIRSTAIRED = "firstaired";
		public final String PLAYCOUNT = "playcount";
		public final String RUNTIME = "runtime";
		public final String DIRECTOR = "director";
		public final String PRODUCTIONCODE = "productioncode";
		public final String SEASON = "season";
		public final String EPISODE = "episode";
		public final String ORIGINALTITLE = "originaltitle";
		public final String SHOWTITLE = "showtitle";
		public final String CAST = "cast";
		public final String STREAMDETAILS = "streamdetails";
		public final String LASTPLAYED = "lastplayed";
		public final String FANART = "fanart";
		public final String THUMBNAIL = "thumbnail";
		public final String FILE = "file";
		public final String RESUME = "resume";
		public final String TVSHOWID = "tvshowid";
	}
	public interface MovieFields {
		public final String TITLE = "title";
		public final String GENRE = "genre";
		public final String YEAR = "year";
		public final String RATING = "rating";
		public final String DIRECTOR = "director";
		public final String TRAILER = "trailer";
		public final String TAGLINE = "tagline";
		public final String PLOT = "plot";
		public final String PLOTOUTLINE = "plotoutline";
		public final String ORIGINALTITLE = "originaltitle";
		public final String LASTPLAYED = "lastplayed";
		public final String PLAYCOUNT = "playcount";
		public final String WRITER = "writer";
		public final String STUDIO = "studio";
		public final String MPAA = "mpaa";
		public final String CAST = "cast";
		public final String COUNTRY = "country";
		public final String IMDBNUMBER = "imdbnumber";
		public final String PREMIERED = "premiered";
		public final String PRODUCTIONCODE = "productioncode";
		public final String RUNTIME = "runtime";
		public final String SET = "set";
		public final String SHOWLINK = "showlink";
		public final String STREAMDETAILS = "streamdetails";
		public final String TOP250 = "top250";
		public final String VOTES = "votes";
		public final String FANART = "fanart";
		public final String THUMBNAIL = "thumbnail";
		public final String FILE = "file";
		public final String SORTTITLE = "sorttitle";
		public final String RESUME = "resume";
		public final String SETID = "setid";
	}
	public interface MovieSetFields {
		public final String TITLE = "title";
		public final String PLAYCOUNT = "playcount";
		public final String FANART = "fanart";
		public final String THUMBNAIL = "thumbnail";
	}
	public interface MusicVideoFields {
		public final String TITLE = "title";
		public final String PLAYCOUNT = "playcount";
		public final String RUNTIME = "runtime";
		public final String DIRECTOR = "director";
		public final String STUDIO = "studio";
		public final String YEAR = "year";
		public final String PLOT = "plot";
		public final String ALBUM = "album";
		public final String ARTIST = "artist";
		public final String GENRE = "genre";
		public final String TRACK = "track";
		public final String STREAMDETAILS = "streamdetails";
		public final String LASTPLAYED = "lastplayed";
		public final String FANART = "fanart";
		public final String THUMBNAIL = "thumbnail";
		public final String FILE = "file";
		public final String RESUME = "resume";
	}
	public interface SeasonFields {
		public final String SEASON = "season";
		public final String SHOWTITLE = "showtitle";
		public final String PLAYCOUNT = "playcount";
		public final String EPISODE = "episode";
		public final String FANART = "fanart";
		public final String THUMBNAIL = "thumbnail";
		public final String TVSHOWID = "tvshowid";
	}
	public interface TVShowFields {
		public final String TITLE = "title";
		public final String GENRE = "genre";
		public final String YEAR = "year";
		public final String RATING = "rating";
		public final String PLOT = "plot";
		public final String STUDIO = "studio";
		public final String MPAA = "mpaa";
		public final String CAST = "cast";
		public final String PLAYCOUNT = "playcount";
		public final String EPISODE = "episode";
		public final String IMDBNUMBER = "imdbnumber";
		public final String PREMIERED = "premiered";
		public final String VOTES = "votes";
		public final String LASTPLAYED = "lastplayed";
		public final String FANART = "fanart";
		public final String THUMBNAIL = "thumbnail";
		public final String FILE = "file";
		public final String ORIGINALTITLE = "originaltitle";
		public final String SORTTITLE = "sorttitle";
		public final String EPISODEGUIDE = "episodeguide";
	}
	/**
	 * Video.Resume
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Resume extends AbstractModel {
		public final static String API_TYPE = "Video.Resume";
		// field names
		public static final String POSITION = "position";
		public static final String TOTAL = "total";
		// class members
		public final Double position;
		public final Double total;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a Resume object
		 */
		public Resume(JSONObject obj) throws JSONException {
			mType = API_TYPE;
			position = parseDouble(obj, POSITION);
			total = parseDouble(obj, TOTAL);
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param position 
		 * @param total 
		 */
		public Resume(Double position, Double total) {
			this.position = position;
			this.total = total;
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(POSITION, position);
			obj.put(TOTAL, total);
			return obj;
		}
		/**
		 * Extracts a list of {@link VideoModel.Resume} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.Resume> getVideoModelResumeList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<VideoModel.Resume> l = new ArrayList<VideoModel.Resume>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new VideoModel.Resume(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.Resume>(0);
		}
	}
	/**
	 * Video.Streams
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Streams extends AbstractModel {
		public final static String API_TYPE = "Video.Streams";
		// field names
		public static final String AUDIO = "audio";
		public static final String SUBTITLE = "subtitle";
		public static final String VIDEO = "video";
		// class members
		public final ArrayList<Audio> audio;
		public final ArrayList<Subtitle> subtitle;
		public final ArrayList<Video> video;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a Streams object
		 */
		public Streams(JSONObject obj) throws JSONException {
			mType = API_TYPE;
			audio = Audio.getAudioList(obj, AUDIO);
			subtitle = Subtitle.getSubtitleList(obj, SUBTITLE);
			video = Video.getVideoList(obj, VIDEO);
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param audio 
		 * @param subtitle 
		 * @param video 
		 */
		public Streams(ArrayList<Audio> audio, ArrayList<Subtitle> subtitle, ArrayList<Video> video) {
			this.audio = audio;
			this.subtitle = subtitle;
			this.video = video;
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			final JSONArray audioArray = new JSONArray();
			for (Audio item : audio) {
				audioArray.put(item.toJSONObject());
			}
			audioArray.put(audioArray);
			obj.put(AUDIO, audioArray);
			final JSONArray subtitleArray = new JSONArray();
			for (Subtitle item : subtitle) {
				subtitleArray.put(item.toJSONObject());
			}
			subtitleArray.put(subtitleArray);
			obj.put(SUBTITLE, subtitleArray);
			final JSONArray videoArray = new JSONArray();
			for (Video item : video) {
				videoArray.put(item.toJSONObject());
			}
			videoArray.put(videoArray);
			obj.put(VIDEO, videoArray);
			return obj;
		}
		/**
		 * Extracts a list of {@link VideoModel.Streams} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.Streams> getVideoModelStreamsList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<VideoModel.Streams> l = new ArrayList<VideoModel.Streams>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new VideoModel.Streams(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.Streams>(0);
		}
		/**
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class Audio implements JSONSerializable {
			// field names
			public static final String CHANNELS = "channels";
			public static final String CODEC = "codec";
			public static final String LANGUAGE = "language";
			// class members
			public final Integer channels;
			public final String codec;
			public final String language;
			/**
			 * Construct from JSON object.
			 * @param obj JSON object representing a Audio object
			 */
			public Audio(JSONObject obj) throws JSONException {
				channels = parseInt(obj, CHANNELS);
				codec = parseString(obj, CODEC);
				language = parseString(obj, LANGUAGE);
			}
			/**
			 * Construct object with native values for later serialization.
			 * @param channels 
			 * @param codec 
			 * @param language 
			 */
			public Audio(Integer channels, String codec, String language) {
				this.channels = channels;
				this.codec = codec;
				this.language = language;
			}
			@Override
			public JSONObject toJSONObject() throws JSONException {
				final JSONObject obj = new JSONObject();
				obj.put(CHANNELS, channels);
				obj.put(CODEC, codec);
				obj.put(LANGUAGE, language);
				return obj;
			}
			/**
			 * Extracts a list of {@link Audio} objects from a JSON array.
			 * @param obj JSONObject containing the list of objects
			 * @param key Key pointing to the node where the list is stored
			 */
			static ArrayList<Audio> getAudioList(JSONObject obj, String key) throws JSONException {
				if (obj.has(key)) {
					final JSONArray a = obj.getJSONArray(key);
					final ArrayList<Audio> l = new ArrayList<Audio>(a.length());
					for (int i = 0; i < a.length(); i++) {
						l.add(new Audio(a.getJSONObject(i)));
					}
					return l;
				}
				return new ArrayList<Audio>(0);
			}
		}
		/**
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class Subtitle implements JSONSerializable {
			// field names
			public static final String LANGUAGE = "language";
			// class members
			public final String language;
			/**
			 * Construct from JSON object.
			 * @param obj JSON object representing a Subtitle object
			 */
			public Subtitle(JSONObject obj) throws JSONException {
				language = parseString(obj, LANGUAGE);
			}
			/**
			 * Construct object with native values for later serialization.
			 * @param language 
			 */
			public Subtitle(String language) {
				this.language = language;
			}
			@Override
			public JSONObject toJSONObject() throws JSONException {
				final JSONObject obj = new JSONObject();
				obj.put(LANGUAGE, language);
				return obj;
			}
			/**
			 * Extracts a list of {@link Subtitle} objects from a JSON array.
			 * @param obj JSONObject containing the list of objects
			 * @param key Key pointing to the node where the list is stored
			 */
			static ArrayList<Subtitle> getSubtitleList(JSONObject obj, String key) throws JSONException {
				if (obj.has(key)) {
					final JSONArray a = obj.getJSONArray(key);
					final ArrayList<Subtitle> l = new ArrayList<Subtitle>(a.length());
					for (int i = 0; i < a.length(); i++) {
						l.add(new Subtitle(a.getJSONObject(i)));
					}
					return l;
				}
				return new ArrayList<Subtitle>(0);
			}
		}
		/**
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class Video implements JSONSerializable {
			// field names
			public static final String ASPECT = "aspect";
			public static final String CODEC = "codec";
			public static final String DURATION = "duration";
			public static final String HEIGHT = "height";
			public static final String WIDTH = "width";
			// class members
			public final Double aspect;
			public final String codec;
			public final Integer duration;
			public final Integer height;
			public final Integer width;
			/**
			 * Construct from JSON object.
			 * @param obj JSON object representing a Video object
			 */
			public Video(JSONObject obj) throws JSONException {
				aspect = parseDouble(obj, ASPECT);
				codec = parseString(obj, CODEC);
				duration = parseInt(obj, DURATION);
				height = parseInt(obj, HEIGHT);
				width = parseInt(obj, WIDTH);
			}
			/**
			 * Construct object with native values for later serialization.
			 * @param aspect 
			 * @param codec 
			 * @param duration 
			 * @param height 
			 * @param width 
			 */
			public Video(Double aspect, String codec, Integer duration, Integer height, Integer width) {
				this.aspect = aspect;
				this.codec = codec;
				this.duration = duration;
				this.height = height;
				this.width = width;
			}
			@Override
			public JSONObject toJSONObject() throws JSONException {
				final JSONObject obj = new JSONObject();
				obj.put(ASPECT, aspect);
				obj.put(CODEC, codec);
				obj.put(DURATION, duration);
				obj.put(HEIGHT, height);
				obj.put(WIDTH, width);
				return obj;
			}
			/**
			 * Extracts a list of {@link Video} objects from a JSON array.
			 * @param obj JSONObject containing the list of objects
			 * @param key Key pointing to the node where the list is stored
			 */
			static ArrayList<Video> getVideoList(JSONObject obj, String key) throws JSONException {
				if (obj.has(key)) {
					final JSONArray a = obj.getJSONArray(key);
					final ArrayList<Video> l = new ArrayList<Video>(a.length());
					for (int i = 0; i < a.length(); i++) {
						l.add(new Video(a.getJSONObject(i)));
					}
					return l;
				}
				return new ArrayList<Video>(0);
			}
		}
	}
}