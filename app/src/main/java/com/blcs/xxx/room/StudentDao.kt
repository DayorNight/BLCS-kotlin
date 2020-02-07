package com.blcs.xxx.room

import androidx.paging.DataSource
import androidx.room.*
import com.blcs.xxx.bean.Student

/**
 * 创建访问数据库的方法
 */
@Dao
interface StudentDao {

    @Query("SELECT * FROM Student ORDER BY name COLLATE NOCASE ASC")
    fun getAllDatas(): DataSource.Factory<Int, Student>

    @Insert
    fun insertOne(student: Student)

    @Insert
    fun insertArray(student: List<Student>)

    @Delete
    fun deleteOne(student: Student)

    @Update
    fun update(student: Student)
}
