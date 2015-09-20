package com.wallspeed.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.wallspeed.R;
import com.wallspeed.activities.BaseActivity;
import com.wallspeed.widgets.TouchInterceptionLayout;

import java.util.ArrayList;

/**
 * Created by ThoLH on 9/19/15.
 */
public class BaseFragment extends Fragment {
    public static final int STATE_RESUME = 0, STATE_PAUSE = 1, STATE_INIT_ACTIONBAR = 2;

    private int mResultCode = Activity.RESULT_CANCELED;
    private Intent mResultData = null;

    //for deliver result for other fragment
    private boolean resulting;
    private int receiverResultCode = Activity.RESULT_CANCELED;
    private Intent receiverResultData = null;
    private int receiverRequestCode = 0;

    protected Handler mHandlerUI = new Handler(Looper.getMainLooper());

    private Toolbar mToolbar;
    private View mSwippableView, mBackgroundView;
    private float mDownX;
    private boolean isSwiping = false, isExiting = false;

    private TouchInterceptionLayout.TouchInterceptionListener mTouchInterceptionListener = new TouchInterceptionLayout.TouchInterceptionListener() {
        @Override
        public boolean shouldInterceptTouchEvent(MotionEvent ev, boolean moving, float diffX, float diffY) {
            if (!allowSwipe() || isExiting)
                return false;
            if (moving && isSwiping)
                return true;
            if (!moving) {
                getBackgroundFragmentView();
                if (mBackgroundView == null) {
                    isSwiping = false;
                } else {
                    isSwiping = checkLeftEdgeTouched(ev);
                }
                return isSwiping;
            }
            return false;
        }

        @Override
        public void onDownMotionEvent(MotionEvent ev) {
            mDownX = ev.getX();
        }

        @Override
        public void onMoveMotionEvent(MotionEvent ev, float diffX, float diffY) {
            float currentTranslationX = ev.getX() - mDownX;
            if (currentTranslationX < 0)
                currentTranslationX = 0;
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            if (!isExiting) {
                isSwiping = true;
                getBaseActivity().showFragmentBelow();
                if (mBackgroundView != null) {
                    float scale = (currentTranslationX / screenWidth) * 0.25f + 0.75f;
                    mBackgroundView.setScaleX(scale);
                    mBackgroundView.setScaleY(scale);
                }
                if (mSwippableView != null)
                    mSwippableView.setTranslationX(currentTranslationX);
            }

        }

        @Override
        public void onUpOrCancelMotionEvent(MotionEvent ev) {
            if (!isExiting) {
                isSwiping = false;
                isExiting = true;
                float toTranslationX = getResources().getDisplayMetrics().widthPixels;
                if (mSwippableView != null) {
                    if (mSwippableView.getTranslationX() < getResources().getDimensionPixelSize(R.dimen.min_translation_x)) {
                        toTranslationX = 0;
                    }
                }
                final boolean isResume = toTranslationX == 0;
                ArrayList<Animator> animators = new ArrayList<>();
                if (mBackgroundView != null) {
                    animators.add(ObjectAnimator.ofFloat(mBackgroundView, "scaleX", mBackgroundView.getScaleX(), toTranslationX == 0 ? 0.75f : 1));
                    animators.add(ObjectAnimator.ofFloat(mBackgroundView, "scaleY", mBackgroundView.getScaleY(), toTranslationX == 0 ? 0.75f : 1));
                }
                if (mSwippableView != null) {
                    animators.add(ObjectAnimator.ofFloat(mSwippableView, "translationX", mSwippableView.getTranslationX(), toTranslationX));
                    if (!isResume) {
                        animators.add(ObjectAnimator.ofFloat(mSwippableView, "alpha", 1f, toTranslationX < (toTranslationX / 2) ? 0f : 0.5f));
                    }
                }
                if (!animators.isEmpty()) {
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.setInterpolator(new DecelerateInterpolator(1.5F));
                    animatorSet.setDuration(getResources().getInteger(R.integer.default_duration));
                    animatorSet.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            if (isResume) {
                                getBaseActivity().hideBelowFragment();
                            } else {
                                getBaseActivity().removeFragmentOnTop();
                            }
                            mHandlerUI.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    isExiting = false;
                                }
                            }, 100);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            super.onAnimationCancel(animation);
                            if (isResume) {
                                getBaseActivity().hideBelowFragment();
                            } else {
                                getBaseActivity().removeFragmentOnTop();
                            }
                            mHandlerUI.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    isExiting = false;
                                }
                            }, 100);
                        }
                    });
                    animatorSet.playTogether(animators);
                    animatorSet.start();
                }
            }
        }
    };

    public void getBackgroundFragmentView() {
        Fragment belowFragment = getBaseActivity().getFragmentBelow();
        if (belowFragment != null)
            mBackgroundView = belowFragment.getView();
        if (getView() != null)
            mSwippableView = getView().findViewById(R.id.swippable_layout);
    }

    public boolean checkLeftEdgeTouched(MotionEvent ev) {
        int result = 0;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        if (x < getView().getLeft() + 20 && getToolbar() != null && y > getToolbar().getBottom())
            result |= ViewDragHelper.EDGE_LEFT;
        if (y < getView().getTop() + 20) result |= ViewDragHelper.EDGE_TOP;
        if (x > getView().getRight() - 20) result |= ViewDragHelper.EDGE_RIGHT;
        if (y > getView().getBottom() - 20) result |= ViewDragHelper.EDGE_BOTTOM;

        return result == ViewDragHelper.EDGE_LEFT;
    }

    protected final void setResult(int resultCode, Intent data) {
        mResultCode = resultCode;
        mResultData = data;
    }

    public final void deliverResult(int requestCode, int resultCode, Intent data) {
        receiverRequestCode = requestCode;
        receiverResultCode = resultCode;
        receiverResultData = data;
        resulting = true;
    }

    public int getResultCode() {
        return mResultCode;
    }

    public Intent getResultData() {
        return mResultData;
    }


    public void deliverUiState(int uiState) {
        switch (uiState) {
            case STATE_RESUME:
                mHandlerUI.post(new Runnable() {
                    @Override
                    public void run() {
                        onResume();
                    }
                });
                break;
            case STATE_PAUSE:
                mHandlerUI.post(new Runnable() {
                    @Override
                    public void run() {
                        onPause();
                    }
                });
                break;
            case STATE_INIT_ACTIONBAR:
                mHandlerUI.post(new Runnable() {
                    @Override
                    public void run() {
                        initActionBar();
                    }
                });
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getResources().getBoolean(R.bool.use_swipe_to_dismiss) && getView() != null && getView() instanceof TouchInterceptionLayout) {
            mSwippableView = getView().findViewById(R.id.swippable_layout);
            if (mSwippableView != null)
                ((TouchInterceptionLayout) getView()).setScrollInterceptionListener(mTouchInterceptionListener);
        }
        if (resulting && receiverRequestCode != 0) {
            resulting = false;
            mHandlerUI.post(new Runnable() {
                @Override
                public void run() { //function can remove this fragment
                    onActivityResult(receiverRequestCode, receiverResultCode, receiverResultData);
                }
            });
        }
        initActionBar();
    }

    @Override
    public void onPause() {
        if (getView() != null && getView() instanceof TouchInterceptionLayout) {
            ((TouchInterceptionLayout) getView()).setScrollInterceptionListener(null);
        }
        super.onPause();
    }

    protected boolean allowSwipe() {
        return true;
    }

    protected void initActionBar() {
        if (mToolbar == null)
            mToolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        getBaseActivity().setSupportActionBar(mToolbar);
        ActionBar actionBar = getBaseActivity().getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(getHomeAsUpIndicator());
        }
    }

    protected Drawable getHomeAsUpIndicator() {
        Drawable upDrawable = getContext().getResources().getDrawable(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        return upDrawable;
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        if (getBaseActivity() != null)
            getBaseActivity().popBackStack();
    }

    protected BaseActivity getBaseActivity() {
        if (getActivity() instanceof BaseActivity)
            return (BaseActivity) getActivity();
        return null;
    }

    public interface KeepBelowFragment {
    }

    public interface DoNotDetachInBackground {
    }

}
