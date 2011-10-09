package org.xbmc.android.remotesandbox.ui;

import org.xbmc.android.jsonrpc.provider.AudioContract;
import org.xbmc.android.jsonrpc.provider.AudioDatabase.Tables;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.util.NotifyingAsyncQueryHandler;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.BaseColumns;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AlbumsFragment extends ListFragment implements NotifyingAsyncQueryHandler.AsyncQueryListener {
	
	private static final String TAG = AlbumsFragment.class.getSimpleName();

	private static final String STATE_CHECKED_POSITION = "checkedPosition";

	private Cursor mCursor;
	private CursorAdapter mAdapter;
	private int mCheckedPosition = -1;
	private boolean mHasSetEmptyText = false;

	private NotifyingAsyncQueryHandler mHandler;
	private Handler mMessageQueueHandler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHandler = new NotifyingAsyncQueryHandler(getActivity().getContentResolver(), this);
		mAdapter = new AlbumsAdapter(getActivity());
		setListAdapter(mAdapter);

		// Start background query to load albums
		query();
		Log.d(TAG, "AlbumsFragment created.");
	}
	
	/**
	 * Runs the (local) query.
	 */
	private void query() {
		if (mHandler != null) {
			if (mCursor != null) {
				mCursor.close();
				mCursor = null;
			}
			mHandler.startQuery(AlbumsQuery._TOKEN, null, AudioContract.Albums.CONTENT_URI, AlbumsQuery.PROJECTION, null, null, AudioContract.Albums.DEFAULT_SORT);
		} else {
			Log.w(TAG, "Handler is null, cannot query.");
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		if (savedInstanceState != null) {
			mCheckedPosition = savedInstanceState.getInt(STATE_CHECKED_POSITION, -1);
		}

		if (!mHasSetEmptyText) {
			// Could be a bug, but calling this twice makes it become visible
			// when it shouldn't
			// be visible.
			setEmptyText(getString(R.string.empty_albums));
			mHasSetEmptyText = true;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void onQueryComplete(int token, Object cookie, Cursor cursor) {
		Log.d(TAG, "onQueryComplete()");
		if (getActivity() == null) {
			return;
		}

		if (token == AlbumsQuery._TOKEN) {
			onQueryComplete(cursor);
		} else {
			Log.d("AlbumsFragment/onQueryComplete", "Query complete, Not Actionable: " + token);
			cursor.close();
		}
	}

	/**
	 * Handle {@link AlbumsQuery} {@link Cursor}.
	 */
	private void onQueryComplete(Cursor cursor) {
		if (mCursor != null) {
			// In case cancelOperation() doesn't work and we end up with
			// consecutive calls to this
			// callback.
			Log.d(TAG, "resetting cursor.");
			getActivity().stopManagingCursor(mCursor);
			mCursor = null;
		}

		mCursor = cursor;
		getActivity().startManagingCursor(mCursor);
		mAdapter.changeCursor(mCursor);
		if (mCheckedPosition >= 0 && getView() != null) {
			getListView().setItemChecked(mCheckedPosition, true);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		mMessageQueueHandler.post(mRefreshAlbumsRunnable);
		getActivity().getContentResolver().registerContentObserver(AudioContract.Albums.CONTENT_URI, true, mAlbumsChangesObserver);
		if (mCursor != null) {
			mCursor.requery();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		mMessageQueueHandler.removeCallbacks(mRefreshAlbumsRunnable);
		getActivity().getContentResolver().unregisterContentObserver(mAlbumsChangesObserver);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_CHECKED_POSITION, mCheckedPosition);
	}
	
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.refresh_menu_items, menu);
    }

	/**
	 * {@link CursorAdapter} that renders a {@link AlbumsQuery}.
	 */
	private class AlbumsAdapter extends CursorAdapter {
		public AlbumsAdapter(Context context) {
			super(context, null);
		}

		/** {@inheritDoc} */
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return getActivity().getLayoutInflater().inflate(R.layout.list_item_album, parent, false);
		}

		/** {@inheritDoc} */
		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			final TextView titleView = (TextView) view.findViewById(R.id.album_title);
			final TextView subtitleView = (TextView) view.findViewById(R.id.album_subtitle);

			titleView.setText(cursor.getString(AlbumsQuery.TITLE));
			subtitleView.setText(cursor.getString(AlbumsQuery.ARTIST));
		}
	}

	private ContentObserver mAlbumsChangesObserver = new ContentObserver(new Handler()) {
		@Override
		public void onChange(boolean selfChange) {
			query();
		}
	};

	private Runnable mRefreshAlbumsRunnable = new Runnable() {
		public void run() {
			if (mAdapter != null) {
				// This is used to refresh session title colors.
				mAdapter.notifyDataSetChanged();
			}

			// Check again on the next quarter hour, with some padding to
			// account for network
			// time differences.
			long nextQuarterHour = (SystemClock.uptimeMillis() / 900000 + 1) * 900000 + 5000;
			mMessageQueueHandler.postAtTime(mRefreshAlbumsRunnable, nextQuarterHour);
		}
	};

	/**
	 * {@link org.xbmc.android.jsonrpc.provider.AudioContract.Albums}
	 * query parameters.
	 */
	private interface AlbumsQuery {
		int _TOKEN = 0x1;

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
//		int YEAR = 3;
		int ARTIST = 4;
	}
}
