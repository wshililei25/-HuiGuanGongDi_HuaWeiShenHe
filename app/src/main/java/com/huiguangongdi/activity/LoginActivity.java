package com.huiguangongdi.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.gyf.immersionbar.ImmersionBar;
import com.huiguangongdi.R;
import com.huiguangongdi.base.activity.BaseActivity;
import com.huiguangongdi.base.api.Constant;
import com.huiguangongdi.bean.BaseBean;
import com.huiguangongdi.bean.UserBean;
import com.huiguangongdi.common.Extra;
import com.huiguangongdi.presenter.LoginPresenter;
import com.huiguangongdi.utils.SPUtil;
import com.huiguangongdi.utils.UrlUtils;
import com.huiguangongdi.view.LoginView;
import com.huiguangongdi.widget.TitleBar;

import butterknife.BindView;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView, View.OnClickListener {

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.btn)
    TextView mBtn;
    @BindView(R.id.codeTv)
    TextView mCodeTv;
    @BindView(R.id.clearIv)
    ImageView mClearIv;
    @BindView(R.id.phoneEt)
    EditText mPhoneEt;
    @BindView(R.id.codeEt)
    EditText mCodeEt;
    @BindView(R.id.agreementsV)
    View mAgreementsV;
    @BindView(R.id.privacyAgreementsV)
    View mPrivacyAgreementsV;
    @BindView(R.id.checkbox)
    CheckBox mCheckbox;

    private TimeCount mTimeCount;

    @Override
    protected int setContentViewID() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initView() {
        initImmersionBar();

        mTimeCount = new TimeCount(60000, 1000);
        mPhoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    mClearIv.setVisibility(View.VISIBLE);
                } else {
                    mClearIv.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(R.id.titleBar)
                .statusBarDarkFont(true)
                .keyboardEnable(true).init();
    }

    @Override
    protected void initListener() {
        mTitleBar.getBackIv().setOnClickListener(view -> finish());
        mBtn.setOnClickListener(this::onClick);
        mCodeTv.setOnClickListener(this::onClick);
        mClearIv.setOnClickListener(view -> mPhoneEt.setText(""));
        mAgreementsV.setOnClickListener(this);
        mPrivacyAgreementsV.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.codeTv:
                if (mPhoneEt.getText().toString().trim().isEmpty()) {
                    showToast(getString(R.string.please_input_phone));
                    return;
                }
                showProgress();
                mPresenter.getCode(mPhoneEt.getText().toString().trim());
                break;
            case R.id.btn:
                if (mPhoneEt.getText().toString().trim().isEmpty()) {
                    showToast(getString(R.string.please_input_phone));
                    return;
                }
                if (mCodeEt.getText().toString().trim().isEmpty()) {
                    showToast(getString(R.string.please_input_code));
                    return;
                }
                if (!mCheckbox.isChecked()) {
                    showToast(getString(R.string.agreement_hint));
                    return;
                }
                showProgress();
                mPresenter.login(mPhoneEt.getText().toString().trim(), mCodeEt.getText().toString().trim());
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

    @Override
    public void getCodeSuccess(BaseBean bean) {
        dismissProgress();
        mTimeCount.start();
    }

    @Override
    public void getCodeFail(String msg) {
        dismissProgress();
        showToast(msg);
    }

    @Override
    public void loginSuccess(BaseBean<UserBean> bean) {
        dismissProgress();
        SPUtil.put(this, Extra.SP_IS_Login, true);
        SPUtil.put(this, Extra.SP_User_Mobile, bean.getData().getMobile());
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void loginFail(BaseBean<UserBean> bean) {
        dismissProgress();
        if (bean.getCode() == Constant.UNREGINSTER_CODE) {
            Intent intent = new Intent(this, InfoActivity.class);
            intent.putExtra(Extra.Mobile, mPhoneEt.getText().toString().trim());
            startActivity(intent);
        } else {
            showToast(bean.getMsg());
        }
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mCodeTv.setClickable(false);
            mCodeTv.setText(getString(R.string.send_again) + millisUntilFinished / 1000 + getString(R.string.second));
            mCodeTv.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.c_8C9AA3));
        }

        @Override
        public void onFinish() {
            mCodeTv.setText(getString(R.string.get_code));
            mCodeTv.setClickable(true);
            mCodeTv.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.c_0071BC));
        }
    }


    @Override
    public void showError(int flag, Throwable e) {

    }

    @Override
    public void complete(int flag) {

    }

}