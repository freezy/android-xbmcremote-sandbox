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
		public AudioStream(JSONObject obj) throws JSONException {
			mType = API_TYPE;
			index = obj.getInt(INDEX);
			language = obj.getString(LANGUAGE);
			name = obj.getString(NAME);
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
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(INDEX, index);
			obj.put(LANGUAGE, language);
			obj.put(NAME, name);
			return obj;
		}
		/**
		 * Extracts a list of {@link PlayerModel.AudioStream} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<PlayerModel.AudioStream> getPlayerModelAudioStreamList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<PlayerModel.AudioStream> l = new ArrayList<PlayerModel.AudioStream>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new PlayerModel.AudioStream(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<PlayerModel.AudioStream>(0);
		}
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
		public AudioStreamExtended(JSONObject obj) throws JSONException {
			super(obj);
			mType = API_TYPE;
			bitrate = obj.getInt(BITRATE);
			channels = obj.getInt(CHANNELS);
			codec = obj.getString(CODEC);
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(BITRATE, bitrate);
			obj.put(CHANNELS, channels);
			obj.put(CODEC, codec);
			return obj;
		}
		/**
		 * Extracts a list of {@link PlayerModel.AudioStreamExtended} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<PlayerModel.AudioStreamExtended> getPlayerModelAudioStreamExtendedList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<PlayerModel.AudioStreamExtended> l = new ArrayList<PlayerModel.AudioStreamExtended>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new PlayerModel.AudioStreamExtended(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<PlayerModel.AudioStreamExtended>(0);
		}
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
		public NotificationsData(JSONObject obj) throws JSONException {
			mType = API_TYPE;
			item = new NotificationsItem(obj.getJSONObject(ITEM));
			player = new PlayerModel.NotificationsPlayer(obj.getJSONObject(PLAYER));
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
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(ITEM, item.toJSONObject());
			obj.put(PLAYER, player.toJSONObject());
			return obj;
		}
		/**
		 * Extracts a list of {@link PlayerModel.NotificationsData} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<PlayerModel.NotificationsData> getPlayerModelNotificationsDataList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<PlayerModel.NotificationsData> l = new ArrayList<PlayerModel.NotificationsData>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new PlayerModel.NotificationsData(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<PlayerModel.NotificationsData>(0);
		}
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
		public NotificationsItem(JSONObject obj) throws JSONException {
			mType = API_TYPE;
			type = obj.getString(TYPE);
			id = obj.getInt(ID);
			title = obj.getString(TITLE);
			year = parseInt(obj, YEAR);
			episode = parseInt(obj, EPISODE);
			season = parseInt(obj, SEASON);
			showtitle = parseString(obj, SHOWTITLE);
			album = parseString(obj, ALBUM);
			artist = parseString(obj, ARTIST);
			track = parseInt(obj, TRACK);
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
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(TYPE, type);
			obj.put(ID, id);
			obj.put(TITLE, title);
			obj.put(YEAR, year);
			obj.put(EPISODE, episode);
			obj.put(SEASON, season);
			obj.put(SHOWTITLE, showtitle);
			obj.put(ALBUM, album);
			obj.put(ARTIST, artist);
			obj.put(TRACK, track);
			return obj;
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
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<PlayerModel.NotificationsItem> getPlayerModelNotificationsItemList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<PlayerModel.NotificationsItem> l = new ArrayList<PlayerModel.NotificationsItem>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new PlayerModel.NotificationsItem(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<PlayerModel.NotificationsItem>(0);
		}
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
		public NotificationsPlayer(JSONObject obj) throws JSONException {
			mType = API_TYPE;
			playerid = obj.getInt(PLAYERID);
			speed = parseInt(obj, SPEED);
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
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(PLAYERID, playerid);
			obj.put(SPEED, speed);
			return obj;
		}
		/**
		 * Extracts a list of {@link PlayerModel.NotificationsPlayer} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<PlayerModel.NotificationsPlayer> getPlayerModelNotificationsPlayerList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<PlayerModel.NotificationsPlayer> l = new ArrayList<PlayerModel.NotificationsPlayer>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new PlayerModel.NotificationsPlayer(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<PlayerModel.NotificationsPlayer>(0);
		}
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
		public NotificationsPlayerSeek(JSONObject obj) throws JSONException {
			super(obj);
			mType = API_TYPE;
			seekoffset = obj.has(SEEKOFFSET) ? new GlobalModel.Time(obj.getJSONObject(SEEKOFFSET)) : null;
			time = obj.has(TIME) ? new GlobalModel.Time(obj.getJSONObject(TIME)) : null;
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(SEEKOFFSET, seekoffset.toJSONObject());
			obj.put(TIME, time.toJSONObject());
			return obj;
		}
		/**
		 * Extracts a list of {@link PlayerModel.NotificationsPlayerSeek} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<PlayerModel.NotificationsPlayerSeek> getPlayerModelNotificationsPlayerSeekList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<PlayerModel.NotificationsPlayerSeek> l = new ArrayList<PlayerModel.NotificationsPlayerSeek>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new PlayerModel.NotificationsPlayerSeek(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<PlayerModel.NotificationsPlayerSeek>(0);
		}
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
		public PropertyValue(JSONObject obj) throws JSONException {
			mType = API_TYPE;
			audiostreams = PlayerModel.AudioStreamExtended.getPlayerModelAudioStreamExtendedList(obj, AUDIOSTREAMS);
			canchangespeed = parseBoolean(obj, CANCHANGESPEED);
			canmove = parseBoolean(obj, CANMOVE);
			canrepeat = parseBoolean(obj, CANREPEAT);
			canrotate = parseBoolean(obj, CANROTATE);
			canseek = parseBoolean(obj, CANSEEK);
			canshuffle = parseBoolean(obj, CANSHUFFLE);
			canzoom = parseBoolean(obj, CANZOOM);
			currentaudiostream = obj.has(CURRENTAUDIOSTREAM) ? new PlayerModel.AudioStreamExtended(obj.getJSONObject(CURRENTAUDIOSTREAM)) : null;
			currentsubtitle = obj.has(CURRENTSUBTITLE) ? new PlayerModel.Subtitle(obj.getJSONObject(CURRENTSUBTITLE)) : null;
			partymode = parseBoolean(obj, PARTYMODE);
			percentage = parseDouble(obj, PERCENTAGE);
			playlistid = parseInt(obj, PLAYLISTID);
			position = parseInt(obj, POSITION);
			repeat = parseString(obj, REPEAT);
			shuffled = parseBoolean(obj, SHUFFLED);
			speed = parseInt(obj, SPEED);
			subtitleenabled = parseBoolean(obj, SUBTITLEENABLED);
			subtitles = PlayerModel.Subtitle.getPlayerModelSubtitleList(obj, SUBTITLES);
			time = obj.has(TIME) ? new GlobalModel.Time(obj.getJSONObject(TIME)) : null;
			totaltime = obj.has(TOTALTIME) ? new GlobalModel.Time(obj.getJSONObject(TOTALTIME)) : null;
			type = parseString(obj, TYPE);
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
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			final JSONArray audiostreamsArray = new JSONArray();
			for (PlayerModel.AudioStreamExtended item : audiostreams) {
				audiostreamsArray.put(item.toJSONObject());
			}
			audiostreamsArray.put(audiostreamsArray);
			obj.put(AUDIOSTREAMS, audiostreamsArray);
			obj.put(CANCHANGESPEED, canchangespeed);
			obj.put(CANMOVE, canmove);
			obj.put(CANREPEAT, canrepeat);
			obj.put(CANROTATE, canrotate);
			obj.put(CANSEEK, canseek);
			obj.put(CANSHUFFLE, canshuffle);
			obj.put(CANZOOM, canzoom);
			obj.put(CURRENTAUDIOSTREAM, currentaudiostream.toJSONObject());
			obj.put(CURRENTSUBTITLE, currentsubtitle.toJSONObject());
			obj.put(PARTYMODE, partymode);
			obj.put(PERCENTAGE, percentage);
			obj.put(PLAYLISTID, playlistid);
			obj.put(POSITION, position);
			obj.put(REPEAT, repeat);
			obj.put(SHUFFLED, shuffled);
			obj.put(SPEED, speed);
			obj.put(SUBTITLEENABLED, subtitleenabled);
			final JSONArray subtitlesArray = new JSONArray();
			for (PlayerModel.Subtitle item : subtitles) {
				subtitlesArray.put(item.toJSONObject());
			}
			subtitlesArray.put(subtitlesArray);
			obj.put(SUBTITLES, subtitlesArray);
			obj.put(TIME, time.toJSONObject());
			obj.put(TOTALTIME, totaltime.toJSONObject());
			obj.put(TYPE, type);
			return obj;
		}
		/**
		 * Extracts a list of {@link PlayerModel.PropertyValue} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<PlayerModel.PropertyValue> getPlayerModelPropertyValueList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<PlayerModel.PropertyValue> l = new ArrayList<PlayerModel.PropertyValue>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new PlayerModel.PropertyValue(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<PlayerModel.PropertyValue>(0);
		}
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
		public Speed(JSONObject obj) throws JSONException {
			mType = API_TYPE;
			speed = parseInt(obj, SPEED);
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param speed 
		 */
		public Speed(Integer speed) {
			this.speed = speed;
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(SPEED, speed);
			return obj;
		}
		/**
		 * Extracts a list of {@link PlayerModel.Speed} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<PlayerModel.Speed> getPlayerModelSpeedList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<PlayerModel.Speed> l = new ArrayList<PlayerModel.Speed>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new PlayerModel.Speed(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<PlayerModel.Speed>(0);
		}
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
		public Subtitle(JSONObject obj) throws JSONException {
			mType = API_TYPE;
			index = obj.getInt(INDEX);
			language = obj.getString(LANGUAGE);
			name = obj.getString(NAME);
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
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(INDEX, index);
			obj.put(LANGUAGE, language);
			obj.put(NAME, name);
			return obj;
		}
		/**
		 * Extracts a list of {@link PlayerModel.Subtitle} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<PlayerModel.Subtitle> getPlayerModelSubtitleList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<PlayerModel.Subtitle> l = new ArrayList<PlayerModel.Subtitle>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new PlayerModel.Subtitle(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<PlayerModel.Subtitle>(0);
		}
	}
	public interface Type {
		public final String VIDEO = "video";
		public final String AUDIO = "audio";
		public final String PICTURE = "picture";
	}
}