package com.example.pc.daandroid;

import java.util.ArrayList;

public class diadiemchitiet {
    private ArrayList<String> URL;
    private String moTa;
    private danhgia comment;

    public ArrayList<String> getURL() {
        return URL;
    }

    public String getMoTa() {
        return moTa;
    }

    public danhgia getComment() {
        return comment;
    }

    public void setURL(ArrayList<String> URL) {
        this.URL = URL;
    }

    public void setComment(danhgia comment) {
        this.comment = comment;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

}
