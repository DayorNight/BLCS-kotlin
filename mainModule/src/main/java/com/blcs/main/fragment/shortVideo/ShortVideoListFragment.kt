package com.blcs.main.fragment.shortVideo

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.blcs.common.Base.BaseFragment
import com.blcs.main.R
import com.blcs.main.adapter.ShortVideoListAdapter
import com.blcs.main.bean.ShortVideo
import com.blcs.main.databinding.FragmentShortVideoBinding
import kotlinx.android.synthetic.main.fragment_short_video.*

/**
 * @Author BLCS
 * @Time 2020/4/22 11:27
 */
class ShortVideoListFragment: BaseFragment<FragmentShortVideoBinding>(){
    var mAdapter =  ShortVideoListAdapter()
    companion object{
        fun getInstance()=
            ShortVideoListFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }
    override fun setLayout() = R.layout.fragment_short_video

    override fun initUI() {
        refresh_rv.setOnRefreshListener { refresh_rv.isRefreshing = false }
        rv_video_list.layoutManager = LinearLayoutManager(activity)
        rv_video_list.adapter = mAdapter
        PagerSnapHelper().attachToRecyclerView(rv_video_list)
        initData()
    }

    private fun initData() {
        val shortVideo = ShortVideo("https://fwres.oss-cn-hangzhou.aliyuncs.com/upload/videos/s-videos/503450/mp4_1582117606_Dcy7b1GVio.mp4", "0001")
        val shortVideo1 = ShortVideo("https://fwres.oss-cn-hangzhou.aliyuncs.com/upload/videos/s-videos/503450/mp4_1582117606_Dcy7b1GVio.mp4", "0001")
        val shortVideo2 = ShortVideo("https://fwres.oss-cn-hangzhou.aliyuncs.com/upload/videos/s-videos/503450/mp4_1582117606_Dcy7b1GVio.mp4", "0001")
        val shortVideo3 = ShortVideo("https://fwres.oss-cn-hangzhou.aliyuncs.com/upload/videos/s-videos/503450/mp4_1582117606_Dcy7b1GVio.mp4", "0001")
        val mutableListOf = mutableListOf(shortVideo,shortVideo1,shortVideo2,shortVideo3)
        mAdapter.setNewInstance(mutableListOf)
    }

    override fun onPause() {
        super.onPause()
        mAdapter.onPause()
    }
    override fun onResume() {
        super.onResume()
        mAdapter.onResume()
    }
    override fun onDestroy() {
        super.onDestroy()
        mAdapter.onDestory()
    }
}
