package com.huiguangongdi.view;


import com.huiguangongdi.base.presenter.BaseContract;
import com.huiguangongdi.bean.CompanyListBean;
import com.huiguangongdi.bean.MemberApplyListBean;
import com.huiguangongdi.bean.ProjectDetailBean;
import com.huiguangongdi.bean.ProjectMemberBean;
import com.huiguangongdi.bean.SpecialtyBean;
import com.huiguangongdi.bean.UserBean;

import java.util.ArrayList;

public interface MemberApplyListView extends BaseContract.BaseView {

    void getMemberApplyListSuccess(ArrayList<MemberApplyListBean> list);

    void getMemberApplyListFail(String msg);

    void getMemberRoleSuccess(ArrayList<ProjectMemberBean> list);

    void getMemberRoleFail(String msg);

    void getCompanySuccess(ArrayList<CompanyListBean> list);

    void getSpecialtySuccess(ArrayList<SpecialtyBean> bean);

    void addProjectMemberSuccess();

    void addProjectMemberFail(String msg);

    void examineAddProjectSuccess();

    void examineAddProjectFail(String msg);

}
