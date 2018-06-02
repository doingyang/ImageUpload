package com.project.upload.mvp.model;

import com.project.upload.mvp.presenter.IUserPresenter;

/**
 * Created by Administrator on 2016/11/29
 */

public interface IUserModel {

    void loadData(int pagerno, IUserPresenter.callBack callBack);

}
