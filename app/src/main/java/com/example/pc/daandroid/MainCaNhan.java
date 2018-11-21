package com.example.pc.daandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainCaNhan extends Fragment implements FragmentCallBack {


    TextView hTen,ngSinh,gTinh;
    ImageView anhDaiDien;
    MainActivity main;
    Context context;

    public static MainCaNhan newInstance(String strArg) {
        MainCaNhan frag = new MainCaNhan();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        frag.setArguments(args);
        return frag;
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final RelativeLayout canhan = (RelativeLayout) inflater.inflate(R.layout.activity_main_ca_nhan,null);


        hTen=(TextView) canhan.findViewById(R.id.ViewHoTen);
        ngSinh=(TextView) canhan.findViewById(R.id.ViewNgaySinh);
        gTinh=(TextView) canhan.findViewById(R.id.ViewGioiTinh);
        anhDaiDien=(ImageView) canhan.findViewById(R.id.anhDaiDien);

        String hten = hTen.getText().toString();

        if(hten.equals("")){
            Toast.makeText(context,"Cập nhật thông tin.",Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(main);

            View view = getLayoutInflater().inflate(R.layout.update_thong_tin,null);

            // ddang lam den cho nay


            EditText edtten = (EditText) view.findViewById(R.id.edtten);



            builder.setView(view);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        return canhan;
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

    }
}
