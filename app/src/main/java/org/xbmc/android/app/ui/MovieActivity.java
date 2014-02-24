package org.xbmc.android.app.ui;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import org.xbmc.android.app.provider.VideoContract;
import org.xbmc.android.app.provider.VideoDatabase;
import org.xbmc.android.remotesandbox.R;

/**
 * @author freezy <freezy@xbmc.org>
 */
public class MovieActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {

	public static final String EXTRA_MOVIE_ID = "MOVIE_ID";

	private static final String TAG = MovieActivity.class.getSimpleName();

	@InjectView(R.id.title) protected TextView titleView;

	private Uri uri;

	public MovieActivity() {
		super(R.string.title_movies, R.layout.activity_movie);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ButterKnife.inject(this);

		final Bundle bundle = getIntent().getExtras();
		if (!bundle.containsKey(EXTRA_MOVIE_ID)) {
			throw new IllegalStateException("Must pass parameter " + EXTRA_MOVIE_ID + " to activity containing database ID of a movie.");
		}
		uri = ContentUris.withAppendedId(VideoContract.Movies.CONTENT_URI, bundle.getLong(EXTRA_MOVIE_ID));

		getSupportLoaderManager().initLoader(0, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(this, uri, MoviesQuery.PROJECTION, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		if (data.moveToNext()) {
			Log.d(TAG, "Loaded movie: " + data.getString(MoviesQuery.TITLE));
		} else {
			Log.e(TAG, "No movie found.");
		}

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
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
