package com.wallspeed.application;

import android.app.Application;

/**
 * Created by ThoLH on 9/19/15.
 */
public class MainApplication extends Application {

    private static MainApplication mInstance = null;

    public static MainApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
