package com.yuanke.liwushuo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yuanke.liwushuo.R;
import com.yuanke.liwushuo.bean.HotDatas;

import java.util.List;

/**
 * @Author ：Created by Jason on 2016/7/3 14:34
 * @Email : 320175912@qq.com
 * @desc : HotDataAdapter（热门适配器）
 */
public class HotDataAdapter extends AbsBaseAdapter<HotDatas.DataBean.ItemsBean> {
    public HotDataAdapter(Context context, List<HotDatas.DataBean.ItemsBean> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder hoder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.two_fragment_grid_item, parent, false);
            hoder = new ViewHoder();
            hoder.tv1 = (TextView) convertView.findViewById(R.id.two_fragment_title_tv);
            hoder.tv2 = (TextView) convertView.findViewById(R.id.two_fragment_price_tv);
            hoder.tv3 = (TextView) convertView.findViewById(R.id.two_fragment_num_tv);
            hoder.iv1 = (ImageView) convertView.findViewById(R.id.two_fragment_iv);
            hoder.iv2 = (ImageView) convertView.findViewById(R.id.two_fragment_heart_iv);

            convertView.setTag(hoder);
        } else {
            hoder = (ViewHoder) convertView.getTag();
        }
        hoder.tv1.setText(getData().get(position).getData().getName());
        hoder.tv2.setText(getData().get(position).getData().getPrice());
        hoder.tv3.setText(getData().get(position).getData().getFavorites_count() + "");
        Log.i("TAG", "图片:" + getData().get(position).getData().getCover_image_url());
        Picasso.with(this.context)
                .load(getData().get(position).getData().getCover_image_url())
                .into(hoder.iv1);
        hoder.iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "你点了我", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    public static class ViewHoder {
        TextView tv1, tv2, tv3;
        ImageView iv1, iv2;
    }
}
