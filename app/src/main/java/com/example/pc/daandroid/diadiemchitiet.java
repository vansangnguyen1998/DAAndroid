package com.example.pc.daandroid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class diadiemchitiet implements Serializable {
    private List<String> urlImage;
    private String moTa;
    private String TenTinhKD;
    private String TenDiaDanh;
    private String TenTinh;
    private String numStar;

    public diadiemchitiet(){}

    public diadiemchitiet( diadiemchitiet a){
        urlImage=a.getUrlImage();
        moTa=a.getMoTa();
        TenTinhKD=a.getTenTinhKD();
        TenDiaDanh=a.getTenDiaDanh();
        TenTinh=a.getTenTinh();
    }

    public  diadiemchitiet(List<String> url, String mota,  String tendiadanh, String tentinh, String tentinhkd){
        urlImage=url;
        moTa=mota;
        TenTinhKD=tentinhkd;
        TenDiaDanh=tendiadanh;
        TenTinh=tentinh;
    }

    public String getNumStar() {
        return numStar;
    }

    public void setNumStar(String numStar) {
        this.numStar = numStar;
    }

    public List<String> getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(List<String> urlImage) {
        this.urlImage = urlImage;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTenTinhKD() {
        return TenTinhKD;
    }

    public void setTenTinhKD(String tenTinhKD) {
        TenTinhKD = tenTinhKD;
    }

    public String getTenDiaDanh() {
        return TenDiaDanh;
    }

    public void setTenDiaDanh(String tenDiaDanh) {
        TenDiaDanh = tenDiaDanh;
    }

    public String getTenTinh() {
        return TenTinh;
    }

    public void setTenTinh(String tenTinh) {
        TenTinh = tenTinh;
    }
}
