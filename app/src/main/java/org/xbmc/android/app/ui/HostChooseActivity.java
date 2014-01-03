package org.xbmc.android.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import org.xbmc.android.app.injection.Injector;
import org.xbmc.android.app.manager.HostManager;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.zeroconf.XBMCHost;

import javax.inject.Inject;

public class HostChooseActivity extends Activity {

	@Inject EventBus bus;
	@Inject HostManager hostManager;
	@InjectView(R.id.list) ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_host_choose);
		setTitle(R.string.host_choose_title);

		ButterKnife.inject(this);
		Injector.inject(this);

		for (XBMCHost host : hostManager.getHosts()) {
			Log.d("test", host.toString());
		}

	}
}
