package org.xbmc.android.account.authenticator.ui;

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

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A parent class for all wizard fragments.
 *
 * @author freezy <freezy@xbmc.org>
 */
public abstract class AbstractWizardFragment extends Fragment {

	protected final static int STATUS_ENABLED = 0x01;
	protected final static int STATUS_DISABLED = 0x02;
	protected final static int STATUS_GONE = 0x03;

	private final int layoutRes;
	protected final Activity activity;
	protected final IOnStatusChangeListener statusChangeListener;


	protected AbstractWizardFragment(int layoutRes, Activity activity, IOnStatusChangeListener statusChangeListener) {
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
	 * @return one of: STATUS_ENABLED, STATUS_DISABLED or STATUS_GONE.
	 */
	abstract int hasNext();
	/**
	 * Indicates whether a page has a previous page.
	 * @return one of: STATUS_ENABLED, STATUS_DISABLED or STATUS_GONE.
	 */
	abstract int hasPrev();

	/**
	 * Returns which step this page is for. Starts with 0.
	 * @return
	 */
	abstract int getStep();

	/**
	 * Returns the next page.
	 * @return Next page
	 */
	AbstractWizardFragment getNext() {
		return null;
	}

	/**
	 * Returns the previous page.
	 * @return Previous page
	 */
	AbstractWizardFragment getPrev() {
		return null;
	}

	/**
	 * Executed when the fragment comes into view
	 */
	void onPageVisible() {
	}

	interface IOnStatusChangeListener {
		void onStatusChanged();
	}
}
