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

package org.xbmc.android.remotesandbox.ui;

import java.io.IOException;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;

import org.xbmc.android.remotesandbox.R;

import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.MulticastLock;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class HomeActivity extends BaseActivity {
	
	private final static String TAG = HomeActivity.class.getSimpleName();
	
	private MulticastLock mLock;
	private final static String ZEROCONF_SERVICE_JSONRPC = "_xbmc-jsonrpc._tcp.local.";
	private final static String ZEROCONF_SERVICE_EVENTSERVER = "_xbmc-events._udp.local.";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_home);
		getActivityHelper().setupActionBar(null, 0);

		// FragmentManager fm = getSupportFragmentManager();

		WifiManager wifi = (WifiManager) getSystemService(android.content.Context.WIFI_SERVICE);
		mLock = wifi.createMulticastLock("xbmc-zeroconf-discover");
		mLock.setReferenceCounted(true);
		mLock.acquire();
		
		new Thread() {
			@Override
			public void run() {
				try {
					        
					final JmDNS jmdns = JmDNS.create();
					final ServiceListener listener;
					jmdns.addServiceListener(ZEROCONF_SERVICE_JSONRPC, listener = new ServiceListener() {
						public void serviceResolved(ServiceEvent ev) {
							Log.e(TAG, "Service resolved: " + ev.getInfo().getQualifiedName() + " port:" + ev.getInfo().getPort());
						}

						public void serviceRemoved(ServiceEvent ev) {
							Log.e(TAG, "Service removed: " + ev.getName());
						}

						public void serviceAdded(ServiceEvent event) {
							// Required to force serviceResolved to be called
							// again
							// (after the first search)
							jmdns.requestServiceInfo(event.getType(), event.getName(), 1);
						}
					});

					jmdns.removeServiceListener(ZEROCONF_SERVICE_JSONRPC, listener);
					jmdns.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mLock != null) {
			mLock.release();
		}

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		getActivityHelper().setupHomeActivity();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.refresh_menu_items, menu);
		super.onCreateOptionsMenu(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_refresh) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
