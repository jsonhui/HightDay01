package com.qmxy.kuaidu.act;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.webkit.WebView;

import com.qmxy.kuaidu.R;


public class WebActivity extends AppCompatActivity {
    //声明一个WebView视图
    private WebView webView;
    //声明一个数据源（String的Url地址）
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_web);
        //初始化视图
        initView();
        //初始化数据（对于WebView来说，他的数据就是一个访问的URL地址）
        initData();
        // 将这个url直接给这个视图就ok了，这就是WebView的作用所在。
        this.webView.loadUrl(url);
    }

    //初始化数据（对于WebView来说，他的数据就是一个访问的URL地址）
    private void initData() {
        this.url = getIntent().getStringExtra("url");
    }

    //初始化视图
    private void initView() {
        this.webView = (WebView) findViewById(R.id.webView_web);
    }
}
