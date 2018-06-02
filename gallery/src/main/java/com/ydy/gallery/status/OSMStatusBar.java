package com.ydy.gallery.status;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * @author ydy
 */

public class OSMStatusBar implements IStatusBar {

    @Override
    public void setStatusBarLightMode(Activity activity, boolean isFontColorDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isFontColorDark) {
                //如果是6.0以上将状态栏文字改为黑色，并设置状态栏颜色
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                activity.getWindow().setStatusBarColor(Color.parseColor("#ffffff"));
                //设置fitsSystemWindow 为 true, 预留系统栏位置.
                ViewGroup mContentView = (ViewGroup) activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
                View mChildView = mContentView.getChildAt(0);
                if (mChildView != null) {
                    ViewCompat.setFitsSystemWindows(mChildView, true);
                    ViewCompat.requestApplyInsets(mChildView);
                }
            } else {
                int uiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();
                activity.getWindow().getDecorView().setSystemUiVisibility(uiVisibility | View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }

}
