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

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import org.xbmc.android.app.injection.Injector;
import org.xbmc.android.app.manager.HostManager;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.api.call.JSONRPC;
import org.xbmc.android.jsonrpc.io.ApiCallback;
import org.xbmc.android.jsonrpc.io.ApiException;
import org.xbmc.android.jsonrpc.io.ConnectionManager;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.view.FragmentStateManager;
import org.xbmc.android.view.RelativePagerFragment;
import org.xbmc.android.zeroconf.XBMCHost;

import javax.inject.Inject;

public class Step3bManualSetupFragment extends WizardFragment {

	@InjectView(R.id.display_name) EditText displayNameView;
	@InjectView(R.id.host) EditText hostView;
	@InjectView(R.id.port) EditText portView;
	@InjectView(R.id.message) TextView messageView;

	@Inject HostManager hostManager;

	private ProgressDialog waiting;
	private XBMCHost enteredHost;
	private boolean hostVerified = false;

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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		waiting = new ProgressDialog(getActivity());
		Injector.inject(this);
	}

	private ApiCallback<String> pingCallback(final ConnectionManager cm, final XBMCHost host, final Handler handler, final JSONRPC.Ping call, final boolean displayError) {
		return new ApiCallback<String>() {
			@Override
			public void onResponse(AbstractCall<String> call) {
				waiting.dismiss();
				enteredHost = host;
				hostVerified = true;
				displayNameView.setEnabled(false);
				hostView.setEnabled(false);
				portView.setEnabled(false);
				messageView.setVisibility(View.VISIBLE);
				statusChangeListener.onStatusUpdated();
			}

			@Override
			public void onError(int code, String message, String hint) {
				waiting.dismiss();
				if (code == ApiException.HTTP_UNAUTHORIZED) {

					if (displayError) {
						Toast.makeText(getActivity().getApplicationContext(), R.string.accountwizard_step3_login_error, Toast.LENGTH_SHORT).show();
					}

					// create login box
					final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					final LayoutInflater inflater = getActivity().getLayoutInflater();
					final View loginView = inflater.inflate(R.layout.dialog_login, null);
					builder.setView(loginView);
					builder.setTitle(R.string.accountwizard_step3_login_title);
					builder.setPositiveButton(R.string.set, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							final EditText login = (EditText)loginView.findViewById(R.id.username);
							final EditText pass = (EditText)loginView.findViewById(R.id.password);
							host.setCredentials(login.getText().toString(), pass.getText().toString());
							cm.setHostConfig(host.toHostConfig());
							cm.call(call, handler, pingCallback(cm, host, handler, call, true));
						}
					});
					builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.dismiss();
						}
					});
					builder.create().show();
				} else {
					Toast.makeText(getApplicationContext(), "(" + code + ") " + message + " " + hint, Toast.LENGTH_LONG).show();
				}
			}
		};
	}

	@Override
	public boolean onNextClicked() {
		if (!hostVerified) {
			final Handler handler = new Handler();
			final String addr = hostView.getText().toString();
			final int port = portView.getText().toString().isEmpty() ? 0 : Integer.parseInt(portView.getText().toString());
			final XBMCHost host = new XBMCHost(addr, addr, port, displayNameView.getText().toString());

			// check for empty name
			if (host.getName() == null || host.getName().isEmpty()) {
				final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setMessage(R.string.accountwizard_step3_empty_name)
						.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						}).create().show();
				return true;
			}
			// check for duplicate name
			if (hostManager.hostExists(host)) {
				final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setMessage(R.string.accountwizard_step3_already_added)
						.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						}).create().show();
				return true;
			}

			waiting.setTitle(String.format(getResources().getString(R.string.accountwizard_step3_waiting_title), host.getName()));
			waiting.setMessage(String.format(getResources().getString(R.string.accountwizard_step3_waiting_message), host.getAddress(), host.getPort()));
			waiting.setIndeterminate(true);
			waiting.setCancelable(false);
			waiting.setOnShowListener(new DialogInterface.OnShowListener() {
				@Override
				public void onShow(DialogInterface dialog) {
					final ConnectionManager cm = new ConnectionManager(getActivity().getApplicationContext(), host.toHostConfig());
					final JSONRPC.Ping call = new JSONRPC.Ping();

					cm.setPreferHTTP();
					cm.call(call, handler, pingCallback(cm, host, handler, call, false));
				}
			});
			waiting.show();

			return true;

		} else {
			return false;
		}
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
		return 2;
	}

	@Override
	public RelativePagerFragment getNext(FragmentStateManager fsm) {
		final RelativePagerFragment fragment = fsm.getFragment(Step4AllDoneFragment.class);
		((Step4AllDoneFragment)fragment).setHost(enteredHost);
		return fragment;
	}

	@Override
	public RelativePagerFragment getPrev(FragmentStateManager fsm) {
		return fsm.getFragment(Step2aSearchingFragment.class);
	}

	@Override
	public int getPrevButtonLabel() {
		return R.string.accountwizard_step3b_prev;
	}

	@Override
	public int getNextButtonLabel() {
		return hostVerified ? R.string.next : R.string.accountwizard_step3b_next;
	}
}
