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

/**
 * Stuff that breaks while accessing the API
 * 
 * @author freezy <freezy@xbmc.org>
 * @author Joel Stemmer <stemmertech@gmail.com>
 */
public class ApiException extends Exception {
	
	public static int MALFORMED_URL = 1000;
	public static int IO_EXCEPTION = 1001;
	public static int IO_SOCKETTIMEOUT = 1002;
	public static int UNSUPPORTED_ENCODING = 1003;
	public static int JSON_EXCEPTION = 1004;
	public static int RESPONSE_ERROR = 1005;
	public static int API_ERROR = 1006;

	private int code;

	public ApiException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
	private static final long serialVersionUID = -8668163106123710291L;
}
