package com.blcs.common.Base


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.blcs.common.utils.L

/**
 * A simple [Fragment] subclass.
 */
abstract class BaseFragment<T : ViewDataBinding?> : Fragment() {
    var mView : T? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mView==null){
            mView = DataBindingUtil.inflate<T>(inflater, setLayout(), container, false)
        }
        return  mView?.getRoot()
    }
    abstract fun setLayout():Int
    abstract fun initUI()
    override fun onStart() {
        super.onStart()
        initUI()
    }
    override fun onDestroy() {
        super.onDestroy()
        mView?.unbind()
    }
}
