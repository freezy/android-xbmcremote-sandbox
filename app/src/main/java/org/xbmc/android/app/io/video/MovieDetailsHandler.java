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
import android.net.Uri;
import android.os.Parcel;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.xbmc.android.app.provider.VideoContract;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.api.call.VideoLibrary;
import org.xbmc.android.jsonrpc.api.model.VideoModel;
import org.xbmc.android.jsonrpc.io.JsonHandler;

import java.util.HashMap;

import static org.xbmc.android.jsonrpc.api.model.VideoModel.MovieDetail;

/**
 * Handles one-way synchronization between XBMC's <tt>movie</tt> table and the local
 * {@link org.xbmc.android.app.provider.VideoContract.Movies} table.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class MovieDetailsHandler extends JsonHandler {

	private final static String TAG = MovieDetailsHandler.class.getSimpleName();

	private static HashMap<String, Integer> PEOPLE_CACHE;

	private final int id;
	private final int hostId;

	public MovieDetailsHandler(int hostId, int id) {
		super(VideoContract.CONTENT_AUTHORITY);
		this.id = id;
		this.hostId = hostId;
	}

	public static void initCache() {
		PEOPLE_CACHE = null;
	}

	@Override
	protected ContentValues[] parse(JsonNode response, ContentResolver resolver) {

		final long now = System.currentTimeMillis();
		if (PEOPLE_CACHE == null) {
			PEOPLE_CACHE = new HashMap<String, Integer>();
			resolver.delete(VideoContract.People.CONTENT_URI, null, null);
		}

		// we intentionally don't use the API for mapping but access the
		// JSON objects directly for performance reasons.
		final ObjectNode movie = (ObjectNode)response.get(AbstractCall.RESULT).get(VideoLibrary.GetMovieDetails.RESULT);

		// loop through cast
		final ArrayNode cast = (ArrayNode)movie.get(MovieDetail.CAST);
		final ContentValues[] batch = new ContentValues[cast.size()];
		int i = 0;
		for (JsonNode actor : cast) {
			final String name = actor.get(VideoModel.Cast.NAME).getTextValue();
			final int actorRef;
			if (PEOPLE_CACHE.containsKey(name)) {
				actorRef = PEOPLE_CACHE.get(name);
			} else {
				final ContentValues row = new ContentValues();
				row.put(VideoContract.People.HOST_ID, hostId);
				row.put(VideoContract.People.NAME, name);
				if (actor.has(VideoModel.Cast.THUMBNAIL)) {
					row.put(VideoContract.People.THUMBNAIL, actor.get(VideoModel.Cast.THUMBNAIL).getTextValue());
				}

				final Uri newPersonUri = resolver.insert(VideoContract.People.CONTENT_URI, row);
				actorRef = VideoContract.People.getPersonId(newPersonUri);
				PEOPLE_CACHE.put(name, actorRef);
			}

			batch[i] = new ContentValues();
			batch[i].put(VideoContract.MovieCast.MOVIE_REF, id);
			batch[i].put(VideoContract.MovieCast.PERSON_REF, actorRef);
			batch[i].put(VideoContract.MovieCast.ROLE, actor.get(VideoModel.Cast.ROLE).getTextValue());
			batch[i].put(VideoContract.MovieCast.SORT, actor.get(VideoModel.Cast.ORDER).getIntValue());
			i++;
		}
		return batch;
	}

	@Override
	protected void insert(ContentResolver resolver, ContentValues[] batch) {
		resolver.bulkInsert(VideoContract.MovieCast.CONTENT_URI, batch);
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeLong(hostId);
		parcel.writeLong(id);
	}

	/**
	 * Generates instances of this Parcelable class from a Parcel.
	 */
	public static final Creator<MovieDetailsHandler> CREATOR = new Creator<MovieDetailsHandler>() {
		@Override
		public MovieDetailsHandler createFromParcel(Parcel parcel) {
			return new MovieDetailsHandler(parcel.readInt(), parcel.readInt());
		}
		@Override
		public MovieDetailsHandler[] newArray(int n) {
			return new MovieDetailsHandler[n];
		}
	};
}
