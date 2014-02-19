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

package org.xbmc.android.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import org.xbmc.android.app.event.DataItemSynced;
import org.xbmc.android.app.event.DataSync;
import org.xbmc.android.app.manager.HostManager;
import org.xbmc.android.app.manager.SettingsManager;
import org.xbmc.android.app.service.SyncService;
import org.xbmc.android.remotesandbox.R;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

import javax.inject.Inject;

/**
 * The landing page of the app when at least one host has been configured.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class HomeActivity extends BaseActivity implements OnRefreshListener {

	private static final String TAG = HomeActivity.class.getSimpleName();
	private static final String STATE_DATA_AVAIL = "dataAvail";

	@Inject protected EventBus bus;
	@Inject protected HostManager hostManager;
	@Inject protected SettingsManager settingsManager;

	@InjectView(R.id.ptr_layout) PullToRefreshLayout pullToRefreshLayout;

	private Fragment musicFragment;
	private Fragment movieFragment;
	private Fragment refreshNoticeFragment;

	private boolean dataAvail = false;

	public HomeActivity() {
		super(R.string.title_home, R.string.ic_logo, R.layout.activity_home);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ButterKnife.inject(this);
		bus.register(this);

		final FragmentManager fm = getSupportFragmentManager();
		musicFragment = fm.findFragmentById(R.id.music_fragment);
		movieFragment = fm.findFragmentById(R.id.movie_fragment);
		refreshNoticeFragment = fm.findFragmentById(R.id.refresh_notice_fragment);

		if (savedInstanceState != null) {
			// Restore value of members from saved state
			dataAvail = savedInstanceState.getBoolean(STATE_DATA_AVAIL);
		}

		if (!dataAvail) {
			if (settingsManager.hasSynced()) {
				fm.beginTransaction().show(musicFragment).show(movieFragment).hide(refreshNoticeFragment).commit();
			} else {
				fm.beginTransaction().hide(musicFragment).hide(movieFragment).show(refreshNoticeFragment).commit();
			}
		}

		// setup pull-to-refresh action bar
		ActionBarPullToRefresh.from(this)
			// Mark All Children as pullable
			.allChildrenArePullable()
			// Set the OnRefreshListener
			.listener(this)
			// Finally commit the setup to our PullToRefreshLayout
			.setup(pullToRefreshLayout);

		if (!hostManager.hasHost()) {
			final Intent intent = new Intent(this, WelcomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivityForResult(intent, 1);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroy() {
		bus.unregister(this);
		super.onDestroy();
	}

	/**
	 * Event bus callback. Ran when video and audio sync finishes.
	 * @param event Event data
	 */
	public void onEvent(DataItemSynced event) {
		if (!dataAvail) {
			settingsManager.setSynced(true);
			final FragmentManager fm = getSupportFragmentManager();
			fm.beginTransaction().show(musicFragment).show(movieFragment).hide(refreshNoticeFragment).commit();
		} else {
			dataAvail = true;
		}
	}

	/**
	 * Event bus callback. Ran when both video and audio sync start, finish or fail.
	 * @param event Event data
	 */
	public void onEvent(DataSync event) {
		if (event.hasFailed()) {
			Toast.makeText(getApplicationContext(), event.getErrorMessage() + " " + event.getErrorHint(), Toast.LENGTH_LONG).show();
		}

		if (event.hasFinished()) {
			Toast.makeText(getApplicationContext(), "Successfully synced.", Toast.LENGTH_LONG).show();
		}

		if (!event.hasStarted()) {
			pullToRefreshLayout.setRefreshing(false);
		}
	}

	@Override
	public void onRefreshStarted(View view) {

		final long start = System.currentTimeMillis();
		startService(new Intent(Intent.ACTION_SYNC, null, this, SyncService.class)
			.putExtra(SyncService.EXTRA_SYNC_MOVIES, true)
			.putExtra(SyncService.EXTRA_SYNC_MUSIC, true)
		);
		Log.d(TAG, "Triggered refresh in " + (System.currentTimeMillis() - start) + "ms.");
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putBoolean(STATE_DATA_AVAIL, dataAvail);
		super.onSaveInstanceState(savedInstanceState);
	}


}
