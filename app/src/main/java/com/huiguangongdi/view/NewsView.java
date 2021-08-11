package com.huiguangongdi.view;


import com.huiguangongdi.base.presenter.BaseContract;
import com.huiguangongdi.bean.NewsNumBean;
import com.huiguangongdi.bean.UserBean;

public interface NewsView extends BaseContract.BaseView {

    void getNewsNumSuccess(NewsNumBean bean);

}
