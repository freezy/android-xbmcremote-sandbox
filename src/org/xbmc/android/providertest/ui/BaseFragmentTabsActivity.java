package org.xbmc.android.providertest.ui;

import org.xbmc.android.providertest.R;
import org.xbmc.android.util.TabsAdapter;

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

	/**
	 * Called in <code>onCreate</code> when the fragment constituting this
	 * activity is needed. The returned fragment's arguments will be set to the
	 * intent used to invoke this activity.
	 */
	protected abstract void onCreateTabs();
}
