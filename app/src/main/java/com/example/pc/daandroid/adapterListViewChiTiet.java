package com.example.pc.daandroid;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class adapterListViewChiTiet extends BaseAdapter {
    private Context context;
    private List<String> TenTinh;
    private List<String> ThongTin;
    private List<Integer> image;

    public adapterListViewChiTiet(Context context, List<String> tenTinh, List<String> thongTin, List<Integer> image) {
        this.context = context;
        TenTinh = tenTinh;
        ThongTin = thongTin;
        this.image = image;
    }

    @Override
    public int getCount() {
        return image.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context,R.layout.item_listview,null);
        ImageView imageView = (ImageView) v.findViewById(R.id.ivPicture);
        TextView txtTen = (TextView) v.findViewById(R.id.txtDiaDiem);
        TextView txtThongTin = (TextView) v.findViewById(R.id.txtThongTin);

        txtTen.setText(TenTinh.get(position));
        txtThongTin.setText(ThongTin.get(position));
        imageView.setImageResource(image.get(position));
        return v;
    }
}
