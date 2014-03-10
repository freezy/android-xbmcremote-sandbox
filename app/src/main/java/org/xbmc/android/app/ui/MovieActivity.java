package org.xbmc.android.app.ui;

import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
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
import org.xbmc.android.app.ui.view.CardView;
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

	public static final int LOADER_MOVIE = 0x00;
	public static final int LOADER_CAST = 0x01;

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
	@InjectView(R.id.cast) protected GridView castView;

	@Inject protected ImageManager imageManager;
	@Inject protected IconManager iconManager;

	private String hostUri;
	private Uri movieUri;
	private Uri castUri;

	private String youtubeId;

	private CursorAdapter castAdapter;

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

		castAdapter = new CastAdapter(this);
		castView.setAdapter(castAdapter);

		final long movieId = bundle.getLong(EXTRA_MOVIE_ID);
		movieUri = ContentUris.withAppendedId(VideoContract.Movies.CONTENT_URI, movieId);
		castUri = VideoContract.MovieCast.buildMovieUri(String.valueOf(movieId));

		getSupportLoaderManager().initLoader(LOADER_MOVIE, null, this);
		getSupportLoaderManager().initLoader(LOADER_CAST, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		switch (id) {
			case LOADER_MOVIE:
				return new CursorLoader(this, movieUri, MoviesQuery.PROJECTION, null, null, null);
			case LOADER_CAST:
				return new CursorLoader(this, castUri, CastQuery.PROJECTION, null, null, null);
			default:
				return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		if (data != null && data.getCount() > 0) {
			switch (loader.getId()) {
				case LOADER_MOVIE:
					onMovieLoaded(data);
					break;
				case LOADER_CAST:
					onCastLoaded(data);
					break;
			}
		}
	}

	private void onMovieLoaded(Cursor data) {
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
	}

	private void onCastLoaded(Cursor data) {
		castAdapter.swapCursor(data);
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
	 * {@link android.support.v4.widget.CursorAdapter} that renders a {@link org.xbmc.android.app.ui.fragment.MovieCompactFragment.MoviesQuery}.
	 */
	private class CastAdapter extends CursorAdapter {

		public CastAdapter(Context context) {
			super(context, null, false);
		}

		/** {@inheritDoc} */
		@Override
		public View newView(Context context, final Cursor cursor, ViewGroup parent) {
			final CardView view = (CardView)getLayoutInflater().inflate(R.layout.list_item_actor_wide, parent, false);

			// setup data holder
			view.setData(new DataHolder());

			return view;
		}

		/** {@inheritDoc} */
		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			final CardView card = (CardView)view;
			final DataHolder dataHolder = (DataHolder)card.getData();

			final TextView nameView = (TextView) view.findViewById(R.id.name);
			final TextView roleView = (TextView) view.findViewById(R.id.role);
			final ImageView shotView = (ImageView) view.findViewById(R.id.shot);

			// load image
			Glide.load(imageManager.getUrl(cursor, CastQuery.THUMBNAIL))
				.centerCrop()
				.animate(android.R.anim.fade_in)
				.into(shotView);

			// set data
			dataHolder._id = cursor.getLong(CastQuery._ID);
			dataHolder.name = cursor.getString(CastQuery.NAME);

			// fill up view content
			nameView.setText(dataHolder.name);
			roleView.setText(cursor.getString(CastQuery.ROLE));
		}
	}

	/**
	 * Data holder used for on click events.
	 */
	public static class DataHolder {
		Long _id;
		String name;
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

	/**
	 * {@link org.xbmc.android.app.provider.VideoContract.Movies}
	 * query parameters.
	 */
	private interface CastQuery {

		String[] PROJECTION = {
			VideoDatabase.Tables.PEOPLE + "." + BaseColumns._ID,
			VideoContract.People.NAME,
			VideoContract.People.THUMBNAIL,
			VideoContract.MovieCast.ROLE
		};

		final int _ID = 0;
		final int NAME = 1;
		final int THUMBNAIL = 2;
		final int ROLE = 3;
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
