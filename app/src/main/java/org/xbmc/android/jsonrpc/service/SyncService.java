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

package org.xbmc.android.jsonrpc.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import de.greenrobot.event.EventBus;
import org.xbmc.android.app.event.DataItemSynced;
import org.xbmc.android.app.event.DataSync;
import org.xbmc.android.app.injection.Injector;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.api.call.AudioLibrary;
import org.xbmc.android.jsonrpc.api.call.VideoLibrary;
import org.xbmc.android.jsonrpc.api.model.AudioModel.AlbumFields;
import org.xbmc.android.jsonrpc.api.model.VideoModel.MovieFields;
import org.xbmc.android.jsonrpc.io.ConnectionManager;
import org.xbmc.android.jsonrpc.io.ConnectionManager.HandlerCallback;
import org.xbmc.android.jsonrpc.io.JsonHandler;
import org.xbmc.android.jsonrpc.io.audio.AlbumHandler;
import org.xbmc.android.jsonrpc.io.audio.ArtistHandler;
import org.xbmc.android.jsonrpc.io.video.MovieHandler;

import javax.inject.Inject;
import java.util.LinkedList;

/**
 * Background {@link Service} that synchronizes data living in {@link android.content.ContentProvider}.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class SyncService extends Service {

	private static final String TAG = SyncService.class.getSimpleName();

	@Inject protected EventBus bus;
	@Inject protected ConnectionManager cm;

	public static final String EXTRA_STATUS_RECEIVER = "org.xbmc.android.jsonprc.extra.STATUS_RECEIVER";

	public static final String EXTRA_SYNC_MUSIC = "org.xbmc.android.jsonprc.extra.SYNC_MUSIC";
	public static final String EXTRA_SYNC_MOVIES = "org.xbmc.android.jsonprc.extra.SYNC_MOVIES";

	public static final int STATUS_RUNNING = 0x1;
	public static final int STATUS_ERROR = 0x2;
	public static final int STATUS_FINISHED = 0x3;

	private long start = 0;
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
		bus.post(new DataSync(DataSync.STARTED));
		synchronized (items) {
			if (intent.hasExtra(EXTRA_SYNC_MUSIC)) {
				items.add(new SyncItem("Artists", DataItemSynced.ARTISTS, new AudioLibrary.GetArtists(), new ArtistHandler()));
				items.add(new SyncItem("Albums", DataItemSynced.ALBUMS, new AudioLibrary.GetAlbums(
						AlbumFields.TITLE, AlbumFields.ARTISTID, AlbumFields.YEAR, AlbumFields.THUMBNAIL
				), new AlbumHandler()));
			}
			if (intent.hasExtra(EXTRA_SYNC_MOVIES)) {
				items.add(new SyncItem("Movies", DataItemSynced.MOVIES, new VideoLibrary.GetMovies(
						MovieFields.TITLE, MovieFields.THUMBNAIL, MovieFields.YEAR, MovieFields.RATING,
						MovieFields.GENRE, MovieFields.RUNTIME
				), new MovieHandler()));
			}
		}

		// start quering...
		start = System.currentTimeMillis();
		next();
		return START_STICKY;
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

		public SyncItem(String what, int eventCode, AbstractCall<?> call, JsonHandler handler) {
			this.what = what;
			this.eventCode = eventCode;
			this.call = call;
			this.handler = handler;
		}

		public String getWhat() {
			return what;
		}

		public void sync() {
			itemStart = System.currentTimeMillis();
			cm.call(call, handler, new HandlerCallback() {
				@Override
				public void onFinish() {
					Log.i(TAG, what + " successfully synced in " + (System.currentTimeMillis() - itemStart) + "ms.");
					bus.post(new DataItemSynced(eventCode));
					next();
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
}
