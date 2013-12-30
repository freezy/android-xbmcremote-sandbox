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
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import org.xbmc.android.app.ui.IconHelper;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.api.call.JSONRPC;
import org.xbmc.android.jsonrpc.io.ApiCallback;
import org.xbmc.android.jsonrpc.io.ApiException;
import org.xbmc.android.jsonrpc.io.ConnectionManager;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.view.RelativePagerFragment;
import org.xbmc.android.zeroconf.XBMCHost;

import java.util.ArrayList;
import java.util.List;

public class Step3aHostFoundFragment extends WizardFragment {

	private final ArrayList<XBMCHost> hosts;
	private final Typeface iconFont;
	private final ProgressDialog waiting;

	@InjectView(R.id.list) ListView listView;

	protected Step3aHostFoundFragment(ArrayList<XBMCHost> hosts,  Activity activity, OnStatusChangeListener statusChangeListener) {
		super(R.layout.fragment_auth_wizard_03a_host_found, activity, statusChangeListener);
		this.hosts = hosts;
		this.iconFont =  IconHelper.getTypeface(activity.getApplicationContext());
		this.waiting = new ProgressDialog(activity);

		this.hosts.add(new XBMCHost("192.168.0.100", "aquarium", 8080, "XBMC Fake"));
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
		final HostListAdapter adapter = new HostListAdapter(activity.getApplicationContext(), R.layout.list_item_host_wide, hosts);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				final XBMCHost host = hosts.get(position);
				waiting.setTitle(String.format(activity.getResources().getString(R.string.accountwizard_step3a_waiting_title), host.getName()));
				waiting.setMessage(String.format(activity.getResources().getString(R.string.accountwizard_step3a_waiting_message), host.getAddress(), host.getPort()));
				waiting.setIndeterminate(true);
				waiting.setCancelable(false);
				waiting.show();

				final ConnectionManager cm = new ConnectionManager(activity.getApplicationContext(), host.toHostConfig());
				final JSONRPC.Ping call = new JSONRPC.Ping();

				cm.setPreferHTTP();
				cm.call(call, handler, pingCallback(cm, host, handler, call, false));
			}
		});
	}

	private ApiCallback<String> pingCallback(final ConnectionManager cm, final XBMCHost host, final Handler handler, final JSONRPC.Ping call, final boolean displayError) {
		return new ApiCallback<String>() {
			@Override
			public void onResponse(AbstractCall<String> call) {
				waiting.hide();
				Toast.makeText(activity, call.getResult(), Toast.LENGTH_LONG).show();
			}

			@Override
			public void onError(int code, String message, String hint) {
				waiting.hide();
				if (code == ApiException.HTTP_UNAUTHORIZED) {

					if (displayError) {
						Toast.makeText(activity, R.string.accountwizard_step3a_login_error, Toast.LENGTH_SHORT).show();
					}

					// create login box
					final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
					final LayoutInflater inflater = activity.getLayoutInflater();
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
					Toast.makeText(activity, "(" + code + ") " + message + " " + hint, Toast.LENGTH_LONG).show();
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
	public int getPrevButtonLabel() {
		return R.string.accountwizard_step3a_prev;
	}

	@Override
	int getStep() {
		return 2;
	}

	@Override
	public RelativePagerFragment getNext() {
		return new Step2aSearchingFragment(activity, statusChangeListener);
	}

	@Override
	public RelativePagerFragment getPrev() {
		return new Step2aSearchingFragment(activity, statusChangeListener);
	}

	private class HostListAdapter extends ArrayAdapter<XBMCHost> {

		private final Context context;
		private final List<XBMCHost> values;

		public HostListAdapter(Context context, int resource, List<XBMCHost> values) {
			super(context, resource, values);
			this.context = context;
			this.values = values;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View rowView = inflater.inflate(R.layout.list_item_host_wide, parent, false);
			final TextView iconView = (TextView) rowView.findViewById(R.id.list_icon);
			final TextView titleView = (TextView) rowView.findViewById(R.id.title_host);
			final TextView subtitleView = (TextView) rowView.findViewById(R.id.address_host);

			final XBMCHost host = values.get(position);
			iconView.setTypeface(iconFont);
			titleView.setText(host.getName());
			subtitleView.setText(host.getAddress() + ":" + host.getPort());

			return rowView;
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		if (waiting.isShowing()) {
			waiting.hide();
		}
	}
}
