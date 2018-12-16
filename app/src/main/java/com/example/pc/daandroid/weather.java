package com.example.pc.daandroid;

public class weather {
    private String date;
    private int minValue;
    private int maxValue;

    private int icon_Ngay;
    private int icon_Dem;

    private String url;

    public weather(String date, int minValue, int maxValue, int icon_Ngay, int icon_Dem,String url) {
        this.date = date;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.icon_Ngay = icon_Ngay;
        this.icon_Dem = icon_Dem;
        this.url=url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getIcon_Ngay() {
        return icon_Ngay;
    }

    public void setIcon_Ngay(int icon_Ngay) {
        this.icon_Ngay = icon_Ngay;
    }

    public int getIcon_Dem() {
        return icon_Dem;
    }

    public void setIcon_Dem(int icon_Dem) {
        this.icon_Dem = icon_Dem;
    }
}

