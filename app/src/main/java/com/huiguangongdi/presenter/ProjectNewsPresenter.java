package com.huiguangongdi.presenter;

import com.huiguangongdi.base.api.Constant;
import com.huiguangongdi.base.net.RetrofitUtil;
import com.huiguangongdi.base.presenter.BasePresenter;
import com.huiguangongdi.bean.BaseBean;
import com.huiguangongdi.bean.ProjectNewsBean;
import com.huiguangongdi.req.ProjectNewsReq;
import com.huiguangongdi.view.ProjectNewsView;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProjectNewsPresenter extends BasePresenter<ProjectNewsView> {

    public void getProjectNews(ProjectNewsReq req) {
        RetrofitUtil.getInstance().getProjectNews(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean<ArrayList<ProjectNewsBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean<ArrayList<ProjectNewsBean>> bean) {
                        if (bean.getCode() == Constant.SUCCESS_CODE) {
                            mView.getProjectNewsSuccess(bean.getData());
                        } else {
                            mView.getProjectNewsFail(bean.getMsg());
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
