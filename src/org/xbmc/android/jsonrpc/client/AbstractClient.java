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

import org.json.JSONObject;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.io.ApiException;
import org.xbmc.android.jsonrpc.io.JsonApiRequest;
import org.xbmc.android.jsonrpc.service.AudioSyncService;
import org.xbmc.android.zeroconf.XBMCHost;

/**
 * Parent class for all "standalone" clients. Contains network logic
 * inclusively error handling.
 *
 * @author freezy <freezy@xbmc.org>
 * @author Joel Stemmer <stemmertech@gmail.com>
 */
public abstract class AbstractClient {

//	private final static String TAG = AbstractClient.class.getSimpleName();

	private final static String URL_SUFFIX = "/jsonrpc";

	private final XBMCHost mHost;

	/**
	 * Empty constructor
	 */
	protected AbstractClient() {
		mHost = null;
	}

	/**
	 * Sometimes we don't want the standard host to be used, but another one,
	 * for example when we're adding a new account and probing for version.
	 * @param host
	 */
	protected AbstractClient(XBMCHost host) {
		mHost = host;
	}
	

	/**
	 * Synchronously posts the request object to XBMC's JSONRPC API and updates
	 * the API call object with the received <tt>result</tt> node of the 
	 * response object.
	 * 
	 * @param api API call object.
	 * @param errorHandler Error handler in case something goes wrong
	 */
	protected void execute(AbstractCall<?> api, ErrorHandler errorHandler) {

		// 1. get the request object from our API implementation
		final JSONObject request = api.getRequest();

		// 2. POST the object to XBMC's JSON-RPC API
		JSONObject response = null;
		try {
			response = JsonApiRequest.execute(getUrl(), request);
		} catch (ApiException e) {
			handleError(errorHandler, e);
		}

		// 3. parse the result and unserialize the JSON object into real {@link Source} objects.
		if (response != null) {
			api.setResponse(response);
		}
	}

	/**
	 * Returns the URL of XBMC to connect to.
	 * 
	 * The URL already contains the JSON-RPC prefix, e.g.:
	 * 		<code>http://192.168.0.100:8080/jsonrpc</code>
	 * <p/>
	 * If the client was instantiated with an explicit host, it trumps the current
	 * host settings.
	 * @return
	 */
	private String getUrl() {
		if (mHost == null) {
			// FIXME this should read the URL from the currently selected account.
			return AudioSyncService.URL;
		} else {
			return "http://" + mHost.getAddress() + ":" + mHost.getPort() + URL_SUFFIX;
		}
	}

	/**
	 * Defines error codes and an action that is executed on error. Is generally
	 * executed on the UI thread.
	 */
	public interface ErrorHandler {
		/**
		 * Implement your error logic here.
		 * @param code Error code as defined above
		 * @param message Error message in english. For translations, refer to the error code.
		 */
		void handleError(ApiException e);
	}

	/**
	 * Handles errors, even if the callback is null.
	 * @param handler Error handler which can be null.
	 * @param code
	 * @param message
	 */
	protected void handleError(ErrorHandler handler, ApiException e) {
		if (handler != null) {
			handler.handleError(e);
		}
	}
}
