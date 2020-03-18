package com.blcs.common.Base

import android.annotation.TargetApi
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Process

import androidx.multidex.MultiDex
import com.blcs.common.BuildConfig
import com.blcs.common.utils.AppUtils
import com.blcs.common.utils.crash.CrashHandler

import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.tinker.entry.DefaultApplicationLike
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeConfig

/**
 * @Author BLCS
 * @Time 2020/3/18 11:11
 */
abstract class BaseApplicationLike(
    application: Application, tinkerFlags: Int,
    tinkerLoadVerifyFlag: Boolean, applicationStartElapsedTime: Long,
    applicationStartMillisTime: Long, tinkerResultIntent: Intent
) : DefaultApplicationLike(
    application,
    tinkerFlags,
    tinkerLoadVerifyFlag,
    applicationStartElapsedTime,
    applicationStartMillisTime,
    tinkerResultIntent
) {

    abstract fun setBuglyAppId():String
    override fun onCreate() {
        super.onCreate()
        screenAdapter()
        //全局异常处理
        CrashHandler.getInstance(application)
        //bugly
        initBugly()
    }
    /**
     * 屏幕适配初始化
     */
    fun screenAdapter(){
        AutoSize.initCompatMultiProcess(application)
        AutoSizeConfig.getInstance()
            .setCustomFragment(true)
            .setLog(false)
            .setUseDeviceSize(true)
        //由于某些原因, 屏幕旋转后 Fragment 的重建, 会导致框架对 Fragment 的自定义适配参数失去效果
        //所以如果您的 Fragment 允许屏幕旋转, 则请在 onCreateView 手动调用一次 AutoSize.autoConvertDensity()
        //如果您的 Fragment 不允许屏幕旋转, 则可以将下面调用 AutoSize.autoConvertDensity() 的代码删除掉
        //AutoSize.autoConvertDensity(getActivity()!!, 1080f, true)
    }
    /**
     * 异常捕获
     */
    private fun initBugly() {
        // 获取当前进程名
        val processName = AppUtils.getProcessName(Process.myPid())
        // 设置是否为上报进程
        val strategy = CrashReport.UserStrategy(application)
        strategy.isUploadProcess = processName == null || processName == application.packageName
        // 初始化Bugly
        Bugly.init(application, setBuglyAppId(), BuildConfig.DEBUG,strategy)
//       1. 设置自定义Map参数
//        自定义Map参数可以保存发生Crash时的一些自定义的环境信息。在发生Crash时会随着异常信息一起上报并在页面展示。
//        CrashReport.putUserData(applicationContext, "userkey", "uservalue");

//      2.  在开发测试阶段，可以在初始化Bugly之前通过以下接口把调试设备设置成“开发设备”。
        CrashReport.setIsDevelopmentDevice(application, BuildConfig.DEBUG)

//       3. Javascript的异常捕获功能
//        CrashReport.setJavascriptMonitor(WebView webView, boolean autoInject)

//        4.该用户本次启动后的异常日志用户ID都将是9527
//        CrashReport.setUserId("9527");
    }
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    override fun onBaseContextAttached(base: Context?) {
        super.onBaseContextAttached(base)
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base)

        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this)
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    fun registerActivityLifecycleCallback(callbacks: Application.ActivityLifecycleCallbacks) {
        application.registerActivityLifecycleCallbacks(callbacks)
    }

}
