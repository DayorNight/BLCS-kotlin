package com.blcs.livemodule
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.alibaba.android.arouter.facade.annotation.Route
import com.blcs.common.Base.BaseActivity
import com.blcs.common.demo.UI_Constants
import com.blcs.livemodule.adapter.LiveListAdapter
import kotlinx.android.synthetic.main.include_rv.*
import com.blcs.common.utils.StatusBarUtils


@Route(path = UI_Constants.LIVE_HOME_ACTIVITY)
class LiveHomeActivity : BaseActivity() {
    var mAdapter = LiveListAdapter()
    val mutableListOf = mutableListOf("https://flv.6721.liveplay.now.qq.com/live/6721_679305269ab60adc0a821fe7ea3f2ff2.flv",
        "https://flv.6721.liveplay.now.qq.com/live/6721_cce87df2e9ff5c8d2f0d28fddab08427.flv",
        "https://flv.6721.liveplay.now.qq.com/live/6721_90520390a2a41fbd14a4f75e909a1625.flv",
        "https://play.yinkehuyu.cn/test/test_500795.flv-7394",
        "https://flv.6721.liveplay.now.qq.com/live/6721_ff9379b25d61a7759e8a2dd6392188d1.flv")
    override fun setLayout() = R.layout.include_rv
    override fun initUI() {
        StatusBarUtils.setTransparent(this)
        initRv()
    }

    private fun initRv() {
        include_rv.layoutManager = LinearLayoutManager(this)
        include_rv.adapter = mAdapter
        PagerSnapHelper().attachToRecyclerView(include_rv)
        mAdapter.setNewInstance(mutableListOf)
    }

    override fun onPause() {
        super.onPause()
        mAdapter.player?.pause()
    }

    override fun onResume() {
        super.onResume()
        mAdapter.player?.resume()
    }

    override fun onStop() {
        super.onStop()
        mAdapter.player?.stopPlay(true)
    }
}
