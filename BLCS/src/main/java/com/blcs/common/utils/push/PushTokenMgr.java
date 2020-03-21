package com.blcs.common.utils.push;

import android.provider.SyncStateContract;
import android.text.TextUtils;

import com.blcs.common.utils.Constants;
import com.blcs.common.utils.L;
import com.blcs.common.utils.SPUtils;


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

    public String getPushToken() {
        if (TextUtils.isEmpty(mThirdPushToken)){
            return (String) SPUtils.INSTANCE.getValue(Constants.SP_PUSH, "");
        }else{
            return mThirdPushToken;
        }
    }

    public void setPushToken(String mThirdPushToken) {
        this.mThirdPushToken = mThirdPushToken;
        SPUtils.INSTANCE.saveValue(Constants.SP_PUSH,mThirdPushToken);
    }

    private static class ThirdPushTokenHolder {
        private static final PushTokenMgr instance = new PushTokenMgr();
    }

}
