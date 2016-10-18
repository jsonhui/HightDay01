package com.yuanke.liwushuo.custom;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.yuanke.liwushuo.R;
import com.yuanke.liwushuo.adapter.PopupWindowAdapter;
import com.yuanke.liwushuo.bean.TabNames;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lw on 16/4/26.
 * 优惠券分类弹窗
 */
public class TitlePopup extends PopupWindow {
    private Context mContext;
    //tab的当前位置
    private int tobPossition;
    //用与回调点击位置
    private IndexCallBack indexCallBack;
    // 坐标的位置（x、y）
    private final int[] mLocation = new int[2];

    // 位置不在中心
    private int popupGravity = Gravity.NO_GRAVITY;

    // 定义列表对象
    private GridView gridView;
    private ImageView ivUp;

    // 定义弹窗子类项列表
    private List<TabNames.DataBean.ChannelsBean> mData = new ArrayList<>();

    private PopupWindowAdapter popAdapter;

    public TitlePopup(Context context, List<TabNames.DataBean.ChannelsBean> mData,
                      int possition, IndexCallBack indexCallBack) {
        // 设置布局的参数
        this(context, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, mData, possition, indexCallBack);
    }

    public TitlePopup(Context context, int width, int height, List mData,
                      int tobPossition, IndexCallBack indexCallBack) {
        this.mContext = context;
        this.mData = mData;
        this.tobPossition = tobPossition;
        this.indexCallBack = indexCallBack;
        // 设置可以获得焦点
        setFocusable(true);
        // 设置弹窗内可点击
        setTouchable(true);
        // 设置弹窗外可点击
        setOutsideTouchable(true);
        //设置背景
        setBackgroundDrawable(new BitmapDrawable());
        // 设置弹窗的宽度和高度
        setWidth(width);
        setHeight(height);
        // 设置弹窗的布局界面
        setContentView(LayoutInflater.from(mContext).inflate(R.layout.item_titlepopup, null));
        initUI();
    }

    /**
     * 初始化弹窗列表
     */
    private void initUI() {
        gridView = (GridView) getContentView().findViewById(R.id.guidview_title_popup);
        ivUp = (ImageView) getContentView().findViewById(R.id.iv_title_up);
        popAdapter = new PopupWindowAdapter(mContext, mData, tobPossition);
        gridView.setAdapter(popAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                indexCallBack.setIndex(position);
                popAdapter.setPossition(position);
                popAdapter.notifyDataSetChanged();
                dismiss();
            }
        });
        ivUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 显示弹窗列表界面
     */
    public void show(View view) {
        // 获得点击屏幕的位置坐标
        view.getLocationOnScreen(mLocation);
        Toolbar r1 = (Toolbar) view.findViewById(R.id.one_fragment_toolbar);
        int r1Height = r1.getHeight();
        int barHeight = getStatusBarHeight();
        int heightIndex = r1Height + barHeight;
        // 显示弹窗的位置
        showAtLocation(view, popupGravity, 0, heightIndex);
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public interface IndexCallBack {
        public void setIndex(int index);
    }
}