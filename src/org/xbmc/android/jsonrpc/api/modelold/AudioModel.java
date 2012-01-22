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

package org.xbmc.android.jsonrpc.api.modelold;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Defines all types in the <code>Audio.*</code> namespace.
 * 
 * @deprecated
 * @author freezy <freezy@xbmc.org>
 */
public final class AudioModel {
	
	public static class BaseDetails extends MediaModel.BaseDetails {
		public final static String TYPE = "Audio.Details.Base";
		public final String genre;
		public BaseDetails(JSONObject obj) throws JSONException {
			super(obj);
			mType = TYPE;
			genre = parseString(obj, "genre");
		}
	}
	
	public static class MediaDetails extends BaseDetails {
		public final static String TYPE = "Audio.Details.Media";
		public final String title;
		public final String artist;
		public final int year;
		public final int rating;
		public final String musicbrainzalbumid;
		public final String musicbrainzalbumartistid;
		public MediaDetails(JSONObject obj) throws JSONException {
			super(obj);
			mType = TYPE;
			title = parseString(obj, "title");
			artist = parseString(obj, "artist");
			year = parseInt(obj, "year");
			rating = parseInt(obj, "rating");
			musicbrainzalbumid = parseString(obj, "musicbrainzalbumid");
			musicbrainzalbumartistid = parseString(obj, "musicbrainzalbumartistid");
		}
		@Override
		public String toString() {
			return String.format("%s by %s (%d)", title, artist, year);
		}
	}
	
	public static class AlbumDetails extends MediaDetails {
		public final static String TYPE = "Audio.Details.Album";
		public int albumid;
		public final String description;
		public final String theme;
		public final String mood;
		public final String style;
		public final String type;
		public final String albumlabel;
		public int artistid;
		public AlbumDetails(JSONObject obj) throws JSONException {
			super(obj);
			mType = TYPE;
			albumid = obj.getInt("albumid");
			description = parseString(obj, "description");
			theme = parseString(obj, "theme");
			mood = parseString(obj, "mood");
			style = parseString(obj, "style");
			type = parseString(obj, "type");
			albumlabel = parseString(obj, "albumlabel");
			artistid = parseInt(obj, "artistid");
		}
	}
	
	public static class SongDetails extends MediaDetails {
		public final static String TYPE = "Audio.Details.Song";
		public final int songid;
		public final String file;
		public final String albumartist;
		public final String album;
		public final int track;
		public final int duration;
		public final String comment;
		public final String lyrics;
		public final int playcount;
		public final String musicbrainztrackid;
		public final String musicbrainzartistid;
		public final int artistid;
		public final int albumid;
		public SongDetails(JSONObject obj) throws JSONException {
			super(obj);
			mType = TYPE;
			songid = obj.getInt("songid");
			file = parseString(obj, "file");
			albumartist = parseString(obj, "albumartist");
			album = parseString(obj, "album");
			track = parseInt(obj, "track");
			duration = parseInt(obj, "duration");
			comment = parseString(obj, "comment");
			lyrics = parseString(obj, "lyrics");
			playcount = parseInt(obj, "playcount");
			musicbrainztrackid = parseString(obj, "musicbrainztrackid");
			musicbrainzartistid = parseString(obj, "musicbrainzartistid");
			artistid = parseInt(obj, "artistid");
			albumid = parseInt(obj, "albumid");
		}
		@Override
		public String toString() {
			return String.format("%s by %s (%d) on %s", title, artist, year, album);
		}
	}
	
	public static class ArtistDetails extends BaseDetails {
		public final static String TYPE = "Audio.Details.Artist";
		public int artistid;
		public final String artist;
		public final String instrument;
		public final String style;
		public final String mood;
		public final String born;
		public final String formed;
		public final String description;
		public final String died;
		public final String disbanded;
		public final String yearsactive;
		public final String musicbrainzartistid;
		public ArtistDetails(JSONObject obj) throws JSONException {
			super(obj);
			mType = TYPE;
			artistid = obj.getInt("artistid");
			artist = obj.getString("artist");
			instrument = parseString(obj, "instrument");
			style = parseString(obj, "style");
			mood = parseString(obj, "mood");
			born = parseString(obj, "born");
			formed = parseString(obj, "formed");
			description = parseString(obj, "description");
			died = parseString(obj, "died");
			disbanded = parseString(obj, "disbanded");
			yearsactive = parseString(obj, "yearsactive");
			musicbrainzartistid = parseString(obj, "musicbrainzartistid");
		}
	}
	
	
	/*========================================================================* 
	 *  FIELDS 
	 *========================================================================*/
	
	/**
	 * Audio.Fields.Song
	 */
	public interface SongFields extends ItemModel.BaseFields {
		final String TITE = "title";
		final String ARTIST = "artist";
		final String ALBUMARTIST = "albumartist";
		final String GENRE = "genre";
		final String YEAR = "year";
		final String RATING = "rating";
		final String ALBUM = "album";
		final String TRACK = "track";
		final String DURATION = "duration";
		final String COMMENT = "comment";
		final String LYRICS= "lyrics";
		final String MUSICBRAINZTRACKID = "musicbrainztrackid";
		final String MUSICBRAINZARTISTID = "musicbrainzartistid";
		final String MUSICBRAINZALBUMID = "musicbrainzalbumid";
		final String MUSICBRAINZALBUMARTISTID = "musicbrainzalbumartistid";
		final String PLAYCOUNT = "playcount";
		final String FANART = "fanart";
		final String THUMBNAIL = "thumbnail";
		final String FILE = "file";
		final String ARTISTID = "artistid";
		final String ALBUMID = "albumid";
	}

	/**
	 * Audio.Fields.Artist
	 */
	public interface ArtistFields extends ItemModel.BaseFields {
		final String INSTRUMENT = "instrument";
		final String STYLE = "style";
		final String MOOD = "mood";
		final String BORN = "born";
		final String FORMED = "formed";
		final String DESCRIPTION = "description";
		final String GENRE = "genre";
		final String DIED = "died";
		final String DISBANDED = "disbanded";
		final String YEARSACTIVE = "yearsactive";
		final String MUSICBRAINZARTISTID = "musicbrainzartistid";
		final String FANART = "fanart";
		final String THUMBNAIL = "thumbnail";
	}
	
	/**
	 * Audio.Fields.Album
	 */
	public interface AlbumFields extends ItemModel.BaseFields {
		final String TITLE = "title";
		final String DESCRIPTION = "description";
		final String ARTIST = "artist";
		final String GENRE = "genre";
		final String THEME = "theme";
		final String MOOD = "mood";
		final String STYLE = "style";
		final String TYPE = "type";
		final String LABEL = "label";
		final String RATING = "rating";
		final String YEAR = "year";
		final String MUSICBRAINZALBUMID = "musicbrainzalbumid";
		final String MUSICBRAINZALBUMARTISTID = "musicbrainzalbumartistid";
		final String FANART = "fanart";
		final String THUMBNAIL = "thumbnail";
		final String ARTISTID = "artistid";
	}
	
}
