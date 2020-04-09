package com.blcs.main.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * @Author BLCS
 * @Time 2020/4/8 14:25
 */
class MainAdapter(fm: FragmentManager, state: Int, private var fragments: List<Fragment>) : FragmentStatePagerAdapter(fm, state) {
    override fun getItem(position: Int) = fragments[position]
    override fun getCount() = fragments.size
}