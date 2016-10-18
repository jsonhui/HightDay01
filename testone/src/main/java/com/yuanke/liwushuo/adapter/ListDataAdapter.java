package com.yuanke.liwushuo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yuanke.liwushuo.R;
import com.yuanke.liwushuo.bean.ListData;

import java.util.List;

/**
 * 作者：Json on 2016/6/29 09:00
 * 邮箱：320175912@qq.com
 */
public class ListDataAdapter extends AbsBaseAdapter<ListData.DataBean.ItemsBean> {
    public ListDataAdapter(Context context, List<ListData.DataBean.ItemsBean> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder hoder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.other_list_item, parent, false);
            hoder = new ViewHoder();
            hoder.tv1 = (TextView) convertView.findViewById(R.id.other_list_item_tv_one);
            hoder.tv2 = (TextView) convertView.findViewById(R.id.other_list_item_tv_two);
            hoder.tv3 = (TextView) convertView.findViewById(R.id.other_list_item_tv_three);
            hoder.tv4 = (TextView) convertView.findViewById(R.id.other_list_item_tv_four);
            hoder.tv5 = (TextView) convertView.findViewById(R.id.other_list_item_tv_five);
            hoder.iv1 = (ImageView) convertView.findViewById(R.id.other_list_item_iv);
            hoder.iv2 = (ImageView) convertView.findViewById(R.id.other_list_item_circle_image);

            convertView.setTag(hoder);
        } else {
            hoder = (ViewHoder) convertView.getTag();
        }
        hoder.tv1.setText(getData().get(position).getColumn().getCategory());
        hoder.tv2.setText(getData().get(position).getColumn().getTitle());
        hoder.tv3.setText(getData().get(position).getAuthor().getNickname());
        hoder.tv4.setText(getData().get(position).getTitle());
        hoder.tv5.setText(getData().get(position).getLikes_count() + "");
        Log.i("TAG", "图片:" + getData().get(position).getCover_image_url());
        Picasso.with(this.context)
                .load(getData().get(position).getCover_image_url())
                .into(hoder.iv1);
        Picasso.with(this.context)
                .load(getData().get(position).getAuthor().getAvatar_url())
                .into(hoder.iv2);
        return convertView;
    }

    public static class ViewHoder {
        TextView tv1, tv2, tv3, tv4, tv5;
        ImageView iv1, iv2;
    }
}
