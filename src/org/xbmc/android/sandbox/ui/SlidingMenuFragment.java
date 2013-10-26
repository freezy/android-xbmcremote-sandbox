package org.xbmc.android.sandbox.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.sandbox.ui.menu.Child;
import org.xbmc.android.sandbox.ui.menu.Group;
import org.xbmc.android.sandbox.ui.menu.ListAdapter;

import java.util.ArrayList;

public class SlidingMenuFragment extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.slidingmenu, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		final ExpandableListView list = (ExpandableListView) getView().findViewById(R.id.slidingmenu_expandable_list);

		final ArrayList<Group> groups = new ArrayList<Group>();
		groups.add(new Group("Parent 1", new Child("Child 1"), new Child("Child 2")));
		groups.add(new Group("Parent 2", new Child("Child 3"), new Child("Child 4"), new Child("Child 5")));

		list.setAdapter(new ListAdapter(getActivity(), groups));
	}

}
