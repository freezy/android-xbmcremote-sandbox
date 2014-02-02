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

package org.xbmc.android.app.io.video;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Parcel;
import android.util.Log;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.xbmc.android.app.provider.VideoContract;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.api.call.VideoLibrary;
import org.xbmc.android.jsonrpc.io.JsonHandler;

/**
 * Handles one-way synchronization between XBMC's <tt>movie</tt> table and the local
 * {@link org.xbmc.android.app.provider.VideoContract.Movies} table.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class MovieDetailsHandler extends JsonHandler {

	private final static String TAG = MovieDetailsHandler.class.getSimpleName();

	private final long id;

	public MovieDetailsHandler(long id) {
		super(VideoContract.CONTENT_AUTHORITY);
		this.id = id;
	}

	@Override
	protected ContentValues[] parse(JsonNode response, ContentResolver resolver) {

		final long now = System.currentTimeMillis();

		// we intentionally don't use the API for mapping but access the
		// JSON objects directly for performance reasons.
		final ObjectNode movie = (ObjectNode)response.get(AbstractCall.RESULT).get(VideoLibrary.GetMovieDetails.RESULT);

		final ContentValues[] batch = new ContentValues[1];

		Log.d(TAG, "Got movie details for ID " + id + " in " + (System.currentTimeMillis() - now) + "ms.");
		return batch;
	}

	@Override
	protected void insert(ContentResolver resolver, ContentValues[] batch) {
		//resolver.bulkInsert(VideoContract.Movies.CONTENT_URI, batch);
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeLong(id);
		super.writeToParcel(parcel, flags);
	}

	/**
	 * Generates instances of this Parcelable class from a Parcel.
	 */
	public static final Creator<MovieDetailsHandler> CREATOR = new Creator<MovieDetailsHandler>() {
		@Override
		public MovieDetailsHandler createFromParcel(Parcel parcel) {
			return new MovieDetailsHandler(parcel.readLong());
		}
		@Override
		public MovieDetailsHandler[] newArray(int n) {
			return new MovieDetailsHandler[n];
		}
	};
}
