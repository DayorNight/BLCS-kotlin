package com.blcs.xxx.fragment


import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blcs.common.Base.BaseFragment
import com.blcs.xxx.R
import com.blcs.xxx.databinding.FragmentRegisterBinding
import kotlinx.android.synthetic.main.fragment_register.*

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(), View.OnClickListener {
    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_register -> findNavController().popBackStack()
        }
    }

    override fun setLayout() = R.layout.fragment_register

    override fun initUI() {
        btn_register.setOnClickListener(this)
    }

}
