package org.xbmc.android.injection;

import com.squareup.otto.Bus;
import dagger.Module;
import dagger.Provides;
import org.xbmc.android.jsonrpc.service.SyncService;
import org.xbmc.android.sandbox.SandboxApplication;
import org.xbmc.android.sandbox.ui.HomeActivity;

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
		SyncService.class
	}
)
public class AppModule {

	@Singleton
	@Provides
	Bus provideOttoBus() {
		return new Bus();
	}

}
