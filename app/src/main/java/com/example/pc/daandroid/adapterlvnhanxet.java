package com.example.pc.daandroid;

import android.content.Context;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;


public class adapterlvnhanxet extends BaseAdapter {

    private Context context;
    private List<danhgia> data;

    public adapterlvnhanxet(Context context, List<danhgia> data) {
        this.context = context;
        this.data = data;
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

        v = inflater.inflate(R.layout.item_lv_nhanxet,null);
        TextView txtUser = (TextView) v.findViewById(R.id.txtUser);
        TextView txtNoiDung = (TextView) v.findViewById(R.id.txtNoiDung);
        TextView txtNgayThang = (TextView) v.findViewById(R.id.txtNgayThang);
        RatingBar ratingNX = (RatingBar) v.findViewById(R.id.ratingNX);

        txtUser.setText(data.get(position).getUser());
        txtNgayThang.setText(data.get(position).getNgayThang());
        txtNoiDung.setText(data.get(position).getNoiDung());

        ratingNX.setRating(data.get(position).getSao());

        return v;
    }
}
