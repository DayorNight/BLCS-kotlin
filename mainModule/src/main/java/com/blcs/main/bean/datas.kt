package com.blcs.main.bean

import android.os.Parcel
import android.os.Parcelable
import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * 数据
 * @Author BLCS
 * @Time 2020/4/8 15:06
 */

data class RecommendDatas(override val itemType: Int) : MultiItemEntity


data class ShortVideo(var videoUrl: String ,var userId: String): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(videoUrl)
        parcel.writeString(userId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShortVideo> {
        override fun createFromParcel(parcel: Parcel): ShortVideo {
            return ShortVideo(parcel)
        }

        override fun newArray(size: Int): Array<ShortVideo?> {
            return arrayOfNulls(size)
        }
    }
}