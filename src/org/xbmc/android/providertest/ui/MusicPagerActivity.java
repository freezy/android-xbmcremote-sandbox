package org.xbmc.android.providertest.ui;

import org.xbmc.android.providertest.R;
import org.xbmc.android.util.TabsAdapter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

public class MusicPagerActivity extends FragmentActivity {
	TabHost mTabHost;
	ViewPager mViewPager;
	TabsAdapter mTabsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_tabs_pager);
		
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		final HorizontalScrollView scroller = (HorizontalScrollView) findViewById(R.id.tab_scroller);
		final TabWidget tabWidget = (TabWidget) findViewById(android.R.id.tabs);
		mTabHost.setup();

		mViewPager = (ViewPager) findViewById(R.id.tab_pager);
		mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPager);

		addTab(tabWidget, "albums", "Albums", AlbumsFragment.class, R.drawable.tab_ic_album);
		addTab(tabWidget, "artists", "Artists", AlbumsFragment.class, R.drawable.tab_ic_artist);
		addTab(tabWidget, "genres", "Genres", AlbumsFragment.class, R.drawable.tab_ic_folder);
		
		
		mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				final int position = mTabHost.getCurrentTab();
				final View tab = tabWidget.getChildTabViewAt(position);
				scroller.smoothScrollTo(tab.getLeft(), 0);
				mViewPager.setCurrentItem(position);
			}
		});

		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}
	}
	
	private void addTab(TabWidget tabWidget, String tabspec, String label, Class<?> c, int imgRes) {
		final View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab, tabWidget, false);
		final TextView title = (TextView) tabIndicator.findViewById(R.id.tab_title);
		final ImageView icon = (ImageView) tabIndicator.findViewById(R.id.tab_icon);
		title.setText(label);
		icon.setImageResource(imgRes);
		mTabsAdapter.addTab(mTabHost.newTabSpec(tabspec).setIndicator(tabIndicator), label, c, null);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("tab", mTabHost.getCurrentTabTag());
	}
}
