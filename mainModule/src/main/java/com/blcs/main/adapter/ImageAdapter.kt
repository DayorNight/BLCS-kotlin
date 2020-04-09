package com.blcs.main.adapter

import android.view.ViewGroup
import android.widget.ImageView
import com.youth.banner.adapter.BannerAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blcs.common.utils.L
import com.blcs.main.R

/**
 * 轮播图
 * @Author BLCS
 * @Time 2020/4/7 16:18
 */
class ImageAdapter(datas: MutableList<String>?) : BannerAdapter<String, ImageAdapter.BannerViewHolder>(datas){

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent?.getContext())
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }

    override fun onBindView(holder: BannerViewHolder?, data: String?, position: Int, size: Int) {
        holder?.imageView?.setImageResource(R.mipmap.ic_app512)
    }

    inner class BannerViewHolder(var imageView: ImageView) :
        RecyclerView.ViewHolder(imageView)


}
