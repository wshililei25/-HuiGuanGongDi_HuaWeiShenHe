package com.huiguangongdi.base.api;


import com.huiguangongdi.bean.BannerBean;
import com.huiguangongdi.bean.BaseBean;
import com.huiguangongdi.bean.BuildingNoBean;
import com.huiguangongdi.bean.BuildingNoSdBean;
import com.huiguangongdi.bean.CompanyListBean;
import com.huiguangongdi.bean.MemberApplyListBean;
import com.huiguangongdi.bean.MemberRoleBean;
import com.huiguangongdi.bean.NewsNumBean;
import com.huiguangongdi.bean.ProjectBean;
import com.huiguangongdi.bean.ProjectDetailBean;
import com.huiguangongdi.bean.ProjectMemberBean;
import com.huiguangongdi.bean.ProjectNewsBean;
import com.huiguangongdi.bean.ProjectProblemBean;
import com.huiguangongdi.bean.ProvinceBean;
import com.huiguangongdi.bean.QualityManagerBean;
import com.huiguangongdi.bean.QualityManagerDetailBean;
import com.huiguangongdi.bean.SpecialtyBean;
import com.huiguangongdi.bean.ToDoBean;
import com.huiguangongdi.bean.UploadPhotoBean;
import com.huiguangongdi.bean.UserBean;
import com.huiguangongdi.req.AddCompanyReq;
import com.huiguangongdi.req.AddMemberReq;
import com.huiguangongdi.req.ApplyAddProjectReq;
import com.huiguangongdi.req.BuildingNoReq;
import com.huiguangongdi.req.CompanyByProjectIdReq;
import com.huiguangongdi.req.CompanyReq;
import com.huiguangongdi.req.CreateProjectReq;
import com.huiguangongdi.req.CreateProjectTwoReq;
import com.huiguangongdi.req.CreateProjectTwoSdReq;
import com.huiguangongdi.req.CreateQualityReq;
import com.huiguangongdi.req.DeleteMemberReq;
import com.huiguangongdi.req.EditInfoReq;
import com.huiguangongdi.req.EditQualityReq;
import com.huiguangongdi.req.ExamineAddProjectReq;
import com.huiguangongdi.req.FeedbackReq;
import com.huiguangongdi.req.GetCodeReq;
import com.huiguangongdi.req.LoginReq;
import com.huiguangongdi.req.MemberApplyListReq;
import com.huiguangongdi.req.MemberByProjectIdReq;
import com.huiguangongdi.req.ProjectDetailReq;
import com.huiguangongdi.req.ProjectMemberReq;
import com.huiguangongdi.req.ProjectNewsReq;
import com.huiguangongdi.req.ProjectProjectNumReq;
import com.huiguangongdi.req.QualityManagerDetailReq;
import com.huiguangongdi.req.QualityManagerHandleReq;
import com.huiguangongdi.req.QualityManagerRefuseReq;
import com.huiguangongdi.req.QualityManagerReq;
import com.huiguangongdi.req.QuitProjectReq;
import com.huiguangongdi.req.SpecialtyByProjectIdReq;
import com.huiguangongdi.req.ToDoReq;
import com.huiguangongdi.req.TurnOverProjectReq;
import com.huiguangongdi.req.UpdateProjectReq;
import com.huiguangongdi.req.UserInfoByMobileReq;
import com.huiguangongdi.req.UserInfoReq;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    @POST("member/login")
    Observable<BaseBean<UserBean>> login(@Body LoginReq req); //登录

    @POST("member/send_code")
    Observable<BaseBean> getCode(@Body GetCodeReq req); //获取验证码

    @POST("tool/get_area")
    Observable<BaseBean<ArrayList<ProvinceBean>>> getProvince(); //获取省市区

    @POST("tool/get_work")
    Observable<BaseBean<ArrayList<SpecialtyBean>>> getSpecialty(@Body CompanyReq req); //获取专业列表

    @POST("project/get_project_work")
    Observable<BaseBean<ArrayList<SpecialtyBean>>> getSpecialtyByProjectId(@Body SpecialtyByProjectIdReq req); //获取项目的专业列表

    @POST("tool/get_work")
    Observable<BaseBean<ArrayList<SpecialtyBean>>> getSpecialtyNull(); //获取专业列表

    @POST("tool/add_work")
    Observable<BaseBean<SpecialtyBean>> addSpecialty(@Body AddCompanyReq req); //添加专业

    @POST("member/register")
    Observable<BaseBean> userInfo(@Body UserInfoReq req); //设置用户信息

    @POST("member/edit_member")
    Observable<BaseBean> editUserInfo(@Body EditInfoReq req); //修改用户信息

    @POST("project/get_project_list")
    Observable<BaseBean<ArrayList<ProjectBean>>> getProjectList(); //获取项目列表

    @POST("tool/get_company")
    Observable<BaseBean<ArrayList<CompanyListBean>>> getCompanyNull(); //获取公司列表

    @POST("tool/get_company")
    Observable<BaseBean<ArrayList<CompanyListBean>>> getCompany(@Body CompanyReq req); //获取公司列表

    @POST("project/get_project_company")
    Observable<BaseBean<ArrayList<CompanyListBean>>> getCompanyByProjectId(@Body CompanyByProjectIdReq req); //获取项目下的公司列表

    @POST("tool/get_manage_param")
    Observable<BaseBean<BuildingNoBean>> getBuildingNo(@Body BuildingNoReq req); //获取楼号、层数列表（轴线图）

    @POST("tool/get_manage_param")
    Observable<BaseBean<BuildingNoSdBean>> getBuildingNoSd(@Body BuildingNoReq req); //获取楼号、层数列表（手动创建）

    @POST("project/get_project_company_user")
    Observable<BaseBean<ArrayList<ProjectMemberBean>>> getMemberByProjectIdTurnOver(@Body MemberByProjectIdReq req); //获取当前项目当前公司下人员

    @POST("project/transfer_project")
    Observable<BaseBean> turnOverProject(@Body TurnOverProjectReq req); //移交项目

    @POST("tool/add_company")
    Observable<BaseBean<CompanyListBean>> addCompany(@Body AddCompanyReq req); //添加公司

    @POST("project/add_step1")
    Observable<BaseBean<ProjectBean>> createProjectOne(@Body CreateProjectReq req); //创建项目第一步

    @POST("project/add_step2")
    Observable<BaseBean> createProjectTwoZx(@Body CreateProjectTwoReq req); //创建项目第二步（轴线图）

    @POST("project/add_step2")
    Observable<BaseBean> createProjectTwoSd(@Body CreateProjectTwoSdReq req); //创建项目第二步（手动创建）

    @POST("project/get_project_user")
    Observable<BaseBean<ArrayList<ProjectMemberBean>>> getProjectMember(@Body ProjectMemberReq req); //获取成员列表

    @POST("project/get_project_role")
    Observable<BaseBean<ArrayList<MemberRoleBean>>> getMemberRole(); //获取成员角色

    @POST("member/get_user")
    Observable<BaseBean<UserBean>> getUserIfoByMobile(@Body UserInfoByMobileReq req); //根据手机号获取用户信息

    @POST("tool/get_banner")
    Observable<BaseBean<ArrayList<BannerBean>>> getBanner(); //发现页banner

    @POST("project/add_project_user")
    Observable<BaseBean<UserBean>> addProjectMember(@Body AddMemberReq req); //添加项目成员

    @POST("project/update_project_user")
    Observable<BaseBean<UserBean>> updateProjectMember(@Body AddMemberReq req); //更新项目成员

    @POST("project/delete_project_user")
    Observable<BaseBean> deleteProjectMember(@Body DeleteMemberReq req); //删除项目成员

    @POST("project/sign_out_project")
    Observable<BaseBean> quitProject(@Body QuitProjectReq req); //退出项目

    @POST("project/get_project_info")
    Observable<BaseBean<ProjectDetailBean>> getProjectDetail(@Body ProjectDetailReq req); //获取项目详情

    @GET("quality/make_report")
    Observable<String> getQualityReport(@Query("pid") int pid, @Query("status") int status, @Query("deal_company") int deal_company
            , @Query("work_type") int work_type, @Query("start_date") String start_date, @Query("end_date") String end_date); //获取质量管理报告

    @GET("security/make_report")
    Observable<String> getSafeReport(@Query("pid") int pid, @Query("status") int status, @Query("deal_company") int deal_company
            , @Query("work_type") int work_type, @Query("start_date") String start_date, @Query("end_date") String end_date); //获取安全管理报告

    @POST("project/apply_join_project")
    Observable<BaseBean> applyAddProject(@Body ApplyAddProjectReq req); //申请加入项目

    @POST("project/get_apply_list")
    Observable<BaseBean<ArrayList<MemberApplyListBean>>> getMemberApplyList(@Body MemberApplyListReq req); //获取项目成员申请列表

    @POST("message/project_message")
    Observable<BaseBean<ArrayList<ProjectNewsBean>>> getProjectNews(@Body ProjectNewsReq req); //获取项目消息

    @POST("message/system_message")
    Observable<BaseBean<ArrayList<ProjectNewsBean>>> getSystemNews(@Body ProjectNewsReq req); //获取系统消息

    @POST("project/check_project_apply")
    Observable<BaseBean> examineAddProject(@Body ExamineAddProjectReq req); //审批申请加入的项目成员

    @POST("project/update_project")
    Observable<BaseBean> updateProject(@Body UpdateProjectReq req); //更新项目信息

    @POST("project/get_manage_num")
    Observable<BaseBean<ProjectProblemBean>> getProblemNum(@Body ProjectProjectNumReq req); //获取首页质量/安全数据统计

    @POST("tool/feedback")
    Observable<BaseBean> feedback(@Body FeedbackReq req); //更新项目信息

    @POST("quality/add")
    Observable<BaseBean> createQuality(@Body CreateQualityReq req); //创建质量管理

    @POST("security/add")
    Observable<BaseBean> createSafe(@Body CreateQualityReq req); //创建安全管理

    @POST("quality/edit")
    Observable<BaseBean> editQuality(@Body EditQualityReq req); //编辑质量管理

    @POST("security/edit")
    Observable<BaseBean> editSafe(@Body EditQualityReq req); //编辑安全管理

    @POST("quality/quality_list")
    Observable<BaseBean<ArrayList<QualityManagerBean>>> getQualityManager(@Body QualityManagerReq req); //获取质量管理列表

    @POST("security/security_list")
    Observable<BaseBean<ArrayList<QualityManagerBean>>> getSafeManager(@Body QualityManagerReq req); //获取安全管理列表

    @POST("message/need_message")
    Observable<BaseBean<ArrayList<ToDoBean>>> getToDoList(@Body ToDoReq req); //获取跟人待办列表（质量管理）

    @POST("message/need_message")
    Observable<BaseBean<ArrayList<ToDoBean>>> getToDoSafeList(@Body ToDoReq req); //获取跟人待办列表（安全管理）

    @POST("quality/get_quality_info")
    Observable<BaseBean<QualityManagerDetailBean>> getQualityManagerDetail(@Body QualityManagerDetailReq req); //获取质量管理详情

    @POST("security/get_security_info")
    Observable<BaseBean<QualityManagerDetailBean>> getSafeManagerDetail(@Body QualityManagerDetailReq req); //获取安全管理详情

    @POST("quality/handle")
    Observable<BaseBean> handleQualityManager(@Body QualityManagerHandleReq req); //质量管理处理

    @POST("security/handle")
    Observable<BaseBean> handleSafeManager(@Body QualityManagerHandleReq req); //安全管理处理

    @POST("quality/check")
    Observable<BaseBean> refuseQualityManager(@Body QualityManagerRefuseReq req); //质量管理核查

    @POST("security/check")
    Observable<BaseBean> refuseSafeManager(@Body QualityManagerRefuseReq req); //安全管理核查

    @POST("message/index")
    Observable<BaseBean<NewsNumBean>> getNewNum(); //获取消息数量

    @Multipart
    @POST("member/avatar")
    Observable<BaseBean<UploadPhotoBean>> uploadHeadPath(@Part MultipartBody.Part req); //上传头像

    @Multipart
    @POST("upload/index")
    Observable<BaseBean<String>> uploadPhotoPath(@Part MultipartBody.Part req); //上传图片

}
