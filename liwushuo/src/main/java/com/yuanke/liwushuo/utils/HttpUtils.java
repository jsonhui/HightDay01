package com.yuanke.liwushuo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 网络请求工具类
 *
 * @author arvin
 */
public class HttpUtils {
    //字符串转化时间  月-日-星期
    public static String toData(long time) {
        Date dat = new Date(time * 1000);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dat);
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("MM月dd日 E");
        String sb = format.format(gc.getTime());
        return sb;
    }

    //字符串转化时间  时间
    public static String toTime(long time) {
        Date dat = new Date(time * 1000);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dat);
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("HH:mm");
        String sb = format.format(gc.getTime());
        return sb;
    }

    private static final String TAG = "HttpUtils";

    /**
     * 下载JSON
     *
     * @param path
     * @return
     */
    public static String getJsonWithPath(String path) {
        StringBuffer sb = new StringBuffer();
        URL url = null;
        HttpURLConnection conn = null;
        BufferedReader br = null;

        try {
            url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String temp = "";
                while ((temp = br.readLine()) != null) {
                    sb.append(temp);
                }
            } else {
                Log.e(TAG, " NET IS ERROR");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(br);
            conn.disconnect();
        }
        Log.i("TAG", "---" + sb.toString());
        return sb.toString();
    }

    /**
     * 下载 图片 path图片路径
     *
     * @param
     * @return
     */
    public static Bitmap getImageWithPath(String path) {
        Bitmap map = null;
        URL url = null;
        HttpURLConnection conn = null;
        try {
            url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return BitmapFactory.decodeStream(conn.getInputStream());
            } else {
                Log.e(TAG, " NET IS ERROR");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return map;
    }


    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
