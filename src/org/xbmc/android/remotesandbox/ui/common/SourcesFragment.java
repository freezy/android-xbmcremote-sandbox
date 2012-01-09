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

package org.xbmc.android.remotesandbox.ui.common;

import java.util.List;

import org.xbmc.android.jsonrpc.api.FilesAPI.Source;
import org.xbmc.android.jsonrpc.client.AbstractClient.ErrorHandler;
import org.xbmc.android.jsonrpc.client.FilesClient;
import org.xbmc.android.jsonrpc.io.ApiException;
import org.xbmc.android.jsonrpc.service.AudioSyncService;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.remotesandbox.ui.base.BaseFragmentTabsActivity;
import org.xbmc.android.util.ListLoader;
import org.xbmc.android.util.ListLoader.Worker;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SourcesFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<Source>> {

	private static final String TAG = SourcesFragment.class.getSimpleName();

	// This is the Adapter being used to display the list's data.
	private SourceListAdapter mAdapter;

	// If non-null, this is the current filter the user has provided.
	private String mCurrentFilter;

	private final AudioSyncService.RefreshObserver mRefreshObserver = new AudioSyncService.RefreshObserver() {

		@Override
		public void onRefreshed() {
			Log.d(TAG, "Refreshing Artists from database.");
			getLoaderManager().restartLoader(0, null, SourcesFragment.this);
		}
	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Give some text to display if there is no data.
		setEmptyText(getResources().getString(R.string.empty_music_sources));

		// We have a menu item to show in action bar.
		setHasOptionsMenu(true);

		// Create an empty adapter we will use to display the loaded data.
		mAdapter = new SourceListAdapter(getActivity());
		setListAdapter(mAdapter);

		// Start out with a progress indicator.
		setListShown(false);

		// Prepare the loader. Either re-connect with an existing one,
		// or start a new one.
		getLoaderManager().initLoader(0, null, this);
	}

	public boolean onQueryTextChange(String newText) {
		// Called when the action bar search text has changed. Update
		// the search filter, and restart the loader to do a new query
		// with this filter.
		mCurrentFilter = !TextUtils.isEmpty(newText) ? newText : null;
		getLoaderManager().restartLoader(0, null, this);
		return true;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// Insert desired behavior here.
		Source source = (Source)getListAdapter().getItem(position);
		Log.i("FragmentComplexList", "Item clicked: " + source.path);
	}

	@Override
	public Loader<List<Source>> onCreateLoader(int id, Bundle args) {
		// This is called when a new Loader needs to be created. This
		// sample only has one Loader with no arguments, so it is simple.
		final Handler h = new Handler();
		return new ListLoader<Source>(getActivity(), new Worker<Source>() {
			@Override
			public List<Source> doWork() {
				final FilesClient filesClient = new FilesClient();
				return filesClient.getMusicSources(new ErrorHandler() {
					@Override
					public void handleError(final ApiException e) {
						Log.e(TAG, "ERROR " + e.getCode() + ": " + e.getMessage());
						h.post(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(getActivity(), "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
							}
						});
					}
				});
			}
		});
	}

	@Override
	public void onLoadFinished(Loader<List<Source>> loader, List<Source> data) {

		if (!isVisible()) {
			return;
		}

		// Set the new data in the adapter.
		mAdapter.setData(data);

		// The list should now be shown.
		if (isResumed()) {
			setListShown(true);
		} else {
			setListShownNoAnimation(true);
		}
	}

	@Override
	public void onLoaderReset(Loader<List<Source>> loader) {
		// Clear the data in the adapter.
		mAdapter.setData(null);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (getActivity() instanceof BaseFragmentTabsActivity) {
			((BaseFragmentTabsActivity) getActivity()).registerRefreshObserver(mRefreshObserver);
		}
		getLoaderManager().restartLoader(0, null, this);
	}

	@Override
	public void onPause() {
		super.onPause();
		if (getActivity() instanceof BaseFragmentTabsActivity) {
			((BaseFragmentTabsActivity) getActivity()).unregisterRefreshObserver(mRefreshObserver);
		}
	}

	public static class SourceListAdapter extends ArrayAdapter<Source> {
		private final LayoutInflater mInflater;

		public SourceListAdapter(Context context) {
			super(context, android.R.layout.simple_list_item_2);
			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public void setData(List<Source> data) {
			clear();
			if (data != null) {
				for (Source source : data) {
					add(source);
				}
			}
		}

		/**
		 * Populate new items in the list.
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;

			if (convertView == null) {
				view = mInflater.inflate(R.layout.list_item_onelabel, parent, false);
			} else {
				view = convertView;
			}
			Source item = getItem(position);
			((TextView) view.findViewById(R.id.item_title)).setText(item.label);

			return view;
		}
	}

}