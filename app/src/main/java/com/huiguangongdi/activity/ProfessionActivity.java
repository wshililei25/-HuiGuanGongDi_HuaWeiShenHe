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
import com.huiguangongdi.adapter.ProjectSelectSpecialtyAdapter;
import com.huiguangongdi.adapter.ProjectSpecialtyAdapter;
import com.huiguangongdi.adapter.ProjectSpecialtyPopAdapter;
import com.huiguangongdi.base.activity.BaseActivity;
import com.huiguangongdi.bean.ProjectBean;
import com.huiguangongdi.bean.SpecialtyBean;
import com.huiguangongdi.common.Extra;
import com.huiguangongdi.common.ResultCode;
import com.huiguangongdi.pop.SpecialtySelectPopup;
import com.huiguangongdi.presenter.ProfessionPresenter;
import com.huiguangongdi.req.CompanyReq;
import com.huiguangongdi.req.CreateProjectReq;
import com.huiguangongdi.view.ProfessionView;
import com.huiguangongdi.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 专业列表
 */
public class ProfessionActivity extends BaseActivity<ProfessionPresenter> implements ProfessionView, View.OnClickListener {

    @BindView(R.id.parentV)
    View mParentV;
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.selectRecyclerView)
    RecyclerView mSelectRecyclerView;
    @BindView(R.id.addV)
    View mAddV;
    @BindView(R.id.addTv)
    View mAddTv;
    @BindView(R.id.searchEt)
    EditText mSearchEt;
    @BindView(R.id.btn)
    TextView mBtnTv;
    @BindView(R.id.contractWithTv)
    TextView mContractWithTv;
    @BindView(R.id.selectedMajorTv)
    TextView mSelectedMajorTv;
    @BindView(R.id.commonMajorTv)
    TextView mCommonMajorTv;
    @BindView(R.id.searchV)
    View mSearchV;
    @BindView(R.id.lineTopV)
    View mLineTopV;

    private SpecialtySelectPopup mSpecialtySelectPopup;
    private ProjectSpecialtyAdapter mAdapter;
    private ProjectSpecialtyPopAdapter mPopAdapter;
    private ProjectSelectSpecialtyAdapter mSelectAdapter;
    private List<SpecialtyBean> mSelectList = new ArrayList<>();
    private boolean mProjectIsFromDetail;
    private SpecialtyBean mSelectSpecialtyBean;
    private CreateProjectReq mCreateProjectReq;

    @Override
    protected int setContentViewID() {
        return R.layout.activity_profession;
    }

    @Override
    protected ProfessionPresenter getPresenter() {
        return new ProfessionPresenter();
    }

    @Override
    protected void initView() {
        mProjectIsFromDetail = getIntent().getBooleanExtra(Extra.Project_Is_From_Detail, false);
        mSelectList = getIntent().getParcelableArrayListExtra(Extra.Specialty_List);
        mCreateProjectReq = getIntent().getParcelableExtra(Extra.Project_Info);
        initRv();
        initSelectRv();
        initPopup();
        if (mProjectIsFromDetail) {
            mBtnTv.setText(getString(R.string.ensure));
        } else {
            mTitleBar.getRightTv().setText(getString(R.string.skip));
        }
    }

    private void initRv() {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new ProjectSpecialtyAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                mIsSelectSearch = true;
                mSearchList.clear();
                mSelectSpecialtyBean = mAdapter.getData().get(position);
                /*if (mSelectSpecialtyBean.isSelectFromDetail() && mSelectSpecialtyBean.isSelect()) {
                    mSelectSpecialtyBean = null;
                    return;
                }*/
                mSelectSpecialtyBean.setSelect(!mSelectSpecialtyBean.isSelect());
                mSelectSpecialtyBean.setSelectFromDetail(true);
                if (mProjectIsFromDetail) {
                    mSelectSpecialtyBean.setSelectFromDetailNew(true);
                }
                mAdapter.notifyDataSetChanged();
                mSearchEt.setText(mSelectSpecialtyBean.getName());
                mSearchEt.setSelection(mSelectSpecialtyBean.getName().length());
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
        mSelectAdapter = new ProjectSelectSpecialtyAdapter();
        mSelectAdapter.setNewInstance(mSelectList);
        mSelectRecyclerView.setAdapter(mSelectAdapter);
        mSelectAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                SpecialtyBean bean = mSelectAdapter.getData().get(position);
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
                mCreateProjectReq.setWork_type(new ArrayList<SpecialtyBean>());
            }
            showProgress();
            mPresenter.createProjectOne(mCreateProjectReq);
        });
        mSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mIsSelectSearch || TextUtils.isEmpty(s.toString().trim())) return;
                CompanyReq companyReq = new CompanyReq();
                companyReq.setKeyword(s.toString().trim());
                mPresenter.getSpecialtySearch(companyReq);
            }

            @Override
            public void afterTextChanged(Editable s) {
                mIsSelectSearch = false;
            }
        });
    }

    private List<SpecialtyBean> mSearchList = new ArrayList<SpecialtyBean>();

    @Override
    public void getSpecialtySearchSuccess(ArrayList<SpecialtyBean> list) {
        dismissProgress();
        if (null == list || list.size() <= 0) {

            return;
        }
        mSearchList = list;
        hideSoftKeyBoard(mSearchEt);
        mPopAdapter.setNewInstance(list);
        mSpecialtySelectPopup.show();

        for (SpecialtyBean bean : list) {
            if (bean.getName().equals(mSearchEt.getText().toString().trim())) {
                mSelectSpecialtyBean = bean;
            }
        }
    }

    private boolean mIsSelectSearch;

    private void initPopup() {
        int height = mParentV.getHeight() - mTitleBar.getHeight() - mSearchV.getHeight() - mLineTopV.getId();
        mSpecialtySelectPopup = new SpecialtySelectPopup(height);
        mSpecialtySelectPopup.initPopupWindow(this, mSearchV, R.layout.layout_pop_specialty_select);
        mSpecialtySelectPopup.getRecyclerView().setLayoutManager(new LinearLayoutManager(this));
        mPopAdapter = new ProjectSpecialtyPopAdapter();
        mSpecialtySelectPopup.getRecyclerView().setAdapter(mPopAdapter);

        mPopAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                mIsSelectSearch = true;
                mSelectSpecialtyBean = mPopAdapter.getData().get(position);
                mSearchEt.setText(mSelectSpecialtyBean.getName());
                mSearchEt.setSelection(mSelectSpecialtyBean.getName().length());
                mSpecialtySelectPopup.hidden();
            }
        });
    }

    @Override
    public void createProjectSuccess(ProjectBean bean) {
        dismissProgress();
        Intent intent = new Intent(this, CreateProjectFourActivity.class);
        intent.putExtra(Extra.Project, bean);
        startActivityForResult(intent, 1000);
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
    public void createProjectFail(String msg) {
        dismissProgress();
        showToast(msg);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addTv:
                if (TextUtils.isEmpty(mSearchEt.getText().toString().trim())) return;
                for (SpecialtyBean bean : mSelectList) {
                    if (bean.getName().equals(mSearchEt.getText().toString().trim())) {
                        showToast(getString(R.string.professional_selected));
                        return;
                    }
                }
                if (mSearchList.size() > 0) {
                    List<String> searchNameList = new ArrayList<>();
                    for (SpecialtyBean bean : mSearchList) {
                        searchNameList.add(bean.getName());
                    }
                    if (!searchNameList.contains(mSearchEt.getText().toString().trim())) {
                        showToast(getString(R.string.professional_not_exist));
                        return;
                    }
                }
                if (null == mSelectSpecialtyBean || null == mSelectSpecialtyBean.getName()) {
                    showToast(getString(R.string.professional_not_exist));
                    return;
                }
                mSelectList.add(mSelectSpecialtyBean);
                mSelectAdapter.setNewInstance(mSelectList);
                mSelectAdapter.notifyDataSetChanged();
                mSearchEt.setText("");
                mSelectSpecialtyBean = null;
                break;
            case R.id.btn:
                if (null == mSelectList || mSelectList.isEmpty()) {
                    showToast(getString(R.string.please_add_profession_list));
                    return;
                }
                if (mProjectIsFromDetail) {
                    Intent intent = new Intent();
                    intent.putParcelableArrayListExtra(Extra.Select_Specialty, (ArrayList<? extends Parcelable>) mSelectList);
                    setResult(ResultCode.Profession, intent);
                    finish();
                } else {
                    showProgress();
                    mCreateProjectReq.setWork_type(mSelectList);
                    mPresenter.createProjectOne(mCreateProjectReq);
                }
                break;
        }
    }

    @Override
    public void addSpecialtySuccess(SpecialtyBean bean) {
        /*bean.setSelect(true);
        bean.setSelectFromDetail(true);
        if (mProjectIsFromDetail) {
            bean.setSelectFromDetailNew(true);
        }
        mSelectList.add(bean);
        mSelectAdapter.setNewInstance(mSelectList);
        mSelectAdapter.notifyDataSetChanged();
        mSearchEt.setText("");
        mAdapter.setNewInstance(new ArrayList<>());
        mSelectSpecialtyBean = null;*/
    }

    @Override
    public void addSpecialtyFail(String msg) {
        dismissProgress();
        showToast(msg);
    }

    @Override
    protected void initData() {
        showProgress();
        getSpecialtyList();
    }

    private void getSpecialtyList() {
        CompanyReq companyReq = new CompanyReq();
        companyReq.setKeyword("");
        mPresenter.getSpecialty(companyReq);
    }

    @Override
    public void getSpecialtySuccess(ArrayList<SpecialtyBean> list) {
        dismissProgress();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < mSelectList.size(); j++) {
                if (mSelectList.get(j).getId() == list.get(i).getId()) {
                    list.get(i).setSelect(true);
                }
                if (mSelectList.get(j).isSelectFromDetail()) {
                    list.get(i).setSelectFromDetail(true);
                }
            }
        }
        if (list.size() <= 20) {
            mAdapter.setNewInstance(list);
        } else {
            mAdapter.setNewInstance(list.subList(0, 20));
        }
    }

    @Override
    public void getSpecialtyFail(String msg) {
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