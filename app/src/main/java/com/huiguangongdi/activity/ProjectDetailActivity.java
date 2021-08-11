package com.huiguangongdi.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cy.dialog.BaseDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.huiguangongdi.R;
import com.huiguangongdi.base.activity.BaseActivity;
import com.huiguangongdi.bean.CompanyListBean;
import com.huiguangongdi.bean.ProjectDetailBean;
import com.huiguangongdi.bean.SpecialtyBean;
import com.huiguangongdi.common.Extra;
import com.huiguangongdi.common.ResultCode;
import com.huiguangongdi.presenter.ProjectDetailPresenter;
import com.huiguangongdi.req.ApplyAddProjectReq;
import com.huiguangongdi.req.ProjectDetailReq;
import com.huiguangongdi.req.UpdateProjectReq;
import com.huiguangongdi.view.ProjectDetailView;
import com.huiguangongdi.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 项目详情
 */
public class ProjectDetailActivity extends BaseActivity<ProjectDetailPresenter> implements ProjectDetailView, View.OnClickListener {

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.projectNameEt)
    EditText mProjectNameEt;
    @BindView(R.id.projectIdTv)
    TextView mProjectIdTv;
    @BindView(R.id.projectCreaterTv)
    TextView mProjectCreaterTv;
    @BindView(R.id.projectDescribeEt)
    EditText mProjectDescribeEt;
    @BindView(R.id.ownerEt)
    TextView mOwnerEt;
    @BindView(R.id.companyAddressEt)
    TextView mCompanyAddressEt;
    @BindView(R.id.contactWayTv)
    TextView mContactWayTv;
    @BindView(R.id.membersNumTv)
    TextView mMembersNumTv;
    @BindView(R.id.membersApplyNumTv)
    TextView mMembersApplyNumTv;
    @BindView(R.id.participationProjectCompaniesV)
    View mParticipationProjectCompaniesV;
    @BindView(R.id.participationProjectCompaniesIv)
    ImageView mParticipationProjectCompaniesIv;
    @BindView(R.id.participationProjectCompaniesTv)
    TextView mParticipationProjectCompaniesTv;
    @BindView(R.id.membersNumV)
    View mMembersNumV;
    @BindView(R.id.membersApplyNumIv)
    ImageView mMembersApplyNumIv;
    @BindView(R.id.btn)
    TextView mBtn;
    @BindView(R.id.specialtyV)
    View mSpecialtyV;
    @BindView(R.id.specialtyIv)
    ImageView mSpecialtyIv;
    @BindView(R.id.specialtyTv)
    TextView mSpecialtyTv;

    private BaseDialog mDialog;

    private ProjectDetailBean mProjectDetailBean;
    private int mProjectId;
    private boolean mIsFromHome; //true：从项目主页跳转  false：从申请加入项目页跳转
    private List<CompanyListBean> mCompanyList = new ArrayList<>(); //参与项目公司
    private List<SpecialtyBean> mSpecialtyList = new ArrayList<>(); //专业

    @Override
    protected int setContentViewID() {
        return R.layout.activity_project_detail;
    }

    @Override
    protected ProjectDetailPresenter getPresenter() {
        return new ProjectDetailPresenter();
    }

    @Override
    protected void initView() {
        initImmersionBar();
        mProjectId = getIntent().getIntExtra(Extra.Project_Id, 0);
        mIsFromHome = getIntent().getBooleanExtra(Extra.Project_Is_From_Home, false);
        mParticipationProjectCompaniesV.setEnabled(false);
        mSpecialtyV.setEnabled(false);
        mMembersNumV.setEnabled(false);
        initDialog();
    }

    private void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(R.id.titleBar)
                .statusBarDarkFont(true)
                .init();
    }

    private boolean mIsSave;

    @Override
    protected void initListener() {
        mTitleBar.getBackIv().setOnClickListener(view -> setResultIntent());
        mTitleBar.getRightTv().setOnClickListener(view -> {

            if (mIsSave) {
                if (TextUtils.isEmpty(mProjectNameEt.getText().toString().trim())) {
                    showToast(getString(R.string.please_input_project_name));
                    return;
                }
                if (TextUtils.isEmpty(mProjectDescribeEt.getText().toString().trim())) {
                    showToast(getString(R.string.please_input_project_describe));
                    return;
                }
                if (TextUtils.isEmpty(mOwnerEt.getText().toString().trim())) {
                    showToast(getString(R.string.please_input_project_owner));
                    return;
                }
                if (TextUtils.isEmpty(mCompanyAddressEt.getText().toString().trim())) {
                    showToast(getString(R.string.please_input_company_address));
                    return;
                }
                showProgress();
                UpdateProjectReq req = new UpdateProjectReq();
                req.setId(mProjectId);
                req.setName(mProjectNameEt.getText().toString().trim());
                req.setDescription(mProjectDescribeEt.getText().toString().trim());
                req.setCompany(mOwnerEt.getText().toString().trim());
                req.setAddress(mCompanyAddressEt.getText().toString().trim());
                req.setPart_company(mCompanyList);
                req.setWork_type(mSpecialtyList);
                mPresenter.updateProject(req);
            } else {
                mProjectNameEt.setEnabled(true);
                mProjectDescribeEt.setEnabled(true);
                mOwnerEt.setEnabled(true);
                mCompanyAddressEt.setEnabled(true);
                mParticipationProjectCompaniesV.setEnabled(true);
                mSpecialtyV.setEnabled(true);
                mParticipationProjectCompaniesIv.setVisibility(View.VISIBLE);
                mSpecialtyIv.setVisibility(View.VISIBLE);
                showSoftKeyboard(this);
                mProjectNameEt.setSelection(mProjectNameEt.getText().toString().trim().length());
                mTitleBar.getRightTv().setText(getString(R.string.save));
            }
            mIsSave = !mIsSave;

        });
        mParticipationProjectCompaniesV.setOnClickListener(this::onClick);
        mSpecialtyV.setOnClickListener(this::onClick);
        mMembersNumV.setOnClickListener(this::onClick);
        mBtn.setOnClickListener(this::onClick);
    }

    @Override
    public void updateProjectSuccess() {
        dismissProgress();
        finish();
    }

    @Override
    public void updateProjectFail(String msg) {
        dismissProgress();
        showToast(msg);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.participationProjectCompaniesV:
//                if (mCompanyList.size() == 0) return;
                Intent intent1 = new Intent(this, CompanyActivity.class);
                intent1.putExtra(Extra.Project_Is_From_Detail, true);
                intent1.putParcelableArrayListExtra(Extra.Specialty_List, (ArrayList<? extends Parcelable>) mCompanyList);
                startActivityForResult(intent1, 200);
                break;
            case R.id.specialtyV:
//                if (mSpecialtyList.size() == 0) return;
                Intent intent2 = new Intent(this, ProfessionActivity.class);
                intent2.putExtra(Extra.Project_Is_From_Detail, true);
                intent2.putParcelableArrayListExtra(Extra.Specialty_List, (ArrayList<? extends Parcelable>) mSpecialtyList);
                startActivityForResult(intent2, 202);
                break;
            case R.id.membersNumV:
                Intent intent = new Intent(this, ProjectMemberActivity.class);
                intent.putExtra(Extra.Project_Id, mProjectId);
                intent.putExtra(Extra.Project_Is_From_Detail, true);
                startActivityForResult(intent, 201);
                break;
            case R.id.btn:
                if (mDialog != null && !mDialog.isShowing()) {
                    mDialog.show();
                }
                break;
        }
    }

    private void initDialog() {
        mDialog = new BaseDialog(this);
        mDialog.contentView(R.layout.dialog_apply_add_project)
                .gravity(Gravity.BOTTOM)
                .animType(BaseDialog.AnimInType.BOTTOM)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true);

        EditText editText = (EditText) mDialog.findViewById(R.id.et);
        TextView numTv = (TextView) mDialog.findViewById(R.id.numTv);
        TextView btn = (TextView) mDialog.findViewById(R.id.btn);
        ImageView closeIv = (ImageView) mDialog.findViewById(R.id.closeIv);
        closeIv.setOnClickListener(v -> mDialog.dismiss());
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                numTv.setText(charSequence.length() + getString(R.string.hundred));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btn.setOnClickListener(v -> {
            showProgress();
            ApplyAddProjectReq req = new ApplyAddProjectReq();
            req.setPid(mProjectId);
            req.setReason(editText.getText().toString().trim());
            mPresenter.applyAddProject(req);
        });
    }

    @Override
    public void applyAddProjectSuccess() {
        dismissProgress();
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        startActivity(new Intent(this, SuccessActivity.class));
        finish();
    }

    @Override
    public void applyAddProjectFail(String msg) {
        dismissProgress();
        showToast(msg);
    }

    @Override
    protected void initData() {
        showProgress();
        ProjectDetailReq req = new ProjectDetailReq();
        req.setId(mProjectId);
        mPresenter.getProjectDetail(req);
    }

    @Override
    public void getProjectDetailSuccess(ProjectDetailBean bean) {
        dismissProgress();
        mProjectDetailBean = bean;
        mProjectNameEt.setText(bean.getName());
        mProjectIdTv.setText(String.valueOf(bean.getId()));
        mProjectCreaterTv.setText(bean.getCreate_user_name());
        mProjectDescribeEt.setText(bean.getDescription());
        mOwnerEt.setText(bean.getCompany());
        mCompanyAddressEt.setText(bean.getAddress());
        mContactWayTv.setText(bean.getMobile());
        mParticipationProjectCompaniesTv.setText(bean.getPart_company_name());
        mSpecialtyTv.setText(bean.getWork_type_name());
        mMembersNumTv.setText(bean.getUser_count() + getString(R.string.people));
        mMembersApplyNumTv.setText(getString(R.string.plus) + bean.getUser_apply_count());
        if (mIsFromHome) {
            if (bean.getRole() == 1) {
                mTitleBar.getRightTv().setText(getString(R.string.edit));
                if (bean.getUser_apply_count() > 0) {
                    mMembersApplyNumTv.setVisibility(View.VISIBLE);
                }
            }
            mMembersNumV.setEnabled(true);
            mMembersApplyNumIv.setVisibility(View.VISIBLE);
        } else {
            mParticipationProjectCompaniesV.setEnabled(false);
            mSpecialtyV.setEnabled(false);
            mMembersNumV.setEnabled(false);
            mBtn.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(bean.getPart_company())) {
            String[] companyIds = bean.getPart_company().split(",");
            String[] companyNames = bean.getPart_company_name().split(",");
            for (int i = 0; i < companyIds.length; i++) {
                CompanyListBean companyListBean = new CompanyListBean();
                companyListBean.setId(Integer.valueOf(companyIds[i]));
                companyListBean.setName(companyNames[i]);
                companyListBean.setSelect(true);
                companyListBean.setSelectFromDetail(true);
                mCompanyList.add(companyListBean);
            }
        }
        if (!TextUtils.isEmpty(bean.getWork_type())) {
            String[] companyIds = bean.getWork_type().split(",");
            String[] companyNames = bean.getWork_type_name().split(",");
            for (int i = 0; i < companyIds.length; i++) {
                SpecialtyBean specialtyBean = new SpecialtyBean();
                specialtyBean.setId(Integer.valueOf(companyIds[i]));
                specialtyBean.setName(companyNames[i]);
                specialtyBean.setSelect(true);
                specialtyBean.setSelectFromDetail(true);
                mSpecialtyList.add(specialtyBean);
            }
        }
    }

    @Override
    public void getProjectDetailFail(String msg) {
        dismissProgress();
        showToast(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ResultCode.Company) { //参与项目公司
            mCompanyList = data.getParcelableArrayListExtra(Extra.Select_Company);
            StringBuffer resultBuffer = new StringBuffer();
            for (int i = 0; i < mCompanyList.size(); i++) {
                CompanyListBean result = mCompanyList.get(i);
                if (i == 0) {
                    resultBuffer.append(result.getName());
                } else {
                    resultBuffer.append("；" + result.getName());
                }
            }
            mParticipationProjectCompaniesTv.setText(resultBuffer.toString());
        }
        if (resultCode == ResultCode.Profession) { //专业
            mSpecialtyList = data.getParcelableArrayListExtra(Extra.Select_Specialty);
            StringBuffer resultBuffer = new StringBuffer();
            for (int i = 0; i < mSpecialtyList.size(); i++) {
                SpecialtyBean result = mSpecialtyList.get(i);
                if (i == 0) {
                    resultBuffer.append(result.getName());
                } else {
                    resultBuffer.append("；" + result.getName());
                }
            }
            mSpecialtyTv.setText(resultBuffer.toString());
        }
        if (resultCode == ResultCode.Create_Project_Member) {
            int totalCount = data.getIntExtra(Extra.Project_Member_Total_Count, 0);
            int applyCount = data.getIntExtra(Extra.Project_Member_Apply_Count, 0);
            mMembersNumTv.setText(totalCount + getString(R.string.people));
            if (applyCount == 0) {
                mMembersApplyNumTv.setVisibility(View.GONE);
            } else {
                mMembersApplyNumTv.setText(getString(R.string.plus) + applyCount);
            }
        }
        if (resultCode == ResultCode.Create_Quit_Project) {
            finish();
        }

    }

    @Override
    public void showError(int flag, Throwable e) {

    }

    @Override
    public void complete(int flag) {

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            setResultIntent();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    private void setResultIntent() {
        if (mProjectDetailBean == null) {
            finish();
            return;
        }
        List<CompanyListBean> companyList = new ArrayList<>();
        if (!TextUtils.isEmpty(mProjectDetailBean.getPart_company())) {
            String[] companyIds = mProjectDetailBean.getPart_company().split(",");
            String[] companyNames = mProjectDetailBean.getPart_company_name().split(",");
            for (int i = 0; i < companyIds.length; i++) {
                CompanyListBean companyListBean = new CompanyListBean();
                companyListBean.setId(Integer.valueOf(companyIds[i]));
                companyListBean.setName(companyNames[i]);
                companyListBean.setSelect(true);
                companyListBean.setSelectFromDetail(true);
                companyList.add(companyListBean);
            }
        }
        List<SpecialtyBean> specialtyList = new ArrayList<>();
        if (!TextUtils.isEmpty(mProjectDetailBean.getWork_type())) {
            String[] companyIds = mProjectDetailBean.getWork_type().split(",");
            String[] companyNames = mProjectDetailBean.getWork_type_name().split(",");
            for (int i = 0; i < companyIds.length; i++) {
                SpecialtyBean specialtyBean = new SpecialtyBean();
                specialtyBean.setId(Integer.valueOf(companyIds[i]));
                specialtyBean.setName(companyNames[i]);
                specialtyBean.setSelect(true);
                specialtyBean.setSelectFromDetail(true);
                specialtyList.add(specialtyBean);
            }
        }
        if (!mProjectDetailBean.getName().equals(mProjectNameEt.getText().toString().trim())
                || !mProjectDetailBean.getDescription().equals(mProjectDescribeEt.getText().toString().trim())
                || !mProjectDetailBean.getCompany().equals(mOwnerEt.getText().toString().trim())
                || !mProjectDetailBean.getAddress().equals(mCompanyAddressEt.getText().toString().trim())
                || companyList.size() != mCompanyList.size()
                || specialtyList.size() != mSpecialtyList.size()) {
            initHintDialog();
        } else {
            finish();
        }
    }

    private void initHintDialog() {
        BaseDialog dialog = new BaseDialog(this);
        dialog.contentView(R.layout.dialog_project_save)
                .gravity(Gravity.CENTER)
                .animType(BaseDialog.AnimInType.CENTER)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true)
                .show();
        dialog.findViewById(R.id.confirmBtn).setOnClickListener(v -> {
            dialog.dismiss();
            finish();
        });
        dialog.findViewById(R.id.cancelBtn).setOnClickListener(v -> dialog.dismiss());
    }
}