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
 * Contract class for interacting with {@link AudioProvider}. Unless otherwise
 * noted, all time-based fields are milliseconds since epoch and can be compared
 * against {@link System#currentTimeMillis()}.
 * <p>
 * The backing {@link android.content.ContentProvider} assumes that {@link Uri}
 * are generated using stronger {@link String} identifiers, instead of
 * {@code int} {@link BaseColumns#_ID} values, which are prone to shuffle during
 * sync.
 * <p>
 * This class, along with the other ones in this package was closely inspired by
 * Google's official iosched app, see http://code.google.com/p/iosched/
 *
 * @author freezy <freezy@xbmc.org>
 */
public class AudioContract {

	public static final String CONTENT_AUTHORITY = "org.xbmc.android.jsonrpc.audio";

	private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

	private static final String PATH_ALBUMS = "albums";
	private static final String PATH_ARTISTS = "artists";

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
		String UPDATED = "updated";
	}

	/**
	 * Constants for albums columns.
	 * @author freezy <freezy@xbmc.org>
	 */
	interface AlbumsColumns {
		final static String PREFIX = "album_";

		/** Unique string identifying this artist. */
		String ID = PREFIX + "id";
		/** Host instance */
		String HOST_ID = PREFIX + "host_id";
		/** Title describing this artist. */
		String TITLE = PREFIX + "title";
		/** Year the albums was released. */
		String YEAR = PREFIX + "year";
		/** Year the albums was released. */
		String THUMBNAIL = PREFIX + "thumbnail";

		String UPDATED = PREFIX + SyncColumns.UPDATED;
	}

	/**
	 * Constants for artists columns.
	 * @author freezy <freezy@xbmc.org>
	 */
	interface ArtistsColumns {
		final static String PREFIX = "artist_";

		/** Unique string identifying this artist. */
		String ID = PREFIX + "id";
		/** Host instance */
		String HOST_ID = PREFIX + "host_id";
		/** Title describing this artist. */
		String NAME = PREFIX + "title";

		String UPDATED = PREFIX + SyncColumns.UPDATED;
	}

	/**
	 * An album is a collection of songs with additional information such as a special "album artist",
	 * release year and associated genres.
	 *
	 * @author freezy <freezy@xbmc.org>
	 */
	public static class Albums implements AlbumsColumns, BaseColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ALBUMS).build();

		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.xbmc.album";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.xbmc.album";

		/** Default "ORDER BY" clause. */
		public static final String SORT_DEFAULT = AlbumsColumns.TITLE + " ASC";
		private static final String SORT_LATEST_N = AlbumsColumns.UPDATED + " DESC LIMIT ";

		/** Latest adde albums first */
		public static final String SORT_LATEST_FIRST = AlbumsColumns.ID + " DESC";

		public static String sortLatest(int n) {
			return SORT_LATEST_N + n;
		}

		/** Build {@link Uri} for requested {@link #ID}. */
		public static Uri buildAlbumUri(String albumId) {
			return CONTENT_URI.buildUpon().appendPath(albumId).build();
		}

		/** Read {@link #ID} from {@link org.xbmc.android.app.provider.AudioContract.Albums} {@link Uri}. */
		public static String getAlbumId(Uri uri) {
			return uri.getPathSegments().get(1);
		}
	}

	/**
	 * An artist in this context is only a string with an associated ID.
	 *
	 * @author freezy <freezy@xbmc.org>
	 */
	public static class Artists implements ArtistsColumns, BaseColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ARTISTS).build();

		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.xbmc.artist";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.xbmc.artist";

		/** Default "ORDER BY" clause. */
		public static final String DEFAULT_SORT = ArtistsColumns.NAME + " ASC";

		/** Count of {@link Albums} for a given {@link org.xbmc.android.app.provider.AudioContract.Artists}. */
		public static final String ALBUMS_COUNT = "albums_count";

		/** Build {@link Uri} for requested {@link #ID}. */
		public static Uri buildArtistUri(String albumId) {
			return CONTENT_URI.buildUpon().appendPath(albumId).build();
		}

		/** Read {@link #ID} from {@link org.xbmc.android.app.provider.AudioContract.Artists} {@link Uri}. */
		public static String getArtistId(Uri uri) {
			return uri.getPathSegments().get(1);
		}
	}

}
