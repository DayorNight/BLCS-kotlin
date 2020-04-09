package com.blcs.main.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.blcs.main.R
import com.blcs.main.bean.RecommendDatas
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * 推荐列表 UI
 * @Author BLCS
 * @Time 2020/4/8 14:50
 */
class RecommendListAdapter : BaseMultiItemQuickAdapter<RecommendDatas, BaseViewHolder>() {
    var mAdapter: RecommendShortVideoAdapter
    init {
        addItemType(0, R.layout.adapter_recommend_list_video)
        addItemType(1, R.layout.adapter_recommend_list_ad)
        addItemType(2, R.layout.adapter_recommend_list_article)
        addItemType(3, R.layout.adapter_recommend_list_shortvideo)
        addItemType(4, R.layout.adapter_recommend_list_live)
        mAdapter = RecommendShortVideoAdapter()
    }
    override fun convert(holder: BaseViewHolder, item: RecommendDatas) {
        when(item.itemType){
            0 -> {}
            1 -> {}
            2 -> {}
            3 -> {
                val rv = holder.getView<RecyclerView>(R.id.rv_short_video)
                rv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                rv.adapter = mAdapter
                mAdapter.setNewInstance(mutableListOf("1","2","3","4","5","6"))
            }
            4 -> {}
        }
    }

}