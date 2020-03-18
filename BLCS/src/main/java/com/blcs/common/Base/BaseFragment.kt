package com.blcs.common.Base


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * A simple [Fragment] subclass.
 */
abstract class BaseFragment<T : ViewDataBinding?> : Fragment() {
    var mBindLayout: T? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBindLayout = DataBindingUtil.inflate<T>(inflater, setLayout(), container, false)
        return  mBindLayout?.getRoot()
    }
    abstract fun setLayout():Int
    abstract fun initUI()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mBindLayout!=null)  mBindLayout = null
    }
}
