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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;
import org.xbmc.android.app.provider.VideoContract.*;

/**
 * Helper for managing {@link android.database.sqlite.SQLiteDatabase} that stores data for
 * {@link org.xbmc.android.app.provider.VideoProvider}.
 * <p>
 * This class, along with the other ones in this package was closely inspired by
 * Google's official iosched app, see http://code.google.com/p/iosched/
 *
 * @author freezy <freezy@xbmc.org>
 */
public class VideoDatabase extends SQLiteOpenHelper {

	private static final String TAG = VideoDatabase.class.getSimpleName();

	private static final String DATABASE_NAME = "video.db";

	private static final int VER_LAUNCH = 2;
	private static final int DATABASE_VERSION = VER_LAUNCH;

	public interface Tables {

		String MOVIES = "movies";
	}

	public VideoDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("CREATE TABLE " + Tables.MOVIES + " ("
			+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ MoviesColumns.UPDATED + " INTEGER NOT NULL,"
			+ MoviesColumns.ID + " TEXT NOT NULL,"
			+ MoviesColumns.TITLE + " TEXT,"
			+ MoviesColumns.YEAR + " TEXT,"
			+ MoviesColumns.GENRE + " TEXT,"
			+ MoviesColumns.RATING + " TEXT,"
			+ MoviesColumns.RUNTIME + " TEXT,"
			+ MoviesColumns.THUMBNAIL + " TEXT,"
			+ "UNIQUE (" + MoviesColumns.ID + ") ON CONFLICT REPLACE)");
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
			db.execSQL("DROP TABLE IF EXISTS " + Tables.MOVIES);
			onCreate(db);
		}
	}

}
