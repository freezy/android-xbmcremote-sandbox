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
import org.xbmc.android.view.RelativePagerFragment;
import org.xbmc.android.zeroconf.DiscoveryService;
import org.xbmc.android.zeroconf.XBMCHost;

import javax.inject.Inject;
import java.util.ArrayList;

public class Step2aSearchingFragment extends WizardFragment {

	@Inject protected EventBus bus;
	@InjectView(R.id.spinner) ProgressBar spinner;

	private WizardFragment next;
	private WizardFragment prev;

	private final ArrayList<XBMCHost> hosts = new ArrayList<XBMCHost>();

	private int nextStatus = STATUS_DISABLED;

	public Step2aSearchingFragment(Activity activity, OnStatusChangeListener statusChangeListener) {
		super(R.layout.fragment_auth_wizard_02a_searching, activity, statusChangeListener);
		next = new Step2bNothingFoundFragment(activity, statusChangeListener);
		prev = new Step1WelcomeFragment(activity, statusChangeListener);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Injector.inject(this);
		bus.register(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = super.onCreateView(inflater, container, savedInstanceState);
		ButterKnife.inject(this, v);
		return v;
	}

	public void onEventMainThread(ZeroConf event) {
		Log.d(Step2aSearchingFragment.class.getSimpleName(), "Got event from bus: " + event);
		if (event.isResolved()) {
			hosts.add(event.getHost());
		}
		if (event.isFinished()) {
			nextStatus = STATUS_ENABLED;
			if (!hosts.isEmpty()) {
				next = new Step3aHostFoundFragment(activity, statusChangeListener);
			}
			statusChangeListener.onNextPage();
		}
	}

	@Override
	public void onPageActive() {
		Animation animFadeIn = AnimationUtils.loadAnimation(activity, android.R.anim.fade_in);
		spinner.setAnimation(animFadeIn);
		spinner.setVisibility(View.VISIBLE);
		activity.startService(new Intent(activity, DiscoveryService.class));
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
	public RelativePagerFragment getNext() {
		return next;
	}

	@Override
	public RelativePagerFragment getPrev() {
		return prev;
	}

}
