package org.xbmc.android.account.authenticator.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import butterknife.ButterKnife;
import butterknife.InjectView;
import co.juliansuarez.libwizardpager.wizard.ui.StepPagerStrip;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import org.xbmc.android.remotesandbox.R;

public class WizardActivity extends SherlockFragmentActivity {

	@InjectView(R.id.strip) StepPagerStrip pagerStrip;
	@InjectView(R.id.pager) ViewPager pager;

	@InjectView(R.id.next_button) Button nextButton;
	@InjectView(R.id.prev_button) Button prevButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accountwizard);
		setTitle(R.string.accountwizard_title);
		ButterKnife.inject(this);

		final PagerAdapter adapter = new WizardPagerAdapter(getSupportFragmentManager());
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
		pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				pagerStrip.setCurrentPage(position);
				updateBottomBar();
			}
		});
	}

	private void updateBottomBar() {
		int position = pager.getCurrentItem();
		if (position == 0) {
			prevButton.setVisibility(View.INVISIBLE);
			nextButton.setVisibility(View.VISIBLE);
		}
	}

	private class WizardPagerAdapter extends FragmentStatePagerAdapter {

		private final Fragment step1WelcomeFragment;

		public WizardPagerAdapter(FragmentManager fm) {
			super(fm);

			step1WelcomeFragment = new Step1WelcomeFragment();
		}

		@Override
		public Fragment getItem(int i) {
			return step1WelcomeFragment;
		}

		@Override
		public int getCount() {
			return 1;
		}
	}

}
