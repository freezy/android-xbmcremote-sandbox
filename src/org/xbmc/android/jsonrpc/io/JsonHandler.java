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

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.xbmc.android.jsonrpc.provider.AudioContract.Albums;
import org.xbmc.android.jsonrpc.provider.AudioContract.Artists;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.util.Log;

/**
 * Abstract class that handles reading and parsing an JSON-serialized API
 * response into a set of {@link ContentProviderOperation}. It catches
 * recoverable network exceptions and re-throws them as {@link HandlerException}. 
 * Any local {@link ContentProvider} exceptions are considered unrecoverable.
 * <p>
 * This class is only designed to handle simple one-way synchronization.
 * <p>
 * This class was closely inspired by Google's official iosched app, see
 * http://code.google.com/p/iosched/
 * 
 * @author freezy <freezy@xbmc.org>
 */
public abstract class JsonHandler {

	private static final String TAG = JsonHandler.class.getSimpleName();

	private final String mAuthority;

	public JsonHandler(String authority) {
		mAuthority = authority;
	}

	/**
	 * Parse the given HTTP response body, turning into a series of
	 * {@link ContentProviderOperation} that are immediately applied using the
	 * given {@link ContentResolver}.
	 * 
	 * @param response HTTP response body
	 * @param resolver Content resolver
	 * @throws HandlerException Re-thrown errors
	 */
	public void applyResult(JSONObject result, ContentResolver resolver) throws ApiException {
		try {
			final long start = System.currentTimeMillis();
			
			if (result == null) {
				// TODO handle empty response correctly.
				Log.w(TAG, "Empty response. DEFINE what todo, ignoring now.");
				
			} else {
				
				final ContentValues[] newBatch = parse(result, resolver);
				Log.i(TAG, "Starting to execute " + newBatch.length + " batches..");
				
				// need to move this to the subclass, we're abstract in here!
				if (result.has("artists")) {
					resolver.bulkInsert(Artists.CONTENT_URI, newBatch);
					
				} else if (result.has("albums")) {
					resolver.bulkInsert(Albums.CONTENT_URI, newBatch);
					
				}
				Log.i(TAG, "Execution done in " + (System.currentTimeMillis() - start) + "ms.");
			}
		} catch (JSONException e) {
			throw new ApiException(ApiException.JSON_EXCEPTION, "Problem reading json", e);
        } catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Parse the HTTP body's de-serialized {@link JSONObject}, returning a set
	 * of {@link ContentProviderOperation} that will bring the
	 * {@link ContentProvider} into sync with the parsed data.
	 * 
	 * @param result HTTP body de-serialized
	 * @param resolver Content resolver
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
    
    public abstract ContentValues[] parse(JSONObject result, ContentResolver resolver)throws JSONException, IOException; 
}
