package org.xbmc.android.app.ui.view;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.util.AttributeSet;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

/**
 *
 * @author freezy <freezy@xbmc.org>
 */
public class CardView extends RelativeLayout {

	private Object data;

	public CardView(Context context) {
		super(context);
	}

	public CardView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setOverflowMenu(int overflowResId, int menuResId, final OnMenuItemClickListener listener) {
		final View overflow = findViewById(overflowResId);
		if (overflow == null) {
			throw new IllegalArgumentException("Cannot find overflow menu in view. Make sure the view contains an id " + overflowResId + ".");
		}

		final PopupMenu popup = new PopupMenu(getContext(), overflow);
		final MenuInflater inflater = popup.getMenuInflater();
		inflater.inflate(menuResId, popup.getMenu());
		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem menuItem) {
				return listener.onMenuItemClick(menuItem, data);
			}
		});

		overflow.setClickable(true);
		overflow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popup.show();
			}
		});
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public interface OnMenuItemClickListener {
		public boolean onMenuItemClick(MenuItem item, Object data);
	}
}
