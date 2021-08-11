package com.huiguangongdi.view;


import com.huiguangongdi.base.presenter.BaseContract;
import com.huiguangongdi.bean.BaseBean;
import com.huiguangongdi.bean.UserBean;

public interface LoginView extends BaseContract.BaseView {

    void getCodeSuccess(BaseBean bean);

    void getCodeFail(String msg);

    void loginSuccess(BaseBean<UserBean> bean);

    void loginFail(BaseBean<UserBean> bean);

}
