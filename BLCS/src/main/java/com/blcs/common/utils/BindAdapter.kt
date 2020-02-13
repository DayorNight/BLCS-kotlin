package com.blcs.common.utils

import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager

@BindingAdapter("viewPageAdapter")
fun setAdapter(view: ViewPager, adapter: FragmentStatePagerAdapter ) {
    view.adapter = adapter
}
