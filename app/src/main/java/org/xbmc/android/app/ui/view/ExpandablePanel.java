package org.xbmc.android.app.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.xbmc.android.app.injection.Injector;
import org.xbmc.android.app.manager.IconManager;
import org.xbmc.android.remotesandbox.R;

import javax.inject.Inject;

/**
 * An expandable view.
 *
 * @see <a href="http://krishnalalstha.wordpress.com/2013/02/19/android-expandable-layout">Blog</a>
 * @author krishnalalstha
 * @author freezy <freezy@xbmc.org>
 */
public class ExpandablePanel extends RelativeLayout {

	@Inject IconManager iconManager;

	private final int handleId;
	private final int contentId;

	// Contains references to the handle and content views
	private TextView handle;
	private View content;

	/** Does the panel start expanded?
	 */
	private boolean expanded = false;

	/** The height of the content when collapsed
	 */
	private int collapsedHeight = 0;

	/** The full expanded height of the content (calculated)
	 */
	private int contentHeight = 0;

	/** How long the expand animation takes
	 */
	private int animationDuration = 0;

	private String handleIconExpanded;
	private String handleIconCollapsed;

	// Listener that gets fired onExpand and onCollapse
	private OnExpandListener listener;

	public ExpandablePanel(Context context) {
		this(context, null);
	}

	/**
	 * The constructor simply validates the arguments being passed in and
	 * sets the global variables accordingly. Required attributes are
	 * 'handle' and 'content'
	 */
	public ExpandablePanel(Context context, AttributeSet attrs) {
		super(context, attrs);

		if (!isInEditMode()) {
			Injector.inject(this);
		}

		listener = new DefaultOnExpandListener();

		final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpandablePanel, 0, 0);

		// How high the content should be in "collapsed" state
		collapsedHeight = (int) a.getDimension(R.styleable.ExpandablePanel_collapsedHeight, 0.0f);

		// How long the animation should take
		animationDuration = a.getInteger(R.styleable.ExpandablePanel_animationDuration, 500);

		int handleId = a.getResourceId(R.styleable.ExpandablePanel_handle, 0);

		if (handleId == 0) {
			throw new IllegalArgumentException("The handle attribute is required and must refer to a valid child.");
		}

		int contentId = a.getResourceId(R.styleable.ExpandablePanel_content, 0);
		if (contentId == 0) {
			throw new IllegalArgumentException("The content attribute is required and must refer to a valid child.");
		}

		this.handleId = handleId;
		this.contentId = contentId;

		handleIconExpanded = a.getString(R.styleable.ExpandablePanel_handleIconExpanded);
		handleIconCollapsed = a.getString(R.styleable.ExpandablePanel_handleIconCollapsed);

		a.recycle();
	}

	// Some public setters for manipulating the
	// ExpandablePanel programmatically
	public void setOnExpandListener(OnExpandListener listener) {
		this.listener = listener;
	}

	public void setCollapsedHeight(int collapsedHeight) {
		this.collapsedHeight = collapsedHeight;
	}

	public void setAnimationDuration(int animationDuration) {
		this.animationDuration = animationDuration;
	}

	/**
	 * This method gets called when the View is physically
	 * visible to the user
	 */
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		handle = (TextView)findViewById(handleId);
		if (handle == null) {
			throw new IllegalArgumentException("The handle attribute is must refer to an existing child.");
		}

		content = findViewById(contentId);
		if (content == null) {
			throw new IllegalArgumentException("The content attribute must refer to an existing child.");
		}

		// This changes the height of the content such that it
		// starts off collapsed
		ViewGroup.LayoutParams lp = content.getLayoutParams();
		lp.height = collapsedHeight;
		content.setLayoutParams(lp);

		// Set the OnClickListener of the handle view
		final PanelToggler toggler = new PanelToggler();
		handle.setOnClickListener(toggler);
		content.setOnClickListener(toggler);

		if (!isInEditMode()) {
			handle.setTypeface(iconManager.getTypeface());
			handle.setText(handleIconCollapsed);
		}
	}

	/**
	 * This is where the magic happens for measuring the actual
	 * (un-expanded) height of the content. If the actual height
	 * is less than the collapsedHeight, the handle will be hidden.
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// First, measure how high content wants to be
		content.measure(widthMeasureSpec, MeasureSpec.UNSPECIFIED);
		contentHeight = content.getMeasuredHeight();
		//Log.v("cHeight", contentHeight + "");
		//Log.v("cCollapseHeight", collapsedHeight + "");

		if (contentHeight < collapsedHeight) {
			handle.setVisibility(View.GONE);
		} else {
			handle.setVisibility(View.VISIBLE);
		}

		// Then let the usual thing happen
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * This is the on click listener for the handle.
	 * It basically just creates a new animation instance and fires
	 * animation.
	 */
	private class PanelToggler implements OnClickListener {
		public void onClick(View v) {
			Animation a;
			if (expanded) {
				a = new ExpandAnimation(contentHeight, collapsedHeight);
				listener.onCollapse(handle, content);
			} else {
				a = new ExpandAnimation(collapsedHeight, contentHeight);
				listener.onExpand(handle, content);
			}
			a.setDuration(animationDuration);
			content.startAnimation(a);
			expanded = !expanded;
		}
	}

	/**
	 * This is a private animation class that handles the expand/collapse
	 * animations. It uses the animationDuration attribute for the length
	 * of time it takes.
	 */
	private class ExpandAnimation extends Animation {
		private final int mStartHeight;
		private final int mDeltaHeight;

		public ExpandAnimation(int startHeight, int endHeight) {
			mStartHeight = startHeight;
			mDeltaHeight = endHeight - startHeight;
		}

		@Override
		protected void applyTransformation(float interpolatedTime, Transformation t) {
			ViewGroup.LayoutParams lp = content.getLayoutParams();
			lp.height = (int) (mStartHeight + mDeltaHeight * interpolatedTime);
			content.setLayoutParams(lp);
		}

		@Override
		public boolean willChangeBounds() {
			return true;
		}
	}

	/**
	 * Simple OnExpandListener interface
	 */
	public interface OnExpandListener {
		public void onExpand(TextView handle, View content);

		public void onCollapse(TextView handle, View content);
	}

	private class DefaultOnExpandListener implements OnExpandListener {
		public void onCollapse(TextView handle, View content) {
			handle.setText(handleIconCollapsed);
		}

		public void onExpand(TextView handle, View content) {
			handle.setText(handleIconExpanded);
		}
	}
}