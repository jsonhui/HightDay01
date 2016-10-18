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
import com.yuanke.liwushuo.utils.HttpUtils;

import java.util.List;

/**
 * 作者：Json on 2016/6/30 13:57
 * 邮箱：320175912@qq.com
 */
public class IndexListDataAdapter extends AbsBaseAdapter<Object> {
    private static final int TYPE_ONE = 0;
    private static final int TYPE_TWO = 1;


    public IndexListDataAdapter(Context context, List<Object> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //获得布局类型
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_ONE:
                ViewHolderOne holderOne;
                if (convertView == null) {
                    holderOne = new ViewHolderOne();
                    convertView = inflater.inflate(R.layout.index_list_item_one, parent, false);
                    holderOne.tvOne = (TextView) convertView.findViewById(R.id.index_list_item_tv);
                    holderOne.tvBelow = (TextView) convertView.findViewById(R.id.index_list_item_tv_below);
                    convertView.setTag(holderOne);
                } else {
                    holderOne = (ViewHolderOne) convertView.getTag();
                }
                String data = getData().get(position).toString();
                holderOne.tvOne.setText(data);
                if (position == 0) {
                    String time = HttpUtils.toTime((long) ((ListData.DataBean.ItemsBean) getData().get(1)).getUpdated_at());
                    holderOne.tvBelow.setText("下次更新" + time);
                } else {
                    holderOne.tvBelow.setVisibility(View.INVISIBLE);
                }
                break;
            case TYPE_TWO:
                ViewHolderTwo holderTwo;
                if (convertView == null) {
                    holderTwo = new ViewHolderTwo();
                    convertView = inflater.inflate(R.layout.other_list_item, parent, false);
                    holderTwo.tv1 = (TextView) convertView.findViewById(R.id.other_list_item_tv_one);
                    holderTwo.tv2 = (TextView) convertView.findViewById(R.id.other_list_item_tv_two);
                    holderTwo.tv3 = (TextView) convertView.findViewById(R.id.other_list_item_tv_three);
                    holderTwo.tv4 = (TextView) convertView.findViewById(R.id.other_list_item_tv_four);
                    holderTwo.tv5 = (TextView) convertView.findViewById(R.id.other_list_item_tv_five);
                    holderTwo.iv1 = (ImageView) convertView.findViewById(R.id.other_list_item_iv);
                    holderTwo.iv2 = (ImageView) convertView.findViewById(R.id.other_list_item_circle_image);
                    convertView.setTag(holderTwo);
                } else {
                    holderTwo = (ViewHolderTwo) convertView.getTag();
                }
                ListData.DataBean.ItemsBean bean = (ListData.DataBean.ItemsBean) getData().get(position);
                Log.i("TAGE", "图片:" + bean.getCover_image_url());
                if (bean != null && bean.getColumn() != null && bean.getAuthor() != null) {
                    holderTwo.tv1.setText(bean.getColumn().getCategory());
                    holderTwo.tv2.setText(bean.getColumn().getTitle());
                    holderTwo.tv3.setText(bean.getAuthor().getNickname());
                    holderTwo.tv4.setText(bean.getTitle());
                    holderTwo.tv5.setText(bean.getLikes_count() + "");
                    Picasso.with(this.context)
                            .load(bean.getCover_image_url())
                            .into(holderTwo.iv1);
                    Picasso.with(this.context)
                            .load(bean.getAuthor().getAvatar_url())
                            .into(holderTwo.iv2);
                }
                break;
        }
        return convertView;
    }

    //获得布局类型的个数
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    //得到布局的类型
    @Override
    public int getItemViewType(int position) {
        Object obj = getData().get(position);
        return obj instanceof ListData.DataBean.ItemsBean ? TYPE_TWO : TYPE_ONE;
    }

    public static class ViewHolderOne {
        TextView tvOne, tvBelow;
    }

    public static class ViewHolderTwo {
        TextView tv1, tv2, tv3, tv4, tv5;
        ImageView iv1, iv2;
    }
}
