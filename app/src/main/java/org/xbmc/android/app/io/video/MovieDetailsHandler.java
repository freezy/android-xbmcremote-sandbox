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
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.provider.BaseColumns;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.xbmc.android.app.provider.VideoContract;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.api.call.VideoLibrary;
import org.xbmc.android.jsonrpc.api.model.VideoModel;
import org.xbmc.android.jsonrpc.io.JsonHandler;
import org.xbmc.android.util.DBUtils;

import java.util.HashMap;
import java.util.HashSet;

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
	private static HashMap<String, Integer> GENRE_CACHE;
	/**
	 * Directors and writers don't have thumbs - need to know when to update a person that hasn't a thumbnail
	 * and might was added by director/writer and not via actor.
	 */
	private static HashSet<Integer> THUMB_CACHE;

	private final int id;
	private final int hostId;

	public MovieDetailsHandler(int hostId, int id) {
		super(VideoContract.CONTENT_AUTHORITY);
		this.id = id;
		this.hostId = hostId;
	}

	public static void initCache() {
		PEOPLE_CACHE = null;
		GENRE_CACHE = null;
		THUMB_CACHE = null;
	}

	@Override
	protected ContentValues[] parse(JsonNode response, ContentResolver resolver) {

		/* SETUP CACHE VARIABLES
		 */
		if (PEOPLE_CACHE == null) {
			PEOPLE_CACHE = new HashMap<String, Integer>();
			THUMB_CACHE = new HashSet<Integer>();
			final String[] model = { BaseColumns._ID, VideoContract.People.NAME, VideoContract.People.THUMBNAIL };
			final Cursor cursor = resolver.query(VideoContract.People.CONTENT_URI, model, null, null, null);
			while (cursor.moveToNext()) {
				final int id = cursor.getInt(0);
				PEOPLE_CACHE.put(cursor.getString(1), id);
				if (cursor.getString(2) != null) {
					THUMB_CACHE.add(id);
				}
			}
		}
		// put genres into cache
		if (GENRE_CACHE == null) {
			GENRE_CACHE = new HashMap<String, Integer>();
			final String[] model = { BaseColumns._ID, VideoContract.Genres.NAME };
			final Cursor cursor = resolver.query(VideoContract.Genres.CONTENT_URI, model, null, null, null);
			while (cursor.moveToNext()) {
				GENRE_CACHE.put(cursor.getString(1), cursor.getInt(0));
			}
		}
		// retrieve object
		final ObjectNode movie = (ObjectNode)response.get(AbstractCall.RESULT).get(VideoLibrary.GetMovieDetails.RESULT);

		/* CAST
		 */
		final ArrayNode cast = (ArrayNode)movie.get(MovieDetail.CAST);
		final ContentValues[] batch = new ContentValues[cast.size()];
		int i = 0;
		for (JsonNode actor : cast) {
			final String name = actor.get(VideoModel.Cast.NAME).getTextValue();
			if (name.isEmpty()) {
				continue;
			}
			batch[i] = new ContentValues();
			batch[i].put(VideoContract.MovieCast.MOVIE_REF, id);
			batch[i].put(VideoContract.MovieCast.PERSON_REF, getPerson(resolver, name, actor));
			batch[i].put(VideoContract.MovieCast.ROLE, DBUtils.getStringValue(actor, VideoModel.Cast.ROLE));
			batch[i].put(VideoContract.MovieCast.SORT, DBUtils.getIntValue(actor, VideoModel.Cast.ORDER));
			i++;
		}

		/* DIRECTORS
		 */
		final ArrayNode directors = (ArrayNode)movie.get(MovieDetail.DIRECTOR);
		if (directors != null) {
			for (JsonNode director : directors) {
				final ContentValues row = new ContentValues();
				row.put(VideoContract.MovieDirector.MOVIE_REF, id);
				row.put(VideoContract.MovieDirector.PERSON_REF, getPerson(resolver, director.getTextValue()));
				resolver.insert(VideoContract.MovieDirector.CONTENT_URI, row);
			}
		}

		/* WRITERS
		 */
		final ArrayNode writers = (ArrayNode)movie.get(MovieDetail.WRITER);
		if (writers != null) {
			for (JsonNode writer : writers) {
				final ContentValues row = new ContentValues();
				row.put(VideoContract.MovieWriter.MOVIE_REF, id);
				row.put(VideoContract.MovieWriter.PERSON_REF, getPerson(resolver, writer.getTextValue()));
				resolver.insert(VideoContract.MovieWriter.CONTENT_URI, row);
			}
		}

		/* GENRES
		 */
		final ArrayNode genres = (ArrayNode)movie.get(MovieDetail.GENRE);
		for (JsonNode genre : genres) {
			final String name = genre.getTextValue();
			final int genreRef;
			if (GENRE_CACHE.containsKey(name)) {
				genreRef = GENRE_CACHE.get(name);
			} else {
				final ContentValues row = new ContentValues();
				row.put(VideoContract.Genres.NAME, name);
				final Uri newGenreUri = resolver.insert(VideoContract.Genres.CONTENT_URI, row);
				genreRef = VideoContract.Genres.getGenreId(newGenreUri);
				GENRE_CACHE.put(name, genreRef);
			}

			// insert reference
			final ContentValues row = new ContentValues();
			row.put(VideoContract.MovieGenres.GENRE_REF, genreRef);
			row.put(VideoContract.MovieGenres.MOVIE_REF, id);
			resolver.insert(VideoContract.MovieGenres.CONTENT_URI, row);
		}
		return batch;
	}


	/**
	 * Returns the ID of a person with a given name.
	 *
	 * Firstly, the cache is checked. If miss, it's added to the database and the ID is returned.
	 * If the "person" parameter is passed and the current record doesn't contain a thumbnail (but the
	 * "person" node does), the current record is updated with the thumbnail. This can happen if
	 * a record is added for a director (which doesn't contain any more data) and only later is checked
	 * for a cast node (which does contain the thumb).
	 *
	 * @param resolver Content resolver
	 * @param name Name of the person
	 * @param person If cast, the node that contains additional metadata. Can be null.
	 * @return Database ID of the person
	 */
	private int getPerson(ContentResolver resolver, String name, JsonNode person) {
		final int personRef;
		if (PEOPLE_CACHE.containsKey(name)) {
			personRef = PEOPLE_CACHE.get(name);

			// update thumb if necessary
			if (person != null && person.has(VideoModel.Cast.THUMBNAIL) && !THUMB_CACHE.contains(personRef)) {
				final ContentValues row = new ContentValues();
				row.put(VideoContract.People.THUMBNAIL, person.get(VideoModel.Cast.THUMBNAIL).getTextValue());
				final String[] args = { String.valueOf(personRef) };
				resolver.update(VideoContract.People.CONTENT_URI, row, BaseColumns._ID + "=?", args);
			}
		} else {
			final ContentValues row = new ContentValues();
			row.put(VideoContract.People.HOST_ID, hostId);
			row.put(VideoContract.People.NAME, name);
			if (person != null && person.has(VideoModel.Cast.THUMBNAIL)) {
				row.put(VideoContract.People.THUMBNAIL, person.get(VideoModel.Cast.THUMBNAIL).getTextValue());
			}

			final Uri newPersonUri = resolver.insert(VideoContract.People.CONTENT_URI, row);
			personRef = VideoContract.People.getPersonId(newPersonUri);
			PEOPLE_CACHE.put(name, personRef);
		}
		return personRef;
	}

	/**
	 * Returns the ID of a person with a given name. If not in the database, the person will be added.
	 * @param resolver Content
	 * @param name Name of the person
	 * @return Database ID of the person
	 */
	private int getPerson(ContentResolver resolver, String name) {
		return getPerson(resolver, name, null);
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
