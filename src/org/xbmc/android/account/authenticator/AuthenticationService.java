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

package org.xbmc.android.account.authenticator;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Service to handle Account authentication. It instantiates the authenticator
 * and returns its IBinder.
 * 
 * @author freezy <freezy@xbmc.org>
 */
public class AuthenticationService extends Service {
	
	private static final String TAG = AuthenticationService.class.getSimpleName();
	
	private Authenticator mAuthenticator;

	@Override
	public void onCreate() {
		if (Log.isLoggable(TAG, Log.VERBOSE)) {
			Log.v(TAG, "XBMC Authentication Service started.");
		}
		mAuthenticator = new Authenticator(this);
	}

	@Override
	public void onDestroy() {
		if (Log.isLoggable(TAG, Log.VERBOSE)) {
			Log.v(TAG, "XBMC Authentication Service stopped.");
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		if (Log.isLoggable(TAG, Log.VERBOSE)) {
			Log.v(TAG, "getBinder()...  returning the AccountAuthenticator binder for intent " + intent);
		}
		IBinder ret = null;
		if (intent.getAction().equals(android.accounts.AccountManager.ACTION_AUTHENTICATOR_INTENT)) {
			ret = mAuthenticator.getIBinder();
		}
		return ret;
	}

}
