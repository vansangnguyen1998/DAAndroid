package com.example.pc.daandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ActivityDiaDiemChiTiet extends Activity implements AdapterView.OnItemSelectedListener,OnMapReadyCallback {


    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap=googleMap;
        Search(DiaDiem.getTenDiaDanh());
    }

    private GoogleMap myMap;
    private FloatingActionButton btnNhanXet;
    private FloatingActionMenu floatingActionMenu;
    private com.github.clans.fab.FloatingActionButton floatingactionbuttonDangNhap,floatingactionbuttonMap;
    private ViewGroup viewGroup,viewWeather;
    private EditText editText;
    private RatingBar ratingBar;
    private Spinner spinner;
    private TextView thongTin;
    private float numberStar;
    private ListView lvNhanXet;
    private ArrayList<danhgia> lvNX;
    private diadiemchitiet DiaDiem = new diadiemchitiet();
    private ProgressDialog progress;
    private RelativeLayout layoutMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_diem_chi_tiet);
        final Intent bundle = this.getIntent();
        // set data cho DiaDiem;
        DiaDiem = new diadiemchitiet ((diadiemchitiet) bundle.getSerializableExtra("diadiem"));
        progress=new ProgressDialog(ActivityDiaDiemChiTiet.this);
        progress.setMessage(" Đang Load Dữ Liệu ");
        progress.show();
        lvNX=new ArrayList<danhgia>();
        AnhXa();


        floatingactionbuttonDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityDiaDiemChiTiet.this,"vao ne`",Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDiaDiemChiTiet.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_login,null);

                final AppCompatEditText edtUser,edtMK;
                final TextInputLayout userLayout, passLayout;
                Button buttonDangNhap;
                edtUser = (AppCompatEditText) view.findViewById(R.id.edtUser);
                edtMK = (AppCompatEditText) view.findViewById(R.id.edtmatkhau);
                userLayout = (TextInputLayout) view.findViewById(R.id.userLayout);
                passLayout = (TextInputLayout) view.findViewById(R.id.passLayout);
                buttonDangNhap = (Button) view.findViewById(R.id.btndangnhap);

                userLayout.setCounterEnabled(true);
                userLayout.setCounterMaxLength(12);

                passLayout.setCounterEnabled(true);
                passLayout.setCounterMaxLength(12);

                edtUser.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(edtUser.getText().toString().isEmpty())
                        {
                            userLayout.setErrorEnabled(true);
                            userLayout.setError("Vui lòng nhập User");
                        }
                        else{
                            userLayout.setErrorEnabled(false);
                        }
                    }
                });

                edtMK.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(edtMK.getText().toString().isEmpty())
                        {
                            passLayout.setErrorEnabled(true);
                            passLayout.setError("Vui lòng nhập Password");
                        }
                        else{
                            userLayout.setErrorEnabled(false);
                        }
                    }
                });
                buttonDangNhap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String User, Pass;
                        User=edtUser.getText().toString();
                        Pass=edtMK.getText().toString();
                        BackgroundTask1 backgroundTask = new  BackgroundTask1(ActivityDiaDiemChiTiet.this);
                        backgroundTask.execute("Login",User,Pass);
                    }
                });

                builder.setView(view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        getNearby();
        ratingBar.setRating(4);

        if(bundle!=null) {
            thongTin.setText(DiaDiem.getMoTa());
        }

        // set picture horiontalImage.
        for(int i=1;i<DiaDiem.getUrlImage().size();i++){
            final View singleFrame = getLayoutInflater().inflate(
                    R.layout.icon_trang_thong_tin, null);

            singleFrame.setId(i);

            ImageView image = (ImageView) singleFrame.findViewById(R.id.iconScoll);

            Picasso.get().load("http://android1998.000webhostapp.com/DiaDanh/"+
                    DiaDiem.getUrlImage().get(0)+"/"+
                    DiaDiem.getUrlImage().get(i)).into(image);
            viewGroup.addView(singleFrame);
        }

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                numberStar=rating;
            }
        });

        SetWeather();

        // nhấn vào nút nhận xet để cập nhật lên database.
        btnNhanXet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(CheckLogin.User.equals("NoName")){
                    Toast.makeText(ActivityDiaDiemChiTiet.this,"Đăng nhập trước khi sử dụng chức năng.",Toast.LENGTH_SHORT).show();
                }else{
                    String sosao= String.valueOf(numberStar);
                    String txtNhanXet= editText.getText().toString();

                    final Calendar newCalendar = Calendar.getInstance();

                    String date=String.valueOf(newCalendar.get(Calendar.DAY_OF_MONTH))+"-"
                            + String.valueOf(newCalendar.get(Calendar.MONTH))+"-"+
                            String.valueOf(newCalendar.get(Calendar.YEAR));

                    BackgroundTask1 backgroundTask1 = new BackgroundTask1(ActivityDiaDiemChiTiet.this);
                    backgroundTask1.execute("NhanXet", sosao, txtNhanXet,DiaDiem.getTenTinh(),CheckLogin.User,
                            DiaDiem.getTenDiaDanh(),date);
                    editText.setText("");
                }
            }
        });

        BackgroundTask1 backgroundTask = new BackgroundTask1(ActivityDiaDiemChiTiet.this);
        backgroundTask.execute("LVNhanXet",DiaDiem.getTenTinh(),DiaDiem.getTenDiaDanh());


        // set cac du lieu hien thi cho map
        MapFragment mapFragment=(MapFragment) getFragmentManager().findFragmentById(R.id.myMap_DiaDiem);
        mapFragment.getMapAsync(this);
        hintKeybroard();
    }

    // ham search thong tin dia diem can dua len map.

    private void Search(String ten){
        Geocoder geocoder = new Geocoder(ActivityDiaDiemChiTiet.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(ten,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(list.size()>0){
            Address address = list.get(0);
            // lay duoc dia diem
            moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),15f,address.getAddressLine(0));

        }
    }

    // ham moveCamera co chuc nang dua den dia diem da set LatLng
    private void moveCamera(LatLng latLng,float zoom,String title){

        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
        if(!title.equals("My Location")) {
            MarkerOptions options = new MarkerOptions().
                    position(latLng).
                    title(title);

            myMap.addMarker(options);
        }
        hintKeybroard();
    }


    private void hintKeybroard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

