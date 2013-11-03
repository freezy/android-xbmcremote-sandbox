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

import org.xbmc.android.jsonrpc.provider.AudioContract.Albums;
import org.xbmc.android.jsonrpc.provider.AudioContract.AlbumsColumns;
import org.xbmc.android.jsonrpc.provider.AudioContract.Artists;
import org.xbmc.android.jsonrpc.provider.AudioContract.ArtistsColumns;
import org.xbmc.android.jsonrpc.provider.AudioContract.SyncColumns;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Helper for managing {@link SQLiteDatabase} that stores data for
 * {@link AudioProvider}.
 * <p>
 * This class, along with the other ones in this package was closely inspired by
 * Google's official iosched app, see http://code.google.com/p/iosched/
 *
 * @author freezy <freezy@xbmc.org>
 */
public class AudioDatabase extends SQLiteOpenHelper {

	private static final String TAG = AudioDatabase.class.getSimpleName();

	private static final String DATABASE_NAME = "audio.db";

	private static final int VER_LAUNCH = 3;
	private static final int DATABASE_VERSION = VER_LAUNCH;

	public interface Tables {

		String ALBUMS = "albums";
		String ARTISTS = "artists";

		String ALBUMS_JOIN_ARTISTS = ALBUMS + " LEFT OUTER JOIN " + ARTISTS + " ON " + Albums.PREFIX + Artists.ID  + "=" + Artists.ID;
	}

	/** {@code REFERENCES} clauses. */
	private interface References {
		String ARTIST_ID = "REFERENCES " + Tables.ARTISTS + "(" + Artists.ID + ")";
	}

	public AudioDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}



	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("CREATE TABLE " + Tables.ARTISTS + " ("
			+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ ArtistsColumns.UPDATED + " INTEGER NOT NULL,"
			+ ArtistsColumns.ID + " TEXT NOT NULL,"
			+ ArtistsColumns.NAME + " TEXT,"
			+ "UNIQUE (" + ArtistsColumns.ID + ") ON CONFLICT REPLACE)");

		db.execSQL("CREATE TABLE " + Tables.ALBUMS + " ("
			+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ AlbumsColumns.UPDATED + " INTEGER NOT NULL,"
			+ AlbumsColumns.ID + " TEXT NOT NULL,"
			+ AlbumsColumns.PREFIX + Artists.ID + " TEXT " + References.ARTIST_ID + ","
			+ AlbumsColumns.TITLE + " TEXT,"
			+ AlbumsColumns.YEAR + " TEXT,"
			+ AlbumsColumns.THUMBNAIL + " TEXT,"
			+ "UNIQUE (" + AlbumsColumns.ID + ") ON CONFLICT REPLACE)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "onUpgrade() from " + oldVersion + " to " + newVersion);

		int version = oldVersion;
		// do stuff here
		switch (version) {
			default:
				break;
		}

		// if not treated, drop + create.
		Log.d(TAG, "after upgrade logic, at version " + version);
		if (version != DATABASE_VERSION) {
			Log.w(TAG, "Destroying old data during upgrade");

			db.execSQL("DROP TABLE IF EXISTS " + Tables.ALBUMS);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.ARTISTS);

			onCreate(db);
		}
	}

}
