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

package org.xbmc.android.remotesandbox.ui;

import java.util.ArrayList;

import org.xbmc.android.jsonrpc.service.AudioSyncService.RefreshObserver;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.util.google.TabsAdapter;

import android.content.Intent;
import android.os.Bundle;
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
 * A {@link BaseActivity} that simply contains a single fragment. The intent
 * used to invoke this activity is forwarded to the fragment as arguments during
 * fragment instantiation. Derived activities should only need to implement
 * {@link com.google.android.apps.iosched.ui.BaseSinglePaneActivity#onCreatePane()}
 * .
 */
public abstract class BaseFragmentTabsActivity extends BaseActivity {
	
	private TabHost mTabHost;
	private TabWidget mTabWidget;
	private TabsAdapter mTabsAdapter;
	private ViewPager mViewPager;
	
	protected final ArrayList<RefreshObserver> mRefreshObservers = new ArrayList<RefreshObserver>(); 
	
	private final static String TAG = BaseFragmentTabsActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final long start = System.currentTimeMillis();
		Log.d(TAG, "Starting onCreate()...");
		setContentView(R.layout.activity_fragment_tabs_pager);
		
		final HorizontalScrollView scroller = (HorizontalScrollView) findViewById(R.id.tab_scroller);
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabWidget = (TabWidget) findViewById(android.R.id.tabs);
		mViewPager = (ViewPager) findViewById(R.id.tab_pager);

		mTabHost.setup();
		mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPager);
		
		getActivityHelper().setupActionBar(getTitle(), 0);

		final String customTitle = getIntent().getStringExtra(Intent.EXTRA_TITLE);
		getActivityHelper().setActionBarTitle(customTitle != null ? customTitle : getTitle());

		if (savedInstanceState == null) {
			onCreateTabs();
			//mFragment = onCreatePane();
			//mFragment.setArguments(intentToFragmentArguments(getIntent()));

		} else {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}
		
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
	
	protected void addTab(String key, String label, Class<?> fragment, int imageResource) {
		final long start = System.currentTimeMillis();
		Log.d(TAG, "Starting addTab()...");
		final View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab, mTabWidget, false);
		final TextView title = (TextView) tabIndicator.findViewById(R.id.tab_title);
		final ImageView icon = (ImageView) tabIndicator.findViewById(R.id.tab_icon);
		title.setText(label);
		icon.setImageResource(imageResource);
		mTabsAdapter.addTab(mTabHost.newTabSpec(key).setIndicator(tabIndicator), label, fragment, null);
        Log.d(TAG, "addTab() done in " + (System.currentTimeMillis() - start ) + "ms.");
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("tab", mTabHost.getCurrentTabTag());
	}
	
	public synchronized void registerRefreshObserver(RefreshObserver observer) {
		mRefreshObservers.add(observer);
		Log.d(TAG, "Registered refresh observer.");
	}
	
	public synchronized void unregisterRefreshObserver(RefreshObserver observer) {
		if (mRefreshObservers.remove(observer)) {
			Log.d(TAG, "Unregistered refresh observer.");
		} else {
			Log.w(TAG, "Could not find observer, NOT unregistering!");
		}
	}
	
	/**
	 * Called in <code>onCreate</code> when the fragment constituting this
	 * activity is needed. The returned fragment's arguments will be set to the
	 * intent used to invoke this activity.
	 */
	protected abstract void onCreateTabs();
}
