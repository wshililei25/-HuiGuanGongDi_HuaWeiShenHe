package com.huiguangongdi.view;


import com.huiguangongdi.base.presenter.BaseContract;

public interface QualityReportView extends BaseContract.BaseView {

    void getQualityReportSuccess(String list);

    void getQualityReportFail(String msg);

}
