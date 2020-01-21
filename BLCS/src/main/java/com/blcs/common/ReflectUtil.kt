package com.blcs.common

import android.text.TextUtils

import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.lang.reflect.Type

/**
 * Created by lwb on 2017/12/26.
 * TODO ReflectUtil反射工具类
 * 1、设置字段值
 * 2、把字段名称第一个字母换成大写
 * 3、根据字段名称获取指定Field字段
 * 4、根据字段名称获取指定的Field
 * 5、判断该字段是否为FieldName对应字段
 *
 */

object ReflectUtil {

    /**
     * 设置字段值
     * @param t     对应实体
     * @param field     字段
     * @param fieldName     字段名称
     * @param value         字段值
     */
    fun <T : Any> setFieldValue(t: T, field: Field, fieldName: String, value: String) {
        val name = field.name
        //判断该字段是否和目标字段相同
        if (fieldName != name) {
            return
        }
        //获取字段的类型
        val type = field.type
        //获取字段的修饰符号码
        val typeCode = field.modifiers
        //获取字段类型的名称
        val typeName = type.toString()
        try {
            when (typeName) {
                "class java.lang.String" -> if (Modifier.isPublic(typeCode)) {
                    field.set(t, value)
                } else {
                    val method =
                        t.javaClass.getMethod("set" + getMethodName(fieldName), String::class.java)
                    method.invoke(t, value)
                }
                "double" -> if (Modifier.isPublic(typeCode)) {
                    field.setDouble(t, java.lang.Double.valueOf(value))
                } else {
                    val method = t.javaClass.getMethod(
                        "set" + getMethodName(fieldName),
                        Double::class.javaPrimitiveType
                    )
                    method.invoke(t, java.lang.Double.valueOf(value))
                }
                "int" -> if (Modifier.isPublic(typeCode)) {
                    field.setInt(t, Integer.valueOf(value))
                } else {
                    val method = t.javaClass.getMethod(
                        "set" + getMethodName(fieldName),
                        Int::class.javaPrimitiveType
                    )
                    method.invoke(t, Integer.valueOf(value))
                }
                "float" -> if (Modifier.isPublic(typeCode)) {
                    field.setFloat(t, java.lang.Float.valueOf(value))
                } else {
                    val method = t.javaClass.getMethod(
                        "set" + getMethodName(fieldName),
                        Float::class.javaPrimitiveType
                    )
                    method.invoke(t, java.lang.Float.valueOf(value))
                }
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 2、把字段名称第一个字母换成大写
     * @param fieldName     字段名称
     * @throws Exception    异常处理
     */
    @Throws(Exception::class)
    private fun getMethodName(fieldName: String): String {
        val items = fieldName.toByteArray()
        items[0] = (items[0].toChar() - 'a' + 'A'.toInt()).toByte()
        return String(items)
    }

    /**
     * 3、根据字段名称获取指定Field字段
     * @param clazz        实体的字节码文件
     * @param filedName        字段的名称
     * @return 返回对应的字符按Field或者返回null
     */
    fun getField(clazz: Class<*>?, filedName: String): Field? {
        require(!(clazz == null || TextUtils.isEmpty(filedName))) { "params is illegal" }
        val fields = clazz.declaredFields
        return getFieldByName(fields, filedName)
    }

    /**
     * 4、根据字段名称获取指定的Field
     * @param fields   字段集合
     * @param fieldName     字段名称
     * @return 返回对应的Field字段或者返回null
     */
    fun getFieldByName(fields: Array<Field>?, fieldName: String): Field? {
        require(!(fields == null || fields.size == 0 || TextUtils.isEmpty(fieldName))) { "params is illegal" }
        for (field in fields) {
            val name = field.name
            //判断该字段是否和目标字段相同
            if (fieldName == name) {
                return field
            }
        }
        return null
    }

    /**
     * 5、判断该字段是否为FieldName对应字段
     * @param field        Field字段
     * @param fieldName        目标字段
     * @return 是，返回true；否，返回false
     */
    fun isFiledWithName(field: Field?, fieldName: String): Boolean {
        require(!(field == null || TextUtils.isEmpty(fieldName))) { "params is illegal" }
        return if (fieldName == field.name) {
            true
        } else false
    }
}
