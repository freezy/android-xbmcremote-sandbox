package org.xbmc.android.app.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import org.xbmc.android.app.ui.IconHelper;
import org.xbmc.android.remotesandbox.R;

/**
 * Fragments that hints the pull-to-refresh pattern.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class RefreshNoticeFragment extends Fragment {

	@InjectView(R.id.arrow1) TextView arrow1;
	@InjectView(R.id.arrow2) TextView arrow2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_refresh_notice, container);
		ButterKnife.inject(this, view);

		final Context context = getActivity().getApplicationContext();
		arrow1.setTypeface(IconHelper.getTypeface(context));
		arrow2.setTypeface(IconHelper.getTypeface(context));

		return view;
	}
}
