package com.blcs.common.utils.spreadFun

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * @Activity 的扩展函数
 */

//  回到手机桌面
fun Activity.toHome() {
    Intent().apply {
        setAction(Intent.ACTION_MAIN)// 设置Intent动作
        addCategory(Intent.CATEGORY_HOME)// 设置Intent种类
        startActivity(this)
    }
}

