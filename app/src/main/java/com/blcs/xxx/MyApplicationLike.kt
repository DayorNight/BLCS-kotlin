package com.blcs.xxx

import android.app.Application
import android.content.Intent
import com.alibaba.android.arouter.launcher.ARouter
import com.blcs.common.Base.BaseApplicationLike
import com.blcs.common.utils.AppUtils
import com.blcs.pushlib.utils.HMSAgent
import com.blcs.pushlib.utils.XMAgent
import com.blcs.xxx.comment.Api_Constant
import me.jessyan.autosize.utils.AutoSizeLog.isDebug

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