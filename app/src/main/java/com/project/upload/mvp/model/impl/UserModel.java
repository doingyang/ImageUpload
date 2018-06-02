package com.project.upload.mvp.model.impl;

import android.util.Log;

import com.project.upload.bean.FileUploadResponse;
import com.project.upload.http.HttpUtils;
import com.project.upload.mvp.model.IUserModel;
import com.project.upload.mvp.presenter.IUserPresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ydy on 2016/11/29
 * Action1方法是Subscriber的父类
 * Func1方法类似Action1，只是Func1有返回值。
 * 被观察者通过map(new Func1<>)拼接消息
 * 观察者new Action1<String>，接收到消息
 */
public class UserModel implements IUserModel{

    @Override
    public void loadData(int pagerno, final IUserPresenter.callBack callBack) {

//        HttpUtils.init().getGiftData(1).enqueue(new Callback<GiftBean>() {
//            @Override
//            public void onResponse(Call<GiftBean> call, Response<GiftBean> response) {
//                String data = response.body().toString();
//            }
//
//            @Override
//            public void onFailure(Call<GiftBean> call, Throwable t) {
//
//            }
//        });

//        HttpUtils
//                .init()
//                .getGiftData(pagerno)
//                .doOnError(new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Log.i("TAG", "error: ");
//                    }
//                })
//                .map(new Func1<GiftBean, GiftBean>() {
//                    @Override
//                    public GiftBean call(GiftBean giftBean) {
//                        return giftBean;
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())//被观察者和观察者默认运行线程
//                .subscribeOn(Schedulers.newThread())//指定被观察者运行线程
//                .subscribe(new Action1<GiftBean>() {
//                    @Override
//                    public void call(GiftBean giftBean) {
//                        callBack.success(giftBean);
//                    }
//                });
    }

//    private void uploadFile() {
//        List<File> mFileList = new ArrayList<>();
//        MultipartBody body = HttpUtils.formMultipartBody(mFileList);
//        HttpUtils
//                .fileUpload()
//                .uploadFileWithRequestBody(body)
//                .enqueue(new Callback<FileUploadResponse>() {
//                    @Override
//                    public void onResponse(Call<FileUploadResponse> call, Response<FileUploadResponse> response) {
//                        if (response.isSuccessful()){
//                            FileUploadResponse result = response.body();
//                            Log.i("TAG", "上传成功");
//                        } else {
//                            Log.i("TAG", "上传失败");
//                        }
//                    }
//                    @Override
//                    public void onFailure(Call<FileUploadResponse> call, Throwable t) {
//                        Log.i("TAG", "网络连接失败");
//                    }
//                });
//    }

}
