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

package org.xbmc.android.remotesandbox.ui.base;

import org.xbmc.android.jsonrpc.service.AudioSyncService.RefreshObserver;

import android.support.v4.app.ListFragment;


/**
 * Abstract superclass for reloadable Lists.
 * 
 * All it does right now is registering the observers.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public abstract class ReloadableListFragment extends ListFragment {
	
	/**
	 * Returns the observer of the fragment so the bridge can notify them.
	 * @return
	 */
	protected abstract RefreshObserver getRefreshObserver();
	
	/**
	 * Makes the fragment refresh itself.
	 */
	protected abstract void restartLoader();
	
	@Override
	public void onResume() {
		super.onResume();
		if (getActivity() instanceof ReloadableActionBarActivity) {
			((ReloadableActionBarActivity)getActivity()).registerRefreshObserver(getRefreshObserver());
		}
		restartLoader();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if (getActivity() instanceof ReloadableActionBarActivity) {
			((ReloadableActionBarActivity)getActivity()).unregisterRefreshObserver(getRefreshObserver());
		}
	}
}
