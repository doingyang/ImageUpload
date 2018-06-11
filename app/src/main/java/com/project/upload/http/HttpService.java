package com.project.upload.http;

import com.project.upload.bean.UploadResponse;
import com.project.upload.bean.GiftBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * @author ydy http://www.1688wan.com/majax.action?method=getGiftList&pageno=1
 */
public interface HttpService {

    @GET("majax.action?method=getGiftList")
    Observable<GiftBean> getGiftData(@Query("pageno") int pageno);

    /**
     * 多文件上传
     * @param map   参数map
     * @param parts 每个part
     * @return return
     */
    @Multipart
    @POST("servlet/file/SignUploadAction")
    Observable<UploadResponse> uploadFilesWithParts(@PartMap Map<String, RequestBody> map, @Part List<MultipartBody.Part> parts);
}
