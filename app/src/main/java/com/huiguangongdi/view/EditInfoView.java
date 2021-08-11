package com.huiguangongdi.view;


import com.huiguangongdi.base.presenter.BaseContract;
import com.huiguangongdi.bean.BaseBean;
import com.huiguangongdi.bean.ProvinceBean;
import com.huiguangongdi.bean.SpecialtyBean;
import com.huiguangongdi.bean.UserBean;

import java.util.ArrayList;

public interface EditInfoView extends BaseContract.BaseView {

    void getUserInfoByMobileSuccess(UserBean bean);

    void getProvinceSuccess(ArrayList<ProvinceBean> bean);

    void getSpecialtySuccess(ArrayList<SpecialtyBean> bean);

    void editUserInfoSuccess(BaseBean bean);

    void editUserInfoFail(String msg);


}
