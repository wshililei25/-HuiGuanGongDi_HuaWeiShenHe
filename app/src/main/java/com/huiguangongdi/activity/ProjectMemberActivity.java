package com.huiguangongdi.activity;

import android.content.Intent;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cy.dialog.BaseDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.huiguangongdi.R;
import com.huiguangongdi.adapter.ContractorAdapter;
import com.huiguangongdi.adapter.MemberRoleAdapter;
import com.huiguangongdi.adapter.ProjectMemberAdapter;
import com.huiguangongdi.adapter.SpecialtyNewAdapter;
import com.huiguangongdi.adapter.TurnOverCompanyAdapter;
import com.huiguangongdi.adapter.TurnOverMemberAdapter;
import com.huiguangongdi.base.activity.BaseActivity;
import com.huiguangongdi.bean.CompanyListBean;
import com.huiguangongdi.bean.MemberRoleBean;
import com.huiguangongdi.bean.ProjectDetailBean;
import com.huiguangongdi.bean.ProjectMemberBean;
import com.huiguangongdi.bean.SpecialtyBean;
import com.huiguangongdi.bean.UserBean;
import com.huiguangongdi.common.Extra;
import com.huiguangongdi.common.ResultCode;
import com.huiguangongdi.event.UpdateMemberEvent;
import com.huiguangongdi.presenter.ProjectMemberPresenter;
import com.huiguangongdi.req.AddMemberReq;
import com.huiguangongdi.req.CompanyByProjectIdReq;
import com.huiguangongdi.req.DeleteMemberReq;
import com.huiguangongdi.req.MemberByProjectIdReq;
import com.huiguangongdi.req.ProjectDetailReq;
import com.huiguangongdi.req.ProjectMemberReq;
import com.huiguangongdi.req.QuitProjectReq;
import com.huiguangongdi.req.SpecialtyByProjectIdReq;
import com.huiguangongdi.req.TurnOverProjectReq;
import com.huiguangongdi.req.UserInfoByMobileReq;
import com.huiguangongdi.utils.GlideUtil;
import com.huiguangongdi.view.ProjectMemberView;
import com.huiguangongdi.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 项目成员
 */
