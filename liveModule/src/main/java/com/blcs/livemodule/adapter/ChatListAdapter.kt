package com.blcs.livemodule.adapter

import com.blcs.livemodule.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @Author BLCS
 * @Time 2020/4/13 11:03
 */
class ChatListAdapter : BaseQuickAdapter<Int, BaseViewHolder>(R.layout.adapter_chat_list) {
    override fun convert(holder: BaseViewHolder, item: Int) {
        holder.setText(R.id.tv_name,"位置："+holder.adapterPosition)
    }
}