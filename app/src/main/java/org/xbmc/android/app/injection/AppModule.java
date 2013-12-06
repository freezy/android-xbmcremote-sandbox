package org.xbmc.android.app.injection;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;
import org.xbmc.android.jsonrpc.config.HostConfig;
import org.xbmc.android.jsonrpc.io.ConnectionManager;
import org.xbmc.android.jsonrpc.service.SyncService;
import org.xbmc.android.app.SandboxApplication;
import org.xbmc.android.app.ui.HomeActivity;
import org.xbmc.android.app.ui.fragment.AlbumCompactFragment;
import org.xbmc.android.app.ui.fragment.MovieCompactFragment;

import javax.inject.Singleton;

/**
 * Dagger module for setting up provides statements.
 * Register all of your entry points below.
 */
@Module(
	complete = false,
	injects = {
		SandboxApplication.class,
		HomeActivity.class,
		AlbumCompactFragment.class,
		MovieCompactFragment.class,
		SyncService.class
	}
)
public class AppModule {

	private static final String HOST = "192.168.0.100";
	public static final String URL = "http://" + HOST + ":8080/jsonrpc";

	@Singleton
	@Provides
	EventBus provideEventBus() {
		return EventBus.getDefault();
	}

	@Singleton
	@Provides
	ConnectionManager provideConnectionManager() {
		return new ConnectionManager(SandboxApplication.getInstance().getApplicationContext(), new HostConfig(HOST));
	}
}
