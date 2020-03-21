package com.blcs.xxx.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blcs.common.Base.BaseFragment
import com.blcs.xxx.R
import com.blcs.xxx.databinding.FragmentGuideBinding
import kotlinx.android.synthetic.main.fragment_guide.*

/**
 * A simple [Fragment] subclass.
 */
class GuideFragment : BaseFragment<FragmentGuideBinding>(), View.OnClickListener {
    override fun onClick(v: View?) {
       when(v?.id){
           R.id.btn_login -> { findNavController().navigate(R.id.action_splashFragment_to_loginFragment) }
       }
    }

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
        btn_login.visibility = if(int==3) View.VISIBLE else View.GONE
        val any = when (int) {
            0 -> android.R.color.holo_blue_light
            1 -> android.R.color.holo_green_dark
            2 -> android.R.color.darker_gray
            3 -> android.R.color.holo_red_dark
            else -> R.color.colorPrimary
        }
        tv_guide.setBackgroundResource(any)
        tv_guide.setText("第"+int?.plus(1)+"页")
        btn_login.setOnClickListener(this)
    }

    override fun onDetach() {
        super.onDetach()
        if (activity?.supportFragmentManager != null){
        }
    }


}
