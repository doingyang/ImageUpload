package com.project.upload.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Author: ydy
 * Created: 2017/3/30 15:19
 * Description:自定义Dialog
 */

public class ConfirmDialog extends DialogFragment implements DialogInterface.OnClickListener {

    /**
     * Dialog界面文字key值
     */
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_MESSAGE = "extra_message";
    public static final String EXTRA_POSITIVE_TEXT = "extra_positive_text";
    public static final String EXTRA_NEGATIVE_TEXT = "extra_negative_text";

    /**
     * 两个按钮的点击事件
     */
    private IPositiveClickListener positiveClickListener;
    private INegativeClickListener negativeClickListener;

    /**
     * 初始化Dialog：newInstance的方式
     * @param title     标题
     * @param message   描述信息
     * @param positiveText  确认文本
     * @param negativeText  取消文本
     * @return  Dialog对象
     */
    public static ConfirmDialog newInstance(String title, String message, String positiveText, String negativeText){
        ConfirmDialog dialog = new ConfirmDialog();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TITLE, title);
        bundle.putString(EXTRA_MESSAGE, message);
        bundle.putString(EXTRA_POSITIVE_TEXT, positiveText);
        bundle.putString(EXTRA_NEGATIVE_TEXT, negativeText);
        dialog.setArguments(bundle);
        return dialog;
    }

    /**
     * 获取Dialog界面属性值
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //取值
        String title = getArguments().getString(EXTRA_TITLE);
        String message = getArguments().getString(EXTRA_MESSAGE);
        final String positiveText = getArguments().getString(EXTRA_POSITIVE_TEXT);
        final String negativeText = getArguments().getString(EXTRA_NEGATIVE_TEXT);
        //填值
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, this);
        builder.setNegativeButton(negativeText, this);
        return builder.create();
    }

    public static interface IPositiveClickListener {
        public void onPositiveButtonClick();
    }

    public static interface INegativeClickListener {
        public void onNegativeButtonClick();
    }

    public void setPositiveClickListener(IPositiveClickListener positiveClickListener) {
        this.positiveClickListener = positiveClickListener;
    }

    public void setNegativeClickListener(INegativeClickListener negativeClickListener) {
        this.negativeClickListener = negativeClickListener;
    }

    /**
     * builder.setPositiveButton(positiveText, this);
     * builder.setNegativeButton(negativeText, this);
     * @param dialog
     * @param which
     */
    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

}
