package com.blcs.common.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.util.ArrayList
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException


/**
 * TODO 跟App相关的辅助类
 * 1、获取应用程序名称
 * 2、获取应用程序版本名称信息
 * 3、获取版本号
 * 4、获取所有安装的应用程序,不包含系统应用
 * 5、获取应用程序的icon图标
 * 6、启动安装应用程序
 * 7、获取渠道名
 * 8、双击退出
 * 8、获取进程名
 */
object AppUtils {



    private var firstTime: Long = 0

    /**
     * 获取包名
     * @param context
     * @return
     */
    fun getAppPackageName(context: Context): String {
        return context.applicationContext.packageName
    }

    /**
     * 1获取应用程序名称
     */
    fun getAppName(context: Context): String? {
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            val labelRes = packageInfo.applicationInfo.labelRes
            return context.resources.getString(labelRes)
        } catch (e: NameNotFoundException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * 2[获取应用程序版本名称信息]
     * @param context
     * @return 当前应用的版本名称
     */
    fun getVersionName(context: Context): String? {
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            return packageInfo.versionName

        } catch (e: NameNotFoundException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * 3获取版本号
     * int
     * @return 当前应用的版本号
     */
    fun getVersionCode(context: Context): Int {
        try {
            val manager = context.packageManager
            val info = manager.getPackageInfo(context.packageName, 0)
            return info.versionCode
        } catch (e: Exception) {
            e.printStackTrace()
            return 1
        }

    }

    /**
     * 4获取所有安装的应用程序,不包含系统应用
     * @param context
     * @return
     */
    fun getInstalledPackages(context: Context): List<PackageInfo> {
        val packageManager = context.packageManager
        val packageInfos = packageManager.getInstalledPackages(0)
        val packageInfoList = ArrayList<PackageInfo>()
        for (i in packageInfos.indices) {
            if (packageInfos[i].applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                packageInfoList.add(packageInfos[i])
            }
        }
        return packageInfoList
    }

    /**
     * 5、获取应用程序的icon图标
     * @param context
     * @return
     * 当包名错误时，返回null
     */
    fun getApplicationIcon(context: Context): Drawable? {
        val packageManager = context.packageManager
        try {
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            return packageInfo.applicationInfo.loadIcon(packageManager)
        } catch (e: NameNotFoundException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * 6、启动安装应用程序(兼容Android7.0) https://www.jianshu.com/p/3c554d3983d8
     * @param apkPath  应用程序路径
     */
    fun installApk(context: Activity, apkPath: String): Boolean {
        try {
            if (TextUtils.isEmpty(apkPath)) {
                Toast.makeText(context, "Apk路径为空", Toast.LENGTH_SHORT).show()
                return false
            }
            val file = File(apkPath)
            if (!file.exists()) {
                Toast.makeText(context, "文件不存在", Toast.LENGTH_SHORT).show()
                return false
            }
            L.e(apkPath)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)//增加读写权限
            }
            intent.setDataAndType(
                getPathUri(context, apkPath),
                "application/vnd.android.package-archive"
            )
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "安装失败，请重新下载", Toast.LENGTH_LONG).show()
            return false
        } catch (error: Error) {
            error.printStackTrace()
            Toast.makeText(context, "安装失败，请重新下载", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    fun getPathUri(context: Context, filePath: String): Uri {
        val uri: Uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val packageName = context.packageName
            uri = FileProvider.getUriForFile(context, "$packageName.fileprovider", File(filePath))
        } else {
            uri = Uri.fromFile(File(filePath))
        }
        return uri
    }

    /**
     * 7、获取渠道名
     * @param context
     * @return
     */
    fun getChannel(context: Context): String? {
        try {
            val pm = context.packageManager
            val appInfo = pm.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            return appInfo.metaData.getString("UMENG_CHANNEL")
        } catch (ignored: PackageManager.NameNotFoundException) {
        }

        return ""
    }
    /**
     * 8、双击退出
     */
    fun againstClick(context: Activity) {
        val secondTime = System.currentTimeMillis()
        if (secondTime - firstTime > 2000) {
            Toast.makeText(context, "再按一次退出程序", Toast.LENGTH_SHORT).show()
            firstTime = secondTime
        } else {
            context.finish()
        }
    }

    /**
     * 获取进程名
     */
    fun getProcessName(pid: Int): String? {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
            var processName = reader.readLine()
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim({ it <= ' ' })
            }
            return processName
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            try {
                 reader?.close()
            } catch (exception: IOException) {
                exception.printStackTrace()
            }

        }
        return null
    }
}
