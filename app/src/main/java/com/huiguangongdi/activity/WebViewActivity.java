package com.huiguangongdi.activity;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.gyf.immersionbar.ImmersionBar;
import com.huiguangongdi.R;
import com.huiguangongdi.base.activity.BaseActivity;
import com.huiguangongdi.common.Extra;
import com.huiguangongdi.presenter.MinePresenter;
import com.huiguangongdi.widget.TitleBar;
import com.just.agentweb.AgentWeb;

import butterknife.BindView;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.webView)
    ConstraintLayout webView;
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;

    @Override
    protected int setContentViewID() {
        return R.layout.activity_web;
    }

    @Override
    protected MinePresenter getPresenter() {
        return new MinePresenter();
    }

    @Override
    protected void initView() {
        initImmersionBar();
        mTitleBar.getTitleTv().setText(getIntent().getStringExtra(Extra.Title));

        AgentWeb.with(this)
                .setAgentWebParent((ConstraintLayout) webView, new ConstraintLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(getIntent().getStringExtra(Extra.Url));
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
    }

    @Override
    protected void initData() {

    }

}
