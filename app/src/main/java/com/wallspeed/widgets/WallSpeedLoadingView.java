package com.wallspeed.widgets;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import com.wallspeed.R;

/**
 * Created by ThoLH on 9/30/15.
 */
public class WallSpeedLoadingView extends ImageView {

    public WallSpeedLoadingView(Context context) {
        super(context);
    }

    public WallSpeedLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAnimation(attrs);
    }

    public WallSpeedLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAnimation(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WallSpeedLoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setAnimation(attrs);
    }

    private void setAnimation(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.WallSpeedLoadingView);
        int frameCount = a.getInt(R.styleable.WallSpeedLoadingView_frameCount, 12);
        int duration = a.getInt(R.styleable.WallSpeedLoadingView_duration, 1000);
        a.recycle();

        setAnimation(frameCount, duration);
    }

    public void setAnimation(final int frameCount, final int duration) {
        ObjectAnimator rotate = ObjectAnimator.ofFloat(this, "rotation", 0f, 359f);
        rotate.setDuration(duration);
        rotate.setRepeatCount(ValueAnimator.INFINITE);
        rotate.setInterpolator(new Interpolator() {

            @Override
            public float getInterpolation(float input) {
                return (float) Math.floor(input * frameCount) / frameCount;
            }
        });
        rotate.start();
//        Animation a = AnimationUtils.loadAnimation(getContext(), R.anim.progress_anim);
//        a.setDuration(duration);
//        a.setInterpolator(new Interpolator() {
//
//            @Override
//            public float getInterpolation(float input) {
//                return (float)Math.floor(input*frameCount)/frameCount;
//            }
//        });
//        startAnimation(a);
    }
}
