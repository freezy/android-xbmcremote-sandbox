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

package org.xbmc.android.jsonrpc.provider;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.util.Log;
import org.xbmc.android.util.DBUtils;
import org.xbmc.android.util.google.SelectionBuilder;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Provider that stores {@link org.xbmc.android.jsonrpc.provider.VideoContract} data. Data is usually inserted
 * by {@link org.xbmc.android.jsonrpc.service.SyncService}, and queried by various {@link android.app.Activity} instances.
 * <p>
 * This class, along with the other ones in this package was closely inspired by
 * Google's official iosched app, see http://code.google.com/p/iosched/
 *
 * @author freezy <freezy@xbmc.org>
 */
public class VideoProvider extends ContentProvider {

	private static final String TAG = VideoProvider.class.getSimpleName();
	private static final boolean LOGV = Log.isLoggable(TAG, Log.VERBOSE);

	private VideoDatabase mOpenHelper;

	private static final UriMatcher sUriMatcher = buildUriMatcher();

	private static final int MOVIES = 100;
	private static final int MOVIES_ID = 101;


	/**
	 * Build and return a {@link android.content.UriMatcher} that catches all {@link android.net.Uri}
	 * variations supported by this {@link android.content.ContentProvider}.
	 */
	private static UriMatcher buildUriMatcher() {
		final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		final String authority = VideoContract.CONTENT_AUTHORITY;

		matcher.addURI(authority, "movies", MOVIES);
		matcher.addURI(authority, "movies/*", MOVIES_ID);

		return matcher;
	}

	@Override
	public boolean onCreate() {
		final Context context = getContext();
		mOpenHelper = new VideoDatabase(context);
		return true;
	}

	@Override
	public String getType(Uri uri) {

		final int match = sUriMatcher.match(uri);
		switch (match) {
		case MOVIES:
			return VideoContract.Movies.CONTENT_TYPE;
		case MOVIES_ID:
			return VideoContract.Movies.CONTENT_ITEM_TYPE;
		default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		if (LOGV)
			Log.v(TAG, "query(uri=" + uri + ", proj=" + Arrays.toString(projection) + ")");
		final SQLiteDatabase db = mOpenHelper.getReadableDatabase();

		final int match = sUriMatcher.match(uri);
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
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		final int match = sUriMatcher.match(uri);
		switch (match) {
			case MOVIES: {
				db.insertOrThrow(VideoDatabase.Tables.MOVIES, null, values);
				getContext().getContentResolver().notifyChange(uri, null);
				return VideoContract.Movies.buildAlbumUri(values.getAsString(VideoContract.Movies.ID));
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
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		final SelectionBuilder builder = buildSimpleSelection(uri);
		int retVal = builder.where(selection, selectionArgs).update(db, values);
		getContext().getContentResolver().notifyChange(uri, null);
		return retVal;
	}

	/** {@inheritDoc} */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		if (LOGV) Log.v(TAG, "delete(uri=" + uri + ")");
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
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
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
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
		final int match = sUriMatcher.match(uri);
		switch (match) {
			case MOVIES: {
				return builder.table(VideoDatabase.Tables.MOVIES);
			}
			case MOVIES_ID: {
				final String movieId = VideoContract.Movies.getMovieId(uri);
				return builder.table(VideoDatabase.Tables.MOVIES).where(VideoContract.Movies.ID + "=?", movieId);
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
		//final SelectionBuilder builder = new SelectionBuilder();
		switch (match) {
			default: {
				throw new UnsupportedOperationException("Unknown uri: " + uri);
			}
		}
	}

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		final int match = sUriMatcher.match(uri);
		switch (match) {
			case MOVIES: {
				int numInserted = 0;
				db.beginTransaction();
				try {
					// delete all rows in table
					db.delete(VideoDatabase.Tables.MOVIES, null, null);

					// insert new rows into table
					// standard SQL insert statement, that can be reused
					SQLiteStatement insert = db.compileStatement(
						"INSERT INTO " + VideoDatabase.Tables.MOVIES + "(" +
						VideoContract.SyncColumns.UPDATED +
						"," + VideoContract.MoviesColumns.ID +
						"," + VideoContract.MoviesColumns.TITLE +
						"," + VideoContract.MoviesColumns.YEAR +
						"," + VideoContract.MoviesColumns.GENRE +
						"," + VideoContract.MoviesColumns.RATING +
						"," + VideoContract.MoviesColumns.RUNTIME +
						"," + VideoContract.MoviesColumns.THUMBNAIL +
						") VALUES " + "(?,?,?,?,?,?,?,?)");

					final long now = System.currentTimeMillis();
					for (ContentValues value : values) {
						insert.bindLong(1, now);
						DBUtils.bind(insert, value, 2, VideoContract.Movies.ID);
						DBUtils.bind(insert, value, 3, VideoContract.Movies.TITLE);
						DBUtils.bind(insert, value, 4, VideoContract.Movies.YEAR);
						DBUtils.bind(insert, value, 5, VideoContract.Movies.GENRE);
						DBUtils.bind(insert, value, 6, VideoContract.Movies.RATING);
						DBUtils.bind(insert, value, 7, VideoContract.Movies.RUNTIME);
						DBUtils.bind(insert, value, 8, VideoContract.Movies.THUMBNAIL);
						insert.executeInsert();
					}
					db.setTransactionSuccessful();
					numInserted = values.length;

				} finally {
					db.endTransaction();
					getContext().getContentResolver().notifyChange(uri, null);
				}
				return numInserted;
			}
			default:
				throw new UnsupportedOperationException("unsupported uri: " + uri);
		}
	}

}
