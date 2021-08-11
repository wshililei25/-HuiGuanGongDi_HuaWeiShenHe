package com.huiguangongdi.presenter;

import com.huiguangongdi.base.api.Constant;
import com.huiguangongdi.base.net.RetrofitUtil;
import com.huiguangongdi.base.presenter.BasePresenter;
import com.huiguangongdi.bean.BaseBean;
import com.huiguangongdi.bean.ToDoBean;
import com.huiguangongdi.req.ToDoReq;
import com.huiguangongdi.view.ToDoView;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ToDoPresenter extends BasePresenter<ToDoView> {

    public void getToDoList(ToDoReq req) {
        RetrofitUtil.getInstance().getToDoList(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean<ArrayList<ToDoBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean<ArrayList<ToDoBean>> bean) {
                        if (bean.getCode() == Constant.SUCCESS_CODE) {
                            mView.getToDoListSuccess(bean.getData());
                        } else {
                            mView.getToDoListFail(bean.getMsg());
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

    public void getToDoSafeList(ToDoReq req) {
        RetrofitUtil.getInstance().getToDoSafeList(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean<ArrayList<ToDoBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean<ArrayList<ToDoBean>> bean) {
                        if (bean.getCode() == Constant.SUCCESS_CODE) {
                            mView.getToDoSafeListSuccess(bean.getData());
                        } else {
                            mView.getToDoSafeListFail(bean.getMsg());
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
