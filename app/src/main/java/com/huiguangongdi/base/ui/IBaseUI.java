package com.huiguangongdi.base.ui;


public interface IBaseUI {
    void toActivity(Class<?> clas);

    void showToast(String msg);

    void showLongToast(String msg);

    void showProgress();

    void showProgress(String msg);

    void showProgress(boolean cancel);

    void showProgress(String msg,boolean cancel);

    void dismissProgress();
}
