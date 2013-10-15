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

package org.xbmc.android.jsonrpc.io.video;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Parcel;
import android.util.Log;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.api.call.AudioLibrary;
import org.xbmc.android.jsonrpc.api.model.AudioModel.ArtistDetail;
import org.xbmc.android.jsonrpc.io.JsonHandler;
import org.xbmc.android.jsonrpc.provider.AudioContract;
import org.xbmc.android.jsonrpc.provider.AudioContract.Artists;
import org.xbmc.android.jsonrpc.provider.AudioContract.SyncColumns;

/**
 * Handles one-way synchronization between XBMC's <tt>movie</tt> table and the local
 * {@link org.xbmc.android.jsonrpc.provider.AudioContract.Artists} table.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class MovieHandler extends JsonHandler {

	private final static String TAG = MovieHandler.class.getSimpleName();

	public MovieHandler() {
		super(AudioContract.CONTENT_AUTHORITY);
	}

	@Override
	protected ContentValues[] parse(JsonNode response, ContentResolver resolver) {
		Log.d(TAG, "Building queries for artist's drop and create.");

		final long now = System.currentTimeMillis();

		// we intentionally don't use the API for de-serializing but access the
		// JSON objects directly for performance reasons.
		final ArrayNode artists = (ArrayNode)response.get(AbstractCall.RESULT).get(AudioLibrary.GetArtists.RESULT);

		final ContentValues[] batch = new ContentValues[artists.size()];
		for (int i = 0; i < artists.size(); i++) {
			final ObjectNode artist = (ObjectNode)artists.get(i);
			batch[i] = new ContentValues();
			batch[i].put(SyncColumns.UPDATED, now);
			batch[i].put(Artists.ID, artist.get(ArtistDetail.ARTISTID).getIntValue());
			batch[i].put(Artists.NAME, artist.get(ArtistDetail.ARTIST).getTextValue());
		}

		Log.d(TAG, batch.length + " artist queries built in " + (System.currentTimeMillis() - now) + "ms.");
		return batch;
	}

	@Override
	protected void insert(ContentResolver resolver, ContentValues[] batch) {
		resolver.bulkInsert(Artists.CONTENT_URI, batch);
	}

	/**
	 * Generates instances of this Parcelable class from a Parcel.
	 */
	public static final Creator<MovieHandler> CREATOR = new Creator<MovieHandler>() {
		@Override
		public MovieHandler createFromParcel(Parcel parcel) {
			return new MovieHandler();
		}
		@Override
		public MovieHandler[] newArray(int n) {
			return new MovieHandler[n];
		}
	};
}
