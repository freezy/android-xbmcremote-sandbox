package org.xbmc.android.app;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import org.xbmc.android.injection.Injector;
import org.xbmc.android.injection.RootModule;

/**
 */
public class SandboxApplication extends Application {

	private static SandboxApplication instance;

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
		instance = this;
		// Perform injection
		Injector.init(getRootModule(), this);
	}

	private Object getRootModule() {
		return new RootModule();
	}

	public static SandboxApplication getInstance() {
		return instance;
	}
}
