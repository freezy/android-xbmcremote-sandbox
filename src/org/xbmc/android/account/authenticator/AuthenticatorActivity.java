/*
 *      Copyright (C) 2005-2015 Team XBMC
 *      http://xbmc.org
 *
 *  This Program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2, or (at your option)
 *  any later version.
 *
 *  This Program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with XBMC Remote; see the file license.  If not, write to
 *  the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.
 *  http://www.gnu.org/copyleft/gpl.html
 *
 */

package org.xbmc.android.account.authenticator;

import java.util.ArrayList;

import org.xbmc.android.account.Constants;
import org.xbmc.android.jsonrpc.api.ApplicationAPI.Version;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.util.google.DetachableResultReceiver;
import org.xbmc.android.zeroconf.DiscoveryService;
import org.xbmc.android.zeroconf.XBMCHost;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

/**
 * Activity which displays login screen to the user.
 */
public class AuthenticatorActivity extends AccountAuthenticatorActivity implements DetachableResultReceiver.Receiver {

	public static final String PARAM_CONFIRMCREDENTIALS = "confirmCredentials";
	public static final String PARAM_PASSWORD = "password";
	public static final String PARAM_USERNAME = "username";
	public static final String PARAM_AUTHTOKEN_TYPE = "authtokenType";

	private static final String TAG = "AuthenticatorActivity";

	private AccountManager mAccountManager;
	private Thread mAuthThread;
	private String mAuthtoken;
	private String mAuthtokenType;
	private final Handler mHandler = new Handler();
	
	private DetachableResultReceiver mReceiver;
	
	/* Those are the different states of the "wizard". */
	private static final int STATE_ZEROCONF = 0x01;
	private static final int STATE_FINISHED = 0x02;
	
	private int mCurrentState = STATE_ZEROCONF;
	
	private int mApiVersion = -1;
	private Version mXbmcVersion = null;

	/**
	 * If set we are just checking that the user knows their credentials; this
	 * doesn't cause the user's password to be changed on the device.
	 */
	private Boolean mConfirmCredentials = false;

	/** cache reference to views */
	private View mPageCredentials;
	private View mPageZeroconf;
	private View mPageFinished;
	private TextView mMessage;
	private String mPassword;
	private EditText mPasswordEdit;
	private ImageButton mZeroconfDiscoverButton;
	private ProgressBar mZeroconfProgressBar;
	private Spinner mZeroconfSpinner;
	private TextView mZeroconfSpinnerText;
	private Button mButtonNext;
	private Button mButtonPrev;

	/** Was the original caller asking for an entirely new account? */
	protected boolean mRequestNewAccount = false;

	private String mUsername;
	private EditText mUsernameEdit;
	
	private final ArrayList<XBMCHost> mDiscoveredHosts = new ArrayList<XBMCHost>();
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onCreate(Bundle icicle) {
		
		Log.i(TAG, "onCreate(" + icicle + ")");
		super.onCreate(icicle);
		
		mAccountManager = AccountManager.get(this);
		final Intent intent = getIntent();
		mUsername = intent.getStringExtra(PARAM_USERNAME);
		mAuthtokenType = intent.getStringExtra(PARAM_AUTHTOKEN_TYPE);
		mRequestNewAccount = mUsername == null;
		mConfirmCredentials = intent.getBooleanExtra(PARAM_CONFIRMCREDENTIALS, false);

		Log.i(TAG, "    request new: " + mRequestNewAccount);
		requestWindowFeature(Window.FEATURE_LEFT_ICON);
		setContentView(R.layout.activity_addaccount);
		getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, android.R.drawable.ic_dialog_info);
		
		// pages
		mPageCredentials = findViewById(R.id.addaccount_credentials);
		mPageZeroconf = findViewById(R.id.addaccount_zeroconf);
		mPageFinished = findViewById(R.id.addaccount_finished);

		// common views
		mButtonNext = (Button)findViewById(R.id.addaccount_next_button);
		mButtonPrev = (Button)findViewById(R.id.addaccount_prev_button);
		
		// zeroconf view
		mZeroconfDiscoverButton = (ImageButton) findViewById(R.id.addaccount_zeroconf_scan_button);
		mZeroconfProgressBar = (ProgressBar) findViewById(R.id.addaccount_zeroconf_progressbar);
		mZeroconfSpinner = (Spinner)findViewById(R.id.addaccount_zeroconf_spinner);
		mZeroconfSpinnerText = (TextView) findViewById(R.id.addaccount_zeroconf_spinnertext);
		
		// credentials view
		mMessage = (TextView) findViewById(R.id.addaccount_credentials_text);
		mUsernameEdit = (EditText) findViewById(R.id.username_edit);
		mPasswordEdit = (EditText) findViewById(R.id.password_edit);
		mUsernameEdit.setText(mUsername);
		mMessage.setText(getMessage());
		
