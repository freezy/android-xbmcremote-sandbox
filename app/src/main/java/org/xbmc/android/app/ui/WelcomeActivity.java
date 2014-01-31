package org.xbmc.android.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
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
				moveTaskToBack(true);
				//android.os.Process.killProcess(android.os.Process.myPid());
			}
		});

		setupBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(WelcomeActivity.this, WizardActivity.class), WizardActivity.RESULT_SUCCESS);
			}
		});

		logo.setTypeface(IconHelper.getTypeface(getApplicationContext()));
		getSupportActionBar().setIcon(IconHelper.getDrawable(getApplicationContext(), R.string.ic_logo));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == WizardActivity.RESULT_SUCCESS) {
			final Intent intent = new Intent(this, HomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			moveTaskToBack(true);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
