package org.xbmc.android.app.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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
import org.xbmc.android.app.ui.MovieActivity;
import org.xbmc.android.app.ui.view.CardView;
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
	public void onGridItemClick(GridView g, View view, int position, long id) {

		final CardView card = (CardView)view;
		final Intent intent = new Intent(getActivity(), MovieActivity.class);
		final DataHolder dataHolder = (DataHolder)card.getData();
		intent.putExtra(MovieActivity.EXTRA_MOVIE_ID, dataHolder._id);
		startActivity(intent);
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

		private final CardView.OnMenuItemClickListener menuItemClickListener = new CardView.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item, Object d) {
				final DataHolder data = (DataHolder)d;
				switch (item.getItemId()) {
					case (R.id.play):
						Toast.makeText(getActivity(), "Playing " + data.title, Toast.LENGTH_SHORT).show();
						break;
					case (R.id.queue):
						Toast.makeText(getActivity(), "Queueing " + data.title, Toast.LENGTH_SHORT).show();
						break;
				}
				return true;
			}
		};

		/** {@inheritDoc} */
		@Override
		public View newView(Context context, final Cursor cursor, ViewGroup parent) {
			final CardView view = (CardView) getActivity().getLayoutInflater().inflate(R.layout.list_item_movie_wide, parent, false);

			// find all elements
			final TextView titleView = (TextView) view.findViewById(R.id.title);
			final TextView subtitleView = (TextView) view.findViewById(R.id.genres);
			final TextView ratingView = (TextView) view.findViewById(R.id.rating);
			final TextView runtimeView = (TextView) view.findViewById(R.id.runtime);
			final ImageView imageView = (ImageView) view.findViewById(R.id.poster);
			final ImageView overflowView = (ImageView) view.findViewById(R.id.overflow);

			// setup icons
			overflowView.setImageDrawable(iconManager.getDrawable(R.string.ic_overflow, 12f, R.color.light_secondry_text));
			ratingView.setTypeface(iconManager.getTypeface());

			// setup view holder
			view.setTag(new ViewHolder(titleView, subtitleView, ratingView, runtimeView, imageView));

			// setup data holder
			view.setData(new DataHolder());

			// setup overflow menu
			view.setOverflowMenu(R.id.overflow, R.menu.movie_wide, menuItemClickListener);

			return view;
		}

		/** {@inheritDoc} */
		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			final CardView card = (CardView)view;
			final ViewHolder viewHolder = (ViewHolder)view.getTag();
			final DataHolder dataHolder = (DataHolder)card.getData();

			// load image
			try {
				final String url = hostUri + "/image/" + URLEncoder.encode(cursor.getString(MoviesQuery.THUMBNAIL), "UTF-8");
				Glide.load(url)
					.centerCrop()
					.animate(android.R.anim.fade_in)
					.into(viewHolder.imageView);

			} catch (UnsupportedEncodingException e) {
				Log.e(TAG, "Cannot encode " + cursor.getString(MoviesQuery.THUMBNAIL) + " from UTF-8.");
			}

			// set data
			dataHolder._id = cursor.getLong(MoviesQuery._ID);
			dataHolder.id = cursor.getString(MoviesQuery.ID);
			dataHolder.title = cursor.getString(MoviesQuery.TITLE);

			// fill up view content
			viewHolder.titleView.setText(dataHolder.title + " (" + cursor.getInt(MoviesQuery.YEAR) + ")");
			viewHolder.genresView.setText(cursor.getString(MoviesQuery.GENRES));
			viewHolder.ratingView.setText(iconManager.getStars(cursor.getFloat(MoviesQuery.RATING)));
			int runtime = cursor.getInt(MoviesQuery.RUNTIME);
			if (runtime == 0) {
				runtime = cursor.getInt(MoviesQuery.VIDEO_DURATION);
			}
			if (runtime > 0) {
				viewHolder.runtimeView.setVisibility(View.VISIBLE);
				viewHolder.runtimeView.setText(Math.round(runtime / 60) + " " + getResources().getString(R.string.minutes_short));
			} else {
				viewHolder.runtimeView.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * View holder for faster view access.
	 *
	 * @see <a href="http://developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder">Smooth Scrolling</a>
	 */
	private static class ViewHolder {
		final TextView titleView;
		final TextView genresView;
		final TextView ratingView;
		final TextView runtimeView;
		final ImageView imageView;

		private ViewHolder(TextView titleView, TextView genresView, TextView ratingView, TextView runtimeView, ImageView imageView) {
			this.titleView = titleView;
			this.genresView = genresView;
			this.ratingView = ratingView;
			this.runtimeView = runtimeView;
			this.imageView = imageView;
		}
	}

	/**
	 * Data holder used for on click events.
	 */
	public static class DataHolder {
		Long _id;
		String id;
		String title;

		public DataHolder() {
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

		final int _ID = 0;
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
