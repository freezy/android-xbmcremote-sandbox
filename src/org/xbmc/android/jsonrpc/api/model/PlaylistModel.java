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

public final class PlaylistModel {
	/**
	 * Playlist.Item
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Item extends AbstractModel {
		public final static String API_TYPE = "Playlist.Item";
		// field names
		public static final String FILE = "file";
		public static final String DIRECTORY = "directory";
		public static final String MOVIEID = "movieid";
		public static final String EPISODEID = "episodeid";
		public static final String MUSICVIDEOID = "musicvideoid";
		public static final String ARTISTID = "artistid";
		public static final String ALBUMID = "albumid";
		public static final String SONGID = "songid";
		public static final String GENREID = "genreid";
		// class members
		/**
		 * Path to a file (not a directory) to be added to the playlist.
		 */
		public final String file;
		public final String directory;
		public final int movieid;
		public final int episodeid;
		public final int musicvideoid;
		public final int artistid;
		public final int albumid;
		public final int songid;
		/**
		 * Identification of a genre from the AudioLibrary.
		 */
		public final int genreid;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a Item object
		 */
		public Item(ObjectNode node) {
			mType = API_TYPE;
			file = node.get(FILE).getTextValue();
			directory = node.get(DIRECTORY).getTextValue();
			movieid = node.get(MOVIEID).getIntValue();
			episodeid = node.get(EPISODEID).getIntValue();
			musicvideoid = node.get(MUSICVIDEOID).getIntValue();
			artistid = node.get(ARTISTID).getIntValue();
			albumid = node.get(ALBUMID).getIntValue();
			songid = node.get(SONGID).getIntValue();
			genreid = node.get(GENREID).getIntValue();
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param file Path to a file (not a directory) to be added to the playlist 
		 * @param directory 
		 * @param movieid 
		 * @param episodeid 
		 * @param musicvideoid 
		 * @param artistid 
		 * @param albumid 
		 * @param songid 
		 * @param genreid Identification of a genre from the AudioLibrary 
		 */
		public Item(String file, String directory, int movieid, int episodeid, int musicvideoid, int artistid, int albumid, int songid, int genreid) {
			this.file = file;
			this.directory = directory;
			this.movieid = movieid;
			this.episodeid = episodeid;
			this.musicvideoid = musicvideoid;
			this.artistid = artistid;
			this.albumid = albumid;
			this.songid = songid;
			this.genreid = genreid;
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(FILE, file);
			node.put(DIRECTORY, directory);
			node.put(MOVIEID, movieid);
			node.put(EPISODEID, episodeid);
			node.put(MUSICVIDEOID, musicvideoid);
			node.put(ARTISTID, artistid);
			node.put(ALBUMID, albumid);
			node.put(SONGID, songid);
			node.put(GENREID, genreid);
			return node;
		}
		/**
		 * Default constructor.
		 * @param file Path to a file (not a directory) to be added to the playlist 
		 */
		public class FileItem extends Item {
			public FileItem(String file) {
				super(file, null, -1, -1, -1, -1, -1, -1, -1);
			}
		}
		/**
		 * Default constructor.
		 * @param directory 
		 */
		public class DirectoryItem extends Item {
			public DirectoryItem(String directory) {
				super(null, directory, -1, -1, -1, -1, -1, -1, -1);
			}
		}
		/**
		 * Default constructor.
		 * @param movieid 
		 */
		public class MovieidItem extends Item {
			public MovieidItem(int movieid) {
				super(null, null, movieid, -1, -1, -1, -1, -1, -1);
			}
		}
		/**
		 * Default constructor.
		 * @param episodeid 
		 */
		public class EpisodeidItem extends Item {
			public EpisodeidItem(int episodeid) {
				super(null, null, -1, episodeid, -1, -1, -1, -1, -1);
			}
		}
		/**
		 * Default constructor.
		 * @param musicvideoid 
		 */
		public class MusicvideoidItem extends Item {
			public MusicvideoidItem(int musicvideoid) {
				super(null, null, -1, -1, musicvideoid, -1, -1, -1, -1);
			}
		}
		/**
		 * Default constructor.
		 * @param artistid 
		 */
		public class ArtistidItem extends Item {
			public ArtistidItem(int artistid) {
				super(null, null, -1, -1, -1, artistid, -1, -1, -1);
			}
		}
		/**
		 * Default constructor.
		 * @param albumid 
		 */
		public class AlbumidItem extends Item {
			public AlbumidItem(int albumid) {
				super(null, null, -1, -1, -1, -1, albumid, -1, -1);
			}
		}
		/**
		 * Default constructor.
		 * @param songid 
		 */
		public class SongidItem extends Item {
			public SongidItem(int songid) {
				super(null, null, -1, -1, -1, -1, -1, songid, -1);
			}
		}
		/**
		 * Default constructor.
		 * @param genreid Identification of a genre from the AudioLibrary 
		 */
		public class GenreidItem extends Item {
			public GenreidItem(int genreid) {
				super(null, null, -1, -1, -1, -1, -1, -1, genreid);
			}
		}
		/**
		 * Extracts a list of {@link PlaylistModel.Item} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<PlaylistModel.Item> getPlaylistModelItemList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<PlaylistModel.Item> l = new ArrayList<PlaylistModel.Item>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new PlaylistModel.Item((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<PlaylistModel.Item>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeValue(file);
			parcel.writeValue(directory);
			parcel.writeValue(movieid);
			parcel.writeValue(episodeid);
			parcel.writeValue(musicvideoid);
			parcel.writeValue(artistid);
			parcel.writeValue(albumid);
			parcel.writeValue(songid);
			parcel.writeValue(genreid);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected Item(Parcel parcel) {
			file = parcel.readString();
			directory = parcel.readString();
			movieid = parcel.readInt();
			episodeid = parcel.readInt();
			musicvideoid = parcel.readInt();
			artistid = parcel.readInt();
			albumid = parcel.readInt();
			songid = parcel.readInt();
			genreid = parcel.readInt();
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
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
	}
	public interface PropertyName {
		public final String TYPE = "type";
		public final String SIZE = "size";
	}
	/**
	 * Playlist.Property.Value
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class PropertyValue extends AbstractModel {
		public final static String API_TYPE = "Playlist.Property.Value";
		// field names
		public static final String SIZE = "size";
		public static final String TYPE = "type";
		// class members
		public final Integer size;
		public final String type;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a PropertyValue object
		 */
		public PropertyValue(ObjectNode node) {
			mType = API_TYPE;
			size = parseInt(node, SIZE);
			type = parseString(node, TYPE);
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param size 
		 * @param type 
		 */
		public PropertyValue(Integer size, String type) {
			this.size = size;
			this.type = type;
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(SIZE, size);
			node.put(TYPE, type);
			return node;
		}
		/**
		 * Extracts a list of {@link PlaylistModel.PropertyValue} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<PlaylistModel.PropertyValue> getPlaylistModelPropertyValueList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<PlaylistModel.PropertyValue> l = new ArrayList<PlaylistModel.PropertyValue>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new PlaylistModel.PropertyValue((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<PlaylistModel.PropertyValue>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeValue(size);
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
			size = parcel.readInt();
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
	public interface Type {
		public final String UNKNOWN = "unknown";
		public final String VIDEO = "video";
		public final String AUDIO = "audio";
		public final String PICTURE = "picture";
		public final String MIXED = "mixed";
	}
}