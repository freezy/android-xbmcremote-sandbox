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

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;
import org.xbmc.android.account.Constants;
import org.xbmc.android.jsonrpc.api.model.ApplicationModel.PropertyValue.Version;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.util.google.DetachableResultReceiver;
import org.xbmc.android.zeroconf.XBMCHost;

import java.util.ArrayList;

/**
 * A "wizard" which guides the user through adding a new XBMC host setting.
 * It first tries to find hosts in the network using zeroconf, but also
 * gives the user the possibility to manually add a host.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class AuthenticatorActivity extends AccountAuthenticatorActivity implements DetachableResultReceiver.Receiver {

	//private StepPagerStrip mStepPagerStrip;

	public static final String PARAM_CONFIRMCREDENTIALS = "confirmCredentials";
	public static final String PARAM_PASSWORD = "password";
	public static final String PARAM_USERNAME = "username";
	public static final String PARAM_AUTHTOKEN_TYPE = "authtokenType";

	private static final String TAG = "AuthenticatorActivity";

	private AccountManager mAccountManager;
	private Thread mAuthThread;
	private final Handler mHandler = new Handler();

	private DetachableResultReceiver mReceiver;

	/* Those are the different states of the "wizard". */
	private static final int PAGE_ZEROCONF = 0x01;
	private static final int PAGE_FINISHED = 0x02;

	private int mCurrentPage = PAGE_ZEROCONF;

	private int mApiVersion = -1;
	private Version mXbmcVersion = null;
	private XBMCHost mHost = null;

	/**
	 * If set we are just checking that the user knows their credentials; this
	 * doesn't cause the user's password to be changed on the device.
	 */
	private Boolean mConfirmCredentials = false;

	/** cache reference to views */
	private ViewFlipper mFlipper;
	private TextView mMessage;
	private String mPassword;
	private EditText mPasswordEdit;
	private ImageButton mZeroconfDiscoverButton;
	private ProgressBar mZeroconfProgressBar;
	private Spinner mZeroconfSpinner;
	private TextView mZeroconfSpinnerText;
	private TextView mFinishedText;
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
		super.onCreate(icicle);

		// set layout params
		requestWindowFeature(Window.FEATURE_LEFT_ICON);
/*		setContentView(R.layout.activity_addaccount);
		getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, android.R.drawable.ic_dialog_info);

		// common views
		mFlipper = (ViewFlipper) findViewById(R.id.addaccount_flipper);
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

		// finished view
		mFinishedText = (TextView) findViewById(R.id.addaccount_finished_text);

		mAccountManager = AccountManager.get(this);

		// define result receiver for zeroconf data
		mReceiver = new DetachableResultReceiver(new Handler());
		mReceiver.setReceiver(this);

		discoverHosts(null);*/
	}

	private void flipPage(int page) {
		int currentPage = mCurrentPage;
		while (currentPage != page) {
			if (currentPage < page) {
				++currentPage;
				mFlipper.showNext();
			} else {
				--currentPage;
				mFlipper.showPrevious();
			}
		}
		mCurrentPage = page;

		// update layouts
		switch (page) {
			case PAGE_ZEROCONF:
				mButtonPrev.setVisibility(View.GONE);
				mButtonNext.setVisibility(View.VISIBLE);
				break;
			case PAGE_FINISHED:
				mButtonPrev.setVisibility(View.GONE);
				mButtonNext.setVisibility(View.VISIBLE);
				mButtonNext.setText(R.string.addaccount_close_button);
				mFinishedText.setText("Found XBMC version " + mXbmcVersion + " running API v" + mApiVersion + ".");
				break;
		}
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
/*		switch (resultCode) {
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
				}
				mZeroconfDiscoverButton.setVisibility(View.VISIBLE);
				mZeroconfProgressBar.setVisibility(View.GONE);
				break;
			default:
				break;
		}*/
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

/*			if (null == convertView) {
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
			*/
			return null;
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
		switch (mCurrentPage) {
		case PAGE_ZEROCONF:

			break;
		}
	}

	public void handleNext(View view) {
		switch (mCurrentPage) {
			/*
			 * From the zeroconf screen, probe XBMC.
			 */
			case PAGE_ZEROCONF: {
				mHost = (XBMCHost)mZeroconfSpinner.getSelectedItem();
				showProgress();
				mAuthThread = NetworkUtilities.attemptProbe(mHost, mHandler, AuthenticatorActivity.this);
				break;
			}
			case PAGE_FINISHED: {
				finishUp();
				break;
			}

			default:
				break;
		}
	}
/*
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
*/
	private void runDiscovery() {

		// discover zeroconf hosts
/*		final Intent discoveryIntent = new Intent(Intent.ACTION_SEARCH, null, this, DiscoveryService.class);
		discoveryIntent.putExtra(DiscoveryService.EXTRA_STATUS_RECEIVER, mReceiver);
		startService(discoveryIntent);*/
	}


	protected void finishUp() {
		Log.i(TAG, "finishLogin()");
		final Account account = new Account(mHost.getHost(), Constants.ACCOUNT_TYPE);
		final Bundle data = new Bundle();
		data.putString(Constants.DATA_ADDRESS, mHost.getAddress());
		data.putString(Constants.DATA_PORT, String.valueOf(mHost.getPort()));
		mAccountManager.addAccountExplicitly(account, null, data);
		final Intent intent = new Intent();
		intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, mHost.getHost());
		intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, Constants.ACCOUNT_TYPE);
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
		flipPage(PAGE_FINISHED);

	}


	/**
	 * Shows the progress UI for a lengthy operation.
	 */
	protected void showProgress() {
		showDialog(0);
	}

}
