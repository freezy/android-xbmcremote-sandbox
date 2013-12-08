package org.xbmc.android.app.ui.menu;

import android.content.Intent;

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

	private final String name;
	private final int icon;
	private final ArrayList<Child> items = new ArrayList<Child>();
	private final Intent intent;

	public boolean collapsed = true;

	public Group(String name, int icon, Intent intent, Child... children) {
		this.name = name;
		this.icon = icon;
		this.intent = intent;
		Collections.addAll(items, children);
	}

	public Group(String name, int icon, Child... children) {
		this(name, icon, null, children);
	}

	public void toggle() {
		collapsed = !collapsed;
	}

	public boolean hasChildren() {
		return !items.isEmpty();
	}

	public String getName() {
		return name;
	}

	public Intent getIntent() {
		return intent;
	}

	public boolean hasIntent() {
		return intent != null;
	}

	public int getIcon() {
		return icon;
	}

	public ArrayList<Child> getItems() {
		return items;
	}
}
