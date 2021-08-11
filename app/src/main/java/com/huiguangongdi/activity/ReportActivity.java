package com.huiguangongdi.activity;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTaskListener;
import com.cy.dialog.BaseDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.huiguangongdi.BaseApplication;
import com.huiguangongdi.R;
import com.huiguangongdi.base.activity.BaseActivity;
import com.huiguangongdi.common.Extra;
import com.huiguangongdi.presenter.QualityReportPresenter;
import com.huiguangongdi.req.QualityManagerReq;
import com.huiguangongdi.utils.DateFormatUtil;
import com.huiguangongdi.utils.SPUtil;
import com.huiguangongdi.utils.Util;
import com.huiguangongdi.view.QualityReportView;
import com.jakewharton.rxbinding4.view.RxView;
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.tauth.DefaultUiListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import butterknife.BindView;

/**
 * 质量管理报告、安全管理报告
 */
public class ReportActivity extends BaseActivity<QualityReportPresenter> implements QualityReportView, View.OnClickListener, DownloadTaskListener {

    @BindView(R.id.backIv)
    ImageView mBackIv;
    @BindView(R.id.contentTv)
    WebView mWebView;
    @BindView(R.id.shapeIv)
    ImageView mShapeIv;
    @BindView(R.id.downloadIv)
    ImageView mDownloadIv;
    @BindView(R.id.titleTv)
    TextView mTitleTv;

    private RxPermissions mRxPermissions;
    private BaseDialog mShareDialog;
    private int mProjectId;
    private String mProjectName;
    private boolean mIsQualityOrSafe; //true：质量管理  false：安全管理
    private QualityManagerReq mQualityManagerReq;
    private static final int THUMB_SIZE = 150;

    @Override
    protected int setContentViewID() {
        Aria.download(this).register();
        return R.layout.activity_report;
    }

    @Override
    protected QualityReportPresenter getPresenter() {
        return new QualityReportPresenter();
    }

    @Override
    protected void initView() {
        initImmersionBar();
        mQualityManagerReq = getIntent().getParcelableExtra(Extra.Quality_Req);
        mIsQualityOrSafe = getIntent().getBooleanExtra(Extra.Quality_or_Safe, false);
        mProjectId = (int) SPUtil.get(this, Extra.SP_Project_Id, -1);
        mProjectName = (String) SPUtil.get(this, Extra.SP_Project_Name, "");
        if (mIsQualityOrSafe) {
            mTitleTv.setText(getString(R.string.quality_assurance_report));
        } else {
            mTitleTv.setText(getString(R.string.safe_assurance_report));
        }
        initShareDialog();
        initPermissions();
        initDialog();
    }

    private String fileUrl;

    private void initPermissions() {
        mRxPermissions = new RxPermissions(this);
        RxView.clicks(mDownloadIv)
                .compose(mRxPermissions.ensure(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                .subscribe(granted -> {
                    if (granted) {
                        String url;

                        if (mIsQualityOrSafe) {
                            url = "http://gongdi.shyouhan.com/index.php/index/quality/download_report?pid=" + mProjectId
                                    + "&status=" + mQualityManagerReq.getStatus()
                                    + "&deal_company=" + mQualityManagerReq.getDeal_company()
                                    + "&work_type=" + mQualityManagerReq.getWork_type()
                                    + "&start_date=" + mQualityManagerReq.getStart_date()
                                    + "&end_date=" + mQualityManagerReq.getEnd_date();
                            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                fileUrl = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getPath() + "/" +
                                        mProjectName + getString(R.string.quality_assurance_report)
                                        + DateFormatUtil.stampToDate(System.currentTimeMillis())
                                        + getString(R.string.xls);
                            } else {
                                fileUrl = Environment.getExternalStorageDirectory().getPath() + "/" +
                                        mProjectName + getString(R.string.quality_assurance_report)
                                        + DateFormatUtil.stampToDate(System.currentTimeMillis())
                                        + getString(R.string.xls);
                            }
                        } else {
                            url = "http://gongdi.shyouhan.com/index.php/index/security/download_report?pid=" + mProjectId
                                    + "&status=" + mQualityManagerReq.getStatus()
                                    + "&deal_company=" + mQualityManagerReq.getDeal_company()
                                    + "&work_type=" + mQualityManagerReq.getWork_type()
                                    + "&start_date=" + mQualityManagerReq.getStart_date()
                                    + "&end_date=" + mQualityManagerReq.getEnd_date();
                            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                fileUrl = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getPath() + "/" +
                                        mProjectName + getString(R.string.safe_assurance_report)
                                        + DateFormatUtil.stampToDate(System.currentTimeMillis())
                                        + getString(R.string.xls);
                            } else {
                                fileUrl = Environment.getExternalStorageDirectory().getPath() + "/" +
                                        mProjectName + getString(R.string.safe_assurance_report)
                                        + DateFormatUtil.stampToDate(System.currentTimeMillis())
                                        + getString(R.string.xls);
                            }
                        }
                        showProgress(getString(R.string.downloading));
                        long taskId = Aria.download(this)
                                .load(url)
                                .setFilePath(fileUrl)
                                .create();
                    } else {
                        showToast(getString(R.string.download_permissions));
                    }
                });
    }

