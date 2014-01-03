package org.xbmc.android.app.manager;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import org.xbmc.android.account.Constants;
import org.xbmc.android.app.injection.Injector;
import org.xbmc.android.zeroconf.XBMCHost;

import javax.inject.Inject;
import java.util.ArrayList;

public class HostManager {

	@Inject AccountManager accountManager;

	public HostManager() {
		Injector.inject(this);
	}

	public void addAccount(XBMCHost host) {
		final Account account = new Account(host.getName(), Constants.ACCOUNT_TYPE);
		final Bundle data = new Bundle();
		data.putString(Constants.DATA_HOST, host.getHost());
		data.putString(Constants.DATA_ADDRESS, host.getAddress());
		data.putString(Constants.DATA_PORT, String.valueOf(host.getPort()));
		data.putString(Constants.DATA_USER, host.getUser());
		data.putString(Constants.DATA_PASS, host.getPass());
		accountManager.addAccountExplicitly(account, null, data);
	}

	public ArrayList<XBMCHost> getHosts() {
		final Account[] accounts = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE);
		final ArrayList<XBMCHost> hosts = new ArrayList<XBMCHost>(accounts.length);
		for (Account account : accounts) {
			final XBMCHost host = new XBMCHost(
					accountManager.getUserData(account, Constants.DATA_ADDRESS),
					accountManager.getUserData(account, Constants.DATA_HOST),
					Integer.parseInt(accountManager.getUserData(account, Constants.DATA_PORT)),
					accountManager.getUserData(account, account.name)
			);
			hosts.add(host);
		}
		return hosts;
	}

}
