package com.wallspeed.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wallspeed.R;

/**
 * Created by ThoLH on 9/19/15.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setBackgroundColor(Color.BLACK);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.logo);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 100;
        imageView.setLayoutParams(params);
        linearLayout.addView(imageView);
        setContentView(linearLayout);
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
