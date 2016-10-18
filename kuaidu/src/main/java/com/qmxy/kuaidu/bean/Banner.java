package com.qmxy.kuaidu.bean;

/**
 * Created by x240 on 2016/10/11.
 */

public class Banner {
    private String img;
    private String fileurl;

    public Banner(String img, String fileurl) {
        this.img = img;
        this.fileurl = fileurl;
    }


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
                ", img='" + img + '\'' +
                ", fileurl='" + fileurl + '\'' +
                '}';
    }
}
