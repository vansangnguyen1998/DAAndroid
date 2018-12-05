package com.example.pc.daandroid;

public class danhgia {
    private String noiDung;
    private int sao;
    private String User;
    private String NgayThang;

    public danhgia(){}

    public danhgia(String a, int b,String user,String ngaythang){
        noiDung=a;
        User=user;
        NgayThang=ngaythang;
        sao=b;
    }

    public void setUser(String user) {
        User = user;
    }

    public void setNgayThang(String ngayThang) {
        NgayThang = ngayThang;
    }

    public String getUser() {
        return User;
    }

    public String getNgayThang() {
        return NgayThang;
    }


    public String getNoiDung() {
        return noiDung;
    }

    public int getSao() {
        return sao;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public void setSao(int sao) {
        this.sao = sao;
    }

}
