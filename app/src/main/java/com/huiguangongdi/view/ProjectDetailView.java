package com.huiguangongdi.view;


import com.huiguangongdi.base.presenter.BaseContract;
import com.huiguangongdi.bean.ProjectDetailBean;

public interface ProjectDetailView extends BaseContract.BaseView {

    void getProjectDetailSuccess(ProjectDetailBean list);

    void getProjectDetailFail(String msg);

    void updateProjectSuccess();

    void updateProjectFail(String msg);

    void applyAddProjectSuccess();

    void applyAddProjectFail(String msg);
}
