package org.xbmc.android.app.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import org.xbmc.android.account.authenticator.ui.WizardActivity;
import org.xbmc.android.app.ui.HostChooseActivity;
import org.xbmc.android.app.ui.IconHelper;
import org.xbmc.android.remotesandbox.R;

import java.util.ArrayList;

public class SlidingMenuFragment extends Fragment {

	@InjectView(R.id.slidingmenu_expandable_list) ExpandableListView list;
	@InjectView(R.id.change_host) Button changeHostButton;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.slidingmenu, null);
		ButterKnife.inject(this, v);
		return v;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		list.setGroupIndicator(null);
		changeHostButton.setTypeface(IconHelper.getTypeface(getActivity().getApplicationContext()));
		changeHostButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), HostChooseActivity.class));
			}
		});

		final ArrayList<Group> groups = new ArrayList<Group>();
		groups.add(new Group("Home", R.string.ic_home));
		groups.add(new Group("Music", R.string.ic_music, new Child("Albums"), new Child("Artists"), new Child("Genres")));
		groups.add(new Group("Movies", R.string.ic_movie, new Child("Sets"), new Child("Genres"), new Child("Actors"), new Child("Recently Added")));
		groups.add(new Group("TV Shows", R.string.ic_tv, new Child("Title"), new Child("Genres"), new Child("Years"), new Child("Recently Added")));
		groups.add(new Group("Pictures", R.string.ic_picture));
		groups.add(new Group("Addons", R.string.ic_addon, new Intent(getActivity(), WizardActivity.class)));

		list.setAdapter(new ListAdapter(getActivity(), groups));
	}


	private class ListAdapter extends BaseExpandableListAdapter {

		private final Context mContext;
		private final ArrayList<Group> mGroups;
		private final Typeface mIconFont;

		public ListAdapter(Context context, ArrayList<Group> groups) {
			mContext = context;
			mGroups = groups;
			mIconFont = IconHelper.getTypeface(context);
		}

		public View getGroupView(final int groupPosition, boolean isLastChild, View view, final ViewGroup parent) {

			final Group group = mGroups.get(groupPosition);
			if (view == null) {
				LayoutInflater inf = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inf.inflate(R.layout.slidingmenu_item_expandable, null);
			}
			final TextView icon = (TextView) view.findViewById(R.id.icon);
			final TextView label = (TextView) view.findViewById(R.id.label);
			final Button indicator = (Button) view.findViewById(R.id.indicator);

			int foreColor;
			if (group.getName().equals("Home")) {
				foreColor = mContext.getResources().getColor(R.color.holo_blue_bright);
			} else {
				foreColor = mContext.getResources().getColor(R.color.dark_secondary_text);
			}

			label.setTextColor(foreColor);
			label.setText(group.getName());
			indicator.setTextColor(foreColor);
			icon.setTypeface(mIconFont);
			icon.setText(group.getIcon());
			icon.setTextColor(foreColor);
			indicator.setTypeface(mIconFont);
			indicator.setVisibility(group.hasChildren() ? View.VISIBLE : View.GONE);
			indicator.setText(group.collapsed ? R.string.ic_expand : R.string.ic_collapse);
			indicator.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (group.collapsed) {
						((ExpandableListView)parent).expandGroup(groupPosition);
					} else {
						((ExpandableListView)parent).collapseGroup(groupPosition);
					}
					group.toggle();
				}
			});

			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(mContext, group.getName(), Toast.LENGTH_SHORT).show();
					if (group.hasIntent()) {
						startActivity(group.getIntent());
					}
				}
			});
			return view;
		}

		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {

			Child child = (Child) getChild(groupPosition, childPosition);
			if (view == null) {
				LayoutInflater infalInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = infalInflater.inflate(R.layout.slidingmenu_item, null);
			}
			TextView tv = (TextView) view.findViewById(R.id.label);
			tv.setText(child.getName());
			return view;
		}

		public Object getGroup(int groupPosition) {
			return mGroups.get(groupPosition);
		}

		public int getGroupCount() {
			return mGroups.size();
		}

		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		public Object getChild(int groupPosition, int childPosition) {
			return mGroups.get(groupPosition).getItems().get(childPosition);
		}

		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		public int getChildrenCount(int groupPosition) {
			return mGroups.get(groupPosition).getItems().size();

		}

		public boolean hasStableIds() {
			return true;
		}

		public boolean isChildSelectable(int arg0, int arg1) {
			return true;
		}
	}


}
