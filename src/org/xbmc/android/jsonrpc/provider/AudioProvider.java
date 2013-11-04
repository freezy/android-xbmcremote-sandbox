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

import java.util.ArrayList;
import java.util.Arrays;

import org.xbmc.android.jsonrpc.provider.AudioContract.Albums;
import org.xbmc.android.jsonrpc.provider.AudioContract.AlbumsColumns;
import org.xbmc.android.jsonrpc.provider.AudioContract.Artists;
import org.xbmc.android.jsonrpc.provider.AudioContract.ArtistsColumns;
import org.xbmc.android.jsonrpc.provider.AudioDatabase.Tables;
import org.xbmc.android.util.google.SelectionBuilder;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.util.Log;

/**
 * Provider that stores {@link AudioContract} data. Data is usually inserted
 * by {@link org.xbmc.android.jsonrpc.service.SyncService}, and queried by various {@link Activity} instances.
 * <p>
 * This class, along with the other ones in this package was closely inspired by
 * Google's official iosched app, see http://code.google.com/p/iosched/
 *
 * @author freezy <freezy@xbmc.org>
 */
public class AudioProvider extends ContentProvider {

	private static final String TAG = AudioProvider.class.getSimpleName();
	private static final boolean LOGV = Log.isLoggable(TAG, Log.VERBOSE);

	private AudioDatabase mOpenHelper;

	private static final UriMatcher sUriMatcher = buildUriMatcher();

	private static final int ALBUMS = 100;
	private static final int ALBUMS_ID = 101;

	private static final int ARTISTS = 200;
	private static final int ARTISTS_ID = 201;
	private static final int ARTISTS_ID_ALBUMS = 202;

	/**
	 * Build and return a {@link UriMatcher} that catches all {@link Uri}
	 * variations supported by this {@link ContentProvider}.
	 */
	private static UriMatcher buildUriMatcher() {
		final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		final String authority = AudioContract.CONTENT_AUTHORITY;

		matcher.addURI(authority, "albums", ALBUMS);
		matcher.addURI(authority, "albums/*", ALBUMS_ID);

		matcher.addURI(authority, "artists", ARTISTS);
		matcher.addURI(authority, "artists/*", ARTISTS_ID);
		matcher.addURI(authority, "artists/*/albums", ARTISTS_ID_ALBUMS);

		return matcher;
	}

	@Override
	public boolean onCreate() {
		final Context context = getContext();
		mOpenHelper = new AudioDatabase(context);
		return true;
	}

