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

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * An abstract Fragment that delivers the next or previous page when used in
 * a {@link RelativeViewPager}.
 *
 * @author freezy <freezy@xbmc.org>
 */
public abstract class RelativePagerFragment extends Fragment {

	protected final static String DATA_NEXT_STATUS = "org.xbmc.android.DATA_NEXT_STATUS";
	protected final static String DATA_HAS_NEXT = "org.xbmc.android.DATA_HAS_NEXT";

	private final int layoutRes;
	protected OnStatusChangeListener statusChangeListener;
	protected FragmentStateManager fragmentStateManager;

	protected RelativePagerFragment(int layoutRes) {
		this.layoutRes = layoutRes;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(layoutRes, container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		fragmentStateManager = FragmentStateManager.get(getActivity());
		statusChangeListener = fragmentStateManager.getOnStatusChangeListener();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		fragmentStateManager.removeFragment(this.getClass());
	}

	/**
	 * Indicates whether a page has a next page.
	 */
	public abstract boolean hasNext();

	/**
	 * Indicates whether a page has a previous page.
	 */
	public abstract boolean hasPrev();

	/**
	 * Returns the next page.
	 * @return Next page
	 * @param fsm Use to instantiate Fragment
	 */
	public RelativePagerFragment getNext(FragmentStateManager fsm) {
		return null;
	}

	/**
	 * Returns the previous page.
	 * @return Previous page
	 * @param fsm Use to instantiate Fragment
	 */
	public RelativePagerFragment getPrev(FragmentStateManager fsm) {
		return null;
	}

	/**
	 * Executed when the fragment is settled.
	 */
	public void onPageActive() {
	}

	/**
	 * Shortcut to {@link #getActivity().getApplicationContext()}.
	 * @return Application Context
	 */
	protected Context getApplicationContext() {
		return getActivity().getApplicationContext();
	}

	/**
	 * Allows to manually enforce the next or previous page.
	 */
	public interface OnStatusChangeListener {
		/**
		 * The next page is displayed.
		 */
		void onNextPage();

		/**
		 * The previous page is displayed.
		 */
		void onPrevPage();
	}
}
