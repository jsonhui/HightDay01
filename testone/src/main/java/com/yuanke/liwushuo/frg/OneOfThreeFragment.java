package com.yuanke.liwushuo.frg;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidxx.yangjw.httplibrary.IOKCallBack;
import com.androidxx.yangjw.httplibrary.OkHttpTool;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.yuanke.liwushuo.R;
import com.yuanke.liwushuo.adapter.AbsBaseAdapter;
import com.yuanke.liwushuo.bean.Column;
import com.yuanke.liwushuo.bean.ThreeDatas;
import com.yuanke.liwushuo.custom.TwoWayGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneOfThreeFragment extends Fragment {
    private TwoWayGridView gridView;
    private MyGridViewAdapter adapter;
    private List<Column.DataBean.ColumnsBean> beanList = new ArrayList<>();
    private TwoWayGridView gridView1, gridView2, gridView3;
    private List<ThreeDatas.DataBean.ChannelGroupsBean.ChannelsBean> channelGroupsBeen1 = new ArrayList<>();
    private List<ThreeDatas.DataBean.ChannelGroupsBean.ChannelsBean> channelGroupsBeen2 = new ArrayList<>();
    private List<ThreeDatas.DataBean.ChannelGroupsBean.ChannelsBean> channelGroupsBeen3 = new ArrayList<>();
    private MyThreeGridAdapter adapter1, adapter2, adapter3;
    private TextView tv1, tv2, tv3;

    public OneOfThreeFragment() {
    }

    public static OneOfThreeFragment newInstance(String strategy_column, String strategy_three) {
        Bundle args = new Bundle();
        args.putSerializable("strategy_column", strategy_column);
        args.putSerializable("strategy_three", strategy_three);
        OneOfThreeFragment fragment = new OneOfThreeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two_of_three, container, false);
        //视图
        initView(view);
        //适配器
        initAdapter();
        //绑定适配器
        bindAdapter();
        //数据
        String strategy_column = (String) getArguments().getSerializable("strategy_column");
        String strategy_three = (String) getArguments().getSerializable("strategy_three");
        OkHttpTool.newInstance().start(strategy_three).callback(new IOKCallBack() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                ThreeDatas threeDatas = gson.fromJson(result, ThreeDatas.class);
                List<ThreeDatas.DataBean.ChannelGroupsBean> channel_groups = threeDatas.getData().getChannel_groups();
                for (int i = 0; i < channel_groups.size(); i++) {
                    switch (i) {
                        case 0:
                            channelGroupsBeen1 = channel_groups.get(i).getChannels();
                            if (channelGroupsBeen1.size() <= 6) {
                                tv1.setVisibility(View.INVISIBLE);
                            }
                            adapter1.getData().addAll(channelGroupsBeen1);
                            adapter1.notifyDataSetChanged();
                            break;
                        case 1:
                            channelGroupsBeen2 = channel_groups.get(i).getChannels();
                            if (channelGroupsBeen2.size() <= 6) {
                                tv2.setVisibility(View.INVISIBLE);
                            }
                            adapter2.getData().addAll(channelGroupsBeen2);
                            adapter2.notifyDataSetChanged();
                            break;
                        case 2:
                            channelGroupsBeen3 = channel_groups.get(i).getChannels();
                            if (channelGroupsBeen3.size() <= 6) {
                                tv3.setVisibility(View.INVISIBLE);
                            }
                            adapter3.getData().addAll(channelGroupsBeen3);
                            adapter3.notifyDataSetChanged();
                            break;
                    }
                }
            }
        });
        OkHttpTool.newInstance().start(strategy_column + 0).callback(new IOKCallBack() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                Column column = gson.fromJson(result, Column.class);
                beanList = column.getData().getColumns();
                adapter.getData().addAll(beanList);
                adapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    //初始化适配器
    private void initAdapter() {
        adapter = new MyGridViewAdapter(getContext(), beanList);
        adapter1 = new MyThreeGridAdapter(getContext(), channelGroupsBeen1);
        adapter2 = new MyThreeGridAdapter(getContext(), channelGroupsBeen2);
        adapter3 = new MyThreeGridAdapter(getContext(), channelGroupsBeen3);
    }

    //初始化适配器
    private void bindAdapter() {
        gridView.setAdapter(adapter);
        gridView1.setAdapter(adapter1);
        gridView2.setAdapter(adapter2);
        gridView3.setAdapter(adapter3);
    }

    //初始化视图
    private void initView(View view) {
        gridView = (TwoWayGridView) view.findViewById(R.id.strategy_column_grid_view);
        gridView1 = (TwoWayGridView) view.findViewById(R.id.strategy_column_grid_view1);
        gridView2 = (TwoWayGridView) view.findViewById(R.id.strategy_column_grid_view2);
        gridView3 = (TwoWayGridView) view.findViewById(R.id.strategy_column_grid_view3);
        tv1 = (TextView) view.findViewById(R.id.strategy_column_right_tv1);
        tv2 = (TextView) view.findViewById(R.id.strategy_column_right_tv2);
        tv3 = (TextView) view.findViewById(R.id.strategy_column_right_tv3);
    }

    private class MyGridViewAdapter extends AbsBaseAdapter<Column.DataBean.ColumnsBean> {
        public MyGridViewAdapter(Context context, List<Column.DataBean.ColumnsBean> data) {
            super(context, data);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHoder hoder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.column_item, parent, false);
                hoder = new ViewHoder();
                hoder.iv = (ImageView) convertView.findViewById(R.id.column_item_iv);
                hoder.tv1 = (TextView) convertView.findViewById(R.id.column_item_tv1);
                hoder.tv2 = (TextView) convertView.findViewById(R.id.column_item_tv2);
                hoder.tv3 = (TextView) convertView.findViewById(R.id.column_item_tv3);
                convertView.setTag(hoder);
            } else {
                hoder = (ViewHoder) convertView.getTag();
            }
            hoder.tv1.setText(getData().get(position).getTitle());
            hoder.tv2.setText(getData().get(position).getSubtitle());
            hoder.tv3.setText(getData().get(position).getAuthor());
            Picasso.with(context).load(getData().get(position).getBanner_image_url()).into(hoder.iv);
            return convertView;
        }

        class ViewHoder {
            TextView tv1, tv2, tv3;
            ImageView iv;
        }
    }

    private class MyThreeGridAdapter extends AbsBaseAdapter<ThreeDatas.DataBean.ChannelGroupsBean.ChannelsBean> {

        public MyThreeGridAdapter(Context context, List<ThreeDatas.DataBean.ChannelGroupsBean.ChannelsBean> data) {
            super(context, data);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ThreeViewHoder hoder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.expand_grid_view_item, parent, false);
                hoder = new ThreeViewHoder();
                hoder.iv = (ImageView) convertView.findViewById(R.id.expand_list_view_item_iv);
                convertView.setTag(hoder);
            } else {
                hoder = (ThreeViewHoder) convertView.getTag();
            }
            Picasso.with(context).load(getData().get(position).getCover_image_url()).into(hoder.iv);
            hoder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "你点了我！", Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }

        class ThreeViewHoder {
            ImageView iv;
        }
    }
}