	@Override
	public String getType(Uri uri) {

		final int match = sUriMatcher.match(uri);
		switch (match) {
		case ALBUMS:
			return Albums.CONTENT_TYPE;
		case ALBUMS_ID:
			return Albums.CONTENT_ITEM_TYPE;
		case ARTISTS:
			return Artists.CONTENT_TYPE;
		case ARTISTS_ID:
			return Artists.CONTENT_ITEM_TYPE;
		case ARTISTS_ID_ALBUMS:
			return Albums.CONTENT_TYPE;
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
			case ALBUMS: {
				db.insertOrThrow(Tables.ALBUMS, null, values);
				getContext().getContentResolver().notifyChange(uri, null);
				return Albums.buildAlbumUri(values.getAsString(Albums.ID));
			}
			case ARTISTS: {
				db.insertOrThrow(Tables.ARTISTS, null, values);
				getContext().getContentResolver().notifyChange(uri, null);
				return Artists.buildArtistUri(values.getAsString(Artists.ID));
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
	 * Apply the given set of {@link ContentProviderOperation}, executing inside
	 * a {@link SQLiteDatabase} transaction. All changes will be rolled back if
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
	 * Build a simple {@link SelectionBuilder} to match the requested
	 * {@link Uri}. This is usually enough to support {@link #insert},
	 * {@link #update}, and {@link #delete} operations.
	 */
	private SelectionBuilder buildSimpleSelection(Uri uri) {
		final SelectionBuilder builder = new SelectionBuilder();
		final int match = sUriMatcher.match(uri);
		switch (match) {
			case ALBUMS: {
				return builder.table(Tables.ALBUMS);
			}
			case ALBUMS_ID: {
				final String albumId = Albums.getAlbumId(uri);
				return builder.table(Tables.ALBUMS).where(Albums.ID + "=?", albumId);
			}
			case ARTISTS: {
				return builder.table(Tables.ARTISTS);
			}
			case ARTISTS_ID: {
				final String artistId = Artists.getArtistId(uri);
				return builder.table(Tables.ARTISTS).where(Artists.ID + "=?", artistId);
			}
			default: {
				throw new UnsupportedOperationException("Unknown uri: " + uri);
			}
		}
    }

	/**
	 * Build an advanced {@link SelectionBuilder} to match the requested
	 * {@link Uri}. This is usually only used by {@link #query}, since it
	 * performs table joins useful for {@link Cursor} data.
	 */
	private SelectionBuilder buildExpandedSelection(Uri uri, int match) {
		final SelectionBuilder builder = new SelectionBuilder();
		switch (match) {
			case ALBUMS: {
				return builder.table(Tables.ALBUMS_JOIN_ARTISTS);
			}
			case ALBUMS_ID: {
				final String albumId = Albums.getAlbumId(uri);
				return builder.table(Tables.ALBUMS).where(Albums.ID + "=?", albumId);
			}
			case ARTISTS: {
				return builder.table(Tables.ARTISTS)
						.map(Artists.ALBUMS_COUNT, Subquery.ARTIST_ALBUMS_COUNT);
			}
			case ARTISTS_ID_ALBUMS: {
				final String artistId = Artists.getArtistId(uri);
				return builder.table(Tables.ALBUMS).where(Artists.ID + "=?", artistId);
			}
			default: {
				throw new UnsupportedOperationException("Unknown uri: " + uri);
			}
		}
	}

	private interface Subquery {
		String ARTIST_ALBUMS_COUNT = "(SELECT COUNT(" + Albums.ID + ") FROM " + Tables.ALBUMS + " WHERE "
				+ Albums.PREFIX + Artists.ID + "=" + Artists.ID + ")";
	}

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		final int match = sUriMatcher.match(uri);
		switch (match) {
			case ALBUMS: {
				int numInserted = 0;
				db.beginTransaction();
				try {
					// delete all rows in table
					db.delete(AudioDatabase.Tables.ALBUMS, null, null);

					// insert new rows into table
					// standard SQL insert statement, that can be reused
					SQLiteStatement insert = db.compileStatement(
						"INSERT INTO " + AudioDatabase.Tables.ALBUMS + "(" +
						AlbumsColumns.UPDATED +
						"," + AlbumsColumns.ID +
						"," + AlbumsColumns.TITLE +
						"," + AlbumsColumns.PREFIX + Artists.ID +
						"," + AlbumsColumns.YEAR +
						"," + AlbumsColumns.THUMBNAIL +
						") VALUES " + "(?,?,?,?,?,?)");

					final long now = System.currentTimeMillis();
					for (ContentValues value : values) {
						insert.bindLong(1, now);
						insert.bindString(2, value.getAsString(Albums.ID));
						insert.bindString(3, value.getAsString(Albums.TITLE));
						insert.bindString(4, value.getAsString(Albums.PREFIX + Artists.ID));
						insert.bindString(5, value.getAsString(Albums.YEAR));
						insert.bindString(6, value.getAsString(Albums.THUMBNAIL));
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
			case ARTISTS: {
				int numInserted = 0;
				db.beginTransaction();
				try {
					// delete all rows in table
					db.delete(AudioDatabase.Tables.ARTISTS, null, null);
					// insert new rows into table
					// standard SQL insert statement, that can be reused
					SQLiteStatement insert = db.compileStatement(
							"INSERT INTO " + AudioDatabase.Tables.ARTISTS + "(" +
							ArtistsColumns.UPDATED +
							"," + ArtistsColumns.ID +
							"," + ArtistsColumns.NAME +
							") VALUES " + "(?,?,?)");

					final long now = System.currentTimeMillis();
					for (ContentValues value : values) {
						insert.bindLong(1, (now));
						insert.bindString(2, value.getAsString(Artists.ID));
						insert.bindString(3, value.getAsString(Artists.NAME));
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
