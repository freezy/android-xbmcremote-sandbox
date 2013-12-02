package org.xbmc.android.sandbox;

import android.app.Application;
import com.squareup.otto.Bus;

/**
 */
public class SandboxApplication extends Application {

	private Bus mBus;

	@Override
	public void onCreate() {
		super.onCreate();
		mBus = new Bus();
	}

	public Bus getBus() {
		return mBus;
	}
}
