package com.blcs.common.utils

import android.content.Context
import android.content.SharedPreferences
import com.blcs.common.Base.BaseApplication

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * SharedPreferences的封装
 */
object SPUtils {
    private val name = "APP_Config"
    private val prefs: SharedPreferences by lazy {
        BaseApplication.instance!!.applicationContext.getSharedPreferences(
            name,
            Context.MODE_PRIVATE
        )
    }

    /**
     * 获取存放数据
     * @return 值
     */
    @Suppress("UNCHECKED_CAST")
    fun getValue(key: String, default: Any): Any = with(prefs) {
        return when (default) {
            is Int -> getInt(key, default)
            is String -> getString(key, default)!!
            is Long -> getLong(key, default)
            is Float -> getFloat(key, default)
            is Boolean -> getBoolean(key, default)
            else -> throw IllegalArgumentException("SharedPreferences 类型错误")
        }
    }

    /**
     * 存放SharedPreferences
     * @param key 键
     * @param value 值
     */
    fun saveValue(key: String, value: Any) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(key, value)
            is Int -> putInt(key, value)
            is String -> putString(key, value)
            is Float -> putFloat(key, value)
            is Boolean -> putBoolean(key, value)
            else -> throw IllegalArgumentException("SharedPreferences 类型错误")
        }.apply()
    }

    /**
     * 清除
     */
    fun clear() {
        prefs.edit().clear().apply()
    }

    /**
     * 删除某Key的值
     */
    fun remove(key: String) {
        prefs.edit().remove(key).apply()
    }

}