package com.huiguangongdi.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.cy.dialog.BaseDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.huiguangongdi.R;
import com.huiguangongdi.adapter.BuildingNoAdapter;
import com.huiguangongdi.adapter.EditQualityImgAdapter;
import com.huiguangongdi.adapter.HorizontalAxesAdapter;
import com.huiguangongdi.adapter.NumberPliesAdapter;
import com.huiguangongdi.adapter.QualityCheckImgAdapter;
import com.huiguangongdi.adapter.QualityHandleImgAdapter;
import com.huiguangongdi.adapter.SelectDealerAdapter;
import com.huiguangongdi.adapter.ShouDongAdapter;
import com.huiguangongdi.adapter.SpecialtyNewAdapter;
import com.huiguangongdi.adapter.TurnOverCompanyAdapter;
import com.huiguangongdi.adapter.TurnOverMemberAdapter;
import com.huiguangongdi.adapter.VerticalAxesAdapter;
import com.huiguangongdi.base.activity.BaseActivity;
import com.huiguangongdi.bean.BuildNoSelectBean;
import com.huiguangongdi.bean.BuildingNoBean;
import com.huiguangongdi.bean.BuildingNoSdBean;
import com.huiguangongdi.bean.CompanyListBean;
import com.huiguangongdi.bean.ProjectDetailBean;
import com.huiguangongdi.bean.ProjectDetailStepSdBean;
import com.huiguangongdi.bean.ProjectMemberBean;
import com.huiguangongdi.bean.SpecialtyBean;
import com.huiguangongdi.bean.VerticalAxesSelectBean;
import com.huiguangongdi.common.Extra;
import com.huiguangongdi.event.DeleteDealerEvent;
import com.huiguangongdi.presenter.CreateSafePresenter;
import com.huiguangongdi.req.BuildingNoReq;
import com.huiguangongdi.req.CompanyByProjectIdReq;
import com.huiguangongdi.req.CreateQualityReq;
import com.huiguangongdi.req.MemberByProjectIdReq;
import com.huiguangongdi.req.ProjectDetailReq;
import com.huiguangongdi.req.SpecialtyByProjectIdReq;
import com.huiguangongdi.utils.DateFormatUtil;
import com.huiguangongdi.utils.GlideEngine;
import com.huiguangongdi.utils.SPUtil;
import com.huiguangongdi.view.CreateSafeView;
import com.huiguangongdi.widget.TitleBar;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import me.kareluo.imaging.IMGEditActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 创建安全管理
 */
