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
public class TwoOfTwoFragment extends Fragment {
    public TwoOfTwoFragment() {
    }

    public static TwoOfTwoFragment newInstance() {
        Bundle args = new Bundle();
        TwoOfTwoFragment fragment = new TwoOfTwoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two_of_two, container, false);
        return view;
    }

}
