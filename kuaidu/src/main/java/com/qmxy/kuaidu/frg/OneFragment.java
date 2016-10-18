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
public class OneFragment extends Fragment {
    @BindView(R.id.time_one)
    TextView textView;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> mChannelsBeen = new ArrayList<>();
    private List<String> mTitleDatas = new ArrayList<>();
    private MyViewPagerAdapter mViewPagerAdapter;

    public static OneFragment newInstance(String path) {
        Bundle args = new Bundle();
        args.putSerializable("path", path);
        OneFragment fragment = new OneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        ButterKnife.bind(this, view);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        textView.setText(TimeUtil.getNowTime().toString());
        String path = (String) getArguments().getSerializable("path");
        mTabLayout = (TabLayout) view.findViewById(R.id.one_fragment_tab);
        mViewPager = (ViewPager) view.findViewById(R.id.one_fragment_vp);
        //2、创建适配器
        mViewPagerAdapter = new MyViewPagerAdapter(getFragmentManager(), fragments, mTitleDatas);
        //3、关联适配器
        mViewPager.setAdapter(mViewPagerAdapter);
        //TabLayout初始化
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setPadding(20, 0, 20, 0);
        mTabLayout.setupWithViewPager(mViewPager);
        //数据
        mTitleDatas.add("数码");
        mTitleDatas.add("星座");
        mTitleDatas.add("旅游");
        mTitleDatas.add("技术");
        mTitleDatas.add("生活");
        mTitleDatas.add("汽车");
        mTitleDatas.add("军事");
        mTitleDatas.add("地震");
        fragments.add(SevenOfOneFragment.newInstance(Constant.SEVEN_PATH));
        fragments.add(TwoOfOneFragment.newInstance(Constant.TWO_PATH));
        fragments.add(ThreeOfOneFragment.newInstance(Constant.THREE_PATH));
        fragments.add(FourOfOneFragment.newInstance(Constant.FOUR_PATH));
        fragments.add(FiveOfOneFragment.newInstance(Constant.FIVE_PATH));
        fragments.add(SixOfOneFragment.newInstance(Constant.SIX_PATH));
        fragments.add(OneOfOneFragment.newInstance(Constant.ONE_PATH));
        fragments.add(EightOfOneFragment.newInstance(Constant.EIGHT_PATH));
        mViewPagerAdapter.notifyDataSetChanged();
        return view;
    }
}