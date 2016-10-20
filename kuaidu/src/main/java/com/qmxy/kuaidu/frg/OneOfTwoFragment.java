package com.qmxy.kuaidu.frg;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.qmxy.kuaidu.R;
import com.qmxy.kuaidu.act.WebActivity;
import com.qmxy.kuaidu.adapter.TuiJianAdapter;
import com.qmxy.kuaidu.bean.TuiJian;
import com.qmxy.kuaidu.utils.httptool.IOKCallBack;
import com.qmxy.kuaidu.utils.httptool.OkHttpTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneOfTwoFragment extends Fragment {
    @BindView(R.id.list_view_one_of_two)
    ListView listView;
    @BindView(R.id.refreshLayout_one_of_two)
    SwipeRefreshLayout refreshLayout;
    private List<TuiJian> tuiJianList = new ArrayList<>();
    private TuiJianAdapter tuijianAdapter;

    public OneOfTwoFragment() {
    }

    public static OneOfTwoFragment newInstance(String path) {
        Bundle args = new Bundle();
        args.putSerializable("path", path);
        OneOfTwoFragment fragment = new OneOfTwoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one_of_two2, container, false);
        ButterKnife.bind(this, view);
        final String path = (String) getArguments().getSerializable("path");
        /**适配器**/
        tuijianAdapter = new TuiJianAdapter(getContext(), tuiJianList);
        /**绑定适配器**/
        listView.setAdapter(tuijianAdapter);
        /**数据**/
        OkHttpTool.newInstance().start(path).callback(new IOKCallBack() {
            @Override
            public void success(String result) {
                //jason解析
                try {
                    JSONObject object = new JSONObject(result);
                    JSONObject data = object.getJSONObject("data");
                    JSONArray feedlist = data.getJSONArray("feedlist");
                    if (tuiJianList.size() == 0) {
                        for (int i = 0; i < feedlist.length(); i++) {
                            JSONArray items = feedlist.getJSONObject(i).getJSONArray("items");
                            JSONObject jsonObject = items.getJSONObject(0);
                            String ext = jsonObject.getString("ext");
                            String time = jsonObject.getString("time");
                            String title = jsonObject.getString("title");
                            String desc = jsonObject.getString("desc");
                            String img = jsonObject.getString("img");
                            String img02 = jsonObject.getString("img02");
                            String img03 = jsonObject.getString("img03");
                            int piccount = jsonObject.getInt("piccount");
                            int commentCount = jsonObject.getInt("commentCount");
                            int followCount = jsonObject.getInt("followCount");
                            String fileurl = jsonObject.getString("fileurl");
                            tuiJianList.add(new TuiJian(ext, time, title, img, img02, img03, desc, piccount, followCount, commentCount, fileurl));
                        }
                        Log.i("json", tuiJianList.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    tuijianAdapter.notifyDataSetChanged();
                }
            }
        });
        /**监听**/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("url", (tuiJianList.get(i)).getFileurl());
                startActivity(intent);
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                OkHttpTool.newInstance().start(path).callback(new IOKCallBack() {
                    @Override
                    public void success(String result) {
                        //jason解析
                        try {
                            tuiJianList.clear();
                            JSONObject object = new JSONObject(result);
                            JSONObject data = object.getJSONObject("data");
                            JSONArray feedlist = data.getJSONArray("feedlist");
                            for (int i = 0; i < feedlist.length(); i++) {
                                JSONArray items = feedlist.getJSONObject(i).getJSONArray("items");
                                JSONObject jsonObject = items.getJSONObject(0);
                                String ext = jsonObject.getString("ext");
                                String time = jsonObject.getString("time");
                                String title = jsonObject.getString("title");
                                String desc = jsonObject.getString("desc");
                                String img = jsonObject.getString("img");
                                String img02 = jsonObject.getString("img02");
                                String img03 = jsonObject.getString("img03");
                                int piccount = jsonObject.getInt("piccount");
                                int commentCount = jsonObject.getInt("commentCount");
                                int followCount = jsonObject.getInt("followCount");
                                String fileurl = jsonObject.getString("fileurl");
                                tuiJianList.add(new TuiJian(ext, time, title, img, img02, img03, desc, piccount, followCount, commentCount, fileurl));
                            }
                            Log.i("json", tuiJianList.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            tuijianAdapter.notifyDataSetChanged();
                            refreshLayout.setRefreshing(false);
                        }
                    }
                });

            }
        });
        return view;
    }

}
