package com.wallspeed.activities;

import android.content.Intent;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (getFragmentManagerLayout() != null) {
            getFragmentManagerLayout().onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
