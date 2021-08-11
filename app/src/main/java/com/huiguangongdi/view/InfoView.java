package com.huiguangongdi.view;


import com.huiguangongdi.base.presenter.BaseContract;
import com.huiguangongdi.bean.BaseBean;
import com.huiguangongdi.bean.ProvinceBean;
import com.huiguangongdi.bean.SpecialtyBean;
import com.huiguangongdi.bean.UserBean;

import java.util.ArrayList;

public interface InfoView extends BaseContract.BaseView {

    void getProvinceSuccess(ArrayList<ProvinceBean> bean);

    void getSpecialtySuccess(ArrayList<SpecialtyBean> bean);

    void userInfoSuccess(BaseBean bean);

    void userInfoFail(String msg);

    void getUserInfoByMobileSuccess(UserBean bean);

}
