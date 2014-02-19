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
import org.xbmc.android.app.provider.AudioContract.Albums;
import org.xbmc.android.app.provider.AudioContract.Artists;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.api.call.AudioLibrary;
import org.xbmc.android.jsonrpc.api.model.AudioModel;
import org.xbmc.android.jsonrpc.io.JsonHandler;
import org.xbmc.android.util.DBUtils;

/**
 * Handles one-way synchronization between XBMC's <tt>album</tt> table and the local
 * {@link Albums} table.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class AlbumHandler extends JsonHandler {

	private final static String TAG = AlbumHandler.class.getSimpleName();

	private final int hostId;

	public AlbumHandler(int hostId) {
		super(AudioContract.CONTENT_AUTHORITY);
		this.hostId = hostId;
	}

	@Override
	protected ContentValues[] parse(JsonNode response, ContentResolver resolver) {

		final long now = System.currentTimeMillis();
		Log.d(TAG, "Building queries for album's drop and create.");

		// check if array is valid
		if (isEmptyResult(response)) {
			return new ContentValues[0];
		}

		// we intentionally don't use the API for mapping but access the
		// JSON objects directly for performance reasons.
		final ArrayNode albums = (ArrayNode)response.get(AbstractCall.RESULT).get(AudioLibrary.GetAlbums.RESULT);

		final ContentValues[] batch = new ContentValues[albums.size()];
		for (int i = 0; i < albums.size(); i++) {
			final ObjectNode album = (ObjectNode)albums.get(i);
			batch[i] = new ContentValues();
			batch[i].put(Albums.UPDATED, now);
			batch[i].put(Albums.HOST_ID, hostId);
			batch[i].put(Albums.ID, album.get(AudioModel.AlbumDetail.ALBUMID).getIntValue());
			batch[i].put(Albums.TITLE, album.get(AudioModel.AlbumDetail.TITLE).getTextValue());
			batch[i].put(Albums.PREFIX + Artists.ID, DBUtils.getIntValue(album, AudioModel.AlbumDetail.ARTISTID));
			batch[i].put(Albums.YEAR, album.get(AudioModel.AlbumDetail.YEAR).getIntValue());
			batch[i].put(Albums.THUMBNAIL, album.get(AudioModel.AlbumDetail.THUMBNAIL).getTextValue());
		}
		Log.d(TAG, batch.length + " album queries built in " + (System.currentTimeMillis() - now) + "ms.");
		return batch;
	}

	@Override
	protected void insert(ContentResolver resolver, ContentValues[] batch) {
		resolver.bulkInsert(Albums.CONTENT_URI, batch);
	}

	/**
	 * Generates instances of this Parcelable class from a Parcel.
	 */
	public static final Parcelable.Creator<AlbumHandler> CREATOR = new Parcelable.Creator<AlbumHandler>() {
		@Override
		public AlbumHandler createFromParcel(Parcel parcel) {
			return new AlbumHandler(parcel.readInt());
		}
		@Override
		public AlbumHandler[] newArray(int n) {
			return new AlbumHandler[n];
		}
	};

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(hostId);
	}
}
