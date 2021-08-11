package com.huiguangongdi.presenter;

import com.huiguangongdi.base.net.RetrofitUtil;
import com.huiguangongdi.base.presenter.BasePresenter;
import com.huiguangongdi.req.QualityManagerReq;
import com.huiguangongdi.view.QualityReportView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class QualityReportPresenter extends BasePresenter<QualityReportView> {
    public void getQualityReport(QualityManagerReq req, boolean misQualityOrSafe) {
        RetrofitUtil.getInstance().getQualityReport(req, misQualityOrSafe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addReqs(d);
                    }

                    @Override
                    public void onNext(String bean) {
                        mView.getQualityReportSuccess(bean);
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
