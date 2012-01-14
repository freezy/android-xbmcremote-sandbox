package org.xbmc.android.remotesandbox.ui.common;

import org.xbmc.android.jsonrpc.NotificationManager;
import org.xbmc.android.jsonrpc.NotificationManager.NotificationObserver;
import org.xbmc.android.jsonrpc.notification.AbstractEvent;
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
			public void handleNotification(AbstractEvent notification) {
				if (notification != null) {
					mStatusText.setText(notification.toString());
				}
				Log.i(TAG, "Received event: " + notification);
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
