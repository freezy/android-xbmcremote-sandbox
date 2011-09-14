package org.xbmc.android.providertest.ui;

import java.util.ArrayList;

import org.xbmc.android.providertest.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class DashboardFragment extends Fragment {
	
	private static final int HOME_ACTION_REMOTE = 0;
	private static final int HOME_ACTION_MUSIC = 1;
	private static final int HOME_ACTION_VIDEOS = 2;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View root = inflater.inflate(R.layout.fragment_dashboard, null);
		final GridView grid = (GridView)root.findViewById(R.id.dashboard_grid);
		setupDashboardItems(grid);
		return root;
	}
	
	private void setupDashboardItems(GridView menuGrid) {
		
		final ArrayList<HomeItem> homeItems = new ArrayList<HomeItem>();
		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
		if (prefs.getBoolean("setting_show_home_music", true))
			homeItems.add(new HomeItem(HOME_ACTION_MUSIC, R.drawable.home_ic_music, "Music", "Listen to"));
		
		menuGrid.setAdapter(new HomeAdapter(getActivity(), homeItems));
		menuGrid.setOnItemClickListener(getHomeMenuOnClickListener());
		menuGrid.setSelected(true);
		menuGrid.setSelection(0);		
	}
	
	private OnItemClickListener getHomeMenuOnClickListener() {
		return new OnItemClickListener() {

			public void onItemClick(AdapterView<?> listView, View v, int position, long ID) {
				HomeItem item = (HomeItem)listView.getAdapter().getItem(position);
				Intent intent = null;
				switch (item.ID) {
					case HOME_ACTION_REMOTE:
					case HOME_ACTION_MUSIC:
					case HOME_ACTION_VIDEOS:
						intent = new Intent(v.getContext(), MusicPagerActivity.class);
						break;
				}
				if (intent != null) {
					getActivity().startActivity(intent);
				}
			}
			
		};
	}
	
	private class HomeAdapter extends ArrayAdapter<HomeItem> {
		private Activity mActivity;
		HomeAdapter(Activity activity, ArrayList<HomeItem> items) {
			super(activity, R.layout.list_item_dashboard, items);
			mActivity = activity;
		}
		public View getView(int position, View convertView, ViewGroup parent) {
			View row;
			
			if (convertView == null) {
				LayoutInflater inflater = mActivity.getLayoutInflater();
				row = inflater.inflate(R.layout.list_item_dashboard, null);
			} else {
				row = convertView;
			}
			
			HomeItem item = this.getItem(position);
			final TextView supertitle = (TextView)row.findViewById(R.id.dashboard_supertitle);
			final TextView title = (TextView)row.findViewById(R.id.dashboard_title);
			final ImageView icon = (ImageView)row.findViewById(R.id.dashboard_icon);

			title.setText(item.title);
			supertitle.setText(item.subtitle);
			icon.setImageResource(item.icon);
			
			return row;
		}
	}
	
	private class HomeItem {
		public final int ID, icon;
		public final String title, subtitle;
		
		public HomeItem(int ID, int icon, String title, String subtitle) {
			this.ID = ID;
			this.icon = icon;
			this.title = title;
			this.subtitle = subtitle;
		}
	}

}
