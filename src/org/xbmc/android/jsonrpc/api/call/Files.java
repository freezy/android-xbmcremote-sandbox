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
import org.xbmc.android.jsonrpc.api.model.ListModel;

public final class Files {

	private final static String PREFIX = "Files.";

	/**
	 * Get the directories and files in the given directory
	 * <p/>
	 * API Name: <code>Files.GetDirectory</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetDirectory extends AbstractCall<ListModel.FileItem> { 
		private static final String NAME = "GetDirectory";
		public static final String RESULTS = "files";
		/**
		 * Get the directories and files in the given directory
		 * @param directory 
		 * @param media One of: <tt>video</tt>, <tt>music</tt>, <tt>pictures</tt>, <tt>files</tt>, <tt>programs</tt>. See constants at {@link FilesModel.Media}.
		 * @param properties One or more of: <tt>title</tt>, <tt>artist</tt>, <tt>albumartist</tt>, <tt>genre</tt>, <tt>year</tt>, <tt>rating</tt>, <tt>album</tt>, <tt>track</tt>, <tt>duration</tt>, <tt>comment</tt>, <tt>lyrics</tt>, <tt>musicbrainztrackid</tt>, <tt>musicbrainzartistid</tt>, <tt>musicbrainzalbumid</tt>, <tt>musicbrainzalbumartistid</tt>, <tt>playcount</tt>, <tt>fanart</tt>, <tt>director</tt>, <tt>trailer</tt>, <tt>tagline</tt>, <tt>plot</tt>, <tt>plotoutline</tt>, <tt>originaltitle</tt>, <tt>lastplayed</tt>, <tt>writer</tt>, <tt>studio</tt>, <tt>mpaa</tt>, <tt>cast</tt>, <tt>country</tt>, <tt>imdbnumber</tt>, <tt>premiered</tt>, <tt>productioncode</tt>, <tt>runtime</tt>, <tt>set</tt>, <tt>showlink</tt>, <tt>streamdetails</tt>, <tt>top250</tt>, <tt>votes</tt>, <tt>firstaired</tt>, <tt>season</tt>, <tt>episode</tt>, <tt>showtitle</tt>, <tt>thumbnail</tt>, <tt>file</tt>, <tt>resume</tt>, <tt>artistid</tt>, <tt>albumid</tt>, <tt>tvshowid</tt>, <tt>setid</tt>. See constants at {@link ListModel.AllFields}.
		 * @see FilesModel.Media
		 * @see ListModel.AllFields
		 */
		public GetDirectory(String directory, String media, String... properties) {
			super();
			addParameter("directory", directory);
			addParameter("media", media);
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<ListModel.FileItem> parseMany(ObjectNode node) {
			final ArrayNode files = (ArrayNode)parseResult(node).get(RESULTS);
			final ArrayList<ListModel.FileItem> ret = new ArrayList<ListModel.FileItem>(files.size());
			for (int i = 0; i < files.size(); i++) {
				final ObjectNode item = (ObjectNode)files.get(i);
				ret.add(new ListModel.FileItem(item));
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
		final ArrayList<ListModel.FileItem> results = mResults;
		if (results != null && results.size() > 0) {
			parcel.writeInt(results.size());
			for (ListModel.FileItem result : results) {
				parcel.writeParcelable(result, flags);
			}
		} else {
			parcel.writeInt(0);
		}
		}
		/**
		 * Construct via parcel
		 */
		protected GetDirectory(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetDirectory> CREATOR = new Parcelable.Creator<GetDirectory>() {
			@Override
			public GetDirectory createFromParcel(Parcel parcel) {
				return new GetDirectory(parcel);
			}
			@Override
			public GetDirectory[] newArray(int n) {
				return new GetDirectory[n];
			}
		};
}
	/**
	 * Get the sources of the media windows
	 * <p/>
	 * API Name: <code>Files.GetSources</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GetSources extends AbstractCall<ListModel.SourcesItem> { 
		private static final String NAME = "GetSources";
		public static final String RESULTS = "sources";
		/**
		 * Get the sources of the media windows
		 * @param media One of: <tt>video</tt>, <tt>music</tt>, <tt>pictures</tt>, <tt>files</tt>, <tt>programs</tt>. See constants at {@link FilesModel.Media}.
		 * @see FilesModel.Media
		 */
		public GetSources(String media) {
			super();
			addParameter("media", media);
		}
		@Override
		protected ArrayList<ListModel.SourcesItem> parseMany(ObjectNode node) {
			final ArrayNode sources = (ArrayNode)parseResult(node).get(RESULTS);
			final ArrayList<ListModel.SourcesItem> ret = new ArrayList<ListModel.SourcesItem>(sources.size());
			for (int i = 0; i < sources.size(); i++) {
				final ObjectNode item = (ObjectNode)sources.get(i);
				ret.add(new ListModel.SourcesItem(item));
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
		final ArrayList<ListModel.SourcesItem> results = mResults;
		if (results != null && results.size() > 0) {
			parcel.writeInt(results.size());
			for (ListModel.SourcesItem result : results) {
				parcel.writeParcelable(result, flags);
			}
		} else {
			parcel.writeInt(0);
		}
		}
		/**
		 * Construct via parcel
		 */
		protected GetSources(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<GetSources> CREATOR = new Parcelable.Creator<GetSources>() {
			@Override
			public GetSources createFromParcel(Parcel parcel) {
				return new GetSources(parcel);
			}
			@Override
			public GetSources[] newArray(int n) {
				return new GetSources[n];
			}
		};
}
	/**
	 * Provides a way to download a given file (e.g. providing an URL to the real file location)
	 * <p/>
	 * API Name: <code>Files.PrepareDownload</code>
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class PrepareDownload extends AbstractCall<PrepareDownload.PrepareDownloadResult> { 
		private static final String NAME = "PrepareDownload";
		/**
		 * Provides a way to download a given file (e.g. providing an URL to the real file location)
		 * @param path 
		 */
		public PrepareDownload(String path) {
			super();
			addParameter("path", path);
		}
		@Override
		protected PrepareDownload.PrepareDownloadResult parseOne(ObjectNode node) {
			return new PrepareDownload.PrepareDownloadResult((ObjectNode)parseResult(node));
		}
		/**
		 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
		 */
		public static class PrepareDownloadResult extends AbstractModel {
			// field names
			public static final String DETAILS = "details";
			public static final String MODE = "mode";
			public static final String PROTOCOL = "protocol";
			// class members
			/**
			 * Transport specific details on how/from where to download the given file.
			 */
			public final String details;
			/**
			 * Direct mode allows using Files.Download whereas redirect mode requires the usage of a different protocol.
			 * One of: <tt>redirect</tt>, <tt>direct</tt>.
			 */
			public final String mode;
			/**
			 * One of: <tt>http</tt>.
			 */
			public final String protocol;
			/**
			 * Construct from JSON object.
			 * @param obj JSON object representing a PrepareDownloadResult object
			 */
			public PrepareDownloadResult(ObjectNode node) {
				details = node.get(DETAILS).getTextValue();
				mode = node.get(MODE).getTextValue();
				protocol = node.get(PROTOCOL).getTextValue();
			}
			/**
			 * Construct object with native values for later serialization.
			 * @param details Transport specific details on how/from where to download the given file 
			 * @param mode Direct mode allows using Files.Download whereas redirect mode requires the usage of a different protocol 
			 * @param protocol 
			 */
			public PrepareDownloadResult(String details, String mode, String protocol) {
				this.details = details;
				this.mode = mode;
				this.protocol = protocol;
			}
			@Override
			public ObjectNode toObjectNode() {
				final ObjectNode node = OM.createObjectNode();
				node.put(DETAILS, details);
				node.put(MODE, mode);
				node.put(PROTOCOL, protocol);
				return node;
			}
			/**
			 * Extracts a list of {@link PrepareDownloadResult} objects from a JSON array.
			 * @param obj ObjectNode containing the list of objects
			 * @param key Key pointing to the node where the list is stored
			 */
			static ArrayList<PrepareDownloadResult> getPrepareDownloadResultList(ObjectNode node, String key) {
				if (node.has(key)) {
					final ArrayNode a = (ArrayNode)node.get(key);
					final ArrayList<PrepareDownloadResult> l = new ArrayList<PrepareDownloadResult>(a.size());
					for (int i = 0; i < a.size(); i++) {
						l.add(new PrepareDownloadResult((ObjectNode)a.get(i)));
					}
					return l;
				}
				return new ArrayList<PrepareDownloadResult>(0);
			}
			/**
			 * Flatten this object into a Parcel.
			 * @param parcel the Parcel in which the object should be written
			 * @param flags additional flags about how the object should be written
			 */
			@Override
			public void writeToParcel(Parcel parcel, int flags) {
				parcel.writeValue(details);
				parcel.writeValue(mode);
				parcel.writeValue(protocol);
			}
			@Override
			public int describeContents() {
				return 0;
			}
			/**
			 * Construct via parcel
			 */
			protected PrepareDownloadResult(Parcel parcel) {
				details = parcel.readString();
				mode = parcel.readString();
				protocol = parcel.readString();
			}
			/**
			 * Generates instances of this Parcelable class from a Parcel.
			 */
			public static final Parcelable.Creator<PrepareDownloadResult> CREATOR = new Parcelable.Creator<PrepareDownloadResult>() {
				@Override
				public PrepareDownloadResult createFromParcel(Parcel parcel) {
					return new PrepareDownloadResult(parcel);
				}
				@Override
				public PrepareDownloadResult[] newArray(int n) {
					return new PrepareDownloadResult[n];
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
			parcel.writeParcelable(mResult, flags);
		}
		/**
		 * Construct via parcel
		 */
		protected PrepareDownload(Parcel parcel) {
			super(parcel);
		}
		/**
		* Generates instances of this Parcelable class from a Parcel.
		*/
		public static final Parcelable.Creator<PrepareDownload> CREATOR = new Parcelable.Creator<PrepareDownload>() {
			@Override
			public PrepareDownload createFromParcel(Parcel parcel) {
				return new PrepareDownload(parcel);
			}
			@Override
			public PrepareDownload[] newArray(int n) {
				return new PrepareDownload[n];
			}
		};
}
}