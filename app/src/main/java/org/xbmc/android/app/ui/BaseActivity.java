package org.xbmc.android.app.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import org.xbmc.android.app.injection.Injector;
import org.xbmc.android.remotesandbox.R;

/**
 * Common to all fragment activities.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class BaseActivity extends ActionBarActivity {

	private int titleRes;
	private int iconRes;
	protected Fragment fragment;

	private final int contentViewRes;

	public BaseActivity(int titleRes, int iconRes, int contentViewRes) {
		this.titleRes = titleRes;
		this.iconRes = iconRes;
		this.contentViewRes = contentViewRes;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Injector.inject(this);

		setTitle(titleRes);
		setContentView(contentViewRes);

/*		final ActionBar actionBar = getSupportActionBar();
		actionBar.setIcon(IconHelper.getDrawable(getApplicationContext(), iconRes));

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
			fragment = new SlidingMenuFragment();
			t.replace(R.id.menu_frame, fragment);
			t.commit();
		} else {
			fragment = this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
		}

		// customize the SlidingMenu
		final SlidingMenu sm = getSlidingMenu();
		sm.setBehindWidthRes(R.dimen.slidingmenu_width);
		sm.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
		sm.setShadowDrawable(R.drawable.slidingmenu_shadow);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setSlidingEnabled(false);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
	}
/*
	protected void enableNavdrawer() {
		getSlidingMenu().setSlidingEnabled(true);
	}
*/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
//				toggle();
				return true;
/*			case R.id.github:
				Util.goToGitHub(this);
				return true;*/
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
