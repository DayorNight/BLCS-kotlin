package com.blcs.xxx.fragment


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.blcs.common.Base.BaseFragment
import com.blcs.xxx.R
import kotlinx.android.synthetic.main.fragment_splash.*

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : BaseFragment() {

    override fun initUI() {
        vp_splash.adapter = MyAdapter(fragmentManager!!)
    }

    override fun setLayout() = R.layout.fragment_splash


    class MyAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int) =  GuideFragment.init(position)


        override fun getCount() = 4
    }
}
