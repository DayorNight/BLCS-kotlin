package com.blcs.common

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.IntDef
import androidx.annotation.NonNull
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 *  1.获取当前时间 Long  Date()  String
 *  2.时间转化Long to String /String to Long/string to date/date to string/long to date
 *  3.时间跨度
 *  4.智能时间显示
 *  5.判断是否是今天
 *  6.秒转倒计时
 *  7.获取两个时间的时间间隔
 *  8.根据生日获取年龄
 *  9.获取智能生日
 *  10.根据生日计算星座
 *  11.获取生日,不带年份
 */
object TimeUtils{

    var DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private val SDF_THREAD_LOCAL = ThreadLocal<SimpleDateFormat>()
    private fun getDefaultFormat() = getDateFormat("yyyy-MM-dd HH:mm:ss")
    /**
     * 系统计时开始时间
     */
    val SYSTEM_START_DATE = intArrayOf(1970, 0, 1, 0, 0, 0)
    val YEAR = 0
    val MONTH = 1
    val DAY_OF_MONTH = 2
    val HOUR_OF_DAY = 3
    val MINUTE = 4
    val SECOND = 5
    val minTimeDetails = intArrayOf(0, 0, 0)
    val maxTimeDetails = intArrayOf(23, 59, 59)
    val LEVEL_YEAR = 0
    val LEVEL_MONTH = 1
    val LEVEL_DAY = 2
    val LEVEL_HOUR = 3
    val LEVEL_MINUTE = 4
    val LEVEL_SECOND = 5
    val LEVELS =
        intArrayOf(LEVEL_YEAR, LEVEL_MONTH, LEVEL_DAY, LEVEL_HOUR, LEVEL_MINUTE, LEVEL_SECOND)
    val NAME_YEAR = "年"
    val NAME_MONTH = "月"
    val NAME_DAY = "日"
    val NAME_HOUR = "时"
    val NAME_MINUTE = "分"
    val NAME_SECOND = "秒"
    val LEVEL_NAMES = arrayOf(NAME_YEAR, NAME_MONTH, NAME_DAY, NAME_HOUR, NAME_MINUTE, NAME_SECOND)
    /**
     * 获取当前时间 Long  Date()  String
     */
    fun getNowTimes() = System.currentTimeMillis()
    fun getNowDate() = Date()
    fun getNowString() = long2String(System.currentTimeMillis(), getDefaultFormat())

    /**
     *  时间转化Long to String
     *  @param pattern  YYYY-MM-DD HH:mm:ss
     */
    fun long2String(@NonNull time: Long, @NonNull pattern: String) = long2String(time, getDateFormat(pattern))

    fun long2String(time: Long, @NonNull format: DateFormat) = format.format(Date(time))

    fun getDateFormat(pattern: String): SimpleDateFormat {
        var simpleDateFormat = SDF_THREAD_LOCAL.get()
        if (simpleDateFormat == null) {
            simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
            SDF_THREAD_LOCAL.set(simpleDateFormat)
        } else {
            simpleDateFormat.applyPattern(pattern)
        }
        return simpleDateFormat
    }

    /**
     * String to Long
     * @param time  YYYY-MM-DD HH:mm:ss
     */
    fun string2Long(time: String) = string2Long(time, getDefaultFormat())

    fun string2Long(time: String, @NonNull format: DateFormat) = format.parse(time).time

    /**
     * string to date
     */
    fun string2Date(time: String, @NonNull format: DateFormat) =  format.parse(time)

    /**
     * date to string
     */
    fun date2String(date: Date) = date2String(date, getDefaultFormat())

    fun date2String(date: Date, @NonNull format: DateFormat) =  format.format(date)

    /**
     * date to long
     */
    fun date2long(date: Date) = date.time

    /**
     * long to date
     */
    fun long2Date(millis: Long) =  Date(millis)

