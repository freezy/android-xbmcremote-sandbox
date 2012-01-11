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
import org.xbmc.android.util.google.TabsAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

/**
 * Contains a view pager with multiple tabs through whose the user can swipe
 * through.
 * <p/>
 * Every tab is a {@link Fragment}. In order to add those, call {@link #addTab(String, int, Class, int)}
 * in {@link #onCreateTabs()}.
 * <p/>
 * Note that currently this activity is designed for phones, not tablets.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public abstract class BaseFragmentTabsActivity extends ReloadableActionBarActivity {

	private final static String TAG = BaseFragmentTabsActivity.class.getSimpleName();
	private final static String EXTRA_TAB = "tab";
	
	private TabHost mTabHost;
	private TabWidget mTabWidget;
	private TabsAdapter mTabsAdapter;
	private ViewPager mViewPager;
	
	/**
	 * Called in <code>onCreate</code> when the fragment constituting this
	 * activity is needed. The returned fragment's arguments will be set to the
	 * intent used to invoke this activity.
	 */
	protected abstract void onCreateTabs();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final long start = System.currentTimeMillis();
		setContentView(R.layout.activity_fragment_tabs_pager);
		
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabWidget = (TabWidget) findViewById(android.R.id.tabs);
		mViewPager = (ViewPager) findViewById(R.id.tab_pager);

		mTabHost.setup();
		mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPager);

		if (savedInstanceState == null) {
			onCreateTabs();
		} else {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString(EXTRA_TAB));
		}
		
		final HorizontalScrollView scroller = (HorizontalScrollView)findViewById(R.id.tab_scroller);
		mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				final int position = mTabHost.getCurrentTab();
				final View tab = mTabWidget.getChildTabViewAt(position);
				scroller.smoothScrollTo(tab.getLeft(), 0);
				mViewPager.setCurrentItem(position);
			}
		});
        Log.d(TAG, "onCreate() done in " + (System.currentTimeMillis() - start ) + "ms.");

	}
	
	/**
	 * Adds a new tab to the pager. Run this only from {@link #onCreateTabs()}.
	 *  
	 * @param key A unique name for the tab
	 * @param labelResId Resource ID for the label shown in the tab
	 * @param fragment Which fragment should be instantiated
	 * @param imageResId Resource ID for the icon shown in the tab
	 */
	protected void addTab(String key, int labelResId, Class<?> fragment, int imageResId) {
		final long start = System.currentTimeMillis();
		Log.d(TAG, "Starting addTab()...");
		final View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab, mTabWidget, false);
		final TextView title = (TextView) tabIndicator.findViewById(R.id.tab_title);
		final ImageView icon = (ImageView) tabIndicator.findViewById(R.id.tab_icon);
		title.setText(labelResId);
		icon.setImageResource(imageResId);
		mTabsAdapter.addTab(mTabHost.newTabSpec(key).setIndicator(tabIndicator), null, fragment, null);
        Log.d(TAG, "addTab() done in " + (System.currentTimeMillis() - start ) + "ms.");
	}
	
	/**
	 * Returns the tag of the currently selected tab.
	 * @return
	 */
	protected String getCurrentTabTag() {
		return mTabHost.getCurrentTabTag();
	}

	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(EXTRA_TAB, mTabHost.getCurrentTabTag());
	}

}
