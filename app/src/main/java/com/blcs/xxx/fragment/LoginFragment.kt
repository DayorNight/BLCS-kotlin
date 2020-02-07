package com.blcs.xxx.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blcs.common.Base.BaseFragment
import com.blcs.xxx.R
import com.blcs.xxx.bean.Student
import com.blcs.xxx.viewModel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : BaseFragment(), View.OnClickListener {
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
        btn_to_register.setOnClickListener(this)
        val adapter = StudentAdapter()
        // 将数据的变化反映到UI上
        viewModel.allStudents.observe(this, Observer { adapter.submitList(it) })
        rv_login.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        rv_login.adapter =adapter

    }


    class StudentAdapter : PagedListAdapter<Student, StudentViewHolder>(diffCallback) {

        override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
            holder.bindTo(getItem(position))
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder =
            StudentViewHolder(parent)

        companion object {
            private val diffCallback = object : DiffUtil.ItemCallback<Student>() {
                override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean =
                    oldItem == newItem
            }
        }
    }

    class StudentViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.student_item, parent, false)) {

        private val nameView = itemView.findViewById<TextView>(R.id.tv_name)
        var student: Student? = null

        fun bindTo(student: Student?) {
            this.student = student
            nameView.text = student?.name
        }
    }
}
