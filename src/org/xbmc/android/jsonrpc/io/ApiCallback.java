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

import org.xbmc.android.jsonrpc.api.AbstractCall;

/**
 * Implementing classes are called with the result of an API call.
 * <p>
 * Use the {@link ConnectionManager} to initiate API calls by running the 
 * {@link ConnectionManager#call(AbstractCall, ApiCallback)} method.
 *
 * @author freezy <freezy@xbmc.org>
 */
public interface ApiCallback<T> {
	
	/**
	 * A successful response has been returned by JSON-RPC and was serialized 
	 * into our model.
	 * <p>
	 * Use {@link AbstractCall#getResult()} or {@link AbstractCall#getResults()}
	 * in order to obtain the result of the API call.
	 * 
	 * @param apiCall Original API call containing the response.
	 */
	public abstract void onResponse(AbstractCall<T> apiCall);
	
	/**
	 * An error has occurred.
	 * 
	 * @param message Translated error message
	 * @param message Translated error hint
	 */
	public abstract void onError(int code, String message, String hint);
}
