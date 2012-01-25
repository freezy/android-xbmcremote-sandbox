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
import org.xbmc.android.jsonrpc.api.JsonSerializable;

public final class VideoModel {
	/**
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Cast extends AbstractModel {
		public final static String API_TYPE = "Video.Cast";
		// field names
		public static final String NAME = "name";
		public static final String ROLE = "role";
		public static final String THUMBNAIL = "thumbnail";
		// class members
		public final String name;
		public final String role;
		public final String thumbnail;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a Cast object
		 */
		public Cast(ObjectNode node) {
			mType = API_TYPE;
			name = node.get(NAME).getTextValue();
			role = node.get(ROLE).getTextValue();
			thumbnail = parseString(node, THUMBNAIL);
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param name 
		 * @param role 
		 * @param thumbnail 
		 */
		public Cast(String name, String role, String thumbnail) {
			this.name = name;
			this.role = role;
			this.thumbnail = thumbnail;
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(NAME, name);
			node.put(ROLE, role);
			node.put(THUMBNAIL, thumbnail);
			return node;
		}
		/**
		 * Extracts a list of {@link VideoModel.Cast} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.Cast> getVideoModelCastList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<VideoModel.Cast> l = new ArrayList<VideoModel.Cast>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new VideoModel.Cast((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.Cast>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeValue(name);
			parcel.writeValue(role);
			parcel.writeValue(thumbnail);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected Cast(Parcel parcel) {
			name = parcel.readString();
			role = parcel.readString();
			thumbnail = parcel.readString();
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<Cast> CREATOR = new Parcelable.Creator<Cast>() {
			@Override
			public Cast createFromParcel(Parcel parcel) {
				return new Cast(parcel);
			}
			@Override
			public Cast[] newArray(int n) {
				return new Cast[n];
			}
		};
	}
	/**
	 * Video.Details.Base
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class BaseDetails extends MediaModel.BaseDetails {
		public final static String API_TYPE = "Video.Details.Base";
		// field names
		public static final String PLAYCOUNT = "playcount";
		// class members
		public final Integer playcount;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a BaseDetails object
		 */
		public BaseDetails(ObjectNode node) {
			super(node);
			mType = API_TYPE;
			playcount = parseInt(node, PLAYCOUNT);
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(PLAYCOUNT, playcount);
			return node;
		}
		/**
		 * Extracts a list of {@link VideoModel.BaseDetails} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.BaseDetails> getVideoModelBaseDetailsList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<VideoModel.BaseDetails> l = new ArrayList<VideoModel.BaseDetails>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new VideoModel.BaseDetails((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.BaseDetails>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(playcount);
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
			playcount = parcel.readInt();
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
	 * Video.Details.Episode
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class EpisodeDetails extends VideoModel.FileDetails {
		public final static String API_TYPE = "Video.Details.Episode";
		// field names
		public static final String CAST = "cast";
		public static final String EPISODE = "episode";
		public static final String EPISODEID = "episodeid";
		public static final String FIRSTAIRED = "firstaired";
		public static final String ORIGINALTITLE = "originaltitle";
		public static final String PRODUCTIONCODE = "productioncode";
		public static final String RATING = "rating";
		public static final String SEASON = "season";
		public static final String SHOWTITLE = "showtitle";
		public static final String TVSHOWID = "tvshowid";
		public static final String VOTES = "votes";
		public static final String WRITER = "writer";
		// class members
		public final ArrayList<VideoModel.Cast> cast;
		public final Integer episode;
		public final int episodeid;
		public final String firstaired;
		public final String originaltitle;
		public final String productioncode;
		public final Double rating;
		public final Integer season;
		public final String showtitle;
		public final Integer tvshowid;
		public final String votes;
		public final String writer;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a EpisodeDetails object
		 */
		public EpisodeDetails(ObjectNode node) {
			super(node);
			mType = API_TYPE;
			cast = VideoModel.Cast.getVideoModelCastList(node, CAST);
			episode = parseInt(node, EPISODE);
			episodeid = node.get(EPISODEID).getIntValue();
			firstaired = parseString(node, FIRSTAIRED);
			originaltitle = parseString(node, ORIGINALTITLE);
			productioncode = parseString(node, PRODUCTIONCODE);
			rating = parseDouble(node, RATING);
			season = parseInt(node, SEASON);
			showtitle = parseString(node, SHOWTITLE);
			tvshowid = parseInt(node, TVSHOWID);
			votes = parseString(node, VOTES);
			writer = parseString(node, WRITER);
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			final ArrayNode castArray = OM.createArrayNode();
			for (VideoModel.Cast item : cast) {
				castArray.add(item.toObjectNode());
			}
			node.put(CAST, castArray);
			node.put(EPISODE, episode);
			node.put(EPISODEID, episodeid);
			node.put(FIRSTAIRED, firstaired);
			node.put(ORIGINALTITLE, originaltitle);
			node.put(PRODUCTIONCODE, productioncode);
			node.put(RATING, rating);
			node.put(SEASON, season);
			node.put(SHOWTITLE, showtitle);
			node.put(TVSHOWID, tvshowid);
			node.put(VOTES, votes);
			node.put(WRITER, writer);
			return node;
		}
		/**
		 * Extracts a list of {@link VideoModel.EpisodeDetails} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.EpisodeDetails> getVideoModelEpisodeDetailsList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<VideoModel.EpisodeDetails> l = new ArrayList<VideoModel.EpisodeDetails>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new VideoModel.EpisodeDetails((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.EpisodeDetails>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeInt(cast.size());
			for (VideoModel.Cast item : cast) {
				parcel.writeParcelable(item, flags);
			}
			parcel.writeValue(episode);
			parcel.writeValue(episodeid);
			parcel.writeValue(firstaired);
			parcel.writeValue(originaltitle);
			parcel.writeValue(productioncode);
			parcel.writeValue(rating);
			parcel.writeValue(season);
			parcel.writeValue(showtitle);
			parcel.writeValue(tvshowid);
			parcel.writeValue(votes);
			parcel.writeValue(writer);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected EpisodeDetails(Parcel parcel) {
			super(parcel);
			final int castSize = parcel.readInt();
			cast = new ArrayList<VideoModel.Cast>(castSize);
			for (int i = 0; i < castSize; i++) {
				cast.add(parcel.<VideoModel.Cast>readParcelable(VideoModel.Cast.class.getClassLoader()));
			}
			episode = parcel.readInt();
			episodeid = parcel.readInt();
			firstaired = parcel.readString();
			originaltitle = parcel.readString();
			productioncode = parcel.readString();
			rating = parcel.readDouble();
			season = parcel.readInt();
			showtitle = parcel.readString();
			tvshowid = parcel.readInt();
			votes = parcel.readString();
			writer = parcel.readString();
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<EpisodeDetails> CREATOR = new Parcelable.Creator<EpisodeDetails>() {
			@Override
			public EpisodeDetails createFromParcel(Parcel parcel) {
				return new EpisodeDetails(parcel);
			}
			@Override
			public EpisodeDetails[] newArray(int n) {
				return new EpisodeDetails[n];
			}
		};
	}
	/**
	 * Video.Details.File
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class FileDetails extends VideoModel.ItemDetails {
		public final static String API_TYPE = "Video.Details.File";
		// field names
		public static final String DIRECTOR = "director";
		public static final String RESUME = "resume";
		public static final String RUNTIME = "runtime";
		public static final String STREAMDETAILS = "streamdetails";
		// class members
		public final String director;
		public final VideoModel.Resume resume;
		public final String runtime;
		public final VideoModel.Streams streamdetails;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a FileDetails object
		 */
		public FileDetails(ObjectNode node) {
			super(node);
			mType = API_TYPE;
			director = parseString(node, DIRECTOR);
			resume = node.has(RESUME) ? new VideoModel.Resume((ObjectNode)node.get(RESUME)) : null;
			runtime = parseString(node, RUNTIME);
			streamdetails = node.has(STREAMDETAILS) ? new VideoModel.Streams((ObjectNode)node.get(STREAMDETAILS)) : null;
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(DIRECTOR, director);
			node.put(RESUME, resume.toObjectNode());
			node.put(RUNTIME, runtime);
			node.put(STREAMDETAILS, streamdetails.toObjectNode());
			return node;
		}
		/**
		 * Extracts a list of {@link VideoModel.FileDetails} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.FileDetails> getVideoModelFileDetailsList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<VideoModel.FileDetails> l = new ArrayList<VideoModel.FileDetails>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new VideoModel.FileDetails((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.FileDetails>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(director);
			parcel.writeValue(resume);
			parcel.writeValue(runtime);
			parcel.writeValue(streamdetails);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected FileDetails(Parcel parcel) {
			super(parcel);
			director = parcel.readString();
			resume = parcel.<VideoModel.Resume>readParcelable(VideoModel.Resume.class.getClassLoader());
			runtime = parcel.readString();
			streamdetails = parcel.<VideoModel.Streams>readParcelable(VideoModel.Streams.class.getClassLoader());
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<FileDetails> CREATOR = new Parcelable.Creator<FileDetails>() {
			@Override
			public FileDetails createFromParcel(Parcel parcel) {
				return new FileDetails(parcel);
			}
			@Override
			public FileDetails[] newArray(int n) {
				return new FileDetails[n];
			}
		};
	}
	/**
	 * Video.Details.Item
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class ItemDetails extends VideoModel.MediaDetails {
		public final static String API_TYPE = "Video.Details.Item";
		// field names
		public static final String FILE = "file";
		public static final String LASTPLAYED = "lastplayed";
		public static final String PLOT = "plot";
		// class members
		public final String file;
		public final String lastplayed;
		public final String plot;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a ItemDetails object
		 */
		public ItemDetails(ObjectNode node) {
			super(node);
			mType = API_TYPE;
			file = parseString(node, FILE);
			lastplayed = parseString(node, LASTPLAYED);
			plot = parseString(node, PLOT);
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(FILE, file);
			node.put(LASTPLAYED, lastplayed);
			node.put(PLOT, plot);
			return node;
		}
		/**
		 * Extracts a list of {@link VideoModel.ItemDetails} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.ItemDetails> getVideoModelItemDetailsList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<VideoModel.ItemDetails> l = new ArrayList<VideoModel.ItemDetails>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new VideoModel.ItemDetails((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.ItemDetails>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(file);
			parcel.writeValue(lastplayed);
			parcel.writeValue(plot);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected ItemDetails(Parcel parcel) {
			super(parcel);
			file = parcel.readString();
			lastplayed = parcel.readString();
			plot = parcel.readString();
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<ItemDetails> CREATOR = new Parcelable.Creator<ItemDetails>() {
			@Override
			public ItemDetails createFromParcel(Parcel parcel) {
				return new ItemDetails(parcel);
			}
			@Override
			public ItemDetails[] newArray(int n) {
				return new ItemDetails[n];
			}
		};
	}
	/**
	 * Video.Details.Media
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class MediaDetails extends VideoModel.BaseDetails {
		public final static String API_TYPE = "Video.Details.Media";
		// field names
		public static final String TITLE = "title";
		// class members
		public final String title;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a MediaDetails object
		 */
		public MediaDetails(ObjectNode node) {
			super(node);
			mType = API_TYPE;
			title = parseString(node, TITLE);
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(TITLE, title);
			return node;
		}
		/**
		 * Extracts a list of {@link VideoModel.MediaDetails} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.MediaDetails> getVideoModelMediaDetailsList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<VideoModel.MediaDetails> l = new ArrayList<VideoModel.MediaDetails>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new VideoModel.MediaDetails((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.MediaDetails>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(title);
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
			title = parcel.readString();
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
	 * Video.Details.Movie
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class MovieDetails extends VideoModel.FileDetails {
		public final static String API_TYPE = "Video.Details.Movie";
		// field names
		public static final String CAST = "cast";
		public static final String COUNTRY = "country";
		public static final String GENRE = "genre";
		public static final String IMDBNUMBER = "imdbnumber";
		public static final String MOVIEID = "movieid";
		public static final String MPAA = "mpaa";
		public static final String ORIGINALTITLE = "originaltitle";
		public static final String PLOTOUTLINE = "plotoutline";
		public static final String PREMIERED = "premiered";
		public static final String PRODUCTIONCODE = "productioncode";
		public static final String RATING = "rating";
		public static final String SET = "set";
		public static final String SETID = "setid";
		public static final String SHOWLINK = "showlink";
		public static final String SORTTITLE = "sorttitle";
		public static final String STUDIO = "studio";
		public static final String TAGLINE = "tagline";
		public static final String TOP250 = "top250";
		public static final String TRAILER = "trailer";
		public static final String VOTES = "votes";
		public static final String WRITER = "writer";
		public static final String YEAR = "year";
		// class members
		public final ArrayList<VideoModel.Cast> cast;
		public final String country;
		public final String genre;
		public final String imdbnumber;
		public final int movieid;
		public final String mpaa;
		public final String originaltitle;
		public final String plotoutline;
		public final String premiered;
		public final String productioncode;
		public final Double rating;
		public final ArrayList<String> set;
		public final ArrayList<Integer> setid;
		public final String showlink;
		public final String sorttitle;
		public final String studio;
		public final String tagline;
		public final Integer top250;
		public final String trailer;
		public final String votes;
		public final String writer;
		public final Integer year;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a MovieDetails object
		 */
		public MovieDetails(ObjectNode node) {
			super(node);
			mType = API_TYPE;
			cast = VideoModel.Cast.getVideoModelCastList(node, CAST);
			country = parseString(node, COUNTRY);
			genre = parseString(node, GENRE);
			imdbnumber = parseString(node, IMDBNUMBER);
			movieid = node.get(MOVIEID).getIntValue();
			mpaa = parseString(node, MPAA);
			originaltitle = parseString(node, ORIGINALTITLE);
			plotoutline = parseString(node, PLOTOUTLINE);
			premiered = parseString(node, PREMIERED);
			productioncode = parseString(node, PRODUCTIONCODE);
			rating = parseDouble(node, RATING);
			set = getStringArray(node, SET);
			setid = getIntegerArray(node, SETID);
			showlink = parseString(node, SHOWLINK);
			sorttitle = parseString(node, SORTTITLE);
			studio = parseString(node, STUDIO);
			tagline = parseString(node, TAGLINE);
			top250 = parseInt(node, TOP250);
			trailer = parseString(node, TRAILER);
			votes = parseString(node, VOTES);
			writer = parseString(node, WRITER);
			year = parseInt(node, YEAR);
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			final ArrayNode castArray = OM.createArrayNode();
			for (VideoModel.Cast item : cast) {
				castArray.add(item.toObjectNode());
			}
			node.put(CAST, castArray);
			node.put(COUNTRY, country);
			node.put(GENRE, genre);
			node.put(IMDBNUMBER, imdbnumber);
			node.put(MOVIEID, movieid);
			node.put(MPAA, mpaa);
			node.put(ORIGINALTITLE, originaltitle);
			node.put(PLOTOUTLINE, plotoutline);
			node.put(PREMIERED, premiered);
			node.put(PRODUCTIONCODE, productioncode);
			node.put(RATING, rating);
			final ArrayNode setArray = OM.createArrayNode();
			for (String item : set) {
				setArray.add(item);
			}
			node.put(SET, setArray);
			final ArrayNode setidArray = OM.createArrayNode();
			for (Integer item : setid) {
				setidArray.add(item);
			}
			node.put(SETID, setidArray);
			node.put(SHOWLINK, showlink);
			node.put(SORTTITLE, sorttitle);
			node.put(STUDIO, studio);
			node.put(TAGLINE, tagline);
			node.put(TOP250, top250);
			node.put(TRAILER, trailer);
			node.put(VOTES, votes);
			node.put(WRITER, writer);
			node.put(YEAR, year);
			return node;
		}
		/**
		 * Extracts a list of {@link VideoModel.MovieDetails} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.MovieDetails> getVideoModelMovieDetailsList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<VideoModel.MovieDetails> l = new ArrayList<VideoModel.MovieDetails>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new VideoModel.MovieDetails((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.MovieDetails>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeInt(cast.size());
			for (VideoModel.Cast item : cast) {
				parcel.writeParcelable(item, flags);
			}
			parcel.writeValue(country);
			parcel.writeValue(genre);
			parcel.writeValue(imdbnumber);
			parcel.writeValue(movieid);
			parcel.writeValue(mpaa);
			parcel.writeValue(originaltitle);
			parcel.writeValue(plotoutline);
			parcel.writeValue(premiered);
			parcel.writeValue(productioncode);
			parcel.writeValue(rating);
			parcel.writeInt(set.size());
			for (String item : set) {
				parcel.writeValue(item);
			}
			parcel.writeInt(setid.size());
			for (Integer item : setid) {
				parcel.writeValue(item);
			}
			parcel.writeValue(showlink);
			parcel.writeValue(sorttitle);
			parcel.writeValue(studio);
			parcel.writeValue(tagline);
			parcel.writeValue(top250);
			parcel.writeValue(trailer);
			parcel.writeValue(votes);
			parcel.writeValue(writer);
			parcel.writeValue(year);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected MovieDetails(Parcel parcel) {
			super(parcel);
			final int castSize = parcel.readInt();
			cast = new ArrayList<VideoModel.Cast>(castSize);
			for (int i = 0; i < castSize; i++) {
				cast.add(parcel.<VideoModel.Cast>readParcelable(VideoModel.Cast.class.getClassLoader()));
			}
			country = parcel.readString();
			genre = parcel.readString();
			imdbnumber = parcel.readString();
			movieid = parcel.readInt();
			mpaa = parcel.readString();
			originaltitle = parcel.readString();
			plotoutline = parcel.readString();
			premiered = parcel.readString();
			productioncode = parcel.readString();
			rating = parcel.readDouble();
			final int setSize = parcel.readInt();
			set = new ArrayList<String>(setSize);
			for (int i = 0; i < setSize; i++) {
				set.add(parcel.readString());
			}
			final int setidSize = parcel.readInt();
			setid = new ArrayList<Integer>(setidSize);
			for (int i = 0; i < setidSize; i++) {
				setid.add(parcel.readInt());
			}
			showlink = parcel.readString();
			sorttitle = parcel.readString();
			studio = parcel.readString();
			tagline = parcel.readString();
			top250 = parcel.readInt();
			trailer = parcel.readString();
			votes = parcel.readString();
			writer = parcel.readString();
			year = parcel.readInt();
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<MovieDetails> CREATOR = new Parcelable.Creator<MovieDetails>() {
			@Override
			public MovieDetails createFromParcel(Parcel parcel) {
				return new MovieDetails(parcel);
			}
			@Override
			public MovieDetails[] newArray(int n) {
				return new MovieDetails[n];
			}
		};
	}
	/**
	 * Video.Details.MovieSet
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class MovieSetDetails extends VideoModel.MediaDetails {
		public final static String API_TYPE = "Video.Details.MovieSet";
		// field names
		public static final String SETID = "setid";
		// class members
		public final int setid;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a MovieSetDetails object
		 */
		public MovieSetDetails(ObjectNode node) {
			super(node);
			mType = API_TYPE;
			setid = node.get(SETID).getIntValue();
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(SETID, setid);
			return node;
		}
		/**
		 * Extracts a list of {@link VideoModel.MovieSetDetails} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.MovieSetDetails> getVideoModelMovieSetDetailsList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<VideoModel.MovieSetDetails> l = new ArrayList<VideoModel.MovieSetDetails>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new VideoModel.MovieSetDetails((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.MovieSetDetails>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(setid);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected MovieSetDetails(Parcel parcel) {
			super(parcel);
			setid = parcel.readInt();
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<MovieSetDetails> CREATOR = new Parcelable.Creator<MovieSetDetails>() {
			@Override
			public MovieSetDetails createFromParcel(Parcel parcel) {
				return new MovieSetDetails(parcel);
			}
			@Override
			public MovieSetDetails[] newArray(int n) {
				return new MovieSetDetails[n];
			}
		};
	}
	/**
	 * Video.Details.MovieSet.Extended
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class DetailsMovieSetExtended extends VideoModel.MovieSetDetails {
		public final static String API_TYPE = "Video.Details.MovieSet.Extended";
		// field names
		public static final String MOVIES = "movies";
		// class members
		public final ArrayList<VideoModel.MovieDetails> movies;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a DetailsMovieSetExtended object
		 */
		public DetailsMovieSetExtended(ObjectNode node) {
			super(node);
			mType = API_TYPE;
			movies = VideoModel.MovieDetails.getVideoModelMovieDetailsList(node, MOVIES);
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			final ArrayNode moviesArray = OM.createArrayNode();
			for (VideoModel.MovieDetails item : movies) {
				moviesArray.add(item.toObjectNode());
			}
			node.put(MOVIES, moviesArray);
			return node;
		}
		/**
		 * Extracts a list of {@link VideoModel.DetailsMovieSetExtended} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.DetailsMovieSetExtended> getVideoModelDetailsMovieSetExtendedList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<VideoModel.DetailsMovieSetExtended> l = new ArrayList<VideoModel.DetailsMovieSetExtended>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new VideoModel.DetailsMovieSetExtended((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.DetailsMovieSetExtended>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeInt(movies.size());
			for (VideoModel.MovieDetails item : movies) {
				parcel.writeParcelable(item, flags);
			}
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected DetailsMovieSetExtended(Parcel parcel) {
			super(parcel);
			final int moviesSize = parcel.readInt();
			movies = new ArrayList<VideoModel.MovieDetails>(moviesSize);
			for (int i = 0; i < moviesSize; i++) {
				movies.add(parcel.<VideoModel.MovieDetails>readParcelable(VideoModel.MovieDetails.class.getClassLoader()));
			}
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<DetailsMovieSetExtended> CREATOR = new Parcelable.Creator<DetailsMovieSetExtended>() {
			@Override
			public DetailsMovieSetExtended createFromParcel(Parcel parcel) {
				return new DetailsMovieSetExtended(parcel);
			}
			@Override
			public DetailsMovieSetExtended[] newArray(int n) {
				return new DetailsMovieSetExtended[n];
			}
		};
	}
	/**
	 * Video.Details.MusicVideo
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class MusicVideoDetails extends VideoModel.FileDetails {
		public final static String API_TYPE = "Video.Details.MusicVideo";
		// field names
		public static final String ALBUM = "album";
		public static final String ARTIST = "artist";
		public static final String GENRE = "genre";
		public static final String MUSICVIDEOID = "musicvideoid";
		public static final String STUDIO = "studio";
		public static final String TRACK = "track";
		public static final String YEAR = "year";
		// class members
		public final String album;
		public final String artist;
		public final String genre;
		public final int musicvideoid;
		public final String studio;
		public final Integer track;
		public final Integer year;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a MusicVideoDetails object
		 */
		public MusicVideoDetails(ObjectNode node) {
			super(node);
			mType = API_TYPE;
			album = parseString(node, ALBUM);
			artist = parseString(node, ARTIST);
			genre = parseString(node, GENRE);
			musicvideoid = node.get(MUSICVIDEOID).getIntValue();
			studio = parseString(node, STUDIO);
			track = parseInt(node, TRACK);
			year = parseInt(node, YEAR);
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(ALBUM, album);
			node.put(ARTIST, artist);
			node.put(GENRE, genre);
			node.put(MUSICVIDEOID, musicvideoid);
			node.put(STUDIO, studio);
			node.put(TRACK, track);
			node.put(YEAR, year);
			return node;
		}
		/**
		 * Extracts a list of {@link VideoModel.MusicVideoDetails} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.MusicVideoDetails> getVideoModelMusicVideoDetailsList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<VideoModel.MusicVideoDetails> l = new ArrayList<VideoModel.MusicVideoDetails>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new VideoModel.MusicVideoDetails((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.MusicVideoDetails>(0);
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
			parcel.writeValue(artist);
			parcel.writeValue(genre);
			parcel.writeValue(musicvideoid);
			parcel.writeValue(studio);
			parcel.writeValue(track);
			parcel.writeValue(year);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected MusicVideoDetails(Parcel parcel) {
			super(parcel);
			album = parcel.readString();
			artist = parcel.readString();
			genre = parcel.readString();
			musicvideoid = parcel.readInt();
			studio = parcel.readString();
			track = parcel.readInt();
			year = parcel.readInt();
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<MusicVideoDetails> CREATOR = new Parcelable.Creator<MusicVideoDetails>() {
			@Override
			public MusicVideoDetails createFromParcel(Parcel parcel) {
				return new MusicVideoDetails(parcel);
			}
			@Override
			public MusicVideoDetails[] newArray(int n) {
				return new MusicVideoDetails[n];
			}
		};
	}
	/**
	 * Video.Details.Season
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class SeasonDetails extends VideoModel.BaseDetails {
		public final static String API_TYPE = "Video.Details.Season";
		// field names
		public static final String EPISODE = "episode";
		public static final String SEASON = "season";
		public static final String SHOWTITLE = "showtitle";
		public static final String TVSHOWID = "tvshowid";
		// class members
		public final Integer episode;
		public final int season;
		public final String showtitle;
		public final Integer tvshowid;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a SeasonDetails object
		 */
		public SeasonDetails(ObjectNode node) {
			super(node);
			mType = API_TYPE;
			episode = parseInt(node, EPISODE);
			season = node.get(SEASON).getIntValue();
			showtitle = parseString(node, SHOWTITLE);
			tvshowid = parseInt(node, TVSHOWID);
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(EPISODE, episode);
			node.put(SEASON, season);
			node.put(SHOWTITLE, showtitle);
			node.put(TVSHOWID, tvshowid);
			return node;
		}
		/**
		 * Extracts a list of {@link VideoModel.SeasonDetails} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.SeasonDetails> getVideoModelSeasonDetailsList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<VideoModel.SeasonDetails> l = new ArrayList<VideoModel.SeasonDetails>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new VideoModel.SeasonDetails((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.SeasonDetails>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeValue(episode);
			parcel.writeValue(season);
			parcel.writeValue(showtitle);
			parcel.writeValue(tvshowid);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected SeasonDetails(Parcel parcel) {
			super(parcel);
			episode = parcel.readInt();
			season = parcel.readInt();
			showtitle = parcel.readString();
			tvshowid = parcel.readInt();
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<SeasonDetails> CREATOR = new Parcelable.Creator<SeasonDetails>() {
			@Override
			public SeasonDetails createFromParcel(Parcel parcel) {
				return new SeasonDetails(parcel);
			}
			@Override
			public SeasonDetails[] newArray(int n) {
				return new SeasonDetails[n];
			}
		};
	}
	/**
	 * Video.Details.TVShow
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class TVShowDetails extends VideoModel.ItemDetails {
		public final static String API_TYPE = "Video.Details.TVShow";
		// field names
		public static final String CAST = "cast";
		public static final String EPISODE = "episode";
		public static final String EPISODEGUIDE = "episodeguide";
		public static final String GENRE = "genre";
		public static final String IMDBNUMBER = "imdbnumber";
		public static final String MPAA = "mpaa";
		public static final String ORIGINALTITLE = "originaltitle";
		public static final String PREMIERED = "premiered";
		public static final String RATING = "rating";
		public static final String SORTTITLE = "sorttitle";
		public static final String STUDIO = "studio";
		public static final String TVSHOWID = "tvshowid";
		public static final String VOTES = "votes";
		public static final String YEAR = "year";
		// class members
		public final ArrayList<VideoModel.Cast> cast;
		public final Integer episode;
		public final String episodeguide;
		public final String genre;
		public final String imdbnumber;
		public final String mpaa;
		public final String originaltitle;
		public final String premiered;
		public final Double rating;
		public final String sorttitle;
		public final String studio;
		public final int tvshowid;
		public final String votes;
		public final Integer year;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a TVShowDetails object
		 */
		public TVShowDetails(ObjectNode node) {
			super(node);
			mType = API_TYPE;
			cast = VideoModel.Cast.getVideoModelCastList(node, CAST);
			episode = parseInt(node, EPISODE);
			episodeguide = parseString(node, EPISODEGUIDE);
			genre = parseString(node, GENRE);
			imdbnumber = parseString(node, IMDBNUMBER);
			mpaa = parseString(node, MPAA);
			originaltitle = parseString(node, ORIGINALTITLE);
			premiered = parseString(node, PREMIERED);
			rating = parseDouble(node, RATING);
			sorttitle = parseString(node, SORTTITLE);
			studio = parseString(node, STUDIO);
			tvshowid = node.get(TVSHOWID).getIntValue();
			votes = parseString(node, VOTES);
			year = parseInt(node, YEAR);
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			final ArrayNode castArray = OM.createArrayNode();
			for (VideoModel.Cast item : cast) {
				castArray.add(item.toObjectNode());
			}
			node.put(CAST, castArray);
			node.put(EPISODE, episode);
			node.put(EPISODEGUIDE, episodeguide);
			node.put(GENRE, genre);
			node.put(IMDBNUMBER, imdbnumber);
			node.put(MPAA, mpaa);
			node.put(ORIGINALTITLE, originaltitle);
			node.put(PREMIERED, premiered);
			node.put(RATING, rating);
			node.put(SORTTITLE, sorttitle);
			node.put(STUDIO, studio);
			node.put(TVSHOWID, tvshowid);
			node.put(VOTES, votes);
			node.put(YEAR, year);
			return node;
		}
		/**
		 * Extracts a list of {@link VideoModel.TVShowDetails} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.TVShowDetails> getVideoModelTVShowDetailsList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<VideoModel.TVShowDetails> l = new ArrayList<VideoModel.TVShowDetails>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new VideoModel.TVShowDetails((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.TVShowDetails>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			super.writeToParcel(parcel, flags);
			parcel.writeInt(cast.size());
			for (VideoModel.Cast item : cast) {
				parcel.writeParcelable(item, flags);
			}
			parcel.writeValue(episode);
			parcel.writeValue(episodeguide);
			parcel.writeValue(genre);
			parcel.writeValue(imdbnumber);
			parcel.writeValue(mpaa);
			parcel.writeValue(originaltitle);
			parcel.writeValue(premiered);
			parcel.writeValue(rating);
			parcel.writeValue(sorttitle);
			parcel.writeValue(studio);
			parcel.writeValue(tvshowid);
			parcel.writeValue(votes);
			parcel.writeValue(year);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected TVShowDetails(Parcel parcel) {
			super(parcel);
			final int castSize = parcel.readInt();
			cast = new ArrayList<VideoModel.Cast>(castSize);
			for (int i = 0; i < castSize; i++) {
				cast.add(parcel.<VideoModel.Cast>readParcelable(VideoModel.Cast.class.getClassLoader()));
			}
			episode = parcel.readInt();
			episodeguide = parcel.readString();
			genre = parcel.readString();
			imdbnumber = parcel.readString();
			mpaa = parcel.readString();
			originaltitle = parcel.readString();
			premiered = parcel.readString();
			rating = parcel.readDouble();
			sorttitle = parcel.readString();
			studio = parcel.readString();
			tvshowid = parcel.readInt();
			votes = parcel.readString();
			year = parcel.readInt();
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<TVShowDetails> CREATOR = new Parcelable.Creator<TVShowDetails>() {
			@Override
			public TVShowDetails createFromParcel(Parcel parcel) {
				return new TVShowDetails(parcel);
			}
			@Override
			public TVShowDetails[] newArray(int n) {
				return new TVShowDetails[n];
			}
		};
	}
	public interface EpisodeFields {
		public final String TITLE = "title";
		public final String PLOT = "plot";
		public final String VOTES = "votes";
		public final String RATING = "rating";
		public final String WRITER = "writer";
		public final String FIRSTAIRED = "firstaired";
		public final String PLAYCOUNT = "playcount";
		public final String RUNTIME = "runtime";
		public final String DIRECTOR = "director";
		public final String PRODUCTIONCODE = "productioncode";
		public final String SEASON = "season";
		public final String EPISODE = "episode";
		public final String ORIGINALTITLE = "originaltitle";
		public final String SHOWTITLE = "showtitle";
		public final String CAST = "cast";
		public final String STREAMDETAILS = "streamdetails";
		public final String LASTPLAYED = "lastplayed";
		public final String FANART = "fanart";
		public final String THUMBNAIL = "thumbnail";
		public final String FILE = "file";
		public final String RESUME = "resume";
		public final String TVSHOWID = "tvshowid";
	}
	public interface MovieFields {
		public final String TITLE = "title";
		public final String GENRE = "genre";
		public final String YEAR = "year";
		public final String RATING = "rating";
		public final String DIRECTOR = "director";
		public final String TRAILER = "trailer";
		public final String TAGLINE = "tagline";
		public final String PLOT = "plot";
		public final String PLOTOUTLINE = "plotoutline";
		public final String ORIGINALTITLE = "originaltitle";
		public final String LASTPLAYED = "lastplayed";
		public final String PLAYCOUNT = "playcount";
		public final String WRITER = "writer";
		public final String STUDIO = "studio";
		public final String MPAA = "mpaa";
		public final String CAST = "cast";
		public final String COUNTRY = "country";
		public final String IMDBNUMBER = "imdbnumber";
		public final String PREMIERED = "premiered";
		public final String PRODUCTIONCODE = "productioncode";
		public final String RUNTIME = "runtime";
		public final String SET = "set";
		public final String SHOWLINK = "showlink";
		public final String STREAMDETAILS = "streamdetails";
		public final String TOP250 = "top250";
		public final String VOTES = "votes";
		public final String FANART = "fanart";
		public final String THUMBNAIL = "thumbnail";
		public final String FILE = "file";
		public final String SORTTITLE = "sorttitle";
		public final String RESUME = "resume";
		public final String SETID = "setid";
	}
	public interface MovieSetFields {
		public final String TITLE = "title";
		public final String PLAYCOUNT = "playcount";
		public final String FANART = "fanart";
		public final String THUMBNAIL = "thumbnail";
	}
	public interface MusicVideoFields {
		public final String TITLE = "title";
		public final String PLAYCOUNT = "playcount";
		public final String RUNTIME = "runtime";
		public final String DIRECTOR = "director";
		public final String STUDIO = "studio";
		public final String YEAR = "year";
		public final String PLOT = "plot";
		public final String ALBUM = "album";
		public final String ARTIST = "artist";
		public final String GENRE = "genre";
		public final String TRACK = "track";
		public final String STREAMDETAILS = "streamdetails";
		public final String LASTPLAYED = "lastplayed";
		public final String FANART = "fanart";
		public final String THUMBNAIL = "thumbnail";
		public final String FILE = "file";
		public final String RESUME = "resume";
	}
	public interface SeasonFields {
		public final String SEASON = "season";
		public final String SHOWTITLE = "showtitle";
		public final String PLAYCOUNT = "playcount";
		public final String EPISODE = "episode";
		public final String FANART = "fanart";
		public final String THUMBNAIL = "thumbnail";
		public final String TVSHOWID = "tvshowid";
	}
	public interface TVShowFields {
		public final String TITLE = "title";
		public final String GENRE = "genre";
		public final String YEAR = "year";
		public final String RATING = "rating";
		public final String PLOT = "plot";
		public final String STUDIO = "studio";
		public final String MPAA = "mpaa";
		public final String CAST = "cast";
		public final String PLAYCOUNT = "playcount";
		public final String EPISODE = "episode";
		public final String IMDBNUMBER = "imdbnumber";
		public final String PREMIERED = "premiered";
		public final String VOTES = "votes";
		public final String LASTPLAYED = "lastplayed";
		public final String FANART = "fanart";
		public final String THUMBNAIL = "thumbnail";
		public final String FILE = "file";
		public final String ORIGINALTITLE = "originaltitle";
		public final String SORTTITLE = "sorttitle";
		public final String EPISODEGUIDE = "episodeguide";
	}
	/**
	 * Video.Resume
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Resume extends AbstractModel {
		public final static String API_TYPE = "Video.Resume";
		// field names
		public static final String POSITION = "position";
		public static final String TOTAL = "total";
		// class members
		public final Double position;
		public final Double total;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a Resume object
		 */
		public Resume(ObjectNode node) {
			mType = API_TYPE;
			position = parseDouble(node, POSITION);
			total = parseDouble(node, TOTAL);
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param position 
		 * @param total 
		 */
		public Resume(Double position, Double total) {
			this.position = position;
			this.total = total;
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			node.put(POSITION, position);
			node.put(TOTAL, total);
			return node;
		}
		/**
		 * Extracts a list of {@link VideoModel.Resume} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.Resume> getVideoModelResumeList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<VideoModel.Resume> l = new ArrayList<VideoModel.Resume>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new VideoModel.Resume((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.Resume>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeValue(position);
			parcel.writeValue(total);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected Resume(Parcel parcel) {
			position = parcel.readDouble();
			total = parcel.readDouble();
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<Resume> CREATOR = new Parcelable.Creator<Resume>() {
			@Override
			public Resume createFromParcel(Parcel parcel) {
				return new Resume(parcel);
			}
			@Override
			public Resume[] newArray(int n) {
				return new Resume[n];
			}
		};
	}
	/**
	 * Video.Streams
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Streams extends AbstractModel {
		public final static String API_TYPE = "Video.Streams";
		// field names
		public static final String AUDIO = "audio";
		public static final String SUBTITLE = "subtitle";
		public static final String VIDEO = "video";
		// class members
		public final ArrayList<Audio> audio;
		public final ArrayList<Subtitle> subtitle;
		public final ArrayList<Video> video;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a Streams object
		 */
		public Streams(ObjectNode node) {
			mType = API_TYPE;
			audio = Audio.getAudioList(node, AUDIO);
			subtitle = Subtitle.getSubtitleList(node, SUBTITLE);
			video = Video.getVideoList(node, VIDEO);
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param audio 
		 * @param subtitle 
		 * @param video 
		 */
		public Streams(ArrayList<Audio> audio, ArrayList<Subtitle> subtitle, ArrayList<Video> video) {
			this.audio = audio;
			this.subtitle = subtitle;
			this.video = video;
		}
		@Override
		public ObjectNode toObjectNode() {
			final ObjectNode node = OM.createObjectNode();
			final ArrayNode audioArray = OM.createArrayNode();
			for (Audio item : audio) {
				audioArray.add(item.toObjectNode());
			}
			node.put(AUDIO, audioArray);
			final ArrayNode subtitleArray = OM.createArrayNode();
			for (Subtitle item : subtitle) {
				subtitleArray.add(item.toObjectNode());
			}
			node.put(SUBTITLE, subtitleArray);
			final ArrayNode videoArray = OM.createArrayNode();
			for (Video item : video) {
				videoArray.add(item.toObjectNode());
			}
			node.put(VIDEO, videoArray);
			return node;
		}
		/**
		 * Extracts a list of {@link VideoModel.Streams} objects from a JSON array.
		 * @param obj ObjectNode containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<VideoModel.Streams> getVideoModelStreamsList(ObjectNode node, String key) {
			if (node.has(key)) {
				final ArrayNode a = (ArrayNode)node.get(key);
				final ArrayList<VideoModel.Streams> l = new ArrayList<VideoModel.Streams>(a.size());
				for (int i = 0; i < a.size(); i++) {
					l.add(new VideoModel.Streams((ObjectNode)a.get(i)));
				}
				return l;
			}
			return new ArrayList<VideoModel.Streams>(0);
		}
		/**
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class Audio implements JsonSerializable, Parcelable {
			// field names
			public static final String CHANNELS = "channels";
			public static final String CODEC = "codec";
			public static final String LANGUAGE = "language";
			// class members
			public final Integer channels;
			public final String codec;
			public final String language;
			/**
			 * Construct from JSON object.
			 * @param obj JSON object representing a Audio object
			 */
			public Audio(ObjectNode node) {
				channels = parseInt(node, CHANNELS);
				codec = parseString(node, CODEC);
				language = parseString(node, LANGUAGE);
			}
			/**
			 * Construct object with native values for later serialization.
			 * @param channels 
			 * @param codec 
			 * @param language 
			 */
			public Audio(Integer channels, String codec, String language) {
				this.channels = channels;
				this.codec = codec;
				this.language = language;
			}
			@Override
			public ObjectNode toObjectNode() {
				final ObjectNode node = OM.createObjectNode();
				node.put(CHANNELS, channels);
				node.put(CODEC, codec);
				node.put(LANGUAGE, language);
				return node;
			}
			/**
			 * Extracts a list of {@link Audio} objects from a JSON array.
			 * @param obj ObjectNode containing the list of objects
			 * @param key Key pointing to the node where the list is stored
			 */
			static ArrayList<Audio> getAudioList(ObjectNode node, String key) {
				if (node.has(key)) {
					final ArrayNode a = (ArrayNode)node.get(key);
					final ArrayList<Audio> l = new ArrayList<Audio>(a.size());
					for (int i = 0; i < a.size(); i++) {
						l.add(new Audio((ObjectNode)a.get(i)));
					}
					return l;
				}
				return new ArrayList<Audio>(0);
			}
			/**
			 * Flatten this object into a Parcel.
			 * @param parcel the Parcel in which the object should be written
			 * @param flags additional flags about how the object should be written
			 */
			@Override
			public void writeToParcel(Parcel parcel, int flags) {
				parcel.writeValue(channels);
				parcel.writeValue(codec);
				parcel.writeValue(language);
			}
			@Override
			public int describeContents() {
				return 0;
			}
			/**
			 * Construct via parcel
			 */
			protected Audio(Parcel parcel) {
				channels = parcel.readInt();
				codec = parcel.readString();
				language = parcel.readString();
			}
			/**
			 * Generates instances of this Parcelable class from a Parcel.
			 */
			public static final Parcelable.Creator<Audio> CREATOR = new Parcelable.Creator<Audio>() {
				@Override
				public Audio createFromParcel(Parcel parcel) {
					return new Audio(parcel);
				}
				@Override
				public Audio[] newArray(int n) {
					return new Audio[n];
				}
			};
		}
		/**
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class Subtitle implements JsonSerializable, Parcelable {
			// field names
			public static final String LANGUAGE = "language";
			// class members
			public final String language;
			/**
			 * Construct from JSON object.
			 * @param obj JSON object representing a Subtitle object
			 */
			public Subtitle(ObjectNode node) {
				language = parseString(node, LANGUAGE);
			}
			/**
			 * Construct object with native values for later serialization.
			 * @param language 
			 */
			public Subtitle(String language) {
				this.language = language;
			}
			@Override
			public ObjectNode toObjectNode() {
				final ObjectNode node = OM.createObjectNode();
				node.put(LANGUAGE, language);
				return node;
			}
			/**
			 * Extracts a list of {@link Subtitle} objects from a JSON array.
			 * @param obj ObjectNode containing the list of objects
			 * @param key Key pointing to the node where the list is stored
			 */
			static ArrayList<Subtitle> getSubtitleList(ObjectNode node, String key) {
				if (node.has(key)) {
					final ArrayNode a = (ArrayNode)node.get(key);
					final ArrayList<Subtitle> l = new ArrayList<Subtitle>(a.size());
					for (int i = 0; i < a.size(); i++) {
						l.add(new Subtitle((ObjectNode)a.get(i)));
					}
					return l;
				}
				return new ArrayList<Subtitle>(0);
			}
			/**
			 * Flatten this object into a Parcel.
			 * @param parcel the Parcel in which the object should be written
			 * @param flags additional flags about how the object should be written
			 */
			@Override
			public void writeToParcel(Parcel parcel, int flags) {
				parcel.writeValue(language);
			}
			@Override
			public int describeContents() {
				return 0;
			}
			/**
			 * Construct via parcel
			 */
			protected Subtitle(Parcel parcel) {
				language = parcel.readString();
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
		/**
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class Video implements JsonSerializable, Parcelable {
			// field names
			public static final String ASPECT = "aspect";
			public static final String CODEC = "codec";
			public static final String DURATION = "duration";
			public static final String HEIGHT = "height";
			public static final String WIDTH = "width";
			// class members
			public final Double aspect;
			public final String codec;
			public final Integer duration;
			public final Integer height;
			public final Integer width;
			/**
			 * Construct from JSON object.
			 * @param obj JSON object representing a Video object
			 */
			public Video(ObjectNode node) {
				aspect = parseDouble(node, ASPECT);
				codec = parseString(node, CODEC);
				duration = parseInt(node, DURATION);
				height = parseInt(node, HEIGHT);
				width = parseInt(node, WIDTH);
			}
			/**
			 * Construct object with native values for later serialization.
			 * @param aspect 
			 * @param codec 
			 * @param duration 
			 * @param height 
			 * @param width 
			 */
			public Video(Double aspect, String codec, Integer duration, Integer height, Integer width) {
				this.aspect = aspect;
				this.codec = codec;
				this.duration = duration;
				this.height = height;
				this.width = width;
			}
			@Override
			public ObjectNode toObjectNode() {
				final ObjectNode node = OM.createObjectNode();
				node.put(ASPECT, aspect);
				node.put(CODEC, codec);
				node.put(DURATION, duration);
				node.put(HEIGHT, height);
				node.put(WIDTH, width);
				return node;
			}
			/**
			 * Extracts a list of {@link Video} objects from a JSON array.
			 * @param obj ObjectNode containing the list of objects
			 * @param key Key pointing to the node where the list is stored
			 */
			static ArrayList<Video> getVideoList(ObjectNode node, String key) {
				if (node.has(key)) {
					final ArrayNode a = (ArrayNode)node.get(key);
					final ArrayList<Video> l = new ArrayList<Video>(a.size());
					for (int i = 0; i < a.size(); i++) {
						l.add(new Video((ObjectNode)a.get(i)));
					}
					return l;
				}
				return new ArrayList<Video>(0);
			}
			/**
			 * Flatten this object into a Parcel.
			 * @param parcel the Parcel in which the object should be written
			 * @param flags additional flags about how the object should be written
			 */
			@Override
			public void writeToParcel(Parcel parcel, int flags) {
				parcel.writeValue(aspect);
				parcel.writeValue(codec);
				parcel.writeValue(duration);
				parcel.writeValue(height);
				parcel.writeValue(width);
			}
			@Override
			public int describeContents() {
				return 0;
			}
			/**
			 * Construct via parcel
			 */
			protected Video(Parcel parcel) {
				aspect = parcel.readDouble();
				codec = parcel.readString();
				duration = parcel.readInt();
				height = parcel.readInt();
				width = parcel.readInt();
			}
			/**
			 * Generates instances of this Parcelable class from a Parcel.
			 */
			public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
				@Override
				public Video createFromParcel(Parcel parcel) {
					return new Video(parcel);
				}
				@Override
				public Video[] newArray(int n) {
					return new Video[n];
				}
			};
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeInt(audio.size());
			for (Audio item : audio) {
				parcel.writeParcelable(item, flags);
			}
			parcel.writeInt(subtitle.size());
			for (Subtitle item : subtitle) {
				parcel.writeParcelable(item, flags);
			}
			parcel.writeInt(video.size());
			for (Video item : video) {
				parcel.writeParcelable(item, flags);
			}
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		 * Construct via parcel
		 */
		protected Streams(Parcel parcel) {
			final int audioSize = parcel.readInt();
			audio = new ArrayList<Audio>(audioSize);
			for (int i = 0; i < audioSize; i++) {
				audio.add(parcel.<Audio>readParcelable(Audio.class.getClassLoader()));
			}
			final int subtitleSize = parcel.readInt();
			subtitle = new ArrayList<Subtitle>(subtitleSize);
			for (int i = 0; i < subtitleSize; i++) {
				subtitle.add(parcel.<Subtitle>readParcelable(Subtitle.class.getClassLoader()));
			}
			final int videoSize = parcel.readInt();
			video = new ArrayList<Video>(videoSize);
			for (int i = 0; i < videoSize; i++) {
				video.add(parcel.<Video>readParcelable(Video.class.getClassLoader()));
			}
		}
		/**
		 * Generates instances of this Parcelable class from a Parcel.
		 */
		public static final Parcelable.Creator<Streams> CREATOR = new Parcelable.Creator<Streams>() {
			@Override
			public Streams createFromParcel(Parcel parcel) {
				return new Streams(parcel);
			}
			@Override
			public Streams[] newArray(int n) {
				return new Streams[n];
			}
		};
	}
}