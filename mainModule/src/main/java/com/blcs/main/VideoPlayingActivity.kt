package com.blcs.main
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.blcs.common.Base.BaseActivity
import com.blcs.common.demo.UI_Constants
import com.blcs.main.adapter.VideoPlayingAdapter
import com.tencent.liteav.demo.play.SuperPlayerGlobalConfig
import com.tencent.liteav.demo.play.SuperPlayerModel
import com.tencent.liteav.demo.play.SuperPlayerView
import kotlinx.android.synthetic.main.activity_video_playing.*

/**
 * 视频详情播放
 */
@Route(path = UI_Constants.MAIN_VIDEO_PLAYING_ACTIVITY)
class VideoPlayingActivity : BaseActivity() {
    var adapter = VideoPlayingAdapter()
    override fun setLayout() = R.layout.activity_video_playing

    override fun initUI() {
        initPlayer()

        rv_recommend.layoutManager = LinearLayoutManager(this)
        rv_recommend.adapter = adapter
        val view = layoutInflater.inflate(R.layout.view_video_header, null)
        adapter.addHeaderView(view)
        adapter.setNewInstance(mutableListOf(1,2,3,4,5,6,7,8,9))
    }

    private fun initPlayer() {
        val model = SuperPlayerModel()
        model.url =
            "http://200024424.vod.myqcloud.com/200024424_709ae516bdf811e6ad39991f76a4df69.f20.mp4"
        player_view.playWithModel(model)
        player_view.setPlayerViewCallback(object : SuperPlayerView.OnSuperPlayerViewCallback {
            override fun onClickSmallReturnBtn() {
                finish()
            }

            override fun onStartFullScreenPlay() {

            }

            override fun onStopFullScreenPlay() {
            }

            override fun onClickFloatCloseBtn() {
            }

            override fun onStartFloatWindowPlay() {
            }
        })
        // 播放器配置
        val prefs = SuperPlayerGlobalConfig.getInstance()
        // 开启悬浮窗播放
        prefs.enableFloatWindow = false
    }

    override fun onDestroy() {
        super.onDestroy()
        player_view.resetPlayer()
    }

    override fun onResume() {
        super.onResume()
        player_view.onResume()
    }

    override fun onPause() {
        super.onPause()
        player_view.onPause()
    }

}
