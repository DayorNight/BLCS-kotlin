package com.blcs.livemodule.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.blcs.livemodule.R

/**
 * 直播管理操作
 * @Author BLCS
 * @Time 2020/4/10 17:54
 */
class LiveManageView : ConstraintLayout {
    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
    init {
        val inflate =LayoutInflater.from(context).inflate(R.layout.view_live_manage, this, true)
    }
}