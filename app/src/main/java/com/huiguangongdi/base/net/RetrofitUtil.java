package com.huiguangongdi.base.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.huiguangongdi.BaseApplication;
import com.huiguangongdi.base.api.ApiService;
import com.huiguangongdi.base.api.Constant;
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
import com.huiguangongdi.common.Extra;
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
import com.huiguangongdi.utils.SPUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    public static RetrofitUtil mInstance;
    private ApiService mApiService;
    private static String mMobile = "";

    public RetrofitUtil(OkHttpClient okHttpClient) {
        Gson gson = new GsonBuilder()
                //配置你的Gson
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .registerTypeHierarchyAdapter(String.class, STRING)//设置解析的时候null转成""
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_BASE_URL)
                //添加的fastjson解析器序列化数据
//                .addConverterFactory(new Retrofit2ConverterFactory())
                //添加转换器支持返回结果为String类型
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                //添加rxjava2适配器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(initClient())
                .build();
        mApiService = retrofit.create(ApiService.class);
    }

    private OkHttpClient initClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(initHeaderInterceptor())
                .addInterceptor(initLogInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    private Interceptor initHeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .header("Content-Type", "application/json")
                        .header("charset", "utf-8")
                        .header("mobile", mMobile)
                        .build();
                return chain.proceed(request);
            }
        };
    }

    private Interceptor initLogInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    /**
     * 自定义TypeAdapter ,null对象将被解析成空字符串
     */
    public static final TypeAdapter<String> STRING = new TypeAdapter<String>() {
        public String read(JsonReader reader) {
            try {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull();
                    return ""; // 原先是返回null，这里改为返回空字符串
                }
                return reader.nextString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        public void write(JsonWriter writer, String value) {
            try {
                if (value == null) {
                    writer.nullValue();
                    return;
                }
                writer.value(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public static RetrofitUtil getInstance() {
        if (null != SPUtil.get(BaseApplication.baseApplication, Extra.SP_User_Mobile, "")) {
            mMobile = (String) SPUtil.get(BaseApplication.baseApplication, Extra.SP_User_Mobile, "");
        }
        if (mInstance == null) {
            mInstance = new RetrofitUtil(new OkHttpClient());
        }
        return mInstance;
    }

    public Observable<BaseBean<UserBean>> login(LoginReq req) {
        return mApiService.login(req);
    }

    public Observable<BaseBean> getCode(GetCodeReq req) {
        return mApiService.getCode(req);
    }

    public Observable<BaseBean<ArrayList<ProvinceBean>>> getProvince() {
        return mApiService.getProvince();
    }

    public Observable<BaseBean<ArrayList<SpecialtyBean>>> getSpecialty(CompanyReq req) {
        if (req.getKeyword().isEmpty()) {
            return mApiService.getSpecialtyNull();
        }
        return mApiService.getSpecialty(req);
    }

    public Observable<BaseBean<ArrayList<SpecialtyBean>>> getSpecialtyByProjectId(SpecialtyByProjectIdReq req) {
        return mApiService.getSpecialtyByProjectId(req);
    }

    public Observable<BaseBean<SpecialtyBean>> addSpecialty(AddCompanyReq req) {
        return mApiService.addSpecialty(req);
    }

    public Observable<BaseBean> userInfo(UserInfoReq req) {
        return mApiService.userInfo(req);
    }

    public Observable<BaseBean> editUserInfo(EditInfoReq req) {
        return mApiService.editUserInfo(req);
    }

    public Observable<BaseBean<ArrayList<ProjectBean>>> getProjectList() {
        return mApiService.getProjectList();
    }

    public Observable<BaseBean<ArrayList<CompanyListBean>>> getCompany(CompanyReq req) {
        if (req.getKeyword().isEmpty()) {
            return mApiService.getCompanyNull();
        }
        return mApiService.getCompany(req);
    }

    public Observable<BaseBean<ArrayList<CompanyListBean>>> getCompanyByProjectId(CompanyByProjectIdReq req) {
        return mApiService.getCompanyByProjectId(req);
    }

    public Observable<BaseBean<BuildingNoBean>> getBuildingNo(BuildingNoReq req) {
        return mApiService.getBuildingNo(req);
    }

    public Observable<BaseBean<BuildingNoSdBean>> getBuildingNoSd(BuildingNoReq req) {
        return mApiService.getBuildingNoSd(req);
    }

    public Observable<BaseBean<ArrayList<ProjectMemberBean>>> getMemberByProjectIdTurnOver(MemberByProjectIdReq req) {
        return mApiService.getMemberByProjectIdTurnOver(req);
    }

    public Observable<BaseBean> turnOverProject(TurnOverProjectReq req) {
        return mApiService.turnOverProject(req);
    }

    public Observable<BaseBean<CompanyListBean>> addCompany(AddCompanyReq req) {
        return mApiService.addCompany(req);
    }

    public Observable<BaseBean<ProjectBean>> createProjectOne(CreateProjectReq req) {
        return mApiService.createProjectOne(req);
    }

    public Observable<BaseBean> createProjectTwoZx(CreateProjectTwoReq req) {
        return mApiService.createProjectTwoZx(req);
    }

    public Observable<BaseBean> createProjectTwoSd(CreateProjectTwoSdReq req) {
        return mApiService.createProjectTwoSd(req);
    }

    public Observable<BaseBean<ArrayList<ProjectMemberBean>>> getProjectMember(ProjectMemberReq req) {
        return mApiService.getProjectMember(req);
    }

    public Observable<BaseBean<ArrayList<MemberRoleBean>>> getMemberRole() {
        return mApiService.getMemberRole();
    }

    public Observable<BaseBean<UserBean>> getUserIfoByMobile(UserInfoByMobileReq req) {
        return mApiService.getUserIfoByMobile(req);
    }

    public Observable<BaseBean<ArrayList<BannerBean>>> getBanner() {
        return mApiService.getBanner();
    }

    public Observable<BaseBean<UserBean>> addProjectMember(AddMemberReq req) {
        return mApiService.addProjectMember(req);
    }

    public Observable<BaseBean<UserBean>> updateProjectMember(AddMemberReq req) {
        return mApiService.updateProjectMember(req);
    }

    public Observable<BaseBean> deleteProjectMember(DeleteMemberReq req) {
        return mApiService.deleteProjectMember(req);
    }

    public Observable<BaseBean> quitProject(QuitProjectReq req) {
        return mApiService.quitProject(req);
    }

    public Observable<BaseBean<ProjectDetailBean>> getProjectDetail(ProjectDetailReq req) {
        return mApiService.getProjectDetail(req);
    }

    public Observable<String> getQualityReport(QualityManagerReq req, boolean misQualityOrSafe) {
        if (misQualityOrSafe) {
            return mApiService.getQualityReport(req.getPid(), req.getStatus(), req.getDeal_company(),
                    req.getWork_type(), req.getStart_date(), req.getEnd_date());
        }
        return mApiService.getSafeReport(req.getPid(), req.getStatus(), req.getDeal_company(),
                req.getWork_type(), req.getStart_date(), req.getEnd_date());
    }

    public Observable<BaseBean> applyAddProject(ApplyAddProjectReq req) {
        return mApiService.applyAddProject(req);
    }

    public Observable<BaseBean<ArrayList<MemberApplyListBean>>> getMemberApplyList(MemberApplyListReq req) {
        return mApiService.getMemberApplyList(req);
    }

    public Observable<BaseBean<ArrayList<ProjectNewsBean>>> getProjectNews(ProjectNewsReq req) {
        return mApiService.getProjectNews(req);
    }

    public Observable<BaseBean<ArrayList<ProjectNewsBean>>> getSystemNews(ProjectNewsReq req) {
        return mApiService.getSystemNews(req);
    }

    public Observable<BaseBean> examineAddProject(ExamineAddProjectReq req) {
        return mApiService.examineAddProject(req);
    }

    public Observable<BaseBean> updateProject(UpdateProjectReq req) {
        return mApiService.updateProject(req);
    }

    public Observable<BaseBean<ProjectProblemBean>> getProblemNum(ProjectProjectNumReq req) {
        return mApiService.getProblemNum(req);
    }

    public Observable<BaseBean> feedback(FeedbackReq req) {
        return mApiService.feedback(req);
    }

    public Observable<BaseBean> createQuality(CreateQualityReq req) {
        return mApiService.createQuality(req);
    }

    public Observable<BaseBean> createSafe(CreateQualityReq req) {
        return mApiService.createSafe(req);
    }

    public Observable<BaseBean> editQuality(EditQualityReq req) {
        return mApiService.editQuality(req);
    }

    public Observable<BaseBean> editSafe(EditQualityReq req) {
        return mApiService.editSafe(req);
    }

    public Observable<BaseBean<ArrayList<QualityManagerBean>>> getQualityManager(QualityManagerReq req) {
        return mApiService.getQualityManager(req);
    }

    public Observable<BaseBean<ArrayList<QualityManagerBean>>> getSafeManager(QualityManagerReq req) {
        return mApiService.getSafeManager(req);
    }

    public Observable<BaseBean<ArrayList<ToDoBean>>> getToDoList(ToDoReq req) {
        return mApiService.getToDoList(req);
    }

    public Observable<BaseBean<ArrayList<ToDoBean>>> getToDoSafeList(ToDoReq req) {
        return mApiService.getToDoSafeList(req);
    }

    public Observable<BaseBean<QualityManagerDetailBean>> getQualityManagerDetail(QualityManagerDetailReq req) {
        return mApiService.getQualityManagerDetail(req);
    }

    public Observable<BaseBean<QualityManagerDetailBean>> getSafeManagerDetail(QualityManagerDetailReq req) {
        return mApiService.getSafeManagerDetail(req);
    }

    public Observable<BaseBean> handleQualityManager(QualityManagerHandleReq req) {
        return mApiService.handleQualityManager(req);
    }

    public Observable<BaseBean> handleSafeManager(QualityManagerHandleReq req) {
        return mApiService.handleSafeManager(req);
    }

    public Observable<BaseBean> refuseQualityManager(QualityManagerRefuseReq req) {
        return mApiService.refuseQualityManager(req);
    }

    public Observable<BaseBean> refuseSafeManager(QualityManagerRefuseReq req) {
        return mApiService.refuseSafeManager(req);
    }

    public Observable<BaseBean<NewsNumBean>> getNewNum() {
        return mApiService.getNewNum();
    }

    public Observable<BaseBean<UploadPhotoBean>> uploadHeadPath(MultipartBody.Part req) {
        return mApiService.uploadHeadPath(req);
    }

    public Observable<BaseBean<String>> uploadPhotoPath(MultipartBody.Part req) {
        return mApiService.uploadPhotoPath(req);
    }
}