    private void initShareDialog() {
        mShareDialog = new BaseDialog(this);
        mShareDialog.contentView(R.layout.dialog_share)
                .gravity(Gravity.BOTTOM)
                .animType(BaseDialog.AnimInType.BOTTOM)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true);

        mShareDialog.findViewById(R.id.closeIv).setOnClickListener(v -> {
            mShareDialog.dismiss();
        });
        mShareDialog.findViewById(R.id.weChatV).setOnClickListener(v -> {
            if (null == mQualityManagerReq) return;
            //初始化一个WXWebpageObject，填写url
            WXWebpageObject webpage = new WXWebpageObject();

            String url = "";
            if (mIsQualityOrSafe) {
                url = "http://gongdi.shyouhan.com/index.php/index/quality/make_report?pid=" + mProjectId
                        + "&status=" + mQualityManagerReq.getStatus()
                        + "&deal_company=" + mQualityManagerReq.getDeal_company()
                        + "&work_type=" + mQualityManagerReq.getWork_type()
                        + "&start_date=" + mQualityManagerReq.getStart_date()
                        + "&end_date=" + mQualityManagerReq.getEnd_date()
                        + "&share=1";
            } else {
                url = "http://gongdi.shyouhan.com/index.php/index/security/make_report?pid=" + mProjectId
                        + "&status=" + mQualityManagerReq.getStatus()
                        + "&deal_company=" + mQualityManagerReq.getDeal_company()
                        + "&work_type=" + mQualityManagerReq.getWork_type()
                        + "&start_date=" + mQualityManagerReq.getStart_date()
                        + "&end_date=" + mQualityManagerReq.getEnd_date()
                        + "&share=1";
            }
            Log.d("XiLei", "url===" + url);
            webpage.webpageUrl = url;

            //用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
            WXMediaMessage msg = new WXMediaMessage(webpage);

            String projectName = (String) SPUtil.get(BaseApplication.appContext, Extra.SP_Project_Name, getString(R.string.no_project));
            if (mIsQualityOrSafe) {
                msg.title = getString(R.string.quality_assurance_report);
                msg.description = projectName + getString(R.string.quality_assurance_report)
                        + DateFormatUtil.stampToDateYMD(System.currentTimeMillis());
            } else {
                msg.title = getString(R.string.safe_assurance_report);
                msg.description = projectName + getString(R.string.safe_assurance_report)
                        + DateFormatUtil.stampToDateYMD(System.currentTimeMillis());
            }

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
        if (mIsQualityOrSafe) {
            params.putString(QQShare.SHARE_TO_QQ_TITLE, getString(R.string.quality_assurance_report));
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, projectName + getString(R.string.quality_assurance_report)
                    + DateFormatUtil.stampToDateYMD(System.currentTimeMillis()));
        } else {
            params.putString(QQShare.SHARE_TO_QQ_TITLE, getString(R.string.safe_assurance_report));
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, projectName + getString(R.string.safe_assurance_report)
                    + DateFormatUtil.stampToDateYMD(System.currentTimeMillis()));
        }
        String url = "";
        if (mIsQualityOrSafe) {
            url = "http://gongdi.shyouhan.com/index.php/index/quality/make_report?pid=" + mProjectId
                    + "&status=" + mQualityManagerReq.getStatus()
                    + "&deal_company=" + mQualityManagerReq.getDeal_company()
                    + "&work_type=" + mQualityManagerReq.getWork_type()
                    + "&start_date=" + mQualityManagerReq.getStart_date()
                    + "&end_date=" + mQualityManagerReq.getEnd_date()
                    + "&share=1";
        } else {
            url = "http://gongdi.shyouhan.com/index.php/index/security/make_report?pid=" + mProjectId
                    + "&status=" + mQualityManagerReq.getStatus()
                    + "&deal_company=" + mQualityManagerReq.getDeal_company()
                    + "&work_type=" + mQualityManagerReq.getWork_type()
                    + "&start_date=" + mQualityManagerReq.getStart_date()
                    + "&end_date=" + mQualityManagerReq.getEnd_date()
                    + "&share=1";
        }

        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
//        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, getString(R.string.app_name));
//        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, "其他附加功能");
        BaseApplication.mTencent.shareToQQ(ReportActivity.this, params, qqShareListener);
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
        mBackIv.setOnClickListener(view -> finish());
        mShapeIv.setOnClickListener(this::onClick);
    }

    private void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(R.id.titleV)
                .statusBarDarkFont(true).init();
    }

    @Override
    protected void initData() {
        showProgress();
        mPresenter.getQualityReport(mQualityManagerReq, mIsQualityOrSafe);
    }

    @Override
    public void getQualityReportSuccess(String bean) {
        dismissProgress();
        mWebView.loadDataWithBaseURL(null, bean, "text/html", "UTF-8", null);//解决乱码
    }

    @Override
    public void getQualityReportFail(String msg) {
        dismissProgress();
        showToast(msg);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shapeIv:
                if (null != mShareDialog && !mShareDialog.isShowing()) {
                    mShareDialog.show();
                }
                break;
        }
    }

    @Override
    public void showError(int flag, Throwable e) {

    }

    @Override
    public void complete(int flag) {

    }

    @Override
    public void onWait(com.arialyy.aria.core.task.DownloadTask downloadTask) {

    }

    @Override
    public void onPre(com.arialyy.aria.core.task.DownloadTask downloadTask) {

    }

    @Override
    public void onTaskPre(com.arialyy.aria.core.task.DownloadTask downloadTask) {

    }

    @Override
    public void onTaskResume(com.arialyy.aria.core.task.DownloadTask downloadTask) {

    }

    @Override
    public void onTaskStart(com.arialyy.aria.core.task.DownloadTask downloadTask) {

    }

    @Override
    public void onTaskStop(com.arialyy.aria.core.task.DownloadTask downloadTask) {

    }

    @Override
    public void onTaskCancel(com.arialyy.aria.core.task.DownloadTask downloadTask) {

    }

    @Override
    public void onTaskFail(com.arialyy.aria.core.task.DownloadTask downloadTask, Exception e) {

    }

    @Override
    public void onTaskComplete(com.arialyy.aria.core.task.DownloadTask downloadTask) {
        dismissProgress();
        Log.d("XiLei", "downloadTask.getFilePath()=" + downloadTask.getFilePath());
        if (!isFinishing() && !mNoProjectDialog.isShowing()) {
            contentTv.setText(getString(R.string.download_finish, downloadTask.getFilePath()));
            mNoProjectDialog.show();
        }

        downloadTask.stop();
        downloadTask.cancel();
//        showLongToast(getString(R.string.download_finish));
    }

    private BaseDialog mNoProjectDialog;
    private TextView contentTv;

    private void initDialog() {

        mNoProjectDialog = new BaseDialog(this);
        mNoProjectDialog.contentView(R.layout.dialog_report)
                .gravity(Gravity.CENTER)
                .animType(BaseDialog.AnimInType.CENTER)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true);
        contentTv = mNoProjectDialog.findViewById(R.id.contentTv);
        mNoProjectDialog.findViewById(R.id.closeBtn).setOnClickListener(v -> mNoProjectDialog.dismiss());
        mNoProjectDialog.findViewById(R.id.createBtn).setOnClickListener(v -> {
            mNoProjectDialog.dismiss();
        });

    }

    @Override
    public void onTaskRunning(com.arialyy.aria.core.task.DownloadTask downloadTask) {
        int p = downloadTask.getPercent();    //任务进度百分比
        String speed = downloadTask.getConvertSpeed();    //转换单位后的下载速度，单位转换需要在配置文件中打开
        long speed1 = downloadTask.getSpeed(); //原始byte长度速度
    }

    @Override
    public void onNoSupportBreakPoint(com.arialyy.aria.core.task.DownloadTask downloadTask) {

    }
}