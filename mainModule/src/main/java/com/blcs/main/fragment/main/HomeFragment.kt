package com.blcs.main.fragment.main

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentPagerAdapter
import com.blcs.common.Base.BaseFragment
import com.blcs.main.R
import com.blcs.main.adapter.HomeAdater
import com.blcs.main.adapter.ImageAdapter
import com.blcs.main.databinding.FragmentHomeBinding
import com.blcs.main.fragment.home.RecommendListFragment
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_home.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView
import com.youth.banner.config.IndicatorConfig
import org.jetbrains.anko.support.v4.toast


/**
 * 首页
 * @Author BLCS
 * @Time 2020/4/7 11:53
 */
class HomeFragment: BaseFragment<FragmentHomeBinding>() {

    val titles= listOf("推荐","热门","短视频","直播","其他")
    var datas: MutableList<String>? =null
    override fun setLayout() = R.layout.fragment_home
    override fun initUI() {
//        /*广告*/
        banner_home.apply {
            datas = mutableListOf("1","2","3","4")
            adapter  = ImageAdapter(datas)
            setOrientation(Banner.HORIZONTAL)
            indicator = CircleIndicator(activity)
            setIndicatorSelectedColor(Color.GREEN)
            setIndicatorNormalColor(Color.WHITE)
            setIndicatorGravity(IndicatorConfig.Direction.CENTER)
            isAutoLoop(true)
            setBannerRound(150f)
            setOnBannerListener { data, position ->
                toast("点击位置："+position+" data: "+data)
            }
        }
        /*导航栏*/
        indicator_home.apply {
            setBackgroundColor(Color.WHITE)
            val commonNavigator = CommonNavigator(activity)
            commonNavigator.apply {
                adapter = object: CommonNavigatorAdapter(){
                    override fun getTitleView(context: Context?, index: Int) =
                        SimplePagerTitleView(context).apply {
                            text = titles[index]
                            setPadding(50,0,50,0)
                            normalColor = ContextCompat.getColor(getContext(),R.color.colorPrimary)
                            selectedColor = Color.WHITE
                            setOnClickListener {
                                vp_home.currentItem = index
                            }

                        }
                    override fun getCount() = titles.size

                    override fun getIndicator(context: Context?) = WrapPagerIndicator(context).apply {
                        fillColor = ContextCompat.getColor(getContext(),R.color.green)
                    }

                }
            }
            navigator = commonNavigator
            ViewPagerHelper.bind(indicator_home,vp_home)
        }

        /* 列表 */
        initViewPager()
    }

    private fun initViewPager() {
        val homeAdapter = HomeAdater(
            childFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        vp_home.adapter = homeAdapter
    }

    override fun onResume() {
        super.onResume()
        banner_home.start()
    }

    override fun onStop() {
        super.onStop()
        banner_home.stop()
    }
}