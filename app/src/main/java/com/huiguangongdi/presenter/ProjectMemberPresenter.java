package com.huiguangongdi.presenter;

import com.huiguangongdi.base.api.Constant;
import com.huiguangongdi.base.net.RetrofitUtil;
import com.huiguangongdi.base.presenter.BasePresenter;
import com.huiguangongdi.bean.BaseBean;
import com.huiguangongdi.bean.CompanyListBean;
import com.huiguangongdi.bean.MemberRoleBean;
import com.huiguangongdi.bean.ProjectDetailBean;
import com.huiguangongdi.bean.ProjectMemberBean;
import com.huiguangongdi.bean.SpecialtyBean;
import com.huiguangongdi.bean.UserBean;
import com.huiguangongdi.req.AddMemberReq;
import com.huiguangongdi.req.CompanyByProjectIdReq;
import com.huiguangongdi.req.DeleteMemberReq;
import com.huiguangongdi.req.MemberByProjectIdReq;
import com.huiguangongdi.req.ProjectDetailReq;
import com.huiguangongdi.req.ProjectMemberReq;
import com.huiguangongdi.req.QuitProjectReq;
import com.huiguangongdi.req.SpecialtyByProjectIdReq;
import com.huiguangongdi.req.TurnOverProjectReq;
import com.huiguangongdi.req.UserInfoByMobileReq;
import com.huiguangongdi.view.ProjectMemberView;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProjectMemberPresenter extends BasePresenter<ProjectMemberView> {

    public void getProjectMember(ProjectMemberReq req) {
        RetrofitUtil.getInstance().getProjectMember(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean<ArrayList<ProjectMemberBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean<ArrayList<ProjectMemberBean>> bean) {
                        if (bean.getCode() == Constant.SUCCESS_CODE) {
                            mView.getProjectMemberSuccess(bean.getData());
                        } else {
                            mView.getProjectMemberFail(bean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(0, e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getProjectDetail(ProjectDetailReq req) {
        RetrofitUtil.getInstance().getProjectDetail(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean<ProjectDetailBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean<ProjectDetailBean> bean) {
                        if (bean.getCode() == Constant.SUCCESS_CODE) {
                            mView.getProjectDetailSuccess(bean.getData());
                        } else {
                            mView.getProjectDetailFail(bean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(0, e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getMemberRole() {
        RetrofitUtil.getInstance().getMemberRole()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean<ArrayList<MemberRoleBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean<ArrayList<MemberRoleBean>> bean) {
                        if (bean.getCode() == Constant.SUCCESS_CODE) {
                            mView.getMemberRoleSuccess(bean.getData());
                        } else {
                            mView.getMemberRoleFail(bean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(0, e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getCompanyByProjectId(CompanyByProjectIdReq req) {
        RetrofitUtil.getInstance().getCompanyByProjectId(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean<ArrayList<CompanyListBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean<ArrayList<CompanyListBean>> bean) {
                        if (bean.getCode() == Constant.SUCCESS_CODE) {
                            mView.getCompanySuccess(bean.getData());
                        } else {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(0, e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getSpecialtyByProjectId(SpecialtyByProjectIdReq req) {
        RetrofitUtil.getInstance().getSpecialtyByProjectId(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean<ArrayList<SpecialtyBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean<ArrayList<SpecialtyBean>> bean) {
                        if (bean.getCode() == Constant.SUCCESS_CODE) {
                            mView.getSpecialtySuccess(bean.getData());
                        } else {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(0, e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getUserIfoByMobile(UserInfoByMobileReq req) {
        RetrofitUtil.getInstance().getUserIfoByMobile(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean<UserBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean<UserBean> bean) {
                        if (bean.getCode() == Constant.SUCCESS_CODE) {
                            mView.getUserInfoByMobileSuccess(bean.getData());
                        } else {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(0, e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void addProjectMember(AddMemberReq req) {
        RetrofitUtil.getInstance().addProjectMember(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean<UserBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean<UserBean> bean) {
                        if (bean.getCode() == Constant.SUCCESS_CODE) {
                            mView.addProjectMemberSuccess();
                        } else {
                            mView.addProjectMemberFail(bean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(0, e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void updateProjectMember(AddMemberReq req) {
        RetrofitUtil.getInstance().updateProjectMember(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean<UserBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean<UserBean> bean) {
                        if (bean.getCode() == Constant.SUCCESS_CODE) {
                            mView.addProjectMemberSuccess();
                        } else {
                            mView.addProjectMemberFail(bean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(0, e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void deleteProjectMember(DeleteMemberReq req) {
        RetrofitUtil.getInstance().deleteProjectMember(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean bean) {
                        if (bean.getCode() == Constant.SUCCESS_CODE) {
                            mView.deleteProjectMemberSuccess();
                        } else {
                            mView.deleteProjectMemberFail(bean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(0, e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void quitProject(QuitProjectReq req) {
        RetrofitUtil.getInstance().quitProject(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean bean) {
                        if (bean.getCode() == Constant.SUCCESS_CODE) {
                            mView.quitProjectSuccess();
                        } else {
                            mView.quitProjectFail(bean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(0, e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getCompanyByProjectIdTurnOver(CompanyByProjectIdReq req) {
        RetrofitUtil.getInstance().getCompanyByProjectId(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean<ArrayList<CompanyListBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean<ArrayList<CompanyListBean>> bean) {
                        if (bean.getCode() == Constant.SUCCESS_CODE) {
                            mView.getCompanyByProjectIdTurnOverSuccess(bean.getData());
                        } else {
                            mView.getCompanyByProjectIdTurnOverFail(bean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(0, e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getMemberByProjectIdTurnOver(MemberByProjectIdReq req) {
        RetrofitUtil.getInstance().getMemberByProjectIdTurnOver(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean<ArrayList<ProjectMemberBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean<ArrayList<ProjectMemberBean>> bean) {
                        if (bean.getCode() == Constant.SUCCESS_CODE) {
                            mView.getMemberByProjectIdTurnOverSuccess(bean.getData());
                        } else {
                            mView.getMemberByProjectIdTurnOverFail(bean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(0, e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void turnOverProject(TurnOverProjectReq req) {
        RetrofitUtil.getInstance().turnOverProject(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean bean) {
                        if (bean.getCode() == Constant.SUCCESS_CODE) {
                            mView.turnOverProjectSuccess();
                        } else {
                            mView.turnOverProjectOverFail(bean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(0, e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
