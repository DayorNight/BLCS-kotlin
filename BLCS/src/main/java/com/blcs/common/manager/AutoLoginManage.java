package com.blcs.common.manager;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blcs.common.BuildConfig;
import com.blcs.common.R;
import com.mobile.auth.gatewayauth.AuthUIConfig;
import com.mobile.auth.gatewayauth.AuthUIControlClickListener;
import com.mobile.auth.gatewayauth.PhoneNumberAuthHelper;
import com.mobile.auth.gatewayauth.PreLoginResultListener;
import com.mobile.auth.gatewayauth.TokenResultListener;
import com.mobile.auth.gatewayauth.model.TokenRet;

/**
 * @Des 一键登录
 * @Author BLCS
 * @Time 2020/3/9 12:22
 * https://help.aliyun.com/document_detail/144231.html?spm=a2c4g.11186623.6.557.3e142dd4obdAsa#h2-5-16-16
 */
public class AutoLoginManage {
    private PhoneNumberAuthHelper mAlicomAuthHelper;
    private static  final int TIME_OUT = 5000;
    private boolean checkRet;
    private static AutoLoginManage autoLoginManage;
    private Context context;
    public AutoLoginManage(Context context) {
        this.context = context;
    }

    public static AutoLoginManage getInstance(Context context){
        if (autoLoginManage ==null){
            synchronized(AutoLoginManage.class){
                if (autoLoginManage ==null){
                    autoLoginManage = new AutoLoginManage(context.getApplicationContext());
                }
            }
        }
        return autoLoginManage;
    }

