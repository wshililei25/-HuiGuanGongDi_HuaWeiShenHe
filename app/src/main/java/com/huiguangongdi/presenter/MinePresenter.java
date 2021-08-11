package com.huiguangongdi.presenter;

import com.huiguangongdi.base.api.Constant;
import com.huiguangongdi.base.net.RetrofitUtil;
import com.huiguangongdi.base.presenter.BasePresenter;
import com.huiguangongdi.bean.BaseBean;
import com.huiguangongdi.bean.UploadPhotoBean;
import com.huiguangongdi.bean.UserBean;
import com.huiguangongdi.req.UserInfoByMobileReq;
import com.huiguangongdi.view.MineView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

public class MinePresenter extends BasePresenter<MineView> {

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

    public void uploadHeadPath(MultipartBody.Part req) {
        RetrofitUtil.getInstance().uploadHeadPath(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean<UploadPhotoBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean<UploadPhotoBean> bean) {
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
