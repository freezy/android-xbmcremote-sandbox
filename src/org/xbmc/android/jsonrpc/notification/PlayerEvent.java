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

package org.xbmc.android.jsonrpc.notification;

import org.json.JSONException;
import org.json.JSONObject;
import org.xbmc.android.jsonrpc.api.model.GlobalModel;

/**
 * Parses Player.* events.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public class PlayerEvent {
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 *  notifications: https://github.com/xbmc/xbmc/blob/master/xbmc/interfaces/json-rpc/notifications.json
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * Playback of a media item has been started or the playback speed has
	 * changed. If there is no ID available extra information will be provided.
	 */
	public static class OnPlay extends AbstractEvent {
		public final static String METHOD = "Player.OnPlay";
		public final Data data;
		public OnPlay(JSONObject obj) throws JSONException {
			super(obj);
			data = new Data(obj.getJSONObject("data"));
		}
		@Override
		public String toString() {
			return 	"PLAY: Item " + data.item + " with player " + data.player.playerId + " at speed " + data.player.speed + ".";
		}
	}
	
	/**
	 * Playback of a media item has been paused. If there is no ID available
	 * extra information will be provided.
	 */
	public static class OnPause extends AbstractEvent {
		public final static String METHOD = "Player.OnPause";
		public final Data data;
		public OnPause(JSONObject obj) throws JSONException {
			super(obj);
			data = new Data(obj.getJSONObject("data"));
		}
		@Override
		public String toString() {
			return 	"PAUSE: Item " + data.item + " with player " + data.player.playerId + " at speed " + data.player.speed + ".";
		}
	}
	
	/**
	 * Playback of a media item has been stopped. If there is no ID available
	 * extra information will be provided.
	 */
	public static class OnStop extends AbstractEvent {
		public final static String METHOD = "Player.OnStop";
		public final Data data;
		public OnStop(JSONObject obj) throws JSONException {
			super(obj);
			data = new Data(obj.getJSONObject("data"));
		}
		@Override
		public String toString() {
			return 	"STOP: Item " + data.item + ".";
		}
		public static class Data {
			public final Item item;
			public Data (JSONObject obj) throws JSONException {
				item = new Item(obj.getJSONObject("item"));
			}
		}
	}
	
	/**
	 * Speed of the playback of a media item has been changed. If there is no ID
	 * available extra information will be provided.
	 */
	public static class OnSpeedChanged extends AbstractEvent {
		public final static String METHOD = "Player.OnSpeedChanged";
		public final Data data;
		public OnSpeedChanged(JSONObject obj) throws JSONException {
			super(obj);
			data = new Data(obj.getJSONObject("data"));
		}
		@Override
		public String toString() {
			return 	"SPEED-CHANGE: Item " + data.item + " with player " + data.player.playerId + " at speed " + data.player.speed + ".";
		}
	}

	/**
	 * The playback position has been changed. If there is no ID available extra
	 * information will be provided.
	 */
	public static class OnSeek extends AbstractEvent {
	public final static String METHOD = "Player.OnSeek";
		public final Data data;
		public OnSeek(JSONObject obj) throws JSONException {
			super(obj);
			data = new Data(obj.getJSONObject("data"));
		}
		public static class Data {
			public final Item item;
			public final PlayerSeek player;
			public Data (JSONObject obj) throws JSONException {
				item = new Item(obj.getJSONObject("item"));
				player = new PlayerSeek(obj.getJSONObject("player"));
			}
		}
		@Override
		public String toString() {
			return 	"SEEK: Item " + data.item + " with player " + data.player.playerId + " to " + data.player.time + " at " + data.player.seekoffset + ".";
		}
	}
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 *  types: https://github.com/xbmc/xbmc/blob/master/xbmc/interfaces/json-rpc/types.json
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	public static class Data {
		public final static String TYPE = "Player.Notifications.Data";
		public final Item item;
		public final Player player;
		public Data (JSONObject obj) throws JSONException {
			item = new Item(obj.getJSONObject("item"));
			player = new Player(obj.getJSONObject("player"));
		}
	}
	
	public static class Item {
		public final static String TYPE = "Player.Notifications.Item";
		public final int type;
		public final int id;
		public final String title;
		public final int year;
		public final int episode;
		public final int season;
		public final String showtitle;
		public final String album;
		public final String artist;
		public final int track;
		public Item (JSONObject obj) throws JSONException {
			type = Type.parse(obj.getString("type"));
			id = AbstractEvent.parseInt(obj, "id");
			title = AbstractEvent.parseString(obj, "title");
			year = AbstractEvent.parseInt(obj, "year");
			episode = AbstractEvent.parseInt(obj, "episode");
			season = AbstractEvent.parseInt(obj, "season");
			showtitle = AbstractEvent.parseString(obj, "showtitle");
			album = AbstractEvent.parseString(obj, "album");
			artist = AbstractEvent.parseString(obj, "artist");
			track = AbstractEvent.parseInt(obj, "track");
		}
		@Override
		public String toString() {
			return Type.stringValue(type) + "(" + id + ")";
			
		}
		
		public static class Type {
			public static final int UNKNOWN = 0x00;
			public static final int MOVIE = 0x01;
			public static final int EPISODE = 0x02;
			public static final int MUSICVIDEO = 0x03;
			public static final int SONG = 0x04;
			public static int parse(String type) {
				if (type.equals("unknown")) {
					return UNKNOWN;
				} else if (type.equals("movie")) {
					return MOVIE;
				} else if (type.equals("episode")) {
					return EPISODE;
				} else if (type.equals("musicvideo")) {
					return MUSICVIDEO;
				} else if (type.equals("song")) {
					return SONG;
				} else {
					return UNKNOWN;
				}
			}
			public static String stringValue(int type) {
				switch (type) {
				case MOVIE: return "Movie";
				case EPISODE: return "Episode";
				case MUSICVIDEO: return "Musicvideo";
				case SONG: return "Song";
				case UNKNOWN: 
				default: return "Unknown";
				}
			}
		}
	}
	
	public static class Player {
		public final static String TYPE = "Player.Notifications.Player";
		public final int playerId;
		public final int speed;
		public Player (JSONObject obj) throws JSONException {
			playerId = obj.getInt("playerid");
			speed = obj.has("speed") ? obj.getInt("speed") : 0;
		}
	}
	
	public static class PlayerSeek extends Player {
		public final static String TYPE = "Player.Notifications.Player.Seek";
		public final GlobalModel.Time time;
		public final GlobalModel.Time seekoffset;
		public PlayerSeek(JSONObject obj) throws JSONException {
			super(obj);
			time = new GlobalModel.Time(obj.getJSONObject("time"));
			seekoffset = new GlobalModel.Time(obj.getJSONObject("seekoffset"));
		}
	}
	
}
