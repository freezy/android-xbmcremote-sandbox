package org.xbmc.android.app.ui;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import org.xbmc.android.account.Constants;
import org.xbmc.android.app.injection.Injector;
import org.xbmc.android.remotesandbox.R;

import javax.inject.Inject;

public class HostChooseActivity extends Activity {

	@Inject EventBus bus;
	@Inject AccountManager accountManager;
	@InjectView(R.id.list) ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_host_choose);
		setTitle(R.string.host_choose_title);

		ButterKnife.inject(this);
		Injector.inject(this);

		final Account[] accounts = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE);
		for (Account account : accounts) {
		}

	}
}
