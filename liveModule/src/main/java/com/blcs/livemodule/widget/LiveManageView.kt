package com.blcs.livemodule.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.blcs.common.utils.L
import com.blcs.common.utils.ScreenUtils
import com.blcs.livemodule.R
import com.blcs.livemodule.adapter.ChatListAdapter
import com.blcs.livemodule.adapter.ClientHeaderAdapter
import kotlinx.android.synthetic.main.view_live_manage.view.*
import org.jetbrains.anko.toast


/**
 * 直播管理操作
 * @Author BLCS
 * @Time 2020/4/10 17:54
 */
class LiveManageView : ConstraintLayout {
    constructor(ctx: Context) : super(ctx)
    private var screenWidth: Int?=null
    private var headerAdapter = ClientHeaderAdapter()
    private var chatListAdapter = ChatListAdapter()
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
    init {
        screenWidth = ScreenUtils.getScreenWidth(context)

        LayoutInflater.from(context).inflate(R.layout.view_live_manage, this, true)
        /*观众列表*/
        rv_client.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        rv_client.adapter = headerAdapter
        headerAdapter.setNewInstance(mutableListOf(1,2,3,4))
        /*聊天列表*/
        rv_chat_info.layoutManager = LinearLayoutManager(context)
        rv_chat_info.adapter = chatListAdapter
        chatListAdapter.setNewInstance(mutableListOf(1,2,3,4,5,6,2,3,4,5,6,3,4,5,6))
        initClick()
    }

    fun getRvChat()  = rv_chat_info

    /**
     * 按钮事件
     */
    private fun initClick() {
        /*关闭页面*/
        iv_close.setOnClickListener { context.toast("关闭页面") }
        /*分享*/
        iv_share.setOnClickListener {context.toast("分享")  }
        /*礼物*/
        iv_gift.setOnClickListener { context.toast("礼物") }
        /*聊天输入*/
        tv_input.setOnClickListener { context.toast("聊天输入") }
        /*小时榜*/
        tv_hours.setOnClickListener {context.toast("小时榜")  }
        /*关注*/
        tv_attention.setOnClickListener { context.toast("关注") }
        /*头像*/
        iv_avator.setOnClickListener { context.toast("头像") }
    }

}