package com.huiguangongdi.view;


import com.huiguangongdi.base.presenter.BaseContract;
import com.huiguangongdi.bean.CompanyListBean;

import java.util.ArrayList;

public interface CompanyView extends BaseContract.BaseView {

    void getCompanySuccess(ArrayList<CompanyListBean> list);

    void getCompanyFail(String msg);

    void addCompanySuccess(CompanyListBean list);

    void addCompanyFail(String msg);
}
