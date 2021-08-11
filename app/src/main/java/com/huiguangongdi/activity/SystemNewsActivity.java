package com.huiguangongdi.activity;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.huiguangongdi.R;
import com.huiguangongdi.adapter.SystemNewsAdapter;
import com.huiguangongdi.base.activity.BaseActivity;
import com.huiguangongdi.bean.ProjectNewsBean;
import com.huiguangongdi.presenter.SystemNewsPresenter;
import com.huiguangongdi.req.ProjectNewsReq;
import com.huiguangongdi.view.SystemNewsView;
import com.huiguangongdi.widget.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 系统消息
 */
public class SystemNewsActivity extends BaseActivity<SystemNewsPresenter> implements SystemNewsView, View.OnClickListener {

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private SystemNewsAdapter mAdapter;

    @Override
    protected int setContentViewID() {
        return R.layout.activity_system_news;
    }

    @Override
    protected SystemNewsPresenter getPresenter() {
        return new SystemNewsPresenter();
    }

    @Override
    protected void initView() {
        initImmersionBar();
        initRecyclerView();
    }

    @Override
    protected void initListener() {
        mTitleBar.getBackIv().setOnClickListener(view -> finish());
    }

    private void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(R.id.titleBar)
                .statusBarDarkFont(true).init();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SystemNewsAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        showProgress();
        getMemberApplyList();
    }

    private void getMemberApplyList() {
        ProjectNewsReq req = new ProjectNewsReq();
        req.setPage(1);
        req.setPagesize(2000);
        mPresenter.getSystemNews(req);
    }

    @Override
    public void getProjectNewsSuccess(ArrayList<ProjectNewsBean> list) {
        dismissProgress();
        mAdapter.setNewInstance(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getProjectNewsFail(String msg) {
        dismissProgress();
        showToast(msg);
    }

    @Override
    public void showError(int flag, Throwable e) {

    }

    @Override
    public void complete(int flag) {

    }

    @Override
    public void onClick(View view) {

    }
}