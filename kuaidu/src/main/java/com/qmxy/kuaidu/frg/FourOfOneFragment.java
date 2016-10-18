package com.qmxy.kuaidu.frg;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.qmxy.kuaidu.R;
import com.qmxy.kuaidu.act.WebActivity;
import com.qmxy.kuaidu.adapter.SevenAdapter;
import com.qmxy.kuaidu.bean.Banner;
import com.qmxy.kuaidu.bean.SevenData;
import com.qmxy.kuaidu.transforms.CubeOutTransformer;
import com.qmxy.kuaidu.utils.httptool.IOKCallBack;
import com.qmxy.kuaidu.utils.httptool.OkHttpTool;
import com.squareup.picasso.Picasso;

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
public class FourOfOneFragment extends Fragment {
    private List<SevenData> sevenDatas = new ArrayList<>();
    private List<Banner> bannerDatas = new ArrayList<>();
    private SevenAdapter sevenAdapter;
    private int[] dots = new int[]{R.drawable.second_head_point, R.drawable.second_head_point_select};
    @BindView(R.id.refreshLayout_four)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.list_view_four)
    ListView listView;
    ConvenientBanner banner;

    public FourOfOneFragment() {
    }

    public static FourOfOneFragment newInstance(String path) {
        Bundle args = new Bundle();
        FourOfOneFragment fragment = new FourOfOneFragment();
        args.putSerializable("path", path);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        /**开始自动滚动**/
        banner.startTurning(4000);
    }

    @Override
    public void onStop() {
        super.onStop();
        /**停止滚动**/
        banner.stopTurning();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one_of_four, container, false);
        ButterKnife.bind(this, view);
        final String path = (String) getArguments().getSerializable("path");
        banner = (ConvenientBanner) LayoutInflater.from(getContext()).inflate(R.layout.adapter_header_cb, null);
        banner.setLayoutParams(new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500));
        ViewPager.PageTransformer transformer = new CubeOutTransformer();
        banner.setPageTransformer(transformer);
        /**适配器**/
        sevenAdapter = new SevenAdapter(getContext(), sevenDatas);
        /**绑定适配器**/
        listView.setAdapter(sevenAdapter);
        OkHttpTool.newInstance().start(path).callback(new IOKCallBack() {
            @Override
            public void success(String result) {
                //jason解析
                try {
                    JSONObject object = new JSONObject(result);
                    JSONObject data = object.getJSONObject("data");
                    JSONArray feedlist = data.getJSONArray("feedlist");
                    JSONObject banner = data.getJSONObject("banner");
                    JSONArray banners = banner.getJSONArray("items");
                    if (bannerDatas.size() == 0) {
                        for (int j = 0; j < banners.length(); j++) {
                            String img = banners.getJSONObject(j).getString("img");
                            String fileurl = banners.getJSONObject(j).getString("fileurl");
                            bannerDatas.add(new Banner(img, fileurl));
                        }
                    }
                    if (sevenDatas.size() == 0) {
                        for (int i = 0; i < feedlist.length(); i++) {
                            JSONArray items = feedlist.getJSONObject(i).getJSONArray("items");
                            JSONObject jsonObject = items.getJSONObject(0);
                            String title = jsonObject.getString("title");
                            String img = jsonObject.getString("img");
                            String time = jsonObject.getString("time");
                            String fileurl = jsonObject.getString("fileurl");
                            sevenDatas.add(new SevenData(title, img, time, fileurl));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    /**获取广告栏数据**/
                    banner.setPages(new CBViewHolderCreator<FourOfOneFragment.BannerViewHolder>() {
                        @Override
                        public FourOfOneFragment.BannerViewHolder createHolder() {
                            return new FourOfOneFragment.BannerViewHolder();
                        }
                    }, bannerDatas);
                    banner.setPageIndicator(dots)
                            .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
                    listView.addHeaderView(banner);
                    sevenAdapter.notifyDataSetChanged();
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //定义一个标记位，来只是List<?>的哪一条具体数据
                int temp = position;
                Log.i("TAG", listView.getHeaderViewsCount() + "---------------------------------");
                if (listView.getHeaderViewsCount() == 1) {
                    temp = position - 1;
                }
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("url", (sevenDatas.get(temp)).getFileurl());
                startActivity(intent);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                OkHttpTool.newInstance().start(path).callback(new IOKCallBack() {
                    @Override
                    public void success(String result) {
                        //jason解析
                        try {
                            sevenDatas.clear();
                            JSONObject object = new JSONObject(result);
                            JSONObject data = object.getJSONObject("data");
                            JSONArray feedlist = data.getJSONArray("feedlist");
                            for (int i = 0; i < feedlist.length(); i++) {
                                JSONArray items = feedlist.getJSONObject(i).getJSONArray("items");
                                JSONObject jsonObject = items.getJSONObject(0);
                                String title = jsonObject.getString("title");
                                String img = jsonObject.getString("img");
                                String time = jsonObject.getString("time");
                                String fileurl = jsonObject.getString("fileurl");
                                sevenDatas.add(new SevenData(title, img, time, fileurl));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            sevenAdapter.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                });
            }
        });
        return view;
    }

    private class BannerViewHolder implements Holder<Banner> {
        ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, final Banner data) {
            Picasso.with(getContext()).load(data.getImg()).into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("url", data.getFileurl());
                    intent.setClass(getContext(), WebActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}