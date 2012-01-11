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

import org.xbmc.android.jsonrpc.provider.AudioContract;
import org.xbmc.android.jsonrpc.provider.AudioDatabase.Tables;
import org.xbmc.android.jsonrpc.service.AudioSyncService;
import org.xbmc.android.jsonrpc.service.AudioSyncService.RefreshObserver;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.remotesandbox.ui.base.ReloadableListFragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class AlbumsFragment extends ReloadableListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
	
	private static final String TAG = AlbumsFragment.class.getSimpleName();

	// This is the Adapter being used to display the list's data.
	private CursorAdapter mAdapter;

	// If non-null, this is the current filter the user has provided.
	private String mCurrentFilter;
	
	private final AudioSyncService.RefreshObserver mRefreshObserver = new AudioSyncService.RefreshObserver() {
		@Override
		public void onRefreshed() {
			Log.d(TAG, "Refreshing Albums from database.");
			getLoaderManager().restartLoader(0, null, AlbumsFragment.this);
		}
	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Give some text to display if there is no data.
		setEmptyText(getResources().getString(R.string.empty_albums));

		// Create an empty adapter we will use to display the loaded data.
		mAdapter = new AlbumsAdapter(getActivity());
		setListAdapter(mAdapter);

		// Start out with a progress indicator.
		setListShown(false);

		// Prepare the loader. Either re-connect with an existing one,
		// or start a new one.
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	protected RefreshObserver getRefreshObserver() {
		return mRefreshObserver;
	}
	
	@Override
	protected void restartLoader() {
		getLoaderManager().restartLoader(0, null, this);
		Log.i(TAG, "Albums reloaded from database.");
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
		Log.i("FragmentComplexList", "Item clicked: " + id);
	}

	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// This is called when a new Loader needs to be created. This
		// sample only has one Loader, so we don't care about the ID.
		// First, pick the base URI to use depending on whether we are
		// currently filtering.
		Uri baseUri;
		if (mCurrentFilter != null) {
			baseUri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI, Uri.encode(mCurrentFilter));
		} else {
			baseUri = Contacts.CONTENT_URI;
		}

		return new CursorLoader(getActivity(), AudioContract.Albums.CONTENT_URI, AlbumsQuery.PROJECTION, null, null,
				AudioContract.Albums.DEFAULT_SORT);
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		
		// see: http://stackoverflow.com/questions/6757156/android-cursorloader-crash-on-non-topmost-fragment
		if (!isVisible()) {
			return;
		}

		// Swap the new cursor in. (The framework will take care of closing the
		// old cursor once we return.)
		mAdapter.swapCursor(data);

		// The list should now be shown.
		if (isResumed()) {
			setListShown(true);
		} else {
			setListShownNoAnimation(true);
		}
	}

	public void onLoaderReset(Loader<Cursor> loader) {
		// This is called when the last Cursor provided to onLoadFinished()
		// above is about to be closed. We need to make sure we are no
		// longer using it.
		mAdapter.swapCursor(null);
	}
	
	/**
	 * {@link CursorAdapter} that renders a {@link AlbumsQuery}.
	 */
	private class AlbumsAdapter extends CursorAdapter {

		public AlbumsAdapter(Context context) {
			super(context, null, false);
		}

		/** {@inheritDoc} */
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return getActivity().getLayoutInflater().inflate(R.layout.list_item_threelabels, parent, false);
		}

		/** {@inheritDoc} */
		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			final TextView titleView = (TextView) view.findViewById(R.id.item_title);
			final TextView subtitleView = (TextView) view.findViewById(R.id.item_subtitle);
			final TextView subsubtitleView = (TextView) view.findViewById(R.id.item_subsubtitle);

			titleView.setText(cursor.getString(AlbumsQuery.TITLE));
			subtitleView.setText(cursor.getString(AlbumsQuery.ARTIST));
			subsubtitleView.setText(cursor.getString(AlbumsQuery.YEAR));
		}
	}




	/**
	 * {@link org.xbmc.android.jsonrpc.provider.AudioContract.Albums}
	 * query parameters.
	 */
	private interface AlbumsQuery {
//		int _TOKEN = 0x1;

		String[] PROJECTION = { 
				Tables.ALBUMS + "." + BaseColumns._ID, 
				AudioContract.Albums.ID,
				AudioContract.Albums.TITLE, 
				AudioContract.Albums.YEAR,
				AudioContract.Artists.NAME
		};

//		int _ID = 0;
//		int ID = 1;
		int TITLE = 2;
		int YEAR = 3;
		int ARTIST = 4;
	}
}