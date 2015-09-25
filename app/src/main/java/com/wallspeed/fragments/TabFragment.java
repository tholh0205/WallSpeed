package com.wallspeed.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.wallspeed.activities.BaseActivity;

/**
 * Created by ThoLH on 9/20/15.
 */
public class TabFragment extends Fragment {

    public BaseFragment getBaseFragment() {
        Fragment fragment = getParentFragment();
        if (fragment instanceof BaseFragment)
            return (BaseFragment) fragment;
        return null;
    }

    public BaseActivity getBaseActivity() {
        Activity activity = getActivity();
        if (activity instanceof BaseActivity)
            return (BaseActivity) activity;
        return null;
    }

}
