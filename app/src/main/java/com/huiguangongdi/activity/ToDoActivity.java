package com.huiguangongdi.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gyf.immersionbar.ImmersionBar;
import com.huiguangongdi.R;
import com.huiguangongdi.adapter.ToDoAdapter;
import com.huiguangongdi.adapter.ToDoSafeAdapter;
import com.huiguangongdi.base.activity.BaseActivity;
import com.huiguangongdi.bean.ToDoBean;
import com.huiguangongdi.common.Extra;
import com.huiguangongdi.presenter.ToDoPresenter;
import com.huiguangongdi.req.ToDoReq;
import com.huiguangongdi.view.ToDoView;
import com.huiguangongdi.widget.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 个人待办
 */
public class ToDoActivity extends BaseActivity<ToDoPresenter> implements ToDoView, View.OnClickListener {

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.safeRecyclerView)
    RecyclerView mSafeRecyclerView;
    @BindView(R.id.qualityTv)
    TextView mQualityTv;
    @BindView(R.id.securityTv)
    TextView mSecurityTv;

    private ToDoAdapter mAdapter;
    private ToDoSafeAdapter mSafeAdapter;

    @Override
    protected int setContentViewID() {
        return R.layout.activity_todo;
    }

    @Override
    protected ToDoPresenter getPresenter() {
        return new ToDoPresenter();
    }

    @Override
    protected void initView() {
        initImmersionBar();
        initRecyclerView();
    }

    private void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(R.id.titleBar)
                .statusBarDarkFont(true).init();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ToDoAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent();
                intent.setClass(ToDoActivity.this, QualityManagerDetailActivity.class);
                intent.putExtra(Extra.Manager_Id, mAdapter.getData().get(position).getId());
                startActivity(intent);
            }
        });

        mSafeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSafeAdapter = new ToDoSafeAdapter(this);
        mSafeRecyclerView.setAdapter(mSafeAdapter);
        mSafeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent();
                intent.setClass(ToDoActivity.this, QualityManagerDetailActivity.class);
                intent.putExtra(Extra.Manager_Id, mSafeAdapter.getData().get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        showProgress();
        ToDoReq req = new ToDoReq();
        req.setType(1);
        mPresenter.getToDoList(req);

        ToDoReq req2 = new ToDoReq();
        req2.setType(2);
        mPresenter.getToDoSafeList(req2);
    }


    @Override
    protected void initListener() {
        mTitleBar.getBackIv().setOnClickListener(view -> finish());
        mQualityTv.setOnClickListener(this::onClick);
        mSecurityTv.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qualityTv:
                mRecyclerView.setVisibility(View.VISIBLE);
                mSafeRecyclerView.setVisibility(View.GONE);
                mQualityTv.setBackgroundResource(R.drawable.shape_e5f0f8_6);
                mQualityTv.setTextColor(ContextCompat.getColor(this, R.color.c_0071BC));
                mSecurityTv.setBackgroundResource(R.drawable.shape_white_b5c7d3_6);
                mSecurityTv.setTextColor(ContextCompat.getColor(this, R.color.c_8C9AA3));
                break;
            case R.id.securityTv:
                mRecyclerView.setVisibility(View.GONE);
                mSafeRecyclerView.setVisibility(View.VISIBLE);
                mQualityTv.setBackgroundResource(R.drawable.shape_white_b5c7d3_6);
                mQualityTv.setTextColor(ContextCompat.getColor(this, R.color.c_8C9AA3));
                mSecurityTv.setBackgroundResource(R.drawable.shape_e5f0f8_6);
                mSecurityTv.setTextColor(ContextCompat.getColor(this, R.color.c_0071BC));
                break;
        }
    }

    @Override
    public void getToDoListSuccess(ArrayList<ToDoBean> list) {
        dismissProgress();
        mAdapter.setNewInstance(list);
    }

    @Override
    public void getToDoListFail(String msg) {
        dismissProgress();
        showToast(msg);
    }

    @Override
    public void getToDoSafeListSuccess(ArrayList<ToDoBean> list) {
        dismissProgress();
        mSafeAdapter.setNewInstance(list);
    }

    @Override
    public void getToDoSafeListFail(String msg) {
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