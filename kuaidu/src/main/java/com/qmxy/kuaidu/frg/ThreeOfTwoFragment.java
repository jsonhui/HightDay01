package com.qmxy.kuaidu.frg;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qmxy.kuaidu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThreeOfTwoFragment extends Fragment {
    public ThreeOfTwoFragment() {
    }

    public static ThreeOfTwoFragment newInstance() {
        Bundle args = new Bundle();
        ThreeOfTwoFragment fragment = new ThreeOfTwoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three_of_two, container, false);

        return view;
    }

}
