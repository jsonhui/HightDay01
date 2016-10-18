package com.yuanke.liwushuo.frg;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuanke.liwushuo.R;
import com.yuanke.liwushuo.adapter.ClassfiyGridAdapter;
import com.yuanke.liwushuo.bean.SingleItem;
import com.yuanke.liwushuo.custom.CustomGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DemoFragment extends Fragment {
    private List<SingleItem.DataBean.CategoriesBean.SubcategoriesBean> beanList = new ArrayList<>();
    private TextView demoTV;
    private CustomGridView demoGrid;
    private ClassfiyGridAdapter gridAdapter;
    private TextView leftTV;
    private TextView rightTV;

    public DemoFragment() {
    }

    public static DemoFragment newInstance(SingleItem.DataBean.CategoriesBean bean) {

        Bundle args = new Bundle();
        args.putSerializable("bean", bean);
        DemoFragment fragment = new DemoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo, container, false);
        SingleItem.DataBean.CategoriesBean bean = (SingleItem.DataBean.CategoriesBean) getArguments().getSerializable("bean");
        //数据
        beanList = bean.getSubcategories();
        //视图
        demoTV = (TextView) view.findViewById(R.id.demo_fragment_tv);
        leftTV = (TextView) view.findViewById(R.id.demo_fragment_letf_tv);
        rightTV = (TextView) view.findViewById(R.id.demo_fragment_right_tv);
        demoGrid = (CustomGridView) view.findViewById(R.id.demo_fragment_custom_grid);
        if ("热门分类".equals(bean.getName())) {
            demoTV.setVisibility(View.INVISIBLE);
            leftTV.setVisibility(View.INVISIBLE);
            rightTV.setVisibility(View.INVISIBLE);
        } else {
            demoTV.setText(bean.getName());
        }
        //适配器
        gridAdapter = new ClassfiyGridAdapter(getActivity(), beanList);
        //绑定适配器
        demoGrid.setAdapter(gridAdapter);
        return view;
    }
}
