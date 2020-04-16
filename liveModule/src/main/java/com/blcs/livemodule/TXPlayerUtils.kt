package com.blcs.livemodule

import android.content.Context
import com.tencent.rtmp.TXLiveConstants
import com.tencent.rtmp.TXLivePlayer
import com.tencent.rtmp.ui.TXCloudVideoView
import com.tencent.rtmp.TXLivePlayConfig


/**
 * https://cloud.tencent.com/document/product/454/7886#step-8.3A-.E5.B1.8F.E5.B9.95.E6.88.AA.E5.9B.BE
 * @Author BLCS
 * @Time 2020/4/9 17:41
 */
object TXPlayerUtils {

    fun init(context: Context, view: TXCloudVideoView,flvUrl: String) = TXLivePlayer(context).apply {
        //关键 player 对象与界面 view
        setPlayerView(view)
        startPlay(flvUrl, TXLivePlayer.PLAY_TYPE_LIVE_FLV) //推荐 FLV
        // 铺满or适应
        setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN)
        // 设置画面渲染方向
        setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT)
    }

    /* 屏幕截图*/
    fun snapshot() {
//        mLivePlayer.snapshot(object : ITXSnapshotListener() {
//            fun onSnapshot(bmp: Bitmap?) {
//                if (null != bmp) {
//                    //获取到截图 bitmap
//                }
//            }
//        })
    }

    /* 截流录制*/
    fun VideoRecord() {
//        //指定一个 ITXVideoRecordListener 用于同步录制的进度和结果
//        mLivePlayer.setVideoRecordListener(recordListener);
//        //启动录制，可放于录制按钮的响应函数里，目前只支持录制视频源，弹幕消息等等目前还不支持
//        mLivePlayer.startRecord(int recordType)
//
//        //结束录制，可放于结束按钮的响应函数里
//        mLivePlayer.stopRecord();
    }

    /*切换播放源*/
    fun chuangeURl(){
//        网络情况在不断发生变化。在网络较差的情况下，最好适度降低画质，以减少卡顿
        // 正在播放的是流http://5815.liveplay.myqcloud.com/live/5815_62fe94d692ab11e791eae435c87f075e.flv，
        // 现切换到码率为900kbps的新流上
        //mLivePlayer.switchStream("http://5815.liveplay.myqcloud.com/live/5815_62fe94d692ab11e791eae435c87f075e_900.flv");
    }

    /*直播回看*/
    fun backLook(){
        // 设置直播回看前，先调用startPlay
// 开始播放 ...
//        TXLiveBase.setAppID("1253131631"); // 配置appId
//        mLivePlayer.prepareLiveSeek();     // 后台请求直播起始时间
//        mLivePlayer.seek(600); // 从第10分钟开始播放
    }
    /*延时调节*/
    fun delayAdjust(){
        val mPlayConfig = TXLivePlayConfig()
//
//自动模式
        mPlayConfig.setAutoAdjustCacheTime(true)
        mPlayConfig.setMinAutoAdjustCacheTime(1f)
        mPlayConfig.setMaxAutoAdjustCacheTime(5f)
//
//极速模式
        mPlayConfig.setAutoAdjustCacheTime(true)
        mPlayConfig.setMinAutoAdjustCacheTime(1f)
        mPlayConfig.setMaxAutoAdjustCacheTime(1f)
//
//流畅模式
        mPlayConfig.setAutoAdjustCacheTime(false)
        mPlayConfig.setMinAutoAdjustCacheTime(5f)
        mPlayConfig.setMaxAutoAdjustCacheTime(5f)
//
//        mLivePlayer.setConfig(mPlayConfig)
//设置完成之后再启动播放
    }
}