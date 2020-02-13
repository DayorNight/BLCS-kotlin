package com.blcs.xxx.fragment

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.blcs.common.Base.BaseFragment
import com.blcs.common.interfaces.HandleBackInterface
import com.blcs.common.utils.HandleBackUtil
import com.blcs.xxx.R
import com.blcs.xxx.databinding.FragmentLoginBinding
import com.blcs.xxx.viewModel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : BaseFragment<FragmentLoginBinding>(), View.OnClickListener , HandleBackInterface {
    override fun onBackPressed() = HandleBackUtil.handleBackPress(this)

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_to_register -> findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
    override fun setLayout() = R.layout.fragment_login

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun initUI() {
        mBindLayout?.click = this
    }

}
