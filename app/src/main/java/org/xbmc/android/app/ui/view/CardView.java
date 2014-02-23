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

package org.xbmc.android.app.ui.view;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.util.AttributeSet;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * A Card view. Groups common code specific to the card.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class CardView extends RelativeLayout {

	private Object data;

	public CardView(Context context) {
		super(context);
	}

	public CardView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * Setup the overflow menu.
	 *
	 * @param overflowResId ID of the overflow button, can be an {@link android.widget.ImageView}.
	 * @param menuResId ID of the menu resource
	 * @param listener Callback when a menu item is selected.
	 */
	public void setOverflowMenu(int overflowResId, int menuResId, final OnMenuItemClickListener listener) {
		final View overflow = findViewById(overflowResId);
		if (overflow == null) {
			throw new IllegalArgumentException("Cannot find overflow menu in view. Make sure the view contains an id " + overflowResId + ".");
		}

		final PopupMenu popup = new PopupMenu(getContext(), overflow);
		final MenuInflater inflater = popup.getMenuInflater();
		inflater.inflate(menuResId, popup.getMenu());
		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem menuItem) {
				return listener.onMenuItemClick(menuItem, data);
			}
		});

		overflow.setClickable(true);
		overflow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popup.show();
			}
		});
	}

	/**
	 * Returns the data that is provided in the {@link OnMenuItemClickListener}.
	 * @return Data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * Sets the data that is provided in the {@link OnMenuItemClickListener}.
	 * @param data Data
	 */
	public void setData(Object data) {
		this.data = data;
	}

	public interface OnMenuItemClickListener {
		/**
		 * Ran when a menu item of the overflow menu is clicked.
		 * @param item Menu item
		 * @param data Data previously set in #setData.
		 * @return <tt>true</tt> if the event was handled, <tt>false</tt> otherwise.
		 */
		public boolean onMenuItemClick(MenuItem item, Object data);
	}
}
