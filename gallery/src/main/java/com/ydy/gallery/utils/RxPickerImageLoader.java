package com.ydy.gallery.utils;

import android.widget.ImageView;

/**
 * @author ydy
 */
public interface RxPickerImageLoader {

    /**
     * display
     * @param imageView imageView
     * @param path path
     * @param width width
     * @param height height
     */
    void display(ImageView imageView, String path, int width, int height);

}