public class ProjectMemberActivity extends BaseActivity<ProjectMemberPresenter> implements ProjectMemberView, View.OnClickListener {

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.btn)
    TextView mBtn;
    @BindView(R.id.addApplyV)
    View mAddApplyV;
    @BindView(R.id.numberTv)
    TextView mNumberTv;
    @BindView(R.id.memberPermissionIv)
    ImageView mMemberPermissionIv;

    private BaseDialog mAddMemberDialog;
    private BaseDialog mSpecialtyDialog;
    private BaseDialog mCompanyDialog;
    private BaseDialog mTurnOverProjectDialog;
    private BaseDialog mMemberRoleDialog;
    private int mProjectId;
    private ProjectMemberAdapter mAdapter;
    private String mSelectSpecialtyName;
    private int mSelectSpecialtyId;
    private String mSelectCompanyName;
    private int mSelectCompanyId;
    private boolean mProjectIsFromDetail;
    private ProjectMemberBean mProjectMemberBean;
    private ProjectDetailBean mProjectDetailBean;
    private int mSelectTurnOverId; //被移交人id

    @Override
    protected int setContentViewID() {
        return R.layout.activity_project_member;
    }

    @Override
    protected ProjectMemberPresenter getPresenter() {
        return new ProjectMemberPresenter();
    }

    @Override
    protected void initView() {
        mProjectId = getIntent().getIntExtra(Extra.Project_Id, 0);
        mProjectIsFromDetail = getIntent().getBooleanExtra(Extra.Project_Is_From_Detail, false);
        initImmersionBar();
        initRecyclerView();
        initAddMemberDialog();
    }

    private void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(R.id.titleBar)
                .statusBarDarkFont(true).init();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ProjectMemberAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mTitleBar.getBackIv().setOnClickListener(view -> setResultIntent());
        mMemberPermissionIv.setOnClickListener(this::onClick);
        mAddApplyV.setOnClickListener(this::onClick);
        mBtn.setOnClickListener(this::onClick);
        mTitleBar.getRightTv().setOnClickListener(view -> {
            switch (mProjectDetailBean.getRole()) {
                case 1: //移交项目
                    showProgress();
                    CompanyByProjectIdReq req = new CompanyByProjectIdReq();
                    req.setPid(mProjectId);
                    mPresenter.getCompanyByProjectIdTurnOver(req);
                    break;
                default: //退出项目
                    initQuitProjectDialog();
                    break;
            }
        });
    }

    @Override
    public void getCompanyByProjectIdTurnOverSuccess(ArrayList<CompanyListBean> list) {
        dismissProgress();
        initTurnOverProjectDialog(list);
    }

    @Override
    public void getCompanyByProjectIdTurnOverFail(String msg) {
        dismissProgress();
        showToast(msg);
    }

    private View mSelectResultV;
    private TextView mSelectResultTv;
    private ImageView mHeadTurnOverIv;
    private TextView mNameTurnOverTv;
    private TextView mTagTv;
    private TextView mSpecialtyTv;
    private RecyclerView mCompanyRv;
    private RecyclerView mMemberRv;
    private ImageView mSelectIv;
    private boolean isSelect = false;

    /**
     * 移交项目Dialog
     */
    private void initTurnOverProjectDialog(ArrayList<CompanyListBean> list) {
        mTurnOverProjectDialog = new BaseDialog(this);
        mTurnOverProjectDialog.contentView(R.layout.dialog_turn_over_project)
                .gravity(Gravity.BOTTOM)
                .animType(BaseDialog.AnimInType.BOTTOM)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true);

        mSelectResultV = mTurnOverProjectDialog.findViewById(R.id.selectResultV);
        mSelectResultTv = mTurnOverProjectDialog.findViewById(R.id.selectTv);
        mHeadTurnOverIv = mTurnOverProjectDialog.findViewById(R.id.headIv);
        mNameTurnOverTv = mTurnOverProjectDialog.findViewById(R.id.nameTv);
        mTagTv = mTurnOverProjectDialog.findViewById(R.id.tagTv);
        mSpecialtyTv = mTurnOverProjectDialog.findViewById(R.id.specialtyTv);
        View selectV = mTurnOverProjectDialog.findViewById(R.id.selectV);
        mSelectIv = mTurnOverProjectDialog.findViewById(R.id.selectIv);
        mCompanyRv = mTurnOverProjectDialog.findViewById(R.id.companyRv);
        mCompanyRv.setLayoutManager(new LinearLayoutManager(this));
        mMemberRv = mTurnOverProjectDialog.findViewById(R.id.memberRv);
        mMemberRv.setLayoutManager(new LinearLayoutManager(this));
        TurnOverCompanyAdapter turnOverCompanyAdapter = new TurnOverCompanyAdapter(list);

        selectV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelect = !isSelect;
                mCompanyRv.setAdapter(turnOverCompanyAdapter);
                if (isSelect) {
                    mSelectResultV.setVisibility(View.GONE);
                    mCompanyRv.setVisibility(View.VISIBLE);
                    mMemberRv.setVisibility(View.VISIBLE);
                    mSelectIv.setImageResource(R.mipmap.icon_pullup);
                } else {
                    if (!TextUtils.isEmpty(mNameTurnOverTv.getText().toString())) {
                        mSelectResultV.setVisibility(View.VISIBLE);
                    }
                    mCompanyRv.setVisibility(View.GONE);
                    mMemberRv.setVisibility(View.GONE);
                    mSelectIv.setImageResource(R.mipmap.icon_pulldown);
                }
            }
        });
        turnOverCompanyAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                turnOverCompanyAdapter.getData().get(position).setSelect(true);
                adapter.notifyDataSetChanged();

                MemberByProjectIdReq req = new MemberByProjectIdReq();
                req.setPid(mProjectId);
                req.setCid(turnOverCompanyAdapter.getData().get(position).getId());
                mPresenter.getMemberByProjectIdTurnOver(req);
            }
        });
        TextView btn = (TextView) mTurnOverProjectDialog.findViewById(R.id.btn);
        ImageView closeIv = (ImageView) mTurnOverProjectDialog.findViewById(R.id.closeIv);
        closeIv.setOnClickListener(v -> mTurnOverProjectDialog.dismiss());
        btn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mNameTurnOverTv.getText().toString())) {
                showToast(getString(R.string.please_select_turn_over));
                return;
            }
            showProgress();
            TurnOverProjectReq req = new TurnOverProjectReq();
            req.setPid(mProjectId);
            req.setUid(mSelectTurnOverId);
            mPresenter.turnOverProject(req);
            mTurnOverProjectDialog.dismiss();
        });
        if (!mTurnOverProjectDialog.isShowing()) {
            mTurnOverProjectDialog.show();
        }
    }

    @Override
    public void getMemberByProjectIdTurnOverSuccess(ArrayList<ProjectMemberBean> list) {
        TurnOverMemberAdapter turnOverMemberAdapter = new TurnOverMemberAdapter(list);
        mMemberRv.setAdapter(turnOverMemberAdapter);
        turnOverMemberAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                isSelect = !isSelect;
                mSelectIv.setImageResource(R.mipmap.icon_pulldown);
                mSelectResultV.setVisibility(View.VISIBLE);
                mCompanyRv.setVisibility(View.GONE);
                mMemberRv.setVisibility(View.GONE);
                ProjectMemberBean bean = turnOverMemberAdapter.getData().get(position);
                mSelectTurnOverId = bean.getUid();
                if (!TextUtils.isEmpty(bean.getAvatar())) {
                    GlideUtil.setImageUrl(ProjectMemberActivity.this, bean.getAvatar(), mHeadTurnOverIv);
                }
                mSelectResultTv.setText(bean.getTruename());
                mNameTurnOverTv.setText(bean.getTruename());
                mTagTv.setText(bean.getTag());
                mSpecialtyTv.setText(bean.getCname() + getString(R.string.whippletree) + bean.getWname());
            }
        });
    }

    @Override
    public void getMemberByProjectIdTurnOverFail(String msg) {

    }

    /**
     * 移交项目成功
     */
    @Override
    public void turnOverProjectSuccess() {
        /*ProjectDetailReq req = new ProjectDetailReq();
        req.setId(mProjectId);
        mPresenter.getProjectDetail(req);*/

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    /**
     * 移交项目失败
     *
     * @param msg
     */
    @Override
    public void turnOverProjectOverFail(String msg) {
        dismissProgress();
        showToast(msg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showProgress();
        ProjectDetailReq req = new ProjectDetailReq();
        req.setId(mProjectId);
        mPresenter.getProjectDetail(req);
    }

    @Override
    public void getProjectDetailSuccess(ProjectDetailBean bean) {
        mProjectDetailBean = bean;
        mAdapter.setRole(bean.getRole());
        getMemberList();
        switch (bean.getRole()) {
            case 1:
                mTitleBar.getRightTv().setText(getString(R.string.transfer_project));
                mAddApplyV.setVisibility(View.VISIBLE);
                mNumberTv.setText(String.valueOf(bean.getUser_apply_count()));
                break;
            default:
                mTitleBar.getRightTv().setText(getString(R.string.quit_project));
                break;
        }
    }

    @Override
    public void getProjectDetailFail(String msg) {
        dismissProgress();
        showToast(msg);
    }

    @Override
    public void getProjectMemberSuccess(ArrayList<ProjectMemberBean> list) {
        dismissProgress();
        mAdapter.setNewInstance(list);
        mAdapter.notifyDataSetChanged();
        if (!mProjectIsFromDetail && mAddMemberDialog != null && !mAddMemberDialog.isShowing()) {
            mAddMemberDialog.show();
        }
    }

    @Override
    public void getProjectMemberFail(String msg) {
        dismissProgress();
        showToast(msg);
    }

    private void getMemberList() {
        ProjectMemberReq projectMemberReq = new ProjectMemberReq();
        projectMemberReq.setPid(mProjectId);
        projectMemberReq.setPage(1);
        projectMemberReq.setPagesize(1000);
        mPresenter.getProjectMember(projectMemberReq);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                isAddOrUpdate = true;
                initAddMemberDialog();
                if (mAddMemberDialog != null && !mAddMemberDialog.isShowing()) {
                    mAddMemberDialog.show();
                }
                break;
            case R.id.memberPermissionIv:
                showProgress();
                mPresenter.getMemberRole();
                break;
            case R.id.addApplyV:
                Intent intent = new Intent(this, MemberApplyListActivity.class);
                intent.putExtra(Extra.Project_Id, mProjectId);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void getMemberRoleSuccess(ArrayList<MemberRoleBean> list) {
        dismissProgress();
        initMemberRoleDialog(list);
    }

    @Override
    public void getMemberRoleFail(String msg) {
        dismissProgress();
        showToast(msg);
    }

    private TextView mTitleTv;
    private TextView mDeleteTv;
    private TextView mAffiliatedContractorsTv;
    private TextView mProfessionTv;
    private EditText mPhoneEt;
    private TextView mNameTv;
    private TextView mCompanyTv;
    private EditText mTagEt;
    private ImageView mEditIv;
    private ImageView mHeadIv;
    private TextView mManagerTv;
    private TextView mExecutantTv;
    private TextView mViewerTv;

    private void initAddMemberDialog() {
        mAddMemberDialog = new BaseDialog(this);
        mAddMemberDialog.contentView(R.layout.dialog_add_member)
                .gravity(Gravity.BOTTOM)
                .animType(BaseDialog.AnimInType.BOTTOM)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true);

        mTitleTv = (TextView) mAddMemberDialog.findViewById(R.id.titleTv);
        mDeleteTv = (TextView) mAddMemberDialog.findViewById(R.id.deleteTv);
        mPhoneEt = (EditText) mAddMemberDialog.findViewById(R.id.phoneEt);
        mNameTv = (TextView) mAddMemberDialog.findViewById(R.id.nameTv);
        mCompanyTv = (TextView) mAddMemberDialog.findViewById(R.id.companyTv);
        mTagEt = (EditText) mAddMemberDialog.findViewById(R.id.tagEt);
        mEditIv = (ImageView) mAddMemberDialog.findViewById(R.id.editIv);
        mHeadIv = (ImageView) mAddMemberDialog.findViewById(R.id.headIv);

        mManagerTv = (TextView) mAddMemberDialog.findViewById(R.id.managerTv);
        mExecutantTv = (TextView) mAddMemberDialog.findViewById(R.id.executantTv);
        mViewerTv = (TextView) mAddMemberDialog.findViewById(R.id.viewerTv);
        mAffiliatedContractorsTv = (TextView) mAddMemberDialog.findViewById(R.id.affiliatedContractorsTv);
        ImageView affiliatedContractorsIv = (ImageView) mAddMemberDialog.findViewById(R.id.affiliatedContractorsIv);
        mProfessionTv = (TextView) mAddMemberDialog.findViewById(R.id.professionTv);
        ImageView professionIv = (ImageView) mAddMemberDialog.findViewById(R.id.professionIv);
        ImageView closeIv = (ImageView) mAddMemberDialog.findViewById(R.id.closeIv);
        closeIv.setOnClickListener(v -> mAddMemberDialog.dismiss());

        mPhoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 11) {
                    UserInfoByMobileReq req = new UserInfoByMobileReq();
                    req.setMobile(s.toString().trim());
                    mPresenter.getUserIfoByMobile(req);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mManagerTv.setSelected(true);
        mManagerTv.setOnClickListener(v -> {
            mManagerTv.setSelected(true);
            mExecutantTv.setSelected(false);
            mViewerTv.setSelected(false);
        });
        mExecutantTv.setOnClickListener(v -> {
            mManagerTv.setSelected(false);
            mExecutantTv.setSelected(true);
            mViewerTv.setSelected(false);
        });
        mViewerTv.setOnClickListener(v -> {
            mManagerTv.setSelected(false);
            mExecutantTv.setSelected(false);
            mViewerTv.setSelected(true);
        });
        mProfessionTv.setOnClickListener(v -> {
            showProgress();
            SpecialtyByProjectIdReq req = new SpecialtyByProjectIdReq();
            req.setPid(mProjectId);
            mPresenter.getSpecialtyByProjectId(req);
        });
        professionIv.setOnClickListener(v -> {
            showProgress();
            SpecialtyByProjectIdReq req = new SpecialtyByProjectIdReq();
            req.setPid(mProjectId);
            mPresenter.getSpecialtyByProjectId(req);
        });
        mAffiliatedContractorsTv.setOnClickListener(v -> {
            showProgress();
            CompanyByProjectIdReq req = new CompanyByProjectIdReq();
            req.setPid(mProjectId);
            mPresenter.getCompanyByProjectId(req);
        });
        affiliatedContractorsIv.setOnClickListener(v -> {
            showProgress();
            CompanyByProjectIdReq req = new CompanyByProjectIdReq();
            req.setPid(mProjectId);
            mPresenter.getCompanyByProjectId(req);
        });
        mEditIv.setOnClickListener(v -> {
            mPhoneEt.setVisibility(View.VISIBLE);
            mNameTv.setVisibility(View.GONE);
            mCompanyTv.setVisibility(View.GONE);
            mEditIv.setVisibility(View.GONE);
            mPhoneEt.setText("");
            mAffiliatedContractorsTv.setText("");
            mProfessionTv.setText("");
            mTagEt.setText("");
            mHeadIv.setImageResource(R.mipmap.icon_null);
        });
        mAddMemberDialog.findViewById(R.id.btn).setOnClickListener(v -> {
            if (isAddOrUpdate) { //添加
                if (mPhoneEt.getText().toString().trim().isEmpty()) {
                    showToast(getString(R.string.please_input_phone));
                    return;
                }
                if (TextUtils.isEmpty(mSelectCompanyName)) {
                    showToast(getString(R.string.please_select_contractor));
                    return;
                }
                if (TextUtils.isEmpty(mSelectSpecialtyName)) {
                    showToast(getString(R.string.please_select_specialty));
                    return;
                }
                if (mTagEt.getText().toString().trim().isEmpty()) {
                    showToast(getString(R.string.please_input_tag));
                    return;
                }
                AddMemberReq req = new AddMemberReq();
                req.setMobile(mPhoneEt.getText().toString().trim());
                req.setPid(mProjectId);
                if (mManagerTv.isSelected()) {
                    req.setRole(2);
                } else if (mExecutantTv.isSelected()) {
                    req.setRole(3);
                } else if (mViewerTv.isSelected()) {
                    req.setRole(4);
                }
                req.setCompany(mSelectCompanyId);
                req.setWork_type(mSelectSpecialtyId);
                req.setTag(mTagEt.getText().toString().trim());
                showProgress();
                mPresenter.addProjectMember(req);
            } else { //修改

                if (mTagEt.getText().toString().trim().isEmpty()) {
                    showToast(getString(R.string.please_input_tag));
                    return;
                }
                AddMemberReq req = new AddMemberReq();
                req.setMobile(mPhoneEt.getText().toString().trim());
                req.setPid(mProjectId);
                if (mManagerTv.isSelected()) {
                    req.setRole(2);
                } else if (mExecutantTv.isSelected()) {
                    req.setRole(3);
                } else if (mViewerTv.isSelected()) {
                    req.setRole(4);
                }
                req.setCompany(mSelectCompanyId);
                req.setWork_type(mSelectSpecialtyId);
                req.setTag(mTagEt.getText().toString().trim());
                showProgress();
                mPresenter.updateProjectMember(req);
            }
        });
        mDeleteTv.setOnClickListener(view -> {
            if (mAddMemberDialog != null && mAddMemberDialog.isShowing()) {
                mAddMemberDialog.dismiss();
            }
            initDeleteDialog();
        });
    }

    /**
     * 删除成员弹框
     */
    private void initDeleteDialog() {
        BaseDialog dialog = new BaseDialog(this);
        dialog.contentView(R.layout.dialog_delete_member)
                .gravity(Gravity.CENTER)
                .animType(BaseDialog.AnimInType.CENTER)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true)
                .show();
        dialog.findViewById(R.id.confirmBtn).setOnClickListener(v -> {
            dialog.dismiss();
            DeleteMemberReq req = new DeleteMemberReq();
            req.setPid(mProjectId);
            req.setUid(mProjectMemberBean.getId());
            showProgress();
            mPresenter.deleteProjectMember(req);
        });
        dialog.findViewById(R.id.cancelBtn).setOnClickListener(v -> dialog.dismiss());
    }

    @Override
    public void deleteProjectMemberSuccess() {
        ProjectDetailReq req = new ProjectDetailReq();
        req.setId(mProjectId);
        mPresenter.getProjectDetail(req);
    }

    @Override
    public void deleteProjectMemberFail(String msg) {
        dismissProgress();
        showToast(msg);
    }


    @Override
    public void addProjectMemberSuccess() {
        if (mAddMemberDialog != null && mAddMemberDialog.isShowing()) {
            mAddMemberDialog.dismiss();
        }
        mProjectIsFromDetail = true;
        getMemberList();
    }

    @Override
    public void addProjectMemberFail(String msg) {
        dismissProgress();
        showToast(msg);
    }

    @Override
    public void getUserInfoByMobileSuccess(UserBean bean) {
        mPhoneEt.setVisibility(View.GONE);
        mNameTv.setVisibility(View.VISIBLE);
        mCompanyTv.setVisibility(View.VISIBLE);
        if (isAddOrUpdate) {
            mEditIv.setVisibility(View.VISIBLE);
        }
        if (bean != null) {
            mNameTv.setText(bean.getTruename());
            mCompanyTv.setText(bean.getCompany() + getString(R.string.whippletree) + bean.getMajor_name());
            if (!TextUtils.isEmpty(bean.getAvatar())) {
                GlideUtil.setCirclePic(this, bean.getAvatar(), mHeadIv);
            }
        } else {
            mNameTv.setText(mPhoneEt.getText().toString().trim());
            mCompanyTv.setText(getString(R.string.unregistered_users));
        }

    }

    private List<CompanyListBean> mCompanyList;

    @Override
    public void getCompanySuccess(ArrayList<CompanyListBean> list) {
        dismissProgress();
        if (null == mCompanyList) {
            mCompanyList = list;
        }
        initCompanyDialog();
    }

    private void initCompanyDialog() {
        mCompanyDialog = new BaseDialog(this);
        mCompanyDialog.contentView(R.layout.dialog_company)
                .gravity(Gravity.BOTTOM)
                .animType(BaseDialog.AnimInType.BOTTOM)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true);
        RecyclerView recyclerView = mCompanyDialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ContractorAdapter adapter = new ContractorAdapter(mCompanyList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                mSelectCompanyName = mCompanyList.get(position).getName();
                mSelectCompanyId = mCompanyList.get(position).getId();
                for (int i = 0; i < mCompanyList.size(); i++) {
                    if (position == i) {
                        mCompanyList.get(i).setSelect(true);
                    } else {
                        mCompanyList.get(i).setSelect(false);
                    }
                }
                adapter.notifyDataSetChanged();
                mAffiliatedContractorsTv.setText(mSelectCompanyName);
                mCompanyDialog.dismiss();
            }
        });
        mCompanyDialog.findViewById(R.id.backIv).setOnClickListener(v -> mCompanyDialog.dismiss());
        mCompanyDialog.findViewById(R.id.closeIv).setOnClickListener(v -> mCompanyDialog.dismiss());
        if (!mCompanyDialog.isShowing()) {
            mCompanyDialog.show();
        }
    }

    private List<SpecialtyBean> mSpecialtyList;

    @Override
    public void getSpecialtySuccess(ArrayList<SpecialtyBean> list) {
        dismissProgress();
        if (null == mSpecialtyList) {
            mSpecialtyList = list;
        }
        initSpecialtyDialog();
    }

    private void initSpecialtyDialog() {
        mSpecialtyDialog = new BaseDialog(this);
        mSpecialtyDialog.contentView(R.layout.dialog_specialty_new)
                .gravity(Gravity.BOTTOM)
                .animType(BaseDialog.AnimInType.BOTTOM)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true);
        RecyclerView recyclerView = mSpecialtyDialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SpecialtyNewAdapter adapter = new SpecialtyNewAdapter();
        adapter.setNewInstance(mSpecialtyList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                mSelectSpecialtyName = mSpecialtyList.get(position).getName();
                mSelectSpecialtyId = mSpecialtyList.get(position).getId();
                for (int i = 0; i < mSpecialtyList.size(); i++) {
                    if (position == i) {
                        mSpecialtyList.get(i).setSelect(true);
                    } else {
                        mSpecialtyList.get(i).setSelect(false);
                    }
                }
                adapter.notifyDataSetChanged();
                mProfessionTv.setText(mSelectSpecialtyName);
                mSpecialtyDialog.dismiss();
            }
        });
        mSpecialtyDialog.findViewById(R.id.backIv).setOnClickListener(v -> mSpecialtyDialog.dismiss());
        mSpecialtyDialog.findViewById(R.id.closeIv).setOnClickListener(v -> mSpecialtyDialog.dismiss());
        if (!mSpecialtyDialog.isShowing()) {
            mSpecialtyDialog.show();
        }
    }

    private void initMemberRoleDialog(ArrayList<MemberRoleBean> list) {
        if (null == list || list.size() == 0) return;
        mMemberRoleDialog = new BaseDialog(this);
        mMemberRoleDialog.contentView(R.layout.dialog_member_role)
                .gravity(Gravity.BOTTOM)
                .animType(BaseDialog.AnimInType.BOTTOM)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true)
                .show();
        RecyclerView recyclerView = mMemberRoleDialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MemberRoleAdapter adapter = new MemberRoleAdapter(list);
        recyclerView.setAdapter(adapter);

        ImageView closeIv = (ImageView) mMemberRoleDialog.findViewById(R.id.closeIv);
        closeIv.setOnClickListener(v -> mMemberRoleDialog.dismiss());
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private boolean isAddOrUpdate = true; //true：add  false：update

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDrawerEvent(UpdateMemberEvent event) {
        isAddOrUpdate = false;
        mProjectMemberBean = mAdapter.getItem(event.getPosition());
        if (mAddMemberDialog != null && !mAddMemberDialog.isShowing()) {
            mAddMemberDialog.show();
            mPhoneEt.setVisibility(View.GONE);
            mNameTv.setVisibility(View.VISIBLE);
            mCompanyTv.setVisibility(View.VISIBLE);
            mDeleteTv.setVisibility(View.VISIBLE);
            mEditIv.setVisibility(View.GONE);
            mTitleTv.setText(getString(R.string.edit_member));
            mNameTv.setText(mProjectMemberBean.getTruename());
            mCompanyTv.setText(mProjectMemberBean.getCname() + getString(R.string.whippletree) + mProjectMemberBean.getWname());
            mAffiliatedContractorsTv.setText(mProjectMemberBean.getCname());
            mProfessionTv.setText(mProjectMemberBean.getWname());
            mTagEt.setText(mProjectMemberBean.getTag());
            mTagEt.setSelection(mProjectMemberBean.getTag().length());
            if (!TextUtils.isEmpty(mProjectMemberBean.getAvatar())) {
                GlideUtil.setImageUrl(this, mProjectMemberBean.getAvatar(), mHeadIv);
            }

            mPhoneEt.setText(mProjectMemberBean.getMobile());
            switch (mProjectMemberBean.getRole()) {
                case 2:
                    mManagerTv.setSelected(true);
                    mExecutantTv.setSelected(false);
                    mViewerTv.setSelected(false);
                    break;
                case 3:
                    mManagerTv.setSelected(false);
                    mExecutantTv.setSelected(true);
                    mViewerTv.setSelected(false);
                    break;
                case 4:
                    mManagerTv.setSelected(false);
                    mExecutantTv.setSelected(false);
                    mViewerTv.setSelected(true);
                    break;
            }
            mSelectCompanyId = mProjectMemberBean.getCid();
            mSelectSpecialtyId = mProjectMemberBean.getWid();

            if (!TextUtils.isEmpty(mProjectMemberBean.getAvatar())) {
                GlideUtil.setImageUrl(this, mProjectMemberBean.getAvatar(), mHeadIv);
            }
        }

    }

    @Override
    public void showError(int flag, Throwable e) {

    }

    @Override
    public void complete(int flag) {

    }

    /**
     * 退出项目
     */
    private void initQuitProjectDialog() {
        BaseDialog dialog = new BaseDialog(this);
        dialog.contentView(R.layout.dialog_quit_project)
                .gravity(Gravity.CENTER)
                .animType(BaseDialog.AnimInType.CENTER)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true)
                .show();
        dialog.findViewById(R.id.confirmBtn).setOnClickListener(v -> {
            dialog.dismiss();
            QuitProjectReq req = new QuitProjectReq();
            req.setPid(mProjectId);
            showProgress();
            mPresenter.quitProject(req);
        });
        dialog.findViewById(R.id.cancelBtn).setOnClickListener(v -> dialog.dismiss());
    }

    @Override
    public void quitProjectSuccess() {
        dismissProgress();
        showToast(getString(R.string.quit_success));
        Intent intent = new Intent();
        setResult(ResultCode.Create_Quit_Project, intent);
        finish();
    }

    @Override
    public void quitProjectFail(String msg) {
        dismissProgress();
        showToast(msg);
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
        Intent intent = new Intent();
        intent.putExtra(Extra.Project_Member_Total_Count, mAdapter.getItemCount());
        intent.putExtra(Extra.Project_Member_Apply_Count, Integer.valueOf(mNumberTv.getText().toString()));
        setResult(ResultCode.Create_Project_Member, intent);
        finish();
    }

}