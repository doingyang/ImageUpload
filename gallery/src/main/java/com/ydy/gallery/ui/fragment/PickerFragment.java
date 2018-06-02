package com.ydy.gallery.ui.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ydy.gallery.R;
import com.ydy.gallery.base.AbstractFragment;
import com.ydy.gallery.bean.FolderClickEvent;
import com.ydy.gallery.bean.ImageFolder;
import com.ydy.gallery.bean.ImageItem;
import com.ydy.gallery.ui.PreviewActivity;
import com.ydy.gallery.ui.adapter.PickerFragmentAdapter;
import com.ydy.gallery.ui.fragment.mvp.PickerFragmentContract;
import com.ydy.gallery.ui.fragment.mvp.PickerFragmentPresenter;
import com.ydy.gallery.utils.CameraHelper;
import com.ydy.gallery.utils.DensityUtil;
import com.ydy.gallery.utils.PickerConfig;
import com.ydy.gallery.utils.RxBus;
import com.ydy.gallery.utils.RxPickerManager;
import com.ydy.gallery.utils.ToastUtil;
import com.ydy.gallery.widget.DividerGridItemDecoration;
import com.ydy.gallery.widget.PopWindowManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.ydy.gallery.R.id.iv_select_preview;

/**
 * @author ydy
 */
public class PickerFragment extends AbstractFragment<PickerFragmentPresenter> implements PickerFragmentContract.View, View.OnClickListener {

    public static final int DEFAULT_SPAN_COUNT = 3;
    public static final int CAMERA_REQUEST = 0x001;
    public static final String MEDIA_RESULT = "MEDIA_RESULT";
    private static final int CAMERA_PERMISSION = 0x002;
    /**
     * 退出相册
     */
    private TextView galleryExit;
    /**
     * 相册切换
     */
    private LinearLayout albumSwitch;
    /**
     * 相册名称
     */
    private TextView albumName;
    /**
     * 相册箭头
     */
    private ImageView albumArrow;
    /**
     * 相册图片显示容器
     */
    private RecyclerView recyclerView;
    /**
     * 预览
     */
    private TextView ivSelectPreview;
    /**
     * 确定
     */
    private TextView tvSelectOk;

    private PickerFragmentAdapter adapter;
    private List<ImageFolder> allFolder;

    private PickerConfig config;
    private Disposable folderClicksubscribe;
    private Disposable imageItemsubscribe;


    public static PickerFragment newInstance() {
        return new PickerFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_picker;
    }

    @Override
    protected void initView(View view) {
        config = RxPickerManager.getInstance().getConfig();
        galleryExit = (TextView) view.findViewById(R.id.gallery_exit);
        albumSwitch = (LinearLayout) view.findViewById(R.id.album_switch);
        albumName = (TextView) view.findViewById(R.id.album_name);
        albumArrow = (ImageView) view.findViewById(R.id.album_arrow);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        ivSelectPreview = (TextView) view.findViewById(iv_select_preview);
        tvSelectOk = (TextView) view.findViewById(R.id.iv_select_ok);
        galleryExit.setOnClickListener(this);
        ivSelectPreview.setOnClickListener(this);
        tvSelectOk.setOnClickListener(this);
        //底部布局
        RelativeLayout rlBottom = (RelativeLayout) view.findViewById(R.id.rl_bottom);
        if (config != null) {
            rlBottom.setVisibility(config.isSingle() ? View.GONE : View.VISIBLE);
        }
        initRecycler();
        initObservable();
        loadData();
    }

