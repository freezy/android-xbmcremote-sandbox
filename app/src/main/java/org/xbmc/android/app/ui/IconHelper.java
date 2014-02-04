package org.xbmc.android.app.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import org.xbmc.android.view.TextDrawable;

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

	public static Drawable getDrawable(Context context, int symbol, float size, int color) {
		final TextDrawable d = new TextDrawable(context);
		d.setText(context.getResources().getString(symbol));
		d.setTypeface(getTypeface(context));
		d.setTextSize(size);
		if (color != 0) {
			d.setTextColor(color);
		}
		return d;
	}

	public static Drawable getDrawable(Context context, int symbol) {
		return getDrawable(context, symbol, 48f, 0);
	}

}
