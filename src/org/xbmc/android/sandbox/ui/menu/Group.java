package org.xbmc.android.sandbox.ui.menu;

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
	private final ArrayList<Child> mItems = new ArrayList<Child>();

	public Group(String name, Child... children) {
		mName = name;
		Collections.addAll(mItems, children);
	}

	public String getName() {
		return mName;
	}

	public ArrayList<Child> getItems() {
		return mItems;
	}
}
