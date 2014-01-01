package org.xbmc.android.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.util.HashMap;

public class FragmentStateManager {

	private final static String TAG = FragmentStateManager.class.getSimpleName();

	private final FragmentActivity activity;
	private final HashMap<String, RelativePagerFragment> fragments = new HashMap<String, RelativePagerFragment>();

	private RelativePagerAdapter onStatusChangeListener;

	public FragmentStateManager(FragmentActivity activity) {
		this.activity = activity;
	}

	public void putFragment(Bundle outState, Class<? extends RelativePagerFragment> klass) {
		if (!fragments.containsKey(klass.getName())) {
			throw new IllegalStateException("Cannot find fragment " + klass.getSimpleName() + " in saved states.");
		}
		Log.d(TAG, "Putting fragment " + klass.getSimpleName() + " into fragment manager.");
		activity.getSupportFragmentManager().putFragment(outState, klass.getName(), fragments.get(klass.getName()));
	}

	public RelativePagerFragment initFragment(Bundle savedInstanceState, Class<? extends RelativePagerFragment> klass) {
		RelativePagerFragment fragment = null;
		Log.d(TAG, "Getting fragment " + klass.getSimpleName() + " from fragment manager.");
		if (savedInstanceState != null) {
			fragment = (RelativePagerFragment)activity.getSupportFragmentManager().getFragment(savedInstanceState, klass.getName());
		}
		if (fragment == null && fragments.containsKey(klass.getName())) {
			Log.d(TAG, "Found fragment " + klass.getSimpleName() + " in cache, returning.");
			return fragments.get(klass.getName());
		}
		if (fragment == null) {
			Log.d(TAG, "Nothing found, instantiating " + klass.getSimpleName() + " manually.");
			fragment = instantiateFragment(klass);
		}
		fragments.put(klass.getName(), fragment);
		return fragment;
	}

	public RelativePagerFragment getFragment(Class<? extends RelativePagerFragment> klass) {
		Log.d(TAG, "Getting fragment " + klass.getSimpleName() + " from cache.");
		if (!fragments.containsKey(klass.getName())) {
			Log.d(TAG, "Nothing found, instantiating " + klass.getSimpleName() + " manually.");
			final RelativePagerFragment fragment = instantiateFragment(klass);
			fragments.put(klass.getName(), fragment);
			return fragment;
		}
		return fragments.get(klass.getName());
	}

	public static FragmentStateManager get(Activity activity) {
		if (activity == null) {
			throw new IllegalArgumentException("Activity for fragment state manager must not be null.");
		}
		if (!(activity instanceof FragmentStateManageable)) {
			throw new IllegalArgumentException("Activity must implement FragmentStateManageable.");
		}
		if (!(activity instanceof FragmentActivity)) {
			throw new IllegalArgumentException("Activity must extend FragmentActivity.");
		}
		return ((FragmentStateManageable)activity).getFragmentStateManager((FragmentActivity)activity);
	}

	private RelativePagerFragment instantiateFragment(Class<? extends RelativePagerFragment> klass) {
		try {
			return klass.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException("Error instantiating fragment " + klass.getSimpleName(), e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Error instantiating fragment " + klass.getSimpleName(), e);
		}
	}

	public void setOnStatusChangeListener(RelativePagerAdapter onStatusChangeListener) {
		this.onStatusChangeListener = onStatusChangeListener;
	}

	public RelativePagerAdapter getOnStatusChangeListener() {
		return onStatusChangeListener;
	}

	public interface FragmentStateManageable {
		public FragmentStateManager getFragmentStateManager(FragmentActivity activity);
	}
}
