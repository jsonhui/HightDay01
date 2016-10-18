package com.qmxy.kuaidu.bean;

/**
 * Created by x240 on 2016/10/11.
 */

public class SevenData {
    private String title;
    private String img;
    private String time;
    private String fileurl;

    public SevenData(String img, String fileurl) {
        this.img = img;
        this.fileurl = fileurl;
    }

    public SevenData(String title, String img, String time, String fileurl) {
        this.title = title;
        this.img = img;
        this.time = time;
        this.fileurl = fileurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    @Override
    public String toString() {
        return "SevenData{" +
                "title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", time='" + time + '\'' +
                ", fileurl='" + fileurl + '\'' +
                '}';
    }
}
