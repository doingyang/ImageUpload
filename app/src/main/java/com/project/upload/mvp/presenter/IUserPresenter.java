package com.project.upload.mvp.presenter;

import com.project.upload.bean.GiftBean;

public interface IUserPresenter {

    void startLoad(int pagerno);

    interface callBack{
        void success(GiftBean data);
    }

}
