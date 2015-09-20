package com.wallspeed.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wallspeed.R;

/**
 * Created by ThoLH on 9/19/15.
 */
public class ReuseFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.reuse_fragment, container, false);
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        ActionBar actionBar = getBaseActivity().getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("ReuseFragment");
        }
    }
}