// hàm set weather HorizontalScollView
    private void SetWeather(){
            // den ngay nay dang bí
            String url_location="http://dataservice.accuweather.com/locations/v1/cities/search?apikey=epiju3Mnk6o7MNoT7b6ZAcY7AMgu6RyJ&q="+
                    DiaDiem.getTenTinhKD();
            String key="";
            String url_weather_5day="http://dataservice.accuweather.com/forecasts/v1/daily/5day/"+key+"?apikey=epiju3Mnk6o7MNoT7b6ZAcY7AMgu6RyJ";

            RequestQueue requestQueue = Volley.newRequestQueue(ActivityDiaDiemChiTiet.this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url_location,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Toast.makeText(ActivityDiaDiemChiTiet.this,response,Toast.LENGTH_SHORT).show();
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = (JSONObject) jsonArray.get(0);


                                String key=jsonObject.getString("Key");
                                String url_weather_5day="http://dataservice.accuweather.com/forecasts/v1/daily/5day/"+key+"?apikey=epiju3Mnk6o7MNoT7b6ZAcY7AMgu6RyJ";

                                RequestQueue requestQueue1 = Volley.newRequestQueue(ActivityDiaDiemChiTiet.this);
                                StringRequest str = new StringRequest(Request.Method.GET, url_weather_5day,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String res) {

                                                try {

                                                    //String image_icon="https://developer.accuweather.com/sites/default/files/";//05-s.png
                                                    // json array gom co 5 phan tu moi phan tu la mot ngay
                                                    JSONObject json=new JSONObject(res);
                                                    // jcon array chua 5 ngay
                                                    JSONArray arrJson = json.getJSONArray("DailyForecasts");

                                                    final ArrayList<weather> data = new ArrayList<weather>();

                                                    int len=arrJson.length();

                                                    for(int i=0; i<len;i++){
                                                        String date, url;
                                                        int minValue;
                                                        int maxValue;
                                                        int iconNgay;
                                                        int iconDem;
                                                        JSONObject jsonobj= (JSONObject) arrJson.get(i);
                                                        date = jsonobj.getString("Date").substring(0,10);
                                                        JSONObject jsonTemperature=jsonobj.getJSONObject("Temperature");
                                                        JSONObject jsonDay=jsonobj.getJSONObject("Day");
                                                        JSONObject jsonNight=jsonobj.getJSONObject("Night");

                                                        JSONObject jsonMin = jsonTemperature.getJSONObject("Minimum");
                                                        JSONObject jsonMax = jsonTemperature.getJSONObject("Maximum");

                                                        minValue = jsonMin.getInt("Value");
                                                        maxValue = jsonMax.getInt("Value");

                                                        iconNgay=jsonDay.getInt("Icon");
                                                        iconDem=jsonNight.getInt("Icon");

                                                        url=jsonobj.getString("MobileLink");

                                                        weather wea=new weather(date,minValue,maxValue,iconNgay,iconDem,url);
                                                        data.add(wea);
                                                    }
                                                    // den cho nay la data da có dữ liệu weather. công việc tiếp theo là set data vào
                                                    // horizontalScollView.

                                                    // thực hiện add dữ liệu vào horizontalScollView
                                                    String image_icon="http://android1998.000webhostapp.com/Image/"; // link ảnh
                                                    if(data!=null) {
                                                        for (int i = 0; i < 5; i++){
                                                            final View singleFrame = getLayoutInflater().inflate(
                                                                    R.layout.item_weather, null);

                                                            singleFrame.setId(i);

                                                            TextView date= (TextView) singleFrame.findViewById(R.id.txt_date);
                                                            TextView nhietdo = (TextView) singleFrame.findViewById(R.id.txt_nhietdo);
                                                            ImageView iconDay = (ImageView) singleFrame.findViewById(R.id.iconDay_weather);
                                                            ImageView iconNight = (ImageView) singleFrame.findViewById(R.id.iconNight_weather);

                                                            // set data cho từng cái nhớ.
                                                            date.setText(data.get(i).getDate().substring(0,10));
                                                            nhietdo.setText(String.valueOf((int)((data.get(i).getMaxValue()-32)/1.8))+"~"+
                                                                    String.valueOf((int)((data.get(i).getMinValue()-32)/1.8)));
                                                            Picasso.get().load(image_icon+String.valueOf(data.get(i).getIcon_Ngay())+".png")
                                                                    .into(iconDay);
                                                            Picasso.get().load(image_icon+String.valueOf(data.get(i).getIcon_Dem())+".png")
                                                                    .into(iconNight);

                                                            //ImageView image = (ImageView) singleFrame.findViewById(R.id.iconScoll);
                                                            //image.setImageResource(Imageview[i]);

                                                            viewWeather.addView(singleFrame);

                                                            // click vào item weathere thì nó sẽ chạy đến web để hiển thị chi tiết cho nguwiof dùng

                                                            final int finalI = i;
                                                            singleFrame.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {
                                                                    final Uri linkMobile = Uri.parse(data.get(finalI).getUrl());

                                                                    Intent browser = new Intent(Intent.ACTION_VIEW,linkMobile);
                                                                    startActivity( browser);
                                                                }
                                                            });
                                                        }
                                                    }


                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                });
                                requestQueue1.add(str);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(ActivityDiaDiemChiTiet.this,""+error+"",Toast.LENGTH_SHORT).show();
                        }
                    });
            requestQueue.add(stringRequest);


    }
