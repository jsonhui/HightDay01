package com.yuanke.liwushuo.frg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.androidxx.yangjw.httplibrary.IOKCallBack;
import com.androidxx.yangjw.httplibrary.OkHttpTool;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.picasso.Picasso;
import com.yuanke.liwushuo.R;
import com.yuanke.liwushuo.act.WebActivity;
import com.yuanke.liwushuo.adapter.IndexListDataAdapter;
import com.yuanke.liwushuo.bean.Banners;
import com.yuanke.liwushuo.bean.ListData;
import com.yuanke.liwushuo.bean.ReCycleData;
import com.yuanke.liwushuo.constant.Constant;
import com.yuanke.liwushuo.utils.HttpUtil;
import com.yuanke.liwushuo.utils.HttpUtils;
import com.yuanke.liwushuo.utils.IRequestCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OneIndexFragment extends Fragment {
    private List<ReCycleData.DataBean.SecondaryBannersBean> been = new ArrayList<>();
    private View view;
    private PullToRefreshListView pullListView;
    private List<Banners.DataBean.BannersBean> imageDatas = new ArrayList<>();
    private Context mContext;
    private IndexListDataAdapter myListAdapter;
    private HeaderViewHolder headerViewHolder;
    private HeaderViewAdapter headerViewAdapter;
    private int flag;
    private List<Object> info = new ArrayList<>();
    private List<ListData.DataBean.ItemsBean> list = new ArrayList<>();
    private int pager = 0;

    public static OneIndexFragment newInstance(int flag) {
        Bundle args = new Bundle();
        args.putSerializable("flag", flag);
        OneIndexFragment fragment = new OneIndexFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_well_chosen, container, false);
        flag = (int) getArguments().getSerializable("flag");
        //视图
        pullListView = (PullToRefreshListView) view.findViewById(R.id.wellchosen_list);
        //加头视图
        addHeaderView();
        //创建适配器
        myListAdapter = new IndexListDataAdapter(getContext(), info);
        //关联适配器
        pullListView.setAdapter(myListAdapter);
        //数据
        initData(Constant.LIST_DATA_FRONT + flag + Constant.LIST_DATA_BEHIND + 0 * 20);
        pullListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //定义一个标记位，来只是List<?>的哪一条具体数据
                int temp = 0;
                //得到内置的ListView
                ListView listView = pullListView.getRefreshableView();
                /**
                 * listView.getHeaderViewsCount()得到头的数量，
                 * 大于1的时候,表示加了头(可能不止一个头)，在这里只加一个头
                 * 等于1的时候，表示没有加头
                 */
                Log.i("TAG", listView.getHeaderViewsCount() + "---------------------------------");
                if (listView.getHeaderViewsCount() == 1) {
                    temp = position - 1;
                } else {
                    temp = position - 2;
                }
                Log.i("TAG", "我是通过item进来的");
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("url", ((ListData.DataBean.ItemsBean) info.get(temp)).getContent_url());
                startActivity(intent);
            }
        });
        pullListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                initData(Constant.LIST_DATA_FRONT + flag + Constant.LIST_DATA_BEHIND + 0 * 20);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pager++;
                String path = Constant.LIST_DATA_FRONT + flag + Constant.LIST_DATA_BEHIND + pager * 20;
                HttpUtil.requestGet(path, new IRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        Gson gson = new Gson();
                        ListData listData = gson.fromJson(result, ListData.class);
                        list = listData.getData().getItems();
                        long time = list.get(0).getCreated_at();
                        String data = HttpUtils.toData(time);
                        if (!info.contains(data)) {
                            info.add(data);
                        }
                        for (int i = 0; i < list.size(); i++) {
                            ListData.DataBean.ItemsBean listBean = list.get(i);
                            //判断是否一个日期
                            String dataTemp = HttpUtils.toData(listBean.getCreated_at());
                            if (data.equals(dataTemp)) {
                                info.add(listBean);
                            } else {
                                time = listBean.getCreated_at();
                                data = HttpUtils.toData(time);
                                info.add(data);
                                info.add(listBean);
                            }
                        }
                        myListAdapter.notifyDataSetChanged();
                        pullListView.onRefreshComplete();
                    }
                });
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始自动滚动
        headerViewHolder.convenientBanner.startTurning(2000);
    }

    @Override
    public void onStop() {
        super.onStop();
        //停止滚动
        headerViewHolder.convenientBanner.stopTurning();
    }

    //添加头部布局
    private void addHeaderView() {
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.wellchosen_header_view, null);
        headerViewHolder = new HeaderViewHolder(headerView);
        setupHeaderRecyclerView(headerViewHolder);
        loadBannerDatas();//动态加载数据
        setupBanner(headerViewHolder);
        pullListView.getRefreshableView().addHeaderView(headerView);
    }

    //下载广告数据
    private void loadBannerDatas() {
        OkHttpTool.newInstance().start(Constant.BANNER).callback(new IOKCallBack() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                Banners bannerInfo = gson.fromJson(result, Banners.class);
                imageDatas.addAll(bannerInfo.getData().getBanners());
                headerViewHolder.convenientBanner.getViewPager().getAdapter().notifyDataSetChanged();
            }
        });
    }

    //设置广告栏
    private void setupBanner(HeaderViewHolder headerViewHolder) {
        headerViewHolder.convenientBanner.setPages(new CBViewHolderCreator<HeaderBannerViewHolder>() {
            @Override
            public HeaderBannerViewHolder createHolder() {
                return new HeaderBannerViewHolder();
            }
        }, imageDatas);
//                .setPageIndicator(new int[]{R.drawable.btn_check_disabled_nightmode, R.drawable.btn_check_normal})
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_LEFT);
    }

    class HeaderBannerViewHolder implements Holder<Banners.DataBean.BannersBean> {
        ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, Banners.DataBean.BannersBean data) {
            Picasso.with(mContext).load(data.getImage_url()).into(imageView);
        }
    }

    //初始化头部RecyclerView
    private void setupHeaderRecyclerView(final HeaderViewHolder headerViewHolder) {
        //创建布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        headerViewHolder.mRecyclerView.setLayoutManager(linearLayoutManager);
        //创建一个适配器
        headerViewAdapter = new HeaderViewAdapter(been);
        headerViewHolder.mRecyclerView.setAdapter(headerViewAdapter);
        //数据
        OkHttpTool.newInstance().start(Constant.CYCLE).callback(new IOKCallBack() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                ReCycleData data = gson.fromJson(result, ReCycleData.class);
                List<ReCycleData.DataBean.SecondaryBannersBean> been = data.getData().getSecondary_banners();
                headerViewAdapter.getData().clear();
                headerViewAdapter.getData().addAll(been);
                headerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    class HeaderViewHolder {

        @BindView(R.id.header_view_rv)
        RecyclerView mRecyclerView;
        @BindView(R.id.header_view_cb)
        ConvenientBanner convenientBanner;

        public HeaderViewHolder(View headerView) {
            ButterKnife.bind(this, headerView);
        }
    }

    //创建ViewHolder
    class HeaderRViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public HeaderRViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView;
        }
    }

    //创建适配器
    class HeaderViewAdapter extends RecyclerView.Adapter<HeaderRViewHolder> {
        private List<ReCycleData.DataBean.SecondaryBannersBean> been;

        public HeaderViewAdapter(List<ReCycleData.DataBean.SecondaryBannersBean> been) {
            this.been = been;
        }

        @Override
        public HeaderRViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            ImageView imageView = new ImageView(mContext);
            imageView.setPadding(10, 20, 10, 20);
            imageView.setOnClickListener(new View.OnClickListener() {
                //这里的链接待处理
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(getContext(), WebActivity.class);
//                    intent.putExtra("url", been.get(flag).getImage_url());
//                    startActivity(intent);
                }
            });
            return new HeaderRViewHolder(imageView);
        }

        @Override
        public void onBindViewHolder(HeaderRViewHolder holder, int position) {
            //flag = position;
            Picasso.with(getContext()).load(been.get(position).getImage_url()).into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return been != null ? been.size() : 0;
        }

        public List<ReCycleData.DataBean.SecondaryBannersBean> getData() {
            return this.been;
        }
    }


    //准备数据
    private void initData(String url) {
        String path = url;
        HttpUtil.requestGet(path, new IRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ListData listData = gson.fromJson(result, ListData.class);
                list = listData.getData().getItems();
                long time = list.get(0).getCreated_at();
                String data = HttpUtils.toData(time);
                info.clear();
                info.add(data);
                for (int i = 0; i < list.size(); i++) {
                    ListData.DataBean.ItemsBean listBean = list.get(i);
                    //判断是否一个日期
                    String dataTemp = HttpUtils.toData(listBean.getCreated_at());
                    if (data.equals(dataTemp)) {
                        info.add(listBean);
                    } else {
                        time = listBean.getCreated_at();
                        data = HttpUtils.toData(time);
                        info.add(data);
                        info.add(listBean);
                    }
                }
                myListAdapter.notifyDataSetChanged();
                pullListView.onRefreshComplete();
            }
        });
    }
}
