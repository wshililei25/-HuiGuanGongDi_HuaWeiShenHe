package com.huiguangongdi.view;


import com.huiguangongdi.base.presenter.BaseContract;
import com.huiguangongdi.bean.QualityManagerDetailBean;

public interface QualityManagerDetailView extends BaseContract.BaseView {

    void getQualityManagerDetailSuccess(QualityManagerDetailBean bean);

    void getQualityManagerDetailFail(String msg);

    void handleQualityManagerSuccess();

    void handleQualityManagerFail(String msg);

    void refuseQualityManagerSuccess();

    void refuseQualityManagerFail(String msg);

    void uploadPhotoPathSuccess(String bean);

    void uploadPhotoPathFail(String msg);
}
