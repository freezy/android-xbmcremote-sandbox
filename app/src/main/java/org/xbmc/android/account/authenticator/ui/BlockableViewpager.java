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

package org.xbmc.android.account.authenticator.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class BlockableViewpager extends ViewPager {

	private boolean enabledPrev = true;
	private boolean enabledNext = true;
	private float x = -1;

	public BlockableViewpager(Context context) {
		super(context);
	}

	public BlockableViewpager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!enabledPrev && !enabledNext) {
			return true;
		}
		if (enabledPrev && enabledNext) {
			return super.onTouchEvent(event);
		}
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			x = event.getX();
		}
		if (x < 0) {
			return super.onTouchEvent(event);
		}
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (!enabledPrev && x < event.getX()) {
				return true;
			}
			if (!enabledNext && x > event.getX()) {
				return true;
			}
		}
		//Log.d("pager", "Event: " + event.getAction() + " (" + event.getX() + ")");
		return super.onTouchEvent(event);
	}

	public void setPagingPrevEnabled(boolean enabled) {
		this.enabledPrev = enabled;
	}

	public void setPagingNextEnabled(boolean enabled) {
		this.enabledNext = enabled;
	}
}