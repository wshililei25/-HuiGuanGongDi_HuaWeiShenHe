package com.huiguangongdi.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gyf.immersionbar.ImmersionBar;
import com.huiguangongdi.R;
import com.huiguangongdi.adapter.MainAdapter;
import com.huiguangongdi.adapter.ProjectDrawAdapter;
import com.huiguangongdi.base.activity.BaseActivity;
import com.huiguangongdi.bean.ProjectBean;
import com.huiguangongdi.common.Extra;
import com.huiguangongdi.event.DrawerEvent;
import com.huiguangongdi.event.NewsNumEvent;
import com.huiguangongdi.fragment.MineFragment;
import com.huiguangongdi.fragment.NewsFragment;
import com.huiguangongdi.fragment.ProjectFragment;
import com.huiguangongdi.presenter.MainPresenter;
import com.huiguangongdi.utils.SPUtil;
import com.huiguangongdi.view.MainView;
import com.huiguangongdi.widget.CustomViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;


public class MainActivity extends BaseActivity<MainPresenter> implements MainView, View.OnClickListener, DrawerLayout.DrawerListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;
    @BindView(R.id.newsV)
    View mNews;
    @BindView(R.id.projectV)
    View mProjectV;
    @BindView(R.id.mineV)
    View mMineV;
    @BindView(R.id.backIv)
    ImageView mBackIv;
    @BindView(R.id.newsNumTv)
    TextView mNewsNumTv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ArrayList<Fragment> mFragments;
    private ProjectFragment mProjectFragment;
    private ProjectDrawAdapter mAdapter;
    public static boolean mIsProjectListNull = false;
    private static final int REQUEST_CODE_PERMISSION = 233;

    @Override
    protected int setContentViewID() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter getPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initView() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mFragments = new ArrayList<>();
        NewsFragment newsFragment = new NewsFragment();
        mProjectFragment = new ProjectFragment();
        MineFragment mineFragment = new MineFragment();
        mFragments.add(newsFragment);
        mFragments.add(mProjectFragment);
        mFragments.add(mineFragment);

        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager(), mFragments));
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(1);
        mProjectV.setSelected(true);
        initRv();
        initImmersionBar();
    }

    private void initImmersionBar() {
        ImmersionBar.with(this).navigationBarColor(R.color.white).init();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (REQUEST_CODE_PERMISSION == requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                if (permissions.length > 0) {
                    if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        showToast(getString(R.string.boxing_storage_permission_deny));
                    } else if (permissions[0].equals(Manifest.permission.CAMERA)) {
                        showToast(getString(R.string.boxing_camera_permission_deny));
                    }
                }
            }
        }
    }

    private void initRv() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ProjectDrawAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                ProjectBean bean = mAdapter.getData().get(position);
                SPUtil.put(MainActivity.this, Extra.SP_Project_Id, bean.getId());
                SPUtil.put(MainActivity.this, Extra.SP_Project_Name, bean.getName());
                mAdapter.notifyDataSetChanged();
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
                mProjectFragment.getManagerNum();
                SPUtil.put(MainActivity.this, Extra.SP_Create_Project_Success, false);
            }
        });
    }

    @Override
    protected void initListener() {
        mDrawerLayout.addDrawerListener(this);
        mNews.setOnClickListener(this);
        mProjectV.setOnClickListener(this);
        mMineV.setOnClickListener(this);
        mBackIv.setOnClickListener(this);
        viewPager.addOnPageChangeListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getProjectList();
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        showProgress();
        mPresenter.getProjectList();
    }

    @Override
    public void getProjectListSuccess(ArrayList<ProjectBean> list) {
        dismissProgress();
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (null == list || list.size() == 0) {
            mIsProjectListNull = true;
        } else {
            mIsProjectListNull = false;
            String projectName = (String) SPUtil.get(this, Extra.SP_Project_Name, "");
            if (TextUtils.isEmpty(projectName)) {
                SPUtil.put(this, Extra.SP_Project_Id, list.get(0).getId());
                SPUtil.put(this, Extra.SP_Project_Name, list.get(0).getName());
            }
            mAdapter.setNewInstance(list);

            if ((boolean) SPUtil.get(this, Extra.SP_Create_Project_Success, false)) {
                SPUtil.put(this, Extra.SP_Project_Id, list.get(0).getId());
                SPUtil.put(this, Extra.SP_Project_Name, list.get(0).getName());
                mProjectFragment.getManagerNum();
            }
        }
        mProjectFragment.setIsProjectListNull(mIsProjectListNull);
        mProjectFragment.getManagerNum();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.newsV:
                viewPager.setCurrentItem(0);
                tabSelected(mNews);
                break;
            case R.id.projectV:
                viewPager.setCurrentItem(1);
                tabSelected(mProjectV);
                break;
            case R.id.mineV:
                viewPager.setCurrentItem(2);
                tabSelected(mMineV);
                break;
            case R.id.backIv:
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
                break;
            default:
                break;
        }
    }

    private void tabSelected(View linearLayout) {
        mNews.setSelected(false);
        mProjectV.setSelected(false);
        mMineV.setSelected(false);
        linearLayout.setSelected(true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                tabSelected(mNews);
                ImmersionBar.with(this).keyboardEnable(false).statusBarDarkFont(true, 0.2f).navigationBarColor(R.color.white).init();
                break;
            case 1:
                tabSelected(mProjectV);
                ImmersionBar.with(this).keyboardEnable(false).statusBarDarkFont(false).navigationBarColor(R.color.white).init();
                break;
            case 2:
                tabSelected(mMineV);
                ImmersionBar.with(this).keyboardEnable(true).statusBarDarkFont(true).navigationBarColor(R.color.white).init();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDrawerEvent(DrawerEvent event) {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewsNumEvent(NewsNumEvent event) {
        if (event.getNum() > 0) {
            mNewsNumTv.setVisibility(View.VISIBLE);
            mNewsNumTv.setText(String.valueOf(event.getNum()));
            if (event.getNum() > 99) {
                mNewsNumTv.setText(getString(R.string.suspension_points));
            }
        } else {
            mNewsNumTv.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDrawerLayout.removeDrawerListener(this);
    }

    @Override
    public void showError(int flag, Throwable e) {

    }

    @Override
    public void complete(int flag) {

    }
}