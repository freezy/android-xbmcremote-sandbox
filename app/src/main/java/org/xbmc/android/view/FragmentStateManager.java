package org.xbmc.android.view;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import org.xbmc.android.view.RelativePagerFragment.OnStatusChangeListener;

import java.util.HashMap;

/**
 * Caches fragments for the {@link RelativePagerFragment}.
 */
public class FragmentStateManager {

	private final static String TAG = FragmentStateManager.class.getSimpleName();

	private final HashMap<String, Fragment> fragments = new HashMap<String, Fragment>();

	private RelativePagerAdapter onStatusChangeListener;

	public FragmentStateManager() {
	}

	/**
	 * Returns fragment from cache if found, otherwise instantiates it.
	 *
	 * Typically called on {@link RelativePagerFragment#hasNext()} and
	 * {@link RelativePagerFragment#hasPrev()}
	 *
	 * @param klass Type of the fragment
	 * @return Fragment instance
	 */
	public RelativePagerFragment getFragment(Class<? extends RelativePagerFragment> klass) {
		Log.d(TAG, "Getting fragment " + klass.getSimpleName() + " from cache.");
		if (!fragments.containsKey(klass.getName())) {
			Log.d(TAG, "Nothing found, instantiating " + klass.getSimpleName() + " manually.");
			final RelativePagerFragment fragment = instantiateFragment(klass);
			fragments.put(klass.getName(), fragment);
			return fragment;
		}
		return (RelativePagerFragment)fragments.get(klass.getName());
	}

	/**
	 * Removes a fragment from the cache. This is sometimes desireable if a fragment contains a
	 * complex state that otherwise would have to be manually reset. In this case, removing the
	 * fragment will force a re-instantiation and thus a clean state.
	 */
	public void removeFragment(Class<? extends RelativePagerFragment> klass) {
		if (fragments.containsKey(klass.getName())) {
			fragments.remove(klass.getName());
		}
	}

	/**
	 * Use this to instantiate the frame state manager.
	 */
	public static FragmentStateManager get(Activity activity) {
		if (activity == null) {
			throw new IllegalArgumentException("Activity for fragment state manager must not be null.");
		}
		if (!(activity instanceof FragmentStateManageable)) {
			throw new IllegalArgumentException("Activity must implement FragmentStateManageable.");
		}
		return ((FragmentStateManageable)activity).getFragmentStateManager();
	}

	/**
	 * Instantiates a fragment by class name.
	 */
	private RelativePagerFragment instantiateFragment(Class<? extends RelativePagerFragment> klass) {
		try {
			return klass.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException("Error instantiating fragment " + klass.getSimpleName(), e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Error instantiating fragment " + klass.getSimpleName(), e);
		}
	}

	/**
	 * Sets the {@link OnStatusChangeListener}, used by the pager.
	 */
	public void setOnStatusChangeListener(RelativePagerAdapter onStatusChangeListener) {
		this.onStatusChangeListener = onStatusChangeListener;
	}

	/**
	 * Returns the {@link OnStatusChangeListener}.
	 */
	public RelativePagerAdapter getOnStatusChangeListener() {
		return onStatusChangeListener;
	}

	/**
	 * An activity that contains an instance of this class.
	 */
	public interface FragmentStateManageable {
		/**
		 * Returns the fragment state manager.
		 */
		public FragmentStateManager getFragmentStateManager();
	}
}
