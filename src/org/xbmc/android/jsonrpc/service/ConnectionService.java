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

package org.xbmc.android.jsonrpc.service;

/**
 * Service which keeps a steady TCP connection to XBMC's JSON-RPC API via
 * TCP socket (as opposed to HTTP messages).
 * <p>
 * It serves as listener for notification, but is also used for posting normal
 * API requests.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class ConnectionService {

	private static final int SOCKET_TIMEOUT = 5000;

	public static final String EXTRA_STATUS_RECEIVER = "org.xbmc.android.jsonprc.extra.STATUS_RECEIVER";
	public static final String EXTRA_JSON_DATA = "org.xbmc.android.jsonprc.extra.JSON_DATA";
	public static final String EXTRA_ERROR_CODE = "org.xbmc.android.jsonprc.extra.ERROR_CODE";
	public static final String EXTRA_ERROR_MESSAGE = "org.xbmc.android.jsonprc.extra.ERROR_MESSAGE";

	public static final int MSG_REGISTER_CLIENT = 1;
	public static final int MSG_UNREGISTER_CLIENT = 2;
	public static final int MSG_RECEIVE_JSON_DATA = 3;	
	public static final int MSG_SEND_JSON_DATA = 4;
	public static final int MSG_ERROR = 5;	
	
}
