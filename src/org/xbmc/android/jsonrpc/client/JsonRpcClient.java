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

package org.xbmc.android.jsonrpc.client;

import org.json.JSONException;
import org.xbmc.android.jsonrpc.api.call.JSONRPC;
import org.xbmc.android.jsonrpc.io.ApiException;
import org.xbmc.android.zeroconf.XBMCHost;

import android.util.Log;

/**
 * Real time access to the JSONRPC API.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public class JsonRpcClient extends AbstractClient {
	
	private final static String TAG = JsonRpcClient.class.getSimpleName();
	
	/**
	 * Sometimes we don't want the standard host to be used, but another one,
	 * for example when we're adding a new account and probing for version.
	 * @param host
	 */
	public JsonRpcClient(XBMCHost host) {
		super(host);
	}
	
	/**
	 * Returns the API version of XBMC's JSON-RPC implementation.
	 *
	 * @param errorHandler Error handler
	 * @return API version or -1 on error.
	 */
	public int getVersion(ErrorHandler errorHandler) {
		try {
			final JSONRPC.Version apicall = new JSONRPC.Version();
			execute(apicall, errorHandler);
			return Integer.parseInt(apicall.getResult());
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
			errorHandler.handleError(new ApiException(ApiException.JSON_EXCEPTION, e.getMessage(), e));
		}
		return -1;
	}
	
}
