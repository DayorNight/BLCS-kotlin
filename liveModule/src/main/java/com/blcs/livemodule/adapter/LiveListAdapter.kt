package com.blcs.livemodule.adapter

import android.app.Activity
import android.os.Bundle
import com.blcs.common.utils.L
import com.blcs.livemodule.R
import com.blcs.livemodule.TXPlayerUtils
import com.blcs.livemodule.living.MLVBLiveRoom
import com.blcs.livemodule.widget.LiveManageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tencent.rtmp.ITXLivePlayListener
import com.tencent.rtmp.TXLiveConstants
import com.tencent.rtmp.TXLivePlayer
import com.tencent.rtmp.ui.TXCloudVideoView
import java.io.UnsupportedEncodingException

/**
 * @Author BLCS
 * @Time 2020/4/10 16:33
 */
class LiveListAdapter(var sharedInstance: MLVBLiveRoom) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.adapter_live_home),
    LiveManageView.OnLiveClickListener {
    var player: TXLivePlayer?=null
    val players = mutableListOf<TXLivePlayer>()
    val liveManageViews = mutableListOf<LiveManageView>()

    override fun convert(holder: BaseViewHolder, item: String) {
    }
    override fun flashlight(enable: Boolean){
        sharedInstance?.enableTorch(true)
    }
    override fun switchCamera() {
        sharedInstance?.switchCamera()
    }
    override fun finish() {
        (context as Activity).finish()
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        super.onViewAttachedToWindow(holder)
        L.e("=====启动item=======")
        val viewVideo= holder.getView<TXCloudVideoView>(R.id.video_view)
        player = TXPlayerUtils.init(context, viewVideo, "https://flv.6721.liveplay.now.qq.com/live/6721_c0c51d80e36bc70da35b405ecdbca4ff.flv")
        sharedInstance?.startLocalPreview(true,viewVideo)
        val lmvManager= holder.getView<LiveManageView>(R.id.lmv_manager)
        lmvManager.setOnLiveClickListener(this)
        //消息接收
        player?.setPlayListener(object : ITXLivePlayListener {
            override fun onPlayEvent(event: Int, param: Bundle) {
                L.e("======="+event)
                if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {
//                    roomListenerCallback.onDebugLog("[AnswerRoom] 拉流失败：网络断开")
//                    roomListenerCallback.onError(-1, "网络断开，拉流失败")
                } else if (event == TXLiveConstants.PLAY_EVT_GET_MESSAGE) {
                    var msg: String? = null
                    try {
                        msg = String(param.getByteArray(TXLiveConstants.EVT_GET_MSG)!!)
//                        roomListenerCallback.onRecvAnswerMsg(msg)
                    } catch (e: UnsupportedEncodingException) {
                        e.printStackTrace()
                    }
                }
            }
            override fun onNetStatus(status: Bundle) {}
        })

        players.add(player!!)
        liveManageViews.add(lmvManager)
    }

    override fun onViewRecycled(holder: BaseViewHolder) {
        super.onViewRecycled(holder)
        L.e("============")
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder) {
        super.onViewDetachedFromWindow(holder)
        L.e("=======结束item=====")

        players[0].stopPlay(true)
        players.removeAt(0)
        liveManageViews[0].onDestroy()
        liveManageViews.removeAt(0)
    }
}