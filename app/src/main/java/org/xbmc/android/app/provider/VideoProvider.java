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

package org.xbmc.android.app.provider;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import org.xbmc.android.util.google.SelectionBuilder;

import java.util.ArrayList;
import java.util.Arrays;

import static org.xbmc.android.app.provider.VideoContract.*;

/**
 * Provider that stores {@link org.xbmc.android.app.provider.VideoContract} data. Data is usually inserted
 * by {@link org.xbmc.android.app.service.SyncService}, and queried by various {@link android.app.Activity} instances.
 * <p>
 * This class, along with the other ones in this package was closely inspired by
 * Google's official iosched app, see http://code.google.com/p/iosched/
 *
 * @author freezy <freezy@xbmc.org>
 */
public class VideoProvider extends AbstractProvider {

	private static final String TAG = VideoProvider.class.getSimpleName();
	private static final boolean LOGV = Log.isLoggable(TAG, Log.VERBOSE);

	private VideoDatabase database;

	private static final UriMatcher URI_MATCHER = buildUriMatcher();

	private static final int MOVIES = 100;
	private static final int MOVIES_ID = 101;
	private static final int PEOPLE = 200;
	private static final int PERSON_ID = 201;
	private static final int MOVIECAST = 210;
	private static final int MOVIEDIRECTOR = 220;
	private static final int MOVIEWRITER = 230;
	private static final int GENRES = 300;
	private static final int MOVIEGENRES = 310;

	/**
	 * Build and return a {@link android.content.UriMatcher} that catches all {@link android.net.Uri}
	 * variations supported by this {@link android.content.ContentProvider}.
	 */
	private static UriMatcher buildUriMatcher() {
		final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		final String authority = VideoContract.CONTENT_AUTHORITY;

		matcher.addURI(authority, VideoContract.PATH_MOVIES, MOVIES);
		matcher.addURI(authority, VideoContract.PATH_MOVIES + "/*", MOVIES_ID);
		matcher.addURI(authority, VideoContract.PATH_MOVIES + "/*/" + VideoContract.PATH_MOVIECAST, MOVIECAST);
		matcher.addURI(authority, VideoContract.PATH_PEOPLE, PEOPLE);
		matcher.addURI(authority, VideoContract.PATH_PEOPLE + "/*", PERSON_ID);
		matcher.addURI(authority, VideoContract.PATH_MOVIEDIRECTOR, MOVIEDIRECTOR);
		matcher.addURI(authority, VideoContract.PATH_MOVIEWRITER, MOVIEWRITER);
		matcher.addURI(authority, VideoContract.PATH_GENRES, GENRES);
		matcher.addURI(authority, VideoContract.PATH_MOVIEGENRES, MOVIEGENRES);

		return matcher;
	}

	@Override
	public boolean onCreate() {
		database = new VideoDatabase(getContext());
		return super.onCreate();
	}

