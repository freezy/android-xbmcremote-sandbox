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

import org.xbmc.android.jsonrpc.service.AudioSyncService;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.remotesandbox.ui.base.ActionBarActivity;
import org.xbmc.android.util.google.DetachableResultReceiver;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class HomeActivity extends ActionBarActivity implements DetachableResultReceiver.Receiver {

	private final static String TAG = HomeActivity.class.getSimpleName();
	
	private DetachableResultReceiver mReceiver;
	private boolean mSyncing = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		mReceiver = new DetachableResultReceiver(new Handler());
		mReceiver.setReceiver(this);
		getActionBarHelper().setRefreshActionItemState(mSyncing);

		/*
		 * final AccountManager am = AccountManager.get(this); final Account[]
		 * accounts = am.getAccountsByType(Constants.ACCOUNT_TYPE); for (Account
		 * account : accounts) { Log.e(TAG, "Address: " +
		 * am.getUserData(account, Constants.DATA_ADDRESS)); Log.e(TAG, "Port: "
		 * + am.getUserData(account, Constants.DATA_PORT)); }
		 */

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.refresh_menu_items, menu);

		// Calling super after populating the menu is necessary here to ensure
		// that the
		// action bar helpers have a chance to handle this event.
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_refresh:
				final long start = System.currentTimeMillis();
				Toast.makeText(this, R.string.toast_syncing_all, Toast.LENGTH_SHORT).show();
				final Intent intent = new Intent(Intent.ACTION_SYNC, null, this, AudioSyncService.class);
				intent.putExtra(AudioSyncService.EXTRA_STATUS_RECEIVER, mReceiver);
				getActionBarHelper().setRefreshActionItemState(true);
				startService(intent);
				Log.d(TAG, "Triggered global refresh in " + (System.currentTimeMillis() - start ) + "ms.");
			
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		final long start = System.currentTimeMillis();

		switch (resultCode) {
			case AudioSyncService.STATUS_RUNNING: {
				mSyncing = true;
				break;
			}
			case AudioSyncService.STATUS_FINISHED: {
				mSyncing = false;
				Toast.makeText(this, R.string.toast_synced_all, Toast.LENGTH_SHORT).show();
				break;
			}
			case AudioSyncService.STATUS_ERROR: {
				// Error happened down in SyncService, show as toast.
				mSyncing = false;
				final String errorText = getString(R.string.toast_sync_error, resultData.getString(Intent.EXTRA_TEXT));
				Toast.makeText(this, errorText, Toast.LENGTH_LONG).show();
				break;
			}
		}

		getActionBarHelper().setRefreshActionItemState(mSyncing);
		Log.d(TAG, "Global refresh callback processed in " + (System.currentTimeMillis() - start) + "ms.");
	}
}
