package com.huiguangongdi.activity;

import android.text.TextUtils;
import android.view.Gravity;
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
import com.huiguangongdi.adapter.MemberApplyListAdapter;
import com.huiguangongdi.adapter.SpecialtyNewAdapter;
import com.huiguangongdi.base.activity.BaseActivity;
import com.huiguangongdi.bean.CompanyListBean;
import com.huiguangongdi.bean.MemberApplyListBean;
import com.huiguangongdi.bean.ProjectMemberBean;
import com.huiguangongdi.bean.SpecialtyBean;
import com.huiguangongdi.common.Extra;
import com.huiguangongdi.enums.MemberApplyType;
import com.huiguangongdi.event.MemberApplyEvent;
import com.huiguangongdi.presenter.MemberApplyListPresenter;
import com.huiguangongdi.req.CompanyByProjectIdReq;
import com.huiguangongdi.req.ExamineAddProjectReq;
import com.huiguangongdi.req.MemberApplyListReq;
import com.huiguangongdi.req.SpecialtyByProjectIdReq;
import com.huiguangongdi.utils.GlideUtil;
import com.huiguangongdi.view.MemberApplyListView;
import com.huiguangongdi.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 项目成员申请列表
 */
public class MemberApplyListActivity extends BaseActivity<MemberApplyListPresenter> implements MemberApplyListView, View.OnClickListener {

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private BaseDialog mRefuseDialog;
    private BaseDialog mAddMemberDialog;
    private BaseDialog mSpecialtyDialog;
    private BaseDialog mCompanyDialog;
    private int mProjectId;
    private MemberApplyListAdapter mAdapter;
    private String mSelectSpecialtyName;
    private int mSelectSpecialtyCode;
    private String mSelectCompanyName;
    private int mSelectCompanyCode;
    private int mRole = 2;
    private int mOp;
    private MemberApplyListBean mMemberApplyListBean;

    @Override
    protected int setContentViewID() {
        return R.layout.activity_member_apply_list;
    }

    @Override
    protected MemberApplyListPresenter getPresenter() {
        return new MemberApplyListPresenter();
    }

    @Override
    protected void initView() {
        mProjectId = getIntent().getIntExtra(Extra.Project_Id, 0);
        initImmersionBar();
        initRecyclerView();
        initRefuseDialog();
        initAddMemberDialog();
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
        mAdapter = new MemberApplyListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        showProgress();
        getMemberApplyList();
    }

    private void getMemberApplyList() {
        MemberApplyListReq req = new MemberApplyListReq();
        req.setPid(mProjectId);
        mPresenter.getMemberApplyList(req);
    }

