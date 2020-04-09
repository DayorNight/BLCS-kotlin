package com.blcs.main

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.alibaba.android.arouter.facade.annotation.Route
import com.blcs.common.Base.BaseActivity
import com.blcs.common.demo.UI_Constants
import com.blcs.main.adapter.MainAdapter
import com.blcs.main.fragment.main.ChatFragment
import com.blcs.main.fragment.main.ContactsFragment
import com.blcs.main.fragment.main.HomeFragment
import com.blcs.main.fragment.main.MineFragment
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = UI_Constants.MAIN_MAIN_ACTIVITY)
class MainActivity : BaseActivity() {
    override fun setLayout() = R.layout.activity_main

    override fun initUI() {
        val fragments = listOf<Fragment>(
            HomeFragment(),
            ChatFragment(),
            ContactsFragment(),
            MineFragment()
        )
       var mainAdapter = MainAdapter(
            supportFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,fragments
        )
        vp_main.adapter = mainAdapter
        vp_main.offscreenPageLimit = 4
        vp_main.addOnPageChangeListener(object : OnPageChangeListener {

            override
            fun onPageScrolled(i: Int, v: Float, i1: Int) {
            }

            override
            fun onPageSelected(pos: Int) {
                bnv_main.menu.getItem(pos).isChecked = true
            }

            override
            fun onPageScrollStateChanged(i: Int) {
            }
        })
        bnv_main.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_home -> vp_main.currentItem = 0
                R.id.item_chat -> vp_main.currentItem = 1
                R.id.item_contacts -> vp_main.currentItem = 2
                R.id.item_mine -> vp_main.currentItem = 3
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

}
