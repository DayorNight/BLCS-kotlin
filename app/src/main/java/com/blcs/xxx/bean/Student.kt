package com.blcs.xxx.bean

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * 通过@Entity 注解 建立一个表
 */
@Entity
data class Student(
    @PrimaryKey(autoGenerate = true)
    var id:Int =0,
    var name: String?
)
