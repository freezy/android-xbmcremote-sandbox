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

import org.xbmc.android.jsonrpc.api.call.AudioLibrary;
import org.xbmc.android.jsonrpc.api.model.AudioModel;
import org.xbmc.android.jsonrpc.io.RemoteExecutor;
import org.xbmc.android.jsonrpc.io.audio.AlbumHandler;
import org.xbmc.android.jsonrpc.io.audio.ArtistHandler;
import org.xbmc.android.jsonrpc.provider.AudioProvider;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

/**
 * Background {@link Service} that synchronizes data living in
 * {@link AudioProvider}.
 * <p>
 * This class, along with the other ones in this package was closely inspired by
 * Google's official iosched app, see http://code.google.com/p/iosched/
 * 
 * @author freezy <freezy@xbmc.org>
 */
public class AudioSyncService extends IntentService {

	public static final String URL = "http://192.168.0.100:8080/jsonrpc";
	
	private static final String TAG = AudioSyncService.class.getSimpleName();

	public static final String EXTRA_STATUS_RECEIVER = "org.xbmc.android.jsonprc.extra.STATUS_RECEIVER";

	public static final int STATUS_RUNNING = 0x1;
	public static final int STATUS_ERROR = 0x2;
	public static final int STATUS_FINISHED = 0x3;

	private RemoteExecutor mRemoteExecutor;

	public AudioSyncService() {
		super(TAG);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "Starting onCreate()...");
		final long start = System.currentTimeMillis();

		mRemoteExecutor = new RemoteExecutor(getContentResolver());
		Log.d(TAG, "onCreate() done in " + (System.currentTimeMillis() - start) + "ms.");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		final long start = System.currentTimeMillis();
		Log.d(TAG, "Starting onHandleIntent(intent=" + intent.toString() + ")...");

		final ResultReceiver receiver = intent.getParcelableExtra(EXTRA_STATUS_RECEIVER);
		if (receiver != null) {
			receiver.send(STATUS_RUNNING, Bundle.EMPTY);
		}

		try {
			final long startRemote = System.currentTimeMillis();

			final AudioLibrary.GetArtists getArtistsAPI = new AudioLibrary.GetArtists(false, null);
			final AudioLibrary.GetAlbums getAlbumsAPI = new AudioLibrary.GetAlbums(null, null, 
					AudioModel.AlbumFields.TITLE, AudioModel.AlbumFields.ARTISTID, AudioModel.AlbumFields.YEAR);
			
			mRemoteExecutor.execute(getApplicationContext(), AudioSyncService.URL, getArtistsAPI, new ArtistHandler());
			mRemoteExecutor.execute(getApplicationContext(), AudioSyncService.URL, getAlbumsAPI, new AlbumHandler());

			Log.i(TAG, "All done, remote sync took " + (System.currentTimeMillis() - startRemote) + "ms.");

		} catch (Exception e) {
			Log.e(TAG, "Problem while syncing.", e);

			if (receiver != null) {
				// Pass back error to surface listener
				final Bundle bundle = new Bundle();
				bundle.putString(Intent.EXTRA_TEXT, e.getMessage());
				receiver.send(STATUS_ERROR, bundle);
			}
		}

		// Announce success to any surface listener
		if (receiver != null) {
			receiver.send(STATUS_FINISHED, Bundle.EMPTY);
		}

		Log.d(TAG, "Sync finished!");
		Log.d(TAG, "onHandleIntent() done in " + (System.currentTimeMillis() - start) + "ms.");
	}

	public interface RefreshObserver {
		public void onRefreshed();
	}

}
