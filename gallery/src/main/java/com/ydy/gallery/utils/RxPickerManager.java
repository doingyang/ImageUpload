package com.ydy.gallery.utils;

import android.content.Intent;
import android.widget.ImageView;

import com.ydy.gallery.bean.ImageItem;
import com.ydy.gallery.ui.fragment.PickerFragment;

import java.util.List;

/**
 * @author ydy
 */
public class RxPickerManager {

    private volatile static RxPickerManager manager;
    private PickerConfig config;
    private RxPickerImageLoader imageLoader;

    private RxPickerManager() {
    }

    public static RxPickerManager getInstance() {
        if (manager == null) {
            synchronized (RxPickerManager.class) {
                if (manager == null) {
                    manager = new RxPickerManager();
                }
            }
        }
        return manager;
    }

    public PickerConfig getConfig() {
        return config == null ? new PickerConfig() : config;
    }

    public RxPickerManager setConfig(PickerConfig config) {
        this.config = config;
        return this;
    }

    public void init(RxPickerImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    public void setMode(int mode) {
        config.setMode(mode);
    }

    public void showCamera(boolean showCamera) {
        config.setShowCamera(showCamera);
    }

    public void limit(int minValue, int maxValue) {
        config.setLimit(minValue, maxValue);
    }

    public void display(ImageView imageView, String path, int width, int height) {
        if (imageLoader == null) {
            throw new NullPointerException("You must fist of all call 'RxPicker.init()' to initialize");
        }
        imageLoader.display(imageView, path, width, height);
    }

    public List<ImageItem> getResult(Intent intent) {
        return (List<ImageItem>) intent.getSerializableExtra(PickerFragment.MEDIA_RESULT);
    }
}
