package com.example.pc.daandroid;

public class danhgia {
    private String noiDung;
    private int sao;

    public danhgia(){}

    public danhgia(String a, int b){
        noiDung=a;
        sao=b;
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
