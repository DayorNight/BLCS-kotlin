package com.blcs.common.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import com.blcs.common.Base.BaseActivity;
import com.blcs.common.R;
import com.just.agentweb.AgentWeb;

import org.jetbrains.annotations.NotNull;

/**
 * @Author BLCS
 * @Time 2020/4/21 14:15
 */
public class WebActivity extends BaseActivity {

    private AgentWeb mAgentWeb;
    private Toolbar toolbar;
    private String MyTitle;

    @Override
    public int setLayout() {
        return R.layout.activity_web;
    }

    public static void start(Context context,String title,String url){
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("TITLE",title);
        intent.putExtra("URL",url);
        context.startActivity(intent);
    }

    @Override
    public void initUI() {
        MyTitle = getIntent().getStringExtra("TITLE");
        String url = getIntent().getStringExtra("URL");
        initToolbar(MyTitle);
        LinearLayout webView = findViewById(R.id.ll_Agentview);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(webView, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(new MyWebChromeClient())
                .createAgentWeb()
                .ready()
                .go(url);
    }
    class MyWebChromeClient extends WebChromeClient{
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (TextUtils.isEmpty(MyTitle)) toolbar.setTitle(title);
        }
    }
    @NotNull
    private Toolbar initToolbar(String title) {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_back_white);
        toolbar.setNavigationOnClickListener(v -> {
            if (!mAgentWeb.back()){
                finish();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP&& !TextUtils.isEmpty(title)) {
            toolbar.setTitle(title);
        }
        return toolbar;
    }

    @Override
    public void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }
}
