package com.project.upload.http;

import com.project.upload.bean.FileUploadResponse;
import com.project.upload.bean.GiftBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @author ydy http://www.1688wan.com/majax.action?method=getGiftList&pageno=1
 */
public interface HttpService {

    @GET("majax.action?method=getGiftList")
    Observable<GiftBean> getGiftData(@Query("pageno")int pageno);

    /**
     * 通过 List<MultipartBody.Part> 传入多个part实现多文件上传
     * @param parts 每个part代表一个
     * @param map 参数
     * @return 状态信息
     */
    @Multipart
    @POST("servlet/file/SignUploadAction")
    Call<FileUploadResponse> uploadFilesWithParts(@Part() List<MultipartBody.Part> parts, @QueryMap Map<String, String> map);
}
