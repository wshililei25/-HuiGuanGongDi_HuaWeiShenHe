package com.huiguangongdi.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cy.dialog.BaseDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.huiguangongdi.R;
import com.huiguangongdi.adapter.ManagerDetailImgAdapter;
import com.huiguangongdi.adapter.ManagerDetailRefuseImgAdapter;
import com.huiguangongdi.adapter.QualityManagerDetailAdapter;
import com.huiguangongdi.base.activity.BaseActivity;
import com.huiguangongdi.bean.QualityManagerDetailBean;
import com.huiguangongdi.common.Extra;
import com.huiguangongdi.presenter.SafeManagerDetailPresenter;
import com.huiguangongdi.req.QualityManagerDetailReq;
import com.huiguangongdi.req.QualityManagerHandleReq;
import com.huiguangongdi.req.QualityManagerRefuseReq;
import com.huiguangongdi.utils.GlideEngine;
import com.huiguangongdi.view.SafeManagerDetailView;
import com.huiguangongdi.widget.TitleBar;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.kareluo.imaging.BoxingFileHelper;
import me.kareluo.imaging.IMGEditActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 安全管理详情
 */
public class SafeManagerDetailActivity extends BaseActivity<SafeManagerDetailPresenter> implements SafeManagerDetailView, View.OnClickListener {

