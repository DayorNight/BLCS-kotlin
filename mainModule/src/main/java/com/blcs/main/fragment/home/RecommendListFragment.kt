package com.blcs.main.fragment.home

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.blcs.common.Base.BaseFragment
import com.blcs.common.utils.L
import com.blcs.main.R
import com.blcs.main.adapter.RecommendListAdapter
import com.blcs.main.bean.RecommendDatas
import com.blcs.main.databinding.FragmentRecommendBinding
import kotlinx.android.synthetic.main.fragment_recommend.*
import org.jetbrains.anko.support.v4.toast

/**
 * 推荐列表
 * @Author BLCS
 * @Time 2020/4/7 11:53
 */
class RecommendListFragment: BaseFragment<FragmentRecommendBinding>() {

    private var pos: Int? =null
    private var mutableListOf: MutableList<RecommendDatas>? = null
    override fun setLayout() = R.layout.fragment_recommend
    fun newInstance(pos: Int) = RecommendListFragment().apply {
        val bundle = Bundle()
        bundle.putInt("POS",pos)
        arguments =bundle
    }
    override fun initUI() {
        initData()
        refresh_recommend.setOnRefreshListener {
            toast("刷新")
            refresh_recommend.isRefreshing = false
        }
        rv_recommend.layoutManager = LinearLayoutManager(context)
        val mAdapter = RecommendListAdapter()
        rv_recommend.adapter = mAdapter
        when(pos){
            0,1 ->{
                val mHeader = layoutInflater.inflate(R.layout.header_recommend,null)
                mAdapter.addHeaderView(mHeader)
            }
        }
        mAdapter.setNewInstance(mutableListOf)
    }

    private fun initData() {
        pos = arguments?.getInt("POS")
        L.e("======$pos")
        when(pos){
            0 ->{
                /*推荐*/
                mutableListOf = mutableListOf(
                    RecommendDatas(0),
                    RecommendDatas(1),
                    RecommendDatas(2),
                    RecommendDatas(3),
                    RecommendDatas(4),
                    RecommendDatas(0),
                    RecommendDatas(1),
                    RecommendDatas(2)
                )
            }
            1 ->{/*热门*/
                mutableListOf = mutableListOf(
                    RecommendDatas(0),
                    RecommendDatas(4),
                    RecommendDatas(0),
                    RecommendDatas(4),
                    RecommendDatas(4),
                    RecommendDatas(4),
                    RecommendDatas(0),
                    RecommendDatas(4)
                )
            }
            2 ->{/*短视频*/}
            3 ->{/*直播*/}
            4 ->{/*其他*/
                mutableListOf = mutableListOf(
                    RecommendDatas(3),
                    RecommendDatas(3),
                    RecommendDatas(3),
                    RecommendDatas(3),
                    RecommendDatas(3),
                    RecommendDatas(3),
                    RecommendDatas(3)
                )
            }
        }

    }
}