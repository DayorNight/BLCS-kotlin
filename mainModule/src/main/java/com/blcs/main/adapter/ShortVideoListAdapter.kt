package com.blcs.main.adapter

import androidx.core.view.get
import com.blcs.common.utils.L
import com.blcs.main.R
import com.blcs.main.bean.ShortVideo
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tencent.liteav.demo.play.SuperPlayerModel
import com.tencent.liteav.demo.play.SuperPlayerView
import kotlinx.android.synthetic.main.activity_video_playing.*

/**
 * @Author BLCS
 * @Time 2020/4/22 16:49
 */
class ShortVideoListAdapter: BaseQuickAdapter<ShortVideo,BaseViewHolder>(R.layout.adapter_short_video) {
//    var views = mutableListOf<SuperPlayerView>()
    var views  = mutableMapOf<Int,SuperPlayerView>()
    var model: SuperPlayerModel? =null
    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        super.onViewAttachedToWindow(holder)
        val adapterPosition = holder.adapterPosition
        L.e("========="+adapterPosition)
        val playerView = holder.getView<SuperPlayerView>(R.id.player_video)
        playerView.playWithModel(model)
        views[adapterPosition] = playerView

    }

    override fun convert(holder: BaseViewHolder, item: ShortVideo) {
        val adapterPosition = holder.adapterPosition
        L.e("========="+adapterPosition)
        model = SuperPlayerModel()
//      model.url = item.videoUrl
        model?.url = "http://200024424.vod.myqcloud.com/200024424_709ae516bdf811e6ad39991f76a4df69.f20.mp4"
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder) {
        super.onViewDetachedFromWindow(holder)
        val adapterPosition = holder.adapterPosition
        L.e("========="+adapterPosition)
        val superPlayerView = views[adapterPosition]
        superPlayerView?.resetPlayer()
        views.remove(adapterPosition)
    }

    fun onPause(){
        views.forEach {
            it.value.onPause()
        }
    }

    fun onResume(){
        views.forEach {
            it.value.onResume()
        }
    }

    fun onDestory(){
        views.clear()
    }
}