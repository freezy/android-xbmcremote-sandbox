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

package org.xbmc.android.app.service;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.provider.BaseColumns;
import android.util.Log;
import de.greenrobot.event.EventBus;
import org.xbmc.android.app.event.DataItemSynced;
import org.xbmc.android.app.event.DataSync;
import org.xbmc.android.app.injection.Injector;
import org.xbmc.android.app.io.audio.AlbumHandler;
import org.xbmc.android.app.io.audio.ArtistHandler;
import org.xbmc.android.app.io.video.MovieDetailsHandler;
import org.xbmc.android.app.io.video.MovieHandler;
import org.xbmc.android.app.manager.HostManager;
import org.xbmc.android.app.provider.VideoContract;
import org.xbmc.android.app.provider.VideoDatabase;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.api.call.AudioLibrary;
import org.xbmc.android.jsonrpc.api.call.VideoLibrary;
import org.xbmc.android.jsonrpc.api.model.AudioModel.AlbumFields;
import org.xbmc.android.jsonrpc.api.model.VideoModel.MovieFields;
import org.xbmc.android.jsonrpc.io.ConnectionManager;
import org.xbmc.android.jsonrpc.io.JsonHandler;

import javax.inject.Inject;
import java.util.LinkedList;

/**
 * Background {@link Service} that synchronizes data living in {@link android.content.ContentProvider}.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class SyncService extends Service implements OnSyncedListener {

	private static final String TAG = SyncService.class.getSimpleName();

	@Inject protected EventBus bus;
	@Inject protected ConnectionManager cm;
	@Inject protected HostManager hostManager;

	public static final String EXTRA_STATUS_RECEIVER = "org.xbmc.android.jsonprc.extra.STATUS_RECEIVER";

	public static final String EXTRA_SYNC_MUSIC = "org.xbmc.android.jsonprc.extra.SYNC_MUSIC";
	public static final String EXTRA_SYNC_MOVIES = "org.xbmc.android.jsonprc.extra.SYNC_MOVIES";

	public static final int STATUS_RUNNING = 0x1;
	public static final int STATUS_ERROR = 0x2;
	public static final int STATUS_FINISHED = 0x3;

	private long start = 0;
	private int hostId;
	private final LinkedList<SyncItem> items = new LinkedList<SyncItem>();

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "Starting SyncService...");
		Injector.inject(this);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "Starting quering...");
		hostId = hostManager.getActiveHost().getId();
		bus.post(new DataSync(DataSync.STARTED));
		synchronized (items) {
			if (intent.hasExtra(EXTRA_SYNC_MUSIC)) {
				items.add(new SyncItem("Artists", DataItemSynced.ARTISTS, new AudioLibrary.GetArtists(), new ArtistHandler(hostId), this));
				items.add(new SyncItem("Albums", DataItemSynced.ALBUMS, new AudioLibrary.GetAlbums(
						AlbumFields.TITLE, AlbumFields.ARTISTID, AlbumFields.YEAR, AlbumFields.THUMBNAIL
				), new AlbumHandler(hostId), this));
			}
			if (intent.hasExtra(EXTRA_SYNC_MOVIES)) {
				items.add(new SyncItem("Movies", DataItemSynced.MOVIES, new VideoLibrary.GetMovies(
						MovieFields.TITLE, MovieFields.THUMBNAIL, MovieFields.YEAR, MovieFields.RATING,
						MovieFields.GENRE, MovieFields.RUNTIME
				), new MovieHandler(hostId), fetchMovieDetails));
			}
		}

		// start quering...
		start = System.currentTimeMillis();
		next();
		return START_STICKY;
	}

	private Cursor moviesCursor;

	/**
	 * Loops through movies in local database and updates each entry with cast details.
	 */
	private final OnSyncedListener fetchMovieDetails = new OnSyncedListener() {
		@Override
		public void onItemSynced() {
			if (moviesCursor == null) {
				moviesCursor = getContentResolver().query(VideoContract.Movies.CONTENT_URI, MoviesQuery.PROJECTION, null, null, null);
			}
			if (moviesCursor.moveToNext()) {
				items.add(new SyncItem("Movie Details for \"" + moviesCursor.getString(MoviesQuery.TITLE) + "\"", DataItemSynced.MOVIES,
						new VideoLibrary.GetMovieDetails(moviesCursor.getInt(MoviesQuery.ID), MovieFields.CAST, MovieFields.DIRECTOR),
						new MovieDetailsHandler(hostId, moviesCursor.getInt(MoviesQuery._ID)),
						this
				));
			} else {
				moviesCursor.close();
				MovieDetailsHandler.initCache();
			}
			next();
		}
	};

	@Override
	public void onItemSynced() {
		next();
	}

	private void next() {
		synchronized (items) {
			if (!items.isEmpty()) {
				final SyncItem next = items.pop();
				next.sync();
			} else {
				Log.i(TAG, "All done after " + (System.currentTimeMillis() - start) + "ms.");
				bus.post(new DataSync(DataSync.FINISHED));
				cm.disconnect();
				stopSelf();
			}
		}
	}

	private class SyncItem {

		private final String what;
		private final int eventCode;
		private final AbstractCall<?> call;
		private final JsonHandler handler;
		private long itemStart;
		private final OnSyncedListener callback;

		public SyncItem(String what, int eventCode, AbstractCall<?> call, JsonHandler handler, OnSyncedListener callback) {
			this.what = what;
			this.eventCode = eventCode;
			this.call = call;
			this.handler = handler;
			this.callback = callback;
		}

		public String getWhat() {
			return what;
		}

		public void sync() {
			itemStart = System.currentTimeMillis();
			cm.call(call, handler, new ConnectionManager.HandlerCallback() {
				@Override
				public void onFinish() {
					Log.i(TAG, what + " successfully synced in " + (System.currentTimeMillis() - itemStart) + "ms.");
					bus.post(new DataItemSynced(eventCode));
					callback.onItemSynced();
				}
				@Override
				public void onError(String message, String hint) {
					bus.post(new DataSync(DataSync.FAILED, message, hint));
					stopSelf();
				}
			});
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/**
	 * {@link org.xbmc.android.app.provider.VideoContract.Movies}
	 * query parameters.
	 */
	private interface MoviesQuery {

		String[] PROJECTION = {
				VideoDatabase.Tables.MOVIES + "." + BaseColumns._ID,
				VideoContract.Movies.ID,
				VideoContract.Movies.TITLE
		};

		int _ID = 0;
		int ID = 1;
		int TITLE = 2;
	}
}
