package com.huiguangongdi.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gyf.immersionbar.ImmersionBar;
import com.huiguangongdi.R;
import com.huiguangongdi.adapter.QualityCompanyAdapter;
import com.huiguangongdi.adapter.QualityManagerAdapter;
import com.huiguangongdi.adapter.QualitySpecialtyAdapter;
import com.huiguangongdi.adapter.QualityStatusAdapter;
import com.huiguangongdi.base.activity.BaseActivity;
import com.huiguangongdi.bean.CompanyListBean;
import com.huiguangongdi.bean.QualityManagerBean;
import com.huiguangongdi.bean.QualityStatusBean;
import com.huiguangongdi.bean.SpecialtyBean;
import com.huiguangongdi.common.Extra;
import com.huiguangongdi.pop.QualityStatusPopup;
import com.huiguangongdi.presenter.SafeManagerPresenter;
import com.huiguangongdi.req.CompanyByProjectIdReq;
import com.huiguangongdi.req.QualityManagerReq;
import com.huiguangongdi.req.SpecialtyByProjectIdReq;
import com.huiguangongdi.utils.DateFormatUtil;
import com.huiguangongdi.utils.SPUtil;
import com.huiguangongdi.view.SafeManagerView;
import com.huiguangongdi.widget.TitleBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * 安全管理
 */
public class SafeManagerActivity extends BaseActivity<SafeManagerPresenter> implements SafeManagerView, View.OnClickListener {

