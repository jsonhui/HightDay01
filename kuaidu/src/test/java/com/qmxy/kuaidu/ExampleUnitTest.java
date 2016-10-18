package com.qmxy.kuaidu;

import com.qmxy.kuaidu.utils.httptool.TimeUtil;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        System.out.print(TimeUtil.getNowTime());
        // System.out.print(TimeUtil.transferLongToDate("1476156740000"));
    }
}