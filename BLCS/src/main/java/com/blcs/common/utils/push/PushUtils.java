package com.blcs.common.utils.push;

import android.os.Build;

/**
 * 推送工具
 * @Author BLCS
 * @Time 2020/3/19 9:47
 */
public class PushUtils {



    public static boolean isBrandMeizu() {
        return "meizu".equalsIgnoreCase(Build.BRAND) || "meizu".equalsIgnoreCase(Build.MANUFACTURER) || "22c4185e".equalsIgnoreCase(Build.BRAND);
    }

    public static boolean isBrandOppo() {
        return "oppo".equalsIgnoreCase(Build.BRAND) || "oppo".equalsIgnoreCase(Build.MANUFACTURER);
    }

    public static boolean isBrandVivo() {
        return "vivo".equalsIgnoreCase(Build.BRAND) || "vivo".equalsIgnoreCase(Build.MANUFACTURER);
    }


    /****** 华为离线推送参数start ******/
    // 在腾讯云控制台上传第三方推送证书后分配的证书ID
    public static final long HW_PUSH_BUZID = 0;
    // 华为开发者联盟给应用分配的应用APPID
    public static final String HW_PUSH_APPID = ""; // 见清单文件
    /****** 华为离线推送参数end ******/

    /****** 小米离线推送参数start ******/
    // 在腾讯云控制台上传第三方推送证书后分配的证书ID
    public static final long XM_PUSH_BUZID = 0;
    // 小米开放平台分配的应用APPID及APPKEY
    public static final String XM_PUSH_APPID = "";
    public static final String XM_PUSH_APPKEY = "";
    /****** 小米离线推送参数end ******/

    /****** 魅族离线推送参数start ******/
    // 在腾讯云控制台上传第三方推送证书后分配的证书ID
    public static final long MZ_PUSH_BUZID = 0;
    // 魅族开放平台分配的应用APPID及APPKEY
    public static final String MZ_PUSH_APPID = "";
    public static final String MZ_PUSH_APPKEY = "";
    /****** 魅族离线推送参数end ******/

    /****** vivo离线推送参数start ******/
    // 在腾讯云控制台上传第三方推送证书后分配的证书ID
    public static final long VIVO_PUSH_BUZID = 0;
    // vivo开放平台分配的应用APPID及APPKEY
    public static final String VIVO_PUSH_APPID = ""; // 见清单文件
    public static final String VIVO_PUSH_APPKEY = ""; // 见清单文件
    /****** vivo离线推送参数end ******/

    /****** google离线推送参数start ******/
    // 在腾讯云控制台上传第三方推送证书后分配的证书ID
    public static final long GOOGLE_FCM_PUSH_BUZID = 0;
    /****** google离线推送参数end ******/
}
