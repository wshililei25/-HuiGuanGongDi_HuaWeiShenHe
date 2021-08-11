package com.huiguangongdi.activity;

import android.view.View;
import android.widget.ImageView;

import com.huiguangongdi.R;
import com.huiguangongdi.base.activity.BaseActivity;
import com.huiguangongdi.presenter.MinePresenter;
import com.huiguangongdi.widget.TitleBar;

import butterknife.BindView;

public class LanguageActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.chineseV)
    View mChineseV;
    @BindView(R.id.englishV)
    View mEnglishV;
    @BindView(R.id.chineseIv)
    ImageView mChineseIv;
    @BindView(R.id.englishIv)
    ImageView mEnglishIv;

    @Override
    protected int setContentViewID() {
        return R.layout.activity_language;
    }

    @Override
    protected MinePresenter getPresenter() {
        return new MinePresenter();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        mTitleBar.getBackIv().setOnClickListener(view -> finish());
        mChineseV.setOnClickListener(this);
        mEnglishV.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chineseV:
                mChineseIv.setVisibility(View.VISIBLE);
                mEnglishIv.setVisibility(View.INVISIBLE);
                break;
            case R.id.englishV:
                mChineseIv.setVisibility(View.INVISIBLE);
                mEnglishIv.setVisibility(View.VISIBLE);
                break;
        }
    }

}
