package com.wallspeed.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.wallspeed.R;
import com.wallspeed.global.FragmentItem;

/**
 * Created by ThoLH on 9/19/15.
 */
public class ExitFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.exit_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btnNextFragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBaseActivity().showFragment(FragmentItem.FragmentType.REUSE, null, R.anim.fragment_enter, R.anim.fragment_exit);
            }
        });
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        ActionBar actionBar = getBaseActivity().getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("ExitFragment");
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            setResult(Activity.RESULT_OK, new Intent());
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