    /**
     * 时间跨度  返回 long 型
     * Return the time span, in unit.
     * @param date1 The first date.
     * @param date2 The second date.
     * @param unit  The unit of time span.
     *              <ul>
     *              <li>{@link TimeConstants#MSEC}</li>
     *              <li>{@link TimeConstants#SEC }</li>
     *              <li>{@link TimeConstants#MIN }</li>
     *              <li>{@link TimeConstants#HOUR}</li>
     *              <li>{@link TimeConstants#DAY }</li>
     *              </ul>
     * @return the time span, in unit
     */
    fun getTimeSpan(time1: String, time2: String, @TimeConstants.Unit unit: Int) = getTimeSpan(time1, time2, getDefaultFormat(), unit)

    fun getTimeSpan(
        time1: String,
        time2: String,
        @NonNull format: DateFormat,
        @TimeConstants.Unit unit: Int
    ) = millis2TimeSpan(string2Long(time1, format) - string2Long(time2, format), unit)

    fun millis2TimeSpan(millis: Long, @TimeConstants.Unit unit: Int) = millis / unit

    /**
     * 时间跨度  返回string
     * @param millis1   The first milliseconds.
     * @param millis2   The second milliseconds.
     * @param precision The precision of time span.
     *                  <ul>
     *                  <li>precision = 0, return null</li>
     *                  <li>precision = 1, return 天</li>
     *                  <li>precision = 2, return 天, 小时</li>
     *                  <li>precision = 3, return 天, 小时, 分钟</li>
     *                  <li>precision = 4, return 天, 小时, 分钟, 秒</li>
     *                  <li>precision &gt;= 5，return 天, 小时, 分钟, 秒, 毫秒</li>
     *                  </ul>
     * @return the fit time span
     */
    fun getFitTimeSpan(time1: String,time2: String, precision: Int ): String? {
        val delta = string2Long(time1, getDefaultFormat()) - string2Long(time2, getDefaultFormat())
        return millis2FitTimeSpan(delta, precision)
    }
    private fun millis2FitTimeSpan(millis: Long, precision: Int): String? {
        var millis = millis
        var precision = precision
        if (precision <= 0) return null
        precision = Math.min(precision, 5)
        val units = arrayOf("天", "小时", "分钟", "秒", "毫秒")
        if (millis == 0L) return 0.toString() + units[precision - 1]
        val sb = StringBuilder()
        if (millis < 0) {
            sb.append("-")
            millis = -millis
        }
        val unitLen = intArrayOf(86400000, 3600000, 60000, 1000, 1)
        for (i in 0 until precision) {
            if (millis >= unitLen[i]) {
                val mode = millis / unitLen[i]
                millis -= mode * unitLen[i]
                sb.append(mode).append(units[i])
            }
        }
        return sb.toString()
    }


    /**
     * 智能时间显示
     * Return the friendly time span by now.
     * The pattern is `yyyy-MM-dd HH:mm:ss`.
     * @param time The formatted time string.
     * @return the friendly time span by now
     *  * 如果小于 1 秒钟内，显示刚刚
     *  * 如果在 1 分钟内，显示 XXX秒前
     *  * 如果在 1 小时内，显示 XXX分钟前
     *  * 如果在 1 小时外的今天内，显示今天15:32
     *  * 如果是昨天的，显示昨天15:32
     *  * 其余显示，2016-10-15
     *  * 时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007
     */
    fun getSmartDate(time: String) = getFriendlyTimeSpanByNow(time, getDefaultFormat())

    fun getFriendlyTimeSpanByNow(time: String, @NonNull format: DateFormat) = getFriendlyTimeSpanByNow(string2Long(time, format))

    fun getFriendlyTimeSpanByNow(millis: Long): String {
        val now = System.currentTimeMillis()
        val span = now - millis
        if (span < 0)
        // U can read http://www.apihome.cn/api/java/Formatter.html to understand it.
            return String.format("%tc", millis)
        if (span < 1000) {
            return "刚刚"
        } else if (span < TimeConstants.MIN) {
            return String.format(Locale.getDefault(), "%d秒前", span / TimeConstants.SEC)
        } else if (span < TimeConstants.HOUR) {
            return String.format(Locale.getDefault(), "%d分钟前", span / TimeConstants.MIN)
        }
        // 获取当天 00:00
        val wee = getWeeOfToday()
        return if (millis >= wee) {
            String.format("今天%tR", millis)
        } else if (millis >= wee - TimeConstants.DAY) {
            String.format("昨天%tR", millis)
        } else {
            String.format("%tF", millis)
        }
    }

