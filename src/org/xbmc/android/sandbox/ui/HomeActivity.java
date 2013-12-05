package org.xbmc.android.sandbox.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import de.greenrobot.event.EventBus;
import org.xbmc.android.injection.Injector;
import org.xbmc.android.jsonrpc.service.SyncService;
import org.xbmc.android.remotesandbox.R;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;

import javax.inject.Inject;

/**
 * The landing page of the app.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class HomeActivity extends BaseActivity implements PullToRefreshAttacher.OnRefreshListener {

	@Inject protected EventBus BUS;

	private static final String TAG = HomeActivity.class.getSimpleName();
	/**
	 * Sync bridge for global refresh.
	 */
//	private SyncBridge mSyncBridge;
	private PullToRefreshAttacher mPullToRefreshAttacher;

	public HomeActivity() {
		super(R.string.title_home, R.layout.activity_home);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Injector.inject(this);
		BUS.register(this);

		// only slide menu, not the action bar.
		setSlidingActionBarEnabled(false);

		mPullToRefreshAttacher = PullToRefreshAttacher.get(this);

		// Retrieve the PullToRefreshLayout from the content view
		PullToRefreshLayout ptrLayout = (PullToRefreshLayout) findViewById(R.id.ptr_layout);

		// Give the PullToRefreshAttacher to the PullToRefreshLayout, along with a refresh listener.
		ptrLayout.setPullToRefreshAttacher(mPullToRefreshAttacher, this);
	}

	@Override
	public void onDestroy() {
		BUS.unregister(this);
		super.onDestroy();
	}

	public void onEvent(SyncService.SyncEvent event) {
		switch (event.getStatus()) {
			case SyncService.STATUS_RUNNING:
				Log.d(TAG, "Got event STATUS_RUNNING from SyncService via bus.");
				break;
			case SyncService.STATUS_ERROR:
				Log.e(TAG, "Got event STATUS_ERROR from SyncService via bus: " + event.getMessage());
				mPullToRefreshAttacher.setRefreshComplete();
				break;
			case SyncService.STATUS_FINISHED:
				Log.d(TAG, "Got event STATUS_FINISHED from SyncService via bus.");
				mPullToRefreshAttacher.setRefreshComplete();
				break;
			default:
				Log.w(TAG, "Got unknown event " + event.getStatus() + " from SyncService via bus.");
				mPullToRefreshAttacher.setRefreshComplete();
				break;
		}
	}

	@Override
	public void onRefreshStarted(View view) {
		final long start = System.currentTimeMillis();
		startService(new Intent(Intent.ACTION_SYNC, null, this, SyncService.class));
		Log.d(TAG, "Triggered refresh in " + (System.currentTimeMillis() - start) + "ms.");
	}

/*	@Override
	protected AbstractSyncBridge[] initSyncBridges() {
		mSyncBridge = new SyncBridge(mRefreshObservers, SyncBridge.SECTION_MUSIC, SyncBridge.SECTION_MOVIES);
		//mSyncBridge = new SyncBridge(mRefreshObservers, SyncBridge.SECTION_MOVIES);
		return new AbstractSyncBridge[]{ mSyncBridge };
	}

	@Override
	protected AbstractSyncBridge getSyncBridge() {
		return mSyncBridge;
	}
*/
}
