package com.blcs.common.utils.spreadFun

import java.text.DecimalFormat
import java.util.regex.Matcher
import java.util.regex.Pattern


object Verify{
    val phone = "^((13[0-9])|(15[^4,\\D])|(18[0-2,5-9])|(17[0-9]))\\d{8}$"
    val email =  "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"
    val password =  "^[a-zA-Z](?![0-9]+\$)(?![a-zA-Z]+\$)[0-9A-Za-z]{6,12}\$"
    val number =  "[0-9]*"
    val letter =  "[a-zA-Z]"
    val URL_PREFIX = "http://"
    val URL_PREFIXs = "https://"
}

// ========格式验证========
/**
 * 1.判断手机格式是否正确
 */
fun String.isPhone() =  Pattern.compile(Verify.phone).matcher(this).matches()

/**
 * 2.判断email格式是否正确
 */
fun String.isEmail() =  Pattern.compile(Verify.email).matcher(this).matches()

/**
 * 3.判断是否全是数字
 */
fun String.isNumber() =  Pattern.compile(Verify.number).matcher(this).matches()

/**
 * 4.判断字符类型是否是号码或字母
 */
fun String.isNumberOrAlpha():Boolean {
    val pNumber = Pattern.compile(Verify.number)
    var mNumber: Matcher
    val pAlpha = Pattern.compile(Verify.letter)
    var mAlpha: Matcher
    for (i in 0 until this.length) {
        mNumber = pNumber.matcher(this.substring(i, i + 1))
        mAlpha = pAlpha.matcher(this.substring(i, i + 1))
        if (!mNumber.matches() && !mAlpha.matches()) {
            return false
        }
    }
    return true
}

/**
 * 4.判断是否是身份证号码
 */
fun String.isIDCard() :Boolean{
    if (this.isNumberOrAlpha()) {
        if (this.length == 15||this.length == 18) {
            return true
        }
    }
    return false
}

/**
 * 5.判断字符类型是否是网址
 */
fun String.isHttp()= this.startsWith(Verify.URL_PREFIX) || this.startsWith(Verify.URL_PREFIXs)

/**
 * 6.判断字符类型是否是路径
 */
fun String.isFilePath() = this.contains(".")&&!this.endsWith(".")

/**
 * 7.将字符串格式化为带两位小数的字符串
 */
fun String.format2Decimals(): String{
    val df = DecimalFormat("#.00")
    return if (df.format(this.toDouble()).startsWith(".")) {
        "0" + df.format(this.toDouble())
    } else {
        df.format(this.toDouble())
    }
}

/**
 * 8.判断密码是否由6-12位数字和字母组成
 */
fun String.isPassword() = Pattern.compile(Verify.password).matcher(this).matches()

