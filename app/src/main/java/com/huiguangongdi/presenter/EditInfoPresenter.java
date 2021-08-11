package com.huiguangongdi.presenter;

import com.huiguangongdi.base.api.Constant;
import com.huiguangongdi.base.net.RetrofitUtil;
import com.huiguangongdi.base.presenter.BasePresenter;
import com.huiguangongdi.bean.BaseBean;
import com.huiguangongdi.bean.ProvinceBean;
import com.huiguangongdi.bean.SpecialtyBean;
import com.huiguangongdi.bean.UserBean;
import com.huiguangongdi.req.CompanyReq;
import com.huiguangongdi.req.EditInfoReq;
import com.huiguangongdi.req.UserInfoByMobileReq;
import com.huiguangongdi.view.EditInfoView;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EditInfoPresenter extends BasePresenter<EditInfoView> {
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

    public void getSpecialty() {
        RetrofitUtil.getInstance().getSpecialty(new CompanyReq())
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

    public void getProvince() {
        RetrofitUtil.getInstance().getProvince()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean<ArrayList<ProvinceBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean<ArrayList<ProvinceBean>> bean) {
                        if (bean.getCode() == Constant.SUCCESS_CODE) {
                            mView.getProvinceSuccess(bean.getData());
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

    public void editUserInfo(EditInfoReq req) {
        RetrofitUtil.getInstance().editUserInfo(req)
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
                            mView.editUserInfoSuccess(bean);
                        } else {
                            mView.editUserInfoFail(bean.getMsg());
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
