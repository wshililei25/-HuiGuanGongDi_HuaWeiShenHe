package com.huiguangongdi.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cy.dialog.BaseDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.huiguangongdi.R;
import com.huiguangongdi.adapter.CreateProjectManualCreationAdapter;
import com.huiguangongdi.adapter.HorizontalAxesAdapter;
import com.huiguangongdi.adapter.VerticalAxesAdapter;
import com.huiguangongdi.base.activity.BaseActivity;
import com.huiguangongdi.bean.BuildNoSelectBean;
import com.huiguangongdi.bean.CreateProjectHandBean;
import com.huiguangongdi.bean.ProjectBean;
import com.huiguangongdi.bean.VerticalAxesSelectBean;
import com.huiguangongdi.common.Extra;
import com.huiguangongdi.common.ResultCode;
import com.huiguangongdi.event.DeleteManualEvent;
import com.huiguangongdi.presenter.CreateProjectTwoPresenter;
import com.huiguangongdi.req.CreateProjectTwoReq;
import com.huiguangongdi.req.CreateProjectTwoSdReq;
import com.huiguangongdi.utils.SPUtil;
import com.huiguangongdi.view.CreateProjectTwoView;
import com.huiguangongdi.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 创建项目第四步
 */
public class CreateProjectFourActivity extends BaseActivity<CreateProjectTwoPresenter> implements CreateProjectTwoView, View.OnClickListener {

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.axisMapRb)
    RadioButton mAxisMapRb;
    @BindView(R.id.manualCreationRb)
    RadioButton mManualCreationRb;
    @BindView(R.id.axisMapV)
    View mAxisMapV;
    @BindView(R.id.manualCreationV)
    View mManualCreationV;
    @BindView(R.id.createBtn)
    View mCreateBtn;
    @BindView(R.id.dongEt)
    EditText mDongEt;
    @BindView(R.id.numberLayersEt)
    EditText mNumberLayersEt;
    @BindView(R.id.horizontalAxesMinEt)
    TextView mHorizontalAxesMinEt;
    @BindView(R.id.horizontalAxesMaxEt)
    TextView mHorizontalAxesMaxEt;
    @BindView(R.id.verticalAxesMinEt)
    TextView mVerticalAxesMinEt;
    @BindView(R.id.verticalAxesMaxEt)
    TextView mVerticalAxesMaxEt;
    @BindView(R.id.buildingNoEt)
    EditText mBuildingNoEt;
    @BindView(R.id.storeyEt)
    EditText mStoreyEt;
    @BindView(R.id.roomNumberEt)
    EditText mRoomNumberEt;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.horizontalAxesMinV)
    View mHorizontalAxesMinV;
    @BindView(R.id.horizontalAxesMaxV)
    View mHorizontalAxesMaxV;
    @BindView(R.id.verticalAxesMinV)
    View mVerticalAxesMinV;
    @BindView(R.id.verticalAxesMaxV)
    View mVerticalAxesMaxV;
    @BindView(R.id.addTv)
    View mAddTv;

    private BaseDialog mHorizontalAxesStartDialog;
    private BaseDialog mHorizontalAxesEndDialog;
    private BaseDialog mVerticalStartDialog;
    private BaseDialog mVerticalEndDialog;

    private ProjectBean mProjectBean;
    private CreateProjectManualCreationAdapter mAdapter;
    private List<CreateProjectHandBean> mList = new ArrayList<>();
    private List<String> mBuildList = new ArrayList<>(); //楼号
    private List<String> mFloorList = new ArrayList<>(); //楼层
    private List<String> mRoomList = new ArrayList<>(); //房间号

    private List<BuildNoSelectBean> mHorizontalAxesStartList = new ArrayList<>(); //横轴数起始
    private HorizontalAxesAdapter mHorizontalAxesStartAdapter;
    private int mHorizontalAxesStartNo; //选择的起始横轴数
    private int mHorizontalAxesStartPosition;
    private List<BuildNoSelectBean> mHorizontalAxesEndList = new ArrayList<>(); //横轴数结束
    private HorizontalAxesAdapter mHorizontalAxesEndAdapter;
    private int mHorizontalAxesEndNo; //选择的结束横轴数

    private List<VerticalAxesSelectBean> mVerticalAxesStartList = new ArrayList<>(); //竖轴数起始
    private VerticalAxesAdapter mVerticalAxesStartAdapter;
    private String mVerticalAxesStartNo; //选择的起始竖轴数
    private int mVerticalAxesStartPosition;
    private List<VerticalAxesSelectBean> mVerticalAxesEndList = new ArrayList<>(); //竖轴数结束
    private VerticalAxesAdapter mVerticalAxesEndAdapter;
    private String mVerticalAxesEndNo; //选择的结束竖轴数

    protected int setContentViewID() {
        return R.layout.activity_create_project_four;
    }

    @Override
    protected CreateProjectTwoPresenter getPresenter() {
        return new CreateProjectTwoPresenter();
    }

    @Override
    protected void initView() {
        mProjectBean = getIntent().getParcelableExtra(Extra.Project);
//        initImmersionBar();
        initRecyclerView();


        for (char c = 'A'; c <= 'Z'; c++) {
            System.out.print("XiLei-- " + c + " ");
            VerticalAxesSelectBean bean = new VerticalAxesSelectBean();
            bean.setId(String.valueOf(c));
            mVerticalAxesStartList.add(bean);
        }
        initHorizontalAxesStartDialog();
        initHorizontalAxesEndDialog();
        initVerticalStartDialog();
        initVerticalEndDialog();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CreateProjectManualCreationAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 横轴数起始Dialog
     */
    private void initHorizontalAxesStartDialog() {
        for (int i = 1; i < 101; i++) {
            BuildNoSelectBean bean = new BuildNoSelectBean();
            bean.setId(i);
            mHorizontalAxesStartList.add(bean);
        }
        mHorizontalAxesStartDialog = new BaseDialog(this);
        mHorizontalAxesStartDialog.contentView(R.layout.dialog_horizontal_axes)
                .gravity(Gravity.BOTTOM)
                .animType(BaseDialog.AnimInType.BOTTOM)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true);
        RecyclerView recyclerView = mHorizontalAxesStartDialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mHorizontalAxesStartAdapter = new HorizontalAxesAdapter();
        recyclerView.setAdapter(mHorizontalAxesStartAdapter);

        mHorizontalAxesStartAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                mHorizontalAxesStartPosition = position;
                mHorizontalAxesStartNo = mHorizontalAxesStartList.get(position).getId();
                mHorizontalAxesMinEt.setText(String.valueOf(mHorizontalAxesStartNo));
                for (int i = 0; i < mHorizontalAxesStartList.size(); i++) {
                    if (position == i) {
                        mHorizontalAxesStartList.get(i).setSelect(true);
                    } else {
                        mHorizontalAxesStartList.get(i).setSelect(false);
                    }
                }
                adapter.notifyDataSetChanged();
                mHorizontalAxesStartDialog.dismiss();
            }
        });
        mHorizontalAxesStartDialog.findViewById(R.id.closeIv).setOnClickListener(v -> mHorizontalAxesStartDialog.dismiss());
        mHorizontalAxesStartDialog.findViewById(R.id.backIv).setOnClickListener(v -> mHorizontalAxesStartDialog.dismiss());
    }

    /**
     * 横轴数结束Dialog
     */
    private void initHorizontalAxesEndDialog() {
        mHorizontalAxesEndDialog = new BaseDialog(this);
        mHorizontalAxesEndDialog.contentView(R.layout.dialog_horizontal_axes)
                .gravity(Gravity.BOTTOM)
                .animType(BaseDialog.AnimInType.BOTTOM)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true);
        RecyclerView recyclerView = mHorizontalAxesEndDialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mHorizontalAxesEndAdapter = new HorizontalAxesAdapter();
        recyclerView.setAdapter(mHorizontalAxesEndAdapter);

        mHorizontalAxesEndAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                mHorizontalAxesEndNo = mHorizontalAxesEndList.get(position).getId();
                mHorizontalAxesMaxEt.setText(String.valueOf(mHorizontalAxesEndNo));
                for (int i = 0; i < mHorizontalAxesEndList.size(); i++) {
                    if (position == i) {
                        mHorizontalAxesEndList.get(i).setSelect(true);
                    } else {
                        mHorizontalAxesEndList.get(i).setSelect(false);
                    }
                }
                adapter.notifyDataSetChanged();
                mHorizontalAxesEndDialog.dismiss();
            }
        });
        mHorizontalAxesEndDialog.findViewById(R.id.closeIv).setOnClickListener(v -> mHorizontalAxesEndDialog.dismiss());
        mHorizontalAxesEndDialog.findViewById(R.id.backIv).setOnClickListener(v -> mHorizontalAxesEndDialog.dismiss());
    }

    /**
     * 竖轴数起始Dialog
     */
    private void initVerticalStartDialog() {
        mVerticalStartDialog = new BaseDialog(this);
        mVerticalStartDialog.contentView(R.layout.dialog_vertical_axes)
                .gravity(Gravity.BOTTOM)
                .animType(BaseDialog.AnimInType.BOTTOM)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true);
        RecyclerView recyclerView = mVerticalStartDialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mVerticalAxesStartAdapter = new VerticalAxesAdapter();
        recyclerView.setAdapter(mVerticalAxesStartAdapter);

        mVerticalAxesStartAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                mVerticalAxesStartPosition = position;
                mVerticalAxesStartNo = mVerticalAxesStartList.get(position).getId();
                mVerticalAxesMinEt.setText(String.valueOf(mVerticalAxesStartNo));
                for (int i = 0; i < mVerticalAxesStartList.size(); i++) {
                    if (position == i) {
                        mVerticalAxesStartList.get(i).setSelect(true);
                    } else {
                        mVerticalAxesStartList.get(i).setSelect(false);
                    }
                }
                adapter.notifyDataSetChanged();
                mVerticalStartDialog.dismiss();
            }
        });
        mVerticalStartDialog.findViewById(R.id.closeIv).setOnClickListener(v -> mVerticalStartDialog.dismiss());
        mVerticalStartDialog.findViewById(R.id.backIv).setOnClickListener(v -> mVerticalStartDialog.dismiss());
    }

    /**
     * 竖轴数结束Dialog
     */
    private void initVerticalEndDialog() {
        mVerticalEndDialog = new BaseDialog(this);
        mVerticalEndDialog.contentView(R.layout.dialog_vertical_axes)
                .gravity(Gravity.BOTTOM)
                .animType(BaseDialog.AnimInType.BOTTOM)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true);
        RecyclerView recyclerView = mVerticalEndDialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mVerticalAxesEndAdapter = new VerticalAxesAdapter();
        recyclerView.setAdapter(mVerticalAxesEndAdapter);

        mVerticalAxesEndAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                mVerticalAxesEndNo = mVerticalAxesEndList.get(position).getId();
                mVerticalAxesMaxEt.setText(String.valueOf(mVerticalAxesEndNo));
                for (int i = 0; i < mVerticalAxesEndList.size(); i++) {
                    if (position == i) {
                        mVerticalAxesEndList.get(i).setSelect(true);
                    } else {
                        mVerticalAxesEndList.get(i).setSelect(false);
                    }
                }
                adapter.notifyDataSetChanged();
                mVerticalEndDialog.dismiss();
            }
        });
        mVerticalEndDialog.findViewById(R.id.closeIv).setOnClickListener(v -> mVerticalEndDialog.dismiss());
        mVerticalEndDialog.findViewById(R.id.backIv).setOnClickListener(v -> mVerticalEndDialog.dismiss());
    }

    @Override
    protected void initListener() {
        mTitleBar.getBackIv().setOnClickListener(view -> finish());
        mAxisMapRb.setOnClickListener(this::onClick);
        mManualCreationRb.setOnClickListener(this::onClick);
        mCreateBtn.setOnClickListener(this::onClick);
        mHorizontalAxesMinV.setOnClickListener(this::onClick);
        mHorizontalAxesMaxV.setOnClickListener(this::onClick);
        mVerticalAxesMinV.setOnClickListener(this::onClick);
        mVerticalAxesMaxV.setOnClickListener(this::onClick);
        mAddTv.setOnClickListener(this::onClick);

        /*mRoomNumberEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mBuildingNoEt.getText().toString().trim().length() > 0
                        && mStoreyEt.getText().toString().trim().length() > 0
                        && mRoomNumberEt.getText().toString().trim().length() == 4) {

                    CreateProjectHandBean createProjectHandBean = new CreateProjectHandBean();
                    createProjectHandBean.setName(mBuildingNoEt.getText().toString().trim() + "号楼 "
                            + mStoreyEt.getText().toString().trim() + "层 "
                            + mRoomNumberEt.getText().toString().trim());
                    mList.add(createProjectHandBean);
                    mBuildList.add(mBuildingNoEt.getText().toString().trim());
                    mFloorList.add(mStoreyEt.getText().toString().trim());
                    mRoomList.add(mRoomNumberEt.getText().toString().trim());

                    mAdapter.setNewInstance(mList);
                    mAdapter.notifyDataSetChanged();
                    mBuildingNoEt.setText("");
                    mStoreyEt.setText("");
                    mRoomNumberEt.setText("");
                }
            }
        });*/
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

    /**
     * 删除手动创建的房源号
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDrawerEvent(DeleteManualEvent event) {
        mAdapter.removeAt(event.getPosition());
        mBuildList.remove(event.getPosition());
        mFloorList.remove(event.getPosition());
        mRoomList.remove(event.getPosition());
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
            case R.id.axisMapRb:
                mAxisMapRb.setChecked(true);
                mManualCreationRb.setChecked(false);
                mAxisMapV.setVisibility(View.VISIBLE);
                mManualCreationV.setVisibility(View.GONE);
                break;
            case R.id.manualCreationRb:
                mAxisMapRb.setChecked(false);
                mManualCreationRb.setChecked(true);
                mAxisMapV.setVisibility(View.GONE);
                mManualCreationV.setVisibility(View.VISIBLE);
                break;
            case R.id.horizontalAxesMinV:
                if (mHorizontalAxesStartList.size() == 0) return;
                mHorizontalAxesStartAdapter.setNewInstance(mHorizontalAxesStartList);
                if (null != mHorizontalAxesStartDialog && !mHorizontalAxesStartDialog.isShowing()) {
                    mHorizontalAxesStartDialog.show();
                }
                break;
            case R.id.horizontalAxesMaxV:
                if (mHorizontalAxesStartList.size() == 0) return;
                if (TextUtils.isEmpty(mHorizontalAxesMinEt.getText())) {
                    showToast(getString(R.string.please_select_horizontal_axes_start));
                    return;
                }
                mHorizontalAxesEndList = mHorizontalAxesStartList.subList(mHorizontalAxesStartPosition, mHorizontalAxesStartList.size());
                mHorizontalAxesEndAdapter.setNewInstance(mHorizontalAxesEndList);
                if (null != mHorizontalAxesEndDialog && !mHorizontalAxesEndDialog.isShowing()) {
                    mHorizontalAxesEndDialog.show();
                }
                break;
            case R.id.verticalAxesMinV:
                if (mVerticalAxesStartList.size() == 0) return;
                mVerticalAxesStartAdapter.setNewInstance(mVerticalAxesStartList);
                if (null != mVerticalStartDialog && !mVerticalStartDialog.isShowing()) {
                    mVerticalStartDialog.show();
                }
                break;
            case R.id.verticalAxesMaxV:
                if (mVerticalAxesStartList.size() == 0) return;
                if (TextUtils.isEmpty(mVerticalAxesMinEt.getText())) {
                    showToast(getString(R.string.please_select_vertical_axes_start));
                    return;
                }
                mVerticalAxesEndList = mVerticalAxesStartList.subList(mVerticalAxesStartPosition, mVerticalAxesStartList.size());

                mVerticalAxesEndAdapter.setNewInstance(mVerticalAxesEndList);
                if (null != mVerticalEndDialog && !mVerticalEndDialog.isShowing()) {
                    mVerticalEndDialog.show();
                }
                break;
            case R.id.addTv:
                if (mBuildingNoEt.getText().toString().trim().length() == 0) {
                    showToast(getString(R.string.please_input_building_no));
                    return;
                }
                if (mStoreyEt.getText().toString().trim().length() == 0) {
                    showToast(getString(R.string.please_input_number_plies));
                    return;
                }
                if (mRoomNumberEt.getText().toString().trim().length() == 0) {
                    showToast(getString(R.string.please_input_room_number));
                    return;
                }
                CreateProjectHandBean createProjectHandBean = new CreateProjectHandBean();
                createProjectHandBean.setName(mBuildingNoEt.getText().toString().trim() + "号楼 "
                        + mStoreyEt.getText().toString().trim() + "层 "
                        + mRoomNumberEt.getText().toString().trim());
                mList.add(createProjectHandBean);
                mBuildList.add(mBuildingNoEt.getText().toString().trim());
                mFloorList.add(mStoreyEt.getText().toString().trim());
                mRoomList.add(mRoomNumberEt.getText().toString().trim());

                mAdapter.setNewInstance(mList);
                mAdapter.notifyDataSetChanged();
                mBuildingNoEt.setText("");
                mStoreyEt.setText("");
                mRoomNumberEt.setText("");

                break;
            case R.id.createBtn:
                if (mAxisMapRb.isChecked()) { //轴线图
                    if (mDongEt.getText().toString().trim().isEmpty()) {
                        showToast(getString(R.string.please_input_number_completed));
                        return;
                    }
                    if (mNumberLayersEt.getText().toString().trim().isEmpty()) {
                        showToast(getString(R.string.please_input_number_layers));
                        return;
                    }
                    if (mHorizontalAxesMinEt.getText().toString().trim().isEmpty()) {
                        showToast(getString(R.string.please_input_number_horizontal_axes_start));
                        return;
                    }
                    if (mHorizontalAxesMaxEt.getText().toString().trim().isEmpty()) {
                        showToast(getString(R.string.please_input_number_horizontal_axes_end));
                        return;
                    }
                    if (mVerticalAxesMinEt.getText().toString().isEmpty()) {
                        showToast(getString(R.string.please_input_number_vertical_axes_start));
                        return;
                    }
                    if (mVerticalAxesMaxEt.getText().toString().isEmpty()) {
                        showToast(getString(R.string.please_input_number_vertical_axes_end));
                        return;
                    }
                    if (mHorizontalAxesEndNo <= mHorizontalAxesStartNo) {
                        showToast(getString(R.string.horizontal_axes_start_end));
                        return;
                    }
                    showProgress();
                    CreateProjectTwoReq req = new CreateProjectTwoReq();
                    req.setPid(mProjectBean.getId());
                    req.setType(1);
                    req.setDong(Integer.valueOf(mDongEt.getText().toString().trim()));
                    req.setFloor(Integer.valueOf(mNumberLayersEt.getText().toString().trim()));
                    req.setLat_axis_start(Integer.valueOf(mHorizontalAxesMinEt.getText().toString().trim()));
                    req.setLat_axis_end(Integer.valueOf(mHorizontalAxesMaxEt.getText().toString().trim()));
                    req.setLon_axis_start(mVerticalAxesMinEt.getText().toString().trim());
                    req.setLon_axis_end(mVerticalAxesMaxEt.getText().toString().trim());
                    mPresenter.createProjectTwoZx(req);
                    return;
                }
                if (mManualCreationRb.isChecked()) { //手动创建
                    if (mList.size() == 0) {
                        showToast(getString(R.string.please_add_house));
                        return;
                    }
                    showProgress();
                    CreateProjectTwoSdReq req = new CreateProjectTwoSdReq();
                    req.setPid(mProjectBean.getId());
                    req.setType(2);
                    req.setBuild_num(mBuildList.toArray(new String[0]));
                    req.setFloor_num(mFloorList.toArray(new String[0]));
                    req.setRoom_num(mRoomList.toArray(new String[0]));
                    mPresenter.createProjectTwoSd(req);
                    return;
                }
                break;
        }
    }

    @Override
    public void createProjectSuccess() {
        dismissProgress();
        SPUtil.put(this, Extra.SP_Create_Project_Success, true);
        Intent intent = new Intent(this, ProjectMemberActivity.class);
        intent.putExtra(Extra.Project_Id, mProjectBean.getId());
        intent.putExtra(Extra.Project_Is_From_Detail, false);
        startActivity(intent);
        setResult(ResultCode.Create_Project);
        finish();
    }

    @Override
    public void createProjectFail(String msg) {
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