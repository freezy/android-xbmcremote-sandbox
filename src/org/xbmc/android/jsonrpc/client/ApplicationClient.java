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

import org.xbmc.android.jsonrpc.api.call.Application;
import org.xbmc.android.jsonrpc.api.model.ApplicationModel;
import org.xbmc.android.zeroconf.XBMCHost;

/**
 * Access to the Application API.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class ApplicationClient extends AbstractClient {

	//private final static String TAG = ApplicationClient.class.getSimpleName();

	/**
	 * Sometimes we don't want the standard host to be used, but another one,
	 * for example when we're adding a new account and probing for version.
	 * @param host
	 */
	public ApplicationClient(XBMCHost host) {
		super(host);
	}

	/**
	 * Returns the version of XBMC.
	 *
	 * @param errorHandler Error handler
	 * @return Version object containing XBMC's version.
	 */
	public ApplicationModel.PropertyValue.Version getVersion(ErrorHandler errorHandler) {
		final Application.GetProperties apicall = new Application.GetProperties(ApplicationModel.PropertyName.VERSION);
		execute(apicall, errorHandler);
		return apicall.getResult().version;
	}
}
