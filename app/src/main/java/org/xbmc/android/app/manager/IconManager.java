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
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import org.xbmc.android.remotesandbox.R;
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

		final Resources resources = context.getResources();
		this.star0 = resources.getString(R.string.ic_star_00);
		this.star1 = resources.getString(R.string.ic_star_25);
		this.star2 = resources.getString(R.string.ic_star_50);
		this.star3 = resources.getString(R.string.ic_star_75);
		this.star4 = resources.getString(R.string.ic_star_100);
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

	/**
	 * Returns number stars based on a rating.
	 *
	 * 0   .25  .5   .75   1
	 * |----|----|----|----|
	 * ==  ===  ===  ===  ==
	 *  .125 .375 .625 .875
	 *
	 * @param rating Rating from 0 - 10
	 * @return Symbol string in 0 - 5 star rating
	 */
	public String getStars(float rating) {
		final StringBuilder sb = new StringBuilder(5);
		rating /= 2f;
		for (int i = 0; i < 5; i++) {
			if (rating < 0.125) {
				sb.append(star0);
			} else if (rating < 0.375) {
				sb.append(star1);
			} else if (rating < 0.625) {
				sb.append(star2);
			} else if (rating < 0.875) {
				sb.append(star3);
			} else {
				sb.append(star4);
			}
			rating -= 1f;
		}
		return sb.toString();
	}

}
