package org.xbmc.android.account.authenticator.ui;

import org.xbmc.android.remotesandbox.R;

public class Step2bNothingFoundFragment extends AbstractWizardFragment {

	protected Step2bNothingFoundFragment() {
		super(R.layout.fragment_auth_wizard_02b_nothing_found);
	}

	@Override
	int hasNext() {
		return STATUS_ENABLED;
	}

	@Override
	int hasPrev() {
		return STATUS_ENABLED;
	}

	@Override
	AbstractWizardFragment getNext() {
		return new Step2bNothingFoundFragment();
	}

	@Override
	AbstractWizardFragment getPrev() {
		return new Step2aSearchingFragment();
	}
}
