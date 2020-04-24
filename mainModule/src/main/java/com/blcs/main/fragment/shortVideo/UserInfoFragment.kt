package com.blcs.main.fragment.shortVideo

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.blcs.common.Base.BaseFragment
import com.blcs.common.utils.L
import com.blcs.common.utils.ScreenUtils
import com.blcs.main.R
import com.blcs.main.databinding.FragmentUserInfoBinding
import com.blcs.main.fragment.product.ProductsListFragment
import kotlinx.android.synthetic.main.activity_short_video.*
import kotlinx.android.synthetic.main.fragment_user_info.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import org.jetbrains.anko.padding
import org.jetbrains.anko.support.v4.viewPager

/**
 * @Author BLCS
 * @Time 2020/4/22 11:27
 */
class UserInfoFragment: BaseFragment<FragmentUserInfoBinding>(){
    val titles= listOf("作品","动态","喜欢")
    companion object{
        fun getInstance(userId: String)=
            UserInfoFragment().apply {
            arguments = Bundle().apply {
                putString("UserId",userId)
            }
        }
    }

    override fun setLayout() = R.layout.fragment_user_info

    override fun initUI() {
        val indicatorWhite = ScreenUtils.getScreenWidth(context) / 3
        val userId = arguments?.get("UserId")
        /*导航栏*/
        indicator_user.apply {
            val commonNavigator = CommonNavigator(activity)

            commonNavigator.apply {
                    adapter = object: CommonNavigatorAdapter(){
                    override fun getTitleView(context: Context?, index: Int) =
                        ColorTransitionPagerTitleView(context).apply {
                            text = titles[index]
                            normalColor = ContextCompat.getColor(getContext(),R.color.gray)
                            selectedColor = Color.WHITE
                            setOnClickListener {
                                vp_user.currentItem = index
                            }
                            width = indicatorWhite
                        }
                    override fun getCount() = titles.size

                    override fun getIndicator(context: Context?) = LinePagerIndicator(context).apply {
                        setColors(ContextCompat.getColor(getContext(), R.color.yellow))
                        lineWidth = indicatorWhite.toFloat()
                    }
                }
            }

            navigator = commonNavigator
            ViewPagerHelper.bind(indicator_user,vp_user)
        }
        /*ViewPager*/
        val fragments = listOf<Fragment>(ProductsListFragment.getInstance(0),ProductsListFragment.getInstance(1),ProductsListFragment.getInstance(0))
        val userProductAdapter = UserProductAdapter(fragments,
            childFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        vp_user.adapter = userProductAdapter

        iv_back.setOnClickListener {
            activity?.viewpager?.currentItem = 0

        }
    }

    class UserProductAdapter(var fragments: List<Fragment>,fm: FragmentManager,status: Int): FragmentStatePagerAdapter(fm,status){
        override fun getItem(position: Int)=fragments[position]
        override fun getCount()= fragments.size
    }

}
