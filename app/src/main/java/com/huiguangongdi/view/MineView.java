package com.huiguangongdi.view;


import com.huiguangongdi.base.presenter.BaseContract;
import com.huiguangongdi.bean.UploadPhotoBean;
import com.huiguangongdi.bean.UserBean;

public interface MineView extends BaseContract.BaseView {

    void getUserInfoByMobileSuccess(UserBean bean);

    void uploadPhotoPathSuccess(UploadPhotoBean bean);

    void uploadPhotoPathFail(String msg);

}
