package com.qmxy.kuaidu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmxy.kuaidu.R;
import com.qmxy.kuaidu.bean.SevenData;
import com.qmxy.kuaidu.utils.httptool.TimeUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by x240 on 2016/10/11.
 */

public class SevenAdapter extends AbsBaseAdapter<SevenData> {
    public SevenAdapter(Context context, List<SevenData> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_seven, parent, false);
            viewHoder = new ViewHoder(convertView);
        } else {
            viewHoder = (ViewHoder) convertView.getTag();
        }
        if (!getData().get(position).getTitle().isEmpty() || !"".equals(getData().get(position).getTitle())) {
            viewHoder.text.setText(getData().get(position).getTitle());
        }
        if (!getData().get(position).getTime().isEmpty() || !"".equals(getData().get(position).getTime())) {
            viewHoder.text2.setText(TimeUtil.transferLongToDate(getData().get(position).getTime()));
        }
        if (!getData().get(position).getImg().isEmpty() || !"".equals(getData().get(position).getImg())) {
            Picasso.with(context)
                    .load(getData().get(position).getImg())
//                .error(R.mipmap.ic_launcher)
//                .placeholder(R.mipmap.ic_launcher)
                    .into(viewHoder.image);
        }
        return convertView;
    }

    static class ViewHoder {
        @BindView(R.id.text_item_seven)
        TextView text;
        @BindView(R.id.text2_item_seven)
        TextView text2;
        @BindView(R.id.image_item_seven)
        ImageView image;

        ViewHoder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }
}
