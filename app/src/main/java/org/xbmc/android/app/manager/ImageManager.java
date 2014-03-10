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

	private String host;

	public ImageManager() {
		Injector.inject(this);
		bus.register(this);
		host = hostManager.getActiveUri();
	}

	public void onEvent(HostSwitched event) {
		host = event.getHost().getUri();
	}

	/**
	 * Returns the absolute URL of an image.
	 * @param cursor Database row
	 * @param field Field name containing image url
	 * @return Image URL
	 */
	public String getUrl(Cursor cursor, int field) {
		try {
			final String fieldValue = cursor.getString(field);
			if (fieldValue == null) {
				return null;
			}
			return host + "/image/" + URLEncoder.encode(fieldValue, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
}
