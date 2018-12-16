package com.example.pc.daandroid;

import java.util.ArrayList;

public class diadiemchitiet {
    private String urlImage;
    private String moTa;
    private String TenTinhKD;
    private String TenDiaDanh;
    private String TenTinh;

    public diadiemchitiet(){}

    public  diadiemchitiet(String url, String mota,  String tendiadanh, String tentinh, String tentinhkd){
        urlImage=url;
        moTa=mota;
        TenTinhKD=tentinhkd;
        TenDiaDanh=tendiadanh;
        TenTinh=tentinh;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getTenTinhKD() {
        return TenTinhKD;
    }

    public void setTenTinhKD(String tenTinhKD) {
        TenTinhKD = tenTinhKD;
    }

    public String getURL() { return urlImage; }
    public void setURL(String URL) { this.urlImage = URL; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public void setTenDiaDanh(String ten){ TenDiaDanh=ten; }
    public String getTenDiaDanh(){return TenDiaDanh;}

    public  void setTenTinh(String Ten){TenTinh=Ten;}
    public String getTenTinh(){return TenTinh;}

}
