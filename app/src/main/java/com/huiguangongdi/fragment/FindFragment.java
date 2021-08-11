package com.huiguangongdi.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gyf.immersionbar.ImmersionBar;
import com.huiguangongdi.R;
import com.huiguangongdi.adapter.FindAdapter;
import com.huiguangongdi.adapter.ImageAdapter;
import com.huiguangongdi.base.activity.BaseFragment;
import com.huiguangongdi.bean.BannerBean;
import com.huiguangongdi.bean.FindBean;
import com.huiguangongdi.presenter.FindPresenter;
import com.huiguangongdi.view.FindView;
import com.huiguangongdi.widget.TitleBar;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FindFragment extends BaseFragment<FindPresenter> implements FindView {

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.banner)
    Banner mBanner;

    private FindAdapter mAdapter;

    @Override
    protected int setContentViewID() {
        return R.layout.fragment_find;
    }

    @Override
    protected FindPresenter getPresenter() {
        return new FindPresenter();
    }

    @Override
    protected void initView() {
        initRecyclerView();
        initImmersionBar();
        mBanner.addBannerLifecycleObserver(this); //添加生命周期观察者
    }
    private void initImmersionBar() {
        ImmersionBar.setTitleBar(this, mTitleBar);
    }
    private void initRecyclerView() {
        List<FindBean> list = new ArrayList<>();
        list.add(new FindBean(R.mipmap.talent_recruitment, getString(R.string.talent_recruitment)));
        list.add(new FindBean(R.mipmap.material_supply, getString(R.string.material_supplies)));
        list.add(new FindBean(R.mipmap.mechanical_equipment, getString(R.string.mechanical_equipment)));
        list.add(new FindBean(R.mipmap.labor_subcontract, getString(R.string.labor_subcontract)));
        list.add(new FindBean(R.mipmap.tool_information, getString(R.string.tool_information)));
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAdapter = new FindAdapter(getActivity());
        mAdapter.setNewInstance(list);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                showToast(getString(R.string.function_not_open));
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mPresenter.getBanner();
    }

    @Override
    public void getBannerSuccess(ArrayList<BannerBean> list) {
        mBanner.setAdapter(new ImageAdapter(getActivity(), list))
                .setIndicator(new CircleIndicator(getActivity()));
    }

    @Override
    public void showError(int flag, Throwable e) {

    }

    @Override
    public void complete(int flag) {

    }
}
