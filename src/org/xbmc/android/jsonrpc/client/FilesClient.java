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

import android.util.Log;

/**
 * Real time access to the Files API.
 * 
 * @author freezy@xbmc.org
 */
public class FilesClient extends AbstractClient {
	
	private final static String TAG = FilesClient.class.getSimpleName();
	
	public void getMusicSources(final SourcesResponseHandler handler, final ErrorHandler errorHandler) {
		final FilesAPI api = new FilesAPI();
		try {
			JSONObject request = api.getSources(FilesAPI.Media.MUSIC);
			execute(request, new HttpHandler() {
				@Override
				public void handle(JSONObject result) {
					try {
						final JSONArray sources = result.getJSONArray("shares");
						final ArrayList<Source> ret = new ArrayList<Source>(sources.length());
						for (int i = 0; i < sources.length(); i++) {
							final JSONObject source = sources.getJSONObject(i);
							ret.add(new Source(source.getString("label"), source.getString("file")));
						}
						handler.handleResponse(ret);
					} catch (JSONException e) {
						Log.e(TAG, e.getMessage(), e);
						errorHandler.handleError(ErrorHandler.JSON_EXCEPTION, e.getMessage());
					}
				}
			}, errorHandler);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Whenever sources are returned, a handler implementing this interface must be provided.
	 */
	public static interface SourcesResponseHandler {
		public void handleResponse(ArrayList<Source> result);
	}
	
	/**
	 * Transfer object for sources.
	 */
	public static class Source {
		/**
		 * Label of the source
		 */
		public final String label;
		/**
		 * Absolute path of the source, can also be addon://, etc.
		 */
		public final String file;
		public Source(String label, String file) {
			this.label = label;
			this.file = file;
		}
		public String toString() {
			return label + " (" + file + ")";
		}
	}

}
