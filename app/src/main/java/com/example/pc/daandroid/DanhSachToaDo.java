package com.example.pc.daandroid;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class DanhSachToaDo {

    private String KhoanCach;
    private String ThoiGian;
    private String End_Address;
    private LatLng endLatlng;
    private LatLng starLatLng;
    private String Star_Address;
    private List<LatLng> DanhSach;

    public DanhSachToaDo(){

    }

    public DanhSachToaDo(String khoanCach, String thoiGian, String end_Address, LatLng endLatlng, LatLng starLatLng, String star_Address, List<LatLng> danhSach) {
        KhoanCach = khoanCach;
        ThoiGian = thoiGian;
        End_Address = end_Address;
        this.endLatlng = endLatlng;
        this.starLatLng = starLatLng;
        Star_Address = star_Address;
        DanhSach = danhSach;
    }

    public String getKhoanCach() {
        return KhoanCach;
    }

    public void setKhoanCach(String khoanCach) {
        KhoanCach = khoanCach;
    }

    public String getThoiGian() {
        return ThoiGian;
    }

    public void setThoiGian(String thoiGian) {
        ThoiGian = thoiGian;
    }

    public String getEnd_Address() {
        return End_Address;
    }

    public void setEnd_Address(String end_Address) {
        End_Address = end_Address;
    }

    public LatLng getEndLatlng() {
        return endLatlng;
    }

    public void setEndLatlng(LatLng endLatlng) {
        this.endLatlng = endLatlng;
    }

    public LatLng getStarLatLng() {
        return starLatLng;
    }

    public void setStarLatLng(LatLng starLatLng) {
        this.starLatLng = starLatLng;
    }

    public String getStar_Address() {
        return Star_Address;
    }

    public void setStar_Address(String star_Address) {
        Star_Address = star_Address;
    }

    public List<LatLng> getDanhSach() {
        return DanhSach;
    }

    public void setDanhSach(List<LatLng> danhSach) {
        DanhSach = danhSach;
    }
}
