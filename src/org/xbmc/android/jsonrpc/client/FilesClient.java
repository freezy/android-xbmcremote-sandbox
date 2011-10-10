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

package org.xbmc.android.jsonrpc.client;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xbmc.android.jsonrpc.api.FilesAPI;
import org.xbmc.android.jsonrpc.api.FilesAPI.Source;

import android.util.Log;

/**
 * Real time access to the Files API.
 * 
 * @author freezy@xbmc.org
 */
public class FilesClient extends AbstractClient {
	
	private final static String TAG = FilesClient.class.getSimpleName();
	
	/**
	 * Returns all music sources.
	 * 
	 * @param errorHandler Error handler
	 * @return List of all music sources
	 */
	public ArrayList<Source> getMusicSources(ErrorHandler errorHandler) {
		return getSources(FilesAPI.Media.MUSIC, errorHandler);
	}
	
	/**
	 * Returns all sources of a specific media type.
	 * 
	 * @param media Media type, see constants at {@link FilesAPI.Media}.
	 * @param errorHandler Error handler
	 * @return
	 */
	private ArrayList<Source> getSources(String media, ErrorHandler errorHandler) {
		final FilesAPI api = new FilesAPI();
		try {
			
			JSONObject request = api.getSources(media);
			JSONObject result = execute(request, errorHandler);
			
			if (result != null) {
				final JSONArray sources = result.getJSONArray("shares");
				final ArrayList<Source> ret = new ArrayList<Source>(sources.length());
				for (int i = 0; i < sources.length(); i++) {
					final JSONObject source = sources.getJSONObject(i);
					ret.add(new Source(source.getString("label"), source.getString("file")));
				}
				return ret;
			}
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
			errorHandler.handleError(ErrorHandler.JSON_EXCEPTION, e.getMessage());
		}
		return new ArrayList<Source>();
	}
	
}
