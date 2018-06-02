package com.ydy.gallery.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * @author ydy
 */
public final class DensityUtil {

    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

    public static float px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return pxValue / scale;
    }

    public static float px2sp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return pxValue / scale;
    }

    public static int getScreenW(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    public static int getScreenH(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    public static int getDeviceWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return manager.getDefaultDisplay().getWidth();
    }

    public static int getDeviceHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return manager.getDefaultDisplay().getHeight();
    }

    public static Screen getScreenPixels(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        Screen screen = new Screen();
        // e.g. 1080
        screen.widthPixels = dm.widthPixels;
        // e.g. 1920
        screen.heightPixels = dm.heightPixels;
        // e.g. 480
        screen.densityDpi = dm.densityDpi;
        // e.g. 2.0
        screen.density = dm.density;
        return screen;
    }

    public static void backgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        //0.0-1.0
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }

    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获得状态栏高度(当前是沉浸试通知栏)
     */
    public static int getStatusBarHeightImmersive(Activity activity) {
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return activity.getResources().getDimensionPixelSize(resourceId);
    }

    public static float getDensity(Context context) {
        // 获取屏幕密度（方法2）
        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        float density = dm.density;
        // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
        int densityDPI = dm.densityDpi;
        return density;
    }

    public static class Screen {
        public int widthPixels;
        public int heightPixels;
        public int densityDpi;
        public float density;
    }

}
