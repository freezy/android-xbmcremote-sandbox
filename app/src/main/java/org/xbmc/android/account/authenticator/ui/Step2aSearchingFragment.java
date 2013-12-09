package org.xbmc.android.account.authenticator.ui;

import org.xbmc.android.remotesandbox.R;

public class Step2aSearchingFragment extends AbstractWizardFragment {

	protected Step2aSearchingFragment() {
		super(R.layout.fragment_auth_wizard_02a_searching);
	}

	@Override
	int hasNext() {
		return STATUS_DISABLED;
	}

	@Override
	int hasPrev() {
		return STATUS_ENABLED;
	}

	@Override
	AbstractWizardFragment getNext() {
		return new Step1WelcomeFragment();
	}

	@Override
	AbstractWizardFragment getPrev() {
		return new Step1WelcomeFragment();
	}
}
