package org.xbmc.android.app.manager;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import de.greenrobot.event.EventBus;
import org.xbmc.android.account.Constants;
import org.xbmc.android.app.event.HostSwitched;
import org.xbmc.android.app.injection.Injector;
import org.xbmc.android.zeroconf.XBMCHost;

import javax.inject.Inject;
import java.util.ArrayList;

/**
 * Bridges access to {@link AccountManager}. Can add and retrieve accounts using the {@link XBMCHost} object.
 *
 * @author freezy <freezy@xmbmc.org>
 */
public class HostManager {

	public static final String PREFS_NAME = "preferences";
	public static final String PREFS_CURRENT_HOST = "current_host";

	@Inject AccountManager accountManager;
	@Inject Context context;
	@Inject EventBus bus;

	public HostManager() {
		Injector.inject(this);
	}

	/**
	 * Adds the host as {@link Account} to the system.
	 *
	 * @param host Host to add
	 */
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

	/**
	 * Retrieves all XBMC accounts and returns them as a list of {@link XBMCHost}.
	 *
	 * @return Hosts added to the system
	 */
	public ArrayList<XBMCHost> getHosts() {
		final Account[] accounts = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE);
		final ArrayList<XBMCHost> hosts = new ArrayList<XBMCHost>(accounts.length);
		final SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		final String activeHost = settings.getString(PREFS_CURRENT_HOST, null);
		for (Account account : accounts) {
			final XBMCHost host = new XBMCHost(
				accountManager.getUserData(account, Constants.DATA_ADDRESS),
				accountManager.getUserData(account, Constants.DATA_HOST),
				Integer.parseInt(accountManager.getUserData(account, Constants.DATA_PORT)),
				account.name
			);
			host.setCredentials(
				accountManager.getUserData(account, Constants.DATA_USER),
				accountManager.getUserData(account, Constants.DATA_PASS)
			);
			host.setActive(activeHost != null && activeHost.equals(account.name));
			hosts.add(host);
		}
		return hosts;
	}

	/**
	 * Switches the current host that is used by the app.
	 * @param host Host to switch to
	 */
	public void switchHost(XBMCHost host) {
		final SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(PREFS_CURRENT_HOST, host.getName());
		editor.commit();
		bus.post(new HostSwitched(host));
	}

	/**
	 * Returns the currently active host of the app.
	 * @return Currently active host
	 */
	public XBMCHost getActiveHost() {
		final SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
		if (!settings.contains(PREFS_CURRENT_HOST)) {
			return null;
		}
		final String name = settings.getString(PREFS_CURRENT_HOST, null);
		final ArrayList<XBMCHost> hosts = getHosts();
		for (XBMCHost host : hosts) {
			if (name.equals(host.getName())) {
				return host;
			}
		}
		return null;
	}

}
