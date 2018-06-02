package com.ydy.gallery.widget;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ydy.gallery.R;
import com.ydy.gallery.bean.ImageFolder;
import com.ydy.gallery.ui.adapter.PickerAlbumAdapter;
import com.ydy.gallery.utils.DensityUtil;

import java.util.List;

/**
 * 相册集PopWindow管理
 *
 * @author ydy
 */
public class PopWindowManager {

    private PopupWindow mAlbumPopWindow;
    private PickerAlbumAdapter albumAdapter;
    private ImageView albumArrow;

    public void init(LinearLayout albumSwitch, final TextView albumName, final ImageView albumArrow, final List<ImageFolder> data) {
        this.albumArrow = albumArrow;
        albumAdapter = new PickerAlbumAdapter(data, DensityUtil.dp2px(albumName.getContext(), 80));
        albumAdapter.setDismissListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAlbumWindow();
                albumArrow.setImageResource(R.drawable.icon_xiala_photo);
            }
        });

        albumSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopWindow(v, data, albumAdapter);
                albumArrow.setImageResource(R.drawable.icon_shangla_photo);
            }
        });
    }

    private void showPopWindow(View v, List<ImageFolder> data, PickerAlbumAdapter albumAdapter) {
        if (mAlbumPopWindow == null) {
            View windowView = createWindowView(v, albumAdapter);
            mAlbumPopWindow = new PopupWindow(windowView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
            mAlbumPopWindow.setAnimationStyle(R.style.RxPicker_PopupAnimation);
            mAlbumPopWindow.setBackgroundDrawable(new ColorDrawable());
            mAlbumPopWindow.setOutsideTouchable(true);
            mAlbumPopWindow.setFocusable(true);
        }
        mAlbumPopWindow.showAsDropDown(v, 0, 0);
        mAlbumPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                albumArrow.setImageResource(R.drawable.icon_xiala_photo);
            }
        });
    }

    @SuppressLint("InflateParams")
    @NonNull
    private View createWindowView(View clickView, PickerAlbumAdapter albumAdapter) {
        //POP相册集，里面是个RecyclerView
        View view = LayoutInflater.from(clickView.getContext()).inflate(R.layout.item_popwindow_album, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.album_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        View albumShadowLayout = view.findViewById(R.id.album_shadow);
        albumShadowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAlbumWindow();
            }
        });
        recyclerView.setAdapter(albumAdapter);
        return view;
    }

    private void dismissAlbumWindow() {
        if (mAlbumPopWindow != null && mAlbumPopWindow.isShowing()) {
            mAlbumPopWindow.dismiss();
        }
    }

}
