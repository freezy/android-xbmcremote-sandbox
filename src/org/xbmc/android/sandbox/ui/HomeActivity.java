package org.xbmc.android.sandbox.ui;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import org.xbmc.android.jsonrpc.provider.AudioContract;
import org.xbmc.android.jsonrpc.provider.AudioDatabase;
import org.xbmc.android.jsonrpc.service.AudioSyncService;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.sandbox.ui.sync.AbstractSyncBridge;
import org.xbmc.android.sandbox.ui.sync.AudioSyncBridge;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * The landing page of the app.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class HomeActivity extends RefreshableActivity implements LoaderManager.LoaderCallbacks<Cursor> {

	private static final String TAG = HomeActivity.class.getSimpleName();
	/**
	 * Sync bridge for global refresh.
	 */
	private AudioSyncBridge mSyncBridge;

	private CursorAdapter mAdapter;

	private final AudioSyncService.RefreshObserver mRefreshObserver = new AudioSyncService.RefreshObserver() {
		@Override
		public void onRefreshed() {
			Log.d(TAG, "Refreshing Albums from database.");
			getSupportLoaderManager().restartLoader(0, null, HomeActivity.this);
		}
	};


	public HomeActivity() {
		super(R.string.title_home, R.layout.activity_home);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// only slide menu, not the action bar.
		setSlidingActionBarEnabled(false);

		// Create an empty adapter we will use to display the loaded data.
		mAdapter = new AlbumsAdapter(this);
		final GridView gl = (GridView)findViewById(R.id.home_music_grid);
		gl.setAdapter(mAdapter);

		// Prepare the loader. Either re-connect with an existing one,
		// or start a new one.
		getSupportLoaderManager().initLoader(0, null, this);
	}

	@Override
	protected AbstractSyncBridge[] initSyncBridges() {
		mSyncBridge = new AudioSyncBridge(mRefreshObservers);
		return new AbstractSyncBridge[]{ mSyncBridge };
	}

	@Override
	protected AbstractSyncBridge getSyncBridge() {
		return mSyncBridge;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		// This is called when a new Loader needs to be created. This
		// sample only has one Loader, so we don't care about the ID.
		// First, pick the base URI to use depending on whether we are
		// currently filtering.
		Uri baseUri;
		baseUri = ContactsContract.Contacts.CONTENT_URI;

		return new CursorLoader(this, AudioContract.Albums.CONTENT_URI, AlbumsQuery.PROJECTION, null, null,
				AudioContract.Albums.DEFAULT_SORT);
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
	 * {@link CursorAdapter} that renders a {@link AlbumsQuery}.
	 */
	private class AlbumsAdapter extends CursorAdapter {

		public AlbumsAdapter(Context context) {
			super(context, null, false);
		}

		/** {@inheritDoc} */
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return HomeActivity.this.getLayoutInflater().inflate(R.layout.list_item_album_compact, parent, false);
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
