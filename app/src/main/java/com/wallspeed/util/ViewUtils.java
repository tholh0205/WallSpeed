package com.wallspeed.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.TouchDelegate;
import android.view.View;

import com.wallspeed.R;
import com.wallspeed.application.MainApplication;


public class ViewUtils {
    public static boolean isXhdpi(Context context) {
        return getScreenDensity(context) == 320;
    }

    public static boolean isHdpi(Context context) {
        return getScreenDensity(context) == 240;
    }

    public static boolean isLdpi(Context context) {
        return getScreenDensity(context) == 120;
    }

    public static boolean isMdpi(Context context) {
        return getScreenDensity(context) == 160;
    }

    public static boolean isMdpiOrLdpi(Context context) {
        final int density = getScreenDensity(context);
        return (density == 120 || density == 160);
    }

    public static boolean isSmallerThanXhdpi(Context context) {
        final int density = getScreenDensity(context);
        return (density < 320);
    }

    public static int getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    public static int getScreenHeightPixels(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidthPixels(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenWidth() {
        return getScreenWidthPixels(MainApplication.getInstance().getApplicationContext());
    }

    public static float dpToPx(Context context, int dimen) {
        DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(1, dimen, displaymetrics);
    }

    public static int convertDipsToPixels(Context context, float dipValue) {
        return (int) (0.5F + dipValue * context.getResources().getDisplayMetrics().density);
    }

    public static int dp(float dipValue) {
        return convertDipsToPixels(MainApplication.getInstance().getApplicationContext(), dipValue);
    }

    public static int convertDipsToPixels(Resources resources, float dipValue) {
        return (int) (0.5F + dipValue * resources.getDisplayMetrics().density);
    }

    public static int convertSpsToPixels(Context context, float spValue) {
        return (int) (0.5F + spValue * context.getResources().getDisplayMetrics().scaledDensity);
    }

    public static int convertSpsToPixels(Resources resources, float spValue) {
        return (int) (0.5F + spValue * resources.getDisplayMetrics().scaledDensity);
    }

    public static int convertPixelsToDips(Context context, float pixelValue) {
        return (int) (pixelValue / context.getResources().getDisplayMetrics().density);
    }

    public static int convertPixelsToDips(Resources resources, float pixelValue) {
        return (int) (pixelValue / resources.getDisplayMetrics().density);
    }

    public static void setPaddingBottom(View view, int i) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), i);
    }

    public static void setPaddingLeft(View view, int i) {
        view.setPadding(i, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
    }

    public static void setPaddingRight(View view, int i) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), i, view.getPaddingBottom());
    }

    public static void setPaddingTop(View view, int i) {
        view.setPadding(view.getPaddingLeft(), i, view.getPaddingRight(), view.getPaddingBottom());
    }

    public static TouchDelegate expandBy(View view, int extraDp) {
        Rect bounds = new Rect();
        view.getHitRect(bounds);
        int extraPx = convertDipsToPixels(view.getContext(), extraDp);
        bounds.left = bounds.left - extraPx;
        bounds.top = bounds.top - extraPx;
        bounds.right = extraPx + bounds.right;
        bounds.bottom = extraPx + bounds.bottom;
        return new TouchDelegate(bounds, view);
    }

    public static TouchDelegate expandToVertically(View view, int topPx, int bottomPx) {
        Rect bounds = new Rect();
        view.getHitRect(bounds);
        bounds.top = topPx;
        bounds.bottom = bottomPx;
        return new TouchDelegate(bounds, view);
    }

    public static TouchDelegate expandToVerticallyAndFixedHorizontally(View view, int topPx, int bottomPx, int extraDp) {
        Rect bounds = new Rect();
        view.getHitRect(bounds);
        int extraPx = convertDipsToPixels(view.getContext(), extraDp);
        bounds.left = bounds.left - extraPx;
        bounds.right = extraPx + bounds.right;
        bounds.top = topPx;
        bounds.bottom = bottomPx;
        return new TouchDelegate(bounds, view);
    }


    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }


    @SuppressLint("NewApi")
    public static void setBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 16) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = MainApplication.getInstance().getApplicationContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = MainApplication.getInstance().getApplicationContext().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getActionBarHeight() {
        try {
            return MainApplication.getInstance().getApplicationContext().getResources().getDimensionPixelSize(R.dimen.abc_action_bar_default_height_material);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void setHardwareType(View view) {
        if (view != null && Build.VERSION.SDK_INT >= 11) {
            view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
    }

    public static void setNoneType(View view) {
        if (view != null && Build.VERSION.SDK_INT >= 18) {
            view.setLayerType(View.LAYER_TYPE_NONE, null);
        }
    }

}
