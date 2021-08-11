package com.huiguangongdi.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cy.dialog.BaseDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.huiguangongdi.BaseApplication;
import com.huiguangongdi.R;
import com.huiguangongdi.activity.EditInfoActivity;
import com.huiguangongdi.activity.FeedbackActivity;
import com.huiguangongdi.activity.SetActivity;
import com.huiguangongdi.base.activity.BaseFragment;
import com.huiguangongdi.bean.UploadPhotoBean;
import com.huiguangongdi.bean.UserBean;
import com.huiguangongdi.common.Extra;
import com.huiguangongdi.presenter.MinePresenter;
import com.huiguangongdi.req.UserInfoByMobileReq;
import com.huiguangongdi.utils.GlideEngine;
import com.huiguangongdi.utils.GlideUtil;
import com.huiguangongdi.utils.SPUtil;
import com.huiguangongdi.utils.Util;
import com.huiguangongdi.view.MineView;
import com.huiguangongdi.widget.TitleBar;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.tauth.DefaultUiListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

public class MineFragment extends BaseFragment<MinePresenter> implements MineView, View.OnClickListener {

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.headIv)
    ImageView mHeadIv;
    @BindView(R.id.nameTv)
    TextView mNameTv;
    @BindView(R.id.specialtyTv)
    TextView mSpecialtyTv;
    @BindView(R.id.phoneTv)
    TextView mPhoneTv;
    @BindView(R.id.addressTv)
    TextView mAddressTv;
    @BindView(R.id.companyNameTv)
    TextView mCompanyNameTv;
    @BindView(R.id.jobTv)
    TextView mJobTv;
    @BindView(R.id.setV)
    View mSetV;
    @BindView(R.id.proposeV)
    View mProposeV;
    @BindView(R.id.shareV)
    View mShareV;
    @BindView(R.id.editIv)
    ImageView mEditIv;

    private BaseDialog mShareDialog;
    private static final int THUMB_SIZE = 150;

    @Override
    protected int setContentViewID() {
        return R.layout.fragment_mine;
    }

    @Override
    protected MinePresenter getPresenter() {
        return new MinePresenter();
    }

    @Override
    protected void initView() {
        initImmersionBar();
        initShareDialog();
    }

    private void initImmersionBar() {
        ImmersionBar.setTitleBar(this, mTitleBar);
    }

    private void initShareDialog() {
        mShareDialog = new BaseDialog(getActivity());
        mShareDialog.contentView(R.layout.dialog_share)
                .gravity(Gravity.BOTTOM)
                .animType(BaseDialog.AnimInType.BOTTOM)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true);

        mShareDialog.findViewById(R.id.closeIv).setOnClickListener(v -> {
            mShareDialog.dismiss();
        });
        mShareDialog.findViewById(R.id.weChatV).setOnClickListener(v -> {
            //初始化一个WXWebpageObject，填写url
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = BaseApplication.ShareUrl;

            //用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title = getString(R.string.app_name);
            msg.description = getString(R.string.share_content);
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
            bmp.recycle();
            msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("webpage");
            req.message = msg;
            req.scene = SendMessageToWX.Req.WXSceneSession;
            //调用api接口，发送数据到微信
            BaseApplication.api.sendReq(req);
        });
        mShareDialog.findViewById(R.id.qqV).setOnClickListener(v -> {
            onClickShare();
        });
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private void onClickShare() {
        String projectName = (String) SPUtil.get(BaseApplication.appContext, Extra.SP_Project_Name, getString(R.string.no_project));
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, getString(R.string.app_name));
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, getString(R.string.share_content));
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, BaseApplication.ShareUrl);
//        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, getString(R.string.app_name));
//        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, "其他附加功能");
        BaseApplication.mTencent.shareToQQ(getActivity(), params, qqShareListener);
    }

    IUiListener qqShareListener = new DefaultUiListener() {
        @Override
        public void onCancel() {
        }

        @Override
        public void onComplete(Object response) {
        }

        @Override
        public void onError(UiError e) {
        }

        @Override
        public void onWarning(int code) {
            if (code == Constants.ERROR_NO_AUTHORITY) {
//                Util.toastMessage(QQShareActivity.this, "onWarning: 请授权手Q访问分享的文件的读取权限!");
            }
        }
    };

    @Override
    protected void initListener() {
        mSetV.setOnClickListener(this);
        mProposeV.setOnClickListener(this);
        mHeadIv.setOnClickListener(this);
        mEditIv.setOnClickListener(this);
        mShareV.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        String mobile = (String) SPUtil.get(getContext(), Extra.SP_User_Mobile, "");
        if (TextUtils.isEmpty(mobile)) return;
        UserInfoByMobileReq req = new UserInfoByMobileReq();
        req.setMobile(mobile);
        mPresenter.getUserIfoByMobile(req);
    }

    @Override
    public void getUserInfoByMobileSuccess(UserBean bean) {
        if (!TextUtils.isEmpty(bean.getAvatar())) {
            GlideUtil.setCirclePic(getContext(), bean.getAvatar(), mHeadIv);
        }
        mNameTv.setText(bean.getTruename());
        mSpecialtyTv.setText(bean.getMajor_name());
        mPhoneTv.setText(bean.getMobile());
        mAddressTv.setText(bean.getAddress());
        mCompanyNameTv.setText(bean.getCompany());
        mJobTv.setText(bean.getPosition());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setV:
                startActivity(new Intent(getActivity(), SetActivity.class));
                break;
            case R.id.proposeV:
                startActivity(new Intent(getActivity(), FeedbackActivity.class));
                break;
            case R.id.editIv:
                startActivity(new Intent(getActivity(), EditInfoActivity.class));
                break;
            case R.id.headIv:
                initPhotoDialog();
                break;
            case R.id.shareV:
                if (null != mShareDialog && !mShareDialog.isShowing()) {
                    mShareDialog.show();
                }
                break;
        }
    }

    private void initPhotoDialog() {
        BaseDialog dialog = new BaseDialog(getActivity());
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
                    .forResult(PictureConfig.REQUEST_CAMERA);
        });
        dialog.findViewById(R.id.albumTv).setOnClickListener(v -> {
            dialog.dismiss();
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())
                    .selectionMode(PictureConfig.SINGLE)
                    .loadImageEngine(GlideEngine.createGlideEngine())
                    .enableCrop(true)
                    .freeStyleCropEnabled(true)
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
                case PictureConfig.REQUEST_CAMERA: //拍照返回
                case PictureConfig.CHOOSE_REQUEST: //相册返回
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    if (null == selectList || selectList.size() == 0) return;
                    uploadPath(selectList.get(0).getRealPath());
                    break;
                default:
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
        mPresenter.uploadHeadPath(body);
    }

    @Override
    public void uploadPhotoPathSuccess(UploadPhotoBean bean) {
        GlideUtil.setCirclePic(getActivity(), bean.getAvatar(), mHeadIv);
        dismissProgress();
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
