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

package org.xbmc.android.app.manager;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import org.xbmc.android.view.TextDrawable;

/**
 * Helper methods around the icon font.
 */
public class IconManager {

	private final Context context;
	private final Typeface symbols;
	private String star0, star1, star2, star3, star4;

	public IconManager(Context context) {
		this.context = context;
		this.symbols = Typeface.createFromAsset(context.getAssets(), "symbols.ttf");
	}

	public Typeface getTypeface() {
		return symbols;
	}

	public Drawable getDrawable(int symbol, float size, int color) {
		final TextDrawable d = new TextDrawable(context);
		d.setText(context.getResources().getString(symbol));
		d.setTypeface(symbols);
		d.setTextSize(size);
		if (color != 0) {
			d.setTextColor(color);
		}
		return d;
	}

	public Drawable getDrawable(int symbol) {
		return getDrawable(symbol, 48f, 0);
	}

	public String getStars(float rating) {
		if (star0 == null) {

		}
		return null;
	}

}
