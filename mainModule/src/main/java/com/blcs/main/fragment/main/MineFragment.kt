package com.blcs.main.fragment.main

import androidx.recyclerview.widget.LinearLayoutManager
import com.blcs.common.Base.BaseFragment
import com.blcs.main.R
import com.blcs.main.databinding.FragmentMineBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * 个人中心
 * @Author BLCS
 * @Time 2020/4/7 12:04
 */
class MineFragment: BaseFragment<FragmentMineBinding>() {
    override fun setLayout() = R.layout.fragment_mine
    private val titles = mutableListOf("签到","邀请","收藏","反馈","关于我们","设置")
    private val mAdapter by lazy{
       object : BaseQuickAdapter<String,BaseViewHolder>(R.layout.adapter_mine){
           override fun convert(holder: BaseViewHolder, item: String) {
               holder.setText(R.id.tv_home_subtitle,item)
           }
       }
    }
    override fun initUI() {
        rv_mine.layoutManager = LinearLayoutManager(activity)
        rv_mine.adapter = mAdapter
        mAdapter.setNewInstance(titles)
    }
}