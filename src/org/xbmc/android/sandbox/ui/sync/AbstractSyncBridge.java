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

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import org.xbmc.android.jsonrpc.service.AbstractSyncService.RefreshObserver;
import org.xbmc.android.jsonrpc.service.AudioSyncService;
import org.xbmc.android.sandbox.ui.RefreshableActivity;

import java.util.ArrayList;

/**
 * Reusable component that bridges asynchronous sync calls to the activity.
 * <p/>
 * There are two main reason for it being a non-UI fragment:
 *    <ol><li>It's retained across configuration changes</li>
 *    <li>Easy access to the activity</li></ol>
 *
 * It's up to the implementing class to support any callback receiver, for
 * instance when calling a sync service. All it has to do from the moment the
 * sync process is triggered by {@link #sync(android.os.Handler)} is running
 * {@link #updateSyncStatus(boolean)} and {@link #notifyObservers()} if there
 * is any data to be updated.
 * <p/>
 * Instantiation of any subclass is always triggered by
 * {@link RefreshableActivity#getSyncBridge()}. It's also at
 * {@link RefreshableActivity} where the fragment will be added to the
 * Activity's view.
 *
 * @see RefreshableActivity
 * @author freezy <freezy@xbmc.org>
 */
public abstract class AbstractSyncBridge extends Fragment {

	public static final String TAG = AbstractSyncBridge.class.getSimpleName();

	/**
	 * Keeps a local copy of the sync status.
	 * Synchronizing when true, finished when false.
	 */
	private boolean mSyncing = false;

	/**
	 * A reference to {@link RefreshableActivity}'s observers so we can
	 * call them when new data is available.
	 */
	protected ArrayList<RefreshObserver> mRefreshObservers = new ArrayList<RefreshObserver>();

	/**
	 * The tag name under which the fragment will be attached to the activity.
	 */
	final private String mTagName;

	/**
	 * Constructor.
	 * @param observers Reference to the activity's observers.
	 */
	public AbstractSyncBridge(String tagName, ArrayList<RefreshObserver> observers) {
		mRefreshObservers = observers;
		mTagName = tagName;
	}

	/**
	 * Triggers the synchronization process. Note that we're still on the main
	 * thread here and any longer actions should either use a threaded service
	 * or spawn a separate thread.
	 *
	 * @param handler A handler that can be used to post back UI updates
	 */
	public abstract void sync(Handler handler);

	/**
	 * Returns the {@link RefreshableActivity} where the fragment is
	 * attached to.
	 *
	 * @return Activity the fragment is attached to.
	 * @throws RuntimeException in case of wrong type or null.
	 */
	protected RefreshableActivity getReloadableActivity() {
		final Activity activity = getActivity();
		if (activity == null) {
			throw new RuntimeException("Bridge fragment's activity is null!");
		}
		if (activity instanceof RefreshableActivity) {
			return (RefreshableActivity)activity;
		}
		throw new RuntimeException("Sync bridge must be connected to a RefreshableActivity, " + activity.getClass().getSimpleName() + " found.");
	}

	/**
	 * Tells every attached observers to refresh themselves. Every subclass
	 * should run this upon successful result.
	 */
	protected void notifyObservers() {
		for (AudioSyncService.RefreshObserver observer : mRefreshObservers) {
			observer.onRefreshed();
		}
	}

	/**
	 * Updates the sync button in the UI.
	 * @param syncing
	 */
	protected void updateSyncStatus(boolean syncing) {
		mSyncing = syncing;
		getReloadableActivity().setSyncing(syncing);
	}

	/**
	 * Resets the observers, e.g. when an activity is recreated.
	 * @param observers
	 */
	public void setRefreshObservers(ArrayList<RefreshObserver> observers) {
		mRefreshObservers = observers;
	}

	/**
	 * Returns the tag name under which the fragment will be attached to the
	 * activity.
	 *
	 * @return Tag name
	 */
	public String getTagName() {
		return mTagName;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// make it retained over configuration changes
		setRetainInstance(true);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getReloadableActivity().setSyncing(mSyncing);
	}

}