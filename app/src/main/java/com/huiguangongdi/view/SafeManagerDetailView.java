package com.huiguangongdi.view;


import com.huiguangongdi.base.presenter.BaseContract;
import com.huiguangongdi.bean.QualityManagerDetailBean;

public interface SafeManagerDetailView extends BaseContract.BaseView {

    void getProjectMemberSuccess(QualityManagerDetailBean bean);

    void getProjectMemberFail(String msg);

    void handleQualityManagerSuccess();

    void handleQualityManagerFail(String msg);

    void refuseQualityManagerSuccess();

    void refuseQualityManagerFail(String msg);

    void uploadPhotoPathSuccess(String bean);

    void uploadPhotoPathFail(String msg);

}