    fun getWeeOfToday(): Long {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }


    /**
     * 判断是否是今天
     */
    fun isToday(time: String) = isToday(string2Long(time, getDefaultFormat()))
    fun isToday(time: String, @NonNull format: DateFormat) = isToday(string2Long(time, format))
    fun isToday(date: Date) = isToday(date.time)
    fun isToday(millis: Long): Boolean {
        val wee = getWeeOfToday()
        return millis >= wee && millis < wee + TimeConstants.DAY
    }

    /**
     * 秒转倒计时   格式 天、时、分、秒
     */
    fun secondsToTimerFormat(millis: Long): String {
        var result: String? = null
        val days = millis / (60 * 60 * 24)
        val hours = millis % (60 * 60 * 24) / (60 * 60)
        val minutes = millis % (60 * 60) / 60
        val seconds = millis % 60
        if (days > 0) {
            result = (days.toString() + "天" + hours + "时" + minutes + "分"
                    + seconds + "秒")
        } else if (hours > 0) {
            result = (hours.toString() + "时" + minutes + "分"
                    + seconds + "秒")
        } else if (minutes > 0) {
            result = (minutes.toString() + "分"
                    + seconds + "秒")
        } else {
            result = seconds.toString() + "秒"
        }
        return result
    }


    /**
     * 获取两个时间的时间间隔
     *
     * @param sdf
     * @param dateLong0
     * @param dateLong1
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    fun getBetween(sdf: SimpleDateFormat?, dateLong0: Long, dateLong1: Long): Long {
        var sdf = sdf
        if (sdf == null) {
            sdf = SimpleDateFormat("yyyy-mm-dd hh:mm:ss")
        }
        val date0: Date
        val date1: Date
        var between: Long = 0
        try {

            date0 = sdf.parse(sdf.format(Date(dateLong0)))
            date1 = sdf.parse(sdf.format(Date(dateLong1)))
            between = (date0.time - date1.time) / 1000//除以1000是为了转换成秒
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        //		System.out.println("between=" + String.valueOf(between));
        return between
    }
    /**
     * 获取日期 年，月， 日 对应值
     *
     * @param time
     * @return
     */
    fun getDateDetail(time: Long): IntArray {
        val mCalendar = Calendar.getInstance()
        mCalendar.timeInMillis = time
        return intArrayOf(
            mCalendar.get(Calendar.YEAR), //0
            mCalendar.get(Calendar.MONTH) + 1, //1
            mCalendar.get(Calendar.DAY_OF_MONTH)
        )//2
    }
    /**
     * 根据生日获取年龄
     *
     * @param birthday
     * @return
     */
    fun getAge(birthday: Date?): Int {
        if (birthday == null) {
            return 0
        }
        if (birthday.year > getDateDetail(System.currentTimeMillis())[0]) {
            birthday.year = birthday.year - SYSTEM_START_DATE[0]
        }

        return getAge(intArrayOf(birthday.year, birthday.month, birthday.day))
    }

    fun getAge(birthday: Long) =getAge(getDateDetail(birthday))

    fun getAge(birthdayDetail: IntArray?): Int {
        if (birthdayDetail == null || birthdayDetail.size < 3) {
            return 0
        }

        val nowDetails = getDateDetail(System.currentTimeMillis())

        var age = nowDetails[0] - birthdayDetail[0]

        if (nowDetails[1] < birthdayDetail[1]) {
            age = age - 1
        } else if (nowDetails[1] == birthdayDetail[1]) {
            if (nowDetails[2] < birthdayDetail[2]) {
                age = age - 1
            }
        }

        return age
    }


