package org.xbmc.android.account.authenticator;

import android.os.Bundle;
import butterknife.ButterKnife;
import butterknife.InjectView;
import co.juliansuarez.libwizardpager.wizard.ui.StepPagerStrip;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import org.xbmc.android.remotesandbox.R;

public class WizardFiddleActivity extends SherlockFragmentActivity {

	@InjectView(R.id.strip) StepPagerStrip pagerStrip;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accountwizard);
		setTitle(R.string.accountwizard_title);
		ButterKnife.inject(this);

		pagerStrip.setPageCount(4);
		pagerStrip.setCurrentPage(1);
	}

}
