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
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import org.xbmc.android.app.ui.IconHelper;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.view.RelativePagerFragment;
import org.xbmc.android.zeroconf.XBMCHost;

import java.util.ArrayList;
import java.util.List;

public class Step3aHostFoundFragment extends WizardFragment {

	private final ArrayList<XBMCHost> hosts;
	private final Typeface iconFont;

	@InjectView(R.id.list) ListView listView;

	protected Step3aHostFoundFragment(ArrayList<XBMCHost> hosts,  Activity activity, OnStatusChangeListener statusChangeListener) {
		super(R.layout.fragment_auth_wizard_03a_host_found, activity, statusChangeListener);
		this.hosts = hosts;
		this.iconFont =  IconHelper.getTypeface(activity.getApplicationContext());

		this.hosts.add(new XBMCHost("192.168.0.100", "aquarium", 8080));
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

		final HostListAdapter adapter = new HostListAdapter(activity.getApplicationContext(), R.layout.list_item_host_wide, hosts);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				final XBMCHost host = hosts.get(position);
				Toast.makeText(activity.getApplicationContext(), host.getAddress(), Toast.LENGTH_LONG).show();
			}
		});
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

			iconView.setTypeface(iconFont);
			titleView.setText(values.get(position).getHost());
			subtitleView.setText(values.get(position).getAddress());

			return rowView;
		}

	}

}
