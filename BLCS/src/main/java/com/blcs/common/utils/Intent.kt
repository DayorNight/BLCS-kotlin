package com.blcs.common.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment

object Intent {
    fun Intent.toHome(activity: Activity) {
//
//        val intent = Intent()
//        intent.setAction(Intent.ACTION_MAIN)// 设置Intent动作
//        intent.addCategory(Intent.CATEGORY_HOME)// 设置Intent种类
//        activity.startActivity(intent)
    }

    fun Context.toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
    fun Fragment.toast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }
}

