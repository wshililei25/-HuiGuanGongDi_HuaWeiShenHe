package com.huiguangongdi.presenter;

import com.huiguangongdi.base.api.Constant;
import com.huiguangongdi.base.net.RetrofitUtil;
import com.huiguangongdi.base.presenter.BasePresenter;
import com.huiguangongdi.bean.BaseBean;
import com.huiguangongdi.bean.CompanyListBean;
import com.huiguangongdi.bean.MemberApplyListBean;
import com.huiguangongdi.bean.SpecialtyBean;
import com.huiguangongdi.req.CompanyByProjectIdReq;
import com.huiguangongdi.req.CompanyReq;
import com.huiguangongdi.req.ExamineAddProjectReq;
import com.huiguangongdi.req.MemberApplyListReq;
import com.huiguangongdi.req.SpecialtyByProjectIdReq;
import com.huiguangongdi.view.MemberApplyListView;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MemberApplyListPresenter extends BasePresenter<MemberApplyListView> {

    public void getMemberApplyList(MemberApplyListReq req) {
        RetrofitUtil.getInstance().getMemberApplyList(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean<ArrayList<MemberApplyListBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean<ArrayList<MemberApplyListBean>> bean) {
                        if (bean.getCode() == Constant.SUCCESS_CODE) {
                            mView.getMemberApplyListSuccess(bean.getData());
                        } else {
                            mView.getMemberApplyListFail(bean.getMsg());
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

    public void getCompanyByProjectId(CompanyByProjectIdReq req) {
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
                            mView.getCompanySuccess(bean.getData());
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

    public void examineAddProject(ExamineAddProjectReq req) {
        RetrofitUtil.getInstance().examineAddProject(req)
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
                            mView.examineAddProjectSuccess();
                        } else {
                            mView.examineAddProjectFail(bean.getMsg());
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
