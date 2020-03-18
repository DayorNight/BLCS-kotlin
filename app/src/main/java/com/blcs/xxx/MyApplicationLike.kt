package com.blcs.xxx

import android.app.Application
import android.content.Intent
import com.blcs.common.Base.BaseApplicationLike
import com.blcs.xxx.utils.Constants

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

    override fun setBuglyAppId() = Constants.BUGLY_APPID
    override fun onCreate() {
        super.onCreate()
    }
}