package com.blcs.xxx

import android.app.Application
import android.content.Intent
import com.blcs.common.Base.BaseApplicationLike
import com.blcs.common.utils.AppUtils
import com.blcs.common.utils.L
import com.blcs.common.utils.push.HMSAgent
import com.blcs.common.utils.push.XMAgent
import com.blcs.xxx.comment.Api_Constant

/**
 * @Author BLCS
 * @Time 2020/3/18 10:59
 */
class MyApplicationLike(
    application: Application,
    tinkerFlags: Int,
    tinkerLoadVerifyFlag: Boolean,
    applicationStartElapsedTime: Long,
    applicationStartMillisTime: Long,
    tinkerResultIntent: Intent
) : BaseApplicationLike(
    application,
    tinkerFlags,
    tinkerLoadVerifyFlag,
    applicationStartElapsedTime,
    applicationStartMillisTime,
    tinkerResultIntent
) {

    override fun setBuglyAppId() = Api_Constant.BUGLY_APPID
    override fun onCreate() {
        super.onCreate()

        if (AppUtils.isMainProcess(application)) {
            HMSAgent.getInstance().getToken(application)
            XMAgent.register(application,Api_Constant.XM_PUSH_APPID,Api_Constant.XM_PUSH_APPKEY)
        }
    }
}