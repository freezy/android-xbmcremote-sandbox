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

public final class AudioModel {
	/**
	 * Audio.Details.Album
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class AlbumDetails extends AudioModel.MediaDetails {
		public final static String API_TYPE = "Audio.Details.Album";
		// field names
		public static final String ALBUMID = "albumid";
		public static final String ALBUMLABEL = "albumlabel";
		public static final String ARTISTID = "artistid";
		public static final String DESCRIPTION = "description";
		public static final String MOOD = "mood";
		public static final String STYLE = "style";
		public static final String THEME = "theme";
		public static final String TYPE = "type";
		// class members
		public final int albumid;
		public final String albumlabel;
		public final int artistid;
		public final String description;
		public final String mood;
		public final String style;
		public final String theme;
		public final String type;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a AlbumDetails object
		 */
		public AlbumDetails(JSONObject obj) throws JSONException {
			super(obj);
			mType = API_TYPE;
			albumid = obj.getInt(ALBUMID);
			albumlabel = parseString(obj, ALBUMLABEL);
			artistid = parseInt(obj, ARTISTID);
			description = parseString(obj, DESCRIPTION);
			mood = parseString(obj, MOOD);
			style = parseString(obj, STYLE);
			theme = parseString(obj, THEME);
			type = parseString(obj, TYPE);
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(ALBUMID, albumid);
			obj.put(ALBUMLABEL, albumlabel);
			obj.put(ARTISTID, artistid);
			obj.put(DESCRIPTION, description);
			obj.put(MOOD, mood);
			obj.put(STYLE, style);
			obj.put(THEME, theme);
			obj.put(TYPE, type);
			return obj;
		}
		/**
		 * Extracts a list of {@link AudioModel.AlbumDetails} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<AudioModel.AlbumDetails> getAudioModelAlbumDetailsList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<AudioModel.AlbumDetails> l = new ArrayList<AudioModel.AlbumDetails>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new AudioModel.AlbumDetails(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<AudioModel.AlbumDetails>(0);
		}
	}
	/**
	 * Audio.Details.Artist
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class ArtistDetails extends AudioModel.BaseDetails {
		public final static String API_TYPE = "Audio.Details.Artist";
		// field names
		public static final String ARTIST = "artist";
		public static final String ARTISTID = "artistid";
		public static final String BORN = "born";
		public static final String DESCRIPTION = "description";
		public static final String DIED = "died";
		public static final String DISBANDED = "disbanded";
		public static final String FORMED = "formed";
		public static final String INSTRUMENT = "instrument";
		public static final String MOOD = "mood";
		public static final String MUSICBRAINZARTISTID = "musicbrainzartistid";
		public static final String STYLE = "style";
		public static final String YEARSACTIVE = "yearsactive";
		// class members
		public final String artist;
		public final int artistid;
		public final String born;
		public final String description;
		public final String died;
		public final String disbanded;
		public final String formed;
		public final String instrument;
		public final String mood;
		public final String musicbrainzartistid;
		public final String style;
		public final String yearsactive;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a ArtistDetails object
		 */
		public ArtistDetails(JSONObject obj) throws JSONException {
			super(obj);
			mType = API_TYPE;
			artist = obj.getString(ARTIST);
			artistid = obj.getInt(ARTISTID);
			born = parseString(obj, BORN);
			description = parseString(obj, DESCRIPTION);
			died = parseString(obj, DIED);
			disbanded = parseString(obj, DISBANDED);
			formed = parseString(obj, FORMED);
			instrument = parseString(obj, INSTRUMENT);
			mood = parseString(obj, MOOD);
			musicbrainzartistid = parseString(obj, MUSICBRAINZARTISTID);
			style = parseString(obj, STYLE);
			yearsactive = parseString(obj, YEARSACTIVE);
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(ARTIST, artist);
			obj.put(ARTISTID, artistid);
			obj.put(BORN, born);
			obj.put(DESCRIPTION, description);
			obj.put(DIED, died);
			obj.put(DISBANDED, disbanded);
			obj.put(FORMED, formed);
			obj.put(INSTRUMENT, instrument);
			obj.put(MOOD, mood);
			obj.put(MUSICBRAINZARTISTID, musicbrainzartistid);
			obj.put(STYLE, style);
			obj.put(YEARSACTIVE, yearsactive);
			return obj;
		}
		/**
		 * Extracts a list of {@link AudioModel.ArtistDetails} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<AudioModel.ArtistDetails> getAudioModelArtistDetailsList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<AudioModel.ArtistDetails> l = new ArrayList<AudioModel.ArtistDetails>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new AudioModel.ArtistDetails(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<AudioModel.ArtistDetails>(0);
		}
	}
	/**
	 * Audio.Details.Base
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class BaseDetails extends MediaModel.BaseDetails {
		public final static String API_TYPE = "Audio.Details.Base";
		// field names
		public static final String GENRE = "genre";
		// class members
		public final String genre;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a BaseDetails object
		 */
		public BaseDetails(JSONObject obj) throws JSONException {
			super(obj);
			mType = API_TYPE;
			genre = parseString(obj, GENRE);
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(GENRE, genre);
			return obj;
		}
		/**
		 * Extracts a list of {@link AudioModel.BaseDetails} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<AudioModel.BaseDetails> getAudioModelBaseDetailsList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<AudioModel.BaseDetails> l = new ArrayList<AudioModel.BaseDetails>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new AudioModel.BaseDetails(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<AudioModel.BaseDetails>(0);
		}
	}
	/**
	 * Audio.Details.Media
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class MediaDetails extends AudioModel.BaseDetails {
		public final static String API_TYPE = "Audio.Details.Media";
		// field names
		public static final String ARTIST = "artist";
		public static final String MUSICBRAINZALBUMARTISTID = "musicbrainzalbumartistid";
		public static final String MUSICBRAINZALBUMID = "musicbrainzalbumid";
		public static final String RATING = "rating";
		public static final String TITLE = "title";
		public static final String YEAR = "year";
		// class members
		public final String artist;
		public final String musicbrainzalbumartistid;
		public final String musicbrainzalbumid;
		public final int rating;
		public final String title;
		public final int year;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a MediaDetails object
		 */
		public MediaDetails(JSONObject obj) throws JSONException {
			super(obj);
			mType = API_TYPE;
			artist = parseString(obj, ARTIST);
			musicbrainzalbumartistid = parseString(obj, MUSICBRAINZALBUMARTISTID);
			musicbrainzalbumid = parseString(obj, MUSICBRAINZALBUMID);
			rating = parseInt(obj, RATING);
			title = parseString(obj, TITLE);
			year = parseInt(obj, YEAR);
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(ARTIST, artist);
			obj.put(MUSICBRAINZALBUMARTISTID, musicbrainzalbumartistid);
			obj.put(MUSICBRAINZALBUMID, musicbrainzalbumid);
			obj.put(RATING, rating);
			obj.put(TITLE, title);
			obj.put(YEAR, year);
			return obj;
		}
		/**
		 * Extracts a list of {@link AudioModel.MediaDetails} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<AudioModel.MediaDetails> getAudioModelMediaDetailsList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<AudioModel.MediaDetails> l = new ArrayList<AudioModel.MediaDetails>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new AudioModel.MediaDetails(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<AudioModel.MediaDetails>(0);
		}
	}
	/**
	 * Audio.Details.Song
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class SongDetails extends AudioModel.MediaDetails {
		public final static String API_TYPE = "Audio.Details.Song";
		// field names
		public static final String ALBUM = "album";
		public static final String ALBUMARTIST = "albumartist";
		public static final String ALBUMID = "albumid";
		public static final String ARTISTID = "artistid";
		public static final String COMMENT = "comment";
		public static final String DURATION = "duration";
		public static final String FILE = "file";
		public static final String LYRICS = "lyrics";
		public static final String MUSICBRAINZARTISTID = "musicbrainzartistid";
		public static final String MUSICBRAINZTRACKID = "musicbrainztrackid";
		public static final String PLAYCOUNT = "playcount";
		public static final String SONGID = "songid";
		public static final String TRACK = "track";
		// class members
		public final String album;
		public final String albumartist;
		public final int albumid;
		public final int artistid;
		public final String comment;
		public final int duration;
		public final String file;
		public final String lyrics;
		public final String musicbrainzartistid;
		public final String musicbrainztrackid;
		public final int playcount;
		public final int songid;
		public final int track;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a SongDetails object
		 */
		public SongDetails(JSONObject obj) throws JSONException {
			super(obj);
			mType = API_TYPE;
			album = parseString(obj, ALBUM);
			albumartist = parseString(obj, ALBUMARTIST);
			albumid = parseInt(obj, ALBUMID);
			artistid = parseInt(obj, ARTISTID);
			comment = parseString(obj, COMMENT);
			duration = parseInt(obj, DURATION);
			file = parseString(obj, FILE);
			lyrics = parseString(obj, LYRICS);
			musicbrainzartistid = parseString(obj, MUSICBRAINZARTISTID);
			musicbrainztrackid = parseString(obj, MUSICBRAINZTRACKID);
			playcount = parseInt(obj, PLAYCOUNT);
			songid = obj.getInt(SONGID);
			track = parseInt(obj, TRACK);
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(ALBUM, album);
			obj.put(ALBUMARTIST, albumartist);
			obj.put(ALBUMID, albumid);
			obj.put(ARTISTID, artistid);
			obj.put(COMMENT, comment);
			obj.put(DURATION, duration);
			obj.put(FILE, file);
			obj.put(LYRICS, lyrics);
			obj.put(MUSICBRAINZARTISTID, musicbrainzartistid);
			obj.put(MUSICBRAINZTRACKID, musicbrainztrackid);
			obj.put(PLAYCOUNT, playcount);
			obj.put(SONGID, songid);
			obj.put(TRACK, track);
			return obj;
		}
		/**
		 * Extracts a list of {@link AudioModel.SongDetails} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<AudioModel.SongDetails> getAudioModelSongDetailsList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<AudioModel.SongDetails> l = new ArrayList<AudioModel.SongDetails>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new AudioModel.SongDetails(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<AudioModel.SongDetails>(0);
		}
	}
	public interface AlbumFields {
		public final String TITLE = "title";
		public final String DESCRIPTION = "description";
		public final String ARTIST = "artist";
		public final String GENRE = "genre";
		public final String THEME = "theme";
		public final String MOOD = "mood";
		public final String STYLE = "style";
		public final String TYPE = "type";
		public final String ALBUMLABEL = "albumlabel";
		public final String RATING = "rating";
		public final String YEAR = "year";
		public final String MUSICBRAINZALBUMID = "musicbrainzalbumid";
		public final String MUSICBRAINZALBUMARTISTID = "musicbrainzalbumartistid";
		public final String FANART = "fanart";
		public final String THUMBNAIL = "thumbnail";
		public final String ARTISTID = "artistid";
	}
	public interface ArtistFields {
		public final String INSTRUMENT = "instrument";
		public final String STYLE = "style";
		public final String MOOD = "mood";
		public final String BORN = "born";
		public final String FORMED = "formed";
		public final String DESCRIPTION = "description";
		public final String GENRE = "genre";
		public final String DIED = "died";
		public final String DISBANDED = "disbanded";
		public final String YEARSACTIVE = "yearsactive";
		public final String MUSICBRAINZARTISTID = "musicbrainzartistid";
		public final String FANART = "fanart";
		public final String THUMBNAIL = "thumbnail";
	}
	public interface SongFields {
		public final String TITLE = "title";
		public final String ARTIST = "artist";
		public final String ALBUMARTIST = "albumartist";
		public final String GENRE = "genre";
		public final String YEAR = "year";
		public final String RATING = "rating";
		public final String ALBUM = "album";
		public final String TRACK = "track";
		public final String DURATION = "duration";
		public final String COMMENT = "comment";
		public final String LYRICS = "lyrics";
		public final String MUSICBRAINZTRACKID = "musicbrainztrackid";
		public final String MUSICBRAINZARTISTID = "musicbrainzartistid";
		public final String MUSICBRAINZALBUMID = "musicbrainzalbumid";
		public final String MUSICBRAINZALBUMARTISTID = "musicbrainzalbumartistid";
		public final String PLAYCOUNT = "playcount";
		public final String FANART = "fanart";
		public final String THUMBNAIL = "thumbnail";
		public final String FILE = "file";
		public final String ARTISTID = "artistid";
		public final String ALBUMID = "albumid";
	}
}