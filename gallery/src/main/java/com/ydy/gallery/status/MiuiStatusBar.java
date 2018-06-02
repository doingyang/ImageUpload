package com.ydy.gallery.status;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Window;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author ydy
 */

public class MiuiStatusBar implements IStatusBar {

    /**
     * 设置状态栏字体图标为深色，需要MIUI6以上
     *
     * @param isFontColorDark 是否把状态栏字体及图标颜色设置为深色
     */
    @SuppressLint("PrivateApi")
    @Override
    public void setStatusBarLightMode(Activity activity, boolean isFontColorDark) {
        Window window = activity.getWindow();
        try {
            if (window != null) {
                Class clazz = window.getClass();
                try {
                    int darkModeFlag = 0;
                    Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                    Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                    darkModeFlag = field.getInt(layoutParams);
                    Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                    if (isFontColorDark) {
                        //状态栏透明且黑色字体
                        extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
                    } else {
                        //清除黑色字体
                        extraFlagField.invoke(window, 0, darkModeFlag);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
