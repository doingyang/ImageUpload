package com.project.upload.mvp.model.impl;

import android.annotation.SuppressLint;
import android.util.Log;

import com.project.upload.bean.GiftBean;
import com.project.upload.http.HttpUtils;
import com.project.upload.mvp.model.IUserModel;
import com.project.upload.mvp.presenter.IUserPresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ydy
 */
public class UserModel implements IUserModel {

    @SuppressLint("CheckResult")
    @Override
    public void loadData(int pagerno, final IUserPresenter.callBack callBack) {
        HttpUtils
                .init()
                .getGiftData(pagerno)
                //指定被观察者运行线程
                .subscribeOn(Schedulers.io())
                //观察者默认运行线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GiftBean>() {
                    @Override
                    public void accept(GiftBean giftBean) throws Exception {
                        Log.i("TAG", "accept: " + giftBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

}
