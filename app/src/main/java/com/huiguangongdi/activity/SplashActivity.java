package com.huiguangongdi.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cy.dialog.BaseDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.huiguangongdi.R;
import com.huiguangongdi.common.Extra;
import com.huiguangongdi.utils.SPUtil;
import com.huiguangongdi.utils.UrlUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity implements Observer<Long> {

    private Disposable mSubscribe;
    private long mTotalTime = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        initImmersionBar();
        if ((boolean) SPUtil.get(this, Extra.SP_IS_Agreement, false)) {
            startCountDown();
        } else {
            initDialog();
        }
    }

    private void initImmersionBar() {
        ImmersionBar.with(this)
//            .statusBarDarkFont(true)
                .init();
    }

    /**
     * 开启倒计时
     */
    private void startCountDown() {
        Observable.interval(1, TimeUnit.SECONDS)
                .map(aLong -> mTotalTime - aLong)
                .take(mTotalTime + 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    /**
     * 关闭倒计时
     */
    private void stopCountDown() {
        if (mSubscribe != null && !mSubscribe.isDisposed()) {
            mSubscribe.dispose();
            mSubscribe = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopCountDown();
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        mSubscribe = d;
    }

    @Override
    public void onNext(@NonNull Long aLong) {

    }

    @Override
    public void onError(@NonNull Throwable e) {
        finish();
    }

    @Override
    public void onComplete() {
        if ((boolean) SPUtil.get(SplashActivity.this, Extra.SP_IS_Login, false)) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }
        finish();
    }

    private void initDialog() {
        BaseDialog dialog = new BaseDialog(this);
        dialog.contentView(R.layout.dialog_agreement)
                .gravity(Gravity.CENTER)
                .animType(BaseDialog.AnimInType.CENTER)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .canceledOnTouchOutside(true)
                .show();
        TextView contentTv = dialog.findViewById(R.id.contentTv);
        contentTv.setText(getSpan());
        contentTv.setHighlightColor(Color.TRANSPARENT);//去掉点击效果
        contentTv.setMovementMethod(LinkMovementMethod.getInstance());//这句话必须有，
        dialog.findViewById(R.id.quitTv).setOnClickListener(view -> {
            dialog.dismiss();
            finish();
        });
        dialog.findViewById(R.id.agreeTv).setOnClickListener(v -> {
            dialog.dismiss();
            SPUtil.put(this, Extra.SP_IS_Agreement, true);
            if ((boolean) SPUtil.get(this, Extra.SP_IS_Login, false)) {
                startActivity(new Intent(this, MainActivity.class));
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
            finish();
        });
    }

    private SpannableString getSpan() {
        final View.OnClickListener l = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.putExtra(Extra.Title, getString(R.string.privacy_agreements));
                intent1.putExtra(Extra.Url, UrlUtils.PrivacyAgreementsUrl);
                intent1.setClass(SplashActivity.this, WebViewActivity.class);
                startActivity(intent1);
            }
        };
        final View.OnClickListener l2 = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Extra.Title, getString(R.string.user_service_agreements));
                intent.putExtra(Extra.Url, UrlUtils.AgreementsUrl);
                intent.setClass(SplashActivity.this, WebViewActivity.class);
                startActivity(intent);
            }
        };
        String one = getString(R.string.splash_agreement_one);
        String two = getString(R.string.app_user_agreement);
        String three = getString(R.string.policy);
        String four = getString(R.string.splash_agreement_two);
        String and = getString(R.string.add);
        SpannableString spnableInfo = new SpannableString(
                one + two + and + three + four);
        int start = one.length() + two.length() + and.length();
        int end = one.length() + two.length() + and.length() + three.length();
        int start2 = one.length();
        int end2 = one.length() + two.length();

        spnableInfo.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);//去掉下划线
            }

            @Override
            public void onClick(View widget) {
                l.onClick(widget);
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spnableInfo.setSpan(new ForegroundColorSpan(Color.parseColor("#007aff")), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spnableInfo.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }

            @Override
            public void onClick(View widget) {
                l2.onClick(widget);
            }
        }, start2, end2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spnableInfo.setSpan(new ForegroundColorSpan(Color.parseColor("#007aff")), start2, end2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spnableInfo;
    }
}