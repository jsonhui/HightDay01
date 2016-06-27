package com.yuanke.hightday01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @Author ：Created by Jason on 2016/6/27 13:53
 * @Email : 320175912@qq.com
 * @desc : 这是一个测试
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.home_content_tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tv.setText("这是黄油刀");
    }

    @OnClick(R.id.home_submit_btn)
    public void submit(View view) {
        Toast.makeText(MainActivity.this, "这是黄油刀监听", Toast.LENGTH_SHORT).show();
    }
}
