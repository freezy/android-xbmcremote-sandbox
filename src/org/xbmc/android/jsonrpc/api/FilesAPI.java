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
 * @author freezy@xbmc.org
 */
public class FilesAPI extends AbstractAPI {
	
	private final static String PREFIX = "Files.";
	
	/**
	 * Gets the sources of the media windows
	 * 
	 * @param media Media type, see {@link Media} constants.
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

}
