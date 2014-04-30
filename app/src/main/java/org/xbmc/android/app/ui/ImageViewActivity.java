/*
 * Copyright (C) 2005-2014 Team XBMC
 *     http://xbmc.org
 *
 * This Program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This Program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with XBMC Remote; see the file license.  If not, write to
 * the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.
 * http://www.gnu.org/copyleft/gpl.html
 */

package org.xbmc.android.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import butterknife.ButterKnife;
import butterknife.InjectView;
import com.bumptech.glide.Glide;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import org.xbmc.android.remotesandbox.R;
import org.xbmc.android.util.VolleyBasicAuthUrlLoader;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author freezy <freezy@xbmc.org>
 */
public class ImageViewActivity extends Activity {

	private static final String TAG = ImageViewActivity.class.getSimpleName();
	public final static String EXTRA_URL = "org.xbmc.android.app.EXTRA_URL";

	@InjectView(R.id.image)	ImageViewTouch imageViewTouch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);

		ButterKnife.inject(this);

		final String url = getIntent().getExtras().getString(EXTRA_URL);

		try {
			Glide.using(new VolleyBasicAuthUrlLoader.Factory())
				.load(new URL(url))
				.asIs()
				.fitCenter()
				.animate(android.R.anim.fade_in)
				.into(imageViewTouch);
		} catch (MalformedURLException e) {
			Log.e(TAG, e.toString());
		}
	}
}
