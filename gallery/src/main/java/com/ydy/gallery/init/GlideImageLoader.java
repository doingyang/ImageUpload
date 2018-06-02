package com.ydy.gallery.init;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ydy.gallery.R;
import com.ydy.gallery.utils.RxPickerImageLoader;

/**
 * @author ydy
 */
public class GlideImageLoader implements RxPickerImageLoader {

    @Override
    public void display(ImageView imageView, String path, int width, int height) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_preview_image)
                .error(R.drawable.ic_preview_error)
                .override(width, height)
                .centerCrop();
        Glide.with(imageView.getContext())
                .load(path)
                .apply(options)
                .into(imageView);
    }

}
