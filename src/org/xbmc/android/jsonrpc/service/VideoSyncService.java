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
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.util.Log;
import org.xbmc.android.jsonrpc.api.call.VideoLibrary;
import org.xbmc.android.jsonrpc.api.model.VideoModel;
import org.xbmc.android.jsonrpc.config.HostConfig;
import org.xbmc.android.jsonrpc.io.ConnectionManager;
import org.xbmc.android.jsonrpc.io.ConnectionManager.HandlerCallback;
import org.xbmc.android.jsonrpc.io.video.MovieHandler;

/**
 * Background {@link android.app.Service} that synchronizes data living in
 * {@link VideoProvider}.
 * <p>
 * This class, along with the other ones in this package was closely inspired by
 * Google's official iosched app, see http://code.google.com/p/iosched/
 *
 * @author freezy <freezy@xbmc.org>
 */
public class VideoSyncService extends Service {

	public static final String URL = "http://192.168.0.100:8080/jsonrpc";

	private static final String TAG = VideoSyncService.class.getSimpleName();

	public static final String EXTRA_STATUS_RECEIVER = "org.xbmc.android.jsonprc.extra.STATUS_RECEIVER";

	public static final int STATUS_RUNNING = 0x1;
	public static final int STATUS_ERROR = 0x2;
	public static final int STATUS_FINISHED = 0x3;

	private long mStart = 0;

	private ConnectionManager mCm = null;
	private ResultReceiver mReceiver = null;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "Starting VideoSyncService...");
		mCm = new ConnectionManager(getApplicationContext(), new HostConfig("192.168.0.100"));
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

		// start quering...
		mStart = System.currentTimeMillis();
		syncMovies();
		return START_STICKY;
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
				VideoSyncService.this.onError(message + " " + hint);
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

	public interface RefreshObserver {
		public void onRefreshed();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
