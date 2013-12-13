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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import butterknife.ButterKnife;
import butterknife.InjectView;
import co.juliansuarez.libwizardpager.wizard.ui.StepPagerStrip;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import org.xbmc.android.remotesandbox.R;

import static org.xbmc.android.account.authenticator.ui.AbstractWizardFragment.*;

public class WizardActivity extends SherlockFragmentActivity {

	@InjectView(R.id.strip) StepPagerStrip pagerStrip;
	@InjectView(R.id.pager) BlockableViewpager pager;

	@InjectView(R.id.next_button) Button nextButton;
	@InjectView(R.id.prev_button) Button prevButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accountwizard);
		setTitle(R.string.accountwizard_title);
		ButterKnife.inject(this);

		final WizardPagerAdapter adapter = new WizardPagerAdapter(getSupportFragmentManager());
		pagerStrip.setOnPageSelectedListener(new StepPagerStrip.OnPageSelectedListener() {
			@Override
			public void onPageStripSelected(int position) {
				position = Math.min(adapter.getCount() - 1, position);
				if (pager.getCurrentItem() != position) {
					pager.setCurrentItem(position);
				}
			}
		});
		pagerStrip.setPageCount(WizardPagerAdapter.TOTAL_COUNT);
		pagerStrip.setCurrentPage(0);

		pager.setAdapter(adapter);
		pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				adapter.setCurrentPage(position);
				pagerStrip.setCurrentPage(adapter.getCurrentFragment().getStep());
			}
		});

		nextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pager.setCurrentItem(pager.getCurrentItem() + 1);
			}
		});
		prevButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pager.setCurrentItem(pager.getCurrentItem() - 1);
			}
		});

		updateBottomBar(adapter.getCurrentFragment());
	}

	private void updateBottomBar(AbstractWizardFragment fragment) {
		updateButton(nextButton, fragment.hasNext());
		updateButton(prevButton, fragment.hasPrev());
	}

	private static void updateButton(Button button, int state) {
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

	private class WizardPagerAdapter extends FragmentStatePagerAdapter implements IOnStatusChangeListener {

		public final static int TOTAL_COUNT = 4;

		private int pagerCount = TOTAL_COUNT;
		private int currentPos = 0;

		private AbstractWizardFragment currentFragment = new Step1WelcomeFragment(WizardActivity.this, this);

		public WizardPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int pagerPos) {
			if (currentPos == pagerPos) {
				return currentFragment;
			}
			if (currentPos == pagerPos - 1 && currentFragment.hasNext() == STATUS_ENABLED) {
				return currentFragment.getNext();
			}
			if (currentPos == pagerPos + 1 && currentFragment.hasPrev() == STATUS_ENABLED) {
				return currentFragment.getPrev();
			}
			return new Fragment();
		}

		@Override
		public int getCount() {
			return pagerCount;
		}

		public AbstractWizardFragment getCurrentFragment() {
			return currentFragment;
		}

		public void setCurrentPage(int pagerPos) {

			final AbstractWizardFragment fragment;
			if (pagerPos > currentPos) {
				fragment = currentFragment.getNext();
			} else if (pagerPos < currentPos) {
				fragment = currentFragment.getPrev();
			} else {
				fragment = currentFragment;
			}
			pager.setPagingPrevEnabled(fragment.hasPrev() == STATUS_ENABLED);
			pager.setPagingNextEnabled(fragment.hasNext() == STATUS_ENABLED);

			currentPos = pagerPos;
			currentFragment = fragment;

			fragment.onPageVisible();
			notifyDataSetChanged();
			updateBottomBar(fragment);
		}

		@Override
		public void onStatusChanged() {
			notifyDataSetChanged();
			pager.setCurrentItem(pager.getCurrentItem() + 1);
		}

	}
}
