package com.huiguangongdi.presenter;

import com.huiguangongdi.base.api.Constant;
import com.huiguangongdi.base.net.RetrofitUtil;
import com.huiguangongdi.base.presenter.BasePresenter;
import com.huiguangongdi.bean.BaseBean;
import com.huiguangongdi.bean.NewsNumBean;
import com.huiguangongdi.bean.UserBean;
import com.huiguangongdi.view.NewsView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsPresenter extends BasePresenter<NewsView> {

    public void getNewNum() {
        RetrofitUtil.getInstance().getNewNum()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<BaseBean<NewsNumBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(BaseBean<NewsNumBean> bean) {
                        if (bean.getCode() == Constant.SUCCESS_CODE) {
                            mView.getNewsNumSuccess(bean.getData());
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

}
