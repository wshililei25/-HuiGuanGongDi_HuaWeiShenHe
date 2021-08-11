package com.huiguangongdi.presenter;

import com.huiguangongdi.base.api.Constant;
import com.huiguangongdi.base.net.RetrofitUtil;
import com.huiguangongdi.base.presenter.BasePresenter;
import com.huiguangongdi.bean.BaseBean;
import com.huiguangongdi.bean.CompanyListBean;
import com.huiguangongdi.bean.QualityManagerBean;
import com.huiguangongdi.bean.SpecialtyBean;
import com.huiguangongdi.req.CompanyByProjectIdReq;
import com.huiguangongdi.req.QualityManagerReq;
import com.huiguangongdi.req.SpecialtyByProjectIdReq;
import com.huiguangongdi.view.SafeManagerView;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SafeManagerPresenter extends BasePresenter<SafeManagerView> {

    public void getSafeManager(QualityManagerReq req) {
        RetrofitUtil.getInstance().getSafeManager(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean<ArrayList<QualityManagerBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean<ArrayList<QualityManagerBean>> bean) {
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
}
