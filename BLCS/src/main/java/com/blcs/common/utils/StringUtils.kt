package com.blcs.common.utils

import android.util.Log
import android.widget.TextView

import java.io.File
import java.text.DecimalFormat
import java.util.regex.Matcher
import java.util.regex.Pattern

/**TODO 通用字符串(String)相关类,为null时返回""
 * @author Lemon
 * @use StringUtil.
 * 1、校正（自动补全等）字符串（获取价格，保留两位小数,获取邮箱，自动补全,获取去掉所有 空格 、"-" 、"+86" 后的phone,
 * 获取网址，自动补全）
 * 2、提取特殊字符(去掉string内所有非数字类型字符)
 * 3、判断字符类型（判断手机格式是否正确，判断email格式是否正确，判断是否全是数字，判断字符类型是否是号码或字母，
 * 判断字符类型是否是身份证号，判断字符类型是否是网址，判断文件路径是否存在，判断字符类型是否是路径）
 * 4、判断字符是否非空
 * 5、获取string的长度
 * 6、获取去掉所有空格后的string
 * 7、获取去掉前后空格后的string
 * 8、获取string,为null时返回
 * 9、获取刚传入处理后的string
 * 10、检查字符串长度，如果字符串的长度超过maxLength，就截取前maxLength个字符串并在末尾拼上…
 * 11、判断字符串是否为空
 * 12、将字符串格式化为带两位小数的字符串
 * 13、字符串转换成double
 * 14、字符串转换成整数
 * 15、读取baseurl
 */
object StringUtils {
    private val TAG = "StringUtil"
    val EMPTY = "无"
    val UNKNOWN = "未知"
    val UNLIMITED = "不限"
    val I = "我"
    val YOU = "你"
    val HE = "他"
    val SHE = "她"
    val IT = "它"
    val MALE = "男"
    val FEMALE = "女"
    val TODO = "未完成"
    val DONE = "已完成"
    val FAIL = "失败"
    val SUCCESS = "成功"
    val SUNDAY = "日"
    val MONDAY = "一"
    val TUESDAY = "二"
    val WEDNESDAY = "三"
    val THURSDAY = "四"
    val FRIDAY = "五"
    val SATURDAY = "六"
    val YUAN = "元"
    private var currentString: String? = ""
    val HTTP = "http"
    val URL_PREFIX = "http://"
    val URL_PREFIXs = "https://"
    val URL_STAFFIX = URL_PREFIX
    val URL_STAFFIXs = URL_PREFIXs
    val FILE_PATH_PREFIX = "file://"
    val PRICE_FORMAT_DEFAULT = 0
    val PRICE_FORMAT_PREFIX = 1
    val PRICE_FORMAT_SUFFIX = 2
    val PRICE_FORMAT_PREFIX_WITH_BLANK = 3
    val PRICE_FORMAT_SUFFIX_WITH_BLANK = 4
    val PRICE_FORMATS = arrayOf("", "￥", "元", "￥ ", " 元")

    /**
     * 获取刚传入处理后的string
     */
    fun getCurrentString() = currentString ?: ""

    //获取string,为null时返回"" <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 获取string,为null则返回""
     */
    fun getString(tv: TextView?): String {
        return if (tv == null || tv.text == null) {
            ""
        } else getString(tv.text.toString())
    }

    /**
     * 获取string,为null则返回""
     */
    fun getString(any: Any?): String {
        return if (any == null) "" else getString(any.toString())
    }

    /**获取string,为null则返回""
     * @param cs
     * @return
     */
    fun getString(cs: CharSequence?): String {
        return if (cs == null) "" else getString(cs.toString())
    }

    /**获取string,为null则返回""
     * @param s
     * @return
     */
    fun getString(s: String?): String {
        return s ?: ""
    }

    // 获取string,为null时返回"" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    // 获取去掉前后空格后的string<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**获取去掉前后空格后的string,为null则返回""
     * @param tv
     * @return
     */
    fun getTrimedString(tv: TextView): String {
        return getTrimedString(
            getString(
                tv
            )
        )
    }

