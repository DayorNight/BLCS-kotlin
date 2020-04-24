package com.blcs.main.fragment.home

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.blcs.common.Base.BaseFragment
import com.blcs.common.BuildConfig
import com.blcs.common.demo.UI_Constants
import com.blcs.common.demo.UI_Constants.URL_COMMON
import com.blcs.common.demo.WebActivity
import com.blcs.common.utils.L
import com.blcs.main.R
import com.blcs.main.ShortVideoActivity
import com.blcs.main.VideoPlayingActivity
import com.blcs.main.ViewModel.RecommendListModel
import com.blcs.main.adapter.CardListAdapter
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
    private var mAdapter: RecommendListAdapter? = null
    private var mCardAdapter: CardListAdapter? = null
    private var cards: MutableList<String>?=null
    private val viewMode by lazy(LazyThreadSafetyMode.NONE){
         ViewModelProvider(this).get(RecommendListModel::class.java)
    }

    fun newInstance(pos: Int) = RecommendListFragment().apply {
        val bundle = Bundle()
        bundle.putInt("POS",pos)
        arguments =bundle
    }
    override fun initUI() {
        pos = arguments?.getInt("POS")
        viewMode.getHomeListDatas(pos)
        initRv()

        viewMode.datas.observe(this, Observer<MutableList<RecommendDatas>> {
            mAdapter?.setNewInstance(it)
        })
        viewMode.cardDatas.observe(this, Observer<MutableList<String>> {
            mCardAdapter?.setNewInstance(it)
        })
        refresh_recommend.setOnRefreshListener {
            toast("刷新")
            refresh_recommend.isRefreshing = false
        }
    }

    private fun initRv() {
        when (pos) {
            0,1,4 -> {
                mAdapter = RecommendListAdapter()
                rv_recommend.layoutManager = LinearLayoutManager(context)
                val mHeader = layoutInflater.inflate(R.layout.header_recommend, null)
                mHeader.setOnClickListener {
                    WebActivity.start(context,null,URL_COMMON)
                }
                mAdapter?.addHeaderView(mHeader)
                rv_recommend.adapter = mAdapter
                mAdapter?.setNewInstance(mutableListOf)
            }
            2,3 ->{
                mCardAdapter = CardListAdapter()
                rv_recommend.layoutManager = GridLayoutManager(context,2)
                rv_recommend.adapter = mCardAdapter
                mCardAdapter?.setNewInstance(cards)
            }
        }
        mCardAdapter?.setOnItemClickListener { _, _, _ ->
            if (BuildConfig.isModule) {
               toast("详情")
            }else{
                if(pos==3){
                    ARouter.getInstance().build(UI_Constants.LIVE_HOME_ACTIVITY).navigation()
                }else{

                }
            }
        }

        mAdapter?.setOnItemClickListener { adapter, _, position ->
           val itemViewType = adapter.getItemViewType(position+1)
            L.e("========itemViewType "+itemViewType)
            when(itemViewType){
                0 ->{ //视频
                    startActivity(Intent(context, VideoPlayingActivity::class.java))
                }
                1 ->{//广告
                    WebActivity.start(context,null,URL_COMMON)
                }
                2 ->{

                }
                3 ->{
                    startActivity(Intent(context,ShortVideoActivity::class.java))
                }
                4 ->{

                }
                5 ->{

                }
            }
        }
    }

}