package com.ydy.gallery.ui.adapter;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ydy.gallery.R;
import com.ydy.gallery.bean.ImageItem;
import com.ydy.gallery.utils.PickerConfig;
import com.ydy.gallery.utils.RxBus;
import com.ydy.gallery.utils.RxPickerManager;
import com.ydy.gallery.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ydy
 */
public class PickerFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int CAMERA_TYPE = 0;
    private static final int NORMAL_TYPE = 1;

    private View.OnClickListener cameraClickListener;

    private int imageWidth;
    private PickerConfig config;

    private List<ImageItem> datas;
    private List<ImageItem> checkImage;

    public PickerFragmentAdapter(int imageWidth) {
        config = RxPickerManager.getInstance().getConfig();
        this.imageWidth = imageWidth;
        if (null == checkImage) {
            checkImage = new ArrayList<>();
        }
        if (null == datas) {
            datas = new ArrayList<>();
        }
        transform();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (CAMERA_TYPE == viewType) {
            return new CameraViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_camera, parent, false));
        } else {
            return new PickerViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picker, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CameraViewHolder) {
            holder.itemView.setOnClickListener(cameraClickListener);
            return;
        }
        final int dataPosition = config.isShowCamera() ? position - 1 : position;

        final ImageItem imageItem = datas.get(dataPosition);
        PickerViewHolder pickerViewHolder = (PickerViewHolder) holder;
        pickerViewHolder.bind(imageItem);
        pickerViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (config.isSingle()) {
                    RxBus.singleton().post(imageItem);
                } else {
                    int maxValue = config.getMaxValue();
                    if (checkImage.size() >= maxValue && !checkImage.contains(imageItem)) {
                        ToastUtil.showToast(holder.itemView.getContext(), holder.itemView.getContext().getString(R.string.max_select, config.getMaxValue() + ""));
                        return;
                    }
                    if (checkImage.contains(imageItem)) {
                        checkImage.remove(imageItem);
                        datas.get(dataPosition).setSelected(false);
                    } else {
                        checkImage.add(imageItem);
                        datas.get(dataPosition).setSelected(true);
                    }
                    notifyItemChanged(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (datas != null && RxPickerManager.getInstance().getConfig().isShowCamera()) {
            return datas.size() + 1;
        } else if (datas != null) {
            return datas.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (config.isShowCamera() && position == 0) {
            return CAMERA_TYPE;
        } else {
            return NORMAL_TYPE;
        }
    }

    public void setData(List<ImageItem> data) {
        this.datas = data;
        transform();
    }

    public void setCameraClickListener(View.OnClickListener cameraClickListener) {
        this.cameraClickListener = cameraClickListener;
    }

    public ArrayList<ImageItem> getCheckImage() {
        return (ArrayList<ImageItem>) checkImage;
    }

    private void transform() {
        List<String> check = RxPickerManager.getInstance().getConfig().getCheckData();
        for (int i = 0; i < datas.size(); i++) {
            for (int j = 0; j < check.size(); j++) {
                if (datas.get(i).getPath().equals(check.get(j))) {
                    datas.get(i).setSelected(true);
                    if (!checkImage.contains(datas.get(i))) {
                        checkImage.add(datas.get(i));
                    }
                }
            }
        }
    }

    public List<String> getCheckData() {
        List<String> result = new ArrayList<>();
        for (ImageItem item : checkImage) {
            result.add(item.getPath());
        }
        return result;
    }

    private class PickerViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private AppCompatCheckBox cbCheck;

        private PickerViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_image);
            cbCheck = (AppCompatCheckBox) itemView.findViewById(R.id.cb_check);
        }

        private void bind(ImageItem imageItem) {
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.width = imageWidth;
            layoutParams.height = imageWidth;
            imageView.setLayoutParams(layoutParams);

            RxPickerManager.getInstance().display(imageView, imageItem.getPath(), imageWidth, imageWidth);
            cbCheck.setVisibility(config.isSingle() ? View.GONE : View.VISIBLE);
            cbCheck.setChecked(checkImage.contains(imageItem));
        }
    }

    private class CameraViewHolder extends RecyclerView.ViewHolder {
        private CameraViewHolder(View itemView) {
            super(itemView);
        }
    }

}