    /**获取去掉前后空格后的string,为null则返回""
     * @param object
     * @return
     */
    fun getTrimedString(`object`: Any): String {
        return getTrimedString(
            getString(
                `object`
            )
        )
    }

    /**获取去掉前后空格后的string,为null则返回""
     * @param cs
     * 0	0 * @return
     */
    fun getTrimedString(cs: CharSequence): String {
        return getTrimedString(
            getString(
                cs
            )
        )
    }

    /**获取去掉前后空格后的string,为null则返回""
     * @param s
     * @return
     */
    fun getTrimedString(s: String): String {
        return getString(s).trim { it <= ' ' }
    }

    //  获取去掉前后空格后的string>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // 获取去掉所有空格后的string <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**获取去掉所有空格后的string,为null则返回""
     * @param tv
     * @return
     */
    fun getNoBlankString(tv: TextView): String {
        return getNoBlankString(
            getString(
                tv
            )
        )
    }

    /**获取去掉所有空格后的string,为null则返回""
     * @param object
     * @return
     */
    fun getNoBlankString(`object`: Any): String {
        return getNoBlankString(
            getString(
                `object`
            )
        )
    }

    /**获取去掉所有空格后的string,为null则返回""
     * @param cs
     * @return
     */
    fun getNoBlankString(cs: CharSequence): String {
        return getNoBlankString(
            getString(
                cs
            )
        )
    }

    /**获取去掉所有空格后的string,为null则返回""
     * @param s
     * @return
     */
    fun getNoBlankString(s: String): String {
        return getString(s).replace(" ".toRegex(), "")
    }

    // 获取去掉所有空格后的string >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // 获取string的长度<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**获取string的长度,为null则返回0
     * @param tv
     * @param trim
     * @return
     */
    fun getLength(tv: TextView, trim: Boolean): Int {
        return getLength(
            getString(
                tv
            ), trim
        )
    }

    /**获取string的长度,为null则返回0
     * @param object
     * @param trim
     * @return
     */
    fun getLength(`object`: Any, trim: Boolean): Int {
        return getLength(
            getString(
                `object`
            ), trim
        )
    }

    /**获取string的长度,为null则返回0
     * @param cs
     * @param trim
     * @return
     */
    fun getLength(cs: CharSequence, trim: Boolean): Int {
        return getLength(
            getString(
                cs
            ), trim
        )
    }

    /**获取string的长度,为null则返回0
     * @param s
     * @param trim
     * @return
     */
    fun getLength(s: String, trim: Boolean): Int {

        return if (trim) getNoBlankString(s).length else getString(
            s
        ).length

    }

