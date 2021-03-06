package com.blcs.xxx.fragment

import android.text.TextUtils
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alibaba.android.arouter.launcher.ARouter
import com.alibaba.fastjson.JSONObject
import com.blcs.common.Base.BaseFragment
import com.blcs.common.demo.UI_Constants
import com.blcs.common.interfaces.HandleBackInterface
import com.blcs.common.manager.AutoLoginManage
import com.blcs.common.manager.AutoLoginManage.OnTokenResultListener
import com.blcs.common.utils.HandleBackUtil
import com.blcs.common.utils.SPUtils
import com.blcs.common.utils.spreadFun.isPassword
import com.blcs.xxx.R
import com.blcs.xxx.comment.Sp_Constant
import com.blcs.xxx.databinding.FragmentLoginBinding
import com.blcs.xxx.comment.Api_Constant
import com.blcs.xxx.viewModel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.support.v4.toast

/**
 * 登录
 */
class LoginFragment : BaseFragment<FragmentLoginBinding>(), View.OnClickListener,
    HandleBackInterface {
    override fun onBackPressed() = HandleBackUtil.handleBackPress(this)
    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_to_register -> findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            R.id.btn_login -> {
                if (TextUtils.isEmpty(mView?.phone)) {
                    toast("手机号不能为空")
                    return
                }
                if (TextUtils.isEmpty(mView?.passWord)) {
                    toast("密码不能为空")
                    return
                }
                if (!mView?.passWord!!.isPassword()) {
                    toast("请输入6-12位由字母与数字组成的密码")
                    return
                }
                ARouter.getInstance().build(UI_Constants.MAIN_MAIN_ACTIVITY).navigation()
                if (rb_remember_psw.isChecked) {
                    val password = mView?.phone!! + "|" + mView?.passWord!!
                    SPUtils.put(activity,Sp_Constant.SP_REMEMBER_PASSWORD, password)
                } else {
                    SPUtils.remove(activity,Sp_Constant.SP_REMEMBER_PASSWORD)
                }
            }
            R.id.btn_to_fin -> toast("找回密码")
            R.id.iv_wx ->   ARouter.getInstance().build(UI_Constants.MAIN_MAIN_ACTIVITY).navigation()
            R.id.iv_clean -> mView?.phone = null
            R.id.iv_show_psw -> {
                et_psw.inputType = if (iv_show_psw.isSelected) 129 else 128
                iv_show_psw.isSelected = !iv_show_psw.isSelected
                et_psw.setSelection(et_psw.text.length)
            }
            R.id.btn_auto_login ->{
                AutoLoginManage.getInstance(activity).login()
            }
        }
    }

    override fun setLayout() = R.layout.fragment_login

    override fun initUI() {
        mView?.click = this
        val value = SPUtils.get(activity,Sp_Constant.SP_REMEMBER_PASSWORD, "") as String
        if (!TextUtils.isEmpty(value)) {
            val split = value.split("|")
            mView?.phone = split[0]
            mView?.passWord = split[1]
        }
        et_phone.addTextChangedListener {
            iv_clean.visibility = if (it?.length!! > 0) View.VISIBLE else View.GONE
            mView?.select = !TextUtils.isEmpty(mView?.passWord) && mView?.passWord!!.length > 0 && it.length > 0

        }
        et_psw.addTextChangedListener {
            iv_show_psw.visibility = if (it?.length!! > 0) View.VISIBLE else View.GONE
            mView?.select = !TextUtils.isEmpty(mView?.phone) && mView?.phone!!.length > 0 && it.length > 0
        }
        autoLogin()
    }

    private  fun autoLogin() {
        AutoLoginManage.getInstance(activity).init(Api_Constant.AUTO_LOGIN_KEY ,object : OnTokenResultListener {
            override fun getTokenSuccess(token: String) {
                activity?.runOnUiThread {
                    toast("登录成功token： " + token)
                }
            }

            override fun getTokenFailed(msg: String) {
                activity?.runOnUiThread {
                    toast("登录失败:"+msg)
                }
            }
        }).addUIClickListener(object : AutoLoginManage.OnUIClickImplements() {
            override fun onClickToggle(jsonObj: JSONObject) {
                //切换登录方式
            }
        })
    }
}
