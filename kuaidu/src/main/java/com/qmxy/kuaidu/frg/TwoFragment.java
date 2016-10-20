package com.qmxy.kuaidu.frg;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.qmxy.kuaidu.R;
import com.qmxy.kuaidu.adapter.MyViewPagerAdapter;
import com.qmxy.kuaidu.constant.Constant;
import com.qmxy.kuaidu.utils.httptool.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TwoFragment extends Fragment {
    @BindView(R.id.time_three)
    TextView textView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> mTitleDatas = new ArrayList<>();
    private MyViewPagerAdapter mViewPagerAdapter;

    public static TwoFragment newInstance(String path) {

        Bundle args = new Bundle();
        args.putSerializable("path", path);
        TwoFragment fragment = new TwoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        ButterKnife.bind(this, view);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        textView.setText(TimeUtil.getNowTime().toString());
        String path = (String) getArguments().getSerializable("path");
        mTabLayout = (TabLayout) view.findViewById(R.id.two_fragment_tab);
        mViewPager = (ViewPager) view.findViewById(R.id.two_fragment_vp);
        //2、创建适配器
        mViewPagerAdapter = new MyViewPagerAdapter(getFragmentManager(), fragments, mTitleDatas);
        //3、关联适配器
        mViewPager.setAdapter(mViewPagerAdapter);
        //TabLayout初始化
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setPadding(10, 0, 10, 0);
        mTabLayout.setupWithViewPager(mViewPager);
        //数据
        mTitleDatas.add("推荐");
        mTitleDatas.add("关注");
//        mTitleDatas.add("发布");
        fragments.add(OneOfTwoFragment.newInstance(Constant.TUIJIAN_PATH));
        fragments.add(TwoOfTwoFragment.newInstance());
//        fragments.add(ThreeOfTwoFragment.newInstance());
        mViewPagerAdapter.notifyDataSetChanged();
        return view;
    }
}