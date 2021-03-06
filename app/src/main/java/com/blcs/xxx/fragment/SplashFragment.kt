package com.blcs.xxx.fragment

import android.graphics.Color
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.navigation.fragment.findNavController
import com.blcs.common.Base.BaseFragment
import com.blcs.common.utils.SPUtils
import com.blcs.xxx.R
import com.blcs.xxx.comment.Sp_Constant
import com.blcs.xxx.databinding.FragmentSplashBinding
import kotlinx.android.synthetic.main.fragment_splash.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator

/**
 * A simple [Fragment] subclass.
 */

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    override fun initUI() {
        view?.postDelayed({
            val isFrist = SPUtils.get(activity,Sp_Constant.SP_FIRST, false) as Boolean
            mView?.isShow = !isFrist
            if (!isFrist) {
                SPUtils.put(activity,Sp_Constant.SP_FIRST, !isFrist)
            }else{
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        }, 3000)
        mView?.adapter = MyAdapter(activity!!.supportFragmentManager)
        initMagicIndicator()
    }

    private fun initMagicIndicator() {
        val circleNavigator = CircleNavigator(activity)
        circleNavigator.circleCount = 4
        circleNavigator.circleColor = Color.RED
        circleNavigator.circleClickListener =
            CircleNavigator.OnCircleClickListener { index -> vp_splash.setCurrentItem(index) }
        magic_indicator.setNavigator(circleNavigator)
        ViewPagerHelper.bind(magic_indicator, vp_splash)
    }

    override fun setLayout() = R.layout.fragment_splash

    class MyAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int) = GuideFragment.init(position)
        override fun getCount() = 4
    }
}
