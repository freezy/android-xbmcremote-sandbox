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

package org.xbmc.android.jsonrpc.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;
import org.xbmc.android.jsonrpc.api.AudioLibraryAPI;
import org.xbmc.android.jsonrpc.io.RemoteExecutor;
import org.xbmc.android.jsonrpc.io.audio.AlbumHandler;
import org.xbmc.android.jsonrpc.io.audio.ArtistHandler;
import org.xbmc.android.jsonrpc.provider.AudioProvider;

import android.app.IntentService;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.format.DateUtils;
import android.util.Log;

/**
 * Background {@link Service} that synchronizes data living in
 * {@link AudioProvider}.
 * <p>
 * This class, along with the other ones in this package was closely inspired by
 * Google's official iosched app, see http://code.google.com/p/iosched/
 * 
 * @author freezy@xbmc.org
 */
public class AudioSyncService extends IntentService {

	private static final String TAG = AudioSyncService.class.getSimpleName();

	private static final String URL = "http://192.168.0.100:8080/jsonrpc";

	public static final String EXTRA_STATUS_RECEIVER = "org.xbmc.android.jsonprc.extra.STATUS_RECEIVER";

	public static final int STATUS_RUNNING = 0x1;
	public static final int STATUS_ERROR = 0x2;
	public static final int STATUS_FINISHED = 0x3;

	private static final int SECOND_IN_MILLIS = (int) DateUtils.SECOND_IN_MILLIS;

	private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
	private static final String ENCODING_GZIP = "gzip";

	private RemoteExecutor mRemoteExecutor;

	public AudioSyncService() {
		super(TAG);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "Starting onCreate()...");
		final long start = System.currentTimeMillis();

		final HttpClient httpClient = getHttpClient(this);
		final ContentResolver resolver = getContentResolver();

		mRemoteExecutor = new RemoteExecutor(httpClient, resolver);
		Log.d(TAG, "onCreate() done in " + (System.currentTimeMillis() - start) + "ms.");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		final long start = System.currentTimeMillis();
		Log.d(TAG, "Starting onHandleIntent(intent=" + intent.toString() + ")...");

		final ResultReceiver receiver = intent.getParcelableExtra(EXTRA_STATUS_RECEIVER);
		if (receiver != null)
			receiver.send(STATUS_RUNNING, Bundle.EMPTY);

		try {
			final long startRemote = System.currentTimeMillis();
			final AudioLibraryAPI audiolib = new AudioLibraryAPI();

			final String[] albumFields = { AudioLibraryAPI.AlbumFields.TITLE, AudioLibraryAPI.AlbumFields.ARTISTID,
					AudioLibraryAPI.AlbumFields.YEAR };

			mRemoteExecutor.executePost(URL, audiolib.getArtists(false, null, null), new ArtistHandler());
			mRemoteExecutor.executePost(URL, audiolib.getAlbums(null, null, albumFields), new AlbumHandler());

			Log.i(TAG, "All done, remote sync took " + (System.currentTimeMillis() - startRemote) + "ms.");

		} catch (Exception e) {
			Log.e(TAG, "Problem while syncing", e);

			if (receiver != null) {
				// Pass back error to surface listener
				final Bundle bundle = new Bundle();
				bundle.putString(Intent.EXTRA_TEXT, e.toString());
				receiver.send(STATUS_ERROR, bundle);
			}
		}

		// Announce success to any surface listener
		if (receiver != null)
			receiver.send(STATUS_FINISHED, Bundle.EMPTY);
		Log.d(TAG, "Sync finished!");
		Log.d(TAG, "onHandleIntent() done in " + (System.currentTimeMillis() - start) + "ms.");
	}

	/**
	 * Generate and return a {@link HttpClient} configured for general use,
	 * including setting an application-specific user-agent string.
	 */
	public static HttpClient getHttpClient(Context context) {
		final HttpParams params = new BasicHttpParams();

		// Use optimistic timeouts for wifi networks
		HttpConnectionParams.setConnectionTimeout(params, 5 * SECOND_IN_MILLIS);
		HttpConnectionParams.setSoTimeout(params, 5 * SECOND_IN_MILLIS);

		HttpConnectionParams.setSocketBufferSize(params, 8192);
		HttpProtocolParams.setUserAgent(params, buildUserAgent(context));

		final DefaultHttpClient client = new DefaultHttpClient(params);

		client.addRequestInterceptor(new HttpRequestInterceptor() {
			public void process(HttpRequest request, HttpContext context) {
				// Add header to accept gzip content
				if (!request.containsHeader(HEADER_ACCEPT_ENCODING)) {
					request.addHeader(HEADER_ACCEPT_ENCODING, ENCODING_GZIP);
				}
			}
		});

		client.addResponseInterceptor(new HttpResponseInterceptor() {
			public void process(HttpResponse response, HttpContext context) {
				// Inflate any responses compressed with gzip
				final HttpEntity entity = response.getEntity();
				final Header encoding = entity.getContentEncoding();
				if (encoding != null) {
					for (HeaderElement element : encoding.getElements()) {
						if (element.getName().equalsIgnoreCase(ENCODING_GZIP)) {
							response.setEntity(new InflatingEntity(response.getEntity()));
							break;
						}
					}
				}
			}
		});

		return client;
	}

	/**
	 * Build and return a user-agent string that can identify this application
	 * to remote servers. Contains the package name and version code.
	 */
	private static String buildUserAgent(Context context) {
		try {
			final PackageManager manager = context.getPackageManager();
			final PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);

			// Some APIs require "(gzip)" in the user-agent string.
			return info.packageName + "/" + info.versionName + " (" + info.versionCode + ") (gzip)";
		} catch (NameNotFoundException e) {
			return null;
		}
	}

	/**
	 * Simple {@link HttpEntityWrapper} that inflates the wrapped
	 * {@link HttpEntity} by passing it through {@link GZIPInputStream}.
	 */
	private static class InflatingEntity extends HttpEntityWrapper {
		public InflatingEntity(HttpEntity wrapped) {
			super(wrapped);
		}

		@Override
		public InputStream getContent() throws IOException {
			return new GZIPInputStream(wrappedEntity.getContent());
		}

		@Override
		public long getContentLength() {
			return -1;
		}
	}
	
	public interface RefreshObserver {
		public void onRefreshed();
	}
	
}
