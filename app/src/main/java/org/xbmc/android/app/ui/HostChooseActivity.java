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

package org.xbmc.android.app.ui;

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
import de.greenrobot.event.EventBus;
import org.xbmc.android.app.injection.Injector;
import org.xbmc.android.app.manager.HostManager;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.zeroconf.XBMCHost;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class HostChooseActivity extends Activity {

	@Inject EventBus bus;
	@Inject HostManager hostManager;

	@InjectView(R.id.list) ListView listView;
	@InjectView(R.id.cancel) Button cancelBtn;
	@InjectView(R.id.ok) Button okBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_host_choose);
		setTitle(R.string.host_choose_title);

		ButterKnife.inject(this);
		Injector.inject(this);

		final ArrayList<XBMCHost> hosts = hostManager.getHosts();
		final HostListAdapter adapter = new HostListAdapter(getApplicationContext(), R.layout.list_item_host_wide, hosts);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				final int s = hosts.size();
				for (int i = 0; i < s; i++) {
					hosts.get(i).setActive(i == position);
				}
				adapter.notifyDataSetChanged();
			}
		});

		okBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				for (XBMCHost host : hosts) {
					if (host.isActive()) {
						hostManager.switchHost(host);
						break;
					}
				}
				finish();
			}
		});

		cancelBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	public static class HostListAdapter extends ArrayAdapter<XBMCHost> {

		private Typeface iconFont;

		private final Context context;
		private final List<XBMCHost> values;

		public HostListAdapter(Context context, int resource, List<XBMCHost> values) {
			super(context, resource, values);
			this.context = context;
			this.values = values;
			this.iconFont = IconHelper.getTypeface(context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View rowView = inflater.inflate(R.layout.list_item_host_wide, parent, false);
			final TextView iconView = (TextView) rowView.findViewById(R.id.list_icon);
			final TextView titleView = (TextView) rowView.findViewById(R.id.title_host);
			final TextView subtitleView = (TextView) rowView.findViewById(R.id.address_host);
			final View overlay = rowView.findViewById(R.id.card_selected);

			final XBMCHost host = values.get(position);
			iconView.setTypeface(iconFont);
			titleView.setText(host.getName());
			subtitleView.setText(host.getAddress() + ":" + host.getPort());
			if (host.isActive()) {
				overlay.setVisibility(View.VISIBLE);
			}

			return rowView;
		}

	}
}
