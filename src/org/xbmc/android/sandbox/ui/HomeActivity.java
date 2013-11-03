package org.xbmc.android.sandbox.ui;

import android.os.Bundle;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.sandbox.ui.sync.AbstractSyncBridge;
import org.xbmc.android.sandbox.ui.sync.AudioSyncBridge;

/**
 * The landing page of the app.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class HomeActivity extends RefreshableActivity {

	/**
	 * Sync bridge for global refresh.
	 */
	private AudioSyncBridge mSyncBridge;

	public HomeActivity() {
		super(R.string.app_name);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		// only slide menu, not the action bar.
		setSlidingActionBarEnabled(false);
	}

	@Override
	protected AbstractSyncBridge[] initSyncBridges() {
		mSyncBridge = new AudioSyncBridge(mRefreshObservers);
		return new AbstractSyncBridge[]{ mSyncBridge };
	}

	@Override
	protected AbstractSyncBridge getSyncBridge() {
		return mSyncBridge;
	}

}
