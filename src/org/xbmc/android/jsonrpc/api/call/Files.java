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
import org.xbmc.android.jsonrpc.api.FilesAPI.Media;
import org.xbmc.android.jsonrpc.api.model.FilesModel;
import org.xbmc.android.jsonrpc.api.model.ListModel;
import org.xbmc.android.jsonrpc.api.model.ListModel.SourceItem;

/**
 * Implements JSON-RPC API methods for the "Files" namespace.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public final class Files {
	
	private final static String PREFIX = "Files.";
	
	/**
	 * Returns all sources of a specific media type. If nothing found, an empty
	 * list is returned.
	 */
	public static class GetSources extends AbstractCall<ListModel.SourceItem> {
		private static final String NAME = "GetSources";
		/**
		 * Class constructor takes API method's arguments.
		 * @param media Media type, see constants at {@link FilesModel.Media}.
		 * @throws JSONException
		 */
		public GetSources(String media) throws JSONException {
			super();
			if (media == null) {
				media = Media.VIDEO;
			}
			addParameter("media", media);
		}
		@Override
		protected ArrayList<SourceItem> parseMany(JSONObject obj) throws JSONException {
			final JSONArray sources = obj.getJSONArray("sources");
			final ArrayList<ListModel.SourceItem> ret = new ArrayList<ListModel.SourceItem>(sources.length());
			for (int i = 0; i < sources.length(); i++) {
				final JSONObject source = sources.getJSONObject(i);
				ret.add(new ListModel.SourceItem(source));
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
}
