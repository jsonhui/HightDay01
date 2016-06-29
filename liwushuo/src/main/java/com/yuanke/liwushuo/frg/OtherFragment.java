package com.yuanke.liwushuo.frg;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.androidxx.yangjw.httplibrary.IOKCallBack;
import com.androidxx.yangjw.httplibrary.OkHttpTool;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yuanke.liwushuo.R;
import com.yuanke.liwushuo.act.WebActivity;
import com.yuanke.liwushuo.adapter.ListDataAdapter;
import com.yuanke.liwushuo.bean.ListData;
import com.yuanke.liwushuo.constant.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherFragment extends Fragment {
    private ListData listData;
    private List<ListData.DataBean.ItemsBean> itemsBeen = new ArrayList<>();
    private PullToRefreshListView mListView;
    private ListDataAdapter adapter;
    private int pager = 0;

    public OtherFragment() {
    }

    public static OtherFragment newInstance(int flag) {
        Bundle args = new Bundle();
        args.putSerializable("flag", flag);
        OtherFragment fragment = new OtherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other, container, false);
        final int flag = (int) getArguments().getSerializable("flag");
        mListView = (PullToRefreshListView) view.findViewById(R.id.other_fragment_ptlv);
        /****添加为空的进度***/
        ProgressBar progressBar = new ProgressBar(getActivity());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressBar.setLayoutParams(params);
        mListView.setEmptyView(progressBar);
        /****添加为空的进度***/
        //适配器
        adapter = new ListDataAdapter(getContext(), itemsBeen);
        //绑定适配器
        mListView.setAdapter(adapter);
        //如果要使用上拉
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        //数据
        Log.i("TAG---+", Constant.LIST_DATA_FRONT + flag + Constant.LIST_DATA_BEHIND);
        OkHttpTool.newInstance()
                .start(Constant.LIST_DATA_FRONT + flag + Constant.LIST_DATA_BEHIND + 0 * 20)
                .callback(new IOKCallBack() {
                    @Override
                    public void success(String result) {
                        adapter.getData().clear();//清空适配器
                        Gson gson = new Gson();
                        listData = gson.fromJson(result, ListData.class);
                        List<ListData.DataBean.ItemsBean> items = listData.getData().getItems();
                        adapter.getData().addAll(items);//加数据到适配器中
                        adapter.notifyDataSetChanged();//刷新适配器
                    }
                });
        //监听
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pager = 0;
                OkHttpTool.newInstance()
                        .start(Constant.LIST_DATA_FRONT + flag + Constant.LIST_DATA_BEHIND + pager * 20)
                        .callback(new IOKCallBack() {
                            @Override
                            public void success(String result) {
                                adapter.getData().clear();//清空适配器
                                Gson gson = new Gson();
                                listData = gson.fromJson(result, ListData.class);
                                List<ListData.DataBean.ItemsBean> items = listData.getData().getItems();
                                adapter.getData().addAll(items);//加数据到适配器中
                                adapter.notifyDataSetChanged();//刷新适配器
                                mListView.onRefreshComplete();
                            }
                        });
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pager++;
                OkHttpTool.newInstance()
                        .start(Constant.LIST_DATA_FRONT + flag + Constant.LIST_DATA_BEHIND + pager * 20)
                        .callback(new IOKCallBack() {
                            @Override
                            public void success(String result) {
                                Gson gson = new Gson();
                                ListData data = gson.fromJson(result, ListData.class);
                                List<ListData.DataBean.ItemsBean> items = data.getData().getItems();
                                Log.i("YUANKE+++", items.toString());
                                adapter.getData().addAll(items);
                                adapter.notifyDataSetChanged();
                                mListView.onRefreshComplete();
                            }
                        });

                mListView.onRefreshComplete();
            }
        });

        //设置监听
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //他做的监听，position表示行数，第一个item的position等于1
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //定义一个标记位，来只是List<?>的哪一条具体数据
                int flag = 0;
                //得到内置的ListView
                ListView listView = mListView.getRefreshableView();
                /**
                 * listView.getHeaderViewsCount()得到头的数量，
                 * 大于1的时候,表示加了头(可能不止一个头)，在这里只加一个头
                 * 等于1的时候，表示没有加头
                 */
                Log.i("TAG", listView.getHeaderViewsCount() + "---------------------------------");
                if (listView.getHeaderViewsCount() == 1) {
                    flag = position - 1;
                } else {
                    flag = position - 2;
                }
                Log.i("TAG", "我是通过item进来的");
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("url", itemsBeen.get(flag).getContent_url());
                startActivity(intent);
            }
        });
        return view;
    }
}
