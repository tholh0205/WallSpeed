package com.wallspeed.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.wallspeed.R;

/**
 * Created by ThoLH on 9/19/15.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.splash);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        setContentView(imageView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SplashActivity.this != null && !isFinishing()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && isDestroyed())
                        return;
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        }, 2000);
    }

}
