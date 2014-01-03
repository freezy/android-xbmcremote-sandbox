package org.xbmc.android.app.ui;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Helper methods around the icon font.
 */
public class IconHelper {

	private static Typeface symbols;

	public static Typeface getTypeface(Context context) {
		if (symbols == null) {
			symbols = Typeface.createFromAsset(context.getAssets(), "symbols.ttf");
		}
		return symbols;
	}


}
