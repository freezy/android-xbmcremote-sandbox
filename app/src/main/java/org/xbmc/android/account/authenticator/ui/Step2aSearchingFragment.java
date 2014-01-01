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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import org.xbmc.android.event.ZeroConf;
import org.xbmc.android.injection.Injector;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.view.FragmentStateManager;
import org.xbmc.android.view.RelativePagerFragment;
import org.xbmc.android.zeroconf.DiscoveryService;
import org.xbmc.android.zeroconf.XBMCHost;

import javax.inject.Inject;
import java.util.ArrayList;

public class Step2aSearchingFragment extends WizardFragment {

	@Inject protected EventBus bus;
	@InjectView(R.id.spinner) ProgressBar spinner;

	private Class<? extends RelativePagerFragment> next;

	private final ArrayList<XBMCHost> hosts = new ArrayList<XBMCHost>();

	private int nextStatus = STATUS_DISABLED;
	private boolean found = false;

	public Step2aSearchingFragment() {
		super(R.layout.fragment_auth_wizard_02a_searching);
		next = Step2bNothingFoundFragment.class;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, Step1WelcomeFragment.class, Step3aHostFoundFragment.class);
		Injector.inject(this);
		bus.register(this);

		if (savedInstanceState != null) {
			nextStatus = savedInstanceState.getInt(NEXT_STATUS);
		}

		// FIXME debug
		hosts.add(new XBMCHost("aquarium", "192.168.0.100", 8080, "Aquarium"));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = super.onCreateView(inflater, container, savedInstanceState);
		ButterKnife.inject(this, v);
		return v;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState, Step1WelcomeFragment.class, Step3aHostFoundFragment.class);
		outState.putInt(NEXT_STATUS, nextStatus);
	}

	public void onEventMainThread(ZeroConf event) {
		Log.d(Step2aSearchingFragment.class.getSimpleName(), "Got event from bus: " + event);
		if (event.isResolved()) {
			hosts.add(event.getHost());
		}
		if (event.isFinished()) {
			nextStatus = STATUS_ENABLED;
			if (!hosts.isEmpty()) {
				next = Step3aHostFoundFragment.class;
				found = true;
			}
			statusChangeListener.onNextPage();
		}
	}

	@Override
	public void onPageActive() {
		Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
		spinner.setAnimation(animFadeIn);
		spinner.setVisibility(View.VISIBLE);
		getActivity().startService(new Intent(getActivity(), DiscoveryService.class));
	}

	@Override
	int hasNextButton() {
		return nextStatus;
	}

	@Override
	int hasPrevButton() {
		return STATUS_DISABLED;
	}

	@Override
	int getStep() {
		return 1;
	}

	@Override
	public void onDestroy() {
		bus.unregister(this);
		super.onDestroy();
	}

	@Override
	public RelativePagerFragment getNext(FragmentStateManager fsm) {
		final RelativePagerFragment fragment = fsm.getFragment(next);
		if (found) {
			((Step3aHostFoundFragment)fragment).setHosts(hosts);
		}
		return fragment;
	}

	@Override
	public RelativePagerFragment getPrev(FragmentStateManager fsm) {
		return fsm.getFragment(Step1WelcomeFragment.class);
	}

}
