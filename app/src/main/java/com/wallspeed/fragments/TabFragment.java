package com.wallspeed.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.wallspeed.activities.BaseFragmentActivity;

/**
 * Created by ThoLH on 9/20/15.
 */
public class TabFragment extends Fragment {

    public BaseFragmentActivity getBaseActivity() {
        Activity activity = getActivity();
        if (activity instanceof BaseFragmentActivity)
            return (BaseFragmentActivity) activity;
        return null;
    }

}
