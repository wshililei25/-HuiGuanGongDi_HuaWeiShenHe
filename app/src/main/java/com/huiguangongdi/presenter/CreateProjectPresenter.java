package com.huiguangongdi.presenter;

import com.huiguangongdi.base.api.Constant;
import com.huiguangongdi.base.net.RetrofitUtil;
import com.huiguangongdi.base.presenter.BasePresenter;
import com.huiguangongdi.bean.BaseBean;
import com.huiguangongdi.bean.ProjectBean;
import com.huiguangongdi.req.CreateProjectReq;
import com.huiguangongdi.view.CreateProjectView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CreateProjectPresenter extends BasePresenter<CreateProjectView> {

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
}
