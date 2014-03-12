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

package org.xbmc.android.app.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.astuetz.PagerSlidingTabStrip;
import com.devspark.robototextview.RobotoTypefaceManager;
import org.xbmc.android.app.injection.Injector;
import org.xbmc.android.app.manager.HostManager;
import org.xbmc.android.app.ui.fragment.MovieListFragment;
import org.xbmc.android.remotesandbox.R;

import javax.inject.Inject;

/**
 * Movie landing page.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class MoviesActivity extends BaseActivity {

	final private static String TAG = MoviesActivity.class.getSimpleName();
	private static final int NUM_PAGES = 1;

	@Inject protected HostManager hostManager;

	@InjectView(R.id.pager) protected ViewPager pager;
	@InjectView(R.id.tabs) protected PagerSlidingTabStrip tabs;


	public MoviesActivity() {
		super(R.string.title_movies, R.string.ic_movie, R.layout.activity_movies);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ButterKnife.inject(this);
		Injector.inject(this);

		pager.setAdapter(new MovieSliderAdapter(getSupportFragmentManager()));
		tabs.setViewPager(pager);
		tabs.setTypeface(RobotoTypefaceManager.obtaintTypeface(getApplicationContext(), 8), Typeface.NORMAL); // 8 = bold.

	}

	private class MovieSliderAdapter extends FragmentStatePagerAdapter {

		public MovieSliderAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return new MovieListFragment();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return getResources().getString(R.string.title_movies);
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
	}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
