package org.xbmc.android.account.authenticator.ui;

import org.xbmc.android.remotesandbox.R;

public class Step1WelcomeFragment extends AbstractWizardFragment {

	protected Step1WelcomeFragment() {
		super(R.layout.fragment_auth_wizard_01_welcome);
	}

	@Override
	int hasNext() {
		return STATUS_ENABLED;
	}

	@Override
	int hasPrev() {
		return STATUS_GONE;
	}

	@Override
	AbstractWizardFragment getNext() {
		return null;
	}

	@Override
	AbstractWizardFragment getPrev() {
		return null;
	}

	@Override
	void onStatusChanged() {

	}
}