    @BindView(R.id.parentV)
    View mParentV;
    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.nameTv)
    TextView mNameTv;
    @BindView(R.id.specialtyTv)
    TextView mSpecialtyTv;
    @BindView(R.id.addressTv)
    TextView mAddressTv;
    @BindView(R.id.remarkTv)
    TextView mRemarkTv;
    @BindView(R.id.dayTv)
    TextView mDayTv;
    @BindView(R.id.peopleTv)
    TextView mPeopleTv;
    @BindView(R.id.schemeTv)
    TextView mSchemeTv;
    @BindView(R.id.statusTv)
    TextView mStatusTv;
    @BindView(R.id.statusIv)
    ImageView mStatusIv;
    @BindView(R.id.submitBtn)
    TextView mSubmitBtn;
    @BindView(R.id.refuseBtn)
    TextView mRefuseBtn;
    @BindView(R.id.passBtn)
    TextView mPassBtn;
    @BindView(R.id.verificationV)
    View mVerificationV;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private int mManagerId;
    private QualityManagerDetailAdapter mAdapter;
    private boolean mIsSubmitOrRefuseBtn = true; //true：提交处理 false：拒绝
    private static final int WeXin_REQUEST_CODE = 2020;

    @Override
    protected int setContentViewID() {
        return R.layout.activity_safe_manager_detail;
    }

    @Override
    protected SafeManagerDetailPresenter getPresenter() {
        return new SafeManagerDetailPresenter();
    }

    @Override
    protected void initView() {
        mManagerId = getIntent().getIntExtra(Extra.Manager_Id, 0);
        initImmersionBar();
        initRecyclerView();
        initDialog();
    }

    private void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(R.id.titleBar)
                .statusBarDarkFont(true).init();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new QualityManagerDetailAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initDialog() {
        initSubmitDialog();
        initRefuseDialog();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mTitleBar.getBackIv().setOnClickListener(view -> finish());
        mSubmitBtn.setOnClickListener(this::onClick);
        mRefuseBtn.setOnClickListener(this::onClick);
        mPassBtn.setOnClickListener(this::onClick);
        mTitleBar.getRightTv().setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(this, EditSafeActivity.class);
            intent.putExtra(Extra.Manager_Id, mManagerId);
            startActivity(intent);
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitBtn:
                if (null != mSubmitDialog && !mSubmitDialog.isShowing()) {
                    mIsSubmitOrRefuseBtn = true;
                    mSubmitDialog.show();
                }
                break;
            case R.id.refuseBtn:
                if (null != mRefuseDialog && !mRefuseDialog.isShowing()) {
                    mIsSubmitOrRefuseBtn = false;
                    mRefuseDialog.show();
                }
                break;
            case R.id.passBtn:
                showProgress();
                QualityManagerRefuseReq req = new QualityManagerRefuseReq();
                req.setId(mManagerId);
                req.setStatus(1);
                mPresenter.refuseSafeManager(req);
                break;
        }
    }

    private BaseDialog mSubmitDialog;
    private List<String> mHandleImagePaths = new ArrayList<>();
    private ManagerDetailImgAdapter mManagerDetailImgAdapter;

    /**
     * 提交处理Dialog
     */
    private void initSubmitDialog() {
        mSubmitDialog = new BaseDialog(this);
        mSubmitDialog.contentView(R.layout.dialog_aquality_manager_submit)
                .gravity(Gravity.BOTTOM)
                .animType(BaseDialog.AnimInType.BOTTOM)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true);

        EditText editText = (EditText) mSubmitDialog.findViewById(R.id.et);
        TextView numTv = (TextView) mSubmitDialog.findViewById(R.id.numTv);
        TextView btn = (TextView) mSubmitDialog.findViewById(R.id.btn);
        ImageView closeIv = (ImageView) mSubmitDialog.findViewById(R.id.closeIv);
        closeIv.setOnClickListener(v -> mSubmitDialog.dismiss());
        RecyclerView handleRv = mSubmitDialog.findViewById(R.id.handleRv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        handleRv.setLayoutManager(linearLayoutManager);
        mManagerDetailImgAdapter = new ManagerDetailImgAdapter();
        handleRv.setAdapter(mManagerDetailImgAdapter);
        mHandleImagePaths.add("");
        mManagerDetailImgAdapter.setNewInstance(mHandleImagePaths);
        mManagerDetailImgAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (position == mManagerDetailImgAdapter.getData().size() - 1) {
                    initPhotoDialog();
                } else {
                    List<String> list = mHandleImagePaths.subList(0, mHandleImagePaths.size() - 1);
                    List<LocalMedia> medias = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        LocalMedia localMedia = new LocalMedia();
                        localMedia.setPath(list.get(i));
                        medias.add(localMedia);
                    }
                    PictureSelector.create(SafeManagerDetailActivity.this)
                            .themeStyle(R.style.picture_default_style) // xml设置主题
                            .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                            .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                            .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                            .openExternalPreview(position, medias);
                }
            }
        });

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
            if (mHandleImagePaths.size() - 1 == 0) {
                showToast(getString(R.string.please_upload_pictures));
                return;
            }
            /*if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                showToast(getString(R.string.please_input_dealer_finish_hint));
                return;
            }*/
            showProgress();
            QualityManagerHandleReq req = new QualityManagerHandleReq();
            req.setId(mManagerId);
            req.setQuestion_supple(editText.getText().toString().trim());
            List<String> list = mHandleImagePaths.subList(0, mHandleImagePaths.size() - 1);
            req.setImage(list.toArray(new String[0]));
            mPresenter.handleSafeManager(req);
        });
    }

    /**
     * 提交处理成功
     */
    @Override
    public void handleQualityManagerSuccess() {
        dismissProgress();
        if (null != mSubmitDialog) {
            mSubmitDialog.dismiss();
        }
        getQualityManagerDetail();
    }

    @Override
    public void handleQualityManagerFail(String msg) {
        dismissProgress();
    }

    private BaseDialog mRefuseDialog;
    private List<String> mRefuseImagePaths = new ArrayList<>();
    private ManagerDetailRefuseImgAdapter mManagerDetailRefuseImgAdapter;

    /**
     * 拒绝Dialog
     */
    private void initRefuseDialog() {
        mRefuseDialog = new BaseDialog(this);
        mRefuseDialog.contentView(R.layout.dialog_aquality_manager_refuse)
                .gravity(Gravity.BOTTOM)
                .animType(BaseDialog.AnimInType.BOTTOM)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true);

        EditText editText = (EditText) mRefuseDialog.findViewById(R.id.et);
        TextView numTv = (TextView) mRefuseDialog.findViewById(R.id.numTv);
        TextView btn = (TextView) mRefuseDialog.findViewById(R.id.btn);
        ImageView closeIv = (ImageView) mRefuseDialog.findViewById(R.id.closeIv);
        closeIv.setOnClickListener(v -> mRefuseDialog.dismiss());
        RecyclerView refuseRv = mRefuseDialog.findViewById(R.id.refuseRv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        refuseRv.setLayoutManager(linearLayoutManager);
        mManagerDetailRefuseImgAdapter = new ManagerDetailRefuseImgAdapter();
        refuseRv.setAdapter(mManagerDetailRefuseImgAdapter);
        mRefuseImagePaths.add("");
        mManagerDetailRefuseImgAdapter.setNewInstance(mRefuseImagePaths);
        mManagerDetailRefuseImgAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (position == mManagerDetailRefuseImgAdapter.getData().size() - 1) {
                    initPhotoDialog();
                } else {
                    List<String> list = mRefuseImagePaths.subList(0, mRefuseImagePaths.size() - 1);
                    List<LocalMedia> medias = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        LocalMedia localMedia = new LocalMedia();
                        localMedia.setPath(list.get(i));
                        medias.add(localMedia);
                    }
                    PictureSelector.create(SafeManagerDetailActivity.this)
                            .themeStyle(R.style.picture_default_style) // xml设置主题
                            .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                            .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                            .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                            .openExternalPreview(position, medias);
                }
            }
        });

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
            if (mRefuseImagePaths.size() - 1 == 0) {
                showToast(getString(R.string.please_upload_pictures));
                return;
            }
            if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                showToast(getString(R.string.please_input_refuse_cause));
                return;
            }
            showProgress();
            QualityManagerRefuseReq req = new QualityManagerRefuseReq();
            req.setId(mManagerId);
            req.setStatus(2);
            req.setQuestion_supple(editText.getText().toString().trim());
            List<String> list = mRefuseImagePaths.subList(0, mRefuseImagePaths.size() - 1);
            req.setImage(list.toArray(new String[0]));
            mPresenter.refuseSafeManager(req);
        });
    }

    /**
     * 拒绝
     */
    @Override
    public void refuseQualityManagerSuccess() {
        dismissProgress();
        if (null != mRefuseDialog) {
            mRefuseDialog.dismiss();
        }
        getQualityManagerDetail();
    }

    @Override
    public void refuseQualityManagerFail(String msg) {
        dismissProgress();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getQualityManagerDetail();
    }

    private void getQualityManagerDetail() {
        showProgress();
        QualityManagerDetailReq req = new QualityManagerDetailReq();
        req.setId(mManagerId);
        mPresenter.getSafeManagerDetail(req);
    }

    @Override
    public void getProjectMemberSuccess(QualityManagerDetailBean bean) {
        dismissProgress();
        mAdapter.setNewInstance(bean.getLog());
        if (bean.getInfo().isCreater() || bean.getInfo().getRole() == 1 || bean.getInfo().getRole() == 2) {
            if (bean.getInfo().getStatus().equals(getString(R.string.off_stocks))) {
                mTitleBar.getRightTv().setText("");
            } else {
                mTitleBar.getRightTv().setText(getString(R.string.edit));
            }
        }
        if (bean.getInfo().isHandler() && bean.getInfo().getStatus().equals(getString(R.string.in_hand))) {
            mSubmitBtn.setVisibility(View.VISIBLE);
            mVerificationV.setVisibility(View.INVISIBLE);
        }
        if (bean.getInfo().isChecker() && bean.getInfo().getStatus().equals(getString(R.string.verification))) {
            mSubmitBtn.setVisibility(View.INVISIBLE);
            mVerificationV.setVisibility(View.VISIBLE);
        }
        if (bean.getInfo().getStatus().equals(getString(R.string.in_hand))) {
            mStatusIv.setImageResource(R.mipmap.state_processing);
            mStatusTv.setTextColor(ContextCompat.getColor(this, R.color.c_FF673D));
        } else if (bean.getInfo().getStatus().equals(getString(R.string.verification))) {
            mStatusIv.setImageResource(R.mipmap.state_check);
            mStatusTv.setTextColor(ContextCompat.getColor(this, R.color.c_FDC200));
        } else if (bean.getInfo().getStatus().equals(getString(R.string.off_stocks))) {
            mStatusIv.setImageResource(R.mipmap.state_complete);
            mStatusTv.setTextColor(ContextCompat.getColor(this, R.color.c_00D092));
            mSubmitBtn.setVisibility(View.GONE);
            mVerificationV.setVisibility(View.GONE);
        }
        mNameTv.setText(bean.getInfo().getName());
        mSpecialtyTv.setText(bean.getInfo().getWork_type());
        String[] address = bean.getInfo().getAddress();
        if (bean.getInfo().getType() == 1) {
            mAddressTv.setText(address[0] + getString(R.string.nom) + " "
                    + address[1] + getString(R.string.plies) + " "
                    + getString(R.string.horizontal_axes_hint, address[2], address[3]) + " "
                    + getString(R.string.vertical_axes_hint, address[4], address[5]));
        } else {
            mAddressTv.setText(address[0] + getString(R.string.nom) + " "
                    + address[1] + getString(R.string.plies) + " "
                    + getString(R.string.room_number) + address[2]);
        }
        if (!TextUtils.isEmpty(address[6])) {
            mRemarkTv.setVisibility(View.VISIBLE);
            mRemarkTv.setText(getString(R.string.remark) + address[6]);
        }
        mStatusTv.setText(bean.getInfo().getStatus());
        mDayTv.setText(bean.getInfo().getDay() + getString(R.string.day) + " "
                + getString(R.string.vertical_line) + " "
                + bean.getInfo().getBegin_time()
                + getString(R.string.whippletree)
                + bean.getInfo().getEnd_time());
        mPeopleTv.setText(getString(R.string.dealers)
                + bean.getHandle().getCount() + " "
                + getString(R.string.vertical_line) + " "
                + getString(R.string.verifiers)
                + bean.getCheck().getCount());
        mSchemeTv.setText(bean.getInfo().getSolution());

    }

    @Override
    public void getProjectMemberFail(String msg) {
        dismissProgress();
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
        if (mIsSubmitOrRefuseBtn) {
            mHandleImagePaths.add(0, bean);
            mManagerDetailImgAdapter.setNewInstance(mHandleImagePaths);
            mManagerDetailImgAdapter.notifyDataSetChanged();
        } else {
            mRefuseImagePaths.add(0, bean);
            mManagerDetailRefuseImgAdapter.setNewInstance(mRefuseImagePaths);
            mManagerDetailRefuseImgAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void uploadPhotoPathFail(String msg) {
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