package com.wallspeed.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.wallspeed.R;
import com.wallspeed.animation.FragmentAnimation;
import com.wallspeed.fragments.BaseFragment;
import com.wallspeed.global.FragmentItem;

import java.util.ArrayList;

/**
 * Created by ThoLH on 9/19/15.
 */
public class BaseActivity extends AppCompatActivity {
    private final String SAVE_KEY_FRAGMENT_STACK = "SAVE_KEY_FRAGMENT_STACK";

    private Handler mHandlerUI = new Handler(Looper.getMainLooper());

    private ArrayList<FragmentItem> mFragmentStack = new ArrayList<>();
    private boolean isActive = false, isContentViewOnTop = false, isRemovingTopFragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mFragmentStack.clear();
        try {
            //Handle case Don't keep activities or activity was killed
            if (savedInstanceState != null) {
                ArrayList<FragmentItem> data = savedInstanceState.getParcelableArrayList(SAVE_KEY_FRAGMENT_STACK);
                if (data != null) {
                    mFragmentStack.addAll(data);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction ft = null;
                    for (int i = 0; i < mFragmentStack.size(); i++) {
                        FragmentItem fragmentItem = mFragmentStack.get(i);
                        Fragment fragment = fragmentManager.findFragmentByTag(fragmentItem.tag);
                        if (fragment instanceof BaseFragment) {
                            fragmentItem.fragment = (BaseFragment) fragment;
                            //Handle overdraws is bad performance
                            if (i < mFragmentStack.size() - 1) {
                                if (ft == null)
                                    ft = fragmentManager.beginTransaction();
                                ft.hide(fragment);
                            }
                        } else {
                            fragmentItem.fragment = fragmentItem.cls.newInstance();
                            if (fragmentItem.data != null)
                                fragmentItem.fragment.setArguments(fragmentItem.data);
                        }
                    }
                    if (ft != null)
                        ft.commit();
                }
                if (mFragmentStack != null && !mFragmentStack.isEmpty()) {
                    final FragmentItem topFragmentItem = mFragmentStack.get(mFragmentStack.size() - 1);
                    mHandlerUI.post(new Runnable() {
                        @Override
                        public void run() {
                            if (topFragmentItem.fragment != null && topFragmentItem.fragment.isAdded() && !topFragmentItem.fragment.isDetached()) {
                                topFragmentItem.fragment.deliverUiState(BaseFragment.STATE_RESUME);
                            }
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void showFragmentForResult(FragmentItem.FragmentType fragmentType, Bundle data, BaseFragment caller, int requestCode, int animationEnter, int animationExit) {
        addFragmentToStack(fragmentType, data, caller, requestCode, animationEnter, animationExit);
        showFragmentOnTop();
    }

    public void showFragment(FragmentItem.FragmentType fragmentType, Bundle data, int animationEnter, int animationExit) {
        showFragmentForResult(fragmentType, data, null, 0, animationEnter, animationExit);
    }

    protected void addFragmentToStack(FragmentItem.FragmentType fragmentType, Bundle data, BaseFragment caller, int requestCode, int animationEnter, int animationExit) {
        try {
            if (data == null)
                data = new Bundle();
            FragmentManager fm = getSupportFragmentManager();
            fm.executePendingTransactions();
            //FragmentTransaction ft = fm.beginTransaction();
            FragmentItem fragmentItem = null;

            //TODO: Check and reuse cached fragment if it's necessary
            //End

            if (fragmentItem == null) {
                fragmentItem = new FragmentItem(fragmentType, data);
                mFragmentStack.add(fragmentItem);
            }
            fragmentItem.fragment = fragmentType.getFragmentClass().newInstance();
            fragmentItem.fragment.setArguments(data != null ? data : new Bundle());

            fragmentItem.animationEnter = animationEnter;
            fragmentItem.animationExit = animationExit;


            if (caller != null && mFragmentStack.size() > 1) {
                FragmentItem fragmentPrevItem = mFragmentStack.get(mFragmentStack.size() - 2);
                if (fragmentPrevItem.fragment == caller) {
                    fragmentItem.callerFragmentTag = fragmentPrevItem.tag;
                }
            }
            if (requestCode != 0) {
                fragmentItem.requestCode = requestCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showFragmentOnTop() {
        if (mFragmentStack == null || mFragmentStack.isEmpty()) {
            isContentViewOnTop = true;
            return;
        }
        isContentViewOnTop = false;
        FragmentManager fm = getSupportFragmentManager();
        fm.executePendingTransactions();
        FragmentTransaction ft = fm.beginTransaction();
        final FragmentItem topFragmentItem = mFragmentStack.get(mFragmentStack.size() - 1);
        final FragmentItem belowFragmentItem = mFragmentStack.size() > 1 ? mFragmentStack.get(mFragmentStack.size() - 2) : null;
        for (int i = 0; i < mFragmentStack.size(); i++) {
            final FragmentItem fragmentItem = mFragmentStack.get(i);
            if (i < mFragmentStack.size() - 2 && !BaseFragment.KeepBelowFragment.class.isAssignableFrom(fragmentItem.cls) && topFragmentItem.animationEnter <= 0) {
                if (BaseFragment.DoNotDetachInBackground.class.isAssignableFrom(fragmentItem.cls)) {
                    if (fragmentItem.fragment.isAdded() && fragmentItem.fragment.isVisible()) {
                        ft.hide(fragmentItem.fragment);
//                        mHandlerUI.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (fragmentItem != null && fragmentItem.fragment != null && fragmentItem.fragment.isAdded())
//                                    fragmentItem.fragment.deliverUiState(BaseFragment.STATE_PAUSE);
//                            }
//                        });
                    }
                } else {
                    if (!fragmentItem.fragment.isDetached()) {
                        ft.detach(fragmentItem.fragment);
                    }
                }
            }
        }
        if (!ft.isEmpty()) {
            ft.commit();
            fm.executePendingTransactions();
            ft = fm.beginTransaction();
        }

//        ft.setCustomAnimations(topFragmentItem.animationEnter, topFragmentItem.animationExit, 0, 0);

        if (topFragmentItem.fragment.isDetached()) {
            ft.attach(topFragmentItem.fragment);
        } else if (!topFragmentItem.fragment.isAdded()) {
            ft.add(R.id.fragment_content, topFragmentItem.fragment, topFragmentItem.tag);
        }

        if (!topFragmentItem.fragment.isVisible()) {
            ft.show(topFragmentItem.fragment);
            mHandlerUI.post(new Runnable() {
                @Override
                public void run() {
                    if (topFragmentItem.fragment != null && topFragmentItem.fragment.isAdded() && !topFragmentItem.fragment.isDetached()) {
                        topFragmentItem.fragment.deliverUiState(BaseFragment.STATE_RESUME);
                    }
                }
            });
        }
        if (isActive) {
            ft.commit();
        } else {
            ft.commitAllowingStateLoss();
        }

        mHandlerUI.post(new Runnable() {
            @Override
            public void run() {
                if (belowFragmentItem != null && belowFragmentItem.fragment != null && belowFragmentItem.fragment.isAdded())
                    belowFragmentItem.fragment.deliverUiState(BaseFragment.STATE_PAUSE);
            }
        });

        handleFragmentAnimation(topFragmentItem, belowFragmentItem);

        mHandlerUI.post(new Runnable() {
            @Override
            public void run() {
                initActionBar();
//                if (topFragmentItem.fragment != null && topFragmentItem.fragment.isAdded() && !topFragmentItem.fragment.isDetached()) {
//                    topFragmentItem.fragment.deliverUiState(BaseFragment.STATE_INIT_ACTIONBAR);
//                }
            }
        });

    }

    public void hideBelowFragment() {
        FragmentItem topFragmentItem = mFragmentStack.isEmpty() ? null : mFragmentStack.get(mFragmentStack.size() - 1);
        FragmentItem belowFragmentItem = mFragmentStack.size() > 1 ? mFragmentStack.get(mFragmentStack.size() - 2) : null;
        if (belowFragmentItem == null || (topFragmentItem != null && BaseFragment.KeepBelowFragment.class.isAssignableFrom(topFragmentItem.cls)))
            return;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(belowFragmentItem.fragment);
        if (isActive)
            ft.commit();
        else
            ft.commitAllowingStateLoss();
    }

    private void handleFragmentAnimation(final FragmentItem topFragmentItem, final FragmentItem belowFragmentItem) {
        mHandlerUI.post(new Runnable() {
            @Override
            public void run() {
                if (topFragmentItem != null && topFragmentItem.fragment != null && topFragmentItem.animationEnter > 0) {
                    FragmentAnimation.fragmentAnimationEnter(topFragmentItem.fragment.getView(), new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationCancel(Animator animation) {
                            super.onAnimationCancel(animation);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                        }

                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                        }
                    });
                }

                if (belowFragmentItem != null && belowFragmentItem.fragment != null && topFragmentItem != null && topFragmentItem.animationExit > 0) {
                    FragmentAnimation.fragmentAnimationExit(belowFragmentItem.fragment.getView(), new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationCancel(Animator animation) {
                            super.onAnimationCancel(animation);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            hideBelowFragment();
                        }

                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        popBackStack();
//        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mFragmentStack != null && !mFragmentStack.isEmpty()) {
            mFragmentStack.get(mFragmentStack.size() - 1).fragment.onOptionsItemSelected(item);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void popBackStack() {
        if (isRemovingTopFragment) return;
        if (isContentViewOnTop || mFragmentStack == null || mFragmentStack.size() == 1) {
            if (isActive)
                finish();
            return;
        }
        isRemovingTopFragment = true;
        FragmentItem topFragmentItem = mFragmentStack.get(mFragmentStack.size() - 1);
        if (!BaseFragment.KeepBelowFragment.class.isAssignableFrom(topFragmentItem.cls)) {
            final FragmentItem belowFragmentItem = mFragmentStack.size() > 1 ? mFragmentStack.get(mFragmentStack.size() - 2) : null;
            if (belowFragmentItem != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if (belowFragmentItem.fragment.isDetached())
                    ft.attach(belowFragmentItem.fragment);
                else if (!belowFragmentItem.fragment.isVisible())
                    ft.show(belowFragmentItem.fragment);
                if (isActive)
                    ft.commit();
                else
                    ft.commitAllowingStateLoss();
            }

            handlePopFragmentAnimation(topFragmentItem, belowFragmentItem);
        } else {
            removeFragmentOnTop();
        }
    }

    public void removeFragmentOnTop() {
        FragmentManager fm = getSupportFragmentManager();
        fm.executePendingTransactions();

        final FragmentItem topFragmentItem = mFragmentStack.get(mFragmentStack.size() - 1);
        final FragmentItem belowFragmentItem = mFragmentStack.size() > 1 ? mFragmentStack.get(mFragmentStack.size() - 2) : null;
        mFragmentStack.remove(mFragmentStack.size() - 1);
        if (topFragmentItem.callerFragmentTag != null && topFragmentItem.callerFragmentTag.equals(belowFragmentItem.tag)) {
            mHandlerUI.post(new Runnable() {
                @Override
                public void run() {
                    if (belowFragmentItem != null && belowFragmentItem.fragment != null) {
                        belowFragmentItem.fragment.deliverResult(topFragmentItem.requestCode, topFragmentItem.fragment.getResultCode(), topFragmentItem.fragment.getResultData());
                    }
                }
            });
        }

        if (topFragmentItem.fragment.isAdded() && !topFragmentItem.fragment.isDetached()) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.detach(topFragmentItem.fragment);
            if (isActive)
                ft.commit();
            else
                ft.commitAllowingStateLoss();
        }
        mHandlerUI.post(new Runnable() {
            @Override
            public void run() {
                if (belowFragmentItem.fragment != null && belowFragmentItem.fragment.isAdded() && !belowFragmentItem.fragment.isDetached()) {
                    belowFragmentItem.fragment.deliverUiState(BaseFragment.STATE_RESUME);
                }
            }
        });
        isRemovingTopFragment = false;
    }

    private void handlePopFragmentAnimation(final FragmentItem topFragmentItem, final FragmentItem belowFragmentItem) {
        mHandlerUI.post(new Runnable() {
            @Override
            public void run() {
                if (topFragmentItem != null && topFragmentItem.fragment != null && topFragmentItem.animationEnter > 0) {
                    FragmentAnimation.fragmentBackAnimationExit(topFragmentItem.fragment.getView(), new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationCancel(Animator animation) {
                            super.onAnimationCancel(animation);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            removeFragmentOnTop();
                        }

                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                        }
                    });
                }

                if (belowFragmentItem != null && belowFragmentItem.fragment != null && topFragmentItem != null && topFragmentItem.animationExit > 0) {
                    FragmentAnimation.fragmentBackAnimationEnter(belowFragmentItem.fragment.getView(), new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationCancel(Animator animation) {
                            super.onAnimationCancel(animation);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                        }

                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                        }
                    });
                }
            }
        });
    }

    protected void initActionBar() {

    }

    public BaseFragment getFragmentBelow() {
        if (mFragmentStack == null || mFragmentStack.size() < 2)
            return null;
        return mFragmentStack.get(mFragmentStack.size() - 2).fragment;
    }

    public void hideFragmentBelow() {
        if (mFragmentStack == null || mFragmentStack.size() < 2)
            return;
        FragmentItem belowFragmentItem = mFragmentStack.get(mFragmentStack.size() - 2);
        if (belowFragmentItem != null && belowFragmentItem.fragment != null && !belowFragmentItem.fragment.isDetached()) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(belowFragmentItem.fragment);
            if (isActive) {
                ft.commit();
            } else {
                ft.commitAllowingStateLoss();
            }
        }
    }

    public void showFragmentBelow() {
        if (mFragmentStack == null || mFragmentStack.size() < 2)
            return;
        FragmentItem belowFragmentItem = mFragmentStack.get(mFragmentStack.size() - 2);
        if (belowFragmentItem != null && belowFragmentItem.fragment != null && !belowFragmentItem.fragment.isDetached()) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.show(belowFragmentItem.fragment);
            if (isActive) {
                ft.commit();
            } else {
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    protected void onResume() {
        isActive = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        isActive = false;
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        try {
            super.onSaveInstanceState(outState);
            outState.putParcelableArrayList(SAVE_KEY_FRAGMENT_STACK, mFragmentStack);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
