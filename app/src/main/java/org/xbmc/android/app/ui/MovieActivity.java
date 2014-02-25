package org.xbmc.android.app.ui;

import android.content.ContentUris;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.InjectView;
import com.bumptech.glide.Glide;
import org.xbmc.android.app.manager.IconManager;
import org.xbmc.android.app.manager.ImageManager;
import org.xbmc.android.app.provider.VideoContract;
import org.xbmc.android.app.provider.VideoDatabase;
import org.xbmc.android.remotesandbox.R;

import javax.inject.Inject;
import java.text.DecimalFormat;

/**
 * @author freezy <freezy@xbmc.org>
 */
public class MovieActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {

	public static final String EXTRA_MOVIE_ID = "MOVIE_ID";

	private static final String TAG = MovieActivity.class.getSimpleName();
	private static final DecimalFormat FORMATTER = new DecimalFormat("###,###,###,###");

	@InjectView(R.id.title) protected TextView titleView;
	@InjectView(R.id.rating) protected TextView ratingView;
	@InjectView(R.id.rating_stars) protected TextView ratingStarsView;
	@InjectView(R.id.votes) protected TextView votesView;
	@InjectView(R.id.runtime) protected TextView runtimeView;
	@InjectView(R.id.genres) protected TextView genresView;
	@InjectView(R.id.poster) protected ImageView posterView;
	@InjectView(R.id.fanart) protected ImageView fanartView;

	@Inject protected ImageManager imageManager;
	@Inject protected IconManager iconManager;

	private Uri uri;

	public MovieActivity() {
		super(R.layout.activity_movie);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

/*		FadingActionBarHelper helper = new FadingActionBarHelper()
				.actionBarBackground(R.color.dark_bg)
//				.headerLayout(R.layout.header)
				.headerView(fanartView)
				.contentLayout(R.layout.activity_movie);
		setContentView(helper.createView(this));
		helper.initActionBar(this);
*/

		final Bundle bundle = getIntent().getExtras();
		if (!bundle.containsKey(EXTRA_MOVIE_ID)) {
			throw new IllegalStateException("Must pass parameter " + EXTRA_MOVIE_ID + " to activity containing database ID of a movie.");
		}

		ratingStarsView.setTypeface(iconManager.getTypeface());

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

			final Resources res = getResources();
			final String title = data.getString(MoviesQuery.TITLE);
			final float rating = data.getFloat(MoviesQuery.RATING);
			final int votes = Math.round(data.getInt(MoviesQuery.RUNTIME) / 60);
			titleView.setText(title);
			setTitle(title);

			// load poster
			Glide.load(imageManager.getUrl(data, MoviesQuery.THUMBNAIL))
					.centerCrop()
					.animate(android.R.anim.fade_in)
					.into(posterView);

			// load fanart
			Glide.load(imageManager.getUrl(data, MoviesQuery.FANART))
					.animate(android.R.anim.fade_in)
					.into(fanartView);

			ratingView.setText(String.valueOf(rating));
			ratingStarsView.setText(iconManager.getStars(rating));
			votesView.setText(data.getString(MoviesQuery.VOTES) + " " + res.getString(R.string.votes));
			runtimeView.setText(FORMATTER.format(votes) + " " + res.getString(R.string.minutes_short));
			genresView.setText(data.getString(MoviesQuery.GENRES));


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
				VideoContract.Movies.THUMBNAIL,
				VideoContract.Movies.FANART,
				VideoContract.Movies.VOTES

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
		final int FANART = 9;
		final int VOTES = 10;
	}
}
