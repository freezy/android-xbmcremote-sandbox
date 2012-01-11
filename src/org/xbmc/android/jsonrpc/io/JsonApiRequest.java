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
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import android.util.Log;

/**
 * Performs HTTP POST requests on the XBMC JSON API and handles the parsing from and to {@link JSONObject}.
 *
 * @author Joel Stemmer <stemmertech@gmail.com>
 */
public class JsonApiRequest {

	private static final String TAG = JsonApiRequest.class.getSimpleName();

	private static final int REQUEST_TIMEOUT = 5000; // 5 sec

	/**
	 * Execute a POST request to the url using the JSONObject as request body and returns a JSONObject if the response
	 * was successful.
	 *
	 * @param url
	 * @param entity
	 * @return JSONObject of the JSON-RPC result.
	 * @throws HandlerException
	 */
	public static JSONObject execute(String url, JSONObject entity) throws ApiException {
		try {
			String response = postRequest(new URL(url), entity.toString());
			return parseResponse(response);
		} catch(MalformedURLException e) {
			throw new ApiException(ApiException.MALFORMED_URL, e.getMessage(), e);
		}
	}

	/**
	 * Execute a POST request on URL using entity as request body.
	 *
	 * @param url
	 * @param entity
	 * @return The response as a string
	 * @throws HandlerException
	 * @throws IOException
	 */
	private static String postRequest(URL url, String entity) throws ApiException {
		try {
			final HttpURLConnection conn = (HttpURLConnection)url.openConnection();

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("User-Agent", buildUserAgent());

			conn.setConnectTimeout(REQUEST_TIMEOUT);
			conn.setReadTimeout(REQUEST_TIMEOUT);

			conn.setDoOutput(true);

			try {
				OutputStreamWriter output = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
				output.write(entity);
				output.close();
			} catch(UnsupportedEncodingException e) {
				throw new ApiException(ApiException.UNSUPPORTED_ENCODING, "Unable to convert request to UTF-8", e);
			}

			Log.i(TAG, "POST request: " + conn.getURL());
			Log.i(TAG, "POST entity:" + entity);

			StringBuilder response = new StringBuilder();
			BufferedReader reader = null;

			try {
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"), 8192);
				String line;
				while((line = reader.readLine()) != null) {
					response.append(line);
				}
			} catch(UnsupportedEncodingException e) {
				throw new ApiException(ApiException.UNSUPPORTED_ENCODING, "Unable to convert HTTP response to UTF-8", e);
			} finally {
				if (reader != null) {
					reader.close();
				}
			}

			Log.i(TAG, "POST response: " + response.toString());
			return response.toString();
		} catch (SocketTimeoutException e) {
			throw new ApiException(ApiException.IO_SOCKETTIMEOUT, e.getMessage(), e);
		} catch (IOException e) {
			throw new ApiException(ApiException.IO_EXCEPTION, e.getMessage(), e);
		}
	}

	/**
	 * Parses the JSON response string and returns a {@link JSONObject}.
	 *
	 * If the response is not valid JSON, contained an error message or did not include a result then a HandlerException
	 * is thrown.
	 *
	 * @param response
	 * @return JSONObject of the result contained in the response.
	 * @throws HandlerException
	 */
	private static JSONObject parseResponse(String response) throws ApiException {
		try {
			final JSONTokener tokener = new JSONTokener(response.toString());
			final JSONObject obj = (JSONObject)tokener.nextValue();

			if (obj.has("error")) {
				final JSONObject error = obj.getJSONObject("error");
				Log.e(TAG, "[JSON-RPC] " + error.getString("message"));
				Log.e(TAG, "[JSON-RPC] " + response);
				throw new ApiException(ApiException.API_ERROR, "Error " + error.getInt("code") + ": " + error.getString("message"), null);
			}

			if (!obj.has("result")) {
				Log.e(TAG, "[JSON-RPC] " + response);
				throw new ApiException(ApiException.RESPONSE_ERROR, "Neither result nor error object found in response.", null);
			}
			
			if (obj.isNull("result")) {
				return null;
			}

			return obj.getJSONObject("result");
		} catch(JSONException e) {
			throw new ApiException(ApiException.JSON_EXCEPTION, "Parse error: " + e.getMessage(), e);
		}
	}

	/**
	 * Build user agent used for the HTTP requests
	 * TODO: include version information
	 *
	 * @return String containing the user agent
	 */
	private static String buildUserAgent() {
		return "XBMCRemote (1.0)";
	}
}