// hàm ánh xạ các thông tin bên xml qua java
    private void AnhXa(){

        btnNhanXet = (FloatingActionButton) findViewById(R.id.btnNhanXet);
        thongTin = (TextView) findViewById(R.id.ThongTin);

        editText = (EditText) findViewById(R.id.NhanXet);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
        //spinner = (Spinner) findViewById(R.id.DichVu);

        lvNhanXet = (ListView) findViewById(R.id.lvNhanXet);

        viewGroup = (ViewGroup) findViewById(R.id.viewgroup);
        viewWeather = (ViewGroup) findViewById(R.id.viewWeather);

        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.floattinhacctionMenu);
        floatingactionbuttonDangNhap = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.floatingactionbuttonDangNhap);
        floatingactionbuttonMap = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.floatingactionbuttonMap);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getNearby(){
        String url="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=5000&type=market&sensor=true&key=AIzaSyAxbKrTQ39T9eoq_YWrlyTkTwN0Dp1M--A";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(ActivityDiaDiemChiTiet.this,response,Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Toast.makeText(ActivityDiaDiemChiTiet.this,""+error+"",Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(stringRequest);
    }

    private class BackgroundTask1 extends AsyncTask<String,Void,String> {
        private Context ctx;
        private String method;
        public BackgroundTask1(Context ctx) {
            this.ctx = ctx;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {
            String insertNV_url = "http://android1998.000webhostapp.com/php/insertNhanXet.php";
            String lvNV_url = "http://android1998.000webhostapp.com/php/getNhanXet.php";
            String login_url ="http://android1998.000webhostapp.com/php/kt_login.php";

            method = params[0];

            if (method.equals("NhanXet")){

                try {
                    URL url = new URL(insertNV_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));

                    String data = URLEncoder.encode("_SoSao", "UTF-8")  + "=" + URLEncoder.encode(params[1], "UTF-8")+"&"+
                            URLEncoder.encode("_NhanXet", "UTF-8")  + "=" + URLEncoder.encode(params[2], "UTF-8")+"&"+
                            URLEncoder.encode("_TenTinh", "UTF-8")  + "=" + URLEncoder.encode(params[3], "UTF-8")+"&"+
                            URLEncoder.encode("_User", "UTF-8")  + "=" + URLEncoder.encode(params[4], "UTF-8")+"&"+
                            URLEncoder.encode("_TenDiaDanh", "UTF-8")  + "=" + URLEncoder.encode(params[5], "UTF-8")+"&"+
                            URLEncoder.encode("_NgayThang", "UTF-8")  + "=" + URLEncoder.encode(params[6], "UTF-8");

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

            }else if(method.equals("LVNhanXet")){
                try {
                    URL url = new URL(lvNV_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));

                    String data = URLEncoder.encode("_TenTinh", "UTF-8")  + "=" + URLEncoder.encode(params[1], "UTF-8")+"&"+
                              URLEncoder.encode("_TenDiaDanh", "UTF-8")  + "=" + URLEncoder.encode(params[2], "UTF-8");
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
            }else if(method.equals("Login")){
                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));

                    String data = URLEncoder.encode("_User", "UTF-8")  + "=" + URLEncoder.encode(params[1], "UTF-8")+"&"+
                            URLEncoder.encode("_Pass", "UTF-8")  + "=" + URLEncoder.encode(params[2], "UTF-8");

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
            }
            return "false...";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
           if(method.equals("LVNhanXet")){
               try {
                   JSONArray jsonArray = new JSONArray(result);

                   int len= jsonArray.length();

                    if(len>5){
                        len=5;
                    }
                   for(int i=0;i<len;i++){
                       JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                       String noidung= jsonObject.getString("noidung");
                       int sosao=Integer.parseInt(jsonObject.getString("sosao"));
                       String user= jsonObject.getString("user");
                       String ngaythang=jsonObject.getString("ngaythang");
                       danhgia DanhGia = new danhgia(noidung,sosao,user,ngaythang);
                       lvNX.add(DanhGia);
                   }

                   adapterlvnhanxet adapter = new adapterlvnhanxet(ActivityDiaDiemChiTiet.this,lvNX);
                   lvNhanXet.setAdapter(adapter);

               } catch (JSONException e) {
                   e.printStackTrace();
               }

               progress.dismiss();
           }
        }
    }
}
