package com.blcs.livemodule;

import android.app.Application;

import com.tencent.rtmp.TXLiveBase;

/**
 * @Author BLCS
 * @Time 2020/4/9 17:00
 */
public class MApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /*https://console.cloud.tencent.com/live/license*/
        String licenceURL = "http://license.vod2.myqcloud.com/license/v1/608ea1fcaa191bed06e69c1bcc26b9ef/TXLiveSDK.licence"; // 获取到的 licence url
        String licenceKey = "70f51eea3eeb2e8d9f8bd14e0cca7043"; // 获取到的 licence key
        TXLiveBase.getInstance().setLicence(this, licenceURL, licenceKey);
    }
}
