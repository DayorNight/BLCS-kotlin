package com.blcs.xxx.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.blcs.common.Base.BaseFragment
import com.blcs.xxx.R
import kotlinx.android.synthetic.main.fragment_guide.*
import java.text.FieldPosition

/**
 * A simple [Fragment] subclass.
 */
class GuideFragment : BaseFragment() {
    companion object {
        fun init(position: Int) =  GuideFragment().apply {
            val bundle = Bundle()
            bundle.putInt("pos",position)
            arguments =bundle
        }
    }
    override fun setLayout() = R.layout.fragment_guide

    override fun initUI() {
        val int = arguments?.getInt("pos")
        val any = when (int) {
            0 -> android.R.color.black
            1 -> android.R.color.holo_red_dark
            2 -> android.R.color.darker_gray
            3 -> android.R.color.holo_blue_dark
            else -> R.color.colorPrimary
        }
        ll_bg.setBackgroundColor(any)
    }

}
