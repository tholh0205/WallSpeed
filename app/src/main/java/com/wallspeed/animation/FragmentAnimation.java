package com.wallspeed.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.wallspeed.R;

import java.util.ArrayList;

/**
 * Created by ThoLH on 9/19/15.
 */
public class FragmentAnimation {

    public static final void fragmentAnimationEnter(View view, final AnimatorListenerAdapter animatorListenerAdapter) {
        if (view == null) return;
        int screenWidth = view.getResources().getDisplayMetrics().widthPixels;
        ArrayList<Animator> animators = new ArrayList<>();
        animators.add(ObjectAnimator.ofFloat(view, "translationX", screenWidth / 2, 0f));
        animators.add(ObjectAnimator.ofFloat(view, "alpha", 0f, 1f));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new DecelerateInterpolator(1.5f));
        animatorSet.setDuration(view.getResources().getInteger(R.integer.default_duration));
//        animatorSet.setDuration(5000);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (animatorListenerAdapter != null)
                    animatorListenerAdapter.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (animatorListenerAdapter != null)
                    animatorListenerAdapter.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (animatorListenerAdapter != null)
                    animatorListenerAdapter.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.playTogether(animators);
        animatorSet.start();
    }

    public static final void fragmentAnimationExit(View view, final AnimatorListenerAdapter animatorListenerAdapter) {
        if (view == null) return;
//        int screenWidth = view.getResources().getDisplayMetrics().widthPixels;
//        int screenHeight = view.getResources().getDisplayMetrics().heightPixels;
        ArrayList<Animator> animators = new ArrayList<>();
//        animators.add(ObjectAnimator.ofFloat(view, "translationX", 0f, screenWidth / 4));
//        animators.add(ObjectAnimator.ofFloat(view, "translationY", 0f, screenHeight / 4));
        animators.add(ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.75f));
        animators.add(ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.75f));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new DecelerateInterpolator(1.5f));
        animatorSet.setDuration(view.getResources().getInteger(R.integer.default_duration));
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (animatorListenerAdapter != null)
                    animatorListenerAdapter.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (animatorListenerAdapter != null)
                    animatorListenerAdapter.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (animatorListenerAdapter != null)
                    animatorListenerAdapter.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.playTogether(animators);
        animatorSet.start();
    }

    public static final void fragmentBackAnimationEnter(View view, final AnimatorListenerAdapter animatorListenerAdapter) {
        if (view == null) return;
//        int screenWidth = view.getResources().getDisplayMetrics().widthPixels;
//        int screenHeight = view.getResources().getDisplayMetrics().heightPixels;
        ArrayList<Animator> animators = new ArrayList<>();
//        animators.add(ObjectAnimator.ofFloat(view, "translationX", 0f, screenWidth / 4));
//        animators.add(ObjectAnimator.ofFloat(view, "translationY", 0f, screenHeight / 4));
        animators.add(ObjectAnimator.ofFloat(view, "scaleX", 0.75f, 1f));
        animators.add(ObjectAnimator.ofFloat(view, "scaleY", 0.75f, 1f));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new DecelerateInterpolator(1.5f));
        animatorSet.setDuration(view.getResources().getInteger(R.integer.default_duration));
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (animatorListenerAdapter != null)
                    animatorListenerAdapter.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (animatorListenerAdapter != null)
                    animatorListenerAdapter.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (animatorListenerAdapter != null)
                    animatorListenerAdapter.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.playTogether(animators);
        animatorSet.start();
    }

    public static final void fragmentBackAnimationExit(View view, final AnimatorListenerAdapter animatorListenerAdapter) {
        if (view == null) return;
        int screenWidth = view.getResources().getDisplayMetrics().widthPixels;
        ArrayList<Animator> animators = new ArrayList<>();
        animators.add(ObjectAnimator.ofFloat(view, "translationX", 0f, screenWidth / 2));
        animators.add(ObjectAnimator.ofFloat(view, "alpha", 1f, 0f));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new DecelerateInterpolator(1.5f));
        animatorSet.setDuration(view.getResources().getInteger(R.integer.default_duration));
//        animatorSet.setDuration(5000);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (animatorListenerAdapter != null)
                    animatorListenerAdapter.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (animatorListenerAdapter != null)
                    animatorListenerAdapter.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (animatorListenerAdapter != null)
                    animatorListenerAdapter.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.playTogether(animators);
        animatorSet.start();
    }

}
