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

package org.xbmc.android.account.authenticator;

import org.xbmc.android.jsonrpc.api.model.ApplicationModel.PropertyValue.Version;
import org.xbmc.android.jsonrpc.client.ApplicationClient;
import org.xbmc.android.jsonrpc.client.JsonRpcClient;
import org.xbmc.android.zeroconf.XBMCHost;

import android.content.Context;
import android.os.Handler;

/**
 * Provides helper threads for the add account procedure.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public class NetworkUtilities {
	
	/**
	 * Attempts to probe XBMC for API version and XBMC version.
	 * 
	 * @param host Host to connect to
	 * @param handler The main UI thread's handler instance.
	 * @param context The caller Activity's context
	 * @return Thread The thread on which the network mOperations are executed.
	 */
	public static Thread attemptProbe(final XBMCHost host, final Handler handler, final Context context) {
		final Runnable runnable = new Runnable() {
			public void run() {
				probe(host, handler, context);
			}
		};
		// run on background thread.
		return NetworkUtilities.performOnBackgroundThread(runnable);
	}
	
	/**
	 * Probes XBMC for API version and XBMC version.
	 * 
	 * @param host Host to connect to
	 * @param handler The main UI thread's handler instance.
	 * @param context The caller Activity's context
	 */
	private static void probe(XBMCHost host, Handler handler, final Context context) {
		final JsonRpcClient jsonClient = new JsonRpcClient(host);
		final ApplicationClient appClient = new ApplicationClient(host);
		final int apiVersion = jsonClient.getVersion(null);
		final Version xbmcVersion = appClient.getVersion(null);
		
		if (handler == null || context == null) {
			return;
		}
		
		handler.post(new Runnable() {
			public void run() {
				((AuthenticatorActivity) context).onProbeResult(apiVersion, xbmcVersion);
			}
		});
	}

	/**
	 * Executes the network requests on a separate thread.
	 * 
	 * @param runnable The runnable instance containing network operations to beexecuted.
	 */
	public static Thread performOnBackgroundThread(final Runnable runnable) {
		final Thread t = new Thread() {
			@Override
			public void run() {
				try {
					runnable.run();
				} finally {

				}
			}
		};
		t.start();
		return t;
	}

}
