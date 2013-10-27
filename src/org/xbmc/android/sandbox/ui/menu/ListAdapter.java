package org.xbmc.android.sandbox.ui.menu;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.sandbox.ui.IconHelper;

import java.util.ArrayList;

public class ListAdapter extends BaseExpandableListAdapter {

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

		label.setText(group.getName());
		icon.setTypeface(mIconFont);
		icon.setText(group.getIcon());
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
