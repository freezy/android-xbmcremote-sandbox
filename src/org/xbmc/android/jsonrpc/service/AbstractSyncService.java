package org.xbmc.android.jsonrpc.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;

public abstract class AbstractSyncService extends Service {

	public static final String HOST = "192.168.0.100";
	public static final String URL = "http://" + HOST + ":8080/jsonrpc";

	public static final int STATUS_RUNNING = 0x1;
	public static final int STATUS_ERROR = 0x2;
	public static final int STATUS_FINISHED = 0x3;

	protected ResultReceiver mReceiver = null;

	public void onError(String message) {
		if (mReceiver != null) {
			// Pass back error to surface listener
			final Bundle bundle = new Bundle();
			bundle.putString(Intent.EXTRA_TEXT, message);
			mReceiver.send(STATUS_ERROR, bundle);
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public interface RefreshObserver {
		public void onRefreshed();
	}

}
