package com.blcs.common

import android.text.TextUtils
import android.util.Xml

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException

import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Field
import java.util.ArrayList

/**
 * Created by lwb on 2017/12/26.
 * TODO XML文件工具类，包含：将xml文件解析成实体集合、获取xml标签值、将标签值解析成实体集合
 * 1、XML文件解析成实体,不涉及到标签的属性值。
 * 2、获取xml字符串标签中的属性值
 * 3、获取Xml文件中的属性值
 *
 */

object XMLUtil {

    /**
     * 1、XML文件解析成实体,不涉及到标签的属性值。
     * @param xml   xml字符串文件
     * @param clazz     对应实体的class文件
     * @param tagEntity
     * 开始解析实体的标签，例如下面的实例中就是student<br></br>
     * < person ><br></br>
     * < student ><br></br>
     * < name >Lucy< /name ><br></br>
     * < age >21< /age ><br></br>
     * < /student ><br></br>
     * < /person ><br></br>
     * @return      返回解析的对应实体文件
     */
    var tagName:String? = null
    fun <T> xmlToObject(xml: String, clazz: Class<T>, tagEntity: String): List<T>? {
        var list: MutableList<T>? = null
        val xmlPullParser = Xml.newPullParser()
        val inputStream = ByteArrayInputStream(xml.toByteArray())
        try {
            xmlPullParser.setInput(inputStream, "utf-8")
            val fields = clazz.declaredFields
            val type = xmlPullParser.eventType
            var lastTag = ""
            var t: T? = null
            while (type != XmlPullParser.END_DOCUMENT) {
                when (type) {
                    XmlPullParser.START_DOCUMENT -> list = ArrayList()
                    XmlPullParser.START_TAG -> {
                         tagName = xmlPullParser.name
                        if (tagEntity == tagName) {
                            t = clazz.newInstance()
                            lastTag = tagEntity
                        } else if (tagEntity == lastTag) {
                            val textValue = xmlPullParser.nextText()
                            val fieldName = xmlPullParser.name
                            for (field in fields) {
                                ReflectUtil.setFieldValue<T>(t!!, field, fieldName, textValue)
                            }
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        tagName = xmlPullParser.name
                        if (tagEntity == tagName) {
                            t?.let { list!!.add(it) }
                            lastTag = ""
                        }
                    }
                    XmlPullParser.END_DOCUMENT -> {
                    }
                }
            }
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return list
    }

    /**
     * 2、获取xml字符串标签中的属性值
     * @param xml   xml字符串
     * @param clazz     转换成对应的实体
     * @param tagName   实体对应xml字符串的起始标签,如下面实例中的person标签<br></br>
     * < person name="Lucy" age="12"><br></br>
     * < student ><br></br>
     * < name >Lucy< /name ><br></br>
     * < age >21< /age ><br></br>
     * < /student ><br></br>
     * < /person ><br></br>
     * @return  返回属性值组成的List对象集合。
     */
    fun <T> attributeToObject(xml: String, clazz: Class<T>, tagName: String): List<T>? {
        if (TextUtils.isEmpty(tagName)) return null
        var list: MutableList<T>? = null
        val xmlPullParser = Xml.newPullParser()
        val inputStream = ByteArrayInputStream(xml.toByteArray())
        try {
            xmlPullParser.setInput(inputStream, "utf-8")
            var type = xmlPullParser.eventType
            var t: T? = null
            while (type != XmlPullParser.END_DOCUMENT) {
                when (type) {
                    XmlPullParser.START_DOCUMENT -> list = ArrayList()
                    XmlPullParser.START_TAG -> if (tagName == xmlPullParser.name) {
                        t = clazz.newInstance()
                        val fields = clazz.declaredFields
                        for (field in fields) {
                            val fieldName = field.name
                            for (index in 0 until xmlPullParser.attributeCount) {
                                if (fieldName == xmlPullParser.getAttributeName(index)) {
                                    ReflectUtil.setFieldValue(
                                        t,
                                        field,
                                        fieldName,
                                        xmlPullParser.getAttributeValue(index)
                                    )
                                }
                            }
                        }
                    }
                    XmlPullParser.END_TAG -> if (tagName == xmlPullParser.name) {
                        t?.let { list!!.add(it) }
                    }
                    XmlPullParser.END_DOCUMENT -> {
                    }
                }
                type = xmlPullParser.next()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return list

    }

    /**
     * 3、获取Xml文件中的属性值
     * @param xml   xml文件字符串
     * @param tagName       标签名称
     * @param attributeName     属性名称
     * @return  返回获取的值，或者null
     */
    fun getTagAttribute(xml: String, tagName: String, attributeName: String): String? {
        require(!(TextUtils.isEmpty(tagName) || TextUtils.isEmpty(attributeName))) { "请填写标签名称或属性名称" }
        val xmlPullParser = Xml.newPullParser()
        val inputStream = ByteArrayInputStream(xml.toByteArray())
        try {
            xmlPullParser.setInput(inputStream, "utf-8")
            var type = xmlPullParser.eventType
            while (type != XmlPullParser.END_DOCUMENT) {
                when (type) {
                    XmlPullParser.START_TAG -> if (tagName == xmlPullParser.name) {
                        for (i in 0 until xmlPullParser.attributeCount) {
                            if (attributeName == xmlPullParser.getAttributeName(i)) {
                                return xmlPullParser.getAttributeValue(i)
                            }
                        }
                    }
                }
                type = xmlPullParser.next()
            }
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }
}
