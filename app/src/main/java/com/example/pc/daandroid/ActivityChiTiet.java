package com.example.pc.daandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ActivityChiTiet extends AppCompatActivity {

    private List<String> TenTinh;
    private List<String> ThongTin;
    private List<Integer> image;
    private TextView txtName;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);

        listView = (ListView) findViewById(R.id.lv) ;
        txtName = (TextView) findViewById(R.id.Name);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                new String[]{"item1","item2", "item3","item4"});

        listView.setAdapter(adapter);

        //TenTinh.add
        Bundle bundle = this.getIntent().getExtras();
        if(bundle.getString("LoaiDuLich").equals("Bien")){
            txtName.setText("Du Lich Bien");
            Toast.makeText(this,"Du lich bien",Toast.LENGTH_SHORT).show();
        }else if(bundle.getString("LoaiDuLich").equals("TheoTinh")){
            String tentinh=bundle.getString("TenTinh");
            Toast.makeText(this,tentinh,Toast.LENGTH_SHORT).show();
        }
    }
}
