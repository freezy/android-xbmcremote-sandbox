package org.xbmc.android.zeroconf;

import java.io.IOException;
import java.net.InetAddress;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;

import android.app.IntentService;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.MulticastLock;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

public class DiscoveryService extends IntentService {

	private static final String TAG = DiscoveryService.class.getSimpleName();

	private static final String SERVICENAME_JSONRPC = "_xbmc-jsonrpc._tcp.local.";
	
	public static final int STATUS_RESOLVED = 0x1;
	public static final int STATUS_ERROR = 0xff;

	public static final String EXTRA_STATUS_RECEIVER = "org.xbmc.android.zeroconf.extra.STATUS_RECEIVER";
	public static final String EXTRA_HOST = "org.xbmc.android.zeroconf.extra.HOST";

	private MulticastLock mMulticastLock;

	private JmDNS mJmDns = null;
	private ServiceListener mListener = null;

	public DiscoveryService() {
		super(TAG);
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		Log.i(TAG, "Starting zeroconf discovery service...");
		final WifiManager wifi = (WifiManager) getSystemService(android.content.Context.WIFI_SERVICE);
		mMulticastLock = wifi.createMulticastLock("xbmc-zeroconf-discover");
		mMulticastLock.setReferenceCounted(true);
		mMulticastLock.acquire();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		Log.i(TAG, "Handelling new intent...");
		
		final ResultReceiver receiver = intent.getParcelableExtra(EXTRA_STATUS_RECEIVER);
		
		try {
			mJmDns = JmDNS.create();
			mJmDns.addServiceListener(SERVICENAME_JSONRPC, mListener = new ServiceListener() {
				
				@Override
				public void serviceResolved(ServiceEvent ev) {
					Log.e(TAG, "Service resolved: " + ev.getInfo().getQualifiedName() + ") port:" + ev.getInfo().getPort());
					final InetAddress[] addresses = ev.getInfo().getInet4Addresses();
					XBMCHost host = null;
					for (int i = 0; i < addresses.length; ) {
						Log.e(TAG, "IP address: " + addresses[i].getHostAddress());
						host = new XBMCHost(addresses[i].getHostAddress(), ev.getInfo().getQualifiedName(), ev.getInfo().getPort());
						break;
					}
					
					if (receiver != null) {
						if (host != null) {
							final Bundle bundle = new Bundle();
							bundle.putParcelable(EXTRA_HOST, host);
							receiver.send(STATUS_RESOLVED, bundle);
						} else {
							receiver.send(STATUS_ERROR, Bundle.EMPTY);
						}
					}
				}
				
				@Override
				public void serviceRemoved(ServiceEvent ev) {
					Log.d(TAG, "Service removed: " + ev.getName());
				}
				
				@Override
				public void serviceAdded(ServiceEvent event) {
					Log.d(TAG, "Service added.");
					// Required to force serviceResolved to be called
					// again (after the first search)
					mJmDns.requestServiceInfo(event.getType(), event.getName(), 1);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	@Override
	public void onDestroy() {
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
		Log.i(TAG, "Zeroconf discovery service stopped.");
		super.onDestroy();
	}
}
