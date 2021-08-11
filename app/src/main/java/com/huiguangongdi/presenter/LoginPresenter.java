package com.huiguangongdi.presenter;

import com.huiguangongdi.base.api.Constant;
import com.huiguangongdi.base.net.RetrofitUtil;
import com.huiguangongdi.base.presenter.BasePresenter;
import com.huiguangongdi.bean.BaseBean;
import com.huiguangongdi.bean.UserBean;
import com.huiguangongdi.req.GetCodeReq;
import com.huiguangongdi.req.LoginReq;
import com.huiguangongdi.view.LoginView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginView> {

    public void login(String mobile, String code) {
        LoginReq loginReq = new LoginReq();
        loginReq.setMobile(mobile);
        loginReq.setCode(code);
        RetrofitUtil.getInstance().login(loginReq)
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
                            mView.loginSuccess(bean);
                        } else {
                            mView.loginFail(bean);
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

    public void getCode(String mobile) {
        GetCodeReq loginReq = new GetCodeReq();
        loginReq.setMobile(mobile);
        RetrofitUtil.getInstance().getCode(loginReq)
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
                            mView.getCodeSuccess(bean);
                        } else {
                            mView.getCodeFail(bean.getMsg());
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
