package com.blcs.main.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.blcs.main.bean.RecommendDatas

/**
 * @Author BLCS
 * @Time 2020/4/9 10:31
 */
class RecommendListModel (app: Application) : AndroidViewModel(app) {
    val datas = MutableLiveData<MutableList<RecommendDatas>>()
    val cardDatas = MutableLiveData<MutableList<String>>()

    /*获取首页列表数据*/
    fun getHomeListDatas(pos: Int?){
        when(pos){
            0 ->{/*推荐*/getRecommend()}
            1 ->{/*热门*/getHotDatas()}
            2 ->{/*短视频*/getShortVideo()}
            3 ->{/*直播*/getLiving()}
            4 ->{/*其他*/getOtherDatas()}
        }
    }

    /*推荐数据*/
    fun getRecommend(){
       var mutableListOf = mutableListOf(
            RecommendDatas(0),
            RecommendDatas(1),
            RecommendDatas(2),
            RecommendDatas(3),
            RecommendDatas(4),
            RecommendDatas(0),
            RecommendDatas(1),
            RecommendDatas(2)
        )
        datas?.postValue(mutableListOf)
    }
    /*热门数据*/
    fun getHotDatas() {
        var mutableListOf = mutableListOf(
            RecommendDatas(0),
            RecommendDatas(4),
            RecommendDatas(0),
            RecommendDatas(4),
            RecommendDatas(4),
            RecommendDatas(4),
            RecommendDatas(0),
            RecommendDatas(4)
        )
        datas?.postValue(mutableListOf)
    }
    /*其他数据*/
    fun getOtherDatas() {
        var mutableListOf = mutableListOf(
            RecommendDatas(3),
            RecommendDatas(3),
            RecommendDatas(3),
            RecommendDatas(3),
            RecommendDatas(3),
            RecommendDatas(3),
            RecommendDatas(3)
        )
        datas?.postValue(mutableListOf)
    }
    /*短视频列表数据*/
    fun getShortVideo() {
       var cards = mutableListOf("1", "2", "2", "2", "2", "2", "2", "2", "2")
        cardDatas?.postValue(cards)
    }
    /*直播列表数据*/
    fun getLiving() {
        var livings = mutableListOf("1", "2", "2", "2", "2", "2", "2", "2", "2")
        cardDatas?.postValue(livings)
    }
}