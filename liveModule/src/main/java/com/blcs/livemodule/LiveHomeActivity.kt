package com.blcs.livemodule
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.alibaba.android.arouter.facade.annotation.Route
import com.blcs.common.Base.BaseActivity
import com.blcs.common.demo.UI_Constants
import com.blcs.common.utils.StatusBarUtils
import com.blcs.livemodule.adapter.LiveListAdapter
import com.blcs.livemodule.living.MLVBLiveRoom
import kotlinx.android.synthetic.main.include_rv.*


@Route(path = UI_Constants.LIVE_HOME_ACTIVITY)
class LiveHomeActivity : BaseActivity() {

    var sharedInstance: MLVBLiveRoom? = null
    var mAdapter: LiveListAdapter?=null
    val mutableListOf = mutableListOf(
        "https://play.yinkehuyu.cn/prod/prod_514371.flv",
        "https://play.yinkehuyu.cn/prod/prod_514371.flv",
        "https://play.yinkehuyu.cn/prod/prod_514371.flv",
        "https://play.yinkehuyu.cn/prod/prod_514371.flv",
        "https://play.yinkehuyu.cn/prod/prod_514371.flv",
        "https://play.yinkehuyu.cn/prod/prod_514371.flv")
    override fun setLayout() = R.layout.include_rv
    override fun initUI() {
        StatusBarUtils.setTransparent(this)
        initRv()
    }

    private fun initRv() {
        sharedInstance = MLVBLiveRoom.sharedInstance(this)
        mAdapter = LiveListAdapter(sharedInstance!!)
        living_rv.layoutManager = LinearLayoutManager(this)
        living_rv.adapter = mAdapter
        PagerSnapHelper().attachToRecyclerView(living_rv)
        mAdapter?.setNewInstance(mutableListOf)
    }
}
