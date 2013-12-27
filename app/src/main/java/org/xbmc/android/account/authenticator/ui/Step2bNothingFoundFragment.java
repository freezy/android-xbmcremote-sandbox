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

import android.app.Activity;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.view.RelativePagerFragment;

public class Step2bNothingFoundFragment extends AbstractWizardFragment {

	protected Step2bNothingFoundFragment(Activity activity, OnStatusChangeListener statusChangeListener) {
		super(R.layout.fragment_auth_wizard_02b_nothing_found, activity, statusChangeListener);
	}

	@Override
	public int hasNextButton() {
		return STATUS_ENABLED;
	}

	@Override
	public int hasPrevButton() {
		return STATUS_ENABLED;
	}

	@Override
	int getStep() {
		return 1;
	}

	@Override
	public RelativePagerFragment getNext() {
		return new Step2bNothingFoundFragment(activity, statusChangeListener);
	}

	@Override
	public RelativePagerFragment getPrev() {
		return new Step2aSearchingFragment(activity, statusChangeListener);
	}
}
