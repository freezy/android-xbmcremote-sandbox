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

package org.xbmc.android.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import static org.xbmc.android.view.RelativePagerFragment.OnStatusChangeListener;

/**
 * A pager adapter that relies on the currently displayed fragment to deliver the
 * next or previous page, as opposed to a global list of fragments.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class RelativePagerAdapter extends FragmentStatePagerAdapter implements OnStatusChangeListener {

	public static final int PAGE_POSITION_LEFT = 0;
	public static final int PAGE_POSITION_CENTER = 1;
	public static final int PAGE_POSITION_RIGHT = 2;

	public static final int NUM_ITEMS = 3;

	private RelativePagerFragment currFragment;
	private RelativeViewPager pager;

	public RelativePagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public void setInitialFragment(RelativePagerFragment fragment) {
		currFragment = fragment;
	}

	public void setPager(RelativeViewPager p) {
		pager = p;
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	public void move(int direction) {
		if (direction == 1) {
			currFragment = currFragment.hasPrev() ? currFragment.getPrev() : currFragment;
		} else {
			currFragment = currFragment.hasNext() ? currFragment.getNext() : currFragment;
		}
		notifyDataSetChanged();
	}

	public RelativePagerFragment getCurrentFragment() {
		return currFragment;
	}

	@Override
	public int getCount() {
		return NUM_ITEMS;
	}

	@Override
	public Fragment getItem(int position) {
		if (currFragment == null) {
			throw new RuntimeException("Must set initial fragment before view is rendered.");
		}
		if (position == PAGE_POSITION_RIGHT) {
			return currFragment.hasNext() ? currFragment.getNext() : new Fragment();
		} else if (position == PAGE_POSITION_LEFT) {
			return currFragment.hasPrev() ? currFragment.getPrev() : new Fragment();
		} else {
			return currFragment;
		}
	}

	@Override
	public void onStatusChanged() {
		notifyDataSetChanged();
	}

	@Override
	public void onNextPage() {
		notifyDataSetChanged();
		pager.setCurrentItem(PAGE_POSITION_RIGHT, true);
	}

	@Override
	public void onPrevPage() {
		notifyDataSetChanged();
		pager.setCurrentItem(PAGE_POSITION_LEFT, true);
	}
}