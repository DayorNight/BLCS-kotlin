package com.blcs.livemodule.adapter

import android.os.Bundle
import com.blcs.common.utils.L
import com.blcs.livemodule.R
import com.blcs.livemodule.TXPlayerUtils
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
class LiveListAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.adapter_live_home) {
    var player: TXLivePlayer?=null
    override fun convert(holder: BaseViewHolder, item: String) {
        val viewVideo= holder.getView<TXCloudVideoView>(R.id.video_view)
        val lmv_manager= holder.getView<LiveManageView>(R.id.lmv_manager)
        player = TXPlayerUtils.init(context, viewVideo,item)
//      //消息接收
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
    }

}