    /**
     * 根据生日计算星座
     */
    fun getStar(birthday: Date): String {
        val c = Calendar.getInstance()
        c.time = birthday
        var month = c.get(Calendar.MONTH)                // 月份从0 ~ 11
        val dayOfMonth = c.get(Calendar.DAY_OF_MONTH)
        val DayArr = intArrayOf(19, 18, 20, 19, 20, 21, 22, 22, 22, 23, 22, 21)
        val starArr = arrayOf(
            "魔羯座",
            "水瓶座",
            "双鱼座",
            "白羊座",
            "金牛座",
            "双子座",
            "巨蟹座",
            "狮子座",
            "处女座",
            "天秤座",
            "天蝎座",
            "射手座"
        )
        if (dayOfMonth > DayArr[month]) {
            month = month + 1
            if (month == 12) {
                month = 0
            }
        }
        return starArr[month]
    }

    /**
     * 获取生日,不带年份
     */
    fun getBirthday(date: Date): String {
        return getBirthday(date, false)
    }

    /**
     * 获取生日
     */
    fun getBirthday(date: Date?, needYear: Boolean): String {
        return if (date == null) "" else getBirthday(date.time, needYear)
    }

    /**
     * 获取生日,不带年份
     *
     * @param date
     * @return
     */
    fun getBirthday(date: Long): String {
        return getBirthday(date, false)
    }

    /**
     * 获取生日
     */
    fun getBirthday(date: Long, needYear: Boolean): String {
        val details = getWholeDetail(date)
        return if (needYear) {
             details[0].toString() + "年" + details[1] + "月" + details[2] + "日"
        } else details[1].toString() + "月" + details[2] + "日"
    }

    /**
     * 获取智能生日
     */
    fun getSmartBirthday(birthdayDetails: IntArray?): String {
        if (birthdayDetails == null || birthdayDetails.size < 3) {
            return ""
        }
        if (birthdayDetails[0] > SYSTEM_START_DATE[0]) {
            birthdayDetails[0] = birthdayDetails[0] - SYSTEM_START_DATE[0]
        }
        return getSmartBirthday(Date(birthdayDetails[0], birthdayDetails[1], birthdayDetails[2]))
    }
    fun getSmartBirthday(birthday: Date?): String {
        if (birthday == null) {
            return ""
        }
        if (birthday.year > getDateDetail(System.currentTimeMillis())[0]) {
            birthday.year = birthday.year - SYSTEM_START_DATE[0]
        }

        return getSmartBirthday(birthday.time, false) + " " + (getDateDetail(System.currentTimeMillis())[0] - birthday.year) + "岁"
    }
    fun getSmartBirthday(birthday: Long, needYear: Boolean): String {
        val birthdayDetails = getDateDetail(birthday)
        val nowDetails = getDateDetail(System.currentTimeMillis())

        val birthdayCalendar = Calendar.getInstance()
        birthdayCalendar.set(birthdayDetails[0], birthdayDetails[1], birthdayDetails[2])

        val nowCalendar = Calendar.getInstance()
        nowCalendar.set(nowDetails[0], nowDetails[1], nowDetails[2])

        val days =
            birthdayCalendar.get(Calendar.DAY_OF_YEAR) - nowCalendar.get(Calendar.DAY_OF_YEAR)
        if (days < 8) {
            if (days >= 3) {
                return days.toString() + "天后"
            }
            if (days >= 2) {
                return Day.NAME_THE_DAY_AFTER_TOMORROW
            }
            if (days >= 1) {
                return Day.NAME_TOMORROW
            }
            if (days >= 0) {
                return Day.NAME_TODAY
            }
        }

        return if (needYear) {
            birthdayDetails[0].toString() + "年" + birthdayDetails[1] + "月" + birthdayDetails[2] + "日"
        } else birthdayDetails[1].toString() + "月" + birthdayDetails[2] + "日"
    }

    /**
     * 获取日期 年，月， 日， 时， 分， 秒 对应值
     */
    fun getWholeDetail(time: Long): IntArray {
        val mCalendar = Calendar.getInstance()
        mCalendar.timeInMillis = time
        return intArrayOf(
            mCalendar.get(Calendar.YEAR), //0
            mCalendar.get(Calendar.MONTH) + 1, //1
            mCalendar.get(Calendar.DAY_OF_MONTH), //2
            mCalendar.get(Calendar.HOUR_OF_DAY), //3
            mCalendar.get(Calendar.MINUTE), //4
            mCalendar.get(Calendar.SECOND)//5
        )
    }

