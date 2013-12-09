package org.xbmc.android.account.authenticator.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A parent class for all wizard fragments.
 *
 * @author freezy <freezy@xbmc.org>
 */
public abstract class AbstractWizardFragment extends Fragment {

	protected final static int STATUS_ENABLED = 0x01;
	protected final static int STATUS_DISABLED = 0x02;
	protected final static int STATUS_GONE = 0x03;

	private final int layoutRes;

	protected AbstractWizardFragment(int layoutRes) {
		this.layoutRes = layoutRes;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(layoutRes, container, false);
	}

	/**
	 * Indicates whether a page has a next page.
	 * @return one of: STATUS_ENABLED, STATUS_DISABLED or STATUS_GONE.
	 */
	abstract int hasNext();
	/**
	 * Indicates whether a page has a previous page.
	 * @return one of: STATUS_ENABLED, STATUS_DISABLED or STATUS_GONE.
	 */
	abstract int hasPrev();

	/**
	 * Returns the next page.
	 * @return Next page
	 */
	AbstractWizardFragment getNext() {
		return null;
	}

	/**
	 * Returns the previous page.
	 * @return Previous page
	 */
	AbstractWizardFragment getPrev() {
		return null;
	}

	/**
	 * Called when status has changed and next/prev potentially return a different result.
	 */
	void onStatusChanged() {
	}
}
