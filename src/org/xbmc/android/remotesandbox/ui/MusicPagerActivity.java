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

package org.xbmc.android.remotesandbox.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import org.xbmc.android.jsonrpc.service.AudioSyncService;
import org.xbmc.android.jsonrpc.service.AudioSyncService.RefreshObserver;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.util.google.DetachableResultReceiver;

import java.util.ArrayList;

public class MusicPagerActivity extends BaseFragmentTabsActivity {
	
	private static final String TAG = MusicPagerActivity.class.getSimpleName();
	private SyncStatusUpdaterFragment mSyncStatusUpdaterFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final FragmentManager fm = getSupportFragmentManager();
		
		mSyncStatusUpdaterFragment = (SyncStatusUpdaterFragment) fm.findFragmentByTag(SyncStatusUpdaterFragment.TAG);
		if (mSyncStatusUpdaterFragment == null) {
			mSyncStatusUpdaterFragment = new SyncStatusUpdaterFragment(mRefreshObservers);
			fm.beginTransaction().add(mSyncStatusUpdaterFragment, SyncStatusUpdaterFragment.TAG).commit();
			triggerRefresh();
		} else {
			mSyncStatusUpdaterFragment.setRefreshObservers(mRefreshObservers);
		}
	}

	@Override
	protected void onCreateTabs() {
		addTab("albums", "Albums", AlbumsFragment.class, R.drawable.tab_ic_album);
		addTab("artists", "Artists", ArtistsFragment.class, R.drawable.tab_ic_artist);
		addTab("files", "Files", SourcesFragment.class, R.drawable.tab_ic_folder);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_refresh) {
			triggerRefresh();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void triggerRefresh() {
		final long start = System.currentTimeMillis();
		Log.d(TAG, "Starting triggerRefresh()...");

		final Intent intent = new Intent(Intent.ACTION_SYNC, null, this, AudioSyncService.class);
		intent.putExtra(AudioSyncService.EXTRA_STATUS_RECEIVER, mSyncStatusUpdaterFragment.mReceiver);
		startService(intent);

/*		if (mTagStreamFragment != null) {
			mTagStreamFragment.refresh();
		}*/
		Log.d(TAG, "triggerRefresh() done in " + (System.currentTimeMillis() - start ) + "ms.");
	}

	private void updateRefreshStatus(boolean refreshing) {
		final long start = System.currentTimeMillis();
		Log.d(TAG, "Starting updateRefreshStatus()...");

		getActivityHelper().setRefreshActionButtonCompatState(refreshing);
		Log.d(TAG, "updateRefreshStatus() done in " + (System.currentTimeMillis() - start) + "ms.");
	}

	/**
	 * A non-UI fragment, retained across configuration changes, that updates
	 * its activity's UI when sync status changes.
	 */
	public static class SyncStatusUpdaterFragment extends Fragment implements DetachableResultReceiver.Receiver {

		public static final String TAG = SyncStatusUpdaterFragment.class.getName();

		private boolean mSyncing = false;
		private DetachableResultReceiver mReceiver;
		
		private ArrayList<RefreshObserver> mRefreshObservers = new ArrayList<RefreshObserver>(); 
		
		public SyncStatusUpdaterFragment(ArrayList<RefreshObserver> observers) {
			mRefreshObservers = observers;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setRetainInstance(true);
			mReceiver = new DetachableResultReceiver(new Handler());
			mReceiver.setReceiver(this);
		}

		/** {@inheritDoc} */
		public void onReceiveResult(int resultCode, Bundle resultData) {
			final long start = System.currentTimeMillis();
			Log.d(TAG, "Starting onReceiveResult()...");
			MusicPagerActivity activity = (MusicPagerActivity) getActivity();
			if (activity == null) {
				return;
			}

			switch (resultCode) {
				case AudioSyncService.STATUS_RUNNING: {
					mSyncing = true;
					break;
				}
				case AudioSyncService.STATUS_FINISHED: {
					mSyncing = false;
					Toast.makeText(activity, getString(R.string.toast_music_db_updated), Toast.LENGTH_LONG).show();
					for (AudioSyncService.RefreshObserver observer : mRefreshObservers) {
						observer.onRefreshed();
					}
					break;
				}
				case AudioSyncService.STATUS_ERROR: {
					// Error happened down in SyncService, show as toast.
					mSyncing = false;
					final String errorText = getString(R.string.toast_sync_error, resultData.getString(Intent.EXTRA_TEXT));
					Toast.makeText(activity, errorText, Toast.LENGTH_LONG).show();
					break;
				}
			}

			activity.updateRefreshStatus(mSyncing);
			Log.d(TAG, "onReceiveResult() done in " + (System.currentTimeMillis() - start) + "ms.");
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			((MusicPagerActivity) getActivity()).updateRefreshStatus(mSyncing);
		}
		
		public void setRefreshObservers(ArrayList<RefreshObserver> observers) {
			mRefreshObservers = observers;
		}
	}
}
