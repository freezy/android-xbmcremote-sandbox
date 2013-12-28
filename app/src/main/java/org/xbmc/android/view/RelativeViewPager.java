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

package org.xbmc.android.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * A view pager that relies on the currently displayed fragment to deliver the
 * next or previous page, as opposed to a global list of fragments.
 *
 * @author freezy <freezy@xbmc.org>
 */
public class RelativeViewPager extends ViewPager {

	private final static String TAG = RelativeViewPager.class.getSimpleName();

	private RelativePagerAdapter adapter;

	private boolean enabledPrev = true;
	private boolean enabledNext = true;
	private float x = -1;

	private int currPosition = 0;
	private OnRelativePageChangeListener pageChangeListener;

	public RelativeViewPager(Context context) {
		super(context);
	}

	public RelativeViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private void initRelativeViewPager() {

		setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageSelected(int position) {
				currPosition = position;
			}

			@Override
			public void onPageScrollStateChanged(final int state) {

				if (state == ViewPager.SCROLL_STATE_IDLE) {
					if (adapter == null) {
						return;
					}
					if (currPosition == RelativePagerAdapter.PAGE_POSITION_LEFT) {
						adapter.move(1);
					} else if (currPosition == RelativePagerAdapter.PAGE_POSITION_RIGHT) {
						adapter.move(-1);
					}
					onPageActive();
				}
			}
		});
		onPageActive();
	}

	private void onPageActive() {
		setCurrentItem(RelativePagerAdapter.PAGE_POSITION_CENTER, false);

		setPagingNextEnabled(adapter.getCurrentFragment().hasNext());
		setPagingPrevEnabled(adapter.getCurrentFragment().hasPrev());

		adapter.getCurrentFragment().onPageActive();
		if (pageChangeListener != null) {
			pageChangeListener.onPageSelected(adapter.getCurrentFragment());
		}
	}

	@Override
	public final void setCurrentItem(final int item) {
		if (item != RelativePagerAdapter.PAGE_POSITION_CENTER) {
		//	throw new RuntimeException("Cannot change page index unless its 1.");
		}
		super.setCurrentItem(item);
	}

	@Override
	public void setAdapter(final PagerAdapter adapter) {
		if (adapter instanceof RelativePagerAdapter) {
			super.setAdapter(adapter);

			this.adapter = (RelativePagerAdapter)adapter;
			this.adapter.setPager(this);

			initRelativeViewPager();

		} else {
			throw new IllegalArgumentException("Adapter must be an instance of RelativePagerAdapter.");
		}
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

	public void setOnRelativePageChangeListener(OnRelativePageChangeListener listener) {
		pageChangeListener = listener;
	}

	public interface OnRelativePageChangeListener {
		public void onPageSelected(RelativePagerFragment fragment);
	}
}