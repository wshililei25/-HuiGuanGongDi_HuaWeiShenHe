package com.huiguangongdi.base.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.huiguangongdi.base.ActivityManager;
import com.huiguangongdi.base.presenter.BaseContract;
import com.huiguangongdi.base.ui.BaseUI;
import com.huiguangongdi.base.ui.IBaseUI;

import java.util.ArrayList;

import butterknife.ButterKnife;

public abstract class BaseActivity<T extends BaseContract.BasePresenter> extends AppCompatActivity implements IBaseUI {

    private BaseUI baseUI;
    protected ArrayList<Fragment> frags = new ArrayList<>();
    protected FragmentManager fragmentManger = getSupportFragmentManager();
    protected FragmentTransaction fragmentTransaction;
    protected Fragment currentFrg = null;
    //    private BackHandledFragment mBackHandedFragment;
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);
        //init something
        baseUI = new BaseUI(this);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
        //requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题栏 如果activity继承AppCompatActivity就无效
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题栏
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//取消横屏
        //设置状态栏颜色
       /* if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(0x00, 0x00, 0x00));
        }
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }*/
        if (setContentViewID() != 0) {
            setContentView(setContentViewID());
        }
        ButterKnife.bind(this);
        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }

        initView();
        initListener();
        initData();
    }

    protected abstract int setContentViewID();

    protected abstract T getPresenter();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();


    protected void loadFragment(int position) {
       /* Fragment f = frags.get(position);
        fragmentTransaction = fragmentManger.beginTransaction();
        if (currentFrg != null) {
            fragmentTransaction.hide(currentFrg);
        }
        if (!f.isAdded()) {
            fragmentTransaction.add(R.id.frameLayout, f);
        } else {
            fragmentTransaction.show(f);
        }
        currentFrg = f;
        fragmentTransaction.commit();*/
    }

    protected void loadFragment(Fragment f) {
       /* fragmentTransaction = fragmentManger.beginTransaction();
        if (currentFrg != null) {
            fragmentTransaction.hide(currentFrg);
        }
        if (!f.isAdded()) {
            fragmentTransaction.add(R.id.frameLayout, f);
        } else {
            fragmentTransaction.show(f);
        }
        currentFrg = f;
        fragmentTransaction.commit();*/
    }

    protected void removeFragment(Fragment f) {
        fragmentTransaction.remove(f);//remove之后这个fragment事例会被销毁的，所以想重新显示需要new一个对象再调add方法
    }

  /*  @Override
    public void onBackPressed() {//如果activity加载的是fragment写了这个方法就可以在fragment里面监听到返回键 事件的传递顺序是 先传递到父activity再传递到子fragment
        if (mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()) {
            if (fragmentManger.getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                fragmentManger.popBackStack();
            }
        }
    }*/

    /**
     * this.getCurrentFocus().getWindowToken() 有时拿的windowToken为空，没有获取到焦点，所以会抛出异常
     *
     * @param view
     */
    public void hideSoftKeyBoard(View view) {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(view.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

/*
    @Override
    public void setSelectedFragment(BackHandledFragment selectedFragment) {
        this.mBackHandedFragment = selectedFragment;
    }
*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.cancelAll();
            mPresenter.detachView();
        }
        System.gc();
        ActivityManager.removeActivity(this);
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BaseUI.REQUEST_CODE && resultCode == BaseUI.RESULT_CODE) {
            BaseApplication.post(new Runnable() {
                @Override
                public void run() {
                    MyUtils.backgroundAlpha(com.le.base.BaseActivity.this, (float) 1.0);
                }
            }, 200);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/

    /**
     * 首先软键盘(可用于Activity，Fragment)
     */
    public static void showSoftKeyboard(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏软键盘(可用于Activity，Fragment)
     */
    public static void hideSoftKeyboard(Context context, View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void toActivity(Class<?> clas) {
        baseUI.toActivity(clas);
    }

    @Override
    public void showToast(String msg) {
        baseUI.showToast(msg);
    }

    @Override
    public void showLongToast(String msg) {
        baseUI.showLongToast(msg);
    }

    @Override
    public void showProgress() {
        baseUI.showProgress();
    }

    @Override
    public void showProgress(String msg) {
        baseUI.showProgress(msg);
    }

    @Override
    public void showProgress(boolean cancel) {
        baseUI.showProgress(cancel);
    }

    @Override
    public void showProgress(String msg, boolean cancel) {
        baseUI.showProgress(msg, cancel);
    }

    @Override
    public void dismissProgress() {
        baseUI.dismissProgress();
    }
}
