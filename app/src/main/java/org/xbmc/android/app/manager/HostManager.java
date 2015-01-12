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
import org.xbmc.android.jsonrpc.io.ConnectionManager;
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
	@Inject ConnectionManager connectionManager;

	public HostManager() {
		Injector.inject(this);
		final XBMCHost host = getActiveHost();
		if (host != null) {
			connectionManager.setHostConfig(host.toHostConfig());
		}
	}

	/**
	 * Adds the host as {@link Account} to the system.
	 *
	 * @param host Host to add
	 */
	public void addAccount(XBMCHost host) {
		// find id
		final Account[] accounts = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE);
		int lastId = -1;
		for (Account a : accounts) {
			final int id = Integer.parseInt(accountManager.getUserData(a, Constants.DATA_ID));
			lastId = Math.max(id, lastId);
		}

		final Account account = new Account(host.getName(), Constants.ACCOUNT_TYPE);
		final Bundle data = new Bundle();
		host.setId(lastId + 1);

		data.putString(Constants.DATA_ID, String.valueOf(lastId + 1));
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
				Integer.parseInt(accountManager.getUserData(account, Constants.DATA_ID)),
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
	 * Returns whether at least one host is setup.
	 */
	public boolean hasHost() {
		return !getHosts().isEmpty();
	}

	/**
	 * Tries to match current hosts by name and returns true on a hit.
	 *
	 * @param host Host to match against
	 * @return True if host exists, false otherwise.
	 */
	public boolean hostExists(XBMCHost host) {
		final Account[] accounts = accountManager.getAccountsByType(Constants.ACCOUNT_TYPE);
		for (Account account : accounts) {
			if (account.name.equals(host.getName())) {
				return true;
			}
		}
		return false;
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
		connectionManager.setHostConfig(host.toHostConfig());
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

	public String getActiveUri() {
		final XBMCHost host = getActiveHost();
		return host == null ? null : host.getUri();
	}

	/**
	 * Returns the URI of the active host without trailing slash, with optional credentials (user info).
	 * If no credentials present, returns the URI without them
	 * @param includeUserInfo Whether to include credentials in the URI
	 * @return URI, e.g "http://user:pass@127.0.0.1:8080".
	 */
	public String getActiveUri(boolean includeUserInfo) {
		final XBMCHost host = getActiveHost();
		return host == null ? null : host.getUri(includeUserInfo);
	}

}
