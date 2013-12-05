package org.xbmc.android.app.ui.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.ExpandableListView;
import org.xbmc.android.remotesandbox.R;

import java.util.ArrayList;

public class SlidingMenuFragment extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.slidingmenu, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		final ExpandableListView list = (ExpandableListView) getView().findViewById(R.id.slidingmenu_expandable_list);
		list.setGroupIndicator(null);

		final ArrayList<Group> groups = new ArrayList<Group>();
		groups.add(new Group("Home", R.string.ic_home));
		groups.add(new Group("Music", R.string.ic_music, new Child("Albums"), new Child("Artists"), new Child("Genres")));
		groups.add(new Group("Movies", R.string.ic_movie, new Child("Sets"), new Child("Genres"), new Child("Actors"), new Child("Recently Added")));
		groups.add(new Group("TV Shows", R.string.ic_tv, new Child("Title"), new Child("Genres"), new Child("Years"), new Child("Recently Added")));
		groups.add(new Group("Pictures", R.string.ic_picture));
		groups.add(new Group("Addons", R.string.ic_addon));

		list.setAdapter(new ListAdapter(getActivity(), groups));
	}

}
