package com.wallspeed.activities;

import android.os.Bundle;

import com.wallspeed.global.FragmentItem;

/**
 * Created by ThoLH on 9/19/15.
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null)
            showFragment(FragmentItem.FragmentType.MAIN, null, 0, 0);
    }

    @Override
    public void finish() {
        moveTaskToBack(true);
//        super.finish();
    }
}
