package org.xbmc.android.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import org.xbmc.android.account.authenticator.ui.WizardActivity;
import org.xbmc.android.remotesandbox.R;

/**
 * @author freezy <freezy@xbmc.org>
 */
public class WelcomeActivity extends ActionBarActivity {

	@InjectView(R.id.exit_button) Button exitBtn;
	@InjectView(R.id.setup_button) Button setupBtn;
	@InjectView(R.id.logo) TextView logo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		ButterKnife.inject(this);

		exitBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		});

		setupBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(WelcomeActivity.this, WizardActivity.class));
			}
		});

		logo.setTypeface(IconHelper.getTypeface(getApplicationContext()));
		getSupportActionBar().setIcon(IconHelper.getDrawable(getApplicationContext(), R.string.ic_logo));
	}
}
