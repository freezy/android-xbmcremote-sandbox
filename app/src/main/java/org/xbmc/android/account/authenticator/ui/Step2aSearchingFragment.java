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

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import de.greenrobot.event.EventBus;
import org.xbmc.android.event.ZeroConf;
import org.xbmc.android.injection.Injector;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.zeroconf.DiscoveryService;

import javax.inject.Inject;

public class Step2aSearchingFragment extends AbstractWizardFragment {

	@Inject protected EventBus bus;

	protected Step2aSearchingFragment() {
		super(R.layout.fragment_auth_wizard_02a_searching);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Injector.inject(this);
		bus.register(this);
	}

	public void onEvent(ZeroConf event) {
		Log.d(Step2aSearchingFragment.class.getSimpleName(), "Got event from bus: " + event);
	}

	@Override
	void onPageVisible() {
		getActivity().startService(new Intent(Intent.ACTION_SYNC, null, getActivity(), DiscoveryService.class));
	}

	@Override
	int hasNext() {
		return STATUS_DISABLED;
	}

	@Override
	int hasPrev() {
		return STATUS_DISABLED;
	}

	@Override
	int getStep() {
		return 1;
	}

	@Override
	AbstractWizardFragment getNext() {
		return new Step2bNothingFoundFragment();
	}

	@Override
	AbstractWizardFragment getPrev() {
		return new Step1WelcomeFragment();
	}

	@Override
	public void onDestroy() {
		bus.unregister(this);
		super.onDestroy();
	}
}