    //  获取string的长度>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //  判断字符类型 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //判断手机格式是否正确
    fun isPhone(phone: String): Boolean {
        val p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-2,5-9])|(17[0-9]))\\d{8}$")
        currentString = phone
        return p.matcher(phone).matches()
    }

    //判断email格式是否正确
    fun isEmail(email: String): Boolean {
        val str =
            "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"
        val p = Pattern.compile(str)

        currentString = email

        return p.matcher(email).matches()
    }

    //判断是否全是数字
    fun isNumer(number: String): Boolean {
        val pattern = Pattern.compile("[0-9]*")
        val isNum = pattern.matcher(number)
        if (!isNum.matches()) {
            return false
        }

        currentString = number

        return true
    }

    /**判断字符类型是否是号码或字母
     * @param inputed
     * @return
     */
    fun isNumberOrAlpha(inputed: String?): Boolean {
        if (inputed == null) {
            Log.e(TAG, "isNumberOrAlpha  inputed == null >> return false;")
            return false
        }
        val pNumber = Pattern.compile("[0-9]*")
        var mNumber: Matcher
        val pAlpha = Pattern.compile("[a-zA-Z]")
        var mAlpha: Matcher
        for (i in 0 until inputed.length) {
            mNumber = pNumber.matcher(inputed.substring(i, i + 1))
            mAlpha = pAlpha.matcher(inputed.substring(i, i + 1))
            if (!mNumber.matches() && !mAlpha.matches()) {
                return false
            }
        }

        currentString = inputed
        return true
    }

    /**判断字符类型是否是身份证号
     * @param idCard
     * @return
     */
    fun isIDCard(idCard: String): Boolean {
        var idCard = idCard
        if (isNumberOrAlpha(idCard) == false) {
            return false
        }
        idCard = getString(idCard)
        if (idCard.length == 15) {
            Log.w(TAG, "isIDCard idCard.length() == 15 old IDCard")
            currentString = idCard
            return true
        }
        if (idCard.length == 18) {
            currentString = idCard
            return true
        }

        return false
    }

    /**判断字符类型是否是网址
     * @param url
     * @return
     */
    fun isUrl(url: String): Boolean {
        if (!url.startsWith(URL_PREFIX) && !url.startsWith(URL_PREFIXs)) {
            return false
        }

        currentString = url
        return true
    }

    /**判断文件路径是否存在
     * @param path
     * @return
     */
    fun isFilePathExist(path: String): Boolean {
        return isFilePath(path) && File(path).exists()
    }

    /**判断字符类型是否是路径
     * @param path
     * @return
     */
    fun isFilePath(path: String): Boolean {
        if (!path.contains(".") || path.endsWith(".")) {
            return false
        }

        currentString = path

        return true
    }

    // 判断字符类型 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //  提取特殊字符<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**去掉string内所有非数字类型字符
     * @param tv
     * @return
     */
    fun getNumber(tv: TextView): String {
        return getNumber(
            getString(
                tv
            )
        )
    }

    /**去掉string内所有非数字类型字符
     * @param object
     * @return
     */
    fun getNumber(`object`: Any): String {
        return getNumber(
            getString(
                `object`
            )
        )
    }

    /**去掉string内所有非数字类型字符
     * @param cs
     * @return
     */
    fun getNumber(cs: CharSequence): String {
        return getNumber(
            getString(
                cs
            )
        )
    }

    /**去掉string内所有非数字类型字符
     * @param s
     * @return
     */
    fun getNumber(s: String): String {
        var numberString = ""
        var single: String
        for (i in 0 until s.length) {
            single = s.substring(i, i + 1)
            if (isNumer(single)) {
                numberString += single
            }
        }

        return numberString
    }

    //  提取特殊字符>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //  校正（自动补全等）字符串<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**获取网址，自动补全
     * @param tv
     * @return
     */
    fun getCorrectUrl(tv: TextView): String {
        return getCorrectUrl(
            getString(
                tv
            )
        )
    }

    /**获取网址，自动补全
     * @param url
     * @return
     */
    fun getCorrectUrl(url: String): String {
        var url = url
        Log.i(TAG, "getCorrectUrl : \n$url")

        if (!url.endsWith("/") && !url.endsWith(".html")) {
            url = "$url/"
        }

        return if (isUrl(url) == false) {
            URL_PREFIX + url
        } else url
    }

    /**获取去掉所有 空格 、"-" 、"+86" 后的phone
     * @param tv
     * @return
     */
    fun getCorrectPhone(tv: TextView): String {
        return getCorrectPhone(
            getString(
                tv
            )
        )
    }

    /**获取去掉所有 空格 、"-" 、"+86" 后的phone
     * @param phone
     * @return
     */
    fun getCorrectPhone(phone: String): String {
        var phone = phone

        phone = getNoBlankString(phone)
        phone = phone.replace("-".toRegex(), "")
        if (phone.startsWith("+86")) {
            phone = phone.substring(3)
        }
        return phone
    }


    /**获取邮箱，自动补全
     * @param tv
     * @return
     */
    fun getCorrectEmail(tv: TextView): String {
        return getCorrectEmail(
            getString(
                tv
            )
        )
    }

    /**获取邮箱，自动补全
     * @param email
     * @return
     */
    fun getCorrectEmail(email: String): String {
        var email = email

        email = getNoBlankString(email)
        if (isEmail(email) == false && !email.endsWith(".com")) {
            email += ".com"
        }

        return email
    }

    /**获取价格，保留两位小数
     * @param price
     * @param formatType 添加单位（元）
     * @return
     */
    @JvmOverloads
    fun getPrice(price: String, formatType: Int = PRICE_FORMAT_DEFAULT): String {
        //单独写到getCorrectPrice? <<<<<<<<<<<<<<<<<<<<<<
        var correctPrice = ""
        var s: String
        for (i in 0 until price.length) {
            s = price.substring(i, i + 1)
            if ("." == s || isNumer(s)) {
                correctPrice += s
            }
        }
        //单独写到getCorrectPrice? >>>>>>>>>>>>>>>>>>>>>>

        Log.i(TAG, "getPrice  <<<<<<<<<<<<<<<<<< correctPrice =  $correctPrice")
        if (correctPrice.contains(".")) {
            //			if (correctPrice.startsWith(".")) {
            //				correctPrice = 0 + correctPrice;
            //			}
            if (correctPrice.endsWith(".")) {
                correctPrice = correctPrice.replace(".".toRegex(), "")
            }
        }

        Log.i(TAG, "getPrice correctPrice =  $correctPrice >>>>>>>>>>>>>>>>")
        return getPrice(0.0, formatType)
    }



    /**获取价格，保留两位小数
     * @param price
     * @param formatType 添加单位（元）
     * @return
     */
    @JvmOverloads
    fun getPrice(price: Double, formatType: Int = PRICE_FORMAT_DEFAULT): String {
        val s = DecimalFormat("#########0.00").format(price)
        when (formatType) {
            PRICE_FORMAT_PREFIX -> return PRICE_FORMATS[PRICE_FORMAT_PREFIX] + s
            PRICE_FORMAT_SUFFIX -> return s + PRICE_FORMATS[PRICE_FORMAT_SUFFIX]
            PRICE_FORMAT_PREFIX_WITH_BLANK -> return PRICE_FORMATS[PRICE_FORMAT_PREFIX_WITH_BLANK] + s
            PRICE_FORMAT_SUFFIX_WITH_BLANK -> return s + PRICE_FORMATS[PRICE_FORMAT_SUFFIX_WITH_BLANK]
            else -> return s
        }
    }

    /**
     * 检查字符串长度，如果字符串的长度超过maxLength，就截取前maxLength个字符串并在末尾拼上appendString
     */
    @JvmOverloads
    fun checkLength(string: String, maxLength: Int, appendString: String? = "…"): String {
        var string = string
        if (string.length > maxLength) {
            string = string.substring(0, maxLength)
            if (appendString != null) {
                string += appendString
            }
        }
        return string
    }

    /**
     * 判断字符串是否为空 为空即true
     *
     * @param str 字符串
     * @return
     */
    fun isNullString(str: String?): Boolean {
        return str == null || str.length == 0 || "null" == str
    }


    /**
     * 将字符串格式化为带两位小数的字符串
     *
     * @param str 字符串
     * @return
     */
    fun format2Decimals(str: String): String {
        val df = DecimalFormat("#.00")
        return if (df.format(stringToDouble(str)).startsWith(".")) {
            "0" + df.format(stringToDouble(str))
        } else {
            df.format(stringToDouble(str))
        }
    }

    /**
     * 字符串转换成double ,转换失败将会 return 0;
     *
     * @param str 字符串
     * @return
     */
    fun stringToDouble(str: String): Double {
        return if (isNullString(str)) {
            0.0
        } else {
            try {
                java.lang.Double.parseDouble(str)
            } catch (e: NumberFormatException) {
                0.0
            }

        }
    }

    /**
     * 字符串转换成整数 ,转换失败将会 return 0;
     *
     * @param str 字符串
     * @return
     */
    fun stringToInt(str: String): Int {
        return if (isNullString(str)) {
            0
        } else {
            try {
                Integer.parseInt(str)
            } catch (e: NumberFormatException) {
                0
            }

        }
    }


    /**
     * 读取baseurl
     * @param url
     * @return
     */
    fun getBasUrl(url: String): String {
        var url = url
        var head = ""
        var index = url.indexOf("://")
        if (index != -1) {
            head = url.substring(0, index + 3)
            url = url.substring(index + 3)
        }
        index = url.indexOf("/")
        if (index != -1) {
            url = url.substring(0, index + 1)
        }
        return head + url
    }
}
