package com.blcs.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.blcs.main.fragment.home.RecommendListFragment

/**
 * @Author BLCS
 * @Time 2020/4/9 12:15
 */
internal class HomeAdater(fragmentManager: FragmentManager,type: Int) : FragmentStatePagerAdapter(
    fragmentManager,type
) {
    override fun getItem(position: Int) =  RecommendListFragment().newInstance(position)
    override fun getCount() = 5
}
