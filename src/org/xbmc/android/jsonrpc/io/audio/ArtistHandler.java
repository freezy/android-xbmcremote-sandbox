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

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xbmc.android.jsonrpc.api.AudioLibraryAPI;
import org.xbmc.android.jsonrpc.io.JsonHandler;
import org.xbmc.android.jsonrpc.provider.AudioContract;
import org.xbmc.android.jsonrpc.provider.AudioContract.Albums;
import org.xbmc.android.jsonrpc.provider.AudioContract.Artists;
import org.xbmc.android.jsonrpc.provider.AudioContract.SyncColumns;

import android.content.ContentProviderOperation;
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
	public ArrayList<ContentProviderOperation> parse(JSONObject result, ContentResolver resolver) 
			throws JSONException, IOException {
		
		Log.d(TAG, "Building queries for artist's drop and create.");
		
		final long now = System.currentTimeMillis();
		final ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
		final JSONArray artists = result.getJSONArray("artists");
		
		// first, delete all
		batch.add(ContentProviderOperation.newDelete(Artists.CONTENT_URI).build());
//		for (int i = 0; i < 50; i++) {
		for (int i = 0; i < artists.length(); i++) {
			final JSONObject artist = artists.getJSONObject(i);
			final ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(Artists.CONTENT_URI);
			builder.withValue(SyncColumns.UPDATED, now);
			builder.withValue(Artists.ID, artist.getString("artistid"));
			builder.withValue(Artists.NAME, artist.getString("artist"));
			batch.add(builder.build());
		}
		return batch;
	}

	@Override
	public ContentValues[] newParse(JSONObject result, ContentResolver resolver)
			throws JSONException, IOException {
		Log.d(TAG, "Building queries for artist's drop and create.");
		
		final long now = System.currentTimeMillis();
		final JSONArray artists = result.getJSONArray("artists");
		
		final ContentValues[] batch = new ContentValues[artists.length()];
		
		
		for (int i = 0; i < artists.length(); i++) {
			final JSONObject artist = artists.getJSONObject(i);
			batch[i] = new ContentValues();
			batch[i].put(SyncColumns.UPDATED, now);
			batch[i].put(Artists.ID, artist.getString("artistid"));
			batch[i].put(Artists.NAME, artist.getString("artist"));
		}
		return batch;
	}

}
