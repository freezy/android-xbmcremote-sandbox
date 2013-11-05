package org.xbmc.android.sandbox.ui.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import org.xbmc.android.jsonrpc.provider.AudioContract;
import org.xbmc.android.jsonrpc.provider.AudioDatabase;
import org.xbmc.android.jsonrpc.service.SyncService;
import org.xbmc.android.remotesandbox.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AlbumCompactFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

	private final static String TAG = AlbumCompactFragment.class.getSimpleName();
	private CursorAdapter mAdapter;
	private GridView mGridView;

	private final SyncService.RefreshObserver mRefreshObserver = new SyncService.RefreshObserver() {
		@Override
		public void onRefreshed() {
			Log.d(TAG, "Refreshing Albums from database.");
			getActivity().getSupportLoaderManager().restartLoader(0, null, AlbumCompactFragment.this);
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final int spacing= (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getActivity().getResources().getDisplayMetrics());
		mGridView = new GridView(getActivity());
		mGridView.setNumColumns(3);
		mGridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		mGridView.setHorizontalSpacing(spacing);
		mGridView.setVerticalSpacing(spacing);
		return mGridView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mAdapter = new AlbumsAdapter(getActivity());
		mGridView.setAdapter(mAdapter);

		// Prepare the loader. Either re-connect with an existing one,
		// or start a new one.
		getActivity().getSupportLoaderManager().initLoader(0, null, this);
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
				AudioContract.Albums.SORT_LATEST_3);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		// Swap the new cursor in. (The framework will take care of closing the
		// old cursor once we return.)
		mAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursorLoader) {
		// This is called when the last Cursor provided to onLoadFinished()
		// above is about to be closed. We need to make sure we are no
		// longer using it.
		mAdapter.swapCursor(null);
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
				final String url = "http://192.168.0.100:8080/image/" + URLEncoder.encode(cursor.getString(AlbumsQuery.THUMBNAIL), "UTF-8");
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
	 * {@link org.xbmc.android.jsonrpc.provider.AudioContract.Albums}
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
