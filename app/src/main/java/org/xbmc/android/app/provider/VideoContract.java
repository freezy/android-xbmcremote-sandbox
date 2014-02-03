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

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Contract class for interacting with {@link org.xbmc.android.app.provider.VideoProvider}. Unless otherwise
 * noted, all time-based fields are milliseconds since epoch and can be compared
 * against {@link System#currentTimeMillis()}.
 * <p>
 * The backing {@link android.content.ContentProvider} assumes that {@link android.net.Uri}
 * are generated using stronger {@link String} identifiers, instead of
 * {@code int} {@link android.provider.BaseColumns#_ID} values, which are prone to shuffle during
 * sync.
 * <p>
 * This class, along with the other ones in this package was closely inspired by
 * Google's official iosched app, see http://code.google.com/p/iosched/
 *
 * @author freezy <freezy@xbmc.org>
 */
public class VideoContract {

	public static final String CONTENT_AUTHORITY = "org.xbmc.android.jsonrpc.video";

	private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

	static final String PATH_MOVIES = "movies";
	static final String PATH_PEOPLE = "people";
	static final String PATH_MOVIECAST = "moviecast";
	static final String PATH_GENRES = "genres";
	static final String PATH_MOVIEGENRES = "moviegenre";

	/**
	 * Special value for {@link SyncColumns#UPDATED} indicating that an entry
	 * has never been updated, or doesn't exist yet.
	 */
	public static final long UPDATED_NEVER = -2;

	/**
	 * Special value for {@link SyncColumns#UPDATED} indicating that the last
	 * update time is unknown, usually when inserted from a local file source.
	 */
	public static final long UPDATED_UNKNOWN = -1;

	/**
	 * Constants for synchronized columns.
	 * @author freezy <freezy@xbmc.org>
	 */
	public interface SyncColumns {
		/** Last time this entry was updated or synchronized. */
		final String UPDATED = "updated";
	}

	/**
	 * Constants for movie columns.
	 * @author freezy <freezy@xbmc.org>
	 */
	interface MoviesColumns {
		final static String PREFIX = "movie_";
		final String ID = PREFIX + "id";
		final String HOST_ID = PREFIX + "host_id";
		final String TITLE = PREFIX + "title";
		final String YEAR = PREFIX + "year";
		final String RATING = PREFIX + "rating";
		final String RUNTIME = PREFIX + "runtime";
		final String THUMBNAIL = PREFIX + "thumbnail";
		final String SORTTITLE = PREFIX + "sorttitle";
		final String VOTES = PREFIX + "votes";
		final String TAGLINE = PREFIX + "tagline";
		final String PLOT = PREFIX + "plot";
		final String MPAA = PREFIX + "mpaa";
		final String IMDBNUMBER = PREFIX + "imdbnumber";
		final String SETID = PREFIX + "setid";
		final String TRAILER = PREFIX + "trailer";
		final String TOP250 = PREFIX + "top250";
		final String FANART = PREFIX + "fanart";
		final String FILE = PREFIX + "file";
		final String RESUME = PREFIX + "resume";
		final String DATEADDED = PREFIX + "dateadded";
		final String LASTPLAYED = PREFIX + "lastplayed";
		final String UPDATED = PREFIX + SyncColumns.UPDATED;
	}

	/**
	 * Constants for people columns.
	 * @author freezy <freezy@xbmc.org>
	 */
	interface PeopleColumns {
		final static String PREFIX = "person_";
		final String HOST_ID = PREFIX + "host_id";
		final String NAME = PREFIX + "name";
		final String THUMBNAIL = PREFIX + "thumbnail";
	}

	/**
	 * Constants for movie cast columns.
	 * @author freezy <freezy@xbmc.org>
	 */
	interface MovieCastColumns {
		final static String PREFIX = "person_cast_";
		final String MOVIE_REF = PREFIX + MoviesColumns.PREFIX + "id";
		final String PERSON_REF = PREFIX + PeopleColumns.PREFIX + "id";
		final String ROLE = PREFIX + "role";
		final String SORT = PREFIX + "sort";
	}

	/**
	 * Constants for movie director columns.
	 * @author freezy <freezy@xbmc.org>
	 */
	interface MovieDirectorColumns {
		final static String PREFIX = "person_director_";
		final String MOVIE_REF = PREFIX + MoviesColumns.PREFIX + "id";
		final String PERSON_REF = PREFIX + PeopleColumns.PREFIX + "id";
	}

	/**
	 * Constants for genre columns.
	 * @author freezy <freezy@xbmc.org>
	 */
	interface GenreColumns {
		final static String PREFIX = "genre_";
		final String NAME = PREFIX + "name";
	}

	/**
	 * Constants for movie genre columns.
	 * @author freezy <freezy@xbmc.org>
	 */
	interface MovieGenreColumns {
		final static String PREFIX = "genre_movie_";
		final String MOVIE_REF = PREFIX + MoviesColumns.PREFIX + "id";
		final String GENRE_REF = PREFIX + GenreColumns.PREFIX + "id";
	}

	/**
	 * The movie library, excluding TV or MV.
	 *
	 * @author freezy <freezy@xbmc.org>
	 */
	public static class Movies implements MoviesColumns, BaseColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

		public static final String CONTENT_TYPE      = "vnd.android.cursor.dir/vnd.xbmc.movie";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.xbmc.movie";

		/** Default "ORDER BY" clause. */
		public static final String DEFAULT_SORT = MoviesColumns.TITLE + " ASC";
		/** Latest added movies first */
		public static final String SORT_LATEST_FIRST = MoviesColumns.ID + " DESC";
		private static final String SORT_LATEST_N = MoviesColumns.DATEADDED + " DESC LIMIT ";

		public static String sortLatest(int n) {
			return SORT_LATEST_N + n;
		}

		/** Build {@link android.net.Uri} for requested {@link #ID}. */
		public static Uri buildMovieUri(String movieId) {
			return CONTENT_URI.buildUpon().appendPath(movieId).build();
		}

		/** Read {@link #ID} from {@link org.xbmc.android.app.provider.VideoContract.Movies} {@link android.net.Uri}. */
		public static String getMovieId(Uri uri) {
			return uri.getPathSegments().get(1);
		}
	}

	/**
	 * People can be actors, directors or writers.
	 */
	public static class People implements PeopleColumns, BaseColumns {

		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_PEOPLE).build();

		public static final String CONTENT_TYPE      = "vnd.android.cursor.dir/vnd.xbmc.person";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.xbmc.person";

		public static Uri buildPersonUri(long personId) {
			return CONTENT_URI.buildUpon().appendPath(String.valueOf(personId)).build();
		}

		public static int getPersonId(Uri uri) {
			return Integer.parseInt(uri.getPathSegments().get(1));
		}
	}

	/**
	 * Links a person as a character to a movie.
	 */
	public static class MovieCast implements MovieCastColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_MOVIECAST).build();
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.xbmc.movie.cast";
	}

	/**
	 * A genre, basically just a name and an ID. Global for all hosts, TV and movies.
	 */
	public static class Genres implements GenreColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_GENRES).build();
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.xbmc.video.genre";
		public static int getGenreId(Uri uri) {
			return Integer.parseInt(uri.getPathSegments().get(1));
		}
		public static Uri buildGenreUri(long genreId) {
			return CONTENT_URI.buildUpon().appendPath(String.valueOf(genreId)).build();
		}
	}

	/**
	 * Links a genre to a movie.
	 */
	public static class MovieGenres implements MovieGenreColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_MOVIEGENRES).build();
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.xbmc.movie.genre";
	}

}
