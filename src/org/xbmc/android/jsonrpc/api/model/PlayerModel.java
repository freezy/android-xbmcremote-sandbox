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
import org.xbmc.android.jsonrpc.api.AbstractModel;

public final class PlayerModel {
	/**
	 * Player.Audio.Stream
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class AudioStream extends AbstractModel {
		public final static String API_TYPE = "Player.Audio.Stream";
		// field names
		public static final String INDEX = "index";
		public static final String LANGUAGE = "language";
		public static final String NAME = "name";
		// class members
		public final int index;
		public final String language;
		public final String name;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a AudioStream object
		 */
		public AudioStream(ObjectNode node) {
			mType = API_TYPE;
			index = node.get(INDEX).getIntValue();
			language = node.get(LANGUAGE).getTextValue();
			name = node.get(NAME).getTextValue();
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param index 
		 * @param language 
		 * @param name 
		 */
		public AudioStream(int index, String language, String name) {
			this.index = index;
			this.language = language;
			this.name = name;
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(INDEX, index);
			node.put(LANGUAGE, language);
			node.put(NAME, name);
			return node;
		}
		/**
		 * Extracts a list of {@link PlayerModel.AudioStream} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<PlayerModel.AudioStream> getPlayerModelAudioStreamList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<PlayerModel.AudioStream> l = new ArrayList<PlayerModel.AudioStream>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new PlayerModel.AudioStream((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<PlayerModel.AudioStream>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeValue(index);
			parcel.writeValue(language);
			parcel.writeValue(name);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected AudioStream(Parcel parcel) {
			index = parcel.readInt();
			language = parcel.readString();
			name = parcel.readString();
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<AudioStream> CREATOR = new Parcelable.Creator<AudioStream>() {
			@Override
			public AudioStream createFromParcel(Parcel parcel) {
				return new AudioStream(parcel);
			}
			@Override
			public AudioStream[] newArray(int n) {
				return new AudioStream[n];
			}
		};
	}
	/**
	 * Player.Audio.Stream.Extended
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class AudioStreamExtended extends PlayerModel.AudioStream {
		public final static String API_TYPE = "Player.Audio.Stream.Extended";
		// field names
		public static final String BITRATE = "bitrate";
		public static final String CHANNELS = "channels";
		public static final String CODEC = "codec";
		// class members
		public final int bitrate;
		public final int channels;
		public final String codec;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a AudioStreamExtended object
		 */
		public AudioStreamExtended(ObjectNode node) {
			super(node);
			mType = API_TYPE;
			bitrate = node.get(BITRATE).getIntValue();
			channels = node.get(CHANNELS).getIntValue();
			codec = node.get(CODEC).getTextValue();
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(BITRATE, bitrate);
			node.put(CHANNELS, channels);
			node.put(CODEC, codec);
			return node;
		}
		/**
		 * Extracts a list of {@link PlayerModel.AudioStreamExtended} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<PlayerModel.AudioStreamExtended> getPlayerModelAudioStreamExtendedList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<PlayerModel.AudioStreamExtended> l = new ArrayList<PlayerModel.AudioStreamExtended>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new PlayerModel.AudioStreamExtended((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<PlayerModel.AudioStreamExtended>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(bitrate);
			parcel.writeValue(channels);
			parcel.writeValue(codec);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected AudioStreamExtended(Parcel parcel) {
			super(parcel);
			bitrate = parcel.readInt();
			channels = parcel.readInt();
			codec = parcel.readString();
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<AudioStreamExtended> CREATOR = new Parcelable.Creator<AudioStreamExtended>() {
			@Override
			public AudioStreamExtended createFromParcel(Parcel parcel) {
				return new AudioStreamExtended(parcel);
			}
			@Override
			public AudioStreamExtended[] newArray(int n) {
				return new AudioStreamExtended[n];
			}
		};
	}
	/**
	 * Player.Notifications.Data
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class NotificationsData extends AbstractModel {
		public final static String API_TYPE = "Player.Notifications.Data";
		// field names
		public static final String ITEM = "item";
		public static final String PLAYER = "player";
		// class members
		public final PlayerModel.NotificationsItem item;
		public final PlayerModel.NotificationsPlayer player;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a NotificationsData object
		 */
		public NotificationsData(ObjectNode node) {
			mType = API_TYPE;
			item = new NotificationsItem((ObjectNode)node.get(ITEM));
			player = new PlayerModel.NotificationsPlayer((ObjectNode)node.get(PLAYER));
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param item 
		 * @param player 
		 */
		public NotificationsData(PlayerModel.NotificationsItem item, PlayerModel.NotificationsPlayer player) {
			this.item = item;
			this.player = player;
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(ITEM, item.toObjectNode());
			node.put(PLAYER, player.toObjectNode());
			return node;
		}
		/**
		 * Extracts a list of {@link PlayerModel.NotificationsData} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<PlayerModel.NotificationsData> getPlayerModelNotificationsDataList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<PlayerModel.NotificationsData> l = new ArrayList<PlayerModel.NotificationsData>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new PlayerModel.NotificationsData((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<PlayerModel.NotificationsData>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeValue(item);
			parcel.writeValue(player);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected NotificationsData(Parcel parcel) {
			item = parcel.<PlayerModel.NotificationsItem>readParcelable(PlayerModel.NotificationsItem.class.getClassLoader());
			player = parcel.<PlayerModel.NotificationsPlayer>readParcelable(PlayerModel.NotificationsPlayer.class.getClassLoader());
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<NotificationsData> CREATOR = new Parcelable.Creator<NotificationsData>() {
			@Override
			public NotificationsData createFromParcel(Parcel parcel) {
				return new NotificationsData(parcel);
			}
			@Override
			public NotificationsData[] newArray(int n) {
				return new NotificationsData[n];
			}
		};
	}
	/**
	 * Player.Notifications.Item
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class NotificationsItem extends AbstractModel {
		public final static String API_TYPE = "Player.Notifications.Item";
		// field names
		public static final String TYPE = "type";
		public static final String ID = "id";
		public static final String TITLE = "title";
		public static final String YEAR = "year";
		public static final String EPISODE = "episode";
		public static final String SEASON = "season";
		public static final String SHOWTITLE = "showtitle";
		public static final String ALBUM = "album";
		public static final String ARTIST = "artist";
		public static final String TRACK = "track";
		// class members
		public final String type;
		public final int id;
		public final String title;
		public final Integer year;
		public final Integer episode;
		public final Integer season;
		public final String showtitle;
		public final String album;
		public final String artist;
		public final Integer track;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a NotificationsItem object
		 */
		public NotificationsItem(ObjectNode node) {
			mType = API_TYPE;
			type = node.get(TYPE).getTextValue();
			id = node.get(ID).getIntValue();
			title = node.get(TITLE).getTextValue();
			year = parseInt(node, YEAR);
			episode = parseInt(node, EPISODE);
			season = parseInt(node, SEASON);
			showtitle = parseString(node, SHOWTITLE);
			album = parseString(node, ALBUM);
			artist = parseString(node, ARTIST);
			track = parseInt(node, TRACK);
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param type 
		 * @param id 
		 * @param title 
		 * @param year 
		 * @param episode 
		 * @param season 
		 * @param showtitle 
		 * @param album 
		 * @param artist 
		 * @param track 
		 */
		public NotificationsItem(String type, int id, String title, Integer year, Integer episode, Integer season, String showtitle, String album, String artist, Integer track) {
			this.type = type;
			this.id = id;
			this.title = title;
			this.year = year;
			this.episode = episode;
			this.season = season;
			this.showtitle = showtitle;
			this.album = album;
			this.artist = artist;
			this.track = track;
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(TYPE, type);
			node.put(ID, id);
			node.put(TITLE, title);
			node.put(YEAR, year);
			node.put(EPISODE, episode);
			node.put(SEASON, season);
			node.put(SHOWTITLE, showtitle);
			node.put(ALBUM, album);
			node.put(ARTIST, artist);
			node.put(TRACK, track);
			return node;
		}
		/**
		 * An unknown item does not have any additional information.
		 * @param type See {@link PlayerModel.NotificationsItemType} for values.
		 * @see PlayerModel.NotificationsItemType
		 */
		public class TypeNotificationsItem extends NotificationsItem {
			public TypeNotificationsItem(String type) {
				super(type, -1, null, null, null, null, null, null, null, null);
			}
		}
		/**
		 * An item known to the database has an identification.
		 * @param id 
		 * @param type See {@link PlayerModel.NotificationsItemType} for values.
		 * @see PlayerModel.NotificationsItemType
		 */
		public class IdTypeNotificationsItem extends NotificationsItem {
			public IdTypeNotificationsItem(int id, String type) {
				super(type, id, null, null, null, null, null, null, null, null);
			}
		}
		/**
		 * A movie item has a title and may have a release year.
		 * @param title 
		 * @param type See {@link PlayerModel.NotificationsItemType} for values.
		 * @param year 
		 * @see PlayerModel.NotificationsItemType
		 */
		public class TitleTypeYearNotificationsItem extends NotificationsItem {
			public TitleTypeYearNotificationsItem(String title, String type, Integer year) {
				super(type, -1, title, year, null, null, null, null, null, null);
			}
		}
		/**
		 * A tv episode has a title and may have an episode number, season number and the title of the show it belongs to.
		 * @param episode 
		 * @param season 
		 * @param showtitle 
		 * @param title 
		 * @param type See {@link PlayerModel.NotificationsItemType} for values.
		 * @see PlayerModel.NotificationsItemType
		 */
		public class EpisodeSeasonShowtitleTitleTypeNotificationsItem extends NotificationsItem {
			public EpisodeSeasonShowtitleTitleTypeNotificationsItem(Integer episode, Integer season, String showtitle, String title, String type) {
				super(type, -1, title, null, episode, season, showtitle, null, null, null);
			}
		}
		/**
		 * A music video has a title and may have an album and an artist.
		 * @param album 
		 * @param artist 
		 * @param title 
		 * @param type See {@link PlayerModel.NotificationsItemType} for values.
		 * @see PlayerModel.NotificationsItemType
		 */
		public class AlbumArtistTitleTypeNotificationsItem extends NotificationsItem {
			public AlbumArtistTitleTypeNotificationsItem(String album, String artist, String title, String type) {
				super(type, -1, title, null, null, null, null, album, artist, null);
			}
		}
		/**
		 * A song has a title and may have an album, an artist and a track number.
		 * @param album 
		 * @param artist 
		 * @param title 
		 * @param track 
		 * @param type See {@link PlayerModel.NotificationsItemType} for values.
		 * @see PlayerModel.NotificationsItemType
		 */
		public class AlbumArtistTitleTrackTypeNotificationsItem extends NotificationsItem {
			public AlbumArtistTitleTrackTypeNotificationsItem(String album, String artist, String title, Integer track, String type) {
				super(type, -1, title, null, null, null, null, album, artist, track);
			}
		}
		/**
		 * Extracts a list of {@link PlayerModel.NotificationsItem} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<PlayerModel.NotificationsItem> getPlayerModelNotificationsItemList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<PlayerModel.NotificationsItem> l = new ArrayList<PlayerModel.NotificationsItem>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new PlayerModel.NotificationsItem((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<PlayerModel.NotificationsItem>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeValue(type);
			parcel.writeValue(id);
			parcel.writeValue(title);
			parcel.writeValue(year);
			parcel.writeValue(episode);
			parcel.writeValue(season);
			parcel.writeValue(showtitle);
			parcel.writeValue(album);
			parcel.writeValue(artist);
			parcel.writeValue(track);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected NotificationsItem(Parcel parcel) {
			type = parcel.readString();
			id = parcel.readInt();
			title = parcel.readString();
			year = parcel.readInt();
			episode = parcel.readInt();
			season = parcel.readInt();
			showtitle = parcel.readString();
			album = parcel.readString();
			artist = parcel.readString();
			track = parcel.readInt();
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<NotificationsItem> CREATOR = new Parcelable.Creator<NotificationsItem>() {
			@Override
			public NotificationsItem createFromParcel(Parcel parcel) {
				return new NotificationsItem(parcel);
			}
			@Override
			public NotificationsItem[] newArray(int n) {
				return new NotificationsItem[n];
			}
		};
	}
	public interface NotificationsItemType {
		public final String UNKNOWN = "unknown";
		public final String MOVIE = "movie";
		public final String EPISODE = "episode";
		public final String MUSICVIDEO = "musicvideo";
		public final String SONG = "song";
	}
	/**
	 * Player.Notifications.Player
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class NotificationsPlayer extends AbstractModel {
		public final static String API_TYPE = "Player.Notifications.Player";
		// field names
		public static final String PLAYERID = "playerid";
		public static final String SPEED = "speed";
		// class members
		public final int playerid;
		public final Integer speed;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a NotificationsPlayer object
		 */
		public NotificationsPlayer(ObjectNode node) {
			mType = API_TYPE;
			playerid = node.get(PLAYERID).getIntValue();
			speed = parseInt(node, SPEED);
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param playerid 
		 * @param speed 
		 */
		public NotificationsPlayer(int playerid, Integer speed) {
			this.playerid = playerid;
			this.speed = speed;
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(PLAYERID, playerid);
			node.put(SPEED, speed);
			return node;
		}
		/**
		 * Extracts a list of {@link PlayerModel.NotificationsPlayer} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<PlayerModel.NotificationsPlayer> getPlayerModelNotificationsPlayerList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<PlayerModel.NotificationsPlayer> l = new ArrayList<PlayerModel.NotificationsPlayer>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new PlayerModel.NotificationsPlayer((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<PlayerModel.NotificationsPlayer>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeValue(playerid);
			parcel.writeValue(speed);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected NotificationsPlayer(Parcel parcel) {
			playerid = parcel.readInt();
			speed = parcel.readInt();
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<NotificationsPlayer> CREATOR = new Parcelable.Creator<NotificationsPlayer>() {
			@Override
			public NotificationsPlayer createFromParcel(Parcel parcel) {
				return new NotificationsPlayer(parcel);
			}
			@Override
			public NotificationsPlayer[] newArray(int n) {
				return new NotificationsPlayer[n];
			}
		};
	}
	/**
	 * Player.Notifications.Player.Seek
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class NotificationsPlayerSeek extends PlayerModel.NotificationsPlayer {
		public final static String API_TYPE = "Player.Notifications.Player.Seek";
		// field names
		public static final String SEEKOFFSET = "seekoffset";
		public static final String TIME = "time";
		// class members
		public final GlobalModel.Time seekoffset;
		public final GlobalModel.Time time;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a NotificationsPlayerSeek object
		 */
		public NotificationsPlayerSeek(ObjectNode node) {
			super(node);
			mType = API_TYPE;
			seekoffset = node.has(SEEKOFFSET) ? new GlobalModel.Time((ObjectNode)node.get(SEEKOFFSET)) : null;
			time = node.has(TIME) ? new GlobalModel.Time((ObjectNode)node.get(TIME)) : null;
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(SEEKOFFSET, seekoffset.toObjectNode());
			node.put(TIME, time.toObjectNode());
			return node;
		}
		/**
		 * Extracts a list of {@link PlayerModel.NotificationsPlayerSeek} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<PlayerModel.NotificationsPlayerSeek> getPlayerModelNotificationsPlayerSeekList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<PlayerModel.NotificationsPlayerSeek> l = new ArrayList<PlayerModel.NotificationsPlayerSeek>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new PlayerModel.NotificationsPlayerSeek((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<PlayerModel.NotificationsPlayerSeek>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(seekoffset);
			parcel.writeValue(time);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected NotificationsPlayerSeek(Parcel parcel) {
			super(parcel);
			seekoffset = parcel.<GlobalModel.Time>readParcelable(GlobalModel.Time.class.getClassLoader());
			time = parcel.<GlobalModel.Time>readParcelable(GlobalModel.Time.class.getClassLoader());
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<NotificationsPlayerSeek> CREATOR = new Parcelable.Creator<NotificationsPlayerSeek>() {
			@Override
			public NotificationsPlayerSeek createFromParcel(Parcel parcel) {
				return new NotificationsPlayerSeek(parcel);
			}
			@Override
			public NotificationsPlayerSeek[] newArray(int n) {
				return new NotificationsPlayerSeek[n];
			}
		};
	}
	public interface PropertyName {
		public final String TYPE = "type";
		public final String PARTYMODE = "partymode";
		public final String SPEED = "speed";
		public final String TIME = "time";
		public final String PERCENTAGE = "percentage";
		public final String TOTALTIME = "totaltime";
		public final String PLAYLISTID = "playlistid";
		public final String POSITION = "position";
		public final String REPEAT = "repeat";
		public final String SHUFFLED = "shuffled";
		public final String CANSEEK = "canseek";
		public final String CANCHANGESPEED = "canchangespeed";
		public final String CANMOVE = "canmove";
		public final String CANZOOM = "canzoom";
		public final String CANROTATE = "canrotate";
		public final String CANSHUFFLE = "canshuffle";
		public final String CANREPEAT = "canrepeat";
		public final String CURRENTAUDIOSTREAM = "currentaudiostream";
		public final String AUDIOSTREAMS = "audiostreams";
		public final String SUBTITLEENABLED = "subtitleenabled";
		public final String CURRENTSUBTITLE = "currentsubtitle";
		public final String SUBTITLES = "subtitles";
	}
	/**
	 * Player.Property.Value
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class PropertyValue extends AbstractModel {
		public final static String API_TYPE = "Player.Property.Value";
		// field names
		public static final String AUDIOSTREAMS = "audiostreams";
		public static final String CANCHANGESPEED = "canchangespeed";
		public static final String CANMOVE = "canmove";
		public static final String CANREPEAT = "canrepeat";
		public static final String CANROTATE = "canrotate";
		public static final String CANSEEK = "canseek";
		public static final String CANSHUFFLE = "canshuffle";
		public static final String CANZOOM = "canzoom";
		public static final String CURRENTAUDIOSTREAM = "currentaudiostream";
		public static final String CURRENTSUBTITLE = "currentsubtitle";
		public static final String PARTYMODE = "partymode";
		public static final String PERCENTAGE = "percentage";
		public static final String PLAYLISTID = "playlistid";
		public static final String POSITION = "position";
		public static final String REPEAT = "repeat";
		public static final String SHUFFLED = "shuffled";
		public static final String SPEED = "speed";
		public static final String SUBTITLEENABLED = "subtitleenabled";
		public static final String SUBTITLES = "subtitles";
		public static final String TIME = "time";
		public static final String TOTALTIME = "totaltime";
		public static final String TYPE = "type";
		// class members
		public final ArrayList<PlayerModel.AudioStreamExtended> audiostreams;
		public final Boolean canchangespeed;
		public final Boolean canmove;
		public final Boolean canrepeat;
		public final Boolean canrotate;
		public final Boolean canseek;
		public final Boolean canshuffle;
		public final Boolean canzoom;
		public final PlayerModel.AudioStreamExtended currentaudiostream;
		public final PlayerModel.Subtitle currentsubtitle;
		public final Boolean partymode;
		public final Double percentage;
		public final Integer playlistid;
		public final Integer position;
		public final String repeat;
		public final Boolean shuffled;
		public final Integer speed;
		public final Boolean subtitleenabled;
		public final ArrayList<PlayerModel.Subtitle> subtitles;
		public final GlobalModel.Time time;
		public final GlobalModel.Time totaltime;
		public final String type;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a PropertyValue object
		 */
		public PropertyValue(ObjectNode node) {
			mType = API_TYPE;
			audiostreams = PlayerModel.AudioStreamExtended.getPlayerModelAudioStreamExtendedList(node, AUDIOSTREAMS);
			canchangespeed = parseBoolean(node, CANCHANGESPEED);
			canmove = parseBoolean(node, CANMOVE);
			canrepeat = parseBoolean(node, CANREPEAT);
			canrotate = parseBoolean(node, CANROTATE);
			canseek = parseBoolean(node, CANSEEK);
			canshuffle = parseBoolean(node, CANSHUFFLE);
			canzoom = parseBoolean(node, CANZOOM);
			currentaudiostream = node.has(CURRENTAUDIOSTREAM) ? new PlayerModel.AudioStreamExtended((ObjectNode)node.get(CURRENTAUDIOSTREAM)) : null;
			currentsubtitle = node.has(CURRENTSUBTITLE) ? new PlayerModel.Subtitle((ObjectNode)node.get(CURRENTSUBTITLE)) : null;
			partymode = parseBoolean(node, PARTYMODE);
			percentage = parseDouble(node, PERCENTAGE);
			playlistid = parseInt(node, PLAYLISTID);
			position = parseInt(node, POSITION);
			repeat = parseString(node, REPEAT);
			shuffled = parseBoolean(node, SHUFFLED);
			speed = parseInt(node, SPEED);
			subtitleenabled = parseBoolean(node, SUBTITLEENABLED);
			subtitles = PlayerModel.Subtitle.getPlayerModelSubtitleList(node, SUBTITLES);
			time = node.has(TIME) ? new GlobalModel.Time((ObjectNode)node.get(TIME)) : null;
			totaltime = node.has(TOTALTIME) ? new GlobalModel.Time((ObjectNode)node.get(TOTALTIME)) : null;
			type = parseString(node, TYPE);
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param audiostreams 
		 * @param canchangespeed 
		 * @param canmove 
		 * @param canrepeat 
		 * @param canrotate 
		 * @param canseek 
		 * @param canshuffle 
		 * @param canzoom 
		 * @param currentaudiostream 
		 * @param currentsubtitle 
		 * @param partymode 
		 * @param percentage 
		 * @param playlistid 
		 * @param position 
		 * @param repeat 
		 * @param shuffled 
		 * @param speed 
		 * @param subtitleenabled 
		 * @param subtitles 
		 * @param time 
		 * @param totaltime 
		 * @param type 
		 */
		public PropertyValue(ArrayList<PlayerModel.AudioStreamExtended> audiostreams, Boolean canchangespeed, Boolean canmove, Boolean canrepeat, Boolean canrotate, Boolean canseek, Boolean canshuffle, Boolean canzoom, PlayerModel.AudioStreamExtended currentaudiostream, PlayerModel.Subtitle currentsubtitle, Boolean partymode, Double percentage, Integer playlistid, Integer position, String repeat, Boolean shuffled, Integer speed, Boolean subtitleenabled, ArrayList<PlayerModel.Subtitle> subtitles, GlobalModel.Time time, GlobalModel.Time totaltime, String type) {
			this.audiostreams = audiostreams;
			this.canchangespeed = canchangespeed;
			this.canmove = canmove;
			this.canrepeat = canrepeat;
			this.canrotate = canrotate;
			this.canseek = canseek;
			this.canshuffle = canshuffle;
			this.canzoom = canzoom;
			this.currentaudiostream = currentaudiostream;
			this.currentsubtitle = currentsubtitle;
			this.partymode = partymode;
			this.percentage = percentage;
			this.playlistid = playlistid;
			this.position = position;
			this.repeat = repeat;
			this.shuffled = shuffled;
			this.speed = speed;
			this.subtitleenabled = subtitleenabled;
			this.subtitles = subtitles;
			this.time = time;
			this.totaltime = totaltime;
			this.type = type;
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			final ArrayNode audiostreamsArray = OM.createArrayNode();
			for (PlayerModel.AudioStreamExtended item : audiostreams) {
				audiostreamsArray.add(item.toObjectNode());
			}
			node.put(AUDIOSTREAMS, audiostreamsArray);
			node.put(CANCHANGESPEED, canchangespeed);
			node.put(CANMOVE, canmove);
			node.put(CANREPEAT, canrepeat);
			node.put(CANROTATE, canrotate);
			node.put(CANSEEK, canseek);
			node.put(CANSHUFFLE, canshuffle);
			node.put(CANZOOM, canzoom);
			node.put(CURRENTAUDIOSTREAM, currentaudiostream.toObjectNode());
			node.put(CURRENTSUBTITLE, currentsubtitle.toObjectNode());
			node.put(PARTYMODE, partymode);
			node.put(PERCENTAGE, percentage);
			node.put(PLAYLISTID, playlistid);
			node.put(POSITION, position);
			node.put(REPEAT, repeat);
			node.put(SHUFFLED, shuffled);
			node.put(SPEED, speed);
			node.put(SUBTITLEENABLED, subtitleenabled);
			final ArrayNode subtitlesArray = OM.createArrayNode();
			for (PlayerModel.Subtitle item : subtitles) {
				subtitlesArray.add(item.toObjectNode());
			}
			node.put(SUBTITLES, subtitlesArray);
			node.put(TIME, time.toObjectNode());
			node.put(TOTALTIME, totaltime.toObjectNode());
			node.put(TYPE, type);
			return node;
		}
		/**
		 * Extracts a list of {@link PlayerModel.PropertyValue} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<PlayerModel.PropertyValue> getPlayerModelPropertyValueList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<PlayerModel.PropertyValue> l = new ArrayList<PlayerModel.PropertyValue>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new PlayerModel.PropertyValue((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<PlayerModel.PropertyValue>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeInt(audiostreams.size());
			for (PlayerModel.AudioStreamExtended item : audiostreams) {
				parcel.writeParcelable(item, flags);
			}
			parcel.writeValue(canchangespeed);
			parcel.writeValue(canmove);
			parcel.writeValue(canrepeat);
			parcel.writeValue(canrotate);
			parcel.writeValue(canseek);
			parcel.writeValue(canshuffle);
			parcel.writeValue(canzoom);
			parcel.writeValue(currentaudiostream);
			parcel.writeValue(currentsubtitle);
			parcel.writeValue(partymode);
			parcel.writeValue(percentage);
			parcel.writeValue(playlistid);
			parcel.writeValue(position);
			parcel.writeValue(repeat);
			parcel.writeValue(shuffled);
			parcel.writeValue(speed);
			parcel.writeValue(subtitleenabled);
			parcel.writeInt(subtitles.size());
			for (PlayerModel.Subtitle item : subtitles) {
				parcel.writeParcelable(item, flags);
			}
			parcel.writeValue(time);
			parcel.writeValue(totaltime);
			parcel.writeValue(type);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected PropertyValue(Parcel parcel) {
			final int audiostreamsSize = parcel.readInt();
			audiostreams = new ArrayList<PlayerModel.AudioStreamExtended>(audiostreamsSize);
			for (int i = 0; i < audiostreamsSize; i++) {
				audiostreams.add(parcel.<PlayerModel.AudioStreamExtended>readParcelable(PlayerModel.AudioStreamExtended.class.getClassLoader()));
			}
			canchangespeed = parcel.readInt() == 1;
			canmove = parcel.readInt() == 1;
			canrepeat = parcel.readInt() == 1;
			canrotate = parcel.readInt() == 1;
			canseek = parcel.readInt() == 1;
			canshuffle = parcel.readInt() == 1;
			canzoom = parcel.readInt() == 1;
			currentaudiostream = parcel.<PlayerModel.AudioStreamExtended>readParcelable(PlayerModel.AudioStreamExtended.class.getClassLoader());
			currentsubtitle = parcel.<PlayerModel.Subtitle>readParcelable(PlayerModel.Subtitle.class.getClassLoader());
			partymode = parcel.readInt() == 1;
			percentage = parcel.readDouble();
			playlistid = parcel.readInt();
			position = parcel.readInt();
			repeat = parcel.readString();
			shuffled = parcel.readInt() == 1;
			speed = parcel.readInt();
			subtitleenabled = parcel.readInt() == 1;
			final int subtitlesSize = parcel.readInt();
			subtitles = new ArrayList<PlayerModel.Subtitle>(subtitlesSize);
			for (int i = 0; i < subtitlesSize; i++) {
				subtitles.add(parcel.<PlayerModel.Subtitle>readParcelable(PlayerModel.Subtitle.class.getClassLoader()));
			}
			time = parcel.<GlobalModel.Time>readParcelable(GlobalModel.Time.class.getClassLoader());
			totaltime = parcel.<GlobalModel.Time>readParcelable(GlobalModel.Time.class.getClassLoader());
			type = parcel.readString();
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<PropertyValue> CREATOR = new Parcelable.Creator<PropertyValue>() {
			@Override
			public PropertyValue createFromParcel(Parcel parcel) {
				return new PropertyValue(parcel);
			}
			@Override
			public PropertyValue[] newArray(int n) {
				return new PropertyValue[n];
			}
		};
	}
	public interface Repeat {
		public final String OFF = "off";
		public final String ONE = "one";
		public final String ALL = "all";
	}
	/**
	 * Player.Speed
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Speed extends AbstractModel {
		public final static String API_TYPE = "Player.Speed";
		// field names
		public static final String SPEED = "speed";
		// class members
		public final Integer speed;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a Speed object
		 */
		public Speed(ObjectNode node) {
			mType = API_TYPE;
			speed = parseInt(node, SPEED);
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param speed 
		 */
		public Speed(Integer speed) {
			this.speed = speed;
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(SPEED, speed);
			return node;
		}
		/**
		 * Extracts a list of {@link PlayerModel.Speed} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<PlayerModel.Speed> getPlayerModelSpeedList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<PlayerModel.Speed> l = new ArrayList<PlayerModel.Speed>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new PlayerModel.Speed((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<PlayerModel.Speed>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeValue(speed);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected Speed(Parcel parcel) {
			speed = parcel.readInt();
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<Speed> CREATOR = new Parcelable.Creator<Speed>() {
			@Override
			public Speed createFromParcel(Parcel parcel) {
				return new Speed(parcel);
			}
			@Override
			public Speed[] newArray(int n) {
				return new Speed[n];
			}
		};
	}
	/**
	 * Player.Subtitle
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Subtitle extends AbstractModel {
		public final static String API_TYPE = "Player.Subtitle";
		// field names
		public static final String INDEX = "index";
		public static final String LANGUAGE = "language";
		public static final String NAME = "name";
		// class members
		public final int index;
		public final String language;
		public final String name;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a Subtitle object
		 */
		public Subtitle(ObjectNode node) {
			mType = API_TYPE;
			index = node.get(INDEX).getIntValue();
			language = node.get(LANGUAGE).getTextValue();
			name = node.get(NAME).getTextValue();
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param index 
		 * @param language 
		 * @param name 
		 */
		public Subtitle(int index, String language, String name) {
			this.index = index;
			this.language = language;
			this.name = name;
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(INDEX, index);
			node.put(LANGUAGE, language);
			node.put(NAME, name);
			return node;
		}
		/**
		 * Extracts a list of {@link PlayerModel.Subtitle} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<PlayerModel.Subtitle> getPlayerModelSubtitleList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<PlayerModel.Subtitle> l = new ArrayList<PlayerModel.Subtitle>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new PlayerModel.Subtitle((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<PlayerModel.Subtitle>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeValue(index);
			parcel.writeValue(language);
			parcel.writeValue(name);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected Subtitle(Parcel parcel) {
			index = parcel.readInt();
			language = parcel.readString();
			name = parcel.readString();
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<Subtitle> CREATOR = new Parcelable.Creator<Subtitle>() {
			@Override
			public Subtitle createFromParcel(Parcel parcel) {
				return new Subtitle(parcel);
			}
			@Override
			public Subtitle[] newArray(int n) {
				return new Subtitle[n];
			}
		};
	}
	public interface Type {
		public final String VIDEO = "video";
		public final String AUDIO = "audio";
		public final String PICTURE = "picture";
	}
}