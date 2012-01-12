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
		public OnPlay(JSONObject event) throws JSONException {
			super(event);
			data = new Data(event.getJSONObject("data"));
		}
		@Override
		public String toString() {
			return 	"Item " + data.item.id + " (" + data.item.type + ") with player " + data.player.playerId + " at speed " + data.player.speed + ".";
		}
	}

	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 *  types: https://github.com/xbmc/xbmc/blob/master/xbmc/interfaces/json-rpc/types.json
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	public static class Data {
		public final static String TYPE = "Player.Notifications.Data";
		public final Item item;
		public final Player player;
		public Data (JSONObject event) throws JSONException {
			item = new Item(event.getJSONObject("item"));
			player = new Player(event.getJSONObject("player"));
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
		public Item (JSONObject event) throws JSONException {
			type = Type.parse(event.getString("type"));
			id = AbstractEvent.parseInt(event, "id");
			title = AbstractEvent.parseString(event, "title");
			year = AbstractEvent.parseInt(event, "year");
			episode = AbstractEvent.parseInt(event, "episode");
			season = AbstractEvent.parseInt(event, "season");
			showtitle = AbstractEvent.parseString(event, "showtitle");
			album = AbstractEvent.parseString(event, "album");
			artist = AbstractEvent.parseString(event, "artist");
			track = AbstractEvent.parseInt(event, "track");
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
		}
	}
	
	public static class Player {
		public final static String TYPE = "Player.Notifications.Player";
		public final int playerId;
		public final int speed;
		public Player (JSONObject event) throws JSONException {
			playerId = event.getInt("playerid");
			speed = event.has("speed") ? event.getInt("speed") : 0;
		}
	}
	
	public static class PlayerSeek extends Player {
		public final static String TYPE = "Player.Notifications.Player.Seek";
		// TODO
		public PlayerSeek(JSONObject event) throws JSONException {
			super(event);
		}
	}
	
	
	
}
