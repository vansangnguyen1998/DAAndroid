package com.example.pc.daandroid;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;

public class ActivityDiaDiemChiTiet extends Activity implements AdapterView.OnItemSelectedListener{

    Button HienThiDiaDiem,ThoiTiet;
    ViewGroup viewGroup;
    EditText editText;
    RatingBar ratingBar;
    Integer [] Imageview= {R.drawable.android1,R.drawable.android3,R.drawable.bien};
    Spinner spinner;

    String [] adapterSpinner = {"Nhà Hàng", "Khách Sạn", "ATM"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_diem_chi_tiet);

        AnhXa();

        for(int i=0;i<Imageview.length;i++){
            final View singleFrame = getLayoutInflater().inflate(
                    R.layout.icon_trang_thong_tin, null);

            singleFrame.setId(i);

            ImageView image = (ImageView) singleFrame.findViewById(R.id.iconScoll);

            image.setImageResource(Imageview[i]);

            viewGroup.addView(singleFrame);
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, adapterSpinner);

        adapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);

        spinner.setAdapter(adapter);
    }


    private void AnhXa(){
        HienThiDiaDiem = (Button) findViewById(R.id.HienThiDiaDiem);
        ThoiTiet = (Button) findViewById(R.id.ThoiTiet);
        viewGroup = (ViewGroup) findViewById(R.id.viewgroup);
        editText = (EditText) findViewById(R.id.NhanXet);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
        spinner = (Spinner) findViewById(R.id.DichVu);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
