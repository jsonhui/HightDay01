package com.yuanke.liwushuo.frg;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.androidxx.yangjw.httplibrary.IOKCallBack;
import com.androidxx.yangjw.httplibrary.OkHttpTool;
import com.google.gson.Gson;
import com.yuanke.liwushuo.R;
import com.yuanke.liwushuo.adapter.SingleListAdapter;
import com.yuanke.liwushuo.adapter.SinglePageAdapter;
import com.yuanke.liwushuo.bean.SingleItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TwoOfThreeFragment extends Fragment {


    private ListView mListView;
    private ViewPager mViewPager;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> mTitleDatas = new ArrayList<>();
    private SingleListAdapter mListAdapter;
    private SinglePageAdapter mVPAdapter;

    public TwoOfThreeFragment() {
    }

    public static TwoOfThreeFragment newInstance(String path) {
        Bundle args = new Bundle();
        args.putSerializable("path", path);
        TwoOfThreeFragment fragment = new TwoOfThreeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one_of_three, container, false);
        final String path = (String) getArguments().getSerializable("path");
        /**1.视图**/
        mListView = (ListView) view.findViewById(R.id.classify_single_tab);
        mViewPager = (ViewPager) view.findViewById(R.id.classify_single_vp);
        /**2、创建适配器**/
        mListAdapter = new SingleListAdapter(this.getContext(), mTitleDatas);
        mListView.setAdapter(mListAdapter);
        mVPAdapter = new SinglePageAdapter(getFragmentManager(), fragments);
        mViewPager.setAdapter(mVPAdapter);
        /**3.数据**/
        OkHttpTool.newInstance().start(path).callback(new IOKCallBack() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                SingleItem singleItem = gson.fromJson(result, SingleItem.class);
                List<SingleItem.DataBean.CategoriesBean> been = singleItem.getData().getCategories();
                for (int i = 0; i < been.size(); i++) {
                    mTitleDatas.add(been.get(i).getName());
                    fragments.add(DemoFragment.newInstance(been.get(i)));
                }
                /**刷新适配器**/
                mListAdapter.notifyDataSetChanged();
                mVPAdapter.notifyDataSetChanged();
                /**刷新适配器**/
            }
        });
        /**4.监听**/
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewPager.setCurrentItem(position);
            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //设置那个被选中
                mListAdapter.setIndex(position);
                //认定哪个item被选中
                mListAdapter.notifyDataSetChanged();
                mListView.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        return view;
    }
}