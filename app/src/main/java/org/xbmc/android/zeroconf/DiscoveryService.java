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

package org.xbmc.android.zeroconf;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.MulticastLock;
import android.text.format.Formatter;
import android.util.Log;
import de.greenrobot.event.EventBus;
import org.xbmc.android.event.ZeroConf;
import org.xbmc.android.injection.Injector;

import javax.inject.Inject;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.xbmc.android.event.ZeroConf.*;

/**
 * This service scans for xbmc-jsonrpc hosts and returns one by one by using the
 * callback receiver.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class DiscoveryService extends IntentService {

	private static final String TAG = DiscoveryService.class.getSimpleName();

	@Inject protected EventBus bus;

	private static final int TIMEOUT = 2000;
	private static final String SERVICENAME_JSONRPC = "_xbmc-jsonrpc._tcp.local.";


	public static final String EXTRA_HOST = "org.xbmc.android.zeroconf.extra.HOST";

	private WifiManager mWifiManager;
	private MulticastLock mMulticastLock;
	private InetAddress mWifiAddress = null;
	private JmDNS mJmDns = null;

	private ServiceListener mListener = null;

	public DiscoveryService() {
		super(TAG);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Injector.inject(this);
		mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		try {
			final int ipAddress = mWifiManager.getConnectionInfo().getIpAddress();
			if (ipAddress != 0) {
				mWifiAddress = InetAddress.getByName(Formatter.formatIpAddress(ipAddress));
				Log.i(TAG, "Discovering XBMC hosts through " + mWifiAddress.getHostAddress() + "...");
			} else {
				Log.i(TAG, "Discovering XBMC hosts on all interfaces...");
			}
		} catch (UnknownHostException e) {
			Log.e(TAG, "Cannot parse Wifi IP address.", e);
			// continue, JmDNS can also run with IP address.
		}

		acquireMulticastLock();
		new Thread() {
			@Override
			public void run() {
				listen();
			}
		}.start();

		try {
			Thread.sleep(TIMEOUT);
		} catch (InterruptedException e) {
			Log.e(TAG, "Error sleeping " + TIMEOUT + "ms.", e);
		}
		bus.post(new ZeroConf(STATUS_FINISHED));
		releaseMulticastLock();
	}

	/**
	 * Launches the zeroconf listener
	 */
	private void listen() {

		try {
			if (mWifiAddress == null) {
				mJmDns = JmDNS.create();
			} else {
				mJmDns = JmDNS.create(mWifiAddress);
			}
			mJmDns.addServiceListener(SERVICENAME_JSONRPC, mListener = new ServiceListener() {

				@Override
				public void serviceResolved(ServiceEvent event) {
					Log.d(TAG, "Service resolved: " + event.getName() + " / " + event.getType());
					notifyEvent(event);
				}

				@Override
				public void serviceRemoved(ServiceEvent event) {
					Log.d(TAG, "Service removed: " + event.getName() + " / " + event.getType());
				}

				@Override
				public void serviceAdded(ServiceEvent event) {
					Log.d(TAG, "Service added:" + event.getName() + " / " + event.getType());
					// Required to force serviceResolved to be called
					// again (after the first search)
					mJmDns.requestServiceInfo(event.getType(), event.getName(), 1);
				}
			});
		} catch (IOException e) {
			Log.e(TAG, "Error listening to multicast: " + e.getMessage(), e);
			bus.post(new ZeroConf(STATUS_ERROR));
		}
	}

	private void notifyEvent(ServiceEvent event) {
		final InetAddress[] addresses = event.getInfo().getInet4Addresses();
		final String hostname = event.getInfo().getServer().replace(".local.", "");
		XBMCHost host = null;
		if (addresses.length == 0) {
			Inet6Address[] v6addresses = event.getInfo().getInet6Addresses();
			if (v6addresses.length == 0) {
				Log.e(TAG, "Could not obtain IP address for " + event.getInfo());
				bus.post(new ZeroConf(STATUS_ERROR));
				return;
			} else {
				for (int i = 0; i < v6addresses.length; ) {
					host = new XBMCHost(v6addresses[i].getHostAddress(), hostname, event.getInfo().getPort());
					break;
				}
			}
		} else {
			for (int i = 0; i < addresses.length; ) {
				Log.i(TAG, "IP address: " + addresses[i].getHostAddress());
				host = new XBMCHost(addresses[i].getHostAddress(), hostname, event.getInfo().getPort());
				break;
			}
		}

		if (host != null) {
			bus.post(new ZeroConf(STATUS_RESOLVED, host));
		} else {
			bus.post(new ZeroConf(STATUS_ERROR));
		}
	}

	private void acquireMulticastLock() {
		mMulticastLock = mWifiManager.createMulticastLock("xbmc-zeroconf-discover");
		mMulticastLock.setReferenceCounted(true);
		mMulticastLock.acquire();
		Log.d(TAG, "Multicast lock acquired.");
	}

	private void releaseMulticastLock() {
		Log.d(TAG, "Multicast lock released.");
		if (mJmDns != null) {
			if (mListener != null) {
				mJmDns.removeServiceListener(SERVICENAME_JSONRPC, mListener);
				mListener = null;
			}
			try {
				mJmDns.unregisterAllServices();
				mJmDns.close();
			} catch (IOException e) {
				Log.e(TAG, "Error closing JmDNS: " + e.getMessage(), e);
			}
			mJmDns = null;
		}
		mMulticastLock.release();
	}
}
