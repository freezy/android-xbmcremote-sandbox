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

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class LibraryModel {
	/**
	 * Library.Details.Genre
	 * <p/>
	 * <i>This class was generated automatically from XBMC's JSON-RPC introspect.</i>
	 */
	public static class GenreDetails extends ItemModel.BaseDetails {
		public final static String API_TYPE = "Library.Details.Genre";
		// field names
		public static final String GENREID = "genreid";
		public static final String THUMBNAIL = "thumbnail";
		public static final String TITLE = "title";
		// class members
		public final int genreid;
		public final String thumbnail;
		public final String title;
		/**
		 * Construct from JSON object.
		 * @param obj JSON object representing a GenreDetails object
		 */
		public GenreDetails(JSONObject obj) throws JSONException {
			super(obj);
			mType = API_TYPE;
			genreid = obj.getInt(GENREID);
			thumbnail = parseString(obj, THUMBNAIL);
			title = parseString(obj, TITLE);
		}
		@Override
		public JSONObject toJSONObject() throws JSONException {
			final JSONObject obj = new JSONObject();
			obj.put(GENREID, genreid);
			obj.put(THUMBNAIL, thumbnail);
			obj.put(TITLE, title);
			return obj;
		}
		/**
		 * Extracts a list of {@link LibraryModel.GenreDetails} objects from a JSON array.
		 * @param obj JSONObject containing the list of objects
		 * @param key Key pointing to the node where the list is stored
		 */
		static ArrayList<LibraryModel.GenreDetails> getLibraryModelGenreDetailsList(JSONObject obj, String key) throws JSONException {
			if (obj.has(key)) {
				final JSONArray a = obj.getJSONArray(key);
				final ArrayList<LibraryModel.GenreDetails> l = new ArrayList<LibraryModel.GenreDetails>(a.length());
				for (int i = 0; i < a.length(); i++) {
					l.add(new LibraryModel.GenreDetails(a.getJSONObject(i)));
				}
				return l;
			}
			return new ArrayList<LibraryModel.GenreDetails>(0);
		}
	}
	public interface GenreFields {
		public final String TITLE = "title";
		public final String THUMBNAIL = "thumbnail";
	}
}