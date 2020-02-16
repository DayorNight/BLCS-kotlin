package com.blcs.common.utils

import android.view.View
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.blcs.common.utils.spreadFun.isPhone
import org.jetbrains.anko.textColor
import org.jetbrains.anko.textColorResource
import java.util.*
import java.util.regex.Pattern

@BindingAdapter("viewPageAdapter")
fun setAdapter(view: ViewPager, adapter: FragmentStatePagerAdapter) {
    view.adapter = adapter
}


@BindingAdapter("verifyPhone")
fun verifyPhone(view: EditText,isVerify: Boolean) {
    if (!isVerify) return
    view.addTextChangedListener {
        view.textColorResource =
            if (!it.toString().isPhone() && it?.length == 11) android.R.color.holo_red_dark else android.R.color.holo_green_dark
    }
}

@BindingAdapter("select")
fun select(view: View,select: Boolean) {
    view.isSelected = select
}
