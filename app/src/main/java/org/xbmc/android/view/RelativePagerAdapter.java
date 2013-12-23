package org.xbmc.android.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class RelativePagerAdapter extends FragmentStatePagerAdapter {

	public static final int PAGE_POSITION_LEFT = 0;
	public static final int PAGE_POSITION_CENTER = 1;
	public static final int PAGE_POSITION_RIGHT = 2;

	public static final int NUM_ITEMS = 3;

	private RelativePagerFragment currFragment;

	public RelativePagerAdapter(FragmentManager fm) {
		super(fm);
		currFragment = new PagerActivity.MyRelativeFragment(0);
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	public void move(int direction) {
		if (direction == 1) {
			currFragment = currFragment.getPrev();
		} else {
			currFragment = currFragment.getNext();
		}
		if (currFragment.hasNext() != RelativePagerFragment.STATUS_ENABLED) {

		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return NUM_ITEMS - (currFragment.hasNext() == RelativePagerFragment.STATUS_ENABLED ? 0 : 1);
	}

	@Override
	public Fragment getItem(int position) {
		if (position == PAGE_POSITION_RIGHT) {
			return currFragment.getNext();
		} else if (position == PAGE_POSITION_LEFT) {
			return currFragment.getPrev();
		} else {
			return currFragment;
		}
	}
}