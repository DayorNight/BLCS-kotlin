package com.blcs.common.Base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @Author BLCS
 * @Time 2020/4/2 9:21
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayout())
        initUI()
    }

    abstract fun setLayout():Int
    abstract fun initUI()
}