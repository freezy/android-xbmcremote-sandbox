package org.xbmc.android.sandbox.ui;

import android.os.Bundle;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import org.xbmc.android.remotesandbox.R;

/**
 * The landing page of the app.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class HomeActivity extends BaseActivity {

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
}
