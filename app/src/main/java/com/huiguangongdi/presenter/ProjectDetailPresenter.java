package com.huiguangongdi.presenter;

import com.huiguangongdi.base.api.Constant;
import com.huiguangongdi.base.net.RetrofitUtil;
import com.huiguangongdi.base.presenter.BasePresenter;
import com.huiguangongdi.bean.BaseBean;
import com.huiguangongdi.bean.ProjectDetailBean;
import com.huiguangongdi.req.ApplyAddProjectReq;
import com.huiguangongdi.req.ProjectDetailReq;
import com.huiguangongdi.req.UpdateProjectReq;
import com.huiguangongdi.view.ProjectDetailView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProjectDetailPresenter extends BasePresenter<ProjectDetailView> {

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

    public void applyAddProject(ApplyAddProjectReq req) {
        RetrofitUtil.getInstance().applyAddProject(req)
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
                            mView.applyAddProjectSuccess();
                        } else {
                            mView.applyAddProjectFail(bean.getMsg());
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

    public void updateProject(UpdateProjectReq req) {
        RetrofitUtil.getInstance().updateProject(req)
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
                            mView.updateProjectSuccess();
                        } else {
                            mView.updateProjectFail(bean.getMsg());
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
