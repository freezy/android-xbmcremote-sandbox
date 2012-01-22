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
import org.xbmc.android.jsonrpc.api.call.AudioLibrary;
import org.xbmc.android.jsonrpc.api.model.AudioModel;
import org.xbmc.android.jsonrpc.io.JsonHandler;
import org.xbmc.android.jsonrpc.provider.AudioContract;
import org.xbmc.android.jsonrpc.provider.AudioContract.Artists;
import org.xbmc.android.jsonrpc.provider.AudioContract.SyncColumns;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.util.Log;

/**
 * Handles one-way synchronization between XBMC's <tt>artist</tt> table and the local
 * {@link Artists} table.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class ArtistHandler extends JsonHandler {

	private final static String TAG = ArtistHandler.class.getSimpleName();
	
	public ArtistHandler() {
		super(AudioContract.CONTENT_AUTHORITY);
	}

	@Override
	protected ContentValues[] parse(JSONObject response, ContentResolver resolver)
			throws JSONException {
		Log.d(TAG, "Building queries for artist's drop and create.");

		final long now = System.currentTimeMillis();
			
		// we intentionally don't use the API for de-serializing but access the 
		// JSON objects directly for performance reasons.
		final JSONArray artists = response.getJSONObject("result").getJSONArray(AudioLibrary.GetArtists.RESULTS);
		
		final ContentValues[] batch = new ContentValues[artists.length()];
		for (int i = 0; i < artists.length(); i++) {
			final JSONObject artist = artists.getJSONObject(i);
			batch[i] = new ContentValues();
			batch[i].put(SyncColumns.UPDATED, now);
			batch[i].put(Artists.ID, artist.getString(AudioModel.ArtistDetails.ARTISTID));
			batch[i].put(Artists.NAME, artist.getString(AudioModel.ArtistDetails.ARTIST));
		}
	
		Log.d(TAG, batch.length + " artist queries built in " + (System.currentTimeMillis() - now) + "ms.");
		return batch;
	}

	@Override
	protected void insert(ContentResolver resolver, ContentValues[] batch) {
		resolver.bulkInsert(Artists.CONTENT_URI, batch);
	}
}
