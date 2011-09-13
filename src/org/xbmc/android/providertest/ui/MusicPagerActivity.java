package org.xbmc.android.providertest.ui;

import org.xbmc.android.providertest.R;

public class MusicPagerActivity extends BaseFragmentTabsActivity {

	@Override
	protected void onCreateTabs() {
		addTab("albums", "Albums", AlbumsFragment.class, R.drawable.tab_ic_album);
		addTab("artists", "Artists", AlbumsFragment.class, R.drawable.tab_ic_artist);
		addTab("files", "Files", AlbumsFragment.class, R.drawable.tab_ic_folder);
	}
}
