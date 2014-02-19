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

package org.xbmc.android.app.io.audio;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.xbmc.android.app.provider.AudioContract;
import org.xbmc.android.app.provider.AudioContract.Artists;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.api.call.AudioLibrary;
import org.xbmc.android.jsonrpc.api.model.AudioModel.ArtistDetail;
import org.xbmc.android.jsonrpc.io.JsonHandler;

/**
 * Handles one-way synchronization between XBMC's <tt>artist</tt> table and the local
 * {@link Artists} table.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class ArtistHandler extends JsonHandler {

	private final static String TAG = ArtistHandler.class.getSimpleName();
	private final int hostId;

	public ArtistHandler(int hostId) {
		super(AudioContract.CONTENT_AUTHORITY);
		this.hostId = hostId;
	}

	@Override
	protected ContentValues[] parse(JsonNode response, ContentResolver resolver) {
		Log.d(TAG, "Building queries for artist's drop and create.");

		final long now = System.currentTimeMillis();

		// check if array is not empty
		if (isEmptyResult(response)) {
			return new ContentValues[0];
		}

		// we intentionally don't use the API for mapping but access the
		// JSON objects directly for performance reasons.
		final ArrayNode artists = (ArrayNode)response.get(AbstractCall.RESULT).get(AudioLibrary.GetArtists.RESULT);

		final ContentValues[] batch = new ContentValues[artists.size()];
		for (int i = 0; i < artists.size(); i++) {
			final ObjectNode artist = (ObjectNode)artists.get(i);
			batch[i] = new ContentValues();
			batch[i].put(Artists.UPDATED, now);
			batch[i].put(Artists.HOST_ID, hostId);
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
	public static final Parcelable.Creator<ArtistHandler> CREATOR = new Parcelable.Creator<ArtistHandler>() {
		@Override
		public ArtistHandler createFromParcel(Parcel parcel) {
			return new ArtistHandler(parcel.readInt());
		}
		@Override
		public ArtistHandler[] newArray(int n) {
			return new ArtistHandler[n];
		}
	};

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(hostId);
	}
}
