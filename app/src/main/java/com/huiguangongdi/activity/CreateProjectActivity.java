package com.huiguangongdi.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.huiguangongdi.R;
import com.huiguangongdi.base.activity.BaseActivity;
import com.huiguangongdi.bean.CompanyListBean;
import com.huiguangongdi.bean.ProjectBean;
import com.huiguangongdi.common.Extra;
import com.huiguangongdi.common.ResultCode;
import com.huiguangongdi.presenter.CreateProjectPresenter;
import com.huiguangongdi.req.CreateProjectReq;
import com.huiguangongdi.view.CreateProjectView;
import com.huiguangongdi.widget.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 创建项目第一步
 */
public class CreateProjectActivity extends BaseActivity<CreateProjectPresenter> implements CreateProjectView, View.OnClickListener {

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.btn)
    TextView mBtn;
    @BindView(R.id.parentV)
    ViewGroup mParentV;
    @BindView(R.id.nameEt)
    EditText mNameEt;
    @BindView(R.id.addressEt)
    EditText mAddressEt;
    @BindView(R.id.describeEt)
    EditText mDescribeEt;
    @BindView(R.id.companyEt)
    EditText mCompanyEt;

    @Override
    protected int setContentViewID() {
        return R.layout.activity_create_project;
    }

    @Override
    protected CreateProjectPresenter getPresenter() {
        return new CreateProjectPresenter();
    }

    @Override
    protected void initView() {
//        initImmersionBar();
//        AndroidBug5497Workaround.assistActivity(this);
    }

    @Override
    protected void initListener() {
        mTitleBar.getBackIv().setOnClickListener(view -> finish());
        mBtn.setOnClickListener(this::onClick);
    }

    private void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(R.id.titleBar)
                .statusBarDarkFont(true).init();
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                if (mNameEt.getText().toString().trim().isEmpty()) {
                    showToast(getString(R.string.please_input_project_name));
                    return;
                }
                if (mAddressEt.getText().toString().trim().isEmpty()) {
                    showToast(getString(R.string.please_input_project_address));
                    return;
                }
                if (mDescribeEt.getText().toString().trim().isEmpty()) {
                    showToast(getString(R.string.please_input_project_describe));
                    return;
                }
                if (mCompanyEt.getText().toString().trim().isEmpty()) {
                    showToast(getString(R.string.please_development_organization));
                    return;
                }

                CreateProjectReq req = new CreateProjectReq();
                req.setName(mNameEt.getText().toString().trim());
                req.setAddress(mAddressEt.getText().toString().trim());
                req.setDescription(mDescribeEt.getText().toString().trim());
                req.setCompany(mCompanyEt.getText().toString().trim());
                Intent intent = new Intent();
                intent.putExtra(Extra.Project_Info, req);
                intent.putExtra(Extra.Project_Is_From_Detail, false);
                intent.putParcelableArrayListExtra(Extra.Specialty_List, new ArrayList<CompanyListBean>());
                intent.setClass(this, CompanyActivity.class);
                startActivityForResult(intent, 1000);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ResultCode.Create_Project) {
            finish();
        }
    }

    @Override
    public void createProjectSuccess(ProjectBean bean) {

    }

    @Override
    public void createProjectFail(String msg) {

    }

    @Override
    public void showError(int flag, Throwable e) {

    }

    @Override
    public void complete(int flag) {

    }
}