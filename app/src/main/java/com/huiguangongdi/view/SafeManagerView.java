package com.huiguangongdi.view;


import com.huiguangongdi.base.presenter.BaseContract;
import com.huiguangongdi.bean.CompanyListBean;
import com.huiguangongdi.bean.QualityManagerBean;
import com.huiguangongdi.bean.SpecialtyBean;

import java.util.ArrayList;

public interface SafeManagerView extends BaseContract.BaseView {
    void getCompanyByProjectIdTurnOverSuccess(ArrayList<CompanyListBean> list);

    void getCompanyByProjectIdTurnOverFail(String msg);

    void getSpecialtySuccess(ArrayList<SpecialtyBean> list);
    void getSpecialtyFail(String msg);
    void getProjectMemberSuccess(ArrayList<QualityManagerBean> list);

    void getProjectMemberFail(String msg);

}
