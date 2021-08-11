package com.huiguangongdi.base.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.huiguangongdi.base.presenter.BaseContract;
import com.huiguangongdi.base.ui.BaseUI;
import com.huiguangongdi.base.ui.IBaseUI;

import butterknife.ButterKnife;

public abstract class BaseFragment<T extends BaseContract.BasePresenter> extends Fragment implements IBaseUI {

    private BaseUI baseUI;
    protected T mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(setContentViewID(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        baseUI = new BaseUI(getActivity());
        ButterKnife.bind(this, view);
        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }

        initView();
        initListener();
        initData();
    }

    protected abstract int setContentViewID();

    protected abstract T getPresenter();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.cancelAll();
            mPresenter.detachView();
        }
        System.gc();
    }


    @Override
    public void toActivity(Class<?> clas) {
        baseUI.toActivity(clas);
    }

    @Override
    public void showToast(String msg) {
        baseUI.showToast(msg);
    }

    @Override
    public void showLongToast(String msg) {
        baseUI.showLongToast(msg);
    }

    @Override
    public void showProgress() {
        baseUI.showProgress();
    }

    @Override
    public void showProgress(String msg) {
        baseUI.showProgress(msg);
    }

    @Override
    public void showProgress(boolean cancel) {
        baseUI.showProgress(cancel);
    }

    @Override
    public void showProgress(String msg, boolean cancel) {
        baseUI.showProgress(msg, cancel);
    }

    @Override
    public void dismissProgress() {
        baseUI.dismissProgress();
    }
}
