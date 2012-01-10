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

package org.xbmc.android.remotesandbox.ui.sync;

import java.util.ArrayList;

import org.xbmc.android.jsonrpc.service.AudioSyncService;
import org.xbmc.android.jsonrpc.service.AudioSyncService.RefreshObserver;
import org.xbmc.android.remotesandbox.ui.base.ReloadableActionBarActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Reusuable component that bridges calling the service from an activity and
 * treating the result in the user interface.
 * 
 * A non-UI fragment, retained across configuration changes, that updates its
 * activity's UI when sync status changes.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public abstract class AbstractSyncBridge extends Fragment {

	public static final String TAG = AbstractSyncBridge.class.getSimpleName();
	
	/**
	 * Excecuted when the sync button is pressed.
	 * 
	 * @param activity Reference to current activity
	 * @param actionbarHelper Reference to actionbar helper
	 * @param receiver Reference to detachable receiver
	 */
	public abstract void sync(Handler handler);

	private boolean mSyncing = false;

	protected ArrayList<RefreshObserver> mRefreshObservers = new ArrayList<RefreshObserver>();

	public AbstractSyncBridge(ArrayList<RefreshObserver> observers) {
		mRefreshObservers = observers;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.e(TAG, "onActivityCreated()");
		super.onActivityCreated(savedInstanceState);
		getReloadableActivity().setSyncing(mSyncing);
		//((ReloadableActionBarActivity)getActivity()).setSyncing(mSyncing);
	}
	
	protected ReloadableActionBarActivity getReloadableActivity() {
		final Activity activity = getActivity();
		if (activity == null) {
			throw new RuntimeException("Bridge fragment is null!");
		}
		if (activity instanceof ReloadableActionBarActivity) {
			return (ReloadableActionBarActivity)activity;
		}
		throw new RuntimeException("Sync bridge must be connected to a ReloadableActionBarActivity, " + activity.getClass().getSimpleName() + " found.");
	}
	
	protected void notifyObservers() {
		for (AudioSyncService.RefreshObserver observer : mRefreshObservers) {
			observer.onRefreshed();
		}
	}
	
	protected void updateSyncStatus(boolean syncing) {
		getReloadableActivity().setSyncing(syncing);
	}

	public void setRefreshObservers(ArrayList<RefreshObserver> observers) {
		mRefreshObservers = observers;
	}
	

}
