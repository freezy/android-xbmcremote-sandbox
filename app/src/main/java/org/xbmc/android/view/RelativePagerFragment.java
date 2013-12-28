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

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * An abstract Fragment that delivers the next or previous page when used in
 * a {@link org.xbmc.android.view.RelativeViewPager}.
 *
 * @author freezy <freezy@xbmc.org>
 */
public abstract class RelativePagerFragment extends Fragment {

	private final int layoutRes;
	protected final Activity activity;
	protected final OnStatusChangeListener statusChangeListener;


	protected RelativePagerFragment(int layoutRes, Activity activity, OnStatusChangeListener statusChangeListener) {
		this.layoutRes = layoutRes;
		this.activity = activity;
		this.statusChangeListener = statusChangeListener;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(layoutRes, container, false);
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
	 */
	public RelativePagerFragment getNext() {
		return null;
	}

	/**
	 * Returns the previous page.
	 * @return Previous page
	 */
	public RelativePagerFragment getPrev() {
		return null;
	}

	/**
	 * Executed when the fragment is settled.
	 */
	public void onPageActive() {
	}

	public interface OnStatusChangeListener {
		void onStatusChanged();
		void onNextPage();
		void onPrevPage();
	}
}
