package com.huiguangongdi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.huiguangongdi.R;
import com.huiguangongdi.activity.ProjectNewsActivity;
import com.huiguangongdi.activity.SystemNewsActivity;
import com.huiguangongdi.activity.ToDoActivity;
import com.huiguangongdi.base.activity.BaseFragment;
import com.huiguangongdi.bean.NewsNumBean;
import com.huiguangongdi.event.NewsNumEvent;
import com.huiguangongdi.presenter.NewsPresenter;
import com.huiguangongdi.view.NewsView;
import com.huiguangongdi.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class NewsFragment extends BaseFragment<NewsPresenter> implements NewsView, View.OnClickListener {

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.systemNewsTv)
    TextView mSystemNewsTv;
    @BindView(R.id.projectNewsTv)
    TextView mProjectNewsTv;
    @BindView(R.id.toDoNewsTv)
    TextView mToDoNewsTv;
    @BindView(R.id.projectNewsV)
    View mProjectNewsV;
    @BindView(R.id.systemNewsV)
    View mSystemNewsV;
    @BindView(R.id.personalNewsV)
    View mPersonalNewsV;
    @BindView(R.id.toDoNewsV)
    View mToDoNewsV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, null);
    }

    @Override
    protected int setContentViewID() {
        return R.layout.fragment_news;
    }

    @Override
    protected NewsPresenter getPresenter() {
        return new NewsPresenter();
    }

    @Override
    protected void initView() {
        initImmersionBar();
    }

    private void initImmersionBar() {
        ImmersionBar.setTitleBar(this, mTitleBar);
    }

    @Override
    protected void initListener() {
        mProjectNewsV.setOnClickListener(this::onClick);
        mSystemNewsV.setOnClickListener(this::onClick);
        mPersonalNewsV.setOnClickListener(this::onClick);
        mToDoNewsV.setOnClickListener(this::onClick);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getNewNum();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.projectNewsV:
                startActivity(new Intent(getActivity(), ProjectNewsActivity.class));
                break;
            case R.id.systemNewsV:
                startActivity(new Intent(getActivity(), SystemNewsActivity.class));
                break;
            case R.id.toDoNewsV:
                startActivity(new Intent(getActivity(), ToDoActivity.class));
                break;
            case R.id.personalNewsV:
                showToast(getString(R.string.function_not_open));
                break;
        }
    }

    /**
     * 获取消息数量成功
     *
     * @param bean
     */
    @Override
    public void getNewsNumSuccess(NewsNumBean bean) {
        if (null == bean || null == bean.getSystem_count() || null == bean.getProject_count() || null == bean.getNeed_count())
            return;
        if (Integer.valueOf(bean.getSystem_count()) > 0) {
            mSystemNewsTv.setVisibility(View.VISIBLE);
            mSystemNewsTv.setText(bean.getSystem_count());
            if (Integer.valueOf(bean.getSystem_count()) > 99) {
                mSystemNewsTv.setText(getString(R.string.suspension_points));
            }
        } else {
            mSystemNewsTv.setVisibility(View.GONE);
        }
        if (Integer.valueOf(bean.getProject_count()) > 0) {
            mProjectNewsTv.setVisibility(View.VISIBLE);
            mProjectNewsTv.setText(bean.getProject_count());
            if (Integer.valueOf(bean.getProject_count()) > 99) {
                mProjectNewsTv.setText(getString(R.string.suspension_points));
            }
        } else {
            mProjectNewsTv.setVisibility(View.GONE);
        }
        if (Integer.valueOf(bean.getNeed_count()) > 0) {
            mToDoNewsTv.setVisibility(View.VISIBLE);
            mToDoNewsTv.setText(bean.getNeed_count());
            if (Integer.valueOf(bean.getNeed_count()) > 99) {
                mToDoNewsTv.setText(getString(R.string.suspension_points));
            }
        } else {
            mToDoNewsTv.setVisibility(View.GONE);
        }

        int num = Integer.valueOf(bean.getSystem_count())
                + Integer.valueOf(bean.getProject_count())
                + Integer.valueOf(bean.getNeed_count());
        EventBus.getDefault().post(new NewsNumEvent(num));
    }

    @Override
    public void showError(int flag, Throwable e) {

    }

    @Override
    public void complete(int flag) {

    }
}
