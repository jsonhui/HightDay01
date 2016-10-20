package com.qmxy.kuaidu.act;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmxy.kuaidu.R;
import com.qmxy.kuaidu.utils.httptool.TimeUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WebActivity extends AppCompatActivity {
    @BindView(R.id.time_web)
    TextView textView;
    @BindView(R.id.back_image)
    ImageView imageView;
    //声明一个WebView视图
    private WebView webView;
    //声明一个数据源（String的Url地址）
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        textView.setText(TimeUtil.getNowTime().toString());
        //初始化视图
        initView();
        //初始化数据（对于WebView来说，他的数据就是一个访问的URL地址）
        initData();
        // 将这个url直接给这个视图就ok了，这就是WebView的作用所在。
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        /**webView.setInitialScale(100);**/
        WebSettings webSettings = webView.getSettings();
        /**支持script**/
        webSettings.setJavaScriptEnabled(true);
        /**扩大比例的缩放**/
        webSettings.setUseWideViewPort(true);
        /**自适应屏幕**/
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        /**放大缩小**/
        webSettings.setSupportZoom(true);
        /**设置可以出现缩放工具**/
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDomStorageEnabled(true);
        this.webView.loadUrl(url);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
