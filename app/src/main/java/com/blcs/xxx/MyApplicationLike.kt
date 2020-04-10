package com.blcs.xxx

import android.app.Application
import android.content.Intent
import com.alibaba.android.arouter.launcher.ARouter
import com.blcs.common.Base.BaseApplicationLike
import com.blcs.common.utils.AppUtils
import com.blcs.pushlib.utils.HMSAgent
import com.blcs.pushlib.utils.XMAgent
import com.blcs.xxx.comment.Api_Constant
import com.tencent.rtmp.TXLiveBase
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

        /*腾讯直播 https://console.cloud.tencent.com/live/license*/
        val licenceURL =
            "http://license.vod2.myqcloud.com/license/v1/608ea1fcaa191bed06e69c1bcc26b9ef/TXLiveSDK.licence" // 获取到的 licence url
        val licenceKey = "70f51eea3eeb2e8d9f8bd14e0cca7043" // 获取到的 licence key
        TXLiveBase.getInstance().setLicence(application, licenceURL, licenceKey)
    }
}