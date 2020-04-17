package com.blcs.livemodule
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.alibaba.android.arouter.facade.annotation.Route
import com.blcs.common.Base.BaseActivity
import com.blcs.common.demo.UI_Constants
import com.blcs.common.utils.StatusBarUtils
import com.blcs.livemodule.adapter.LiveListAdapter
import kotlinx.android.synthetic.main.include_rv.*
import org.jetbrains.anko.toast


@Route(path = UI_Constants.LIVE_HOME_ACTIVITY)
class LiveHomeActivity : BaseActivity() {
    var mAdapter = LiveListAdapter()
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
        living_rv.layoutManager = LinearLayoutManager(this)
        living_rv.adapter = mAdapter
        PagerSnapHelper().attachToRecyclerView(living_rv)
        mAdapter.setNewInstance(mutableListOf)
    }

    override fun onResume() {
        super.onResume()
        mAdapter.player?.resume()
    }

    override fun onStop() {
        super.onStop()
        mAdapter.player?.stopPlay(true)
    }

    override fun onPause() {
        super.onPause()
        mAdapter.player?.pause()
    }

}
