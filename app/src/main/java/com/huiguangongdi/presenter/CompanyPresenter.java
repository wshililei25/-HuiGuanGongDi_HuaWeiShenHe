package com.huiguangongdi.presenter;

import com.huiguangongdi.base.api.Constant;
import com.huiguangongdi.base.net.RetrofitUtil;
import com.huiguangongdi.base.presenter.BasePresenter;
import com.huiguangongdi.bean.BaseBean;
import com.huiguangongdi.bean.CompanyListBean;
import com.huiguangongdi.req.AddCompanyReq;
import com.huiguangongdi.req.CompanyReq;
import com.huiguangongdi.view.CompanyView;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CompanyPresenter extends BasePresenter<CompanyView> {

    public void getCompany(CompanyReq req) {
        RetrofitUtil.getInstance().getCompany(req)
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
                            mView.getCompanyFail(bean.getMsg());
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

    public void addCompany(AddCompanyReq req) {
        RetrofitUtil.getInstance().addCompany(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean<CompanyListBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean<CompanyListBean> bean) {
                        if (bean.getCode() == Constant.SUCCESS_CODE) {
                            mView.addCompanySuccess(bean.getData());
                        } else {
                            mView.addCompanyFail(bean.getMsg());
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
