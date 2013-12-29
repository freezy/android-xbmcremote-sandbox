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

	private static final int TIMEOUT = 3000;
//	private static final String SERVICENAME_JSONRPC = "_xbmc-jsonrpc._tcp.local.";
	private static final String SERVICENAME_JSONRPC = "_xbmc-jsonrpc-h._tcp.local.";

	private WifiManager wifiManager;
	private MulticastLock multicastLock;
	private InetAddress wifiAddress = null;
	private JmDNS jmDns = null;

	private ServiceListener mListener = null;

	public DiscoveryService() {
		super(TAG);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Injector.inject(this);
		wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		try {
			final int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
			if (ipAddress != 0) {
				wifiAddress = InetAddress.getByName(Formatter.formatIpAddress(ipAddress));
				Log.i(TAG, "Discovering XBMC hosts through " + wifiAddress.getHostAddress() + "...");
			} else {
				Log.i(TAG, "Discovering XBMC hosts on all interfaces...");
			}
		} catch (UnknownHostException e) {
			Log.e(TAG, "Cannot parse Wifi IP address.", e);
			// continue, JmDNS can also run without IP address.
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
			if (wifiAddress == null) {
				jmDns = JmDNS.create();
			} else {
				jmDns = JmDNS.create(wifiAddress);
			}
			jmDns.addServiceListener(SERVICENAME_JSONRPC, mListener = new ServiceListener() {

				@Override
				public void serviceResolved(ServiceEvent event) {
					Log.d(TAG, "Service resolved: " + event.getName() + " / " + event.getType());
					DiscoveryService.this.serviceResolved(event);
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
					jmDns.requestServiceInfo(event.getType(), event.getName(), 1);
				}
			});
		} catch (IOException e) {
			Log.e(TAG, "Error listening to multicast: " + e.getMessage(), e);
			bus.post(new ZeroConf(STATUS_ERROR));
		}
	}

	private void serviceResolved(ServiceEvent event) {
		final InetAddress[] addresses = event.getInfo().getInet4Addresses();
		final String hostname = event.getInfo().getServer().replace(".local.", "");
		final XBMCHost host;
		if (addresses.length == 0) {
			Inet6Address[] v6addresses = event.getInfo().getInet6Addresses();
			if (v6addresses.length == 0) {
				Log.e(TAG, "Could not obtain IP address for " + event.getInfo());
				bus.post(new ZeroConf(STATUS_ERROR));
				return;
			} else {
				host = new XBMCHost(v6addresses[0].getHostAddress(), hostname, event.getInfo().getPort());
			}
		} else {
			Log.d(TAG, "Discovered IP address: " + addresses[0].getHostAddress());
			host = new XBMCHost(addresses[0].getHostAddress(), hostname, event.getInfo().getPort());
		}

		bus.post(new ZeroConf(STATUS_RESOLVED, host));
	}

	private void acquireMulticastLock() {
		multicastLock = wifiManager.createMulticastLock("xbmc-zeroconf-discover");
		multicastLock.setReferenceCounted(true);
		multicastLock.acquire();
		Log.d(TAG, "Multicast lock acquired.");
	}

	private void releaseMulticastLock() {
		Log.d(TAG, "Multicast lock released.");
		if (jmDns != null) {
			if (mListener != null) {
				jmDns.removeServiceListener(SERVICENAME_JSONRPC, mListener);
				mListener = null;
			}
			try {
				jmDns.unregisterAllServices();
				jmDns.close();
			} catch (IOException e) {
				Log.e(TAG, "Error closing JmDNS: " + e.getMessage(), e);
			}
			jmDns = null;
		}
		multicastLock.release();
	}
}
