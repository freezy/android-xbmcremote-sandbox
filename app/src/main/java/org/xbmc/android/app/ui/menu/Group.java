package org.xbmc.android.app.ui.menu;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: neh
 * Date: 26.10.13
 * Time: 23:38
 * To change this template use File | Settings | File Templates.
 */
public class Group {

	private final String mName;
	private final int mIcon;
	private final ArrayList<Child> mItems = new ArrayList<Child>();

	public boolean collapsed = true;

	public Group(String name, int icon, Child... children) {
		mName = name;
		mIcon = icon;
		Collections.addAll(mItems, children);
	}

	public void toggle() {
		collapsed = !collapsed;
	}

	public boolean hasChildren() {
		return !mItems.isEmpty();
	}

	public String getName() {
		return mName;
	}

	public int getIcon() {
		return mIcon;
	}

	public ArrayList<Child> getItems() {
		return mItems;
	}
}
