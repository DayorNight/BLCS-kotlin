package com.blcs.xxx.fragment


import android.graphics.Color
import android.text.TextUtils
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blcs.common.Base.BaseFragment
import com.blcs.common.utils.spreadFun.isPassword
import com.blcs.common.utils.spreadFun.isPhone
import com.blcs.xxx.R
import com.blcs.xxx.databinding.FragmentRegisterBinding
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.et_phone
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.textColorResource

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(), View.OnClickListener {
    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_register -> {
                if (TextUtils.isEmpty(mBindLayout?.phone)) {
                    toast("手机号不能为空")
                    return
                }
                if (TextUtils.isEmpty(mBindLayout?.verifyCode)) {
                    toast("验证码不能为空")
                    return
                }
                if (TextUtils.isEmpty(mBindLayout?.passWord)||TextUtils.isEmpty(mBindLayout?.resPassWord)) {
                    toast("密码不能为空")
                    return
                }
                if (!mBindLayout?.passWord.equals(mBindLayout?.resPassWord)) {
                    toast("两次密码输入不同")
                    return
                }
                if (!mBindLayout?.passWord!!.isPassword()) {
                    toast("请输入6-12位由字母优先与数字组成的密码")
                    return
                }
                toast("注册成功")
                findNavController().popBackStack()
            }
            R.id.iv_close -> mBindLayout?.phone = null
            R.id.btn_vertify_code -> {
                mBindLayout?.isClick = true
            }
        }
    }

    override fun setLayout() = R.layout.fragment_register

    override fun initUI() {
        mBindLayout?.click = this
        toolbar_titile.setNavigationOnClickListener{findNavController().popBackStack()}
        et_phone.addTextChangedListener {
            iv_close.visibility = if (it?.length!! > 0) View.VISIBLE else View.GONE //显示 清空手机号按钮
            mBindLayout?.isGetCode = it.toString().isPhone() //验证码是否可点击
//            btn_vertify_code.isSelected = it?.toString().isPhone() //改变验证码的颜色
//            ll_vertify.isSelected = it?.toString().isPhone()
            mBindLayout?.isClick = false
        }

        et_password.addTextChangedListener {
            mBindLayout?.isRegister = !TextUtils.isEmpty(mBindLayout?.resPassWord) && mBindLayout?.resPassWord!!.length > 0 && it?.length!! > 0
        }
        et_reset_password.addTextChangedListener {
            mBindLayout?.isRegister = !TextUtils.isEmpty(mBindLayout?.passWord) && mBindLayout?.passWord!!.length > 0 && it?.length!! > 0
        }
    }
}
