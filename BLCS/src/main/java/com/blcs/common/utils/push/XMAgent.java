package com.blcs.common.utils.push;

import android.content.Context;
import android.os.Build;

import com.xiaomi.mipush.sdk.MiPushClient;

/**
 * 小米推送
 * @Author BLCS
 * @Time 2020/3/21 17:38
 */
public class XMAgent {
    /**
     * 判断是否是小米手机
     * @return
     */
    public static boolean isBrandXiaoMi() {
        return "xiaomi".equalsIgnoreCase(Build.BRAND) || "xiaomi".equalsIgnoreCase(Build.MANUFACTURER);
    }
    /**
     * 注册推送
     */
    public static void register(Context context,String AppId,String AppKey){
        if (!isBrandXiaoMi()) return;
        MiPushClient.registerPush(context, AppId, AppKey);
    }

    public static void unRegister(Context context){
        MiPushClient.unregisterPush(context);
    }
    /**
     * 启用推送
     */
    public static void enablePush(Context context){
        MiPushClient.enablePush(context);
    }
    /**
     * 禁用推送
     */
    public static void disablePush(Context context){
        MiPushClient.disablePush(context);
    }
    /**
     * 设置别名
     */
    public static void setAlias(Context context, String var1, String var2){
        MiPushClient.setAlias(context,var1,var2);
    }

    public static void unsetAlias(Context context, String var1, String var2){
        MiPushClient.unsetAlias(context,var1,var2);
    }

    /**
     * 设置用户账号
     */
    public static void setUserAccount(Context context, String var1, String var2){
        MiPushClient.setUserAccount(context,var1,var2);

    }
    public static void unsetUserAccount(Context context, String var1, String var2){
        MiPushClient.unsetUserAccount(context,var1,var2);
    }

    /**
     * 获取客户端标识
     */
    public static void getRegId(Context context){
        MiPushClient.getRegId(context);
    }


}
