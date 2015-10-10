package com.wallspeed.animation;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.wallspeed.R;
import com.wallspeed.util.ViewUtils;

import java.util.ArrayList;

/**
 * Created by ThoLH on 10/7/15.
 */
public class FragmentAnimationUtils {

    private static AnimatorSet sCurrentAnimatorSet;

    public static void cancelCurrentAnimation() {
        if (sCurrentAnimatorSet != null && sCurrentAnimatorSet.isRunning()) {
            sCurrentAnimatorSet.cancel();
            sCurrentAnimatorSet = null;
        }
    }

    public static boolean isRunning() {
        if (sCurrentAnimatorSet != null) {
            return sCurrentAnimatorSet.isRunning();
        }
        return false;
    }

    public static void openTranslationWithFadeIn(final View topView, final Animator.AnimatorListener animatorListener) {
        if (topView == null)
            return;
        cancelCurrentAnimation();
        int screenWidth = topView.getResources().getDisplayMetrics().widthPixels;
        ArrayList<Animator> animators = new ArrayList<>();
        animators.add(ObjectAnimator.ofFloat(topView, "translationX", screenWidth / 2f, 0f));
        animators.add(ObjectAnimator.ofFloat(topView, "alpha", 0f, 1f));
        sCurrentAnimatorSet = new AnimatorSet();
        sCurrentAnimatorSet.playTogether(animators);
        sCurrentAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                ViewUtils.setHardwareType(topView);
                if (animatorListener != null)
                    animatorListener.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ViewUtils.setNoneType(topView);
                if (animatorListener != null)
                    animatorListener.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                ViewUtils.setNoneType(topView);
                if (animatorListener != null)
                    animatorListener.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                if (animatorListener != null)
                    animatorListener.onAnimationRepeat(animation);
            }
        });
        sCurrentAnimatorSet.setDuration(topView.getResources().getInteger(R.integer.default_duration));
        sCurrentAnimatorSet.setInterpolator(new DecelerateInterpolator(1.5F));
        sCurrentAnimatorSet.start();
    }

    public static void closeTranslationWithFadeIn(final View topView, final Animator.AnimatorListener animatorListener) {
        if (topView == null)
            return;
        cancelCurrentAnimation();
        int screenWidth = topView.getResources().getDisplayMetrics().widthPixels;
        ArrayList<Animator> animators = new ArrayList<>();
        animators.add(ObjectAnimator.ofFloat(topView, "translationX", 0f, screenWidth / 2f));
        animators.add(ObjectAnimator.ofFloat(topView, "alpha", 1f, 0f));
        sCurrentAnimatorSet = new AnimatorSet();
        sCurrentAnimatorSet.playTogether(animators);
        sCurrentAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                ViewUtils.setHardwareType(topView);
                if (animatorListener != null)
                    animatorListener.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ViewUtils.setNoneType(topView);
                if (animatorListener != null)
                    animatorListener.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                ViewUtils.setNoneType(topView);
                if (animatorListener != null)
                    animatorListener.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                if (animatorListener != null)
                    animatorListener.onAnimationRepeat(animation);
            }
        });
        sCurrentAnimatorSet.setDuration(topView.getResources().getInteger(R.integer.default_duration));
        sCurrentAnimatorSet.setInterpolator(new AccelerateInterpolator(1.5F));
        sCurrentAnimatorSet.start();
    }

}
