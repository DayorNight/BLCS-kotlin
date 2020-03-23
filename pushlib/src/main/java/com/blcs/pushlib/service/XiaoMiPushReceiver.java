package com.blcs.pushlib.service;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


import com.blcs.pushlib.utils.PushTokenMgr;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.List;

/**
 * 处理小米推送过来的消息 https://dev.mi.com/console/doc/detail?pId=41
 * @Author BLCS
 * @Time 2020/3/21 17:27
 */
public class XiaoMiPushReceiver extends PushMessageReceiver {
    private static final String TAG = "XiaoMiPushReceiver";
    private String mRegId;
    private long mResultCode = -1;
    private String mReason;
    private String mCommand;
    private String mMessage;
    private String mTopic;
    private String mAlias;
    private String mUserAccount;
    private String mStartTime;
    private String mEndTime;

    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        //接收服务器推送的透传消息，消息封装在 MiPushMessage类中。
        mMessage = message.getContent();

        Log.i(TAG, "onReceivePassThroughMessage: "+ mMessage);
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        } else if (!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount = message.getUserAccount();
        }
    }

    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        //接收服务器推送的通知消息，用户点击后触发，消息封装在 MiPushMessage类中。
        mMessage = message.getContent();
        Log.i(TAG, "onNotificationMessageClicked: "+mMessage );
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        } else if (!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount = message.getUserAccount();
        }
    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        //接收服务器推送的通知消息，消息到达客户端时触发，还可以接受应用在前台时不弹出通知的通知消息，消息封装在 MiPushMessage类中。在MIUI上，只有应用处于启动状态，或者自启动白名单中，才可以通过此方法接受到该消息。
        mMessage = message.getContent();
        Log.i(TAG, "onNotificationMessageArrived: "+mMessage );
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        } else if (!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount = message.getUserAccount();
        }
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        //获取给服务器发送命令的结果，结果封装在MiPushCommandMessage类中。
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        Log.i(TAG, "onCommandResult: "+cmdArg1 );
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mStartTime = cmdArg1;
                mEndTime = cmdArg2;
            }
        }
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        //获取给服务器发送注册命令的结果，结果封装在MiPushCommandMessage类中。
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
                Log.i(TAG, "onReceiveRegisterResult: "+cmdArg1);
                PushTokenMgr.getInstance().setPushToken(context,mRegId);
            }
        }
    }

    @Override
    public void onRequirePermissions(Context context, String[] strings) {
        super.onRequirePermissions(context, strings);
        //当所需要的权限未获取到的时候会回调该接口
    }
}