		// define result receiver for zeroconf data
		mReceiver = new DetachableResultReceiver(new Handler());
		mReceiver.setReceiver(this);
		
		discoverHosts(null);
	}
	
	private void switchPage() {
		
		switch(mCurrentState) {
			case STATE_ZEROCONF:
				mPageZeroconf.setVisibility(View.VISIBLE);
				mPageCredentials.setVisibility(View.GONE);
				mPageFinished.setVisibility(View.GONE);
				break;
			case STATE_FINISHED:
				mPageZeroconf.setVisibility(View.GONE);
				mPageCredentials.setVisibility(View.GONE);
				mPageFinished.setVisibility(View.VISIBLE);
				break;
		}
	}
	

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		switch (resultCode) {
			case DiscoveryService.STATUS_RESOLVED:
				// only toggle views the first time
				if (mDiscoveredHosts.isEmpty()) {
					mButtonNext.setEnabled(true);
					mZeroconfSpinnerText.setVisibility(View.GONE);
					mZeroconfSpinner.setVisibility(View.VISIBLE);
				}
				final XBMCHost host = (XBMCHost)resultData.getParcelable(DiscoveryService.EXTRA_HOST);
				mDiscoveredHosts.add(host);
				Log.i(TAG, "Received host data: " + host);
				mZeroconfSpinner.setAdapter(new DiscoveredHostsAdapter(this, mDiscoveredHosts));
				break;
				
			case DiscoveryService.STATUS_FINISHED:
				if (mDiscoveredHosts.isEmpty()) {
					mZeroconfSpinnerText.setText(R.string.addaccount_nothingfound);
				} else {
					mZeroconfDiscoverButton.setVisibility(View.VISIBLE);
					mZeroconfProgressBar.setVisibility(View.GONE);
				}
				break;
			default:
				break;
		}
	}
	
	private class DiscoveredHostsAdapter extends ArrayAdapter<XBMCHost> implements SpinnerAdapter {
		final LayoutInflater mInflater;
		public DiscoveredHostsAdapter(Context context, ArrayList<XBMCHost> items) {
			super(context, android.R.layout.simple_spinner_item, items);
			mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			View row;
	 
			if (null == convertView) {
				row = mInflater.inflate(R.layout.list_item_threelabels, null);
				((ImageView) row.findViewById(R.id.item_icon)).setImageResource(R.drawable.icon);
			} else {
				row = convertView;
			}
	 
			final XBMCHost host = getItem(position);
			final TextView line1 = (TextView) row.findViewById(R.id.item_title);
			final TextView line2 = (TextView) row.findViewById(R.id.item_subtitle);
			line1.setText(host.getHost());
			line2.setText(host.getAddress() + ":" + host.getPort());
			return row;
		}
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		final ProgressDialog dialog = new ProgressDialog(this);
		dialog.setMessage(getText(R.string.addaccount_progress_checkingapiversion));
		dialog.setIndeterminate(true);
		dialog.setCancelable(true);
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				Log.i(TAG, "dialog cancel has been invoked");
				if (mAuthThread != null) {
					mAuthThread.interrupt();
					finish();
				}
			}
		});
		return dialog;
	}
	
	public void handlePrev(View view) {
		switch (mCurrentState) {
		case STATE_ZEROCONF:
			
			break;
		}
	}
	
	public void handleNext(View view) {
		switch (mCurrentState) {
			/*
			 * From the zeroconf screen, probe XBMC.  
			 */
			case STATE_ZEROCONF: {
				showProgress();
				mAuthThread = NetworkUtilities.attemptProbe((XBMCHost)mZeroconfSpinner.getSelectedItem(), mHandler, AuthenticatorActivity.this);
				break;
			}
			
			default:
				break;
		}
	}

	/**
	 * Handles onClick event on the Submit button. Sends username/password to
	 * the server for authentication.
	 * 
	 * @param view
	 *            The Submit button for which this method is invoked
	 */
	public void handleLogin(View view) {
		if (mRequestNewAccount) {
			mUsername = mUsernameEdit.getText().toString();
		}
		mPassword = mPasswordEdit.getText().toString();
		if (TextUtils.isEmpty(mUsername) || TextUtils.isEmpty(mPassword)) {
			mMessage.setText(getMessage());
		} else {
			showProgress();
			// Start authenticating...
			//mAuthThread = NetworkUtilities.attemptAuth(mUsername, mPassword, mHandler, AuthenticatorActivity.this);
		}
	}
	
	public void discoverHosts(View view) {
		mZeroconfDiscoverButton.setVisibility(View.INVISIBLE);
		mZeroconfProgressBar.setVisibility(View.VISIBLE);
		
		mZeroconfSpinnerText.setText(R.string.addaccount_scanning);
		mZeroconfSpinner.setAdapter(new DiscoveredHostsAdapter(this, new ArrayList<XBMCHost>(0)));
		mDiscoveredHosts.clear();
		
		mZeroconfSpinner.setVisibility(View.INVISIBLE);
		mZeroconfSpinnerText.setVisibility(View.VISIBLE);
		
		mButtonNext.setEnabled(false);
		
		runDiscovery();
	}
	
	private void runDiscovery() {
		
		// discover zeroconf hosts
		final Intent discoveryIntent = new Intent(Intent.ACTION_SEARCH, null, this, DiscoveryService.class);
		discoveryIntent.putExtra(DiscoveryService.EXTRA_STATUS_RECEIVER, mReceiver);
		startService(discoveryIntent);
	}

	/**
	 * Called when response is received from the server for confirm credentials
	 * request. See onAuthenticationResult(). Sets the
	 * AccountAuthenticatorResult which is sent back to the caller.
	 * 
	 * @param the
	 *            confirmCredentials result.
	 */
	protected void finishConfirmCredentials(boolean result) {
		Log.i(TAG, "finishConfirmCredentials()");
		final Account account = new Account(mUsername, Constants.ACCOUNT_TYPE);
		mAccountManager.setPassword(account, mPassword);
		final Intent intent = new Intent();
		intent.putExtra(AccountManager.KEY_BOOLEAN_RESULT, result);
		setAccountAuthenticatorResult(intent.getExtras());
		setResult(RESULT_OK, intent);
		finish();
	}

	/**
	 * 
	 * Called when response is received from the server for authentication
	 * request. See onAuthenticationResult(). Sets the
	 * AccountAuthenticatorResult which is sent back to the caller. Also sets
	 * the authToken in AccountManager for this account.
	 * 
	 * @param the
	 *            confirmCredentials result.
	 */

	protected void finishLogin() {
		Log.i(TAG, "finishLogin()");
		final Account account = new Account(mUsername, Constants.ACCOUNT_TYPE);

		if (mRequestNewAccount) {
			mAccountManager.addAccountExplicitly(account, mPassword, null);
			// Set contacts sync for this account.
			ContentResolver.setSyncAutomatically(account, ContactsContract.AUTHORITY, true);
		} else {
			mAccountManager.setPassword(account, mPassword);
		}
		final Intent intent = new Intent();
		mAuthtoken = mPassword;
		intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, mUsername);
		intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, Constants.ACCOUNT_TYPE);
		if (mAuthtokenType != null && mAuthtokenType.equals(Constants.AUTHTOKEN_TYPE)) {
			intent.putExtra(AccountManager.KEY_AUTHTOKEN, mAuthtoken);
		}
		setAccountAuthenticatorResult(intent.getExtras());
		setResult(RESULT_OK, intent);
		finish();
	}

	/**
	 * Hides the progress UI for a lengthy operation.
	 */
	protected void hideProgress() {
		dismissDialog(0);
	}
	
	public void onProbeResult(int apiVersion, Version xbmcVersion) {
		
		mApiVersion = apiVersion;
		mXbmcVersion = xbmcVersion;
		
		Log.i(TAG, "Found XBMC with API version " + apiVersion + ".");
		Log.i(TAG, "Found XBMC at version " + xbmcVersion + ".");
		hideProgress();
		mCurrentState = STATE_FINISHED;
		switchPage();
		
	}

	/**
	 * Called when the authentication process completes (see attemptLogin()).
	 */
	public void onAuthenticationResult(boolean result) {
		Log.i(TAG, "onAuthenticationResult(" + result + ")");
		// Hide the progress dialog
		hideProgress();
		if (result) {
			if (!mConfirmCredentials) {
				finishLogin();
			} else {
				finishConfirmCredentials(true);
			}
		} else {
			Log.e(TAG, "onAuthenticationResult: failed to authenticate");
			if (mRequestNewAccount) {
				// "Please enter a valid username/password.
				//mMessage.setText(getText(R.string.login_activity_loginfail_text_both));
				mMessage.setText("both failed.");
			} else {
				// "Please enter a valid password." (Used when the
				// account is already in the database but the password
				// doesn't work.)
				mMessage.setText("password failed.");
				//mMessage.setText(getText(R.string.login_activity_loginfail_text_pwonly));
			}
		}
	}

	/**
	 * Returns the message to be displayed at the top of the login dialog box.
	 */
	private CharSequence getMessage() {
		if (TextUtils.isEmpty(mUsername)) {
			// If no username, then we ask the user to log in using an
			// appropriate service.
			//final CharSequence msg = getText(R.string.login_activity_newaccount_text);
			final CharSequence msg = "new account";
			return msg;
		}
		if (TextUtils.isEmpty(mPassword)) {
			// We have an account but no password
			//return getText(R.string.login_activity_loginfail_text_pwmissing);
			return "login failed, password missing.";
		}
		return null;
	}

	/**
	 * Shows the progress UI for a lengthy operation.
	 */
	protected void showProgress() {
		showDialog(0);
	}
	
}
