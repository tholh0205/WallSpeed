package com.wallspeed.util;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ThoLH on 10/21/15.
 */
public class PermissionUtils {

    public static boolean checkPermission(AppCompatActivity activity, String permission) {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        } else {
            int hasPerm = activity.checkSelfPermission(permission);
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static void requestPermissions(AppCompatActivity activity, String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            activity.requestPermissions(permissions, requestCode);
        }
    }

}
