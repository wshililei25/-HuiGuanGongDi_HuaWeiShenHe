package com.huiguangongdi.presenter;

import com.huiguangongdi.base.api.Constant;
import com.huiguangongdi.base.net.RetrofitUtil;
import com.huiguangongdi.base.presenter.BasePresenter;
import com.huiguangongdi.bean.BaseBean;
import com.huiguangongdi.bean.QualityManagerDetailBean;
import com.huiguangongdi.req.QualityManagerDetailReq;
import com.huiguangongdi.req.QualityManagerHandleReq;
import com.huiguangongdi.req.QualityManagerRefuseReq;
import com.huiguangongdi.view.SafeManagerDetailView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

public class SafeManagerDetailPresenter extends BasePresenter<SafeManagerDetailView> {

    public void getSafeManagerDetail(QualityManagerDetailReq req) {
        RetrofitUtil.getInstance().getSafeManagerDetail(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean<QualityManagerDetailBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean<QualityManagerDetailBean> bean) {
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

    public void handleSafeManager(QualityManagerHandleReq req) {
        RetrofitUtil.getInstance().handleSafeManager(req)
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
                            mView.handleQualityManagerSuccess();
                        } else {
                            mView.handleQualityManagerFail(bean.getMsg());
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

    public void refuseSafeManager(QualityManagerRefuseReq req) {
        RetrofitUtil.getInstance().refuseSafeManager(req)
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
                            mView.refuseQualityManagerSuccess();
                        } else {
                            mView.refuseQualityManagerFail(bean.getMsg());
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
