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

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
		 * @throws JSONException
		 */
		public GetDirectory(String directory, String media, String... properties) throws JSONException {
			super();
			addParameter("directory", directory);
			addParameter("media", media);
			addParameter("properties", properties);
		}
		@Override
		protected ArrayList<ListModel.FileItem> parseMany(JSONObject obj) throws JSONException {
			final JSONArray files = parseResult(obj).getJSONArray(RESULTS);
			final ArrayList<ListModel.FileItem> ret = new ArrayList<ListModel.FileItem>(files.length());
			for (int i = 0; i < files.length(); i++) {
				final JSONObject item = files.getJSONObject(i);
				ret.add(new ListModel.FileItem(item));
			}
			return ret;
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return true;
		}
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
		 * @throws JSONException
		 */
		public GetSources(String media) throws JSONException {
			super();
			addParameter("media", media);
		}
		@Override
		protected ArrayList<ListModel.SourcesItem> parseMany(JSONObject obj) throws JSONException {
			final JSONArray sources = parseResult(obj).getJSONArray(RESULTS);
			final ArrayList<ListModel.SourcesItem> ret = new ArrayList<ListModel.SourcesItem>(sources.length());
			for (int i = 0; i < sources.length(); i++) {
				final JSONObject item = sources.getJSONObject(i);
				ret.add(new ListModel.SourcesItem(item));
			}
			return ret;
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return true;
		}
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
		 * @throws JSONException
		 */
		public PrepareDownload(String path) throws JSONException {
			super();
			addParameter("path", path);
		}
		@Override
		protected PrepareDownload.PrepareDownloadResult parseOne(JSONObject obj) throws JSONException {
			return new PrepareDownload.PrepareDownloadResult(parseResult(obj));
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
			public PrepareDownloadResult(JSONObject obj) throws JSONException {
				details = obj.getString(DETAILS);
				mode = obj.getString(MODE);
				protocol = obj.getString(PROTOCOL);
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
			public JSONObject toJSONObject() throws JSONException {
				final JSONObject obj = new JSONObject();
				obj.put(DETAILS, details);
				obj.put(MODE, mode);
				obj.put(PROTOCOL, protocol);
				return obj;
			}
			/**
			 * Extracts a list of {@link PrepareDownloadResult} objects from a JSON array.
			 * @param obj JSONObject containing the list of objects
			 * @param key Key pointing to the node where the list is stored
			 */
			static ArrayList<PrepareDownloadResult> getPrepareDownloadResultList(JSONObject obj, String key) throws JSONException {
				if (obj.has(key)) {
					final JSONArray a = obj.getJSONArray(key);
					final ArrayList<PrepareDownloadResult> l = new ArrayList<PrepareDownloadResult>(a.length());
					for (int i = 0; i < a.length(); i++) {
						l.add(new PrepareDownloadResult(a.getJSONObject(i)));
					}
					return l;
				}
				return new ArrayList<PrepareDownloadResult>(0);
			}
		}
		@Override
		protected String getName() {
			return PREFIX + NAME;
		}
		@Override
		protected boolean returnsList() {
			return false;
		}
	}
}