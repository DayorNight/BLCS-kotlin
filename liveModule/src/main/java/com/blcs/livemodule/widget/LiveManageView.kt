package com.blcs.livemodule.widget

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.blcs.common.utils.L
import com.blcs.common.utils.ScreenUtils
import com.blcs.livemodule.R
import com.blcs.livemodule.adapter.ChatListAdapter
import com.blcs.livemodule.adapter.ClientHeaderAdapter
import com.blcs.livemodule.common.msg.TCChatEntity
import com.blcs.livemodule.common.msg.TCSimpleUserInfo
import com.blcs.livemodule.living.MLVBLiveRoom
import com.blcs.livemodule.widget.living.danmaku.TCDanmuMgr
import com.tencent.rtmp.TXLivePusher
import kotlinx.android.synthetic.main.view_live_manage.view.*
import master.flame.danmaku.controller.IDanmakuView
import org.jetbrains.anko.toast
import java.io.UnsupportedEncodingException
import java.util.*


/**
 * 直播管理操作
 * @Author BLCS
 * @Time 2020/4/10 17:54
 */
class LiveManageView : ConstraintLayout, TCInputTextMsgDialog.OnTextSendListener {

    //直播端右下角listview显示type
    companion object{
        val TEXT_TYPE:Int =0
        val MEMBER_ENTER = 1
        val MEMBER_EXIT = 2
        val PRAISE = 3
    }

    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

    private lateinit var onClickListener: OnLiveClickListener
    private val mArrayListChatEntity = ArrayList<TCChatEntity>() // 消息列表集合
    private val mHandler = Handler(Looper.getMainLooper())
    private var screenWidth: Int? = null
    private var mAvatarListAdapter = ClientHeaderAdapter()
    private var chatListAdapter = ChatListAdapter()
    private var mDanmuMgr: TCDanmuMgr? = null
    private var activity: Activity? = null
    private var isClient: Boolean = true
    private var mInputTextMsgDialog: TCInputTextMsgDialog?=null

    init {
        activity = context as Activity

        screenWidth = ScreenUtils.getScreenWidth(context)
        LayoutInflater.from(context).inflate(R.layout.view_live_manage, this, true)
        /*观众列表*/
        rv_client.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_client.adapter = mAvatarListAdapter
        mAvatarListAdapter.setNewInstance(mutableListOf(1, 2, 3, 4))
        /*聊天列表*/
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.stackFromEnd = true
        rv_chat_info.layoutManager = linearLayoutManager
        rv_chat_info.adapter = chatListAdapter
        initClick()

        /* 弹幕 */
        initDanmaku()

        /*消息输入框*/
        mInputTextMsgDialog = TCInputTextMsgDialog(activity, R.style.InputDialog)
        mInputTextMsgDialog?.setmOnTextSendListener(this)

        /*UI*/
        ll_client.visibility = if(isClient) View.VISIBLE else View.GONE
        ll_anchor.visibility = if(isClient) View.GONE else View.VISIBLE
    }

    private fun initDanmaku() {
        dv_danmaku.visibility = View.VISIBLE
        mDanmuMgr = TCDanmuMgr(context)
        mDanmuMgr?.setDanmakuView(dv_danmaku)

    }

    fun getRvChat() = rv_chat_info

    /**
     * 按钮事件
     */
    private fun initClick() {
        /*关闭页面*/
        iv_close.setOnClickListener {   onClickListener?.finish()  }
        /*分享*/
        iv_share.setOnClickListener { context.toast("分享") }
        /*礼物*/
        iv_gift.setOnClickListener { context.toast("礼物") }
        /*聊天输入*/
        tv_input.setOnClickListener {
            showInputMsgDialog()
        }
        /*小时榜*/
        tv_hours.setOnClickListener { context.toast("小时榜") }
        /*关注*/
        tv_attention.setOnClickListener { context.toast("关注") }
        /*头像*/
        iv_avator.setOnClickListener { context.toast("头像") }

        /*点赞*/
        iv_like.setOnClickListener { heart_layout.addFavor() }
        /*闪光灯*/
        iv_flashlight.setOnClickListener {
            onClickListener?.flashlight(true)
        }
        /*反转相机*/
        iv_reverse_camera.setOnClickListener {
            onClickListener?.switchCamera()
        }
        /*美颜*/
        iv_beauty.setOnClickListener {
            beauty_panel.visibility =  if (beauty_panel.visibility== View.VISIBLE) View.GONE else View.VISIBLE
        }
        /*声控*/
        iv_voice.setOnClickListener {

        }
    }

