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

public class UploadActivity extends AppCompatActivity {

    private GridView gvImage;
    private Button btnSelect;
    private Button btnUpload;

    private List<String> imageList = new ArrayList<>();
    private ImageAdapter adapter;
    private String base64Data;
    private String merchantId;
    private String timeStamp;
    private String md5Sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        initView();
        setListener();
        initData();
    }

    private void initData() {
        timeStamp = String.valueOf((int) (System.currentTimeMillis() / 1000));
        String jsonStr = "{\"business_code\":\"img_upload\"}";
        base64Data = Base64.encodeToString(jsonStr.getBytes(), Base64.NO_WRAP);
        merchantId = "THINKIVEFILE";
        String signKey = "53e079663bfe4f0bc4cb14f9a819e3d5";
        Map<String, String> map = new HashMap<>();
        map.put("data", base64Data);
        map.put("merchant_id", merchantId);
        map.put("signKey", signKey);
        map.put("timestamp", timeStamp);
        String signPaper = SignUtil.getSignCheckAPIInterfaceContent(map);
        md5Sign = MD5Util.md5Encode(signPaper, null);
    }

    public MultipartBody formMultipartBody(List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            builder.addFormDataPart("file", file.getName(), requestBody);
        }
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("data", base64Data);
        builder.addFormDataPart("merchant_id", merchantId);
        builder.addFormDataPart("sign", md5Sign);
        builder.addFormDataPart("timestamp", timeStamp);
        return builder.build();
    }

    private void uploadImage() {
        List<File> fileList = new ArrayList<>();
        if (imageList.size() > 0) {
            for (int i = 0; i < imageList.size(); i++) {
                String path = imageList.get(i);
                File file = new File(path);
                if (file.exists()) {
                    fileList.add(file);
                }
            }
        }
        MultipartBody body = formMultipartBody(fileList);
        HttpUtils
                .fileUpload()
                .uploadFileWithRequestBody(body)
                .enqueue(new Callback<FileUploadResponse>() {
                    @Override
                    public void onResponse(Call<FileUploadResponse> call, Response<FileUploadResponse> response) {
                        if (response.isSuccessful()) {
                            String resp = response.body().toString();
                            Log.i("TAG", "onResponse: " + resp);
                        } else {
                            Log.i("TAG", "onResponse: 失败");
                        }
                    }

                    @Override
                    public void onFailure(Call<FileUploadResponse> call, Throwable t) {
                        Log.i("TAG", "onFailure: 网络...");
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
                        imageList.clear();
                        for (ImageItem imageItem : imageItems) {
                            String path = imageItem.getPath();
                            File file = new File(path);
                            if (file.exists()) {
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
        gvImage = (GridView) findViewById(R.id.gv_image);
        btnSelect = (Button) findViewById(R.id.btn_select);
        btnUpload = (Button) findViewById(R.id.btn_upload);
        adapter = new ImageAdapter(this, imageList);
        gvImage.setAdapter(adapter);
    }
}