    private void initObservable() {
        folderClicksubscribe = RxBus.singleton().toObservable(FolderClickEvent.class).subscribe(new Consumer<FolderClickEvent>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull FolderClickEvent folderClickEvent) throws Exception {
                String folderName = folderClickEvent.getFolder().getName();
                albumName.setText(folderName);
                refreshData(allFolder.get(folderClickEvent.getPosition()));
            }
        });
        imageItemsubscribe = RxBus.singleton().toObservable(ImageItem.class).subscribe(new Consumer<ImageItem>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull ImageItem imageItem) throws Exception {
                ArrayList<ImageItem> data = new ArrayList<>();
                data.add(imageItem);
                handleResult(data);
            }
        });
    }

    private void loadData() {
        presenter.loadAllImage(getActivity());
    }

    private void refreshData(ImageFolder folder) {
        adapter.setData(folder.getImages());
        adapter.notifyDataSetChanged();
    }

    private void initPopWindow(List<ImageFolder> data) {
        new PopWindowManager().init(albumSwitch, albumName, albumArrow, data);
    }

    private void initRecycler() {

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), DEFAULT_SPAN_COUNT);
        recyclerView.setLayoutManager(layoutManager);

        final DividerGridItemDecoration decoration = new DividerGridItemDecoration(getActivity());
        Drawable divider = decoration.getDivider();
        int imageWidth = DensityUtil.getDeviceWidth(getActivity()) / DEFAULT_SPAN_COUNT + divider.getIntrinsicWidth() * DEFAULT_SPAN_COUNT - 1;

        adapter = new PickerFragmentAdapter(imageWidth);
        adapter.setCameraClickListener(new CameraClickListener());
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                int selectedSize = adapter.getCheckImage().size();
                if (selectedSize == 0) {
                    ivSelectPreview.setText(getString(R.string.galley_picture_preview));
                    ivSelectPreview.setEnabled(false);
                    tvSelectOk.setText(getString(R.string.galley_picture_use));
                    tvSelectOk.setEnabled(false);
                } else {
                    ivSelectPreview.setText(getString(R.string.galley_picture_preview));
                    ivSelectPreview.setEnabled(true);
                    tvSelectOk.setText(getString(R.string.use_confirm, adapter.getCheckImage().size(), config.getMaxValue()));
                    tvSelectOk.setEnabled(true);
                }
            }
        });
        //初始值！！！
        int selectedSize = config.getCheckData().size();
        if (selectedSize == 0) {
            ivSelectPreview.setText(getString(R.string.galley_picture_preview));
            ivSelectPreview.setEnabled(false);
            tvSelectOk.setText(getString(R.string.galley_picture_use));
            tvSelectOk.setEnabled(false);
        } else {
            ivSelectPreview.setText(getString(R.string.galley_picture_preview));
            ivSelectPreview.setEnabled(true);
            tvSelectOk.setText(getString(R.string.use_confirm, selectedSize, config.getMaxValue()));
            tvSelectOk.setEnabled(true);
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //take camera
        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST) {
            handleCameraResult();
        }
    }

    private void handleCameraResult() {
        File file = CameraHelper.getTakeImageFile();
        if (null != file && file.exists()) {
            CameraHelper.scanPic(getActivity(), file);
            for (ImageFolder imageFolder : allFolder) {
                imageFolder.setChecked(false);
            }
            ImageFolder allImageFolder = allFolder.get(0);
            allImageFolder.setChecked(true);
            ImageItem item = new ImageItem(0, file.getAbsolutePath(), file.getName(), System.currentTimeMillis());
            item.setSelected(true);
            config.getCheckData().add(item.getPath());
            allImageFolder.getImages().add(0, item);
            tvSelectOk.setText(getString(R.string.use_confirm, config.getCheckData().size(), config.getMaxValue()));
            tvSelectOk.setEnabled(true);
            RxBus.singleton().post(new FolderClickEvent(0, allImageFolder));
        }
    }

    private void handleResult(ArrayList<ImageItem> data) {
        Intent intent = new Intent();
        intent.putExtra(MEDIA_RESULT, data);
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    @Override
    public void showAllImage(List<ImageFolder> datas) {
        allFolder = datas;
        adapter.setData(datas.get(0).getImages());
        adapter.notifyDataSetChanged();
        initPopWindow(datas);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (!folderClicksubscribe.isDisposed()) {
            folderClicksubscribe.dispose();
        }

        if (!imageItemsubscribe.isDisposed()) {
            imageItemsubscribe.dispose();
        }
    }

    @Override
    public void onClick(View v) {
        if (galleryExit == v) {
            //退出
            AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
            appCompatActivity.onBackPressed();
        } else if (tvSelectOk == v) {
            //确定
            int minValue = config.getMinValue();
            ArrayList<ImageItem> checkImage = adapter.getCheckImage();
            if (checkImage.size() < minValue) {
                ToastUtil.showToast(getActivity(), getString(R.string.min_image, minValue));
                return;
            }
            handleResult(checkImage);
        } else if (ivSelectPreview == v) {
            //预览
            ArrayList<ImageItem> checkImage = adapter.getCheckImage();
            if (checkImage.isEmpty()) {
                ToastUtil.showToast(getActivity(), getString(R.string.select_one_image));
                return;
            }
            PreviewActivity.start(getActivity(), checkImage);
        }
    }

    @TargetApi(23)
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
        } else {
            takePictures();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePictures();
            } else {
                ToastUtil.showToast(getActivity(), getString(R.string.permissions_error));
            }
        }
    }

    private void takePictures() {
        if (adapter != null) {
            config.setCheckData(adapter.getCheckData());
        }
        if (config.getCheckData().size() >= config.getMaxValue()){
            ToastUtil.showToast(getActivity(), getActivity().getString(R.string.max_select, config.getMaxValue() + ""));
        }else {
            CameraHelper.take(PickerFragment.this, CAMERA_REQUEST);
        }
    }

    private class CameraClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermission();
            } else {
                takePictures();
            }
        }
    }

}
