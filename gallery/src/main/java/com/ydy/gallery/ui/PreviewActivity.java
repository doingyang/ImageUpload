package com.ydy.gallery.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ydy.gallery.R;
import com.ydy.gallery.bean.ImageItem;
import com.ydy.gallery.ui.adapter.PreviewAdapter;
import com.ydy.gallery.widget.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 预览界面
 *
 * @author ydy
 */
public class PreviewActivity extends AppCompatActivity {

    /**
     * ViewPager
     */
    private ViewPager vpPreview;
    /**
     * Indicator
     */
    private CircleIndicator vpIndicator;
    /**
     * Adapter
     */
    private PreviewAdapter vpAdapter;
    /**
     * Data
     */
    private List<ImageItem> data;

    public static void start(Context context, ArrayList<ImageItem> data) {
        Intent intent = new Intent(context, PreviewActivity.class);
        intent.putExtra("preview_list", data);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        handleData();
        vpPreview = (ViewPager) findViewById(R.id.vp_preview);
        vpIndicator = (CircleIndicator) findViewById(R.id.vp_indicator);
        //设置适配器
        vpAdapter = new PreviewAdapter(this, data);
        vpPreview.setAdapter(vpAdapter);
        //绑定指示器
        vpIndicator.setViewPager(vpPreview);
        if (data.size() <= 1) {
            vpIndicator.setVisibility(View.GONE);
        }
    }

    private void handleData() {
        data = (List<ImageItem>) getIntent().getSerializableExtra("preview_list");
    }

}