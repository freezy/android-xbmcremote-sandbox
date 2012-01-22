package org.xbmc.android.remotesandbox.ui.common;

import org.json.JSONException;
import org.xbmc.android.jsonrpc.NotificationManager;
import org.xbmc.android.jsonrpc.NotificationManager.NotificationObserver;
import org.xbmc.android.jsonrpc.api.AbstractModel;
import org.xbmc.android.jsonrpc.api.call.AudioLibrary;
import org.xbmc.android.jsonrpc.api.call.Player;
import org.xbmc.android.jsonrpc.api.model.AudioModel;
import org.xbmc.android.jsonrpc.api.model.AudioModel.SongDetails;
import org.xbmc.android.jsonrpc.api.model.PlayerModel;
import org.xbmc.android.jsonrpc.api.model.PlayerModel.PropertyValue;
import org.xbmc.android.jsonrpc.notification.FollowupCall;
import org.xbmc.android.jsonrpc.notification.PlayerEvent;
import org.xbmc.android.jsonrpc.notification.PlayerEvent.Pause;
import org.xbmc.android.jsonrpc.notification.PlayerEvent.Play;
import org.xbmc.android.jsonrpc.notification.PlayerEvent.Stop;
import org.xbmc.android.jsonrpc.notification.PlayerObserver;
import org.xbmc.android.remotesandbox.R;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class NowPlayingFragment extends Fragment {
	
	private static final String TAG = NowPlayingFragment.class.getSimpleName();
	
	private TextView mStatusText;
	private Button mConnectButton;
	private Chronometer mChronometer;
	
	private NotificationManager nm;
	private NotificationObserver mPlayerObserver;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View root = inflater.inflate(R.layout.fragment_nowplaying, null);
		mStatusText = (TextView)root.findViewById(R.id.nowplaying_status);
		mConnectButton = (Button)root.findViewById(R.id.nowplaying_connect);
		mChronometer = (Chronometer)root.findViewById(R.id.nowplaying_chronometer);
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
					public FollowupCall<? extends AbstractModel> onPlay(final Play notification) {
						final long onPlayReceived = System.currentTimeMillis();
						final int currentlyPlayingId = notification.data.item.id;
						final int currentPlayer = notification.data.player.playerId;
						final int currentType = notification.data.item.type;
						try {
							return new FollowupCall<PlayerModel.PropertyValue>(new Player.GetProperties(currentPlayer, PlayerModel.PropertyName.TIME)) {
								@Override
								@SuppressWarnings("unchecked")
								protected <U extends AbstractModel> FollowupCall<U> onResponse(PropertyValue response) {
									Log.i(TAG, "Setting clock to " + response.time.getMilliseconds() + "ms (" + SystemClock.elapsedRealtime() + ").");
									final long lag = System.currentTimeMillis() - onPlayReceived;
									mChronometer.setBase(SystemClock.elapsedRealtime() - response.time.getMilliseconds() + lag * 2);
									mChronometer.start();
									try {
										switch (currentType) {
											case PlayerEvent.Item.Type.SONG:
												return (FollowupCall<U>) new FollowupCall<AudioModel.SongDetails>(new AudioLibrary.GetSongDetails(currentlyPlayingId)) {
													@Override
													@SuppressWarnings("hiding")
													protected <U extends AbstractModel> FollowupCall<U> onResponse(SongDetails response) {
														mStatusText.setText(response.label);
														return null;
													}
												};
											case PlayerEvent.Item.Type.EPISODE:
											case PlayerEvent.Item.Type.MUSICVIDEO:
											default:
												return null;
										}		
									} catch (JSONException e) {
										return null;
									}
								}
								
							};
						} catch (JSONException e) {
							e.printStackTrace();
						}
						return null;
					}
					@Override
					public FollowupCall<? extends AbstractModel> onPause(Pause notification) {
						mChronometer.stop();
						return super.onPause(notification);
					}
					@Override
					public FollowupCall<? extends AbstractModel> onStop(Stop notification) {
						mChronometer.stop();
						mChronometer.setBase(SystemClock.elapsedRealtime());
						mStatusText.setText("");
						return super.onStop(notification);
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
	}
	
}
