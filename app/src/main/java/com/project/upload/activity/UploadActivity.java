package com.project.upload.activity;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.project.upload.R;
import com.project.upload.adapter.ImageAdapter;
import com.project.upload.bean.FileUploadResponse;
import com.project.upload.http.HttpUtils;
import com.project.upload.rsa.MD5Util;
import com.project.upload.rsa.SignUtil;
import com.ydy.gallery.init.RxPicker;
import com.ydy.gallery.bean.ImageItem;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author ydy
 */
public class UploadActivity extends AppCompatActivity {

    private Button btnSelect;
    private Button btnUpload;

    private List<File> fileList = new ArrayList<>();
    private List<String> imageList = new ArrayList<>();
    private ImageAdapter adapter;

    private Map<String, String> paramsMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        initView();
        setListener();
        initData();
    }

    private void initData() {
        String jsonStr = "{\"business_code\":\"img_upload\"}";
        String base64Data = Base64.encodeToString(jsonStr.getBytes(), Base64.NO_WRAP);
        String merchantId = "THINKIVEFILE";
        String signKey = "53e079663bfe4f0bc4cb14f9a819e3d5";
        String timeStamp = String.valueOf(System.currentTimeMillis());
        Map<String, String> map = new HashMap<>();
        map.put("data", base64Data);
        map.put("merchant_id", merchantId);
        map.put("signKey", signKey);
        map.put("timestamp", timeStamp);
        //生成摘要
        String signPaper = SignUtil.getSignCheckAPIInterfaceContent(map);
        //MD5签名
        String md5Sign = MD5Util.md5Encode(signPaper, null);
        //请求参数
        paramsMap = new HashMap<>();
        paramsMap.put("data", base64Data);
        paramsMap.put("merchant_id", merchantId);
        paramsMap.put("sign", md5Sign);
        paramsMap.put("timestamp", timeStamp);
    }

    private void uploadImage() {
        List<MultipartBody.Part> parts = HttpUtils.filesToMultipartBodyParts(fileList);
        HttpUtils
                .fileUpload()
                .uploadFilesWithParts(parts, paramsMap)
                .enqueue(new Callback<FileUploadResponse>() {
                    @Override
                    public void onResponse(Call<FileUploadResponse> call, Response<FileUploadResponse> response) {
                        if (response.isSuccessful()) {
                            FileUploadResponse resp = response.body();
                            Log.i("TAG", "onResponse: 成功" + resp.getError_no());
                            Log.i("TAG", "onResponse: 成功" + resp.getError_info());
                        } else {
                            Log.i("TAG", "onResponse: 失败");
                        }
                    }

                    @Override
                    public void onFailure(Call<FileUploadResponse> call, Throwable t) {
                        Log.i("TAG", "onFailure: 连接失败");
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void selectImage() {
        RxPicker.of()
                .single(false)
                .camera(true)
                .limit(1, 3)
                .check(imageList)
                .start(this)
                .subscribe(new Consumer<List<ImageItem>>() {
                    @Override
                    public void accept(@NonNull List<ImageItem> imageItems) throws Exception {
                        fileList.clear();
                        imageList.clear();
                        for (ImageItem imageItem : imageItems) {
                            String path = imageItem.getPath();
                            File file = new File(path);
                            if (file.exists()) {
                                fileList.add(file);
                                imageList.add(path);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void setListener() {
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }

    private void initView() {
        btnSelect = (Button) findViewById(R.id.btn_select);
        btnUpload = (Button) findViewById(R.id.btn_upload);
        GridView gvImage = (GridView) findViewById(R.id.gv_image);
        adapter = new ImageAdapter(this, imageList);
        gvImage.setAdapter(adapter);
    }
}
