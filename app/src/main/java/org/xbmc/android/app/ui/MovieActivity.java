package org.xbmc.android.app.ui;

import android.content.ContentUris;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.InjectView;
import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import org.xbmc.android.app.manager.IconManager;
import org.xbmc.android.app.manager.ImageManager;
import org.xbmc.android.app.provider.VideoContract;
import org.xbmc.android.app.provider.VideoDatabase;
import org.xbmc.android.remotesandbox.R;

import javax.inject.Inject;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author freezy <freezy@xbmc.org>
 */
public class MovieActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor>, YouTubePlayer.OnInitializedListener {

	public static final String EXTRA_MOVIE_ID = "MOVIE_ID";

	private static final String TAG = MovieActivity.class.getSimpleName();
	private static final DecimalFormat FORMATTER = new DecimalFormat("###,###,###,###");
	private static final Pattern PATTERN_YOUTUBE = Pattern.compile("plugin://plugin\\.video\\.youtube.*videoid=(\\w+)");

	@InjectView(R.id.title) protected TextView titleView;
	@InjectView(R.id.rating) protected TextView ratingView;
	@InjectView(R.id.rating_stars) protected TextView ratingStarsView;
	@InjectView(R.id.votes) protected TextView votesView;
	@InjectView(R.id.runtime) protected TextView runtimeView;
	@InjectView(R.id.genres) protected TextView genresView;
	@InjectView(R.id.poster) protected ImageView posterView;
	@InjectView(R.id.fanart) protected ImageView fanartView;
	@InjectView(R.id.plot) protected TextView plotView;

	@Inject protected ImageManager imageManager;
	@Inject protected IconManager iconManager;

	private Uri uri;
	private String youtubeId;

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
		if (data != null && data.getCount() > 0) {
			data.moveToFirst();
			final Resources res = getResources();
			final String title = data.getString(MoviesQuery.TITLE);
			final String year = data.getString(MoviesQuery.YEAR);
			final float rating = data.getFloat(MoviesQuery.RATING);
			final int votes = Math.round(data.getInt(MoviesQuery.RUNTIME) / 60);
			titleView.setText(title + (year != null ? " (" + year + ")" : ""));
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
			plotView.setText(data.getString(MoviesQuery.PLOT));

			// trailer
			final FragmentManager fm = getSupportFragmentManager();
			final YouTubePlayerSupportFragment youtubeFragment = (YouTubePlayerSupportFragment) fm.findFragmentById(R.id.youtube);

			final Matcher matcher = PATTERN_YOUTUBE.matcher(data.getString(MoviesQuery.TRAILER));
			if (matcher.find()) {
				youtubeId = matcher.group(1);
				Log.d(TAG, "Found youtube trailer " + youtubeId);
				youtubeFragment.initialize("AIzaSyDdKyESE56AQvphuVCU0hgpittw9kWCYwQ", this);
			} else {
				//fm.beginTransaction().hide(youtubeFragment).commit();
			}

		} else {
			Log.e(TAG, "No movie found.");
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
	}

	@Override
	public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean b) {
		Log.d(TAG, "Youtube play initialized.");
		player.cueVideo(youtubeId);
	}

	@Override
	public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
		if (errorReason.isUserRecoverableError()) {
			errorReason.getErrorDialog(this, 1).show();
		} else {
			String errorMessage = String.format(getString(R.string.youtube_error), errorReason.toString());
			Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
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
				VideoContract.Movies.THUMBNAIL,
				VideoContract.Movies.FANART,
				VideoContract.Movies.VOTES,
				VideoContract.Movies.PLOT,
				VideoContract.Movies.TRAILER

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
		final int PLOT = 11;
		final int TRAILER = 12;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
