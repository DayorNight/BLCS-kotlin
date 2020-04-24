package com.blcs.main.fragment.product

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.blcs.common.Base.BaseFragment
import com.blcs.main.R
import com.blcs.main.adapter.ProductsAdapter
import com.blcs.main.databinding.FragmentProductsListBinding
import kotlinx.android.synthetic.main.fragment_products_list.*

/**
 * @Author BLCS
 * @Time 2020/4/22 16:21
 */
class ProductsListFragment: BaseFragment<FragmentProductsListBinding>() {
    companion object{
        fun getInstance(type: Int) = ProductsListFragment().apply {
            arguments = Bundle().apply {
                putInt("TYPE",type)
            }
        }
    }

    var mAdapter = ProductsAdapter()
    override fun setLayout()= R.layout.fragment_products_list

    override fun initUI() {
        refrsh.setOnRefreshListener { refrsh.isRefreshing = false }
        val gridLayoutManager = GridLayoutManager(context, 3)
        rv_product.layoutManager = gridLayoutManager
        rv_product.adapter = mAdapter
        mAdapter.setNewInstance(mutableListOf(1,2,3,4,5,6,7,8,9,0))
    }
}