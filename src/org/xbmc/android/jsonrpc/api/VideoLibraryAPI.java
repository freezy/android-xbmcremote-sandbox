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

package org.xbmc.android.jsonrpc.api;

import org.json.JSONException;
import org.json.JSONObject;
import org.xbmc.android.jsonrpc.api.FilesAPI.File;
import org.xbmc.android.jsonrpc.api.FilesAPI.OneLabelledNavigation;

/**
 * Creates request objects for the video library.
 * 
 * @deprecated
 * @author freezy <freezy@xbmc.org>
 */
public class VideoLibraryAPI extends AbstractAPI {
	
	private final static String PREFIX = "VideoLibrary.";
	
	/**
	 * Retrieve all movies.
	 * 
	 * 	Curl example:
	 * 		<code>curl -i -X POST -d '{"jsonrpc": "2.0", "method": "VideoLibrary.getMovies", "params": { "properties": [ "director", "year" ] }, "id": 1}' http://localhost:8080/jsonrpc</code>
	 * 
	 * @param fields Additional fields to return, see constants at {@link MovieFields}.
	 * @return
	 * @throws JSONException
	 */
	public JSONObject getMovies(String[] fields) throws JSONException {
		final JSONObject request = createRequest("GetAlbums");
		if (fields != null && fields.length > 0) {
			getParameters(request).put("properties", toJSONArray(fields));
		}
		return request;
	}
	
	/**
	 * Field definitions for movies.
	 * @author freezy <freezy@xbmc.org>
	 */
	public interface MovieFields {
		
		final String TITLE = "title";
		final String GENRE = "genre";
		final String YEAR = "year";
		final String RATING = "rating";
		final String DIRECTOR = "director";
		final String TRAILER = "trailer";
		final String TAGLINE = "tagline";
		final String PLOT = "plot";
		final String PLOTOUTLINE = "plotoutline";
		final String ORIGINALTITLE = "originaltitle";
		final String LASTPLAYED = "lastplayed";
		final String PLAYCOUNT = "playcount";
		final String WRITER = "writer";
		final String STUDIO = "studio";
		final String MPAA = "mpaa";
		final String CAST = "cast";
		final String COUNTRY = "country";
		final String IMDBNUMBER = "imdbnumber";
		final String PREMIERED = "premiered";
		final String PRODUCTIONCODE = "productioncode";
		final String RUNTIME = "runtime";
		final String SET = "set";
		final String SHOWLINKE = "showlink";
		final String STREAMDETAILS = "streamdetails";
		final String TOP250 = "top250";
		final String VOTES = "votes";
		final String FANART = "fanart";
		final String THUMBNAIL = "thumbnail";
		final String FILE = "file";
		final String SORTTITLE = "sorttitle";
		final String RESUME = "resume";
		final String SETTID = "setid";
	}
	
	@Override
	protected String getPrefix() {
		return PREFIX;
	}
	
	
	/**
	 * Transfer object for movies.
	 */
	public static class Movie implements OneLabelledNavigation {
		/**
		 * Label of the source
		 */
		public final String label;
		/**
		 * Absolute path of the source, can also be addon://, etc.
		 */
		public final String path;
		
		public Movie(String label, String file) {
			this.label = label;
			this.path = file;
		}
		public String toString() {
			return label + " (" + path + ")";
		}
		
		@Override
		public String getLabel() {
			return label;
		}
		@Override
		public int getType() {
			return File.FILETYPE_SOURCE;
		}
		@Override
		public String getPath() {
			return path;
		}
	}

}
