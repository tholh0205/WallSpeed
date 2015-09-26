package com.wallspeed.application;

import android.app.Application;
import android.os.Environment;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.animated.factory.AnimatedImageFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.io.File;

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
        initFresco();
    }

    private void initFresco() {
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder()//
                .setBaseDirectoryPath(new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), ""))
                .setBaseDirectoryName("WallSpeed")
                .setMaxCacheSize(20 * 1024)
                .setMaxCacheSizeOnLowDiskSpace(10 * 1024)
                .setMaxCacheSizeOnVeryLowDiskSpace(5 * 1024)
                .setVersion(1)
                .build();
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(this)//
                .setMainDiskCacheConfig(diskCacheConfig)
                .setResizeAndRotateEnabledForNetwork(true)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, imagePipelineConfig);
    }
}
