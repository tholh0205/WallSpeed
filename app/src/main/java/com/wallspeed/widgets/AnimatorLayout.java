package com.wallspeed.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by ThoLH on 9/19/15.
 */
public class AnimatorLayout extends TouchInterceptionLayout {

    public AnimatorLayout(Context context) {
        super(context);
    }

    public AnimatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnimatorLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

}
