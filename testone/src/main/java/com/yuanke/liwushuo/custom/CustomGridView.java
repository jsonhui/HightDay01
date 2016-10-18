package com.yuanke.liwushuo.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 作者：Json on 2016/7/8 21:20
 * 邮箱：320175912@qq.com
 */
public class CustomGridView extends GridView {
    public CustomGridView(Context context) {
        this(context, null);
    }

    public CustomGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
    }
}
