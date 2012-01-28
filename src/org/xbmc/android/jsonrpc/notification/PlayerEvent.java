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

import org.codehaus.jackson.node.ObjectNode;
import org.xbmc.android.jsonrpc.api.model.GlobalModel;

import android.os.Parcel;
import android.os.Parcelable;

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
	public static class Play extends AbstractEvent {
		public final static int ID = 0x01;
		public final static String METHOD = "Player.OnPlay";
		public final Data data;
		public Play(ObjectNode node) {
			super(node);
			data = new Data((ObjectNode)node.get("data"));
		}
		protected Play(Parcel parcel) {
			super(parcel);
			data = parcel.<Data>readParcelable(Data.class.getClassLoader());
		}
		@Override
		public int describeContents() {
			return 0;
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeParcelable(data, flags);
		}
		@Override
		public String toString() {
			return 	"PLAY: Item " + data.item + " with player " + data.player.playerId + " at speed " + data.player.speed + ".";
		}
		public static final Parcelable.Creator<Play> CREATOR = new Parcelable.Creator<Play>() {
			@Override
			public Play createFromParcel(Parcel parcel) {
				return new Play(parcel);
			}
			@Override
			public Play[] newArray(int n) {
				return new Play[n];
			}
		};
		@Override
		public int getId() {
			return ID;
		}
	}
	
	/**
	 * Playback of a media item has been paused. If there is no ID available
	 * extra information will be provided.
	 */
	public static class Pause extends AbstractEvent {
		public final static int ID = 0x02;
		public final static String METHOD = "Player.OnPause";
		public final Data data;
		public Pause(ObjectNode node) {
			super(node);
			data = new Data((ObjectNode)node.get("data"));
		}
		protected Pause(Parcel parcel) {
			super(parcel);
			data = parcel.<Data>readParcelable(Data.class.getClassLoader());
		}
		@Override
		public String toString() {
			return 	"PAUSE: Item " + data.item + " with player " + data.player.playerId + " at speed " + data.player.speed + ".";
		}
		@Override
		public int describeContents() {
			return 0;
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeParcelable(data, flags);
		}
		@Override
		public int getId() {
			return ID;
		}
		public static final Parcelable.Creator<Pause> CREATOR = new Parcelable.Creator<Pause>() {
			@Override
			public Pause createFromParcel(Parcel parcel) {
				return new Pause(parcel);
			}
			@Override
			public Pause[] newArray(int n) {
				return new Pause[n];
			}
		};
	}
	
	/**
	 * Playback of a media item has been stopped. If there is no ID available
	 * extra information will be provided.
	 */
	public static class Stop extends AbstractEvent {
		public final static int ID = 0x03;
		public final static String METHOD = "Player.OnStop";
		public final Data data;
		public Stop(ObjectNode node) {
			super(node);
			data = new Data((ObjectNode)node.get("data"));
		}
		protected Stop(Parcel parcel) {
			super(parcel);
			data = parcel.<Data>readParcelable(Data.class.getClassLoader());
		}
		@Override
		public String toString() {
			return 	"STOP: Item " + data.item + ".";
		}
		@Override
		public int describeContents() {
			return 0;
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeParcelable(data, flags);
		}
		public static final Parcelable.Creator<Stop> CREATOR = new Parcelable.Creator<Stop>() {
			@Override
			public Stop createFromParcel(Parcel parcel) {
				return new Stop(parcel);
			}
			@Override
			public Stop[] newArray(int n) {
				return new Stop[n];
			}
		};
		@Override
		public int getId() {
			return ID;
		}
		public static class Data implements Parcelable {
			public final Item item;
			public Data(ObjectNode node) {
				item = new Item((ObjectNode)node.get("item"));
			}
			protected Data(Parcel parcel) {
				item = parcel.<Item>readParcelable(Item.class.getClassLoader());
			}
			@Override
			public int describeContents() {
				return 0;
			}
			@Override
			public void writeToParcel(Parcel parcel, int flags) {
				parcel.writeParcelable(item, flags);
			}
			public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
				@Override
				public Data createFromParcel(Parcel parcel) {
					return new Data(parcel);
				}
				@Override
				public Data[] newArray(int n) {
					return new Data[n];
				}
			};
		}
	}
	
	/**
	 * Speed of the playback of a media item has been changed. If there is no ID
	 * available extra information will be provided.
	 */
	public static class SpeedChanged extends AbstractEvent {
		public final static int ID = 0x04;
		public final static String METHOD = "Player.OnSpeedChanged";
		public final Data data;
		public SpeedChanged(ObjectNode node) {
			super(node);
			data = new Data((ObjectNode)node.get("data"));
		}
		public SpeedChanged(Parcel parcel) {
			super(parcel);
			data = parcel.<Data>readParcelable(Data.class.getClassLoader());
		}
		@Override
		public String toString() {
			return 	"SPEED-CHANGE: Item " + data.item + " with player " + data.player.playerId + " at speed " + data.player.speed + ".";
		}
		@Override
		public int describeContents() {
			return 0;
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeParcelable(data, flags);
		}
		public static final Parcelable.Creator<SpeedChanged> CREATOR = new Parcelable.Creator<SpeedChanged>() {
			@Override
			public SpeedChanged createFromParcel(Parcel parcel) {
				return new SpeedChanged(parcel);
			}
			@Override
			public SpeedChanged[] newArray(int n) {
				return new SpeedChanged[n];
			}
		};
		@Override
		public int getId() {
			return ID;
		}
	}

	/**
	 * The playback position has been changed. If there is no ID available extra
	 * information will be provided.
	 */
	public static class Seek extends AbstractEvent {
		public final static int ID = 0x05;
		public final static String METHOD = "Player.OnSeek";
		public final Data data;
		public Seek(ObjectNode node) {
			super(node);
			data = new Data((ObjectNode)node.get("data"));
		}
		public Seek(Parcel parcel) {
			super(parcel);
			data = parcel.<Data>readParcelable(Data.class.getClassLoader());
		}
		
		@Override
		public String toString() {
			return 	"SEEK: Item " + data.item + " with player " + data.player.playerId + " to " + data.player.time + " at " + data.player.seekoffset + ".";
		}
		@Override
		public int describeContents() {
			return 0;
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeParcelable(data, flags);
		}
		public static final Parcelable.Creator<Seek> CREATOR = new Parcelable.Creator<Seek>() {
			@Override
			public Seek createFromParcel(Parcel parcel) {
				return new Seek(parcel);
			}
			@Override
			public Seek[] newArray(int n) {
				return new Seek[n];
			}
		};
		@Override
		public int getId() {
			return ID;
		}
		public static class Data implements Parcelable {
			public final Item item;
			public final PlayerSeek player;
			public Data (ObjectNode node) {
				item = new Item((ObjectNode)node.get("item"));
				player = new PlayerSeek((ObjectNode)node.get("player"));
			}
			public Data (Parcel parcel) {
				item = parcel.<Item>readParcelable(Item.class.getClassLoader());
				player = parcel.<PlayerSeek>readParcelable(PlayerSeek.class.getClassLoader());
			}
			@Override
			public int describeContents() {
				return 0;
			}
			@Override
			public void writeToParcel(Parcel parcel, int flags) {
				parcel.writeParcelable(item, flags);
				parcel.writeParcelable(player, flags);
			}
			public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
				@Override
				public Data createFromParcel(Parcel parcel) {
					return new Data(parcel);
				}
				@Override
				public Data[] newArray(int n) {
					return new Data[n];
				}
			};
		}
	}
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 *  types: https://github.com/xbmc/xbmc/blob/master/xbmc/interfaces/json-rpc/types.json
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	public static class Data implements Parcelable {
		public final static String TYPE = "Player.Notifications.Data";
		public final Item item;
		public final Player player;
		public Data (ObjectNode node) {
			item = new Item((ObjectNode)node.get("item"));
			player = new Player((ObjectNode)node.get("player"));
		}
		public Data (Parcel parcel) {
			item = parcel.<Item>readParcelable(Item.class.getClassLoader());
			player = parcel.<Player>readParcelable(Player.class.getClassLoader());
		}
		@Override
		public int describeContents() {
			return 0;
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeParcelable(item, flags);
			parcel.writeParcelable(player, flags);
		}
		public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
			@Override
			public Data createFromParcel(Parcel parcel) {
				return new Data(parcel);
			}
			@Override
			public Data[] newArray(int n) {
				return new Data[n];
			}
		};
	}
	
	public static class Item implements Parcelable {
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
		public Item (ObjectNode node) {
			type = Type.parse(node.get("type").getTextValue());
			id = AbstractEvent.parseInt(node, "id");
			title = AbstractEvent.parseString(node, "title");
			year = AbstractEvent.parseInt(node, "year");
			episode = AbstractEvent.parseInt(node, "episode");
			season = AbstractEvent.parseInt(node, "season");
			showtitle = AbstractEvent.parseString(node, "showtitle");
			album = AbstractEvent.parseString(node, "album");
			artist = AbstractEvent.parseString(node, "artist");
			track = AbstractEvent.parseInt(node, "track");
		}
		public Item (Parcel parcel) {
			type = parcel.readInt();
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
		@Override
		public String toString() {
			return Type.stringValue(type) + "(" + id + ")";
			
		}
		@Override
		public int describeContents() {
			return 0;
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeInt(type);
			parcel.writeInt(id);
			parcel.writeString(title);
			parcel.writeInt(year);
			parcel.writeInt(episode);
			parcel.writeInt(season);
			parcel.writeString(showtitle);
			parcel.writeString(album);
			parcel.writeString(artist);
			parcel.writeInt(track);
		}
		public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
			@Override
			public Item createFromParcel(Parcel parcel) {
				return new Item(parcel);
			}
			@Override
			public Item[] newArray(int n) {
				return new Item[n];
			}
		};
		
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
	
	public static class Player implements Parcelable {
		public final static String TYPE = "Player.Notifications.Player";
		public final int playerId;
		public final int speed;
		public Player (ObjectNode node) {
			playerId = node.get("playerid").getIntValue();
			speed = node.get("speed").getValueAsInt(0);
		}
		protected Player(Parcel parcel) {
			playerId = parcel.readInt();
			speed = parcel.readInt();
		}
		@Override
		public int describeContents() {
			return 0;
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeInt(playerId);
			parcel.writeInt(speed);
		}
		public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
			@Override
			public Player createFromParcel(Parcel parcel) {
				return new Player(parcel);
			}
			@Override
			public Player[] newArray(int n) {
				return new Player[n];
			}
		};
	}
	
	public static class PlayerSeek extends Player {
		public final static String TYPE = "Player.Notifications.Player.Seek";
		public final GlobalModel.Time time;
		public final GlobalModel.Time seekoffset;
		public PlayerSeek(ObjectNode node) {
			super(node);
			time = new GlobalModel.Time((ObjectNode)node.get("time"));
			seekoffset = new GlobalModel.Time((ObjectNode)node.get("seekoffset"));
		}
		protected PlayerSeek(Parcel parcel) {
			super(parcel);
			time = parcel.<GlobalModel.Time>readParcelable(GlobalModel.Time.class.getClassLoader());
			seekoffset = parcel.<GlobalModel.Time>readParcelable(GlobalModel.Time.class.getClassLoader());
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeParcelable(time, flags);
			parcel.writeParcelable(seekoffset, flags);
		}
		public static final Parcelable.Creator<PlayerSeek> CREATOR = new Parcelable.Creator<PlayerSeek>() {
			@Override
			public PlayerSeek createFromParcel(Parcel parcel) {
				return new PlayerSeek(parcel);
			}
			@Override
			public PlayerSeek[] newArray(int n) {
				return new PlayerSeek[n];
			}
		};
	}
	
}
