package com.yuanke.alter_animation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/5/3.
 */
public class AnimationActivity extends Activity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_layout);
        imageView = (ImageView) findViewById(R.id.image);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_alpha:
                alpha();
                break;
            case R.id.btn_scale:
                scale();
                break;
            case R.id.btn_translate:
                translate();
                break;
            case R.id.btn_rotate:
                rotate();
                break;
        }
    }

    private void rotate() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        imageView.startAnimation(animation);
    }

    private void translate() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate);
        imageView.startAnimation(animation);
    }

    private void scale() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
        imageView.startAnimation(animation);
    }


    private void alpha() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        imageView.startAnimation(animation);

    }
}
