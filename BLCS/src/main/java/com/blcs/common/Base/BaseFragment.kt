package com.blcs.common.Base


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * A simple [Fragment] subclass.
 */
abstract class BaseFragment : Fragment() {
    var mView: View?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mView==null){
            mView = inflater.inflate(setLayout(), container, false)
            initUI()
        }
        return mView
    }
    abstract fun setLayout():Int
    abstract fun initUI()
}