public class CreateSafeActivity extends BaseActivity<CreateSafePresenter> implements CreateSafeView, View.OnClickListener {

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.btn)
    TextView mBtn;
    @BindView(R.id.positionMarkV)
    View mPositionMarkV;
    @BindView(R.id.selectDealerV)
    View mSelectDealerV;
    @BindView(R.id.selectVerifierV)
    View mSelectVerifierV;
    @BindView(R.id.selectSpecialtyV)
    View mSelectSpecialtyV;
    @BindView(R.id.completionDaysV)
    View mCompletionDaysV;
    @BindView(R.id.selectSpecialtyTV)
    TextView mSelectSpecialtyTV;
    @BindView(R.id.positionMarkTV)
    TextView mPositionMarkTV;
    @BindView(R.id.completionDaysTV)
    TextView mCompletionDaysTV;
    @BindView(R.id.selectDealerTV)
    TextView mSelectDealerTV;
    @BindView(R.id.selectVerifierTV)
    TextView mSelectVerifierTV;
    @BindView(R.id.remarkTv)
    TextView mRemarkTv;
    @BindView(R.id.problemEt)
    EditText mProblemEt;
    @BindView(R.id.rxEt)
    EditText mRxEt;
    @BindView(R.id.supplementaryEt)
    EditText mSupplementaryEt;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.dealerRv)
    RecyclerView mDealerRv;
    @BindView(R.id.verifierRv)
    RecyclerView mVerifierRv;

    private int mProjectId;
    private BaseDialog mPositionMarkDialog;
    private BaseDialog mSelectDealerDialog;
    private BaseDialog mSpecialtyDialog;
    private BaseDialog mShouDongDialog;
    private BaseDialog mBuildingNoDialog;
    private BaseDialog mNumberPliesDialog;
    private BaseDialog mHorizontalAxesStartDialog;
    private BaseDialog mHorizontalAxesEndDialog;
    private BaseDialog mVerticalStartDialog;
    private BaseDialog mVerticalEndDialog;
    private TimePickerView mOptionsPickerViewDialog;
    private boolean isSelectDealer; //true:选择处理人  false:选择核查人
    private List<ProjectMemberBean> mSelectDealerList = new ArrayList(); //被选择的处理人
    private List<ProjectMemberBean> mSelectVerifierList = new ArrayList(); //被选择的核查人
    private SpecialtyNewAdapter mSpecialtyAdapter;
    private String mSelectSpecialtyName; //选择的专业名称
    private int mSelectSpecialtyId; //选择专业id
    private BuildingNoBean mBuildingNoBean;
    private BuildingNoSdBean mBuildingNoSdBean;
    private BuildingNoAdapter mBuildingNoAdapter;
    private List<BuildNoSelectBean> mBuildNoList = new ArrayList<>(); //楼号
    private int mBuildingNo; //选择的楼号
    private NumberPliesAdapter mNumberPliesAdapter;
    private List<BuildNoSelectBean> mNumberPliesList = new ArrayList<>(); //层数
    private int mNumberPliesNo; //选择的层数
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
    private String mStartDayStr; //选择的开始日期
    private String mEndDayStr; //选择的结束日期
    private int mDays; //天数

    private List<ProjectDetailStepSdBean> mManualList = new ArrayList<>();//手动创建的房源
    private ShouDongAdapter mShouDongAdapter;
    private String mSelectShouDongName; //手动创建选择房源

    private List<String> mImagePaths = new ArrayList<>();
    private List<SpecialtyBean> mSpecialtyList = new ArrayList<>(); //专业
    private ProjectDetailBean mProjectDetailBean;
    private EditQualityImgAdapter mAdapter;
    private QualityHandleImgAdapter mQualityHandleImgAdapter;
    private QualityCheckImgAdapter mQualityCheckImgAdapter;
    private static final int WeXin_REQUEST_CODE = 2020;

    @Override
    protected int setContentViewID() {
        return R.layout.activity_create_safe;
    }

    @Override
    protected CreateSafePresenter getPresenter() {
        return new CreateSafePresenter();
    }

    @Override
    protected void initView() {
//        initImmersionBar();
//        AndroidBug5497Workaround.assistActivity(this);
        mProjectId = (int) SPUtil.get(this, Extra.SP_Project_Id, -1);
        initRecyclerView();
        initDialog();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new EditQualityImgAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mImagePaths.add("");
        mAdapter.setNewInstance(mImagePaths);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (position == mAdapter.getData().size() - 1) {
                    initPhotoDialog();
                } else {
                    List<String> list = mImagePaths.subList(0, mImagePaths.size() - 1);
                    List<LocalMedia> medias = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        LocalMedia localMedia = new LocalMedia();
                        localMedia.setPath(list.get(i));
                        medias.add(localMedia);
                    }
                    PictureSelector.create(CreateSafeActivity.this)
                            .themeStyle(R.style.picture_default_style) // xml设置主题
                            .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                            .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                            .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                            .openExternalPreview(position, medias);
                }
            }
        });

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        mDealerRv.setLayoutManager(linearLayoutManager1);
        mQualityHandleImgAdapter = new

                QualityHandleImgAdapter();
        mDealerRv.setAdapter(mQualityHandleImgAdapter);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        mVerifierRv.setLayoutManager(linearLayoutManager2);
        mQualityCheckImgAdapter = new

                QualityCheckImgAdapter();
        mVerifierRv.setAdapter(mQualityCheckImgAdapter);
    }

    private void initPhotoDialog() {
        BaseDialog dialog = new BaseDialog(this);
        dialog.contentView(R.layout.dialog_photo)
                .gravity(Gravity.BOTTOM)
                .animType(BaseDialog.AnimInType.BOTTOM)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true)
                .show();
        dialog.findViewById(R.id.takePictureTv).setOnClickListener(view -> {
            dialog.dismiss();
            PictureSelector.create(this)
                    .openCamera(PictureMimeType.ofImage())
                    .loadImageEngine(GlideEngine.createGlideEngine())
                    .isUseDoodle(true)
                    .forResult(PictureConfig.REQUEST_CAMERA);
        });
        dialog.findViewById(R.id.albumTv).setOnClickListener(v -> {
            dialog.dismiss();
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())
                    .selectionMode(PictureConfig.SINGLE)
                    .loadImageEngine(GlideEngine.createGlideEngine())
                    .isCamera(false)
                    .previewImage(false)
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        });
        dialog.findViewById(R.id.cancelTv).setOnClickListener(v -> dialog.dismiss());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST: //相册返回
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    if (null == selectList || selectList.size() == 0) return;
                    Uri uri = Uri.fromFile(new File(selectList.get(0).getRealPath()));
                    Intent intent = new Intent(this, IMGEditActivity.class);
                    intent.putExtra(IMGEditActivity.EXTRA_IMAGE_URI, uri);
                    intent.putExtra(IMGEditActivity.EXTRA_IMAGE_SAVE_PATH, selectList.get(0).getRealPath());
                    startActivityForResult(intent, WeXin_REQUEST_CODE);
                    break;
                case PictureConfig.REQUEST_CAMERA: //拍照返回
                case WeXin_REQUEST_CODE: //涂鸦返回
                    String path = data.getStringExtra("namePath");
                    uploadPath(path);
                    break;
            }
        }
    }

    /**
     * 上传图片
     *
     * @param path
     */
    private void uploadPath(String path) {
        showProgress();
        File file = new File(path);
        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        mPresenter.uploadPhotoPath(body);
    }

    /**
     * 上传图片成功
     *
     * @param bean
     */
    @Override
    public void uploadPhotoPathSuccess(String bean) {
        dismissProgress();
        mImagePaths.add(0, bean);
        mAdapter.setNewInstance(mImagePaths);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void uploadPhotoPathFail(String msg) {
        dismissProgress();
        showToast(msg);
    }

    @Override
    protected void initListener() {
        mTitleBar.getBackIv().setOnClickListener(view -> finish());
        mPositionMarkV.setOnClickListener(this::onClick);
        mSelectDealerV.setOnClickListener(this::onClick);
        mSelectVerifierV.setOnClickListener(this::onClick);
        mSelectSpecialtyV.setOnClickListener(this::onClick);
        mCompletionDaysV.setOnClickListener(this::onClick);
        mBtn.setOnClickListener(this::onClick);
    }

    private void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(R.id.titleBar)
                .statusBarDarkFont(true).init();
    }

    private void initDialog() {
        initPositionMarkDialog();
        initBuildingNoDialog();
        initNumberPliesDialog();
        initHorizontalAxesStartDialog();
        initHorizontalAxesEndDialog();
        initVerticalStartDialog();
        initVerticalEndDialog();
        showPickerViewDialog();
        initSpecialtyDialog();
        initShouDongDialog();
    }

    private View mAddressV, mBuildingNoV, mNumberPliesV,
            mHorizontalAxesV, mHorizontalAxesStartV, mHorizontalAxesEndV,
            mVerticalAxesV, mVerticalAxesStartV, mVerticalAxesEndV;
    private TextView mBuildingNoTV, mNumberPliesTV,
            mHorizontalAxesStartTV, mHorizontalAxesEndTV,
            mVerticalAxesStartTV, mVerticalAxesEndTV, mAddressTV;
    private EditText mRemarksEt;

    /**
     * 位置标记Dialog
     */
    private void initPositionMarkDialog() {
        mPositionMarkDialog = new BaseDialog(this);
        mPositionMarkDialog.contentView(R.layout.dialog_position_mark)
                .gravity(Gravity.BOTTOM)
                .animType(BaseDialog.AnimInType.BOTTOM)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true);

        mAddressV = mPositionMarkDialog.findViewById(R.id.addressV);
        mBuildingNoV = mPositionMarkDialog.findViewById(R.id.buildingNoV);
        mNumberPliesV = mPositionMarkDialog.findViewById(R.id.numberPliesV);
        mHorizontalAxesV = mPositionMarkDialog.findViewById(R.id.horizontalAxesV);
        mHorizontalAxesStartV = mPositionMarkDialog.findViewById(R.id.horizontalAxesStartV);
        mHorizontalAxesEndV = mPositionMarkDialog.findViewById(R.id.horizontalAxesEndV);
        mVerticalAxesV = mPositionMarkDialog.findViewById(R.id.verticalAxesV);
        mVerticalAxesStartV = mPositionMarkDialog.findViewById(R.id.verticalAxesStartV);
        mVerticalAxesEndV = mPositionMarkDialog.findViewById(R.id.verticalAxesEndV);

        mAddressTV = mPositionMarkDialog.findViewById(R.id.addressTV);
        mBuildingNoTV = mPositionMarkDialog.findViewById(R.id.buildingNoTV);
        mNumberPliesTV = mPositionMarkDialog.findViewById(R.id.numberPliesTV);
        mHorizontalAxesStartTV = mPositionMarkDialog.findViewById(R.id.horizontalAxesStartTV);
        mHorizontalAxesEndTV = mPositionMarkDialog.findViewById(R.id.horizontalAxesEndTV);
        mVerticalAxesStartTV = mPositionMarkDialog.findViewById(R.id.verticalAxesStartTV);
        mVerticalAxesEndTV = mPositionMarkDialog.findViewById(R.id.verticalAxesEndTV);
        mRemarksEt = mPositionMarkDialog.findViewById(R.id.rxEt);
        mPositionMarkDialog.findViewById(R.id.closeIv).setOnClickListener(v -> mPositionMarkDialog.dismiss());
        mBuildingNoV.setOnClickListener(v -> {
            if (mBuildNoList.size() == 0) return;
            mBuildingNoAdapter.setNewInstance(mBuildNoList);
            if (null != mBuildingNoDialog && !mBuildingNoDialog.isShowing()) {
                mBuildingNoDialog.show();
            }
        });
        mNumberPliesV.setOnClickListener(v -> {
            if (mNumberPliesList.size() == 0) return;
            mNumberPliesAdapter.setNewInstance(mNumberPliesList);
            if (null != mNumberPliesDialog && !mNumberPliesDialog.isShowing()) {
                mNumberPliesDialog.show();
            }
        });
        mHorizontalAxesStartV.setOnClickListener(v -> {
            if (mHorizontalAxesStartList.size() == 0) return;
            mHorizontalAxesStartAdapter.setNewInstance(mHorizontalAxesStartList);
            if (null != mHorizontalAxesStartDialog && !mHorizontalAxesStartDialog.isShowing()) {
                mHorizontalAxesStartDialog.show();
            }
        });
        mHorizontalAxesEndV.setOnClickListener(v -> {
            if (mHorizontalAxesStartList.size() == 0) return;
            if (TextUtils.isEmpty(mHorizontalAxesStartTV.getText())) {
                showToast(getString(R.string.please_select_horizontal_axes_start));
                return;
            }
            mHorizontalAxesEndList = mHorizontalAxesStartList.subList(mHorizontalAxesStartPosition, mHorizontalAxesStartList.size());
            mHorizontalAxesEndAdapter.setNewInstance(mHorizontalAxesEndList);
            if (null != mHorizontalAxesEndDialog && !mHorizontalAxesEndDialog.isShowing()) {
                mHorizontalAxesEndDialog.show();
            }
        });
        mVerticalAxesStartV.setOnClickListener(v -> {
            if (mVerticalAxesStartList.size() == 0) return;
            mVerticalAxesStartAdapter.setNewInstance(mVerticalAxesStartList);
            if (null != mVerticalStartDialog && !mVerticalStartDialog.isShowing()) {
                mVerticalStartDialog.show();
            }
        });
        mVerticalAxesEndV.setOnClickListener(v -> {
            if (mVerticalAxesStartList.size() == 0) return;
            if (TextUtils.isEmpty(mVerticalAxesStartTV.getText())) {
                showToast(getString(R.string.please_select_vertical_axes_start));
                return;
            }
            mVerticalAxesEndList = mVerticalAxesStartList.subList(mVerticalAxesStartPosition, mVerticalAxesStartList.size());

            mVerticalAxesEndAdapter.setNewInstance(mVerticalAxesEndList);
            if (null != mVerticalEndDialog && !mVerticalEndDialog.isShowing()) {
                mVerticalEndDialog.show();
            }
        });
        mAddressV.setOnClickListener(v -> {
            if (mManualList.size() == 0) return;
            mShouDongAdapter.setNewInstance(mManualList);
            if (null != mShouDongDialog && !mShouDongDialog.isShowing()) {
                mShouDongDialog.show();
            }
        });
        mPositionMarkDialog.findViewById(R.id.btn).setOnClickListener(v -> {
            if (mProjectDetailBean.getType() == 1) {
                if (TextUtils.isEmpty(mBuildingNoTV.getText().toString())) {
                    showToast(getString(R.string.please_select_building_no));
                    return;
                }
                if (TextUtils.isEmpty(mNumberPliesTV.getText().toString())) {
                    showToast(getString(R.string.please_select_number_plies));
                    return;
                }
                if (TextUtils.isEmpty(mHorizontalAxesStartTV.getText().toString())
                        || TextUtils.isEmpty(mHorizontalAxesEndTV.getText().toString())) {
                    showToast(getString(R.string.please_select_horizontal_axes));
                    return;
                }
                if (TextUtils.isEmpty(mVerticalAxesStartTV.getText().toString())
                        || TextUtils.isEmpty(mVerticalAxesEndTV.getText().toString())) {
                    showToast(getString(R.string.please_select_vertical_axes));
                    return;
                }
                if (mHorizontalAxesEndNo <= mHorizontalAxesStartNo) {
                    showToast(getString(R.string.horizontal_axes_start_end));
                    return;
                }
                if (!TextUtils.isEmpty(mRemarksEt.getText().toString().trim())) {
                    mRemarkTv.setVisibility(View.VISIBLE);
                    mRemarkTv.setText(getString(R.string.remark) + mRemarksEt.getText().toString().trim());
                }
                mPositionMarkTV.setText(mBuildingNoTV.getText() + " "
                        + mNumberPliesTV.getText() + " "
                        + getString(R.string.horizontal_axes_hint, mHorizontalAxesStartTV.getText(), mHorizontalAxesEndTV.getText()) + " "
                        + getString(R.string.vertical_axes_hint, mVerticalAxesStartTV.getText(), mVerticalAxesEndTV.getText()));
            } else {
                if (!TextUtils.isEmpty(mRemarksEt.getText().toString().trim())) {
                    mRemarkTv.setVisibility(View.VISIBLE);
                    mRemarkTv.setText(getString(R.string.remark) + mRemarksEt.getText().toString().trim());
                }
                mPositionMarkTV.setText(mSelectShouDongName);
            }
            mPositionMarkDialog.dismiss();
        });
    }

    /**
     * 选择楼号Dialog
     */
    private void initBuildingNoDialog() {
        mBuildingNoDialog = new BaseDialog(this);
        mBuildingNoDialog.contentView(R.layout.dialog_building_no)
                .gravity(Gravity.BOTTOM)
                .animType(BaseDialog.AnimInType.BOTTOM)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true);
        RecyclerView recyclerView = mBuildingNoDialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBuildingNoAdapter = new BuildingNoAdapter();
        recyclerView.setAdapter(mBuildingNoAdapter);

        mBuildingNoAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                mBuildingNo = mBuildNoList.get(position).getId();
                mBuildingNoTV.setText(mBuildingNo + getString(R.string.nom));
                for (int i = 0; i < mBuildNoList.size(); i++) {
                    if (position == i) {
                        mBuildNoList.get(i).setSelect(true);
                    } else {
                        mBuildNoList.get(i).setSelect(false);
                    }
                }
                adapter.notifyDataSetChanged();
                mBuildingNoDialog.dismiss();
            }
        });
        mBuildingNoDialog.findViewById(R.id.closeIv).setOnClickListener(v -> mBuildingNoDialog.dismiss());
        mBuildingNoDialog.findViewById(R.id.backIv).setOnClickListener(v -> mBuildingNoDialog.dismiss());
    }

    /**
     * 选择层数Dialog
     */
    private void initNumberPliesDialog() {
        mNumberPliesDialog = new BaseDialog(this);
        mNumberPliesDialog.contentView(R.layout.dialog_number_plies)
                .gravity(Gravity.BOTTOM)
                .animType(BaseDialog.AnimInType.BOTTOM)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true);
        RecyclerView recyclerView = mNumberPliesDialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNumberPliesAdapter = new NumberPliesAdapter();
        recyclerView.setAdapter(mNumberPliesAdapter);

        mNumberPliesAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                mNumberPliesNo = mNumberPliesList.get(position).getId();
                mNumberPliesTV.setText(mNumberPliesNo + getString(R.string.plies));
                for (int i = 0; i < mNumberPliesList.size(); i++) {
                    if (position == i) {
                        mNumberPliesList.get(i).setSelect(true);
                    } else {
                        mNumberPliesList.get(i).setSelect(false);
                    }
                }
                adapter.notifyDataSetChanged();
                mNumberPliesDialog.dismiss();
            }
        });
        mNumberPliesDialog.findViewById(R.id.closeIv).setOnClickListener(v -> mNumberPliesDialog.dismiss());
        mNumberPliesDialog.findViewById(R.id.backIv).setOnClickListener(v -> mNumberPliesDialog.dismiss());
    }

    /**
     * 横轴数起始Dialog
     */
    private void initHorizontalAxesStartDialog() {
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
                mHorizontalAxesStartTV.setText(String.valueOf(mHorizontalAxesStartNo));
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
                mHorizontalAxesEndTV.setText(String.valueOf(mHorizontalAxesEndNo));
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
                mVerticalAxesStartTV.setText(String.valueOf(mVerticalAxesStartNo));
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
                mVerticalAxesEndTV.setText(String.valueOf(mVerticalAxesEndNo));
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

    private TextView mStartDayTv, mEndDayTv;
    private TextView mDaysTv;
    private boolean isStartDate = false;
    private Date mStartDate;
    private Date mEndDate;

    /**
     * 完成天数Dialog
     */
    private void showPickerViewDialog() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.set(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONDAY), selectedDate.get(Calendar.DAY_OF_MONTH));
        endDate.set(2050, 11, 31);
        mStartDate = startDate.getTime();
        mOptionsPickerViewDialog = new TimePickerBuilder(this, new OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date, View v) {

            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.dialog_pickerview_day, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView btn = (TextView) v.findViewById(R.id.btn);
                        ImageView closeIv = (ImageView) v.findViewById(R.id.closeIv);
                        mStartDayTv = (TextView) v.findViewById(R.id.startDayTv);
                        mEndDayTv = (TextView) v.findViewById(R.id.endDayTv);
                        mDaysTv = (TextView) v.findViewById(R.id.daysTv);
                        mStartDayTv.setText(DateFormatUtil.getCalendarToString(selectedDate));
                        mStartDayStr = mStartDayTv.getText().toString();

                        btn.setOnClickListener(v1 -> {
                            if (null == mStartDate) {
                                showToast(getString(R.string.please_select_start_time));
                                return;
                            }
                            if (null == mEndDate) {
                                showToast(getString(R.string.please_select_end_time));
                                return;
                            }
                            if (mDays <= 0) {
                                showToast(getString(R.string.select_time_hint));
                                return;
                            }
                            mCompletionDaysTV.setText(mDays + getString(R.string.day) +
                                    getString(R.string.semicolon) + " " +
                                    DateFormatUtil.getDateToMMDDString(mStartDate) +
                                    getString(R.string.whippletree) +
                                    DateFormatUtil.getDateToMMDDString(mEndDate));
                            mOptionsPickerViewDialog.returnData();
                            mOptionsPickerViewDialog.dismiss();
                        });
                        closeIv.setOnClickListener(v12 -> mOptionsPickerViewDialog.dismiss());
                        v.findViewById(R.id.startDayV).setOnClickListener(view -> isStartDate = true);
                        v.findViewById(R.id.endDayV).setOnClickListener(view -> isStartDate = false);
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
                            mStartDayStr = mStartDayTv.getText().toString();
                            mStartDate = date;
                        } else {
                            mEndDayTv.setText(DateFormatUtil.getDateToString(date));
                            mEndDayStr = mEndDayTv.getText().toString();
                            mEndDate = date;
                        }
                        if (!mStartDayTv.getText().toString().isEmpty() && !mEndDayTv.getText().toString().isEmpty()) {
                            mDays = DateFormatUtil.getDayDiffer(mStartDate, mEndDate);
                            if (mDays < 0) {
                                mDays = 0;
                            }
                            mDaysTv.setText(mDays + getString(R.string.day));
                        }

                    }
                })
                .build();
    }

    @Override
    protected void initData() {
        showProgress();
        ProjectDetailReq req = new ProjectDetailReq();
        req.setId(mProjectId);
        mPresenter.getProjectDetail(req);
    }

    /**
     * 获取项目详情成功
     *
     * @param bean
     */
    @Override
    public void getProjectDetailSuccess(ProjectDetailBean bean) {
        mProjectDetailBean = bean;
        if (bean.getType() == 1) { //轴线图
            BuildingNoReq buildingNoReq = new BuildingNoReq();
            buildingNoReq.setPid(mProjectId);
            mPresenter.getBuildingNo(buildingNoReq);
        } else { //手动创建
            BuildingNoReq buildingNoReq = new BuildingNoReq();
            buildingNoReq.setPid(mProjectId);
            mPresenter.getBuildingNoSd(buildingNoReq);
        }
    }

    @Override
    public void getProjectDetailFail(String msg) {
        dismissProgress();
        showToast(msg);
    }

    /**
     * 获取楼号、层数成功（轴线图）
     *
     * @param bean
     */
    @Override
    public void getBuildingNoSuccess(BuildingNoBean bean) {

        mBuildingNoBean = bean;
        for (int i : bean.getAxis().getDong_list()) {
            mBuildNoList.add(new BuildNoSelectBean(i, false));
        }
        for (int i : bean.getAxis().getFloor_list()) {
            mNumberPliesList.add(new BuildNoSelectBean(i, false));
        }
        for (int i : bean.getAxis().getLat_axis()) {
            mHorizontalAxesStartList.add(new BuildNoSelectBean(i, false));
        }
        for (String i : bean.getAxis().getLon_axis()) {
            mVerticalAxesStartList.add(new VerticalAxesSelectBean(i, false));
        }

        SpecialtyByProjectIdReq req = new SpecialtyByProjectIdReq();
        req.setPid(mProjectId);
        mPresenter.getSpecialtyByProjectId(req);
    }

    @Override
    public void getBuildingNoFail(String msg) {
        dismissProgress();
        showToast(msg);
    }

    /**
     * 获取楼号、层数成功（手动创建）
     *
     * @param bean
     */
    @Override
    public void getBuildingNoSdSuccess(BuildingNoSdBean bean) {

        mBuildingNoSdBean = bean;
        mManualList = bean.getManual();
        SpecialtyByProjectIdReq req = new SpecialtyByProjectIdReq();
        req.setPid(mProjectId);
        mPresenter.getSpecialtyByProjectId(req);
    }

    @Override
    public void getBuildingNoSdFail(String msg) {
        dismissProgress();
        showToast(msg);
    }

    /**
     * 获取专业成功
     *
     * @param list
     */
    @Override
    public void getSpecialtySuccess(ArrayList<SpecialtyBean> list) {
        dismissProgress();
        mSpecialtyList = list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.positionMarkV:
                if (null != mBuildingNoBean) {
                   /* mHorizontalAxesStartTV.setHint(String.format(getString(R.string.select_number_horizontal_axes)
                            , String.valueOf(mBuildingNoBean.getAxis().getLat_axis_start()), String.valueOf(mBuildingNoBean.getAxis().getLat_axis_end())));
                    mVerticalAxesTV.setHint(String.format(getString(R.string.select_number_vertical_axes)
                            , String.valueOf(mBuildingNoBean.getAxis().getLon_axis_start()), String.valueOf(mBuildingNoBean.getAxis().getLon_axis_end())));*/
                }
                if (null != mPositionMarkDialog && !mPositionMarkDialog.isShowing()) {
                    if (mProjectDetailBean.getType() == 1) {
                        mBuildingNoV.setVisibility(View.VISIBLE);
                        mNumberPliesV.setVisibility(View.VISIBLE);
                        mHorizontalAxesV.setVisibility(View.VISIBLE);
                        mVerticalAxesV.setVisibility(View.VISIBLE);
                    } else {
                        mAddressV.setVisibility(View.VISIBLE);
                    }
                    mPositionMarkDialog.show();
                }
                break;
            case R.id.selectDealerV:
                isSelectDealer = true;
                getCompanyByProjectId();
                break;
            case R.id.selectVerifierV:
                isSelectDealer = false;
                getCompanyByProjectId();
                break;
            case R.id.selectSpecialtyV:
                mSpecialtyAdapter.setNewInstance(mSpecialtyList);
                if (mSpecialtyDialog != null && !mSpecialtyDialog.isShowing()) {
                    mSpecialtyDialog.show();
                }
                break;
            case R.id.completionDaysV:
                if (null != mOptionsPickerViewDialog && !mOptionsPickerViewDialog.isShowing()) {
                    mOptionsPickerViewDialog.show();
                }
                break;
            case R.id.btn:
                if (mProblemEt.getText().toString().trim().isEmpty()) {
                    showToast(getString(R.string.please_input_problem_description));
                    return;
                }
                if (mImagePaths.size() - 1 == 0) {
                    showToast(getString(R.string.please_upload_pictures));
                    return;
                }
                if (mPositionMarkTV.getText().toString().isEmpty()) {
                    showToast(getString(R.string.please_select_position_mark));
                    return;
                }
                if (mCompletionDaysTV.getText().toString().isEmpty()) {
                    showToast(getString(R.string.please_select_completion_days));
                    return;
                }
                if (mSelectDealerList.size() == 0) {
                    showToast(getString(R.string.please_select_dealers));
                    return;
                }
                if (mSelectVerifierList.size() == 0) {
                    showToast(getString(R.string.please_select_verifiers));
                    return;
                }
                if (mRxEt.getText().toString().trim().isEmpty()) {
                    showToast(getString(R.string.please_input_rx));
                    return;
                }
                if (mSelectSpecialtyTV.getText().toString().isEmpty()) {
                    showToast(getString(R.string.please_select_major));
                    return;
                }
                /*if (mSupplementaryEt.getText().toString().trim().isEmpty()) {
                    showToast(getString(R.string.please_input_supplementary_questions));
                    return;
                }*/
                showProgress();
                CreateQualityReq createQualityReq = new CreateQualityReq();
                createQualityReq.setPid(mProjectId);
                createQualityReq.setName(mProblemEt.getText().toString().trim());
                List<String> list = mImagePaths.subList(0, mImagePaths.size() - 1);
                createQualityReq.setImage(list.toArray(new String[0]));
                String[] address = {String.valueOf(mBuildingNo)
                        , String.valueOf(mNumberPliesNo)
                        , String.valueOf(mHorizontalAxesStartNo)
                        , String.valueOf(mHorizontalAxesEndNo)
                        , mVerticalAxesStartNo
                        , mVerticalAxesEndNo
                        , mRemarksEt.getText().toString().trim()};
                createQualityReq.setAddress(address);
                createQualityReq.setBegin_time(mStartDayStr);
                createQualityReq.setEnd_time(mEndDayStr);
                createQualityReq.setDay(mDays);
                createQualityReq.setWork_type(mSelectSpecialtyId);
                int[] handle_people = new int[mSelectDealerList.size()];
                for (int i = 0; i < mSelectDealerList.size(); i++) {
                    handle_people[i] = mSelectDealerList.get(i).getUid();
                }
                createQualityReq.setHandle_people(handle_people);
                int[] check_people = new int[mSelectVerifierList.size()];
                for (int i = 0; i < mSelectVerifierList.size(); i++) {
                    check_people[i] = mSelectVerifierList.get(i).getUid();
                }
                createQualityReq.setCheck_people(check_people);
                createQualityReq.setSolution(mRxEt.getText().toString().trim());
                createQualityReq.setQuestion_supple(mSupplementaryEt.getText().toString().trim());
                mPresenter.createSafe(createQualityReq);
                break;
        }
    }

    /**
     * 创建质量管理成功
     */
    @Override
    public void createQualitySuccess() {
        dismissProgress();
        finish();
    }

    @Override
    public void createQualityFail(String msg) {
        dismissProgress();
        showToast(msg);
    }


    /**
     * 所属专业Dialog
     */
    private void initSpecialtyDialog() {
        mSpecialtyDialog = new BaseDialog(this);
        mSpecialtyDialog.contentView(R.layout.dialog_specialty_new)
                .gravity(Gravity.BOTTOM)
                .animType(BaseDialog.AnimInType.BOTTOM)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true);
        RecyclerView recyclerView = mSpecialtyDialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSpecialtyAdapter = new SpecialtyNewAdapter();
        recyclerView.setAdapter(mSpecialtyAdapter);

        mSpecialtyAdapter.setOnItemClickListener(new OnItemClickListener() {
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
                mSelectSpecialtyTV.setText(mSelectSpecialtyName);
                mSpecialtyDialog.dismiss();
            }
        });

        mSpecialtyDialog.findViewById(R.id.backIv).setOnClickListener(v -> mSpecialtyDialog.dismiss());
        mSpecialtyDialog.findViewById(R.id.closeIv).setOnClickListener(v -> mSpecialtyDialog.dismiss());
    }

    /**
     * 手动创建的房源Dialog
     */
    private void initShouDongDialog() {
        mShouDongDialog = new BaseDialog(this);
        mShouDongDialog.contentView(R.layout.dialog_shoudong)
                .gravity(Gravity.BOTTOM)
                .animType(BaseDialog.AnimInType.BOTTOM)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true);
        RecyclerView recyclerView = mShouDongDialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mShouDongAdapter = new ShouDongAdapter();
        recyclerView.setAdapter(mShouDongAdapter);

        mShouDongAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                mSelectShouDongName = getString(R.string.building_no)
                        + mManualList.get(position).getBuild_num() + " " +
                        getString(R.string.storey) + mManualList.get(position).getFloor_num() + " " +
                        getString(R.string.room_number) + mManualList.get(position).getRoom_num();
                mBuildingNo = mManualList.get(position).getBuild_num();
                mNumberPliesNo = mManualList.get(position).getFloor_num();
                mHorizontalAxesStartNo = mManualList.get(position).getRoom_num();

                mSelectSpecialtyId = mManualList.get(position).getId();
                for (int i = 0; i < mManualList.size(); i++) {
                    if (position == i) {
                        mManualList.get(i).setSelect(true);
                    } else {
                        mManualList.get(i).setSelect(false);
                    }
                }
                adapter.notifyDataSetChanged();
                mAddressTV.setText(mSelectShouDongName);
                mShouDongDialog.dismiss();
            }
        });

        mShouDongDialog.findViewById(R.id.backIv).setOnClickListener(v -> mShouDongDialog.dismiss());
        mShouDongDialog.findViewById(R.id.closeIv).setOnClickListener(v -> mShouDongDialog.dismiss());
    }

    /**
     * 获取当前项目下公司
     */
    private void getCompanyByProjectId() {
        showProgress();
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
        dismissProgress();
        initSelectDealerDialog(list);
    }

    /**
     * 获取处理人公司失败
     *
     * @param msg
     */
    @Override
    public void getCompanyByProjectIdTurnOverFail(String msg) {
        dismissProgress();
        showToast(msg);
    }

    private RecyclerView mSelectDealerRv;
    private RecyclerView mCompanyRv;
    private RecyclerView mMemberRv;
    private ImageView mSelectIv;
    private boolean isSelect = false;
    private SelectDealerAdapter mSelectDealerAdapter;

    /**
     * 选择处理人Dialog
     */
    private void initSelectDealerDialog(ArrayList<CompanyListBean> list) {
        mSelectDealerDialog = new BaseDialog(this);
        mSelectDealerDialog.contentView(R.layout.dialog_select_dealer)
                .gravity(Gravity.BOTTOM)
                .animType(BaseDialog.AnimInType.BOTTOM)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true);
        if (!isSelectDealer) {
            ((TextView) mSelectDealerDialog.findViewById(R.id.titleTv)).setText(getString(R.string.select_verifier));
            ((TextView) mSelectDealerDialog.findViewById(R.id.selectTv)).setText(getString(R.string.please_select_verifier));
        }

        mSelectDealerRv = mSelectDealerDialog.findViewById(R.id.recyclerView);
        mSelectDealerRv.setLayoutManager(new LinearLayoutManager(CreateSafeActivity.this));
        mSelectDealerAdapter = new SelectDealerAdapter();
        mSelectDealerRv.setAdapter(mSelectDealerAdapter);
        View selectV = mSelectDealerDialog.findViewById(R.id.selectV);
        mSelectIv = mSelectDealerDialog.findViewById(R.id.selectIv);
        mCompanyRv = mSelectDealerDialog.findViewById(R.id.companyRv);
        mCompanyRv.setLayoutManager(new LinearLayoutManager(this));
        mMemberRv = mSelectDealerDialog.findViewById(R.id.memberRv);
        mMemberRv.setLayoutManager(new LinearLayoutManager(this));
        TurnOverCompanyAdapter turnOverCompanyAdapter = new TurnOverCompanyAdapter(list);

        selectV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelect = !isSelect;
                mCompanyRv.setAdapter(turnOverCompanyAdapter);
                if (isSelect) {
                    mSelectDealerRv.setVisibility(View.GONE);
                    mCompanyRv.setVisibility(View.VISIBLE);
                    mMemberRv.setVisibility(View.VISIBLE);
                    mSelectIv.setImageResource(R.mipmap.icon_pullup);
                } else {
                    if (isSelectDealer && mSelectDealerList.size() > 0) {
                        mSelectDealerRv.setVisibility(View.VISIBLE);
                    } else if (!isSelectDealer && mSelectVerifierList.size() > 0) {
                        mSelectDealerRv.setVisibility(View.VISIBLE);
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
                for (int i = 0; i < turnOverCompanyAdapter.getData().size(); i++) {
                    if (turnOverCompanyAdapter.getData().get(position).getId() == turnOverCompanyAdapter.getData().get(i).getId()) {
                        turnOverCompanyAdapter.getData().get(i).setSelect(true);
                    } else {
                        turnOverCompanyAdapter.getData().get(i).setSelect(false);
                    }
                }
                adapter.notifyDataSetChanged();
                if (null != mTurnOverMemberAdapter) {
                    mTurnOverMemberAdapter.getData().clear();
                    mTurnOverMemberAdapter.notifyDataSetChanged();
                }
                showProgress();
                MemberByProjectIdReq req = new MemberByProjectIdReq();
                req.setPid(mProjectId);
                req.setCid(turnOverCompanyAdapter.getData().get(position).getId());
                mPresenter.getMemberByProjectIdTurnOver(req);
            }
        });
        TextView btn = (TextView) mSelectDealerDialog.findViewById(R.id.btn);
        ImageView closeIv = (ImageView) mSelectDealerDialog.findViewById(R.id.closeIv);
        closeIv.setOnClickListener(v -> mSelectDealerDialog.dismiss());
        btn.setOnClickListener(v -> {
            if (isSelectDealer && mSelectDealerList.size() == 0) {
                showToast(getString(R.string.please_select_dealer));
                return;
            }
            if (!isSelectDealer && mSelectVerifierList.size() == 0) {
                showToast(getString(R.string.please_select_verifier));
                return;
            }

            if (isSelectDealer) {
                mQualityHandleImgAdapter.setNewInstance(mSelectDealerList);
                mQualityHandleImgAdapter.notifyDataSetChanged();
            } else {
                mQualityCheckImgAdapter.setNewInstance(mSelectVerifierList);
                mQualityCheckImgAdapter.notifyDataSetChanged();
            }
            mSelectDealerDialog.dismiss();
        });
        if (!mSelectDealerDialog.isShowing()) {
            mSelectDealerDialog.show();
        }
    }

    private TurnOverMemberAdapter mTurnOverMemberAdapter;

    /**
     * 获取处理人成功
     *
     * @param list
     */
    @Override
    public void getMemberByProjectIdTurnOverSuccess(ArrayList<ProjectMemberBean> list) {
        dismissProgress();
        mTurnOverMemberAdapter = new TurnOverMemberAdapter(list);
        mMemberRv.setAdapter(mTurnOverMemberAdapter);
        mTurnOverMemberAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                isSelect = !isSelect;
                mSelectIv.setImageResource(R.mipmap.icon_pulldown);
                mSelectDealerRv.setVisibility(View.VISIBLE);
                mCompanyRv.setVisibility(View.GONE);
                mMemberRv.setVisibility(View.GONE);
                ProjectMemberBean bean = mTurnOverMemberAdapter.getData().get(position);
                if (isSelectDealer) {
                    mSelectDealerTV.setText(getString(R.string.dealers));
                    if (mSelectDealerList.size() == 0) {
                        mSelectDealerList.add(bean);
                    } else {
                        List<Integer> list1 = new ArrayList<>();
                        for (int i = 0; i < mSelectDealerList.size(); i++) {
                            list1.add(mSelectDealerList.get(i).getUid());
                        }
                        if (!list1.contains(bean.getUid())) {
                            mSelectDealerList.add(bean);
                        }
                    }
                    mSelectDealerAdapter.setNewInstance(mSelectDealerList);
                } else {
                    mSelectVerifierTV.setText(getString(R.string.verifiers));
                    if (mSelectVerifierList.size() == 0) {
                        mSelectVerifierList.add(bean);
                    } else {
                        List<Integer> list1 = new ArrayList<>();
                        for (int i = 0; i < mSelectVerifierList.size(); i++) {
                            list1.add(mSelectVerifierList.get(i).getUid());
                        }
                        if (!list1.contains(bean.getUid())) {
                            mSelectVerifierList.add(bean);
                        }
                    }
                    mSelectDealerAdapter.setNewInstance(mSelectVerifierList);
                }
            }
        });
    }

    /**
     * 获取处理人失败
     *
     * @param msg
     */
    @Override
    public void getMemberByProjectIdTurnOverFail(String msg) {
        dismissProgress();
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
     * 删除选择的处理人事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDrawerEvent(DeleteDealerEvent event) {
        mSelectDealerAdapter.removeAt(event.getPosition());
    }

    @Override
    public void showError(int flag, Throwable e) {

    }

    @Override
    public void complete(int flag) {

    }
}