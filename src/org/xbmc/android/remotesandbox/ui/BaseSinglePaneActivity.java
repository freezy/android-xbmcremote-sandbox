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

import org.xbmc.android.remotesandbox.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * A {@link BaseActivity} that simply contains a single fragment. The intent
 * used to invoke this activity is forwarded to the fragment as arguments during
 * fragment instantiation. Derived activities should only need to implement
 * {@link com.google.android.apps.iosched.ui.BaseSinglePaneActivity#onCreatePane()}
 * .
 */
public abstract class BaseSinglePaneActivity extends BaseActivity {
	private Fragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_singlepane_empty);
		getActivityHelper().setupActionBar(getTitle(), 0);

		final String customTitle = getIntent().getStringExtra(Intent.EXTRA_TITLE);
		getActivityHelper().setActionBarTitle(customTitle != null ? customTitle : getTitle());

		if (savedInstanceState == null) {
			mFragment = onCreatePane();
			mFragment.setArguments(intentToFragmentArguments(getIntent()));

			getSupportFragmentManager().beginTransaction().add(R.id.root_container, mFragment).commit();
		}
	}

	/**
	 * Called in <code>onCreate</code> when the fragment constituting this
	 * activity is needed. The returned fragment's arguments will be set to the
	 * intent used to invoke this activity.
	 */
	protected abstract Fragment onCreatePane();
}
