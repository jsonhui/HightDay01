package com.yuanke.liwushuo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuanke.liwushuo.R;

import java.util.List;

/**
 * 作者：Json on 2016/7/6 19:38
 * 邮箱：320175912@qq.com
 */
public class SingleListAdapter extends AbsBaseAdapter<String> {
    private int index;

    public SingleListAdapter(Context context, List<String> data) {
        super(context, data);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHoder hoder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.single_list_item, parent, false);
            hoder = new ViewHoder();
            hoder.tv1 = (TextView) convertView.findViewById(R.id.single_list_item_tv);
            hoder.tv0 = (TextView) convertView.findViewById(R.id.single_list_item_tv0);
            convertView.setTag(hoder);
        } else {
            hoder = (ViewHoder) convertView.getTag();
        }
        hoder.tv1.setText(getData().get(position));
        if (index == position) {
            hoder.tv0.setVisibility(View.VISIBLE);
            hoder.tv1.setTextColor(Color.RED);
            hoder.tv1.setBackgroundColor(Color.WHITE);
        } else {
            hoder.tv0.setVisibility(View.INVISIBLE);
            hoder.tv1.setTextColor(Color.BLACK);
            hoder.tv1.setBackgroundColor(context.getResources().getColor(R.color.backOfList));
        }
        return convertView;
    }

    static class ViewHoder {
        TextView tv0, tv1;
    }
}
