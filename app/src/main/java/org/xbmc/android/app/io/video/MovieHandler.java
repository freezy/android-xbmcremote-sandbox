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
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.xbmc.android.app.provider.VideoContract;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.api.call.VideoLibrary;
import org.xbmc.android.jsonrpc.api.model.VideoModel.MovieDetail;
import org.xbmc.android.jsonrpc.io.JsonHandler;
import org.xbmc.android.util.DBUtils;

import static org.xbmc.android.app.provider.VideoContract.Movies;

/**
 * Handles one-way synchronization between XBMC's <tt>movie</tt> table and the local
 * {@link Movies} table.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class MovieHandler extends JsonHandler {

	private final static String TAG = MovieHandler.class.getSimpleName();

	private final int hostId;

	public MovieHandler(int hostId) {
		super(VideoContract.CONTENT_AUTHORITY);
		this.hostId = hostId;
	}

	@Override
	protected ContentValues[] parse(JsonNode response, ContentResolver resolver) {
		Log.d(TAG, "Building queries for movies...");

		final long now = System.currentTimeMillis();

		// we intentionally don't use the API for mapping but access the
		// JSON objects directly for performance reasons.
		final ArrayNode movies = (ArrayNode)response.get(AbstractCall.RESULT).get(VideoLibrary.GetMovies.RESULT);

		final int s = movies.size();
		final ContentValues[] batch = new ContentValues[s];
		for (int i = 0; i < s; i++) {
			final ObjectNode movie = (ObjectNode)movies.get(i);

			batch[i] = new ContentValues();
			batch[i].put(Movies.UPDATED, now);
			batch[i].put(Movies.HOST_ID, hostId);
			batch[i].put(Movies.ID, movie.get(MovieDetail.MOVIEID).getIntValue());
			batch[i].put(Movies.TITLE, movie.get(MovieDetail.TITLE).getTextValue());
			batch[i].put(Movies.SORTTITLE, DBUtils.getStringValue(movie, MovieDetail.SORTTITLE));
			batch[i].put(Movies.YEAR, DBUtils.getIntValue(movie, MovieDetail.YEAR));
			batch[i].put(Movies.RATING, DBUtils.getDoubleValue(movie, MovieDetail.RATING));
			batch[i].put(Movies.VOTES, DBUtils.getMessedUpIntValue(movie, MovieDetail.VOTES));
			batch[i].put(Movies.RUNTIME, DBUtils.getIntValue(movie, MovieDetail.RUNTIME));
			batch[i].put(Movies.THUMBNAIL, DBUtils.getStringValue(movie, MovieDetail.THUMBNAIL));
			batch[i].put(Movies.TAGLINE, DBUtils.getStringValue(movie, MovieDetail.TAGLINE));
			batch[i].put(Movies.PLOT, DBUtils.getStringValue(movie, MovieDetail.PLOT));
			batch[i].put(Movies.MPAA, DBUtils.getStringValue(movie, MovieDetail.MPAA));
			batch[i].put(Movies.IMDBNUMBER, DBUtils.getStringValue(movie, MovieDetail.IMDBNUMBER));
			batch[i].put(Movies.SETID, DBUtils.getIntValue(movie, MovieDetail.SETID));
			batch[i].put(Movies.TRAILER, DBUtils.getStringValue(movie, MovieDetail.TRAILER));
			batch[i].put(Movies.TOP250, DBUtils.getIntValue(movie, MovieDetail.TOP250));
			batch[i].put(Movies.FANART, DBUtils.getStringValue(movie, MovieDetail.FANART));
			batch[i].put(Movies.FILE, DBUtils.getStringValue(movie, MovieDetail.FILE));
			batch[i].put(Movies.RESUME, DBUtils.getIntValue(movie, MovieDetail.RESUME));
			batch[i].put(Movies.DATEADDED, DBUtils.getDateValue(movie, MovieDetail.DATEADDED));
			batch[i].put(Movies.LASTPLAYED, DBUtils.getDateValue(movie, MovieDetail.LASTPLAYED));
		}

		Log.d(TAG, batch.length + " movie queries built in " + (System.currentTimeMillis() - now) + "ms.");
		return batch;
	}

	@Override
	protected void insert(ContentResolver resolver, ContentValues[] batch) {
		resolver.bulkInsert(Movies.CONTENT_URI, batch);
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(hostId);
	}

	/**
	 * Generates instances of this Parcelable class from a Parcel.
	 */
	public static final Creator<MovieHandler> CREATOR = new Creator<MovieHandler>() {
		@Override
		public MovieHandler createFromParcel(Parcel parcel) {
			return new MovieHandler(parcel.readInt());
		}
		@Override
		public MovieHandler[] newArray(int n) {
			return new MovieHandler[n];
		}
	};
}
