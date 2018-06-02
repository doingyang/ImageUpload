package com.ydy.gallery.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

/**
 * @author ydy
 */
public class ToastUtil {

    private static Toast toast;

    @SuppressLint("ShowToast")
    public static void showToast(Context context, String message) {
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }
}
