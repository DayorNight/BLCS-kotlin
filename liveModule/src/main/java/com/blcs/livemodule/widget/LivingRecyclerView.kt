package com.blcs.livemodule.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.core.view.marginLeft
import androidx.recyclerview.widget.RecyclerView
import com.blcs.common.utils.L
import com.blcs.common.utils.ScreenUtils
import com.blcs.livemodule.R
import com.chad.library.adapter.base.util.getItemView
import com.cmic.sso.sdk.utils.i
import org.jetbrains.anko.find
import kotlin.math.abs

/**
 * @Author BLCS
 * @Time 2020/4/13 18:18
 */
class LivingRecyclerView:RecyclerView {
    private var lastX: Float?=0f
    private var lastY: Float?=0f

    private var downX: Float?=null
    private var downY: Float?=null
    private var screenWidth: Int =0
    private var screenHeight: Int =0
    private var scrollXmax: Int =0
    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)
    init {
         screenWidth = ScreenUtils.getScreenWidth(context)
         screenHeight = ScreenUtils.getScreenHeight(context)
         scrollXmax = screenWidth/3
    }
    var viewManager: LiveManageView?=null
    private var scrX: Float =0f
    private var scrY: Float =0f
    private var canScrollRight = true
    private var scrollChatList = false
    private var leftX = 0f
    private var leftY = 0f
    private var rvChat: RecyclerView? =null
    private var view: View? =null
    override fun onTouchEvent(event: MotionEvent?): Boolean {
            when(event?.action){
                MotionEvent.ACTION_DOWN ->{
                    downX = event?.rawX
                    downY = event?.rawY
                    rvChat?.onTouchEvent(event)
                }
                MotionEvent.ACTION_MOVE ->{
                    val moveX = leftX?.minus(lastX!!) //移动X距离
                    val moveY = leftY?.minus(lastY!!) //移动Y距离

                    if ( moveX!!<0f&&viewManager?.translationX!!>=0f&& abs(moveX!!)>viewManager?.translationX!!){
                        viewManager?.translationX = 0f
                    }else if (viewManager?.translationX!!>=0f&&canScrollRight){//向右移动
                        viewManager?.translationX = viewManager?.translationX!! + moveX!!
                    }
                    /*保持一个方向滑动*/
                    if (abs(moveY!!)>3 && viewManager?.translationX==0f||abs(moveY!!)>3 && viewManager?.translationX!!.toInt() == screenWidth){
                        canScrollRight =false
                        /*判断滑动位置是否在控件内  聊天信息控件*/
                        if (rvChat?.x!!<=leftX&&leftX<= rvChat?.measuredWidth!! +rvChat?.x!!&& rvChat?.y!!<=leftY&&leftY<= rvChat?.measuredHeight!! + rvChat?.y!!){
                            scrollChatList = true
                            rvChat?.onTouchEvent(event)
                        }else{
                            if (!scrollChatList){
                                return super.onTouchEvent(event)
                            }
                        }
                    }else if (abs(moveX!!)>0 && viewManager?.translationX==0f&&!scrollChatList||abs(moveX!!)>0 && viewManager?.translationX!!.toInt() == screenWidth&&!scrollChatList){
                        canScrollRight =true
                    }
                }
                MotionEvent.ACTION_UP ->{
                    /*横坐标位移*/
                    scrX = leftX?.minus(downX!!)!!//滑动X距离
                    scrY = leftY?.minus(downY!!)!!//滑动Y距离
                    if(scrX> scrollXmax||scrX> -scrollXmax &&scrX< -2&&viewManager?.translationX!!>scrollXmax){//向右滑
                        viewManager?.animate()?.translationX(screenWidth.toFloat())
                    }else if(scrX> 0&&scrX<=scrollXmax||scrX< -scrollXmax&&viewManager?.translationX!!>0){//向左滑
                        viewManager?.animate()?.translationX(0f)
                    }else if (viewManager?.translationX!!>5&&viewManager?.translationX!!<scrollXmax){//保证回到指定位置
                        viewManager?.animate()?.translationX(0f)
                    }else if(viewManager?.translationX!!>=scrollXmax&&viewManager?.translationX!! < screenWidth-5){
                        viewManager?.animate()?.translationX(screenWidth.toFloat())
                    }
                    /*纵坐标位移*/
                    if (!canScrollRight){
                        if (rvChat?.x!!<=leftX&&leftX<=rvChat?.measuredWidth!!+rvChat?.x!!&& rvChat?.y!!<=leftY&&leftY<= rvChat?.measuredHeight!! + rvChat?.y!!){
                            rvChat?.onTouchEvent(event)
                        }else{
                            if (!scrollChatList) {
                                return super.onTouchEvent(event)
                            }
                        }
                    }

                    /*保证纵坐标回到指定位置*/
                    val translationY =view?.y
                    val viewAdapterPosition = (view?.layoutParams as LayoutParams).viewLayoutPosition
                    if (translationY!=0f){
                        smoothScrollToPosition(viewAdapterPosition)
                    }
                    scrollChatList = false
                }
            }
            lastX = leftX
            lastY = leftY
            return true
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        leftX = event?.rawX!! //距离左边距离
        leftY = event?.rawY!! //距离上边距离
        view = getChildAt(0)
        viewManager = view?.find(R.id.lmv_manager)
        rvChat = viewManager?.getRvChat()
        //处理 recyclerview  拦截父类点击事件
        when(event?.action){
            MotionEvent.ACTION_DOWN ->{
                if (rvChat?.x!!<=leftX&&leftX<= rvChat?.measuredWidth!! +rvChat?.x!!&& rvChat?.y!!<=leftY&&leftY<= rvChat?.measuredHeight!! + rvChat?.y!!){
                    onTouchEvent(event)
                }
            }
        }
      return super.dispatchTouchEvent(event)
    }

}