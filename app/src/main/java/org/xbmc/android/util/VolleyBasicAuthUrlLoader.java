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

import android.content.Context;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.loader.model.GenericLoaderFactory;
import com.bumptech.glide.loader.model.ModelLoader;
import com.bumptech.glide.loader.model.ModelLoaderFactory;
import com.bumptech.glide.loader.stream.StreamLoader;

import java.net.URL;


/**
 * Custom ModelLoader using Volley with basic authentication
 *
 * This is basically a copy of VolleyUrlLoader present in the Glide library, which couldn't be
 * extended because it has the requestQueue as a private member, and we'll need access to it to
 * pass it to the StreamLoader.
 *
 * This just serves to create the VolleyBasicAuthStreamLoader where the basic authentication
 * is implemented.
 *
 * @author Synced <synced.synapse@gmail.com>
 */
public class VolleyBasicAuthUrlLoader implements ModelLoader<URL> {
	public static class Factory implements ModelLoaderFactory<URL> {
		private RequestQueue requestQueue;

		public Factory() { }

		public Factory(RequestQueue requestQueue) {
			this.requestQueue = requestQueue;
		}

		protected RequestQueue getRequestQueue(Context context) {
			if (requestQueue == null) {
				requestQueue = Volley.newRequestQueue(context);
			}
			return requestQueue;
		}

		@Override
		public ModelLoader<URL> build(Context context, GenericLoaderFactory factories) {
			return new VolleyBasicAuthUrlLoader(getRequestQueue(context));
		}

		@Override
		public Class<? extends ModelLoader<URL>> loaderClass() {
			return VolleyBasicAuthUrlLoader.class;
		}

		@Override
		public void teardown() {
			if (requestQueue != null) {
				requestQueue.stop();
				requestQueue.cancelAll(new RequestQueue.RequestFilter() {
					@Override
					public boolean apply(Request<?> request) {
						return true;
					}
				});
				requestQueue = null;
			}
		}
	}

	private final RequestQueue requestQueue;

	public VolleyBasicAuthUrlLoader(RequestQueue requestQueue) {
		this.requestQueue = requestQueue;
	}

	@Override
	public StreamLoader getStreamLoader(URL url, int width, int height) {
		return new VolleyBasicAuthStreamLoader(requestQueue, url, getRetryPolicy());
	}

	@Override
	public String getId(URL url) {
		return url.toString();
	}

	protected RetryPolicy getRetryPolicy() {
		return new DefaultRetryPolicy();
	}
}