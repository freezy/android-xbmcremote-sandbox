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

import org.json.JSONObject;
import org.xbmc.android.jsonrpc.NotificationManager;
import org.xbmc.android.jsonrpc.NotificationManager.NotificationObserver;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.remotesandbox.ui.base.ReloadableActionBarActivity;
import org.xbmc.android.remotesandbox.ui.sync.AbstractSyncBridge;
import org.xbmc.android.remotesandbox.ui.sync.AudioSyncBridge;

import android.os.Bundle;
import android.util.Log;

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
		
		final NotificationManager nm = NotificationManager.getInstance(getApplicationContext());
		
		nm.registerHandler(new NotificationObserver() {
			@Override
			public void handleNotification(JSONObject data) {
				Log.i(TAG, "Received notification: " + data.toString());
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
