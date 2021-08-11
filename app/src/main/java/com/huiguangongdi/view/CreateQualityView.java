package com.huiguangongdi.view;


import com.huiguangongdi.base.presenter.BaseContract;
import com.huiguangongdi.bean.BuildingNoBean;
import com.huiguangongdi.bean.BuildingNoSdBean;
import com.huiguangongdi.bean.CompanyListBean;
import com.huiguangongdi.bean.ProjectDetailBean;
import com.huiguangongdi.bean.ProjectMemberBean;
import com.huiguangongdi.bean.SpecialtyBean;

import java.util.ArrayList;

public interface CreateQualityView extends BaseContract.BaseView {

    void getProjectDetailSuccess(ProjectDetailBean list);

    void getProjectDetailFail(String msg);

    void getCompanyByProjectIdTurnOverSuccess(ArrayList<CompanyListBean> list);

    void getCompanyByProjectIdTurnOverFail(String msg);

    void getBuildingNoSuccess(BuildingNoBean bean);

    void getBuildingNoFail(String msg);

    void getBuildingNoSdSuccess(BuildingNoSdBean bean);

    void getBuildingNoSdFail(String msg);

    void getMemberByProjectIdTurnOverSuccess(ArrayList<ProjectMemberBean> list);

    void getMemberByProjectIdTurnOverFail(String msg);

    void getSpecialtySuccess(ArrayList<SpecialtyBean> list);

    void createQualitySuccess();

    void createQualityFail(String msg);

    void uploadPhotoPathSuccess(String bean);

    void uploadPhotoPathFail(String msg);
}
