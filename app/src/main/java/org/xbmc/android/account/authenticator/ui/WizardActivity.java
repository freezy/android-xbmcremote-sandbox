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

package org.xbmc.android.account.authenticator.ui;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import butterknife.ButterKnife;
import butterknife.InjectView;
import org.xbmc.android.account.Constants;
import org.xbmc.android.app.injection.Injector;
import org.xbmc.android.app.manager.HostManager;
import org.xbmc.android.app.manager.IconManager;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.view.FragmentStateManager;
import org.xbmc.android.view.RelativePagerAdapter;
import org.xbmc.android.view.RelativePagerFragment;
import org.xbmc.android.view.RelativeViewPager;
import org.xbmc.android.zeroconf.XBMCHost;

import javax.inject.Inject;

import static org.xbmc.android.account.authenticator.ui.WizardFragment.*;

/**
 * Guides the user through the host setup.
 *
 * This is the activity that is executed by Android's account settings when adding a new XBMC host. It is also used
 * from within the app itself though.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class WizardActivity extends AccountAuthenticatorActivity implements FragmentStateManager.FragmentStateManageable {

	public static final String TAG = WizardActivity.class.getSimpleName();

	public static final String PARAM_AUTHTOKEN_TYPE = "authtokenType";
	public static final String DATA_NEXT_BUTTON_AVAILABLE = "org.xbmc.android.account.DATA_NEXT_BUTTON_AVAILABLE";
	public static final String DATA_PREV_BUTTON_AVAILABLE = "org.xbmc.android.account.DATA_PREV_BUTTON_AVAILABLE";
	public static final String DATA_NEXT_BUTTON_TEXT = "org.xbmc.android.account.DATA_NEXT_BUTTON_TEXT";
	public static final String DATA_PREV_BUTTON_TEXT = "org.xbmc.android.account.DATA_PREV_BUTTON_TEXT";
	public static final String DATA_PAGER_STEP = "org.xbmc.android.account.DATA_PAGER_STEP";
	public static final String DATA_IS_LAST = "org.xbmc.android.account.DATA_IS_LAST";

	public static final int RESULT_SUCCESS = 0x666;

	@Inject HostManager hostManager;
	@Inject IconManager iconManager;

	@InjectView(R.id.strip) StepPagerStrip pagerStrip;
	@InjectView(R.id.pager) RelativeViewPager pager;

	@InjectView(R.id.next_button) Button nextButton;
	@InjectView(R.id.prev_button) Button prevButton;

	private FragmentStateManager fragmentStateManager;
	private RelativePagerAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accountwizard);
		setTitle(R.string.accountwizard_title);
		ButterKnife.inject(this);
		Injector.inject(this);

		// set icon
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setIcon(iconManager.getDrawable(R.string.ic_logo));

		// init adapter and first fragment
		adapter = new RelativePagerAdapter(getSupportFragmentManager(), getFragmentStateManager());
		final Fragment firstPage = getFragmentStateManager().getFragment(Step1WelcomeFragment.class);

		adapter.setInitialFragment((Step1WelcomeFragment)firstPage);

		// ths only part we use from WizardPager is the pager strip.
		pagerStrip.setOnPageSelectedListener(new StepPagerStrip.OnPageSelectedListener() {
			@Override
			public void onPageStripSelected(int position) {
				position = Math.min(adapter.getCount() - 1, position);
				if (pager.getCurrentItem() != position) {
					pager.setCurrentItem(position);
				}
			}
		});
		pagerStrip.setPageCount(4);
		pagerStrip.setCurrentPage(0);

		// init pager
		pager.setAdapter(adapter);
		pager.setOnRelativePageChangeListener(new RelativeViewPager.OnRelativePageChangeListener() {
			@Override
			public void onPageSelected(RelativePagerFragment f) {
				final WizardFragment fragment = (WizardFragment)f;
				pagerStrip.setCurrentPage(fragment.getStep());
				updateBottomBar(fragment);
			}

			@Override
			public void onPageUpdated(RelativePagerFragment f) {
				final WizardFragment fragment = (WizardFragment)f;
				updateBottomBar(fragment);
			}
		});

		// init buttons
		nextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final WizardFragment fragment = (WizardFragment)adapter.getCurrentFragment();
				if (fragment.isLast()) {
					setResult(RESULT_SUCCESS);
					finish();
				} else {
					adapter.onNextPage();
				}
			}
		});
		prevButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				adapter.onPrevPage();
			}
		});

		// restore from bundle if necessary
		if (savedInstanceState == null) {
			updateBottomBar((WizardFragment)adapter.getCurrentFragment());
		} else {
			updateButton(nextButton, savedInstanceState.getInt(DATA_NEXT_BUTTON_AVAILABLE), savedInstanceState.getBoolean(DATA_IS_LAST));
			updateButton(prevButton, savedInstanceState.getInt(DATA_PREV_BUTTON_AVAILABLE), false);
			nextButton.setText(savedInstanceState.getInt(DATA_NEXT_BUTTON_TEXT));
			prevButton.setText(savedInstanceState.getInt(DATA_PREV_BUTTON_TEXT));
			pagerStrip.setCurrentPage(savedInstanceState.getInt(DATA_PAGER_STEP));
		}
	}

	/**
	 * Executed from the last fragment when host is found and validated.
	 * @param host Host to add to the system
	 */
	public void addHost(XBMCHost host) {
		hostManager.addAccount(host);
		hostManager.switchHost(host);

		final Intent intent = new Intent();
		intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, host.getName());
		intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, Constants.ACCOUNT_TYPE);
		setAccountAuthenticatorResult(intent.getExtras());
		setResult(RESULT_OK, intent);
	}

	/**
	 * Updates states and lables of the bottom buttons.
	 * @param fragment
	 */
	private void updateBottomBar(WizardFragment fragment) {
		updateButton(nextButton, fragment.hasNextButton(), fragment.isLast());
		updateButton(prevButton, fragment.hasPrevButton(), false);
		nextButton.setText(fragment.getNextButtonLabel());
		prevButton.setText(fragment.getPrevButtonLabel());
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		final WizardFragment fragment = (WizardFragment)adapter.getCurrentFragment();
		outState.putInt(DATA_NEXT_BUTTON_AVAILABLE, fragment.hasNextButton());
		outState.putInt(DATA_PREV_BUTTON_AVAILABLE, fragment.hasPrevButton());
		outState.putInt(DATA_NEXT_BUTTON_TEXT, fragment.getNextButtonLabel());
		outState.putInt(DATA_PREV_BUTTON_TEXT, fragment.getPrevButtonLabel());
		outState.putInt(DATA_PAGER_STEP, fragment.getStep());
		outState.putBoolean(DATA_IS_LAST, fragment.isLast());
	}

	/**
	 * Updates button visibility based on given state.
	 * @param button Button to update
	 * @param state Button state
	 * @param last If on last page, button is always enabled.
	 */
	private static void updateButton(Button button, int state, boolean last) {
		if (last) {
			state = STATUS_ENABLED;
		}
		switch (state) {
			case STATUS_DISABLED:
				button.setVisibility(View.VISIBLE);
				button.setEnabled(false);
				break;
			case STATUS_ENABLED:
				button.setVisibility(View.VISIBLE);
				button.setEnabled(true);
				break;
			case STATUS_GONE:
				button.setVisibility(View.INVISIBLE);
				break;
		}
	}

	@Override
	public FragmentStateManager getFragmentStateManager() {
		if (fragmentStateManager == null) {
			fragmentStateManager = new FragmentStateManager();
		}
		return fragmentStateManager;
	}
}
