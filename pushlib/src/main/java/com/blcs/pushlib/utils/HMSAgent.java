package com.blcs.pushlib.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.blcs.pushlib.ExecutorsMgr;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.aaid.HmsInstanceId;

/**
 * 华为推送 https://developer.huawei.com/consumer/cn/codelab/HMSPushKit/index.html#6
 * @Author BLCS
 * @Time 2020/3/21 9:48
 */
//    HMSAgent.getInstance().getToken(activity).addOnTokenResultListener {
//         L.e("====="+it)
//    }//调用

public class HMSAgent {
    private static final String TAG = "HMSAgent";
    private static HMSAgent hmsAgent;
    public static HMSAgent getInstance(){
        if (hmsAgent ==null){
            synchronized (HMSAgent.class){
                if (hmsAgent==null){
                    hmsAgent = new HMSAgent();
                }
            }
        }
        return hmsAgent;
    }
    /**
     * 是否为华为手机
     * @return
     */
    public static boolean isBrandHuawei() {
        return "huawei".equalsIgnoreCase(Build.BRAND) || "huawei".equalsIgnoreCase(Build.MANUFACTURER);
    }
    /**
     * 获取token
     */
    public  HMSAgent getToken(Context context) {
        if (!isBrandHuawei()) return this;
        ExecutorsMgr.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String appId = AGConnectServicesConfig.fromContext(context).getString("client/app_id");
                    String pushtoken = HmsInstanceId.getInstance(context).getToken(appId, "HCM");
                    if(!TextUtils.isEmpty(pushtoken)) {
                        PushTokenMgr.getInstance().setPushToken(context,pushtoken);
                        if (listener!=null) listener.getToken(pushtoken);
                    }
                } catch (Exception e) {
                    Log.i(TAG, "run: "+e);
                }
            }
        });
        return this;
    }
    public OnTokenResultListener listener;
    public interface OnTokenResultListener{
        void getToken(String token);
    }

    public void addOnTokenResultListener(OnTokenResultListener onTokenResultListener){
        listener = onTokenResultListener;
    }

}
