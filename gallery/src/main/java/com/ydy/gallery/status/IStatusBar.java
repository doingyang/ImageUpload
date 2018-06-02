package com.ydy.gallery.status;

import android.app.Activity;

/**
 * @author ydy
 */
public interface IStatusBar {

    /**
     * setStatusBarLightMode
     *
     * @param activity        activity
     * @param isFontColorDark isFontColorDark
     */
    void setStatusBarLightMode(Activity activity, boolean isFontColorDark);
}
