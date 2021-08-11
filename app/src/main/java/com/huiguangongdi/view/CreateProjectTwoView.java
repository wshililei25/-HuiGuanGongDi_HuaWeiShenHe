package com.huiguangongdi.view;


import com.huiguangongdi.base.presenter.BaseContract;
import com.huiguangongdi.bean.ProjectBean;

public interface CreateProjectTwoView extends BaseContract.BaseView {

    void createProjectSuccess();

    void createProjectFail(String msg);


}
