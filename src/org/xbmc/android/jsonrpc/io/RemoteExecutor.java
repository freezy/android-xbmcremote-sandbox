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

package org.xbmc.android.jsonrpc.io;

import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONObject;

import android.content.ContentResolver;

/**
 * Executes an {@link HttpUriRequest} and passes the HTTP body of the result as a String to the given {@link JsonHandler}.
 * <p>
 * This class was closely inspired by Google's official iosched app, see
 * http://code.google.com/p/iosched/
 *
 * @author freezy <freezy@xbmc.org>
 */
public class RemoteExecutor {

	private static final String TAG = RemoteExecutor.class.getSimpleName();

	private final ContentResolver mResolver;

	public RemoteExecutor(ContentResolver resolver) {
		mResolver = resolver;
	}

	/**
	 * Execute api request, passing a valid response through
	 * {@link JsonHandler#applyResult(JSONObject, ContentResolver)}.
	 */
	public void execute(String url, JSONObject entity, JsonHandler handler) throws ApiException {
		JSONObject result = JsonApiRequest.execute(url, entity);
		handler.applyResult(result, mResolver);
	}
}
