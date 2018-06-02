package com.project.upload.mvp.presenter.impl;

import com.project.upload.bean.GiftBean;
import com.project.upload.mvp.model.IUserModel;
import com.project.upload.mvp.model.impl.UserModel;
import com.project.upload.mvp.presenter.IUserPresenter;
import com.project.upload.mvp.view.IUserView;

/**
 * Created by ydy
 */
public class UserPresenter implements IUserPresenter,IUserPresenter.callBack{

    private IUserModel userModel = new UserModel();
    private IUserView  userView;

    public UserPresenter(IUserView userView) {
        this.userView = userView;
    }

    @Override
    public void startLoad(int pagerno) {
        userModel.loadData(pagerno, this);
    }

    @Override
    public void success(GiftBean data) {
        userView.dataResult(data);
    }

}
