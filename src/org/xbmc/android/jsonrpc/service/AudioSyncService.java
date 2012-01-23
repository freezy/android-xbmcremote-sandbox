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

import org.json.JSONObject;
import org.xbmc.android.jsonrpc.NotificationManager;
import org.xbmc.android.jsonrpc.api.call.AudioLibrary;
import org.xbmc.android.jsonrpc.api.model.AudioModel;
import org.xbmc.android.jsonrpc.io.audio.AlbumHandler;
import org.xbmc.android.jsonrpc.io.audio.ArtistHandler;
import org.xbmc.android.jsonrpc.provider.AudioProvider;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
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
public class AudioSyncService extends Service implements NotificationManager.ApiCallback {

	public static final String URL = "http://192.168.0.100:8080/jsonrpc";
	
	private static final String TAG = AudioSyncService.class.getSimpleName();

	public static final String EXTRA_STATUS_RECEIVER = "org.xbmc.android.jsonprc.extra.STATUS_RECEIVER";

	public static final int STATUS_RUNNING = 0x1;
	public static final int STATUS_ERROR = 0x2;
	public static final int STATUS_FINISHED = 0x3;
	
	private long mStart = 0;

	private NotificationManager mNm = null;
	private ResultReceiver mReceiver = null;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "Starting onCreate()...");
		mNm = new NotificationManager(getApplicationContext());
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		Log.d(TAG, "Starting AudioSyncService...");

		mReceiver = intent.getParcelableExtra(EXTRA_STATUS_RECEIVER);
		if (mReceiver != null) {
			mReceiver.send(STATUS_RUNNING, Bundle.EMPTY);
		} else {
			Log.w(TAG, "Receiver is null, cannot post back data!");
		}
		
		// start quering...
		onResponse(INIT, null);
	}
	
	private final static int INIT = 0x00; 
	private final static int CALL_ARTISTS = 0x01; 
	private final static int CALL_ALBUMS = 0x02; 
	
	@Override
	public void onResponse(int what, JSONObject response) {
		try {
			switch (what) {
				case INIT:
					mStart = System.currentTimeMillis();
					// 1. run GetArtists
					final AudioLibrary.GetArtists getArtistsCall = new AudioLibrary.GetArtists(false, null);
					mNm.call(CALL_ARTISTS, getArtistsCall, this);
					break;
					
				case CALL_ARTISTS:
					// 1. parse GetArtists
					new ArtistHandler().applyResult(response, getContentResolver());
					// 2. run GetAlbums
					final AudioLibrary.GetAlbums getAlbumsCall = new AudioLibrary.GetAlbums(null, null, 
							AudioModel.AlbumFields.TITLE, AudioModel.AlbumFields.ARTISTID, AudioModel.AlbumFields.YEAR);
					mNm.call(CALL_ALBUMS, getAlbumsCall, this);
					break;
					
				case CALL_ALBUMS:
					// 1. parse GetAlbums
					new AlbumHandler().applyResult(response, getContentResolver());
					Log.i(TAG, "All done, remote sync took " + (System.currentTimeMillis() - mStart) + "ms.");
					// Announce success to any surface listener
					if (mReceiver != null) {
						mReceiver.send(STATUS_FINISHED, Bundle.EMPTY);
					}
					Log.d(TAG, "Sync finished!");
				default:
			}
		} catch (Exception e) {
			Log.e(TAG, "Problem while syncing.", e);
			if (mReceiver != null) {
				// Pass back error to surface listener
				final Bundle bundle = new Bundle();
				bundle.putString(Intent.EXTRA_TEXT, e.getMessage());
				mReceiver.send(STATUS_ERROR, bundle);
			}
		}
	}
	
	@Override
	public void onError(int code, String message) {
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
