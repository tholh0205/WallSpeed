package com.wallspeed.activities;

import android.os.Bundle;

import com.wallspeed.global.FragmentData;

/**
 * Created by ThoLH on 9/19/15.
 */
public class MainActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getFragmentManagerLayout().showFragment(FragmentData.FragmentType.MAIN, Bundle.EMPTY, 0, true, false);
        }
    }

    @Override
    public void finish() {
        moveTaskToBack(true);
//        super.finish();
    }
}
