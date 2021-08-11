package com.huiguangongdi.fragment;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cy.dialog.BaseDialog;
import com.google.zxing.activity.CaptureActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.huiguangongdi.BaseApplication;
import com.huiguangongdi.R;
import com.huiguangongdi.activity.AddProjectActivity;
import com.huiguangongdi.activity.CreateProjectActivity;
import com.huiguangongdi.activity.ProjectDetailActivity;
import com.huiguangongdi.activity.QrCodeActivity;
import com.huiguangongdi.activity.QualityManagerActivity;
import com.huiguangongdi.activity.SafeManagerActivity;
import com.huiguangongdi.adapter.ProjectTabAdapter;
import com.huiguangongdi.base.activity.BaseFragment;
import com.huiguangongdi.bean.ProjectProblemBean;
import com.huiguangongdi.bean.ProjectTabBean;
import com.huiguangongdi.common.Extra;
import com.huiguangongdi.event.DrawerEvent;
import com.huiguangongdi.presenter.ProjectPresenter;
import com.huiguangongdi.req.ProjectProjectNumReq;
import com.huiguangongdi.utils.DisplayUtil;
import com.huiguangongdi.utils.SPUtil;
import com.huiguangongdi.view.ProjectFragmentView;
import com.jakewharton.rxbinding4.view.RxView;
import com.tbruyelle.rxpermissions3.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProjectFragment extends BaseFragment<ProjectPresenter> implements ProjectFragmentView, View.OnClickListener {

    @BindView(R.id.topV)
    View mTitleV;
    @BindView(R.id.drawerIv)
    ImageView mDrawerIv;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.detailIv)
    ImageView mDetailIv;
    @BindView(R.id.qualityProblemV)
    View mQualityProblemV;
    @BindView(R.id.safetyProblemV)
    View mSafetyProblemV;
    @BindView(R.id.moreIv)
    View mMoreIv;
    @BindView(R.id.qualityProblemNum)
    TextView mQualityProblemNum;
    @BindView(R.id.safetyProblemNum)
    TextView mSafetyProblemNum;
    @BindView(R.id.projectNameTv)
    TextView mProjectNameTv;

    private TextView mScanTv;
    private BaseDialog mNoProjectDialog;
    private PopupWindow mMenuPopup;
    private RxPermissions mRxPermissions;

    private boolean mIsProjectListNull = false;
    private final static int REQ_CODE = 1028;

    @Override
    protected int setContentViewID() {
        return R.layout.fragment_project;
    }

    @Override
    protected ProjectPresenter getPresenter() {
        return new ProjectPresenter();
    }

    @Override
    protected void initView() {
        initRv();
        initImmersionBar();
        initNoProjectDialog();
    }

    private void initImmersionBar() {
        ImmersionBar.setTitleBar(this, mTitleV);
    }

    @Override
    protected void initListener() {
        mDrawerIv.setOnClickListener(this::onClick);
        mDetailIv.setOnClickListener(this::onClick);
        mQualityProblemV.setOnClickListener(this::onClick);
        mSafetyProblemV.setOnClickListener(this::onClick);
        mMoreIv.setOnClickListener(this::onClick);
    }

    @Override
    protected void initData() {

    }

    public void getManagerNum() {
        String projectName = (String) SPUtil.get(BaseApplication.appContext, Extra.SP_Project_Name, getString(R.string.no_project));
        mProjectNameTv.setText(projectName);
        int projectId = (int) SPUtil.get(getActivity(), Extra.SP_Project_Id, 0);
        ProjectProjectNumReq req = new ProjectProjectNumReq();
        req.setPid(projectId);
        mPresenter.getProblemNum(req);
    }

    @Override
    public void getProblemNumSuccess(ProjectProblemBean bean) {
        mQualityProblemNum.setText(String.valueOf(bean.getQuality()));
        mSafetyProblemNum.setText(String.valueOf(bean.getSecurity()));
    }

    private void initRv() {
        List<ProjectTabBean> list = new ArrayList<ProjectTabBean>();
        list.add(new ProjectTabBean(R.mipmap.quality_assurance, getString(R.string.quality_assurance)));
        list.add(new ProjectTabBean(R.mipmap.security_management, getString(R.string.security_management)));
        /*list.add(new ProjectTabBean(R.mipmap.schedule_management, getString(R.string.schedule_management)));
        list.add(new ProjectTabBean(R.mipmap.daily_report, getString(R.string.daily_report)));
        list.add(new ProjectTabBean(R.mipmap.workflow, getString(R.string.workflow)));
        list.add(new ProjectTabBean(R.mipmap.real_time_monitoring, getString(R.string.real_time_monitoring)));*/

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ProjectTabAdapter adapter = new ProjectTabAdapter(list);
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (mIsProjectListNull && null != mNoProjectDialog && !mNoProjectDialog.isShowing()) {
                    mNoProjectDialog.show();
                    return;
                }
                switch (position) {
                    case 0:
                        startActivity(new Intent(getActivity(), QualityManagerActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), SafeManagerActivity.class));
                        break;
                }
            }
        });
    }

    private void initNoProjectDialog() {
        mNoProjectDialog = new BaseDialog(getActivity());
        mNoProjectDialog.contentView(R.layout.dialog_no_project)
                .gravity(Gravity.CENTER)
                .animType(BaseDialog.AnimInType.CENTER)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true);
        mNoProjectDialog.findViewById(R.id.closeBtn).setOnClickListener(v -> mNoProjectDialog.dismiss());
        mNoProjectDialog.findViewById(R.id.createBtn).setOnClickListener(v -> {
            mNoProjectDialog.dismiss();
            startActivity(new Intent(getActivity(), CreateProjectActivity.class));
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.drawerIv:
                if (mIsProjectListNull && null != mNoProjectDialog && !mNoProjectDialog.isShowing()) {
                    mNoProjectDialog.show();
                    return;
                }
                EventBus.getDefault().post(new DrawerEvent());
                break;
            case R.id.detailIv:
                if (mIsProjectListNull && null != mNoProjectDialog && !mNoProjectDialog.isShowing()) {
                    mNoProjectDialog.show();
                    return;
                }
                int id = (int) SPUtil.get(getActivity(), Extra.SP_Project_Id, -1);
                Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
                intent.putExtra(Extra.Project_Id, Integer.valueOf(id));
                intent.putExtra(Extra.Project_Is_From_Home, true);
                startActivity(intent);
                break;
            case R.id.qualityProblemV:
                if (mIsProjectListNull && null != mNoProjectDialog && !mNoProjectDialog.isShowing()) {
                    mNoProjectDialog.show();
                    return;
                }
                break;
            case R.id.safetyProblemV:
                if (mIsProjectListNull && null != mNoProjectDialog && !mNoProjectDialog.isShowing()) {
                    mNoProjectDialog.show();
                    return;
                }
                break;
            case R.id.moreIv:
                View popupView = LayoutInflater.from(getContext()).inflate(R.layout.layout_project_menu, null, false);
                mMenuPopup = new PopupWindow(popupView, ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.WRAP_CONTENT, true);
                mMenuPopup.showAsDropDown(mMoreIv, (int) (DisplayUtil.getDensity(getActivity()) * -110), 0, Gravity.BOTTOM);
                mScanTv = popupView.findViewById(R.id.scanTv);
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (mMenuPopup != null && mMenuPopup.isShowing()) {
                            mMenuPopup.dismiss();
                            mMenuPopup = null;
                        }
                        return false;
                    }
                });
                popupView.findViewById(R.id.qrCodeTv).setOnClickListener(view1 -> {
                    if (mIsProjectListNull && null != mNoProjectDialog && !mNoProjectDialog.isShowing()) {
                        mNoProjectDialog.show();
                        return;
                    }
                    startActivity(new Intent(getActivity(), QrCodeActivity.class));
                    if (mMenuPopup != null && mMenuPopup.isShowing()) {
                        mMenuPopup.dismiss();
                        mMenuPopup = null;
                    }
                });
                popupView.findViewById(R.id.addProjectTv).setOnClickListener(view1 -> {
                    if (mIsProjectListNull && null != mNoProjectDialog && !mNoProjectDialog.isShowing()) {
                        mNoProjectDialog.show();
                        return;
                    }
                    startActivity(new Intent(getActivity(), AddProjectActivity.class));
                    if (mMenuPopup != null && mMenuPopup.isShowing()) {
                        mMenuPopup.dismiss();
                        mMenuPopup = null;
                    }
                });
                popupView.findViewById(R.id.createProjectTv).setOnClickListener(view1 -> {
                    startActivity(new Intent(getActivity(), CreateProjectActivity.class));
                    if (mMenuPopup != null && mMenuPopup.isShowing()) {
                        mMenuPopup.dismiss();
                        mMenuPopup = null;
                    }
                });
                initPermissions();
                break;
        }
    }

    private void initPermissions() {
        mRxPermissions = new RxPermissions(this);
        RxView.clicks(mScanTv)
                .compose(mRxPermissions.ensure(Manifest.permission.CAMERA))
                .subscribe(granted -> {
                    if (granted) {
                        Intent intentScan = new Intent(getActivity(), CaptureActivity.class);
                        this.startActivityForResult(intentScan, REQ_CODE);
                        if (mMenuPopup != null && mMenuPopup.isShowing()) {
                            mMenuPopup.dismiss();
                            mMenuPopup = null;
                        }
                    } else {
//                        showToast(getString(R.string.download_permissions));
                    }
                });
    }

    public void setIsProjectListNull(boolean isProjectListNull) {
        mIsProjectListNull = isProjectListNull;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data && requestCode == REQ_CODE) {
            String result = data.getStringExtra(CaptureActivity.SCAN_QRCODE_RESULT);
            if (result.contains("http")) return;
            Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
            intent.putExtra(Extra.Project_Id, Integer.valueOf(result));
            intent.putExtra(Extra.Project_Is_From_Home, false);
            startActivity(intent);
        }
    }

    @Override
    public void showError(int flag, Throwable e) {

    }

    @Override
    public void complete(int flag) {

    }
}
