package org.xbmc.android.app.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import org.xbmc.android.app.injection.Injector;
import org.xbmc.android.app.manager.HostManager;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.zeroconf.XBMCHost;

import javax.inject.Inject;
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

		final HostListAdapter adapter = new HostListAdapter(getApplicationContext(), R.layout.list_item_host_wide, hostManager.getHosts());
		listView.setAdapter(adapter);

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

			final XBMCHost host = values.get(position);
			iconView.setTypeface(iconFont);
			titleView.setText(host.getName());
			subtitleView.setText(host.getAddress() + ":" + host.getPort());

			return rowView;
		}

	}
}
