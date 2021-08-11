package com.huiguangongdi.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.huiguangongdi.R;
import com.huiguangongdi.base.activity.BaseActivity;
import com.huiguangongdi.common.Extra;
import com.huiguangongdi.presenter.FeedbackPresenter;
import com.huiguangongdi.req.FeedbackReq;
import com.huiguangongdi.view.FeedbackView;
import com.huiguangongdi.widget.TitleBar;

import butterknife.BindView;

/**
 * 反馈与建议
 */
public class FeedbackActivity extends BaseActivity<FeedbackPresenter> implements FeedbackView, View.OnClickListener {

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.et)
    EditText mEt;
    @BindView(R.id.numTv)
    TextView mNumTv;
    @BindView(R.id.btn)
    TextView mBtn;

    @Override
    protected int setContentViewID() {
        return R.layout.activity_feedback;
    }

    @Override
    protected FeedbackPresenter getPresenter() {
        return new FeedbackPresenter();
    }

    @Override
    protected void initView() {
        initImmersionBar();
    }

    private void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(R.id.titleBar)
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    protected void initListener() {
        mTitleBar.getBackIv().setOnClickListener(view -> finish());
        mBtn.setOnClickListener(this::onClick);
        mEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mNumTv.setText(charSequence.length() + getString(R.string.two_hundred));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                if (TextUtils.isEmpty(mEt.getText().toString().trim())) return;
                showProgress();
                FeedbackReq req = new FeedbackReq();
                req.setContent(mEt.getText().toString().trim());
                mPresenter.feedback(req);
                break;

        }
    }

    @Override
    public void feedbackSuccess(String msg) {
        dismissProgress();
        showToast(msg);
        Intent intent = new Intent();
        intent.putExtra(Extra.Project_Success_Title, getString(R.string.propose));
        intent.putExtra(Extra.Project_Success_Content, getString(R.string.submit_success));
        intent.setClass(this, SuccessActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void feedbackFail(String msg) {
        dismissProgress();
        showToast(msg);
    }

    @Override
    public void showError(int flag, Throwable e) {

    }

    @Override
    public void complete(int flag) {

    }
}