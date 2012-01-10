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

import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.remotesandbox.ui.sync.AbstractSyncBridge;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Action bar activity that contains a sync action.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public abstract class ReloadableActionBarActivity extends ActionBarActivity {

	private boolean mSyncing = false;
	
	/**
	 * Excecuted when the sync button is pressed.
	 */
	protected abstract AbstractSyncBridge getSyncBridge();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBarHelper().setRefreshActionItemState(mSyncing);
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
				getSyncBridge().sync(this, new Handler());
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void setSyncing(boolean syncing) {
		getActionBarHelper().setRefreshActionItemState(syncing);
		mSyncing = syncing;
	}

	
}
