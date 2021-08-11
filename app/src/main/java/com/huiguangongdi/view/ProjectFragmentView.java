package com.huiguangongdi.view;


import com.huiguangongdi.base.presenter.BaseContract;
import com.huiguangongdi.bean.ProjectProblemBean;

public interface ProjectFragmentView extends BaseContract.BaseView {

    void getProblemNumSuccess(ProjectProblemBean bean);

}
