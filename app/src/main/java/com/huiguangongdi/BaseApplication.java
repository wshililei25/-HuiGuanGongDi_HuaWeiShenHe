package com.huiguangongdi;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Handler;
import android.provider.Settings;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

public class BaseApplication extends Application {

    public static BaseApplication baseApplication;
    public static Context appContext;

    public static Resources mResources;//方便获取资源id
    public static String mDeviceToken;//设备唯一ID
    public static String mAppVersion;//app版本号 1.0.1

    public static Handler mHandler;
    public static Toast mToast;
    public static Toast mLongToast;

    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    private static final String WeChart_APP_ID = "wx77a92f5211160c6c";
    // IWXAPI 是第三方app和微信通信的openApi接口
    public static IWXAPI api;
    public static Tencent mTencent;
    public static String ShareUrl = "http://gongdi.shyouhan.com/index.php/index/member/register_html#/";

    @Override
    public void onCreate() {
        super.onCreate();

        baseApplication = this;
        appContext = baseApplication.getApplicationContext();

//        sPreferences = getSharedPreferences("Flag",MODE_PRIVATE);
        mResources = getResources();
        mDeviceToken = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        PackageManager manager = this.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            mAppVersion = info.versionName + "." + info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
//            MLog.e("获取应用版本号失败！");
        }

        mHandler = new Handler(getMainLooper());
        mToast = Toast.makeText(appContext, "", Toast.LENGTH_SHORT);
        mLongToast = Toast.makeText(appContext, "", Toast.LENGTH_LONG);

        regToWx();
        initQq();
    }

    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, WeChart_APP_ID, true);

        // 将应用的appId注册到微信
        api.registerApp(WeChart_APP_ID);

        //建议动态监听微信启动广播进行注册到微信
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // 将该app注册到微信
                api.registerApp(WeChart_APP_ID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));

    }

    private void initQq() {
        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
// 其中APP_ID是分配给第三方应用的appid，类型为String。
// 其中Authorities为 Manifest文件中注册FileProvider时设置的authorities属性值
        mTencent = Tencent.createInstance(AppConstants.QQ_APP_ID, this.getApplicationContext(), AppConstants.APP_AUTHORITIES);
// 1.4版本:此处需新增参数，传入应用程序的全局context，可通过activity的getApplicationContext方法获取
    }

    /**
     * handler.post() 这里为了try-catch
     */
    public static void post(Runnable r) {
        post(r, 0);
    }

    /**
     * handler.post() 这里为了try-catch
     */
    public static void post(Runnable r, int time) {
        try {
            mHandler.postDelayed(r, time);
        } catch (Exception e) {
            e.printStackTrace();
//            MLog.e(baseApplication.getClass().getSimpleName()+":post-"+e);
        }
    }

    /**
     * 显示 Toast 放这里是为了在任何地方都可用
     *
     * @param msg 可以为空
     */
    public static void showToast(final Object msg) {
        post(new Runnable() {
            @Override
            public void run() {
                mToast.setText(msg + "");
                mToast.show();
            }
        });
    }

    public static void showLongToast(final Object msg) {
        post(new Runnable() {
            @Override
            public void run() {
                mLongToast.setText(msg + "");
                mLongToast.show();
            }
        });
    }
}
