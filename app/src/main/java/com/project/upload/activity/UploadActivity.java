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

import com.google.gson.Gson;
import com.project.upload.R;
import com.project.upload.adapter.ImageAdapter;
import com.project.upload.bean.UploadResponse;
import com.project.upload.http.HttpUtils;
import com.project.upload.rsa.MD5Util;
import com.project.upload.rsa.SignUtil;
import com.ydy.gallery.init.RxPicker;
import com.ydy.gallery.bean.ImageItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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

    private Map<String, RequestBody> requestBodyMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        initView();
        setListener();
        initData();
    }

    private void initData() {
        Map<String, String> dataMap = new HashMap();
        dataMap.put("business_code", "png_upload");
        String jsonStr = new Gson().toJson(dataMap);
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
        requestBodyMap = new HashMap<>();
        requestBodyMap.put("data", HttpUtils.convertToRequestBody(base64Data));
        requestBodyMap.put("merchant_id", HttpUtils.convertToRequestBody(merchantId));
        requestBodyMap.put("sign", HttpUtils.convertToRequestBody(md5Sign));
        requestBodyMap.put("timestamp", HttpUtils.convertToRequestBody(timeStamp));
    }


    @SuppressLint("CheckResult")
    private void uploadImage() {
        HttpUtils
                .init()
                .uploadFilesWithParts(requestBodyMap, HttpUtils.fileToMultipartBodyPart2(fileList))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UploadResponse>() {
                    @Override
                    public void accept(UploadResponse uploadResponse) throws Exception {
                        if (null != uploadResponse) {
                            Log.i("TAG", "onResponse: 成功" + uploadResponse.toString());
                        } else {
                            Log.i("TAG", "onResponse: 失败");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
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
                /*uploadImageOkHttp();*/
            }
        });
    }

    private void uploadImageOkHttp() {
        UploadHelper.uploadMultiFiles(fileList, new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    Log.i("TAG", "onResponse: " + body.string());
                            /*{
                                "error_no":"0",
                                "results":[
                                    {
                                        "upload_id":"f43438c3788647d4b11843cbc1c4bb48",
                                        "file_path":"/20180605/201806051528183146257.jpg",
                                        "high_file_addr":"",
                                        "low_file_addr":"",
                                        "file_name":"20741-102.jpg",
                                        "is_safe":"0",
                                        "middle_file_addr":""
                                    }
                                ],
                                "dsName":[
                                    "results"
                                ],
                                "error_info":"上传文件成功"
                            }*/
                }
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