	@Override
	public String getType(Uri uri) {

		final int match = URI_MATCHER.match(uri);
		switch (match) {
			case MOVIES:
				return Movies.CONTENT_TYPE;
			case MOVIES_ID:
				return Movies.CONTENT_ITEM_TYPE;
			case PEOPLE:
				return People.CONTENT_TYPE;
			case PERSON_ID:
				return People.CONTENT_ITEM_TYPE;
			case MOVIECAST:
				return MovieCast.CONTENT_TYPE;
			case MOVIEDIRECTOR:
				return MovieDirector.CONTENT_TYPE;
			case MOVIEWRITER:
				return MovieWriter.CONTENT_TYPE;
			case GENRES:
				return Genres.CONTENT_TYPE;
			case MOVIEGENRES:
				return MovieGenres.CONTENT_TYPE;
			default:
				throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		if (LOGV)
			Log.v(TAG, "query(uri=" + uri + ", proj=" + Arrays.toString(projection) + ")");
		final SQLiteDatabase db = database.getReadableDatabase();

		final int match = URI_MATCHER.match(uri);
		switch (match) {
			default: {
				// Most cases are handled with simple SelectionBuilder
				final SelectionBuilder builder = buildExpandedSelection(uri, match);
				return builder.where(selection, selectionArgs).query(db, projection, sortOrder);
			}
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		if (LOGV) Log.v(TAG, "insert(uri=" + uri + ", values=" + values.toString() + ")");
		final SQLiteDatabase db = database.getWritableDatabase();
		final int match = URI_MATCHER.match(uri);
		switch (match) {
			case MOVIES: {
				db.insertOrThrow(VideoDatabase.Tables.MOVIES, null, values);
				getContext().getContentResolver().notifyChange(uri, null);
				return Movies.buildMovieUri(values.getAsString(Movies.ID));
			}
			case PEOPLE: {
				final long id = db.insertOrThrow(VideoDatabase.Tables.PEOPLE, null, values);
				getContext().getContentResolver().notifyChange(uri, null);
				return People.buildPersonUri(id);
			}
			case GENRES: {
				final long id = db.insertOrThrow(VideoDatabase.Tables.GENRES, null, values);
				getContext().getContentResolver().notifyChange(uri, null);
				return Genres.buildGenreUri(id);
			}
			case MOVIECAST: {
				db.insertOrThrow(VideoDatabase.Tables.PEOPLE_MOVIE_CAST, null, values);
				return null;
			}
			case MOVIEDIRECTOR: {
				db.insertOrThrow(VideoDatabase.Tables.PEOPLE_MOVIE_DIRECTOR, null, values);
				return null;
			}
			case MOVIEWRITER: {
				db.insertOrThrow(VideoDatabase.Tables.PEOPLE_MOVIE_WRITER, null, values);
				return null;
			}
			case MOVIEGENRES: {
				db.insertOrThrow(VideoDatabase.Tables.GENRES_MOVIE, null, values);
				return null;
			}
			default: {
				throw new UnsupportedOperationException("Unknown uri: " + uri);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		if (LOGV) Log.v(TAG, "update(uri=" + uri + ", values=" + values.toString() + ")");
		final SQLiteDatabase db = database.getWritableDatabase();
		final SelectionBuilder builder = buildSimpleSelection(uri);
		int retVal = builder.where(selection, selectionArgs).update(db, values);
		getContext().getContentResolver().notifyChange(uri, null);
		return retVal;
	}

	/** {@inheritDoc} */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		if (LOGV) Log.v(TAG, "delete(uri=" + uri + ")");
		final SQLiteDatabase db = database.getWritableDatabase();
		final SelectionBuilder builder = buildSimpleSelection(uri);
		int retVal = builder.where(selection, selectionArgs).delete(db);
		getContext().getContentResolver().notifyChange(uri, null);
		return retVal;
	}

	/**
	 * Apply the given set of {@link android.content.ContentProviderOperation}, executing inside
	 * a {@link android.database.sqlite.SQLiteDatabase} transaction. All changes will be rolled back if
	 * any single one fails.
	 */
	@Override
	public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
		final SQLiteDatabase db = database.getWritableDatabase();
		db.beginTransaction();
		try {
			final int numOperations = operations.size();
			final ContentProviderResult[] results = new ContentProviderResult[numOperations];
			for (int i = 0; i < numOperations; i++) {
				results[i] = operations.get(i).apply(this, results, i);
			}
			db.setTransactionSuccessful();
			return results;
		} finally {
			db.endTransaction();
		}
	}

	/**
	 * Build a simple {@link org.xbmc.android.util.google.SelectionBuilder} to match the requested
	 * {@link android.net.Uri}. This is usually enough to support {@link #insert},
	 * {@link #update}, and {@link #delete} operations.
	 */
	private SelectionBuilder buildSimpleSelection(Uri uri) {
		final SelectionBuilder builder = new SelectionBuilder();
		final int match = URI_MATCHER.match(uri);
		switch (match) {
			case MOVIES: {
				return builder.table(VideoDatabase.Tables.MOVIES).where(Movies.HOST_ID + "=?", getHostIdAsString());
			}
			case MOVIES_ID: {
				final String movieId = Movies.getMovieId(uri);
				return builder.table(VideoDatabase.Tables.MOVIES).where(Movies.ID + "=?", movieId).where(Movies.HOST_ID + "=?", getHostIdAsString());
			}
			case PEOPLE: {
				return builder.table(VideoDatabase.Tables.PEOPLE).where(People.HOST_ID + "=?", getHostIdAsString());
			}
			case GENRES: {
				return builder.table(VideoDatabase.Tables.GENRES);
			}
			default: {
				throw new UnsupportedOperationException("Unknown uri: " + uri);
			}
		}
    }

	/**
	 * Build an advanced {@link org.xbmc.android.util.google.SelectionBuilder} to match the requested
	 * {@link android.net.Uri}. This is usually only used by {@link #query}, since it
	 * performs table joins useful for {@link android.database.Cursor} data.
	 */
	private SelectionBuilder buildExpandedSelection(Uri uri, int match) {
		final SelectionBuilder builder = new SelectionBuilder();
		switch (match) {
			case MOVIES: {
				return builder.table(VideoDatabase.Tables.MOVIES).where(Movies.HOST_ID + "=?", getHostIdAsString());
			}
			case MOVIES_ID: {
				final String movieId = Movies.getMovieId(uri);
				return builder.table(VideoDatabase.Tables.MOVIES).where(Movies._ID + "=?", movieId);
			}
			case MOVIECAST: {
				final String movieId = MovieCast.getMovieId(uri);
				return builder.table(VideoDatabase.Tables.PEOPLE_MOVIE_CAST_JOIN_PEOPLE).where(MovieCast.MOVIE_REF + "=?", movieId);
			}
			case PEOPLE: {
				return builder.table(VideoDatabase.Tables.PEOPLE).where(People.HOST_ID + "=?", getHostIdAsString());
			}
			case GENRES: {
				return builder.table(VideoDatabase.Tables.GENRES);
			}

			default: {
				throw new UnsupportedOperationException("Unknown uri: " + uri);
			}
		}
	}

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		final SQLiteDatabase db = database.getWritableDatabase();
		final int numInserted;
		db.beginTransaction();
		try {
			numInserted = super.bulkInsert(uri, values);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return numInserted;
	}

}
