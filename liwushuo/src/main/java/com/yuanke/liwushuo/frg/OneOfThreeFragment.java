package com.yuanke.liwushuo.frg;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuanke.liwushuo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneOfThreeFragment extends Fragment {


    public OneOfThreeFragment() {
        // Required empty public constructor
    }

    public static OneOfThreeFragment newInstance() {
        Bundle args = new Bundle();
        OneOfThreeFragment fragment = new OneOfThreeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one_of_three, container, false);
    }

}
