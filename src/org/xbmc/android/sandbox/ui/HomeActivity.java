package org.xbmc.android.sandbox.ui;

import android.os.Bundle;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.sandbox.ui.sync.AbstractSyncBridge;
import org.xbmc.android.sandbox.ui.sync.SyncBridge;

/**
 * The landing page of the app.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class HomeActivity extends RefreshableActivity {

	private static final String TAG = HomeActivity.class.getSimpleName();
	/**
	 * Sync bridge for global refresh.
	 */
	private SyncBridge mSyncBridge;

	public HomeActivity() {
		super(R.string.title_home, R.layout.activity_home);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// only slide menu, not the action bar.
		setSlidingActionBarEnabled(false);
	}

	@Override
	protected AbstractSyncBridge[] initSyncBridges() {
		mSyncBridge = new SyncBridge(mRefreshObservers, SyncBridge.SECTION_MUSIC, SyncBridge.SECTION_MOVIES);
		//mSyncBridge = new SyncBridge(mRefreshObservers, SyncBridge.SECTION_MOVIES);
		return new AbstractSyncBridge[]{ mSyncBridge };
	}

	@Override
	protected AbstractSyncBridge getSyncBridge() {
		return mSyncBridge;
	}

}
