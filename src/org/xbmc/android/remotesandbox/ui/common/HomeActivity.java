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

package org.xbmc.android.remotesandbox.ui.common;

import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.api.call.AudioLibrary;
import org.xbmc.android.jsonrpc.api.call.VideoLibrary;
import org.xbmc.android.jsonrpc.api.model.AudioModel.AlbumDetails;
import org.xbmc.android.jsonrpc.api.model.LibraryModel;
import org.xbmc.android.jsonrpc.api.model.LibraryModel.GenreDetails;
import org.xbmc.android.jsonrpc.io.ApiCallback;
import org.xbmc.android.jsonrpc.io.ConnectionManager;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.remotesandbox.ui.base.ReloadableActionBarActivity;
import org.xbmc.android.remotesandbox.ui.sync.AbstractSyncBridge;
import org.xbmc.android.remotesandbox.ui.sync.AudioSyncBridge;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeActivity extends ReloadableActionBarActivity {

	private final static String TAG = HomeActivity.class.getSimpleName();
	
	/**
	 * Sync bridge for global refresh.
	 */
	private AudioSyncBridge mSyncBridge;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(null);
		setContentView(R.layout.activity_home);
		final Button testBtn = (Button)findViewById(R.id.home_testbtn);
		testBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			final ConnectionManager cm = new ConnectionManager(getApplicationContext());
			cm.call(new AudioLibrary.GetAlbums(null, null), new ApiCallback<AlbumDetails>() {
				@Override
				public void onResponse(AbstractCall<AlbumDetails> apiCall) {
					final AlbumDetails details = apiCall.getResult();
					Log.i(TAG, "Got response from " + apiCall.getName() + ". First album fetched: " + details.label);
				}
				@Override
				public void onError(int code, String message) {
				}
			});
			
			cm.call(new VideoLibrary.GetGenres("movie"), new ApiCallback<LibraryModel.GenreDetails>() {
				@Override
				public void onResponse(AbstractCall<GenreDetails> apiCall) {
					final GenreDetails details = apiCall.getResult();
					Log.i(TAG, "Got response from " + apiCall.getName() + ". First genre fetched: " + details.label);
				}
				@Override
				public void onError(int code, String message) {
					// TODO Auto-generated method stub
					
				}
			});
				
/*				try {
					final RemoteExecutor remoteExecutor = new RemoteExecutor(getContentResolver());
				Log.e(TAG, "Something went wrong: " + message);
					AudioLibrary.GetArtists getArtistsAPI;
					getArtistsAPI = new AudioLibrary.GetArtists(false, null);
					final NotificationManager nm = new NotificationManager(getApplicationContext());
					remoteExecutor.execute(nm, getArtistsAPI, new ArtistHandler());
				} catch (ApiException e) {
					Log.e(TAG, "API Exception: " + e.getMessage(), e);
				}*/
			}
		});
		

		/*
		 * final AccountManager am = AccountManager.get(this); final Account[]
		 * accounts = am.getAccountsByType(Constants.ACCOUNT_TYPE); for (Account
		 * account : accounts) { Log.e(TAG, "Address: " +
		 * am.getUserData(account, Constants.DATA_ADDRESS)); Log.e(TAG, "Port: "
		 * + am.getUserData(account, Constants.DATA_PORT)); }
		 */
	}
	
	@Override
	protected AbstractSyncBridge[] initSyncBridges() {
		mSyncBridge = new AudioSyncBridge(mRefreshObservers); 
		return new AbstractSyncBridge[]{ mSyncBridge };
	}

	@Override
	protected AbstractSyncBridge getSyncBridge() {
		return mSyncBridge;
	}

}
