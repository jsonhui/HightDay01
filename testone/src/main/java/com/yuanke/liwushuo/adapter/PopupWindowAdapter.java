package com.yuanke.liwushuo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuanke.liwushuo.R;
import com.yuanke.liwushuo.bean.TabNames;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LiuHao on 2016/6/2.
 */
public class PopupWindowAdapter extends BaseAdapter {
    private Context context;
    private List<TabNames.DataBean.ChannelsBean> list;
    private int possition;
    public void setPossition(int possition){
        this.possition = possition;
    }
    public PopupWindowAdapter(Context context, List<TabNames.DataBean.ChannelsBean> list, int possition) {
        this.context = context;
        this.list = list;
        this.possition = possition;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list != null ? list.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_title_pupup_item, null);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvTitle.setText(list.get(position).getName());
        if(possition == position){
            holder.tvHx.setVisibility(View.VISIBLE);
        }else {
            holder.tvHx.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tv_title_window)
        TextView tvTitle;
        @BindView(R.id.tv_title_hx)
        TextView tvHx;

        ViewHolder(View view) {
            view.setTag(this);
            ButterKnife.bind(this, view);
        }
    }
}
