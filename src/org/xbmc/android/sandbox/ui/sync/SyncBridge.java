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

package org.xbmc.android.sandbox.ui.sync;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;
import org.xbmc.android.jsonrpc.service.SyncService;
import org.xbmc.android.jsonrpc.service.SyncService.RefreshObserver;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.sandbox.ui.RefreshableActivity;
import org.xbmc.android.util.google.DetachableResultReceiver;

import java.util.ArrayList;

/**
 * Manages synchronization in the UI.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class SyncBridge extends AbstractSyncBridge implements DetachableResultReceiver.Receiver {

	private final static String TAG = SyncBridge.class.getSimpleName();

	public final static int SECTION_MUSIC = 0x01;
	public final static int SECTION_MOVIES = 0x02;

	public final static SparseArray<String> SECTIONS = new SparseArray<String>();
	static {
		SECTIONS.put(SECTION_MUSIC, SyncService.EXTRA_SYNC_MUSIC);
		SECTIONS.put(SECTION_MOVIES, SyncService.EXTRA_SYNC_MOVIES);
	}

	/**
	 * Sync status
	 */
	private boolean mSyncing = false;
	private final int[] mSections;

	public SyncBridge(ArrayList<RefreshObserver> observers, int... sections) {
		super(TAG, observers);
		mSections = sections;
	}

	@Override
	public void sync(Handler handler) {

		final long start = System.currentTimeMillis();
		final RefreshableActivity activity = getReloadableActivity();
		final DetachableResultReceiver receiver = new DetachableResultReceiver(handler);
		receiver.setReceiver(this);

		Toast.makeText(activity, R.string.toast_syncing_all, Toast.LENGTH_SHORT).show();
		final Intent intent = new Intent(Intent.ACTION_SYNC, null, activity, SyncService.class);
		intent.putExtra(SyncService.EXTRA_STATUS_RECEIVER, receiver);
		for (int section : mSections) {
			intent.putExtra(SECTIONS.get(section), true);
		}
		activity.setSyncing(true);
		activity.startService(intent);
		Log.d(TAG, "Triggered sync in " + (System.currentTimeMillis() - start ) + "ms.");

	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		final long start = System.currentTimeMillis();
		final RefreshableActivity activity = getReloadableActivity();
		final boolean syncing;
		switch (resultCode) {
			case SyncService.STATUS_RUNNING: {
				Log.d(TAG, "Got event: STATUS_RUNNING");
				syncing = true;
				break;
			}
			case SyncService.STATUS_FINISHED: {
				Log.d(TAG, "Got event: STATUS_FINISHED");

				// only notify if we're still syncing (i.e. no errors occurred).
				if (mSyncing) {
					Toast.makeText(activity, R.string.toast_synced_all, Toast.LENGTH_SHORT).show();
					notifyObservers();
				}
				syncing = false;
				break;
			}
			case SyncService.STATUS_ERROR: {
				Log.d(TAG, "Got event: STATUS_ERROR");
				// Error happened down in SyncService, show as toast.
				if (resultData == null) {
					Log.e(TAG, "CANNOT READ RESULT DATA!!!");
				} else {
					final String errorText = activity.getString(R.string.toast_sync_error, resultData.getString(Intent.EXTRA_TEXT));
					Toast.makeText(activity, errorText, Toast.LENGTH_LONG).show();
				}
				syncing = false;
				break;
			}
			default:
				syncing = false;
				break;
		}
		Log.d(TAG, "Refresh callback processed in " + (System.currentTimeMillis() - start) + "ms.");
		updateSyncStatus(syncing);
		mSyncing = syncing;
	}
}