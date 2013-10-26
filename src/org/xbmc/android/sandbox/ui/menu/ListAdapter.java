package org.xbmc.android.sandbox.ui.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import org.xbmc.android.remotesandbox.R;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: neh
 * Date: 26.10.13
 * Time: 23:41
 * To change this template use File | Settings | File Templates.
 */
public class ListAdapter extends BaseExpandableListAdapter {

	private final Context mContext;
	private final ArrayList<Group> mGroups;

	public ListAdapter(Context context, ArrayList<Group> groups) {
		this.mContext = context;
		this.mGroups = groups;
	}

	public View getGroupView(int groupPosition, boolean isLastChild, View view, ViewGroup parent) {

		final Group group = mGroups.get(groupPosition);
		if (view == null) {
			LayoutInflater inf = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inf.inflate(R.layout.slidingmenu_item_expandable, null);
		}
		final TextView tv = (TextView) view.findViewById(R.id.label);
		tv.setText(group.getName());
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
