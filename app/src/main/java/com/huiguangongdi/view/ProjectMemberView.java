package com.huiguangongdi.view;


import com.huiguangongdi.base.presenter.BaseContract;
import com.huiguangongdi.bean.CompanyListBean;
import com.huiguangongdi.bean.MemberRoleBean;
import com.huiguangongdi.bean.ProjectDetailBean;
import com.huiguangongdi.bean.ProjectMemberBean;
import com.huiguangongdi.bean.SpecialtyBean;
import com.huiguangongdi.bean.UserBean;

import java.util.ArrayList;

public interface ProjectMemberView extends BaseContract.BaseView {

    void getProjectMemberSuccess(ArrayList<ProjectMemberBean> list);

    void getProjectMemberFail(String msg);

    void getProjectDetailSuccess(ProjectDetailBean list);

    void getProjectDetailFail(String msg);

    void getMemberRoleSuccess(ArrayList<MemberRoleBean> list);

    void getMemberRoleFail(String msg);

    void getCompanySuccess(ArrayList<CompanyListBean> list);

    void getCompanyByProjectIdTurnOverSuccess(ArrayList<CompanyListBean> list);

    void getCompanyByProjectIdTurnOverFail(String msg);

    void getMemberByProjectIdTurnOverSuccess(ArrayList<ProjectMemberBean> list);

    void getMemberByProjectIdTurnOverFail(String msg);

    void getSpecialtySuccess(ArrayList<SpecialtyBean> list);

    void getUserInfoByMobileSuccess(UserBean bean);

    void addProjectMemberSuccess();

    void addProjectMemberFail(String msg);

    void deleteProjectMemberSuccess();

    void deleteProjectMemberFail(String msg);

    void quitProjectSuccess();

    void quitProjectFail(String msg);

    void turnOverProjectSuccess();

    void turnOverProjectOverFail(String msg);
}