    @BindView(R.id.parentV)
    View mParentV;
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.topV)
    View mTopV;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.createIv)
    ImageView mCreateIv;
    @BindView(R.id.allTv)
    TextView mAllTv;
    @BindView(R.id.companyTv)
    TextView mCompanyTv;
    @BindView(R.id.professionTv)
    TextView mProfessionTv;
    @BindView(R.id.dateTv)
    TextView mDateTv;

    private int mProjectId;
    private QualityManagerAdapter mAdapter;

    private QualityStatusAdapter mQualityStatusAdapter;
    private List<QualityStatusBean> mQualityStatusList = new ArrayList();
    private int mStatus; //选中的状态

    private QualityCompanyAdapter mQualityCompanyAdapter;
    private List<CompanyListBean> mCompanyList;
    private int mCompanyId;

    private QualitySpecialtyAdapter mQualitySpecialtyAdapter;
    private List<SpecialtyBean> mSpecialtyList;
    private int mSpecialtyId;

    private TimePickerView mDateDialog;
    private String mStartDayStr; //选择的开始日期
    private String mEndDayStr; //选择的结束日期

    @Override
    protected int setContentViewID() {
        return R.layout.activity_safe_manager;
    }

    @Override
    protected SafeManagerPresenter getPresenter() {
        return new SafeManagerPresenter();
    }

    @Override
    protected void initView() {
        mProjectId = (int) SPUtil.get(this, Extra.SP_Project_Id, -1);
        initImmersionBar();
        initRecyclerView();

        mQualityStatusList.add(new QualityStatusBean(0, getString(R.string.all), true));
        mQualityStatusList.add(new QualityStatusBean(1, getString(R.string.in_hand), false));
        mQualityStatusList.add(new QualityStatusBean(2, getString(R.string.verification), false));
        mQualityStatusList.add(new QualityStatusBean(3, getString(R.string.off_stocks), false));
    }

    private void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(R.id.titleBar)
                .statusBarDarkFont(true).init();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new QualityManagerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent();
                intent.setClass(SafeManagerActivity.this, SafeManagerDetailActivity.class);
                intent.putExtra(Extra.Manager_Id, mAdapter.getData().get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        CompanyByProjectIdReq req = new CompanyByProjectIdReq();
        req.setPid(mProjectId);
        mPresenter.getCompanyByProjectIdTurnOver(req);
    }

    /**
     * 获取处理人公司成功
     *
     * @param list
     */
    @Override
    public void getCompanyByProjectIdTurnOverSuccess(ArrayList<CompanyListBean> list) {
        mCompanyList = list;
        CompanyListBean bean = new CompanyListBean();
        bean.setName(getString(R.string.all_company));
        bean.setSelect(true);
        mCompanyList.add(0, bean);

        SpecialtyByProjectIdReq req = new SpecialtyByProjectIdReq();
        req.setPid(mProjectId);
        mPresenter.getSpecialtyByProjectId(req);
    }

    /**
     * 获取处理人公司失败
     *
     * @param msg
     */
    @Override
    public void getCompanyByProjectIdTurnOverFail(String msg) {

    }

    /**
     * 获取处理人专业成功
     *
     * @param list
     */
    @Override
    public void getSpecialtySuccess(ArrayList<SpecialtyBean> list) {
        mSpecialtyList = list;
        SpecialtyBean bean = new SpecialtyBean();
        bean.setName(getString(R.string.all_specialty));
        bean.setSelect(true);
        mSpecialtyList.add(0, bean);
    }

    @Override
    public void getSpecialtyFail(String msg) {

    }

    @Override
    protected void initListener() {
        mTitleBar.getBackIv().setOnClickListener(view -> finish());
        mCreateIv.setOnClickListener(this::onClick);
        mAllTv.setOnClickListener(this::onClick);
        mCompanyTv.setOnClickListener(this::onClick);
        mProfessionTv.setOnClickListener(this::onClick);
        mDateTv.setOnClickListener(this::onClick);
        mTitleBar.getRightTv().setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.putExtra(Extra.Quality_Req, mQualityManagerReq);
            intent.putExtra(Extra.Quality_or_Safe, false);
            intent.setClass(this, ReportActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createIv:
                startActivity(new Intent(this, CreateSafeActivity.class));
                break;
            case R.id.allTv:
                initStatusPopup();
                break;
            case R.id.companyTv:
                initCompanyPopup();
                break;
            case R.id.professionTv:
                initSpecialtyPopup();
                break;
            case R.id.dateTv:
                initDatePopup();
                break;
        }
    }

    private void initStatusPopup() {
        int height = mParentV.getHeight() - mTitleBar.getHeight() - mTopV.getHeight();
        QualityStatusPopup popup = new QualityStatusPopup(height);
        popup.initPopupWindow(this, mAllTv, R.layout.layout_pop_quality_status);
        popup.getRecyclerView().setLayoutManager(new LinearLayoutManager(this));
        mQualityStatusAdapter = new QualityStatusAdapter();
        popup.getRecyclerView().setLayoutManager(new LinearLayoutManager(this));
        popup.getRecyclerView().setAdapter(mQualityStatusAdapter);
        mQualityStatusAdapter.setNewInstance(mQualityStatusList);
        mQualityStatusAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                mStatus = mQualityStatusList.get(position).getStatus();
                mAllTv.setText(mQualityStatusList.get(position).getName());
                mAllTv.setTextColor(ContextCompat.getColor(SafeManagerActivity.this, R.color.c_0071BC));
                for (int i = 0; i < mQualityStatusList.size(); i++) {
                    if (position == i) {
                        mQualityStatusList.get(i).setSelect(true);
                    } else {
                        mQualityStatusList.get(i).setSelect(false);
                    }
                }
                adapter.notifyDataSetChanged();
                popup.hidden();
                getQualityManager();
            }
        });
        popup.show();
    }

    private void initCompanyPopup() {
        if (null == mCompanyList) return;
        int height = mParentV.getHeight() - mTitleBar.getHeight() - mTopV.getHeight();
        QualityStatusPopup popup = new QualityStatusPopup(height);
        popup.initPopupWindow(this, mAllTv, R.layout.layout_pop_quality_status);
        popup.getRecyclerView().setLayoutManager(new LinearLayoutManager(this));
        mQualityCompanyAdapter = new QualityCompanyAdapter();
        popup.getRecyclerView().setLayoutManager(new LinearLayoutManager(this));
        popup.getRecyclerView().setAdapter(mQualityCompanyAdapter);
        mQualityCompanyAdapter.setNewInstance(mCompanyList);
        mQualityCompanyAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                mCompanyId = mCompanyList.get(position).getId();
                mCompanyTv.setText(mCompanyList.get(position).getName());
                mCompanyTv.setTextColor(ContextCompat.getColor(SafeManagerActivity.this, R.color.c_0071BC));
                for (int i = 0; i < mCompanyList.size(); i++) {
                    if (position == i) {
                        mCompanyList.get(i).setSelect(true);
                    } else {
                        mCompanyList.get(i).setSelect(false);
                    }
                }
                adapter.notifyDataSetChanged();
                popup.hidden();
                getQualityManager();
            }
        });
        popup.show();
    }

    private void initSpecialtyPopup() {
        if (null == mSpecialtyList) return;
        int height = mParentV.getHeight() - mTitleBar.getHeight() - mTopV.getHeight();
        QualityStatusPopup popup = new QualityStatusPopup(height);
        popup.initPopupWindow(this, mAllTv, R.layout.layout_pop_quality_status);
        popup.getRecyclerView().setLayoutManager(new LinearLayoutManager(this));
        mQualitySpecialtyAdapter = new QualitySpecialtyAdapter();
        popup.getRecyclerView().setLayoutManager(new LinearLayoutManager(this));
        popup.getRecyclerView().setAdapter(mQualitySpecialtyAdapter);
        mQualitySpecialtyAdapter.setNewInstance(mSpecialtyList);
        mQualitySpecialtyAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                mSpecialtyId = mSpecialtyList.get(position).getId();
                mProfessionTv.setText(mSpecialtyList.get(position).getName());
                mProfessionTv.setTextColor(ContextCompat.getColor(SafeManagerActivity.this, R.color.c_0071BC));
                for (int i = 0; i < mSpecialtyList.size(); i++) {
                    if (position == i) {
                        mSpecialtyList.get(i).setSelect(true);
                    } else {
                        mSpecialtyList.get(i).setSelect(false);
                    }
                }
                adapter.notifyDataSetChanged();
                popup.hidden();
                getQualityManager();
            }
        });
        popup.show();
    }

    private TextView mStartDayTv, mEndDayTv;
    private boolean isStartDate = true;
    private Date mStartDate;
    private Date mEndDate;
    private String startDayStr; //选择的开始日期
    private String endDayStr; //选择的结束日期

    private void initDatePopup() {

        int height = mTitleBar.getHeight() + mTopV.getHeight();
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.set(2020, 0, 1);
        endDate.set(2050, 11, 31);
        mDateDialog = new TimePickerBuilder(this, new OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date, View v) {

            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.dialog_quelity_date, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        View timepicker = v.findViewById(R.id.timepicker);
                        mStartDayTv = (TextView) v.findViewById(R.id.startDayTv);
                        mEndDayTv = (TextView) v.findViewById(R.id.endDayTv);
                        v.findViewById(R.id.confirmBtn).setOnClickListener(v1 -> {
                            if (null == mStartDate) {
                                showToast(getString(R.string.please_select_start_time));
                                return;
                            }
                            if (null == mEndDate) {
                                showToast(getString(R.string.please_select_end_time));
                                return;
                            }
                            mDateTv.setTextColor(ContextCompat.getColor(SafeManagerActivity.this, R.color.c_0071BC));
                            mStartDayStr = startDayStr;
                            mEndDayStr = endDayStr;
                            mDateDialog.returnData();
                            mDateDialog.dismiss();
                            getQualityManager();
                        });
                        v.findViewById(R.id.resetBtn).setOnClickListener(v1 -> {
                            startDayStr = "";
                            endDayStr = "";
                            mStartDayTv.setText("");
                            mEndDayTv.setText("");
                            mDateDialog.setDate(selectedDate);
                            isStartDate = true;
                        });
                        v.findViewById(R.id.startDayV).setOnClickListener(view -> {
                            isStartDate = true;
                            timepicker.setVisibility(View.VISIBLE);
                        });
                        v.findViewById(R.id.endDayV).setOnClickListener(view -> {
                            isStartDate = false;
                            timepicker.setVisibility(View.VISIBLE);
                        });
                    }
                })
                .setContentTextSize(15)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLineSpacingMultiplier(2.6f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setTextColorCenter(ContextCompat.getColor(this, R.color.c_0071BC))
                .setDividerColor(ContextCompat.getColor(this, R.color.c_B5C7D3))
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        if (isStartDate) {
                            mStartDayTv.setText(DateFormatUtil.getDateToString(date));
                            startDayStr = mStartDayTv.getText().toString();
                            mStartDate = date;
                        } else {
                            mEndDayTv.setText(DateFormatUtil.getDateToString(date));
                            endDayStr = mEndDayTv.getText().toString();
                            mEndDate = date;
                        }

                    }
                })
                .isShowTop(true)
                .setTopMargin(height)
                .build();
        mDateDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getQualityManager();
    }

    private QualityManagerReq mQualityManagerReq;

    private void getQualityManager() {
        showProgress();
        mQualityManagerReq = new QualityManagerReq();
        mQualityManagerReq.setPid(mProjectId);
        mQualityManagerReq.setStatus(mStatus);
        mQualityManagerReq.setDeal_company(mCompanyId);
        mQualityManagerReq.setWork_type(mSpecialtyId);
        mQualityManagerReq.setStart_date(mStartDayStr);
        mQualityManagerReq.setEnd_date(mEndDayStr);
        mPresenter.getSafeManager(mQualityManagerReq);
    }

    @Override
    public void getProjectMemberSuccess(ArrayList<QualityManagerBean> list) {
        dismissProgress();
        mAdapter.setNewInstance(list);
    }

    @Override
    public void getProjectMemberFail(String msg) {
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