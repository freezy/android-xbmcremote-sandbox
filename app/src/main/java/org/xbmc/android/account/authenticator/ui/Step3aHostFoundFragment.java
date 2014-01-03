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

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import org.xbmc.android.account.Constants;
import org.xbmc.android.app.injection.Injector;
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
import java.util.ArrayList;

import static org.xbmc.android.app.ui.HostChooseActivity.HostListAdapter;

public class Step3aHostFoundFragment extends WizardFragment {

	private final static String DATA_HOSTS = "org.xbmc.android.account.HOSTS";

	private ArrayList<XBMCHost> hosts;
	private XBMCHost selectedHost;

	private ProgressDialog waiting;

	private boolean hasNext = false;

	@Inject AccountManager accountManager;
	@InjectView(R.id.list) ListView listView;

	public Step3aHostFoundFragment() {
		super(R.layout.fragment_auth_wizard_03a_host_found);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		waiting = new ProgressDialog(getActivity());
		Injector.inject(this);

		if (savedInstanceState != null) {
			hasNext = savedInstanceState.getBoolean(DATA_HAS_NEXT, false);
			final Parcelable[] hostArray = savedInstanceState.getParcelableArray(DATA_HOSTS);
			if (hostArray != null) {
				hosts = new ArrayList<XBMCHost>(hostArray.length);
				for (Parcelable host : hostArray) {
					hosts.add((XBMCHost) host);
				}
			}
		}
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

		final Handler handler = new Handler();
		final HostListAdapter adapter = new HostListAdapter(getActivity().getApplicationContext(), R.layout.list_item_host_wide, hosts);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				final XBMCHost host = hosts.get(position);

				final Account[] accounts = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE);
				boolean match = false;
				for (Account account : accounts) {
					if (account.name.equals(host.getName())) {
						match = true;
					}
				}
				if (match) {
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setMessage(R.string.accountwizard_step3a_already_added)
						.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						}).create().show();
					return;
				}


				waiting.setTitle(String.format(getResources().getString(R.string.accountwizard_step3a_waiting_title), host.getName()));
				waiting.setMessage(String.format(getResources().getString(R.string.accountwizard_step3a_waiting_message), host.getAddress(), host.getPort()));
				waiting.setIndeterminate(true);
				waiting.setCancelable(false);
				waiting.show();

				final ConnectionManager cm = new ConnectionManager(getActivity().getApplicationContext(), host.toHostConfig());
				final JSONRPC.Ping call = new JSONRPC.Ping();

				cm.setPreferHTTP();
				cm.call(call, handler, pingCallback(cm, host, handler, call, false));
			}
		});
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(DATA_HAS_NEXT, hasNext);
		outState.putParcelableArray(DATA_HOSTS, hosts.toArray(new Parcelable[hosts.size()]));
	}

	private ApiCallback<String> pingCallback(final ConnectionManager cm, final XBMCHost host, final Handler handler, final JSONRPC.Ping call, final boolean displayError) {
		return new ApiCallback<String>() {
			@Override
			public void onResponse(AbstractCall<String> call) {
				waiting.dismiss();
				selectedHost = host;
				hasNext = true;
				statusChangeListener.onNextPage();
				Toast.makeText(getActivity().getApplicationContext(), call.getResult(), Toast.LENGTH_LONG).show();
			}

			@Override
			public void onError(int code, String message, String hint) {
				waiting.dismiss();
				if (code == ApiException.HTTP_UNAUTHORIZED) {

					if (displayError) {
						Toast.makeText(getActivity().getApplicationContext(), R.string.accountwizard_step3a_login_error, Toast.LENGTH_SHORT).show();
					}

					// create login box
					final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					final LayoutInflater inflater = getActivity().getLayoutInflater();
					final View loginView = inflater.inflate(R.layout.dialog_login, null);
					builder.setView(loginView);
					builder.setTitle(R.string.accountwizard_step3a_login_title);
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
	public int hasNextButton() {
		return STATUS_GONE;
	}

	@Override
	public int hasPrevButton() {
		return STATUS_ENABLED;
	}

	@Override
	public boolean hasNext() {
		return hasNext;
	}

	@Override
	public int getPrevButtonLabel() {
		return R.string.accountwizard_step3a_prev;
	}

	@Override
	int getStep() {
		return 2;
	}

	@Override
	public RelativePagerFragment getNext(FragmentStateManager fsm) {
		final RelativePagerFragment fragment = fsm.getFragment(Step4AllDoneFragment.class);
		((Step4AllDoneFragment)fragment).setHost(selectedHost);
		return fragment;
	}

	@Override
	public RelativePagerFragment getPrev(FragmentStateManager fsm) {
		return fsm.getFragment(Step2aSearchingFragment.class);
	}

	public void setHosts(ArrayList<XBMCHost> hosts) {
		this.hosts = hosts;
	}


	@Override
	public void onPause() {
		super.onPause();
		if (waiting.isShowing()) {
			waiting.dismiss();
		}
	}
}
