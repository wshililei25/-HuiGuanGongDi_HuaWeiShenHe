package com.huiguangongdi.view;


import com.huiguangongdi.base.presenter.BaseContract;
import com.huiguangongdi.bean.ProjectBean;
import com.huiguangongdi.bean.SpecialtyBean;

import java.util.ArrayList;

public interface ProfessionView extends BaseContract.BaseView {

    void createProjectSuccess(ProjectBean bean);

    void createProjectFail(String msg);

    void getSpecialtySuccess(ArrayList<SpecialtyBean> list);

    void getSpecialtyFail(String msg);

    void getSpecialtySearchSuccess(ArrayList<SpecialtyBean> list);

    void addSpecialtySuccess(SpecialtyBean bean);

    void addSpecialtyFail(String msg);


}
