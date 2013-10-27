package org.xbmc.android.sandbox.ui;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Helper methods around the icon font.
 */
public class IconHelper {

	public static Typeface getTypeface(Context context) {
		return Typeface.createFromAsset(context.getAssets(), "symbols.ttf");
	}


}
