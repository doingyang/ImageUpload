package com.project.upload.activity;


import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by dltao on 2018/5/29.
 * okHttp图片上传工具类
 */

public class UploadHelper {

    private static String url="http://192.168.11.172:9901/servlet/file/SignUploadAction";

    private static String imgUrl="http://192.168.11.172:9901/tmp/file/unsafty/";

    private static final OkHttpClient.Builder mHttpClientBuilder = new OkHttpClient.Builder();

    /**
     * 图片上传方法
     */
    public static void uploadMultiFiles(List<File> files, Callback callback) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
            builder.addFormDataPart("file", file.getName(), fileBody);
        }
        addParams(builder);

        MultipartBody multipartBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(multipartBody)
                .build();

        mHttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS).addInterceptor(interceptor);
        OkHttpClient okHttpClient = mHttpClientBuilder.build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    /***
     * 需要传递的参数
     * @return
     */
    public static void  addParams(MultipartBody.Builder builder){
        Map dataMap = new HashMap();
        dataMap.put("business_code", "png_upload");
//      dataMap.put("loginId", "admin");
//      dataMap.put("password", "admin8888");
        //请求参数
        String data=new Gson().toJson(dataMap);
        //转成base64字符串
//        data= Base64Util.encode(data);
        data= "";
        System.out.println(data);
        //时间戳
        String timestamp=String.valueOf(System.currentTimeMillis());
        //商户id
        String merchant_id="THINKIVEFILE";
        //签名参数
        //将参数拼接成签名字符串
        String signStr ="data="+data
                +"&merchant_id="+merchant_id
                +"&signKey=53e079663bfe4f0bc4cb14f9a819e3d5"
                +"&timestamp="+timestamp;
        //签名结果值
//        String sign= MD5Util.getMD5String(signStr);
        String sign= "";

        builder.addFormDataPart("timestamp",timestamp);
        builder.addFormDataPart("data",data);
        builder.addFormDataPart("merchant_id",merchant_id);
        builder.addFormDataPart("sign",sign);
    }

    /*****
     * Interceptor 接口只包含一个方法 intercept，
     * 其参数是 Chain 对象。Chain 对象表示的是当前的拦截器链条。
     * 通过 Chain 的 request 方法可以获取到当前的 Request 对象。
     * 在使用完 Request 对象之后，通过 Chain 对象的 proceed 方法来继续拦截器链条的执行。
     * 当执行完成之后，可以对得到的 Response 对象进行额外的处理。
     */
    private static Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            return chain.proceed(builder.build());
        }
    };


    public static String getAbusoluteUrl(String path){
        return imgUrl+path;
    }

}
