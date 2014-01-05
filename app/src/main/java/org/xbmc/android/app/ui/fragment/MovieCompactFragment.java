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
import org.xbmc.android.app.manager.HostManager;
import org.xbmc.android.app.provider.VideoContract;
import org.xbmc.android.app.provider.VideoDatabase;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.app.event.DataItemSynced;
import org.xbmc.android.app.injection.Injector;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MovieCompactFragment extends GridFragment implements LoaderManager.LoaderCallbacks<Cursor> {

	private final static String TAG = MovieCompactFragment.class.getSimpleName();

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

		adapter = new MoviesAdapter(getActivity());
		setGridAdapter(adapter);
		((TextView)getView().findViewById(R.id.home_header1)).setText(R.string.title_movies);
		((TextView)getView().findViewById(R.id.home_header2)).setText(R.string.recently_added);

		// prepare the loader. Either re-connect with an existing one, or start a new one.
		getLoaderManager().initLoader(0, null, this);
	}

	public void onEvent(DataItemSynced event) {
		if (event.videoSynced()) {
			getLoaderManager().restartLoader(0, null, this);
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		// This is called when a new Loader needs to be created. This
		// sample only has one Loader, so we don't care about the ID.
		// First, pick the base URI to use depending on whether we are
		// currently filtering.
		Uri baseUri;
		baseUri = ContactsContract.Contacts.CONTENT_URI;

		return new CursorLoader(getActivity(), VideoContract.Movies.CONTENT_URI, MoviesQuery.PROJECTION, null, null,
				VideoContract.Movies.SORT_LATEST_3);
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
	 * {@link android.support.v4.widget.CursorAdapter} that renders a {@link org.xbmc.android.app.ui.fragment.MovieCompactFragment.MoviesQuery}.
	 */
	private class MoviesAdapter extends CursorAdapter {

		public MoviesAdapter(Context context) {
			super(context, null, false);
		}

		/** {@inheritDoc} */
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return getActivity().getLayoutInflater().inflate(R.layout.list_item_movie_compact, parent, false);
		}

		/** {@inheritDoc} */
		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			final TextView titleView = (TextView) view.findViewById(R.id.title_movie);
			final TextView subtitleView = (TextView) view.findViewById(R.id.title_genre);
			final ImageView imageView = (ImageView) view.findViewById(R.id.list_movie_poster);
			try {
				final String url = hostUri + "/image/" + URLEncoder.encode(cursor.getString(MoviesQuery.THUMBNAIL), "UTF-8");
				Glide.load(url)
					.centerCrop()
					.animate(android.R.anim.fade_in)
					.into(imageView);

			} catch (UnsupportedEncodingException e) {
				Log.e(TAG, "Cannot encode " + cursor.getString(MoviesQuery.THUMBNAIL) + " from UTF-8.");
			}

			titleView.setText(cursor.getString(MoviesQuery.TITLE));
			subtitleView.setText(cursor.getString(MoviesQuery.GENRE));
		}
	}

	/**
	 * {@link org.xbmc.android.app.provider.VideoContract.Movies}
	 * query parameters.
	 */
	private interface MoviesQuery {

		String[] PROJECTION = {
				VideoDatabase.Tables.MOVIES + "." + BaseColumns._ID,
				VideoContract.Movies.ID,
				VideoContract.Movies.TITLE,
				VideoContract.Movies.GENRE,
				VideoContract.Movies.THUMBNAIL
		};

		// int _ID = 0; int ID = 1;
		int TITLE = 2;
		int GENRE = 3;
		int THUMBNAIL = 4;
	}

}
