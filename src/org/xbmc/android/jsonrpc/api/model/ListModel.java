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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xbmc.android.jsonrpc.api.AbstractModel;

public final class ListModel {
	public interface AllFields {
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
		public final String DIRECTOR = "director";
		public final String TRAILER = "trailer";
		public final String TAGLINE = "tagline";
		public final String PLOT = "plot";
		public final String PLOTOUTLINE = "plotoutline";
		public final String ORIGINALTITLE = "originaltitle";
		public final String LASTPLAYED = "lastplayed";
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
		public final String FIRSTAIRED = "firstaired";
		public final String SEASON = "season";
		public final String EPISODE = "episode";
		public final String SHOWTITLE = "showtitle";
		public final String THUMBNAIL = "thumbnail";
		public final String FILE = "file";
		public final String RESUME = "resume";
		public final String ARTISTID = "artistid";
		public final String ALBUMID = "albumid";
		public final String TVSHOWID = "tvshowid";
		public final String SETID = "setid";
	}
	/**
	 * List.Item.All
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class AllItem extends VideoModel.FileDetails {
		public final static String API_TYPE = "List.Item.All";
		// field names
		public static final String ALBUM = "album";
		public static final String ALBUMARTIST = "albumartist";
		public static final String ALBUMID = "albumid";
		public static final String ARTISTID = "artistid";
		public static final String CAST = "cast";
		public static final String COMMENT = "comment";
		public static final String COUNTRY = "country";
		public static final String DURATION = "duration";
		public static final String EPISODE = "episode";
		public static final String FIRSTAIRED = "firstaired";
		public static final String ID = "id";
		public static final String IMDBNUMBER = "imdbnumber";
		public static final String LYRICS = "lyrics";
		public static final String MPAA = "mpaa";
		public static final String MUSICBRAINZARTISTID = "musicbrainzartistid";
		public static final String MUSICBRAINZTRACKID = "musicbrainztrackid";
		public static final String ORIGINALTITLE = "originaltitle";
		public static final String PLOTOUTLINE = "plotoutline";
		public static final String PREMIERED = "premiered";
		public static final String PRODUCTIONCODE = "productioncode";
		public static final String SEASON = "season";
		public static final String SET = "set";
		public static final String SETID = "setid";
		public static final String SHOWLINK = "showlink";
		public static final String SHOWTITLE = "showtitle";
		public static final String STUDIO = "studio";
		public static final String TAGLINE = "tagline";
		public static final String TOP250 = "top250";
		public static final String TRACK = "track";
		public static final String TRAILER = "trailer";
		public static final String TVSHOWID = "tvshowid";
		public static final String TYPE = "type";
		public static final String VOTES = "votes";
		public static final String WRITER = "writer";
		// class members
		public final String album;
		public final String albumartist;
		public final Integer albumid;
		public final Integer artistid;
		public final ArrayList<VideoModel.Cast> cast;
		public final String comment;
		public final String country;
		public final Integer duration;
		public final Integer episode;
		public final String firstaired;
		public final Integer id;
		public final String imdbnumber;
		public final String lyrics;
		public final String mpaa;
		public final String musicbrainzartistid;
		public final String musicbrainztrackid;
		public final String originaltitle;
		public final String plotoutline;
		public final String premiered;
		public final String productioncode;
		public final Integer season;
		public final ArrayList<String> set;
		public final ArrayList<Integer> setid;
		public final String showlink;
		public final String showtitle;
		public final String studio;
		public final String tagline;
		public final Integer top250;
		public final Integer track;
		public final String trailer;
		public final Integer tvshowid;
		/**
		 * One of: <tt>unknown</tt>, <tt>movie</tt>, <tt>episode</tt>, <tt>musicvideo</tt>, <tt>song</tt>, <tt>picture</tt>.
		 */
		public final String type;
		public final String votes;
		public final String writer;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a AllItem object
		 */
		public AllItem(JSONObject obj) throws JSONException {
			super(obj);
			mType = API_TYPE;
			album = parseString(obj, ALBUM);
			albumartist = parseString(obj, ALBUMARTIST);
			albumid = parseInt(obj, ALBUMID);
			artistid = parseInt(obj, ARTISTID);
			cast = VideoModel.Cast.getVideoModelCastList(obj, CAST);
			comment = parseString(obj, COMMENT);
			country = parseString(obj, COUNTRY);
			duration = parseInt(obj, DURATION);
			episode = parseInt(obj, EPISODE);
			firstaired = parseString(obj, FIRSTAIRED);
			id = parseInt(obj, ID);
			imdbnumber = parseString(obj, IMDBNUMBER);
			lyrics = parseString(obj, LYRICS);
			mpaa = parseString(obj, MPAA);
			musicbrainzartistid = parseString(obj, MUSICBRAINZARTISTID);
			musicbrainztrackid = parseString(obj, MUSICBRAINZTRACKID);
			originaltitle = parseString(obj, ORIGINALTITLE);
			plotoutline = parseString(obj, PLOTOUTLINE);
			premiered = parseString(obj, PREMIERED);
			productioncode = parseString(obj, PRODUCTIONCODE);
			season = parseInt(obj, SEASON);
			set = getStringArray(obj, SET);
			setid = getIntegerArray(obj, SETID);
			showlink = parseString(obj, SHOWLINK);
			showtitle = parseString(obj, SHOWTITLE);
			studio = parseString(obj, STUDIO);
			tagline = parseString(obj, TAGLINE);
			top250 = parseInt(obj, TOP250);
			track = parseInt(obj, TRACK);
			trailer = parseString(obj, TRAILER);
			tvshowid = parseInt(obj, TVSHOWID);
			type = parseString(obj, TYPE);
			votes = parseString(obj, VOTES);
			writer = parseString(obj, WRITER);
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(ALBUM, album);
			obj.put(ALBUMARTIST, albumartist);
			obj.put(ALBUMID, albumid);
			obj.put(ARTISTID, artistid);
			final JSONArray castArray = new JSONArray();
			for (VideoModel.Cast item : cast) {
				castArray.put(item.toJSONObject());
			}
			castArray.put(castArray);
			obj.put(CAST, castArray);
			obj.put(COMMENT, comment);
			obj.put(COUNTRY, country);
			obj.put(DURATION, duration);
			obj.put(EPISODE, episode);
			obj.put(FIRSTAIRED, firstaired);
			obj.put(ID, id);
			obj.put(IMDBNUMBER, imdbnumber);
			obj.put(LYRICS, lyrics);
			obj.put(MPAA, mpaa);
			obj.put(MUSICBRAINZARTISTID, musicbrainzartistid);
			obj.put(MUSICBRAINZTRACKID, musicbrainztrackid);
			obj.put(ORIGINALTITLE, originaltitle);
			obj.put(PLOTOUTLINE, plotoutline);
			obj.put(PREMIERED, premiered);
			obj.put(PRODUCTIONCODE, productioncode);
			obj.put(SEASON, season);
			obj.put(SET, set);
			obj.put(SETID, setid);
			obj.put(SHOWLINK, showlink);
			obj.put(SHOWTITLE, showtitle);
			obj.put(STUDIO, studio);
			obj.put(TAGLINE, tagline);
			obj.put(TOP250, top250);
			obj.put(TRACK, track);
			obj.put(TRAILER, trailer);
			obj.put(TVSHOWID, tvshowid);
			obj.put(TYPE, type);
			obj.put(VOTES, votes);
			obj.put(WRITER, writer);
			return obj;
		}
		/**
		 * Extracts a list of {@link ListModel.AllItem} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<ListModel.AllItem> getListModelAllItemList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<ListModel.AllItem> l = new ArrayList<ListModel.AllItem>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new ListModel.AllItem(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<ListModel.AllItem>(0);
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
			parcel.writeInt(cast.size());
			for (VideoModel.Cast item : cast) {
				parcel.writeParcelable(item, flags);
			}
			parcel.writeValue(comment);
			parcel.writeValue(country);
			parcel.writeValue(duration);
			parcel.writeValue(episode);
			parcel.writeValue(firstaired);
			parcel.writeValue(id);
			parcel.writeValue(imdbnumber);
			parcel.writeValue(lyrics);
			parcel.writeValue(mpaa);
			parcel.writeValue(musicbrainzartistid);
			parcel.writeValue(musicbrainztrackid);
			parcel.writeValue(originaltitle);
			parcel.writeValue(plotoutline);
			parcel.writeValue(premiered);
			parcel.writeValue(productioncode);
			parcel.writeValue(season);
			parcel.writeInt(set.size());
			for (String item : set) {
				parcel.writeValue(item);
			}
			parcel.writeInt(setid.size());
			for (Integer item : setid) {
				parcel.writeValue(item);
			}
			parcel.writeValue(showlink);
			parcel.writeValue(showtitle);
			parcel.writeValue(studio);
			parcel.writeValue(tagline);
			parcel.writeValue(top250);
			parcel.writeValue(track);
			parcel.writeValue(trailer);
			parcel.writeValue(tvshowid);
			parcel.writeValue(type);
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
		protected AllItem(Parcel parcel) {
			super(parcel);
			album = parcel.readString();
			albumartist = parcel.readString();
			albumid = parcel.readInt();
			artistid = parcel.readInt();
			final int castSize = parcel.readInt();
			cast = new ArrayList<VideoModel.Cast>(castSize);
			for (int i = 0; i < castSize; i++) {
				cast.add(parcel.<VideoModel.Cast>readParcelable(VideoModel.Cast.class.getClassLoader()));
			}
			comment = parcel.readString();
			country = parcel.readString();
			duration = parcel.readInt();
			episode = parcel.readInt();
			firstaired = parcel.readString();
			id = parcel.readInt();
			imdbnumber = parcel.readString();
			lyrics = parcel.readString();
			mpaa = parcel.readString();
			musicbrainzartistid = parcel.readString();
			musicbrainztrackid = parcel.readString();
			originaltitle = parcel.readString();
			plotoutline = parcel.readString();
			premiered = parcel.readString();
			productioncode = parcel.readString();
			season = parcel.readInt();
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
			showtitle = parcel.readString();
			studio = parcel.readString();
			tagline = parcel.readString();
			top250 = parcel.readInt();
			track = parcel.readInt();
			trailer = parcel.readString();
			tvshowid = parcel.readInt();
			type = parcel.readString();
			votes = parcel.readString();
			writer = parcel.readString();
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<AllItem> CREATOR = new Parcelable.Creator<AllItem>() {
			@Override
			public AllItem createFromParcel(Parcel parcel) {
				return new AllItem(parcel);
			}
			@Override
			public AllItem[] newArray(int n) {
				return new AllItem[n];
			}
		};
	}
	/**
	 * List.Item.File
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class FileItem extends ListModel.AllItem {
		public final static String API_TYPE = "List.Item.File";
		// field names
		public static final String FILE = "file";
		public static final String FILETYPE = "filetype";
		// class members
		public final String file;
		/**
		 * One of: <tt>file</tt>, <tt>directory</tt>.
		 */
		public final String filetype;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a FileItem object
		 */
		public FileItem(JSONObject obj) throws JSONException {
			super(obj);
			mType = API_TYPE;
			file = obj.getString(FILE);
			filetype = obj.getString(FILETYPE);
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(FILE, file);
			obj.put(FILETYPE, filetype);
			return obj;
		}
		/**
		 * Extracts a list of {@link ListModel.FileItem} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<ListModel.FileItem> getListModelFileItemList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<ListModel.FileItem> l = new ArrayList<ListModel.FileItem>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new ListModel.FileItem(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<ListModel.FileItem>(0);
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
			parcel.writeValue(filetype);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		* Construct via parcel
		*/
		protected FileItem(Parcel parcel) {
			super(parcel);
			file = parcel.readString();
			filetype = parcel.readString();
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<FileItem> CREATOR = new Parcelable.Creator<FileItem>() {
			@Override
			public FileItem createFromParcel(Parcel parcel) {
				return new FileItem(parcel);
			}
			@Override
			public FileItem[] newArray(int n) {
				return new FileItem[n];
			}
		};
	}
	/**
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class SourcesItem extends ItemModel.BaseDetails {
		public final static String API_TYPE = "List.Items.Sources";
		// field names
		public static final String FILE = "file";
		// class members
		public final String file;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a SourcesItem object
		 */
		public SourcesItem(JSONObject obj) throws JSONException {
			super(obj);
			mType = API_TYPE;
			file = obj.getString(FILE);
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(FILE, file);
			return obj;
		}
		/**
		 * Extracts a list of {@link ListModel.SourcesItem} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<ListModel.SourcesItem> getListModelSourcesItemList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<ListModel.SourcesItem> l = new ArrayList<ListModel.SourcesItem>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new ListModel.SourcesItem(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<ListModel.SourcesItem>(0);
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
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		* Construct via parcel
		*/
		protected SourcesItem(Parcel parcel) {
			super(parcel);
			file = parcel.readString();
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<SourcesItem> CREATOR = new Parcelable.Creator<SourcesItem>() {
			@Override
			public SourcesItem createFromParcel(Parcel parcel) {
				return new SourcesItem(parcel);
			}
			@Override
			public SourcesItem[] newArray(int n) {
				return new SourcesItem[n];
			}
		};
	}
	/**
	 * List.Limits
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Limits extends AbstractModel {
		public final static String API_TYPE = "List.Limits";
		// field names
		public static final String END = "end";
		public static final String START = "start";
		// class members
		public final Integer end;
		public final Integer start;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a Limits object
		 */
		public Limits(JSONObject obj) throws JSONException {
			mType = API_TYPE;
			end = parseInt(obj, END);
			start = parseInt(obj, START);
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param end 
		 * @param start 
		 */
		public Limits(Integer end, Integer start) {
			this.end = end;
			this.start = start;
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(END, end);
			obj.put(START, start);
			return obj;
		}
		/**
		 * Extracts a list of {@link ListModel.Limits} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<ListModel.Limits> getListModelLimitsList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<ListModel.Limits> l = new ArrayList<ListModel.Limits>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new ListModel.Limits(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<ListModel.Limits>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeValue(end);
			parcel.writeValue(start);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		* Construct via parcel
		*/
		protected Limits(Parcel parcel) {
			end = parcel.readInt();
			start = parcel.readInt();
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Limits> CREATOR = new Parcelable.Creator<Limits>() {
			@Override
			public Limits createFromParcel(Parcel parcel) {
				return new Limits(parcel);
			}
			@Override
			public Limits[] newArray(int n) {
				return new Limits[n];
			}
		};
	}
	/**
	 * List.LimitsReturned
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class LimitsReturned extends AbstractModel {
		public final static String API_TYPE = "List.LimitsReturned";
		// field names
		public static final String END = "end";
		public static final String START = "start";
		public static final String TOTAL = "total";
		// class members
		public final Integer end;
		public final Integer start;
		public final int total;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a LimitsReturned object
		 */
		public LimitsReturned(JSONObject obj) throws JSONException {
			mType = API_TYPE;
			end = parseInt(obj, END);
			start = parseInt(obj, START);
			total = obj.getInt(TOTAL);
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param end 
		 * @param start 
		 * @param total 
		 */
		public LimitsReturned(Integer end, Integer start, int total) {
			this.end = end;
			this.start = start;
			this.total = total;
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(END, end);
			obj.put(START, start);
			obj.put(TOTAL, total);
			return obj;
		}
		/**
		 * Extracts a list of {@link ListModel.LimitsReturned} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<ListModel.LimitsReturned> getListModelLimitsReturnedList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<ListModel.LimitsReturned> l = new ArrayList<ListModel.LimitsReturned>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new ListModel.LimitsReturned(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<ListModel.LimitsReturned>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeValue(end);
			parcel.writeValue(start);
			parcel.writeValue(total);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		* Construct via parcel
		*/
		protected LimitsReturned(Parcel parcel) {
			end = parcel.readInt();
			start = parcel.readInt();
			total = parcel.readInt();
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<LimitsReturned> CREATOR = new Parcelable.Creator<LimitsReturned>() {
			@Override
			public LimitsReturned createFromParcel(Parcel parcel) {
				return new LimitsReturned(parcel);
			}
			@Override
			public LimitsReturned[] newArray(int n) {
				return new LimitsReturned[n];
			}
		};
	}
	/**
	 * List.Sort
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class Sort extends AbstractModel {
		public final static String API_TYPE = "List.Sort";
		// field names
		public static final String IGNOREARTICLE = "ignorearticle";
		public static final String METHOD = "method";
		public static final String ORDER = "order";
		// class members
		public final Boolean ignorearticle;
		/**
		 * One of: <tt>none</tt>, <tt>label</tt>, <tt>date</tt>, <tt>size</tt>, <tt>file</tt>, <tt>drivetype</tt>, <tt>track</tt>, <tt>duration</tt>, <tt>title</tt>, <tt>artist</tt>, <tt>album</tt>, <tt>genre</tt>, <tt>year</tt>, <tt>videorating</tt>, <tt>programcount</tt>, <tt>playlist</tt>, <tt>episode</tt>, <tt>videotitle</tt>, <tt>sorttitle</tt>, <tt>productioncode</tt>, <tt>songrating</tt>, <tt>mpaarating</tt>, <tt>videoruntime</tt>, <tt>studio</tt>, <tt>fullpath</tt>, <tt>lastplayed</tt>, <tt>unsorted</tt>, <tt>max</tt>.
		 */
		public final String method;
		/**
		 * One of: <tt>ascending</tt>, <tt>descending</tt>.
		 */
		public final String order;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a Sort object
		 */
		public Sort(JSONObject obj) throws JSONException {
			mType = API_TYPE;
			ignorearticle = parseBoolean(obj, IGNOREARTICLE);
			method = parseString(obj, METHOD);
			order = parseString(obj, ORDER);
		}
		/**
		 * Construct object with native values for later serialization.
		 * @param ignorearticle 
		 * @param method 
		 * @param order 
		 */
		public Sort(Boolean ignorearticle, String method, String order) {
			this.ignorearticle = ignorearticle;
			this.method = method;
			this.order = order;
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(IGNOREARTICLE, ignorearticle);
			obj.put(METHOD, method);
			obj.put(ORDER, order);
			return obj;
		}
		/**
		 * Extracts a list of {@link ListModel.Sort} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<ListModel.Sort> getListModelSortList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<ListModel.Sort> l = new ArrayList<ListModel.Sort>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new ListModel.Sort(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<ListModel.Sort>(0);
		}
		/**
		 * Flatten this object into a Parcel.
		 * @param parcel the Parcel in which the object should be written
		 * @param flags additional flags about how the object should be written
		 */
		@Override
		public void writeToParcel(Parcel parcel, int flags) {
			parcel.writeValue(ignorearticle);
			parcel.writeValue(method);
			parcel.writeValue(order);
		}
		@Override
		public int describeContents() {
			return 0;
		}
		/**
		* Construct via parcel
		*/
		protected Sort(Parcel parcel) {
			ignorearticle = parcel.readInt() == 1;
			method = parcel.readString();
			order = parcel.readString();
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<Sort> CREATOR = new Parcelable.Creator<Sort>() {
			@Override
			public Sort createFromParcel(Parcel parcel) {
				return new Sort(parcel);
			}
			@Override
			public Sort[] newArray(int n) {
				return new Sort[n];
			}
		};
	}
}