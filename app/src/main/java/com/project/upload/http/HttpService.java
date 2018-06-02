package com.project.upload.http;

import com.project.upload.bean.FileUploadResponse;
import com.project.upload.bean.GiftBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ydy on 2017/03/31
 * http://www.1688wan.com/majax.action?method=getGiftList&pageno=1
 */
public interface HttpService {

    // /majax.action?method=getGiftList&pageno=1
//    @POST("/majax.action?method=getGiftList")
//    Call<GiftBean> getGiftData(@Query("pageno")int pageno);

    @POST("majax.action?method=getGiftList")
    Observable<GiftBean> getGiftData(@Query("pageno")int pageno);

    /**
     * 通过 List<MultipartBody.Part> 传入多个part实现多文件上传
     * @param parts 每个part代表一个
     * @return 状态信息
     */
    @Multipart
    @POST("users/image")
    Call<FileUploadResponse> uploadFilesWithParts(@Part() List<MultipartBody.Part> parts);

    /**
     * 通过 MultipartBody和@body作为参数来上传
     * @param multipartBody MultipartBody包含多个Part
     * @return 状态信息
     */
    @POST("servlet/file/SignUploadAction")
    Call<FileUploadResponse> uploadFileWithRequestBody(@Body MultipartBody multipartBody);

}
