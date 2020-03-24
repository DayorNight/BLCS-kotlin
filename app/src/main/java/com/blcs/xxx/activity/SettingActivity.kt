package com.blcs.xxx.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.blcs.common.demo.UI_Constants
import com.blcs.xxx.R

@Route(path = UI_Constants.LOGIN_SETTING_ACTIVITY )
class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
    }
}
