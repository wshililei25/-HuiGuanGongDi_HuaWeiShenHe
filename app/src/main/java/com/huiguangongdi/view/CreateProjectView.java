package com.huiguangongdi.view;


import com.huiguangongdi.base.presenter.BaseContract;
import com.huiguangongdi.bean.ProjectBean;

public interface CreateProjectView extends BaseContract.BaseView {

    void createProjectSuccess(ProjectBean bean);

    void createProjectFail(String msg);


}
