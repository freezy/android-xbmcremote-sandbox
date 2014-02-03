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

import static org.xbmc.android.app.provider.VideoContract.*;

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

	private static final int VER_LAUNCH = 14;
	private static final int DATABASE_VERSION = VER_LAUNCH;

	public interface Tables {

		final String MOVIES = "movies";
		final String PEOPLE = "people";
		final String PEOPLE_MOVIE_CAST = PEOPLE + "_movie_cast";
		final String PEOPLE_MOVIE_DIRECTOR = PEOPLE + "_movie_director";
		final String GENRES = "genres";
		final String GENRES_MOVIE = GENRES + "_movie";
	}

	/** {@code REFERENCES} clauses. */
	private interface References {
		final String MOVIES_ID = "REFERENCES " + Tables.MOVIES + "(" + BaseColumns._ID + ")";
		final String PEOPLE_ID = "REFERENCES " + Tables.PEOPLE + "(" + BaseColumns._ID + ")";
		final String GENRE_ID = "REFERENCES " + Tables.GENRES + "(" + BaseColumns._ID + ")";
	}

	public interface Indexes {
		final String PEOPLE_MOVIECAST_MOVIE_REF = Tables.PEOPLE_MOVIE_CAST + "_" + MovieCastColumns.MOVIE_REF +
				" ON " + Tables.PEOPLE_MOVIE_CAST + "(" + MovieCastColumns.MOVIE_REF + ")";
		final String PEOPLE_MOVIECAST_PERSON_REF = Tables.PEOPLE_MOVIE_CAST + "_" + MovieCastColumns.PERSON_REF +
				" ON " + Tables.PEOPLE_MOVIE_CAST + "(" + MovieCastColumns.PERSON_REF + ")";

		final String PEOPLE_DIRECTOR_MOVIE_REF = Tables.PEOPLE_MOVIE_DIRECTOR + "_" + MovieDirectorColumns.MOVIE_REF +
				" ON " + Tables.PEOPLE_MOVIE_DIRECTOR + "(" + MovieDirectorColumns.MOVIE_REF + ")";
		final String PEOPLE_DIRECTOR_PERSON_REF = Tables.PEOPLE_MOVIE_DIRECTOR + "_" + MovieDirectorColumns.PERSON_REF +
				" ON " + Tables.PEOPLE_MOVIE_DIRECTOR + "(" + MovieDirectorColumns.PERSON_REF + ")";

		final String GENRE_MOVIE_MOVIE_REF = Tables.GENRES_MOVIE + "_" + MovieGenreColumns.MOVIE_REF +
				" ON " + Tables.GENRES_MOVIE + "(" + MovieGenreColumns.MOVIE_REF + ")";
		final String GENRE_MOVIE_GENRE_REF = Tables.GENRES_MOVIE + "_" + MovieGenreColumns.GENRE_REF +
				" ON " + Tables.GENRES_MOVIE + "(" + MovieGenreColumns.GENRE_REF + ")";
	}

	public VideoDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// movies
		db.execSQL("CREATE TABLE " + Tables.MOVIES + " ("
			+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ MoviesColumns.ID + " TEXT NOT NULL, "
			+ MoviesColumns.UPDATED + " INTEGER NOT NULL, "
			+ MoviesColumns.HOST_ID + " INTEGER NOT NULL, "
			+ MoviesColumns.TITLE + " TEXT, "
			+ MoviesColumns.SORTTITLE + " TEXT, "
			+ MoviesColumns.YEAR + " TEXT, "
			+ MoviesColumns.RATING + " TEXT, "
			+ MoviesColumns.VOTES + " INTEGER, "
			+ MoviesColumns.RUNTIME + " INTEGER, "
			+ MoviesColumns.TAGLINE + " TEXT, "
			+ MoviesColumns.PLOT + " TEXT, "
			+ MoviesColumns.MPAA + " TEXT, "
			+ MoviesColumns.IMDBNUMBER + " TEXT, "
			+ MoviesColumns.SETID + " INTEGER, "
			+ MoviesColumns.TRAILER + " TEXT, "
			+ MoviesColumns.TOP250 + " INTEGER, "
			+ MoviesColumns.THUMBNAIL + " TEXT, "
			+ MoviesColumns.FANART + " TEXT, "
			+ MoviesColumns.FILE + " TEXT, "
			+ MoviesColumns.RESUME + " INTEGER, "
			+ MoviesColumns.DATEADDED + " INTEGER, "
			+ MoviesColumns.LASTPLAYED + " INTEGER, "
			+ "UNIQUE (" + MoviesColumns.HOST_ID + ", " + MoviesColumns.ID + ") ON CONFLICT REPLACE)");

		// people
		db.execSQL("CREATE TABLE " + Tables.PEOPLE + " ("
			+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ PeopleColumns.HOST_ID + " INTEGER NOT NULL, "
			+ PeopleColumns.NAME + " TEXT, "
			+ PeopleColumns.THUMBNAIL + " TEXT, "
			+ "UNIQUE (" + PeopleColumns.HOST_ID + ", " + PeopleColumns.NAME + ") ON CONFLICT REPLACE)");

		// people <-> movie cast
		db.execSQL("CREATE TABLE " + Tables.PEOPLE_MOVIE_CAST + " ("
			+ MovieCastColumns.MOVIE_REF + " INTEGER " + References.MOVIES_ID + ", "
			+ MovieCastColumns.PERSON_REF + " INTEGER " + References.PEOPLE_ID + ", "
			+ MovieCastColumns.ROLE + " TEXT NOT NULL, "
			+ MovieCastColumns.SORT + " INTEGER NOT NULL"
			+ ")");
		db.execSQL("CREATE INDEX " + Indexes.PEOPLE_MOVIECAST_MOVIE_REF);
		db.execSQL("CREATE INDEX " + Indexes.PEOPLE_MOVIECAST_PERSON_REF);

		// people <-> movie director
		db.execSQL("CREATE TABLE " + Tables.PEOPLE_MOVIE_DIRECTOR + " ("
			+ MovieDirectorColumns.MOVIE_REF + " INTEGER " + References.MOVIES_ID + ", "
			+ MovieDirectorColumns.PERSON_REF + " INTEGER " + References.PEOPLE_ID + ", "
			+ "UNIQUE (" + MovieDirectorColumns.MOVIE_REF + ", " + MovieDirectorColumns.PERSON_REF + ") ON CONFLICT REPLACE)");
		db.execSQL("CREATE INDEX " + Indexes.PEOPLE_DIRECTOR_MOVIE_REF);
		db.execSQL("CREATE INDEX " + Indexes.PEOPLE_DIRECTOR_PERSON_REF);

		// genres
		db.execSQL("CREATE TABLE " + Tables.GENRES + " ("
			+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ GenreColumns.NAME + " TEXT, "
			+ "UNIQUE (" + GenreColumns.NAME + ") ON CONFLICT REPLACE)");

		// genres <-> movies
		db.execSQL("CREATE TABLE " + Tables.GENRES_MOVIE + " ("
			+ MovieGenreColumns.MOVIE_REF + " INTEGER " + References.MOVIES_ID + ", "
			+ MovieGenreColumns.GENRE_REF + " INTEGER " + References.GENRE_ID + ", "
			+ "UNIQUE (" + MovieGenreColumns.MOVIE_REF + ", " + MovieGenreColumns.GENRE_REF + ") ON CONFLICT REPLACE)");
		db.execSQL("CREATE INDEX " + Indexes.GENRE_MOVIE_MOVIE_REF);
		db.execSQL("CREATE INDEX " + Indexes.GENRE_MOVIE_GENRE_REF);
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
			db.execSQL("DROP TABLE IF EXISTS " + Tables.PEOPLE_MOVIE_CAST);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.PEOPLE_MOVIE_DIRECTOR);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.GENRES);
			db.execSQL("DROP TABLE IF EXISTS " + Tables.GENRES_MOVIE);
			onCreate(db);
		}
	}

}
