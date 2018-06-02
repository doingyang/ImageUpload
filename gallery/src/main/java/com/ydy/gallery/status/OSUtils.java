package com.ydy.gallery.status;

import android.os.Build;

import com.ydy.gallery.BuildConfig;


/**
 * @author ydy
 */

public class OSUtils {

    private static final String PACKAGE_NAME = BuildConfig.APPLICATION_ID;
    private static final String XIAO_MI = "Xiaomi";
    private static final String HUA_WEI = "HUAWEI";
    private static final String MEI_ZU = "Meizu";
    private static final String VIVO = "vivo";
    private static final String SAMSUNG = "samsung";
    private static final String OPPO = "OPPO";

    public static boolean isXiaoMi() {
        return Build.MANUFACTURER.equals(XIAO_MI);
    }

    public static boolean isHuaWei() {
        return Build.MANUFACTURER.equals(HUA_WEI);
    }

    public static boolean isMeiZu() {
        return Build.MANUFACTURER.equals(MEI_ZU);
    }

    public static boolean isSamSung() {
        return Build.MANUFACTURER.equals(SAMSUNG);
    }

    public static boolean isOPPO() {
        return Build.MANUFACTURER.equals(OPPO);
    }

    public static boolean isOSM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
