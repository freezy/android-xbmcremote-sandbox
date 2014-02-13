package org.xbmc.android.app.manager;

import android.content.Context;
import android.content.SharedPreferences;
import org.xbmc.android.app.injection.Injector;

import javax.inject.Inject;

/**
 * Manages non-user preferences.
 *
 * @author freezy <freezy@xmbmc.org>
 */
public class SettingsManager {

	private static final String NAME = "GlobalPreferences";
	public static final String PREFS_SYNCED = "synced";

	@Inject Context context;

	final SharedPreferences settings;

	public SettingsManager() {
		Injector.inject(this);

		settings = context.getSharedPreferences(NAME, 0);
	}

	public boolean hasSynced() {
		return settings.getBoolean(PREFS_SYNCED, false);
	}

	public void setSynced(boolean synced) {
		final SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(PREFS_SYNCED, synced);
		editor.commit();
	}
}
