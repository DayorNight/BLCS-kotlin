package com.blcs.common.utils;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

/**
 * Arouter 登录拦截
 * @Author BLCS
 * @Time 2020/3/24 14:33
 */
// 拦截器会在跳转之间执行，多个拦截器会按优先级顺序依次执行
@Interceptor(priority =1, name = "登录拦截器")
public class LoginInterceptor implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        callback.onContinue(postcard);  // 处理完成，交还控制权
        // callback.onInterrupt(new RuntimeException("我觉得有点异常"));      // 觉得有问题，中断路由流程
        L.e("===========");
        // 以上两种至少需要调用其中一种，否则不会继续路由
    }

    @Override
    public void init(Context context) {
        L.e("===========");
        // 拦截器的初始化，会在sdk初始化的时候调用该方法，仅会调用一次
    }
}
