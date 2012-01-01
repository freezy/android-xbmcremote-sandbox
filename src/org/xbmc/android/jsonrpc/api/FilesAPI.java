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

/**
 * Creates request objects for the files API.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public class FilesAPI extends AbstractAPI {
	
	private final static String PREFIX = "Files.";
	
	/**
	 * Gets the sources of the media windows.
	 * 
	 * Curl example:
	 * 		<code>curl -i -X POST -d '{"jsonrpc": "2.0", "method": "Files.GetSources", "params": { "media": "music"}, "id": 1}' http://localhost:8080/jsonrpc</code>
	 * 
	 * @param media Media type, see {@link Media} constants. If null, defaults to <tt>video</tt>.
	 */
	public JSONObject getSources(String media) throws JSONException {
		final JSONObject request = createRequest("GetSources");
		if (media == null) {
			media = Media.VIDEO;
		}
		getParameters(request).put("media", media);
		return request;
	}

	/**
	 * Returns the contents of a directory.
	 * 
	 * Curl example:
	 * 		<code>curl -i -X POST -d '{"jsonrpc": "2.0", "method": "Files.GetSources", "params": { "directory": "V:\\mp3\\archive\\"}, "id": 1}' http://localhost:8080/jsonrpc</code>
	 * 
	 * @param directory Which directory to list
	 */
	public JSONObject getDirectory(String directory) throws JSONException {
		final JSONObject request = createRequest("GetSources");
		if (directory == null || directory.isEmpty()) {
			throw new IllegalArgumentException("Directory parameter must not be null or empty.");
		}
		getParameters(request).put("directory", directory);
		return request;
	}

	/**
	 * Media types
	 */
	public interface Media {
		final String VIDEO = "video";
		final String MUSIC = "music";
		final String PICTURES = "pictures";
		final String FILES = "files";
		final String PROGRAMS = "programs";
	}

	@Override
	protected String getPrefix() {
		return PREFIX;
	}
	
	
	/**
	 * Transfer object for sources.
	 */
	public static class Source implements OneLabelledNavigation {
		/**
		 * Label of the source
		 */
		public final String label;
		/**
		 * Absolute path of the source, can also be addon://, etc.
		 */
		public final String path;
		public Source(String label, String file) {
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
	
	/**
	 * Transfer object for files and folders
	 */
	public static class File implements OneLabelledNavigation {
		
		public final String path; // required
		public final int filetype; // required
		public final String label; // required
		
		public int type = TYPE_UNKNOWN;
		
		public final static int FILETYPE_UNKNOWN = -1;
		public final static int FILETYPE_SOURCE = 0;
		public final static int FILETYPE_FILE = 1;
		public final static int FILETYPE_DIRECTORY = 2;
		
		public final static int TYPE_UNKNOWN = -1;
		public final static int TYPE_MOVIE = 1;
		public final static int TYPE_EPISODE = 2;
		public final static int TYPE_MUSICVIDEO = 3;
		public final static int TYPE_SONG = 4;
		
		public File(String path, String filetype, String label) {
			this.path = path;
			this.filetype = parseFiletype(filetype);
			this.label = label;
		}
		
		public void setType(String type) {
			this.type = parseType(type);
		}
		
		private static int parseFiletype(String filetype) {
			if (filetype == null || filetype.isEmpty()) {
				return FILETYPE_UNKNOWN;
			}
			if (filetype.equals("file")) {
				return FILETYPE_FILE;
			}
			if (filetype.equals("directory")) {
				return FILETYPE_DIRECTORY;
			}
			return FILETYPE_UNKNOWN;
		}
		
		private static int parseType(String type) {
			if (type == null || type.isEmpty()) {
				return TYPE_UNKNOWN;
			}
			if (type.equals("movie")) {
				return TYPE_MOVIE;
			}
			if (type.equals("episode")) {
				return TYPE_EPISODE;
			}
			if (type.equals("musicvideo")) {
				return TYPE_MUSICVIDEO;
			}
			if (type.equals("song")) {
				return TYPE_SONG;
			}
			return TYPE_UNKNOWN;
		}

		@Override
		public String getLabel() {
			return label;
		}

		@Override
		public int getType() {
			return filetype == FILETYPE_FILE ? type : filetype;
		}

		@Override
		public String getPath() {
			return path;
		}
	}
	
	public interface OneLabelledNavigation {
		public String getLabel();
		public int getType();
		public String getPath();
	}

}
