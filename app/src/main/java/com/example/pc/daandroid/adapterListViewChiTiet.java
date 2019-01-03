package com.example.pc.daandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class adapterListViewChiTiet extends BaseAdapter {
    private Context context;
    private List<diadiemchitiet> data;

    public adapterListViewChiTiet(Context context, List<diadiemchitiet> d) {
        //super(context,R.layout.item_listview,data);
        this.context = context;
        data=d;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        v = inflater.inflate(R.layout.item_listview,null);
        ImageView imageView = (ImageView) v.findViewById(R.id.ivPicture);
        TextView txtTen = (TextView) v.findViewById(R.id.txtDiaDiem);
        TextView txtThongTin = (TextView) v.findViewById(R.id.txtThongTin);

        String mota="";
        mota+=data.get(position).getMoTa().substring(0,97);

        mota+="...";


        txtTen.setText(data.get(position).getTenDiaDanh());
        txtThongTin.setText(mota);
        //imageView.setImageResource(R.drawable.bien);
        Picasso.get().load("http://android1998.000webhostapp.com/DiaDanh/"+
                data.get(position).getUrlImage().get(0)+"/"+data.get(position).getUrlImage().get(1)).into(imageView);
        return v;
    }
}
