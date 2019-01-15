package com.example.pc.daandroid;

import android.content.Context;
import android.content.Intent;
import android.media.Rating;
import android.net.Uri;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class trangchu_class extends Fragment implements FragmentCallBack {

    MainActivity main;
    Context context=null;
    ViewGroup horizontalXuHuong;
    ArrayList<diadiemchitiet> diadiemXuHuong;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        RelativeLayout layout_trangchu = (RelativeLayout) inflater.inflate(R.layout.fragment_trangchu,null);

        diadiemXuHuong=new ArrayList<diadiemchitiet>();

        horizontalXuHuong = (ViewGroup) layout_trangchu.findViewById(R.id.horizontal_XuHuong);

        SetHorizontalXuHuong();

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


     btnXuHuong.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Toast.makeText(context,"Xu Huong",Toast.LENGTH_SHORT).show();
             Intent intent = new Intent(getContext(), ActivityChiTiet.class);
             intent.putExtra("LoaiDuLich","XuHuong");
             startActivity(intent);
         }
     });

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

        main.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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


    private void SetHorizontalXuHuong() {
        // den ngay nay dang bí
            String XuHuong_url = "http://android1998.000webhostapp.com/php/xuhuong.php";

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest str= new StringRequest(Request.Method.POST, XuHuong_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONArray jsonArray= new JSONArray(response);
                                int n=jsonArray.length();
                                for(int i=0;i<n;i++) {

                                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);


                                    String tentinh = jsonObject.getString("tentinh");
                                    String tendiadanh=jsonObject.getString("tendiadanh");

                                    String mota=jsonObject.getString("mota");

                                    ArrayList<String> urlImage = new ArrayList<String>();

                                    JSONArray image = new JSONArray(jsonObject.getString("hinhanh"));

                                    for(int j=0;j<image.length();j++){
                                        urlImage.add((String) image.get(j));
                                    }

                                    String tentinhkd=jsonObject.getString("tentinh_KD");

                                    String star = jsonObject.getString("sosao");

                                    diadiemchitiet temp=new diadiemchitiet(urlImage,mota,tendiadanh,tentinh,tentinhkd);
                                    temp.setNumStar(star);

                                    diadiemXuHuong.add(temp);
                                    //Toast.makeText(ctx, "" + jsonObject.getString("tentinh") + jsonArray.length(), Toast.LENGTH_SHORT).show();
                                }

                                int lenXuHuong=diadiemXuHuong.size();

                                for(int i=0;i<lenXuHuong;i++){
                                    final View singleFrame = getLayoutInflater().inflate(
                                            R.layout.itemhorizontal_xuhuong, null);

                                    singleFrame.setId(i);

                                    ImageView imageView = (ImageView) singleFrame.findViewById(R.id.imageview_anh);
                                    TextView txtTen = (TextView) singleFrame.findViewById(R.id.textview_tendiadiem);
                                    //TextView txtThongTin = (TextView) singleFrame.findViewById(R.id.textview_loaidulich);

                                    RatingBar numStar = (RatingBar) singleFrame.findViewById(R.id.numStar);
                                    numStar.setRating(Integer.parseInt(diadiemXuHuong.get(i).getNumStar()));

                                    //imageView.setImageResource(R.drawable.bien);
                                    Picasso.get().load("http://android1998.000webhostapp.com/DiaDanh/"+
                                            diadiemXuHuong.get(i).getUrlImage().get(0)+"/"+
                                            diadiemXuHuong.get(i).getUrlImage().get(1)).into(imageView);
                                    txtTen.setText(diadiemXuHuong.get(i).getTenDiaDanh());

                                    horizontalXuHuong.addView(singleFrame);

                                    final int finalI = i;
                                    singleFrame.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent =new Intent(getContext(),ActivityDiaDiemChiTiet.class);
                                            //Bundle bundle1 = new Bundle();
                                            intent.putExtra("diadiem", diadiemXuHuong.get(finalI));
                                            startActivity(intent);
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

            requestQueue.add(str);

    }

}
