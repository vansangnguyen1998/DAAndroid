package com.example.pc.daandroid;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class adapter_lv_weather extends BaseAdapter {

    private Context context;
    private List<weather> data;

    public adapter_lv_weather(Context context, List<weather> data) {
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

        String image_icon="http://android1998.000webhostapp.com/Image/";//05-s.png

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.item_weather,null);

        TextView txtdate= (TextView) v.findViewById(R.id.txt_date);
        TextView txt_nhietdo = (TextView) v.findViewById(R.id.txt_nhietdo);
        ImageView imageDay = (ImageView) v.findViewById(R.id.iconDay_weather);
        ImageView imageNight = (ImageView) v.findViewById(R.id.iconNight_weather);

        txtdate.setText(data.get(position).getDate());
        String nhietdo= String.valueOf((int)((data.get(position).getMaxValue()-32)/1.8))+"~"+
                String.valueOf((int)((data.get(position).getMinValue()-32)/1.8));
        txt_nhietdo.setText(nhietdo);

        String iconDay=String.valueOf(data.get(position).getIcon_Ngay());
        String iconNight=String.valueOf(data.get(position).getIcon_Dem());

        Picasso.get().load(image_icon+iconDay+".png").into(imageDay);
        Picasso.get().load(image_icon+iconNight+".png").into(imageNight);

        return v;
    }


}