    /**
     * 发消息弹出框
     */
    private fun showInputMsgDialog() {
        val windowManager = activity?.windowManager
        val display = windowManager?.defaultDisplay
        val lp = mInputTextMsgDialog?.window?.attributes
        lp?.width = display?.width!! //设置宽度
        mInputTextMsgDialog?.window?.attributes =lp
        mInputTextMsgDialog?.setCancelable(true)
        mInputTextMsgDialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        mInputTextMsgDialog?.show()
    }

    /**
     * 发送消息处理
     */
    override fun onTextSend(msg: String?, tanmuOpen: Boolean) {
        if (msg?.length == 0) return

        try {
            val byte_num = msg?.toByteArray(charset("utf8"))
            if (byte_num?.size!! > 160) {
                context.toast("请输入内容")
                return
            }
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            return
        }
        //消息回显
        val entity = TCChatEntity()
        entity.senderName = "我:"
        entity.content = msg
        entity.type = TEXT_TYPE
        val name = TCSimpleUserInfo("", "我", "")
        if (tanmuOpen) {//判断是否打开弹幕
            if (mDanmuMgr != null) {
                mDanmuMgr?.addDanmu("", "昵称", msg)
            }
            handleTextMsg(name, msg)
        } else {
            handleTextMsg(name, msg)
        }
    }

    /**
     * 更新消息列表控件
     *
     * @param entity
     */
    private fun notifyMsg(entity: TCChatEntity) {
        mHandler.post(Runnable {
            if (mArrayListChatEntity.size > 1000) {
                while (mArrayListChatEntity.size > 900) {
                    mArrayListChatEntity.removeAt(0)
                }
            }

            mArrayListChatEntity.add(entity)
            chatListAdapter.addData(entity)
        })
    }

    /**
     * 观众进房消息
     */
    fun handleAudienceJoinMsg(userInfo: TCSimpleUserInfo) {
        //左下角显示用户加入消息
        val entity = TCChatEntity()
        entity.senderName = "通知"
        if (TextUtils.isEmpty(userInfo.nickname))
            entity.content = userInfo.userid + "加入直播"
        else
            entity.content = userInfo.nickname + "加入直播"
        entity.type = MEMBER_ENTER
        notifyMsg(entity)
    }

    /**
     * 观众退房消息
     */
    fun handleAudienceQuitMsg(userInfo: TCSimpleUserInfo) {
        val entity = TCChatEntity()
        entity.senderName = "通知"
        if (TextUtils.isEmpty(userInfo.nickname))
            entity.content = userInfo.userid + "退出直播"
        else
            entity.content = userInfo.nickname + "退出直播"
        entity.type = MEMBER_EXIT
        notifyMsg(entity)
    }

    /**
     * 收到文本消息
     */
    fun handleTextMsg(userInfo: TCSimpleUserInfo, text: String) {
        val entity = TCChatEntity()
        entity.senderName = userInfo.nickname
        entity.content = text
        entity.type = TEXT_TYPE
        notifyMsg(entity)
    }

    fun onResume() {
        mDanmuMgr?.resume()
    }

    fun onPause() {
        mDanmuMgr?.pause()
    }

    fun onDestroy() {
        mDanmuMgr?.destroy()
        mDanmuMgr = null
    }

    interface OnLiveClickListener{
        fun  flashlight(enable: Boolean)
        fun  switchCamera()
        fun  finish()
    }
    fun setOnLiveClickListener(listener: OnLiveClickListener){
        onClickListener = listener
    }

}