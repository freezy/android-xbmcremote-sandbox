package org.xbmc.android.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class RelativePagerFragment extends Fragment {

	protected final static int STATUS_ENABLED = 0x01;
	protected final static int STATUS_DISABLED = 0x02;
	protected final static int STATUS_GONE = 0x03;

	private final int layoutRes;
	protected final Activity activity;
	protected final OnStatusChangeListener statusChangeListener;


	protected RelativePagerFragment(int layoutRes, Activity activity, OnStatusChangeListener statusChangeListener) {
		this.layoutRes = layoutRes;
		this.activity = activity;
		this.statusChangeListener = statusChangeListener;
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
	RelativePagerFragment getNext() {
		return null;
	}

	/**
	 * Returns the previous page.
	 * @return Previous page
	 */
	RelativePagerFragment getPrev() {
		return null;
	}

	/**
	 * Executed when the fragment comes into view
	 */
	void onPageVisible() {
	}

	interface OnStatusChangeListener {
		void onStatusChanged();
	}
}
