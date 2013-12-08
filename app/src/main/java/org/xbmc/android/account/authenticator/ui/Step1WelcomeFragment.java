package org.xbmc.android.account.authenticator.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.xbmc.android.remotesandbox.R;

public class Step1WelcomeFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_auth_wizard_01_welcome, container, false);
	}
}
