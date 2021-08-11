package com.huiguangongdi.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.huiguangongdi.R;
import com.huiguangongdi.base.activity.BaseActivity;
import com.huiguangongdi.bean.CompanyListBean;
import com.huiguangongdi.bean.ProjectDetailBean;
import com.huiguangongdi.common.Extra;
import com.huiguangongdi.presenter.ProjectDetailPresenter;
import com.huiguangongdi.req.ProjectDetailReq;
import com.huiguangongdi.view.ProjectDetailView;
import com.huiguangongdi.widget.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 申请加入项目
 */
public class AddProjectActivity extends BaseActivity<ProjectDetailPresenter> implements ProjectDetailView, View.OnClickListener {

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.searchEt)
    EditText mSearchEt;
    @BindView(R.id.btn)
    TextView mBtn;


    @Override
    protected int setContentViewID() {
        return R.layout.activity_add_project;
    }

    @Override
    protected ProjectDetailPresenter getPresenter() {
        return new ProjectDetailPresenter();
    }

    @Override
    protected void initView() {
        initImmersionBar();
    }

    private void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(R.id.titleBar)
                .statusBarDarkFont(true)
                .keyboardEnable(true).init();
    }

    protected void initListener() {
        mTitleBar.getBackIv().setOnClickListener(view -> finish());
        mBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                showProgress();
                ProjectDetailReq req = new ProjectDetailReq();
                req.setId(Integer.valueOf(mSearchEt.getText().toString().trim()));
                mPresenter.getProjectDetail(req);
                break;
        }
    }

    @Override
    public void getProjectDetailSuccess(ProjectDetailBean bean) {
        dismissProgress();
        Intent intent = new Intent(this, ProjectDetailActivity.class);
        intent.putExtra(Extra.Project_Id,Integer.valueOf(mSearchEt.getText().toString().trim()));
        intent.putExtra(Extra.Project_Is_From_Home, false);
        startActivity(intent);
    }

    @Override
    public void getProjectDetailFail(String msg) {
        dismissProgress();
        showToast(msg);
    }

    @Override
    public void updateProjectSuccess() {

    }

    @Override
    public void updateProjectFail(String msg) {

    }

    @Override
    public void applyAddProjectSuccess() {

    }

    @Override
    public void applyAddProjectFail(String msg) {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void showError(int flag, Throwable e) {

    }

    @Override
    public void complete(int flag) {

    }


}