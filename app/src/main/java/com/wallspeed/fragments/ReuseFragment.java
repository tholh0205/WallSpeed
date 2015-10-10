package com.wallspeed.fragments;

import android.content.Context;
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
    @Override
    public View onCreateView(Context context, ViewGroup container) {
        return LayoutInflater.from(context).inflate(R.layout.reuse_fragment, container, false);
    }

    @Override
    public void onSetupActionBar() {
        super.onSetupActionBar();
        ActionBar actionBar = getActivity().getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("ReuseFragment");
        }
    }
}
