package com.huiguangongdi.activity;

import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.huiguangongdi.R;
import com.huiguangongdi.base.activity.BaseActivity;
import com.huiguangongdi.common.Extra;
import com.huiguangongdi.presenter.ProjectDetailPresenter;
import com.huiguangongdi.widget.TitleBar;

import butterknife.BindView;

public class SuccessActivity extends BaseActivity {

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.contentTv)
    TextView mContentTv;
    @BindView(R.id.btn)
    TextView mBtn;

    @Override
    protected int setContentViewID() {
        return R.layout.activity_success;
    }

    @Override
    protected ProjectDetailPresenter getPresenter() {
        return new ProjectDetailPresenter();
    }

    @Override
    protected void initView() {
        initImmersionBar();

        if (getIntent().hasExtra(Extra.Project_Success_Title)) {
            mTitleBar.getTitleTv().setText(getIntent().getStringExtra(Extra.Project_Success_Title));
        }
        if (getIntent().hasExtra(Extra.Project_Success_Content)) {
            mContentTv.setText(getIntent().getStringExtra(Extra.Project_Success_Content));
        }
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
        mBtn.setOnClickListener(view -> finish());
    }

    @Override
    protected void initData() {

    }
}