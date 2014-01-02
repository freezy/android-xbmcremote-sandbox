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

import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.view.RelativePagerFragment;

/**
 * A parent class for all wizard fragments.
 *
 * @author freezy <freezy@xbmc.org>
 */
public abstract class WizardFragment extends RelativePagerFragment {

	protected final static int STATUS_ENABLED = 0x01;
	protected final static int STATUS_DISABLED = 0x02;
	protected final static int STATUS_GONE = 0x03;

	protected WizardFragment(int layoutRes) {
		super(layoutRes);
	}

	/**
	 * Indicates whether a page has a next page.
	 * @return one of: STATUS_ENABLED, STATUS_DISABLED or STATUS_GONE.
	 */
	abstract int hasNextButton();
	/**
	 * Indicates whether a page has a previous page.
	 * @return one of: STATUS_ENABLED, STATUS_DISABLED or STATUS_GONE.
	 */
	abstract int hasPrevButton();

	public boolean isLast() {
		return false;
	}

	public int getNextButtonLabel() {
		return R.string.next;
	}

	public int getPrevButtonLabel() {
		return R.string.prev;
	}

	/**
	 * Returns which step this page is for. Starts with 0.
	 */
	abstract int getStep();

	@Override
	public boolean hasNext() {
		return hasNextButton() == STATUS_ENABLED;
	}

	@Override
	public boolean hasPrev() {
		return hasPrevButton() == STATUS_ENABLED;
	}
}
