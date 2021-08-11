package com.huiguangongdi.view;


import com.huiguangongdi.base.presenter.BaseContract;
import com.huiguangongdi.bean.ProjectNewsBean;

import java.util.ArrayList;

public interface ProjectNewsView extends BaseContract.BaseView {

    void getProjectNewsSuccess(ArrayList<ProjectNewsBean> list);

    void getProjectNewsFail(String msg);

}
