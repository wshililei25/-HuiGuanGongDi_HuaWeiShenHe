package com.huiguangongdi.base.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.huiguangongdi.BaseApplication;
import com.huiguangongdi.utils.MyUtils;
import com.huiguangongdi.widget.LoadProgressDialog;


public class BaseUI implements IBaseUI, DialogInterface.OnDismissListener {

    public Context mContext;
    private LoadProgressDialog progressDialog;

    public BaseUI(Context context) {
        this.mContext = context;
    }

    @Override
    public void toActivity(Class<?> clas) {
        mContext.startActivity(new Intent(mContext, clas));
    }

    @Override
    public void showToast(String msg) {
        BaseApplication.showToast(msg);
    }

    @Override
    public void showLongToast(String msg) {
        BaseApplication.showLongToast(msg);
    }

    @Override
    public void showProgress() {
        MyUtils.backgroundAlpha((Activity) mContext, (float) 0.5);
        if (progressDialog == null) {
            progressDialog = new LoadProgressDialog(mContext);
            progressDialog.setOnDismissListener(BaseUI.this);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void showProgress(final String msg) {
        MyUtils.backgroundAlpha((Activity) mContext, (float) 0.5);
        if (progressDialog == null) {
            progressDialog = new LoadProgressDialog(mContext);
            progressDialog.setOnDismissListener(BaseUI.this);
        }
        progressDialog.setMessage(msg);
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void showProgress(final boolean cancel) {
        MyUtils.backgroundAlpha((Activity) mContext, (float) 0.5);
        if (progressDialog == null) {
            progressDialog = new LoadProgressDialog(mContext);
            progressDialog.setOnDismissListener(BaseUI.this);
        }
        progressDialog.setCanceledOnTouchOutside(cancel);
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void showProgress(final String msg, final boolean cancel) {
        MyUtils.backgroundAlpha((Activity) mContext, (float) 0.5);
        if (progressDialog == null) {
            progressDialog = new LoadProgressDialog(mContext);
            progressDialog.setOnDismissListener(BaseUI.this);
        }
        progressDialog.setMessage(msg);
        progressDialog.setCanceledOnTouchOutside(cancel);
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void dismissProgress() {
        if (null == mContext) return;
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        MyUtils.backgroundAlpha((Activity) mContext, (float) 1.0);
    }
}