    @Override
    public void getMemberApplyListSuccess(ArrayList<MemberApplyListBean> list) {
        dismissProgress();
        mAdapter.setNewInstance(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getMemberApplyListFail(String msg) {
        dismissProgress();
        showToast(msg);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDrawerEvent(MemberApplyEvent event) {
        mOp = event.getOp();
        mMemberApplyListBean = mAdapter.getItem(event.getPosition());
        if (event.getOp() == MemberApplyType.REFUSE.getValue()) {
            if (mRefuseDialog != null && !mRefuseDialog.isShowing()) {
                mRefuseDialog.show();
            }
        }
        if (event.getOp() == MemberApplyType.AGREE.getValue()) {
            if (mAddMemberDialog != null && !mAddMemberDialog.isShowing()) {
                mAddMemberDialog.show();
                mNameTv.setText(mMemberApplyListBean.getTruename());
                mCompanyTv.setText(mMemberApplyListBean.getCompany() + getString(R.string.whippletree) + mMemberApplyListBean.getWork_type());
                if (!TextUtils.isEmpty(mMemberApplyListBean.getAvatar())) {
                    GlideUtil.setCirclePic(this, mMemberApplyListBean.getAvatar(), mHeadIv);
                }
            }
        }
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void getMemberRoleSuccess(ArrayList<ProjectMemberBean> list) {
        dismissProgress();
    }

    @Override
    public void getMemberRoleFail(String msg) {
        dismissProgress();
        showToast(msg);
    }

    private TextView mAffiliatedContractorsTv;
    private TextView mProfessionTv;
    private TextView mNameTv;
    private TextView mCompanyTv;
    private EditText mPhoneEt;
    private EditText mTagEt;
    private ImageView mHeadIv;

    private void initRefuseDialog() {
        mRefuseDialog = new BaseDialog(this);
        mRefuseDialog.contentView(R.layout.dialog_refuse_member)
                .gravity(Gravity.CENTER)
                .animType(BaseDialog.AnimInType.CENTER)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true);

        mRefuseDialog.findViewById(R.id.confirmBtn).setOnClickListener(v -> {

            ExamineAddProjectReq req = new ExamineAddProjectReq();
            req.setId(mMemberApplyListBean.getId());
            req.setOp(mOp);
            showProgress();
            mPresenter.examineAddProject(req);
        });
        mRefuseDialog.findViewById(R.id.cancelBtn).setOnClickListener(v -> mRefuseDialog.dismiss());
    }

    @Override
    public void examineAddProjectSuccess() {
        if (mRefuseDialog != null && mRefuseDialog.isShowing()) {
            mRefuseDialog.dismiss();
        }
        if (mAddMemberDialog != null && mAddMemberDialog.isShowing()) {
            mAddMemberDialog.dismiss();
        }
        getMemberApplyList();
    }

    @Override
    public void examineAddProjectFail(String msg) {
        dismissProgress();
        showToast(msg);
    }

    private void initAddMemberDialog() {
        mAddMemberDialog = new BaseDialog(this);
        mAddMemberDialog.contentView(R.layout.dialog_add_member)
                .gravity(Gravity.BOTTOM)
                .animType(BaseDialog.AnimInType.BOTTOM)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true);

        TextView titleTv = (TextView) mAddMemberDialog.findViewById(R.id.titleTv);
        mPhoneEt = (EditText) mAddMemberDialog.findViewById(R.id.phoneEt);
        mNameTv = (TextView) mAddMemberDialog.findViewById(R.id.nameTv);
        mCompanyTv = (TextView) mAddMemberDialog.findViewById(R.id.companyTv);
        mTagEt = (EditText) mAddMemberDialog.findViewById(R.id.tagEt);
        mHeadIv = (ImageView) mAddMemberDialog.findViewById(R.id.headIv);

        TextView managerTv = (TextView) mAddMemberDialog.findViewById(R.id.managerTv);
        TextView executantTv = (TextView) mAddMemberDialog.findViewById(R.id.executantTv);
        TextView viewerTv = (TextView) mAddMemberDialog.findViewById(R.id.viewerTv);
        mAffiliatedContractorsTv = (TextView) mAddMemberDialog.findViewById(R.id.affiliatedContractorsTv);
        ImageView affiliatedContractorsIv = (ImageView) mAddMemberDialog.findViewById(R.id.affiliatedContractorsIv);
        mProfessionTv = (TextView) mAddMemberDialog.findViewById(R.id.professionTv);
        ImageView professionIv = (ImageView) mAddMemberDialog.findViewById(R.id.professionIv);
        ImageView closeIv = (ImageView) mAddMemberDialog.findViewById(R.id.closeIv);
        closeIv.setOnClickListener(v -> mAddMemberDialog.dismiss());

        titleTv.setText(getString(R.string.allocation_identity));
        mPhoneEt.setVisibility(View.GONE);
        mNameTv.setVisibility(View.VISIBLE);
        mCompanyTv.setVisibility(View.VISIBLE);

        managerTv.setSelected(true);
        managerTv.setOnClickListener(v -> {
            mRole = 2;
            managerTv.setSelected(true);
            executantTv.setSelected(false);
            viewerTv.setSelected(false);
        });
        executantTv.setOnClickListener(v -> {
            mRole = 3;
            managerTv.setSelected(false);
            executantTv.setSelected(true);
            viewerTv.setSelected(false);
        });
        viewerTv.setOnClickListener(v -> {
            mRole = 4;
            managerTv.setSelected(false);
            executantTv.setSelected(false);
            viewerTv.setSelected(true);
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

        mAddMemberDialog.findViewById(R.id.btn).setOnClickListener(v -> {
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
            ExamineAddProjectReq req = new ExamineAddProjectReq();
            req.setRole(mRole);
            req.setCompany(mSelectCompanyCode);
            req.setWork_type(mSelectSpecialtyCode);
            req.setTag(mTagEt.getText().toString().trim());
            req.setId(mMemberApplyListBean.getId());
            req.setOp(mOp);
            mPresenter.examineAddProject(req);
        });
    }

    @Override
    public void addProjectMemberSuccess() {
        if (mAddMemberDialog != null && mAddMemberDialog.isShowing()) {
            mAddMemberDialog.dismiss();
        }
        getMemberApplyList();
    }

    @Override
    public void addProjectMemberFail(String msg) {
        dismissProgress();
        showToast(msg);
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
                mSelectCompanyCode = mCompanyList.get(position).getId();
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

    @Override
    public void getSpecialtySuccess(ArrayList<SpecialtyBean> bean) {
        dismissProgress();
        initSpecialtyDialog(bean);
    }

    private void initSpecialtyDialog(ArrayList<SpecialtyBean> list) {
        if (null == list || list.size() == 0) return;
        mSpecialtyDialog = new BaseDialog(this);
        mSpecialtyDialog.contentView(R.layout.dialog_specialty_new)
                .gravity(Gravity.BOTTOM)
                .animType(BaseDialog.AnimInType.BOTTOM)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true);
        RecyclerView recyclerView = mSpecialtyDialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SpecialtyNewAdapter adapter = new SpecialtyNewAdapter();
        adapter.setNewInstance(list);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                mSelectSpecialtyName = list.get(position).getName();
                mSelectSpecialtyCode = list.get(position).getId();
                for (int i = 0; i < list.size(); i++) {
                    if (position == i) {
                        list.get(i).setSelect(true);
                    } else {
                        list.get(i).setSelect(false);
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

    @Override
    public void showError(int flag, Throwable e) {

    }

    @Override
    public void complete(int flag) {

    }
}