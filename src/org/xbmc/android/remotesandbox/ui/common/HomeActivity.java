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
import org.xbmc.android.remotesandbox.ui.base.ReloadableActionBarActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class HomeActivity extends ReloadableActionBarActivity {

	private final static String TAG = HomeActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		/*
		 * final AccountManager am = AccountManager.get(this); final Account[]
		 * accounts = am.getAccountsByType(Constants.ACCOUNT_TYPE); for (Account
		 * account : accounts) { Log.e(TAG, "Address: " +
		 * am.getUserData(account, Constants.DATA_ADDRESS)); Log.e(TAG, "Port: "
		 * + am.getUserData(account, Constants.DATA_PORT)); }
		 */

	}

	@Override
	protected void onSyncPressed() {
		final long start = System.currentTimeMillis();
		Toast.makeText(this, R.string.toast_syncing_all, Toast.LENGTH_SHORT).show();
		final Intent intent = new Intent(Intent.ACTION_SYNC, null, this, AudioSyncService.class);
		intent.putExtra(AudioSyncService.EXTRA_STATUS_RECEIVER, mReceiver);
		getActionBarHelper().setRefreshActionItemState(true);
		startService(intent);
		Log.d(TAG, "Triggered global refresh in " + (System.currentTimeMillis() - start ) + "ms.");
	}

	@Override
	protected boolean onSyncResult(int resultCode, Bundle resultData) {
		final long start = System.currentTimeMillis();
		final boolean syncing;
		switch (resultCode) {
			case AudioSyncService.STATUS_RUNNING: {
				syncing = true;
				break;
			}
			case AudioSyncService.STATUS_FINISHED: {
				syncing = false;
				Toast.makeText(this, R.string.toast_synced_all, Toast.LENGTH_SHORT).show();
				break;
			}
			case AudioSyncService.STATUS_ERROR: {
				// Error happened down in SyncService, show as toast.
				syncing = false;
				final String errorText = getString(R.string.toast_sync_error, resultData.getString(Intent.EXTRA_TEXT));
				Toast.makeText(this, errorText, Toast.LENGTH_LONG).show();
				break;
			}
			default:
				syncing = false;
				break;
		}
		Log.d(TAG, "Global refresh callback processed in " + (System.currentTimeMillis() - start) + "ms.");
		return syncing;
	}
}
