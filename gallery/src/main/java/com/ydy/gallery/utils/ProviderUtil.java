package com.ydy.gallery.utils;

import android.content.Context;

/**
 * @author ydy
 */
public class ProviderUtil {

    public static String getFileProviderName(Context context) {
        return context.getPackageName() + ".provider";
    }
}
