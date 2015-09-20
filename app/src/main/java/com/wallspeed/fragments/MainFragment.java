package com.wallspeed.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wallspeed.R;
import com.wallspeed.global.FragmentItem;

/**
 * Created by ThoLH on 9/19/15.
 */
public class MainFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btnNextFragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBaseActivity().showFragmentForResult(FragmentItem.FragmentType.EXIT, null, MainFragment.this, 1111, R.anim.fragment_enter, R.anim.fragment_exit);
            }
        });
    }

    @Override
    protected Drawable getHomeAsUpIndicator() {
        Drawable upDrawable = getResources().getDrawable(R.drawable.ic_drawer);
        upDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        return upDrawable;
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        ActionBar actionBar = getBaseActivity().getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("MainFragment");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1111 && resultCode == Activity.RESULT_OK) {
//            Toast.makeText(getContext(), "ExitFragment", Toast.LENGTH_LONG).show();
//        }
    }
}
