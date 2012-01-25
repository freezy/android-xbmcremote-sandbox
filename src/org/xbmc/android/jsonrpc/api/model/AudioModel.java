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
		public final Integer artistid;
		public final String description;
		public final String mood;
		public final String style;
		public final String theme;
		public final String type;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a AlbumDetails object
		 */
		public AlbumDetails(ObjectNode node) {
			super(node);
			mType = API_TYPE;
			albumid = node.get(ALBUMID).getIntValue();
			albumlabel = parseString(node, ALBUMLABEL);
			artistid = parseInt(node, ARTISTID);
			description = parseString(node, DESCRIPTION);
			mood = parseString(node, MOOD);
			style = parseString(node, STYLE);
			theme = parseString(node, THEME);
			type = parseString(node, TYPE);
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(ALBUMID, albumid);
			node.put(ALBUMLABEL, albumlabel);
			node.put(ARTISTID, artistid);
			node.put(DESCRIPTION, description);
			node.put(MOOD, mood);
			node.put(STYLE, style);
			node.put(THEME, theme);
			node.put(TYPE, type);
			return node;
		}
		/**
		 * Extracts a list of {@link AudioModel.AlbumDetails} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<AudioModel.AlbumDetails> getAudioModelAlbumDetailsList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<AudioModel.AlbumDetails> l = new ArrayList<AudioModel.AlbumDetails>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new AudioModel.AlbumDetails((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<AudioModel.AlbumDetails>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(albumid);
			parcel.writeValue(albumlabel);
			parcel.writeValue(artistid);
			parcel.writeValue(description);
			parcel.writeValue(mood);
			parcel.writeValue(style);
			parcel.writeValue(theme);
			parcel.writeValue(type);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected AlbumDetails(Parcel parcel) {
			super(parcel);
			albumid = parcel.readInt();
			albumlabel = parcel.readString();
			artistid = parcel.readInt();
			description = parcel.readString();
			mood = parcel.readString();
			style = parcel.readString();
			theme = parcel.readString();
			type = parcel.readString();
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<AlbumDetails> CREATOR = new Parcelable.Creator<AlbumDetails>() {
			@Override
			public AlbumDetails createFromParcel(Parcel parcel) {
				return new AlbumDetails(parcel);
			}
			@Override
			public AlbumDetails[] newArray(int n) {
				return new AlbumDetails[n];
			}
		};
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
		public ArtistDetails(ObjectNode node) {
			super(node);
			mType = API_TYPE;
			artist = node.get(ARTIST).getTextValue();
			artistid = node.get(ARTISTID).getIntValue();
			born = parseString(node, BORN);
			description = parseString(node, DESCRIPTION);
			died = parseString(node, DIED);
			disbanded = parseString(node, DISBANDED);
			formed = parseString(node, FORMED);
			instrument = parseString(node, INSTRUMENT);
			mood = parseString(node, MOOD);
			musicbrainzartistid = parseString(node, MUSICBRAINZARTISTID);
			style = parseString(node, STYLE);
			yearsactive = parseString(node, YEARSACTIVE);
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(ARTIST, artist);
			node.put(ARTISTID, artistid);
			node.put(BORN, born);
			node.put(DESCRIPTION, description);
			node.put(DIED, died);
			node.put(DISBANDED, disbanded);
			node.put(FORMED, formed);
			node.put(INSTRUMENT, instrument);
			node.put(MOOD, mood);
			node.put(MUSICBRAINZARTISTID, musicbrainzartistid);
			node.put(STYLE, style);
			node.put(YEARSACTIVE, yearsactive);
			return node;
		}
		/**
		 * Extracts a list of {@link AudioModel.ArtistDetails} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<AudioModel.ArtistDetails> getAudioModelArtistDetailsList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<AudioModel.ArtistDetails> l = new ArrayList<AudioModel.ArtistDetails>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new AudioModel.ArtistDetails((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<AudioModel.ArtistDetails>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(artist);
			parcel.writeValue(artistid);
			parcel.writeValue(born);
			parcel.writeValue(description);
			parcel.writeValue(died);
			parcel.writeValue(disbanded);
			parcel.writeValue(formed);
			parcel.writeValue(instrument);
			parcel.writeValue(mood);
			parcel.writeValue(musicbrainzartistid);
			parcel.writeValue(style);
			parcel.writeValue(yearsactive);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected ArtistDetails(Parcel parcel) {
			super(parcel);
			artist = parcel.readString();
			artistid = parcel.readInt();
			born = parcel.readString();
			description = parcel.readString();
			died = parcel.readString();
			disbanded = parcel.readString();
			formed = parcel.readString();
			instrument = parcel.readString();
			mood = parcel.readString();
			musicbrainzartistid = parcel.readString();
			style = parcel.readString();
			yearsactive = parcel.readString();
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<ArtistDetails> CREATOR = new Parcelable.Creator<ArtistDetails>() {
			@Override
			public ArtistDetails createFromParcel(Parcel parcel) {
				return new ArtistDetails(parcel);
			}
			@Override
			public ArtistDetails[] newArray(int n) {
				return new ArtistDetails[n];
			}
		};
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
		public BaseDetails(ObjectNode node) {
			super(node);
			mType = API_TYPE;
			genre = parseString(node, GENRE);
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(GENRE, genre);
			return node;
		}
		/**
		 * Extracts a list of {@link AudioModel.BaseDetails} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<AudioModel.BaseDetails> getAudioModelBaseDetailsList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<AudioModel.BaseDetails> l = new ArrayList<AudioModel.BaseDetails>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new AudioModel.BaseDetails((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<AudioModel.BaseDetails>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(genre);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected BaseDetails(Parcel parcel) {
			super(parcel);
			genre = parcel.readString();
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<BaseDetails> CREATOR = new Parcelable.Creator<BaseDetails>() {
			@Override
			public BaseDetails createFromParcel(Parcel parcel) {
				return new BaseDetails(parcel);
			}
			@Override
			public BaseDetails[] newArray(int n) {
				return new BaseDetails[n];
			}
		};
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
		public final Integer rating;
		public final String title;
		public final Integer year;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a MediaDetails object
		 */
		public MediaDetails(ObjectNode node) {
			super(node);
			mType = API_TYPE;
			artist = parseString(node, ARTIST);
			musicbrainzalbumartistid = parseString(node, MUSICBRAINZALBUMARTISTID);
			musicbrainzalbumid = parseString(node, MUSICBRAINZALBUMID);
			rating = parseInt(node, RATING);
			title = parseString(node, TITLE);
			year = parseInt(node, YEAR);
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(ARTIST, artist);
			node.put(MUSICBRAINZALBUMARTISTID, musicbrainzalbumartistid);
			node.put(MUSICBRAINZALBUMID, musicbrainzalbumid);
			node.put(RATING, rating);
			node.put(TITLE, title);
			node.put(YEAR, year);
			return node;
		}
		/**
		 * Extracts a list of {@link AudioModel.MediaDetails} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<AudioModel.MediaDetails> getAudioModelMediaDetailsList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<AudioModel.MediaDetails> l = new ArrayList<AudioModel.MediaDetails>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new AudioModel.MediaDetails((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<AudioModel.MediaDetails>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(artist);
			parcel.writeValue(musicbrainzalbumartistid);
			parcel.writeValue(musicbrainzalbumid);
			parcel.writeValue(rating);
			parcel.writeValue(title);
			parcel.writeValue(year);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected MediaDetails(Parcel parcel) {
			super(parcel);
			artist = parcel.readString();
			musicbrainzalbumartistid = parcel.readString();
			musicbrainzalbumid = parcel.readString();
			rating = parcel.readInt();
			title = parcel.readString();
			year = parcel.readInt();
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<MediaDetails> CREATOR = new Parcelable.Creator<MediaDetails>() {
			@Override
			public MediaDetails createFromParcel(Parcel parcel) {
				return new MediaDetails(parcel);
			}
			@Override
			public MediaDetails[] newArray(int n) {
				return new MediaDetails[n];
			}
		};
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
		public final Integer albumid;
		public final Integer artistid;
		public final String comment;
		public final Integer duration;
		public final String file;
		public final String lyrics;
		public final String musicbrainzartistid;
		public final String musicbrainztrackid;
		public final Integer playcount;
		public final int songid;
		public final Integer track;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a SongDetails object
		 */
		public SongDetails(ObjectNode node) {
			super(node);
			mType = API_TYPE;
			album = parseString(node, ALBUM);
			albumartist = parseString(node, ALBUMARTIST);
			albumid = parseInt(node, ALBUMID);
			artistid = parseInt(node, ARTISTID);
			comment = parseString(node, COMMENT);
			duration = parseInt(node, DURATION);
			file = parseString(node, FILE);
			lyrics = parseString(node, LYRICS);
			musicbrainzartistid = parseString(node, MUSICBRAINZARTISTID);
			musicbrainztrackid = parseString(node, MUSICBRAINZTRACKID);
			playcount = parseInt(node, PLAYCOUNT);
			songid = node.get(SONGID).getIntValue();
			track = parseInt(node, TRACK);
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(ALBUM, album);
			node.put(ALBUMARTIST, albumartist);
			node.put(ALBUMID, albumid);
			node.put(ARTISTID, artistid);
			node.put(COMMENT, comment);
			node.put(DURATION, duration);
			node.put(FILE, file);
			node.put(LYRICS, lyrics);
			node.put(MUSICBRAINZARTISTID, musicbrainzartistid);
			node.put(MUSICBRAINZTRACKID, musicbrainztrackid);
			node.put(PLAYCOUNT, playcount);
			node.put(SONGID, songid);
			node.put(TRACK, track);
			return node;
		}
		/**
		 * Extracts a list of {@link AudioModel.SongDetails} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<AudioModel.SongDetails> getAudioModelSongDetailsList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<AudioModel.SongDetails> l = new ArrayList<AudioModel.SongDetails>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new AudioModel.SongDetails((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<AudioModel.SongDetails>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(album);
			parcel.writeValue(albumartist);
			parcel.writeValue(albumid);
			parcel.writeValue(artistid);
			parcel.writeValue(comment);
			parcel.writeValue(duration);
			parcel.writeValue(file);
			parcel.writeValue(lyrics);
			parcel.writeValue(musicbrainzartistid);
			parcel.writeValue(musicbrainztrackid);
			parcel.writeValue(playcount);
			parcel.writeValue(songid);
			parcel.writeValue(track);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected SongDetails(Parcel parcel) {
			super(parcel);
			album = parcel.readString();
			albumartist = parcel.readString();
			albumid = parcel.readInt();
			artistid = parcel.readInt();
			comment = parcel.readString();
			duration = parcel.readInt();
			file = parcel.readString();
			lyrics = parcel.readString();
			musicbrainzartistid = parcel.readString();
			musicbrainztrackid = parcel.readString();
			playcount = parcel.readInt();
			songid = parcel.readInt();
			track = parcel.readInt();
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<SongDetails> CREATOR = new Parcelable.Creator<SongDetails>() {
			@Override
			public SongDetails createFromParcel(Parcel parcel) {
				return new SongDetails(parcel);
			}
			@Override
			public SongDetails[] newArray(int n) {
				return new SongDetails[n];
			}
		};
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