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

package org.xbmc.android.remotesandbox.ui.tablet;

import org.xbmc.android.jsonrpc.provider.AudioContract;
import org.xbmc.android.jsonrpc.provider.AudioDatabase.Tables;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.remotesandbox.ui.MusicPagerActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class DashboardFragment extends Fragment {
	
	//private final static String TAG = DashboardFragment.class.getSimpleName();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View root = inflater.inflate(R.layout.fragment_dashboard, null);
		setupLastestAlbums((DashboardBoxLayout) root.findViewById(R.id.dashboardbox_music));
		return root;
	}

	private void setupLastestAlbums(DashboardBoxLayout musicBox) {
		final ListView list = (ListView) musicBox.findViewById(R.id.dashboardbox_list);
		final Cursor c = getActivity().getContentResolver().query(AudioContract.Albums.CONTENT_URI,
				AlbumsQuery.PROJECTION, null, null, AudioContract.Albums.DEFAULT_SORT);
		list.setAdapter(new AlbumsAdapter(getActivity().getApplicationContext(), c));
		
		// on click open music pager for now (TODO use tablet optimized activity)
		musicBox.setOnTitlebarClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().startActivity(new Intent(v.getContext(), MusicPagerActivity.class));
			}
		});
	}

	/**
	 * {@link CursorAdapter} that renders a {@link AlbumsQuery}.
	 */
	private class AlbumsAdapter extends CursorAdapter {

		public AlbumsAdapter(Context context, Cursor cursor) {
			super(context, cursor, false);
		}

		/** {@inheritDoc} */
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return getActivity().getLayoutInflater().inflate(R.layout.list_item_threelabels, parent, false);
		}

		/** {@inheritDoc} */
		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			final TextView titleView = (TextView) view.findViewById(R.id.item_title);
			final TextView subtitleView = (TextView) view.findViewById(R.id.item_subtitle);
			final TextView subsubtitleView = (TextView) view.findViewById(R.id.item_subsubtitle);

			titleView.setText(cursor.getString(AlbumsQuery.TITLE));
			subtitleView.setText(cursor.getString(AlbumsQuery.ARTIST));
			subsubtitleView.setText(cursor.getString(AlbumsQuery.YEAR));
		}
	}

	/**
	 * {@link org.xbmc.android.jsonrpc.provider.AudioContract.Albums} query
	 * parameters.
	 */
	private interface AlbumsQuery {
		// int _TOKEN = 0x1;

		String[] PROJECTION = { Tables.ALBUMS + "." + BaseColumns._ID, AudioContract.Albums.ID,
				AudioContract.Albums.TITLE, AudioContract.Albums.YEAR, AudioContract.Artists.NAME };

		// int _ID = 0;
		// int ID = 1;
		int TITLE = 2;
		int YEAR = 3;
		int ARTIST = 4;
	}
	
}
