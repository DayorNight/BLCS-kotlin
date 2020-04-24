package com.blcs.main
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.blcs.common.Base.BaseActivity
import com.blcs.common.demo.UI_Constants
import com.blcs.main.bean.ShortVideo
import com.blcs.main.fragment.shortVideo.ShortVideoListFragment
import com.blcs.main.fragment.shortVideo.UserInfoFragment
import kotlinx.android.synthetic.main.activity_short_video.*

/**
 * 短视频播放
 */
@Route(path = UI_Constants.MAIN_SHORT_VIDEO_ACTIVITY)
class ShortVideoActivity : BaseActivity() {
    override fun setLayout() = R.layout.activity_short_video
    var shortVideoVpAdapter: ShortVideoVpAdapter? = null
    override fun initUI() {
        val listOf = listOf(ShortVideoListFragment.getInstance(), UserInfoFragment.getInstance(""))
        shortVideoVpAdapter = ShortVideoVpAdapter(supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, listOf)
        viewpager.adapter = shortVideoVpAdapter

    }

    class ShortVideoVpAdapter(fm: FragmentManager, state: Int, private var fragments: List<Fragment>): FragmentStatePagerAdapter(fm, state){
        override fun getItem(position: Int) = fragments[position]
        override fun getCount()=fragments.size
    }
}
