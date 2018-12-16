package com.example.pc.daandroid;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
import java.util.List;
import java.util.Map;

public class ActivityChiTiet extends AppCompatActivity {

    private TextView txtName;
    private ListView listView;
    private ArrayList<diadiemchitiet> data;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);

        data=new ArrayList<diadiemchitiet>();

        listView = (ListView) findViewById(R.id.lv) ;
        txtName = (TextView) findViewById(R.id.Name);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                //new String[]{"item1","item2", "item3","item4"});



        //TenTinh.add
        Bundle bundle = this.getIntent().getExtras();
        if(bundle.getString("LoaiDuLich").equals("Bien")){
            txtName.setText("Du Lich Bien");

            BackgroundTask1 backgroundTask1 = new BackgroundTask1(this);
            backgroundTask1.execute("LoaiDL","bien");


            Toast.makeText(this,"Du lich bien",Toast.LENGTH_SHORT).show();
        }else if(bundle.getString("LoaiDuLich").equals("LichSu")){
            txtName.setText("Du Lich Lich Su");

            BackgroundTask1 backgroundTask1 = new BackgroundTask1(this);
            backgroundTask1.execute("LoaiDL","lichsu");


            Toast.makeText(this,"Du lich lich su",Toast.LENGTH_SHORT).show();
        }else if(bundle.getString("LoaiDuLich").equals("RungNui")){
            txtName.setText("Du Lich Rung Nui");

            BackgroundTask1 backgroundTask1 = new BackgroundTask1(this);
            backgroundTask1.execute("LoaiDL","nui");


            Toast.makeText(this,"Du lich rung nui",Toast.LENGTH_SHORT).show();
        }else if(bundle.getString("LoaiDuLich").equals("SinhThai")){
            txtName.setText("Du Lich Sinh Thai");

            BackgroundTask1 backgroundTask1 = new BackgroundTask1(this);
            backgroundTask1.execute("LoaiDL","sinhthai");


            Toast.makeText(this,"Du lich sinh thai",Toast.LENGTH_SHORT).show();
        }else if(bundle.getString("LoaiDuLich").equals("MienTay")){
            txtName.setText("Du Lich Mien Tay");

            BackgroundTask1 backgroundTask1 = new BackgroundTask1(this);
            backgroundTask1.execute("LoaiDL","mientay");


            Toast.makeText(this,"Du lich mien tay",Toast.LENGTH_SHORT).show();
        }else if(bundle.getString("LoaiDuLich").equals("TayBac")){
            txtName.setText("Du Lich Tay Bac");

            BackgroundTask1 backgroundTask1 = new BackgroundTask1(this);
            backgroundTask1.execute("LoaiDL","taybac");


            Toast.makeText(this,"Du lich tay bac",Toast.LENGTH_SHORT).show();
        }else if(bundle.getString("LoaiDuLich").equals("TheoTinh")){
            String tentinh=bundle.getString("TenTinh");
            txtName.setText(tentinh);

            BackgroundTask1 backgroundTask1 = new BackgroundTask1(this);
            backgroundTask1.execute("TheoTinh",tentinh);

            Toast.makeText(this,tentinh,Toast.LENGTH_SHORT).show();
        }else if(bundle.getString("LoaiDuLich").equals("XuHuong")){

            BackgroundTask1 backgroundTask1 = new BackgroundTask1(this);
            backgroundTask1.execute("XuHuong");
            Toast.makeText(this,"Xu hướng",Toast.LENGTH_SHORT).show();
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent =new Intent(ActivityChiTiet.this,ActivityDiaDiemChiTiet.class);
                //Bundle bundle1 = new Bundle();

                intent.putExtra("tentinh",data.get(position).getTenTinh());
                intent.putExtra("tendiadanh",data.get(position).getTenDiaDanh());
                intent.putExtra("mota",data.get(position).getMoTa());
                intent.putExtra("tentinh_KD", data.get(position).getTenTinhKD());
                startActivity(intent);
            }
        });
    }



    private class BackgroundTask1 extends AsyncTask<String,Void,String> {
        private Context ctx;
        private String User;


        public BackgroundTask1(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            String loaidl_url = "http://android1998.000webhostapp.com/php/loaidulich.php";
            String theotinh_url="http://android1998.000webhostapp.com/php/dulichtinh.php";
            String XuHuong_url="http://android1998.000webhostapp.com/php/xuhuong.php";

            String method = params[0];
            //User=params[1];
            if (method.equals("LoaiDL")){

                try {
                    URL url = new URL(loaidl_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));

                    String data = URLEncoder.encode("_LoaiDL", "UTF-8")  + "=" + URLEncoder.encode(params[1], "UTF-8");
//                            URLEncoder.encode("_Pass", "UTF-8")  + "=" + URLEncoder.encode(params[2], "UTF-8")+"&"+
//                            URLEncoder.encode("_Gmail", "UTF-8")  + "=" + URLEncoder.encode(params[3], "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));


                    String response="",line="";

                    while((line=bufferedReader.readLine())!=null){
                        response+=line;
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    return response;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(method.equals("TheoTinh")){

                try {
                    URL url = new URL(theotinh_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));

                    String data = URLEncoder.encode("_TenTinh", "UTF-8")  + "=" + URLEncoder.encode(params[1], "UTF-8");
//                            URLEncoder.encode("_Pass", "UTF-8")  + "=" + URLEncoder.encode(params[2], "UTF-8")+"&"+
//                            URLEncoder.encode("_Gmail", "UTF-8")  + "=" + URLEncoder.encode(params[3], "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));


                    String response="",line="";

                    while((line=bufferedReader.readLine())!=null){
                        response+=line;
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    return response;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(method.equals("XuHuong")){

                try {
                    URL url = new URL(XuHuong_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));


                    String response="",line="";

                    while((line=bufferedReader.readLine())!=null){
                        response+=line;
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    return response;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return "false...";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

//            Toast.makeText(ctx,result,Toast.LENGTH_SHORT).show();

            setData(data, result);

        }
    }

    private void setData(ArrayList<diadiemchitiet> data,String result){

        try {
            JSONArray jsonArray= new JSONArray(result);
            int n=jsonArray.length();
            for(int i=0;i<n;i++) {

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);


                String tentinh = jsonObject.getString("tentinh");
                String tendiadanh=jsonObject.getString("tendiadanh");

                String mota=jsonObject.getString("mota");
                String urlImage=jsonObject.getString("hinhanh");
                String tentinhkd=jsonObject.getString("tentinh_KD");

                diadiemchitiet temp=new diadiemchitiet(urlImage,mota,tendiadanh,tentinh,tentinhkd);

                data.add(temp);

                //Toast.makeText(ctx, "" + jsonObject.getString("tentinh") + jsonArray.length(), Toast.LENGTH_SHORT).show();
            }

            adapterListViewChiTiet adapter= new adapterListViewChiTiet(ActivityChiTiet.this,data);

            listView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
