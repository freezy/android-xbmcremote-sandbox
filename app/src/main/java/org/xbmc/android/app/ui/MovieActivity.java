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

package org.xbmc.android.app.ui;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.bumptech.glide.Glide;
import de.greenrobot.event.EventBus;
import org.xbmc.android.app.event.DataItemSynced;
import org.xbmc.android.app.event.HostSwitched;
import org.xbmc.android.app.injection.Injector;
import org.xbmc.android.app.manager.HostManager;
import org.xbmc.android.app.provider.VideoContract;
import org.xbmc.android.app.provider.VideoDatabase;
import org.xbmc.android.remotesandbox.R;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Movie landing page.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class MovieActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {

	final private static String TAG = MovieActivity.class.getSimpleName();

	@Inject protected EventBus bus;
	@Inject protected HostManager hostManager;

	@InjectView(R.id.list_movies) protected ListView list;

	private String hostUri;
	private CursorAdapter adapter;

	public MovieActivity() {
		super(R.string.title_movies, R.string.ic_movie, R.layout.activity_movies);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ButterKnife.inject(this);

		bus.register(this);
		Injector.inject(this);
		hostUri = hostManager.getActiveUri();

		adapter = new MoviesAdapter(getApplicationContext());
		list.setAdapter(adapter);
		getSupportLoaderManager().initLoader(0, null, this);
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
			getSupportLoaderManager().restartLoader(0, null, this);
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
		getSupportLoaderManager().restartLoader(0, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(this, VideoContract.Movies.CONTENT_URI, MoviesQuery.PROJECTION, null, null, VideoContract.Movies.DEFAULT_SORT);
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
			final View view = getLayoutInflater().inflate(R.layout.list_item_movie_compact, parent, false);
			((ImageButton)view.findViewById(R.id.optionButton)).setImageDrawable(IconHelper.getDrawable(MovieActivity.this, R.string.ic_overflow, 20f, R.color.light_secondry_text));
			return view;
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
			subtitleView.setText(cursor.getString(MoviesQuery.GENRES));
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
				VideoContract.Movies.GENRES,
				VideoContract.Movies.THUMBNAIL
		};

		//final int _ID = 0;
		final int ID = 1;
		final int TITLE = 2;
		final int GENRES = 3;
		final int THUMBNAIL = 4;
	}
}
