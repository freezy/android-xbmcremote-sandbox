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

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.api.call.AudioLibrary;
import org.xbmc.android.jsonrpc.api.model.AudioModel;
import org.xbmc.android.jsonrpc.io.JsonHandler;
import org.xbmc.android.jsonrpc.provider.AudioContract;
import org.xbmc.android.jsonrpc.provider.AudioContract.Albums;
import org.xbmc.android.jsonrpc.provider.AudioContract.Artists;
import org.xbmc.android.jsonrpc.provider.AudioContract.SyncColumns;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
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
	protected ContentValues[] parse(JsonNode result, ContentResolver resolver) {
		
		final long now = System.currentTimeMillis();
		Log.d(TAG, "Building queries for album's drop and create.");
		
		// we intentionally don't use the API for de-serializing but access the 
		// JSON objects directly for performance reasons.
		final ArrayNode albums = (ArrayNode)result.get(AbstractCall.RESULT).get(AudioLibrary.GetAlbums.RESULTS);

		final ContentValues[] batch = new ContentValues[albums.size()];
		for (int i = 0; i < albums.size(); i++) {
			final ObjectNode album = (ObjectNode)albums.get(i);
			batch[i] = new ContentValues();
			batch[i].put(SyncColumns.UPDATED, now);
			batch[i].put(Albums.ID, album.get(AudioModel.AlbumDetails.ALBUMID).getIntValue());
			batch[i].put(Albums.TITLE, album.get(AudioModel.AlbumDetails.TITLE).getTextValue());
			batch[i].put(Albums.PREFIX + Artists.ID, album.get(AudioModel.AlbumDetails.ARTISTID).getTextValue());
			batch[i].put(Albums.YEAR, album.get(AudioModel.AlbumDetails.YEAR).getTextValue());
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
			return new AlbumHandler();
		}
		@Override
		public AlbumHandler[] newArray(int n) {
			return new AlbumHandler[n];
		}
	};

}
