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

package org.xbmc.android.account;

/**
 * Account-related constants.
 * 
 * This will probably move somewhere else more central eventually.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public class Constants {

	/**
	 * Minumal API level of XBMC's JSON-RPC API.
	 */
	public static final int MIN_JSONRPC_API = 3;
	
	/**
	 * Account type string.
	 */
	public static final String ACCOUNT_TYPE = "org.xbmc.android.remote";

	/**
	 * Authtoken type string.
	 */
	public static final String AUTHTOKEN_TYPE = "org.xbmc.android.remote";
	
	public static final String DATA_ADDRESS = "org.xbmc.android.account.ADDRESS";
	public static final String DATA_PORT = "org.xbmc.android.account.PORT";
	

}
