package com.yuanke.liwushuo.frg;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androidxx.yangjw.httplibrary.IOKCallBack;
import com.androidxx.yangjw.httplibrary.OkHttpTool;
import com.google.gson.Gson;
import com.yuanke.liwushuo.R;
import com.yuanke.liwushuo.adapter.MyViewPagerAdapter;
import com.yuanke.liwushuo.bean.TabNames;
import com.yuanke.liwushuo.constant.Constant;
import com.yuanke.liwushuo.custom.TitlePopup;

import java.util.ArrayList;
import java.util.List;


public class OneFragment extends Fragment {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<Fragment> fragments = new ArrayList<>();
    private List<TabNames.DataBean.ChannelsBean> mChannelsBeen = new ArrayList<>();
    private List<String> mTitleDatas = new ArrayList<>();
    private MyViewPagerAdapter mViewPagerAdapter;
    private ImageView ivAddMore;
    private View view;

    public OneFragment() {
    }

    public static OneFragment newInstance() {
        OneFragment fragment = new OneFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_one, container, false);
        mTabLayout = (TabLayout) view.findViewById(R.id.one_fragment_tab);
        mViewPager = (ViewPager) view.findViewById(R.id.one_fragment_vp);
        ivAddMore = (ImageView) view.findViewById(R.id.one_fragment_tab_iv);
        //2、创建适配器
        mViewPagerAdapter = new MyViewPagerAdapter(getFragmentManager(), fragments, mTitleDatas);
        //3、关联适配器
        mViewPager.setAdapter(mViewPagerAdapter);
        //TabLayout初始化
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //mTabLayout.setPadding(20, 0, 20, 0);
        mTabLayout.setupWithViewPager(mViewPager);
        //数据
        OkHttpTool.newInstance().start(Constant.TAB_NAMES).callback(new IOKCallBack() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                TabNames tabNames = gson.fromJson(result, TabNames.class);
                mChannelsBeen = tabNames.getData().getChannels();
                if (fragments.size() != 0 && mTitleDatas.size() != 0) {
                    return;
                }
                for (int i = 0; i < mChannelsBeen.size(); i++) {
                    if (i == 0) {
                        fragments.add(OneIndexFragment.newInstance(mChannelsBeen.get(i).getId()));
                    } else {
                        Log.i("TAG+++", mChannelsBeen.get(i).getId() + "");
                        fragments.add(OtherFragment.newInstance(mChannelsBeen.get(i).getId()));
                    }
                    mTitleDatas.add(mChannelsBeen.get(i).getName());
                }
                Log.i("TAG+++", "fragments的长度:" + fragments.size() + "");
                mViewPagerAdapter.notifyDataSetChanged();
            }
        });
        ivAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mTabLayout.getSelectedTabPosition();
                new TitlePopup(getActivity(), mChannelsBeen, position, new TitlePopup.IndexCallBack() {
                    @Override
                    public void setIndex(int index) {
                        mTabLayout.setScrollPosition(index, 0, true);
                        mViewPager.setCurrentItem(index);
                        mViewPagerAdapter.notifyDataSetChanged();
                    }
                }).show(view);

            }
        });
        return view;
    }


}
