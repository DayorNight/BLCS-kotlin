package com.blcs.common.utils.crash

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build

import com.blcs.common.BuildConfig
import com.blcs.common.manager.AppManager
import com.blcs.common.utils.AppUtils
import com.blcs.common.utils.L
import com.blcs.common.utils.SPUtils

import java.io.File
import java.io.FilenameFilter
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * 全局异常捕获类
 * Created by lwb on 2018/1/3.
 */
class CrashHandler private constructor(context: Context) : Thread.UncaughtExceptionHandler {
    /**
     * 系统默认UncaughtExceptionHandler
     */
    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null
    /**
     * context
     */
    private var mContext: Context? = null

    /**
     * 存储异常和参数信息
     */
    private var bugInfomation: StringBuilder? = null
    /**
     * 格式化时间
     */
    private val format = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA)

    private val TAG = this.javaClass.simpleName


    init {
        init(context)
    }

    fun init(context: Context) {
        mContext = context
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        //设置该CrashHandler为系统默认的
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    /**
     * uncaughtException 回调函数
     */
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        //收集设备参数信息
        collectDeviceInfo(mContext!!)
        //添加自定义信息
        addCustomInfo()
        //处理错误日志
        saveCrashInfo2File(ex)
        mDefaultHandler!!.uncaughtException(thread, ex)
    }

    /**
     * 发送错误报告给服务器
     * @param ctx
     */
    private fun sendCrashReportsToServer(ctx: Context?) {
        val infomation = SPUtils.getValue( SP_BUG, "") as String?
        //上传到自己的服务器
        //失败保存到Sp  下次启动的时候判断是否有值 进行提交
        if (infomation!!.length > 0) {
            //存储错误报告
            SPUtils.saveValue( SP_BUG, bugInfomation!!.toString())
        }
        L.e("未做上传服务器处理")
        AppManager.appManager.AppExit()
    }

    /**
     * 获取错误报告文件名
     *
     * @param ctx
     * @return
     */
    private fun getCrashReportFiles(ctx: Context): Array<String>? {
        val filesDir = ctx.filesDir
        val filter = FilenameFilter { dir, name -> name.endsWith(CRASH_REPORTER_EXTENSION) }
        return filesDir.list(filter)
    }

    private fun postReport(file: File) {
        // TODO 发送错误报告到服务器
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    fun collectDeviceInfo(ctx: Context) {
        bugInfomation = StringBuilder()
        //获取versionName,versionCode
        try {
            val pm = ctx.packageManager
            val pi = pm.getPackageInfo(ctx.packageName, PackageManager.GET_ACTIVITIES)
            if (pi != null) {
                val versionName = if (pi.versionName == null) "null" else pi.versionName
                val versionCode = pi.versionCode.toString() + ""
                bugInfomation!!.append("应用名：").append(AppUtils.getAppName(ctx)).append("\n")
                bugInfomation!!.append("应用版本：").append(versionName).append("\n")
                bugInfomation!!.append("应用版本号：").append(versionCode).append("\n")
                bugInfomation!!.append("型号：").append(Build.MODEL).append("\n")
                bugInfomation!!.append("设备名：").append(Build.DEVICE).append("\n")
                bugInfomation!!.append("安卓版本：").append(Build.VERSION.RELEASE).append("\n")
                bugInfomation!!.append("生产厂商：").append(Build.MANUFACTURER).append("\n")
                bugInfomation!!.append("Android SDK版本：").append(Build.VERSION.SDK_INT).append("\n")
                bugInfomation!!.append("硬件名：").append(Build.HARDWARE).append("\n")
                bugInfomation!!.append("———————————————").append("\n")
            }
        } catch (e: PackageManager.NameNotFoundException) {
            L.e("an error occured when collect package info$e")
        }

    }

    /**
     * 添加自定义参数
     */
    private fun addCustomInfo() {

    }

    /**
     * Handle Exception
     */
    private fun saveCrashInfo2File(ex: Throwable) {
        val writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        printWriter.close()
        var stackTraceString = writer.toString()
        if (stackTraceString.length > MAX_STACK_TRACE_SIZE) {
            val disclaimer = " [stack trace too large]"
            stackTraceString =
                stackTraceString.substring(0, MAX_STACK_TRACE_SIZE - disclaimer.length) + disclaimer
        }
        //错误日志
        bugInfomation!!.append("BUG：").append(stackTraceString)
        if (BuildConfig.DEBUG) {
            val intent = Intent(mContext, DefaultErrorActivity::class.java)
            intent.putExtra(SP_BUG, bugInfomation!!.toString())
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            mContext!!.startActivity(intent)
        } else {
            //发送错误报告到服务器
            sendCrashReportsToServer(mContext)
        }
    }

    companion object {
        private val MAX_STACK_TRACE_SIZE = 131071 //128 KB - 1
        val SP_BUG = "BUG"
        /**
         * 错误报告文件的扩展名
         */
        private val CRASH_REPORTER_EXTENSION = ".cr"

        private var mInstance: CrashHandler? = null

        /**
         * 获取CrashHandler实例
         */
        @Synchronized
        fun getInstance(context: Context): CrashHandler {
            if (null == mInstance) {
                mInstance = CrashHandler(context)
            }
            return mInstance as CrashHandler
        }
    }
}