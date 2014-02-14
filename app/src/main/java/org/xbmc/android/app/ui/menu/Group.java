/*
 *      Copyright (C) 2005-2015 Team XBMC
 *      http://xbmc.org
 *
 *  This Program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2, or (at your option)
 *  any later version.
 *
 *  This Program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with XBMC Remote; see the file license.  If not, write to
 *  the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.
 *  http://www.gnu.org/copyleft/gpl.html
 *
 */

package org.xbmc.android.app.ui.menu;

import android.content.Intent;

import java.util.ArrayList;
import java.util.Collections;

/**
 * An expandable menu item.
 *
 * @author freezy <freezy@xbmc.org>
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
