package com.qmxy.kuaidu.utils.commontool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 网络请求工具类
 *
 * @author arvin
 */
public class HttpUtils {
    /**
     * 缓存的解决
     **/


    public static String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }


    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    // 获取文件
    //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    /**
     * 缓存的解决
     **/
    private final static String[] hex = {"00", "01", "02", "03", "04", "05",
            "06", "07", "08", "09", "0A", "0B", "0C", "0D", "0E", "0F", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "1A", "1B",
            "1C", "1D", "1E", "1F", "20", "21", "22", "23", "24", "25", "26",
            "27", "28", "29", "2A", "2B", "2C", "2D", "2E", "2F", "30", "31",
            "32", "33", "34", "35", "36", "37", "38", "39", "3A", "3B", "3C",
            "3D", "3E", "3F", "40", "41", "42", "43", "44", "45", "46", "47",
            "48", "49", "4A", "4B", "4C", "4D", "4E", "4F", "50", "51", "52",
            "53", "54", "55", "56", "57", "58", "59", "5A", "5B", "5C", "5D",
            "5E", "5F", "60", "61", "62", "63", "64", "65", "66", "67", "68",
            "69", "6A", "6B", "6C", "6D", "6E", "6F", "70", "71", "72", "73",
            "74", "75", "76", "77", "78", "79", "7A", "7B", "7C", "7D", "7E",
            "7F", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89",
            "8A", "8B", "8C", "8D", "8E", "8F", "90", "91", "92", "93", "94",
            "95", "96", "97", "98", "99", "9A", "9B", "9C", "9D", "9E", "9F",
            "A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "AA",
            "AB", "AC", "AD", "AE", "AF", "B0", "B1", "B2", "B3", "B4", "B5",
            "B6", "B7", "B8", "B9", "BA", "BB", "BC", "BD", "BE", "BF", "C0",
            "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "CA", "CB",
            "CC", "CD", "CE", "CF", "D0", "D1", "D2", "D3", "D4", "D5", "D6",
            "D7", "D8", "D9", "DA", "DB", "DC", "DD", "DE", "DF", "E0", "E1",
            "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "EA", "EB", "EC",
            "ED", "EE", "EF", "F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7",
            "F8", "F9", "FA", "FB", "FC", "FD", "FE", "FF"};

    private final static byte[] val = {0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x00, 0x01,
            0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F};

    /** */
    /**
     * 编码
     *
     * @param s
     * @return
     */
    public static String escape(String s) {
        StringBuffer sbuf = new StringBuffer();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            int ch = s.charAt(i);
            if ('A' <= ch && ch <= 'Z') {
                sbuf.append((char) ch);
            } else if ('a' <= ch && ch <= 'z') {
                sbuf.append((char) ch);
            } else if ('0' <= ch && ch <= '9') {
                sbuf.append((char) ch);
            } else if (ch == '-' || ch == '_' || ch == '.' || ch == '!'
                    || ch == '~' || ch == '*' || ch == '\'' || ch == '('
                    || ch == ')') {
                sbuf.append((char) ch);
            } else if (ch <= 0x007F) {
                sbuf.append('%');
                sbuf.append(hex[ch]);
            } else {
                sbuf.append('%');
                sbuf.append('u');
                sbuf.append(hex[(ch >>> 8)]);
                sbuf.append(hex[(0x00FF & ch)]);
            }
        }
        return sbuf.toString();
    }

    /** */
    /**
     * 解码 说明：本方法保证 不论参数s是否经过escape()编码，均能得到正确的“解码”结果
     *
     * @param s
     * @return
     */
    public static String unescape(String s) {
        StringBuffer sbuf = new StringBuffer();
        int i = 0;
        int len = s.length();
        while (i < len) {
            int ch = s.charAt(i);
            if ('A' <= ch && ch <= 'Z') {
                sbuf.append((char) ch);
            } else if ('a' <= ch && ch <= 'z') {
                sbuf.append((char) ch);
            } else if ('0' <= ch && ch <= '9') {
                sbuf.append((char) ch);
            } else if (ch == '-' || ch == '_' || ch == '.' || ch == '!'
                    || ch == '~' || ch == '*' || ch == '\'' || ch == '('
                    || ch == ')') {
                sbuf.append((char) ch);
            } else if (ch == '%') {
                int cint = 0;
                if ('u' != s.charAt(i + 1)) {
                    cint = (cint << 4) | val[s.charAt(i + 1)];
                    cint = (cint << 4) | val[s.charAt(i + 2)];
                    i += 2;
                } else {
                    cint = (cint << 4) | val[s.charAt(i + 2)];
                    cint = (cint << 4) | val[s.charAt(i + 3)];
                    cint = (cint << 4) | val[s.charAt(i + 4)];
                    cint = (cint << 4) | val[s.charAt(i + 5)];
                    i += 5;
                }
                sbuf.append((char) cint);
            } else {
                sbuf.append((char) ch);
            }
            i++;
        }
        return sbuf.toString();
    }

    //字符串转化时间  月-日-年
    public static String toData(long time) {
        Date dat = new Date(time);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dat);
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日 yyyy年");
        String sb = format.format(gc.getTime());
        return sb;
    }

    //返TADA
    public static String getTAdA(String path) {
        return (path.split("handdata_id=")[1].split("n1")[0]);
    }

    //返回toutuNum
    public static String getToutuNum(String path) {
        return (path.split("handdata_id=")[1].split("n1")[1].split("20&")[1]);
    }

    //字符串转化时间  月-日-年
    public static String toDataTwo(long time) {
        Date dat = new Date(time);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dat);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd/");
        String sb = format.format(gc.getTime());
        return sb;
    }

    public static String toHM(long time) {
        Date dat = new Date(time);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dat);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String sb = format.format(gc.getTime());
        return sb;
    }

    //字符串转化时间 小时
    public static int getNowH() {
        SimpleDateFormat df = new SimpleDateFormat("HH");//设置日期格式
        String sb = df.format(new Date());// new Date()为获取当前系统时间
        int i = Integer.parseInt(sb);
        return i;
    }

    //字符串转化时间 年月日
    public static String getNowTime(int i) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - i);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
            String format = dft.format(endDate);
            return format;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    //字符串转化时间 小时
    public static int getNowM() {
        SimpleDateFormat df = new SimpleDateFormat("MM");//设置日期格式
        String sb = df.format(new Date());// new Date()为获取当前系统时间
        int i = Integer.parseInt(sb);
        return i;
    }

    //字符串转化时间  小时
    public static int toH(long time) {
        Date dat = new Date(time);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dat);
        SimpleDateFormat format = new SimpleDateFormat("HH");
        String sb = format.format(gc.getTime());
        int i = Integer.parseInt(sb);
        return i;
    }

    //字符串转化时间  天
    public static int toD(long time) {
        Date dat = new Date(time);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dat);
        SimpleDateFormat format = new SimpleDateFormat("dd");
        String sb = format.format(gc.getTime());
        int i = Integer.parseInt(sb);
        return i;
    }

    //字符串转化时间  小时
    public static int toTimeM(long time) {
        Date dat = new Date(time);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dat);
        SimpleDateFormat format = new SimpleDateFormat("mm");
        String sb = format.format(gc.getTime());
        int i = Integer.parseInt(sb);
        return i;
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

    public static int toM(long time) {
        Date dat = new Date(time);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dat);
        SimpleDateFormat format = new SimpleDateFormat("mm");
        String sb = format.format(gc.getTime());
        int i = Integer.parseInt(sb);
        return i;

    }
}
