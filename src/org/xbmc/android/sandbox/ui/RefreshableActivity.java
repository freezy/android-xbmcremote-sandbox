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

package org.xbmc.android.sandbox.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import org.xbmc.android.jsonrpc.service.AbstractSyncService.RefreshObserver;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.sandbox.ui.sync.AbstractSyncBridge;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;

import java.util.ArrayList;

/**
 * Manages the sync button's state and behavior.
 * <p>
 * Any activity extending this class must provide an {@link AbstractSyncBridge}
 * which is triggered when the user taps on the sync button. In order to do
 * this, {@link #getSyncBridge()} must be implemented.
 * <p>
 * This class also keeps track of any observers that may need to be updated
 * when new data arrives. It's however up to the {@link AbstractSyncBridge}
 * implementations to call the update through {@link AbstractSyncBridge#notifyObservers()}.
 * <p>
 * The sync button itself is automatically injected into the action bar.
 *
 * @author freezy <freezy@xbmc.org>
 */
public abstract class RefreshableActivity extends BaseActivity implements PullToRefreshAttacher.OnRefreshListener {

	private final static String TAG = RefreshableActivity.class.getSimpleName();
	private final static int MENU_REFRESH = 0x01;
	private PullToRefreshAttacher mPullToRefreshAttacher;

	/**
	 * If true, progress animation is displayed, otherwise static refresh button.
	 */
	private boolean mSyncing = false;

	/**
	 * List of observers to be called upon success.
	 */
	protected final ArrayList<RefreshObserver> mRefreshObservers = new ArrayList<RefreshObserver>();

	/**
	 * This can be used by subclasses to cast lists into arrays.
	 */
	protected final static AbstractSyncBridge[] BRIDGEARRAY_TYPE = new AbstractSyncBridge[0];

	private final int mContentViewRes;

	public RefreshableActivity(int titleRes, int contentViewRes) {
		super(titleRes);
		mContentViewRes = contentViewRes;
	}

	/**
	 * Initializes the sync bridges that are going to be used in this activity,
	 * meaning attaching them to the activity.
	 * <p>
	 * This is the method where where all of them should be initialized and
	 * stored somewhere.
	 *
	 * @return All sync bridges that are going to be used in this activity
	 */
	protected abstract AbstractSyncBridge[] initSyncBridges();

	/**
	 * Executed when the sync button is pressed. Returned object must already
	 * be instantiated by {@link #initSyncBridges()}, otherwise it won't be
	 * attached to the activity and will crash.
	 *
	 * @return The sync bridge object that should be used for refreshing data
	 */
	protected abstract AbstractSyncBridge getSyncBridge();


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(mContentViewRes);

		//setProgressBarIndeterminateVisibility(mSyncing ? Boolean.TRUE : Boolean.FALSE);

		mPullToRefreshAttacher = PullToRefreshAttacher.get(this);

		// Retrieve the PullToRefreshLayout from the content view
		PullToRefreshLayout ptrLayout = (PullToRefreshLayout) findViewById(R.id.ptr_layout);

		// Give the PullToRefreshAttacher to the PullToRefreshLayout, along with a refresh listener.
		ptrLayout.setPullToRefreshAttacher(mPullToRefreshAttacher, this);

		if (mSyncing) {
			mPullToRefreshAttacher.setRefreshing(true);
		}


		final FragmentManager fm = getSupportFragmentManager();

		// get all sync bridges and attach them so the activity.
		final AbstractSyncBridge[] bridges = initSyncBridges();
		FragmentTransaction ft = null;
		for (AbstractSyncBridge syncBridge : bridges) {
			if (ft == null) {
				ft = fm.beginTransaction();
			}
			final AbstractSyncBridge checkSyncBridge = (AbstractSyncBridge)fm.findFragmentByTag(syncBridge.getTagName());
			if (checkSyncBridge != null) {
				// This syncBridge already exists, so we'll replace it.
				ft.remove(checkSyncBridge);
				Log.i(TAG, "Updated refresh observers.");
				syncBridge.setRefreshObservers(mRefreshObservers);
			}
			Log.i(TAG, String.format("Added %s fragment to activity.", ((Object)syncBridge).getClass().getSimpleName()));
			ft.add(syncBridge, syncBridge.getTagName());
		}

		// commit fragment transactions
		if (ft != null) {
			ft.commit();
		}
	}

	@Override
	public void onRefreshStarted(View view) {
		getSyncBridge().sync(new Handler());
	}

	/**
	 * Updates the status of the sync button (progress vs. sync button).
	 * @param syncing
	 */
	public void setSyncing(boolean syncing) {
		if (!syncing) {
			mPullToRefreshAttacher.setRefreshComplete();
		}
		//setProgressBarIndeterminateVisibility(syncing ? Boolean.TRUE : Boolean.FALSE);
		mSyncing = syncing;
	}

	/**
	 * Registers a new observer.
	 * @param observer
	 */
	public synchronized void registerRefreshObserver(RefreshObserver observer) {
		mRefreshObservers.add(observer);
		Log.d(TAG, "Registered refresh observer.");
	}

	/**
	 * Unregisters an observer.
	 * @param observer
	 */
	public synchronized void unregisterRefreshObserver(RefreshObserver observer) {
		if (mRefreshObservers.remove(observer)) {
			Log.d(TAG, "Unregistered refresh observer.");
		} else {
			Log.w(TAG, "Could not find observer, NOT unregistering!");
		}
	}

}