package com.example.pc.daandroid;

import java.util.ArrayList;

public class diadiemchitiet {
    private String urlImage;
    private String moTa;
    private danhgia comment;
    private String TenDiaDanh;
    private String TenTinh;

    public diadiemchitiet(){}

    public  diadiemchitiet(String url, String mota, danhgia cmt, String tendiadanh, String tentinh){
        urlImage=url;
        moTa=mota;
        comment=cmt;
        TenDiaDanh=tendiadanh;
        TenTinh=tentinh;
    }

    public String getURL() { return urlImage; }
    public void setURL(String URL) { this.urlImage = URL; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public danhgia getComment() { return comment; }
    public void setComment(danhgia comment) { this.comment = comment; }

    public void setTenDiaDanh(String ten){ TenDiaDanh=ten; }
    public String getTenDiaDanh(){return TenDiaDanh;}

    public  void setTenTinh(String Ten){TenTinh=Ten;}
    public String getTenTinh(){return TenTinh;}

}
