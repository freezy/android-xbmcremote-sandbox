package org.xbmc.android.remotesandbox.ui.base;

/**
 * Creates text for the symbol font.
 */
public class IconHelper {

	public static String makeRatingStars(double rating) {
		final StringBuilder sb = new StringBuilder();
		int full = (int) Math.floor(rating / 2);
		for (int i = 0; i < full; i++) {
			sb.append("$");
		}
		double diff = (rating / 2) - (double)full;
		if (diff > 0.125 && diff <= 0.375) {
			sb.append("!");
		} else if (diff > 0.375 && diff <= 0.625) {
			sb.append("\"");
		} else if (diff > 0.625 && diff <= 0.875) {
			sb.append("#");
		}
		return sb.toString();
	}
}
