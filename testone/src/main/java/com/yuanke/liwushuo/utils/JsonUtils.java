package com.yuanke.liwushuo.utils;

import com.google.gson.Gson;
import com.yuanke.liwushuo.bean.ListData;

import java.util.List;

/**
 * @Author ：Created by Jason on 2016/6/20 17:42
 * @Email : 320175912@qq.com
 * @desc : 工具类
 */
public class JsonUtils {
    public static List<ListData.DataBean.ItemsBean> getDataOfList(String path) {
        String json = HttpUtils.getJsonWithPath(path);
        Gson gson = new Gson();
        ListData data = gson.fromJson(json, ListData.class);
        List<ListData.DataBean.ItemsBean> info = data.getData().getItems();
        return info;
    }
}