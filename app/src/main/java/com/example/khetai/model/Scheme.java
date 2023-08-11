package com.example.khetai.model;

public class Scheme {

    private String Header;
    private String desc;
    private int imgName;

    public Scheme(int imgName,String Header,String desc)
    {
        this.imgName=imgName;
        this.Header= Header;
        this.desc=desc;
    }

    public String getHeader() {
        return Header;
    }

    public void setHeader(String header) {
        Header = header;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImgName() {
        return imgName;
    }

    public void setImgName(int imgName) {
        this.imgName = imgName;
    }
}
