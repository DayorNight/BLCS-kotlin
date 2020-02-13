package com.blcs.common.utils

import android.util.Log

import com.blcs.common.BuildConfig


/**
 * Created by lwb on 2017/12/25.
 * 日志封装
 */

object L {
    internal lateinit var className: String//类名
    internal lateinit var methodName: String//方法名
    internal var lineNumber: Int = 0//行数
    /**
     * 判断是否可以调试
     * @return
     */
    val isDebuggable: Boolean
        get() = BuildConfig.DEBUG

    private fun createLog(log: String): String {
        val buffer = StringBuffer()
        buffer.append("==").append(methodName)
        buffer.append("(").append(className).append(":").append(lineNumber).append(")==:")
        buffer.append(log)
        return buffer.toString()
    }

    /**
     * 获取文件名、方法名、所在行数
     * @param sElements
     */
    private fun getMethodNames(sElements: Array<StackTraceElement>) {
        className = sElements[1].fileName
        methodName = sElements[1].methodName
        lineNumber = sElements[1].lineNumber
    }

    fun e(message: String) {
        if (!isDebuggable)
            return
        getMethodNames(Throwable().stackTrace)
        Log.e(className, createLog(message))
    }

    fun i(message: String) {
        if (!isDebuggable)
            return
        getMethodNames(Throwable().stackTrace)
        Log.i(className, createLog(message))
    }

    fun d(message: String) {
        if (!isDebuggable)
            return
        getMethodNames(Throwable().stackTrace)
        Log.d(className, createLog(message))
    }

    fun v(message: String) {
        if (!isDebuggable)
            return
        getMethodNames(Throwable().stackTrace)
        Log.v(className, createLog(message))
    }

    fun w(message: String) {
        if (!isDebuggable)
            return
        getMethodNames(Throwable().stackTrace)
        Log.w(className, createLog(message))
    }
}