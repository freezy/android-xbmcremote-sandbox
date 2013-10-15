package org.xbmc.android.remotesandbox.ui.cards;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.bumptech.glide.Glide;
import org.xbmc.android.jsonrpc.api.AbstractCall;
import org.xbmc.android.jsonrpc.api.call.VideoLibrary;
import org.xbmc.android.jsonrpc.api.model.VideoModel;
import org.xbmc.android.jsonrpc.config.HostConfig;
import org.xbmc.android.jsonrpc.io.ApiCallback;
import org.xbmc.android.jsonrpc.io.ConnectionManager;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.remotesandbox.ui.base.IconHelper;

import java.net.URLEncoder;
import java.util.ArrayList;

public class MovieActivity extends Activity {

	private final String TAG = MovieActivity.class.getSimpleName();

	//private static final String IP = "178.15.169.97";
	private static final String IP = "192.168.0.100";
	private Typeface mSymbolFont;

	ArrayList<VideoModel.MovieDetail> mMovies;
	ImageAdapter mImageAdapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movies);

		mImageAdapter = new ImageAdapter(this);
		mSymbolFont =  Typeface.createFromAsset(getAssets(), "symbols.ttf");
		final ListView listView = (ListView) findViewById(R.id.list_movies);
		listView.setAdapter(mImageAdapter);


		final ConnectionManager cm = new ConnectionManager(getApplicationContext(), new HostConfig(IP));
		//cm.setPreferHTTP();

		final VideoLibrary.GetMovies call = new VideoLibrary.GetMovies(
				VideoModel.MovieFields.THUMBNAIL, VideoModel.MovieFields.YEAR,
				VideoModel.MovieFields.RATING, VideoModel.MovieFields.GENRE,
				VideoModel.MovieFields.RUNTIME
		);
		final ApiCallback<VideoModel.MovieDetail> callback = new ApiCallback<VideoModel.MovieDetail>() {
			@Override
			public void onResponse(AbstractCall<VideoModel.MovieDetail> call) {
				Log.d(TAG, "Received movies after " + call.getTime() + " ms.");
				mMovies = call.getResults();
				mImageAdapter.notifyDataSetChanged();
			}

			@Override
			public void onError(int code, String message, String hint) {
				Log.e(TAG, "ERROR: " + message);
			}
		};
		cm.call(call, callback);
	}


	public class ImageAdapter extends BaseAdapter {
		private Context mContext;

		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return mMovies == null ? 0 : mMovies.size();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View recycled, ViewGroup container) {

			final RelativeLayout imageView;
			final ViewHolder holder;
			if (recycled == null) {  // if it's not recycled, initialize some attributes
				imageView = (RelativeLayout)getLayoutInflater().inflate(R.layout.list_item_movie_wide, null);
				holder = new ViewHolder();
				holder.poster = (ImageView)imageView.findViewById(R.id.list_movie_poster);
				holder.title = ((TextView)imageView.findViewById(R.id.title_movie));
				holder.subtitle = ((TextView)imageView.findViewById(R.id.genres_movie));
				holder.subsubtitle = ((TextView)imageView.findViewById(R.id.runtime_movie));
				holder.ratingStars = ((TextView)imageView.findViewById(R.id.rating_movie));
				holder.position = position;
				imageView.setTag(holder);
				holder.ratingStars.setTypeface(mSymbolFont);
			} else {
				imageView = (RelativeLayout) recycled;
				holder = (ViewHolder)imageView.getTag();
			}

			final VideoModel.MovieDetail movie = mMovies.get(position);
			final String url = "http://" + IP + ":8080/image/" + URLEncoder.encode(movie.thumbnail);
			final String title = movie.label + " (" + movie.year + ")";
			final String genre = movie.genre.toString();
			holder.title.setText(title);
			holder.subtitle.setText(genre.substring(1, genre.length() - 1));
			holder.ratingStars.setText(IconHelper.makeRatingStars(movie.rating));
			if (movie.runtime != null) {
				holder.subsubtitle.setText((movie.runtime / 60) + " min");
			}

			Glide.load(url)
					.centerCrop()
					.animate(android.R.anim.fade_in)
					.into(holder.poster);

			return imageView;
		}

	}

	static class ViewHolder {
		TextView title;
		TextView subtitle;
		TextView subsubtitle;
		TextView ratingStars;
		ImageView poster;
		int position;
	}
}
