package com.huiguangongdi.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayUtil {
    /**
     * 获取屏幕密度（像素比例：0.75/1.0/1.5/2.0）
     */
    public static float getDensity(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        return dm.density;
    }
}
