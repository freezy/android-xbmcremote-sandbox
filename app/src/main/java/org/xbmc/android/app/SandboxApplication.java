package org.xbmc.android.app;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import org.xbmc.android.app.injection.Injector;
import org.xbmc.android.app.injection.RootModule;

/**
 */
public class SandboxApplication extends Application {

	public SandboxApplication() {
	}

	/**
	 * Create main application
	 *
	 * @param context
	 */
	public SandboxApplication(final Context context) {
		this();
		attachBaseContext(context);
	}

	/**
	 * Create main application
	 *
	 * @param instrumentation
	 */
	public SandboxApplication(final Instrumentation instrumentation) {
		this();
		attachBaseContext(instrumentation.getTargetContext());
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// Perform injection
		Injector.setContext(getApplicationContext());
		Injector.init(getRootModule(), this);
	}

	private Object getRootModule() {
		return new RootModule();
	}
}
