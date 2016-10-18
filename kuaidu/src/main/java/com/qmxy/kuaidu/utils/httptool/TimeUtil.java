package com.qmxy.kuaidu.utils.httptool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by x240 on 2016/10/12.
 */

public class TimeUtil {
    public static String getNowTime() {
        Date dat = new Date();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dat);
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("MM月dd日 E");
        String sb = format.format(gc.getTime());
        return sb;
    }

    public static String transferLongToDate(String millSec) {
        long time = Long.parseLong(millSec);
        Date date = new Date(time);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy年MM月dd日");
        return sd.format(date);
    }
}
