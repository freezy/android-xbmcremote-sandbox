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
import android.widget.*;
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
import org.xbmc.android.app.ui.MoviesActivity;
import org.xbmc.android.app.ui.view.CardView;
import org.xbmc.android.remotesandbox.R;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static org.xbmc.android.app.ui.fragment.MovieListFragment.DataHolder;

/**
 * Lists albums in grid using the compact view.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class MovieCompactFragment extends GridFragment implements LoaderManager.LoaderCallbacks<Cursor> {

	private final static String TAG = MovieCompactFragment.class.getSimpleName();

	@Inject protected EventBus bus;
	@Inject protected HostManager hostManager;
	@Inject IconManager iconManager;

	@InjectView(R.id.see_more) RelativeLayout seeMoreBtn;
	@InjectView(android.R.id.empty) TextView emptyView;

	private CursorAdapter adapter;
	private String hostUri;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_home_grid, container);
		ButterKnife.inject(this, view);

		emptyView.setText(R.string.empty_movies);
		seeMoreBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), MoviesActivity.class));
			}
		});
		return view;
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

	@Override
	public void onGridItemClick(GridView g, View view, int position, long id) {

		final CardView card = (CardView)view;
		final Intent intent = new Intent(getActivity(), MovieActivity.class);
		final DataHolder dataHolder = (DataHolder)card.getData();
		intent.putExtra(MovieActivity.EXTRA_MOVIE_ID, dataHolder._id);
		startActivity(intent);
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
		final String limit = VideoContract.Movies.sortLatest(getResources().getInteger(R.integer.home_numrows));
		return new CursorLoader(getActivity(), VideoContract.Movies.CONTENT_URI, MoviesQuery.PROJECTION, null, null, limit);
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
			final CardView view = (CardView)getActivity().getLayoutInflater().inflate(R.layout.list_item_movie_compact, parent, false);

			// find all elements
			final TextView titleView = (TextView) view.findViewById(R.id.title);
			final TextView subtitleView = (TextView) view.findViewById(R.id.genres);
			final ImageView imageView = (ImageView) view.findViewById(R.id.poster);
			final ImageView overflowView = (ImageView) view.findViewById(R.id.overflow);

			// setup icons
			overflowView.setImageDrawable(iconManager.getDrawable(R.string.ic_overflow, 12f, R.color.light_secondry_text));

			// setup view holder
			view.setTag(new ViewHolder(titleView, subtitleView, imageView));

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
	}

	/**
	 * View holder for faster view access.
	 *
	 * @see <a href="http://developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder">Smooth Scrolling</a>
	 */
	private static class ViewHolder {
		final TextView titleView;
		final TextView genresView;
		final ImageView imageView;

		private ViewHolder(TextView titleView, TextView genresView, ImageView imageView) {
			this.titleView = titleView;
			this.genresView = genresView;
			this.imageView = imageView;
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
				VideoContract.Movies.THUMBNAIL
		};

		final int _ID = 0;
		final int ID = 1;
		final int TITLE = 2;
		final int YEAR = 3;
		final int GENRES = 4;
		final int THUMBNAIL = 5;
	}

}
