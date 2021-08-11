package com.huiguangongdi.presenter;

import com.huiguangongdi.base.api.Constant;
import com.huiguangongdi.base.net.RetrofitUtil;
import com.huiguangongdi.base.presenter.BasePresenter;
import com.huiguangongdi.bean.BaseBean;
import com.huiguangongdi.bean.ProjectBean;
import com.huiguangongdi.bean.SpecialtyBean;
import com.huiguangongdi.req.AddCompanyReq;
import com.huiguangongdi.req.CompanyReq;
import com.huiguangongdi.req.CreateProjectReq;
import com.huiguangongdi.view.ProfessionView;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfessionPresenter extends BasePresenter<ProfessionView> {
    public void createProjectOne(CreateProjectReq req) {
        RetrofitUtil.getInstance().createProjectOne(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean<ProjectBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean<ProjectBean> bean) {
                        if (bean.getCode() == Constant.SUCCESS_CODE) {
                            mView.createProjectSuccess(bean.getData());
                        } else {
                            mView.createProjectFail(bean.getMsg());
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

    public void getSpecialty(CompanyReq req) {
        RetrofitUtil.getInstance().getSpecialty(req)
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
                            mView.getSpecialtyFail(bean.getMsg());
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

    public void getSpecialtySearch(CompanyReq req) {
        RetrofitUtil.getInstance().getSpecialty(req)
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
                            mView.getSpecialtySearchSuccess(bean.getData());
                        } else {
                            mView.getSpecialtyFail(bean.getMsg());
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

    public void addSpecialty(AddCompanyReq req) {
        RetrofitUtil.getInstance().addSpecialty(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean<SpecialtyBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean<SpecialtyBean> bean) {
                        if (bean.getCode() == Constant.SUCCESS_CODE) {
                            mView.addSpecialtySuccess(bean.getData());
                        } else {
                            mView.addSpecialtyFail(bean.getMsg());
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
