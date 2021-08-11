package com.huiguangongdi.presenter;

import com.huiguangongdi.base.api.Constant;
import com.huiguangongdi.base.net.RetrofitUtil;
import com.huiguangongdi.base.presenter.BasePresenter;
import com.huiguangongdi.bean.BaseBean;
import com.huiguangongdi.bean.BuildingNoBean;
import com.huiguangongdi.bean.BuildingNoSdBean;
import com.huiguangongdi.bean.CompanyListBean;
import com.huiguangongdi.bean.ProjectDetailBean;
import com.huiguangongdi.bean.ProjectMemberBean;
import com.huiguangongdi.bean.SpecialtyBean;
import com.huiguangongdi.req.BuildingNoReq;
import com.huiguangongdi.req.CompanyByProjectIdReq;
import com.huiguangongdi.req.CreateQualityReq;
import com.huiguangongdi.req.MemberByProjectIdReq;
import com.huiguangongdi.req.ProjectDetailReq;
import com.huiguangongdi.req.SpecialtyByProjectIdReq;
import com.huiguangongdi.view.CreateSafeView;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

public class CreateSafePresenter extends BasePresenter<CreateSafeView> {
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

    public void getBuildingNo(BuildingNoReq req) {
        RetrofitUtil.getInstance().getBuildingNo(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean<BuildingNoBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean<BuildingNoBean> bean) {
                        if (bean.getCode() == Constant.SUCCESS_CODE) {
                            mView.getBuildingNoSuccess(bean.getData());
                        } else {
                            mView.getBuildingNoFail(bean.getMsg());
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

    public void getBuildingNoSd(BuildingNoReq req) {
        RetrofitUtil.getInstance().getBuildingNoSd(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean<BuildingNoSdBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean<BuildingNoSdBean> bean) {
                        if (bean.getCode() == Constant.SUCCESS_CODE) {
                            mView.getBuildingNoSdSuccess(bean.getData());
                        } else {
                            mView.getBuildingNoSdFail(bean.getMsg());
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

    public void createSafe(CreateQualityReq req) {
        RetrofitUtil.getInstance().createSafe(req)
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
                            mView.createQualitySuccess();
                        } else {
                            mView.createQualityFail(bean.getMsg());
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

    public void uploadPhotoPath(MultipartBody.Part req) {
        RetrofitUtil.getInstance().uploadPhotoPath(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean<String> bean) {
                        if (bean.getCode() == Constant.SUCCESS_CODE) {
                            mView.uploadPhotoPathSuccess(bean.getData());
                        } else {
                            mView.uploadPhotoPathFail(bean.getMsg());
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
