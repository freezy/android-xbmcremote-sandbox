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

package org.xbmc.android.jsonrpc.io.audio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.api.AbstractModel;
import org.xbmc.android.jsonrpc.api.call.AudioLibrary;
import org.xbmc.android.jsonrpc.api.model.AudioModel;
import org.xbmc.android.jsonrpc.io.JsonHandler;
import org.xbmc.android.jsonrpc.provider.AudioContract;
import org.xbmc.android.jsonrpc.provider.AudioContract.Albums;
import org.xbmc.android.jsonrpc.provider.AudioContract.Artists;
import org.xbmc.android.jsonrpc.provider.AudioContract.SyncColumns;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.util.Log;

/**
 * Handles one-way synchronization between XBMC's <tt>album</tt> table and the local
 * {@link Albums} table.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class AlbumHandler extends JsonHandler {

	private final static String TAG = AlbumHandler.class.getSimpleName();

	public AlbumHandler() {
		super(AudioContract.CONTENT_AUTHORITY);
	}

	@Override
	public ContentValues[] parse(JSONObject result, ContentResolver resolver) throws JSONException {
		
		final long now = System.currentTimeMillis();
		Log.d(TAG, "Building queries for album's drop and create.");
		
		// we intentionally don't use the API for de-serializing but access the 
		// JSON objects directly for performance reasons.
		final JSONArray albums = result.getJSONObject(AbstractCall.RESULT).getJSONArray(AudioLibrary.GetAlbums.RESULTS);

		final ContentValues[] batch = new ContentValues[albums.length()];
		for (int i = 0; i < albums.length(); i++) {
			final JSONObject album = albums.getJSONObject(i);
			batch[i] = new ContentValues();
			batch[i].put(SyncColumns.UPDATED, now);
			batch[i].put(Albums.ID, album.getString(AudioModel.AlbumDetails.ALBUMID));
			batch[i].put(Albums.TITLE, album.getString(AudioModel.AlbumDetails.TITLE));
			batch[i].put(Albums.PREFIX + Artists.ID, album.getString(AudioModel.AlbumDetails.ARTISTID));
			batch[i].put(Albums.YEAR, album.getString(AudioModel.AlbumDetails.YEAR));
		}
		Log.d(TAG, "Album queries built in " + (System.currentTimeMillis() - now) + "ms.");
		return batch;
	}

}
