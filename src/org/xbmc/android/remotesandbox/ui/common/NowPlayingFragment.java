package org.xbmc.android.remotesandbox.ui.common;

import org.json.JSONException;
import org.xbmc.android.jsonrpc.NotificationManager;
import org.xbmc.android.jsonrpc.NotificationManager.NotificationObserver;
import org.xbmc.android.jsonrpc.api.AbstractModel;
import org.xbmc.android.jsonrpc.api.call.AudioLibrary;
import org.xbmc.android.jsonrpc.api.model.AudioModel;
import org.xbmc.android.jsonrpc.api.model.AudioModel.SongDetails;
import org.xbmc.android.jsonrpc.notification.FollowupCall;
import org.xbmc.android.jsonrpc.notification.PlayerEvent.Play;
import org.xbmc.android.jsonrpc.notification.PlayerObserver;
import org.xbmc.android.remotesandbox.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class NowPlayingFragment extends Fragment {
	
	private static final String TAG = NowPlayingFragment.class.getSimpleName();
	
	private TextView mStatusText;
	private Button mConnectButton;
	private Button mDisconnectButton;
	
	private NotificationManager nm;
	private NotificationObserver mPlayerObserver;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View root = inflater.inflate(R.layout.fragment_nowplaying, null);
		mStatusText = (TextView)root.findViewById(R.id.nowplaying_status);
		mConnectButton = (Button)root.findViewById(R.id.nowplaying_connect);
		mDisconnectButton = (Button)root.findViewById(R.id.nowplaying_disconnect);
		setup();
		return root;
	}
	
	private void setup() {
		nm = new NotificationManager(getActivity().getApplicationContext());
		mPlayerObserver = new NotificationObserver() {
			@Override
			public PlayerObserver getPlayerObserver() {
				return new PlayerObserver() {
					@Override
					public FollowupCall<AudioModel.SongDetails> onPlay(final Play notification) {
						mStatusText.setText(notification.toString());
						try {
							return new FollowupCall<AudioModel.SongDetails>(new AudioLibrary.GetSongDetails(notification.data.item.id)) {
								@Override
								protected <U extends AbstractModel> FollowupCall<U> onResponse(SongDetails response) {
									Log.i(TAG, "Got song details: " + response.label);
									return null;
								}
							};
						} catch (JSONException e) {
							return null;
						}
					}
				};
			}
		};
		
		mConnectButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				nm.registerObserver(mPlayerObserver);
				mStatusText.setText("Connected.");
			}
		});

		mDisconnectButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				nm.unregisterObserver(mPlayerObserver);
				mStatusText.setText("Disconnected.");
			}
		});
		
	}
}
