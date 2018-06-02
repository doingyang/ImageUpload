package com.ydy.gallery.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.ydy.gallery.R;
import com.ydy.gallery.status.StatusBar;
import com.ydy.gallery.ui.fragment.PickerFragment;
import com.ydy.gallery.utils.ToastUtil;

/**
 * @author ydy
 */
public class RxPickerActivity extends AppCompatActivity {

    public static final String TAG = "YDY";
    private static final int READ_STORAGE_PERMISSION = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);
        StatusBar.apply(this, true);
//        setStatusBarColor();
    }

    /**
     * 设置状态栏颜色
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#47BD6B"));
        }
    }

    @TargetApi(16)
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RxPickerActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_PERMISSION);
        } else {
            setupFragment();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_STORAGE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupFragment();
            } else {
                ToastUtil.showToast(RxPickerActivity.this, getString(R.string.permissions_error));
                finish();
            }
        }
    }

    private void setupFragment() {
        String tag = PickerFragment.class.getSimpleName();
        PickerFragment fragment = (PickerFragment) getFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            fragment = PickerFragment.newInstance();
        }
        getFragmentManager().beginTransaction().replace(R.id.fl_container, fragment, tag).commitAllowingStateLoss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestPermission();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!hasPermission(this, Manifest.permission.CAMERA) || !hasPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            finish();
        }
    }

    private boolean hasPermission(Context context, String permission) {
        return (PackageManager.PERMISSION_GRANTED ==
                PermissionChecker.checkSelfPermission(context, permission));
    }

}
