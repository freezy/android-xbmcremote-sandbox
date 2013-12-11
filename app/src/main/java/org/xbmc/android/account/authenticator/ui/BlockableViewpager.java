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