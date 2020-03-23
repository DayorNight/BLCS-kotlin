package com.blcs.pushlib.service;

import android.util.Log;

import com.blcs.pushlib.utils.HMSAgent;
import com.blcs.pushlib.utils.PushTokenMgr;
import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;

/**
 * @Author BLCS
 * @Time 2020/3/21 10:25
 */
public class HuaweiPushService extends HmsMessageService {
    private static final String TAG = "HuaweiPushService";
        @Override
        public void onNewToken(String token) {
            super.onNewToken(token);
            Log.i(TAG, "onNewToken: "+token);
            PushTokenMgr.getInstance().setPushToken(getApplicationContext(),token);
            HMSAgent.OnTokenResultListener listener = HMSAgent.getInstance().listener;
            if (listener!=null) listener.getToken(token);
      }

    @Override
    public void onTokenError(Exception e) {
        Log.i(TAG, "onTokenError: "+e.getMessage());
        super.onTokenError(e);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.i(TAG, "onMessageReceived: "+remoteMessage.getData());
        super.onMessageReceived(remoteMessage);
    }

    @Override
    public void onMessageSent(String s) {
        Log.i(TAG, "onMessageSent: "+ s);
        super.onMessageSent(s);
    }
}
