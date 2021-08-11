package com.huiguangongdi.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.huiguangongdi.R;
import com.huiguangongdi.adapter.CompanyAdapter;
import com.huiguangongdi.adapter.CompanySelectAdapter;
import com.huiguangongdi.base.activity.BaseActivity;
import com.huiguangongdi.bean.CompanyListBean;
import com.huiguangongdi.bean.SpecialtyBean;
import com.huiguangongdi.common.Extra;
import com.huiguangongdi.common.ResultCode;
import com.huiguangongdi.presenter.CompanyPresenter;
import com.huiguangongdi.req.AddCompanyReq;
import com.huiguangongdi.req.CompanyReq;
import com.huiguangongdi.req.CreateProjectReq;
import com.huiguangongdi.view.CompanyView;
import com.huiguangongdi.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 参与项目公司
 */
public class CompanyActivity extends BaseActivity<CompanyPresenter> implements CompanyView, View.OnClickListener {

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.selectRecyclerView)
    RecyclerView mSelectRecyclerView;
    @BindView(R.id.searchEt)
    EditText mSearchEt;
    @BindView(R.id.addV)
    View mAddV;
    @BindView(R.id.addTv)
    View mAddTv;
    @BindView(R.id.btn)
    TextView mBtnTv;
    @BindView(R.id.contractWithTv)
    TextView mContractWithTv;

    private CompanyAdapter mAdapter;
    private CompanySelectAdapter mSelectAdapter;
    private List<CompanyListBean> mSelectList = new ArrayList<>();
    private boolean mProjectIsFromDetail;
    private CompanyListBean mSelectCompanyListBean;
    private CreateProjectReq mCreateProjectReq;

    @Override
    protected int setContentViewID() {
        return R.layout.activity_company;
    }

    @Override
    protected CompanyPresenter getPresenter() {
        return new CompanyPresenter();
    }

    @Override
    protected void initView() {
        mProjectIsFromDetail = getIntent().getBooleanExtra(Extra.Project_Is_From_Detail, false);
        mSelectList = getIntent().getParcelableArrayListExtra(Extra.Specialty_List);
        mCreateProjectReq = getIntent().getParcelableExtra(Extra.Project_Info);
        initRv();
        initSelectRv();
        if (mProjectIsFromDetail) {
            mBtnTv.setText(getString(R.string.ensure));
        } else {
            mTitleBar.getRightTv().setText(getString(R.string.skip));
        }
    }

    private void initRv() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CompanyAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                mSelectCompanyListBean = mAdapter.getData().get(position);
                if (mSelectCompanyListBean.isSelectFromDetail() && mSelectCompanyListBean.isSelect()) {
                    mSelectCompanyListBean = null;
                    return;
                }
                mSelectCompanyListBean.setSelect(!mSelectCompanyListBean.isSelect());
                mSelectCompanyListBean.setSelectFromDetail(true);
                if (mProjectIsFromDetail) {
                    mSelectCompanyListBean.setSelectFromDetailNew(true);
                }
                mAdapter.notifyDataSetChanged();
                if (mSelectCompanyListBean.isSelect()) {
                    mSearchEt.setText(mSelectCompanyListBean.getName());
                    mSearchEt.setSelection(mSelectCompanyListBean.getName().length());
                } else {
                    mSearchEt.setText("");
                }
            }
        });
    }

    private void initSelectRv() {
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mSelectRecyclerView.setLayoutManager(flexboxLayoutManager);
        mSelectAdapter = new CompanySelectAdapter();
        mSelectAdapter.setNewInstance(mSelectList);
        mSelectRecyclerView.setAdapter(mSelectAdapter);
        mSelectAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                CompanyListBean bean = mSelectAdapter.getData().get(position);
                if (mProjectIsFromDetail && bean.isSelectFromDetail() && !bean.isSelectFromDetailNew())
                    return;
                for (int i = 0; i < mAdapter.getData().size(); i++) {
                    if (bean.getId() == mAdapter.getData().get(i).getId()) {
                        mAdapter.getData().get(i).setSelect(false);
                    }
                }
                mSelectList.remove(position);
                mSelectAdapter.notifyDataSetChanged();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initListener() {
        mAddTv.setOnClickListener(this);
        mBtnTv.setOnClickListener(this);
        mTitleBar.getBackIv().setOnClickListener(view -> finish());
        mTitleBar.getRightTv().setOnClickListener(view -> {
            if (null != mCreateProjectReq) {
                mCreateProjectReq.setPart_company(new ArrayList<CompanyListBean>());
            }
            Intent intent = new Intent();
            intent.putExtra(Extra.Project_Info, mCreateProjectReq);
            intent.putExtra(Extra.Project_Is_From_Detail, false);
            intent.putParcelableArrayListExtra(Extra.Specialty_List, new ArrayList<SpecialtyBean>());
            intent.setClass(this, ProfessionActivity.class);
            startActivityForResult(intent, 1000);
        });
        mSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getCompanyList();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addTv:
                if (TextUtils.isEmpty(mSearchEt.getText().toString().trim())) return;
                for (CompanyListBean bean : mSelectList) {
                    if (bean.getName().equals(mSearchEt.getText().toString().trim())) {
                        return;
                    }
                }
                if (mSelectCompanyListBean == null) {
                    AddCompanyReq addCompanyReq = new AddCompanyReq();
                    addCompanyReq.setName(mSearchEt.getText().toString().trim());
                    mPresenter.addCompany(addCompanyReq);
                } else {
                    mSelectList.add(mSelectCompanyListBean);

                    mSelectAdapter.setNewInstance(mSelectList);
                    mSelectAdapter.notifyDataSetChanged();
                    mSearchEt.setText("");
                    mAdapter.setNewInstance(new ArrayList<>());
                    mSelectCompanyListBean = null;
                }
                break;
            case R.id.btn:
                if (null == mSelectList || mSelectList.isEmpty()) {
                    showToast(getString(R.string.please_add_participation_project_companies));
                    return;
                }
                if (mProjectIsFromDetail) {
                    Intent intent = new Intent();
                    intent.putParcelableArrayListExtra(Extra.Select_Company, (ArrayList<? extends Parcelable>) mSelectList);
                    setResult(ResultCode.Company, intent);
                    finish();
                } else {
                    if (null != mCreateProjectReq) {
                        mCreateProjectReq.setPart_company(mSelectList);
                    }
                    Intent intent = new Intent();
                    intent.putExtra(Extra.Project_Info, mCreateProjectReq);
                    intent.putExtra(Extra.Project_Is_From_Detail, false);
                    intent.putParcelableArrayListExtra(Extra.Specialty_List, new ArrayList<SpecialtyBean>());
                    intent.setClass(this, ProfessionActivity.class);
                    startActivityForResult(intent, 1000);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ResultCode.Create_Project) {
            setResult(ResultCode.Create_Project);
            finish();
        }
    }

    @Override
    protected void initData() {

    }

    private void getCompanyList() {
        CompanyReq companyReq = new CompanyReq();
        companyReq.setKeyword(mSearchEt.getText().toString().trim());
        mPresenter.getCompany(companyReq);
    }

    @Override
    public void addCompanySuccess(CompanyListBean bean) {
        bean.setSelect(true);
        bean.setSelectFromDetail(true);
        if (mProjectIsFromDetail) {
            bean.setSelectFromDetailNew(true);
        }
        mSelectList.add(bean);
        mSelectAdapter.setNewInstance(mSelectList);
        mSelectAdapter.notifyDataSetChanged();
        mSearchEt.setText("");
        mAdapter.setNewInstance(new ArrayList<>());
        mSelectCompanyListBean = null;
    }

    @Override
    public void addCompanyFail(String msg) {
        showToast(msg);
    }

    @Override
    public void getCompanySuccess(ArrayList<CompanyListBean> list) {
        if (null == list || list.size() == 0) {
            mRecyclerView.setVisibility(View.GONE);
            if (TextUtils.isEmpty(mSearchEt.getText().toString().trim())) {
                mAddV.setVisibility(View.GONE);
            } else {
                mAddV.setVisibility(View.VISIBLE);
                mContractWithTv.setText(mSearchEt.getText().toString().trim());
            }
        } else {
            if (TextUtils.isEmpty(mSearchEt.getText().toString().trim())) {
                mRecyclerView.setVisibility(View.GONE);
            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
            }
            mAddV.setVisibility(View.GONE);
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < mSelectList.size(); j++) {
                    if (mSelectList.get(j).getId() == list.get(i).getId()) {
                        list.get(i).setSelect(true);
                        if (mSelectList.get(j).isSelectFromDetail()) {
                            list.get(i).setSelectFromDetail(true);
                        }
                    }
                }
            }
            mAdapter.setNewInstance(list);
        }
    }

    @Override
    public void getCompanyFail(String msg) {
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