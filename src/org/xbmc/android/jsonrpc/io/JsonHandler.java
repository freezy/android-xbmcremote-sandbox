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
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.xbmc.android.jsonrpc.provider.AudioContract.Albums;
import org.xbmc.android.jsonrpc.provider.AudioContract.Artists;
import org.xmlpull.v1.XmlPullParser;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.os.RemoteException;
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
    public void parseAndApply(StringBuilder response, ContentResolver resolver) throws HandlerException {
        try {
        	final JSONTokener tokener = new JSONTokener(response.toString());
			final JSONObject responseObj = (JSONObject)tokener.nextValue();
			if (responseObj.has("error")) {
				final JSONObject error = responseObj.getJSONObject("error");
				Log.e(TAG, "[JSON-RPC] " + error.getString("message"));
				Log.e(TAG, "[JSON-RPC] " + response);
				throw new HandlerException("Error " + error.getInt("code") + ": " + error.getString("message"));
			}
			if (!responseObj.has("result")) {
				Log.e(TAG, "[JSON-RPC] " + response);
				throw new HandlerException("Neither result nor error object found in response.");
			}
			final JSONObject result = responseObj.getJSONObject("result");
        	
            //final ArrayList<ContentProviderOperation> batch = parse(result, resolver);
            final long start = System.currentTimeMillis();
            //Log.i(TAG, "Starting to execute " + batch.size() + " batches..");
            //resolver.applyBatch(mAuthority, batch);
            //currently apply batch which deletes all albums and then adds again. can improve this using bulk insert.
            final ContentValues [] newBatch = newParse(result, resolver);
            Log.i(TAG, "Starting to execute " + newBatch.length + " batches..");
            //resolver.delete(Artists.CONTENT_URI, null,null);
            if(result.has("artists")){
        		Log.d(TAG, "@@@@@@@@@@artists");
        		resolver.bulkInsert(Artists.CONTENT_URI, newBatch);
        	}else if(result.has("albums")){
        		Log.d(TAG, "@@@@@@@@@@albums");
        		resolver.bulkInsert(Albums.CONTENT_URI, newBatch);
        	}
            
            //resolver.notifyChange(Artists.CONTENT_URI, observer);
            Log.i(TAG, "Execution done in " + (System.currentTimeMillis() - start) + "ms.");

        } catch (HandlerException e) {
            throw e;
        } catch (JSONException e) {
            throw new HandlerException("Problem parsing JSON response", e);
        } catch (IOException e) {
            throw new HandlerException("Problem reading response", e);
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
    public abstract ArrayList<ContentProviderOperation> parse(JSONObject result, ContentResolver resolver) 
    		throws JSONException, IOException; 

    public abstract ContentValues[] newParse(JSONObject result, ContentResolver resolver)throws JSONException, IOException; 
    /**
     * General {@link IOException} that indicates a problem occured while
     * parsing or applying an {@link XmlPullParser}.
     */
    public static class HandlerException extends IOException {
    	
		public HandlerException(String message) {
            super(message);
        }

        public HandlerException(String message, Throwable cause) {
            super(message);
            initCause(cause);
        }

        @Override
        public String toString() {
            if (getCause() != null) {
                return getLocalizedMessage() + ": " + getCause();
            } else {
                return getLocalizedMessage();
            }
        }
        private static final long serialVersionUID = 9183136825983873790L;
    }
}
