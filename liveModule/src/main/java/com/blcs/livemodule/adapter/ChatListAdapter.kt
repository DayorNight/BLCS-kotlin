package com.blcs.livemodule.adapter

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.blcs.livemodule.R
import com.blcs.livemodule.common.msg.TCChatEntity
import com.blcs.livemodule.widget.LiveManageView.Companion.TEXT_TYPE
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import org.jetbrains.anko.textColor
import kotlin.experimental.and
import kotlin.experimental.xor

/**
 * @Author BLCS
 * @Time 2020/4/13 11:03
 */
class ChatListAdapter : BaseQuickAdapter<TCChatEntity, BaseViewHolder>(R.layout.adapter_chat_list) {
    override fun convert(holder: BaseViewHolder, item: TCChatEntity) {
        holder.setText(R.id.tv_name,"位置："+holder.adapterPosition)
        val content = holder.getView<TextView>(R.id.tv_name)
        var spanString = SpannableString(item.senderName + "  " + item.content)
        if (item.type !== TEXT_TYPE) {
            // 设置名称为粗体
            val boldStyle = StyleSpan(Typeface.BOLD_ITALIC)
            spanString.setSpan(
                boldStyle,
                0,
                item.senderName.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            content.setTextColor( ContextCompat.getColor(context, R.color.colorSendName1))
        } else {
            // 根据名称计算颜色
            spanString.setSpan(
                ForegroundColorSpan(calcNameColor(item.senderName)),
                0, item.senderName.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            content.textColor = context.getResources().getColor(R.color.white)

        }
        content.text = spanString
    }


    /**
     * 通过名称计算颜色
     */
    private fun calcNameColor(strName: String?): Int {
        if (strName == null) return 0
        var idx: Byte = 0
        val byteArr = strName.toByteArray()
        for (i in byteArr.indices) {
            idx = idx xor byteArr[i]
        }

        return when (idx and 0x7) {
            1.toByte() -> ContextCompat.getColor(context, R.color.colorSendName1)
            2.toByte() -> ContextCompat.getColor(context,R.color.colorSendName2)
            3.toByte() -> ContextCompat.getColor(context,R.color.colorSendName3)
            4.toByte() -> ContextCompat.getColor(context,R.color.colorSendName4)
            5.toByte() -> ContextCompat.getColor(context,R.color.colorSendName5)
            6.toByte() -> ContextCompat.getColor(context,R.color.colorSendName6)
            7.toByte() -> ContextCompat.getColor(context,R.color.colorSendName7)
            0.toByte() -> ContextCompat.getColor(context,R.color.colorSendName)
            else -> ContextCompat.getColor(context,R.color.colorSendName)
        }
    }
}