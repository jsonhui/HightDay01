package com.yuanke.liwushuo.frg;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuanke.liwushuo.R;

public class GuideFragment extends Fragment {
    public GuideFragment() {
    }

    public static GuideFragment newInstance() {
        Bundle args = new Bundle();
        GuideFragment fragment = new GuideFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide, container, false);

        return view;
    }
}