    /**
     * 初始化
     * @param listener
     */
    public AutoLoginManage init(String keySDK,OnTokenResultListener listener){
        // 1.init get token callback Listener
        TokenResultListener mTokenListener = new TokenResultListener() {
            @Override
            public void onTokenSuccess(final String ret) {
                try {
                    TokenRet  tokenRet = JSON.parseObject(ret, TokenRet.class);
                    if (tokenRet != null && !("600001").equals(tokenRet.getCode())) {
                        if (listener!=null&&mAlicomAuthHelper!=null){
                            listener.getTokenSuccess(tokenRet.getToken());
                            mAlicomAuthHelper.quitLoginPage();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onTokenFailed(final String ret) {
                try {
                    TokenRet tokenRet = JSON.parseObject(ret, TokenRet.class);
                    if (listener!=null&&mAlicomAuthHelper!=null){
                        listener.getTokenFailed(tokenRet.getMsg());
                        mAlicomAuthHelper.hideLoginLoading();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        // 2. 获取认证实例
        mAlicomAuthHelper = PhoneNumberAuthHelper.getInstance(context, mTokenListener);
        //设置SDK秘钥
        mAlicomAuthHelper.setAuthSDKInfo(keySDK);
        //检查终端是否支持号码认证
        checkRet = mAlicomAuthHelper.checkEnvAvailable();
        mAlicomAuthHelper.setAuthListener(mTokenListener);

        if (!checkRet){
            Toast.makeText(context,"当前网络不支持，请检测蜂窝网络后重试",Toast.LENGTH_SHORT).show();
        }

        //设置日志输出是否开启
        mAlicomAuthHelper.setLoggerEnable(BuildConfig.DEBUG);

        /**
         * 控件点击事件回调
         */
        mAlicomAuthHelper.setUIClickListener(new AuthUIControlClickListener() {
            @Override
            public void onClick(String code, Context context, JSONObject jsonObj) {
                if (TextUtils.isEmpty(code)) return;
                if (onUIClickListener==null) return;
                switch (code){
                    case "700000"://点击返回，⽤户取消免密登录
                        onUIClickListener.onClickBack(jsonObj);
                        break;
                    case "700001"://点击切换按钮，⽤户取消免密登录
                        onUIClickListener.onClickToggle(jsonObj);
                        break;
                    case "700002"://点击登录按钮事件
                        onUIClickListener.onClickLogin(jsonObj);
                        break;
                    case "700003"://点击check box事件
                        onUIClickListener.onClickCheck(jsonObj);
                        break;
                    case "700004"://点击协议富文本文字事件
                        onUIClickListener.onClickText(jsonObj);
                        break;
                }
            }
        });

        /**
         * 一键登录预取号
         */
        mAlicomAuthHelper.accelerateLoginPage(TIME_OUT, new PreLoginResultListener() {
            @Override
            public void onTokenSuccess(final String vendor) {
                if (onAccelerateListener==null) return;
                onAccelerateListener.onTokenSuccess(vendor);
            }

            @Override
            public void onTokenFailed(final String vendor, final String ret) {
                if (onAccelerateListener==null) return;
                onAccelerateListener.onTokenFailed(vendor,ret);
            }
        });
        configLoginTokenLand();
        return this;
    }
    /**
     * 一键登录接口
     */
    public void login(){
        if (mAlicomAuthHelper==null) return;

        mAlicomAuthHelper.getLoginToken(context, TIME_OUT);
    }

    /**
     * 退出登录页面
     */
    public void quitLoginPage(){
        if (mAlicomAuthHelper==null) return;
        mAlicomAuthHelper.quitLoginPage();
    }

    /**
     * 退出登录授权页时，授权页的loading消失由APP控制
     */
    public void hide(){
        if (mAlicomAuthHelper==null) return;
        mAlicomAuthHelper.hideLoginLoading();
    }

    private void configLoginTokenLand() {
        mAlicomAuthHelper.setAuthUIConfig(new AuthUIConfig.Builder()
                .setAppPrivacyOne("隐私条款", "https://www.baidu.com/")
                .setAppPrivacyColor(Color.GRAY, context.getResources().getColor(R.color.green))
                .setPrivacyState(true)//默认勾选
                .setStatusBarColor(Color.TRANSPARENT)
                .setVendorPrivacyPrefix("《")
                .setVendorPrivacySuffix("》")
                .setSloganTextColor(context.getResources().getColor(R.color.green))
                .setNavColor(Color.TRANSPARENT)
                .setWebViewStatusBarColor(Color.WHITE)
                .setWebNavColor(Color.WHITE)
                .setWebNavTextColor(context.getResources().getColor(R.color.green))
                .create());
    }


    /**
     * 获取 token 监听
     */
    public interface OnTokenResultListener{
        void getTokenSuccess(String token);
        void getTokenFailed(String msg);
    }

    /**
     * 预取号监听
     */
    public OnAccelerateListener onAccelerateListener;
    public interface OnAccelerateListener{
        void onTokenSuccess(String msg);
        void onTokenFailed(String msg, String ret);
    }
    public AutoLoginManage addAccelerateListener(OnAccelerateListener listener){
        this.onAccelerateListener = listener;
        return this;

    }

    /**
     * 控件点击事件回调
     */
    public OnUIClickListener onUIClickListener;
    public interface OnUIClickListener{
        void onClickBack(JSONObject jsonObj);

        void onClickToggle(JSONObject jsonObj);

        void onClickLogin(JSONObject jsonObj);

        void onClickCheck(JSONObject jsonObj);

        void onClickText(JSONObject jsonObj);
    }
    public AutoLoginManage addUIClickListener(OnUIClickImplements listener){
        this.onUIClickListener = listener;
        return this;
    }
    public static abstract class OnUIClickImplements implements OnUIClickListener{

        @Override
        public void onClickBack(JSONObject jsonObj) {
            /**
             * 点击返回，⽤户取消免密登录
             */
        }

        @Override
        public void onClickLogin(JSONObject jsonObj) {
            /**
             * 点击登录按钮事件
             */
        }

        @Override
        public void onClickCheck(JSONObject jsonObj) {
            /**
             * 点击check box事件
             */
        }

        @Override
        public void onClickText(JSONObject jsonObj) {
            /**
             * 点击协议富文本文字事件
             */
        }
    }

}
