package com.wallspeed.application;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.animated.factory.AnimatedImageFactory;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;

import java.io.File;

/**
 * Created by ThoLH on 9/19/15.
 */
public class MainApplication extends Application {

    private static MainApplication mInstance = null;
    private Handler mHanderUI;

    public static MainApplication getInstance() {
        return mInstance;
    }

    public Context getAppContext() {
        return getInstance().getApplicationContext();
    }

    public void runOnUIThread(Runnable r) {
        mHanderUI.post(r);
    }

    public void cancelOnUIThread(Runnable r) {
        mHanderUI.removeCallbacks(r);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mHanderUI = new Handler(Looper.getMainLooper());
        initFresco();
    }

    private void initFresco() {
        Fresco.initialize(this);
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder()//
                .setBaseDirectoryPath(new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), ""))
                .setBaseDirectoryName("WallSpeed")
                .setMaxCacheSize(41943040L)
                .setMaxCacheSizeOnLowDiskSpace(10485760L)
                .setMaxCacheSizeOnVeryLowDiskSpace(2097152L)
                .build();
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(this)//
                .setMainDiskCacheConfig(diskCacheConfig)
                .setResizeAndRotateEnabledForNetwork(true)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, imagePipelineConfig);
    }
}
