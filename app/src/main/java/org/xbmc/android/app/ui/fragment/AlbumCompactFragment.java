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

package org.xbmc.android.app.ui.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import de.greenrobot.event.EventBus;
import org.xbmc.android.app.event.HostSwitched;
import org.xbmc.android.app.manager.HostManager;
import org.xbmc.android.app.provider.AudioContract;
import org.xbmc.android.app.provider.AudioDatabase;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.app.event.DataItemSynced;
import org.xbmc.android.app.injection.Injector;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AlbumCompactFragment extends GridFragment implements LoaderManager.LoaderCallbacks<Cursor> {

	private final static String TAG = AlbumCompactFragment.class.getSimpleName();

	@Inject protected EventBus bus;
	@Inject protected HostManager hostManager;

	private CursorAdapter adapter;
	private String hostUri;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home_grid, container);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Injector.inject(this);
		bus.register(this);
		hostUri = hostManager.getActiveUri();
	}

	@Override
	public void onDestroy() {
		bus.unregister(this);
		super.onDestroy();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		adapter = new AlbumsAdapter(getActivity());
		setGridAdapter(adapter);

		// prepare the loader. Either re-connect with an existing one, or start a new one.
		getLoaderManager().initLoader(0, null, this);
	}

	public void onEvent(DataItemSynced event) {
		if (event.audioSynced()) {
			getLoaderManager().restartLoader(0, null, this);
		}
	}

	public void onEvent(HostSwitched event) {
		hostUri = event.getHost().getUri();
		getLoaderManager().restartLoader(0, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		// This is called when a new Loader needs to be created. This
		// sample only has one Loader, so we don't care about the ID.
		// First, pick the base URI to use depending on whether we are
		// currently filtering.
		Uri baseUri;
		baseUri = ContactsContract.Contacts.CONTENT_URI;

		return new CursorLoader(getActivity(), AudioContract.Albums.CONTENT_URI, AlbumsQuery.PROJECTION, null, null,
				AudioContract.Albums.sortLatest(getResources().getInteger(R.integer.home_numrows)));
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		// Swap the new cursor in. (The framework will take care of closing the
		// old cursor once we return.)
		adapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursorLoader) {
		// This is called when the last Cursor provided to onLoadFinished()
		// above is about to be closed. We need to make sure we are no
		// longer using it.
		adapter.swapCursor(null);
	}

	/**
	 * {@link android.support.v4.widget.CursorAdapter} that renders a {@link AlbumsQuery}.
	 */
	private class AlbumsAdapter extends CursorAdapter {

		public AlbumsAdapter(Context context) {
			super(context, null, false);
		}

		/** {@inheritDoc} */
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return getActivity().getLayoutInflater().inflate(R.layout.list_item_album_compact, parent, false);
		}

		/** {@inheritDoc} */
		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			final TextView titleView = (TextView) view.findViewById(R.id.title_album);
			final TextView subtitleView = (TextView) view.findViewById(R.id.title_artist);
			final ImageView imageView = (ImageView) view.findViewById(R.id.list_album_cover);
			try {
				final String url = hostUri + "/image/" + URLEncoder.encode(cursor.getString(AlbumsQuery.THUMBNAIL), "UTF-8");
				Glide.load(url)
					.centerCrop()
					.animate(android.R.anim.fade_in)
					.into(imageView);

			} catch (UnsupportedEncodingException e) {
				Log.e(TAG, "Cannot encode " + cursor.getString(AlbumsQuery.THUMBNAIL) + " from UTF-8.");
			}

			titleView.setText(cursor.getString(AlbumsQuery.TITLE));
			subtitleView.setText(cursor.getString(AlbumsQuery.ARTIST));
		}
	}

	/**
	 * {@link org.xbmc.android.app.provider.AudioContract.Albums}
	 * query parameters.
	 */
	private interface AlbumsQuery {
//		int _TOKEN = 0x1;

		String[] PROJECTION = {
				AudioDatabase.Tables.ALBUMS + "." + BaseColumns._ID,
				AudioContract.Albums.ID,
				AudioContract.Albums.TITLE,
				AudioContract.Albums.YEAR,
				AudioContract.Artists.NAME,
				AudioContract.Albums.THUMBNAIL
		};

		//		int _ID = 0;
//		int ID = 1;
		int TITLE = 2;
		int YEAR = 3;
		int ARTIST = 4;
		int THUMBNAIL = 5;
	}

}
