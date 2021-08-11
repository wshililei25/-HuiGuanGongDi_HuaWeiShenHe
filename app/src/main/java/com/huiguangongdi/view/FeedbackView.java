package com.huiguangongdi.view;


import com.huiguangongdi.base.presenter.BaseContract;

public interface FeedbackView extends BaseContract.BaseView {

    void feedbackSuccess(String msg);

    void feedbackFail(String msg);

}
