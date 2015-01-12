package org.xbmc.android.app.manager;

import android.database.Cursor;
import de.greenrobot.event.EventBus;
import org.xbmc.android.app.event.HostSwitched;
import org.xbmc.android.app.injection.Injector;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author freezy <freezy@xbmc.org>
 */
public class ImageManager {

	@Inject protected HostManager hostManager;
	@Inject protected EventBus bus;

	private String hostUri, hostUriWithUserInfo;

	public ImageManager() {
		Injector.inject(this);
		bus.register(this);
		hostUri = hostManager.getActiveUri();
		hostUriWithUserInfo = hostManager.getActiveUri(true);
	}

	public void onEvent(HostSwitched event) {
		hostUri = event.getHost().getUri();
		hostUriWithUserInfo = event.getHost().getUri(true);
	}

	/**
	 * Returns the absolute URL of an image.
	 * @param cursor Database row
	 * @param field Field name containing image url
	 * @return Image URL
	 */
	public String getUrl(Cursor cursor, int field) {
		return getUrl(cursor, field, hostUri);
	}

	/**
	 * Returns the absolute URL of an image, including the user credentials if requested.
	 * @param cursor Database row
	 * @param field Field name containing image url
	 * @param cursor Database row
	 * @param includeUserInfo Whether to include credentials in the URI
	 * @return Image URL
	 */
	public String getUrl(Cursor cursor, int field, boolean includeUserInfo) {
		return getUrl(cursor, field, includeUserInfo? hostUriWithUserInfo : hostUri);
	}

	private String getUrl(Cursor cursor, int field, String hostUri) {
		try {
			final String fieldValue = cursor.getString(field);
			if (fieldValue == null) {
				return null;
			}
			return hostUri + "/image/" + URLEncoder.encode(fieldValue, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
}
