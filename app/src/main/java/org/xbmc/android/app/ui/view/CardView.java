package org.xbmc.android.app.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import butterknife.InjectView;
import butterknife.Optional;
import org.xbmc.android.remotesandbox.R;

/**
 *
 * @author freezy <freezy@xbmc.org>
 */
public class CardView extends FrameLayout {

	/**
	 * Popup Menu for overflow button
	 */
	protected PopupMenu popupMenu;

	/**
	 * Overflow
	 */
	@Optional
	@InjectView(R.id.overflow) protected ImageView overFlowButton;

	public CardView(Context context) {
		super(context);
	}

	public CardView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setOverflowMenu(int overflowResId, OnOverflowClickMenuListener listener) {
		
	}

	public interface OnOverflowClickMenuListener {
		public void onMenuItemClick(MenuItem item);
	}
}
