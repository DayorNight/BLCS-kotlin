package com.blcs.common.utils;
import android.app.Activity;
import android.view.WindowManager;

/**
 * @Author BLCS
 * @Time 2020/4/10 17:32
 */
public class StatusBarUtils {
    /**
     * 适配刘海  沉浸式状态栏
     * @param context
     */
    public static void setTransparent(Activity context){
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT;
        context.getWindow().setAttributes(lp);
    }
}
