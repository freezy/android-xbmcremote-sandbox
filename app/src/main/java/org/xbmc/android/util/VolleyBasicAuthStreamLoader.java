/*
 * Copyright (C) 2005-2014 Team XBMC
 *     http://xbmc.org
 *
 * This Program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This Program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with XBMC Remote; see the file license.  If not, write to
 * the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.
 * http://www.gnu.org/copyleft/gpl.html
 */

package org.xbmc.android.util;

import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.bumptech.glide.loader.stream.StreamLoader;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Custom StreamLoader using Volley for fetching images via http, with basic authentication
 *
 * This is basically a copy of VolleyStreamLoader present in the Glide library, which couldn't be
 * extended because it has private members, which we'll need to access
 *
 * @author Synced <synced.synapse@gmail.com>
 */
public class VolleyBasicAuthStreamLoader implements StreamLoader {
	private final RequestQueue requestQueue;
	private final URL url;
	private final RetryPolicy retryPolicy;
	private Request current = null;

	@SuppressWarnings("unused")
	public VolleyBasicAuthStreamLoader(RequestQueue requestQueue, URL url) {
		this(requestQueue, url, new DefaultRetryPolicy());
	}

	public VolleyBasicAuthStreamLoader(RequestQueue requestQueue, URL url, RetryPolicy retryPolicy) {
		this.requestQueue = requestQueue;
		this.url = url;
		this.retryPolicy = retryPolicy;
	}

	@Override
	public void loadStream(final StreamReadyCallback cb) {
		Request<Void> request = new BasicAuthRequest(url, cb);
		request.setRetryPolicy(retryPolicy);
		current = requestQueue.add(request);
	}

	@Override
	public void cancel() {
		final Request local = current;
		if (local != null) {
			local.cancel();
			current = null;
		}
	}

	private static class BasicAuthRequest extends Request<Void> {
		private final StreamReadyCallback cb;
		private final URL url;

		public BasicAuthRequest(URL url, final StreamReadyCallback cb) {
			super(Method.GET, url.toString(), new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					cb.onException(error);
				}
			});
			this.cb = cb;
			this.url = url;
		}

		@Override
		protected Response<Void> parseNetworkResponse(NetworkResponse response) {
			cb.onStreamReady(new ByteArrayInputStream(response.data));
			return Response.success(null, getCacheEntry());
		}

		@Override
		protected void deliverResponse(Void response) { }

		@Override
		public Map<String, String> getHeaders() throws AuthFailureError {
			Map<String, String> params = new HashMap<String, String>();
			String creds = url.getUserInfo();
			if ((creds != null) && !creds.isEmpty()) {
				String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
				params.put("Authorization", auth);
			}
			return params;
		}
	}
}