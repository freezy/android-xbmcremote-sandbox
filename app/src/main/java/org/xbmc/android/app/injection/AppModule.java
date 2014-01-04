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

package org.xbmc.android.app.injection;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;
import org.xbmc.android.account.authenticator.ui.Step2aSearchingFragment;
import org.xbmc.android.account.authenticator.ui.Step3aHostFoundFragment;
import org.xbmc.android.account.authenticator.ui.WizardActivity;
import org.xbmc.android.app.SandboxApplication;
import org.xbmc.android.app.manager.HostManager;
import org.xbmc.android.app.service.SyncService;
import org.xbmc.android.app.ui.HomeActivity;
import org.xbmc.android.app.ui.HostChooseActivity;
import org.xbmc.android.app.ui.fragment.AlbumCompactFragment;
import org.xbmc.android.app.ui.fragment.MovieCompactFragment;
import org.xbmc.android.app.ui.menu.SlidingMenuFragment;
import org.xbmc.android.jsonrpc.client.AbstractClient;
import org.xbmc.android.jsonrpc.io.ConnectionManager;
import org.xbmc.android.zeroconf.DiscoveryService;

import javax.inject.Singleton;

/**
 * Dagger module for setting up provides statements.
 * Register all of your entry points below.
 */
@Module(
	complete = false,
	injects = {
		AlbumCompactFragment.class,
		DiscoveryService.class,
		HomeActivity.class,
		HostChooseActivity.class,
		HostManager.class,
		AbstractClient.class,
		MovieCompactFragment.class,
		SandboxApplication.class,
		SlidingMenuFragment.class,
		Step2aSearchingFragment.class,
		Step3aHostFoundFragment.class,
		SyncService.class,
		WizardActivity.class
	}
)
public class AppModule {

	@Singleton
	@Provides
	EventBus provideEventBus() {
		return EventBus.getDefault();
	}

	@Singleton
	@Provides
	ConnectionManager provideConnectionManager() {
		return new ConnectionManager(SandboxApplication.getInstance().getApplicationContext(), null);
	}

	@Singleton
	@Provides
	HostManager provideHostManager() {
		return new HostManager();
	}
}
