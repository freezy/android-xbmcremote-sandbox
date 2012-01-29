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

package org.xbmc.android.jsonrpc.api.call;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.api.AbstractModel;
import org.xbmc.android.jsonrpc.api.model.AudioModel;
import org.xbmc.android.jsonrpc.api.model.LibraryModel;

public final class AudioLibrary {

	private final static String PREFIX = "AudioLibrary.";

	/**
	 * Cleans the audio library from non-existent items
	 * <p/>
	 * API Name: <code>AudioLibrary.Clean</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Clean extends AbstractCall<String> { 
		private static final String NAME = "Clean";
		/**
		 * Cleans the audio library from non-existent items
		 */
		public Clean() {
			super();
		}
		@Override
		protected String parseOne(ObjectNode node) {
			return node.getTextValue();
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
				parcel.writeValue(mResult);
			}
		/**
		 * Construct via parcel
		 */
		protected Clean(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Clean> CREATOR = new Parcelable.Creator<Clean>() {
			@Override
			public Clean createFromParcel(Parcel parcel) {
				return new Clean(parcel);
			}
			@Override
			public Clean[] newArray(int n) {
				return new Clean[n];
			}
		};
}
	/**
	 * Exports all items from the audio library
	 * <p/>
	 * API Name: <code>AudioLibrary.Export</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Export extends AbstractCall<String> { 
		private static final String NAME = "Export";
		/**
		 * Exports all items from the audio library
		 * @param options 
		 */
		public Export(Path options) {
			super();
			addParameter("options", options);
		}
		/**
		 * Exports all items from the audio library
		 * @param options 
		 */
		public Export(ImagesOverwrite options) {
			super();
			addParameter("options", options);
		}
		@Override
		protected String parseOne(ObjectNode node) {
			return node.getTextValue();
		}
		/**
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class Path extends AbstractModel {
			// field names
			public static final String PATH = "path";
			// class members
			/**
			 * Path to the directory to where the data should be exported.
			 */
			public final String path;
			/**
			 * Construct object with native values for later serialization.
			 * @param path Path to the directory to where the data should be exported 
			 */
			public Path(String path) {
				this.path = path;
			}
			@Override
			public ObjectNode toObjectNode() {
				final ObjectNode node = OM.createObjectNode();
				node.put(PATH, path);
				return node;
			}
			/**
			 * Flatten this object into a Parcel.
			 * @param parcel the Parcel in which the object should be written
			 * @param flags additional flags about how the object should be written
			 */
			@Override
			public void writeToParcel(Parcel parcel, int flags) {
				parcel.writeValue(path);
			}
			@Override
			public int describeContents() {
				return 0;
			}
			/**
			 * Construct via parcel
			 */
			protected Path(Parcel parcel) {
				path = parcel.readString();
			}
			/**
			 * Generates instances of this Parcelable class from a Parcel.
			 */
			public static final Parcelable.Creator<Path> CREATOR = new Parcelable.Creator<Path>() {
				@Override
				public Path createFromParcel(Parcel parcel) {
					return new Path(parcel);
				}
				@Override
				public Path[] newArray(int n) {
					return new Path[n];
				}
			};
		}
		/**
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class ImagesOverwrite extends AbstractModel {
			// field names
			public static final String IMAGES = "images";
			public static final String OVERWRITE = "overwrite";
			// class members
			/**
			 * Whether to export thumbnails and fanart images.
			 */
			public final Boolean images;
			/**
			 * Whether to overwrite existing exported files.
			 */
			public final Boolean overwrite;
			/**
			 * Construct object with native values for later serialization.
			 * @param images Whether to export thumbnails and fanart images 
			 * @param overwrite Whether to overwrite existing exported files 
			 */
			public ImagesOverwrite(Boolean images, Boolean overwrite) {
				this.images = images;
				this.overwrite = overwrite;
			}
			@Override
			public ObjectNode toObjectNode() {
				final ObjectNode node = OM.createObjectNode();
				node.put(IMAGES, images);
				node.put(OVERWRITE, overwrite);
				return node;
			}
			/**
			 * Flatten this object into a Parcel.
			 * @param parcel the Parcel in which the object should be written
			 * @param flags additional flags about how the object should be written
			 */
			@Override
			public void writeToParcel(Parcel parcel, int flags) {
				parcel.writeValue(images);
				parcel.writeValue(overwrite);
			}
			@Override
			public int describeContents() {
				return 0;
			}
			/**
			 * Construct via parcel
			 */
			protected ImagesOverwrite(Parcel parcel) {
				images = parcel.readInt() == 1;
				overwrite = parcel.readInt() == 1;
			}
			/**
			 * Generates instances of this Parcelable class from a Parcel.
			 */
			public static final Parcelable.Creator<ImagesOverwrite> CREATOR = new Parcelable.Creator<ImagesOverwrite>() {
				@Override
				public ImagesOverwrite createFromParcel(Parcel parcel) {
					return new ImagesOverwrite(parcel);
				}
				@Override
				public ImagesOverwrite[] newArray(int n) {
					return new ImagesOverwrite[n];
				}
			};
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
				parcel.writeValue(mResult);
			}
		/**
		 * Construct via parcel
		 */
		protected Export(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Export> CREATOR = new Parcelable.Creator<Export>() {
			@Override
			public Export createFromParcel(Parcel parcel) {
				return new Export(parcel);
			}
			@Override
			public Export[] newArray(int n) {
				return new Export[n];
			}
		};
}
	/**
	 * Retrieve details about a specific album
	 * <p/>
	 * API Name: <code>AudioLibrary.GetAlbumDetails</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetAlbumDetails extends AbstractCall<AudioModel.AlbumDetails> { 
		private static final String NAME = "GetAlbumDetails";
		public static final String RESULTS = "albumdetails";
		/**
		 * Retrieve details about a specific album
		 * @param albumid 
		 * @param properties One or more of: <tt>title</tt>, <tt>description</tt>, <tt>artist</tt>, <tt>genre</tt>, <tt>theme</tt>, <tt>mood</tt>, <tt>style</tt>, <tt>type</tt>, <tt>albumlabel</tt>, <tt>rating</tt>, <tt>year</tt>, <tt>musicbrainzalbumid</tt>, <tt>musicbrainzalbumartistid</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>artistid</tt>. See constants at {@link AudioModel.AlbumFields}.
		 * @see AudioModel.AlbumFields
		 */
		public GetAlbumDetails(Integer albumid, String... properties) {
			super();
			addParameter("albumid", albumid);
			addParameter("properties", properties);
		}
		@Override
		protected AudioModel.AlbumDetails parseOne(ObjectNode node) {
			return new AudioModel.AlbumDetails((ObjectNode)node.get(RESULTS));
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
				parcel.writeParcelable(mResult, flags);
			}
		/**
		 * Construct via parcel
		 */
		protected GetAlbumDetails(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetAlbumDetails> CREATOR = new Parcelable.Creator<GetAlbumDetails>() {
			@Override
			public GetAlbumDetails createFromParcel(Parcel parcel) {
				return new GetAlbumDetails(parcel);
			}
			@Override
			public GetAlbumDetails[] newArray(int n) {
				return new GetAlbumDetails[n];
			}
		};
}
	/**
	 * Retrieve all albums from specified artist or genre
	 * <p/>
	 * API Name: <code>AudioLibrary.GetAlbums</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetAlbums extends AbstractCall<AudioModel.AlbumDetails> { 
		private static final String NAME = "GetAlbums";
		public static final String RESULTS = "albums";
		/**
		 * Retrieve all albums from specified artist or genre
		 * @param artistid 
		 * @param genreid 
		 * @param properties One or more of: <tt>title</tt>, <tt>description</tt>, <tt>artist</tt>, <tt>genre</tt>, <tt>theme</tt>, <tt>mood</tt>, <tt>style</tt>, <tt>type</tt>, <tt>albumlabel</tt>, <tt>rating</tt>, <tt>year</tt>, <tt>musicbrainzalbumid</tt>, <tt>musicbrainzalbumartistid</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>artistid</tt>. See constants at {@link AudioModel.AlbumFields}.
		 * @see AudioModel.AlbumFields
		 */
		public GetAlbums(Integer artistid, Integer genreid, String... properties) {
			super();
			addParameter("artistid", artistid);
			addParameter("genreid", genreid);
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<AudioModel.AlbumDetails> parseMany(ObjectNode node) {
			final ArrayNode albums = parseResults(node, RESULTS);
			final ArrayList<AudioModel.AlbumDetails> ret = new ArrayList<AudioModel.AlbumDetails>(albums.size());
			for (int i = 0; i < albums.size(); i++) {
				final ObjectNode item = (ObjectNode)albums.get(i);
				ret.add(new AudioModel.AlbumDetails(item));
			}
			return ret;
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return true;
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			final ArrayList<AudioModel.AlbumDetails> results = mResults;
			if (results != null && results.size() > 0) {
				parcel.writeInt(results.size());
				for (AudioModel.AlbumDetails result : results) {
					parcel.writeParcelable(result, flags);
				}
			} else {
				parcel.writeInt(0);
			}
			}
		/**
		 * Construct via parcel
		 */
		protected GetAlbums(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetAlbums> CREATOR = new Parcelable.Creator<GetAlbums>() {
			@Override
			public GetAlbums createFromParcel(Parcel parcel) {
				return new GetAlbums(parcel);
			}
			@Override
			public GetAlbums[] newArray(int n) {
				return new GetAlbums[n];
			}
		};
}
	/**
	 * Retrieve details about a specific artist
	 * <p/>
	 * API Name: <code>AudioLibrary.GetArtistDetails</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetArtistDetails extends AbstractCall<AudioModel.ArtistDetails> { 
		private static final String NAME = "GetArtistDetails";
		public static final String RESULTS = "artistdetails";
		/**
		 * Retrieve details about a specific artist
		 * @param artistid 
		 * @param properties One or more of: <tt>instrument</tt>, <tt>style</tt>, <tt>mood</tt>, <tt>born</tt>, <tt>formed</tt>, <tt>description</tt>, <tt>genre</tt>, <tt>died</tt>, <tt>disbanded</tt>, <tt>yearsactive</tt>, <tt>musicbrainzartistid</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>. See constants at {@link AudioModel.ArtistFields}.
		 * @see AudioModel.ArtistFields
		 */
		public GetArtistDetails(Integer artistid, String... properties) {
			super();
			addParameter("artistid", artistid);
			addParameter("properties", properties);
		}
		@Override
		protected AudioModel.ArtistDetails parseOne(ObjectNode node) {
			return new AudioModel.ArtistDetails((ObjectNode)node.get(RESULTS));
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
				parcel.writeParcelable(mResult, flags);
			}
		/**
		 * Construct via parcel
		 */
		protected GetArtistDetails(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetArtistDetails> CREATOR = new Parcelable.Creator<GetArtistDetails>() {
			@Override
			public GetArtistDetails createFromParcel(Parcel parcel) {
				return new GetArtistDetails(parcel);
			}
			@Override
			public GetArtistDetails[] newArray(int n) {
				return new GetArtistDetails[n];
			}
		};
}
	/**
	 * Retrieve all artists
	 * <p/>
	 * API Name: <code>AudioLibrary.GetArtists</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetArtists extends AbstractCall<AudioModel.ArtistDetails> { 
		private static final String NAME = "GetArtists";
		public static final String RESULTS = "artists";
		/**
		 * Retrieve all artists
		 * @param albumartistsonly Retrieve all artists
		 * @param genreid 
		 * @param properties One or more of: <tt>instrument</tt>, <tt>style</tt>, <tt>mood</tt>, <tt>born</tt>, <tt>formed</tt>, <tt>description</tt>, <tt>genre</tt>, <tt>died</tt>, <tt>disbanded</tt>, <tt>yearsactive</tt>, <tt>musicbrainzartistid</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>. See constants at {@link AudioModel.ArtistFields}.
		 * @see AudioModel.ArtistFields
		 */
		public GetArtists(Boolean albumartistsonly, Integer genreid, String... properties) {
			super();
			addParameter("albumartistsonly", albumartistsonly);
			addParameter("genreid", genreid);
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<AudioModel.ArtistDetails> parseMany(ObjectNode node) {
			final ArrayNode artists = parseResults(node, RESULTS);
			final ArrayList<AudioModel.ArtistDetails> ret = new ArrayList<AudioModel.ArtistDetails>(artists.size());
			for (int i = 0; i < artists.size(); i++) {
				final ObjectNode item = (ObjectNode)artists.get(i);
				ret.add(new AudioModel.ArtistDetails(item));
			}
			return ret;
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return true;
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			final ArrayList<AudioModel.ArtistDetails> results = mResults;
			if (results != null && results.size() > 0) {
				parcel.writeInt(results.size());
				for (AudioModel.ArtistDetails result : results) {
					parcel.writeParcelable(result, flags);
				}
			} else {
				parcel.writeInt(0);
			}
			}
		/**
		 * Construct via parcel
		 */
		protected GetArtists(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetArtists> CREATOR = new Parcelable.Creator<GetArtists>() {
			@Override
			public GetArtists createFromParcel(Parcel parcel) {
				return new GetArtists(parcel);
			}
			@Override
			public GetArtists[] newArray(int n) {
				return new GetArtists[n];
			}
		};
}
	/**
	 * Retrieve all genres
	 * <p/>
	 * API Name: <code>AudioLibrary.GetGenres</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetGenres extends AbstractCall<LibraryModel.GenreDetails> { 
		private static final String NAME = "GetGenres";
		public static final String RESULTS = "genres";
		/**
		 * Retrieve all genres
		 * @param properties One or more of: <tt>title</tt>, <tt>thumbnail</tt>. See constants at {@link LibraryModel.GenreFields}.
		 * @see LibraryModel.GenreFields
		 */
		public GetGenres(String... properties) {
			super();
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<LibraryModel.GenreDetails> parseMany(ObjectNode node) {
			final ArrayNode genres = parseResults(node, RESULTS);
			final ArrayList<LibraryModel.GenreDetails> ret = new ArrayList<LibraryModel.GenreDetails>(genres.size());
			for (int i = 0; i < genres.size(); i++) {
				final ObjectNode item = (ObjectNode)genres.get(i);
				ret.add(new LibraryModel.GenreDetails(item));
			}
			return ret;
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return true;
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			final ArrayList<LibraryModel.GenreDetails> results = mResults;
			if (results != null && results.size() > 0) {
				parcel.writeInt(results.size());
				for (LibraryModel.GenreDetails result : results) {
					parcel.writeParcelable(result, flags);
				}
			} else {
				parcel.writeInt(0);
			}
			}
		/**
		 * Construct via parcel
		 */
		protected GetGenres(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetGenres> CREATOR = new Parcelable.Creator<GetGenres>() {
			@Override
			public GetGenres createFromParcel(Parcel parcel) {
				return new GetGenres(parcel);
			}
			@Override
			public GetGenres[] newArray(int n) {
				return new GetGenres[n];
			}
		};
}
	/**
	 * Retrieve recently added albums
	 * <p/>
	 * API Name: <code>AudioLibrary.GetRecentlyAddedAlbums</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetRecentlyAddedAlbums extends AbstractCall<AudioModel.AlbumDetails> { 
		private static final String NAME = "GetRecentlyAddedAlbums";
		public static final String RESULTS = "albums";
		/**
		 * Retrieve recently added albums
		 * @param properties One or more of: <tt>title</tt>, <tt>description</tt>, <tt>artist</tt>, <tt>genre</tt>, <tt>theme</tt>, <tt>mood</tt>, <tt>style</tt>, <tt>type</tt>, <tt>albumlabel</tt>, <tt>rating</tt>, <tt>year</tt>, <tt>musicbrainzalbumid</tt>, <tt>musicbrainzalbumartistid</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>artistid</tt>. See constants at {@link AudioModel.AlbumFields}.
		 * @see AudioModel.AlbumFields
		 */
		public GetRecentlyAddedAlbums(String... properties) {
			super();
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<AudioModel.AlbumDetails> parseMany(ObjectNode node) {
			final ArrayNode albums = parseResults(node, RESULTS);
			final ArrayList<AudioModel.AlbumDetails> ret = new ArrayList<AudioModel.AlbumDetails>(albums.size());
			for (int i = 0; i < albums.size(); i++) {
				final ObjectNode item = (ObjectNode)albums.get(i);
				ret.add(new AudioModel.AlbumDetails(item));
			}
			return ret;
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return true;
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			final ArrayList<AudioModel.AlbumDetails> results = mResults;
			if (results != null && results.size() > 0) {
				parcel.writeInt(results.size());
				for (AudioModel.AlbumDetails result : results) {
					parcel.writeParcelable(result, flags);
				}
			} else {
				parcel.writeInt(0);
			}
			}
		/**
		 * Construct via parcel
		 */
		protected GetRecentlyAddedAlbums(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetRecentlyAddedAlbums> CREATOR = new Parcelable.Creator<GetRecentlyAddedAlbums>() {
			@Override
			public GetRecentlyAddedAlbums createFromParcel(Parcel parcel) {
				return new GetRecentlyAddedAlbums(parcel);
			}
			@Override
			public GetRecentlyAddedAlbums[] newArray(int n) {
				return new GetRecentlyAddedAlbums[n];
			}
		};
}
	/**
	 * Retrieve recently added songs
	 * <p/>
	 * API Name: <code>AudioLibrary.GetRecentlyAddedSongs</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetRecentlyAddedSongs extends AbstractCall<AudioModel.SongDetails> { 
		private static final String NAME = "GetRecentlyAddedSongs";
		public static final String RESULTS = "songs";
		/**
		 * Retrieve recently added songs
		 * @param albumlimit Retrieve recently added songs
		 * @param properties One or more of: <tt>title</tt>, <tt>artist</tt>, <tt>albumartist</tt>, <tt>genre</tt>, <tt>year</tt>, <tt>rating</tt>, <tt>album</tt>, <tt>track</tt>, <tt>duration</tt>, <tt>comment</tt>, <tt>lyrics</tt>, <tt>musicbrainztrackid</tt>, <tt>musicbrainzartistid</tt>, <tt>musicbrainzalbumid</tt>, <tt>musicbrainzalbumartistid</tt>, <tt>playcount</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>artistid</tt>, <tt>albumid</tt>. See constants at {@link AudioModel.SongFields}.
		 * @see AudioModel.SongFields
		 */
		public GetRecentlyAddedSongs(Integer albumlimit, String... properties) {
			super();
			addParameter("albumlimit", albumlimit);
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<AudioModel.SongDetails> parseMany(ObjectNode node) {
			final ArrayNode songs = parseResults(node, RESULTS);
			final ArrayList<AudioModel.SongDetails> ret = new ArrayList<AudioModel.SongDetails>(songs.size());
			for (int i = 0; i < songs.size(); i++) {
				final ObjectNode item = (ObjectNode)songs.get(i);
				ret.add(new AudioModel.SongDetails(item));
			}
			return ret;
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return true;
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			final ArrayList<AudioModel.SongDetails> results = mResults;
			if (results != null && results.size() > 0) {
				parcel.writeInt(results.size());
				for (AudioModel.SongDetails result : results) {
					parcel.writeParcelable(result, flags);
				}
			} else {
				parcel.writeInt(0);
			}
			}
		/**
		 * Construct via parcel
		 */
		protected GetRecentlyAddedSongs(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetRecentlyAddedSongs> CREATOR = new Parcelable.Creator<GetRecentlyAddedSongs>() {
			@Override
			public GetRecentlyAddedSongs createFromParcel(Parcel parcel) {
				return new GetRecentlyAddedSongs(parcel);
			}
			@Override
			public GetRecentlyAddedSongs[] newArray(int n) {
				return new GetRecentlyAddedSongs[n];
			}
		};
}
	/**
	 * Retrieve details about a specific song
	 * <p/>
	 * API Name: <code>AudioLibrary.GetSongDetails</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetSongDetails extends AbstractCall<AudioModel.SongDetails> { 
		private static final String NAME = "GetSongDetails";
		public static final String RESULTS = "songdetails";
		/**
		 * Retrieve details about a specific song
		 * @param songid 
		 * @param properties One or more of: <tt>title</tt>, <tt>artist</tt>, <tt>albumartist</tt>, <tt>genre</tt>, <tt>year</tt>, <tt>rating</tt>, <tt>album</tt>, <tt>track</tt>, <tt>duration</tt>, <tt>comment</tt>, <tt>lyrics</tt>, <tt>musicbrainztrackid</tt>, <tt>musicbrainzartistid</tt>, <tt>musicbrainzalbumid</tt>, <tt>musicbrainzalbumartistid</tt>, <tt>playcount</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>artistid</tt>, <tt>albumid</tt>. See constants at {@link AudioModel.SongFields}.
		 * @see AudioModel.SongFields
		 */
		public GetSongDetails(Integer songid, String... properties) {
			super();
			addParameter("songid", songid);
			addParameter("properties", properties);
		}
		@Override
		protected AudioModel.SongDetails parseOne(ObjectNode node) {
			return new AudioModel.SongDetails((ObjectNode)node.get(RESULTS));
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
				parcel.writeParcelable(mResult, flags);
			}
		/**
		 * Construct via parcel
		 */
		protected GetSongDetails(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetSongDetails> CREATOR = new Parcelable.Creator<GetSongDetails>() {
			@Override
			public GetSongDetails createFromParcel(Parcel parcel) {
				return new GetSongDetails(parcel);
			}
			@Override
			public GetSongDetails[] newArray(int n) {
				return new GetSongDetails[n];
			}
		};
}
	/**
	 * Retrieve all songs from specified album, artist or genre
	 * <p/>
	 * API Name: <code>AudioLibrary.GetSongs</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetSongs extends AbstractCall<AudioModel.SongDetails> { 
		private static final String NAME = "GetSongs";
		public static final String RESULTS = "songs";
		/**
		 * Retrieve all songs from specified album, artist or genre
		 * @param artistid 
		 * @param albumid 
		 * @param genreid 
		 * @param properties One or more of: <tt>title</tt>, <tt>artist</tt>, <tt>albumartist</tt>, <tt>genre</tt>, <tt>year</tt>, <tt>rating</tt>, <tt>album</tt>, <tt>track</tt>, <tt>duration</tt>, <tt>comment</tt>, <tt>lyrics</tt>, <tt>musicbrainztrackid</tt>, <tt>musicbrainzartistid</tt>, <tt>musicbrainzalbumid</tt>, <tt>musicbrainzalbumartistid</tt>, <tt>playcount</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>artistid</tt>, <tt>albumid</tt>. See constants at {@link AudioModel.SongFields}.
		 * @see AudioModel.SongFields
		 */
		public GetSongs(Integer artistid, Integer albumid, Integer genreid, String... properties) {
			super();
			addParameter("artistid", artistid);
			addParameter("albumid", albumid);
			addParameter("genreid", genreid);
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<AudioModel.SongDetails> parseMany(ObjectNode node) {
			final ArrayNode songs = parseResults(node, RESULTS);
			final ArrayList<AudioModel.SongDetails> ret = new ArrayList<AudioModel.SongDetails>(songs.size());
			for (int i = 0; i < songs.size(); i++) {
				final ObjectNode item = (ObjectNode)songs.get(i);
				ret.add(new AudioModel.SongDetails(item));
			}
			return ret;
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return true;
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			final ArrayList<AudioModel.SongDetails> results = mResults;
			if (results != null && results.size() > 0) {
				parcel.writeInt(results.size());
				for (AudioModel.SongDetails result : results) {
					parcel.writeParcelable(result, flags);
				}
			} else {
				parcel.writeInt(0);
			}
			}
		/**
		 * Construct via parcel
		 */
		protected GetSongs(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetSongs> CREATOR = new Parcelable.Creator<GetSongs>() {
			@Override
			public GetSongs createFromParcel(Parcel parcel) {
				return new GetSongs(parcel);
			}
			@Override
			public GetSongs[] newArray(int n) {
				return new GetSongs[n];
			}
		};
}
	/**
	 * Scans the audio sources for new library items
	 * <p/>
	 * API Name: <code>AudioLibrary.Scan</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Scan extends AbstractCall<String> { 
		private static final String NAME = "Scan";
		/**
		 * Scans the audio sources for new library items
		 */
		public Scan() {
			super();
		}
		@Override
		protected String parseOne(ObjectNode node) {
			return node.getTextValue();
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
				parcel.writeValue(mResult);
			}
		/**
		 * Construct via parcel
		 */
		protected Scan(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Scan> CREATOR = new Parcelable.Creator<Scan>() {
			@Override
			public Scan createFromParcel(Parcel parcel) {
				return new Scan(parcel);
			}
			@Override
			public Scan[] newArray(int n) {
				return new Scan[n];
			}
		};
}
}