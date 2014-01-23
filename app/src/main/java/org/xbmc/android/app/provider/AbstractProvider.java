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

package org.xbmc.android.app.provider;

import android.content.ContentProvider;
import android.content.Context;
import org.xbmc.android.app.injection.Injector;
import org.xbmc.android.app.manager.HostManager;
import org.xbmc.android.zeroconf.XBMCHost;

import javax.inject.Inject;

/**
 * Common class for all providers.
 *
 * @author freezy <freezy@xbmc.org>
 */
public abstract class AbstractProvider extends ContentProvider {

	@Inject HostManager hostManager;

	@Override
	public boolean onCreate() {
		final Context context = getContext();
		Injector.setContext(context);
		Injector.injectSafely(this);
		return true;
	}

	protected String getHostIdAsString() {
		final XBMCHost host = hostManager.getActiveHost();
		return host == null ? "-1" : String.valueOf(host.getId());
	}

	protected long getHostId() {
		final XBMCHost host = hostManager.getActiveHost();
		return host == null ? -1L : host.getId();
	}
}