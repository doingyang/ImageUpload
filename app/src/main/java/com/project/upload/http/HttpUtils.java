package com.project.upload.http;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author ydy
 */
public class HttpUtils {

    private static String URL_UPLOAD = "http://192.168.11.172:9901/servlet/file/SignUploadAction";
    private static String URL_DOWNLOAD = "http://192.168.11.172:9901/tmp/file/unsafty";

    public static final String BASE_URL = "http://192.168.11.172:9901/";
    public static final String BASE_URL2 = "http://www.1688wan.com/";

    private static HttpService mHttpService;

    public static HttpService init() {
        if (mHttpService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    //返回值为String的支持
                    .addConverterFactory(ScalarsConverterFactory.create())
                    //返回值为Gson的支持(以实体类返回)
                    .addConverterFactory(GsonConverterFactory.create())
                    //返回值为Observable<>的支持
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            mHttpService = retrofit.create(HttpService.class);
        }
        return mHttpService;
    }

    //---------------------------------------------------------------------------------------------

    /**
     * 构造MultipartBody
     * 把File对象转化成MultipartBody
     *
     * @param fileList 文件集合
     */
    public static MultipartBody fileToMultipartBody(List<File> fileList) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (File file : fileList) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            builder.addFormDataPart("file", file.getName(), requestBody);
        }
        return builder.build();
    }

    /**
     * 把File转化成MultipartBody.Part
     *
     * @param fileList 文件集合
     */
    public static List<MultipartBody.Part> fileToMultipartBodyPart(List<File> fileList) {
        List<MultipartBody.Part> parts = new ArrayList<>(fileList.size());
        for (File file : fileList) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }

    /**
     * 把File转化成MultipartBody.Part
     *
     * @param fileList 文件集合
     */
    public static List<MultipartBody.Part> fileToMultipartBodyPart2(List<File> fileList) {
        List<MultipartBody.Part> parts = new ArrayList<>(fileList.size());
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (File file : fileList) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            builder.addFormDataPart("file", file.getName(), requestBody);
        }
        parts.addAll(builder.build().parts());
        return parts;
    }

    public static RequestBody convertToRequestBody(String param) {
        return RequestBody.create(MediaType.parse("text/plain"), param);
    }
}