    fun fomerIsBigger(fomer: IntArray?, current: IntArray?): Boolean {
        if (fomer == null || current == null) {
            return false
        }
        val compareLength = if (fomer.size < current.size) fomer.size else current.size

        for (i in 0 until compareLength) {
            if (fomer[i] < current[i]) {
                return false
            }
            if (fomer[i] > current[i]) {
                return true
            }
        }

        return false
    }

    /**
     * 判断现在是否属于一段时间,不包含端点
     */
    fun isNowInTimeArea(start: IntArray, end: IntArray): Boolean {
        return isInTimeArea(getTimeDetail(System.currentTimeMillis()), start, end)
    }

    /**
     * 获取日期  时， 分， 秒 对应值
     */
    fun getTimeDetail(time: Long): IntArray {
        val mCalendar = Calendar.getInstance()
        mCalendar.timeInMillis = time
        return intArrayOf(
            mCalendar.get(Calendar.HOUR_OF_DAY), //3
            mCalendar.get(Calendar.MINUTE), //4
            mCalendar.get(Calendar.SECOND)//5
        )
    }

    /**
     * 判断一个时间是否属于一段时间,不包含端点
     * (start, end)可跨越0:00,即start < end也行
     */
    fun isInTimeArea(time: IntArray, start: IntArray, end: IntArray): Boolean {
        if (fomerIsBigger(end, start)) {
            return fomerIsBigger(time, start) && fomerIsBigger(end, time)
        }

        if (fomerIsBigger(time, start) && fomerIsBigger(maxTimeDetails, time)) {
            return true
        }
        return if (fomerIsBigger(time, minTimeDetails) && fomerIsBigger(end, time)) {
            true
        } else false

    }
}

object Day {

    val NAME_THE_DAY_BEFORE_YESTERDAY = "前天"
    val NAME_YESTERDAY = "昨天"
    val NAME_TODAY = "今天"
    val NAME_TOMORROW = "明天"
    val NAME_THE_DAY_AFTER_TOMORROW = "后天"


    val TYPE_SUNDAY = 0
    val TYPE_MONDAY = 1
    val TYPE_TUESDAY = 2
    val TYPE_WEDNESDAY = 3
    val TYPE_THURSDAY = 4
    val TYPE_FRIDAY = 5
    val TYPE_SATURDAY = 6
    val DAY_OF_WEEK_TYPES = intArrayOf(
        TYPE_SUNDAY,
        TYPE_MONDAY,
        TYPE_TUESDAY,
        TYPE_WEDNESDAY,
        TYPE_THURSDAY,
        TYPE_FRIDAY,
        TYPE_SATURDAY
    )

    val NAME_SUNDAY = "日"
    val NAME_MONDAY = "一"
    val NAME_TUESDAY = "二"
    val NAME_WEDNESDAY = "三"
    val NAME_THURSDAY = "四"
    val NAME_FRIDAY = "五"
    val NAME_SATURDAY = "六"
    val DAY_OF_WEEK_NAMES = arrayOf(
        NAME_SUNDAY,
        NAME_MONDAY,
        NAME_TUESDAY,
        NAME_WEDNESDAY,
        NAME_THURSDAY,
        NAME_FRIDAY,
        NAME_SATURDAY
    )


    /**
     * @param type
     * @return
     */
    fun isContainType(type: Int): Boolean {
        for (existType in DAY_OF_WEEK_TYPES) {
            if (type == existType) {
                return true
            }
        }
        return false
    }

    fun getDayNameOfWeek(type: Int): String {
        return if (isContainType(type)) DAY_OF_WEEK_NAMES[type - TYPE_SUNDAY] else ""
    }

}
object TimeConstants {
    const val MSEC = 1
    const val SEC = 1000
    const val MIN = 60000
    const val HOUR = 3600000
    const val DAY = 86400000

    @IntDef(MSEC, SEC, MIN, HOUR, DAY)
    @Retention(RetentionPolicy.SOURCE)
    annotation class Unit
}
