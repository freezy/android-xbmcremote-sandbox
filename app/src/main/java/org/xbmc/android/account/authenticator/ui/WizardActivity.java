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
import android.view.View;
import android.widget.Button;
import butterknife.ButterKnife;
import butterknife.InjectView;
import co.juliansuarez.libwizardpager.wizard.ui.StepPagerStrip;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.view.RelativePagerAdapter;
import org.xbmc.android.view.RelativeViewPager;

import static org.xbmc.android.account.authenticator.ui.AbstractWizardFragment.*;

public class WizardActivity extends SherlockFragmentActivity {

	@InjectView(R.id.strip) StepPagerStrip pagerStrip;
	@InjectView(R.id.pager) RelativeViewPager pager;

	@InjectView(R.id.next_button) Button nextButton;
	@InjectView(R.id.prev_button) Button prevButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accountwizard);
		setTitle(R.string.accountwizard_title);
		ButterKnife.inject(this);

		final RelativePagerAdapter adapter = new RelativePagerAdapter(getSupportFragmentManager(), null);
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

		pager.setAdapter(adapter);

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

		updateBottomBar((AbstractWizardFragment)adapter.getCurrentFragment());
	}

	private void updateBottomBar(AbstractWizardFragment fragment) {
		updateButton(nextButton, fragment.hasNextButton());
		updateButton(prevButton, fragment.hasPrevButton());
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


}
