package com.project.upload.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.project.upload.R;

import java.util.List;

/**
 * **************************************************
 * 文件名称 : ImageAdapter
 * 作    者 : Created by ydy
 * 创建时间 : 2018/5/31 18:02
 * 文件描述 :
 * 注意事项 :
 * 修改历史 : 2018/5/31 1.00 初始版本
 * **************************************************
 */
public class ImageAdapter extends BaseAdapter {

    private Context context;
    private List<String> imageList;
    private LayoutInflater inflater;

    public ImageAdapter(Context context, List<String> imageList) {
        this.context = context;
        this.imageList = imageList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imageList == null ? 0 : imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_image_upload, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(imageList.get(position)).into(holder.iv);

        return convertView;
    }

    static class ViewHolder {

        ImageView iv;

        public ViewHolder(View view) {
            view.setTag(this);
            iv = view.findViewById(R.id.iv_item_image);
        }
    }
}
