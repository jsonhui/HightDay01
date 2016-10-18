package com.yuanke.liwushuo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yuanke.liwushuo.R;
import com.yuanke.liwushuo.bean.SingleItem;

import java.util.List;

/**
 * 作者：Json on 2016/7/8 21:43
 * 邮箱：320175912@qq.com
 */
public class ClassfiyGridAdapter extends AbsBaseAdapter<SingleItem.DataBean.CategoriesBean.SubcategoriesBean> {
    public ClassfiyGridAdapter(Context context, List<SingleItem.DataBean.CategoriesBean.SubcategoriesBean> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder hoder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.classfiy_single_grid_item, parent, false);
            hoder = new ViewHoder();
            hoder.iv = (ImageView) convertView.findViewById(R.id.grid_view_item_iv);
            hoder.tv = (TextView) convertView.findViewById(R.id.grid_view_item_tv);
            convertView.setTag(hoder);
        } else {
            hoder = (ViewHoder) convertView.getTag();
        }
        Picasso.with(context).load(getData().get(position).getIcon_url()).into(hoder.iv);
        hoder.tv.setText(getData().get(position).getName());
        return convertView;
    }

    static class ViewHoder {
        ImageView iv;
        TextView tv;
    }
}
