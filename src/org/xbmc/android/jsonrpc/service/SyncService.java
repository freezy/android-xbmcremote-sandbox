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

import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.api.call.AudioLibrary;
import org.xbmc.android.jsonrpc.api.call.VideoLibrary;
import org.xbmc.android.jsonrpc.api.model.AudioModel.AlbumFields;
import org.xbmc.android.jsonrpc.api.model.VideoModel;
import org.xbmc.android.jsonrpc.config.HostConfig;
import org.xbmc.android.jsonrpc.io.ConnectionManager;
import org.xbmc.android.jsonrpc.io.ConnectionManager.HandlerCallback;
import org.xbmc.android.jsonrpc.io.JsonHandler;
import org.xbmc.android.jsonrpc.io.audio.AlbumHandler;
import org.xbmc.android.jsonrpc.io.audio.ArtistHandler;
import org.xbmc.android.jsonrpc.io.video.MovieHandler;
import org.xbmc.android.jsonrpc.provider.AudioProvider;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.util.Log;

import java.util.LinkedList;

/**
 * Background {@link Service} that synchronizes data living in
 * {@link AudioProvider}.
 * <p>
 * This class, along with the other ones in this package was closely inspired by
 * Google's official iosched app, see http://code.google.com/p/iosched/
 *
 * @author freezy <freezy@xbmc.org>
 */
public class SyncService extends Service {

	public static final String HOST = "192.168.0.100";
	public static final String URL = "http://" + HOST + ":8080/jsonrpc";

	private static final String TAG = SyncService.class.getSimpleName();

	public static final String EXTRA_STATUS_RECEIVER = "org.xbmc.android.jsonprc.extra.STATUS_RECEIVER";

	public static final String EXTRA_SYNC_MUSIC = "org.xbmc.android.jsonprc.extra.SYNC_MUSIC";
	public static final String EXTRA_SYNC_MOVIES = "org.xbmc.android.jsonprc.extra.SYNC_MOVIES";

	public static final int STATUS_RUNNING = 0x1;
	public static final int STATUS_ERROR = 0x2;
	public static final int STATUS_FINISHED = 0x3;

	protected ResultReceiver mReceiver = null;

	private long mStart = 0;

	private ConnectionManager mCm = null;

	private final LinkedList<SyncItem> mItems = new LinkedList<SyncItem>();


	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "Starting SyncService...");
		mCm = new ConnectionManager(getApplicationContext(), new HostConfig(HOST));
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "Starting quering...");
		mReceiver = intent.getParcelableExtra(EXTRA_STATUS_RECEIVER);
		if (mReceiver != null) {
			mReceiver.send(STATUS_RUNNING, Bundle.EMPTY);
		} else {
			Log.w(TAG, "Receiver is null, cannot post back data!");
		}

		synchronized (mItems) {
			if (intent.hasExtra(EXTRA_SYNC_MUSIC)) {
				mItems.add(new SyncItem("Artists", new AudioLibrary.GetArtists(), new ArtistHandler()));
				mItems.add(new SyncItem("Albums", new AudioLibrary.GetAlbums(
						AlbumFields.TITLE, AlbumFields.ARTISTID, AlbumFields.YEAR,
						AlbumFields.THUMBNAIL
				), new AlbumHandler()));
			}
			if (intent.hasExtra(EXTRA_SYNC_MOVIES)) {
				mItems.add(new SyncItem("Movies", new VideoLibrary.GetMovies(
						VideoModel.MovieFields.THUMBNAIL, VideoModel.MovieFields.YEAR,
						VideoModel.MovieFields.RATING, VideoModel.MovieFields.GENRE,
						VideoModel.MovieFields.RUNTIME
				), new MovieHandler()));
			}
		}

		// start quering...
		mStart = System.currentTimeMillis();
		next();
		return START_STICKY;
	}

	private void next() {
		synchronized (mItems) {
			if (!mItems.isEmpty()) {
				final SyncItem next = mItems.pop();
				next.sync();
			} else {
				Log.i(TAG, "All done after " + (System.currentTimeMillis() - mStart) + "ms.");
				if (mReceiver != null) {
					// Pass back result to surface listener
					mReceiver.send(STATUS_FINISHED, null);
				}
				mCm.disconnect();
				stopSelf();
			}
		}
	}

	private class SyncItem {

		private final String mWhat;
		private final AbstractCall<?> mCall;
		private final JsonHandler mHandler;
		private long mItemStart;

		public SyncItem(String what, AbstractCall<?> call, JsonHandler handler) {
			mWhat = what;
			mCall = call;
			mHandler = handler;
		}

		public String getWhat() {
			return mWhat;
		}

		public void sync() {
			mItemStart = System.currentTimeMillis();
			mCm.call(mCall, mHandler, new HandlerCallback() {
				@Override
				public void onFinish() {
					Log.i(TAG, mWhat + " successfully synced in " + (System.currentTimeMillis() - mItemStart) + "ms.");
					next();
				}

				@Override
				public void onError(String message, String hint) {
					SyncService.this.onError(message + " " + hint);
					stopSelf();
				}
			});
		}

	}

	private void syncArtists() {
		final AudioLibrary.GetArtists getArtistsCall = new AudioLibrary.GetArtists();
		mCm.call(getArtistsCall, new ArtistHandler(), new HandlerCallback() {
			@Override
			public void onFinish() {
				Log.i(TAG, "Artists successfully synced in " + (System.currentTimeMillis() - mStart) + "ms, starting albums.");
				syncAlbums();
			}

			@Override
			public void onError(String message, String hint) {
				SyncService.this.onError(message + " " + hint);
				stopSelf();
			}

		});
	}

	private void syncAlbums() {
		final AudioLibrary.GetAlbums getAlbumsCall = new AudioLibrary.GetAlbums(
				AlbumFields.TITLE, AlbumFields.ARTISTID, AlbumFields.YEAR,
				AlbumFields.THUMBNAIL
		);

		mCm.call(getAlbumsCall, new AlbumHandler(), new HandlerCallback() {

			@Override
			public void onFinish() {
				Log.i(TAG, "Albums successfully synced too! Total time: " + (System.currentTimeMillis() - mStart) + "ms.");
				if (mReceiver != null) {
					// Pass back result to surface listener
					mReceiver.send(STATUS_FINISHED, null);
				}
				mCm.disconnect();
				stopSelf();
			}

			@Override
			public void onError(String message, String hint) {
				SyncService.this.onError(message + " " + hint);
				stopSelf();
			}
		});
	}

	private void syncMovies() {
		final VideoLibrary.GetMovies getMoviesCall = new VideoLibrary.GetMovies(
				VideoModel.MovieFields.THUMBNAIL, VideoModel.MovieFields.YEAR,
				VideoModel.MovieFields.RATING, VideoModel.MovieFields.GENRE,
				VideoModel.MovieFields.RUNTIME
		);
		mCm.call(getMoviesCall, new MovieHandler(), new HandlerCallback() {
			@Override
			public void onFinish() {
				Log.i(TAG, "Movies successfully synced in " + (System.currentTimeMillis() - mStart) + "ms, starting albums.");
			}

			@Override
			public void onError(String message, String hint) {
				SyncService.this.onError(message + " " + hint);
				stopSelf();
			}

		});
	}

	public void onError(String message) {
		if (mReceiver != null) {
			// Pass back error to surface listener
			final Bundle bundle = new Bundle();
			bundle.putString(Intent.EXTRA_TEXT, message);
			mReceiver.send(STATUS_ERROR, bundle);
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public interface RefreshObserver {
		public void onRefreshed();
	}
}
