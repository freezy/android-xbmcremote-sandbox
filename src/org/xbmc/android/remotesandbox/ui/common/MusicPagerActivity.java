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

import java.util.HashMap;

import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.remotesandbox.ui.base.BaseFragmentTabsActivity;
import org.xbmc.android.remotesandbox.ui.sync.AbstractSyncBridge;
import org.xbmc.android.remotesandbox.ui.sync.AudioSyncBridge;
import org.xbmc.android.remotesandbox.ui.sync.FilesBridge;

/**
 * Music activity contains multiple tabs. 
 * 
 * So far, there are:
 *    - Albums
 *    - Artists
 *    - Files
 *    
 * Albums and files are read from the local database, while files are read live from XBMC.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public class MusicPagerActivity extends BaseFragmentTabsActivity {
	
//	private static final String TAG = MusicPagerActivity.class.getSimpleName();
	
	private static final String TAB_ALBUMS = "albums";
	private static final String TAB_ARTISTS = "artists";
	private static final String TAB_FILES = "files";
	
	private static final Integer SYNC_AUDIO = 0x01;
	private static final Integer SYNC_FILES = 0x02;
	
	private final HashMap<Integer, AbstractSyncBridge> mSyncBridges = new HashMap<Integer, AbstractSyncBridge>();

	@Override
	protected void onCreateTabs() {
		addTab(TAB_ALBUMS, R.string.tab_music_albums, AlbumsFragment.class, R.drawable.tab_ic_album);
		addTab(TAB_ARTISTS, R.string.tab_music_artists, ArtistsFragment.class, R.drawable.tab_ic_artist);
		addTab(TAB_FILES, R.string.tab_music_files, SourcesFragment.class, R.drawable.tab_ic_folder);
	}
	
	@Override
	protected AbstractSyncBridge[] initSyncBridges() {
		// use temp local var for faster access
		final HashMap<Integer, AbstractSyncBridge> syncBridges = mSyncBridges;
		syncBridges.put(SYNC_AUDIO, new AudioSyncBridge(mRefreshObservers));
		syncBridges.put(SYNC_FILES, new FilesBridge(mRefreshObservers));
		return syncBridges.values().toArray(BRIDGEARRAY_TYPE);
	}

	@Override
	protected AbstractSyncBridge getSyncBridge() {
		// depending on selected tab, return a different sync bridge
		if (getCurrentTabTag().equals(TAB_FILES)) {
			return mSyncBridges.get(SYNC_FILES);
		} else {
			return mSyncBridges.get(SYNC_AUDIO);
		}
	}

}
