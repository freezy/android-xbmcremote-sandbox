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

import org.xbmc.android.remotesandbox.R;

import android.content.res.Resources;
import android.os.Bundle;

/**
 * Stuff that breaks while accessing the API
 * 
 * @author freezy <freezy@xbmc.org>
 * @author Joel Stemmer <stemmertech@gmail.com>
 */
public class ApiException extends Exception {
	
	public static final int MALFORMED_URL = 0x01;
	public static final int IO_EXCEPTION = 0x02;
	public static final int IO_EXCEPTION_WHILE_READING = 0x10;
	public static final int IO_EXCEPTION_WHILE_WRITING = 0x11;
	public static final int IO_EXCEPTION_WHILE_OPENING = 0x12;
	public static final int IO_SOCKETTIMEOUT = 0x03;
	public static final int IO_UNKNOWN_HOST = 0x04;
	public static final int IO_DISCONNECTED = 0x05;
	public static final int UNSUPPORTED_ENCODING = 0x06;
	public static final int JSON_EXCEPTION = 0x07;
	public static final int RESPONSE_ERROR = 0x08;
	public static final int API_ERROR = 0x09;
	
	public static final String EXTRA_ERROR_CODE = "org.xbmc.android.jsonprc.extra.ERROR_CODE";
	public static final String EXTRA_ERROR_MESSAGE = "org.xbmc.android.jsonprc.extra.ERROR_MESSAGE";
	public static final String EXTRA_ERROR_HINT = "org.xbmc.android.jsonprc.extra.ERROR_HINT";

	private int code;

	public ApiException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
	public static String getError(Resources r, int error) {
		switch (error) {
			case MALFORMED_URL:
				return r.getString(R.string.error_malformed_url);
			case IO_EXCEPTION:
				return r.getString(R.string.error_io_exception);
			case IO_EXCEPTION_WHILE_READING:
				return r.getString(R.string.error_io_exception_while_reading);
			case IO_EXCEPTION_WHILE_WRITING:
				return r.getString(R.string.error_io_exception_while_writing);
			case IO_EXCEPTION_WHILE_OPENING:
				return r.getString(R.string.error_io_exception_while_connecting);
			case IO_SOCKETTIMEOUT:
				return r.getString(R.string.error_connection_timeout);
			case IO_UNKNOWN_HOST:
				return r.getString(R.string.error_unknown_host);
			case UNSUPPORTED_ENCODING:
				return r.getString(R.string.error_encoding);
			case JSON_EXCEPTION:
				return r.getString(R.string.error_parse_json);
			case RESPONSE_ERROR:
				return r.getString(R.string.error_response);
			case API_ERROR:
				return r.getString(R.string.error_api);				
		}
		return null;
	}
	
	public static String getHint(Resources r, int error) {
		switch (error) {
		case MALFORMED_URL:
			return r.getString(R.string.error_malformed_url_hint);
		case IO_EXCEPTION:
			return r.getString(R.string.error_io_exception_hint);
		case IO_EXCEPTION_WHILE_READING:
			return r.getString(R.string.error_io_exception_while_reading_hint);
		case IO_EXCEPTION_WHILE_WRITING:
			return r.getString(R.string.error_io_exception_while_writing_hint);
		case IO_EXCEPTION_WHILE_OPENING:
			return r.getString(R.string.error_io_exception_while_connecting_hint);
		case IO_SOCKETTIMEOUT:
			return r.getString(R.string.error_connection_timeout_hint);
		case IO_UNKNOWN_HOST:
			return r.getString(R.string.error_unknown_host_hint);
		case UNSUPPORTED_ENCODING:
			return r.getString(R.string.error_encoding_hint);
		case JSON_EXCEPTION:
			return r.getString(R.string.error_parse_json_hint);
		case RESPONSE_ERROR:
			return r.getString(R.string.error_response_hint);
		case API_ERROR:
			return r.getString(R.string.error_api_hint);	
		}
		return null;
	}
	
	public Bundle getBundle() {
		final Bundle b = new Bundle();
		b.putInt(EXTRA_ERROR_CODE, code);
		b.putString(EXTRA_ERROR_MESSAGE, getMessage());
		return b;
	}
	
	public Bundle getBundle(Resources r) {
		final Bundle b = new Bundle();
		b.putInt(EXTRA_ERROR_CODE, code);
		b.putString(EXTRA_ERROR_MESSAGE, getError(r, code));
		b.putString(EXTRA_ERROR_HINT, getHint(r, code));
		return b;
	}
	
	private static final long serialVersionUID = -8668163106123710291L;
}
