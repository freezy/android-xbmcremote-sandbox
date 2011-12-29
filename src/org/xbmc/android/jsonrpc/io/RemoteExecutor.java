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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;
import org.xbmc.android.jsonrpc.io.JsonHandler.HandlerException;
import org.xbmc.android.util.HttpHelper;
import org.xmlpull.v1.XmlPullParser;

import android.content.ContentResolver;
import android.util.Log;

/**
 * Executes an {@link HttpUriRequest} and passes the HTTP body of the result as a String to the given {@link JsonHandler}.
 * <p>
 * This class was closely inspired by Google's official iosched app, see
 * http://code.google.com/p/iosched/
 * 
 * @author freezy <freezy@xbmc.org>
 */
public class RemoteExecutor {
	
	public static final String TAG = RemoteExecutor.class.getSimpleName();

	private final HttpClient mHttpClient;
	private final ContentResolver mResolver;

	public RemoteExecutor(ContentResolver resolver) {
		mHttpClient = HttpHelper.getHttpClient();
		mResolver = resolver;
	}

	/**
	 * Execute a {@link HttpPost} request, passing a valid response through
	 * {@link JsonHandler#parseAndApply(XmlPullParser, ContentResolver)}.
	 */
	public void executePost(String url, JSONObject entity, JsonHandler handler) throws HandlerException {
		final HttpPost request = new HttpPost(url);
		try {
			request.setEntity(new StringEntity(entity.toString(), "UTF-8"));
			request.addHeader("Content-Type", "application/json");
			Log.i(TAG, "POSTING: " + entity.toString());
		} catch (UnsupportedEncodingException e) {
			throw new HandlerException("Cannon encode JSON object to a String.", e);
		}
		execute(request, handler);
	}

	/**
	 * Execute this {@link HttpUriRequest}, passing a valid response through
	 * {@link JsonHandler#parseAndApply(XmlPullParser, ContentResolver)}.
	 */
	public void execute(HttpUriRequest request, JsonHandler handler) throws HandlerException {
		try {
			
			final HttpResponse resp = mHttpClient.execute(request);
			final int status = resp.getStatusLine().getStatusCode();
			if (status != HttpStatus.SC_OK) {
				throw new HandlerException("Unexpected server response " + resp.getStatusLine() + " for " + request.getRequestLine());
			}

			final InputStream input = resp.getEntity().getContent();
			try {
				
				final BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"), 8192);
				final StringBuilder sb = new StringBuilder();
				for (String line = null; (line = reader.readLine()) != null;) {
				    sb.append(line).append("\n");
				}
				//Log.d(TAG, "RESPONSE: " + sb.toString());
				handler.parseAndApply(sb, mResolver);
				
			} catch (HandlerException e) {
				throw e;
			} finally {
				if (input != null)
					input.close();
			}
			
		} catch (HandlerException e) {
			throw e;
		} catch (IOException e) {
			throw new HandlerException("Problem reading remote response for " + request.getRequestLine(), e);
		}
	}
}
