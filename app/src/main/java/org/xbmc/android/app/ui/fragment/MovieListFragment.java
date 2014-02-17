package org.xbmc.android.app.ui.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.bumptech.glide.Glide;
import de.greenrobot.event.EventBus;
import org.xbmc.android.app.event.DataItemSynced;
import org.xbmc.android.app.event.HostSwitched;
import org.xbmc.android.app.injection.Injector;
import org.xbmc.android.app.manager.HostManager;
import org.xbmc.android.app.manager.IconManager;
import org.xbmc.android.app.provider.VideoContract;
import org.xbmc.android.app.provider.VideoDatabase;
import org.xbmc.android.remotesandbox.R;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Lists movies in a grid view (one column on phones).
 *
 * @author freezy <freezy@xbmc.org>
 */
public class MovieListFragment extends GridFragment implements LoaderManager.LoaderCallbacks<Cursor> {

	final private static String TAG = MovieListFragment.class.getSimpleName();

	@Inject protected EventBus bus;
	@Inject protected HostManager hostManager;
	@Inject protected IconManager iconManager;

	@InjectView(android.R.id.empty) TextView emptyView;

	private String hostUri;
	private CursorAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
		ButterKnife.inject(this, view);
		emptyView.setText(R.string.empty_movies);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Injector.inject(this);
		bus.register(this);

		hostUri = hostManager.getActiveUri();
		adapter = new MoviesAdapter(getActivity());

		setGridAdapter(adapter);
		getLoaderManager().initLoader(0, null, this);
	}

	/**
	 * Event bus callback. Called when either video or audio sync completed.
	 *
	 * Refreshes data of the fragment.
	 *
	 * @param event Event data
	 */
	public void onEvent(DataItemSynced event) {
		if (event.videoSynced()) {
			getLoaderManager().restartLoader(0, null, this);
		}
	}

	/**
	 * Event bus callback. Called when XBMC host was switched by the user (or
	 * by adding a new host).
	 *
	 * Refreshes data of the fragment.
	 *
	 * @param event Event data
	 */
	public void onEvent(HostSwitched event) {
		hostUri = event.getHost().getUri();
		getLoaderManager().restartLoader(0, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(getActivity(), VideoContract.Movies.CONTENT_URI, MoviesQuery.PROJECTION, null, null, VideoContract.Movies.DEFAULT_SORT);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		adapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursorLoader) {
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
			final View view = getActivity().getLayoutInflater().inflate(R.layout.list_item_movie_wide, parent, false);
			((ImageButton)view.findViewById(R.id.overflow)).setImageDrawable(iconManager.getDrawable(R.string.ic_overflow, 12f, R.color.light_secondry_text));
			((TextView)view.findViewById(R.id.rating)).setTypeface(iconManager.getTypeface());
			return view;
		}

		/** {@inheritDoc} */
		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			final TextView titleView = (TextView) view.findViewById(R.id.title);
			final TextView subtitleView = (TextView) view.findViewById(R.id.genres);
			final TextView ratingView = (TextView) view.findViewById(R.id.rating);
			final TextView runtimeView = (TextView) view.findViewById(R.id.runtime);
			final ImageView imageView = (ImageView) view.findViewById(R.id.poster);
			try {
				final String url = hostUri + "/image/" + URLEncoder.encode(cursor.getString(MoviesQuery.THUMBNAIL), "UTF-8");
				Glide.load(url)
						.centerCrop()
						.animate(android.R.anim.fade_in)
						.into(imageView);

			} catch (UnsupportedEncodingException e) {
				Log.e(TAG, "Cannot encode " + cursor.getString(MoviesQuery.THUMBNAIL) + " from UTF-8.");
			}

			titleView.setText(cursor.getString(MoviesQuery.TITLE) + " (" + cursor.getInt(MoviesQuery.YEAR) + ")");
			subtitleView.setText(cursor.getString(MoviesQuery.GENRES));
			ratingView.setText(iconManager.getStars(cursor.getFloat(MoviesQuery.RATING)));
			int runtime = cursor.getInt(MoviesQuery.RUNTIME);
			if (runtime == 0) {
				runtime = cursor.getInt(MoviesQuery.VIDEO_DURATION);
			}
			if (runtime > 0) {
				runtimeView.setVisibility(View.VISIBLE);
				runtimeView.setText(Math.round(runtime / 60) + " " + getResources().getString(R.string.minutes_short));
			} else {
				runtimeView.setVisibility(View.GONE);
			}

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
				VideoContract.Movies.YEAR,
				VideoContract.Movies.GENRES,
				VideoContract.Movies.RUNTIME,
				VideoContract.Movies.VIDEO_DURATION,
				VideoContract.Movies.RATING,
				VideoContract.Movies.THUMBNAIL
		};

		//final int _ID = 0;
		final int ID = 1;
		final int TITLE = 2;
		final int YEAR = 3;
		final int GENRES = 4;
		final int RUNTIME = 5;
		final int VIDEO_DURATION = 6;
		final int RATING = 7;
		final int THUMBNAIL = 8;
	}
}
