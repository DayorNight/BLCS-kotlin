package com.blcs.common.service;

import com.blcs.common.utils.L;
import com.blcs.common.utils.push.HMSAgent;
import com.blcs.common.utils.push.PushTokenMgr;
import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;

/**
 * @Author BLCS
 * @Time 2020/3/21 10:25
 */
public class HuaweiPushService extends HmsMessageService {
        @Override
        public void onNewToken(String token) {
            super.onNewToken(token);
            L.e("============"+token);
            PushTokenMgr.getInstance().setPushToken(token);
            HMSAgent.OnTokenResultListener listener = HMSAgent.getInstance().listener;
            if (listener!=null) listener.getToken(token);
      }

    @Override
    public void onTokenError(Exception e) {
        L.e("========"+e.getMessage());
        super.onTokenError(e);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        L.e("========"+remoteMessage.getData());
        super.onMessageReceived(remoteMessage);
    }

    @Override
    public void onMessageSent(String s) {
        L.e("========"+s);
        super.onMessageSent(s);
    }
}
