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
import org.xbmc.android.jsonrpc.api.FilesAPI.File;
import org.xbmc.android.jsonrpc.api.FilesAPI.Source;
import org.xbmc.android.jsonrpc.io.ApiException;

import android.util.Log;

/**
 * Real time access to the Files API.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class FilesClient extends AbstractClient {

	private final static String TAG = FilesClient.class.getSimpleName();

	private final FilesAPI api = new FilesAPI();

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
	 * Returns all files of a specific folder. If nothing found, an empty
	 * list is returned.
	 *
	 * @param directory Folder to fetch items for
	 * @param errorHandler Error handler
	 * @return Folder contents or empty list if nothing found.
	 */
	public ArrayList<File> getDirectory(String directory, ErrorHandler errorHandler) {

		try {

			JSONObject request = api.getDirectory(directory);
			JSONObject result = execute(request, errorHandler);

			if (result != null) {
				final JSONArray files = result.getJSONArray("files");
				final ArrayList<File> ret = new ArrayList<File>(files.length());
				for (int i = 0; i < files.length(); i++) {
					final JSONObject f = files.getJSONObject(i);
					final File file = new File(f.getString("file"), f.getString("filetype"), f.getString("label"));
					if (f.has("type")) {
						file.setType(f.getString("type"));
					}
					ret.add(file);
				}
				return ret;
			}
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
			errorHandler.handleError(new ApiException(ApiException.JSON_EXCEPTION, e.getMessage(), e));
		}
		return new ArrayList<File>();
	}


	/**
	 * Returns all sources of a specific media type. If nothing found, an empty
	 * list is returned.
	 *
	 * @param media Media type, see constants at {@link FilesAPI.Media}.
	 * @param errorHandler Error handler
	 * @return Sources or empty list if nothing found.
	 */
	private ArrayList<Source> getSources(String media, ErrorHandler errorHandler) {

		try {

			// 1. get the request object from our API implementation
			JSONObject request = api.getSources(media);

			// 2. POST the object to XBMC's JSON-RPC API
			JSONObject result = execute(request, errorHandler);

			// 3. parse the result and unserialize the JSON object into real {@link Source} objects.
			if (result != null) {
				final JSONArray sources = result.getJSONArray("sources");
				final ArrayList<Source> ret = new ArrayList<Source>(sources.length());
				for (int i = 0; i < sources.length(); i++) {
					final JSONObject source = sources.getJSONObject(i);
					ret.add(new Source(source.getString("label"), source.getString("file")));
				}
				return ret;
			}
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
			errorHandler.handleError(new ApiException(ApiException.JSON_EXCEPTION, e.getMessage(), e));
		}
		return new ArrayList<Source>();
	}
}
