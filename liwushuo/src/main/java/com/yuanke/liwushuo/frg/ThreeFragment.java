package com.yuanke.liwushuo.frg;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuanke.liwushuo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ThreeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ThreeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThreeFragment extends Fragment {
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> mTitleDatas = new ArrayList<>();
    private MyViewPagerAdapter mViewPagerAdapter;
    @BindView(R.id.three_fragment_vp)
    ViewPager mViewPager;
    @BindView(R.id.three_fragment_tab)
    TabLayout mTabLayout;
    @BindView(R.id.three_fragment_text)
    TextView mTextView;

    public ThreeFragment() {
    }

    public static ThreeFragment newInstance() {
        ThreeFragment fragment = new ThreeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        ButterKnife.bind(this, view);
        //1、准备数据源
        setupData();
        setupTitleDatas();
        //2、创建适配器
        mViewPagerAdapter = new MyViewPagerAdapter(getActivity().getSupportFragmentManager());
        //3、关联适配器
        mViewPager.setAdapter(mViewPagerAdapter);
        //TabLayout初始化
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mTextView.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        mTextView.setVisibility(View.VISIBLE);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    private void setupTitleDatas() {
        mTitleDatas.add("攻略");
        mTitleDatas.add("单品");
    }

    private void setupData() {
        fragments.add(OneOfThreeFragment.newInstance());
        fragments.add(TwoOfThreeFragment.newInstance());
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {


        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleDatas.get(position);
        }
    }
}
