package com.huiguangongdi.view;


import com.huiguangongdi.base.presenter.BaseContract;
import com.huiguangongdi.bean.ProjectBean;

import java.util.ArrayList;

public interface MainView extends BaseContract.BaseView {

    void getProjectListSuccess(ArrayList<ProjectBean> bean);

}
