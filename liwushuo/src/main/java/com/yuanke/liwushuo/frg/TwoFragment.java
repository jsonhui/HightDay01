package com.yuanke.liwushuo.frg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.androidxx.yangjw.httplibrary.IOKCallBack;
import com.androidxx.yangjw.httplibrary.OkHttpTool;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.yuanke.liwushuo.R;
import com.yuanke.liwushuo.adapter.HotDataAdapter;
import com.yuanke.liwushuo.bean.HotDatas;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author ：Created by Jason on 2016/7/3 13:36
 * @Email : 320175912@qq.com
 * @desc : TwoFragment（热门）
 */
public class TwoFragment extends Fragment {
    private PullToRefreshGridView pullGrid;
    private List<HotDatas.DataBean.ItemsBean> info = new ArrayList<>();
    private HotDataAdapter myGridAdapter;
    private String url;
    private HotDatas listData;
    private int page = 0;

    public TwoFragment() {
    }

    public static TwoFragment newInstance(String path) {
        TwoFragment fragment = new TwoFragment();
        Bundle args = new Bundle();
        args.putSerializable("path", path);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        url = (String) getArguments().getSerializable("path");
        //视图
        pullGrid = (PullToRefreshGridView) view.findViewById(R.id.two_fragment_grid);
        /****添加为空的进度***/
        ProgressBar progressBar = new ProgressBar(getActivity());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressBar.setLayoutParams(params);
        pullGrid.setEmptyView(progressBar);
        /****添加为空的进度***/
        //设置上拉下拉模式(允许)
        pullGrid.setMode(PullToRefreshBase.Mode.BOTH);
        /**设置下拉上拉样式**/
        ILoadingLayout startProxy = pullGrid.getLoadingLayoutProxy(true, false);
        startProxy.setPullLabel("");
        startProxy.setRefreshingLabel("");
        startProxy.setReleaseLabel("");
        startProxy.setLoadingDrawable(this.getResources().getDrawable(R.drawable.flip_head));
        ILoadingLayout endProxy = pullGrid.getLoadingLayoutProxy(false, true);
        endProxy.setPullLabel("");
        endProxy.setRefreshingLabel("");
        endProxy.setReleaseLabel("");
        endProxy.setLoadingDrawable(this.getResources().getDrawable(R.drawable.default_ptr_rotate));
        /**设置下拉上拉样式**/
        //创建适配器
        myGridAdapter = new HotDataAdapter(getContext(), info);
        //关联适配器
        pullGrid.setAdapter(myGridAdapter);
        //数据
        initData();
        pullGrid.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                page = 0;
                OkHttpTool.newInstance()
                        .start(url + page * 20)
                        .callback(new IOKCallBack() {
                            @Override
                            public void success(String result) {
                                myGridAdapter.getData().clear();//清空适配器
                                Gson gson = new Gson();
                                listData = gson.fromJson(result, HotDatas.class);
                                List<HotDatas.DataBean.ItemsBean> items = listData.getData().getItems();
                                myGridAdapter.getData().addAll(items);//加数据到适配器中
                                myGridAdapter.notifyDataSetChanged();//刷新适配器
                                pullGrid.onRefreshComplete();
                            }
                        });
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                page++;
                OkHttpTool.newInstance()
                        .start(url + page * 20)
                        .callback(new IOKCallBack() {
                            @Override
                            public void success(String result) {
                                Gson gson = new Gson();
                                listData = gson.fromJson(result, HotDatas.class);
                                List<HotDatas.DataBean.ItemsBean> items = listData.getData().getItems();
                                myGridAdapter.getData().addAll(items);//加数据到适配器中
                                myGridAdapter.notifyDataSetChanged();//刷新适配器
                                pullGrid.onRefreshComplete();
                            }
                        });
            }
        });
        return view;
    }

    private void initData() {
        page = 0;
        OkHttpTool.newInstance()
                .start(url + page * 20)
                .callback(new IOKCallBack() {
                    @Override
                    public void success(String result) {
                        myGridAdapter.getData().clear();//清空适配器
                        Gson gson = new Gson();
                        listData = gson.fromJson(result, HotDatas.class);
                        List<HotDatas.DataBean.ItemsBean> items = listData.getData().getItems();
                        myGridAdapter.getData().addAll(items);//加数据到适配器中
                        myGridAdapter.notifyDataSetChanged();//刷新适配器
                    }
                });
    }

}
