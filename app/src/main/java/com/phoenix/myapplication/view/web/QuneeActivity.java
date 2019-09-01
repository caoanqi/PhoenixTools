package com.phoenix.myapplication.view.web;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.phoenix.myapplication.R;
import com.phoenix.myapplication.databinding.ActivityQuneeBinding;

public class QuneeActivity extends AppCompatActivity {

    ActivityQuneeBinding activityQuneeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityQuneeBinding = DataBindingUtil.setContentView(this, R.layout.activity_qunee);
        initView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        WebSettings webSettings = activityQuneeBinding.wvQunee.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        activityQuneeBinding.wvQunee.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.e(QuneeActivity.class.getSimpleName(), consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }
        });
        activityQuneeBinding.wvQunee.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                view.loadUrl("file:///android_asset/index.html");
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
//        activityQuneeBinding.wvQunee.loadUrl("http://www.baidu.com");
        activityQuneeBinding.wvQunee.loadUrl("file:///android_asset/index.html");
    }
}
