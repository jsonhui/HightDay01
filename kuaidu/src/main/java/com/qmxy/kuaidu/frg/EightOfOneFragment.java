package com.qmxy.kuaidu.frg;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.qmxy.kuaidu.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class EightOfOneFragment extends Fragment {
    @BindView(R.id.eight_web)
    WebView webView;

    public EightOfOneFragment() {
    }

    public static EightOfOneFragment newInstance(String path) {
        Bundle args = new Bundle();
        args.putSerializable("path", path);
        EightOfOneFragment fragment = new EightOfOneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one_of_eight, container, false);
        ButterKnife.bind(this, view);
        String path = (String) getArguments().getSerializable("path");
        ButterKnife.bind(this, view);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(path);
        return view;
    }

}
