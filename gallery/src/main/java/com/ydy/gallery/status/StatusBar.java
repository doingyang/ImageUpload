package com.ydy.gallery.status;

import android.app.Activity;
import android.os.Build;

/**
 * @author ydy
 */

public class StatusBar {

    /**
     * apply
     *
     * @param activity   activity
     * @param isDarkFont isDarkFont
     */
    public static void apply(Activity activity, boolean isDarkFont) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            IStatusBar statusBar = null;
            if (OSUtils.isMeiZu()) {
                // is MeiZu
                statusBar = new MeizuStatusBar();
            } else if (OSUtils.isXiaoMi()) {
                // is XiaoMi
                statusBar = new MiuiStatusBar();
            } else if (OSUtils.isOSM()) {
                // is OS M
                statusBar = new OSMStatusBar();
            } else {
                // 都不是
            }
            if (statusBar != null) {
                statusBar.setStatusBarLightMode(activity, isDarkFont);
            }
        }
    }

}
