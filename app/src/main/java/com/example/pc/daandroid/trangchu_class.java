package com.example.pc.daandroid;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class trangchu_class extends Fragment implements FragmentCallBack {

    MainActivity main;
    Context context=null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final RelativeLayout layout_trangchu = (RelativeLayout) inflater.inflate(R.layout.fragment_trangchu,null);
        // click Bien
        CardView btnBaiBien = (CardView) layout_trangchu.findViewById(R.id.btnBaiBien);
        btnBaiBien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ActivityChiTiet.class);
                intent.putExtra("LoaiDuLich","Bien");
                startActivity(intent);
            }
        });

        // click btn Rừng Núi
        CardView btnRungNui = (CardView) layout_trangchu.findViewById(R.id.btnRungNui);
        btnRungNui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ActivityChiTiet.class);
                intent.putExtra("LoaiDuLich","RungNui");
                startActivity(intent);
            }
        });

        // click btn Lịch Sử
        CardView btnLichSu = (CardView) layout_trangchu.findViewById(R.id.btnLichSu);
        btnLichSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ActivityChiTiet.class);
                intent.putExtra("LoaiDuLich","LichSu");
                startActivity(intent);
            }
        });

        //click btn Sinh Thai

        CardView btnSinhThai = (CardView) layout_trangchu.findViewById(R.id.btnSinhThai);
        btnSinhThai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ActivityChiTiet.class);
                intent.putExtra("LoaiDuLich","SinhThai");
                startActivity(intent);
            }
        });

        // click btn Mien Tay
        CardView btnMienTay = (CardView) layout_trangchu.findViewById(R.id.btnMienTay);
        btnMienTay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ActivityChiTiet.class);
                intent.putExtra("LoaiDuLich","MienTay");
                startActivity(intent);
            }
        });

        // click btn Tay Bac

        CardView btnTayBac = (CardView) layout_trangchu.findViewById(R.id.btnTayBac);
        btnTayBac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ActivityChiTiet.class);
                intent.putExtra("LoaiDuLich","TayBac");
                startActivity(intent);
            }
        });




     Button btnCaNhan = (Button) layout_trangchu.findViewById(R.id.btnCaNhan);
     Button btnXuHuong = (Button) layout_trangchu.findViewById(R.id.btnXuHuong);
     final Button btnMap = (Button) layout_trangchu.findViewById(R.id.btnMap);

        btnCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(context,MainCaNhan.class);
                //startActivity(intent);

                // cách 2
                main.onMsgFromFragToMain("TrangChu_CaNhan", "");
            }
        });


        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IIntent = new Intent(context, Map_api.class);
                startActivity(IIntent);
                //main.onMsgFromFragToMain("TrangChu_ChiTiet", "");
            }
        });

        return layout_trangchu;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity();
            main = (MainActivity) getActivity();
        }catch  (IllegalStateException e) {
            throw new IllegalStateException(
                    "MainActivity must implement callbacks");
        }
    }

    @Override
    public void onMsgFromMainToFragment(String strValue) {
        // nhận giá trị từ main về
    }




}
