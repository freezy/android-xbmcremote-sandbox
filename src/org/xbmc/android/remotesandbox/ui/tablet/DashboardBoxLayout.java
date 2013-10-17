package org.xbmc.android.remotesandbox.ui.tablet;

import org.xbmc.android.remotesandbox.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DashboardBoxLayout extends LinearLayout {

	private OnClickListener mOnTitleClickListener;

	public DashboardBoxLayout(Context context, AttributeSet attrs) {
		super(context, attrs);

		// inflate
		String service = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);
		li.inflate(R.layout.dashboard_box, this, true);

		// set title and icon from custom attributes
		final TextView title = (TextView)findViewById(R.id.dashboardbox_title);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DashboardBoxLayout);
        CharSequence s = a.getString(R.styleable.DashboardBoxLayout_title2);
        if (s != null) {
        	title.setText(s);
        }
        final ImageView icon = (ImageView)findViewById(R.id.dashboardbox_icon);
        icon.setImageResource(a.getResourceId(R.styleable.DashboardBoxLayout_icon2, R.drawable.ic_dashboard_movies));

        // enable title bar onclick
        findViewById(R.id.dashboardbox_titlebar).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mOnTitleClickListener != null) {
					mOnTitleClickListener.onClick(v);
				}
			}
		});
	}

	public DashboardBoxLayout setOnTitlebarClickListener(OnClickListener listener) {
		mOnTitleClickListener = listener;
		return this;
	}

}
