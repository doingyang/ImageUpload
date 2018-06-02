package com.project.upload;

import android.support.multidex.MultiDexApplication;

import com.ydy.gallery.init.GlideImageLoader;
import com.ydy.gallery.init.RxPicker;

/**
 * @author : ydy
 */
public class MyApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        RxPicker.init(new GlideImageLoader());
    }
}
