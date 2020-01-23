package com.blcs.common.Base


import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.onAdaptListener
import me.jessyan.autosize.utils.AutoSizeLog
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
abstract class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        screenAdapter()
    }

    /**
     * 屏幕适配初始化
     */
    fun screenAdapter(){
        AutoSize.initCompatMultiProcess(this)
        AutoSizeConfig.getInstance()
            .setCustomFragment(true)
            .setLog(false)
            .setUseDeviceSize(true)

        //由于某些原因, 屏幕旋转后 Fragment 的重建, 会导致框架对 Fragment 的自定义适配参数失去效果
        //所以如果您的 Fragment 允许屏幕旋转, 则请在 onCreateView 手动调用一次 AutoSize.autoConvertDensity()
        //如果您的 Fragment 不允许屏幕旋转, 则可以将下面调用 AutoSize.autoConvertDensity() 的代码删除掉
        //AutoSize.autoConvertDensity(getActivity()!!, 1080f, true)
    }
}
