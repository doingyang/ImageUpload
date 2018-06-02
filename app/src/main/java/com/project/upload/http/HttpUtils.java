package com.project.upload.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HttpUtils {

    public static String URL_UPLOAD = "http://192.168.11.172:9901/servlet/file/SignUploadAction";
    private static String URL_DOWNLOAD = "http://192.168.11.172:9901/tmp/file/unsafty";

//    public static final String BASE_URL = "http://www.1688wan.com/";
    public static final String BASE_URL = "http://192.168.11.172:9901/";

    private static HttpService mHttpService;

    public static HttpService init() {
        if (mHttpService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    //返回值为String的支持
                    .addConverterFactory(ScalarsConverterFactory.create())
                    //返回值为Gson的支持(以实体类返回)
                    .addConverterFactory(GsonConverterFactory.create())
                    //返回值为Observable<ToastUtil>的支持
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            mHttpService = retrofit.create(HttpService.class);
        }
        return mHttpService;
    }

    //---------------------------------------------------------------------------------------------
    /**
     * 构造MultipartBody
     * 把File对象转化成MultipartBody
     * @param files 文件集合
     */
    public static MultipartBody filesToMultipartBody(List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (File file : files) {
            // TODO: 16-4-2  这里没有判断file的类型
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            builder.addFormDataPart("file", file.getName(), requestBody);
        }
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }

    /**
     * 把File转化成MultipartBody.Part
     * @param files 文件集合
     */
    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (File file : files) {
            // TODO: 16-4-2  这里没有判断file的类型
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }

    /**
     * 文件上传配置
     */
    public synchronized static HttpService fileUpload() {
        if (mHttpService == null) {
            //打印请求到的json字符串和查看log
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .retryOnConnectionFailure(true)
                    .build();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(gson);
            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            mHttpService = retrofit.create(HttpService.class);
        }
        return mHttpService;
    }

}
