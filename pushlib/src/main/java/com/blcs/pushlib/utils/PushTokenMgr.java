package com.blcs.pushlib.utils;

import android.content.Context;
import android.text.TextUtils;

/**
 * 管理推送 token
 * @Author BLCS
 * @Time 2020/3/21 15:30
 */
public class PushTokenMgr {

    private String mThirdPushToken;

    public static PushTokenMgr getInstance() {
        return ThirdPushTokenHolder.instance;
    }

    public String getPushToken(Context context) {
        if (TextUtils.isEmpty(mThirdPushToken)){
            return (String) SPUtils.get(context,SPUtils.SP_PUSH, "");
        }else{
            return mThirdPushToken;
        }
    }

    public void setPushToken(Context context,String mThirdPushToken) {
        this.mThirdPushToken = mThirdPushToken;
        SPUtils.put(context,SPUtils.SP_PUSH,mThirdPushToken);
    }

    private static class ThirdPushTokenHolder {
        private static final PushTokenMgr instance = new PushTokenMgr();
    }

}
