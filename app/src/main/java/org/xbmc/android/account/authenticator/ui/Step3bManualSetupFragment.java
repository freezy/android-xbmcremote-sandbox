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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.view.FragmentStateManager;
import org.xbmc.android.view.RelativePagerFragment;
import org.xbmc.android.zeroconf.XBMCHost;

public class Step3bManualSetupFragment extends WizardFragment {

	@InjectView(R.id.display_name) EditText displayNameView;
	@InjectView(R.id.host) EditText hostView;
	@InjectView(R.id.port) EditText portView;
	@InjectView(R.id.test) Button testBtn;

	public Step3bManualSetupFragment() {
		super(R.layout.fragment_auth_wizard_03b_manual_setup);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = super.onCreateView(inflater, container, savedInstanceState);
		ButterKnife.inject(this, v);
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		testBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final String addr = hostView.getText().toString();
				final int port = portView.getText().toString().isEmpty() ? 0 : Integer.parseInt(portView.getText().toString());
				final XBMCHost host = new XBMCHost(addr, addr, port, displayNameView.getText().toString());
				Toast.makeText(getApplicationContext(), "Will test at some point.", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public int hasNextButton() {
		return STATUS_DISABLED;
	}

	@Override
	public int hasPrevButton() {
		return STATUS_ENABLED;
	}

	@Override
	int getStep() {
		return 2;
	}

	@Override
	public RelativePagerFragment getNext(FragmentStateManager fsm) {
		return new Step4AllDoneFragment();
	}

	@Override
	public RelativePagerFragment getPrev(FragmentStateManager fsm) {
		return new Step2aSearchingFragment();
	}
}
