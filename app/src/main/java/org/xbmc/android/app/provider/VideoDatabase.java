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
import org.xbmc.android.app.provider.VideoContract.MoviesColumns;

import static org.xbmc.android.app.provider.VideoContract.MoviesCastColumns;
import static org.xbmc.android.app.provider.VideoContract.PeopleColumns;

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

	private static final int VER_LAUNCH = 12;
	private static final int DATABASE_VERSION = VER_LAUNCH;

	public interface Tables {

		final String MOVIES = "movies";
		final String PEOPLE = "people";
		final String PEOPLE_MOVIECAST = PEOPLE + "_moviecast";
	}

	/** {@code REFERENCES} clauses. */
	private interface References {
		final String MOVIES_ID = "REFERENCES " + Tables.MOVIES + "(" + BaseColumns._ID + ")";
		final String PEOPLE_ID = "REFERENCES " + Tables.PEOPLE + "(" + BaseColumns._ID + ")";
	}

	public interface Indexes {
		final String PEOPLE_MOVIECAST_MOVIE_REF = Tables.PEOPLE_MOVIECAST + "_" + MoviesCastColumns.MOVIE_REF +
				" ON " + Tables.PEOPLE_MOVIECAST + "(" + MoviesCastColumns.MOVIE_REF + ")";
		final String PEOPLE_MOVIECAST_PERSON_REF = Tables.PEOPLE_MOVIECAST + "_" + MoviesCastColumns.PERSON_REF +
				" ON " + Tables.PEOPLE_MOVIECAST + "(" + MoviesCastColumns.PERSON_REF + ")";
	}

	public VideoDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("CREATE TABLE " + Tables.MOVIES + " ("
			+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ MoviesColumns.ID + " TEXT NOT NULL,"
			+ MoviesColumns.UPDATED + " INTEGER NOT NULL,"
			+ MoviesColumns.HOST_ID + " INTEGER NOT NULL,"
			+ MoviesColumns.TITLE + " TEXT,"
			+ MoviesColumns.SORTTITLE + " TEXT,"
			+ MoviesColumns.YEAR + " TEXT,"
			+ MoviesColumns.RATING + " TEXT,"
			+ MoviesColumns.VOTES + " INTEGER,"
			+ MoviesColumns.RUNTIME + " INTEGER,"
			+ MoviesColumns.TAGLINE + " TEXT,"
			+ MoviesColumns.PLOT + " TEXT,"
			+ MoviesColumns.MPAA + " TEXT,"
			+ MoviesColumns.IMDBNUMBER + " TEXT,"
			+ MoviesColumns.SETID + " INTEGER,"
			+ MoviesColumns.TRAILER + " TEXT,"
			+ MoviesColumns.TOP250 + " INTEGER,"
			+ MoviesColumns.THUMBNAIL + " TEXT,"
			+ MoviesColumns.FANART + " TEXT,"
			+ MoviesColumns.FILE + " TEXT,"
			+ MoviesColumns.RESUME + " INTEGER,"
			+ MoviesColumns.DATEADDED + " INTEGER,"
			+ MoviesColumns.LASTPLAYED + " INTEGER,"
			+ "UNIQUE (" + MoviesColumns.HOST_ID + ", " + MoviesColumns.ID + ") ON CONFLICT REPLACE)");

		db.execSQL("CREATE TABLE " + Tables.PEOPLE + " ("
			+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ PeopleColumns.HOST_ID + " INTEGER NOT NULL,"
			+ PeopleColumns.NAME + " TEXT,"
			+ PeopleColumns.THUMBNAIL + " TEXT,"
			+ "UNIQUE (" + PeopleColumns.HOST_ID + ", " + PeopleColumns.NAME + ") ON CONFLICT REPLACE)");

		db.execSQL("CREATE TABLE " + Tables.PEOPLE_MOVIECAST + " ("
			+ MoviesCastColumns.MOVIE_REF + " INTEGER " + References.MOVIES_ID + ", "
			+ MoviesCastColumns.PERSON_REF + " INTEGER " + References.PEOPLE_ID + ", "
			+ MoviesCastColumns.ROLE + " TEXT NOT NULL, "
			+ MoviesCastColumns.SORT + " INTEGER NOT NULL"
			+ ")");
		db.execSQL("CREATE INDEX " + Indexes.PEOPLE_MOVIECAST_MOVIE_REF);
		db.execSQL("CREATE INDEX " + Indexes.PEOPLE_MOVIECAST_PERSON_REF);

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
			db.execSQL("DROP TABLE IF EXISTS " + Tables.PEOPLE);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.PEOPLE_MOVIECAST);
			onCreate(db);
		}
	}

}
