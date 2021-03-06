package com.blcs.xxx.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.blcs.common.utils.HandleBackUtil
import com.blcs.common.utils.XStatusBar
import com.blcs.common.utils.spreadFun.toHome
import com.blcs.xxx.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        XStatusBar.setTransparent(this)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    }

    override fun onBackPressed() {
        if (!HandleBackUtil.handleBackPress(this)){
            toHome()
        }
    }
}
