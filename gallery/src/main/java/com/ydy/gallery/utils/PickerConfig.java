package com.ydy.gallery.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ydy
 */
public class PickerConfig {

    public static final int SINGLE_IMG = 0x001;
    public static final int MULTIPLE_IMG = 0x002;
    private int minValue = 1;
    private int maxValue = 9;
    private boolean showCamera = true;
    private int mode = SINGLE_IMG;
    private List<String> checkData;

    public int getMode() {
        return mode;
    }

    public void setMode(@Mode int mode) {
        this.mode = mode;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setLimit(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public boolean isShowCamera() {
        return showCamera;
    }

    public void setShowCamera(boolean showCamera) {
        this.showCamera = showCamera;
    }

    public boolean isSingle() {
        return SINGLE_IMG == mode;
    }

    public List<String> getCheckData() {
        if (null == checkData) {
            checkData = new ArrayList<>();
        }
        return checkData;
    }

    public void setCheckData(List<String> checkData) {
        this.checkData = new ArrayList<>();
        this.checkData = checkData;
    }

    @IntDef({SINGLE_IMG, MULTIPLE_IMG})
    @Retention(RetentionPolicy.SOURCE)
    @interface Mode {
    }
}
