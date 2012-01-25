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
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.api.AbstractModel;
import org.xbmc.android.jsonrpc.api.model.LibraryModel;
import org.xbmc.android.jsonrpc.api.model.ListModel;
import org.xbmc.android.jsonrpc.api.model.VideoModel;

public final class VideoLibrary {

	private final static String PREFIX = "VideoLibrary.";

	/**
	 * Cleans the video library from non-existent items
	 * <p/>
	 * API Name: <code>VideoLibrary.Clean</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Clean extends AbstractCall<String> { 
		private static final String NAME = "Clean";
		/**
		 * Cleans the video library from non-existent items
		 */
		public Clean() {
			super();
		}
		@Override
		protected String parseOne(ObjectNode node) {
			return node.get(RESULT).getTextValue();
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
		/**
		 * Construct via parcel
		 */
		protected Clean(Parcel parcel) {
			try {
				mResponse = (ObjectNode)OM.readTree(parcel.readString());
			} catch (JsonProcessingException e) {
				Log.e(NAME, "Error reading JSON object from parcel: " + e.getMessage(), e);
			} catch (IOException e) {
				Log.e(NAME, "I/O exception reading JSON object from parcel: " + e.getMessage(), e);
			}
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
	 * Exports all items from the video library
	 * <p/>
	 * API Name: <code>VideoLibrary.Export</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Export extends AbstractCall<String> { 
		private static final String NAME = "Export";
		/**
		 * Exports all items from the video library
		 * @param options 
		 */
		public Export(Path options) {
			super();
			addParameter("options", options);
		}
		/**
		 * Exports all items from the video library
		 * @param options 
		 */
		public Export(ActorthumbsImagesOverwrite options) {
			super();
			addParameter("options", options);
		}
		@Override
		protected String parseOne(ObjectNode node) {
			return node.get(RESULT).getTextValue();
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
		public static class ActorthumbsImagesOverwrite extends AbstractModel {
			// field names
			public static final String ACTORTHUMBS = "actorthumbs";
			public static final String IMAGES = "images";
			public static final String OVERWRITE = "overwrite";
			// class members
			/**
			 * Whether to export actor thumbnails.
			 */
			public final Boolean actorthumbs;
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
			 * @param actorthumbs Whether to export actor thumbnails 
			 * @param images Whether to export thumbnails and fanart images 
			 * @param overwrite Whether to overwrite existing exported files 
			 */
			public ActorthumbsImagesOverwrite(Boolean actorthumbs, Boolean images, Boolean overwrite) {
				this.actorthumbs = actorthumbs;
				this.images = images;
				this.overwrite = overwrite;
			}
			@Override
			public ObjectNode toObjectNode() {
				final ObjectNode node = OM.createObjectNode();
				node.put(ACTORTHUMBS, actorthumbs);
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
				parcel.writeValue(actorthumbs);
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
			protected ActorthumbsImagesOverwrite(Parcel parcel) {
				actorthumbs = parcel.readInt() == 1;
				images = parcel.readInt() == 1;
				overwrite = parcel.readInt() == 1;
			}
			/**
			 * Generates instances of this Parcelable class from a Parcel.
			 */
			public static final Parcelable.Creator<ActorthumbsImagesOverwrite> CREATOR = new Parcelable.Creator<ActorthumbsImagesOverwrite>() {
				@Override
				public ActorthumbsImagesOverwrite createFromParcel(Parcel parcel) {
					return new ActorthumbsImagesOverwrite(parcel);
				}
				@Override
				public ActorthumbsImagesOverwrite[] newArray(int n) {
					return new ActorthumbsImagesOverwrite[n];
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
		/**
		 * Construct via parcel
		 */
		protected Export(Parcel parcel) {
			try {
				mResponse = (ObjectNode)OM.readTree(parcel.readString());
			} catch (JsonProcessingException e) {
				Log.e(NAME, "Error reading JSON object from parcel: " + e.getMessage(), e);
			} catch (IOException e) {
				Log.e(NAME, "I/O exception reading JSON object from parcel: " + e.getMessage(), e);
			}
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
	 * Retrieve details about a specific tv show episode
	 * <p/>
	 * API Name: <code>VideoLibrary.GetEpisodeDetails</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetEpisodeDetails extends AbstractCall<VideoModel.EpisodeDetails> { 
		private static final String NAME = "GetEpisodeDetails";
		public static final String RESULTS = "episodedetails";
		/**
		 * Retrieve details about a specific tv show episode
		 * @param episodeid 
		 * @param properties One or more of: <tt>title</tt>, <tt>plot</tt>, <tt>votes</tt>, <tt>rating</tt>, <tt>writer</tt>, <tt>firstaired</tt>, <tt>playcount</tt>, <tt>runtime</tt>, <tt>director</tt>, <tt>productioncode</tt>, <tt>season</tt>, <tt>episode</tt>, <tt>originaltitle</tt>, <tt>showtitle</tt>, <tt>cast</tt>, <tt>streamdetails</tt>, <tt>lastplayed</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>resume</tt>, <tt>tvshowid</tt>. See constants at {@link VideoModel.EpisodeFields}.
		 * @see VideoModel.EpisodeFields
		 */
		public GetEpisodeDetails(Integer episodeid, String... properties) {
			super();
			addParameter("episodeid", episodeid);
			addParameter("properties", properties);
		}
		@Override
		protected VideoModel.EpisodeDetails parseOne(ObjectNode node) {
			return new VideoModel.EpisodeDetails((ObjectNode)parseResult(node).get(RESULTS));
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
		/**
		 * Construct via parcel
		 */
		protected GetEpisodeDetails(Parcel parcel) {
			try {
				mResponse = (ObjectNode)OM.readTree(parcel.readString());
			} catch (JsonProcessingException e) {
				Log.e(NAME, "Error reading JSON object from parcel: " + e.getMessage(), e);
			} catch (IOException e) {
				Log.e(NAME, "I/O exception reading JSON object from parcel: " + e.getMessage(), e);
			}
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetEpisodeDetails> CREATOR = new Parcelable.Creator<GetEpisodeDetails>() {
			@Override
			public GetEpisodeDetails createFromParcel(Parcel parcel) {
				return new GetEpisodeDetails(parcel);
			}
			@Override
			public GetEpisodeDetails[] newArray(int n) {
				return new GetEpisodeDetails[n];
			}
		};
}
	/**
	 * Retrieve all tv show episodes
	 * <p/>
	 * API Name: <code>VideoLibrary.GetEpisodes</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetEpisodes extends AbstractCall<VideoModel.EpisodeDetails> { 
		private static final String NAME = "GetEpisodes";
		public static final String RESULTS = "episodes";
		/**
		 * Retrieve all tv show episodes
		 * @param tvshowid 
		 * @param season 
		 * @param properties One or more of: <tt>title</tt>, <tt>plot</tt>, <tt>votes</tt>, <tt>rating</tt>, <tt>writer</tt>, <tt>firstaired</tt>, <tt>playcount</tt>, <tt>runtime</tt>, <tt>director</tt>, <tt>productioncode</tt>, <tt>season</tt>, <tt>episode</tt>, <tt>originaltitle</tt>, <tt>showtitle</tt>, <tt>cast</tt>, <tt>streamdetails</tt>, <tt>lastplayed</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>resume</tt>, <tt>tvshowid</tt>. See constants at {@link VideoModel.EpisodeFields}.
		 * @see VideoModel.EpisodeFields
		 */
		public GetEpisodes(Integer tvshowid, Integer season, String... properties) {
			super();
			addParameter("tvshowid", tvshowid);
			addParameter("season", season);
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<VideoModel.EpisodeDetails> parseMany(ObjectNode node) {
			final ArrayNode episodes = (ArrayNode)parseResult(node).get(RESULTS);
			final ArrayList<VideoModel.EpisodeDetails> ret = new ArrayList<VideoModel.EpisodeDetails>(episodes.size());
			for (int i = 0; i < episodes.size(); i++) {
				final ObjectNode item = (ObjectNode)episodes.get(i);
				ret.add(new VideoModel.EpisodeDetails(item));
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
		/**
		 * Construct via parcel
		 */
		protected GetEpisodes(Parcel parcel) {
			try {
				mResponse = (ObjectNode)OM.readTree(parcel.readString());
			} catch (JsonProcessingException e) {
				Log.e(NAME, "Error reading JSON object from parcel: " + e.getMessage(), e);
			} catch (IOException e) {
				Log.e(NAME, "I/O exception reading JSON object from parcel: " + e.getMessage(), e);
			}
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetEpisodes> CREATOR = new Parcelable.Creator<GetEpisodes>() {
			@Override
			public GetEpisodes createFromParcel(Parcel parcel) {
				return new GetEpisodes(parcel);
			}
			@Override
			public GetEpisodes[] newArray(int n) {
				return new GetEpisodes[n];
			}
		};
}
	/**
	 * Retrieve all genres
	 * <p/>
	 * API Name: <code>VideoLibrary.GetGenres</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetGenres extends AbstractCall<LibraryModel.GenreDetails> { 
		private static final String NAME = "GetGenres";
		public static final String RESULTS = "genres";
		/**
		 * Retrieve all genres
		 * @param type One of: <tt>movie</tt>, <tt>tvshow</tt>, <tt>musicvideo</tt>. See constants at {@link GlobalModel.}.
		 * @param properties One or more of: <tt>title</tt>, <tt>thumbnail</tt>. See constants at {@link LibraryModel.GenreFields}.
		 * @see GlobalModel.
		 * @see LibraryModel.GenreFields
		 */
		public GetGenres(String type, String... properties) {
			super();
			addParameter("type", type);
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<LibraryModel.GenreDetails> parseMany(ObjectNode node) {
			final ArrayNode genres = (ArrayNode)parseResult(node).get(RESULTS);
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
		/**
		 * Construct via parcel
		 */
		protected GetGenres(Parcel parcel) {
			try {
				mResponse = (ObjectNode)OM.readTree(parcel.readString());
			} catch (JsonProcessingException e) {
				Log.e(NAME, "Error reading JSON object from parcel: " + e.getMessage(), e);
			} catch (IOException e) {
				Log.e(NAME, "I/O exception reading JSON object from parcel: " + e.getMessage(), e);
			}
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
	 * Retrieve details about a specific movie
	 * <p/>
	 * API Name: <code>VideoLibrary.GetMovieDetails</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetMovieDetails extends AbstractCall<VideoModel.MovieDetails> { 
		private static final String NAME = "GetMovieDetails";
		public static final String RESULTS = "moviedetails";
		/**
		 * Retrieve details about a specific movie
		 * @param movieid 
		 * @param properties One or more of: <tt>title</tt>, <tt>genre</tt>, <tt>year</tt>, <tt>rating</tt>, <tt>director</tt>, <tt>trailer</tt>, <tt>tagline</tt>, <tt>plot</tt>, <tt>plotoutline</tt>, <tt>originaltitle</tt>, <tt>lastplayed</tt>, <tt>playcount</tt>, <tt>writer</tt>, <tt>studio</tt>, <tt>mpaa</tt>, <tt>cast</tt>, <tt>country</tt>, <tt>imdbnumber</tt>, <tt>premiered</tt>, <tt>productioncode</tt>, <tt>runtime</tt>, <tt>set</tt>, <tt>showlink</tt>, <tt>streamdetails</tt>, <tt>top250</tt>, <tt>votes</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>sorttitle</tt>, <tt>resume</tt>, <tt>setid</tt>. See constants at {@link VideoModel.MovieFields}.
		 * @see VideoModel.MovieFields
		 */
		public GetMovieDetails(Integer movieid, String... properties) {
			super();
			addParameter("movieid", movieid);
			addParameter("properties", properties);
		}
		@Override
		protected VideoModel.MovieDetails parseOne(ObjectNode node) {
			return new VideoModel.MovieDetails((ObjectNode)parseResult(node).get(RESULTS));
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
		/**
		 * Construct via parcel
		 */
		protected GetMovieDetails(Parcel parcel) {
			try {
				mResponse = (ObjectNode)OM.readTree(parcel.readString());
			} catch (JsonProcessingException e) {
				Log.e(NAME, "Error reading JSON object from parcel: " + e.getMessage(), e);
			} catch (IOException e) {
				Log.e(NAME, "I/O exception reading JSON object from parcel: " + e.getMessage(), e);
			}
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetMovieDetails> CREATOR = new Parcelable.Creator<GetMovieDetails>() {
			@Override
			public GetMovieDetails createFromParcel(Parcel parcel) {
				return new GetMovieDetails(parcel);
			}
			@Override
			public GetMovieDetails[] newArray(int n) {
				return new GetMovieDetails[n];
			}
		};
}
	/**
	 * Retrieve details about a specific movie set
	 * <p/>
	 * API Name: <code>VideoLibrary.GetMovieSetDetails</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetMovieSetDetails extends AbstractCall<VideoModel.DetailsMovieSetExtended> { 
		private static final String NAME = "GetMovieSetDetails";
		public static final String RESULTS = "setdetails";
		/**
		 * Retrieve details about a specific movie set
		 * @param setid 
		 * @param properties One or more of: <tt>title</tt>, <tt>playcount</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>. See constants at {@link VideoModel.MovieSetFields}.
		 * @param movies 
		 * @see VideoModel.MovieSetFields
		 */
		public GetMovieSetDetails(Integer setid, LimitsPropertiesSort movies, String... properties) {
			super();
			addParameter("setid", setid);
			addParameter("movies", movies);
			addParameter("properties", properties);
		}
		@Override
		protected VideoModel.DetailsMovieSetExtended parseOne(ObjectNode node) {
			return new VideoModel.DetailsMovieSetExtended((ObjectNode)parseResult(node).get(RESULTS));
		}
		/**
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class LimitsPropertiesSort extends AbstractModel {
			// field names
			public static final String LIMITS = "limits";
			public static final String PROPERTIES = "properties";
			public static final String SORT = "sort";
			// class members
			public final ListModel.Limits limits;
			public final ArrayList<String> properties;
			public final ListModel.Sort sort;
			/**
			 * Construct object with native values for later serialization.
			 * @param limits 
			 * @param properties 
			 * @param sort 
			 */
			public LimitsPropertiesSort(ListModel.Limits limits, ArrayList<String> properties, ListModel.Sort sort) {
				this.limits = limits;
				this.properties = properties;
				this.sort = sort;
			}
			@Override
			public ObjectNode toObjectNode() {
				final ObjectNode node = OM.createObjectNode();
				node.put(LIMITS, limits.toObjectNode());
				final ArrayNode propertiesArray = OM.createArrayNode();
				for (String item : properties) {
					propertiesArray.add(item);
				}
				node.put(PROPERTIES, propertiesArray);
				node.put(SORT, sort.toObjectNode());
				return node;
			}
			/**
			 * Flatten this object into a Parcel.
			 * @param parcel the Parcel in which the object should be written
			 * @param flags additional flags about how the object should be written
			 */
			@Override
			public void writeToParcel(Parcel parcel, int flags) {
				parcel.writeValue(limits);
				parcel.writeInt(properties.size());
				for (String item : properties) {
					parcel.writeValue(item);
				}
				parcel.writeValue(sort);
			}
			@Override
			public int describeContents() {
				return 0;
			}
			/**
			 * Construct via parcel
			 */
			protected LimitsPropertiesSort(Parcel parcel) {
				limits = parcel.<ListModel.Limits>readParcelable(ListModel.Limits.class.getClassLoader());
				final int propertiesSize = parcel.readInt();
				properties = new ArrayList<String>(propertiesSize);
				for (int i = 0; i < propertiesSize; i++) {
					properties.add(parcel.readString());
				}
				sort = parcel.<ListModel.Sort>readParcelable(ListModel.Sort.class.getClassLoader());
			}
			/**
			 * Generates instances of this Parcelable class from a Parcel.
			 */
			public static final Parcelable.Creator<LimitsPropertiesSort> CREATOR = new Parcelable.Creator<LimitsPropertiesSort>() {
				@Override
				public LimitsPropertiesSort createFromParcel(Parcel parcel) {
					return new LimitsPropertiesSort(parcel);
				}
				@Override
				public LimitsPropertiesSort[] newArray(int n) {
					return new LimitsPropertiesSort[n];
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
		/**
		 * Construct via parcel
		 */
		protected GetMovieSetDetails(Parcel parcel) {
			try {
				mResponse = (ObjectNode)OM.readTree(parcel.readString());
			} catch (JsonProcessingException e) {
				Log.e(NAME, "Error reading JSON object from parcel: " + e.getMessage(), e);
			} catch (IOException e) {
				Log.e(NAME, "I/O exception reading JSON object from parcel: " + e.getMessage(), e);
			}
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetMovieSetDetails> CREATOR = new Parcelable.Creator<GetMovieSetDetails>() {
			@Override
			public GetMovieSetDetails createFromParcel(Parcel parcel) {
				return new GetMovieSetDetails(parcel);
			}
			@Override
			public GetMovieSetDetails[] newArray(int n) {
				return new GetMovieSetDetails[n];
			}
		};
}
	/**
	 * Retrieve all movie sets
	 * <p/>
	 * API Name: <code>VideoLibrary.GetMovieSets</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetMovieSets extends AbstractCall<VideoModel.MovieSetDetails> { 
		private static final String NAME = "GetMovieSets";
		public static final String RESULTS = "sets";
		/**
		 * Retrieve all movie sets
		 * @param properties One or more of: <tt>title</tt>, <tt>playcount</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>. See constants at {@link VideoModel.MovieSetFields}.
		 * @see VideoModel.MovieSetFields
		 */
		public GetMovieSets(String... properties) {
			super();
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<VideoModel.MovieSetDetails> parseMany(ObjectNode node) {
			final ArrayNode sets = (ArrayNode)parseResult(node).get(RESULTS);
			final ArrayList<VideoModel.MovieSetDetails> ret = new ArrayList<VideoModel.MovieSetDetails>(sets.size());
			for (int i = 0; i < sets.size(); i++) {
				final ObjectNode item = (ObjectNode)sets.get(i);
				ret.add(new VideoModel.MovieSetDetails(item));
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
		/**
		 * Construct via parcel
		 */
		protected GetMovieSets(Parcel parcel) {
			try {
				mResponse = (ObjectNode)OM.readTree(parcel.readString());
			} catch (JsonProcessingException e) {
				Log.e(NAME, "Error reading JSON object from parcel: " + e.getMessage(), e);
			} catch (IOException e) {
				Log.e(NAME, "I/O exception reading JSON object from parcel: " + e.getMessage(), e);
			}
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetMovieSets> CREATOR = new Parcelable.Creator<GetMovieSets>() {
			@Override
			public GetMovieSets createFromParcel(Parcel parcel) {
				return new GetMovieSets(parcel);
			}
			@Override
			public GetMovieSets[] newArray(int n) {
				return new GetMovieSets[n];
			}
		};
}
	/**
	 * Retrieve all movies
	 * <p/>
	 * API Name: <code>VideoLibrary.GetMovies</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetMovies extends AbstractCall<VideoModel.MovieDetails> { 
		private static final String NAME = "GetMovies";
		public static final String RESULTS = "movies";
		/**
		 * Retrieve all movies
		 * @param properties One or more of: <tt>title</tt>, <tt>genre</tt>, <tt>year</tt>, <tt>rating</tt>, <tt>director</tt>, <tt>trailer</tt>, <tt>tagline</tt>, <tt>plot</tt>, <tt>plotoutline</tt>, <tt>originaltitle</tt>, <tt>lastplayed</tt>, <tt>playcount</tt>, <tt>writer</tt>, <tt>studio</tt>, <tt>mpaa</tt>, <tt>cast</tt>, <tt>country</tt>, <tt>imdbnumber</tt>, <tt>premiered</tt>, <tt>productioncode</tt>, <tt>runtime</tt>, <tt>set</tt>, <tt>showlink</tt>, <tt>streamdetails</tt>, <tt>top250</tt>, <tt>votes</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>sorttitle</tt>, <tt>resume</tt>, <tt>setid</tt>. See constants at {@link VideoModel.MovieFields}.
		 * @see VideoModel.MovieFields
		 */
		public GetMovies(String... properties) {
			super();
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<VideoModel.MovieDetails> parseMany(ObjectNode node) {
			final ArrayNode movies = (ArrayNode)parseResult(node).get(RESULTS);
			final ArrayList<VideoModel.MovieDetails> ret = new ArrayList<VideoModel.MovieDetails>(movies.size());
			for (int i = 0; i < movies.size(); i++) {
				final ObjectNode item = (ObjectNode)movies.get(i);
				ret.add(new VideoModel.MovieDetails(item));
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
		/**
		 * Construct via parcel
		 */
		protected GetMovies(Parcel parcel) {
			try {
				mResponse = (ObjectNode)OM.readTree(parcel.readString());
			} catch (JsonProcessingException e) {
				Log.e(NAME, "Error reading JSON object from parcel: " + e.getMessage(), e);
			} catch (IOException e) {
				Log.e(NAME, "I/O exception reading JSON object from parcel: " + e.getMessage(), e);
			}
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetMovies> CREATOR = new Parcelable.Creator<GetMovies>() {
			@Override
			public GetMovies createFromParcel(Parcel parcel) {
				return new GetMovies(parcel);
			}
			@Override
			public GetMovies[] newArray(int n) {
				return new GetMovies[n];
			}
		};
}
	/**
	 * Retrieve details about a specific music video
	 * <p/>
	 * API Name: <code>VideoLibrary.GetMusicVideoDetails</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetMusicVideoDetails extends AbstractCall<VideoModel.MusicVideoDetails> { 
		private static final String NAME = "GetMusicVideoDetails";
		public static final String RESULTS = "musicvideodetails";
		/**
		 * Retrieve details about a specific music video
		 * @param musicvideoid 
		 * @param properties One or more of: <tt>title</tt>, <tt>playcount</tt>, <tt>runtime</tt>, <tt>director</tt>, <tt>studio</tt>, <tt>year</tt>, <tt>plot</tt>, <tt>album</tt>, <tt>artist</tt>, <tt>genre</tt>, <tt>track</tt>, <tt>streamdetails</tt>, <tt>lastplayed</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>resume</tt>. See constants at {@link VideoModel.MusicVideoFields}.
		 * @see VideoModel.MusicVideoFields
		 */
		public GetMusicVideoDetails(Integer musicvideoid, String... properties) {
			super();
			addParameter("musicvideoid", musicvideoid);
			addParameter("properties", properties);
		}
		@Override
		protected VideoModel.MusicVideoDetails parseOne(ObjectNode node) {
			return new VideoModel.MusicVideoDetails((ObjectNode)parseResult(node).get(RESULTS));
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
		/**
		 * Construct via parcel
		 */
		protected GetMusicVideoDetails(Parcel parcel) {
			try {
				mResponse = (ObjectNode)OM.readTree(parcel.readString());
			} catch (JsonProcessingException e) {
				Log.e(NAME, "Error reading JSON object from parcel: " + e.getMessage(), e);
			} catch (IOException e) {
				Log.e(NAME, "I/O exception reading JSON object from parcel: " + e.getMessage(), e);
			}
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetMusicVideoDetails> CREATOR = new Parcelable.Creator<GetMusicVideoDetails>() {
			@Override
			public GetMusicVideoDetails createFromParcel(Parcel parcel) {
				return new GetMusicVideoDetails(parcel);
			}
			@Override
			public GetMusicVideoDetails[] newArray(int n) {
				return new GetMusicVideoDetails[n];
			}
		};
}
	/**
	 * Retrieve all music videos
	 * <p/>
	 * API Name: <code>VideoLibrary.GetMusicVideos</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetMusicVideos extends AbstractCall<VideoModel.MusicVideoDetails> { 
		private static final String NAME = "GetMusicVideos";
		public static final String RESULTS = "musicvideos";
		/**
		 * Retrieve all music videos
		 * @param artistid 
		 * @param albumid 
		 * @param properties One or more of: <tt>title</tt>, <tt>playcount</tt>, <tt>runtime</tt>, <tt>director</tt>, <tt>studio</tt>, <tt>year</tt>, <tt>plot</tt>, <tt>album</tt>, <tt>artist</tt>, <tt>genre</tt>, <tt>track</tt>, <tt>streamdetails</tt>, <tt>lastplayed</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>resume</tt>. See constants at {@link VideoModel.MusicVideoFields}.
		 * @see VideoModel.MusicVideoFields
		 */
		public GetMusicVideos(Integer artistid, Integer albumid, String... properties) {
			super();
			addParameter("artistid", artistid);
			addParameter("albumid", albumid);
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<VideoModel.MusicVideoDetails> parseMany(ObjectNode node) {
			final ArrayNode musicvideos = (ArrayNode)parseResult(node).get(RESULTS);
			final ArrayList<VideoModel.MusicVideoDetails> ret = new ArrayList<VideoModel.MusicVideoDetails>(musicvideos.size());
			for (int i = 0; i < musicvideos.size(); i++) {
				final ObjectNode item = (ObjectNode)musicvideos.get(i);
				ret.add(new VideoModel.MusicVideoDetails(item));
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
		/**
		 * Construct via parcel
		 */
		protected GetMusicVideos(Parcel parcel) {
			try {
				mResponse = (ObjectNode)OM.readTree(parcel.readString());
			} catch (JsonProcessingException e) {
				Log.e(NAME, "Error reading JSON object from parcel: " + e.getMessage(), e);
			} catch (IOException e) {
				Log.e(NAME, "I/O exception reading JSON object from parcel: " + e.getMessage(), e);
			}
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetMusicVideos> CREATOR = new Parcelable.Creator<GetMusicVideos>() {
			@Override
			public GetMusicVideos createFromParcel(Parcel parcel) {
				return new GetMusicVideos(parcel);
			}
			@Override
			public GetMusicVideos[] newArray(int n) {
				return new GetMusicVideos[n];
			}
		};
}
	/**
	 * Retrieve all recently added tv episodes
	 * <p/>
	 * API Name: <code>VideoLibrary.GetRecentlyAddedEpisodes</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetRecentlyAddedEpisodes extends AbstractCall<VideoModel.EpisodeDetails> { 
		private static final String NAME = "GetRecentlyAddedEpisodes";
		public static final String RESULTS = "episodes";
		/**
		 * Retrieve all recently added tv episodes
		 * @param properties One or more of: <tt>title</tt>, <tt>plot</tt>, <tt>votes</tt>, <tt>rating</tt>, <tt>writer</tt>, <tt>firstaired</tt>, <tt>playcount</tt>, <tt>runtime</tt>, <tt>director</tt>, <tt>productioncode</tt>, <tt>season</tt>, <tt>episode</tt>, <tt>originaltitle</tt>, <tt>showtitle</tt>, <tt>cast</tt>, <tt>streamdetails</tt>, <tt>lastplayed</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>resume</tt>, <tt>tvshowid</tt>. See constants at {@link VideoModel.EpisodeFields}.
		 * @see VideoModel.EpisodeFields
		 */
		public GetRecentlyAddedEpisodes(String... properties) {
			super();
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<VideoModel.EpisodeDetails> parseMany(ObjectNode node) {
			final ArrayNode episodes = (ArrayNode)parseResult(node).get(RESULTS);
			final ArrayList<VideoModel.EpisodeDetails> ret = new ArrayList<VideoModel.EpisodeDetails>(episodes.size());
			for (int i = 0; i < episodes.size(); i++) {
				final ObjectNode item = (ObjectNode)episodes.get(i);
				ret.add(new VideoModel.EpisodeDetails(item));
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
		/**
		 * Construct via parcel
		 */
		protected GetRecentlyAddedEpisodes(Parcel parcel) {
			try {
				mResponse = (ObjectNode)OM.readTree(parcel.readString());
			} catch (JsonProcessingException e) {
				Log.e(NAME, "Error reading JSON object from parcel: " + e.getMessage(), e);
			} catch (IOException e) {
				Log.e(NAME, "I/O exception reading JSON object from parcel: " + e.getMessage(), e);
			}
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetRecentlyAddedEpisodes> CREATOR = new Parcelable.Creator<GetRecentlyAddedEpisodes>() {
			@Override
			public GetRecentlyAddedEpisodes createFromParcel(Parcel parcel) {
				return new GetRecentlyAddedEpisodes(parcel);
			}
			@Override
			public GetRecentlyAddedEpisodes[] newArray(int n) {
				return new GetRecentlyAddedEpisodes[n];
			}
		};
}
	/**
	 * Retrieve all recently added movies
	 * <p/>
	 * API Name: <code>VideoLibrary.GetRecentlyAddedMovies</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetRecentlyAddedMovies extends AbstractCall<VideoModel.MovieDetails> { 
		private static final String NAME = "GetRecentlyAddedMovies";
		public static final String RESULTS = "movies";
		/**
		 * Retrieve all recently added movies
		 * @param properties One or more of: <tt>title</tt>, <tt>genre</tt>, <tt>year</tt>, <tt>rating</tt>, <tt>director</tt>, <tt>trailer</tt>, <tt>tagline</tt>, <tt>plot</tt>, <tt>plotoutline</tt>, <tt>originaltitle</tt>, <tt>lastplayed</tt>, <tt>playcount</tt>, <tt>writer</tt>, <tt>studio</tt>, <tt>mpaa</tt>, <tt>cast</tt>, <tt>country</tt>, <tt>imdbnumber</tt>, <tt>premiered</tt>, <tt>productioncode</tt>, <tt>runtime</tt>, <tt>set</tt>, <tt>showlink</tt>, <tt>streamdetails</tt>, <tt>top250</tt>, <tt>votes</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>sorttitle</tt>, <tt>resume</tt>, <tt>setid</tt>. See constants at {@link VideoModel.MovieFields}.
		 * @see VideoModel.MovieFields
		 */
		public GetRecentlyAddedMovies(String... properties) {
			super();
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<VideoModel.MovieDetails> parseMany(ObjectNode node) {
			final ArrayNode movies = (ArrayNode)parseResult(node).get(RESULTS);
			final ArrayList<VideoModel.MovieDetails> ret = new ArrayList<VideoModel.MovieDetails>(movies.size());
			for (int i = 0; i < movies.size(); i++) {
				final ObjectNode item = (ObjectNode)movies.get(i);
				ret.add(new VideoModel.MovieDetails(item));
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
		/**
		 * Construct via parcel
		 */
		protected GetRecentlyAddedMovies(Parcel parcel) {
			try {
				mResponse = (ObjectNode)OM.readTree(parcel.readString());
			} catch (JsonProcessingException e) {
				Log.e(NAME, "Error reading JSON object from parcel: " + e.getMessage(), e);
			} catch (IOException e) {
				Log.e(NAME, "I/O exception reading JSON object from parcel: " + e.getMessage(), e);
			}
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetRecentlyAddedMovies> CREATOR = new Parcelable.Creator<GetRecentlyAddedMovies>() {
			@Override
			public GetRecentlyAddedMovies createFromParcel(Parcel parcel) {
				return new GetRecentlyAddedMovies(parcel);
			}
			@Override
			public GetRecentlyAddedMovies[] newArray(int n) {
				return new GetRecentlyAddedMovies[n];
			}
		};
}
	/**
	 * Retrieve all recently added music videos
	 * <p/>
	 * API Name: <code>VideoLibrary.GetRecentlyAddedMusicVideos</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetRecentlyAddedMusicVideos extends AbstractCall<VideoModel.MusicVideoDetails> { 
		private static final String NAME = "GetRecentlyAddedMusicVideos";
		public static final String RESULTS = "musicvideos";
		/**
		 * Retrieve all recently added music videos
		 * @param properties One or more of: <tt>title</tt>, <tt>playcount</tt>, <tt>runtime</tt>, <tt>director</tt>, <tt>studio</tt>, <tt>year</tt>, <tt>plot</tt>, <tt>album</tt>, <tt>artist</tt>, <tt>genre</tt>, <tt>track</tt>, <tt>streamdetails</tt>, <tt>lastplayed</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>resume</tt>. See constants at {@link VideoModel.MusicVideoFields}.
		 * @see VideoModel.MusicVideoFields
		 */
		public GetRecentlyAddedMusicVideos(String... properties) {
			super();
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<VideoModel.MusicVideoDetails> parseMany(ObjectNode node) {
			final ArrayNode musicvideos = (ArrayNode)parseResult(node).get(RESULTS);
			final ArrayList<VideoModel.MusicVideoDetails> ret = new ArrayList<VideoModel.MusicVideoDetails>(musicvideos.size());
			for (int i = 0; i < musicvideos.size(); i++) {
				final ObjectNode item = (ObjectNode)musicvideos.get(i);
				ret.add(new VideoModel.MusicVideoDetails(item));
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
		/**
		 * Construct via parcel
		 */
		protected GetRecentlyAddedMusicVideos(Parcel parcel) {
			try {
				mResponse = (ObjectNode)OM.readTree(parcel.readString());
			} catch (JsonProcessingException e) {
				Log.e(NAME, "Error reading JSON object from parcel: " + e.getMessage(), e);
			} catch (IOException e) {
				Log.e(NAME, "I/O exception reading JSON object from parcel: " + e.getMessage(), e);
			}
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetRecentlyAddedMusicVideos> CREATOR = new Parcelable.Creator<GetRecentlyAddedMusicVideos>() {
			@Override
			public GetRecentlyAddedMusicVideos createFromParcel(Parcel parcel) {
				return new GetRecentlyAddedMusicVideos(parcel);
			}
			@Override
			public GetRecentlyAddedMusicVideos[] newArray(int n) {
				return new GetRecentlyAddedMusicVideos[n];
			}
		};
}
	/**
	 * Retrieve all tv seasons
	 * <p/>
	 * API Name: <code>VideoLibrary.GetSeasons</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetSeasons extends AbstractCall<VideoModel.SeasonDetails> { 
		private static final String NAME = "GetSeasons";
		public static final String RESULTS = "seasons";
		/**
		 * Retrieve all tv seasons
		 * @param tvshowid 
		 * @param properties One or more of: <tt>season</tt>, <tt>showtitle</tt>, <tt>playcount</tt>, <tt>episode</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>tvshowid</tt>. See constants at {@link VideoModel.SeasonFields}.
		 * @see VideoModel.SeasonFields
		 */
		public GetSeasons(Integer tvshowid, String... properties) {
			super();
			addParameter("tvshowid", tvshowid);
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<VideoModel.SeasonDetails> parseMany(ObjectNode node) {
			final ArrayNode seasons = (ArrayNode)parseResult(node).get(RESULTS);
			final ArrayList<VideoModel.SeasonDetails> ret = new ArrayList<VideoModel.SeasonDetails>(seasons.size());
			for (int i = 0; i < seasons.size(); i++) {
				final ObjectNode item = (ObjectNode)seasons.get(i);
				ret.add(new VideoModel.SeasonDetails(item));
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
		/**
		 * Construct via parcel
		 */
		protected GetSeasons(Parcel parcel) {
			try {
				mResponse = (ObjectNode)OM.readTree(parcel.readString());
			} catch (JsonProcessingException e) {
				Log.e(NAME, "Error reading JSON object from parcel: " + e.getMessage(), e);
			} catch (IOException e) {
				Log.e(NAME, "I/O exception reading JSON object from parcel: " + e.getMessage(), e);
			}
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetSeasons> CREATOR = new Parcelable.Creator<GetSeasons>() {
			@Override
			public GetSeasons createFromParcel(Parcel parcel) {
				return new GetSeasons(parcel);
			}
			@Override
			public GetSeasons[] newArray(int n) {
				return new GetSeasons[n];
			}
		};
}
	/**
	 * Retrieve details about a specific tv show
	 * <p/>
	 * API Name: <code>VideoLibrary.GetTVShowDetails</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetTVShowDetails extends AbstractCall<VideoModel.TVShowDetails> { 
		private static final String NAME = "GetTVShowDetails";
		public static final String RESULTS = "tvshowdetails";
		/**
		 * Retrieve details about a specific tv show
		 * @param tvshowid 
		 * @param properties One or more of: <tt>title</tt>, <tt>genre</tt>, <tt>year</tt>, <tt>rating</tt>, <tt>plot</tt>, <tt>studio</tt>, <tt>mpaa</tt>, <tt>cast</tt>, <tt>playcount</tt>, <tt>episode</tt>, <tt>imdbnumber</tt>, <tt>premiered</tt>, <tt>votes</tt>, <tt>lastplayed</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>originaltitle</tt>, <tt>sorttitle</tt>, <tt>episodeguide</tt>. See constants at {@link VideoModel.TVShowFields}.
		 * @see VideoModel.TVShowFields
		 */
		public GetTVShowDetails(Integer tvshowid, String... properties) {
			super();
			addParameter("tvshowid", tvshowid);
			addParameter("properties", properties);
		}
		@Override
		protected VideoModel.TVShowDetails parseOne(ObjectNode node) {
			return new VideoModel.TVShowDetails((ObjectNode)parseResult(node).get(RESULTS));
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
		/**
		 * Construct via parcel
		 */
		protected GetTVShowDetails(Parcel parcel) {
			try {
				mResponse = (ObjectNode)OM.readTree(parcel.readString());
			} catch (JsonProcessingException e) {
				Log.e(NAME, "Error reading JSON object from parcel: " + e.getMessage(), e);
			} catch (IOException e) {
				Log.e(NAME, "I/O exception reading JSON object from parcel: " + e.getMessage(), e);
			}
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetTVShowDetails> CREATOR = new Parcelable.Creator<GetTVShowDetails>() {
			@Override
			public GetTVShowDetails createFromParcel(Parcel parcel) {
				return new GetTVShowDetails(parcel);
			}
			@Override
			public GetTVShowDetails[] newArray(int n) {
				return new GetTVShowDetails[n];
			}
		};
}
	/**
	 * Retrieve all tv shows
	 * <p/>
	 * API Name: <code>VideoLibrary.GetTVShows</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetTVShows extends AbstractCall<VideoModel.TVShowDetails> { 
		private static final String NAME = "GetTVShows";
		public static final String RESULTS = "tvshows";
		/**
		 * Retrieve all tv shows
		 * @param properties One or more of: <tt>title</tt>, <tt>genre</tt>, <tt>year</tt>, <tt>rating</tt>, <tt>plot</tt>, <tt>studio</tt>, <tt>mpaa</tt>, <tt>cast</tt>, <tt>playcount</tt>, <tt>episode</tt>, <tt>imdbnumber</tt>, <tt>premiered</tt>, <tt>votes</tt>, <tt>lastplayed</tt>, <tt>fanart</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>originaltitle</tt>, <tt>sorttitle</tt>, <tt>episodeguide</tt>. See constants at {@link VideoModel.TVShowFields}.
		 * @see VideoModel.TVShowFields
		 */
		public GetTVShows(String... properties) {
			super();
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<VideoModel.TVShowDetails> parseMany(ObjectNode node) {
			final ArrayNode tvshows = (ArrayNode)parseResult(node).get(RESULTS);
			final ArrayList<VideoModel.TVShowDetails> ret = new ArrayList<VideoModel.TVShowDetails>(tvshows.size());
			for (int i = 0; i < tvshows.size(); i++) {
				final ObjectNode item = (ObjectNode)tvshows.get(i);
				ret.add(new VideoModel.TVShowDetails(item));
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
		/**
		 * Construct via parcel
		 */
		protected GetTVShows(Parcel parcel) {
			try {
				mResponse = (ObjectNode)OM.readTree(parcel.readString());
			} catch (JsonProcessingException e) {
				Log.e(NAME, "Error reading JSON object from parcel: " + e.getMessage(), e);
			} catch (IOException e) {
				Log.e(NAME, "I/O exception reading JSON object from parcel: " + e.getMessage(), e);
			}
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetTVShows> CREATOR = new Parcelable.Creator<GetTVShows>() {
			@Override
			public GetTVShows createFromParcel(Parcel parcel) {
				return new GetTVShows(parcel);
			}
			@Override
			public GetTVShows[] newArray(int n) {
				return new GetTVShows[n];
			}
		};
}
	/**
	 * Scans the video sources for new library items
	 * <p/>
	 * API Name: <code>VideoLibrary.Scan</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Scan extends AbstractCall<String> { 
		private static final String NAME = "Scan";
		/**
		 * Scans the video sources for new library items
		 */
		public Scan() {
			super();
		}
		@Override
		protected String parseOne(ObjectNode node) {
			return node.get(RESULT).getTextValue();
		}
		@Override
		public String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
		/**
		 * Construct via parcel
		 */
		protected Scan(Parcel parcel) {
			try {
				mResponse = (ObjectNode)OM.readTree(parcel.readString());
			} catch (JsonProcessingException e) {
				Log.e(NAME, "Error reading JSON object from parcel: " + e.getMessage(), e);
			} catch (IOException e) {
				Log.e(NAME, "I/O exception reading JSON object from parcel: " + e.getMessage(), e);
			}
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