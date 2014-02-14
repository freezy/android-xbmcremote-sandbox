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

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import org.xbmc.android.app.injection.Injector;
import org.xbmc.android.remotesandbox.R;

/**
 * Common to all activities using the action bar.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class BaseActivity extends ActionBarActivity {

	@Optional
	@InjectView(R.id.drawer_layout)
	protected DrawerLayout drawerLayout;

	private ActionBarDrawerToggle drawerToggle;
	private CharSequence drawerTitle;

	private final int titleRes;
	private final int iconRes;
	private final int contentViewRes;

	public BaseActivity(int titleRes, int iconRes, int contentViewRes) {
		this.titleRes = titleRes;
		this.iconRes = iconRes;
		this.contentViewRes = contentViewRes;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(titleRes);
		setContentView(contentViewRes);

		ButterKnife.inject(this);
		Injector.inject(this);

		drawerTitle = getTitle();
		//mDrawerList = (NavigationDrawerFragment) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer opens
		//drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		if (drawerLayout != null) {
			drawerToggle = new ActionBarDrawerToggle(
					this,                  /* host Activity */
					drawerLayout,         /* DrawerLayout object */
					R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
					R.string.drawer_open,  /* "open drawer" description for accessibility */
					R.string.drawer_close  /* "close drawer" description for accessibility */
			) {
				public void onDrawerClosed(View view) {
					getSupportActionBar().setTitle(titleRes);
					supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
				}

				public void onDrawerOpened(View drawerView) {
					getSupportActionBar().setTitle(drawerTitle);
					supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
				}
			};
			drawerLayout.setDrawerListener(drawerToggle);
		}

		final ActionBar actionBar = getSupportActionBar();
		actionBar.setIcon(IconHelper.getDrawable(getApplicationContext(), iconRes));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content view
		//boolean drawerOpen = drawerLayout.isDrawerOpen(mDrawerList);
		//menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (drawerToggle != null && drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
		switch(item.getItemId()) {
/*			case R.id.action_websearch:
				// create intent to perform web search for this planet
				Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
				intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
				// catch event that there's no activity to handle intent
				if (intent.resolveActivity(getPackageManager()) != null) {
					startActivity(intent);
				} else {
					Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
				}
				return true;*/
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		// update the main content by replacing fragments
	/*	Fragment fragment = new PlanetFragment();
		Bundle args = new Bundle();
		args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
		fragment.setArguments(args);

		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();*/

		// update selected item and title, then close the drawer
		//mDrawerList.setItemChecked(position, true);
		//setTitle(mPlanetTitles[position]);
		//drawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		getSupportActionBar().setTitle(title);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		if (drawerToggle != null) {
			// Sync the toggle state after onRestoreInstanceState has occurred.
			drawerToggle.syncState();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (drawerToggle != null) {
			// Pass any configuration change to the drawer toggls
			drawerToggle.onConfigurationChanged(newConfig);
		}
	}

}
