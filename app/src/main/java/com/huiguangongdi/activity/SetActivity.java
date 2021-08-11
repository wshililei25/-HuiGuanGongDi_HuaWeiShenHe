package com.huiguangongdi.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cy.dialog.BaseDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.huiguangongdi.R;
import com.huiguangongdi.base.ActivityManager;
import com.huiguangongdi.base.activity.BaseActivity;
import com.huiguangongdi.common.Extra;
import com.huiguangongdi.presenter.MinePresenter;
import com.huiguangongdi.utils.SPUtil;
import com.huiguangongdi.utils.UrlUtils;
import com.huiguangongdi.widget.TitleBar;

import butterknife.BindView;

public class SetActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.languageV)
    View mLanguageV;
    @BindView(R.id.companyIntroductionV)
    View mCompanyIntroductionV;
    @BindView(R.id.instructionsV)
    View mInstructionsV;
    @BindView(R.id.agreementsV)
    View mAgreementsV;
    @BindView(R.id.privacyAgreementsV)
    View mPrivacyAgreementsV;
    @BindView(R.id.contactWayV)
    View mContactWayV;
    @BindView(R.id.logOffTv)
    TextView mLogOffTv;
    @BindView(R.id.currentVersionTv)
    TextView mCurrentVersionTv;

    @Override
    protected int setContentViewID() {
        return R.layout.activity_set;
    }

    @Override
    protected MinePresenter getPresenter() {
        return new MinePresenter();
    }

    @Override
    protected void initView() {
        initImmersionBar();
        mCurrentVersionTv.setText(getVersion());
    }

    private void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(R.id.titleBar)
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    protected void initListener() {
        mTitleBar.getBackIv().setOnClickListener(view -> finish());
        mLanguageV.setOnClickListener(this);
        mContactWayV.setOnClickListener(this);
        mLogOffTv.setOnClickListener(this);
        mAgreementsV.setOnClickListener(this);
        mPrivacyAgreementsV.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.languageV:
                startActivity(new Intent(this, LanguageActivity.class));
                finish();
                break;
            case R.id.contactWayV:
                initContactWayDialog();
                break;
            case R.id.logOffTv:
                SPUtil.clear(this);
                ActivityManager.finish();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.agreementsV:
                Intent intent = new Intent();
                intent.putExtra(Extra.Title, getString(R.string.user_service_agreements));
                intent.putExtra(Extra.Url, UrlUtils.AgreementsUrl);
                intent.setClass(this, WebViewActivity.class);
                startActivity(intent);
                break;
            case R.id.privacyAgreementsV:
                Intent intent1 = new Intent();
                intent1.putExtra(Extra.Title, getString(R.string.privacy_agreements));
                intent1.putExtra(Extra.Url, UrlUtils.PrivacyAgreementsUrl);
                intent1.setClass(this, WebViewActivity.class);
                startActivity(intent1);
                break;
        }
    }

    private void initContactWayDialog() {
        BaseDialog dialog = new BaseDialog(this);
        dialog.contentView(R.layout.dialog_contact_way)
                .gravity(Gravity.BOTTOM)
                .animType(BaseDialog.AnimInType.BOTTOM)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true)
                .show();
        dialog.findViewById(R.id.mobileTv).setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri uri = Uri.parse("tel:" + ((TextView) dialog.findViewById(R.id.mobileTv)).getText().toString());
            intent.setData(uri);
            startActivity(intent);

        });
        dialog.findViewById(R.id.cancelTv).setOnClickListener(v -> dialog.dismiss());
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */

    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return "V" + version;
        } catch (Exception e) {
            e.printStackTrace();
            return "找不到版本号";
        }

    }
}
